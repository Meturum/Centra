package com.meturum.centra.mongo;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.ReplaceOptions;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.InsertOneResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public interface CollectionWrapper {

    /**
     * Searches for a document by the specified filter.
     *
     * @param filter the filter to search for.
     * @param lambda the lambda to execute on the document.
     */
    void findAsync(@Nullable Bson filter, @NotNull FindLambda lambda);

    /**
     * Searches for a document by the specified filter.
     *
     * @param lambda the lambda to execute on the document.
     */
    void findAsync(@NotNull FindLambda lambda);

    /**
     * Inserts a document into the collection.
     *
     * @param document the document to insert.
     * @param lambda the lambda to execute on the result.
     */
    void insertOneAsync(@NotNull Document document, @Nullable InsertOneLambda lambda);

    /**
     * Inserts a document into the collection.
     *
     * @param document the document to insert.
     */
    void insertOneAsync(@NotNull Document document);

    /**
     * Updates a document in the collection.
     *
     * @param filter the filter to search for.
     * @param updates the updates to apply.
     * @param options the options to apply.
     * @param lambda the lambda to execute on the result.
     */
    void updateOneAsync(@NotNull Bson filter, @NotNull Bson updates, @Nullable UpdateOptions options, @Nullable UpdateOneLambda lambda);

    /**
     * Updates a document in the collection.
     *
     * @param filter the filter to search for.
     * @param updates the updates to apply.
     * @param lambda the lambda to execute on the result.
     */
    void updateOneAsync(@NotNull Bson filter, @NotNull Bson updates, @Nullable UpdateOneLambda lambda);

    /**
     * Updates a document in the collection.
     *
     * @param filter the filter to search for.
     * @param updates the updates to apply.
     */
    void updateOneAsync(@NotNull Bson filter, @NotNull Bson updates, @Nullable UpdateOptions options);

    /**
     * Updates a document in the collection.
     *
     * @param filter the filter to search for.
     * @param updates the updates to apply.
     */
    void updateOneAsync(@NotNull Bson filter, @NotNull Bson updates);

    /**
     * Replaces a document in the collection.
     *
     * @param filter the filter to search for.
     * @param document the replacement document.
     * @param options the options to apply.
     * @param lambda the lambda to execute on the result.
     */
    void replaceOneAsync(@NotNull Bson filter, @NotNull Document document, @Nullable ReplaceOptions options, @Nullable ReplaceOneLambda lambda);

    /**
     * Replaces a document in the collection.
     *
     * @param filter the filter to search for.
     * @param document the replacement document.
     * @param lambda the lambda to execute on the result.
     */
    void replaceOneAsync(@NotNull Bson filter, @NotNull Document document, @Nullable ReplaceOneLambda lambda);

    /**
     * Replaces a document in the collection.
     *
     * @param filter the filter to search for.
     * @param document the replacement document.
     * @param options the options to apply.
     */
    void replaceOneAsync(@NotNull Bson filter, @NotNull Document document, @Nullable ReplaceOptions options);

    /**
     * Replaces a document in the collection.
     *
     * @param filter the filter to search for.
     * @param document the replacement document.
     */
    void replaceOneAsync(@NotNull Bson filter, @NotNull Document document);

    /**
     * Deletes a document in the collection.
     *
     * @param filter the filter to search for.
     * @param lambda the lambda to execute on the result.
     */
    void deleteOneAsync(@NotNull Bson filter, @Nullable DeleteOneLambda lambda);

    /**
     * Deletes a document in the collection.
     *
     * @param filter the filter to search for.
     */
    void deleteOneAsync(@NotNull Bson filter);

    @NotNull MongoCollection<Document> raw();

    interface FindLambda {
        void run(@Nullable FindIterable<Document> result, @Nullable Exception exception);
    }

    interface InsertOneLambda {
        void run(@Nullable InsertOneResult result, @Nullable Exception exception);
    }

    interface UpdateOneLambda {
        void run(@Nullable UpdateResult result, @Nullable Exception exception);
    }

    interface ReplaceOneLambda {
        void run(@Nullable UpdateResult result, @Nullable Exception exception);
    }

    interface DeleteOneLambda {
        void run(@Nullable DeleteResult result, @Nullable Exception exception);
    }

}
