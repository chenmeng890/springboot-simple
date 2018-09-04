package com.mengchen.springboot.exception;


import com.mengchen.springboot.domain.ResultCode;

/**
 * Created by chenmeng12 on 2017/11/14.
 */
public class BusinessException extends RuntimeException{
    public ResultCode resultCode;

    public BusinessException() {
        this.resultCode = ResultCode.FAIL;
    }

    public BusinessException(Integer code, String message) {
        ResultCode resultCode = new ResultCode();
        resultCode.setCode(code);
        resultCode.setMessage(message);
        this.resultCode = resultCode;
    }

    public BusinessException(ResultCode resultCode) {
        this.resultCode = resultCode;
    }

    public ResultCode getResultCode() {
        return this.resultCode;
    }

    public void setResultCode(ResultCode resultCode) {
        this.resultCode = resultCode;
    }
}
