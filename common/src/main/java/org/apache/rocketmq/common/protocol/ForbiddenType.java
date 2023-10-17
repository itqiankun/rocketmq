

package org.apache.rocketmq.common.protocol;

/**
 * 
 * gives the reason for a no permission messaging pulling.
 *
 */
public interface ForbiddenType {

    /**
     * 1=forbidden by broker
     */
    int BROKER_FORBIDDEN               = 1;
    /**
     * 2=forbidden by groupId
     */
    int GROUP_FORBIDDEN                = 2;
    /**
     * 3=forbidden by topic
     */
    int TOPIC_FORBIDDEN                = 3;
    /**
     * 4=forbidden by brocasting mode
     */
    int BROADCASTING_DISABLE_FORBIDDEN = 4;
    /**
     * 5=forbidden for a substription(group with a topic)
     */
    int SUBSCRIPTION_FORBIDDEN         = 5;

}
