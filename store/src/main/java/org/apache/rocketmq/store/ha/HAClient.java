

package org.apache.rocketmq.store.ha;

public interface HAClient {

    /**
     * Start HAClient
     */
    void start();

    /**
     * Shutdown HAClient
     */
    void shutdown();

    /**
     * Wakeup HAClient
     */
    void wakeup();

    /**
     * Update master address
     *
     * @param newAddress
     */
    void updateMasterAddress(String newAddress);

    /**
     * Update master ha address
     *
     * @param newAddress
     */
    void updateHaMasterAddress(String newAddress);

    /**
     * Get master address
     *
     * @return master address
     */
    String getMasterAddress();

    /**
     * Get master ha address
     *
     * @return master ha address
     */
    String getHaMasterAddress();

    /**
     * Get HAClient last read timestamp
     *
     * @return last read timestamp
     */
    long getLastReadTimestamp();

    /**
     * Get HAClient last write timestamp
     *
     * @return last write timestamp
     */
    long getLastWriteTimestamp();

    /**
     * Get current state for ha connection
     *
     * @return HAConnectionState
     */
    HAConnectionState getCurrentState();

    /**
     * Change the current state for ha connection for testing
     *
     * @param haConnectionState
     */
    void changeCurrentState(HAConnectionState haConnectionState);

    /**
     * Disconnecting from the master for testing
     */
    void closeMaster();

    /**
     * Get the transfer rate per second
     *
     *  @return transfer bytes in second
     */
    long getTransferredByteInSecond();
}
