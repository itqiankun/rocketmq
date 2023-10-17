

package org.apache.rocketmq.store.ha;

import java.util.concurrent.atomic.AtomicLong;

import org.apache.rocketmq.common.ServiceThread;
import org.apache.rocketmq.store.config.MessageStoreConfig;

public class FlowMonitor extends ServiceThread {
    private final AtomicLong transferredByte = new AtomicLong(0L);
    private volatile long transferredByteInSecond;
    protected MessageStoreConfig messageStoreConfig;

    public FlowMonitor(MessageStoreConfig messageStoreConfig) {
        this.messageStoreConfig = messageStoreConfig;
    }

    @Override
    public void run() {
        while (!this.isStopped()) {
            this.waitForRunning(1 * 1000);
            this.calculateSpeed();
        }
    }

    public void calculateSpeed() {
        this.transferredByteInSecond = this.transferredByte.get();
        this.transferredByte.set(0);
    }

    public int canTransferMaxByteNum() {
        //Flow control is not started at present
        if (this.isFlowControlEnable()) {
            long res = Math.max(this.maxTransferByteInSecond() - this.transferredByte.get(), 0);
            return res > Integer.MAX_VALUE ? Integer.MAX_VALUE : (int) res;
        }
        return Integer.MAX_VALUE;
    }

    public void addByteCountTransferred(long count) {
        this.transferredByte.addAndGet(count);
    }

    public long getTransferredByteInSecond() {
        return this.transferredByteInSecond;
    }

    @Override
    public String getServiceName() {
        return FlowMonitor.class.getSimpleName();
    }

    protected boolean isFlowControlEnable() {
        return this.messageStoreConfig.isHaFlowControlEnable();
    }

    public long maxTransferByteInSecond() {
        return this.messageStoreConfig.getMaxHaTransferByteInSecond();
    }
}