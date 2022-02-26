package com.pan.pmall.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 商品;商品信息相关表：分类表，商品图片表，商品规格表，商品参数表
 * @TableName product
 */
@TableName(value ="product")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product implements Serializable {
    /**
     * 商品主键id
     */
    @TableId
    private String productId;

    /**
     * 商品名称;商品名称
     */
    private String productName;

    /**
     * 分类外键id;分类id
     */
    private String categoryId;

    /**
     * 一级分类外键id;一级分类id，用于优化查询
     */
    private String rootCategoryId;

    /**
     * 销量;累计销售
     */
    private Integer soldNum;

    /**
     * 默认是1，表示正常状态, -1表示删除, 0下架;默认是1，表示正常状态, -1表示删除, 0下架
     */
    private Integer productStatus;

    /**
     * 商品内容;商品内容
     */
    private String content;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}