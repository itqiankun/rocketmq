
package org.apache.rocketmq.proxy.processor;

import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.protocol.heartbeat.SubscriptionData;
import org.apache.rocketmq.proxy.common.ProxyContext;

public interface PopMessageResultFilter {

    enum FilterResult {
        TO_DLQ,
        NO_MATCH,
        MATCH
    }

    FilterResult filterMessage(ProxyContext ctx, String consumerGroup, SubscriptionData subscriptionData,
        MessageExt messageExt);
}
