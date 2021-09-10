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
import com.apih5.mybatis.pojo.ZxSkStockTransItemMaterialRequisition;
import com.apih5.service.ZxSkStockTransItemMaterialRequisitionService;

@RestController
public class ZxSkStockTransItemMaterialRequisitionController {

    @Autowired(required = true)
    private ZxSkStockTransItemMaterialRequisitionService zxSkStockTransItemMaterialRequisitionService;

    @ApiOperation(value="查询领料单明细", notes="查询领料单明细")
    @ApiImplicitParam(name = "zxSkStockTransItemMaterialRequisition", value = "领料单明细entity", dataType = "ZxSkStockTransItemMaterialRequisition")
    @RequireToken
    @PostMapping("/getZxSkStockTransItemMaterialRequisitionList")
    public ResponseEntity getZxSkStockTransItemMaterialRequisitionList(@RequestBody(required = false) ZxSkStockTransItemMaterialRequisition zxSkStockTransItemMaterialRequisition) {
        return zxSkStockTransItemMaterialRequisitionService.getZxSkStockTransItemMaterialRequisitionListByCondition(zxSkStockTransItemMaterialRequisition);
    }

    @ApiOperation(value="查询详情领料单明细", notes="查询详情领料单明细")
    @ApiImplicitParam(name = "zxSkStockTransItemMaterialRequisition", value = "领料单明细entity", dataType = "ZxSkStockTransItemMaterialRequisition")
    @RequireToken
    @PostMapping("/getZxSkStockTransItemMaterialRequisitionDetails")
    public ResponseEntity getZxSkStockTransItemMaterialRequisitionDetails(@RequestBody(required = false) ZxSkStockTransItemMaterialRequisition zxSkStockTransItemMaterialRequisition) {
        return zxSkStockTransItemMaterialRequisitionService.getZxSkStockTransItemMaterialRequisitionDetails(zxSkStockTransItemMaterialRequisition);
    }

    @ApiOperation(value="新增领料单明细", notes="新增领料单明细")
    @ApiImplicitParam(name = "zxSkStockTransItemMaterialRequisition", value = "领料单明细entity", dataType = "ZxSkStockTransItemMaterialRequisition")
    @RequireToken
    @PostMapping("/addZxSkStockTransItemMaterialRequisition")
    public ResponseEntity addZxSkStockTransItemMaterialRequisition(@RequestBody(required = false) ZxSkStockTransItemMaterialRequisition zxSkStockTransItemMaterialRequisition) {
        return zxSkStockTransItemMaterialRequisitionService.saveZxSkStockTransItemMaterialRequisition(zxSkStockTransItemMaterialRequisition);
    }

    @ApiOperation(value="更新领料单明细", notes="更新领料单明细")
    @ApiImplicitParam(name = "zxSkStockTransItemMaterialRequisition", value = "领料单明细entity", dataType = "ZxSkStockTransItemMaterialRequisition")
    @RequireToken
    @PostMapping("/updateZxSkStockTransItemMaterialRequisition")
    public ResponseEntity updateZxSkStockTransItemMaterialRequisition(@RequestBody(required = false) ZxSkStockTransItemMaterialRequisition zxSkStockTransItemMaterialRequisition) {
        return zxSkStockTransItemMaterialRequisitionService.updateZxSkStockTransItemMaterialRequisition(zxSkStockTransItemMaterialRequisition);
    }

    @ApiOperation(value="删除领料单明细", notes="删除领料单明细")
    @ApiImplicitParam(name = "zxSkStockTransItemMaterialRequisitionList", value = "领料单明细List", dataType = "List<ZxSkStockTransItemMaterialRequisition>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxSkStockTransItemMaterialRequisition")
    public ResponseEntity batchDeleteUpdateZxSkStockTransItemMaterialRequisition(@RequestBody(required = false) List<ZxSkStockTransItemMaterialRequisition> zxSkStockTransItemMaterialRequisitionList) {
        return zxSkStockTransItemMaterialRequisitionService.batchDeleteUpdateZxSkStockTransItemMaterialRequisition(zxSkStockTransItemMaterialRequisitionList);
    }

}
