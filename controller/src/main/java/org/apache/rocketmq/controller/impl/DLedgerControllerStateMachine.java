
package org.apache.rocketmq.controller.impl;

import io.openmessaging.storage.dledger.entry.DLedgerEntry;
import io.openmessaging.storage.dledger.snapshot.SnapshotReader;
import io.openmessaging.storage.dledger.snapshot.SnapshotWriter;
import io.openmessaging.storage.dledger.statemachine.CommittedEntryIterator;
import io.openmessaging.storage.dledger.statemachine.StateMachine;
import java.util.concurrent.CompletableFuture;
import org.apache.rocketmq.common.constant.LoggerName;
import org.apache.rocketmq.controller.impl.event.EventMessage;
import org.apache.rocketmq.controller.impl.event.EventSerializer;
import org.apache.rocketmq.controller.impl.manager.ReplicasInfoManager;
import org.apache.rocketmq.logging.InternalLogger;
import org.apache.rocketmq.logging.InternalLoggerFactory;

/**
 * The state machine implementation of the dledger controller
 */
public class DLedgerControllerStateMachine implements StateMachine {
    private static final InternalLogger log = InternalLoggerFactory.getLogger(LoggerName.CONTROLLER_LOGGER_NAME);
    private final ReplicasInfoManager replicasInfoManager;
    private final EventSerializer eventSerializer;
    private final String dLedgerId;

    public DLedgerControllerStateMachine(final ReplicasInfoManager replicasInfoManager,
        final EventSerializer eventSerializer, final String dLedgerId) {
        this.replicasInfoManager = replicasInfoManager;
        this.eventSerializer = eventSerializer;
        this.dLedgerId = dLedgerId;
    }

    @Override
    public void onApply(CommittedEntryIterator iterator) {
        int applyingSize = 0;
        while (iterator.hasNext()) {
            final DLedgerEntry entry = iterator.next();
            final byte[] body = entry.getBody();
            if (body != null && body.length > 0) {
                final EventMessage event = this.eventSerializer.deserialize(body);
                this.replicasInfoManager.applyEvent(event);
            }
            applyingSize++;
        }
        log.info("Apply {} events on controller {}", applyingSize, this.dLedgerId);
    }

    @Override
    public void onSnapshotSave(SnapshotWriter writer, CompletableFuture<Boolean> future) {
    }

    @Override
    public boolean onSnapshotLoad(SnapshotReader reader) {
        return false;
    }


    @Override
    public void onShutdown() {
    }

    @Override
    public String getBindDLedgerId() {
        return dLedgerId;
    }
}
