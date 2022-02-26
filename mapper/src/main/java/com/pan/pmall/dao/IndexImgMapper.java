package com.pan.pmall.dao;

import com.pan.pmall.entity.IndexImg;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author Mr.Pan
* @description 针对表【index_img(轮播图)】的数据库操作Mapper
* @createDate 2022-02-05 12:40:33
* @Entity com.pan.pmall.entity.IndexImg
*/
public interface IndexImgMapper extends BaseMapper<IndexImg> {
    /*获取轮播图信息, status为1且按照seq属性升序*/
    List<IndexImg> selectIndexImgs();
}




