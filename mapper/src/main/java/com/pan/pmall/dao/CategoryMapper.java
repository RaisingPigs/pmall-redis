package com.pan.pmall.dao;

import com.pan.pmall.entity.Category;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pan.pmall.pojo.CategoryVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Mr.Pan
 * @description 针对表【category(商品分类)】的数据库操作Mapper
 * @createDate 2022-02-05 12:40:33
 * @Entity com.pan.pmall.entity.Category
 */
public interface CategoryMapper extends BaseMapper<Category> {
    /*连接查询获取二 三级分类*/
    List<CategoryVo> selectChildrenByCategoryId1(@Param("categoryId") String categoryId);

    /*子查询获取二 三级分类*/
    List<CategoryVo> selectChildrenByCategoryId2(@Param("categoryId") String categoryId);

    /*根据一级分类id查询该分类下商品销量前几位的商品及商品图片*/
    CategoryVo selectOneWithProductVOByCategoryId(@Param("categoryId") String categoryId, @Param("start") Integer start, @Param("num") Integer num);
}




