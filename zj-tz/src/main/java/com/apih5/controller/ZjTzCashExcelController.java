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
import com.apih5.mybatis.pojo.ZjTzCashExcel;
import com.apih5.service.ZjTzCashExcelService;

@RestController
public class ZjTzCashExcelController {

    @Autowired(required = true)
    private ZjTzCashExcelService zjTzCashExcelService;

    @ApiOperation(value="查询现金流量导入", notes="查询现金流量导入")
    @ApiImplicitParam(name = "zjTzCashExcel", value = "现金流量导入entity", dataType = "ZjTzCashExcel")
    @RequireToken
    @PostMapping("/getZjTzCashExcelList")
    public ResponseEntity getZjTzCashExcelList(@RequestBody(required = false) ZjTzCashExcel zjTzCashExcel) {
        return zjTzCashExcelService.getZjTzCashExcelListByCondition(zjTzCashExcel);
    }

    @ApiOperation(value="查询详情现金流量导入", notes="查询详情现金流量导入")
    @ApiImplicitParam(name = "zjTzCashExcel", value = "现金流量导入entity", dataType = "ZjTzCashExcel")
    @RequireToken
    @PostMapping("/getZjTzCashExcelDetails")
    public ResponseEntity getZjTzCashExcelDetails(@RequestBody(required = false) ZjTzCashExcel zjTzCashExcel) {
        return zjTzCashExcelService.getZjTzCashExcelDetails(zjTzCashExcel);
    }

    @ApiOperation(value="新增现金流量导入", notes="新增现金流量导入")
    @ApiImplicitParam(name = "zjTzCashExcel", value = "现金流量导入entity", dataType = "ZjTzCashExcel")
    @RequireToken
    @PostMapping("/addZjTzCashExcel")
    public ResponseEntity addZjTzCashExcel(@RequestBody(required = false) ZjTzCashExcel zjTzCashExcel) {
        return zjTzCashExcelService.saveZjTzCashExcel(zjTzCashExcel);
    }

    @ApiOperation(value="更新现金流量导入", notes="更新现金流量导入")
    @ApiImplicitParam(name = "zjTzCashExcel", value = "现金流量导入entity", dataType = "ZjTzCashExcel")
    @RequireToken
    @PostMapping("/updateZjTzCashExcel")
    public ResponseEntity updateZjTzCashExcel(@RequestBody(required = false) ZjTzCashExcel zjTzCashExcel) {
        return zjTzCashExcelService.updateZjTzCashExcel(zjTzCashExcel);
    }

    @ApiOperation(value="删除现金流量导入", notes="删除现金流量导入")
    @ApiImplicitParam(name = "zjTzCashExcelList", value = "现金流量导入List", dataType = "List<ZjTzCashExcel>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZjTzCashExcel")
    public ResponseEntity batchDeleteUpdateZjTzCashExcel(@RequestBody(required = false) List<ZjTzCashExcel> zjTzCashExcelList) {
        return zjTzCashExcelService.batchDeleteUpdateZjTzCashExcel(zjTzCashExcelList);
    }
    
    @ApiOperation(value="导入现金流量导入", notes="导入现金流量导入")
    @ApiImplicitParam(name = "zjTzCashExcel", value = "现金流量导入entity", dataType = "ZjTzCashExcel")
    @RequireToken
    @PostMapping("/batchImportZjTzCashExcel")
    public ResponseEntity batchImportZjTzCashExcel(@RequestBody(required = false) ZjTzCashExcel zjTzCashExcel) {
        return zjTzCashExcelService.batchImportZjTzCashExcel(zjTzCashExcel);
    }


}
