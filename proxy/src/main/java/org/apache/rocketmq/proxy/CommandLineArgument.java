
package org.apache.rocketmq.proxy;

public class CommandLineArgument {
    private String namesrvAddr;
    private String brokerConfigPath;
    private String proxyConfigPath;
    private String proxyMode;

    public String getNamesrvAddr() {
        return namesrvAddr;
    }

    public void setNamesrvAddr(String namesrvAddr) {
        this.namesrvAddr = namesrvAddr;
    }

    public String getBrokerConfigPath() {
        return brokerConfigPath;
    }

    public void setBrokerConfigPath(String brokerConfigPath) {
        this.brokerConfigPath = brokerConfigPath;
    }

    public String getProxyConfigPath() {
        return proxyConfigPath;
    }

    public void setProxyConfigPath(String proxyConfigPath) {
        this.proxyConfigPath = proxyConfigPath;
    }

    public String getProxyMode() {
        return proxyMode;
    }

    public void setProxyMode(String proxyMode) {
        this.proxyMode = proxyMode;
    }
}
