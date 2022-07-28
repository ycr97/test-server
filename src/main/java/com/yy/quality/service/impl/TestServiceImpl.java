package com.yy.quality.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.google.common.collect.Lists;
import com.yy.quality.common.HttpUtils;
import com.yy.quality.common.JsonResponse;
import com.yy.quality.mapper.TestMapper;
import com.yy.quality.model.*;
import com.yy.quality.service.TestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
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

    @Override
    public void insertData() {
        List<String> data = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            data.add(uuid);
        }
        List<List<String>> partition = Lists.partition(data, 100);
        partition.parallelStream().forEach(temp -> {
            testMapper.insertData(temp);
        });
    }

    @Override
    public void copyData(int pageNum, int pageSize) {
        getAllData(pageNum, pageSize);
    }

    void getAllData(int pageNum, int pageSize) {
        PageResult<List<String>> data = getData(pageNum, pageSize);
        testMapper.insertData1(data.getData());
        int total = data.getTotal();
        int totalPage = total % pageSize == 0 ? total / pageSize : (total / pageSize) + 1;
        if (pageNum < totalPage) {
            getAllData(pageNum + 1, pageSize);
        }
    }

    PageResult<List<String>> getData(int pageNum, int pageSize) {
        int starIndex = (pageNum - 1) * pageSize;
        List<String> allData = testMapper.getAllData(starIndex, pageSize);
        int total = testMapper.getDataTotal();
        PageResult<List<String>> result = new PageResult<>();
        result.setData(allData);
        result.setPageNum(pageNum);
        result.setPageSize(pageSize);
        result.setTotal(total);
        return result;
    }

    @Override
    public HealthyIterationVo getFinalScore() {
        List<HealthyConfig> allConfig = testMapper.getConfig();
        List<HealthyIteration> healthyList = testMapper.getHealthy();
        Map<String, List<HealthyConfig>> configMap =
                allConfig.stream().collect(Collectors.groupingBy(HealthyConfig::getParentIndex));
        int score = 0;
        for (HealthyIteration healthyIteration : healthyList) {
            String index = healthyIteration.getIndex();
            double finishRate = healthyIteration.getFinishRate();
            List<HealthyConfig> configs = configMap.get(index);
            for (HealthyConfig config : configs) {
                int standardScore = Integer.parseInt(config.getSocore());
                if (config.getMinValue() != null) {
                    int minValue = Integer.parseInt(config.getMinValue());
                    if (config.getMaxValue() != null) {
                        int maxValue = Integer.parseInt(config.getMaxValue());
                        if (finishRate >= minValue && finishRate < maxValue) {
                            score += standardScore;
                        }
                    } else {
                        if (finishRate >= minValue) {
                            score += standardScore;
                        }
                    }
                } else {
                    if (config.getMaxValue() != null) {
                        int maxValue = Integer.parseInt(config.getMaxValue());
                        if (finishRate < maxValue) {
                            score += standardScore;
                        }
                    }
                }
            }

        }
        HealthyIterationVo vo = new HealthyIterationVo();
        vo.setData(healthyList);
        vo.setFinalScore(score);
        return vo;
    }


}
