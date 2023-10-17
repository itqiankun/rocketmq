
package org.apache.rocketmq.proxy.service.mqclient;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import java.nio.ByteBuffer;
import org.apache.rocketmq.broker.client.ProducerManager;
import org.apache.rocketmq.client.impl.ClientRemotingProcessor;
import org.apache.rocketmq.common.constant.LoggerName;
import org.apache.rocketmq.common.message.MessageConst;
import org.apache.rocketmq.common.message.MessageDecoder;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.protocol.RequestCode;
import org.apache.rocketmq.common.protocol.header.CheckTransactionStateRequestHeader;
import org.apache.rocketmq.logging.InternalLogger;
import org.apache.rocketmq.logging.InternalLoggerFactory;
import org.apache.rocketmq.proxy.common.utils.ProxyUtils;
import org.apache.rocketmq.remoting.common.RemotingUtil;
import org.apache.rocketmq.remoting.exception.RemotingCommandException;
import org.apache.rocketmq.remoting.protocol.RemotingCommand;

public class ProxyClientRemotingProcessor extends ClientRemotingProcessor {
    private static final InternalLogger log = InternalLoggerFactory.getLogger(LoggerName.PROXY_LOGGER_NAME);
    private final ProducerManager producerManager;

    public ProxyClientRemotingProcessor(ProducerManager producerManager) {
        super(null);
        this.producerManager = producerManager;
    }

    @Override
    public RemotingCommand processRequest(ChannelHandlerContext ctx, RemotingCommand request)
        throws RemotingCommandException {
        if (request.getCode() == RequestCode.CHECK_TRANSACTION_STATE) {
            return this.checkTransactionState(ctx, request);
        }
        return null;
    }

    @Override
    public RemotingCommand checkTransactionState(ChannelHandlerContext ctx,
        RemotingCommand request) throws RemotingCommandException {
        final ByteBuffer byteBuffer = ByteBuffer.wrap(request.getBody());
        final MessageExt messageExt = MessageDecoder.decode(byteBuffer, true, false, false);
        if (messageExt != null) {
            final String group = messageExt.getProperty(MessageConst.PROPERTY_PRODUCER_GROUP);
            if (group != null) {
                CheckTransactionStateRequestHeader requestHeader =
                    (CheckTransactionStateRequestHeader) request.decodeCommandCustomHeader(CheckTransactionStateRequestHeader.class);
                request.writeCustomHeader(requestHeader);
                request.addExtField(ProxyUtils.BROKER_ADDR, RemotingUtil.socketAddress2String(ctx.channel().remoteAddress()));
                Channel channel = this.producerManager.getAvailableChannel(group);
                if (channel != null) {
                    channel.writeAndFlush(request);
                } else {
                    log.warn("check transaction failed, channel is empty. groupId={}, requestHeader:{}", group, requestHeader);
                }
            }
        }
        return null;
    }
}
