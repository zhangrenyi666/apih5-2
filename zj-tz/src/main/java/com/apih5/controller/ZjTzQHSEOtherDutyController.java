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
import com.apih5.mybatis.pojo.ZjTzOtherDuty;
import com.apih5.service.ZjTzOtherDutyService;

@RestController
public class ZjTzQHSEOtherDutyController {

    @Autowired(required = true)
    private ZjTzOtherDutyService zjTzOtherDutyService;

    @ApiOperation(value="查询其他责任书", notes="查询其他责任书")
    @ApiImplicitParam(name = "zjTzOtherDuty", value = "其他责任书entity", dataType = "ZjTzOtherDuty")
    @RequireToken
    @PostMapping("/getZjTzOtherDutyList")
    public ResponseEntity getZjTzOtherDutyList(@RequestBody(required = false) ZjTzOtherDuty zjTzOtherDuty) {
        return zjTzOtherDutyService.getZjTzOtherDutyListByCondition(zjTzOtherDuty);
    }

    @ApiOperation(value="查询详情其他责任书", notes="查询详情其他责任书")
    @ApiImplicitParam(name = "zjTzOtherDuty", value = "其他责任书entity", dataType = "ZjTzOtherDuty")
    @RequireToken
    @PostMapping("/getZjTzOtherDutyDetails")
    public ResponseEntity getZjTzOtherDutyDetails(@RequestBody(required = false) ZjTzOtherDuty zjTzOtherDuty) {
        return zjTzOtherDutyService.getZjTzOtherDutyDetails(zjTzOtherDuty);
    }

    @ApiOperation(value="新增其他责任书", notes="新增其他责任书")
    @ApiImplicitParam(name = "zjTzOtherDuty", value = "其他责任书entity", dataType = "ZjTzOtherDuty")
    @RequireToken
    @PostMapping("/addZjTzOtherDuty")
    public ResponseEntity addZjTzOtherDuty(@RequestBody(required = false) ZjTzOtherDuty zjTzOtherDuty) {
        return zjTzOtherDutyService.saveZjTzOtherDuty(zjTzOtherDuty);
    }

    @ApiOperation(value="更新其他责任书", notes="更新其他责任书")
    @ApiImplicitParam(name = "zjTzOtherDuty", value = "其他责任书entity", dataType = "ZjTzOtherDuty")
    @RequireToken
    @PostMapping("/updateZjTzOtherDuty")
    public ResponseEntity updateZjTzOtherDuty(@RequestBody(required = false) ZjTzOtherDuty zjTzOtherDuty) {
        return zjTzOtherDutyService.updateZjTzOtherDuty(zjTzOtherDuty);
    }
    
    @ApiOperation(value="新增其他责任书（增加附件）", notes="新增其他责任书（增加附件")
    @ApiImplicitParam(name = "zjTzOtherDuty", value = "其他责任书entity", dataType = "ZjTzOtherDuty")
    @RequireToken
    @PostMapping("/addZjTzOtherDutyAddFile")
    public ResponseEntity addZjTzOtherDutyAddFile(@RequestBody(required = false) ZjTzOtherDuty zjTzOtherDuty) {
    	return zjTzOtherDutyService.saveZjTzOtherDutyAddFile(zjTzOtherDuty);
    }
    
    @ApiOperation(value="更新其他责任书（增加附件", notes="更新其他责任书（增加附件")
    @ApiImplicitParam(name = "zjTzOtherDuty", value = "其他责任书entity", dataType = "ZjTzOtherDuty")
    @RequireToken
    @PostMapping("/updateZjTzOtherDutyAddFile")
    public ResponseEntity updateZjTzOtherDutyAddFile(@RequestBody(required = false) ZjTzOtherDuty zjTzOtherDuty) {
    	return zjTzOtherDutyService.updateZjTzOtherDutyAddFile(zjTzOtherDuty);
    }

    @ApiOperation(value="删除其他责任书", notes="删除其他责任书")
    @ApiImplicitParam(name = "zjTzOtherDutyList", value = "其他责任书List", dataType = "List<ZjTzOtherDuty>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZjTzOtherDuty")
    public ResponseEntity batchDeleteUpdateZjTzOtherDuty(@RequestBody(required = false) List<ZjTzOtherDuty> zjTzOtherDutyList) {
        return zjTzOtherDutyService.batchDeleteUpdateZjTzOtherDuty(zjTzOtherDutyList);
    }

}
