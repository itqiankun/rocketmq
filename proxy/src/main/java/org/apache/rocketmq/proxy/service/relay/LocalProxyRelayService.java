
package org.apache.rocketmq.proxy.service.relay;

import java.util.concurrent.CompletableFuture;
import org.apache.rocketmq.broker.BrokerController;
import org.apache.rocketmq.common.protocol.ResponseCode;
import org.apache.rocketmq.common.protocol.body.ConsumeMessageDirectlyResult;
import org.apache.rocketmq.common.protocol.body.ConsumerRunningInfo;
import org.apache.rocketmq.common.protocol.header.ConsumeMessageDirectlyResultRequestHeader;
import org.apache.rocketmq.common.protocol.header.GetConsumerRunningInfoRequestHeader;
import org.apache.rocketmq.proxy.common.ProxyContext;
import org.apache.rocketmq.proxy.service.channel.SimpleChannel;
import org.apache.rocketmq.proxy.service.transaction.TransactionService;
import org.apache.rocketmq.remoting.RemotingServer;
import org.apache.rocketmq.remoting.netty.NettyRemotingAbstract;
import org.apache.rocketmq.remoting.protocol.RemotingCommand;

public class LocalProxyRelayService extends AbstractProxyRelayService {

    private final BrokerController brokerController;

    public LocalProxyRelayService(BrokerController brokerController, TransactionService transactionService) {
        super(transactionService);
        this.brokerController = brokerController;
    }

    @Override
    public CompletableFuture<ProxyRelayResult<ConsumerRunningInfo>> processGetConsumerRunningInfo(
        ProxyContext context, RemotingCommand command, GetConsumerRunningInfoRequestHeader header) {
        CompletableFuture<ProxyRelayResult<ConsumerRunningInfo>> future = new CompletableFuture<>();
        future.thenAccept(proxyOutResult -> {
            RemotingServer remotingServer = this.brokerController.getRemotingServer();
            if (remotingServer instanceof NettyRemotingAbstract) {
                NettyRemotingAbstract nettyRemotingAbstract = (NettyRemotingAbstract) remotingServer;
                RemotingCommand remotingCommand = RemotingCommand.createResponseCommand(null);
                remotingCommand.setOpaque(command.getOpaque());
                remotingCommand.setCode(proxyOutResult.getCode());
                remotingCommand.setRemark(proxyOutResult.getRemark());
                if (proxyOutResult.getCode() == ResponseCode.SUCCESS && proxyOutResult.getResult() != null) {
                    ConsumerRunningInfo consumerRunningInfo = proxyOutResult.getResult();
                    remotingCommand.setBody(consumerRunningInfo.encode());
                }
                SimpleChannel simpleChannel = new SimpleChannel(context.getRemoteAddress(), context.getLocalAddress());
                nettyRemotingAbstract.processResponseCommand(simpleChannel.getChannelHandlerContext(), remotingCommand);
            }
        });
        return future;
    }

    @Override
    public CompletableFuture<ProxyRelayResult<ConsumeMessageDirectlyResult>> processConsumeMessageDirectly(
        ProxyContext context, RemotingCommand command,
        ConsumeMessageDirectlyResultRequestHeader header) {
        CompletableFuture<ProxyRelayResult<ConsumeMessageDirectlyResult>> future = new CompletableFuture<>();
        future.thenAccept(proxyOutResult -> {
            RemotingServer remotingServer = this.brokerController.getRemotingServer();
            if (remotingServer instanceof NettyRemotingAbstract) {
                NettyRemotingAbstract nettyRemotingAbstract = (NettyRemotingAbstract) remotingServer;
                RemotingCommand remotingCommand = RemotingCommand.createResponseCommand(null);
                remotingCommand.setOpaque(command.getOpaque());
                remotingCommand.setCode(proxyOutResult.getCode());
                remotingCommand.setRemark(proxyOutResult.getRemark());
                if (proxyOutResult.getCode() == ResponseCode.SUCCESS && proxyOutResult.getResult() != null) {
                    ConsumeMessageDirectlyResult consumeMessageDirectlyResult = proxyOutResult.getResult();
                    remotingCommand.setBody(consumeMessageDirectlyResult.encode());
                }
                SimpleChannel simpleChannel = new SimpleChannel(context.getRemoteAddress(), context.getLocalAddress());
                nettyRemotingAbstract.processResponseCommand(simpleChannel.getChannelHandlerContext(), remotingCommand);
            }
        });
        return future;
    }
}
