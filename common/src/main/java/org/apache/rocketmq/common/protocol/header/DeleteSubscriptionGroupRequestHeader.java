

package org.apache.rocketmq.common.protocol.header;

import org.apache.rocketmq.remoting.CommandCustomHeader;
import org.apache.rocketmq.remoting.annotation.CFNotNull;
import org.apache.rocketmq.remoting.exception.RemotingCommandException;

public class DeleteSubscriptionGroupRequestHeader implements CommandCustomHeader {
    @CFNotNull
    private String groupName;

    private boolean cleanOffset = false;

    @Override
    public void checkFields() throws RemotingCommandException {
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public boolean isCleanOffset() {
        return cleanOffset;
    }

    public void setCleanOffset(boolean cleanOffset) {
        this.cleanOffset = cleanOffset;
    }
}
