package com.pan.pmall.utils;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Arrays;

/**
 * @description:
 * @author: Mr.Pan
 * @create: 2022-02-11 09:45
 **/
public class MyPageHelperTest {
    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12,13,14,15,16})
    @Disabled
    void test(int i) {
        MyPageHelper myPageHelper = new MyPageHelper(i, 5, 76, null, 7);

        System.out.println(Arrays.toString(myPageHelper.getNavigatePagenums()));
    }
}
