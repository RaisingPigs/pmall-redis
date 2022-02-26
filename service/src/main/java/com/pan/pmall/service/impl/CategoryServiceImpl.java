package com.pan.pmall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.pan.pmall.dao.CategoryMapper;
import com.pan.pmall.entity.Category;
import com.pan.pmall.pojo.CategoryVo;
import com.pan.pmall.service.CategoryService;
import com.pan.pmall.vo.ResultVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @description:
 * @author: Mr.Pan
 * @create: 2022-02-07 17:05
 **/
@Service
public class CategoryServiceImpl implements CategoryService {
    @Resource
    private CategoryMapper categoryMapper;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public ResultVo listCategories(String level, String id) {
        QueryWrapper<Category> wrapper = new QueryWrapper<>();
        if (level != null && !"".equals(level)) {
            wrapper.lambda().eq(Category::getCategoryLevel, level);
        }
        if (id != null) {
            wrapper.lambda().eq(Category::getParentId, id);
        }
        List<Category> categories = categoryMapper.selectList(wrapper);

        if (!categories.isEmpty()) {
            return ResultVo.success("获取分类成功").add("categories", categories);
        } else {
            return ResultVo.failed("获取分类失败");
        }
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public ResultVo listCategoriesWithChildren(String categoryId) {
        List<CategoryVo> categories = categoryMapper.selectChildrenByCategoryId2(categoryId);

        if (!categories.isEmpty()) {
            return ResultVo.success("获取2,3级分类成功").add("categories", categories);
        } else {
            return ResultVo.failed("获取2,3级分类失败");
        }
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
