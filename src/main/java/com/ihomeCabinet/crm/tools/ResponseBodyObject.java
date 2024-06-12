package com.ihomeCabinet.crm.tools;

import org.springframework.web.bind.annotation.ResponseBody;

public class ResponseBodyObject<T> {
    Integer code;
    T data;
    String msg;

    public ResponseBodyObject(Integer code, T t, Object data, String msg) {
        this.code = code;
        this.msg = msg;
        this.data = t;
    }

    public ResponseBodyObject(Integer code) {
        this.code = code;
    }

    public ResponseBodyObject(Integer code, T t) {
        this.code = code;
        this.data = t;
    }

    public ResponseBodyObject(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
