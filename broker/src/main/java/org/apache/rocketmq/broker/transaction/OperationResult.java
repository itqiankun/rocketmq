
package org.apache.rocketmq.broker.transaction;

import org.apache.rocketmq.common.message.MessageExt;

public class OperationResult {
    private MessageExt prepareMessage;

    private int responseCode;

    private String responseRemark;

    public void setPrepareMessage(MessageExt prepareMessage) {
        this.prepareMessage = prepareMessage;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseRemark() {
        return responseRemark;
    }

    public void setResponseRemark(String responseRemark) {
        this.responseRemark = responseRemark;
    }

    public MessageExt getPrepareMessage() {
        return prepareMessage;
    }
}
