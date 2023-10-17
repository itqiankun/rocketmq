
package org.apache.rocketmq.common.protocol.header;

import org.apache.rocketmq.remoting.CommandCustomHeader;
import org.apache.rocketmq.remoting.exception.RemotingCommandException;

public class NotifyBrokerRoleChangedRequestHeader implements CommandCustomHeader {
    private String masterAddress;
    private int masterEpoch;
    private int syncStateSetEpoch;
    // The id of this broker.
    private long brokerId;

    public NotifyBrokerRoleChangedRequestHeader() {
    }

    public NotifyBrokerRoleChangedRequestHeader(String masterAddress, int masterEpoch, int syncStateSetEpoch, long brokerId) {
        this.masterAddress = masterAddress;
        this.masterEpoch = masterEpoch;
        this.syncStateSetEpoch = syncStateSetEpoch;
        this.brokerId = brokerId;
    }

    public String getMasterAddress() {
        return masterAddress;
    }

    public void setMasterAddress(String masterAddress) {
        this.masterAddress = masterAddress;
    }

    public int getMasterEpoch() {
        return masterEpoch;
    }

    public void setMasterEpoch(int masterEpoch) {
        this.masterEpoch = masterEpoch;
    }

    public int getSyncStateSetEpoch() {
        return syncStateSetEpoch;
    }

    public void setSyncStateSetEpoch(int syncStateSetEpoch) {
        this.syncStateSetEpoch = syncStateSetEpoch;
    }

    public long getBrokerId() {
        return brokerId;
    }

    public void setBrokerId(long brokerId) {
        this.brokerId = brokerId;
    }

    @Override
    public String toString() {
        return "NotifyBrokerRoleChangedRequestHeader{" +
            "masterAddress='" + masterAddress + '\'' +
            ", masterEpoch=" + masterEpoch +
            ", syncStateSetEpoch=" + syncStateSetEpoch +
            ", brokerId=" + brokerId +
            '}';
    }

    @Override
    public void checkFields() throws RemotingCommandException {

    }
}
