package com.mengchen.springboot.converter;

import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.mengchen.springboot.domain.ResultInfo;
import com.mengchen.springboot.util.CollectionUtils;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenmeng12 on 2017/11/14.
 */
public class FastJsonResultInfoHttpMessageConverter extends FastJsonHttpMessageConverter {
    private List<String> unAutoResultInfoPaths;

    public FastJsonResultInfoHttpMessageConverter(List<String> unAutoResultInfoPaths) {
        if (CollectionUtils.trimCollection(unAutoResultInfoPaths) == null)
            this.unAutoResultInfoPaths = new ArrayList<>();
        this.unAutoResultInfoPaths = unAutoResultInfoPaths;
    }

    @Override
    protected void writeInternal(Object obj, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
        if (obj != null && obj.getClass().isAssignableFrom(ResultInfo.class)) {
            super.writeInternal(obj, outputMessage);
            return;
        }
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

        String requestURI = request.getRequestURI();
        for (String unAutoResultInfoPath : unAutoResultInfoPaths) {
            if (requestURI.startsWith(unAutoResultInfoPath.trim())) {
                super.writeInternal(obj, outputMessage);
                return;
            }
        }
        super.writeInternal(new ResultInfo(obj), outputMessage);
    }
}
