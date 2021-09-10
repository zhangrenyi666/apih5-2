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
import com.apih5.mybatis.pojo.ZxCtSuppliesContrApply;
import com.apih5.service.ZxCtSuppliesContrApplyService;

import cn.hutool.json.JSONObject;

@RestController
public class ZxCtSuppliesContrApplyController {

    @Autowired(required = true)
    private ZxCtSuppliesContrApplyService zxCtSuppliesContrApplyService;

    @ApiOperation(value="查询物资管理类合同评审", notes="查询物资管理类合同评审")
    @ApiImplicitParam(name = "zxCtSuppliesContrApply", value = "物资管理类合同评审entity", dataType = "ZxCtSuppliesContrApply")
    @RequireToken
    @PostMapping("/getZxCtSuppliesContrApplyList")
    public ResponseEntity getZxCtSuppliesContrApplyList(@RequestBody(required = false) ZxCtSuppliesContrApply zxCtSuppliesContrApply) {
        return zxCtSuppliesContrApplyService.getZxCtSuppliesContrApplyListByCondition(zxCtSuppliesContrApply);
    }

    @ApiOperation(value="查询详情物资管理类合同评审", notes="查询详情物资管理类合同评审")
    @ApiImplicitParam(name = "zxCtSuppliesContrApply", value = "物资管理类合同评审entity", dataType = "ZxCtSuppliesContrApply")
    @RequireToken
    @PostMapping("/getZxCtSuppliesContrApplyDetail")
    public ResponseEntity getZxCtSuppliesContrApplyDetail(@RequestBody(required = false) ZxCtSuppliesContrApply zxCtSuppliesContrApply) {
        return zxCtSuppliesContrApplyService.getZxCtSuppliesContrApplyDetail(zxCtSuppliesContrApply);
    }

    @ApiOperation(value="新增物资管理类合同评审", notes="新增物资管理类合同评审")
    @ApiImplicitParam(name = "zxCtSuppliesContrApply", value = "物资管理类合同评审entity", dataType = "ZxCtSuppliesContrApply")
    @RequireToken
    @PostMapping("/addZxCtSuppliesContrApply")
    public ResponseEntity addZxCtSuppliesContrApply(@RequestBody(required = false) ZxCtSuppliesContrApply zxCtSuppliesContrApply) {
        return zxCtSuppliesContrApplyService.saveZxCtSuppliesContrApply(zxCtSuppliesContrApply);
    }

    @ApiOperation(value="更新物资管理类合同评审", notes="更新物资管理类合同评审")
    @ApiImplicitParam(name = "zxCtSuppliesContrApply", value = "物资管理类合同评审entity", dataType = "ZxCtSuppliesContrApply")
    @RequireToken
    @PostMapping("/updateZxCtSuppliesContrApply")
    public ResponseEntity updateZxCtSuppliesContrApply(@RequestBody(required = false) ZxCtSuppliesContrApply zxCtSuppliesContrApply) {
        return zxCtSuppliesContrApplyService.updateZxCtSuppliesContrApply(zxCtSuppliesContrApply);
    }

    @ApiOperation(value="删除物资管理类合同评审", notes="删除物资管理类合同评审")
    @ApiImplicitParam(name = "zxCtSuppliesContrApplyList", value = "物资管理类合同评审List", dataType = "List<ZxCtSuppliesContrApply>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxCtSuppliesContrApply")
    public ResponseEntity batchDeleteUpdateZxCtSuppliesContrApply(@RequestBody(required = false) List<ZxCtSuppliesContrApply> zxCtSuppliesContrApplyList) {
        return zxCtSuppliesContrApplyService.batchDeleteUpdateZxCtSuppliesContrApply(zxCtSuppliesContrApplyList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

    @ApiOperation(value="获取物资管理类合同评审流程详情", notes="获取物资管理类合同评审流程详情")
    @ApiImplicitParam(name = "zxCtSuppliesContrApply", value = "物资管理类合同评审entity", dataType = "ZxCtSuppliesContrApply")
    @RequireToken
    @PostMapping("/getZxCtSuppliesContrApplyFlowDetail")
    public ResponseEntity getZxCtSuppliesContrApplyFlowDetail(@RequestBody(required = false) ZxCtSuppliesContrApply zxCtSuppliesContrApply) {
        return zxCtSuppliesContrApplyService.getZxCtSuppliesContrApplyFlowDetail(zxCtSuppliesContrApply);
    }
    
    @ApiOperation(value="添加物资管理类合同评审流程", notes="添加物资管理类合同评审")
    @ApiImplicitParam(name = "zxCtSuppliesContrApply", value = "物资管理类合同评审entity", dataType = "ZxCtSuppliesContrApply")
    @RequireToken
    @PostMapping("/addZxCtSuppliesContrApplyFlow")
    public ResponseEntity addZxCtSuppliesContrApplyFlow(@RequestBody(required = false) ZxCtSuppliesContrApply zxCtSuppliesContrApply) {
    	return zxCtSuppliesContrApplyService.addZxCtSuppliesContrApplyFlow(zxCtSuppliesContrApply);
    }    
    
    @ApiOperation(value="更新物资管理类合同评审流程", notes="更新物资管理类合同评审")
    @ApiImplicitParam(name = "zxCtSuppliesContrApply", value = "物资管理类合同评审entity", dataType = "ZxCtSuppliesContrApply")
    @RequireToken
    @PostMapping("/updateZxCtSuppliesContrApplyFlow")
    public ResponseEntity updateZxCtSuppliesContrApplyFlow(@RequestBody(required = false) ZxCtSuppliesContrApply zxCtSuppliesContrApply) {
    	return zxCtSuppliesContrApplyService.updateZxCtSuppliesContrApplyFlow(zxCtSuppliesContrApply);
    }

    @ApiOperation(value="根据合同编号和类别编写物资合同编号", notes="根据合同编号和类别编写物资合同编号")
    @ApiImplicitParam(name = "zxCtSuppliesContrApply", value = "物资管理类合同评审entity", dataType = "ZxCtSuppliesContrApply")
    @RequireToken
    @PostMapping("/getZxCtSuppliesContrNoByCode")
    public ResponseEntity getZxCtSuppliesContrNoByCode(@RequestBody(required = false) ZxCtSuppliesContrApply zxCtSuppliesContrApply) {
        return zxCtSuppliesContrApplyService.getZxCtSuppliesContrNoByCode(zxCtSuppliesContrApply);
    }

    @ApiOperation(value="删除物资管理类合同评审及流程", notes="删除物资管理类合同评审及流程")
    @ApiImplicitParam(name = "zxCtSuppliesContrApplyList", value = "物资管理类合同评审List", dataType = "List<ZxCtSuppliesContrApply>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxCtSuppliesContrApplyFlow")
    public ResponseEntity batchDeleteUpdateZxCtSuppliesContrApplyFlow(@RequestBody(required = false) List<ZxCtSuppliesContrApply> zxCtSuppliesContrApplyList) {
        return zxCtSuppliesContrApplyService.batchDeleteUpdateZxCtSuppliesContrApplyFlow(zxCtSuppliesContrApplyList);
    }
    
    @ApiOperation(value="流程分支判断-物资管理类合同评审", notes="流程分支判断-物资管理类合同评审")
    @ApiImplicitParam(name = "zxCtSuppliesContrApply", value = "物资管理类合同评审entity", dataType = "ZxCtSuppliesContrApply")
    @RequireToken
    @PostMapping("/checkZxCtSuppliesContrApplyByFlow")
    public JSONObject checkZxCtSuppliesContrApplyByFlow(@RequestBody(required = false) ZxCtSuppliesContrApply zxCtSuppliesContrApply) {
        return zxCtSuppliesContrApplyService.checkZxCtSuppliesContrApplyByFlow(zxCtSuppliesContrApply);
    }
}
