package com.pan.pmall.dao;

import com.pan.pmall.entity.ProductImg;
import com.pan.pmall.entity.ProductSku;
import com.pan.pmall.pojo.ProductVo;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

/**
 * @description:
 * @author: Mr.Pan
 * @create: 2022-02-07 20:31
 **/
@SpringBootTest
public class ProductMapperTest {
    @Resource
    private ProductMapper productMapper;

    @Resource
    private ProductImgMapper productImgMapper;

    @Resource
    private CategoryMapper categoryMapper;

    @Test
    void testSelectProductsWithImgs() {
        //List<ProductVO> products = productMapper.selectProductsWithImgs(0, 3);
        //for (ProductVO product : products) {
        //    System.out.println(product);
        //}
    }

    @Test
    void testAddCategory() {
        //int cId = 42;
        //int pId = 13;
        //
        //for (int i = 13; i <= 30; i++) {
        //    for (int j = 1; j <= 2; j++) {
        //        Integer categoryId = cId;
        //        String categoryName = "分类测试" + cId;
        //        Integer categoryLevel = 3;
        //        Integer parentId = pId;
        //
        //        Category category = new Category(categoryId, categoryName, categoryLevel, parentId, null, null, null, null);
        //        categoryMapper.insert(category);
        //
        //        cId++;
        //    }
        //
        //    pId++;
        //}
    }

    @Test
    void testAddProduct() {
        //Random random = new Random();
        //int pNum = 67;
        //int count = 0;
        //int rId = 2;
        //
        //for (int j = 42; j <= 77; j++) {
        //    if (count == 4) {
        //        rId++;
        //        count = 0;
        //    }
        //    for (int i = 0; i < 6; i++) {
        //        String productName = "商品测试" + pNum;
        //        Integer categoryId = j;
        //        Integer rootCategoryId = rId;
        //        Integer soldNum = random.nextInt(2000) + 20;
        //        Integer productStatus = 1;
        //        String content = productName + "的商品内容";
        //        long l = System.currentTimeMillis() - (random.nextInt(10) * 50000000);
        //        Date time = new Date(l);
        //
        //        Product product = new Product(null, productName, categoryId, rootCategoryId, soldNum, productStatus, content, time, time);
        //        productMapper.insert(product);
        //
        //        pNum++;
        //    }
        //    count++;
        //}

    }

    /*商品数为1-282*/
    @Test
    void testAddProductImg() {
       /* Random random = new Random();

        for (int j = 1; j <= 282; j++) {
            for (int i = 1; i <= 2; i++) {
                String itemId = String.valueOf(j);
                String url = random.nextInt(10) + 1 + ".jpg";
                Integer sort = i;
                Integer isMain = i == 1 ? 1 : 0;
                long l = System.currentTimeMillis() - (random.nextInt(10) * 50000000);
                Date time = new Date(l);

                ProductImg productImg = new ProductImg(null, itemId, url, sort, isMain, time, time);
                productImgMapper.insert(productImg);
            }
        }
*/
    }

    @Test
    @Disabled
    void testSelectProductsWithImgsAndSkusByCategoryId() {
        List<ProductVo> productVos = productMapper.selectProductsWithImgsAndSkusByCategoryId("77", "", "min_sell_price", "desc");

        /*for (ProductVo productVo : productVos) {
            System.out.println(productVo);
            for (ProductImg productImg : productVo.getProductImgs()) {
                System.out.println("\t" + productImg);
            }
            for (ProductSku productSku : productVo.getProductSkus()) {
                System.out.println("\t" + productSku);
            }
        }*/
        for (ProductVo productVo : productVos) {
            for (ProductSku productSkus : productVo.getProductSkus()) {
                System.out.println(productSkus.getSellPrice());
            }
        }
    }


}
