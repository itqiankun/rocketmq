
package org.apache.rocketmq.store;

import com.google.common.base.Preconditions;
import org.apache.rocketmq.common.constant.LoggerName;
import org.apache.rocketmq.logging.InternalLogger;
import org.apache.rocketmq.logging.InternalLoggerFactory;
import org.apache.rocketmq.store.logfile.MappedFile;

import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.nio.ByteBuffer;

import static java.lang.String.format;

public class StoreUtil {
    private static final InternalLogger log = InternalLoggerFactory.getLogger(LoggerName.STORE_LOGGER_NAME);

    public static final long TOTAL_PHYSICAL_MEMORY_SIZE = getTotalPhysicalMemorySize();

    @SuppressWarnings("restriction")
    public static long getTotalPhysicalMemorySize() {
        long physicalTotal = 1024 * 1024 * 1024 * 24L;
        OperatingSystemMXBean osmxb = ManagementFactory.getOperatingSystemMXBean();
        if (osmxb instanceof com.sun.management.OperatingSystemMXBean) {
            physicalTotal = ((com.sun.management.OperatingSystemMXBean) osmxb).getTotalPhysicalMemorySize();
        }

        return physicalTotal;
    }

    public static void fileAppend(MappedFile file, ByteBuffer data) {
        boolean success = file.appendMessage(data);
        if (!success) {
            throw new RuntimeException(format("fileAppend failed for file: %s and data remaining: %d", file, data.remaining()));
        }
    }

    public static FileQueueSnapshot getFileQueueSnapshot(MappedFileQueue mappedFileQueue) {
        return getFileQueueSnapshot(mappedFileQueue, mappedFileQueue.getLastMappedFile().getFileFromOffset());
    }

    public static FileQueueSnapshot getFileQueueSnapshot(MappedFileQueue mappedFileQueue, final long currentFile) {
        try {
            Preconditions.checkNotNull(mappedFileQueue, "file queue shouldn't be null");
            MappedFile firstFile = mappedFileQueue.getFirstMappedFile();
            MappedFile lastFile = mappedFileQueue.getLastMappedFile();
            int mappedFileSize = mappedFileQueue.getMappedFileSize();
            if (firstFile == null || lastFile == null) {
                return new FileQueueSnapshot(firstFile, -1, lastFile, -1, currentFile, -1, 0, false);
            }

            long firstFileIndex = 0;
            long lastFileIndex = (lastFile.getFileFromOffset() - firstFile.getFileFromOffset()) / mappedFileSize;
            long currentFileIndex = (currentFile - firstFile.getFileFromOffset()) / mappedFileSize;
            long behind = (lastFile.getFileFromOffset() - currentFile) / mappedFileSize;
            boolean exist = firstFile.getFileFromOffset() <= currentFile && currentFile <= lastFile.getFileFromOffset();
            return new FileQueueSnapshot(firstFile, firstFileIndex, lastFile, lastFileIndex, currentFile, currentFileIndex, behind, exist);
        } catch (Exception e) {
            log.error("[BUG] get file queue snapshot failed. fileQueue: {}, currentFile: {}", mappedFileQueue, currentFile, e);
        }
        return new FileQueueSnapshot();
    }
}
