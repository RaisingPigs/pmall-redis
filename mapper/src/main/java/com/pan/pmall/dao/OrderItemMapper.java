package com.pan.pmall.dao;

import com.pan.pmall.entity.OrderItem;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author Mr.Pan
* @description 针对表【order_item(订单项/快照)】的数据库操作Mapper
* @createDate 2022-02-05 12:40:33
* @Entity com.pan.pmall.entity.OrderItem
*/
public interface OrderItemMapper extends BaseMapper<OrderItem> {
    List<OrderItem> selectOrderItemsByOrderId(@Param("orderId") String orderId);
}




