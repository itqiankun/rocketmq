
package org.apache.rocketmq.common.protocol.route;

public enum MessageQueueRouteState {
    // do not change below order, since ordinal() is used
    Expired,
    ReadOnly,
    Normal,
    WriteOnly,
    ;
}
