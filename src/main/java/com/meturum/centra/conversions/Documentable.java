package com.meturum.centra.conversions;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.meturum.centra.conversions.annotations.DocumentableMethod;
import com.meturum.centra.mongo.Mongo;
import com.meturum.centra.system.SystemManager;
import org.bson.Document;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.lang.annotation.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;

public interface Documentable {

    /**
     * @param instance The object to serialize.
     *
     * @return a {@link Document} containing all the selected fields of the given {@link Object}
     */
    static Document toDocument(@NotNull Object instance) {
        Preconditions.checkArgument(instance instanceof Documentable, "Object (instance) must be an instance of Documentable");

        // Get the serialization type from the class. If it is not found, default to ALL.
        Document document = new Document();

        List<Field> filteredFields = getTrueFields(instance.getClass());
        for(Field field : filteredFields) {
            field.setAccessible(true);

            try {
                Object value = field.get(instance);

                if(value == null) {
                    document.put(field.getName(), null);
                    continue;
                }

                if (isPrimitive(field.getType())) {
                    document.put(field.getName(), value);
                    continue;
                }

                if(field.getType().equals(UUID.class)) {
                    document.put(field.getName(), value.toString()); // Cannot put UUID objects in MongoDB...
                    continue;
                }

                SerializationMethod serializationMethod = field.isAnnotationPresent(Serialize.class)
                        ? field.getAnnotation(Serialize.class).method()
                        : SerializationMethod.OBJECT;

                switch (serializationMethod) {
                    case OBJECT -> {
                        if(!(value instanceof Documentable)) continue;

                        document.put(field.getName(), ((Documentable) value).asDocument());
                    }
                    case REFERENCE -> {
                        if(value instanceof IDynamicTag) {
                            document.put(field.getName(), ((IDynamicTag) value).getUniqueId().toString());
                        }else {
                            try {
                                Method method = getSecretMethod(field.getType(), SerializationMethod.REFERENCE, "to"); // Try to find method automatically.
                                if(method == null) continue;
                                method.setAccessible(true);

                                if(!method.isAnnotationPresent(DocumentableMethod.class))
                                    throw new NoSuchMethodException(); // False trigger catch block.

                                document.put(field.getName(), invokeMethod(method, value));
                            }catch (NoSuchMethodException ignored) { }
                        }
                    }
                    case STRING -> {
                        try {
                            Method method = getSecretMethod(field.getType(), SerializationMethod.STRING, "to"); // Try to find method automatically.
                            if(method == null) continue;
                            method.setAccessible(true);

                            if(!method.isAnnotationPresent(DocumentableMethod.class))
                                throw new NoSuchMethodException(); // False trigger catch block.

                            document.put(field.getName(), invokeMethod(method, value));
                        } catch (NoSuchMethodException exception) {
                            document.put(field.getName(), value.toString());
                        }
                    }
                }
            }catch (Exception ignored) { }
        }

        return document;
    }

    /**
     * Inserts a document into an existing instance of an object.
     *
     * @param document The document to insert.
     * @param instance The instance to insert the document into.
     */
    static void insertDocument(@NotNull SystemManager manager, Document document, Object instance) {
        List<Field> filteredFields = getTrueFields(instance.getClass());

        for(Field field : filteredFields) {
            try {
                field.setAccessible(true);

                Object value = document.get(field.getName());
                if (value == null) continue;

                SerializationMethod serializationMethod = field.isAnnotationPresent(Serialize.class)
                        ? field.getAnnotation(Serialize.class).method()
                        : SerializationMethod.OBJECT;

                // Define our Setter methods
                Method setter = Arrays.stream(instance.getClass().getDeclaredMethods())
                        .filter(method -> method.getName().equalsIgnoreCase("set" + field.getName()))
                        .findFirst().orElse(null);

                if (isPrimitive(field.getType())) {
                    if(setter == null) field.set(instance, value);
                    else invokeMethod(setter, instance, value);

                    continue;
                }

                if (field.getType().equals(UUID.class)) {
                    if(setter == null) field.set(instance, UUID.fromString((String) value)); // Cannot put UUID objects in MongoDB...
                    else invokeMethod(setter, instance, value);

                    continue;
                }

                switch (serializationMethod) {
                    case OBJECT -> {
                        if(!(value instanceof Documentable)) continue;

                        if(setter == null) field.set(instance, ((Documentable) value).asDocument());
                        else invokeMethod(setter, instance, ((Documentable) value).asDocument());
                    }
                    case REFERENCE -> {
                        try {
                            Method method = getSecretMethod(field.getType(), SerializationMethod.REFERENCE, "from"); // Try to find method automatically.
                            if(method == null) continue;
                            method.setAccessible(true);

                            if (!method.isAnnotationPresent(DocumentableMethod.class) || !Arrays.asList(method.getParameterTypes()).contains(UUID.class))
                                throw new NoSuchMethodException(); // False trigger catch block.

                            if(value instanceof String) value = UUID.fromString((String) value);
                            if(setter == null) field.set(instance, invokeMethod(method, null, manager, value));
                            else invokeMethod(setter, instance, invokeMethod(method, null, manager, value));
                        } catch (NoSuchMethodException ignored) { }
                    }
                    case STRING -> {
                        try {
                            Method method = getSecretMethod(field.getType(), SerializationMethod.STRING, "from"); // Try to find method automatically.
                            if(method == null) continue;
                            method.setAccessible(true);

                            if(!method.isAnnotationPresent(DocumentableMethod.class))throw new NoSuchMethodException(); // False trigger catch block.

                            if(setter == null) field.set(instance, invokeMethod(method, null, manager, value));
                            else invokeMethod(setter, instance, invokeMethod(method, null, manager, value));
                        } catch (NoSuchMethodException ignored) {  }
                    }
                }
            }catch (Exception ignored) { } //TODO: Add a notification for errors.
        }
    }

    /**
     * Creates an instance of the inputted object and inserts the document data into it.
     *
     * @param manager the system manager.
     * @param document The document to convert.
     * @param instance The instance to insert the document into.
     */
    static @NotNull <T> T fromDocument(@NotNull SystemManager manager, Document document, Class<? extends T> instance) throws Exception {
        T object;

        boolean isAssignable = Documentable.class.isAssignableFrom(instance);
        Constructor<? extends T> constructor = isAssignable ? instance.getDeclaredConstructor(Mongo.class) : instance.getConstructor();
        constructor.setAccessible(true);

        object = isAssignable ? constructor.newInstance(manager.search(Mongo.class)) : constructor.newInstance();

        insertDocument(manager, document, object);
        return object;
    }

    /**
     * Privately invokes a given method and returns the result.
     * (This is used to throw exceptions caused by the method invocation.)
     *
     * @param method The method to invoke.
     * @param instance The instance to invoke the method on.
     * @param parameters The parameters to pass to the method.
     * @return The result of the method invocation.
     */
    private static Object invokeMethod(Method method, @Nullable Object instance, Object... parameters) {
        Object value = null;

        try { // Nested try-catch block to print any exception occurred while invoking the method.
            value = method.invoke(instance, parameters);
        }catch (Exception exception) { exception.printStackTrace(); }

        return value;
    }

    /**
     * Gets all the fields of a class, including the ones in the superclasses.
     * Excludes static fields & optionally fields not annotated with {@link Serialize}.
     *
     * @param child The class to get the fields of.
     * @return A list of all the fields.
     */
    private static List<Field> getTrueFields(Class<?> child) {
        List<Field> arr = new ArrayList<>();
        Class<?> parent = child.getSuperclass();

        if (parent != null)
            arr.addAll(List.of(parent.getDeclaredFields()));
        arr.addAll(List.of(child.getDeclaredFields())); // The reason why we add the superclass fields first is to make sure that the superclass fields are at the beginning of the list.

        List<Field> filteredFields = new ArrayList<>();
        for(Field field : arr) {
            try {
                field.setAccessible(true);

                if (Modifier.isStatic(field.getModifiers())) continue;
                if (Modifier.isTransient(field.getModifiers())) continue;
                if (field.getName().contains("$")) continue;

                filteredFields.add(field);

                field.setAccessible(false);
            }catch (Exception ignored) { }
        }

        return ImmutableList.copyOf(filteredFields);
    }

    /**
     * Gets all methods annotated by the inputted annotation.
     *
     * @param type The class to retrieve methods from..
     * @param annotation The annotation to check for.
     * @return A list of methods.
     */
    private static List<Method> getMethodsByAnnotation(Class<?> type, Class<? extends Annotation> annotation) {
        List<Method> fields = new ArrayList<>();

        for(Method method : type.getDeclaredMethods()) {
            method.setAccessible(true);

            try {
                if(!method.isAnnotationPresent(annotation))continue;

                fields.add(method);
            }catch (Exception ignored) { }
        }

        return fields;
    }


    /**
     * Finds all methods annotated by {@link DocumentableMethod} and returns the one with the inputted serialization method.
     *
     * @param type The class to retrieve methods from.
     * @param serializationMethod The name of the method.
     * @return The method, or null if none is found.
     */
    private static Method getSecretMethod(Class<?> type, SerializationMethod serializationMethod, @Nullable String prefix) {
        List<Method> methods = getMethodsByAnnotation(type, DocumentableMethod.class);

        for(Method method : methods) {
            method.setAccessible(true);

            if(method.getName().equalsIgnoreCase("_"+prefix+serializationMethod.name()))
                return method;
        }

        return null;
    }

    private static boolean isPrimitive(Class<?> object) {
        return object.isPrimitive() // Actual primitive types.
                || object.equals(String.class)
                || object.equals(Integer.class)
                || object.equals(Long.class)
                || object.equals(Double.class)
                || object.equals(Float.class)
                || object.equals(Boolean.class)
                || object.equals(Byte.class)
                || object.equals(Character.class)
                || object.equals(Short.class)

                // Aren't primitives, but are treated as such. (Naturally supported by MongoDB)
                || object.isAssignableFrom(List.class)
                || object.isAssignableFrom(Map.class);
    }

    Document asDocument();

    enum SerializationMethod {
        OBJECT(), REFERENCE(), STRING(), IGNORE()
    }

    @Target({ElementType.FIELD})
    @Retention(RetentionPolicy.RUNTIME)
    @interface Serialize {
        SerializationMethod method() default SerializationMethod.OBJECT;
    }

}