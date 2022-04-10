package com.pan.pmall.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.pan.pmall.utils.QueryInfo;
import com.pan.pmall.vo.ResultVo;

/**
 * @description:
 * @author: Mr.Pan
 * @create: 2022-02-07 20:41
 **/
public interface ProductService {
    /*根据商品的创建日期, 查询排行前几个的商品及其图片*/
    ResultVo listRecommendProductsWithImgs(Integer start, Integer num);

    /*根据商品id查询商品信息, sku, 图片, 评价数量*/
    ResultVo getProductInfoById(String id) throws Exception;

    /*根据三级分类id查询商品及图片*/
    ResultVo listProductsPageByCategoryId(String categoryId, QueryInfo queryInfo, String orderByCol, String seq);

    /*查询某分类下所有的品牌*/
    ResultVo listBrandsByCategoryId(String categoryId);

    /*商品搜索*/
    ResultVo listProductsPageLikeProductName(QueryInfo queryInfo, String categoryId,String brand, String orderByCol, String seq);

    /*查询某商品属于的的品牌*/
    ResultVo listBrandsLikeProductName(String query);
}
