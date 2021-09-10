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
import com.apih5.mybatis.pojo.ZxBuBudgetDetails;
import com.apih5.service.ZxBuBudgetDetailsService;

@RestController
public class ZxBuBudgetDetailsController {

    @Autowired(required = true)
    private ZxBuBudgetDetailsService zxBuBudgetDetailsService;

    @ApiOperation(value="查询预算书详细", notes="查询预算书详细")
    @ApiImplicitParam(name = "zxBuBudgetDetails", value = "预算书详细entity", dataType = "ZxBuBudgetDetails")
    @RequireToken
    @PostMapping("/getZxBuBudgetDetailsList")
    public ResponseEntity getZxBuBudgetDetailsList(@RequestBody(required = false) ZxBuBudgetDetails zxBuBudgetDetails) {
        return zxBuBudgetDetailsService.getZxBuBudgetDetailsListByCondition(zxBuBudgetDetails);
    }

    @ApiOperation(value="查询详情预算书详细", notes="查询详情预算书详细")
    @ApiImplicitParam(name = "zxBuBudgetDetails", value = "预算书详细entity", dataType = "ZxBuBudgetDetails")
    @RequireToken
    @PostMapping("/getZxBuBudgetDetailsDetail")
    public ResponseEntity getZxBuBudgetDetailsDetail(@RequestBody(required = false) ZxBuBudgetDetails zxBuBudgetDetails) {
        return zxBuBudgetDetailsService.getZxBuBudgetDetailsDetail(zxBuBudgetDetails);
    }

    @ApiOperation(value="新增预算书详细", notes="新增预算书详细")
    @ApiImplicitParam(name = "zxBuBudgetDetails", value = "预算书详细entity", dataType = "ZxBuBudgetDetails")
    @RequireToken
    @PostMapping("/addZxBuBudgetDetails")
    public ResponseEntity addZxBuBudgetDetails(@RequestBody(required = false) ZxBuBudgetDetails zxBuBudgetDetails) {
        return zxBuBudgetDetailsService.saveZxBuBudgetDetails(zxBuBudgetDetails);
    }

    @ApiOperation(value="更新预算书详细", notes="更新预算书详细")
    @ApiImplicitParam(name = "zxBuBudgetDetails", value = "预算书详细entity", dataType = "ZxBuBudgetDetails")
    @RequireToken
    @PostMapping("/updateZxBuBudgetDetails")
    public ResponseEntity updateZxBuBudgetDetails(@RequestBody(required = false) ZxBuBudgetDetails zxBuBudgetDetails) {
        return zxBuBudgetDetailsService.updateZxBuBudgetDetails(zxBuBudgetDetails);
    }

    @ApiOperation(value="删除预算书详细", notes="删除预算书详细")
    @ApiImplicitParam(name = "zxBuBudgetDetailsList", value = "预算书详细List", dataType = "List<ZxBuBudgetDetails>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxBuBudgetDetails")
    public ResponseEntity batchDeleteUpdateZxBuBudgetDetails(@RequestBody(required = false) List<ZxBuBudgetDetails> zxBuBudgetDetailsList) {
        return zxBuBudgetDetailsService.batchDeleteUpdateZxBuBudgetDetails(zxBuBudgetDetailsList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
