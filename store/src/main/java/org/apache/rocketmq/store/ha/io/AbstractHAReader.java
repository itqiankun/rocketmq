

package org.apache.rocketmq.store.ha.io;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;
import org.apache.rocketmq.common.constant.LoggerName;
import org.apache.rocketmq.logging.InternalLogger;
import org.apache.rocketmq.logging.InternalLoggerFactory;

public abstract class AbstractHAReader {
    private static final InternalLogger LOGGER = InternalLoggerFactory.getLogger(LoggerName.STORE_LOGGER_NAME);
    protected final List<HAReadHook> readHookList = new ArrayList<>();

    public boolean read(SocketChannel socketChannel, ByteBuffer byteBufferRead) {
        int readSizeZeroTimes = 0;
        while (byteBufferRead.hasRemaining()) {
            try {
                int readSize = socketChannel.read(byteBufferRead);
                for (HAReadHook readHook : readHookList) {
                    readHook.afterRead(readSize);
                }
                if (readSize > 0) {
                    readSizeZeroTimes = 0;
                    boolean result = processReadResult(byteBufferRead);
                    if (!result) {
                        LOGGER.error("Process read result failed");
                        return false;
                    }
                } else if (readSize == 0) {
                    if (++readSizeZeroTimes >= 3) {
                        break;
                    }
                } else {
                    LOGGER.info("Read socket < 0");
                    return false;
                }
            } catch (IOException e) {
                LOGGER.info("Read socket exception", e);
                return false;
            }
        }

        return true;
    }

    public void registerHook(HAReadHook readHook) {
        readHookList.add(readHook);
    }

    public void clearHook() {
        readHookList.clear();
    }

    /**
     * Process read result.
     *
     * @param byteBufferRead read result
     * @return true if process succeed, false otherwise
     */
    protected abstract boolean processReadResult(ByteBuffer byteBufferRead);
}
