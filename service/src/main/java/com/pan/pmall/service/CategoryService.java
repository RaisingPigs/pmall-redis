package com.pan.pmall.service;

import com.pan.pmall.vo.ResultVo;

/**
 * @description:
 * @author: Mr.Pan
 * @create: 2022-02-07 17:04
 **/
public interface CategoryService {
    /*查询根据分类等级或者分类id查询分类*/
    ResultVo listCategories(String level, String id) throws Exception;
    
    /*根据一级分类id查询分类及其2,3级子分类*/
    ResultVo listCategoriesWithChildren(String categoryId) throws Exception;

    /*查询一级分类下销量前6的商品和图片*/
    ResultVo getPrimaryCategoryWithProductVOById(String categoryId);
}
