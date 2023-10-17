
package org.apache.rocketmq.tools.admin.common;

public class AdminToolResult<T> {

    private boolean success;
    private int code;
    private String errorMsg;
    private T data;

    public AdminToolResult(boolean success, int code, String errorMsg, T data) {
        this.success = success;
        this.code = code;
        this.errorMsg = errorMsg;
        this.data = data;
    }

    public static AdminToolResult success(Object data) {
        return new AdminToolResult(true, AdminToolsResultCodeEnum.SUCCESS.getCode(), "success", data);
    }

    public static AdminToolResult failure(AdminToolsResultCodeEnum errorCodeEnum, String errorMsg) {
        return new AdminToolResult(false, errorCodeEnum.getCode(), errorMsg, null);
    }

    public static AdminToolResult failure(AdminToolsResultCodeEnum errorCodeEnum, String errorMsg, Object data) {
        return new AdminToolResult(false, errorCodeEnum.getCode(), errorMsg, data);
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
