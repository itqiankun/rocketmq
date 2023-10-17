
package org.apache.rocketmq.tools.command.server;

import org.apache.rocketmq.common.MixAll;
import org.apache.rocketmq.common.protocol.route.BrokerData;
import org.apache.rocketmq.common.protocol.route.TopicRouteData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * tools class
 */
public class NameServerMocker {

    /**
     * use the specified port to start the nameserver
     *
     * @param nameServerPort    nameServer port
     * @param brokerPort    broker port
     * @return  ServerResponseMocker
     */
    public static ServerResponseMocker startByDefaultConf(int nameServerPort, int brokerPort) {
        return startByDefaultConf(nameServerPort, brokerPort, null);
    }

    /**
     * use the specified port to start the nameserver
     *
     * @param nameServerPort    nameServer port
     * @param brokerPort    broker port
     * @param extMap    extend config
     * @return  ServerResponseMocker
     */
    public static ServerResponseMocker startByDefaultConf(int nameServerPort, int brokerPort,
                                                          HashMap<String, String> extMap) {

        System.setProperty(MixAll.NAMESRV_ADDR_PROPERTY, "127.0.0.1:" + nameServerPort);
        TopicRouteData topicRouteData = new TopicRouteData();
        List<BrokerData> dataList = new ArrayList<>();
        HashMap<Long, String> brokerAddress = new HashMap<>();
        brokerAddress.put(1L, "127.0.0.1:" + brokerPort);
        BrokerData brokerData = new BrokerData("mockCluster", "mockBrokerName", brokerAddress);
        brokerData.setBrokerName("mockBrokerName");
        dataList.add(brokerData);
        topicRouteData.setBrokerDatas(dataList);
        // start name server
        return ServerResponseMocker.startServer(nameServerPort, topicRouteData.encode(), extMap);
    }

}
