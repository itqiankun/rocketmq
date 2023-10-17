
package org.apache.rocketmq.common.protocol.header.namesrv.controller;

import org.apache.rocketmq.remoting.CommandCustomHeader;
import org.apache.rocketmq.remoting.annotation.CFNotNull;
import org.apache.rocketmq.remoting.exception.RemotingCommandException;

public class ElectMasterRequestHeader implements CommandCustomHeader {

    @CFNotNull
    private String clusterName;

    @CFNotNull
    private String brokerName;

    @CFNotNull
    private String brokerAddress;

    public ElectMasterRequestHeader() {
    }

    public ElectMasterRequestHeader(String brokerName) {
        this.brokerName = brokerName;
    }

    public ElectMasterRequestHeader(String clusterName, String brokerName, String brokerAddress) {
        this.clusterName = clusterName;
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

    public String getClusterName() {
        return clusterName;
    }

    public void setClusterName(String clusterName) {
        this.clusterName = clusterName;
    }

    @Override
    public String toString() {
        return "ElectMasterRequestHeader{" +
            "clusterName='" + clusterName + '\'' +
            "brokerName='" + brokerName + '\'' +
            "brokerAddress='" + brokerAddress + '\'' +
            '}';
    }

    @Override
    public void checkFields() throws RemotingCommandException {
    }
}
