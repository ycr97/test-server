package com.yy.quality.model;

import lombok.Data;

/**
 * @author ycr
 * @date 2022/4/8 22:26
 */
@Data
public class HealthyIteration {

    private Integer id;

    private Integer parentId;

    private double finishRate;

    private String elementsName;
}
