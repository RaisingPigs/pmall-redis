package com.pan.pmall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.pan.pmall.dao.ProductParamMapper;
import com.pan.pmall.entity.ProductParam;
import com.pan.pmall.service.ProductParamService;
import com.pan.pmall.vo.ResultVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @description:
 * @author: Mr.Pan
 * @create: 2022-02-10 11:01
 **/
@Service
public class ProductParamServiceImpl implements ProductParamService {
    @Resource
    private ProductParamMapper productParamMapper;
    
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public ResultVo getProductParamByProductId(String productId) {
        QueryWrapper<ProductParam> paramQueryWrapper = new QueryWrapper<>();
        paramQueryWrapper.lambda().eq(ProductParam::getProductId, productId);

        ProductParam productParam = productParamMapper.selectOne(paramQueryWrapper);

        if (productParam != null) {
            return ResultVo.success("获取商品参数成功").add("productParam", productParam);
        } else {
            return ResultVo.failed("获取商品参数失败");
        }
    }
}
