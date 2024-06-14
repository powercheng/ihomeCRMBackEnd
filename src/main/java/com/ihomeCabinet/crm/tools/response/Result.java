package com.ihomeCabinet.crm.tools.response;

import lombok.Builder;
import lombok.Data;

@Data
public class Result {

    /**
     * 状态码
     */
    private Integer code;

    /**
     * 提示信息
     */
    private String  msg;

    /**
     * 数据记录
     */
    private Object data;

    public Result() {
    }

    public Result(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Result(Integer code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }
    public static Result ok(Object data) {
        return succ(200, "操作成功", data);
    }
    public static Result ok() {
        return succ(200, "success", null);
    }
    public static Result fail(String msg) {
        return fail(400, msg, null);
    }

    public static Result fail(Integer code, String msg) {
        return fail(code, msg, null);
    }
    public static Result succ (int code, String msg, Object data) {
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        result.setData(data);
        return result;
    }
    public static Result fail (int code, String msg, Object data) {
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        result.setData(data);
        return result;
    }
}
