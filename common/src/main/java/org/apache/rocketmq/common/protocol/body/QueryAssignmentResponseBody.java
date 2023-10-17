

package org.apache.rocketmq.common.protocol.body;

import java.util.Set;
import org.apache.rocketmq.common.message.MessageQueueAssignment;
import org.apache.rocketmq.remoting.protocol.RemotingSerializable;

public class QueryAssignmentResponseBody extends RemotingSerializable {

    private Set<MessageQueueAssignment> messageQueueAssignments;

    public Set<MessageQueueAssignment> getMessageQueueAssignments() {
        return messageQueueAssignments;
    }

    public void setMessageQueueAssignments(
        Set<MessageQueueAssignment> messageQueueAssignments) {
        this.messageQueueAssignments = messageQueueAssignments;
    }
}
