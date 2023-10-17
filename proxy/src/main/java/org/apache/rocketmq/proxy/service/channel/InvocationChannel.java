

package org.apache.rocketmq.proxy.service.channel;

import io.netty.channel.ChannelFuture;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.apache.rocketmq.proxy.config.ConfigurationManager;
import org.apache.rocketmq.remoting.protocol.RemotingCommand;

public class InvocationChannel extends SimpleChannel {
    protected final ConcurrentMap<Integer, InvocationContextInterface> inFlightRequestMap;

    public InvocationChannel(String remoteAddress, String localAddress) {
        super(remoteAddress, localAddress);
        this.inFlightRequestMap = new ConcurrentHashMap<>();
    }

    @Override
    public ChannelFuture writeAndFlush(Object msg) {
        if (msg instanceof RemotingCommand) {
            RemotingCommand responseCommand = (RemotingCommand) msg;
            InvocationContextInterface context = inFlightRequestMap.remove(responseCommand.getOpaque());
            if (null != context) {
                context.handle(responseCommand);
            }
            inFlightRequestMap.remove(responseCommand.getOpaque());
        }
        return super.writeAndFlush(msg);
    }

    @Override
    public boolean isWritable() {
        return inFlightRequestMap.size() > 0;
    }

    @Override
    public void registerInvocationContext(int opaque, InvocationContextInterface context) {
        inFlightRequestMap.put(opaque, context);
    }

    @Override
    public void eraseInvocationContext(int opaque) {
        inFlightRequestMap.remove(opaque);
    }

    @Override
    public void clearExpireContext() {
        Iterator<Map.Entry<Integer, InvocationContextInterface>> iterator = inFlightRequestMap.entrySet().iterator();
        int count = 0;
        while (iterator.hasNext()) {
            Map.Entry<Integer, InvocationContextInterface> entry = iterator.next();
            if (entry.getValue().expired(ConfigurationManager.getProxyConfig().getChannelExpiredInSeconds())) {
                iterator.remove();
                count++;
                log.debug("An expired request is found, request: {}", entry.getValue());
            }
        }
        if (count > 0) {
            log.warn("[BUG] {} expired in-flight requests is cleaned.", count);
        }
    }
}
