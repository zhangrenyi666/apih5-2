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
import com.apih5.mybatis.pojo.ZxCrJYearCreditEva;
import com.apih5.service.ZxCrJYearCreditEvaService;

@RestController
public class ZxCrJYearCreditEvaController {

    @Autowired(required = true)
    private ZxCrJYearCreditEvaService zxCrJYearCreditEvaService;

    @ApiOperation(value="查询局年度信用评价", notes="查询局年度信用评价")
    @ApiImplicitParam(name = "zxCrJYearCreditEva", value = "局年度信用评价entity", dataType = "ZxCrJYearCreditEva")
    @RequireToken
    @PostMapping("/getZxCrJYearCreditEvaList")
    public ResponseEntity getZxCrJYearCreditEvaList(@RequestBody(required = false) ZxCrJYearCreditEva zxCrJYearCreditEva) {
        return zxCrJYearCreditEvaService.getZxCrJYearCreditEvaListByCondition(zxCrJYearCreditEva);
    }

    @ApiOperation(value="查询详情局年度信用评价", notes="查询详情局年度信用评价")
    @ApiImplicitParam(name = "zxCrJYearCreditEva", value = "局年度信用评价entity", dataType = "ZxCrJYearCreditEva")
    @RequireToken
    @PostMapping("/getZxCrJYearCreditEvaDetail")
    public ResponseEntity getZxCrJYearCreditEvaDetail(@RequestBody(required = false) ZxCrJYearCreditEva zxCrJYearCreditEva) {
        return zxCrJYearCreditEvaService.getZxCrJYearCreditEvaDetail(zxCrJYearCreditEva);
    }

    @ApiOperation(value="新增局年度信用评价", notes="新增局年度信用评价")
    @ApiImplicitParam(name = "zxCrJYearCreditEva", value = "局年度信用评价entity", dataType = "ZxCrJYearCreditEva")
    @RequireToken
    @PostMapping("/addZxCrJYearCreditEva")
    public ResponseEntity addZxCrJYearCreditEva(@RequestBody(required = false) ZxCrJYearCreditEva zxCrJYearCreditEva) {
        return zxCrJYearCreditEvaService.saveZxCrJYearCreditEva(zxCrJYearCreditEva);
    }

    @ApiOperation(value="更新局年度信用评价", notes="更新局年度信用评价")
    @ApiImplicitParam(name = "zxCrJYearCreditEva", value = "局年度信用评价entity", dataType = "ZxCrJYearCreditEva")
    @RequireToken
    @PostMapping("/updateZxCrJYearCreditEva")
    public ResponseEntity updateZxCrJYearCreditEva(@RequestBody(required = false) ZxCrJYearCreditEva zxCrJYearCreditEva) {
        return zxCrJYearCreditEvaService.updateZxCrJYearCreditEva(zxCrJYearCreditEva);
    }

    @ApiOperation(value="删除局年度信用评价", notes="删除局年度信用评价")
    @ApiImplicitParam(name = "zxCrJYearCreditEvaList", value = "局年度信用评价List", dataType = "List<ZxCrJYearCreditEva>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxCrJYearCreditEva")
    public ResponseEntity batchDeleteUpdateZxCrJYearCreditEva(@RequestBody(required = false) List<ZxCrJYearCreditEva> zxCrJYearCreditEvaList) {
        return zxCrJYearCreditEvaService.batchDeleteUpdateZxCrJYearCreditEva(zxCrJYearCreditEvaList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    
    @ApiOperation(value="更新局年度信用评价", notes="更新局年度信用评价")
    @ApiImplicitParam(name = "zxCrJYearCreditEva", value = "局年度信用评价entity", dataType = "ZxCrJYearCreditEva")
    @RequireToken
    @PostMapping("/updateZxCrJYearCreditEvaAuditStatus")
    public ResponseEntity updateZxCrJYearCreditEvaAuditStatus(@RequestBody(required = false) ZxCrJYearCreditEva zxCrJYearCreditEva) {
        return zxCrJYearCreditEvaService.updateZxCrJYearCreditEvaAuditStatus(zxCrJYearCreditEva);
    }
}
