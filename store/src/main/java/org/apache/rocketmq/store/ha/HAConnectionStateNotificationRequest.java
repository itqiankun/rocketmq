

package org.apache.rocketmq.store.ha;

import java.util.concurrent.CompletableFuture;

public class HAConnectionStateNotificationRequest {
    private final CompletableFuture<Boolean> requestFuture = new CompletableFuture<>();
    private final HAConnectionState expectState;
    private final String remoteAddr;
    private final boolean notifyWhenShutdown;

    public HAConnectionStateNotificationRequest(HAConnectionState expectState, String remoteAddr, boolean notifyWhenShutdown) {
        this.expectState = expectState;
        this.remoteAddr = remoteAddr;
        this.notifyWhenShutdown = notifyWhenShutdown;
    }

    public CompletableFuture<Boolean> getRequestFuture() {
        return requestFuture;
    }

    public String getRemoteAddr() {
        return remoteAddr;
    }

    public boolean isNotifyWhenShutdown() {
        return notifyWhenShutdown;
    }

    public HAConnectionState getExpectState() {
        return expectState;
    }
}
