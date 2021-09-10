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
import com.apih5.mybatis.pojo.ZjTzExecutivePersonnel;
import com.apih5.service.ZjTzExecutivePersonnelService;

@RestController
public class ZjTzExecutivePersonnelController {

    @Autowired(required = true)
    private ZjTzExecutivePersonnelService zjTzExecutivePersonnelService;

    @ApiOperation(value="查询董监高人员信息", notes="查询董监高人员信息")
    @ApiImplicitParam(name = "zjTzExecutivePersonnel", value = "董监高人员信息entity", dataType = "ZjTzExecutivePersonnel")
    @RequireToken
    @PostMapping("/getZjTzExecutivePersonnelList")
    public ResponseEntity getZjTzExecutivePersonnelList(@RequestBody(required = false) ZjTzExecutivePersonnel zjTzExecutivePersonnel) {
        return zjTzExecutivePersonnelService.getZjTzExecutivePersonnelListByCondition(zjTzExecutivePersonnel);
    }

    @ApiOperation(value="查询详情董监高人员信息", notes="查询详情董监高人员信息")
    @ApiImplicitParam(name = "zjTzExecutivePersonnel", value = "董监高人员信息entity", dataType = "ZjTzExecutivePersonnel")
    @RequireToken
    @PostMapping("/getZjTzExecutivePersonnelDetails")
    public ResponseEntity getZjTzExecutivePersonnelDetails(@RequestBody(required = false) ZjTzExecutivePersonnel zjTzExecutivePersonnel) {
        return zjTzExecutivePersonnelService.getZjTzExecutivePersonnelDetails(zjTzExecutivePersonnel);
    }

    @ApiOperation(value="新增董监高人员信息", notes="新增董监高人员信息")
    @ApiImplicitParam(name = "zjTzExecutivePersonnel", value = "董监高人员信息entity", dataType = "ZjTzExecutivePersonnel")
    @RequireToken
    @PostMapping("/addZjTzExecutivePersonnel")
    public ResponseEntity addZjTzExecutivePersonnel(@RequestBody(required = false) ZjTzExecutivePersonnel zjTzExecutivePersonnel) {
        return zjTzExecutivePersonnelService.saveZjTzExecutivePersonnel(zjTzExecutivePersonnel);
    }

    @ApiOperation(value="更新董监高人员信息", notes="更新董监高人员信息")
    @ApiImplicitParam(name = "zjTzExecutivePersonnel", value = "董监高人员信息entity", dataType = "ZjTzExecutivePersonnel")
    @RequireToken
    @PostMapping("/updateZjTzExecutivePersonnel")
    public ResponseEntity updateZjTzExecutivePersonnel(@RequestBody(required = false) ZjTzExecutivePersonnel zjTzExecutivePersonnel) {
        return zjTzExecutivePersonnelService.updateZjTzExecutivePersonnel(zjTzExecutivePersonnel);
    }

    @ApiOperation(value="删除董监高人员信息", notes="删除董监高人员信息")
    @ApiImplicitParam(name = "zjTzExecutivePersonnelList", value = "董监高人员信息List", dataType = "List<ZjTzExecutivePersonnel>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZjTzExecutivePersonnel")
    public ResponseEntity batchDeleteUpdateZjTzExecutivePersonnel(@RequestBody(required = false) List<ZjTzExecutivePersonnel> zjTzExecutivePersonnelList) {
        return zjTzExecutivePersonnelService.batchDeleteUpdateZjTzExecutivePersonnel(zjTzExecutivePersonnelList);
    }

    
    @ApiOperation(value="查询导出董监高人员信息列表", notes="查询董监高人员信息")
    @ApiImplicitParam(name = "zjTzExecutivePersonnel", value = "董监高人员信息entity", dataType = "ZjTzExecutivePersonnel")
    @RequireToken
    @PostMapping("/getZjTzExecutivePersonnelListForReport")
    public ResponseEntity getZjTzExecutivePersonnelListForReport(@RequestBody(required = false) ZjTzExecutivePersonnel zjTzExecutivePersonnel) {
        return zjTzExecutivePersonnelService.getZjTzExecutivePersonnelListForReport(zjTzExecutivePersonnel);
    }
    
    @ApiOperation(value="导出董监高人员信息列表", notes="导出董监高人员信息列表")
    @ApiImplicitParam(name = "zjTzExecutivePersonnel", value = "董监高人员信息entity", dataType = "ZjTzExecutivePersonnel")
    @RequireToken
    @PostMapping("/uReportZjTzExecutivePersonnelList")
    public List<ZjTzExecutivePersonnel> uReportZjTzExecutivePersonnelList(@RequestBody(required = false) ZjTzExecutivePersonnel zjTzExecutivePersonnel) {
        return zjTzExecutivePersonnelService.uReportZjTzExecutivePersonnelList(zjTzExecutivePersonnel);
    }
}