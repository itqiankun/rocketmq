
package org.apache.rocketmq.common.rpc;

public class RpcRequest {
    int code;
    private RpcRequestHeader header;
    private Object body;

    public RpcRequest(int code, RpcRequestHeader header, Object body) {
        this.code = code;
        this.header = header;
        this.body = body;
    }

    public RpcRequestHeader getHeader() {
        return header;
    }

    public Object getBody() {
        return body;
    }

    public int getCode() {
        return code;
    }
}
