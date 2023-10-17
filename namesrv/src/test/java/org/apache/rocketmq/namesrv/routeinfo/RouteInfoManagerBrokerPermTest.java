
package org.apache.rocketmq.namesrv.routeinfo;

import org.apache.rocketmq.common.constant.PermName;
import org.apache.rocketmq.common.namesrv.NamesrvConfig;
import org.apache.rocketmq.common.protocol.route.BrokerData;
import org.apache.rocketmq.common.protocol.route.QueueData;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class RouteInfoManagerBrokerPermTest extends RouteInfoManagerTestBase {
    private static RouteInfoManager routeInfoManager;
    public static String clusterName = "cluster";
    public static String brokerPrefix = "broker";
    public static String topicPrefix = "topic";

    public static RouteInfoManagerTestBase.Cluster cluster;

    @Before
    public void setup() {
        routeInfoManager = new RouteInfoManager(new NamesrvConfig(), null);
        cluster = registerCluster(routeInfoManager,
            clusterName,
            brokerPrefix,
            3,
            3,
            topicPrefix,
            10);
    }

    @After
    public void terminate() {
        routeInfoManager.printAllPeriodically();

        for (BrokerData bd : cluster.brokerDataMap.values()) {
            unregisterBrokerAll(routeInfoManager, bd);
        }
    }

    @Test
    public void testAddWritePermOfBrokerByLock() throws Exception {
        String brokerName = getBrokerName(brokerPrefix, 0);
        String topicName = getTopicName(topicPrefix, 0);

        QueueData qd = new QueueData();
        qd.setPerm(PermName.PERM_READ);
        qd.setBrokerName(brokerName);

        HashMap<String, Map<String, QueueData>> topicQueueTable = new HashMap<>();

        Map<String, QueueData> queueDataMap = new HashMap<>();
        queueDataMap.put(brokerName, qd);
        topicQueueTable.put(topicName, queueDataMap);

        Field filed = RouteInfoManager.class.getDeclaredField("topicQueueTable");
        filed.setAccessible(true);
        filed.set(routeInfoManager, topicQueueTable);

        int addTopicCnt = routeInfoManager.addWritePermOfBrokerByLock(brokerName);
        assertThat(addTopicCnt).isEqualTo(1);
        assertThat(qd.getPerm()).isEqualTo(PermName.PERM_READ | PermName.PERM_WRITE);

    }

    @Test
    public void testWipeWritePermOfBrokerByLock() throws Exception {
        String brokerName = getBrokerName(brokerPrefix, 0);
        String topicName = getTopicName(topicPrefix, 0);

        QueueData qd = new QueueData();
        qd.setPerm(PermName.PERM_READ);
        qd.setBrokerName(brokerName);

        HashMap<String, Map<String, QueueData>> topicQueueTable = new HashMap<>();

        Map<String, QueueData> queueDataMap = new HashMap<>();
        queueDataMap.put(brokerName, qd);
        topicQueueTable.put(topicName, queueDataMap);

        Field filed = RouteInfoManager.class.getDeclaredField("topicQueueTable");
        filed.setAccessible(true);
        filed.set(routeInfoManager, topicQueueTable);

        int addTopicCnt = routeInfoManager.wipeWritePermOfBrokerByLock(brokerName);
        assertThat(addTopicCnt).isEqualTo(1);
        assertThat(qd.getPerm()).isEqualTo(PermName.PERM_READ);

    }
}
