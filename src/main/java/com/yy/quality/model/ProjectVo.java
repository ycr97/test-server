package com.yy.quality.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author ycr
 * @date 2022/4/9 14:45
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ProjectVo extends Project {

    private List<ProjectVo> childList;
}
