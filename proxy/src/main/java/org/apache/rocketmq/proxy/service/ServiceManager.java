
package org.apache.rocketmq.proxy.service;

import org.apache.rocketmq.broker.client.ConsumerManager;
import org.apache.rocketmq.broker.client.ProducerManager;
import org.apache.rocketmq.proxy.common.StartAndShutdown;
import org.apache.rocketmq.proxy.service.message.MessageService;
import org.apache.rocketmq.proxy.service.metadata.MetadataService;
import org.apache.rocketmq.proxy.service.relay.ProxyRelayService;
import org.apache.rocketmq.proxy.service.route.TopicRouteService;
import org.apache.rocketmq.proxy.service.transaction.TransactionService;

public interface ServiceManager extends StartAndShutdown {
    MessageService getMessageService();

    TopicRouteService getTopicRouteService();

    ProducerManager getProducerManager();

    ConsumerManager getConsumerManager();

    TransactionService getTransactionService();

    ProxyRelayService getProxyRelayService();

    MetadataService getMetadataService();
}
