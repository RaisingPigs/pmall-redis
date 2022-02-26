package com.pan.pmall.dao;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.pan.pmall.entity.ProductImg;
import com.pan.pmall.entity.ProductSku;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * @description:
 * @author: Mr.Pan
 * @create: 2022-02-09 20:45
 **/
@SpringBootTest
public class ProductSkuTest {
    @Resource
    private ProductSkuMapper productSkuMapper;
    @Resource
    private ProductImgMapper productImgMapper;

    @Test
    void test() {
        Random random = new Random();

        //for (int j = 1; j <= 282; j++) {
          /*  String productId = "100004";
            String skuName;
            String skuImg;
            List<ProductImg> productImgs = productImgMapper.selectList(new QueryWrapper<ProductImg>().lambda().eq(ProductImg::getItemId, productId));
            String untitled;

            int randomInt = random.nextInt(10);
            int price = random.nextInt(1000) + 1;
            BigDecimal originalPrice;
            BigDecimal sellPrice;
            BigDecimal discount;
            if (randomInt % 2 == 0) {
                float discountInt = (float) ((random.nextInt(6) + 4) * 0.1);
                originalPrice = new BigDecimal(price);
                sellPrice = new BigDecimal((int) (price * discountInt));
                discount = new BigDecimal(discountInt);
            } else {
                originalPrice = new BigDecimal(price);
                sellPrice = originalPrice;
                discount = new BigDecimal(1);
            }

            Integer stock = random.nextInt(1200) + 20;
            long l = System.currentTimeMillis() - (random.nextInt(10) * 50000000);
            Date time = new Date(l);
            Integer status = 1;
            skuName = "单包";
            skuImg = productImgs.get(0).getUrl();
            untitled = "[{\"\"key\"\":\"\"口味\"\",\"\"value\"\":[\"\"原味\"\",\"\"奶油\"\",\"\"芥末\"\"]},{\"\"key\"\":\"\"包装\"\",\"\"value\"\":[\"\"袋装\"\",\"\"盒装\"\n" +
                    "                    \"]}]";


            ProductSku productSku1 = new ProductSku(null, productId, skuName, skuImg, untitled, originalPrice, sellPrice, discount, stock, time, time, status);
            productSkuMapper.insert(productSku1);

            skuName = "整箱装(10包)";
            skuImg = productImgs.get(1).getUrl();
            untitled = "[{\"\"key\"\":\"\"口味\"\",\"\"value\"\":[\"\"原味\"\",\"\"香肠\"\",\"\"孜然\"\"]}]";
            stock = random.nextInt(1200) + 20;

            randomInt = random.nextInt(10);
            price = price * 10;

            if (randomInt % 2 == 0) {
                float discountInt = (float) ((random.nextInt(6) + 4) * 0.1);
                originalPrice = new BigDecimal(price);
                sellPrice = new BigDecimal((int) (price * discountInt));
                discount = new BigDecimal(discountInt);
            } else {
                originalPrice = new BigDecimal(price);
                sellPrice = originalPrice;
                discount = new BigDecimal(1);
            }

            ProductSku productSku2 = new ProductSku(null, productId, skuName, skuImg, untitled, originalPrice, sellPrice, discount, stock, time, time, status);
            productSkuMapper.insert(productSku2);*/
        //}
    }


}
