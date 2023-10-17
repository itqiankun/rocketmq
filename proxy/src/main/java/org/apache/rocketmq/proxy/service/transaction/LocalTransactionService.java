
package org.apache.rocketmq.proxy.service.transaction;

import java.util.List;
import org.apache.rocketmq.common.BrokerConfig;

/**
 * no need to implements, because the channel of producer will put into the broker's producerManager
 */
public class LocalTransactionService extends AbstractTransactionService {

    protected final BrokerConfig brokerConfig;

    public LocalTransactionService(BrokerConfig brokerConfig) {
        this.brokerConfig = brokerConfig;
    }

    @Override
    public void addTransactionSubscription(String group, List<String> topicList) {

    }

    @Override
    public void addTransactionSubscription(String group, String topic) {

    }

    @Override
    public void replaceTransactionSubscription(String group, List<String> topicList) {

    }

    @Override
    public void unSubscribeAllTransactionTopic(String group) {

    }

    @Override
    protected String getBrokerNameByAddr(String brokerAddr) {
        return this.brokerConfig.getBrokerName();
    }
}
