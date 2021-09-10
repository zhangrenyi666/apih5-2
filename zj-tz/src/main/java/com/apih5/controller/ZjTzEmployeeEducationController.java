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
import com.apih5.mybatis.pojo.ZjTzEmployeeEducation;
import com.apih5.service.ZjTzEmployeeEducationService;

@RestController
public class ZjTzEmployeeEducationController {

    @Autowired(required = true)
    private ZjTzEmployeeEducationService zjTzEmployeeEducationService;

    @ApiOperation(value="查询人员库-学历学位", notes="查询人员库-学历学位")
    @ApiImplicitParam(name = "zjTzEmployeeEducation", value = "人员库-学历学位entity", dataType = "ZjTzEmployeeEducation")
    @RequireToken
    @PostMapping("/getZjTzEmployeeEducationList")
    public ResponseEntity getZjTzEmployeeEducationList(@RequestBody(required = false) ZjTzEmployeeEducation zjTzEmployeeEducation) {
        return zjTzEmployeeEducationService.getZjTzEmployeeEducationListByCondition(zjTzEmployeeEducation);
    }

    @ApiOperation(value="查询详情人员库-学历学位", notes="查询详情人员库-学历学位")
    @ApiImplicitParam(name = "zjTzEmployeeEducation", value = "人员库-学历学位entity", dataType = "ZjTzEmployeeEducation")
    @RequireToken
    @PostMapping("/getZjTzEmployeeEducationDetails")
    public ResponseEntity getZjTzEmployeeEducationDetails(@RequestBody(required = false) ZjTzEmployeeEducation zjTzEmployeeEducation) {
        return zjTzEmployeeEducationService.getZjTzEmployeeEducationDetails(zjTzEmployeeEducation);
    }

    @ApiOperation(value="新增人员库-学历学位", notes="新增人员库-学历学位")
    @ApiImplicitParam(name = "zjTzEmployeeEducation", value = "人员库-学历学位entity", dataType = "ZjTzEmployeeEducation")
    @RequireToken
    @PostMapping("/addZjTzEmployeeEducation")
    public ResponseEntity addZjTzEmployeeEducation(@RequestBody(required = false) ZjTzEmployeeEducation zjTzEmployeeEducation) {
        return zjTzEmployeeEducationService.saveZjTzEmployeeEducation(zjTzEmployeeEducation);
    }

    @ApiOperation(value="更新人员库-学历学位", notes="更新人员库-学历学位")
    @ApiImplicitParam(name = "zjTzEmployeeEducation", value = "人员库-学历学位entity", dataType = "ZjTzEmployeeEducation")
    @RequireToken
    @PostMapping("/updateZjTzEmployeeEducation")
    public ResponseEntity updateZjTzEmployeeEducation(@RequestBody(required = false) ZjTzEmployeeEducation zjTzEmployeeEducation) {
        return zjTzEmployeeEducationService.updateZjTzEmployeeEducation(zjTzEmployeeEducation);
    }

    @ApiOperation(value="删除人员库-学历学位", notes="删除人员库-学历学位")
    @ApiImplicitParam(name = "zjTzEmployeeEducationList", value = "人员库-学历学位List", dataType = "List<ZjTzEmployeeEducation>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZjTzEmployeeEducation")
    public ResponseEntity batchDeleteUpdateZjTzEmployeeEducation(@RequestBody(required = false) List<ZjTzEmployeeEducation> zjTzEmployeeEducationList) {
        return zjTzEmployeeEducationService.batchDeleteUpdateZjTzEmployeeEducation(zjTzEmployeeEducationList);
    }

    @ApiOperation(value="查询人员库-学历学位", notes="查询人员库-学历学位")
    @ApiImplicitParam(name = "zjTzEmployeeEducation", value = "人员库-学历学位entity", dataType = "ZjTzEmployeeEducation")
    @PostMapping("/printZjTzEmployeeEducation")
    public ZjTzEmployeeEducation printZjTzEmployeeEducation(@RequestBody(required = false) ZjTzEmployeeEducation zjTzEmployeeEducation) {
        return zjTzEmployeeEducationService.printZjTzEmployeeEducation(zjTzEmployeeEducation);
    }

}