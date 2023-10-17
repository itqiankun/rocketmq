
package org.apache.rocketmq.common.statistics;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;

public class FutureHolder<T> {
    private ConcurrentMap<T, BlockingQueue<Future>> futureMap = new ConcurrentHashMap<T, BlockingQueue<Future>>(8);

    public void addFuture(T t, Future future) {
        BlockingQueue<Future> list = futureMap.get(t);
        if (list == null) {
            list = new LinkedBlockingQueue<Future>();
            BlockingQueue<Future> old = futureMap.putIfAbsent(t, list);
            if (old != null) {
                list = old;
            }
        }
        list.add(future);
    }

    public void removeAllFuture(T t) {
        cancelAll(t, false);
        futureMap.remove(t);
    }

    private void cancelAll(T t, boolean mayInterruptIfRunning) {
        BlockingQueue<Future> list = futureMap.get(t);
        if (list != null) {
            for (Future future : list) {
                future.cancel(mayInterruptIfRunning);
            }
        }
    }
}
