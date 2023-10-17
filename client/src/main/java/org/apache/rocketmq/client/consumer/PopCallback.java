
package org.apache.rocketmq.client.consumer;

/**
 * Async message pop interface
 */
public interface PopCallback {
    void onSuccess(final PopResult popResult);

    void onException(final Throwable e);
}
