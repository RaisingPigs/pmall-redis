package com.pan.pmall.service;

import com.pan.pmall.vo.ResultVo;

/**
 * @description:
 * @author: Mr.Pan
 * @create: 2022-01-28 19:36
 **/
public interface UserService {
    ResultVo checkRegist(String username, String password);
    ResultVo checkLogin(String username, String password);
}
