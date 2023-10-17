

/**
 * $Id: DeleteTopicRequestHeader.java 1835 2013-05-16 02:00:50Z vintagewang@apache.org $
 */
package org.apache.rocketmq.common.protocol.header;

import com.google.common.base.MoreObjects;
import org.apache.rocketmq.remoting.CommandCustomHeader;
import org.apache.rocketmq.remoting.annotation.CFNotNull;
import org.apache.rocketmq.remoting.exception.RemotingCommandException;

public class CloneGroupOffsetRequestHeader implements CommandCustomHeader {
    @CFNotNull
    private String srcGroup;
    @CFNotNull
    private String destGroup;
    private String topic;
    private boolean offline;

    @Override
    public void checkFields() throws RemotingCommandException {
    }

    public String getDestGroup() {
        return destGroup;
    }

    public void setDestGroup(String destGroup) {
        this.destGroup = destGroup;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getSrcGroup() {

        return srcGroup;
    }

    public void setSrcGroup(String srcGroup) {
        this.srcGroup = srcGroup;
    }

    public boolean isOffline() {
        return offline;
    }

    public void setOffline(boolean offline) {
        this.offline = offline;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
            .add("srcGroup", srcGroup)
            .add("destGroup", destGroup)
            .add("topic", topic)
            .add("offline", offline)
            .toString();
    }
}
