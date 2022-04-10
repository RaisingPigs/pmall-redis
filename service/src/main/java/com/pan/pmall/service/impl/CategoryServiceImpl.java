package com.pan.pmall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pan.pmall.dao.CategoryMapper;
import com.pan.pmall.entity.Category;
import com.pan.pmall.pojo.CategoryVo;
import com.pan.pmall.service.CategoryService;
import com.pan.pmall.vo.ResultVo;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.SplittableRandom;
import java.util.concurrent.TimeUnit;

/**
 * @description:
 * @author: Mr.Pan
 * @create: 2022-02-07 17:05
 **/
@Service
public class CategoryServiceImpl implements CategoryService {
    @Resource
    private CategoryMapper categoryMapper;
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public ResultVo listCategories(String level, String id) throws Exception {
        /*从redis中获取分类*/
        String primaryCategoriesJson = stringRedisTemplate.boundValueOps("primaryCategories").get();

        List<Category> categories;

        if (primaryCategoriesJson == null) {
            /*若redis中没有, 则查询数据库*/
            QueryWrapper<Category> wrapper = new QueryWrapper<>();
            wrapper.lambda().eq(Category::getCategoryLevel, level);
            categories = categoryMapper.selectList(wrapper);

            /*查完后放入redis, 设置过时时间为1天*/
            stringRedisTemplate.boundValueOps("primaryCategories").set(objectMapper.writeValueAsString(categories), 1, TimeUnit.DAYS);
        } else {
            /*若redis有分类, 则将获取到的分类json转为对象*/
            categories = objectMapper.readValue(primaryCategoriesJson, new TypeReference<List<Category>>() {
            });
        }

        return ResultVo.success("获取分类成功").add("categories", categories);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public ResultVo listCategoriesWithChildren(String categoryId) throws Exception {
        /*从redis中获取分类*/
        String childCategoriesJson = (String) stringRedisTemplate.boundHashOps("childCategories").get(categoryId);

        List<CategoryVo> categories;

        if (childCategoriesJson == null) {
            /*若redis中没有, 则查询数据库*/
            categories = categoryMapper.selectChildrenByCategoryId2(categoryId);

            /*查完后放入redis, 设置过时时间为1天*/
            stringRedisTemplate.boundHashOps("childCategories").put(categoryId, objectMapper.writeValueAsString(categories));
            stringRedisTemplate.boundHashOps("childCategories").expire(1, TimeUnit.DAYS);
        } else {
            /*若redis有分类, 则将获取到的分类json转为对象*/
            categories = objectMapper.readValue(childCategoriesJson, new TypeReference<List<CategoryVo>>() {
            });
        }

        return ResultVo.success("获取2,3级分类成功").add("categories", categories);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public ResultVo getPrimaryCategoryWithProductVOById(String categoryId) {
        CategoryVo categoryVO = categoryMapper.selectOneWithProductVOByCategoryId(categoryId, 0, 6);

        if (categoryVO != null) {
            return ResultVo.success("获取一级分类下商品销量top6成功").add("category", categoryVO);
        } else {
            return ResultVo.failed("获取一级分类下商品销量top6失败");
        }
    }
}
