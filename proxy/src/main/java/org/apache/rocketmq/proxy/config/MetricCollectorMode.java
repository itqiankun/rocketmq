
package org.apache.rocketmq.proxy.config;

public enum MetricCollectorMode {
    /**
     * Do not collect the metric from clients.
     */
    OFF("off"),
    /**
     * Collect the metric from clients to the given address.
     */
    ON("on"),
    /**
     * Collect the metric by the proxy itself.
     */
    PROXY("proxy");

    private final String modeString;

    MetricCollectorMode(String modeString) {
        this.modeString = modeString;
    }

    public String getModeString() {
        return modeString;
    }

    public static MetricCollectorMode getEnumByString(String modeString) {
        for (MetricCollectorMode mode : MetricCollectorMode.values()) {
            if (mode.modeString.equals(modeString.toLowerCase())) {
                return mode;
            }
        }
        return OFF;
    }
}
