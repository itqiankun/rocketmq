
package org.apache.rocketmq.controller;


import io.netty.channel.Channel;


public class BrokerLiveInfo {
    private final String brokerName;

    private final String brokerAddr;
    private final long heartbeatTimeoutMillis;
    private final Channel channel;
    private long brokerId;
    private long lastUpdateTimestamp;
    private int epoch;
    private long maxOffset;
    private long confirmOffset;

    public BrokerLiveInfo(String brokerName, String brokerAddr,long brokerId, long lastUpdateTimestamp, long heartbeatTimeoutMillis,
                          Channel channel, int epoch, long maxOffset) {
        this.brokerName = brokerName;
        this.brokerAddr = brokerAddr;
        this.brokerId = brokerId;
        this.lastUpdateTimestamp = lastUpdateTimestamp;
        this.heartbeatTimeoutMillis = heartbeatTimeoutMillis;
        this.channel = channel;
        this.epoch = epoch;
        this.maxOffset = maxOffset;
    }
    public BrokerLiveInfo(String brokerName, String brokerAddr,long brokerId, long lastUpdateTimestamp, long heartbeatTimeoutMillis,
                          Channel channel, int epoch, long maxOffset, long confirmOffset) {
        this.brokerName = brokerName;
        this.brokerAddr = brokerAddr;
        this.brokerId = brokerId;
        this.lastUpdateTimestamp = lastUpdateTimestamp;
        this.heartbeatTimeoutMillis = heartbeatTimeoutMillis;
        this.channel = channel;
        this.epoch = epoch;
        this.maxOffset = maxOffset;
        this.confirmOffset = confirmOffset;
    }

    @Override
    public String toString() {
        return "BrokerLiveInfo{" +
                "brokerName='" + brokerName + '\'' +
                ", brokerAddr='" + brokerAddr + '\'' +
                ", heartbeatTimeoutMillis=" + heartbeatTimeoutMillis +
                ", channel=" + channel +
                ", brokerId=" + brokerId +
                ", lastUpdateTimestamp=" + lastUpdateTimestamp +
                ", epoch=" + epoch +
                ", maxOffset=" + maxOffset +
                ", confirmOffset=" + confirmOffset +
                '}';
    }

    public String getBrokerName() {
        return brokerName;
    }

    public long getHeartbeatTimeoutMillis() {
        return heartbeatTimeoutMillis;
    }

    public Channel getChannel() {
        return channel;
    }

    public long getBrokerId() {
        return brokerId;
    }

    public void setBrokerId(long brokerId) {
        this.brokerId = brokerId;
    }

    public long getLastUpdateTimestamp() {
        return lastUpdateTimestamp;
    }

    public void setLastUpdateTimestamp(long lastUpdateTimestamp) {
        this.lastUpdateTimestamp = lastUpdateTimestamp;
    }

    public int getEpoch() {
        return epoch;
    }

    public void setEpoch(int epoch) {
        this.epoch = epoch;
    }

    public long getMaxOffset() {
        return maxOffset;
    }

    public void setMaxOffset(long maxOffset) {
        this.maxOffset = maxOffset;
    }

    public String getBrokerAddr() {
        return brokerAddr;
    }

    public void setConfirmOffset(long confirmOffset) {
        this.confirmOffset = confirmOffset;
    }

    public long getConfirmOffset() {
        return confirmOffset;
    }
}
