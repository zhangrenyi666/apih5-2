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
import com.apih5.mybatis.pojo.ZjTzComplianceDeal;
import com.apih5.service.ZjTzComplianceDealService;

@RestController
public class ZjTzComplianceDealController {

    @Autowired(required = true)
    private ZjTzComplianceDealService zjTzComplianceDealService;

    @ApiOperation(value="查询合规办理", notes="查询合规办理")
    @ApiImplicitParam(name = "zjTzComplianceDeal", value = "合规办理entity", dataType = "ZjTzComplianceDeal")
    @RequireToken
    @PostMapping("/getZjTzComplianceDealList")
    public ResponseEntity getZjTzComplianceDealList(@RequestBody(required = false) ZjTzComplianceDeal zjTzComplianceDeal) {
        return zjTzComplianceDealService.getZjTzComplianceDealListByCondition(zjTzComplianceDeal);
    }

    @ApiOperation(value="查询详情合规办理", notes="查询详情合规办理")
    @ApiImplicitParam(name = "zjTzComplianceDeal", value = "合规办理entity", dataType = "ZjTzComplianceDeal")
    @RequireToken
    @PostMapping("/getZjTzComplianceDealDetails")
    public ResponseEntity getZjTzComplianceDealDetails(@RequestBody(required = false) ZjTzComplianceDeal zjTzComplianceDeal) {
        return zjTzComplianceDealService.getZjTzComplianceDealDetails(zjTzComplianceDeal);
    }

    @ApiOperation(value="新增合规办理", notes="新增合规办理")
    @ApiImplicitParam(name = "zjTzComplianceDeal", value = "合规办理entity", dataType = "ZjTzComplianceDeal")
    @RequireToken
    @PostMapping("/addZjTzComplianceDeal")
    public ResponseEntity addZjTzComplianceDeal(@RequestBody(required = false) ZjTzComplianceDeal zjTzComplianceDeal) {
        return zjTzComplianceDealService.saveZjTzComplianceDeal(zjTzComplianceDeal);
    }

    @ApiOperation(value="更新合规办理", notes="更新合规办理")
    @ApiImplicitParam(name = "zjTzComplianceDeal", value = "合规办理entity", dataType = "ZjTzComplianceDeal")
    @RequireToken
    @PostMapping("/updateZjTzComplianceDeal")
    public ResponseEntity updateZjTzComplianceDeal(@RequestBody(required = false) ZjTzComplianceDeal zjTzComplianceDeal) {
        return zjTzComplianceDealService.updateZjTzComplianceDeal(zjTzComplianceDeal);
    }

    @ApiOperation(value="删除合规办理", notes="删除合规办理")
    @ApiImplicitParam(name = "zjTzComplianceDealList", value = "合规办理List", dataType = "List<ZjTzComplianceDeal>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZjTzComplianceDeal")
    public ResponseEntity batchDeleteUpdateZjTzComplianceDeal(@RequestBody(required = false) List<ZjTzComplianceDeal> zjTzComplianceDealList) {
        return zjTzComplianceDealService.batchDeleteUpdateZjTzComplianceDeal(zjTzComplianceDealList);
    }
    
    @ApiOperation(value="保存合规办理所有环节明细接口", notes="保存合规办理所有环节明细接口")
    @ApiImplicitParam(name = "zjTzComplianceDeal", value = "合规办理entity", dataType = "ZjTzComplianceDeal")
    @RequireToken
    @PostMapping("/saveZjTzComplianceDealAllDetail")
    public ResponseEntity saveZjTzComplianceDealAllDetail(@RequestBody(required = false) ZjTzComplianceDeal zjTzComplianceDeal) {
        return zjTzComplianceDealService.saveZjTzComplianceDealAllDetail(zjTzComplianceDeal);
    }
    
    @ApiOperation(value="查询合规办理", notes="查询合规办理")
    @ApiImplicitParam(name = "zjTzComplianceDeal", value = "合规办理entity", dataType = "ZjTzComplianceDeal")
    @PostMapping("/uReportZjTzComplianceDealList")
    public List<ZjTzComplianceDeal> uReportZjTzComplianceDealList(@RequestBody(required = false) ZjTzComplianceDeal zjTzComplianceDeal) {
        return zjTzComplianceDealService.uReportZjTzComplianceDealList(zjTzComplianceDeal);
    }
    

}