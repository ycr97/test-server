package com.yy.quality.model;

import lombok.Data;

import java.util.List;

/**
 * @author ycr
 * @date 2022/4/8 22:26
 */
@Data
public class HealthyIterationVo {

    List<HealthyIteration> data;

    int finalScore;
}
