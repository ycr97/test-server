package com.yy.quality.service;

import com.yy.quality.model.HealthyIteration;
import com.yy.quality.model.ProjectVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author ycr
 * @date 2022/4/8 21:37
 */
public interface TestService {

    HealthyIteration getHealthyIterationById(@Param("id") int id);

    List<ProjectVo> getProjectVo();

    ProjectVo remoteCall();
}
