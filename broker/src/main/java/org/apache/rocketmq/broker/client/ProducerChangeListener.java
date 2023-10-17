
package org.apache.rocketmq.broker.client;

/**
 * producer manager will call this listener when something happen
 * <p>
 * event type: {@link ProducerGroupEvent}
 */
public interface ProducerChangeListener {

    void handle(ProducerGroupEvent event, String group, ClientChannelInfo clientChannelInfo);
}
