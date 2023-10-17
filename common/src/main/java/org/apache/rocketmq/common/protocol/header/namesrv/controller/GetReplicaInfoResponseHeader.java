
package org.apache.rocketmq.common.protocol.header.namesrv.controller;

import org.apache.rocketmq.remoting.CommandCustomHeader;
import org.apache.rocketmq.remoting.exception.RemotingCommandException;

public class GetReplicaInfoResponseHeader implements CommandCustomHeader {
    private String masterAddress;
    private int masterEpoch;
    // BrokerId for current replicas.
    private long brokerId = -1L;

    public GetReplicaInfoResponseHeader() {
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

    public long getBrokerId() {
        return brokerId;
    }

    public void setBrokerId(long brokerId) {
        this.brokerId = brokerId;
    }

    @Override
    public String toString() {
        return "GetReplicaInfoResponseHeader{" +
            "masterAddress='" + masterAddress + '\'' +
            ", masterEpoch=" + masterEpoch +
            ", brokerId=" + brokerId +
            '}';
    }

    @Override
    public void checkFields() throws RemotingCommandException {
    }
}
