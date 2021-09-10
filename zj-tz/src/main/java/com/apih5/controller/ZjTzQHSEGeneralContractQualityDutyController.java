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
import com.apih5.mybatis.pojo.ZjTzGeneralContractQualityDuty;
import com.apih5.service.ZjTzGeneralContractQualityDutyService;

@RestController
public class ZjTzQHSEGeneralContractQualityDutyController {

    @Autowired(required = true)
    private ZjTzGeneralContractQualityDutyService zjTzGeneralContractQualityDutyService;

    @ApiOperation(value="查询与总承包单位签订质量责任书", notes="查询与总承包单位签订质量责任书")
    @ApiImplicitParam(name = "zjTzGeneralContractQualityDuty", value = "与总承包单位签订质量责任书entity", dataType = "ZjTzGeneralContractQualityDuty")
    @RequireToken
    @PostMapping("/getZjTzGeneralContractQualityDutyList")
    public ResponseEntity getZjTzGeneralContractQualityDutyList(@RequestBody(required = false) ZjTzGeneralContractQualityDuty zjTzGeneralContractQualityDuty) {
        return zjTzGeneralContractQualityDutyService.getZjTzGeneralContractQualityDutyListByCondition(zjTzGeneralContractQualityDuty);
    }

    @ApiOperation(value="查询详情与总承包单位签订质量责任书", notes="查询详情与总承包单位签订质量责任书")
    @ApiImplicitParam(name = "zjTzGeneralContractQualityDuty", value = "与总承包单位签订质量责任书entity", dataType = "ZjTzGeneralContractQualityDuty")
    @RequireToken
    @PostMapping("/getZjTzGeneralContractQualityDutyDetails")
    public ResponseEntity getZjTzGeneralContractQualityDutyDetails(@RequestBody(required = false) ZjTzGeneralContractQualityDuty zjTzGeneralContractQualityDuty) {
        return zjTzGeneralContractQualityDutyService.getZjTzGeneralContractQualityDutyDetails(zjTzGeneralContractQualityDuty);
    }

    @ApiOperation(value="新增与总承包单位签订质量责任书", notes="新增与总承包单位签订质量责任书")
    @ApiImplicitParam(name = "zjTzGeneralContractQualityDuty", value = "与总承包单位签订质量责任书entity", dataType = "ZjTzGeneralContractQualityDuty")
    @RequireToken
    @PostMapping("/addZjTzGeneralContractQualityDuty")
    public ResponseEntity addZjTzGeneralContractQualityDuty(@RequestBody(required = false) ZjTzGeneralContractQualityDuty zjTzGeneralContractQualityDuty) {
        return zjTzGeneralContractQualityDutyService.saveZjTzGeneralContractQualityDuty(zjTzGeneralContractQualityDuty);
    }

    @ApiOperation(value="更新与总承包单位签订质量责任书", notes="更新与总承包单位签订质量责任书")
    @ApiImplicitParam(name = "zjTzGeneralContractQualityDuty", value = "与总承包单位签订质量责任书entity", dataType = "ZjTzGeneralContractQualityDuty")
    @RequireToken
    @PostMapping("/updateZjTzGeneralContractQualityDuty")
    public ResponseEntity updateZjTzGeneralContractQualityDuty(@RequestBody(required = false) ZjTzGeneralContractQualityDuty zjTzGeneralContractQualityDuty) {
        return zjTzGeneralContractQualityDutyService.updateZjTzGeneralContractQualityDuty(zjTzGeneralContractQualityDuty);
    }
    
    @ApiOperation(value="新增与总承包单位签订质量责任书（增加附件）", notes="新增与总承包单位签订质量责任书（增加附件）")
    @ApiImplicitParam(name = "zjTzGeneralContractQualityDuty", value = "与总承包单位签订质量责任书entity", dataType = "ZjTzGeneralContractQualityDuty")
    @RequireToken
    @PostMapping("/addZjTzGeneralContractQualityDutyAddFile")
    public ResponseEntity addZjTzGeneralContractQualityDutyAddFile(@RequestBody(required = false) ZjTzGeneralContractQualityDuty zjTzGeneralContractQualityDuty) {
    	return zjTzGeneralContractQualityDutyService.saveZjTzGeneralContractQualityDutyAddFile(zjTzGeneralContractQualityDuty);
    }
    
    @ApiOperation(value="更新与总承包单位签订质量责任书（增加附件）", notes="更新与总承包单位签订质量责任书（增加附件）")
    @ApiImplicitParam(name = "zjTzGeneralContractQualityDuty", value = "与总承包单位签订质量责任书entity", dataType = "ZjTzGeneralContractQualityDuty")
    @RequireToken
    @PostMapping("/updateZjTzGeneralContractQualityDutyAddFile")
    public ResponseEntity updateZjTzGeneralContractQualityDutyAddFile(@RequestBody(required = false) ZjTzGeneralContractQualityDuty zjTzGeneralContractQualityDuty) {
    	return zjTzGeneralContractQualityDutyService.updateZjTzGeneralContractQualityDutyAddFile(zjTzGeneralContractQualityDuty);
    }

    @ApiOperation(value="删除与总承包单位签订质量责任书", notes="删除与总承包单位签订质量责任书")
    @ApiImplicitParam(name = "zjTzGeneralContractQualityDutyList", value = "与总承包单位签订质量责任书List", dataType = "List<ZjTzGeneralContractQualityDuty>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZjTzGeneralContractQualityDuty")
    public ResponseEntity batchDeleteUpdateZjTzGeneralContractQualityDuty(@RequestBody(required = false) List<ZjTzGeneralContractQualityDuty> zjTzGeneralContractQualityDutyList) {
        return zjTzGeneralContractQualityDutyService.batchDeleteUpdateZjTzGeneralContractQualityDuty(zjTzGeneralContractQualityDutyList);
    }

}
