package com.pan.pmall.service;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * @description:
 * @author: Mr.Pan
 * @create: 2022-02-07 17:44
 **/
@SpringBootTest
public class CategoryServiceTest {
    @Resource
    private CategoryService categoryService;
    
    @Test
    void testListCategoriesWithChildren() {
        //ResultVO resultVO = categoryService.listCategoriesWithChildren(1);
        //
        //System.out.println(resultVO);
    }
}
