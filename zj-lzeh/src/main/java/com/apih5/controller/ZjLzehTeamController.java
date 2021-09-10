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
import com.apih5.mybatis.pojo.ZjLzehTeam;
import com.apih5.service.ZjLzehTeamService;

@RestController
public class ZjLzehTeamController {

    @Autowired(required = true)
    private ZjLzehTeamService zjLzehTeamService;

    @ApiOperation(value="查询班组信息", notes="查询班组信息")
    @ApiImplicitParam(name = "zjLzehTeam", value = "班组信息entity", dataType = "ZjLzehTeam")
    @RequireToken
    @PostMapping("/getZjLzehTeamList")
    public ResponseEntity getZjLzehTeamList(@RequestBody(required = false) ZjLzehTeam zjLzehTeam) {
        return zjLzehTeamService.getZjLzehTeamListByCondition(zjLzehTeam);
    }

    @ApiOperation(value="查询详情班组信息", notes="查询详情班组信息")
    @ApiImplicitParam(name = "zjLzehTeam", value = "班组信息entity", dataType = "ZjLzehTeam")
    @RequireToken
    @PostMapping("/getZjLzehTeamDetails")
    public ResponseEntity getZjLzehTeamDetails(@RequestBody(required = false) ZjLzehTeam zjLzehTeam) {
        return zjLzehTeamService.getZjLzehTeamDetails(zjLzehTeam);
    }

    @ApiOperation(value="新增班组信息", notes="新增班组信息")
    @ApiImplicitParam(name = "zjLzehTeam", value = "班组信息entity", dataType = "ZjLzehTeam")
    @RequireToken
    @PostMapping("/addZjLzehTeam")
    public ResponseEntity addZjLzehTeam(@RequestBody(required = false) ZjLzehTeam zjLzehTeam) {
        return zjLzehTeamService.saveZjLzehTeam(zjLzehTeam);
    }

    @ApiOperation(value="更新班组信息", notes="更新班组信息")
    @ApiImplicitParam(name = "zjLzehTeam", value = "班组信息entity", dataType = "ZjLzehTeam")
    @RequireToken
    @PostMapping("/updateZjLzehTeam")
    public ResponseEntity updateZjLzehTeam(@RequestBody(required = false) ZjLzehTeam zjLzehTeam) {
        return zjLzehTeamService.updateZjLzehTeam(zjLzehTeam);
    }

    @ApiOperation(value="删除班组信息", notes="删除班组信息")
    @ApiImplicitParam(name = "zjLzehTeamList", value = "班组信息List", dataType = "List<ZjLzehTeam>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZjLzehTeam")
    public ResponseEntity batchDeleteUpdateZjLzehTeam(@RequestBody(required = false) List<ZjLzehTeam> zjLzehTeamList) {
        return zjLzehTeamService.batchDeleteUpdateZjLzehTeam(zjLzehTeamList);
    }

}
