
package org.apache.rocketmq.proxy.grpc.v2.common;

import apache.rocketmq.v2.Code;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.rocketmq.proxy.common.ProxyException;
import org.apache.rocketmq.proxy.common.ProxyExceptionCode;

public class GrpcProxyException extends RuntimeException {

    private ProxyException proxyException;
    private Code code;

    protected static final Map<ProxyExceptionCode, Code> CODE_MAPPING = new ConcurrentHashMap<>();

    static {
        CODE_MAPPING.put(ProxyExceptionCode.INVALID_BROKER_NAME, Code.BAD_REQUEST);
        CODE_MAPPING.put(ProxyExceptionCode.INVALID_RECEIPT_HANDLE, Code.INVALID_RECEIPT_HANDLE);
        CODE_MAPPING.put(ProxyExceptionCode.FORBIDDEN, Code.FORBIDDEN);
        CODE_MAPPING.put(ProxyExceptionCode.INTERNAL_SERVER_ERROR, Code.INTERNAL_SERVER_ERROR);
        CODE_MAPPING.put(ProxyExceptionCode.MESSAGE_PROPERTY_CONFLICT_WITH_TYPE, Code.MESSAGE_PROPERTY_CONFLICT_WITH_TYPE);
    }

    public GrpcProxyException(Code code, String message) {
        super(message);
        this.code = code;
    }

    public GrpcProxyException(Code code, String message, Throwable t) {
        super(message, t);
        this.code = code;
    }

    public GrpcProxyException(ProxyException proxyException) {
        super(proxyException);
        this.proxyException = proxyException;
    }

    public Code getCode() {
        if (this.code != null) {
            return this.code;
        }
        if (this.proxyException != null) {
            return CODE_MAPPING.getOrDefault(this.proxyException.getCode(), Code.INTERNAL_SERVER_ERROR);
        }
        return Code.INTERNAL_SERVER_ERROR;
    }

    public ProxyException getProxyException() {
        return proxyException;
    }
}
