

package org.apache.rocketmq.proxy.service.channel;

import org.apache.rocketmq.remoting.protocol.RemotingCommand;

public interface InvocationContextInterface {
    void handle(RemotingCommand remotingCommand);

    boolean expired(long expiredTimeSec);
}
