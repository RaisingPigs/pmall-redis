package com.pan.pmall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.Query;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pan.pmall.dao.ProductCommentMapper;
import com.pan.pmall.dao.ProductImgMapper;
import com.pan.pmall.dao.ProductMapper;
import com.pan.pmall.dao.ProductSkuMapper;
import com.pan.pmall.entity.Product;
import com.pan.pmall.entity.ProductComment;
import com.pan.pmall.entity.ProductImg;
import com.pan.pmall.entity.ProductSku;
import com.pan.pmall.pojo.ProductVo;
import com.pan.pmall.service.ProductService;
import com.pan.pmall.utils.QueryInfo;
import com.pan.pmall.vo.ResultVo;
import org.ietf.jgss.Oid;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/**
 * @description:
 * @author: Mr.Pan
 * @create: 2022-02-07 20:44
 **/
@Service
public class ProductServiceImpl implements ProductService {
    @Resource
    private ProductMapper productMapper;
    @Resource
    private ProductImgMapper productImgMapper;
    @Resource
    private ProductSkuMapper productSkuMapper;
    @Resource
    private ProductCommentMapper productCommentMapper;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public ResultVo listRecommendProductsWithImgs(Integer start, Integer num) {
        List<ProductVo> products = productMapper.selectProductsWithImgsByCreateTime(0, 3);

        if (!products.isEmpty()) {
            return ResultVo.success("获取推荐商品成功").add("products", products);
        } else {
            return ResultVo.failed("获取推荐商品失败");
        }
    }

    @Override
    /*由于这里是一系列查询, 所以可以将事务传播机制设置为Propagation.SUPPORTS*/
    @Transactional(propagation = Propagation.SUPPORTS)
    public ResultVo getProductInfoById(String id) {
        
        QueryWrapper<Product> productQueryWrapper = new QueryWrapper<>();
        productQueryWrapper.lambda()
                .eq(Product::getProductId, id)
                .eq(Product::getProductStatus, 1);

        Product product = productMapper.selectOne(productQueryWrapper);

        /*如果商品不为空, 才去查询商品的图片和sku和评价总数*/
        if (product != null) {
            QueryWrapper<ProductImg> imgQueryWrapper = new QueryWrapper<>();
            imgQueryWrapper.lambda()
                    .eq(ProductImg::getItemId, id);
            List<ProductImg> productImgs = productImgMapper.selectList(imgQueryWrapper);

            QueryWrapper<ProductSku> skuQueryWrapper = new QueryWrapper<>();
            skuQueryWrapper.lambda()
                    .eq(ProductSku::getProductId, id)
                    .eq(ProductSku::getStatus, 1);
            List<ProductSku> productSkus = productSkuMapper.selectList(skuQueryWrapper);

            QueryWrapper<ProductComment> commentQueryWrapper = new QueryWrapper<>();
            commentQueryWrapper.lambda()
                    .eq(ProductComment::getProductId, id);
            Integer commentCount = productCommentMapper.selectCount(commentQueryWrapper);

            if (!productImgs.isEmpty() && !productSkus.isEmpty()) {
                return ResultVo.success()
                        .add("product", product)
                        .add("productImgs", productImgs)
                        .add("productSkus", productSkus)
                        .add("commentCount", commentCount);
            } else {
                return ResultVo.failed("查询商品失败");
            }

        } else {
            return ResultVo.failed("查询商品不存在");
        }
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public ResultVo listProductsPageByCategoryId(String categoryId, QueryInfo queryInfo, String orderByCol, String seq) {
        PageHelper.startPage(queryInfo.getPagenum(), queryInfo.getPagesize());
        List<ProductVo> products = productMapper.selectProductsWithImgsAndSkusByCategoryId(categoryId, queryInfo.getQuery(), orderByCol, seq);

        PageInfo<ProductVo> pageInfo = new PageInfo<>(products);

        return ResultVo.success().add("pageInfo", pageInfo);
    }

    @Override
    public ResultVo listBrandsByCategoryId(String categoryId) {
        List<String> brands = productMapper.selectBrandsByCategoryId(categoryId);

        return ResultVo.success().add("brands", brands);
    }

    @Override
    public ResultVo listProductsPageLikeProductName(QueryInfo queryInfo, String categoryId, String brand, String orderByCol, String seq) {
        PageHelper.startPage(queryInfo.getPagenum(), queryInfo.getPagesize());
        List<ProductVo> products = productMapper.selectProductsWithImgsAndSkusLikeProductName(queryInfo.getQuery(), categoryId, brand, orderByCol, seq);
        PageInfo<ProductVo> pageInfo = new PageInfo<>(products);

        return ResultVo.success().add("pageInfo", pageInfo);
    }

    @Override
    public ResultVo listBrandsLikeProductName(String query) {
        List<String> brands = productMapper.selectBrandsLikeProductName(query);

        return ResultVo.success().add("brands", brands);
    }


}
