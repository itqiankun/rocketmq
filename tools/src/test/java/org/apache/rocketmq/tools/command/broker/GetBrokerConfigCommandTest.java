
package org.apache.rocketmq.tools.command.broker;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.PosixParser;
import org.apache.rocketmq.common.MixAll;
import org.apache.rocketmq.srvutil.ServerUtil;
import org.apache.rocketmq.tools.command.SubCommandException;
import org.apache.rocketmq.tools.command.server.ServerResponseMocker;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.util.Properties;


public class GetBrokerConfigCommandTest extends ServerResponseMocker {

    private static final int PORT = 45678;

    @Override
    protected int getPort() {
        return PORT;
    }

    @Override
    protected byte[] getBody() {
        StringBuilder sb = new StringBuilder();
        Properties properties = new Properties();
        properties.setProperty("stat", "123");
        properties.setProperty("ip", "192.168.1.1");
        properties.setProperty("broker_name", "broker_101");
        sb.append(MixAll.properties2String(properties));
        try {
            return sb.toString().getBytes(MixAll.DEFAULT_CHARSET);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testExecute() throws SubCommandException {
        GetBrokerConfigCommand cmd = new GetBrokerConfigCommand();
        Options options = ServerUtil.buildCommandlineOptions(new Options());
        String[] subargs = new String[] {"-b 127.0.0.1:" + PORT, "-c default-cluster"};
        final CommandLine commandLine =
            ServerUtil.parseCmdLine("mqadmin " + cmd.commandName(), subargs, cmd.buildCommandlineOptions(options), new PosixParser());
        cmd.execute(commandLine, options, null);
    }
}
