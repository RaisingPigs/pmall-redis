package com.pan.pmall.controller;

import com.pan.pmall.service.PayTypeService;
import com.pan.pmall.vo.ResultVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @description:
 * @author: Mr.Pan
 * @create: 2022-02-16 15:39
 **/
@CrossOrigin
@RestController
@RequestMapping("/pay-type")
@Api(tags = "支付类型管理")
public class PayTypeController {
    @Resource
    private PayTypeService payTypeService;

    @GetMapping("/list")
    @ApiOperation("支付类型获取接口")
    public ResultVo listPayTypes() {
        String str = "这是一个字符串";
        return payTypeService.listPayTypes();
    }
}
