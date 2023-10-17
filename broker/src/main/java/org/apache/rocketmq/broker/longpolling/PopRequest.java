
package org.apache.rocketmq.broker.longpolling;

import java.util.Comparator;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.rocketmq.remoting.protocol.RemotingCommand;

import io.netty.channel.Channel;

public class PopRequest {
    private static final AtomicLong COUNTER = new AtomicLong(Long.MIN_VALUE);

    private RemotingCommand remotingCommand;
    private Channel channel;
    private long expired;
    private AtomicBoolean complete = new AtomicBoolean(false);
    private final long op = COUNTER.getAndIncrement();

    public PopRequest(RemotingCommand remotingCommand, Channel channel, long expired) {
        this.channel = channel;
        this.remotingCommand = remotingCommand;
        this.expired = expired;
    }

    public Channel getChannel() {
        return channel;
    }

    public RemotingCommand getRemotingCommand() {
        return remotingCommand;
    }

    public boolean isTimeout() {
        return System.currentTimeMillis() > (expired - 50);
    }

    public boolean complete() {
        return complete.compareAndSet(false, true);
    }

    public long getExpired() {
        return expired;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("PopRequest{");
        sb.append("cmd=").append(remotingCommand);
        sb.append(", channel=").append(channel);
        sb.append(", expired=").append(expired);
        sb.append(", complete=").append(complete);
        sb.append(", op=").append(op);
        sb.append('}');
        return sb.toString();
    }

    public static final Comparator<PopRequest> COMPARATOR = new Comparator<PopRequest>() {
        @Override
        public int compare(PopRequest o1, PopRequest o2) {
            int ret = (int) (o1.getExpired() - o2.getExpired());

            if (ret != 0) {
                return ret;
            }
            ret = (int) (o1.op - o2.op);
            if (ret != 0) {
                return ret;
            }
            return -1;
        }
    };
}
