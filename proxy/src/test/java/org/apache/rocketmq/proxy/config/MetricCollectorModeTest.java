

package org.apache.rocketmq.proxy.config;

import org.junit.Assert;
import org.junit.Test;

public class MetricCollectorModeTest {

    @Test
    public void testGetEnumByOrdinal() {
        Assert.assertEquals(MetricCollectorMode.OFF, MetricCollectorMode.getEnumByString("off"));
        Assert.assertEquals(MetricCollectorMode.ON, MetricCollectorMode.getEnumByString("on"));
        Assert.assertEquals(MetricCollectorMode.PROXY, MetricCollectorMode.getEnumByString("proxy"));

        Assert.assertEquals(MetricCollectorMode.OFF, MetricCollectorMode.getEnumByString("OFF"));
        Assert.assertEquals(MetricCollectorMode.ON, MetricCollectorMode.getEnumByString("ON"));
        Assert.assertEquals(MetricCollectorMode.PROXY, MetricCollectorMode.getEnumByString("PROXY"));
    }

}