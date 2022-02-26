package com.pan.pmall.dao;

import com.pan.pmall.entity.ProductSku;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
* @author Mr.Pan
* @description 针对表【product_sku(商品规格;每一件商品都有不同的规格，不同的规格又有不同的价格和优惠力度，规格表为此设计)】的数据库操作Mapper
* @createDate 2022-02-05 12:40:33
* @Entity com.pan.pmall.entity.ProductSku
*/
public interface ProductSkuMapper extends BaseMapper<ProductSku> {

}




