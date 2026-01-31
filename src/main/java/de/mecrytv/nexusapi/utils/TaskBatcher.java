package de.mecrytv.nexusapi.utils;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class TaskBatcher {

    private static InterfaceUtils.PlatformScheduler scheduler;

    public static void setScheduler(InterfaceUtils.PlatformScheduler platformScheduler) {
        scheduler = platformScheduler;
    }


    public static <T> void acceptAsync(CompletableFuture<T> future, Consumer<T> callback) {
        future.thenAccept(result -> {
            if (scheduler != null) {
                scheduler.runTask(() -> callback.accept(result));
            } else {
                callback.accept(result);
            }
        });
    }
}