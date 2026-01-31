package de.mecrytv.nexusapi.repository;

import de.mecrytv.databaseapi.DatabaseAPI;
import de.mecrytv.databaseapi.model.ICacheModel;

import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public class NexusRepository<T extends ICacheModel> {

    private final String nodeName;

    public NexusRepository(String nodeName) {
        this.nodeName = nodeName;
    }

    public CompletableFuture<Optional<T>> getOnce(String identifier) {
        return DatabaseAPI.<T>get(nodeName, identifier).thenApply(Optional::ofNullable);
    }

    public CompletableFuture<Collection<T>> getAll() {
        return DatabaseAPI.<T>getAll(nodeName).thenApply(list -> (Collection<T>) list);
    }

    public CompletableFuture<Collection<T>> getList(String jsonKey, String value) {
        return DatabaseAPI.<T>getList(nodeName, jsonKey, value).thenApply(list -> (Collection<T>) list);
    }

    public void save(T model) {
        DatabaseAPI.set(nodeName, model);
    }

    public void delete(String identifier) {
        DatabaseAPI.delete(nodeName, identifier);
    }

    public void update(T model) {
        save(model);
    }
}