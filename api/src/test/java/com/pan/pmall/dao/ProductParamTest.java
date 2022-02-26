package com.pan.pmall.dao;

import com.pan.pmall.entity.ProductParam;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Random;

/**
 * @description:
 * @author: Mr.Pan
 * @create: 2022-02-10 10:27
 **/
@SpringBootTest
public class ProductParamTest {
    @Resource
    private ProductParamMapper productParamMapper;

    @Test
    @Disabled
    void testAddProductParam() {
        /*String[] address = {"辽宁沈阳", "吉林长春", "湖北武汉", "四川成都", "江苏南京", "河北张家口", "云南昆明", "福建厦门", "河南平顶山", "广东汕头"};
        String[] packageMethod = {"袋装", "盒装", "包装", "罐装", "塑封", "封口", "箱装", "卷装", "裹包", "瓶装"};
        Random random = new Random();

        for (int i = 100002; i <= 100004; i++) {
            int index = random.nextInt(10);

            String realAddress = address[index].substring(0, 2) + "省" + address[index].substring(2) + "市";
            String company = address[index] + "某某有限责任公司";
            String detailAddress = address[index] + "xxx区xxx路xx号x幢x楼";
            String realPackageMethod = packageMethod[index];
            String weight = random.nextInt(5000) + 500 + "g";
            String foodPeried = random.nextInt(100) + 20 + "天";

            long l = System.currentTimeMillis() - (random.nextInt(10) * 50000000);
            Date time = new Date(l);

            ProductParam productParam = new ProductParam(null, i+"", realAddress, foodPeried, "良品铺子", company, detailAddress, realPackageMethod, weight, "避免高温阳光直射，常温5°~25°储存", "开袋即食", time, time);
            productParamMapper.insert(productParam);
        }*/
    }
}
