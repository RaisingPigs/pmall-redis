package com.pan.pmall.config;

import com.pan.pmall.interceptor.CheckTokenInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @description:
 * @author: Mr.Pan
 * @create: 2022-02-06 18:57
 **/
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new CheckTokenInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/user/**")
                .excludePathPatterns("/index/**")
                .excludePathPatterns("/product/**")
                /*放行swagger相关路径*/
                .excludePathPatterns("/swagger-ui.html/**", "/swagger-resources/**", "/webjars/**", "/v2/**", "/doc.html/**");
    }
}
