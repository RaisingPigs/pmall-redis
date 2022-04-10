package com.pan.pmall.controller;

import com.pan.pmall.entity.ProductComment;
import com.pan.pmall.service.ProductCommentService;
import com.pan.pmall.service.ProductParamService;
import com.pan.pmall.service.ProductService;
import com.pan.pmall.utils.QueryInfo;
import com.pan.pmall.vo.ResultVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @description:
 * @author: Mr.Pan
 * @create: 2022-02-09 12:21
 **/
@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/product")
@Api(value = "商品业务相关接口", tags = "商品管理")
public class ProductController {
    @Resource
    private ProductService productService;
    @Resource
    private ProductParamService productParamService;
    @Resource
    private ProductCommentService productCommentService;

    @ApiOperation("商品信息获取接口")
    @GetMapping("/info/{id}")
    public ResultVo getProductInfo(@PathVariable("id") String id) {
        try {
            return productService.getProductInfoById(id);
        } catch (Exception e) {
            log.error("", e);
            return ResultVo.failed("获取商品信息失败");
        }
    }

    @ApiOperation("商品参数获取接口")
    @GetMapping("/param/{pid}")
    public ResultVo getProductParam(@PathVariable String pid) {
        return productParamService.getProductParamByProductId(pid);
    }

    @ApiOperation("商品评论分页获取接口")
    @GetMapping("/comment/{pid}")
    public ResultVo getProductComment(@PathVariable String pid,
                                      QueryInfo queryInfo) {
        return productCommentService.listCommentsByProductId(pid, queryInfo);
    }

    @ApiOperation("商品评论计数获取接口")
    @GetMapping("/comment-count/{pid}")
    public ResultVo getProductCommentCount(@PathVariable String pid) {
        return productCommentService.getCommentCount(pid);
    }

    @ApiOperation("商品评论自定义分页获取接口")
    @GetMapping("/comment-page/{pid}")
    public ResultVo getCommentPage(@PathVariable String pid,
                                   QueryInfo queryInfo) {
        return productCommentService.listCommentPageByProductId(pid, queryInfo);
    }

    @ApiOperation("根据分类获取商品分页接口")
    @GetMapping("/by-category-id/{categoryId}")
    public ResultVo getProductsPageByCategoryId(@PathVariable String categoryId,
                                                QueryInfo queryInfo,
                                                @RequestParam("orderByCol") String orderByCol,
                                                @RequestParam("seq") String seq) {

        return productService.listProductsPageByCategoryId(categoryId, queryInfo, orderByCol, seq);
    }

    @ApiOperation("获取某分类下所有品牌的接口")
    @GetMapping("/brands/{categoryId}")
    public ResultVo getBrandsByCategoryId(@PathVariable String categoryId) {
        return productService.listBrandsByCategoryId(categoryId);
    }

    @ApiOperation("商品搜索")
    @GetMapping("/search")
    public ResultVo searchProductsPage(QueryInfo queryInfo,
                                       @RequestParam(value = "categoryId", required = false) String categoryId,
                                       @RequestParam(value = "brand", required = false) String brand,
                                       @RequestParam(value = "orderByCol", required = false) String orderByCol,
                                       @RequestParam(value = "seq", required = false) String seq) {

        return productService.listProductsPageLikeProductName(queryInfo, categoryId, brand, orderByCol, seq);
    }

    @ApiOperation("获取某商品所属品牌的接口")
    @GetMapping("/brands")
    public ResultVo getBrandsLikeProductName(@RequestParam("query") String query) {
        return productService.listBrandsLikeProductName(query);
    }

    @PostMapping("/comment/add")
    public ResultVo addComment(@RequestBody ProductComment productComment,
                               @RequestParam String orderId) {
        System.out.println(productComment);

        return productCommentService.addComment(productComment, orderId);
    }
}
