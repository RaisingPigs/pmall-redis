package com.pan.pmall.dao;

import com.pan.pmall.entity.ProductImg;
import com.pan.pmall.pojo.CategoryVo;
import com.pan.pmall.pojo.ProductVo;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * @description:
 * @author: Mr.Pan
 * @create: 2022-02-07 16:28
 **/
@SpringBootTest
public class CategoryMapperTest {
    @Resource
    private CategoryMapper categoryMapper;
    
    @Test
    void testSelectChildrenByCategoryId1() {
        //List<CategoryVO> categories = categoryMapper.selectChildrenByCategoryId1(1);
        //
        //for (CategoryVO category : categories) {
        //    System.out.println(category);
        //}
    }
    
    @Test
    void testSelectChildrenByCategoryId2() {
        //List<CategoryVO> categories = categoryMapper.selectChildrenByCategoryId2(1);
        //
        //for (CategoryVO category : categories) {
        //    System.out.println(category);
        //}
    }
    
    @Test
    @Disabled
    void testSelectOneWithProductVOByCategoryId() {
        CategoryVo categoryVO = categoryMapper.selectOneWithProductVOByCategoryId("1", 0, 6);

        for (CategoryVo category : categoryVO.getCategories()) {
            System.out.println(category);
        }
        
        for (ProductVo product : categoryVO.getProducts()) {
            System.out.println(product);
            for (ProductImg productImg : product.getProductImgs()) {
                System.out.println("\t" + productImg);
            }
        }
    }
    
    
}
