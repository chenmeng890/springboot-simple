package com.mengchen.springboot.domain;

/**
 * Created by chenmeng12 on 2017/11/14.
 */
public class ResultCode {
    public static final ResultCode SUCCESS = new ResultCode(Integer.valueOf(200), "Success");
    public static final ResultCode FAIL = new ResultCode(Integer.valueOf(201), "Fail");
    public static final ResultCode PARAM_EMPTY = new ResultCode(Integer.valueOf(202), "Param is empty");
    public static final ResultCode NOT_LOGIN = new ResultCode(Integer.valueOf(203), "Not login");
    public static final ResultCode JSF_ERROR = new ResultCode(Integer.valueOf(204), "Fail");
    private Integer code;
    private String message;

    public ResultCode() {
    }

    public ResultCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return this.code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String toString() {
        return "ResultCode{code=" + this.code + ", message=\'" + this.message + '\'' + '}';
    }
}