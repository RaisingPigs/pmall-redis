package com.pan.pmall.dao;

import com.pan.pmall.entity.ShoppingCart;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pan.pmall.pojo.ShoppingCartVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author Mr.Pan
* @description 针对表【shopping_cart(购物车)】的数据库操作Mapper
* @createDate 2022-02-05 12:40:33
* @Entity com.pan.pmall.entity.ShoppingCart
*/
public interface ShoppingCartMapper extends BaseMapper<ShoppingCart> {
    List<ShoppingCartVo> selectShoppingCartVOByUserId(@Param("userId") String userId);

    List<ShoppingCartVo> selectShoppingCartVOByCartIdList(@Param("cartIds") List<String> cartIds);
}




