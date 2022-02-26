package com.pan.pmall.service;

import com.pan.pmall.vo.ResultVo;

/**
 * @description:
 * @author: Mr.Pan
 * @create: 2022-02-10 11:00
 **/
public interface ProductParamService {
    ResultVo getProductParamByProductId(String productId);
}
