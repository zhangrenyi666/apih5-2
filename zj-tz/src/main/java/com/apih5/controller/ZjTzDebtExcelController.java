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
import com.apih5.mybatis.pojo.ZjTzDebtExcel;
import com.apih5.service.ZjTzDebtExcelService;

@RestController
public class ZjTzDebtExcelController {

    @Autowired(required = true)
    private ZjTzDebtExcelService zjTzDebtExcelService;

    @ApiOperation(value="查询资产负债情况导入", notes="查询资产负债情况导入")
    @ApiImplicitParam(name = "zjTzDebtExcel", value = "资产负债情况导入entity", dataType = "ZjTzDebtExcel")
    @RequireToken
    @PostMapping("/getZjTzDebtExcelList")
    public ResponseEntity getZjTzDebtExcelList(@RequestBody(required = false) ZjTzDebtExcel zjTzDebtExcel) {
        return zjTzDebtExcelService.getZjTzDebtExcelListByCondition(zjTzDebtExcel);
    }

    @ApiOperation(value="查询详情资产负债情况导入", notes="查询详情资产负债情况导入")
    @ApiImplicitParam(name = "zjTzDebtExcel", value = "资产负债情况导入entity", dataType = "ZjTzDebtExcel")
    @RequireToken
    @PostMapping("/getZjTzDebtExcelDetails")
    public ResponseEntity getZjTzDebtExcelDetails(@RequestBody(required = false) ZjTzDebtExcel zjTzDebtExcel) {
        return zjTzDebtExcelService.getZjTzDebtExcelDetails(zjTzDebtExcel);
    }

    @ApiOperation(value="新增资产负债情况导入", notes="新增资产负债情况导入")
    @ApiImplicitParam(name = "zjTzDebtExcel", value = "资产负债情况导入entity", dataType = "ZjTzDebtExcel")
    @RequireToken
    @PostMapping("/addZjTzDebtExcel")
    public ResponseEntity addZjTzDebtExcel(@RequestBody(required = false) ZjTzDebtExcel zjTzDebtExcel) {
        return zjTzDebtExcelService.saveZjTzDebtExcel(zjTzDebtExcel);
    }

    @ApiOperation(value="更新资产负债情况导入", notes="更新资产负债情况导入")
    @ApiImplicitParam(name = "zjTzDebtExcel", value = "资产负债情况导入entity", dataType = "ZjTzDebtExcel")
    @RequireToken
    @PostMapping("/updateZjTzDebtExcel")
    public ResponseEntity updateZjTzDebtExcel(@RequestBody(required = false) ZjTzDebtExcel zjTzDebtExcel) {
        return zjTzDebtExcelService.updateZjTzDebtExcel(zjTzDebtExcel);
    }

    @ApiOperation(value="删除资产负债情况导入", notes="删除资产负债情况导入")
    @ApiImplicitParam(name = "zjTzDebtExcelList", value = "资产负债情况导入List", dataType = "List<ZjTzDebtExcel>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZjTzDebtExcel")
    public ResponseEntity batchDeleteUpdateZjTzDebtExcel(@RequestBody(required = false) List<ZjTzDebtExcel> zjTzDebtExcelList) {
        return zjTzDebtExcelService.batchDeleteUpdateZjTzDebtExcel(zjTzDebtExcelList);
    }
    
    @ApiOperation(value="导入资产负债情况导入", notes="导入资产负债情况导入")
    @ApiImplicitParam(name = "zjTzDebtExcel", value = "资产负债情况导入entity", dataType = "ZjTzDebtExcel")
    @RequireToken
    @PostMapping("/batchImportZjTzDebtExcel")
    public ResponseEntity batchImportZjTzDebtExcel(@RequestBody(required = false) ZjTzDebtExcel zjTzDebtExcel) {
        return zjTzDebtExcelService.batchImportZjTzDebtExcel(zjTzDebtExcel);
    }
    
    

}
