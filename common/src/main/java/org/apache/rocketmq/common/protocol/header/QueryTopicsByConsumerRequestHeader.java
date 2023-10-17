

/**
 * $Id: QueryMessageRequestHeader.java 1835 2013-05-16 02:00:50Z vintagewang@apache.org $
 */
package org.apache.rocketmq.common.protocol.header;

import org.apache.rocketmq.remoting.CommandCustomHeader;
import org.apache.rocketmq.remoting.annotation.CFNotNull;
import org.apache.rocketmq.remoting.exception.RemotingCommandException;

public class QueryTopicsByConsumerRequestHeader implements CommandCustomHeader {
    @CFNotNull
    private String group;

    @Override
    public void checkFields() throws RemotingCommandException {

    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }
}
