package com.yy.quality.model;

import lombok.Data;

/**
 * @author ycr
 * @date 2022/4/20 23:15
 */
@Data
public class PageResult<T> {
    private int pageNum;

    private int pageSize;

    private int total;

    private T data;
}
