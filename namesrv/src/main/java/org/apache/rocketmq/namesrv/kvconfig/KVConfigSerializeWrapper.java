
package org.apache.rocketmq.namesrv.kvconfig;

import java.util.HashMap;
import org.apache.rocketmq.remoting.protocol.RemotingSerializable;

public class KVConfigSerializeWrapper extends RemotingSerializable {
    private HashMap<String/* Namespace */, HashMap<String/* Key */, String/* Value */>> configTable;

    public HashMap<String, HashMap<String, String>> getConfigTable() {
        return configTable;
    }

    public void setConfigTable(HashMap<String, HashMap<String, String>> configTable) {
        this.configTable = configTable;
    }
}
