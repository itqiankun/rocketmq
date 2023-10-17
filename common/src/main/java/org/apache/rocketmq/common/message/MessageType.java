

package org.apache.rocketmq.common.message;

public enum MessageType {
    Normal_Msg("Normal"),
    Order_Msg("Order"),
    Trans_Msg_Half("Trans"),
    Trans_msg_Commit("TransCommit"),
    Delay_Msg("Delay");

    private final String shortName;

    MessageType(String shortName) {
        this.shortName = shortName;
    }

    public String getShortName() {
        return shortName;
    }

    public static MessageType getByShortName(String shortName) {
        for (MessageType msgType : MessageType.values()) {
            if (msgType.getShortName().equals(shortName)) {
                return msgType;
            }
        }
        return Normal_Msg;
    }
}
