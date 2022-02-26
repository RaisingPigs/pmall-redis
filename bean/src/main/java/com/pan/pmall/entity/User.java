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
 * 用户
 * @TableName users
 */
@TableName(value ="users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {
    /**
     * 主键id;用户id
     */
    @TableId
    private String userId;

    /**
     * 用户名;用户名
     */
    private String username;

    /**
     * 密码;密码
     */
    private String password;

    /**
     * 昵称;昵称
     */
    private String nickname;

    /**
     * 真实姓名;真实姓名
     */
    private String realname;

    /**
     * 头像;头像
     */
    private String userImg;

    /**
     * 手机号;手机号
     */
    private String userMobile;

    /**
     * 邮箱地址;邮箱地址
     */
    private String userEmail;

    /**
     * 性别;M(男) or F(女)
     */
    private String userSex;

    /**
     * 生日;生日
     */
    private Date userBirth;

    /**
     * 注册时间;创建时间
     */
    private Date userRegtime;

    /**
     * 更新时间;更新时间
     */
    private Date userModtime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}