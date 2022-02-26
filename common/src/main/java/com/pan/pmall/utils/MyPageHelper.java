package com.pan.pmall.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.List;

/**
 * @description:
 * @author: Mr.Pan
 * @create: 2022-02-11 08:17
 **/
@Data
@NoArgsConstructor
public class MyPageHelper<T> {
    /*当前页*/
    private int pagenum;
    /*页大小*/
    private int pagesize;
    /*总记录数*/
    private int total;
    /*总页数*/
    private int pages;
    /*分页数据*/
    private List<T> list;
    /*是否为第一页*/
    private boolean isFirstPage;
    /*是否为最后一页*/
    private boolean isLastPage;
    /*活动页为几页*/
    private int navigatePages;
    /*具体活动页*/
    private int[] navigatePagenums;

    public MyPageHelper(int pagenum, int pagesize, int total, List<T> list, int navigatePages) {
        setPagenum(pagenum);
        this.pagesize = pagesize;
        this.total = total;
        this.list = list;
        this.pages = getPages();
        this.isFirstPage = isFirstPage();
        this.isLastPage = isLastPage();
        this.navigatePages = navigatePages;
        this.navigatePagenums = getNavigatePagenums();
    }

    public void setPagenum(int pagenum) {
        if (pagenum < 1) {
            pagenum = 1;
        }
        this.pagenum = pagenum;
    }

    /*根据总记录数和页大小算出总页数*/
    public int getPages() {
        return total / pagesize + (total % pagesize == 0 ? 0 : 1);
    }

    /*算出是否为第一页*/
    public boolean isFirstPage() {
        return pagenum == 1;
    }

    /*算出是否为最后一页*/
    public boolean isLastPage() {
        return pagenum == pages;
    }

    /*计算活动页*/
    public int[] getNavigatePagenums() {
        int[] arr = new int[navigatePages];
        int mid = navigatePages / 2;
        if (navigatePages % 2 != 0) {
            mid++;
        }

        if (pages < navigatePages) {
            for (int i = 0; i < pages; i++) {
                arr[i] = i + 1;
            }
        } else {
            if (pagenum <= mid) {
                for (int i = 0; i < navigatePages; i++) {
                    arr[i] = i + 1;
                }
            } else if (pagenum > pages - mid) {
                int index = 0;
                for (int i = pages - navigatePages + 1; i <= pages; i++) {
                    arr[index++] = i;
                }
            } else {
                int index = 0;
                for (int i = pagenum - mid+1; i < pagenum + mid; i++) {
                    arr[index++] = i;
                }
            }
        }

        return arr;
    }
}
