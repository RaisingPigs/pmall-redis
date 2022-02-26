package com.pan.pmall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.pan.pmall.dao.UserAddrMapper;
import com.pan.pmall.entity.UserAddr;
import com.pan.pmall.service.UserAddrService;
import com.pan.pmall.vo.ResultVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @description:
 * @author: Mr.Pan
 * @create: 2022-02-14 09:20
 **/
@Service
public class UserAddrServiceImpl implements UserAddrService {
    @Resource
    private UserAddrMapper userAddrMapper;
    
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public ResultVo listUserAddrByUserId(String userId) {
        QueryWrapper<UserAddr> addrQueryWrapper = new QueryWrapper<>();
        addrQueryWrapper.lambda()
                .eq(UserAddr::getUserId, userId)
                .eq(UserAddr::getStatus, 1);

        List<UserAddr> userAddrs = userAddrMapper.selectList(addrQueryWrapper);

        return ResultVo.success().add("userAddrs", userAddrs);
    }
    
    
}
