

package org.apache.rocketmq.broker.plugin;

import org.apache.rocketmq.broker.BrokerController;
import org.apache.rocketmq.common.BrokerConfig;
import org.apache.rocketmq.store.MessageArrivingListener;
import org.apache.rocketmq.store.config.MessageStoreConfig;
import org.apache.rocketmq.store.stats.BrokerStatsManager;

public class MessageStorePluginContext {
    private BrokerController controller;
    private MessageStoreConfig messageStoreConfig;
    private BrokerStatsManager brokerStatsManager;
    private MessageArrivingListener messageArrivingListener;

    public MessageStorePluginContext(BrokerController controller, MessageStoreConfig messageStoreConfig,
        BrokerStatsManager brokerStatsManager, MessageArrivingListener messageArrivingListener) {
        super();
        this.messageStoreConfig = messageStoreConfig;
        this.brokerStatsManager = brokerStatsManager;
        this.messageArrivingListener = messageArrivingListener;
        this.controller = controller;
    }

    public MessageStoreConfig getMessageStoreConfig() {
        return messageStoreConfig;
    }

    public BrokerStatsManager getBrokerStatsManager() {
        return brokerStatsManager;
    }

    public MessageArrivingListener getMessageArrivingListener() {
        return messageArrivingListener;
    }

    public BrokerConfig getBrokerConfig() {
        return controller.getBrokerConfig();
    }

    public BrokerController getController() {
        return controller;
    }

}
