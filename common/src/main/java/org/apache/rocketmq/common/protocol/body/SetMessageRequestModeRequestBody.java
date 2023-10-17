

package org.apache.rocketmq.common.protocol.body;

import org.apache.rocketmq.common.message.MessageRequestMode;
import org.apache.rocketmq.remoting.protocol.RemotingSerializable;

public class SetMessageRequestModeRequestBody extends RemotingSerializable {

    private String topic;

    private String consumerGroup;

    private MessageRequestMode mode = MessageRequestMode.PULL;

    /*
    consumer working in pop mode could share the MessageQueues assigned to the N (N = popShareQueueNum) consumers following it in the cid list
     */
    private int popShareQueueNum = 0;

    public SetMessageRequestModeRequestBody() {
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getConsumerGroup() {
        return consumerGroup;
    }

    public void setConsumerGroup(String consumerGroup) {
        this.consumerGroup = consumerGroup;
    }

    public MessageRequestMode getMode() {
        return mode;
    }

    public void setMode(MessageRequestMode mode) {
        this.mode = mode;
    }

    public int getPopShareQueueNum() {
        return popShareQueueNum;
    }

    public void setPopShareQueueNum(int popShareQueueNum) {
        this.popShareQueueNum = popShareQueueNum;
    }
}
