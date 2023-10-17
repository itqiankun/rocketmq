

package org.apache.rocketmq.container;

import org.apache.rocketmq.common.MixAll;
import org.apache.rocketmq.common.annotation.ImportantField;
import org.apache.rocketmq.remoting.common.RemotingUtil;

public class BrokerContainerConfig {

    private String rocketmqHome = System.getProperty(MixAll.ROCKETMQ_HOME_PROPERTY, System.getenv(MixAll.ROCKETMQ_HOME_ENV));

    @ImportantField
    private String namesrvAddr = System.getProperty(MixAll.NAMESRV_ADDR_PROPERTY, System.getenv(MixAll.NAMESRV_ADDR_ENV));

    @ImportantField
    private boolean fetchNamesrvAddrByAddressServer = false;

    @ImportantField
    private String brokerContainerIP = RemotingUtil.getLocalAddress();

    private String brokerConfigPaths = null;

    public String getRocketmqHome() {
        return rocketmqHome;
    }

    public void setRocketmqHome(String rocketmqHome) {
        this.rocketmqHome = rocketmqHome;
    }

    public String getNamesrvAddr() {
        return namesrvAddr;
    }

    public void setNamesrvAddr(String namesrvAddr) {
        this.namesrvAddr = namesrvAddr;
    }

    public boolean isFetchNamesrvAddrByAddressServer() {
        return fetchNamesrvAddrByAddressServer;
    }

    public void setFetchNamesrvAddrByAddressServer(boolean fetchNamesrvAddrByAddressServer) {
        this.fetchNamesrvAddrByAddressServer = fetchNamesrvAddrByAddressServer;
    }

    public String getBrokerContainerIP() {
        return brokerContainerIP;
    }

    public String getBrokerConfigPaths() {
        return brokerConfigPaths;
    }

    public void setBrokerConfigPaths(String brokerConfigPaths) {
        this.brokerConfigPaths = brokerConfigPaths;
    }

}
