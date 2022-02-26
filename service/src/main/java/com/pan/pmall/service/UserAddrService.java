package com.pan.pmall.service;

import com.pan.pmall.vo.ResultVo;

/**
 * @description:
 * @author: Mr.Pan
 * @create: 2022-02-14 09:20
 **/
public interface UserAddrService {
    ResultVo listUserAddrByUserId(String userId);
}
