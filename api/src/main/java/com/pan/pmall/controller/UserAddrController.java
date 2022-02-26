package com.pan.pmall.controller;

import com.pan.pmall.service.UserAddrService;
import com.pan.pmall.vo.ResultVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @description:
 * @author: Mr.Pan
 * @create: 2022-02-14 09:42
 **/
@CrossOrigin
@RestController
@RequestMapping("/user-addr")
@Api(tags = "收货地址管理")
public class UserAddrController {
    @Resource
    private UserAddrService userAddrService;

    @GetMapping("/{userId}")
    @ApiOperation("用户收货地址获取接口")
    public ResultVo listUserAddrs(@PathVariable String userId) {
        return userAddrService.listUserAddrByUserId(userId);
    }
    
    
}
