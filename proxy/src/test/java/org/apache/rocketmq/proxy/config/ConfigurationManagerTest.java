

package org.apache.rocketmq.proxy.config;

import org.apache.rocketmq.proxy.ProxyMode;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ConfigurationManagerTest extends InitConfigAndLoggerTest {

    @Test
    public void testInitEnv() {
        // configure proxy home by system env.
        assertThat(ConfigurationManager.getProxyHome()).isEqualTo(mockProxyHome);
    }

    @Test
    public void testIntConfig() {
        assertThat(ConfigurationManager.getProxyConfig()).isNotNull();
        assertThat(ConfigurationManager.getProxyConfig().getProxyMode()).isEqualToIgnoringCase(ProxyMode.CLUSTER.toString());

        String brokerConfig = ConfigurationManager.getProxyConfig().getBrokerConfigPath();
        assertThat(brokerConfig).isEqualTo(ConfigurationManager.getProxyHome() + "/conf/broker.conf");
    }

    @Test
    public void testGetProxyHome() {
        // test configured proxy home
        assertThat(ConfigurationManager.getProxyHome()).isEqualTo(mockProxyHome);
    }

    @Test
    public void testGetProxyConfig() {
        assertThat(ConfigurationManager.getProxyConfig()).isNotNull();
    }

} 
