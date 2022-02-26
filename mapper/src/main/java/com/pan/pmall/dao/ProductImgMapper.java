package com.pan.pmall.dao;

import com.pan.pmall.entity.ProductImg;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Mr.Pan
 * @description 针对表【product_img(商品图片)】的数据库操作Mapper
 * @createDate 2022-02-05 12:40:33
 * @Entity com.pan.pmall.entity.ProductImg
 */
public interface ProductImgMapper extends BaseMapper<ProductImg> {
    List<ProductImg> selectImgsByProductId(@Param("productId") String productId);
}




