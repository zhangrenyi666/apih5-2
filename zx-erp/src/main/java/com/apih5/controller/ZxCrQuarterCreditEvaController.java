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
import com.apih5.mybatis.pojo.ZxCrQuarterCreditEva;
import com.apih5.service.ZxCrQuarterCreditEvaService;

@RestController
public class ZxCrQuarterCreditEvaController {

    @Autowired(required = true)
    private ZxCrQuarterCreditEvaService zxCrQuarterCreditEvaService;

    @ApiOperation(value="查询公司季度信用评价", notes="查询公司季度信用评价")
    @ApiImplicitParam(name = "zxCrQuarterCreditEva", value = "公司季度信用评价entity", dataType = "ZxCrQuarterCreditEva")
    @RequireToken
    @PostMapping("/getZxCrQuarterCreditEvaList")
    public ResponseEntity getZxCrQuarterCreditEvaList(@RequestBody(required = false) ZxCrQuarterCreditEva zxCrQuarterCreditEva) {
        return zxCrQuarterCreditEvaService.getZxCrQuarterCreditEvaListByCondition(zxCrQuarterCreditEva);
    }

    @ApiOperation(value="查询详情公司季度信用评价", notes="查询详情公司季度信用评价")
    @ApiImplicitParam(name = "zxCrQuarterCreditEva", value = "公司季度信用评价entity", dataType = "ZxCrQuarterCreditEva")
    @RequireToken
    @PostMapping("/getZxCrQuarterCreditEvaDetail")
    public ResponseEntity getZxCrQuarterCreditEvaDetail(@RequestBody(required = false) ZxCrQuarterCreditEva zxCrQuarterCreditEva) {
        return zxCrQuarterCreditEvaService.getZxCrQuarterCreditEvaDetail(zxCrQuarterCreditEva);
    }

    @ApiOperation(value="新增公司季度信用评价", notes="新增公司季度信用评价")
    @ApiImplicitParam(name = "zxCrQuarterCreditEva", value = "公司季度信用评价entity", dataType = "ZxCrQuarterCreditEva")
    @RequireToken
    @PostMapping("/addZxCrQuarterCreditEva")
    public ResponseEntity addZxCrQuarterCreditEva(@RequestBody(required = false) ZxCrQuarterCreditEva zxCrQuarterCreditEva) {
        return zxCrQuarterCreditEvaService.saveZxCrQuarterCreditEva(zxCrQuarterCreditEva);
    }

    @ApiOperation(value="更新公司季度信用评价", notes="更新公司季度信用评价")
    @ApiImplicitParam(name = "zxCrQuarterCreditEva", value = "公司季度信用评价entity", dataType = "ZxCrQuarterCreditEva")
    @RequireToken
    @PostMapping("/updateZxCrQuarterCreditEva")
    public ResponseEntity updateZxCrQuarterCreditEva(@RequestBody(required = false) ZxCrQuarterCreditEva zxCrQuarterCreditEva) {
        return zxCrQuarterCreditEvaService.updateZxCrQuarterCreditEva(zxCrQuarterCreditEva);
    }

    @ApiOperation(value="删除公司季度信用评价", notes="删除公司季度信用评价")
    @ApiImplicitParam(name = "zxCrQuarterCreditEvaList", value = "公司季度信用评价List", dataType = "List<ZxCrQuarterCreditEva>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxCrQuarterCreditEva")
    public ResponseEntity batchDeleteUpdateZxCrQuarterCreditEva(@RequestBody(required = false) List<ZxCrQuarterCreditEva> zxCrQuarterCreditEvaList) {
        return zxCrQuarterCreditEvaService.batchDeleteUpdateZxCrQuarterCreditEva(zxCrQuarterCreditEvaList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
