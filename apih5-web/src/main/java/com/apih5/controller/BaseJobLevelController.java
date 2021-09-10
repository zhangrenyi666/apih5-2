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
import com.apih5.mybatis.pojo.BaseJobLevel;
import com.apih5.service.BaseJobLevelService;

@RestController
public class BaseJobLevelController {

    @Autowired(required = true)
    private BaseJobLevelService baseJobLevelService;

    @ApiOperation(value="查询岗位等级表", notes="查询岗位等级表")
    @ApiImplicitParam(name = "baseJobLevel", value = "岗位等级表entity", dataType = "BaseJobLevel")
    @RequireToken
    @PostMapping("/getBaseJobLevelList")
    public ResponseEntity getBaseJobLevelList(@RequestBody(required = false) BaseJobLevel baseJobLevel) {
        return baseJobLevelService.getBaseJobLevelListByCondition(baseJobLevel);
    }

    @ApiOperation(value="查询详情岗位等级表", notes="查询详情岗位等级表")
    @ApiImplicitParam(name = "baseJobLevel", value = "岗位等级表entity", dataType = "BaseJobLevel")
    @RequireToken
    @PostMapping("/getBaseJobLevelDetail")
    public ResponseEntity getBaseJobLevelDetail(@RequestBody(required = false) BaseJobLevel baseJobLevel) {
        return baseJobLevelService.getBaseJobLevelDetail(baseJobLevel);
    }

    @ApiOperation(value="新增岗位等级表", notes="新增岗位等级表")
    @ApiImplicitParam(name = "baseJobLevel", value = "岗位等级表entity", dataType = "BaseJobLevel")
    @RequireToken
    @PostMapping("/addBaseJobLevel")
    public ResponseEntity addBaseJobLevel(@RequestBody(required = false) BaseJobLevel baseJobLevel) {
        return baseJobLevelService.saveBaseJobLevel(baseJobLevel);
    }

    @ApiOperation(value="更新岗位等级表", notes="更新岗位等级表")
    @ApiImplicitParam(name = "baseJobLevel", value = "岗位等级表entity", dataType = "BaseJobLevel")
    @RequireToken
    @PostMapping("/updateBaseJobLevel")
    public ResponseEntity updateBaseJobLevel(@RequestBody(required = false) BaseJobLevel baseJobLevel) {
        return baseJobLevelService.updateBaseJobLevel(baseJobLevel);
    }

    @ApiOperation(value="删除岗位等级表", notes="删除岗位等级表")
    @ApiImplicitParam(name = "baseJobLevelList", value = "岗位等级表List", dataType = "List<BaseJobLevel>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateBaseJobLevel")
    public ResponseEntity batchDeleteUpdateBaseJobLevel(@RequestBody(required = false) List<BaseJobLevel> baseJobLevelList) {
        return baseJobLevelService.batchDeleteUpdateBaseJobLevel(baseJobLevelList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
