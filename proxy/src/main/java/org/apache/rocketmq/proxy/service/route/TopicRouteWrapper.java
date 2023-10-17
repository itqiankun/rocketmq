
package org.apache.rocketmq.proxy.service.route;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.apache.rocketmq.common.MixAll;
import org.apache.rocketmq.common.protocol.route.BrokerData;
import org.apache.rocketmq.common.protocol.route.QueueData;
import org.apache.rocketmq.common.protocol.route.TopicRouteData;

public class TopicRouteWrapper {

    private final TopicRouteData topicRouteData;
    private final String topicName;
    private final Map<String/* brokerName */, BrokerData> brokerNameRouteData = new HashMap<>();

    public TopicRouteWrapper(TopicRouteData topicRouteData, String topicName) {
        this.topicRouteData = topicRouteData;
        this.topicName = topicName;

        if (this.topicRouteData.getBrokerDatas() != null) {
            for (BrokerData brokerData : this.topicRouteData.getBrokerDatas()) {
                this.brokerNameRouteData.put(brokerData.getBrokerName(), brokerData);
            }
        }
    }

    public String getMasterAddr(String brokerName) {
        return this.brokerNameRouteData.get(brokerName).getBrokerAddrs().get(MixAll.MASTER_ID);
    }

    public String getMasterAddrPrefer(String brokerName) {
        HashMap<Long, String> brokerAddr = brokerNameRouteData.get(brokerName).getBrokerAddrs();
        String addr = brokerAddr.get(MixAll.MASTER_ID);
        if (addr == null) {
            Optional<Long> optional = brokerAddr.keySet().stream().findFirst();
            return optional.map(brokerAddr::get).orElse(null);
        }
        return addr;
    }

    public String getTopicName() {
        return topicName;
    }

    public TopicRouteData getTopicRouteData() {
        return topicRouteData;
    }

    public List<QueueData> getQueueDatas() {
        return this.topicRouteData.getQueueDatas();
    }

    public String getOrderTopicConf() {
        return this.topicRouteData.getOrderTopicConf();
    }
}
