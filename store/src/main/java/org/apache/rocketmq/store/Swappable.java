
package org.apache.rocketmq.store;

/**
 * Clean up page-table on super large disk
 */
public interface Swappable {
    void swapMap(int reserveNum, long forceSwapIntervalMs, long normalSwapIntervalMs);
    void cleanSwappedMap(long forceCleanSwapIntervalMs);
}
