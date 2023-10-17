
package org.apache.rocketmq.proxy.service.route;

import java.util.List;
import org.apache.rocketmq.common.MixAll;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.common.protocol.route.BrokerData;
import org.apache.rocketmq.common.protocol.route.TopicRouteData;
import org.apache.rocketmq.proxy.common.Address;
import org.apache.rocketmq.proxy.service.mqclient.MQClientAPIFactory;

public class ClusterTopicRouteService extends TopicRouteService {

    public ClusterTopicRouteService(MQClientAPIFactory mqClientAPIFactory) {
        super(mqClientAPIFactory);
    }

    @Override
    public MessageQueueView getCurrentMessageQueueView(String topicName) throws Exception {
        return getAllMessageQueueView(topicName);
    }

    @Override
    public ProxyTopicRouteData getTopicRouteForProxy(List<Address> requestHostAndPortList,
        String topicName) throws Exception {
        TopicRouteData topicRouteData = getAllMessageQueueView(topicName).getTopicRouteData();

        ProxyTopicRouteData proxyTopicRouteData = new ProxyTopicRouteData();
        proxyTopicRouteData.setQueueDatas(topicRouteData.getQueueDatas());

        for (BrokerData brokerData : topicRouteData.getBrokerDatas()) {
            ProxyTopicRouteData.ProxyBrokerData proxyBrokerData = new ProxyTopicRouteData.ProxyBrokerData();
            proxyBrokerData.setCluster(brokerData.getCluster());
            proxyBrokerData.setBrokerName(brokerData.getBrokerName());
            for (Long brokerId : brokerData.getBrokerAddrs().keySet()) {
                proxyBrokerData.getBrokerAddrs().put(brokerId, requestHostAndPortList);
            }
            proxyTopicRouteData.getBrokerDatas().add(proxyBrokerData);
        }

        return proxyTopicRouteData;
    }

    @Override
    public String getBrokerAddr(String brokerName) throws Exception {
        List<BrokerData> brokerDataList = getAllMessageQueueView(brokerName).getTopicRouteData().getBrokerDatas();
        if (brokerDataList.isEmpty()) {
            return null;
        }
        return brokerDataList.get(0).getBrokerAddrs().get(MixAll.MASTER_ID);
    }

    @Override
    public AddressableMessageQueue buildAddressableMessageQueue(MessageQueue messageQueue) throws Exception {
        String brokerAddress = getBrokerAddr(messageQueue.getBrokerName());
        return new AddressableMessageQueue(messageQueue, brokerAddress);
    }
}
