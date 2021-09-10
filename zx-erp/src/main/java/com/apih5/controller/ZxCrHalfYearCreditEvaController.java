package com.apih5.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.apih5.framework.annotation.RequireToken;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxCrHalfYearCreditEva;
import com.apih5.service.ZxCrHalfYearCreditEvaService;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

@RestController
public class ZxCrHalfYearCreditEvaController {

    @Autowired(required = true)
    private ZxCrHalfYearCreditEvaService zxCrHalfYearCreditEvaService;

    @ApiOperation(value="查询公司半年信用评价", notes="查询公司半年信用评价")
    @ApiImplicitParam(name = "zxCrHalfYearCreditEva", value = "公司半年信用评价entity", dataType = "ZxCrHalfYearCreditEva")
    @RequireToken
    @PostMapping("/getZxCrHalfYearCreditEvaList")
    public ResponseEntity getZxCrHalfYearCreditEvaList(@RequestBody(required = false) ZxCrHalfYearCreditEva zxCrHalfYearCreditEva) {
        return zxCrHalfYearCreditEvaService.getZxCrHalfYearCreditEvaListByCondition(zxCrHalfYearCreditEva);
    }

    @ApiOperation(value="查询详情公司半年信用评价", notes="查询详情公司半年信用评价")
    @ApiImplicitParam(name = "zxCrHalfYearCreditEva", value = "公司半年信用评价entity", dataType = "ZxCrHalfYearCreditEva")
    @RequireToken
    @PostMapping("/getZxCrHalfYearCreditEvaDetail")
    public ResponseEntity getZxCrHalfYearCreditEvaDetail(@RequestBody(required = false) ZxCrHalfYearCreditEva zxCrHalfYearCreditEva) {
        return zxCrHalfYearCreditEvaService.getZxCrHalfYearCreditEvaDetail(zxCrHalfYearCreditEva);
    }

    @ApiOperation(value="新增公司半年信用评价", notes="新增公司半年信用评价")
    @ApiImplicitParam(name = "zxCrHalfYearCreditEva", value = "公司半年信用评价entity", dataType = "ZxCrHalfYearCreditEva")
    @RequireToken
    @PostMapping("/addZxCrHalfYearCreditEva")
    public ResponseEntity addZxCrHalfYearCreditEva(@RequestBody(required = false) ZxCrHalfYearCreditEva zxCrHalfYearCreditEva) {
        return zxCrHalfYearCreditEvaService.saveZxCrHalfYearCreditEva(zxCrHalfYearCreditEva);
    }

    @ApiOperation(value="更新公司半年信用评价", notes="更新公司半年信用评价")
    @ApiImplicitParam(name = "zxCrHalfYearCreditEva", value = "公司半年信用评价entity", dataType = "ZxCrHalfYearCreditEva")
    @RequireToken
    @PostMapping("/updateZxCrHalfYearCreditEva")
    public ResponseEntity updateZxCrHalfYearCreditEva(@RequestBody(required = false) ZxCrHalfYearCreditEva zxCrHalfYearCreditEva) {
        return zxCrHalfYearCreditEvaService.updateZxCrHalfYearCreditEva(zxCrHalfYearCreditEva);
    }

    @ApiOperation(value="删除公司半年信用评价", notes="删除公司半年信用评价")
    @ApiImplicitParam(name = "zxCrHalfYearCreditEvaList", value = "公司半年信用评价List", dataType = "List<ZxCrHalfYearCreditEva>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxCrHalfYearCreditEva")
    public ResponseEntity batchDeleteUpdateZxCrHalfYearCreditEva(@RequestBody(required = false) List<ZxCrHalfYearCreditEva> zxCrHalfYearCreditEvaList) {
        return zxCrHalfYearCreditEvaService.batchDeleteUpdateZxCrHalfYearCreditEva(zxCrHalfYearCreditEvaList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    
    @ApiOperation(value="更新评审状态", notes="更新评审状态")
    @ApiImplicitParam(name = "zxCrHalfYearCreditEva", value = "公司半年信用评价entity", dataType = "List<ZxCrHalfYearCreditEva>")
    @RequireToken
    @PostMapping("/updateZxCrHalfYearCreditEvaAuditStatus")
    public ResponseEntity updateZxCrHalfYearCreditEvaAuditStatus(@RequestBody(required = false) ZxCrHalfYearCreditEva zxCrHalfYearCreditEvaList) {
        return zxCrHalfYearCreditEvaService.updateauditStatus(zxCrHalfYearCreditEvaList);
    }
    
}
