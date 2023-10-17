
package org.apache.rocketmq.proxy.service.message;

import java.util.HashMap;
import org.apache.rocketmq.remoting.CommandCustomHeader;
import org.apache.rocketmq.remoting.exception.RemotingCommandException;
import org.apache.rocketmq.remoting.protocol.RemotingCommand;

public class LocalRemotingCommand extends RemotingCommand {

    public static LocalRemotingCommand createRequestCommand(int code, CommandCustomHeader customHeader) {
        LocalRemotingCommand cmd = new LocalRemotingCommand();
        cmd.setCode(code);
        cmd.writeCustomHeader(customHeader);
        cmd.setExtFields(new HashMap<>());
        setCmdVersion(cmd);
        return cmd;
    }

    @Override
    public CommandCustomHeader decodeCommandCustomHeader(
        Class<? extends CommandCustomHeader> classHeader) throws RemotingCommandException {
        return classHeader.cast(readCustomHeader());
    }
}
