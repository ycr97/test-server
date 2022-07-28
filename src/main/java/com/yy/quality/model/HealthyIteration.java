package com.yy.quality.model;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author ycr
 * @date 2022/4/8 22:26
 */
@Data
public class HealthyIteration {

    private Integer id;

    private String index;

    private double finishRate;

    private String elementsName;

    private String date;
}
