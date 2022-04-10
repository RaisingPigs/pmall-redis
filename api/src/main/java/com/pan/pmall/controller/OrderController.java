package com.pan.pmall.controller;

import com.github.pagehelper.PageInfo;
import com.pan.pmall.entity.OrderItem;
import com.pan.pmall.pojo.ShoppingCartVo;
import com.pan.pmall.service.OrderService;
import com.pan.pmall.service.ShoppingCartService;
import com.pan.pmall.utils.QueryInfo;
import com.pan.pmall.vo.ResultVo;
import com.sun.corba.se.impl.interceptors.ORBInitInfoImpl;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.logging.LogException;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @description:
 * @author: Mr.Pan
 * @create: 2022-02-15 09:21
 **/
@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/order")
@Api(tags = "订单管理")
public class OrderController {
    @Resource
    private OrderService orderService;
    @Resource
    private RedissonClient redissonClient;
    @Resource
    private ShoppingCartService shoppingCartService;

    @PostMapping("/add")
    public ResultVo addOrder(@RequestBody Map<String, Object> reqMap) throws Exception {
        /*将收到的cartIds字符串转为list集合*/
        String cartIdsStr = (String) reqMap.get("cartIds");
        List<String> cartIdList = Arrays.asList(cartIdsStr.split(","));

        Integer payType = (Integer) reqMap.get("payType");
        String userAddrId = (String) reqMap.get("userAddrId");
        String orderRemark = (String) reqMap.get("orderRemark");
        String userId = (String) reqMap.get("userId");

        /*从数据库查询购物车信息*/
        List<ShoppingCartVo> shoppingCarts = shoppingCartService.listShoppingCartByCartIdList(cartIdList);

        /*实现分布式锁*/
        /*遍历订单中的skuid集合, 以skuid为key, 执行setnx*/
        boolean isLock = true; //用来保存是否所有skuid都添加成功
        //用来保存添加成功的skuid
        List<String> skuIdList = new ArrayList<>(shoppingCarts.size());

        try {
            for (ShoppingCartVo shoppingCart : shoppingCarts) {
                String skuId = shoppingCart.getSkuId();
                /*获取锁*/
                RLock lock = redissonClient.getLock(skuId);
                /*尝试锁住, 加不上锁最多等待3秒，上锁以后10秒自动解锁(看门狗可以续命)*/
                boolean tryLock = lock.tryLock(3, 10, TimeUnit.SECONDS);
                if (tryLock) {
                    //锁住则将skuId和lock都保存下来
                    skuIdList.add(skuId);
                } else {
                    //没锁住则将isLock置为false
                    isLock = false;
                }
            }
            /*如果isLock为true, 则表示加锁成功*/
            if (isLock) {
                String orderId = orderService.addOrder(cartIdList, payType, userAddrId, orderRemark, userId);
                return ResultVo.created("创建订单成功").add("orderId", orderId);
            } else {
                /*加锁失败则返回*/
                return ResultVo.failed("创建订单失败");
            }
        } catch (SQLException e) {
            log.error("", e);
            return ResultVo.failed("生成订单失败");
        } finally {
            /*无论是异常, 还是程序执行结束, 还是库存不足, 都应该释放redis实现的分布式锁*/
            for (String skuId : skuIdList) {
                RLock lock = redissonClient.getLock(skuId);
                if (lock != null && lock.isHeldByCurrentThread()) {
                    lock.unlock();
                }
            }
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
