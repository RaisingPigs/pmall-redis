package com.pan.pmall.service.task;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.pan.pmall.dao.OrderItemMapper;
import com.pan.pmall.dao.OrderMapper;
import com.pan.pmall.dao.ProductSkuMapper;
import com.pan.pmall.entity.Order;
import com.pan.pmall.entity.OrderItem;
import com.pan.pmall.entity.ProductSku;
import com.pan.pmall.service.OrderService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @description:
 * @author: Mr.Pan
 * @create: 2022-02-17 10:41
 **/
@Component
public class OrderTimeoutCheckTask {
    @Resource
    private OrderMapper orderMapper;
    @Resource
    private OrderItemMapper orderItemMapper;
    @Resource
    private ProductSkuMapper productSkuMapper;
    @Resource
    private OrderService orderService;

    @Scheduled(cron = "0/60 * * * * ?")
    public void checkOrder() {
        /*订单超时时间为三十分钟
         当前时间戳 - 30分钟乘60秒乘1000毫秒 = 订单超时日期*/
        String formatDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(System.currentTimeMillis() - 24 * 60 * 60 * 1000);

        QueryWrapper<Order> orderQueryWrapper = new QueryWrapper<Order>();

        /*QueryWrapper的apply()方法, 是增加where条件的, 会自动增加and
         *       若日期为 2020-10-20 11:20:45, 除了有日期还有时间, 中间会有空格, 所以sql必须加单引号 */
        orderQueryWrapper.lambda()
                .eq(Order::getStatus, 1)
                .apply("create_time <= '" + formatDate + "'");

        List<Order> orders = orderMapper.selectList(orderQueryWrapper);

        /*注意: 当查询到了超时订单时, 并不是要第一时间进行修改订单状态. 因为当用户支付完成后, 可能由于网络波动, 或者接口异常等原因, 导致用户支付了但是数据库状态未修改. 所以不能直接修改订单状态, 应该向微信支付平台发送请求, 查看用户是否已经支付, 如果已支付, 则将订单状态改为已支付/未发货
         *
         * 如果为未支付, 则先向微信支付平台发送请求, 关闭当前订单的支付链接, 再取消订单. 避免修改了订单状态却还能支付 */

        for (Order order : orders) {
            /*关闭订单*/
            orderService.closeOrder(order, 1);
        }
    }
}
