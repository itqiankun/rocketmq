
package org.apache.rocketmq.client.common;

import java.lang.reflect.Field;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ThreadLocalIndexTest {
    @Test
    public void testIncrementAndGet() throws Exception {
        ThreadLocalIndex localIndex = new ThreadLocalIndex();
        int initialVal = localIndex.incrementAndGet();

        assertThat(localIndex.incrementAndGet()).isEqualTo(initialVal + 1);
    }

    @Test
    public void testIncrementAndGet2() throws Exception {
        ThreadLocalIndex localIndex = new ThreadLocalIndex();
        int initialVal = localIndex.incrementAndGet();
        assertThat(initialVal >= 0).isTrue();
    }

    @Test
    public void testIncrementAndGet3() throws Exception {
        ThreadLocalIndex localIndex = new ThreadLocalIndex();
        Field threadLocalIndexField = ThreadLocalIndex.class.getDeclaredField("threadLocalIndex");
        ThreadLocal<Integer> mockThreadLocal = new ThreadLocal<Integer>();
        mockThreadLocal.set(Integer.MAX_VALUE);

        threadLocalIndexField.setAccessible(true);
        threadLocalIndexField.set(localIndex, mockThreadLocal);

        int initialVal = localIndex.incrementAndGet();
        assertThat(initialVal >= 0).isTrue();
    }

}