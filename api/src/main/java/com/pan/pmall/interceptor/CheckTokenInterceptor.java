package com.pan.pmall.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pan.pmall.vo.ResultVo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @description:
 * @author: Mr.Pan
 * @create: 2022-02-06 18:16
 **/
@Component
public class CheckTokenInterceptor implements HandlerInterceptor {
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private ObjectMapper objectMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        /*处理浏览器预请求*/
        if ("OPTIONS".equals(request.getMethod())) {
            return true;
        }

        String token = request.getHeader("Authorization");

        if (token != null && !"null".equals(token)) {
            /*token不为空, 则从redis中根据token获取用户*/
            String userJson = stringRedisTemplate.boundValueOps(token).get();
            if (userJson != null) {
                /*能获取到用户, 则放行*/
                return true;
            } else {
                /*获取不到用户, 说明登录过期*/
                doResponse(response, ResultVo.unauthorized("登录已过期, 请重新登录"));
                return false;
            }
        }

        /*验证不通过, 则用response来发送失败的响应*/
        doResponse(response, ResultVo.unauthorized("请先登录"));
        return false;
    }

    /*封装响应方法, 将resultVO以json形式用response响应给前端*/
    private void doResponse(HttpServletResponse resp, ResultVo resultVO) throws IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("utf-8");

        /*将resultVO转为json*/
        String resultVOJson = objectMapper.writeValueAsString(resultVO);

        resp.getWriter().write(resultVOJson);
    }
}
