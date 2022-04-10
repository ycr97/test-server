package com.yy.quality.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

/**
 * @author ycr
 * @date 2022/4/9 14:44
 */
@Data
public class Project {
    private Integer id;

    private String productLine;

    private String target;

    @JsonIgnore
    private Integer parentId;

    private String description;

}
