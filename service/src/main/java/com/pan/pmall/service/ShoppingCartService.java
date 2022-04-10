package com.pan.pmall.service;

import com.pan.pmall.entity.ShoppingCart;
import com.pan.pmall.pojo.ShoppingCartVo;
import com.pan.pmall.vo.ResultVo;

import java.util.List;

/**
 * @description:
 * @author: Mr.Pan
 * @create: 2022-02-12 09:13
 **/
public interface ShoppingCartService {
    ResultVo addShoppingCart(ShoppingCart shoppingCart);

    ResultVo listShoppingCartByUserId(String userId);

    ResultVo updateShoppingCartNum(String cartId, Integer cartNum);

    ResultVo deleteShoppingCartById(String cartId);

    List<ShoppingCartVo> listShoppingCartByCartIdList(List<String> cartIds);
}
