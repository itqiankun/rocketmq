

package org.apache.rocketmq.remoting.netty;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class NettyServerConfigTest {

  @Test
  public void testChangeConfigBySystemProperty() {
    System.setProperty(NettySystemConfig.COM_ROCKETMQ_REMOTING_SOCKET_BACKLOG, "65535");
    NettySystemConfig.socketBacklog =
            Integer.parseInt(System.getProperty(NettySystemConfig.COM_ROCKETMQ_REMOTING_SOCKET_BACKLOG, "1024"));
    NettyServerConfig changedConfig = new NettyServerConfig();
    assertThat(changedConfig.getServerSocketBacklog()).isEqualTo(65535);
  }
}
