

package org.apache.rocketmq.proxy.grpc.interceptor;

import com.google.common.net.HostAndPort;
import io.grpc.Grpc;
import io.grpc.Metadata;
import io.grpc.ServerCall;
import io.grpc.ServerCallHandler;
import io.grpc.ServerInterceptor;
import java.net.InetSocketAddress;
import java.net.SocketAddress;

public class HeaderInterceptor implements ServerInterceptor {
    @Override
    public <R, W> ServerCall.Listener<R> interceptCall(
        ServerCall<R, W> call,
        Metadata headers,
        ServerCallHandler<R, W> next
    ) {
        SocketAddress remoteSocketAddress = call.getAttributes().get(Grpc.TRANSPORT_ATTR_REMOTE_ADDR);
        String remoteAddress = parseSocketAddress(remoteSocketAddress);
        headers.put(InterceptorConstants.REMOTE_ADDRESS, remoteAddress);

        SocketAddress localSocketAddress = call.getAttributes().get(Grpc.TRANSPORT_ATTR_LOCAL_ADDR);
        String localAddress = parseSocketAddress(localSocketAddress);
        headers.put(InterceptorConstants.LOCAL_ADDRESS, localAddress);
        return next.startCall(call, headers);
    }

    private String parseSocketAddress(SocketAddress socketAddress) {
        if (socketAddress instanceof InetSocketAddress) {
            InetSocketAddress inetSocketAddress = (InetSocketAddress) socketAddress;
            return HostAndPort.fromParts(
                inetSocketAddress.getAddress()
                    .getHostAddress(),
                inetSocketAddress.getPort()
            ).toString();
        }

        return "";
    }
}
