
package org.apache.rocketmq.common;

import org.apache.rocketmq.remoting.common.RemotingUtil;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class RemotingUtilTest {
    @Test
    public void testGetLocalAddress() throws Exception {
        String localAddress = RemotingUtil.getLocalAddress();
        assertThat(localAddress).isNotNull();
        assertThat(localAddress.length()).isGreaterThan(0);
    }

    @Test
    public void testConvert2IpStringWithIp() {
        String result = RemotingUtil.convert2IpString("127.0.0.1:9876");
        assertThat(result).isEqualTo("127.0.0.1:9876");
    }

    @Test
    public void testConvert2IpStringWithHost() {
        String result = RemotingUtil.convert2IpString("localhost:9876");
        assertThat(result).isEqualTo("127.0.0.1:9876");
    }
}
