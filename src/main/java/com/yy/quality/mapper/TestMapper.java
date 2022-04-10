package com.yy.quality.mapper;

import com.yy.quality.model.HealthyIteration;
import com.yy.quality.model.Project;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author ycr
 * @date 2022/4/8 21:39
 */
@Mapper
public interface TestMapper {

    /**
     * 新增
     *
     * @param healthyIteration 实体
     */
    void addHealthyIteration(HealthyIteration healthyIteration);

    HealthyIteration getHealthyIterationById(@Param("id") int id);

    List<Project> getAllProject();


}
