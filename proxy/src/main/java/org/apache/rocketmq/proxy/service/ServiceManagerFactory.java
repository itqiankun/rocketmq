
package org.apache.rocketmq.proxy.service;

import org.apache.rocketmq.broker.BrokerController;
import org.apache.rocketmq.remoting.RPCHook;

public class ServiceManagerFactory {
    public static ServiceManager createForLocalMode(BrokerController brokerController) {
        return createForLocalMode(brokerController, null);
    }

    public static ServiceManager createForLocalMode(BrokerController brokerController, RPCHook rpcHook) {
        return new LocalServiceManager(brokerController, rpcHook);
    }

    public static ServiceManager createForClusterMode() {
        return createForClusterMode(null);
    }

    public static ServiceManager createForClusterMode(RPCHook rpcHook) {
        return new ClusterServiceManager(rpcHook);
    }
}
