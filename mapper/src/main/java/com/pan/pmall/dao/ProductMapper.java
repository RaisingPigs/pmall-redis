package com.pan.pmall.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pan.pmall.entity.Product;
import com.pan.pmall.pojo.ProductVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Mr.Pan
 * @description 针对表【product(商品;商品信息相关表：分类表，商品图片表，商品规格表，商品参数表)】的数据库操作Mapper
 * @createDate 2022-02-05 12:40:33
 * @Entity com.pan.pmall.entity.Product
 */
public interface ProductMapper extends BaseMapper<Product> {
    /*根据日期排序查询商品及图片*/
    List<ProductVo> selectProductsWithImgsByCreateTime(@Param("start") Integer start,
                                                       @Param("num") Integer num);

    /*查询指定类别下, 销量最高的几个商品*/
    List<ProductVo> selectProductsWithImgsByCategoryId(@Param("categoryId") String categoryId, @Param("start") Integer start, @Param("num") Integer num);

    /*根据三级分类id查询商品及图片和sku和参数*/
    List<ProductVo> selectProductsWithImgsAndSkusByCategoryId(@Param("categoryId") String categoryId, @Param("query") String query, @Param("orderByCol") String orderByCol, @Param("seq") String seq);

    /*获取某一分类下的所有品牌*/
    List<String> selectBrandsByCategoryId(@Param("categoryId") String categoryId);

    /*根据三级分类id查询商品及图片和sku和参数*/
    List<ProductVo> selectProductsWithImgsAndSkusLikeProductName(@Param("query") String query, @Param("categoryId") String categoryId, @Param("brand") String brand, @Param("orderByCol") String orderByCol, @Param("seq") String seq);

    /*根据关键字获取所有品牌*/
    List<String> selectBrandsLikeProductName(@Param("query") String query);
}




