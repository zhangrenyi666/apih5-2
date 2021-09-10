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
import com.apih5.mybatis.pojo.ZjTzDebt;
import com.apih5.service.ZjTzDebtService;

@RestController
public class ZjTzDebtController {

    @Autowired(required = true)
    private ZjTzDebtService zjTzDebtService;

    @ApiOperation(value="查询资产负债情况", notes="查询资产负债情况")
    @ApiImplicitParam(name = "zjTzDebt", value = "资产负债情况entity", dataType = "ZjTzDebt")
    @RequireToken
    @PostMapping("/getZjTzDebtList")
    public ResponseEntity getZjTzDebtList(@RequestBody(required = false) ZjTzDebt zjTzDebt) {
        return zjTzDebtService.getZjTzDebtListByCondition(zjTzDebt);
    }

    @ApiOperation(value="查询详情资产负债情况", notes="查询详情资产负债情况")
    @ApiImplicitParam(name = "zjTzDebt", value = "资产负债情况entity", dataType = "ZjTzDebt")
    @RequireToken
    @PostMapping("/getZjTzDebtDetails")
    public ResponseEntity getZjTzDebtDetails(@RequestBody(required = false) ZjTzDebt zjTzDebt) {
        return zjTzDebtService.getZjTzDebtDetails(zjTzDebt);
    }

    @ApiOperation(value="新增资产负债情况", notes="新增资产负债情况")
    @ApiImplicitParam(name = "zjTzDebt", value = "资产负债情况entity", dataType = "ZjTzDebt")
    @RequireToken
    @PostMapping("/addZjTzDebt")
    public ResponseEntity addZjTzDebt(@RequestBody(required = false) ZjTzDebt zjTzDebt) {
        return zjTzDebtService.saveZjTzDebt(zjTzDebt);
    }

    @ApiOperation(value="更新资产负债情况", notes="更新资产负债情况")
    @ApiImplicitParam(name = "zjTzDebt", value = "资产负债情况entity", dataType = "ZjTzDebt")
    @RequireToken
    @PostMapping("/updateZjTzDebt")
    public ResponseEntity updateZjTzDebt(@RequestBody(required = false) ZjTzDebt zjTzDebt) {
        return zjTzDebtService.updateZjTzDebt(zjTzDebt);
    }

    @ApiOperation(value="删除资产负债情况", notes="删除资产负债情况")
    @ApiImplicitParam(name = "zjTzDebtList", value = "资产负债情况List", dataType = "List<ZjTzDebt>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZjTzDebt")
    public ResponseEntity batchDeleteUpdateZjTzDebt(@RequestBody(required = false) List<ZjTzDebt> zjTzDebtList) {
        return zjTzDebtService.batchDeleteUpdateZjTzDebt(zjTzDebtList);
    }

}
