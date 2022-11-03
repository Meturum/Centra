package com.meturum.centra.conversions;

import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.UUID;

public interface IDynamicTag extends Documentable {

    /**
     * Gets the unique UUID of this tag.
     *
     * @return the unique UUID of this tag.
     */
    @NotNull UUID getUniqueId();

    /**
     * Saves this tag to the database.
     *
     * @param async whether to save this tag asynchronously.
     * @param lambda the lambda to execute after saving.
     * @param upsert whether to upsert this tag.
     * @return true if the tag was saved, false otherwise.
     */
    boolean save(boolean async, @Nullable SaveLambda lambda, boolean upsert);

    /**
     * Saves this tag to the database.
     *
     * @param async whether to save this tag asynchronously.
     * @param upsert whether to upsert this tag.
     * @return true if the tag was saved, false otherwise.
     */
    boolean save(boolean async, boolean upsert);

    /**
     * Saves this tag to the database.
     *
     * @param async whether to save this tag asynchronously.
     * @param lambda the lambda to execute after saving.
     * @return true if the tag was saved, false otherwise.
     *
     * @apiNote This method is equivalent to {@code IDynamicTag#save(async, lambda, false)}. the variable {@code upsert} is set to false.
     */
    boolean save(boolean async, @Nullable SaveLambda lambda);

    /**
     * Saves this tag to the database.
     *
     * @param async whether to save this tag asynchronously.
     * @return true if the tag was saved, false otherwise.
     *
     * @apiNote This method is equivalent to {@code IDynamicTag#save(async, null, false)}. the variable {@code upsert} is set to false.
     */
    boolean save(boolean async);

    /**
     * Saves this tag to the database.
     *
     * @return true if the tag was saved, false otherwise.
     *
     * @apiNote This method is equivalent to {@code IDynamicTag#save(true, null, false)}. the variable {@code upsert} is set to {@code false} & {@code async} is set to {@code true}.
     */
    boolean save();

    /**
     * Saves this tag to the database synchronously. (thread-blocking)
     *
     * @param upsert whether to upsert this tag.
     * @return true if the tag was saved, false otherwise.
     * @apiNote This method executed synchronously and may cause thread-blocking. This method is not recommended to be executed in the main thread.
     */
    boolean saveSync(boolean upsert);

    /**
     * Saves this tag to the database synchronously. (thread-blocking)
     *
     * @return true if the tag was saved, false otherwise.
     * @apiNote This method executed synchronously and may cause thread-blocking. This method is not recommended to be executed in the main thread.
     */
    boolean saveSync();

    /**
     * Saves this tag to the database asynchronously.
     *
     * @param lambda the lambda to execute after saving.
     * @param upsert whether to upsert this tag.
     */
    void saveAsync(@Nullable SaveLambda lambda, boolean upsert);

    /**
     * Saves this tag to the database asynchronously.
     *
     * @param upsert whether to upsert this tag.
     */
    void saveAsync(boolean upsert);

    /**
     * Saves this tag to the database asynchronously.
     *
     * @param lambda the lambda to execute after saving.
     * @apiNote This method is equivalent to {@code IDynamicTag#saveAsync(lambda, false)}. the variable {@code upsert} is set to false.
     */
    void saveAsync(@Nullable SaveLambda lambda);

    /**
     * Saves this tag to the database asynchronously.
     *
     * @apiNote This method is equivalent to {@code IDynamicTag#saveAsync(null, false)}. the variable {@code upsert} is set to false.
     */
    void saveAsync();

    interface SaveLambda {
        void run(boolean saved);
    }

}
