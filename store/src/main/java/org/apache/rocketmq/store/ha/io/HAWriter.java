

package org.apache.rocketmq.store.ha.io;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;
import org.apache.rocketmq.common.constant.LoggerName;
import org.apache.rocketmq.logging.InternalLogger;
import org.apache.rocketmq.logging.InternalLoggerFactory;

public class HAWriter {
    private static final InternalLogger LOGGER = InternalLoggerFactory.getLogger(LoggerName.STORE_LOGGER_NAME);
    protected final List<HAWriteHook> writeHookList = new ArrayList<>();

    public boolean write(SocketChannel socketChannel, ByteBuffer byteBufferWrite) throws IOException {
        int writeSizeZeroTimes = 0;
        while (byteBufferWrite.hasRemaining()) {
            int writeSize = socketChannel.write(byteBufferWrite);
            for (HAWriteHook writeHook : writeHookList) {
                writeHook.afterWrite(writeSize);
            }
            if (writeSize > 0) {
                writeSizeZeroTimes = 0;
            } else if (writeSize == 0) {
                if (++writeSizeZeroTimes >= 3) {
                    break;
                }
            } else {
                LOGGER.info("Write socket < 0");
            }
        }

        return !byteBufferWrite.hasRemaining();
    }

    public void registerHook(HAWriteHook writeHook) {
        writeHookList.add(writeHook);
    }

    public void clearHook() {
        writeHookList.clear();
    }
}
