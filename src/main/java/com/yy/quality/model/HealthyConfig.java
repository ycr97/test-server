package com.yy.quality.model;

import lombok.Data;

/**
 * @author ycr
 * @date 2022/4/21 23:08
 */
@Data
public class HealthyConfig {

    private String id;
    private String index;
    private String socore;
    private String minValue;
    private String maxValue;
    private String nextId;
    private String parentIndex;

}
