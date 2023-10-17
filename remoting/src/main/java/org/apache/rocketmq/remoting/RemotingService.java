

package org.apache.rocketmq.remoting;

public interface RemotingService {
    void start();

    void shutdown();

    void registerRPCHook(RPCHook rpcHook);

    /**
     * Remove all rpc hooks.
     */
    void clearRPCHook();
}
