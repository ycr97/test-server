package com.yy.quality.model;

import lombok.Data;

import java.util.List;

/**
 * @author ycr
 * @date 2022/6/15 21:20
 */

@Data
public class Menu {

    private Integer id;

    private Integer parentId;

    private String  name;

    private String includeTo;

    private List<Menu> children;

}
