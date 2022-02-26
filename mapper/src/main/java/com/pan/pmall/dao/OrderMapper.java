package com.pan.pmall.dao;

import com.pan.pmall.entity.Order;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pan.pmall.pojo.OrderVo;
import org.apache.ibatis.annotations.Param;

import javax.annotation.Resource;
import java.util.List;

/**
* @author Mr.Pan
* @description 针对表【orders(订单)】的数据库操作Mapper
* @createDate 2022-02-05 12:40:33
* @Entity com.pan.pmall.entity.Order
*/
public interface OrderMapper extends BaseMapper<Order> {
    OrderVo selectOrderVoByOrderId(@Param("orderId") String orderId);

    List<OrderVo> selectOrdersWithItemByUserIdAndStatus(@Param("userId") String userId, @Param("status") Integer status);
}




