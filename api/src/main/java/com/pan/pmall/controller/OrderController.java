package com.pan.pmall.controller;

import com.github.pagehelper.PageInfo;
import com.pan.pmall.entity.OrderItem;
import com.pan.pmall.service.OrderService;
import com.pan.pmall.utils.QueryInfo;
import com.pan.pmall.vo.ResultVo;
import com.sun.corba.se.impl.interceptors.ORBInitInfoImpl;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: Mr.Pan
 * @create: 2022-02-15 09:21
 **/
@CrossOrigin
@RestController
@RequestMapping("/order")
@Api(tags = "订单管理")
public class OrderController {
    @Resource
    private OrderService orderService;

    @PostMapping("/add")
    public ResultVo addOrder(@RequestBody Map<String, Object> reqMap) {
        /*将收到的cartIds字符串转为list集合*/
        String cartIdsStr = (String) reqMap.get("cartIds");
        List<String> cartIdList = Arrays.asList(cartIdsStr.split(","));

        Integer payType = (Integer) reqMap.get("payType");
        String userAddrId = (String) reqMap.get("userAddrId");
        String orderRemark = (String) reqMap.get("orderRemark");
        String userId = (String) reqMap.get("userId");

        try {
            return orderService.addOrder(cartIdList, payType, userAddrId, orderRemark, userId);
        } catch (SQLException e) {
            return ResultVo.failed("生成订单失败");
        }
    }

    @GetMapping("/{orderId}")
    public ResultVo getOrderByOrderId(@PathVariable String orderId) {
        return orderService.getOrder(orderId);
    }

    @PutMapping("/{orderId}")
    public ResultVo payOrder(@PathVariable String orderId) {

        return orderService.payOrder(orderId);
    }

    @GetMapping("/order-num/{status}")
    public ResultVo getOrderNumByStatus(@PathVariable Integer status) {
        return orderService.getOrderNumByStatus(status);
    }

    @GetMapping("/{userId}/{status}")
    public ResultVo getOrdersWithItem(@PathVariable String userId,
                                      @PathVariable Integer status,
                                      QueryInfo queryInfo) {
        if (status == 0) {
            status = null;
        }
        return orderService.listOrdersWithItem(userId, status, queryInfo);
    }

    @PutMapping("/{orderId}/{status}")
    public ResultVo updateOrderStatus(@PathVariable String orderId,
                             @PathVariable Integer status,
                             @RequestParam(value = "closeType", required = false) Integer closeType) {

        return orderService.updateOrderStatus(orderId, status, closeType);
    }

    @GetMapping("/orderItem/{orderItemId}")
    public ResultVo getOrderItemById(@PathVariable String orderItemId) {
        return orderService.getOrderItemById(orderItemId);
    }
}
