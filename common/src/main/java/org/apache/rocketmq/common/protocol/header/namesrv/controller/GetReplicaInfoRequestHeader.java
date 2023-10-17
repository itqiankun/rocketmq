
package org.apache.rocketmq.common.protocol.header.namesrv.controller;

import org.apache.rocketmq.remoting.CommandCustomHeader;
import org.apache.rocketmq.remoting.exception.RemotingCommandException;

public class GetReplicaInfoRequestHeader implements CommandCustomHeader {
    private String brokerName;
    private String brokerAddress;

    public GetReplicaInfoRequestHeader() {
    }

    public GetReplicaInfoRequestHeader(String brokerName) {
        this.brokerName = brokerName;
    }

    public GetReplicaInfoRequestHeader(String brokerName, String brokerAddress) {
        this.brokerName = brokerName;
        this.brokerAddress = brokerAddress;
    }

    public String getBrokerName() {
        return brokerName;
    }

    public void setBrokerName(String brokerName) {
        this.brokerName = brokerName;
    }

    public String getBrokerAddress() {
        return brokerAddress;
    }

    public void setBrokerAddress(String brokerAddress) {
        this.brokerAddress = brokerAddress;
    }

    @Override
    public String toString() {
        return "GetReplicaInfoRequestHeader{" +
            "brokerName='" + brokerName + '\'' +
            ", brokerAddress='" + brokerAddress + '\'' +
            '}';
    }

    @Override
    public void checkFields() throws RemotingCommandException {
    }
}
