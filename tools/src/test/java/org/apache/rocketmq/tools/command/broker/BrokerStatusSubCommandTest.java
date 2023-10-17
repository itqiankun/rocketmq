
package org.apache.rocketmq.tools.command.broker;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.PosixParser;
import org.apache.rocketmq.common.protocol.body.BrokerStatsData;
import org.apache.rocketmq.common.protocol.body.BrokerStatsItem;
import org.apache.rocketmq.srvutil.ServerUtil;
import org.apache.rocketmq.tools.command.SubCommandException;
import org.apache.rocketmq.tools.command.server.ServerResponseMocker;
import org.junit.Test;

public class BrokerStatusSubCommandTest extends ServerResponseMocker {

    private static final int PORT = 45678;

    @Override
    protected int getPort() {
        return PORT;
    }

    @Override
    protected byte[] getBody() {
        BrokerStatsData brokerStatsData = new BrokerStatsData();
        BrokerStatsItem item = new BrokerStatsItem();
        brokerStatsData.setStatsDay(item);
        return brokerStatsData.encode();
    }

    @Test
    public void testExecute() throws SubCommandException {
        BrokerStatusSubCommand cmd = new BrokerStatusSubCommand();
        Options options = ServerUtil.buildCommandlineOptions(new Options());
        String[] subargs = new String[] {"-b 127.0.0.1:" + PORT, "-c default-cluster"};
        final CommandLine commandLine =
            ServerUtil.parseCmdLine("mqadmin " + cmd.commandName(), subargs, cmd.buildCommandlineOptions(options), new PosixParser());

        cmd.execute(commandLine, options, null);
    }


}
