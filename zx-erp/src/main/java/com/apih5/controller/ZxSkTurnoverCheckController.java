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
import com.apih5.mybatis.pojo.ZxSkTurnoverCheck;
import com.apih5.service.ZxSkTurnoverCheckService;

@RestController
public class ZxSkTurnoverCheckController {

    @Autowired(required = true)
    private ZxSkTurnoverCheckService zxSkTurnoverCheckService;

    @ApiOperation(value="查询周转材料冲帐", notes="查询周转材料冲帐")
    @ApiImplicitParam(name = "zxSkTurnoverCheck", value = "周转材料冲帐entity", dataType = "ZxSkTurnoverCheck")
    @RequireToken
    @PostMapping("/getZxSkTurnoverCheckList")
    public ResponseEntity getZxSkTurnoverCheckList(@RequestBody(required = false) ZxSkTurnoverCheck zxSkTurnoverCheck) {
        return zxSkTurnoverCheckService.getZxSkTurnoverCheckListByCondition(zxSkTurnoverCheck);
    }

    @ApiOperation(value="查询详情周转材料冲帐", notes="查询详情周转材料冲帐")
    @ApiImplicitParam(name = "zxSkTurnoverCheck", value = "周转材料冲帐entity", dataType = "ZxSkTurnoverCheck")
    @RequireToken
    @PostMapping("/getZxSkTurnoverCheckDetail")
    public ResponseEntity getZxSkTurnoverCheckDetail(@RequestBody(required = false) ZxSkTurnoverCheck zxSkTurnoverCheck) {
        return zxSkTurnoverCheckService.getZxSkTurnoverCheckDetail(zxSkTurnoverCheck);
    }

    @ApiOperation(value="新增周转材料冲帐", notes="新增周转材料冲帐")
    @ApiImplicitParam(name = "zxSkTurnoverCheck", value = "周转材料冲帐entity", dataType = "ZxSkTurnoverCheck")
    @RequireToken
    @PostMapping("/addZxSkTurnoverCheck")
    public ResponseEntity addZxSkTurnoverCheck(@RequestBody(required = false) ZxSkTurnoverCheck zxSkTurnoverCheck) {
        return zxSkTurnoverCheckService.saveZxSkTurnoverCheck(zxSkTurnoverCheck);
    }

    @ApiOperation(value="更新周转材料冲帐", notes="更新周转材料冲帐")
    @ApiImplicitParam(name = "zxSkTurnoverCheck", value = "周转材料冲帐entity", dataType = "ZxSkTurnoverCheck")
    @RequireToken
    @PostMapping("/updateZxSkTurnoverCheck")
    public ResponseEntity updateZxSkTurnoverCheck(@RequestBody(required = false) ZxSkTurnoverCheck zxSkTurnoverCheck) {
        return zxSkTurnoverCheckService.updateZxSkTurnoverCheck(zxSkTurnoverCheck);
    }

    @ApiOperation(value="删除周转材料冲帐", notes="删除周转材料冲帐")
    @ApiImplicitParam(name = "zxSkTurnoverCheckList", value = "周转材料冲帐List", dataType = "List<ZxSkTurnoverCheck>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxSkTurnoverCheck")
    public ResponseEntity batchDeleteUpdateZxSkTurnoverCheck(@RequestBody(required = false) List<ZxSkTurnoverCheck> zxSkTurnoverCheckList) {
        return zxSkTurnoverCheckService.batchDeleteUpdateZxSkTurnoverCheck(zxSkTurnoverCheckList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

    //编号
    @ApiOperation(value = "获取周转材料冲帐编号", notes = "获取周转材料冲帐编号")
    @ApiImplicitParam(name = "zxSkTurnoverCheck", value = "周转材料冲账entity", dataType = "zxSkTurnoverCheck")
    @RequireToken
    @PostMapping("/getZxSkTurnoverCheckNo")
    public ResponseEntity getZxSkTurnoverCheckNo(@RequestBody(required = false) ZxSkTurnoverCheck zxSkTurnoverCheck) {
        return zxSkTurnoverCheckService.getZxSkTurnoverCheckNo(zxSkTurnoverCheck);
    }

    @ApiOperation(value = "获取周转材料预收单", notes = "获取周转材料预收单")
    @ApiImplicitParam(name = "zxSkTurnoverCheck", value = "周转材料冲账entity", dataType = "zxSkTurnoverCheck")
    @RequireToken
    @PostMapping("/getZxSkTurnoverCheckReceive")
    public ResponseEntity getZxSkTurnoverCheckReceive(@RequestBody(required = false) ZxSkTurnoverCheck zxSkTurnoverCheck) {
        return zxSkTurnoverCheckService.getZxSkTurnoverCheckReceive(zxSkTurnoverCheck);
    }

    @ApiOperation(value = "审核周转材料冲账单", notes = "审核周转材料冲账单")
    @ApiImplicitParam(name = "zxSkTurnoverCheck", value = "周转材料冲账entity", dataType = "zxSkTurnoverCheck")
    @RequireToken
    @PostMapping("/checkZxSkTurnoverCheck")
    public ResponseEntity checkZxSkTurnoverCheck(@RequestBody(required = false) ZxSkTurnoverCheck zxSkTurnoverCheck) {
        return zxSkTurnoverCheckService.checkZxSkTurnoverCheck(zxSkTurnoverCheck);
    }

    @ApiOperation(value="周转材报表管理-导出周转材料冲帐台账报表", notes="周转材报表管理-导出周转材料冲帐台账报表")
    @ApiImplicitParam(name = "zxSkTurnoverCheck", value = "周转材料冲帐entity", dataType = "ZxSkTurnoverCheck")
    @PostMapping("/ureportZxSkTurnoverCheckList")
    public List<ZxSkTurnoverCheck> ureportZxSkTurnoverCheckList(@RequestBody(required = false) ZxSkTurnoverCheck zxSkTurnoverCheck) {
        return zxSkTurnoverCheckService.ureportZxSkTurnoverCheckList(zxSkTurnoverCheck);
    }

    @ApiOperation(value="周转材报表管理-导出周转材料冲帐台账报表前端查看", notes="周转材报表管理-导出周转材料冲帐台账报表前端查看")
    @ApiImplicitParam(name = "zxSkTurnoverCheck", value = "周转材料冲帐entity", dataType = "ZxSkTurnoverCheck")
    @RequireToken
    @PostMapping("/getUreportZxSkTurnoverCheckList")
    public ResponseEntity getUreportZxSkTurnoverCheckList(@RequestBody(required = false) ZxSkTurnoverCheck zxSkTurnoverCheck) {
        return zxSkTurnoverCheckService.getUreportZxSkTurnoverCheckList(zxSkTurnoverCheck);
    }

    //获取供应商单位
    @ApiOperation(value="获取供应商单位", notes="获取供应商单位")
    @ApiImplicitParam(name = "zxSkTurnoverCheck", value = "周转材料冲帐entity", dataType = "ZxSkTurnoverCheck")
    @PostMapping("/getZxSkTurnoverCheckSupplierList")
    public ResponseEntity getZxSkTurnoverCheckSupplierList(@RequestBody(required = false) ZxSkTurnoverCheck zxSkTurnoverCheck) {
        return zxSkTurnoverCheckService.getZxSkTurnoverCheckSupplierList(zxSkTurnoverCheck);
    }



}
