
package org.apache.rocketmq.client.impl.consumer;

import java.util.concurrent.atomic.AtomicInteger;
import org.apache.rocketmq.common.protocol.body.PopProcessQueueInfo;

/**
 * Queue consumption snapshot
 */
public class PopProcessQueue {

    private final static long PULL_MAX_IDLE_TIME = Long.parseLong(System.getProperty("rocketmq.client.pull.pullMaxIdleTime", "120000"));

    private long lastPopTimestamp;
    private AtomicInteger waitAckCounter = new AtomicInteger(0);
    private volatile boolean dropped = false;

    public long getLastPopTimestamp() {
        return lastPopTimestamp;
    }

    public void setLastPopTimestamp(long lastPopTimestamp) {
        this.lastPopTimestamp = lastPopTimestamp;
    }

    public void incFoundMsg(int count) {
        this.waitAckCounter.getAndAdd(count);
    }

    /**
     * @return the value before decrement.
     */
    public int ack() {
        return this.waitAckCounter.getAndDecrement();
    }

    public void decFoundMsg(int count) {
        this.waitAckCounter.addAndGet(count);
    }

    public int getWaiAckMsgCount() {
        return this.waitAckCounter.get();
    }

    public boolean isDropped() {
        return dropped;
    }

    public void setDropped(boolean dropped) {
        this.dropped = dropped;
    }

    public void fillPopProcessQueueInfo(final PopProcessQueueInfo info) {
        info.setWaitAckCount(getWaiAckMsgCount());
        info.setDroped(isDropped());
        info.setLastPopTimestamp(getLastPopTimestamp());
    }

    public boolean isPullExpired() {
        return (System.currentTimeMillis() - this.lastPopTimestamp) > PULL_MAX_IDLE_TIME;
    }

    @Override
    public String toString() {
        return "PopProcessQueue[waitAckCounter:" + this.waitAckCounter.get()
                + ", lastPopTimestamp:" + getLastPopTimestamp()
                + ", drop:" + dropped +  "]";
    }
}
