package com.mengchen.springboot.api.domain;


public class ResultInfo<T> extends ResultCode {
    private T data;

    public ResultInfo() {
        this(ResultCode.SUCCESS);
    }

    public ResultInfo(ResultCode resultCode) {
        this(resultCode, (T) null);
    }

    public ResultInfo(T data) {
        this(ResultCode.SUCCESS, data);
    }

    public ResultInfo(ResultCode resultCode, T data) {
        this.setResultCode(resultCode);
        this.data = data;
    }

    public T getData() {
        return this.data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public ResultInfo setResultCode(ResultCode resultCode) {
        this.setCode(resultCode.getCode());
        this.setMessage(resultCode.getMessage());
        return this;
    }

    public ResultInfo setErrorResultCode(Integer code, String message) {
        this.setCode(code);
        this.setMessage(message);
        return this;
    }
}
