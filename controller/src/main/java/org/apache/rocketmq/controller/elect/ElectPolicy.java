
package org.apache.rocketmq.controller.elect;


import java.util.Set;

public interface ElectPolicy {

    /**
     * elect a master
     *
     * @param clusterName       the brokerGroup belongs
     * @param syncStateBrokers  all broker replicas in syncStateSet
     * @param allReplicaBrokers all broker replicas
     * @param oldMaster         old master
     * @param preferBrokerAddr  the broker prefer to be elected
     * @return new master's brokerAddr
     */
    String elect(String clusterName, Set<String> syncStateBrokers, Set<String> allReplicaBrokers, String oldMaster, String preferBrokerAddr);

}
