package com.pan.pmall.dao;

import com.pan.pmall.entity.ProductComment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pan.pmall.pojo.ProductCommentVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Mr.Pan
 * @description 针对表【product_comments(商品评价)】的数据库操作Mapper
 * @createDate 2022-02-05 12:40:33
 * @Entity com.pan.pmall.entity.ProductComment
 */
public interface ProductCommentMapper extends BaseMapper<ProductComment> {
    /*查询所有评论, 包含评论所属用户相关信息和评论所属的订单快照的信息*/
    List<ProductCommentVo> selectCommentsByProductId(@Param("productId") String productId, @Param("query") String query);
    
    /*分页查询所有评论, 包含评论所属用户相关信息和评论所属的订单快照的信息*/
    List<ProductCommentVo> selectCommentPageByProductId(@Param("productId") String productId, @Param("start") Integer start, @Param("pagesize") Integer pagesize);
}




