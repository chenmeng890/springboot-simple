package com.mengchen.springboot.exception;

import com.mengchen.springboot.api.domain.ResultCode;
import com.mengchen.springboot.api.domain.ResultInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by chenmeng12 on 2017/11/14.
 */
@ControllerAdvice(
        annotations = {Controller.class, RestController.class}
)
public class AdviceExceptionHandler {
    private final Logger logger = LoggerFactory.getLogger(AdviceExceptionHandler.class);

    @Value("${app.ajaxModel:null}")
    private String ajaxModel;

    public AdviceExceptionHandler() {
    }

    @ExceptionHandler
    @Order(2147483647)
    @ResponseBody
    public Object runtimeExceptionHandler(Exception e, HttpServletRequest request, HttpServletResponse response) throws Exception {
        this.logger.error("Global Exception.", e);
        if (!isAjaxRequest(request)) {
            throw e;
        }
        return new ResultInfo(ResultCode.FAIL);
    }

    @ExceptionHandler({BusinessException.class})
    @Order(2147483647)
    public Object businessExceptionHandler(BusinessException e, HttpServletRequest request) {
        this.logger.error("BusinessException, result code is: {}.", e.getResultCode());
        if (!isAjaxRequest(request)) {
            throw e;
        }
        return new ResultInfo(ResultCode.FAIL);
    }


    @ExceptionHandler({MethodArgumentNotValidException.class})
    @Order(-2147483648)
    public Object methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e, HttpServletRequest request) throws MethodArgumentNotValidException {
        this.logger.error("MethodArgumentNotValidException : {}.", e.getMessage());
        if (!isAjaxRequest(request)) {
            throw e;
        }
        String errorMessage = e.getMessage();
        BindingResult bindingResult = e.getBindingResult();
        if (bindingResult.hasFieldErrors()) {
            FieldError ResultInfo = bindingResult.getFieldError();
            errorMessage = ResultInfo.getField() + ResultInfo.getDefaultMessage();
        }
        ResultInfo resultInfo = new ResultInfo(ResultCode.PARAM_EMPTY);
        resultInfo.setData(errorMessage);
        return resultInfo;

    }

    @ExceptionHandler({BindException.class})
    @Order(-2147483648)
    @ResponseBody
    public Object methodArgumentNotValidExceptionHandler(BindException e, HttpServletRequest request) throws BindException {
        this.logger.error("BindException, : {}.", e.getMessage());
        if (!isAjaxRequest(request)) {
            throw e;
        }
        String errorMessage = e.getMessage();
        BindingResult bindingResult = e.getBindingResult();
        if (bindingResult.hasFieldErrors()) {
            FieldError ResultInfo = bindingResult.getFieldError();
            errorMessage = ResultInfo.getField() + ResultInfo.getDefaultMessage();
        }
        ResultInfo resultInfo = new ResultInfo(ResultCode.PARAM_EMPTY);
        resultInfo.setData(errorMessage);
        return resultInfo;

    }

    protected boolean isAjaxRequest(HttpServletRequest request) {
        boolean isAjaxReuest = false;
        if (ajaxModel == null) {
            if (request.getHeader("X-Requested-With") != null && "XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
                isAjaxReuest = true;
            }
        } else {
            isAjaxReuest = request.getHeader("X-Requested-With") != null;
        }

        return isAjaxReuest;
    }

}
