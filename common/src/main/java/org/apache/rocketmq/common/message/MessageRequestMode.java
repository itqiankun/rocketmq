
package org.apache.rocketmq.common.message;

/**
 * Message Request Mode
 */
public enum MessageRequestMode {

    /**
     * pull
     */
    PULL("PULL"),

    /**
     * pop, consumer working in pop mode could share MessageQueue
     */
    POP("POP");

    private String name;

    MessageRequestMode(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
