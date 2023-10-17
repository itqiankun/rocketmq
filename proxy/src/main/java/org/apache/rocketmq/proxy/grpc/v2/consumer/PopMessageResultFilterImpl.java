
package org.apache.rocketmq.proxy.grpc.v2.consumer;

import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.protocol.heartbeat.SubscriptionData;
import org.apache.rocketmq.proxy.common.ProxyContext;
import org.apache.rocketmq.proxy.common.utils.FilterUtils;
import org.apache.rocketmq.proxy.processor.PopMessageResultFilter;

public class PopMessageResultFilterImpl implements PopMessageResultFilter {

    private final int maxAttempts;

    public PopMessageResultFilterImpl(int maxAttempts) {
        this.maxAttempts = maxAttempts;
    }

    @Override
    public FilterResult filterMessage(ProxyContext ctx, String consumerGroup, SubscriptionData subscriptionData,
        MessageExt messageExt) {
        if (!FilterUtils.isTagMatched(subscriptionData.getTagsSet(), messageExt.getTags())) {
            return FilterResult.NO_MATCH;
        }
        if (messageExt.getReconsumeTimes() >= maxAttempts) {
            return FilterResult.TO_DLQ;
        }
        return FilterResult.MATCH;
    }
}
