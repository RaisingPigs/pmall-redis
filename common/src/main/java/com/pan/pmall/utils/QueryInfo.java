package com.pan.pmall.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description:
 * @author: Mr.Pan
 * @create: 2022-02-10 20:43
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QueryInfo {
    private String query;
    private Integer pagenum;
    private Integer pagesize;
}
