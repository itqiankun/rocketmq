

/**
 * $Id: RegisterBrokerRequestHeader.java 1835 2013-05-16 02:00:50Z vintagewang@apache.org $
 */
package org.apache.rocketmq.common.protocol.header.namesrv;

import org.apache.rocketmq.remoting.CommandCustomHeader;
import org.apache.rocketmq.remoting.annotation.CFNotNull;
import org.apache.rocketmq.remoting.annotation.CFNullable;
import org.apache.rocketmq.remoting.exception.RemotingCommandException;

public class RegisterBrokerRequestHeader implements CommandCustomHeader {
    @CFNotNull
    private String brokerName;
    @CFNotNull
    private String brokerAddr;
    @CFNotNull
    private String clusterName;
    @CFNotNull
    private String haServerAddr;
    @CFNotNull
    private Long brokerId;
    @CFNullable
    private Long heartbeatTimeoutMillis;
    @CFNullable
    private Boolean enableActingMaster;

    private boolean compressed;

    private Integer bodyCrc32 = 0;

    @Override
    public void checkFields() throws RemotingCommandException {
    }

    public String getBrokerName() {
        return brokerName;
    }

    public void setBrokerName(String brokerName) {
        this.brokerName = brokerName;
    }

    public String getBrokerAddr() {
        return brokerAddr;
    }

    public void setBrokerAddr(String brokerAddr) {
        this.brokerAddr = brokerAddr;
    }

    public String getClusterName() {
        return clusterName;
    }

    public void setClusterName(String clusterName) {
        this.clusterName = clusterName;
    }

    public String getHaServerAddr() {
        return haServerAddr;
    }

    public void setHaServerAddr(String haServerAddr) {
        this.haServerAddr = haServerAddr;
    }

    public Long getBrokerId() {
        return brokerId;
    }

    public void setBrokerId(Long brokerId) {
        this.brokerId = brokerId;
    }

    public Long getHeartbeatTimeoutMillis() {
        return heartbeatTimeoutMillis;
    }

    public void setHeartbeatTimeoutMillis(Long heartbeatTimeoutMillis) {
        this.heartbeatTimeoutMillis = heartbeatTimeoutMillis;
    }

    public boolean isCompressed() {
        return compressed;
    }

    public void setCompressed(boolean compressed) {
        this.compressed = compressed;
    }

    public Integer getBodyCrc32() {
        return bodyCrc32;
    }

    public void setBodyCrc32(Integer bodyCrc32) {
        this.bodyCrc32 = bodyCrc32;
    }

    public Boolean getEnableActingMaster() {
        return enableActingMaster;
    }

    public void setEnableActingMaster(Boolean enableActingMaster) {
        this.enableActingMaster = enableActingMaster;
    }
}
