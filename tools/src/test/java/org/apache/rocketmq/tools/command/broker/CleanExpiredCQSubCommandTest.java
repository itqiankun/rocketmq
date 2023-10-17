
package org.apache.rocketmq.tools.command.broker;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.PosixParser;
import org.apache.rocketmq.srvutil.ServerUtil;
import org.apache.rocketmq.tools.command.SubCommandException;
import org.apache.rocketmq.tools.command.server.ServerResponseMocker;
import org.junit.Test;

public class CleanExpiredCQSubCommandTest extends ServerResponseMocker {

    private static final int PORT = 45678;

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
        CleanExpiredCQSubCommand cmd = new CleanExpiredCQSubCommand();
        Options options = ServerUtil.buildCommandlineOptions(new Options());
        String[] subargs = new String[] {"-b 127.0.0.1:" + PORT, "-c default-cluster"};
        final CommandLine commandLine =
            ServerUtil.parseCmdLine("mqadmin " + cmd.commandName(), subargs, cmd.buildCommandlineOptions(options), new PosixParser());
        cmd.execute(commandLine, options, null);
    }
}
