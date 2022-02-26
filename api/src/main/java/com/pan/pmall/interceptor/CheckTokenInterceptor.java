package com.pan.pmall.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pan.pmall.vo.ResultVo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @description:
 * @author: Mr.Pan
 * @create: 2022-02-06 18:16
 **/
public class CheckTokenInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        /*处理浏览器预请求*/
        if ("OPTIONS".equals(request.getMethod())) {
            return true;
        }
        
        String token = request.getHeader("Authorization");

        if (token != null && !"null".equals(token)) {
            try {
                JwtParser jwtParser = Jwts.parser();
                /*填写密码才能解析生成的token*/
                jwtParser.setSigningKey("pandemo");

                /*jwtParser.parseClaimsJws(): 解析并验证token, 如果token正确则正常执行, 如果错误则抛出异常*/
                Jws<Claims> claimsJws = jwtParser.parseClaimsJws(token);

                return true;
            } catch (Exception e) {
                /*有异常则说明token有问题, 则用response来发送失败的响应*/
                doResponse(response, ResultVo.unauthorized("登录信息已过时, 请重新登录"));
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
        String json = new ObjectMapper().writeValueAsString(resultVO);

        resp.getWriter().write(json);
    }
}
