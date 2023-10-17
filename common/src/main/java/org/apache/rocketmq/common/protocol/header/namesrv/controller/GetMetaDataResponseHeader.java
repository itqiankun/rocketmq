
package org.apache.rocketmq.common.protocol.header.namesrv.controller;

import org.apache.rocketmq.remoting.CommandCustomHeader;
import org.apache.rocketmq.remoting.exception.RemotingCommandException;

public class GetMetaDataResponseHeader implements CommandCustomHeader {
    private String group;
    private String controllerLeaderId;
    private String controllerLeaderAddress;
    private boolean isLeader;
    private String peers;

    public GetMetaDataResponseHeader() {
    }

    public GetMetaDataResponseHeader(String group, String controllerLeaderId, String controllerLeaderAddress, boolean isLeader, String peers) {
        this.group = group;
        this.controllerLeaderId = controllerLeaderId;
        this.controllerLeaderAddress = controllerLeaderAddress;
        this.isLeader = isLeader;
        this.peers = peers;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getControllerLeaderId() {
        return controllerLeaderId;
    }

    public void setControllerLeaderId(String controllerLeaderId) {
        this.controllerLeaderId = controllerLeaderId;
    }

    public String getControllerLeaderAddress() {
        return controllerLeaderAddress;
    }

    public void setControllerLeaderAddress(String controllerLeaderAddress) {
        this.controllerLeaderAddress = controllerLeaderAddress;
    }

    public boolean isLeader() {
        return isLeader;
    }

    public void setIsLeader(boolean leader) {
        isLeader = leader;
    }

    public String getPeers() {
        return peers;
    }

    public void setPeers(String peers) {
        this.peers = peers;
    }

    @Override
    public String toString() {
        return "GetMetaDataResponseHeader{" +
            "group='" + group + '\'' +
            ", controllerLeaderId='" + controllerLeaderId + '\'' +
            ", controllerLeaderAddress='" + controllerLeaderAddress + '\'' +
            ", isLeader=" + isLeader +
            ", peers='" + peers + '\'' +
            '}';
    }

    @Override
    public void checkFields() throws RemotingCommandException {
    }
}
