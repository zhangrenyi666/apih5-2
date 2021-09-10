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
import com.apih5.mybatis.pojo.ZjTzSuperiorUnitSecurityDuty;
import com.apih5.service.ZjTzSuperiorUnitSecurityDutyService;

@RestController
public class ZjTzQHSESuperiorUnitSecurityDutyController {

    @Autowired(required = true)
    private ZjTzSuperiorUnitSecurityDutyService zjTzSuperiorUnitSecurityDutyService;

    @ApiOperation(value="查询与上级单位签订安全责任书", notes="查询与上级单位签订安全责任书")
    @ApiImplicitParam(name = "zjTzSuperiorUnitSecurityDuty", value = "与上级单位签订安全责任书entity", dataType = "ZjTzSuperiorUnitSecurityDuty")
    @RequireToken
    @PostMapping("/getZjTzSuperiorUnitSecurityDutyList")
    public ResponseEntity getZjTzSuperiorUnitSecurityDutyList(@RequestBody(required = false) ZjTzSuperiorUnitSecurityDuty zjTzSuperiorUnitSecurityDuty) {
        return zjTzSuperiorUnitSecurityDutyService.getZjTzSuperiorUnitSecurityDutyListByCondition(zjTzSuperiorUnitSecurityDuty);
    }

    @ApiOperation(value="查询详情与上级单位签订安全责任书", notes="查询详情与上级单位签订安全责任书")
    @ApiImplicitParam(name = "zjTzSuperiorUnitSecurityDuty", value = "与上级单位签订安全责任书entity", dataType = "ZjTzSuperiorUnitSecurityDuty")
    @RequireToken
    @PostMapping("/getZjTzSuperiorUnitSecurityDutyDetails")
    public ResponseEntity getZjTzSuperiorUnitSecurityDutyDetails(@RequestBody(required = false) ZjTzSuperiorUnitSecurityDuty zjTzSuperiorUnitSecurityDuty) {
        return zjTzSuperiorUnitSecurityDutyService.getZjTzSuperiorUnitSecurityDutyDetails(zjTzSuperiorUnitSecurityDuty);
    }

    @ApiOperation(value="新增与上级单位签订安全责任书", notes="新增与上级单位签订安全责任书")
    @ApiImplicitParam(name = "zjTzSuperiorUnitSecurityDuty", value = "与上级单位签订安全责任书entity", dataType = "ZjTzSuperiorUnitSecurityDuty")
    @RequireToken
    @PostMapping("/addZjTzSuperiorUnitSecurityDuty")
    public ResponseEntity addZjTzSuperiorUnitSecurityDuty(@RequestBody(required = false) ZjTzSuperiorUnitSecurityDuty zjTzSuperiorUnitSecurityDuty) {
        return zjTzSuperiorUnitSecurityDutyService.saveZjTzSuperiorUnitSecurityDuty(zjTzSuperiorUnitSecurityDuty);
    }

    @ApiOperation(value="更新与上级单位签订安全责任书", notes="更新与上级单位签订安全责任书")
    @ApiImplicitParam(name = "zjTzSuperiorUnitSecurityDuty", value = "与上级单位签订安全责任书entity", dataType = "ZjTzSuperiorUnitSecurityDuty")
    @RequireToken
    @PostMapping("/updateZjTzSuperiorUnitSecurityDuty")
    public ResponseEntity updateZjTzSuperiorUnitSecurityDuty(@RequestBody(required = false) ZjTzSuperiorUnitSecurityDuty zjTzSuperiorUnitSecurityDuty) {
        return zjTzSuperiorUnitSecurityDutyService.updateZjTzSuperiorUnitSecurityDuty(zjTzSuperiorUnitSecurityDuty);
    }
    
    @ApiOperation(value="新增与上级单位签订安全责任书（添加附件）", notes="新增与上级单位签订安全责任书（添加附件）")
    @ApiImplicitParam(name = "zjTzSuperiorUnitSecurityDuty", value = "与上级单位签订安全责任书entity", dataType = "ZjTzSuperiorUnitSecurityDuty")
    @RequireToken
    @PostMapping("/addZjTzSuperiorUnitSecurityDutyAddFile")
    public ResponseEntity addZjTzSuperiorUnitSecurityDutyAddFile(@RequestBody(required = false) ZjTzSuperiorUnitSecurityDuty zjTzSuperiorUnitSecurityDuty) {
    	return zjTzSuperiorUnitSecurityDutyService.saveZjTzSuperiorUnitSecurityDutyAddFile(zjTzSuperiorUnitSecurityDuty);
    }
    
    @ApiOperation(value="更新与上级单位签订安全责任书（添加附件）", notes="更新与上级单位签订安全责任书（添加附件）")
    @ApiImplicitParam(name = "zjTzSuperiorUnitSecurityDuty", value = "与上级单位签订安全责任书entity", dataType = "ZjTzSuperiorUnitSecurityDuty")
    @RequireToken
    @PostMapping("/updateZjTzSuperiorUnitSecurityDutyAddFile")
    public ResponseEntity updateZjTzSuperiorUnitSecurityDutyAddFile(@RequestBody(required = false) ZjTzSuperiorUnitSecurityDuty zjTzSuperiorUnitSecurityDuty) {
    	return zjTzSuperiorUnitSecurityDutyService.updateZjTzSuperiorUnitSecurityDutyAddFile(zjTzSuperiorUnitSecurityDuty);
    }

    @ApiOperation(value="删除与上级单位签订安全责任书", notes="删除与上级单位签订安全责任书")
    @ApiImplicitParam(name = "zjTzSuperiorUnitSecurityDutyList", value = "与上级单位签订安全责任书List", dataType = "List<ZjTzSuperiorUnitSecurityDuty>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZjTzSuperiorUnitSecurityDuty")
    public ResponseEntity batchDeleteUpdateZjTzSuperiorUnitSecurityDuty(@RequestBody(required = false) List<ZjTzSuperiorUnitSecurityDuty> zjTzSuperiorUnitSecurityDutyList) {
        return zjTzSuperiorUnitSecurityDutyService.batchDeleteUpdateZjTzSuperiorUnitSecurityDuty(zjTzSuperiorUnitSecurityDutyList);
    }

}
