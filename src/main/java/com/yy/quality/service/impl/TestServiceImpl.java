package com.yy.quality.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.yy.quality.common.HttpUtils;
import com.yy.quality.common.JsonResponse;
import com.yy.quality.mapper.TestMapper;
import com.yy.quality.model.HealthyIteration;
import com.yy.quality.model.Project;
import com.yy.quality.model.ProjectVo;
import com.yy.quality.service.TestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author ycr
 * @date 2022/4/8 21:38
 */
@Service
@Slf4j
public class TestServiceImpl implements TestService {

    @Resource
    private TestMapper testMapper;

    @Override
    public HealthyIteration getHealthyIterationById(int id) {
        return testMapper.getHealthyIterationById(id);
    }

    @Override
    public List<ProjectVo> getProjectVo() {
        List<Project> allProject = testMapper.getAllProject();
        List<ProjectVo> result = allProject.stream()
                .filter(item -> item.getParentId() == -1)
                .map(item -> {
                    ProjectVo vo = new ProjectVo();
                    vo.setId(item.getId());
                    vo.setProductLine(item.getProductLine());
                    vo.setChildList(getChildList(item, allProject));
                    return vo;
                }).collect(Collectors.toList());
        return result;
    }

    private List<ProjectVo> getChildList(Project item, List<Project> allProject) {
        return allProject.stream()
                .filter(temp -> temp.getParentId().equals(item.getId()))
                .map(temp -> {
                    ProjectVo vo = new ProjectVo();
                    vo.setId(temp.getId());
                    vo.setDescription(temp.getDescription());
                    vo.setProductLine(temp.getProductLine());
                    vo.setTarget(temp.getTarget());
                    vo.setChildList(getChildList(temp, allProject));
                    return vo;
                }).collect(Collectors.toList());
    }

    @Override
    public ProjectVo remoteCall() {
        HttpUtils instance = HttpUtils.getInstance();
        String result = instance.httpGet("http://120.79.170.87:8001/test/tree");
        JSONObject map = JSON.parseObject(result, JSONObject.class);
        Integer code = (Integer) map.get("code");
        ProjectVo vo = null;
        if (code == 200) {
            JsonResponse<List<ProjectVo>> jsonObject = JSONObject.parseObject(result,
                    new TypeReference<JsonResponse<List<ProjectVo>>>() {
            });
            for (ProjectVo datum : jsonObject.getData()) {
                System.out.println(datum);
            }
            List<ProjectVo> data = jsonObject.getData();
            vo = getResult(data, "淘宝");
        } else {
            String msg = (String) map.get("msg");
            log.error(msg);
        }

        return vo;
    }

    public ProjectVo getResult(List<ProjectVo> vos, String key) {
        ProjectVo result = null;
        if (key == null || key.isEmpty()) {
            return null;
        }
        for (ProjectVo vo : vos) {
            if (key.equals(vo.getProductLine())) {
                return vo;
            } else {
                return getResult(vo.getChildList(), key);
            }
        }
        return result;
    }
}
