package com.yy.quality.mapper;

import com.yy.quality.model.HealthyConfig;
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

    void insertData(@Param("data") List<String> data);

    void insertData1(@Param("data") List<String> data);

    List<String> getAllData(@Param("startIndex") int startIndex, @Param("pageSize") int pageSize);

    int getDataTotal();


    List<HealthyConfig> getConfig();

    List<HealthyIteration> getHealthy();
}
