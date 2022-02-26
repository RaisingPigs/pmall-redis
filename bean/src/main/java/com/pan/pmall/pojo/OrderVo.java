package com.pan.pmall.pojo;

import com.pan.pmall.entity.Order;
import com.pan.pmall.entity.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @description:
 * @author: Mr.Pan
 * @create: 2022-02-16 17:10
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class OrderVo extends Order {
    /*微信支付或者支付宝支付图片*/
    private String payQrcodeImg;
    /*订单项*/
    private List<OrderItem> orderItems; 
}
