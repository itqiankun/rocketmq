
package org.apache.rocketmq.proxy.processor;

import org.apache.rocketmq.proxy.common.ProxyContext;
import org.apache.rocketmq.proxy.service.route.AddressableMessageQueue;
import org.apache.rocketmq.proxy.service.route.MessageQueueView;

public interface QueueSelector {

    AddressableMessageQueue select(ProxyContext ctx, MessageQueueView messageQueueView);
}
