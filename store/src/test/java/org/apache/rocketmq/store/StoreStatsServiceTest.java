
package org.apache.rocketmq.store;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.LongAdder;

import org.junit.Test;

public class StoreStatsServiceTest {

    @Test
    public void getSinglePutMessageTopicSizeTotal() throws Exception {
        final StoreStatsService storeStatsService = new StoreStatsService();
        int num = Runtime.getRuntime().availableProcessors() * 2;
        for (int j = 0; j < 100; j++) {
            final AtomicReference<LongAdder> reference = new AtomicReference<>(null);
            final CountDownLatch latch = new CountDownLatch(num);
            final CyclicBarrier barrier = new CyclicBarrier(num);
            for (int i = 0; i < num; i++) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            barrier.await();
                            LongAdder longAdder = storeStatsService.getSinglePutMessageTopicSizeTotal("test");
                            if (reference.compareAndSet(null, longAdder)) {
                            } else if (reference.get() != longAdder) {
                                throw new RuntimeException("Reference should be same!");
                            }
                        } catch (InterruptedException | BrokenBarrierException e) {
                            e.printStackTrace();
                        } finally {
                            latch.countDown();
                        }
                    }
                }).start();
            }
            latch.await();
        }
    }

    @Test
    public void getSinglePutMessageTopicTimesTotal() throws Exception {
        final StoreStatsService storeStatsService = new StoreStatsService();
        int num = Runtime.getRuntime().availableProcessors() * 2;
        for (int j = 0; j < 100; j++) {
            final AtomicReference<LongAdder> reference = new AtomicReference<>(null);
            final CountDownLatch latch = new CountDownLatch(num);
            final CyclicBarrier barrier = new CyclicBarrier(num);
            for (int i = 0; i < num; i++) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            barrier.await();
                            LongAdder longAdder = storeStatsService.getSinglePutMessageTopicTimesTotal("test");
                            if (reference.compareAndSet(null, longAdder)) {
                            } else if (reference.get() != longAdder) {
                                throw new RuntimeException("Reference should be same!");
                            }
                        } catch (InterruptedException | BrokenBarrierException e) {
                            e.printStackTrace();
                        } finally {
                            latch.countDown();
                        }
                    }
                }).start();
            }
            latch.await();
        }
    }

    @Test
    public void findPutMessageEntireTimePXTest() throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        final StoreStatsService storeStatsService = new StoreStatsService();
        for (int i = 1; i <= 1000; i++) {
            for (int j = 0; j < i; j++) {
                storeStatsService.incPutMessageEntireTime(i);
            }
        }
        Method method = StoreStatsService.class.getDeclaredMethod("resetPutMessageTimeBuckets");
        method.setAccessible(true);
        method.invoke(storeStatsService);
        System.out.println(storeStatsService.findPutMessageEntireTimePX(0.99));
        System.out.println(storeStatsService.findPutMessageEntireTimePX(0.999));
    }

}