

/**
 * $Id: MappedFileTest.java 1831 2013-05-16 01:39:51Z vintagewang@apache.org $
 */
package org.apache.rocketmq.store;

import java.io.File;
import java.io.IOException;
import org.apache.rocketmq.common.UtilAll;
import org.apache.rocketmq.store.logfile.DefaultMappedFile;
import org.junit.After;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class MappedFileTest {
    private final String storeMessage = "Once, there was a chance for me!";

    @Test
    public void testSelectMappedBuffer() throws IOException {
        DefaultMappedFile mappedFile = new DefaultMappedFile("target/unit_test_store/MappedFileTest/000", 1024 * 64);
        boolean result = mappedFile.appendMessage(storeMessage.getBytes());
        assertThat(result).isTrue();

        SelectMappedBufferResult selectMappedBufferResult = mappedFile.selectMappedBuffer(0);
        byte[] data = new byte[storeMessage.length()];
        selectMappedBufferResult.getByteBuffer().get(data);
        String readString = new String(data);

        assertThat(readString).isEqualTo(storeMessage);

        mappedFile.shutdown(1000);
        assertThat(mappedFile.isAvailable()).isFalse();
        selectMappedBufferResult.release();
        assertThat(mappedFile.isCleanupOver()).isTrue();
        assertThat(mappedFile.destroy(1000)).isTrue();
    }

    @After
    public void destroy() {
        File file = new File("target/unit_test_store");
        UtilAll.deleteFile(file);
    }
}
