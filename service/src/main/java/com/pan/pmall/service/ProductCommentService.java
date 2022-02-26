package com.pan.pmall.service;

import com.pan.pmall.entity.ProductComment;
import com.pan.pmall.utils.QueryInfo;
import com.pan.pmall.vo.ResultVo;

/**
 * @description:
 * @author: Mr.Pan
 * @create: 2022-02-10 15:05
 **/
public interface ProductCommentService {
    ResultVo listCommentsByProductId(String productId, QueryInfo queryInfo);

    ResultVo getCommentCount(String productId);

    ResultVo listCommentPageByProductId(String productId, QueryInfo queryInfo);

    ResultVo addComment(ProductComment productComment, String orderId);
}
