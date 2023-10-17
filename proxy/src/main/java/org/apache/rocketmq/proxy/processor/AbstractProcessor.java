
package org.apache.rocketmq.proxy.processor;

import org.apache.rocketmq.common.attribute.TopicMessageType;
import org.apache.rocketmq.common.consumer.ReceiptHandle;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageConst;
import org.apache.rocketmq.proxy.common.AbstractStartAndShutdown;
import org.apache.rocketmq.proxy.common.ProxyException;
import org.apache.rocketmq.proxy.common.ProxyExceptionCode;
import org.apache.rocketmq.proxy.service.ServiceManager;

public abstract class AbstractProcessor extends AbstractStartAndShutdown {

    protected MessagingProcessor messagingProcessor;
    protected ServiceManager serviceManager;

    public AbstractProcessor(MessagingProcessor messagingProcessor,
        ServiceManager serviceManager) {
        this.messagingProcessor = messagingProcessor;
        this.serviceManager = serviceManager;
    }

    protected void validateReceiptHandle(ReceiptHandle handle) {
        if (handle.isExpired()) {
            throw new ProxyException(ProxyExceptionCode.INVALID_RECEIPT_HANDLE, "receipt handle is expired");
        }
    }

    protected TopicMessageType parseFromMessageExt(Message message) {
        String isTrans = message.getProperty(MessageConst.PROPERTY_TRANSACTION_PREPARED);
        String isTransValue = "true";
        if (isTransValue.equals(isTrans)) {
            return TopicMessageType.TRANSACTION;
        } else if (message.getProperty(MessageConst.PROPERTY_DELAY_TIME_LEVEL) != null
            || message.getProperty(MessageConst.PROPERTY_TIMER_DELIVER_MS) != null
            || message.getProperty(MessageConst.PROPERTY_TIMER_DELAY_SEC) != null) {
            return TopicMessageType.DELAY;
        } else if (message.getProperty(MessageConst.PROPERTY_SHARDING_KEY) != null) {
            return TopicMessageType.FIFO;
        } else {
            return TopicMessageType.NORMAL;
        }
    }
}
