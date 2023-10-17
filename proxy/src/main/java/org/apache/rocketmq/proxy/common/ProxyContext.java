

package org.apache.rocketmq.proxy.common;

import java.util.HashMap;
import java.util.Map;

public class ProxyContext {
    public static final String INNER_ACTION_PREFIX = "Inner";
    private final Map<String, Object> value = new HashMap<>();

    public static ProxyContext create() {
        return new ProxyContext();
    }

    public static ProxyContext createForInner(String actionName) {
        return create().setAction(INNER_ACTION_PREFIX + actionName);
    }

    public static ProxyContext createForInner(Class<?> clazz) {
        return createForInner(clazz.getSimpleName());
    }

    public Map<String, Object> getValue() {
        return this.value;
    }

    public ProxyContext withVal(String key, Object val) {
        this.value.put(key, val);
        return this;
    }

    public <T> T getVal(String key) {
        return (T) this.value.get(key);
    }

    public ProxyContext setLocalAddress(String localAddress) {
        this.withVal(ContextVariable.LOCAL_ADDRESS, localAddress);
        return this;
    }

    public String getLocalAddress() {
        return this.getVal(ContextVariable.LOCAL_ADDRESS);
    }

    public ProxyContext setRemoteAddress(String remoteAddress) {
        this.withVal(ContextVariable.REMOTE_ADDRESS, remoteAddress);
        return this;
    }

    public String getRemoteAddress() {
        return this.getVal(ContextVariable.REMOTE_ADDRESS);
    }

    public ProxyContext setClientID(String clientID) {
        this.withVal(ContextVariable.CLIENT_ID, clientID);
        return this;
    }

    public String getClientID() {
        return this.getVal(ContextVariable.CLIENT_ID);
    }

    public ProxyContext setLanguage(String language) {
        this.withVal(ContextVariable.LANGUAGE, language);
        return this;
    }

    public String getLanguage() {
        return this.getVal(ContextVariable.LANGUAGE);
    }

    public ProxyContext setClientVersion(String clientVersion) {
        this.withVal(ContextVariable.CLIENT_VERSION, clientVersion);
        return this;
    }

    public String getClientVersion() {
        return this.getVal(ContextVariable.CLIENT_VERSION);
    }

    public ProxyContext setRemainingMs(Long remainingMs) {
        this.withVal(ContextVariable.REMAINING_MS, remainingMs);
        return this;
    }

    public Long getRemainingMs() {
        return this.getVal(ContextVariable.REMAINING_MS);
    }

    public ProxyContext setAction(String action) {
        this.withVal(ContextVariable.ACTION, action);
        return this;
    }

    public String getAction() {
        return this.getVal(ContextVariable.ACTION);
    }

}
