
package org.apache.rocketmq.common.utils;

import java.util.concurrent.ConcurrentMap;
import java.util.function.Function;

public abstract class ConcurrentHashMapUtils {

    private static final boolean IS_JDK8;

    static {
        // Java 8
        // Java 9+: 9,11,17
        IS_JDK8 = System.getProperty("java.version").startsWith("1.8.");
    }

    /**
     * A temporary workaround for Java 8 specific performance issue JDK-8161372 .<br> Use implementation of
     * ConcurrentMap.computeIfAbsent instead.
     *
     * @see <a href="https://bugs.openjdk.java.net/browse/JDK-8161372">https://bugs.openjdk.java.net/browse/JDK-8161372</a>
     */
    public static <K, V> V computeIfAbsent(ConcurrentMap<K, V> map, K key, Function<? super K, ? extends V> func) {
        if (IS_JDK8) {
            V v = map.get(key);
            if (null == v) {
                v = map.computeIfAbsent(key, func);
            }
            return v;
        } else {
            return map.computeIfAbsent(key, func);
        }
    }
}
