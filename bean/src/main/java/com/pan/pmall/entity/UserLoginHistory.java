package com.pan.pmall.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 登录历史表
 * @TableName user_login_history
 */
@TableName(value ="user_login_history")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginHistory implements Serializable {
    /**
     * ID
     */
    @TableId
    private String histId;

    /**
     * 地区
     */
    private String histArea;

    /**
     * 国家
     */
    private String histCountry;

    /**
     * 用户id
     */
    private String userId;

    /**
     * IP
     */
    private String ip;

    /**
     * 时间
     */
    private String loginTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}