package com.pan.pmall.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 购物车VO类
 * 新增 productName, originalPrice, sellPrice
 * @TableName shopping_cart
 */
@TableName(value = "shopping_cart")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShoppingCartVo {
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
    
    /*
    * sku属性
    */
    private String skuAttr;

    /**
     * 购物车商品数量
     */
    private Integer cartNum;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 添加购物车时间
     */
    private Date createTime;

    /**
     * 添加购物车的时间
     */
    private Date updateTime;
    
    /*购物车项的状态: 
        1为正常 
        0为商家已删除该商品或者该商品的sku 
        -1为用户已删除*/
    private Integer status;

    
    private String productName;
    private String skuName;
    private String skuImg;
    private Integer stock;
    private BigDecimal originalPrice;
    private BigDecimal sellPrice;
    private BigDecimal discounts;
}