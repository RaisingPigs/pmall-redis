package com.pan.pmall.service.impl;

import com.pan.pmall.dao.IndexImgMapper;
import com.pan.pmall.entity.IndexImg;
import com.pan.pmall.service.IndexImgService;
import com.pan.pmall.vo.ResultVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @description:
 * @author: Mr.Pan
 * @create: 2022-02-06 21:29
 **/
@Service
public class IndexImgServiceImpl implements IndexImgService {
    @Resource
    private IndexImgMapper indexImgMapper;
    
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public ResultVo listIndexImgs() {
        List<IndexImg> indexImgs = indexImgMapper.selectIndexImgs();

        if (!indexImgs.isEmpty()) {
            /*如果查到了, 返回轮播图*/
            return ResultVo.success().add("indexImgs", indexImgs);
        } else {
            /*如果没查到, 返回失败*/
            return ResultVo.failed("获取轮播图失败");
        } 
    }
}
