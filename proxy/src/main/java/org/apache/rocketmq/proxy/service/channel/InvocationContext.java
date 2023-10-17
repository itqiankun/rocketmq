

package org.apache.rocketmq.proxy.service.channel;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import org.apache.rocketmq.remoting.protocol.RemotingCommand;

public class InvocationContext implements InvocationContextInterface {
    private final CompletableFuture<RemotingCommand> response;
    private final long timestamp = System.currentTimeMillis();

    public InvocationContext(CompletableFuture<RemotingCommand> resp) {
        this.response = resp;
    }

    public boolean expired(long expiredTimeSec) {
        return System.currentTimeMillis() - timestamp >= Duration.ofSeconds(expiredTimeSec).toMillis();
    }

    public CompletableFuture<RemotingCommand> getResponse() {
        return response;
    }

    public void handle(RemotingCommand remotingCommand) {
        response.complete(remotingCommand);
    }
}
