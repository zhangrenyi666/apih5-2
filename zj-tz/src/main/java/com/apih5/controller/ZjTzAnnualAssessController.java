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
import com.apih5.mybatis.pojo.ZjTzAnnualAssess;
import com.apih5.service.ZjTzAnnualAssessService;

@RestController
public class ZjTzAnnualAssessController {

    @Autowired(required = true)
    private ZjTzAnnualAssessService zjTzAnnualAssessService;

    @ApiOperation(value="查询项目年度考核", notes="查询项目年度考核")
    @ApiImplicitParam(name = "zjTzAnnualAssess", value = "项目年度考核entity", dataType = "ZjTzAnnualAssess")
    @RequireToken
    @PostMapping("/getZjTzAnnualAssessList")
    public ResponseEntity getZjTzAnnualAssessList(@RequestBody(required = false) ZjTzAnnualAssess zjTzAnnualAssess) {
        return zjTzAnnualAssessService.getZjTzAnnualAssessListByCondition(zjTzAnnualAssess);
    }

    @ApiOperation(value="查询详情项目年度考核", notes="查询详情项目年度考核")
    @ApiImplicitParam(name = "zjTzAnnualAssess", value = "项目年度考核entity", dataType = "ZjTzAnnualAssess")
    @RequireToken
    @PostMapping("/getZjTzAnnualAssessDetails")
    public ResponseEntity getZjTzAnnualAssessDetails(@RequestBody(required = false) ZjTzAnnualAssess zjTzAnnualAssess) {
        return zjTzAnnualAssessService.getZjTzAnnualAssessDetails(zjTzAnnualAssess);
    }

    @ApiOperation(value="新增项目年度考核", notes="新增项目年度考核")
    @ApiImplicitParam(name = "zjTzAnnualAssess", value = "项目年度考核entity", dataType = "ZjTzAnnualAssess")
    @RequireToken
    @PostMapping("/addZjTzAnnualAssess")
    public ResponseEntity addZjTzAnnualAssess(@RequestBody(required = false) ZjTzAnnualAssess zjTzAnnualAssess) {
        return zjTzAnnualAssessService.saveZjTzAnnualAssess(zjTzAnnualAssess);
    }

    @ApiOperation(value="更新项目年度考核", notes="更新项目年度考核")
    @ApiImplicitParam(name = "zjTzAnnualAssess", value = "项目年度考核entity", dataType = "ZjTzAnnualAssess")
    @RequireToken
    @PostMapping("/updateZjTzAnnualAssess")
    public ResponseEntity updateZjTzAnnualAssess(@RequestBody(required = false) ZjTzAnnualAssess zjTzAnnualAssess) {
        return zjTzAnnualAssessService.updateZjTzAnnualAssess(zjTzAnnualAssess);
    }

    @ApiOperation(value="删除项目年度考核", notes="删除项目年度考核")
    @ApiImplicitParam(name = "zjTzAnnualAssessList", value = "项目年度考核List", dataType = "List<ZjTzAnnualAssess>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZjTzAnnualAssess")
    public ResponseEntity batchDeleteUpdateZjTzAnnualAssess(@RequestBody(required = false) List<ZjTzAnnualAssess> zjTzAnnualAssessList) {
        return zjTzAnnualAssessService.batchDeleteUpdateZjTzAnnualAssess(zjTzAnnualAssessList);
    }
    
    @ApiOperation(value="批量上报项目年度考核", notes="批量上报项目年度考核")
    @ApiImplicitParam(name = "zjTzAnnualAssessList", value = "项目年度考核List", dataType = "List<ZjTzAnnualAssess>")
    @RequireToken
    @PostMapping("/batchReleaseZjTzAnnualAssess")
    public ResponseEntity batchReleaseZjTzAnnualAssess(@RequestBody(required = false) List<ZjTzAnnualAssess> zjTzAnnualAssessList) {
        return zjTzAnnualAssessService.batchReleaseZjTzAnnualAssess(zjTzAnnualAssessList);
    }
    
    @ApiOperation(value="批量退回项目年度考核", notes="批量退回项目年度考核")
    @ApiImplicitParam(name = "zjTzAnnualAssessList", value = "项目年度考核List", dataType = "List<ZjTzAnnualAssess>")
    @RequireToken
    @PostMapping("/batchRecallZjTzAnnualAssess")
    public ResponseEntity batchRecallZjTzAnnualAssess(@RequestBody(required = false) List<ZjTzAnnualAssess> zjTzAnnualAssessList) {
        return zjTzAnnualAssessService.batchRecallZjTzAnnualAssess(zjTzAnnualAssessList);
    }

    @ApiOperation(value="批量导出项目年度考核附件", notes="批量导出项目年度考核附件")
    @ApiImplicitParam(name = "zjTzAnnualAssessList", value = "项目年度考核List", dataType = "List<ZjTzAnnualAssess>")
    @RequireToken
    @PostMapping("/batchExportZjTzAnnualAssessFile")
    public ResponseEntity batchExportZjTzAnnualAssessFile(@RequestBody(required = false) List<ZjTzAnnualAssess> zjTzAnnualAssessList) {
        return zjTzAnnualAssessService.batchExportZjTzAnnualAssessFile(zjTzAnnualAssessList);
    }


}
