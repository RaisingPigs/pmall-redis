package com.pan.pmall.pojo;

import com.pan.pmall.entity.Product;
import com.pan.pmall.entity.ProductImg;
import com.pan.pmall.entity.ProductParam;
import com.pan.pmall.entity.ProductSku;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 商品;商品信息相关表：分类表，商品图片表，商品规格表，商品参数表
 * @TableName product
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductVo extends Product {
    /* 商品图片 */
    private List<ProductImg> productImgs;
    /*商品规格信息*/
    private List<ProductSku> productSkus;
    /*商品参数信息*/
    private ProductParam productParams;
}