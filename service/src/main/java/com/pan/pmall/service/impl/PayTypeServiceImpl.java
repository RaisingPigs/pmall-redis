package com.pan.pmall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.pan.pmall.dao.PayTypeMapper;
import com.pan.pmall.entity.PayType;
import com.pan.pmall.service.PayTypeService;
import com.pan.pmall.vo.ResultVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @description:
 * @author: Mr.Pan
 * @create: 2022-02-16 15:35
 **/
@Service
public class PayTypeServiceImpl implements PayTypeService {
    @Resource
    private PayTypeMapper payTypeMapper;


    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public ResultVo listPayTypes() {
        QueryWrapper<PayType> payTypeQueryWrapper = new QueryWrapper<>();
        payTypeQueryWrapper.lambda().eq(PayType::getStatus, 1);
        List<PayType> payTypes = payTypeMapper.selectList(payTypeQueryWrapper);

        if (!payTypes.isEmpty()) {
            return ResultVo.success().add("payTypes", payTypes);
        } else {
            return ResultVo.failed("获取支付方式失败");
        } 

    }
}
