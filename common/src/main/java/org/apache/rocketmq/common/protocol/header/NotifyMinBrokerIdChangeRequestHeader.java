

package org.apache.rocketmq.common.protocol.header;

import org.apache.rocketmq.remoting.CommandCustomHeader;
import org.apache.rocketmq.remoting.annotation.CFNullable;
import org.apache.rocketmq.remoting.exception.RemotingCommandException;

public class NotifyMinBrokerIdChangeRequestHeader implements CommandCustomHeader {
    @CFNullable
    private Long minBrokerId;

    @CFNullable
    private String brokerName;

    @CFNullable
    private String minBrokerAddr;

    @CFNullable
    private String offlineBrokerAddr;

    @CFNullable
    private String haBrokerAddr;

    @Override
    public void checkFields() throws RemotingCommandException {

    }

    public Long getMinBrokerId() {
        return minBrokerId;
    }

    public void setMinBrokerId(Long minBrokerId) {
        this.minBrokerId = minBrokerId;
    }

    public String getBrokerName() {
        return brokerName;
    }

    public void setBrokerName(String brokerName) {
        this.brokerName = brokerName;
    }

    public String getMinBrokerAddr() {
        return minBrokerAddr;
    }

    public void setMinBrokerAddr(String minBrokerAddr) {
        this.minBrokerAddr = minBrokerAddr;
    }

    public String getOfflineBrokerAddr() {
        return offlineBrokerAddr;
    }

    public void setOfflineBrokerAddr(String offlineBrokerAddr) {
        this.offlineBrokerAddr = offlineBrokerAddr;
    }

    public String getHaBrokerAddr() {
        return haBrokerAddr;
    }

    public void setHaBrokerAddr(String haBrokerAddr) {
        this.haBrokerAddr = haBrokerAddr;
    }
}
