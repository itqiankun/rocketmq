

package org.apache.rocketmq.common.subscription;

public interface RetryPolicy {
    /**
     * Compute message's next delay duration by specify reconsumeTimes
     *
     * @param reconsumeTimes Message reconsumeTimes
     * @return Message's nextDelayDuration in milliseconds
     */
    long nextDelayDuration(int reconsumeTimes);
}
