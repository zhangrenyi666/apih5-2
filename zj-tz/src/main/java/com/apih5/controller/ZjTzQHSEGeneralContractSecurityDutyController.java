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
import com.apih5.mybatis.pojo.ZjTzGeneralContractSecurityDuty;
import com.apih5.service.ZjTzGeneralContractSecurityDutyService;

@RestController
public class ZjTzQHSEGeneralContractSecurityDutyController {

    @Autowired(required = true)
    private ZjTzGeneralContractSecurityDutyService zjTzGeneralContractSecurityDutyService;

    @ApiOperation(value="查询与总承包单位签订安全责任书", notes="查询与总承包单位签订安全责任书")
    @ApiImplicitParam(name = "zjTzGeneralContractSecurityDuty", value = "与总承包单位签订安全责任书entity", dataType = "ZjTzGeneralContractSecurityDuty")
    @RequireToken
    @PostMapping("/getZjTzGeneralContractSecurityDutyList")
    public ResponseEntity getZjTzGeneralContractSecurityDutyList(@RequestBody(required = false) ZjTzGeneralContractSecurityDuty zjTzGeneralContractSecurityDuty) {
        return zjTzGeneralContractSecurityDutyService.getZjTzGeneralContractSecurityDutyListByCondition(zjTzGeneralContractSecurityDuty);
    }

    @ApiOperation(value="查询详情与总承包单位签订安全责任书", notes="查询详情与总承包单位签订安全责任书")
    @ApiImplicitParam(name = "zjTzGeneralContractSecurityDuty", value = "与总承包单位签订安全责任书entity", dataType = "ZjTzGeneralContractSecurityDuty")
    @RequireToken
    @PostMapping("/getZjTzGeneralContractSecurityDutyDetails")
    public ResponseEntity getZjTzGeneralContractSecurityDutyDetails(@RequestBody(required = false) ZjTzGeneralContractSecurityDuty zjTzGeneralContractSecurityDuty) {
        return zjTzGeneralContractSecurityDutyService.getZjTzGeneralContractSecurityDutyDetails(zjTzGeneralContractSecurityDuty);
    }

    @ApiOperation(value="新增与总承包单位签订安全责任书", notes="新增与总承包单位签订安全责任书")
    @ApiImplicitParam(name = "zjTzGeneralContractSecurityDuty", value = "与总承包单位签订安全责任书entity", dataType = "ZjTzGeneralContractSecurityDuty")
    @RequireToken
    @PostMapping("/addZjTzGeneralContractSecurityDuty")
    public ResponseEntity addZjTzGeneralContractSecurityDuty(@RequestBody(required = false) ZjTzGeneralContractSecurityDuty zjTzGeneralContractSecurityDuty) {
        return zjTzGeneralContractSecurityDutyService.saveZjTzGeneralContractSecurityDuty(zjTzGeneralContractSecurityDuty);
    }

    @ApiOperation(value="更新与总承包单位签订安全责任书", notes="更新与总承包单位签订安全责任书")
    @ApiImplicitParam(name = "zjTzGeneralContractSecurityDuty", value = "与总承包单位签订安全责任书entity", dataType = "ZjTzGeneralContractSecurityDuty")
    @RequireToken
    @PostMapping("/updateZjTzGeneralContractSecurityDuty")
    public ResponseEntity updateZjTzGeneralContractSecurityDuty(@RequestBody(required = false) ZjTzGeneralContractSecurityDuty zjTzGeneralContractSecurityDuty) {
        return zjTzGeneralContractSecurityDutyService.updateZjTzGeneralContractSecurityDuty(zjTzGeneralContractSecurityDuty);
    }
    
    @ApiOperation(value="新增与总承包单位签订安全责任书（添加附件）", notes="新增与总承包单位签订安全责任书（添加附件）")
    @ApiImplicitParam(name = "zjTzGeneralContractSecurityDuty", value = "与总承包单位签订安全责任书entity", dataType = "ZjTzGeneralContractSecurityDuty")
    @RequireToken
    @PostMapping("/addZjTzGeneralContractSecurityDutyAddFile")
    public ResponseEntity addZjTzGeneralContractSecurityDutyAddFile(@RequestBody(required = false) ZjTzGeneralContractSecurityDuty zjTzGeneralContractSecurityDuty) {
    	return zjTzGeneralContractSecurityDutyService.saveZjTzGeneralContractSecurityDutyAddFile(zjTzGeneralContractSecurityDuty);
    }
    
    @ApiOperation(value="更新与总承包单位签订安全责任书（添加附件）", notes="更新与总承包单位签订安全责任书（添加附件）")
    @ApiImplicitParam(name = "zjTzGeneralContractSecurityDuty", value = "与总承包单位签订安全责任书entity", dataType = "ZjTzGeneralContractSecurityDuty")
    @RequireToken
    @PostMapping("/updateZjTzGeneralContractSecurityDutyAddFile")
    public ResponseEntity updateZjTzGeneralContractSecurityDutyAddFile(@RequestBody(required = false) ZjTzGeneralContractSecurityDuty zjTzGeneralContractSecurityDuty) {
    	return zjTzGeneralContractSecurityDutyService.updateZjTzGeneralContractSecurityDutyAddFile(zjTzGeneralContractSecurityDuty);
    }

    @ApiOperation(value="删除与总承包单位签订安全责任书", notes="删除与总承包单位签订安全责任书")
    @ApiImplicitParam(name = "zjTzGeneralContractSecurityDutyList", value = "与总承包单位签订安全责任书List", dataType = "List<ZjTzGeneralContractSecurityDuty>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZjTzGeneralContractSecurityDuty")
    public ResponseEntity batchDeleteUpdateZjTzGeneralContractSecurityDuty(@RequestBody(required = false) List<ZjTzGeneralContractSecurityDuty> zjTzGeneralContractSecurityDutyList) {
        return zjTzGeneralContractSecurityDutyService.batchDeleteUpdateZjTzGeneralContractSecurityDuty(zjTzGeneralContractSecurityDutyList);
    }

}
