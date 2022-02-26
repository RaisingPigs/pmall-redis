package com.pan.pmall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pan.pmall.dao.*;
import com.pan.pmall.entity.*;
import com.pan.pmall.pojo.OrderVo;
import com.pan.pmall.pojo.ShoppingCartVo;
import com.pan.pmall.service.OrderService;
import com.pan.pmall.utils.QueryInfo;
import com.pan.pmall.vo.ResultVo;
import com.sun.org.apache.xpath.internal.operations.Bool;
import com.sun.org.apache.xpath.internal.operations.Or;
import org.springframework.stereotype.Service;
import org.springframework.transaction.ReactiveTransaction;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.imageio.event.IIOWriteProgressListener;
import javax.xml.stream.FactoryConfigurationError;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

/**
 * @description:
 * @author: Mr.Pan
 * @create: 2022-02-15 09:23
 **/
@Service
public class OrderServiceImpl implements OrderService {
    @Resource
    private OrderMapper orderMapper;
    @Resource
    private ShoppingCartMapper shoppingCartMapper;
    @Resource
    private UserAddrMapper userAddrMapper;
    @Resource
    private OrderItemMapper orderItemMapper;
    @Resource
    private ProductSkuMapper productSkuMapper;
    @Resource
    private ProductMapper productMapper;


    @Override
    @Transactional
    public ResultVo addOrder(List<String> cartIds,
                             Integer payType,
                             String userAddrId,
                             String orderRemark,
                             String userId) throws SQLException {

        /*1. 从数据库查询购物车信息, 并校验库存是否足够*/
        List<ShoppingCartVo> shoppingCarts = shoppingCartMapper.selectShoppingCartVOByCartIdList(cartIds);

        /*校验库存是否足够*/
        for (ShoppingCartVo shoppingCartVO : shoppingCarts) {
            if (shoppingCartVO.getCartNum() > shoppingCartVO.getStock()) {
                return ResultVo.failed("买的商品太多, 库存不够了");
            }
        }

        /*2. 从数据库查询用户地址信息*/
        UserAddr userAddr = userAddrMapper.selectById(userAddrId);

        /*3. 获取并设置订单信息*/
        /* 拼接商品名, 形成订单名
         * 计算商品总价*/
        StringBuilder stringBuilder = new StringBuilder();
        BigDecimal totalPrice = new BigDecimal(0);
        for (ShoppingCartVo shoppingCartVO : shoppingCarts) {
            stringBuilder
                    .append(shoppingCartVO.getProductName())
                    .append(shoppingCartVO.getSkuName())
                    .append(",");

            totalPrice = totalPrice.add(shoppingCartVO.getSellPrice().multiply(new BigDecimal(shoppingCartVO.getCartNum())));
        }
        /*删除最后一个逗号*/
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        /*形成订单名*/
        String orderName = stringBuilder.toString();

        /*拼接收货用户地址*/
        /*清空stringBuilder*/
        stringBuilder.setLength(0);
        stringBuilder
                .append(userAddr.getProvince())
                .append(userAddr.getCity())
                .append(userAddr.getArea())
                .append(userAddr.getAddr());
        String receiverAddress = stringBuilder.toString();
        Date createDate = new Date();

        /*4. 保存订单*/
        Order order = new Order();
        order.setUserId(userId);
        order.setUntitled(orderName);
        order.setReceiverName(userAddr.getReceiverName());
        order.setReceiverMobile(userAddr.getReceiverMobile());
        order.setReceiverAddress(receiverAddress);
        order.setTotalAmount(totalPrice);
        order.setActualAmount(totalPrice);
        order.setPayType(payType);
        order.setOrderRemark(orderRemark);
        order.setStatus(1);
        order.setOrderFreight(new BigDecimal(0));
        order.setDeleteStatus(0);
        order.setCreateTime(createDate);
        order.setUpdateTime(createDate);

        int insertRes = orderMapper.insert(order);

        if (insertRes < 0) {
            return ResultVo.failed("创建订单失败");
        }

        /*5. 将购物车记录转化为订单项, 并将订单项保存数据库*/
        for (ShoppingCartVo shoppingCartVO : shoppingCarts) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrderId(order.getOrderId());
            orderItem.setProductId(shoppingCartVO.getProductId());
            orderItem.setProductName(shoppingCartVO.getProductName());
            orderItem.setSkuId(shoppingCartVO.getSkuId());
            orderItem.setSkuName(shoppingCartVO.getSkuName());
            orderItem.setSkuImg(shoppingCartVO.getSkuImg());
            orderItem.setSkuAttr(shoppingCartVO.getSkuAttr());
            orderItem.setProductPrice(shoppingCartVO.getSellPrice());
            orderItem.setBuyCounts(shoppingCartVO.getCartNum());

            BigDecimal orderItemTotalPrice = shoppingCartVO.getSellPrice().multiply(new BigDecimal(shoppingCartVO.getCartNum()));

            orderItem.setTotalAmount(orderItemTotalPrice);
            orderItem.setBasketDate(shoppingCartVO.getCreateTime());
            orderItem.setBuyTime(createDate);
            orderItem.setIsComment(0);

            int orderItemInsertRes = orderItemMapper.insert(orderItem);

            if (orderItemInsertRes < 0) {
                return ResultVo.failed("创建订单项失败");
            }

            /*6. 修改库存*/
            UpdateWrapper<ProductSku> skuUpdateWrapper = new UpdateWrapper<>();
            skuUpdateWrapper.lambda()
                    .set(ProductSku::getStock, shoppingCartVO.getStock() - shoppingCartVO.getCartNum())
                    .eq(ProductSku::getSkuId, shoppingCartVO.getSkuId())
                    .eq(ProductSku::getStatus, 1);

            productSkuMapper.update(null, skuUpdateWrapper);

            /*7. 修改销量*/
            Product product = productMapper.selectById(shoppingCartVO.getProductId());
            UpdateWrapper<Product> productUpdateWrapper = new UpdateWrapper<>();
            productUpdateWrapper.lambda()
                    .set(Product::getSoldNum, product.getSoldNum() + shoppingCartVO.getCartNum())
                    .eq(Product::getProductId, shoppingCartVO.getProductId());
            productMapper.update(null, productUpdateWrapper);

            /*8. 删除购物车记录*/
            UpdateWrapper<ShoppingCart> cartUpdateWrapper = new UpdateWrapper<>();
            cartUpdateWrapper.lambda()
                    .set(ShoppingCart::getStatus, 0)
                    .eq(ShoppingCart::getCartId, shoppingCartVO.getCartId());

            shoppingCartMapper.update(null, cartUpdateWrapper);
        }

        return ResultVo.created("创建订单项成功").add("orderId", order.getOrderId());
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public ResultVo getOrder(String orderId) {
        Order order = orderMapper.selectOrderVoByOrderId(orderId);

        if (order != null) {
            return ResultVo.success().add("order", order);
        } else {
            return ResultVo.failed("获取订单失败");
        }
    }

    @Override
    @Transactional
    public ResultVo payOrder(String orderId) {
        Order order = orderMapper.selectById(orderId);
        if (order == null) {
            return ResultVo.failed("查无此订单");
        }
        if (order.getStatus() == 6) {
            return ResultVo.failed("订单已超时取消, 请重新下单");
        }

        UpdateWrapper<Order> orderUpdateWrapper = new UpdateWrapper<>();
        orderUpdateWrapper.lambda()
                .eq(Order::getOrderId, orderId)
                .eq(Order::getStatus, 1)
                .set(Order::getStatus, 2)
                .set(Order::getPayTime, new Date());
        int update = orderMapper.update(null, orderUpdateWrapper);

        if (update > 0) {
            return ResultVo.success();
        } else {
            return ResultVo.failed("付款失败");
        }
    }

    @Override
    /*因为可能有不同的线程都要关闭订单, 所以要加锁
     * 可以将事务的隔离级别设置为串行化 事务锁, 也可以加JVM锁 synchronized*/
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void closeOrder(Order order, Integer closeType) {
        synchronized (this) {
            /*关闭订单
             *   1. 设置status=6 已关闭状态, 设置closeType=1 超时关闭
             *   2. 恢复库存 (找到orderItem中的skuId和buyCounts, 恢复库存)*/

            /*修改订单状态*/
            UpdateWrapper<Order> orderUpdateWrapper = new UpdateWrapper<>();
            orderUpdateWrapper.lambda()
                    .set(Order::getStatus, 6)
                    .set(Order::getCloseType, closeType)
                    .set(Order::getCancelTime, new Date())
                    .eq(Order::getOrderId, order.getOrderId())
                    .eq(Order::getStatus, 1);

            orderMapper.update(null, orderUpdateWrapper);

            /*恢复库存和销量*/
            QueryWrapper<OrderItem> itemQueryWrapper = new QueryWrapper<>();
            itemQueryWrapper.lambda().eq(OrderItem::getOrderId, order.getOrderId());
            List<OrderItem> orderItems = orderItemMapper.selectList(itemQueryWrapper);
            for (OrderItem orderItem : orderItems) {
                /*查找到sku的库存 + 订单的购买量 = 恢复的库存量*/
                ProductSku productSku = productSkuMapper.selectById(orderItem.getSkuId());
                productSku.setStock(productSku.getStock() + orderItem.getBuyCounts());

                UpdateWrapper<ProductSku> skuUpdateWrapper = new UpdateWrapper<>();
                skuUpdateWrapper.lambda()
                        .set(ProductSku::getStock, productSku.getStock())
                        .eq(ProductSku::getSkuId, productSku.getSkuId());
                productSkuMapper.update(null, skuUpdateWrapper);

                /*查找到product的销量 - 订单的购买量 = 恢复的销量*/
                Product product = productMapper.selectById(orderItem.getProductId());
                product.setSoldNum(product.getSoldNum() - orderItem.getBuyCounts());

                UpdateWrapper<Product> productUpdateWrapper = new UpdateWrapper<>();
                productUpdateWrapper.lambda()
                        .set(Product::getSoldNum, product.getSoldNum())
                        .eq(Product::getProductId, product.getProductId());
                productMapper.update(null, productUpdateWrapper);
            }
        }
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public ResultVo getOrderNumByStatus(Integer status) {
        QueryWrapper<Order> orderQueryWrapper = new QueryWrapper<>();
        orderQueryWrapper.lambda().eq(Order::getStatus, status);

        Integer orderNum = orderMapper.selectCount(orderQueryWrapper);

        return ResultVo.success().add("orderNum", orderNum);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public ResultVo listOrdersWithItem(String userId, Integer status, QueryInfo queryInfo) {
        PageHelper.startPage(queryInfo.getPagenum(), queryInfo.getPagesize());
        List<OrderVo> orders = orderMapper.selectOrdersWithItemByUserIdAndStatus(userId, status);

        PageInfo<OrderVo> pageInfo = new PageInfo<>(orders);

        return ResultVo.success().add("pageInfo", pageInfo);
    }

    @Override
    @Transactional
    public ResultVo updateOrderStatus(String orderId, Integer status, Integer closeType) {
        UpdateWrapper<Order> orderUpdateWrapper = new UpdateWrapper<>();

        /*如果是6就是取消订单*/
        if (status == 6) {
            /*取消订单要还原销量与库存*/
            closeOrder(orderMapper.selectById(orderId), closeType);

            return ResultVo.success();
        } else if (status == 4) {
            /*如果是4就是确认收货*/
            orderUpdateWrapper.lambda()
                    .set(Order::getStatus, status)
                    .eq(Order::getOrderId, orderId);
        } else if (status == -1) {
            /*如果是-1就是删除订单*/
            orderUpdateWrapper.lambda()
                    .set(Order::getDeleteStatus, 1)
                    .eq(Order::getOrderId, orderId);
        } else if (status == 5) {
            /*如果是5就是已评论, 检查评论状态, 如果订单下所有评论都是已评论, 则为已完成*/
            QueryWrapper<OrderItem> itemQueryWrapper = new QueryWrapper<>();
            itemQueryWrapper.lambda()
                    .eq(OrderItem::getOrderId, orderId);
            List<OrderItem> orderItems = orderItemMapper.selectList(itemQueryWrapper);

            boolean flag = true;
            for (OrderItem orderItem : orderItems) {
                if (orderItem.getIsComment() == 0) {
                    flag = false;
                }
            }

            if (flag) {
                orderUpdateWrapper.lambda()
                        .set(Order::getStatus, status)
                        .eq(Order::getOrderId, orderId);
                orderMapper.update(null, orderUpdateWrapper);
            } else {
                return ResultVo.success();
            } 
        }

        orderMapper.update(null, orderUpdateWrapper);
        return ResultVo.success();
    }

    @Override
    public ResultVo getOrderItemById(String orderItemId) {
        OrderItem orderItem = orderItemMapper.selectById(orderItemId);

        return ResultVo.success().add("orderItem", orderItem);
    }
}
