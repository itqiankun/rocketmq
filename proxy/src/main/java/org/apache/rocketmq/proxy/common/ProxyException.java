
package org.apache.rocketmq.proxy.common;

public class ProxyException extends RuntimeException {

    private final ProxyExceptionCode code;

    public ProxyException(ProxyExceptionCode code, String message) {
        super(message);
        this.code = code;
    }

    public ProxyException(ProxyExceptionCode code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public ProxyExceptionCode getCode() {
        return code;
    }
}
