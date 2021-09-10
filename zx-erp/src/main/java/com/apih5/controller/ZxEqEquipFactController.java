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
import com.apih5.mybatis.pojo.ZxEqEquipFact;
import com.apih5.service.ZxEqEquipFactService;

@RestController
public class ZxEqEquipFactController {

    @Autowired(required = true)
    private ZxEqEquipFactService zxEqEquipFactService;

    @ApiOperation(value="查询项目设备管理-设备核实台账(月)", notes="查询项目设备管理-设备核实台账(月)")
    @ApiImplicitParam(name = "zxEqEquipFact", value = "项目设备管理-设备核实台账(月)entity", dataType = "ZxEqEquipFact")
    @RequireToken
    @PostMapping("/getZxEqEquipFactList")
    public ResponseEntity getZxEqEquipFactList(@RequestBody(required = false) ZxEqEquipFact zxEqEquipFact) {
        return zxEqEquipFactService.getZxEqEquipFactListByCondition(zxEqEquipFact);
    }

    @ApiOperation(value="查询详情项目设备管理-设备核实台账(月)", notes="查询详情项目设备管理-设备核实台账(月)")
    @ApiImplicitParam(name = "zxEqEquipFact", value = "项目设备管理-设备核实台账(月)entity", dataType = "ZxEqEquipFact")
    @RequireToken
    @PostMapping("/getZxEqEquipFactDetails")
    public ResponseEntity getZxEqEquipFactDetails(@RequestBody(required = false) ZxEqEquipFact zxEqEquipFact) {
        return zxEqEquipFactService.getZxEqEquipFactDetails(zxEqEquipFact);
    }

    @ApiOperation(value="新增项目设备管理-设备核实台账(月)", notes="新增项目设备管理-设备核实台账(月)")
    @ApiImplicitParam(name = "zxEqEquipFact", value = "项目设备管理-设备核实台账(月)entity", dataType = "ZxEqEquipFact")
    @RequireToken
    @PostMapping("/addZxEqEquipFact")
    public ResponseEntity addZxEqEquipFact(@RequestBody(required = false) ZxEqEquipFact zxEqEquipFact) {
        return zxEqEquipFactService.saveZxEqEquipFact(zxEqEquipFact);
    }

    @ApiOperation(value="更新项目设备管理-设备核实台账(月)", notes="更新项目设备管理-设备核实台账(月)")
    @ApiImplicitParam(name = "zxEqEquipFact", value = "项目设备管理-设备核实台账(月)entity", dataType = "ZxEqEquipFact")
    @RequireToken
    @PostMapping("/updateZxEqEquipFact")
    public ResponseEntity updateZxEqEquipFact(@RequestBody(required = false) ZxEqEquipFact zxEqEquipFact) {
        return zxEqEquipFactService.updateZxEqEquipFact(zxEqEquipFact);
    }

    @ApiOperation(value="删除项目设备管理-设备核实台账(月)", notes="删除项目设备管理-设备核实台账(月)")
    @ApiImplicitParam(name = "zxEqEquipFactList", value = "项目设备管理-设备核实台账(月)List", dataType = "List<ZxEqEquipFact>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxEqEquipFact")
    public ResponseEntity batchDeleteUpdateZxEqEquipFact(@RequestBody(required = false) List<ZxEqEquipFact> zxEqEquipFactList) {
        return zxEqEquipFactService.batchDeleteUpdateZxEqEquipFact(zxEqEquipFactList);
    }

    @ApiOperation(value="查询复制期次列表接口", notes="查询复制期次列表接口")
    @ApiImplicitParam(name = "zxEqEquipFact", value = "项目设备管理-设备核实台账(月)entity", dataType = "ZxEqEquipFact")
    @RequireToken
    @PostMapping("/getZxEqEquipFactListForCopy")
    public ResponseEntity getZxEqEquipFactListForCopy(@RequestBody(required = false) ZxEqEquipFact zxEqEquipFact) {
        return zxEqEquipFactService.getZxEqEquipFactListForCopy(zxEqEquipFact);
    }
    
    @ApiOperation(value="审核项目设备管理-设备核实台账(月)", notes="审核项目设备管理-设备核实台账(月)")
    @ApiImplicitParam(name = "zxEqEquipFact", value = "项目设备管理-设备核实台账(月)entity", dataType = "ZxEqEquipFact")
    @RequireToken
    @PostMapping("/auditZxEqEquipFact")
    public ResponseEntity auditZxEqEquipFact(@RequestBody(required = false) ZxEqEquipFact zxEqEquipFact) {
        return zxEqEquipFactService.auditZxEqEquipFact(zxEqEquipFact);
    }
}
