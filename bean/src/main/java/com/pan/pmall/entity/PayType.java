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
 * 支付类型
 * @TableName pay_type
 */
@TableName(value ="pay_type")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PayType implements Serializable {
    /**
     * 
     */
    @TableId
    private String payId;

    /**
     * 
     */
    private Integer payType;

    /**
     * 
     */
    private String payName;

    /**
     * 
     */
    private String payLogo;
    /**
     * 
     */
    private String payQrcodeImg;

    /**
     * 
     */
    private Date createTime;

    /**
     * 
     */
    private Date updateTime;
    
    private Integer status;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}