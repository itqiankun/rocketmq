

package org.apache.rocketmq.broker.plugin;

import io.netty.channel.Channel;
import org.apache.rocketmq.common.protocol.header.PullMessageRequestHeader;
import org.apache.rocketmq.common.protocol.heartbeat.SubscriptionData;
import org.apache.rocketmq.common.subscription.SubscriptionGroupConfig;
import org.apache.rocketmq.remoting.protocol.RemotingCommand;
import org.apache.rocketmq.store.GetMessageResult;
import org.apache.rocketmq.store.MessageFilter;

public interface PullMessageResultHandler {

    /**
     * Handle result of get message from store.
     *
     * @param getMessageResult store result
     * @param request request
     * @param requestHeader request header
     * @param channel channel
     * @param subscriptionData sub data
     * @param subscriptionGroupConfig sub config
     * @param brokerAllowSuspend brokerAllowSuspend
     * @param messageFilter store message filter
     * @param response response
     * @return response or null
     */
    RemotingCommand handle(final GetMessageResult getMessageResult,
                           final RemotingCommand request,
                           final PullMessageRequestHeader requestHeader,
                           final Channel channel,
                           final SubscriptionData subscriptionData,
                           final SubscriptionGroupConfig subscriptionGroupConfig,
                           final boolean brokerAllowSuspend,
                           final MessageFilter messageFilter,
                           final RemotingCommand response);
}
