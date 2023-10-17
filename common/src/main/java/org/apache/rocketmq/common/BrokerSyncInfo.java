

package org.apache.rocketmq.common;

import org.apache.rocketmq.remoting.protocol.RemotingSerializable;

public class BrokerSyncInfo extends RemotingSerializable {
    /**
     * For slave online sync, retrieve HA address before register
     */
    private String masterHaAddress;

    private long masterFlushOffset;

    private String masterAddress;

    public BrokerSyncInfo(String masterHaAddress, long masterFlushOffset, String masterAddress) {
        this.masterHaAddress = masterHaAddress;
        this.masterFlushOffset = masterFlushOffset;
        this.masterAddress = masterAddress;
    }

    public String getMasterHaAddress() {
        return masterHaAddress;
    }

    public void setMasterHaAddress(String masterHaAddress) {
        this.masterHaAddress = masterHaAddress;
    }

    public long getMasterFlushOffset() {
        return masterFlushOffset;
    }

    public void setMasterFlushOffset(long masterFlushOffset) {
        this.masterFlushOffset = masterFlushOffset;
    }

    public String getMasterAddress() {
        return masterAddress;
    }

    public void setMasterAddress(String masterAddress) {
        this.masterAddress = masterAddress;
    }

    @Override
    public String toString() {
        return "BrokerSyncInfo{" +
            "masterHaAddress='" + masterHaAddress + '\'' +
            ", masterFlushOffset=" + masterFlushOffset +
            ", masterAddress=" + masterAddress +
            '}';
    }
}
