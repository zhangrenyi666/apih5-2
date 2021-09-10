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
import com.apih5.mybatis.pojo.ZjTzPppTermBase;
import com.apih5.service.ZjTzPppTermBaseService;

@RestController
public class ZjTzPppTermBaseController {

    @Autowired(required = true)
    private ZjTzPppTermBaseService zjTzPppTermBaseService;

    @ApiOperation(value="查询ppp合同风险条款设置", notes="查询ppp合同风险条款设置")
    @ApiImplicitParam(name = "zjTzPppTermBase", value = "ppp合同风险条款设置entity", dataType = "ZjTzPppTermBase")
    @RequireToken
    @PostMapping("/getZjTzPppTermBaseList")
    public ResponseEntity getZjTzPppTermBaseList(@RequestBody(required = false) ZjTzPppTermBase zjTzPppTermBase) {
        return zjTzPppTermBaseService.getZjTzPppTermBaseListByCondition(zjTzPppTermBase);
    }

    @ApiOperation(value="查询详情ppp合同风险条款设置", notes="查询详情ppp合同风险条款设置")
    @ApiImplicitParam(name = "zjTzPppTermBase", value = "ppp合同风险条款设置entity", dataType = "ZjTzPppTermBase")
    @RequireToken
    @PostMapping("/getZjTzPppTermBaseDetails")
    public ResponseEntity getZjTzPppTermBaseDetails(@RequestBody(required = false) ZjTzPppTermBase zjTzPppTermBase) {
        return zjTzPppTermBaseService.getZjTzPppTermBaseDetails(zjTzPppTermBase);
    }

    @ApiOperation(value="新增ppp合同风险条款设置", notes="新增ppp合同风险条款设置")
    @ApiImplicitParam(name = "zjTzPppTermBase", value = "ppp合同风险条款设置entity", dataType = "ZjTzPppTermBase")
    @RequireToken
    @PostMapping("/addZjTzPppTermBase")
    public ResponseEntity addZjTzPppTermBase(@RequestBody(required = false) ZjTzPppTermBase zjTzPppTermBase) {
        return zjTzPppTermBaseService.saveZjTzPppTermBase(zjTzPppTermBase);
    }

    @ApiOperation(value="更新ppp合同风险条款设置", notes="更新ppp合同风险条款设置")
    @ApiImplicitParam(name = "zjTzPppTermBase", value = "ppp合同风险条款设置entity", dataType = "ZjTzPppTermBase")
    @RequireToken
    @PostMapping("/updateZjTzPppTermBase")
    public ResponseEntity updateZjTzPppTermBase(@RequestBody(required = false) ZjTzPppTermBase zjTzPppTermBase) {
        return zjTzPppTermBaseService.updateZjTzPppTermBase(zjTzPppTermBase);
    }

    @ApiOperation(value="删除ppp合同风险条款设置", notes="删除ppp合同风险条款设置")
    @ApiImplicitParam(name = "zjTzPppTermBaseList", value = "ppp合同风险条款设置List", dataType = "List<ZjTzPppTermBase>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZjTzPppTermBase")
    public ResponseEntity batchDeleteUpdateZjTzPppTermBase(@RequestBody(required = false) List<ZjTzPppTermBase> zjTzPppTermBaseList) {
        return zjTzPppTermBaseService.batchDeleteUpdateZjTzPppTermBase(zjTzPppTermBaseList);
    }
    
    @ApiOperation(value="批量导入ppp合同风险条款设置", notes="批量导入ppp合同风险条款设置")
    @ApiImplicitParam(name = "zjTzPppTermBase", value = "ppp合同风险条款设置entity", dataType = "ZjTzPppTermBase")
    @RequireToken
    @PostMapping("/batchImportZjTzPppTermBase")
    public ResponseEntity batchImportZjTzPppTermBase(@RequestBody(required = false) ZjTzPppTermBase zjTzPppTermBase) {
        return zjTzPppTermBaseService.batchImportZjTzPppTermBase(zjTzPppTermBase);
    }
    
}
