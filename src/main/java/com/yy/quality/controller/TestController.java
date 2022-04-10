package com.yy.quality.controller;

import com.yy.quality.common.JsonResponse;
import com.yy.quality.model.HealthyIteration;
import com.yy.quality.model.ProjectVo;
import com.yy.quality.service.TestService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.logging.Logger;

/**
 * @author ycr
 * @date 2022/4/8 21:34
 */
@RestController
@RequestMapping("/test")
@Slf4j
public class TestController {

    @Resource
    private TestService testService;


    @GetMapping("/test/{str}")
    public JsonResponse<String> getStr(@PathVariable("str") String str) {
        log.info(str);
        return JsonResponse.success(str);
    }

    @GetMapping("/{id}")
    public JsonResponse<HealthyIteration> getHealthyIterationById(@PathVariable("id") int id){
        return JsonResponse.success(testService.getHealthyIterationById(id));
    }

    @GetMapping("/tree")
    public JsonResponse<List<ProjectVo>> getTree() {
        List<ProjectVo> vos = testService.getProjectVo();
        return JsonResponse.success(vos);
    }

    @GetMapping("/remote")
    public JsonResponse<ProjectVo> remote() {
        ProjectVo result = testService.remoteCall();
        return JsonResponse.success(result);
    }


}
