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
import com.apih5.mybatis.pojo.BaseJobLevelSub;
import com.apih5.service.BaseJobLevelSubService;

@RestController
public class BaseJobLevelSubController {

    @Autowired(required = true)
    private BaseJobLevelSubService baseJobLevelSubService;

    @ApiOperation(value="查询岗位等级子表", notes="查询岗位等级子表")
    @ApiImplicitParam(name = "baseJobLevelSub", value = "岗位等级子表entity", dataType = "BaseJobLevelSub")
    @RequireToken
    @PostMapping("/getBaseJobLevelSubList")
    public ResponseEntity getBaseJobLevelSubList(@RequestBody(required = false) BaseJobLevelSub baseJobLevelSub) {
        return baseJobLevelSubService.getBaseJobLevelSubListByCondition(baseJobLevelSub);
    }

    @ApiOperation(value="查询详情岗位等级子表", notes="查询详情岗位等级子表")
    @ApiImplicitParam(name = "baseJobLevelSub", value = "岗位等级子表entity", dataType = "BaseJobLevelSub")
    @RequireToken
    @PostMapping("/getBaseJobLevelSubDetail")
    public ResponseEntity getBaseJobLevelSubDetail(@RequestBody(required = false) BaseJobLevelSub baseJobLevelSub) {
        return baseJobLevelSubService.getBaseJobLevelSubDetail(baseJobLevelSub);
    }

    @ApiOperation(value="新增岗位等级子表", notes="新增岗位等级子表")
    @ApiImplicitParam(name = "baseJobLevelSub", value = "岗位等级子表entity", dataType = "BaseJobLevelSub")
    @RequireToken
    @PostMapping("/addBaseJobLevelSub")
    public ResponseEntity addBaseJobLevelSub(@RequestBody(required = false) BaseJobLevelSub baseJobLevelSub) {
        return baseJobLevelSubService.saveBaseJobLevelSub(baseJobLevelSub);
    }

    @ApiOperation(value="更新岗位等级子表", notes="更新岗位等级子表")
    @ApiImplicitParam(name = "baseJobLevelSub", value = "岗位等级子表entity", dataType = "BaseJobLevelSub")
    @RequireToken
    @PostMapping("/updateBaseJobLevelSub")
    public ResponseEntity updateBaseJobLevelSub(@RequestBody(required = false) BaseJobLevelSub baseJobLevelSub) {
        return baseJobLevelSubService.updateBaseJobLevelSub(baseJobLevelSub);
    }

    @ApiOperation(value="删除岗位等级子表", notes="删除岗位等级子表")
    @ApiImplicitParam(name = "baseJobLevelSubList", value = "岗位等级子表List", dataType = "List<BaseJobLevelSub>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateBaseJobLevelSub")
    public ResponseEntity batchDeleteUpdateBaseJobLevelSub(@RequestBody(required = false) List<BaseJobLevelSub> baseJobLevelSubList) {
        return baseJobLevelSubService.batchDeleteUpdateBaseJobLevelSub(baseJobLevelSubList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
