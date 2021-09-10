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
import com.apih5.mybatis.pojo.ZxSkStockTransferMaterialRequisition;
import com.apih5.service.ZxSkStockTransferMaterialRequisitionService;

@RestController
public class ZxSkStockTransferMaterialRequisitionController {

    @Autowired(required = true)
    private ZxSkStockTransferMaterialRequisitionService zxSkStockTransferMaterialRequisitionService;

    @ApiOperation(value="查询领料单", notes="查询领料单")
    @ApiImplicitParam(name = "zxSkStockTransferMaterialRequisition", value = "领料单entity", dataType = "ZxSkStockTransferMaterialRequisition")
    @RequireToken
    @PostMapping("/getZxSkStockTransferMaterialRequisitionList")
    public ResponseEntity getZxSkStockTransferMaterialRequisitionList(@RequestBody(required = false) ZxSkStockTransferMaterialRequisition zxSkStockTransferMaterialRequisition) {
        return zxSkStockTransferMaterialRequisitionService.getZxSkStockTransferMaterialRequisitionListByCondition(zxSkStockTransferMaterialRequisition);
    }

    @ApiOperation(value="查询详情领料单", notes="查询详情领料单")
    @ApiImplicitParam(name = "zxSkStockTransferMaterialRequisition", value = "领料单entity", dataType = "ZxSkStockTransferMaterialRequisition")
    @RequireToken
    @PostMapping("/getZxSkStockTransferMaterialRequisitionDetails")
    public ResponseEntity getZxSkStockTransferMaterialRequisitionDetails(@RequestBody(required = false) ZxSkStockTransferMaterialRequisition zxSkStockTransferMaterialRequisition) {
        return zxSkStockTransferMaterialRequisitionService.getZxSkStockTransferMaterialRequisitionDetails(zxSkStockTransferMaterialRequisition);
    }

    @ApiOperation(value="新增领料单", notes="新增领料单")
    @ApiImplicitParam(name = "zxSkStockTransferMaterialRequisition", value = "领料单entity", dataType = "ZxSkStockTransferMaterialRequisition")
    @RequireToken
    @PostMapping("/addZxSkStockTransferMaterialRequisition")
    public ResponseEntity addZxSkStockTransferMaterialRequisition(@RequestBody(required = false) ZxSkStockTransferMaterialRequisition zxSkStockTransferMaterialRequisition) {
        return zxSkStockTransferMaterialRequisitionService.saveZxSkStockTransferMaterialRequisition(zxSkStockTransferMaterialRequisition);
    }

    @ApiOperation(value="更新领料单", notes="更新领料单")
    @ApiImplicitParam(name = "zxSkStockTransferMaterialRequisition", value = "领料单entity", dataType = "ZxSkStockTransferMaterialRequisition")
    @RequireToken
    @PostMapping("/updateZxSkStockTransferMaterialRequisition")
    public ResponseEntity updateZxSkStockTransferMaterialRequisition(@RequestBody(required = false) ZxSkStockTransferMaterialRequisition zxSkStockTransferMaterialRequisition) {
        return zxSkStockTransferMaterialRequisitionService.updateZxSkStockTransferMaterialRequisition(zxSkStockTransferMaterialRequisition);
    }

    @ApiOperation(value="删除领料单", notes="删除领料单")
    @ApiImplicitParam(name = "zxSkStockTransferMaterialRequisitionList", value = "领料单List", dataType = "List<ZxSkStockTransferMaterialRequisition>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxSkStockTransferMaterialRequisition")
    public ResponseEntity batchDeleteUpdateZxSkStockTransferMaterialRequisition(@RequestBody(required = false) List<ZxSkStockTransferMaterialRequisition> zxSkStockTransferMaterialRequisitionList) {
        return zxSkStockTransferMaterialRequisitionService.batchDeleteUpdateZxSkStockTransferMaterialRequisition(zxSkStockTransferMaterialRequisitionList);
    }

    //审核
    @ApiOperation(value="审核领料单", notes="审核领料单")
    @ApiImplicitParam(name = "zxSkStockTransferMaterialRequisition", value = "审核领料单entity", dataType = "ZxSkStockTransferMaterialRequisition")
    @RequireToken
    @PostMapping("/checkZxSkStockTransferMaterialRequisition")
    public ResponseEntity checkZxSkStockTransferMaterialRequisition(@RequestBody(required = false) ZxSkStockTransferMaterialRequisition zxSkStockTransferMaterialRequisition) {
        return zxSkStockTransferMaterialRequisitionService.checkZxSkStockTransferMaterialRequisition(zxSkStockTransferMaterialRequisition);
    }

    //获取领料单据编号
    @ApiOperation(value = "获取领料单据编号", notes = "获取领料单据编号")
    @ApiImplicitParam(name = "zxSkStockTransferMaterialRequisition", value = "领料单entity", dataType = "ZxSkStockTransferMaterialRequisition")
    @RequireToken
    @PostMapping("/getZxSkStockTransferMaterialRequisitionNo")
    public ResponseEntity getZxSkStockTransferMaterialRequisitionNo(@RequestBody(required = false) ZxSkStockTransferMaterialRequisition zxSkStockTransferMaterialRequisition) {
        return zxSkStockTransferMaterialRequisitionService.getZxSkStockTransferMaterialRequisitionNo(zxSkStockTransferMaterialRequisition);
    }

    //获取退料部门    (根据仓库id,项目id)  //搜索内部单位/领料单位
    @ApiOperation(value = "获取内部单位或领料部门", notes = "获取内部单位或领料部门")
    @ApiImplicitParam(name = "zxSkStockTransferMaterialRequisition", value = "领料单entity", dataType = "ZxSkStockTransferMaterialRequisition")
    @RequireToken
    @PostMapping("/getZxSkStockTransferMaterialRequisitionInOrgName")
    public ResponseEntity getZxSkStockTransferMaterialRequisitionInOrgName(@RequestBody(required = false)ZxSkStockTransferMaterialRequisition zxSkStockTransferMaterialRequisition) {
        return zxSkStockTransferMaterialRequisitionService.getZxSkStockTransferMaterialRequisitionInOrgName(zxSkStockTransferMaterialRequisition);
    }

    //获取分部分项 (根据仓库id,项目id)  //搜索分部分项
    @ApiOperation(value = "获取分部分项", notes = "获取分部分项")
    @ApiImplicitParam(name = "zxSkStockTransferMaterialRequisition", value = "领料单entity", dataType = "ZxSkStockTransferMaterialRequisition")
    @RequireToken
    @PostMapping("/getZxSkStockTransferMaterialRequisitionCbsName")
    public ResponseEntity getZxSkStockTransferMaterialRequisitionCbsName(@RequestBody(required = false)ZxSkStockTransferMaterialRequisition zxSkStockTransferMaterialRequisition) {
        return zxSkStockTransferMaterialRequisitionService.getZxSkStockTransferMaterialRequisitionCbsName(zxSkStockTransferMaterialRequisition);
    }

    //获取物资类别 (根据仓库id,项目id, 内部单位id/领料单位id) // 搜索物资类别
    @ApiOperation(value = "获取物资类别", notes = "获取物资类别")
    @ApiImplicitParam(name = "zxSkStockTransferMaterialRequisition", value = "领料单entity", dataType = "ZxSkStockTransferMaterialRequisition")
    @RequireToken
    @PostMapping("/getZxSkStockTransferMaterialRequisitionResourceName")
    public ResponseEntity getZxSkStockTransferMaterialRequisitionResourceName(@RequestBody(required = false)ZxSkStockTransferMaterialRequisition zxSkStockTransferMaterialRequisition) {
        return zxSkStockTransferMaterialRequisitionService.getZxSkStockTransferMaterialRequisitionResourceName(zxSkStockTransferMaterialRequisition);
    }

    //获取物资编码 (根据仓库id,项目id, 内部单位id/领料单位id,物资大类id) // 搜索物资编码
    @ApiOperation(value = "获取物资编码", notes = "获取物资编码")
    @ApiImplicitParam(name = "zxSkStockTransferMaterialRequisition", value = "领料单entity", dataType = "ZxSkStockTransferMaterialRequisition")
    @RequireToken
    @PostMapping("/getZxSkStockTransferMaterialRequisitionResName")
    public ResponseEntity getZxSkStockTransferMaterialRequisitionResName(@RequestBody(required = false)ZxSkStockTransferMaterialRequisition zxSkStockTransferMaterialRequisition) {
        return zxSkStockTransferMaterialRequisitionService.getZxSkStockTransferMaterialRequisitionResName(zxSkStockTransferMaterialRequisition);
    }

}
