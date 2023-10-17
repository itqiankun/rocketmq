
package org.apache.rocketmq.tools.admin.common;

public enum AdminToolsResultCodeEnum {

    /**
     *
     */
    SUCCESS(200),

    REMOTING_ERROR(-1001),
    MQ_BROKER_ERROR(-1002),
    MQ_CLIENT_ERROR(-1003),
    INTERRUPT_ERROR(-1004),

    TOPIC_ROUTE_INFO_NOT_EXIST(-2001),
    CONSUMER_NOT_ONLINE(-2002);

    private int code;

    AdminToolsResultCodeEnum(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
