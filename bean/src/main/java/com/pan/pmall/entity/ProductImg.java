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
 * 商品图片
 * @TableName product_img
 */
@TableName(value ="product_img")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductImg implements Serializable {
    /**
     * 图片主键
     */
    @TableId
    private String id;

    /**
     * 商品外键id;商品外键id
     */
    private String itemId;

    /**
     * 图片地址;图片地址
     */
    private String url;

    /**
     * 顺序;图片顺序，从小到大
     */
    private Integer sort;

    /**
     * 是否主图;是否主图，1：是，0：否
     */
    private Integer isMain;

    /**
     * 创建时间
     */
    private Date createdTime;

    /**
     * 更新时间
     */
    private Date updatedTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}