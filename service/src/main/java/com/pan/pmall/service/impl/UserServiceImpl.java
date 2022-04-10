package com.pan.pmall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pan.pmall.dao.UserMapper;
import com.pan.pmall.entity.User;
import com.pan.pmall.service.UserService;
import com.pan.pmall.utils.MD5Utils;
import com.pan.pmall.vo.ResultVo;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @description:
 * @author: Mr.Pan
 * @create: 2022-01-28 19:36
 **/
@Service
/*spring默认是单例的, 可以不写*/
//@Scope("singleton") 
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private ObjectMapper objectMapper;

    @Override
    @Transactional
    /*注册做了两步: 1.检验用户名是否被占用 2.保存用户. 所以要加事务*/
    public ResultVo checkRegist(String username, String password) {
        synchronized (this) {
            QueryWrapper<User> wrapper = new QueryWrapper<>();
            wrapper.lambda().eq(User::getUsername, username);
            User user = userMapper.selectOne(wrapper);

            /*检验用户名是否被占用*/
            if (user == null) {
                /*密码加密*/
                user = new User();
                user.setUsername(username);
                user.setPassword(MD5Utils.md5(password));
                Date date = new Date();
                user.setUserRegtime(date);
                user.setUserModtime(date);
                /*因为用户头像不能为空, 所以给个默认头像*/
                user.setUserImg("img/default.png");
                int insertRes = userMapper.insert(user);
                if (insertRes > 0) {
                    return ResultVo.created("注册成功").add("user", user);
                } else {
                    return ResultVo.failed("服务器内部错误");
                }
            } else {
                return ResultVo.failed("该用户名已经被使用");
            }
        }
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public ResultVo checkLogin(String username, String password) throws Exception {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(User::getUsername, username);
        User user = userMapper.selectOne(wrapper);

        if (user != null) {
            if (user.getPassword().equals(MD5Utils.md5(password))) {
                /*生成token*/
                JwtBuilder jwtBuilder = Jwts.builder();

                /*map中可以放入角色权限信息*/
                Map<String, Object> map = new HashMap<>();
                map.put("权限级别", "1级");
                map.put("用户信息", "用户1");

                /*jwtBuilder用来生成token
                 * setSubject(): 设置主题, 就是token中携带的数据
                 * setIssuedAt(): 设置token的生成时间
                 * setId(): 设用户id 为token的id
                 * setClaims(): 放入map, map中可以放入角色权限信息
                 * setExpiration(): token过期时间
                 *      当前时间戳 + x小时*60分钟*60秒*1000: 过期时间为x小时
                 * signWith(): 设置jwt密码
                 *      SignatureAlgorithm.HS256: 加密方式
                 *      "pandemo": 密码*/
                String token = jwtBuilder.setSubject(username)
                        .setIssuedAt(new Date())
                        .setId(user.getUserId().toString())
                        .setClaims(map)
                        .signWith(SignatureAlgorithm.HS256, "pandemo")
                        .compact();

                stringRedisTemplate.boundValueOps(token).set(objectMapper.writeValueAsString(user), 30, TimeUnit.MINUTES);

                return ResultVo.success("登录成功").add("user", user).add("token", token);
            } else {
                return ResultVo.failed("用户名或密码错误");
            }
        } else {
            return ResultVo.failed("用户不存在");
        }
    }
}
