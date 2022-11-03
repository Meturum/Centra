package com.meturum.centra.mongo;

import org.jetbrains.annotations.NotNull;
import com.meturum.centra.system.System;

public interface IMongo extends System {

    /**
     * Searches for a collection by the specified name. If the collection does not exist, it will be created.
     *
     * @param name the name of the collection.
     * @param type the type of the collection.
     * @return the collection.
     *
     * @apiNote The object returned is not the actual collection, but a wrapper around it. to access the actual collection, use {@link CollectionWrapper#raw()}.
     */
    @NotNull CollectionWrapper getCollection(@NotNull String name, @NotNull MongoClientTypes type);

    enum MongoClientTypes {
        GLOBAL_DATABASE, SERVER_DATABASE
    }

}
