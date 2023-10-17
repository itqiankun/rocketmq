
package org.apache.rocketmq.tools.command.broker;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.PosixParser;
import org.apache.rocketmq.srvutil.ServerUtil;
import org.apache.rocketmq.tools.command.SubCommandException;
import org.apache.rocketmq.tools.command.server.ServerResponseMocker;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class DeleteExpiredCommitLogSubCommandTest extends ServerResponseMocker {

    private static final int PORT = 45678;

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;

    @Before
    public void setUp() throws Exception {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @After
    public void tearDown() throws Exception {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }

    @Override
    protected int getPort() {
        return PORT;
    }

    @Override
    protected byte[] getBody() {
        return null;
    }

    @Test
    public void testExecute() throws SubCommandException {
        DeleteExpiredCommitLogSubCommand cmd = new DeleteExpiredCommitLogSubCommand();
        Options options = ServerUtil.buildCommandlineOptions(new Options());
        String[] subargs = new String[] {"-b 127.0.0.1:" + PORT, "-c default-cluster"};
        final CommandLine commandLine = ServerUtil.parseCmdLine("mqadmin " + cmd.commandName(), subargs,
            cmd.buildCommandlineOptions(options), new PosixParser());
        cmd.execute(commandLine, options, null);
        Assert.assertTrue(outContent.toString().startsWith("success"));
        Assert.assertEquals("", errContent.toString());
    }
}
