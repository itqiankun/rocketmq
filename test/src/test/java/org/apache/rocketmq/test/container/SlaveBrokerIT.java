

package org.apache.rocketmq.test.container;

import java.time.Duration;
import java.util.concurrent.TimeUnit;
import org.apache.rocketmq.common.protocol.body.ClusterInfo;
import org.apache.rocketmq.store.DefaultMessageStore;
import org.junit.Ignore;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;

@Ignore
public class SlaveBrokerIT extends ContainerIntegrationTestBase {
    @Test
    public void reAddSlaveBroker() throws Exception {
        await().atMost(Duration.ofMinutes(1)).until(() -> {
            ClusterInfo clusterInfo = defaultMQAdminExt.examineBrokerClusterInfo();

            if (clusterInfo.getClusterAddrTable().get(master1With3Replicas.getBrokerConfig().getBrokerClusterName()).size() != 3) {
                return false;
            }

            if (clusterInfo.getBrokerAddrTable().get(master1With3Replicas.getBrokerConfig().getBrokerName()).getBrokerAddrs().size() != 3) {
                return false;
            }

            if (clusterInfo.getBrokerAddrTable().get(master2With3Replicas.getBrokerConfig().getBrokerName()).getBrokerAddrs().size() != 3) {
                return false;
            }

            if (clusterInfo.getBrokerAddrTable().get(master3With3Replicas.getBrokerConfig().getBrokerName()).getBrokerAddrs().size() != 3) {
                return false;
            }

            return true;
        });

        // Remove one replicas from each broker group
        removeSlaveBroker(1, brokerContainer1, master3With3Replicas);
        removeSlaveBroker(1, brokerContainer2, master1With3Replicas);
        removeSlaveBroker(1, brokerContainer3, master2With3Replicas);

        await().atMost(Duration.ofMinutes(1)).until(() -> {
            // Test cluster info again
            ClusterInfo clusterInfo = defaultMQAdminExt.examineBrokerClusterInfo();
            assertThat(clusterInfo.getBrokerAddrTable().get(master1With3Replicas.getBrokerConfig().getBrokerName()).getBrokerAddrs().size())
                .isEqualTo(2);

            assertThat(clusterInfo.getBrokerAddrTable().get(master2With3Replicas.getBrokerConfig().getBrokerName()).getBrokerAddrs().size())
                .isEqualTo(2);

            assertThat(clusterInfo.getBrokerAddrTable().get(master3With3Replicas.getBrokerConfig().getBrokerName()).getBrokerAddrs().size())
                .isEqualTo(2);
            return true;
        });

        // ReAdd the slave broker
        createAndAddSlave(1, brokerContainer1, master3With3Replicas);
        createAndAddSlave(1, brokerContainer2, master1With3Replicas);
        createAndAddSlave(1, brokerContainer3, master2With3Replicas);

        // Trigger a register action
        //for (final SlaveBrokerController slaveBrokerController : brokerContainer1.getSlaveBrokers()) {
        //    slaveBrokerController.registerBrokerAll(false, false, true);
        //}
        //
        //for (final SlaveBrokerController slaveBrokerController : brokerContainer2.getSlaveBrokers()) {
        //    slaveBrokerController.registerBrokerAll(false, false, true);
        //}

        await().atMost(Duration.ofMinutes(1)).until(() -> {
            ClusterInfo clusterInfo = defaultMQAdminExt.examineBrokerClusterInfo();

            return clusterInfo.getBrokerAddrTable()
                .get(master1With3Replicas.getBrokerConfig().getBrokerName()).getBrokerAddrs().size() == 3
                && clusterInfo.getBrokerAddrTable()
                .get(master2With3Replicas.getBrokerConfig().getBrokerName()).getBrokerAddrs().size() == 3
                && clusterInfo.getBrokerAddrTable()
                .get(master2With3Replicas.getBrokerConfig().getBrokerName()).getBrokerAddrs().size() == 3;
        });
    }

    @Test
    public void reAddSlaveBroker_ConnectionCheck() throws Exception {
        await().atMost(100, TimeUnit.SECONDS)
            .until(() -> ((DefaultMessageStore) master3With3Replicas.getMessageStore()).getHaService().getConnectionCount().get() == 2);

        removeSlaveBroker(1, brokerContainer1, master3With3Replicas);
        createAndAddSlave(1, brokerContainer1, master3With3Replicas);

        await().atMost(100, TimeUnit.SECONDS)
            .until(() -> ((DefaultMessageStore) master3With3Replicas.getMessageStore()).getHaService().getConnectionCount().get() == 2);

        await().atMost(100, TimeUnit.SECONDS)
            .until(() -> ((DefaultMessageStore) master3With3Replicas.getMessageStore()).getHaService().inSyncReplicasNums(0) == 3);

        Thread.sleep(1000 * 101);
    }
}
