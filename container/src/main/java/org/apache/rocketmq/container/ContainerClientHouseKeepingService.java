

package org.apache.rocketmq.container;

import io.netty.channel.Channel;
import java.util.Collection;
import org.apache.rocketmq.broker.BrokerController;
import org.apache.rocketmq.remoting.ChannelEventListener;

public class ContainerClientHouseKeepingService implements ChannelEventListener {
    private final IBrokerContainer brokerContainer;

    public ContainerClientHouseKeepingService(final IBrokerContainer brokerContainer) {
        this.brokerContainer = brokerContainer;
    }

    @Override
    public void onChannelConnect(String remoteAddr, Channel channel) {
        onChannelOperation(CallbackCode.CONNECT, remoteAddr, channel);
    }

    @Override
    public void onChannelClose(String remoteAddr, Channel channel) {
        onChannelOperation(CallbackCode.CLOSE, remoteAddr, channel);
    }

    @Override
    public void onChannelException(String remoteAddr, Channel channel) {
        onChannelOperation(CallbackCode.EXCEPTION, remoteAddr, channel);
    }

    @Override
    public void onChannelIdle(String remoteAddr, Channel channel) {
        onChannelOperation(CallbackCode.IDLE, remoteAddr, channel);
    }

    private void onChannelOperation(CallbackCode callbackCode, String remoteAddr, Channel channel) {
        Collection<InnerBrokerController> masterBrokers = this.brokerContainer.getMasterBrokers();
        Collection<InnerSalveBrokerController> slaveBrokers = this.brokerContainer.getSlaveBrokers();

        for (BrokerController masterBroker : masterBrokers) {
            brokerOperation(masterBroker, callbackCode, remoteAddr, channel);
        }

        for (InnerSalveBrokerController slaveBroker : slaveBrokers) {
            brokerOperation(slaveBroker, callbackCode, remoteAddr, channel);
        }
    }

    private void brokerOperation(BrokerController brokerController, CallbackCode callbackCode, String remoteAddr,
        Channel channel) {
        if (callbackCode == CallbackCode.CONNECT) {
            brokerController.getBrokerStatsManager().incChannelConnectNum();
            return;
        }
        boolean removed = brokerController.getProducerManager().doChannelCloseEvent(remoteAddr, channel);
        removed &= brokerController.getConsumerManager().doChannelCloseEvent(remoteAddr, channel);
        if (removed) {
            switch (callbackCode) {
                case CLOSE:
                    brokerController.getBrokerStatsManager().incChannelCloseNum();
                    break;
                case EXCEPTION:
                    brokerController.getBrokerStatsManager().incChannelExceptionNum();
                    break;
                case IDLE:
                    brokerController.getBrokerStatsManager().incChannelIdleNum();
                    break;
                default:
                    break;
            }
        }
    }

    public enum CallbackCode {
        /**
         * onChannelConnect
         */
        CONNECT,
        /**
         * onChannelClose
         */
        CLOSE,
        /**
         * onChannelException
         */
        EXCEPTION,
        /**
         * onChannelIdle
         */
        IDLE
    }
}
