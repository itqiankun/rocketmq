
package org.apache.rocketmq.controller.impl.event;

/**
 * The parent class of Event, the subclass needs to indicate eventType.
 */
public interface EventMessage {

    /**
     * Returns the event type of this message
     */
    EventType getEventType();
}
