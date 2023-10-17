
package org.apache.rocketmq.common.protocol.header.namesrv.controller;

import org.apache.rocketmq.remoting.CommandCustomHeader;
import org.apache.rocketmq.remoting.annotation.CFNullable;
import org.apache.rocketmq.remoting.exception.RemotingCommandException;

public class RegisterBrokerToControllerRequestHeader implements CommandCustomHeader {
    private String clusterName;
    private String brokerName;
    private String brokerAddress;
    @CFNullable
    private Integer epoch;
    @CFNullable
    private Long maxOffset;
    @CFNullable
    private Long heartbeatTimeoutMillis;


    public RegisterBrokerToControllerRequestHeader() {
    }

    public RegisterBrokerToControllerRequestHeader(String clusterName, String brokerName, String brokerAddress) {
        this.clusterName = clusterName;
        this.brokerName = brokerName;
        this.brokerAddress = brokerAddress;
    }

    public RegisterBrokerToControllerRequestHeader(String clusterName, String brokerName, String brokerAddress, int epoch, long maxOffset) {
        this.clusterName = clusterName;
        this.brokerName = brokerName;
        this.brokerAddress = brokerAddress;
        this.epoch = epoch;
        this.maxOffset = maxOffset;
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

    public Long getHeartbeatTimeoutMillis() {
        return heartbeatTimeoutMillis;
    }

    public void setHeartbeatTimeoutMillis(Long heartbeatTimeoutMillis) {
        this.heartbeatTimeoutMillis = heartbeatTimeoutMillis;
    }

    @Override
    public String toString() {
        return "RegisterBrokerToControllerRequestHeader{" +
                "clusterName='" + clusterName + '\'' +
                ", brokerName='" + brokerName + '\'' +
                ", brokerAddress='" + brokerAddress + '\'' +
                ", epoch=" + epoch +
                ", maxOffset=" + maxOffset +
                ", heartbeatTimeoutMillis=" + heartbeatTimeoutMillis +
                '}';
    }

    public Integer getEpoch() {
        return epoch;
    }

    public void setEpoch(Integer epoch) {
        this.epoch = epoch;
    }

    public Long getMaxOffset() {
        return maxOffset;
    }

    public void setMaxOffset(Long maxOffset) {
        this.maxOffset = maxOffset;
    }

    @Override
    public void checkFields() throws RemotingCommandException {
    }
}
