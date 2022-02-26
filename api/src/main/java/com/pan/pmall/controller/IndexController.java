package com.pan.pmall.controller;

import com.pan.pmall.service.CategoryService;
import com.pan.pmall.service.IndexImgService;
import com.pan.pmall.service.ProductService;
import com.pan.pmall.vo.ResultVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @description:
 * @author: Mr.Pan
 * @create: 2022-02-06 21:34
 **/
@RestController
@RequestMapping("/index")
@CrossOrigin
@Api(value = "提供首页相关业务的接口", tags = "首页管理")
public class IndexController {
    @Resource
    private IndexImgService indexImgService;
    @Resource
    private CategoryService categoryService;
    @Resource
    private ProductService productService;

    @GetMapping("/indexImgs")
    @ApiOperation("首页轮播图查询接口")
    public ResultVo getIndexImgs() {
        return indexImgService.listIndexImgs();
    }

    @GetMapping("/categories")
    @ApiOperation("商品分类信息查询接口")
    public ResultVo getCategories(@RequestParam(value = "level", required = false) String level,
                                  @RequestParam(value = "id", required = false) String id) {

        if ("2,3".equals(level) && id != null) {
            return categoryService.listCategoriesWithChildren(id);
        } else {
            return categoryService.listCategories(level, id);
        }
    }

    @GetMapping("/recommend")
    @ApiOperation("推荐商品查询接口")
    public ResultVo getRecommendProducts() {
        return productService.listRecommendProductsWithImgs(0, 3);
    }

    @GetMapping("/top6/{id}")
    @ApiOperation("各类商品top6查询接口")
    public ResultVo getProductsTop6ByCategory(@PathVariable String id) {
        return categoryService.getPrimaryCategoryWithProductVOById(id);
    }
}
