

package org.apache.rocketmq.common.sysflag;

import org.apache.rocketmq.common.compression.CompressionType;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CompressionFlagTest {

    @Test
    public void testCompressionFlag() {
        int flag = 0;
        flag |= MessageSysFlag.COMPRESSED_FLAG;

        assertThat(MessageSysFlag.getCompressionType(flag)).isEqualTo(CompressionType.ZLIB);

        flag |= MessageSysFlag.COMPRESSION_LZ4_TYPE;
        assertThat(MessageSysFlag.getCompressionType(flag)).isEqualTo(CompressionType.LZ4);

        flag &= (~MessageSysFlag.COMPRESSION_TYPE_COMPARATOR);
        flag |= MessageSysFlag.COMPRESSION_ZSTD_TYPE;
        assertThat(MessageSysFlag.getCompressionType(flag)).isEqualTo(CompressionType.ZSTD);


        flag &= (~MessageSysFlag.COMPRESSION_TYPE_COMPARATOR);
        flag |= MessageSysFlag.COMPRESSION_ZLIB_TYPE;
        assertThat(MessageSysFlag.getCompressionType(flag)).isEqualTo(CompressionType.ZLIB);
    }

    @Test(expected = RuntimeException.class)
    public void testCompressionFlagNotMatch() {
        int flag = 0;
        flag |= MessageSysFlag.COMPRESSED_FLAG;
        flag |= (0x4 << 8);

        MessageSysFlag.getCompressionType(flag);
    }
}
