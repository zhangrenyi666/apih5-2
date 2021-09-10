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
import com.apih5.mybatis.pojo.ZxEqEquipFactReal;
import com.apih5.service.ZxEqEquipFactRealService;

@RestController
public class ZxEqEquipFactRealController {

    @Autowired(required = true)
    private ZxEqEquipFactRealService zxEqEquipFactRealService;

    @ApiOperation(value="查询项目设备管理-设备实际验收台账(月)", notes="查询项目设备管理-设备实际验收台账(月)")
    @ApiImplicitParam(name = "zxEqEquipFactReal", value = "项目设备管理-设备实际验收台账(月)entity", dataType = "ZxEqEquipFactReal")
    @RequireToken
    @PostMapping("/getZxEqEquipFactRealList")
    public ResponseEntity getZxEqEquipFactRealList(@RequestBody(required = false) ZxEqEquipFactReal zxEqEquipFactReal) {
        return zxEqEquipFactRealService.getZxEqEquipFactRealListByCondition(zxEqEquipFactReal);
    }

    @ApiOperation(value="查询详情项目设备管理-设备实际验收台账(月)", notes="查询详情项目设备管理-设备实际验收台账(月)")
    @ApiImplicitParam(name = "zxEqEquipFactReal", value = "项目设备管理-设备实际验收台账(月)entity", dataType = "ZxEqEquipFactReal")
    @RequireToken
    @PostMapping("/getZxEqEquipFactRealDetails")
    public ResponseEntity getZxEqEquipFactRealDetails(@RequestBody(required = false) ZxEqEquipFactReal zxEqEquipFactReal) {
        return zxEqEquipFactRealService.getZxEqEquipFactRealDetails(zxEqEquipFactReal);
    }

    @ApiOperation(value="新增项目设备管理-设备实际验收台账(月)", notes="新增项目设备管理-设备实际验收台账(月)")
    @ApiImplicitParam(name = "zxEqEquipFactReal", value = "项目设备管理-设备实际验收台账(月)entity", dataType = "ZxEqEquipFactReal")
    @RequireToken
    @PostMapping("/addZxEqEquipFactReal")
    public ResponseEntity addZxEqEquipFactReal(@RequestBody(required = false) ZxEqEquipFactReal zxEqEquipFactReal) {
        return zxEqEquipFactRealService.saveZxEqEquipFactReal(zxEqEquipFactReal);
    }

    @ApiOperation(value="更新项目设备管理-设备实际验收台账(月)", notes="更新项目设备管理-设备实际验收台账(月)")
    @ApiImplicitParam(name = "zxEqEquipFactReal", value = "项目设备管理-设备实际验收台账(月)entity", dataType = "ZxEqEquipFactReal")
    @RequireToken
    @PostMapping("/updateZxEqEquipFactReal")
    public ResponseEntity updateZxEqEquipFactReal(@RequestBody(required = false) ZxEqEquipFactReal zxEqEquipFactReal) {
        return zxEqEquipFactRealService.updateZxEqEquipFactReal(zxEqEquipFactReal);
    }

    @ApiOperation(value="删除项目设备管理-设备实际验收台账(月)", notes="删除项目设备管理-设备实际验收台账(月)")
    @ApiImplicitParam(name = "zxEqEquipFactRealList", value = "项目设备管理-设备实际验收台账(月)List", dataType = "List<ZxEqEquipFactReal>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxEqEquipFactReal")
    public ResponseEntity batchDeleteUpdateZxEqEquipFactReal(@RequestBody(required = false) List<ZxEqEquipFactReal> zxEqEquipFactRealList) {
        return zxEqEquipFactRealService.batchDeleteUpdateZxEqEquipFactReal(zxEqEquipFactRealList);
    }
    
    @ApiOperation(value="同步项目设备管理-设备实际验收台账(月)", notes="同步项目设备管理-设备实际验收台账(月)")
    @ApiImplicitParam(name = "zxEqEquipFactReal", value = "项目设备管理-设备实际验收台账(月)entity", dataType = "ZxEqEquipFactReal")
    @RequireToken
    @PostMapping("/syncZxEqEquipFactReal")
    public ResponseEntity syncZxEqEquipFactReal(@RequestBody(required = false) ZxEqEquipFactReal zxEqEquipFactReal) {
        return zxEqEquipFactRealService.syncZxEqEquipFactReal(zxEqEquipFactReal);
    }
    
    @ApiOperation(value="审核项目设备管理-设备实际验收台账(月)", notes="审核项目设备管理-设备实际验收台账(月)")
    @ApiImplicitParam(name = "zxEqEquipFactReal", value = "项目设备管理-设备实际验收台账(月)entity", dataType = "ZxEqEquipFactReal")
    @RequireToken
    @PostMapping("/auditZxEqEquipFactReal")
    public ResponseEntity auditZxEqEquipFactReal(@RequestBody(required = false) ZxEqEquipFactReal zxEqEquipFactReal) {
        return zxEqEquipFactRealService.auditZxEqEquipFactReal(zxEqEquipFactReal);
    }

}
