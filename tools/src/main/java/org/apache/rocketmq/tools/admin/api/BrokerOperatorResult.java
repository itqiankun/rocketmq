
package org.apache.rocketmq.tools.admin.api;

import java.util.List;

public class BrokerOperatorResult {

    private List<String> successList;

    private List<String> failureList;

    public List<String> getSuccessList() {
        return successList;
    }

    public void setSuccessList(List<String> successList) {
        this.successList = successList;
    }

    public List<String> getFailureList() {
        return failureList;
    }

    public void setFailureList(List<String> failureList) {
        this.failureList = failureList;
    }

    @Override
    public String toString() {
        return "BrokerOperatorResult{" +
            "successList=" + successList +
            ", failureList=" + failureList +
            '}';
    }
}
