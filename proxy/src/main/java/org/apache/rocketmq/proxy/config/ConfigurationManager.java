

package org.apache.rocketmq.proxy.config;

import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.common.MixAll;

public class ConfigurationManager {
    public static final String RMQ_PROXY_HOME = "RMQ_PROXY_HOME";
    protected static final String DEFAULT_RMQ_PROXY_HOME = System.getenv(MixAll.ROCKETMQ_HOME_ENV);
    protected static String proxyHome;
    protected static Configuration configuration;

    public static void initEnv() {
        proxyHome = System.getenv(RMQ_PROXY_HOME);
        if (StringUtils.isEmpty(proxyHome)) {
            proxyHome = System.getProperty(RMQ_PROXY_HOME, DEFAULT_RMQ_PROXY_HOME);
        }
    }

    public static void intConfig() throws Exception {
        configuration = new Configuration();
        configuration.init();
    }

    public static String getProxyHome() {
        return proxyHome;
    }

    public static ProxyConfig getProxyConfig() {
        return configuration.getProxyConfig();
    }
}
