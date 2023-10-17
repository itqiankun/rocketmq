
package org.apache.rocketmq.controller.impl.event;

import java.util.ArrayList;
import java.util.List;
import org.apache.rocketmq.common.protocol.ResponseCode;

public class ControllerResult<T> {
    private final List<EventMessage> events;
    private final T response;
    private byte[] body;
    private int responseCode = ResponseCode.SUCCESS;
    private String remark;

    public ControllerResult() {
        this(null);
    }

    public ControllerResult(T response) {
        this.events = new ArrayList<>();
        this.response = response;
    }

    public ControllerResult(List<EventMessage> events, T response) {
        this.events = new ArrayList<>(events);
        this.response = response;
    }

    public static <T> ControllerResult<T> of(List<EventMessage> events, T response) {
        return new ControllerResult<>(events, response);
    }

    public List<EventMessage> getEvents() {
        return new ArrayList<>(events);
    }

    public T getResponse() {
        return response;
    }

    public byte[] getBody() {
        return body;
    }

    public void setBody(byte[] body) {
        this.body = body;
    }

    public void setCodeAndRemark(int responseCode, String remark) {
        this.responseCode = responseCode;
        this.remark = remark;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public String getRemark() {
        return remark;
    }

    public void addEvent(EventMessage event) {
        this.events.add(event);
    }

    @Override
    public String toString() {
        return "ControllerResult{" +
            "events=" + events +
            ", response=" + response +
            '}';
    }
}
