

package org.apache.rocketmq.broker.processor;

import org.apache.rocketmq.broker.mqtrace.SendMessageContext;
import org.apache.rocketmq.remoting.protocol.RemotingCommand;

public interface SendMessageCallback {
    /**
     * On send complete.
     *
     * @param ctx send context
     * @param response send response
     */
    void onComplete(SendMessageContext ctx, RemotingCommand response);
}
