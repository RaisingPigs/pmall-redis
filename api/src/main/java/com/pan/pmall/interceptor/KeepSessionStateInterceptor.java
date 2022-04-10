package com.pan.pmall.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.SecureRandom;
import java.security.cert.TrustAnchor;
import java.util.concurrent.TimeUnit;

/**
 * @description:
 * @author: Mr.Pan
 * @create: 2022-03-05 11:23
 **/
@Component
public class KeepSessionStateInterceptor implements HandlerInterceptor {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String token = request.getHeader("Authorization");

        if (token != null && !"null".equals(token)) {
            /*token不为空, 则从redis中根据token获取用户*/
            String userJson = stringRedisTemplate.boundValueOps(token).get();
            if (userJson != null) {
                /*能获取到用户, 则重设超时时间为30min*/
                stringRedisTemplate.boundValueOps(token).expire(30, TimeUnit.MINUTES);
            }
        }

        return true;
    }
}
