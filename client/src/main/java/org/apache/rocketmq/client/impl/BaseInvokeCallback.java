

package org.apache.rocketmq.client.impl;

import org.apache.rocketmq.remoting.InvokeCallback;
import org.apache.rocketmq.remoting.netty.ResponseFuture;

public abstract class BaseInvokeCallback implements InvokeCallback {
    private final MQClientAPIImpl mqClientAPI;

    public BaseInvokeCallback(MQClientAPIImpl mqClientAPI) {
        this.mqClientAPI = mqClientAPI;
    }

    @Override
    public void operationComplete(final ResponseFuture responseFuture) {
        mqClientAPI.execRpcHooksAfterRequest(responseFuture);
        onComplete(responseFuture);
    }

    public abstract void onComplete(final ResponseFuture responseFuture);
}
