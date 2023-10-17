
package org.apache.rocketmq.proxy.service.route;

import com.google.common.base.MoreObjects;
import java.util.Objects;
import org.apache.rocketmq.common.message.MessageQueue;

public class AddressableMessageQueue implements Comparable<AddressableMessageQueue> {

    private final MessageQueue messageQueue;
    private final String brokerAddr;

    public AddressableMessageQueue(MessageQueue messageQueue, String brokerAddr) {
        this.messageQueue = messageQueue;
        this.brokerAddr = brokerAddr;
    }

    @Override
    public int compareTo(AddressableMessageQueue o) {
        return messageQueue.compareTo(o.messageQueue);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AddressableMessageQueue)) {
            return false;
        }
        AddressableMessageQueue queue = (AddressableMessageQueue) o;
        return Objects.equals(messageQueue, queue.messageQueue);
    }

    @Override
    public int hashCode() {
        return messageQueue == null ? 1 : messageQueue.hashCode();
    }

    public int getQueueId() {
        return this.messageQueue.getQueueId();
    }

    public String getBrokerName() {
        return this.messageQueue.getBrokerName();
    }

    public String getTopic() {
        return messageQueue.getTopic();
    }

    public MessageQueue getMessageQueue() {
        return messageQueue;
    }

    public String getBrokerAddr() {
        return brokerAddr;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
            .add("messageQueue", messageQueue)
            .add("brokerAddr", brokerAddr)
            .toString();
    }
}
