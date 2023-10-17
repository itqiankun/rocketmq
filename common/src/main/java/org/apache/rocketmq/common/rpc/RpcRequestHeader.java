
package org.apache.rocketmq.common.rpc;

import org.apache.rocketmq.remoting.CommandCustomHeader;

public abstract class RpcRequestHeader implements CommandCustomHeader {
    //the namespace name
    protected String ns;
    //if the data has been namespaced
    protected Boolean nsd;
    //the abstract remote addr name, usually the physical broker name
    protected String bname;
    //oneway
    protected Boolean oway;

    public String getBname() {
        return bname;
    }

    public void setBname(String bname) {
        this.bname = bname;
    }

    public Boolean getOway() {
        return oway;
    }

    public void setOway(Boolean oway) {
        this.oway = oway;
    }

    public String getNs() {
        return ns;
    }

    public void setNs(String ns) {
        this.ns = ns;
    }

    public Boolean getNsd() {
        return nsd;
    }

    public void setNsd(Boolean nsd) {
        this.nsd = nsd;
    }
}
