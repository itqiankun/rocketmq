

package org.apache.rocketmq.proxy;

public enum ProxyMode {
    LOCAL("LOCAL"),
    CLUSTER("CLUSTER");

    private final String mode;

    ProxyMode(String mode) {
        this.mode = mode;
    }

    public static boolean isClusterMode(String mode) {
        if (mode == null) {
            return false;
        }
        return CLUSTER.mode.equals(mode.toUpperCase());
    }

    public static boolean isClusterMode(ProxyMode mode) {
        if (mode == null) {
            return false;
        }
        return CLUSTER.equals(mode);
    }

    public static boolean isLocalMode(String mode) {
        if (mode == null) {
            return false;
        }
        return LOCAL.mode.equals(mode.toUpperCase());
    }

    public static boolean isLocalMode(ProxyMode mode) {
        if (mode == null) {
            return false;
        }
        return LOCAL.equals(mode);
    }
}
