

package org.apache.rocketmq.common.protocol.body;

import com.google.common.base.Objects;
import java.util.ArrayList;
import java.util.Map;
import java.util.List;
import java.util.Set;
import org.apache.rocketmq.common.protocol.route.BrokerData;
import org.apache.rocketmq.remoting.protocol.RemotingSerializable;

public class ClusterInfo extends RemotingSerializable {
    private Map<String/* brokerName */, BrokerData> brokerAddrTable;
    private Map<String/* clusterName */, Set<String/* brokerName */>> clusterAddrTable;

    public Map<String, BrokerData> getBrokerAddrTable() {
        return brokerAddrTable;
    }

    public void setBrokerAddrTable(Map<String, BrokerData> brokerAddrTable) {
        this.brokerAddrTable = brokerAddrTable;
    }

    public Map<String, Set<String>> getClusterAddrTable() {
        return clusterAddrTable;
    }

    public void setClusterAddrTable(Map<String, Set<String>> clusterAddrTable) {
        this.clusterAddrTable = clusterAddrTable;
    }

    public String[] retrieveAllAddrByCluster(String cluster) {
        List<String> addrs = new ArrayList<String>();
        if (clusterAddrTable.containsKey(cluster)) {
            Set<String> brokerNames = clusterAddrTable.get(cluster);
            for (String brokerName : brokerNames) {
                BrokerData brokerData = brokerAddrTable.get(brokerName);
                if (null != brokerData) {
                    addrs.addAll(brokerData.getBrokerAddrs().values());
                }
            }
        }

        return addrs.toArray(new String[] {});
    }

    public String[] retrieveAllClusterNames() {
        return clusterAddrTable.keySet().toArray(new String[] {});
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        ClusterInfo info = (ClusterInfo) o;
        return Objects.equal(brokerAddrTable, info.brokerAddrTable) && Objects.equal(clusterAddrTable, info.clusterAddrTable);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(brokerAddrTable, clusterAddrTable);
    }
}
