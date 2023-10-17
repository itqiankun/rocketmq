

package org.apache.rocketmq.proxy.common.utils;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

public class FutureUtils {

    public static <T> CompletableFuture<T> appendNextFuture(CompletableFuture<T> future,
        CompletableFuture<T> nextFuture, ExecutorService executor) {
        future.whenCompleteAsync((t, throwable) -> {
            if (throwable != null) {
                nextFuture.completeExceptionally(throwable);
            } else {
                nextFuture.complete(t);
            }
        }, executor);
        return nextFuture;
    }

    public static <T> CompletableFuture<T> addExecutor(CompletableFuture<T> future, ExecutorService executor) {
        return appendNextFuture(future, new CompletableFuture<>(), executor);
    }
}
