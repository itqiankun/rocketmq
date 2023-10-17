

package org.apache.rocketmq.acl.common;

import com.google.common.base.MoreObjects;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;

public class AuthorizationHeader {
    private static final String HEADER_SEPARATOR = " ";
    private static final String CREDENTIALS_SEPARATOR = "/";
    private static final int AUTH_HEADER_KV_LENGTH = 2;
    private static final String CREDENTIAL = "Credential";
    private static final String SIGNED_HEADERS = "SignedHeaders";
    private static final String SIGNATURE = "Signature";
    private String method;
    private String accessKey;
    private String[] signedHeaders;
    private String signature;

    /**
     * Parse authorization from gRPC header.
     *
     * @param header gRPC header string.
     * @throws Exception exception.
     */
    public AuthorizationHeader(String header) throws DecoderException {
        String[] result = header.split(HEADER_SEPARATOR, 2);
        if (result.length != 2) {
            throw new DecoderException("authorization header is incorrect");
        }
        this.method = result[0];
        String[] keyValues = result[1].split(",");
        for (String keyValue : keyValues) {
            String[] kv = keyValue.trim().split("=", 2);
            int kvLength = kv.length;
            if (kv.length != AUTH_HEADER_KV_LENGTH) {
                throw new DecoderException("authorization keyValues length is incorrect, actual length=" + kvLength);
            }
            String authItem = kv[0];
            if (CREDENTIAL.equals(authItem)) {
                String[] credential = kv[1].split(CREDENTIALS_SEPARATOR);
                int credentialActualLength = credential.length;
                if (credentialActualLength == 0) {
                    throw new DecoderException("authorization credential length is incorrect, actual length=" + credentialActualLength);
                }
                this.accessKey = credential[0];
                continue;
            }
            if (SIGNED_HEADERS.equals(authItem)) {
                this.signedHeaders = kv[1].split(";");
                continue;
            }
            if (SIGNATURE.equals(authItem)) {
                this.signature = this.hexToBase64(kv[1]);
            }
        }
    }

    public String hexToBase64(String input) throws DecoderException {
        byte[] bytes = Hex.decodeHex(input);
        return Base64.encodeBase64String(bytes);
    }

    public String getMethod() {
        return this.method;
    }

    public String getAccessKey() {
        return this.accessKey;
    }

    public String[] getSignedHeaders() {
        return this.signedHeaders;
    }

    public String getSignature() {
        return this.signature;
    }

    public void setMethod(final String method) {
        this.method = method;
    }

    public void setAccessKey(final String accessKey) {
        this.accessKey = accessKey;
    }

    public void setSignedHeaders(final String[] signedHeaders) {
        this.signedHeaders = signedHeaders;
    }

    public void setSignature(final String signature) {
        this.signature = signature;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
            .add("method", method)
            .add("accessKey", accessKey)
            .add("signedHeaders", signedHeaders)
            .add("signature", signature)
            .toString();
    }
}
