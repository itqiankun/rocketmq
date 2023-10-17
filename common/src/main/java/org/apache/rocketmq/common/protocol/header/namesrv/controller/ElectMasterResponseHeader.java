
package org.apache.rocketmq.common.protocol.header.namesrv.controller;

import org.apache.rocketmq.common.protocol.body.BrokerMemberGroup;
import org.apache.rocketmq.remoting.CommandCustomHeader;
import org.apache.rocketmq.remoting.exception.RemotingCommandException;

public class ElectMasterResponseHeader implements CommandCustomHeader {
    private String newMasterAddress;
    private int masterEpoch;
    private int syncStateSetEpoch;
    private BrokerMemberGroup brokerMemberGroup;

    public ElectMasterResponseHeader() {
    }

    public String getNewMasterAddress() {
        return newMasterAddress;
    }

    public void setNewMasterAddress(String newMasterAddress) {
        this.newMasterAddress = newMasterAddress;
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

    public BrokerMemberGroup getBrokerMemberGroup() {
        return brokerMemberGroup;
    }

    public void setBrokerMemberGroup(BrokerMemberGroup brokerMemberGroup) {
        this.brokerMemberGroup = brokerMemberGroup;
    }

    @Override
    public String toString() {
        return "ElectMasterResponseHeader{" +
            "newMasterAddress='" + newMasterAddress + '\'' +
            ", masterEpoch=" + masterEpoch +
            ", syncStateSetEpoch=" + syncStateSetEpoch +
            ", brokerMember=" + brokerMemberGroup +
            '}';
    }

    @Override
    public void checkFields() throws RemotingCommandException {
    }
}
