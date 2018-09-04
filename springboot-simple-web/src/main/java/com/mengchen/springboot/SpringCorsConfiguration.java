package com.mengchen.springboot;

import com.mengchen.springboot.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenmeng12 on 2017/11/24.
 */
@Configuration
public class SpringCorsConfiguration {
    @Value("#{\'${spring.cors.pathPatterns:}\'.split(\',\') ?: null}")
    private List<String> pathPatterns;
    @Value("#{'${spring.cors.allowedOrigin:}'.split(',') ?: null}")
    private List<String> allowedOrigin;
    @Value("#{\'${spring.cors.allowedHeaders:}\'.split(\',\') ?: null}")
    private List<String> allowedHeaders;
    @Value("#{\'${spring.cors.allowedMethods:}\'.split(\',\') ?: null}")
    private List<String> allowedMethods;
    @Value("${spring.cors.maxAge:3600}")
    private Long maxAge;
    @Value("${spring.cors.allowedCredentials:true}")
    private Boolean allowedCredentials;

    private CorsConfiguration buildCorsConfiguration() {
        if (CollectionUtils.trimCollection(this.allowedHeaders) == null) {
            this.allowedHeaders = new ArrayList<>();
        }

        if (CollectionUtils.trimCollection(this.allowedMethods) == null) {
            this.allowedMethods = new ArrayList<>();
        }

        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedOrigins(this.allowedOrigin); // 1 设置访问源地址
        for (String allowedHeader : this.allowedHeaders) {
            corsConfiguration.addAllowedHeader(allowedHeader.trim()); // 2 设置访问源请求头
        }
        for (String allowedMethod : this.allowedMethods) {
            corsConfiguration.addAllowedMethod(allowedMethod.trim()); // 3 设置访问源请求方法
        }
        corsConfiguration.setMaxAge(maxAge);
        corsConfiguration.setAllowCredentials(allowedCredentials);
        return corsConfiguration;
    }

    @Bean
    public CorsFilter corsFilter() {
        if (CollectionUtils.trimCollection(this.pathPatterns) == null) {
            this.pathPatterns = new ArrayList<>();
        }

        CorsConfiguration corsConfiguration = buildCorsConfiguration();
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        for (String pathPattern : this.pathPatterns) {
            source.registerCorsConfiguration(pathPattern.trim(), corsConfiguration); // 4 对接口配置跨域设置
        }

        return new CorsFilter(source);
    }
}
