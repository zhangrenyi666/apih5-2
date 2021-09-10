package com.apih5.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import com.apih5.framework.annotation.RequireToken;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.BaseProjectConfJob;
import com.apih5.service.BaseProjectConfJobService;

@RestController
public class BaseProjectConfJobController {

    @Autowired(required = true)
    private BaseProjectConfJobService baseProjectConfJobService;

    @ApiOperation(value="查询项目人员配置岗位表", notes="查询项目人员配置岗位表")
    @ApiImplicitParam(name = "baseProjectConfJob", value = "项目人员配置岗位表entity", dataType = "BaseProjectConfJob")
    @RequireToken
    @PostMapping("/getBaseProjectConfJobList")
    public ResponseEntity getBaseProjectConfJobList(@RequestBody(required = false) BaseProjectConfJob baseProjectConfJob) {
        return baseProjectConfJobService.getBaseProjectConfJobListByCondition(baseProjectConfJob);
    }

    @ApiOperation(value="查询详情项目人员配置岗位表", notes="查询详情项目人员配置岗位表")
    @ApiImplicitParam(name = "baseProjectConfJob", value = "项目人员配置岗位表entity", dataType = "BaseProjectConfJob")
    @RequireToken
    @PostMapping("/getBaseProjectConfJobDetail")
    public ResponseEntity getBaseProjectConfJobDetail(@RequestBody(required = false) BaseProjectConfJob baseProjectConfJob) {
        return baseProjectConfJobService.getBaseProjectConfJobDetail(baseProjectConfJob);
    }

    @ApiOperation(value="新增项目人员配置岗位表", notes="新增项目人员配置岗位表")
    @ApiImplicitParam(name = "baseProjectConfJob", value = "项目人员配置岗位表entity", dataType = "BaseProjectConfJob")
    @RequireToken
    @PostMapping("/addBaseProjectConfJob")
    public ResponseEntity addBaseProjectConfJob(@RequestBody(required = false) BaseProjectConfJob baseProjectConfJob) {
        return baseProjectConfJobService.saveBaseProjectConfJob(baseProjectConfJob);
    }

    @ApiOperation(value="更新项目人员配置岗位表", notes="更新项目人员配置岗位表")
    @ApiImplicitParam(name = "baseProjectConfJob", value = "项目人员配置岗位表entity", dataType = "BaseProjectConfJob")
    @RequireToken
    @PostMapping("/updateBaseProjectConfJob")
    public ResponseEntity updateBaseProjectConfJob(@RequestBody(required = false) BaseProjectConfJob baseProjectConfJob) {
        return baseProjectConfJobService.updateBaseProjectConfJob(baseProjectConfJob);
    }

    @ApiOperation(value="删除项目人员配置岗位表", notes="删除项目人员配置岗位表")
    @ApiImplicitParam(name = "baseProjectConfJobList", value = "项目人员配置岗位表List", dataType = "List<BaseProjectConfJob>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateBaseProjectConfJob")
    public ResponseEntity batchDeleteUpdateBaseProjectConfJob(@RequestBody(required = false) List<BaseProjectConfJob> baseProjectConfJobList) {
        return baseProjectConfJobService.batchDeleteUpdateBaseProjectConfJob(baseProjectConfJobList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
