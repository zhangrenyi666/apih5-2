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
import com.apih5.mybatis.pojo.ZjTzSuperiorUnitQualityDuty;
import com.apih5.service.ZjTzSuperiorUnitQualityDutyService;

@RestController
public class ZjTzQHSESuperiorUnitQualityDutyController {

    @Autowired(required = true)
    private ZjTzSuperiorUnitQualityDutyService zjTzSuperiorUnitQualityDutyService;

    @ApiOperation(value="查询与上级单位签订质量责任书", notes="查询与上级单位签订质量责任书")
    @ApiImplicitParam(name = "zjTzSuperiorUnitQualityDuty", value = "与上级单位签订质量责任书entity", dataType = "ZjTzSuperiorUnitQualityDuty")
    @RequireToken
    @PostMapping("/getZjTzSuperiorUnitQualityDutyList")
    public ResponseEntity getZjTzSuperiorUnitQualityDutyList(@RequestBody(required = false) ZjTzSuperiorUnitQualityDuty zjTzSuperiorUnitQualityDuty) {
        return zjTzSuperiorUnitQualityDutyService.getZjTzSuperiorUnitQualityDutyListByCondition(zjTzSuperiorUnitQualityDuty);
    }

    @ApiOperation(value="查询详情与上级单位签订质量责任书", notes="查询详情与上级单位签订质量责任书")
    @ApiImplicitParam(name = "zjTzSuperiorUnitQualityDuty", value = "与上级单位签订质量责任书entity", dataType = "ZjTzSuperiorUnitQualityDuty")
    @RequireToken
    @PostMapping("/getZjTzSuperiorUnitQualityDutyDetails")
    public ResponseEntity getZjTzSuperiorUnitQualityDutyDetails(@RequestBody(required = false) ZjTzSuperiorUnitQualityDuty zjTzSuperiorUnitQualityDuty) {
        return zjTzSuperiorUnitQualityDutyService.getZjTzSuperiorUnitQualityDutyDetails(zjTzSuperiorUnitQualityDuty);
    }

    @ApiOperation(value="新增与上级单位签订质量责任书", notes="新增与上级单位签订质量责任书")
    @ApiImplicitParam(name = "zjTzSuperiorUnitQualityDuty", value = "与上级单位签订质量责任书entity", dataType = "ZjTzSuperiorUnitQualityDuty")
    @RequireToken
    @PostMapping("/addZjTzSuperiorUnitQualityDuty")
    public ResponseEntity addZjTzSuperiorUnitQualityDuty(@RequestBody(required = false) ZjTzSuperiorUnitQualityDuty zjTzSuperiorUnitQualityDuty) {
        return zjTzSuperiorUnitQualityDutyService.saveZjTzSuperiorUnitQualityDuty(zjTzSuperiorUnitQualityDuty);
    }

    @ApiOperation(value="更新与上级单位签订质量责任书", notes="更新与上级单位签订质量责任书")
    @ApiImplicitParam(name = "zjTzSuperiorUnitQualityDuty", value = "与上级单位签订质量责任书entity", dataType = "ZjTzSuperiorUnitQualityDuty")
    @RequireToken
    @PostMapping("/updateZjTzSuperiorUnitQualityDuty")
    public ResponseEntity updateZjTzSuperiorUnitQualityDuty(@RequestBody(required = false) ZjTzSuperiorUnitQualityDuty zjTzSuperiorUnitQualityDuty) {
        return zjTzSuperiorUnitQualityDutyService.updateZjTzSuperiorUnitQualityDuty(zjTzSuperiorUnitQualityDuty);
    }
    
    @ApiOperation(value="新增与上级单位签订质量责任书（添加附件）", notes="新增与上级单位签订质量责任书（添加附件）")
    @ApiImplicitParam(name = "zjTzSuperiorUnitQualityDuty", value = "与上级单位签订质量责任书entity", dataType = "ZjTzSuperiorUnitQualityDuty")
    @RequireToken
    @PostMapping("/addZjTzSuperiorUnitQualityDutyAddFile")
    public ResponseEntity addZjTzSuperiorUnitQualityDutyAddFile(@RequestBody(required = false) ZjTzSuperiorUnitQualityDuty zjTzSuperiorUnitQualityDuty) {
    	return zjTzSuperiorUnitQualityDutyService.saveZjTzSuperiorUnitQualityDutyAddFile(zjTzSuperiorUnitQualityDuty);
    }
    
    @ApiOperation(value="更新与上级单位签订质量责任书（添加附件）", notes="更新与上级单位签订质量责任书（添加附件）")
    @ApiImplicitParam(name = "zjTzSuperiorUnitQualityDuty", value = "与上级单位签订质量责任书entity", dataType = "ZjTzSuperiorUnitQualityDuty")
    @RequireToken
    @PostMapping("/updateZjTzSuperiorUnitQualityDutyAddFile")
    public ResponseEntity updateZjTzSuperiorUnitQualityDutyAddFile(@RequestBody(required = false) ZjTzSuperiorUnitQualityDuty zjTzSuperiorUnitQualityDuty) {
    	return zjTzSuperiorUnitQualityDutyService.updateZjTzSuperiorUnitQualityDutyAddFile(zjTzSuperiorUnitQualityDuty);
    }

    @ApiOperation(value="删除与上级单位签订质量责任书", notes="删除与上级单位签订质量责任书")
    @ApiImplicitParam(name = "zjTzSuperiorUnitQualityDutyList", value = "与上级单位签订质量责任书List", dataType = "List<ZjTzSuperiorUnitQualityDuty>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZjTzSuperiorUnitQualityDuty")
    public ResponseEntity batchDeleteUpdateZjTzSuperiorUnitQualityDuty(@RequestBody(required = false) List<ZjTzSuperiorUnitQualityDuty> zjTzSuperiorUnitQualityDutyList) {
        return zjTzSuperiorUnitQualityDutyService.batchDeleteUpdateZjTzSuperiorUnitQualityDuty(zjTzSuperiorUnitQualityDutyList);
    }

}
