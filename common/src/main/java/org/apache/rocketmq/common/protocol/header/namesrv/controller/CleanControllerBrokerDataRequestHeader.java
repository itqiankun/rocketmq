

package org.apache.rocketmq.common.protocol.header.namesrv.controller;

import org.apache.rocketmq.remoting.CommandCustomHeader;
import org.apache.rocketmq.remoting.annotation.CFNotNull;
import org.apache.rocketmq.remoting.annotation.CFNullable;
import org.apache.rocketmq.remoting.exception.RemotingCommandException;

public class CleanControllerBrokerDataRequestHeader implements CommandCustomHeader {

    @CFNullable
    private String clusterName;

    @CFNotNull
    private String brokerName;

    @CFNullable
    private String brokerAddress;

    private boolean isCleanLivingBroker = false;

    public CleanControllerBrokerDataRequestHeader() {
    }

    public CleanControllerBrokerDataRequestHeader(String clusterName, String brokerName, String brokerAddress,
        boolean isCleanLivingBroker) {
        this.clusterName = clusterName;
        this.brokerName = brokerName;
        this.brokerAddress = brokerAddress;
        this.isCleanLivingBroker = isCleanLivingBroker;
    }

    public CleanControllerBrokerDataRequestHeader(String clusterName, String brokerName, String brokerAddress) {
        this(clusterName, brokerName, brokerAddress, false);
    }

    @Override
    public void checkFields() throws RemotingCommandException {

    }

    public String getClusterName() {
        return clusterName;
    }

    public void setClusterName(String clusterName) {
        this.clusterName = clusterName;
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

    public boolean isCleanLivingBroker() {
        return isCleanLivingBroker;
    }

    public void setCleanLivingBroker(boolean cleanLivingBroker) {
        isCleanLivingBroker = cleanLivingBroker;
    }

    @Override
    public String toString() {
        return "CleanControllerBrokerDataRequestHeader{" +
            "clusterName='" + clusterName + '\'' +
            "brokerName='" + brokerName + '\'' +
            "brokerAddress='" + brokerAddress + '\'' +
            "isCleanLivingBroker='" + isCleanLivingBroker + '\'' +
            '}';
    }
}
