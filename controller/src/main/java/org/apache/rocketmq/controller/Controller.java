

package org.apache.rocketmq.controller;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import org.apache.rocketmq.common.protocol.body.SyncStateSet;
import org.apache.rocketmq.common.protocol.header.namesrv.controller.AlterSyncStateSetRequestHeader;
import org.apache.rocketmq.common.protocol.header.namesrv.controller.CleanControllerBrokerDataRequestHeader;
import org.apache.rocketmq.common.protocol.header.namesrv.controller.ElectMasterRequestHeader;
import org.apache.rocketmq.common.protocol.header.namesrv.controller.GetReplicaInfoRequestHeader;
import org.apache.rocketmq.common.protocol.header.namesrv.controller.RegisterBrokerToControllerRequestHeader;
import org.apache.rocketmq.remoting.RemotingServer;
import org.apache.rocketmq.remoting.protocol.RemotingCommand;

/**
 * The api for controller
 */
public interface Controller {

    /**
     * Startup controller
     */
    void startup();

    /**
     * Shutdown controller
     */
    void shutdown();

    /**
     * Start scheduling controller events, this function only will be triggered when the controller becomes leader.
     */
    void startScheduling();

    /**
     * Stop scheduling controller events, this function only will be triggered when the controller lose leadership.
     */
    void stopScheduling();

    /**
     * Whether this controller is in leader state.
     */
    boolean isLeaderState();

    /**
     * Alter SyncStateSet of broker replicas.
     *
     * @param request AlterSyncStateSetRequestHeader
     * @return RemotingCommand(AlterSyncStateSetResponseHeader)
     */
    CompletableFuture<RemotingCommand> alterSyncStateSet(
        final AlterSyncStateSetRequestHeader request, final SyncStateSet syncStateSet);

    /**
     * Elect new master for a broker.
     *
     * @param request ElectMasterRequest
     * @return RemotingCommand(ElectMasterResponseHeader)
     */
    CompletableFuture<RemotingCommand> electMaster(final ElectMasterRequestHeader request);

    /**
     * Register api when a replicas of a broker startup.
     *
     * @param request RegisterBrokerRequest
     * @return RemotingCommand(RegisterBrokerResponseHeader)
     */
    CompletableFuture<RemotingCommand> registerBroker(final RegisterBrokerToControllerRequestHeader request);

    /**
     * Get the Replica Info for a target broker.
     *
     * @param request GetRouteInfoRequest
     * @return RemotingCommand(GetReplicaInfoResponseHeader)
     */
    CompletableFuture<RemotingCommand> getReplicaInfo(final GetReplicaInfoRequestHeader request);

    /**
     * Get Metadata of controller
     *
     * @return RemotingCommand(GetControllerMetadataResponseHeader)
     */
    RemotingCommand getControllerMetadata();

    /**
     * Get inSyncStateData for target brokers, this api is used for admin tools.
     */
    CompletableFuture<RemotingCommand> getSyncStateData(final List<String> brokerNames);

    /**
     * Get the remotingServer used by the controller, the upper layer will reuse this remotingServer.
     */
    RemotingServer getRemotingServer();

    /**
     * Clean controller broker data
     *
     */
    CompletableFuture<RemotingCommand> cleanBrokerData(final CleanControllerBrokerDataRequestHeader requestHeader);
}
