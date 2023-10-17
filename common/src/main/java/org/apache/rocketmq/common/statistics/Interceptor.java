
package org.apache.rocketmq.common.statistics;

/**
 * interceptor
 */
public interface Interceptor {
    /**
     * increase multiple values
     *
     * @param deltas
     */
    void inc(long... deltas);

    void reset();
}
