package com.pan.pmall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.pan.pmall.dao.ShoppingCartMapper;
import com.pan.pmall.entity.ShoppingCart;
import com.pan.pmall.pojo.ShoppingCartVo;
import com.pan.pmall.service.ShoppingCartService;
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
 * @create: 2022-02-12 09:14
 **/
@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {
    @Resource
    private ShoppingCartMapper shoppingCartMapper;

    @Override
    @Transactional
    public ResultVo addShoppingCart(ShoppingCart shoppingCart) {
        QueryWrapper<ShoppingCart> cartQueryWrapper = new QueryWrapper<>();
        cartQueryWrapper.lambda()
                .eq(ShoppingCart::getSkuId, shoppingCart.getSkuId())
                .eq(ShoppingCart::getSkuAttr, shoppingCart.getSkuAttr())
                .eq(ShoppingCart::getStatus, 1);

        /*添加购物车时, 先查看是否已有该商品, 如果有则增加数量即可*/
        ShoppingCart shoppingCartExist = shoppingCartMapper.selectOne(cartQueryWrapper);
        int res = 0;
        if (shoppingCartExist != null) {
            UpdateWrapper<ShoppingCart> cartUpdateWrapper = new UpdateWrapper<>();
            cartUpdateWrapper.lambda().eq(ShoppingCart::getCartId, shoppingCartExist.getCartId());

            Integer newNum = shoppingCartExist.getCartNum() + shoppingCart.getCartNum();
            cartUpdateWrapper.lambda()
                    .set(ShoppingCart::getCartNum, newNum)
                    .set(ShoppingCart::getUpdateTime, new Date());
            res = shoppingCartMapper.update(null, cartUpdateWrapper);
        } else {
            /*如果没有再添加*/
            Date date = new Date();
            shoppingCart.setCreateTime(date);
            shoppingCart.setUpdateTime(date);
            shoppingCart.setStatus(1);

            res = shoppingCartMapper.insert(shoppingCart);
        }

        /*根据结构判断是否数据库操作是否成功*/
        if (res > 0) {
            return ResultVo.created();
        } else {
            return ResultVo.failed("添加购物车失败");
        }
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public ResultVo listShoppingCartByUserId(String userId) {
        List<ShoppingCartVo> shoppingCarts = shoppingCartMapper.selectShoppingCartVOByUserId(userId);

        return ResultVo.success().add("shoppingCarts", shoppingCarts);
    }

    @Override
    @Transactional
    public ResultVo updateShoppingCartNum(String cartId, Integer cartNum) {
        UpdateWrapper<ShoppingCart> cartUpdateWrapper = new UpdateWrapper<>();
        cartUpdateWrapper.lambda().eq(ShoppingCart::getCartId, cartId);
        cartUpdateWrapper.lambda().set(ShoppingCart::getCartNum, cartNum);

        int updateRes = shoppingCartMapper.update(null, cartUpdateWrapper);

        if (updateRes > 0) {
            return ResultVo.success();
        } else {
            return ResultVo.failed("修改商品数量失败");
        }
    }

    @Override
    @Transactional
    public ResultVo deleteShoppingCartById(String cartId) {
        int deleteRes = shoppingCartMapper.deleteById(cartId);
        if (deleteRes > 0) {
            return ResultVo.deleted("删除成功");
        } else {
            return ResultVo.failed("删除失败");
        }
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public ResultVo listShoppingCartByCartIdList(List<String> cartIds) {
        List<ShoppingCartVo> shoppingCarts = shoppingCartMapper.selectShoppingCartVOByCartIdList(cartIds);

        return ResultVo.success().add("shoppingCarts", shoppingCarts);
    }

}
