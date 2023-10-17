
package org.apache.rocketmq.tools.command.namesrv;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.PosixParser;
import org.apache.rocketmq.srvutil.ServerUtil;
import org.apache.rocketmq.tools.command.SubCommandException;
import org.apache.rocketmq.tools.command.server.NameServerMocker;
import org.apache.rocketmq.tools.command.server.ServerResponseMocker;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

public class AddWritePermSubCommandTest {

    private static final int NAME_SERVER_PORT = 45677;

    private static final int BROKER_PORT = 45676;

    private ServerResponseMocker brokerMocker;

    private ServerResponseMocker nameServerMocker;

    @Before
    public void before() {
        brokerMocker = startOneBroker();
        nameServerMocker = startNameServer();
    }

    @After
    public void after() {
        brokerMocker.shutdown();
        nameServerMocker.shutdown();
    }

    @Test
    public void testExecute() throws SubCommandException {
        AddWritePermSubCommand cmd = new AddWritePermSubCommand();
        Options options = ServerUtil.buildCommandlineOptions(new Options());
        String[] subargs = new String[]{"-b default-broker"};
        final CommandLine commandLine =
                ServerUtil.parseCmdLine("mqadmin " + cmd.commandName(), subargs, cmd.buildCommandlineOptions(options), new PosixParser());
        cmd.execute(commandLine, options, null);
    }

    private ServerResponseMocker startNameServer() {
        HashMap<String, String> extMap = new HashMap<>();
        extMap.put("addTopicCount", "1");
        // start name server
        return NameServerMocker.startByDefaultConf(NAME_SERVER_PORT, BROKER_PORT, extMap);
    }

    private ServerResponseMocker startOneBroker() {
        // start broker
        return ServerResponseMocker.startServer(BROKER_PORT, null);
    }
}
