package com.pan.pmall.controller;

import com.pan.pmall.entity.User;
import com.pan.pmall.service.UserService;
import com.pan.pmall.vo.ResultVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;

/**
 * @description: 用户控制器
 * @author: Mr.Pan
 * @create: 2022-01-28 19:38
 **/
@RestController
@RequestMapping("/user")
@CrossOrigin
@Api(value = "提供用户登录与注册的接口", tags = "用户管理")
@Slf4j
public class UserController {
    @Resource
    private UserService userService;

    @PostMapping("/login")
    @ApiOperation("用户登录接口")
    public ResultVo login(@RequestBody User user) {
        try {
            return userService.checkLogin(user.getUsername(), user.getPassword());
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResultVo.failed("登录失败");
        }
    }

    @PostMapping("/regist")
    @ApiOperation("用户注册接口")
    public ResultVo regist(@RequestBody User user) {
        return userService.checkRegist(user.getUsername(), user.getPassword());
    }
}
