
package org.apache.rocketmq.client.hook;

import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.common.message.Message;

public class EndTransactionContext {
    private String producerGroup;
    private Message message;
    private String brokerAddr;
    private String msgId;
    private String transactionId;
    private LocalTransactionState transactionState;
    private boolean fromTransactionCheck;

    public String getProducerGroup() {
        return producerGroup;
    }

    public void setProducerGroup(String producerGroup) {
        this.producerGroup = producerGroup;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public String getBrokerAddr() {
        return brokerAddr;
    }

    public void setBrokerAddr(String brokerAddr) {
        this.brokerAddr = brokerAddr;
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public LocalTransactionState getTransactionState() {
        return transactionState;
    }

    public void setTransactionState(LocalTransactionState transactionState) {
        this.transactionState = transactionState;
    }

    public boolean isFromTransactionCheck() {
        return fromTransactionCheck;
    }

    public void setFromTransactionCheck(boolean fromTransactionCheck) {
        this.fromTransactionCheck = fromTransactionCheck;
    }
}
