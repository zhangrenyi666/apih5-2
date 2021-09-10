package com.apih5.controller;

import java.util.List;

import com.apih5.mybatis.pojo.ZjLzehTeamInformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import com.apih5.framework.annotation.RequireToken;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjLzehTeamInformationTeam;
import com.apih5.service.ZjLzehTeamInformationTeamService;

@RestController
public class ZjLzehTeamInformationTeamController {

    @Autowired(required = true)
    private ZjLzehTeamInformationTeamService zjLzehTeamInformationTeamService;

    @ApiOperation(value="查询班组信息", notes="查询班组信息")
    @ApiImplicitParam(name = "zjLzehTeamInformationTeam", value = "班组信息entity", dataType = "ZjLzehTeamInformationTeam")
    @RequireToken
    @PostMapping("/getZjLzehTeamInformationTeamList")
    public ResponseEntity getZjLzehTeamInformationTeamList(@RequestBody(required = false) ZjLzehTeamInformationTeam zjLzehTeamInformationTeam) {
        return zjLzehTeamInformationTeamService.getZjLzehTeamInformationTeamListByCondition(zjLzehTeamInformationTeam);
    }

    @ApiOperation(value="查询详情班组信息", notes="查询详情班组信息")
    @ApiImplicitParam(name = "zjLzehTeamInformationTeam", value = "班组信息entity", dataType = "ZjLzehTeamInformationTeam")
    @RequireToken
    @PostMapping("/getZjLzehTeamInformationTeamDetails")
    public ResponseEntity getZjLzehTeamInformationTeamDetails(@RequestBody(required = false) ZjLzehTeamInformationTeam zjLzehTeamInformationTeam) {
        return zjLzehTeamInformationTeamService.getZjLzehTeamInformationTeamDetails(zjLzehTeamInformationTeam);
    }

    @ApiOperation(value="新增班组信息", notes="新增班组信息")
    @ApiImplicitParam(name = "zjLzehTeamInformationTeam", value = "班组信息entity", dataType = "ZjLzehTeamInformationTeam")
    @RequireToken
    @PostMapping("/addZjLzehTeamInformationTeam")
    public ResponseEntity addZjLzehTeamInformationTeam(@RequestBody(required = false) ZjLzehTeamInformationTeam zjLzehTeamInformationTeam) {
        return zjLzehTeamInformationTeamService.saveZjLzehTeamInformationTeam(zjLzehTeamInformationTeam);
    }

    @ApiOperation(value="更新班组信息", notes="更新班组信息")
    @ApiImplicitParam(name = "zjLzehTeamInformationTeam", value = "班组信息entity", dataType = "ZjLzehTeamInformationTeam")
    @RequireToken
    @PostMapping("/updateZjLzehTeamInformationTeam")
    public ResponseEntity updateZjLzehTeamInformationTeam(@RequestBody(required = false) ZjLzehTeamInformationTeam zjLzehTeamInformationTeam) {
        return zjLzehTeamInformationTeamService.updateZjLzehTeamInformationTeam(zjLzehTeamInformationTeam);
    }

    @ApiOperation(value="删除班组信息", notes="删除班组信息")
    @ApiImplicitParam(name = "zjLzehTeamInformationTeamList", value = "班组信息List", dataType = "List<ZjLzehTeamInformationTeam>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZjLzehTeamInformationTeam")
    public ResponseEntity batchDeleteUpdateZjLzehTeamInformationTeam(@RequestBody(required = false) List<ZjLzehTeamInformationTeam> zjLzehTeamInformationTeamList) {
        return zjLzehTeamInformationTeamService.batchDeleteUpdateZjLzehTeamInformationTeam(zjLzehTeamInformationTeamList);
    }

}
