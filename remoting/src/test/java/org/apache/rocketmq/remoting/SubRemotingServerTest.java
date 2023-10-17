

package org.apache.rocketmq.remoting;

import io.netty.channel.ChannelHandlerContext;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.apache.rocketmq.remoting.exception.RemotingConnectException;
import org.apache.rocketmq.remoting.exception.RemotingSendRequestException;
import org.apache.rocketmq.remoting.exception.RemotingTimeoutException;
import org.apache.rocketmq.remoting.netty.NettyRequestProcessor;
import org.apache.rocketmq.remoting.protocol.RemotingCommand;
import org.apache.rocketmq.remoting.protocol.RemotingSysResponseCode;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Fail.failBecauseExceptionWasNotThrown;

public class SubRemotingServerTest {
    private static final int subServerPort = 1234;

    private static RemotingServer remotingServer;
    private static RemotingClient remotingClient;
    private static RemotingServer subServer;

    @BeforeClass
    public static void setup() throws InterruptedException {
        remotingServer = RemotingServerTest.createRemotingServer();
        remotingClient = RemotingServerTest.createRemotingClient();
        subServer = createSubRemotingServer(remotingServer);
    }

    @AfterClass
    public static void destroy() {
        remotingClient.shutdown();
        remotingServer.shutdown();
    }

    public static RemotingServer createSubRemotingServer(RemotingServer parentServer) {
        RemotingServer subServer = parentServer.newRemotingServer(subServerPort);
        subServer.registerProcessor(1, new NettyRequestProcessor() {
            @Override
            public RemotingCommand processRequest(final ChannelHandlerContext ctx,
                    final RemotingCommand request) throws Exception {
                request.setRemark(String.valueOf(RemotingHelper.parseSocketAddressPort(ctx.channel().localAddress())));
                return request;
            }

            @Override
            public boolean rejectRequest() {
                return false;
            }
        }, null);
        subServer.start();
        return subServer;
    }

    @Test
    public void testInvokeSubRemotingServer() throws InterruptedException, RemotingTimeoutException,
            RemotingConnectException, RemotingSendRequestException {
        RequestHeader requestHeader = new RequestHeader();
        requestHeader.setCount(1);
        requestHeader.setMessageTitle("Welcome");

        // Parent remoting server doesn't support RequestCode 1
        RemotingCommand request = RemotingCommand.createRequestCommand(1, requestHeader);
        RemotingCommand response = remotingClient.invokeSync("localhost:" + remotingServer.localListenPort(), request,
                1000 * 3);
        assertThat(response).isNotNull();
        assertThat(response.getCode()).isEqualTo(RemotingSysResponseCode.REQUEST_CODE_NOT_SUPPORTED);

        // Issue request to SubRemotingServer
        response = remotingClient.invokeSync("localhost:1234", request, 1000 * 3);
        assertThat(response).isNotNull();
        assertThat(response.getExtFields()).hasSize(2);
        assertThat(response.getRemark()).isEqualTo(String.valueOf(subServerPort));

        // Issue unsupported request to SubRemotingServer
        request.setCode(0);
        response = remotingClient.invokeSync("localhost:1234", request, 1000 * 3);
        assertThat(response).isNotNull();
        assertThat(response.getCode()).isEqualTo(RemotingSysResponseCode.REQUEST_CODE_NOT_SUPPORTED);

        // Issue request to a closed SubRemotingServer
        request.setCode(1);
        remotingServer.removeRemotingServer(subServerPort);
        subServer.shutdown();
        try {
            remotingClient.invokeSync("localhost:1234", request, 1000 * 3);
            failBecauseExceptionWasNotThrown(RemotingTimeoutException.class);
        } catch (Exception e) {
            assertThat(e).isInstanceOf(RemotingTimeoutException.class);
        }
    }
}
