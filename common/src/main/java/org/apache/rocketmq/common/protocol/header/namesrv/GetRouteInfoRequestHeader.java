

/**
 * $Id: GetRouteInfoRequestHeader.java 1835 2013-05-16 02:00:50Z vintagewang@apache.org $
 */
package org.apache.rocketmq.common.protocol.header.namesrv;

import org.apache.rocketmq.remoting.CommandCustomHeader;
import org.apache.rocketmq.remoting.annotation.CFNotNull;
import org.apache.rocketmq.remoting.annotation.CFNullable;
import org.apache.rocketmq.remoting.exception.RemotingCommandException;

public class GetRouteInfoRequestHeader implements CommandCustomHeader {

    @CFNotNull
    private String topic;

    @CFNullable
    private Boolean acceptStandardJsonOnly;

    @Override
    public void checkFields() throws RemotingCommandException {
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public Boolean getAcceptStandardJsonOnly() {
        return acceptStandardJsonOnly;
    }

    public void setAcceptStandardJsonOnly(Boolean acceptStandardJsonOnly) {
        this.acceptStandardJsonOnly = acceptStandardJsonOnly;
    }
}
