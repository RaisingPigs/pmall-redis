package com.pan.pmall.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pan.pmall.dao.IndexImgMapper;
import com.pan.pmall.entity.IndexImg;
import com.pan.pmall.service.IndexImgService;
import com.pan.pmall.vo.ResultVo;
import net.sf.jsqlparser.statement.create.table.Index;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @description:
 * @author: Mr.Pan
 * @create: 2022-02-06 21:29
 **/
@Service
public class IndexImgServiceImpl implements IndexImgService {
    @Resource
    private IndexImgMapper indexImgMapper;
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public ResultVo listIndexImgs() throws Exception {
        /*从redis中获取首页轮播图*/
        String indexImgsJson = stringRedisTemplate.boundValueOps("indexImgs").get();

        List<IndexImg> indexImgs;

        if (indexImgsJson == null) {
            /*利用双重检测锁来避免多个请求访问数据库*/
            synchronized (this) {
                /*第二次从redis中获取首页轮播图*/
                indexImgsJson = stringRedisTemplate.boundValueOps("indexImgs").get();

                if (indexImgsJson == null) {
                    /*如果没获取到, 则查询数据库*/
                    indexImgs = indexImgMapper.selectIndexImgs();
                    if (indexImgs != null) {
                        /*将其转json放入redis中, 设置过期时间为1天*/
                        stringRedisTemplate.boundValueOps("indexImgs").set(objectMapper.writeValueAsString(indexImgs), 1, TimeUnit.DAYS);
                    } else {
                        /*如果数据库中没有该数据, 则生成一个空list, 放入redis, 10s过期*/
                        stringRedisTemplate.boundValueOps("indexImgs").set("[]", 10, TimeUnit.SECONDS);
                    }
                } else {
                    /*获取到了则将json转为list*/
                    indexImgs = objectMapper.readValue(indexImgsJson, new TypeReference<List<IndexImg>>() {
                    });
                }
            }
        } else {
            /*获取到了则将json转为list*/
            indexImgs = objectMapper.readValue(indexImgsJson, new TypeReference<List<IndexImg>>() {
            });
        }

        /*返回轮播图*/
        return ResultVo.success().add("indexImgs", indexImgs);
    }
}
