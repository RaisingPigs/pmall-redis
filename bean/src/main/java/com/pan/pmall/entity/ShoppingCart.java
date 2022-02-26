package com.pan.pmall.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 购物车
 * @TableName shopping_cart
 */
@TableName(value ="shopping_cart")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShoppingCart implements Serializable {
    /**
     * 主键
     */
    @TableId
    private String cartId;

    /**
     * 商品ID
     */
    private String productId;

    /**
     * skuID
     */
    private String skuId;
    /*sku属性*/
    private String skuAttr;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 购物车商品数量
     */
    private Integer cartNum;

    /**
     * 添加购物车的时间
     */
    private Date createTime;
    
    /**
     * 修改购物车的时间
     */
    private Date updateTime;

    /*
    * 购物车状态
    *   若为1, 则为已添加购物车
    *   若为2, 则为购物车已被添加到订单
    *   若为0, 则为用户删除
    *   若为-1, 则为商品的sku被删除, 购物车项失效*/
    private Integer status;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}