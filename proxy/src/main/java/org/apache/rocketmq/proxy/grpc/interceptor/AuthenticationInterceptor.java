

package org.apache.rocketmq.proxy.grpc.interceptor;

import com.google.protobuf.GeneratedMessageV3;
import io.grpc.Context;
import io.grpc.ForwardingServerCallListener;
import io.grpc.Metadata;
import io.grpc.ServerCall;
import io.grpc.ServerCallHandler;
import io.grpc.ServerInterceptor;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import java.util.List;
import org.apache.rocketmq.acl.AccessResource;
import org.apache.rocketmq.acl.AccessValidator;
import org.apache.rocketmq.acl.common.AclException;
import org.apache.rocketmq.acl.common.AuthenticationHeader;
import org.apache.rocketmq.acl.plain.PlainAccessResource;
import org.apache.rocketmq.proxy.config.ConfigurationManager;

public class AuthenticationInterceptor implements ServerInterceptor {
    protected final List<AccessValidator> accessValidatorList;

    public AuthenticationInterceptor(List<AccessValidator> accessValidatorList) {
        this.accessValidatorList = accessValidatorList;
    }

    @Override
    public <R, W> ServerCall.Listener<R> interceptCall(ServerCall<R, W> call, Metadata headers,
        ServerCallHandler<R, W> next) {
        return new ForwardingServerCallListener.SimpleForwardingServerCallListener<R>(next.startCall(call, headers)) {
            @Override
            public void onMessage(R message) {
                GeneratedMessageV3 messageV3 = (GeneratedMessageV3) message;
                headers.put(InterceptorConstants.RPC_NAME, messageV3.getDescriptorForType().getFullName());
                if (ConfigurationManager.getProxyConfig().isEnableACL()) {
                    try {
                        AuthenticationHeader authenticationHeader = AuthenticationHeader.builder()
                            .remoteAddress(InterceptorConstants.METADATA.get(Context.current()).get(InterceptorConstants.REMOTE_ADDRESS))
                            .namespace(InterceptorConstants.METADATA.get(Context.current()).get(InterceptorConstants.NAMESPACE_ID))
                            .authorization(InterceptorConstants.METADATA.get(Context.current()).get(InterceptorConstants.AUTHORIZATION))
                            .datetime(InterceptorConstants.METADATA.get(Context.current()).get(InterceptorConstants.DATE_TIME))
                            .sessionToken(InterceptorConstants.METADATA.get(Context.current()).get(InterceptorConstants.SESSION_TOKEN))
                            .requestId(InterceptorConstants.METADATA.get(Context.current()).get(InterceptorConstants.REQUEST_ID))
                            .language(InterceptorConstants.METADATA.get(Context.current()).get(InterceptorConstants.LANGUAGE))
                            .clientVersion(InterceptorConstants.METADATA.get(Context.current()).get(InterceptorConstants.CLIENT_VERSION))
                            .protocol(InterceptorConstants.METADATA.get(Context.current()).get(InterceptorConstants.PROTOCOL_VERSION))
                            .requestCode(RequestMapping.map(messageV3.getDescriptorForType().getFullName()))
                            .build();

                        validate(authenticationHeader, headers, messageV3);
                        super.onMessage(message);
                    } catch (AclException aclException) {
                        throw new StatusRuntimeException(Status.PERMISSION_DENIED, headers);
                    }
                } else {
                    super.onMessage(message);
                }
            }
        };
    }

    protected void validate(AuthenticationHeader authenticationHeader, Metadata headers, GeneratedMessageV3 messageV3) {
        for (AccessValidator accessValidator : accessValidatorList) {
            AccessResource accessResource = accessValidator.parse(messageV3, authenticationHeader);
            accessValidator.validate(accessResource);

            if (accessResource instanceof PlainAccessResource) {
                PlainAccessResource plainAccessResource = (PlainAccessResource) accessResource;
                headers.put(InterceptorConstants.AUTHORIZATION_AK, plainAccessResource.getAccessKey());
            }
        }
    }
}
