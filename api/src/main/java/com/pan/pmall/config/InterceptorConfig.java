package com.pan.pmall.config;

import com.pan.pmall.interceptor.CheckTokenInterceptor;
import com.pan.pmall.interceptor.KeepSessionStateInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

/**
 * @description:
 * @author: Mr.Pan
 * @create: 2022-02-06 18:57
 **/
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    @Resource
    private CheckTokenInterceptor checkTokenInterceptor;
    @Resource
    private KeepSessionStateInterceptor keepSessionStateInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        /*拦截器的生效顺序就是注册的顺序*/
        registry.addInterceptor(keepSessionStateInterceptor)
                .addPathPatterns("/**");

        registry.addInterceptor(checkTokenInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/user/**")
                .excludePathPatterns("/index/**")
                .excludePathPatterns("/product/**")
                /*放行swagger相关路径*/
                .excludePathPatterns("/swagger-ui.html/**", "/swagger-resources/**", "/webjars/**", "/v2/**", "/doc.html/**");
    }
}
