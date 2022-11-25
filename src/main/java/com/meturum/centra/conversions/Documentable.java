package com.meturum.centra.conversions;

import com.google.common.collect.ImmutableList;
import com.meturum.centra.conversions.annotations.DocumentableMethod;
import com.meturum.centra.mongo.Mongo;
import com.meturum.centra.system.System;
import com.meturum.centra.system.SystemManager;
import org.bson.Document;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.lang.annotation.*;
import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public interface Documentable {

    /**
     * @param object The object to serialize.
     *
     * @return a {@link Document} containing all the selected fields of the given {@link Object}
     */
    static @NotNull Document toDocument(@NotNull final Documentable object) {
        final Class<?> clazz = object.getClass();
        final Document document = new Document();

        List<Field> fields = getFields(clazz);
        for (final Field field : fields) { // iterate through all fields
            try {
                field.setAccessible(true);

                final Object value = field.get(object);
                Object serialized = value;
                if(value == null) continue; // skip null values

                final boolean isArray = field.getType().isArray();
                final boolean isList = List.class.isAssignableFrom(field.getType());

                final Serialize serializeAnnotation = field.isAnnotationPresent(Serialize.class) ? field.getAnnotation(Serialize.class) : null;
                final SerializationMethod serializationMethod = serializeAnnotation != null ? serializeAnnotation.method() : SerializationMethod.OBJECT;
                final Class<?> type = serializeAnnotation != null ?
                        serializeAnnotation.type() != Object.class ? serializeAnnotation.type() : field.getType()
                        : field.getType();
                final boolean saveTags = serializeAnnotation == null || serializeAnnotation.save();

                if(isArray) {
                    serialized = Arrays.asList((Object[]) value); // convert array to list
                } else if (!isList) { // if not a list,
                    serialized = List.of(value); // convert single value to list
                }

                // Attempt to find Documentable#toX() method...
                Method method = Arrays.stream(type.getDeclaredMethods()).filter(m -> m.getName().equalsIgnoreCase("serialize"))
                        .findFirst()
                        .orElse(null);

                if(IDynamicTag.class.isAssignableFrom(type) && method == null) { // If it's a tag we can use the base serialization method
                    method = Arrays.stream(IDynamicTag.class.getDeclaredMethods()).filter(m -> m.getName().equalsIgnoreCase("serialize"))
                            .findFirst()
                            .orElse(null);
                }

                if (method != null) method.setAccessible(true);

                final ArrayList<Object> array = new ArrayList<>();
                for (final Object element : List.copyOf((List<?>) serialized)) { // Iterate over a copy of serialized to prevent modification exceptions.
                    switch (serializationMethod) {
                        case OBJECT -> {
                            if(element instanceof Documentable documentable) {
                                array.add(documentable.asDocument());
                            }else if(element instanceof UUID uuid) {
                                array.add(uuid.toString());
                            }else array.add(element);
                        }
                        default -> {
                            if(method == null) continue;
                            if(!method.isAnnotationPresent(DocumentableMethod.class))
                                throw new NoSuchMethodException(); // if the method is not annotated with DocumentableMethod, throw an exception

                            if(saveTags && element instanceof IDynamicTag tag)
                                tag.saveSync(true);

                            array.add(invokeMethod(method, element));
                        }
                    }
                }

                serialized = isArray || isList ? array : array.get(0);
                if(serialized instanceof UUID uuid) serialized = uuid.toString();
                document.append(field.getName(), serialized);
            }catch (Exception ignored) { }
        }

        return document;
    }

    /**
     * Inserts a document into an existing object of an object.
     *
     * @param document The document to insert.
     * @param object The object to insert the document into.
     */
    static void insertDocument(@NotNull final SystemManager manager, @NotNull final Document document, @NotNull final Object object) {
        final List<Field> fields = getFields(object.getClass());

        for (final Field field : fields) {
            try {
                field.setAccessible(true);

                final Object value = document.get(field.getName());
                if (value == null) continue; // If null no reason to continue.
                Object deserialized = value;

                final Method setter = Arrays.stream(object.getClass().getDeclaredMethods())
                        .filter(m -> m.getName().equalsIgnoreCase("set" + field.getName()))
                        .findFirst().orElse(null);

                final boolean isArray = field.getType().isArray();
                final boolean isList = List.class.isAssignableFrom(field.getType());

                final Serialize serializeAnnotation = field.isAnnotationPresent(Serialize.class) ? field.getAnnotation(Serialize.class) : null;
                final SerializationMethod serializationMethod = serializeAnnotation != null ? serializeAnnotation.method() : SerializationMethod.OBJECT;
                final Class<?> type = serializeAnnotation != null ? serializeAnnotation.type() != Object.class ? serializeAnnotation.type() : field.getType() : field.getType();

                if(!(isArray || isList)) {
                    deserialized = List.of(value);
                }

                // Attempt to find Documentable#fromX() method...
                final Method method = Arrays.stream(type.getDeclaredMethods()).filter(m -> m.getName().equalsIgnoreCase("deserialize"))
                        .findFirst()
                        .orElse(null);
                if (method != null) method.setAccessible(true);

                final ArrayList<Object> arrayList = new ArrayList<>();
                for (final Object element : List.copyOf((List<?>) deserialized)) {
                    switch (serializationMethod) {
                        case OBJECT -> {
                            if(element instanceof Document doc) {
                                arrayList.add(Documentable.fromDocument(manager, doc, type));
                                continue;
                            }

                            arrayList.add(element);
                        }
                        default -> {
                            if(method == null) continue;
                            if(!method.isAnnotationPresent(DocumentableMethod.class))
                                throw new NoSuchMethodException();

                            arrayList.add(invokeMethod(method, null, manager, element));
                        }
                    }
                }

                deserialized = arrayList;
                if(isArray) {
                  Object[] nonTypedArray = arrayList.toArray();

                  Object typedArray = Array.newInstance(type, nonTypedArray.length);
                  java.lang.System.arraycopy(nonTypedArray, 0, typedArray, 0, nonTypedArray.length);

                  deserialized = typedArray;
                } else if(!isList) deserialized = arrayList.get(0);

                if(type.equals(UUID.class) && deserialized instanceof String string) {
                    deserialized = UUID.fromString(string);
                }

                if (setter != null) invokeMethod(setter, object, deserialized);
                else field.set(object, deserialized);
            } catch (Exception ignored) {
                ignored.printStackTrace();
            }
        }
    }

    /**
     * Creates an instance of the inputted object and inserts the document data into it.
     *
     * @param manager the system manager.
     * @param document The document to convert.
     * @param instance The instance to insert the document into.
     */
    static @NotNull <T> T fromDocument(@NotNull final SystemManager manager, @NotNull final Document document, @NotNull final Class<? extends T> instance) throws Exception {
        T object;

        Constructor<? extends T>[] constructors = (Constructor<? extends T>[]) instance.getDeclaredConstructors();
        Constructor<? extends T> constructor = null;

        for (Constructor<? extends T> compare : constructors) {
            if(!compare.isAnnotationPresent(DocumentableMethod.class)) continue;

            constructor = compare;
            break;
        }

        if(constructor == null)
            constructor = instance.getDeclaredConstructor();

        constructor.setAccessible(true);

        ArrayList<Object> parameters = new ArrayList<>();
        for (Parameter parameter : constructor.getParameters()) {
            int orginalSize = parameters.size();
            Class<?> type = parameter.getType();

            if (SystemManager.class.equals(type)) parameters.add(manager); // system manager
            if (System.class.isAssignableFrom(type)) parameters.add(manager.search((Class<? extends System>) type)); // any system registered in the system manager
            if (Document.class.equals(type)) parameters.add(document); // the document that created the object.

            if(parameters.size() == orginalSize) parameters.add(null); // Default
        }

        object = parameters.isEmpty() ? constructor.newInstance() : constructor.newInstance(parameters.toArray());
        insertDocument(manager, document, object);
        return object;
    }

    private static @NotNull List<Field> getFields(@NotNull final Class<?> child) {
        List<Field> arr = new ArrayList<>();
        Class<?> parent = child.getSuperclass();

        if (parent != null)
            arr.addAll(List.of(parent.getDeclaredFields()));
        arr.addAll(List.of(child.getDeclaredFields())); // The reason why we add the superclass fields first is to make sure that the superclass fields are at the beginning of the list.

        List<Field> filteredFields = new ArrayList<>();
        for(Field field : arr) {
            try {
                field.setAccessible(true);

                int modifiers = field.getModifiers();

                if( // If the field is static, final, or transient, we don't want to include it in the document.
                        Modifier.isStatic(modifiers)
                                || Modifier.isTransient(modifiers)
                                || field.getName().contains("$")
                ) continue;

                filteredFields.add(field);

                field.setAccessible(false);
            }catch (Exception ignored) { }
        }

        return ImmutableList.copyOf(filteredFields);
    }

    private static @Nullable Object invokeMethod(@NotNull final Method method, @Nullable final Object instance, final Object... parameters) {
        Object value = null;

        try { // Nested try-catch block to print any exception occurred while invoking the method.
            value = method.invoke(instance, parameters);
        }catch (Exception exception) { exception.printStackTrace(); }

        return value;
    }

    @NotNull Document asDocument();

    enum SerializationMethod {
        OBJECT(), METHOD(), IGNORE()
    }

    @Target({ElementType.FIELD})
    @Retention(RetentionPolicy.RUNTIME)
    @interface Serialize {
        SerializationMethod method() default SerializationMethod.OBJECT;
        Class<?> type() default Object.class;
        boolean save() default false;
    }

}
