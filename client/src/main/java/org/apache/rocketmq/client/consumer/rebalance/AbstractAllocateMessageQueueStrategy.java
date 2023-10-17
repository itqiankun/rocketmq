

package org.apache.rocketmq.client.consumer.rebalance;

import java.util.List;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.client.consumer.AllocateMessageQueueStrategy;
import org.apache.rocketmq.client.log.ClientLogger;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.logging.InternalLogger;

public abstract class AbstractAllocateMessageQueueStrategy implements AllocateMessageQueueStrategy {

    protected InternalLogger log;

    AbstractAllocateMessageQueueStrategy() {
        this.log = ClientLogger.getLog();
    }

    public AbstractAllocateMessageQueueStrategy(InternalLogger log) {
        this.log = log;
    }

    public boolean check(String consumerGroup, String currentCID, List<MessageQueue> mqAll,
        List<String> cidAll) {
        if (StringUtils.isEmpty(currentCID)) {
            throw new IllegalArgumentException("currentCID is empty");
        }
        if (CollectionUtils.isEmpty(mqAll)) {
            throw new IllegalArgumentException("mqAll is null or mqAll empty");
        }
        if (CollectionUtils.isEmpty(cidAll)) {
            throw new IllegalArgumentException("cidAll is null or cidAll empty");
        }

        if (!cidAll.contains(currentCID)) {
            log.info("[BUG] ConsumerGroup: {} The consumerId: {} not in cidAll: {}",
                consumerGroup,
                currentCID,
                cidAll);
            return false;
        }

        return true;
    }
}
