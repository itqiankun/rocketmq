
package org.apache.rocketmq.common.protocol.header.namesrv.controller;

import org.apache.rocketmq.remoting.CommandCustomHeader;
import org.apache.rocketmq.remoting.exception.RemotingCommandException;

public class AlterSyncStateSetRequestHeader implements CommandCustomHeader {
    private String brokerName;
    private String masterAddress;
    private int masterEpoch;

    public AlterSyncStateSetRequestHeader() {
    }

    public AlterSyncStateSetRequestHeader(String brokerName, String masterAddress, int masterEpoch) {
        this.brokerName = brokerName;
        this.masterAddress = masterAddress;
        this.masterEpoch = masterEpoch;
    }

    public String getBrokerName() {
        return brokerName;
    }

    public void setBrokerName(String brokerName) {
        this.brokerName = brokerName;
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

    @Override
    public String toString() {
        return "AlterSyncStateSetRequestHeader{" +
            "brokerName='" + brokerName + '\'' +
            ", masterAddress='" + masterAddress + '\'' +
            ", masterEpoch=" + masterEpoch +
            '}';
    }

    @Override
    public void checkFields() throws RemotingCommandException {
    }
}
