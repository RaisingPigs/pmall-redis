package com.pan.pmall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pan.pmall.dao.OrderItemMapper;
import com.pan.pmall.dao.ProductCommentMapper;
import com.pan.pmall.entity.OrderItem;
import com.pan.pmall.entity.ProductComment;
import com.pan.pmall.pojo.ProductCommentVo;
import com.pan.pmall.service.OrderService;
import com.pan.pmall.service.ProductCommentService;
import com.pan.pmall.utils.MyPageHelper;
import com.pan.pmall.utils.QueryInfo;
import com.pan.pmall.vo.ResultVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @description:
 * @author: Mr.Pan
 * @create: 2022-02-10 15:05
 **/
@Service
public class ProductCommentServiceImpl implements ProductCommentService {
    @Resource
    private ProductCommentMapper productCommentMapper;
    @Resource
    private OrderItemMapper orderItemMapper;
    @Resource
    private OrderService orderService;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public ResultVo listCommentsByProductId(String productId, QueryInfo queryInfo) {
        PageHelper.startPage(queryInfo.getPagenum(), queryInfo.getPagesize());
        List<ProductCommentVo> comments = productCommentMapper.selectCommentsByProductId(productId, queryInfo.getQuery());
        PageInfo<ProductCommentVo> pageInfo = new PageInfo<>(comments, 5);


        return ResultVo.success("获取商品评论成功").add("pageInfo", pageInfo);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public ResultVo getCommentCount(String productId) {
        QueryWrapper<ProductComment> commentQueryWrapper = new QueryWrapper<>();
        commentQueryWrapper.lambda()
                .eq(ProductComment::getProductId, productId)
                .eq(ProductComment::getIsShow, 1);
        Integer totalCount = productCommentMapper.selectCount(commentQueryWrapper);

        commentQueryWrapper.clear();
        commentQueryWrapper.lambda()
                .eq(ProductComment::getProductId, productId)
                .eq(ProductComment::getCommType, 1)
                .eq(ProductComment::getIsShow, 1);
        Integer goodCount = productCommentMapper.selectCount(commentQueryWrapper);

        commentQueryWrapper.clear();
        commentQueryWrapper.lambda()
                .eq(ProductComment::getProductId, productId)
                .eq(ProductComment::getCommType, 0)
                .eq(ProductComment::getIsShow, 1);
        Integer mediumCount = productCommentMapper.selectCount(commentQueryWrapper);

        commentQueryWrapper.clear();
        commentQueryWrapper.lambda()
                .eq(ProductComment::getProductId, productId)
                .eq(ProductComment::getCommType, -1)
                .eq(ProductComment::getIsShow, 1);
        Integer badCount = productCommentMapper.selectCount(commentQueryWrapper);

        if (totalCount != null && goodCount != null && mediumCount != null && badCount != null) {
            return ResultVo.success("获取评论计数成功")
                    .add("totalCount", totalCount)
                    .add("goodCount", goodCount)
                    .add("mediumCount", mediumCount)
                    .add("badCount", badCount);
        } else {
            return ResultVo.failed("获取评论计数失败");
        }

    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public ResultVo listCommentPageByProductId(String productId, QueryInfo queryInfo) {
        /*1. 根据商品id查询总记录数*/
        QueryWrapper<ProductComment> commentQueryWrapper = new QueryWrapper<>();
        commentQueryWrapper.lambda().eq(ProductComment::getProductId, productId);
        int total = productCommentMapper.selectCount(commentQueryWrapper);

        /*计算start*/
        int start = (queryInfo.getPagenum() - 1) * queryInfo.getPagesize();

        List<ProductCommentVo> comments = productCommentMapper.selectCommentPageByProductId(productId, start, queryInfo.getPagesize());

        return ResultVo.success().add("pageInfo", new MyPageHelper<ProductCommentVo>(queryInfo.getPagenum(), queryInfo.getPagesize(), total, comments, 5));
    }

    @Override
    @Transactional
    public ResultVo addComment(ProductComment productComment, String orderId) {
        productComment.setSepcName(new Date());
        productComment.setReplyStatus(0);
        productComment.setIsShow(1);

        int insertRes = productCommentMapper.insert(productComment);

        UpdateWrapper<OrderItem> itemUpdateWrapper = new UpdateWrapper<>();
        itemUpdateWrapper.lambda()
                .set(OrderItem::getIsComment, 1)
                .eq(OrderItem::getItemId, productComment.getOrderItemId());
        orderItemMapper.update(null, itemUpdateWrapper);

        orderService.updateOrderStatus(orderId, 5, null);

        if (insertRes > 0) {
            return ResultVo.success();
        } else {
            return ResultVo.failed("添加评论失败");
        }
    }
}
