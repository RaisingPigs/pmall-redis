package com.pan.pmall.service;

import com.pan.pmall.entity.Order;
import com.pan.pmall.utils.QueryInfo;
import com.pan.pmall.vo.ResultVo;

import java.sql.SQLException;
import java.util.List;

/**
 * @description:
 * @author: Mr.Pan
 * @create: 2022-02-15 09:22
 **/
public interface OrderService {
    ResultVo addOrder(List<String> cartIds,
                      Integer payType,
                      String userAddrId,
                      String orderRemark,
                      String userId) throws SQLException;

    ResultVo getOrder(String orderId);

    ResultVo payOrder(String orderId);

    void closeOrder(Order order, Integer closeType);

    ResultVo getOrderNumByStatus(Integer status);

    ResultVo listOrdersWithItem(String userId, Integer status, QueryInfo queryInfo);

    ResultVo updateOrderStatus(String orderId, Integer status, Integer closeType);

    ResultVo getOrderItemById(String orderItemId);
}
