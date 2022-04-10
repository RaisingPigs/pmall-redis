package com.pan.pmall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.data.redis.core.StringRedisTemplate;
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
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    private final ObjectMapper objectMapper = new ObjectMapper();

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
    public ResultVo getProductInfoById(String id) throws Exception {
        /*获取商品*/
        Product product = getProductByProductId(id);
        
        /*如果商品不为空, 获取其他信息*/
        if (product != null) {
            List<ProductImg> productImgs = listProductImgsByProductId(product.getProductId());

            List<ProductSku> productSkus = listProductSkusByProductId(product.getProductId());

            Integer commentCount = getCommentCountByProductId(product.getProductId());

            return ResultVo.success()
                    .add("product", product)
                    .add("productImgs", productImgs)
                    .add("productSkus", productSkus)
                    .add("commentCount", commentCount);
        } else {
            return ResultVo.failed("获取商品信息失败");
        }
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public Product getProductByProductId(String productId) throws JsonProcessingException {
        /*从redis中获取商品信息json, 再将json转为对象*/
        String productJson = (String) stringRedisTemplate.boundHashOps("products").get(productId);

        Product product;

        if (productJson == null) {
            /*如果商品为空, 则从数据库中查询, 并放入redis*/
            QueryWrapper<Product> productQueryWrapper = new QueryWrapper<>();
            productQueryWrapper.lambda()
                    .eq(Product::getProductId, productId)
                    .eq(Product::getProductStatus, 1);

            product = productMapper.selectOne(productQueryWrapper);

            if (product != null) {
                /*如果从数据库中查询的商品不为空, 则放入redis*/
                stringRedisTemplate.boundHashOps("products").put(product.getProductId(), objectMapper.writeValueAsString(product));
            } else {
                /*如果从数据库中查不到商品, 则返回null*/
                return null;
            }
        } else {
            /*如果商品json不为空, 则转换为对象*/
            product = objectMapper.readValue(productJson, Product.class);
        }

        return product;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public List<ProductImg> listProductImgsByProductId(String productId) throws JsonProcessingException {
        /*从redis中获取商品图片信息*/
        String productImgJson = (String) stringRedisTemplate.boundHashOps("productImgs").get(productId);

        List<ProductImg> productImgs;

        if (productImgJson == null) {
            /*如果从redis中查不到, 则通过数据库查询商品的图片*/
            QueryWrapper<ProductImg> imgQueryWrapper = new QueryWrapper<>();
            imgQueryWrapper.lambda()
                    .eq(ProductImg::getItemId, productId);
            productImgs = productImgMapper.selectList(imgQueryWrapper);

            /*查出图片放入redis*/
            stringRedisTemplate.boundHashOps("productImgs").put(productId, objectMapper.writeValueAsString(productImgs));
        } else {
            /*从redis中查到了, 则直接将json转为对象*/
            productImgs = objectMapper.readValue(productImgJson, new TypeReference<List<ProductImg>>() {});
        }

        return productImgs;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public List<ProductSku> listProductSkusByProductId(String productId) throws JsonProcessingException {
        /*从redis中获取商品sku信息*/
        String productSkusJson = (String) stringRedisTemplate.boundHashOps("productSkus").get(productId);

        List<ProductSku> productSkus;

        if (productSkusJson == null) {
            /*如果从redis中查不到, 则通过数据库查询商品的sku*/
            QueryWrapper<ProductSku> skuQueryWrapper = new QueryWrapper<>();
            skuQueryWrapper.lambda()
                    .eq(ProductSku::getProductId, productId)
                    .eq(ProductSku::getStatus, 1);
            productSkus = productSkuMapper.selectList(skuQueryWrapper);

            /*查出sku放入redis*/
            stringRedisTemplate.boundHashOps("productSkus").put(productId, objectMapper.writeValueAsString(productSkus));
        } else {
            /*从redis中查到了, 则直接将json转为对象*/
            productSkus = objectMapper.readValue(productSkusJson, new TypeReference<List<ProductSku>>() {

            });
        }

        return productSkus;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public Integer getCommentCountByProductId(String productId) {
        /*从redis中获取商品评论总数*/
        String commentCountStr = (String) stringRedisTemplate.boundHashOps("commentCounts").get(productId);

        Integer commentCount;

        if (commentCountStr == null) {
            /*如果从redis中查不到, 则通过数据库查询*/
            QueryWrapper<ProductComment> commentQueryWrapper = new QueryWrapper<>();
            commentQueryWrapper.lambda()
                    .eq(ProductComment::getProductId, productId);
            commentCount = productCommentMapper.selectCount(commentQueryWrapper);

            /*查出sku放入redis*/
            stringRedisTemplate.boundHashOps("commentCounts").put(productId, commentCount.toString());
        } else {
            /*从redis中查到了, 则直接转为数字*/
            commentCount = Integer.valueOf(commentCountStr);
        }

        return commentCount;
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
