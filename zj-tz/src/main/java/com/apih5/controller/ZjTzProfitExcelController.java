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
import com.apih5.mybatis.pojo.ZjTzProfitExcel;
import com.apih5.service.ZjTzProfitExcelService;

@RestController
public class ZjTzProfitExcelController {

    @Autowired(required = true)
    private ZjTzProfitExcelService zjTzProfitExcelService;

    @ApiOperation(value="查询利润导入", notes="查询利润导入")
    @ApiImplicitParam(name = "zjTzProfitExcel", value = "利润导入entity", dataType = "ZjTzProfitExcel")
    @RequireToken
    @PostMapping("/getZjTzProfitExcelList")
    public ResponseEntity getZjTzProfitExcelList(@RequestBody(required = false) ZjTzProfitExcel zjTzProfitExcel) {
        return zjTzProfitExcelService.getZjTzProfitExcelListByCondition(zjTzProfitExcel);
    }

    @ApiOperation(value="查询详情利润导入", notes="查询详情利润导入")
    @ApiImplicitParam(name = "zjTzProfitExcel", value = "利润导入entity", dataType = "ZjTzProfitExcel")
    @RequireToken
    @PostMapping("/getZjTzProfitExcelDetails")
    public ResponseEntity getZjTzProfitExcelDetails(@RequestBody(required = false) ZjTzProfitExcel zjTzProfitExcel) {
        return zjTzProfitExcelService.getZjTzProfitExcelDetails(zjTzProfitExcel);
    }

    @ApiOperation(value="新增利润导入", notes="新增利润导入")
    @ApiImplicitParam(name = "zjTzProfitExcel", value = "利润导入entity", dataType = "ZjTzProfitExcel")
    @RequireToken
    @PostMapping("/addZjTzProfitExcel")
    public ResponseEntity addZjTzProfitExcel(@RequestBody(required = false) ZjTzProfitExcel zjTzProfitExcel) {
        return zjTzProfitExcelService.saveZjTzProfitExcel(zjTzProfitExcel);
    }

    @ApiOperation(value="更新利润导入", notes="更新利润导入")
    @ApiImplicitParam(name = "zjTzProfitExcel", value = "利润导入entity", dataType = "ZjTzProfitExcel")
    @RequireToken
    @PostMapping("/updateZjTzProfitExcel")
    public ResponseEntity updateZjTzProfitExcel(@RequestBody(required = false) ZjTzProfitExcel zjTzProfitExcel) {
        return zjTzProfitExcelService.updateZjTzProfitExcel(zjTzProfitExcel);
    }

    @ApiOperation(value="删除利润导入", notes="删除利润导入")
    @ApiImplicitParam(name = "zjTzProfitExcelList", value = "利润导入List", dataType = "List<ZjTzProfitExcel>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZjTzProfitExcel")
    public ResponseEntity batchDeleteUpdateZjTzProfitExcel(@RequestBody(required = false) List<ZjTzProfitExcel> zjTzProfitExcelList) {
        return zjTzProfitExcelService.batchDeleteUpdateZjTzProfitExcel(zjTzProfitExcelList);
    }
    
    @ApiOperation(value="导入利润导入", notes="导入利润导入")
    @ApiImplicitParam(name = "zjTzProfitExcel", value = "利润导入entity", dataType = "ZjTzProfitExcel")
    @RequireToken
    @PostMapping("/batchImportZjTzProfitExcel")
    public ResponseEntity batchImportZjTzProfitExcel(@RequestBody(required = false) ZjTzProfitExcel zjTzProfitExcel) {
        return zjTzProfitExcelService.batchImportZjTzProfitExcel(zjTzProfitExcel);
    }


}
