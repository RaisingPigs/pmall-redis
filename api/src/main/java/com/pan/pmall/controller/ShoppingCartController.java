package com.pan.pmall.controller;

import com.pan.pmall.entity.ShoppingCart;
import com.pan.pmall.service.ShoppingCartService;
import com.pan.pmall.vo.ResultVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

/**
 * @description:
 * @author: Mr.Pan
 * @create: 2022-02-06 14:59
 **/
@RestController
@RequestMapping("/shopcart")
@CrossOrigin
@Api(value = "购物车业务相关接口", tags = "购物车管理")
public class ShoppingCartController {
    @Resource
    private ShoppingCartService shoppingCartService;

    @PostMapping("/add")
    @ApiOperation("添加到购物车接口")
    public ResultVo addShoppingCart(@RequestBody ShoppingCart shoppingCart) {
        return shoppingCartService.addShoppingCart(shoppingCart);
    }


    @GetMapping("/{userId}")
    @ApiOperation("获取某用户购物车列表接口")
    public ResultVo listShopCartsByUserId(@PathVariable String userId) {
        return shoppingCartService.listShoppingCartByUserId(userId);
    }

    @PutMapping("/{cartId}/{cartNum}")
    @ApiOperation("修改购物车商品数量")
    public ResultVo updateCartNum(@PathVariable String cartId,
                                  @PathVariable Integer cartNum) {
        return shoppingCartService.updateShoppingCartNum(cartId, cartNum);
    }

    @DeleteMapping("/{cartId}")
    @ApiOperation("删除购物车商品接口")
    public ResultVo deleteCartItem(@PathVariable String cartId) {
        return shoppingCartService.deleteShoppingCartById(cartId);
    }


    @GetMapping("/list-by-cart-ids")
    @ApiOperation("根据购物车id列表获取购物车列表接口")
    public ResultVo listShopCartsByCartIdList(@RequestParam String cartIds) {
        if (cartIds == null || "".equals(cartIds)) {
            return ResultVo.failed("购物车为空不能结算");
        }

        String[] arr = cartIds.split(",");
        List<String> cartIdList = Arrays.asList(arr);

        return shoppingCartService.listShoppingCartByCartIdList(cartIdList);
    }
}
