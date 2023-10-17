
package org.apache.rocketmq.client.exception;

public class MQRedirectException extends MQBrokerException {
    private static final StackTraceElement[] UNASSIGNED_STACK = new StackTraceElement[0];

    private final byte[] body;

    public MQRedirectException(byte[] responseBody) {
        this.body = responseBody;
    }

    // This exception class is used as a flow control item, so stack trace is useless and performance killer.
    @Override
    public synchronized Throwable fillInStackTrace() {
        this.setStackTrace(UNASSIGNED_STACK);
        return this;
    }

    public byte[] getBody() {
        return body;
    }
}
