

package org.apache.rocketmq.store.ha;

import java.nio.channels.SocketChannel;

public interface HAConnection {
    /**
     * Start HA Connection
     */
    void start();

    /**
     * Shutdown HA Connection
     */
    void shutdown();

    /**
     * Close HA Connection
     */
    void close();

    /**
     * Get socket channel
     */
    SocketChannel getSocketChannel();

    /**
     * Get current state for ha connection
     *
     * @return HAConnectionState
     */
    HAConnectionState getCurrentState();

    /**
     * Get client address for ha connection
     *
     * @return client ip address
     */
    String getClientAddress();

    /**
     * Get the transfer rate per second
     *
     *  @return transfer bytes in second
     */
    long getTransferredByteInSecond();

    /**
     * Get the current transfer offset to the slave
     *
     * @return the current transfer offset to the slave
     */
    long getTransferFromWhere();

    /**
     * Get slave ack offset
     *
     * @return slave ack offset
     */
    long getSlaveAckOffset();
}
