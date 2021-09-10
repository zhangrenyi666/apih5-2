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
import com.apih5.mybatis.pojo.ZjTzEmployeeProfessional;
import com.apih5.service.ZjTzEmployeeProfessionalService;

@RestController
public class ZjTzEmployeeProfessionalController {

    @Autowired(required = true)
    private ZjTzEmployeeProfessionalService zjTzEmployeeProfessionalService;

    @ApiOperation(value="查询人员库-职称情况", notes="查询人员库-职称情况")
    @ApiImplicitParam(name = "zjTzEmployeeProfessional", value = "人员库-职称情况entity", dataType = "ZjTzEmployeeProfessional")
    @RequireToken
    @PostMapping("/getZjTzEmployeeProfessionalList")
    public ResponseEntity getZjTzEmployeeProfessionalList(@RequestBody(required = false) ZjTzEmployeeProfessional zjTzEmployeeProfessional) {
        return zjTzEmployeeProfessionalService.getZjTzEmployeeProfessionalListByCondition(zjTzEmployeeProfessional);
    }

    @ApiOperation(value="查询详情人员库-职称情况", notes="查询详情人员库-职称情况")
    @ApiImplicitParam(name = "zjTzEmployeeProfessional", value = "人员库-职称情况entity", dataType = "ZjTzEmployeeProfessional")
    @RequireToken
    @PostMapping("/getZjTzEmployeeProfessionalDetails")
    public ResponseEntity getZjTzEmployeeProfessionalDetails(@RequestBody(required = false) ZjTzEmployeeProfessional zjTzEmployeeProfessional) {
        return zjTzEmployeeProfessionalService.getZjTzEmployeeProfessionalDetails(zjTzEmployeeProfessional);
    }

    @ApiOperation(value="新增人员库-职称情况", notes="新增人员库-职称情况")
    @ApiImplicitParam(name = "zjTzEmployeeProfessional", value = "人员库-职称情况entity", dataType = "ZjTzEmployeeProfessional")
    @RequireToken
    @PostMapping("/addZjTzEmployeeProfessional")
    public ResponseEntity addZjTzEmployeeProfessional(@RequestBody(required = false) ZjTzEmployeeProfessional zjTzEmployeeProfessional) {
        return zjTzEmployeeProfessionalService.saveZjTzEmployeeProfessional(zjTzEmployeeProfessional);
    }

    @ApiOperation(value="更新人员库-职称情况", notes="更新人员库-职称情况")
    @ApiImplicitParam(name = "zjTzEmployeeProfessional", value = "人员库-职称情况entity", dataType = "ZjTzEmployeeProfessional")
    @RequireToken
    @PostMapping("/updateZjTzEmployeeProfessional")
    public ResponseEntity updateZjTzEmployeeProfessional(@RequestBody(required = false) ZjTzEmployeeProfessional zjTzEmployeeProfessional) {
        return zjTzEmployeeProfessionalService.updateZjTzEmployeeProfessional(zjTzEmployeeProfessional);
    }

    @ApiOperation(value="删除人员库-职称情况", notes="删除人员库-职称情况")
    @ApiImplicitParam(name = "zjTzEmployeeProfessionalList", value = "人员库-职称情况List", dataType = "List<ZjTzEmployeeProfessional>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZjTzEmployeeProfessional")
    public ResponseEntity batchDeleteUpdateZjTzEmployeeProfessional(@RequestBody(required = false) List<ZjTzEmployeeProfessional> zjTzEmployeeProfessionalList) {
        return zjTzEmployeeProfessionalService.batchDeleteUpdateZjTzEmployeeProfessional(zjTzEmployeeProfessionalList);
    }

}