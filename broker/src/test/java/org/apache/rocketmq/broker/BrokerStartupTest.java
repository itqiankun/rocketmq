

package org.apache.rocketmq.broker;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Properties;
import org.apache.rocketmq.common.MixAll;
import org.junit.Assert;
import org.junit.Test;

public class BrokerStartupTest {

    private String storePathRootDir = ".";

    @Test
    public void testProperties2SystemEnv() throws NoSuchMethodException, InvocationTargetException,
        IllegalAccessException {
        Properties properties = new Properties();
        Class<BrokerStartup> clazz = BrokerStartup.class;
        Method method = clazz.getDeclaredMethod("properties2SystemEnv", Properties.class);
        method.setAccessible(true);
        {
            properties.put("rmqAddressServerDomain", "value1");
            properties.put("rmqAddressServerSubGroup", "value2");
            method.invoke(null, properties);
            Assert.assertEquals("value1", System.getProperty("rocketmq.namesrv.domain"));
            Assert.assertEquals("value2", System.getProperty("rocketmq.namesrv.domain.subgroup"));
        }
        {
            properties.put("rmqAddressServerDomain", MixAll.WS_DOMAIN_NAME);
            properties.put("rmqAddressServerSubGroup", MixAll.WS_DOMAIN_SUBGROUP);
            method.invoke(null, properties);
            Assert.assertEquals(MixAll.WS_DOMAIN_NAME, System.getProperty("rocketmq.namesrv.domain"));
            Assert.assertEquals(MixAll.WS_DOMAIN_SUBGROUP, System.getProperty("rocketmq.namesrv.domain.subgroup"));
        }


    }
}