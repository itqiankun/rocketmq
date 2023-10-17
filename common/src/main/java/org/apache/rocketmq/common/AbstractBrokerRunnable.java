

package org.apache.rocketmq.common;

import org.apache.rocketmq.logging.InnerLoggerFactory;

public abstract class AbstractBrokerRunnable implements Runnable {
    protected final BrokerIdentity brokerIdentity;

    public AbstractBrokerRunnable(BrokerIdentity brokerIdentity) {
        this.brokerIdentity = brokerIdentity;
    }

    /**
     * real logic for running
     */
    public abstract void run2();

    @Override
    public void run() {
        if (brokerIdentity.isInBrokerContainer()) {
            // set threadlocal broker identity to forward logging to corresponding broker
            InnerLoggerFactory.BROKER_IDENTITY.set(brokerIdentity.getCanonicalName());
        }
        run2();
    }
}
