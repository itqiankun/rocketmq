
package org.apache.rocketmq.proxy.common;

import com.google.common.cache.CacheLoader;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListenableFutureTask;
import java.util.concurrent.ThreadPoolExecutor;
import javax.annotation.Nonnull;

public abstract class AbstractCacheLoader<K, V> extends CacheLoader<K, V> {
    private final ThreadPoolExecutor cacheRefreshExecutor;

    public AbstractCacheLoader(ThreadPoolExecutor cacheRefreshExecutor) {
        this.cacheRefreshExecutor = cacheRefreshExecutor;
    }

    @Override
    public ListenableFuture<V> reload(@Nonnull K key, @Nonnull V oldValue) throws Exception {
        ListenableFutureTask<V> task = ListenableFutureTask.create(() -> {
            try {
                return getDirectly(key);
            } catch (Exception e) {
                onErr(key, e);
                return oldValue;
            }
        });
        cacheRefreshExecutor.execute(task);
        return task;
    }

    @Override
    public V load(@Nonnull K key) throws Exception {
        return getDirectly(key);
    }

    protected abstract V getDirectly(K key) throws Exception;

    protected abstract void onErr(K key, Exception e);
}