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
import com.apih5.mybatis.pojo.ZjLzehTeamManagement;
import com.apih5.service.ZjLzehTeamManagementService;

@RestController
public class ZjLzehTeamManagementController {

    @Autowired(required = true)
    private ZjLzehTeamManagementService zjLzehTeamManagementService;

    @ApiOperation(value="查询班组管理", notes="查询班组管理")
    @ApiImplicitParam(name = "zjLzehTeamManagement", value = "班组管理entity", dataType = "ZjLzehTeamManagement")
    @RequireToken
    @PostMapping("/getZjLzehTeamManagementList")
    public ResponseEntity getZjLzehTeamManagementList(@RequestBody(required = false) ZjLzehTeamManagement zjLzehTeamManagement) {
        return zjLzehTeamManagementService.getZjLzehTeamManagementListByCondition(zjLzehTeamManagement);
    }

    @ApiOperation(value="查询详情班组管理", notes="查询详情班组管理")
    @ApiImplicitParam(name = "zjLzehTeamManagement", value = "班组管理entity", dataType = "ZjLzehTeamManagement")
    @RequireToken
    @PostMapping("/getZjLzehTeamManagementDetail")
    public ResponseEntity getZjLzehTeamManagementDetail(@RequestBody(required = false) ZjLzehTeamManagement zjLzehTeamManagement) {
        return zjLzehTeamManagementService.getZjLzehTeamManagementDetail(zjLzehTeamManagement);
    }

    @ApiOperation(value="新增班组管理", notes="新增班组管理")
    @ApiImplicitParam(name = "zjLzehTeamManagement", value = "班组管理entity", dataType = "ZjLzehTeamManagement")
    @RequireToken
    @PostMapping("/addZjLzehTeamManagement")
    public ResponseEntity addZjLzehTeamManagement(@RequestBody(required = false) ZjLzehTeamManagement zjLzehTeamManagement) {
        return zjLzehTeamManagementService.saveZjLzehTeamManagement(zjLzehTeamManagement);
    }

    @ApiOperation(value="更新班组管理", notes="更新班组管理")
    @ApiImplicitParam(name = "zjLzehTeamManagement", value = "班组管理entity", dataType = "ZjLzehTeamManagement")
    @RequireToken
    @PostMapping("/updateZjLzehTeamManagement")
    public ResponseEntity updateZjLzehTeamManagement(@RequestBody(required = false) ZjLzehTeamManagement zjLzehTeamManagement) {
        return zjLzehTeamManagementService.updateZjLzehTeamManagement(zjLzehTeamManagement);
    }

    @ApiOperation(value="删除班组管理", notes="删除班组管理")
    @ApiImplicitParam(name = "zjLzehTeamManagementList", value = "班组管理List", dataType = "List<ZjLzehTeamManagement>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZjLzehTeamManagement")
    public ResponseEntity batchDeleteUpdateZjLzehTeamManagement(@RequestBody(required = false) List<ZjLzehTeamManagement> zjLzehTeamManagementList) {
        return zjLzehTeamManagementService.batchDeleteUpdateZjLzehTeamManagement(zjLzehTeamManagementList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

    @ApiOperation(value="查询参与评分的班组数量", notes="查询参与评分的班组数量")
    @ApiImplicitParam(name = "zjLzehTeamManagement", value = "班组管理entity", dataType = "ZjLzehTeamManagement")
    @RequireToken
    @PostMapping("/getZjLzehTeamManagementisScoreCount")
    public ResponseEntity getZjLzehTeamManagementisScoreCount(@RequestBody(required = false) ZjLzehTeamManagement zjLzehTeamManagement) {
        return zjLzehTeamManagementService.getZjLzehTeamManagementisScoreCount();
    }
}
