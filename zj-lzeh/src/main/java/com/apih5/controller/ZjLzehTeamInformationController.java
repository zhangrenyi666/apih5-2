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
import com.apih5.mybatis.pojo.ZjLzehTeamInformation;
import com.apih5.service.ZjLzehTeamInformationService;

@RestController
public class ZjLzehTeamInformationController {

    @Autowired(required = true)
    private ZjLzehTeamInformationService zjLzehTeamInformationService;

    @ApiOperation(value="查询班组信息", notes="查询班组信息")
    @ApiImplicitParam(name = "zjLzehTeamInformation", value = "班组信息entity", dataType = "ZjLzehTeamInformation")
    @RequireToken
    @PostMapping("/getZjLzehTeamInformationList")
    public ResponseEntity getZjLzehTeamInformationList(@RequestBody(required = false) ZjLzehTeamInformation zjLzehTeamInformation) {
        return zjLzehTeamInformationService.getZjLzehTeamInformationListByCondition(zjLzehTeamInformation);
    }

    @ApiOperation(value="查询详情班组信息", notes="查询详情班组信息")
    @ApiImplicitParam(name = "zjLzehTeamInformation", value = "班组信息entity", dataType = "ZjLzehTeamInformation")
    @RequireToken
    @PostMapping("/getZjLzehTeamInformationDetails")
    public ResponseEntity getZjLzehTeamInformationDetails(@RequestBody(required = false) ZjLzehTeamInformation zjLzehTeamInformation) {
        return zjLzehTeamInformationService.getZjLzehTeamInformationDetails(zjLzehTeamInformation);
    }

    @ApiOperation(value="新增班组信息", notes="新增班组信息")
    @ApiImplicitParam(name = "zjLzehTeamInformation", value = "班组信息entity", dataType = "ZjLzehTeamInformation")
    @RequireToken
    @PostMapping("/addZjLzehTeamInformation")
    public ResponseEntity addZjLzehTeamInformation(@RequestBody(required = false) ZjLzehTeamInformation zjLzehTeamInformation) {
        return zjLzehTeamInformationService.saveZjLzehTeamInformation(zjLzehTeamInformation);
    }

    @ApiOperation(value="更新班组信息", notes="更新班组信息")
    @ApiImplicitParam(name = "zjLzehTeamInformation", value = "班组信息entity", dataType = "ZjLzehTeamInformation")
    @RequireToken
    @PostMapping("/updateZjLzehTeamInformation")
    public ResponseEntity updateZjLzehTeamInformation(@RequestBody(required = false) ZjLzehTeamInformation zjLzehTeamInformation) {
        return zjLzehTeamInformationService.updateZjLzehTeamInformation(zjLzehTeamInformation);
    }

    @ApiOperation(value="删除班组信息", notes="删除班组信息")
    @ApiImplicitParam(name = "zjLzehTeamInformationList", value = "班组信息List", dataType = "List<ZjLzehTeamInformation>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZjLzehTeamInformation")
    public ResponseEntity batchDeleteUpdateZjLzehTeamInformation(@RequestBody(required = false) List<ZjLzehTeamInformation> zjLzehTeamInformationList) {
        return zjLzehTeamInformationService.batchDeleteUpdateZjLzehTeamInformation(zjLzehTeamInformationList);
    }





}
