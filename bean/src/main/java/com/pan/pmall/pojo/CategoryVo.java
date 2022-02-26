package com.pan.pmall.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 商品分类
 * @TableName category
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryVo {
    /* 主键;分类id主键 */
    private String categoryId;

    /* 分类名称;分类名称 */
    private String categoryName;

    /**
     * 分类层级;分类得类型，
     * 1:一级大分类
     * 2:二级分类
     * 3:三级小分类
     */
    private Integer categoryLevel;

    /* 父层级id;父id 上一级依赖的id，1级分类则为0，二级三级分别依赖上一级 */
    private String parentId;

    /* 图标;logo */
    private String categoryIcon;

    /* 口号 */
    private String categorySlogan;

    /* 分类图 */
    private String categoryPic;

    /* 背景颜色 */
    private String categoryBgColor;

    /* 用来封装二级分类 */
    private List<CategoryVo> categories;

    /* 用来查询分类下的商品 */
    private List<ProductVo> products;
}