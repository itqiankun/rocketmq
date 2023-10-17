
package org.apache.rocketmq.client.consumer;

public interface AckCallback {
    void onSuccess(final AckResult ackResult);

    void onException(final Throwable e);
}
