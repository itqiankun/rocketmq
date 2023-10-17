

package org.apache.rocketmq.common.utils;

import org.apache.rocketmq.common.UtilAll;
import org.apache.rocketmq.remoting.common.RemotingHelper;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.lang.reflect.Method;
import java.util.List;

public class IOTinyUtilsTest {

    /**
     * https://bazel.build/reference/test-encyclopedia#filesystem
     */
    private String testRootDir = System.getProperty("java.io.tmpdir") + File.separator + "iotinyutilstest";

    @Before
    public void init() {
        File dir = new File(testRootDir);
        if (dir.exists()) {
            UtilAll.deleteFile(dir);
        }

        dir.mkdirs();
    }

    @After
    public void destroy() {
        File file = new File(testRootDir);
        UtilAll.deleteFile(file);
    }

    @Test
    public void testToString() throws Exception {
        byte[] b = "testToString".getBytes(RemotingHelper.DEFAULT_CHARSET);
        InputStream is = new ByteArrayInputStream(b);

        String str = IOTinyUtils.toString(is, null);
        assertEquals("testToString", str);

        is = new ByteArrayInputStream(b);
        str = IOTinyUtils.toString(is, RemotingHelper.DEFAULT_CHARSET);
        assertEquals("testToString", str);

        is = new ByteArrayInputStream(b);
        Reader isr = new InputStreamReader(is, RemotingHelper.DEFAULT_CHARSET);
        str = IOTinyUtils.toString(isr);
        assertEquals("testToString", str);
    }


    @Test
    public void testCopy() throws Exception {
        char[] arr = "testToString".toCharArray();
        Reader reader = new CharArrayReader(arr);
        Writer writer = new CharArrayWriter();

        long count = IOTinyUtils.copy(reader, writer);
        assertEquals(arr.length, count);
    }

    @Test
    public void testReadLines() throws Exception {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            sb.append("testReadLines").append("\n");
        }

        StringReader reader = new StringReader(sb.toString());
        List<String> lines = IOTinyUtils.readLines(reader);

        assertEquals(10, lines.size());
    }

    @Test
    public void testToBufferedReader() throws Exception {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            sb.append("testToBufferedReader").append("\n");
        }

        StringReader reader = new StringReader(sb.toString());
        Method method = IOTinyUtils.class.getDeclaredMethod("toBufferedReader", new Class[]{Reader.class});
        method.setAccessible(true);
        Object bReader = method.invoke(IOTinyUtils.class, reader);

        assertTrue(bReader instanceof BufferedReader);
    }

    @Test
    public void testWriteStringToFile() throws Exception {
        File file = new File(testRootDir, "testWriteStringToFile");
        assertTrue(!file.exists());

        IOTinyUtils.writeStringToFile(file, "testWriteStringToFile", RemotingHelper.DEFAULT_CHARSET);

        assertTrue(file.exists());
    }

    @Test
    public void testCleanDirectory() throws Exception {
        for (int i = 0; i < 10; i++) {
            IOTinyUtils.writeStringToFile(new File(testRootDir, "testCleanDirectory" + i), "testCleanDirectory", RemotingHelper.DEFAULT_CHARSET);
        }

        File dir = new File(testRootDir);
        assertTrue(dir.exists() && dir.isDirectory());
        assertTrue(dir.listFiles().length > 0);

        IOTinyUtils.cleanDirectory(new File(testRootDir));

        assertTrue(dir.listFiles().length == 0);
    }

    @Test
    public void testDelete() throws Exception {
        for (int i = 0; i < 10; i++) {
            IOTinyUtils.writeStringToFile(new File(testRootDir, "testDelete" + i), "testCleanDirectory", RemotingHelper.DEFAULT_CHARSET);
        }

        File dir = new File(testRootDir);
        assertTrue(dir.exists() && dir.isDirectory());
        assertTrue(dir.listFiles().length > 0);

        IOTinyUtils.delete(new File(testRootDir));

        assertTrue(!dir.exists());
    }

    @Test
    public void testCopyFile() throws Exception {
        File source = new File(testRootDir, "source");
        String target = testRootDir + File.separator + "dest";

        IOTinyUtils.writeStringToFile(source, "testCopyFile", RemotingHelper.DEFAULT_CHARSET);

        IOTinyUtils.copyFile(source.getCanonicalPath(), target);

        File dest = new File(target);
        assertTrue(dest.exists());
    }
}
