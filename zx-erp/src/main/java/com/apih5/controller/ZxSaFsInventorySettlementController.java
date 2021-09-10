package com.apih5.controller;

import java.util.List;

import com.apih5.mybatis.pojo.ZxSaFsSettlement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import com.apih5.framework.annotation.RequireToken;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxSaFsInventorySettlement;
import com.apih5.service.ZxSaFsInventorySettlementService;

@RestController
public class ZxSaFsInventorySettlementController {

    @Autowired(required = true)
    private ZxSaFsInventorySettlementService zxSaFsInventorySettlementService;

    @ApiOperation(value="查询附属类结算清单", notes="查询附属类结算清单")
    @ApiImplicitParam(name = "zxSaFsInventorySettlement", value = "附属类结算清单entity", dataType = "ZxSaFsInventorySettlement")
    @RequireToken
    @PostMapping("/getZxSaFsInventorySettlementList")
    public ResponseEntity getZxSaFsInventorySettlementList(@RequestBody(required = false) ZxSaFsInventorySettlement zxSaFsInventorySettlement) {
        return zxSaFsInventorySettlementService.getZxSaFsInventorySettlementListByCondition(zxSaFsInventorySettlement);
    }

    @ApiOperation(value="查询详情附属类结算清单", notes="查询详情附属类结算清单")
    @ApiImplicitParam(name = "zxSaFsInventorySettlement", value = "附属类结算清单entity", dataType = "ZxSaFsInventorySettlement")
    @RequireToken
    @PostMapping("/getZxSaFsInventorySettlementDetail")
    public ResponseEntity getZxSaFsInventorySettlementDetail(@RequestBody(required = false) ZxSaFsInventorySettlement zxSaFsInventorySettlement) {
        return zxSaFsInventorySettlementService.getZxSaFsInventorySettlementDetail(zxSaFsInventorySettlement);
    }

    @ApiOperation(value="新增附属类结算清单", notes="新增附属类结算清单")
    @ApiImplicitParam(name = "zxSaFsInventorySettlement", value = "附属类结算清单entity", dataType = "ZxSaFsInventorySettlement")
    @RequireToken
    @PostMapping("/addZxSaFsInventorySettlement")
    public ResponseEntity addZxSaFsInventorySettlement(@RequestBody(required = false) ZxSaFsInventorySettlement zxSaFsInventorySettlement) {
        return zxSaFsInventorySettlementService.saveZxSaFsInventorySettlement(zxSaFsInventorySettlement);
    }

    @ApiOperation(value="更新附属类结算清单", notes="更新附属类结算清单")
    @ApiImplicitParam(name = "zxSaFsInventorySettlement", value = "附属类结算清单entity", dataType = "ZxSaFsInventorySettlement")
    @RequireToken
    @PostMapping("/updateZxSaFsInventorySettlement")
    public ResponseEntity updateZxSaFsInventorySettlement(@RequestBody(required = false) ZxSaFsInventorySettlement zxSaFsInventorySettlement) {
        return zxSaFsInventorySettlementService.updateZxSaFsInventorySettlement(zxSaFsInventorySettlement);
    }

    @ApiOperation(value="删除附属类结算清单", notes="删除附属类结算清单")
    @ApiImplicitParam(name = "zxSaFsInventorySettlementList", value = "附属类结算清单List", dataType = "List<ZxSaFsInventorySettlement>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxSaFsInventorySettlement")
    public ResponseEntity batchDeleteUpdateZxSaFsInventorySettlement(@RequestBody(required = false) List<ZxSaFsInventorySettlement> zxSaFsInventorySettlementList) {
        return zxSaFsInventorySettlementService.batchDeleteUpdateZxSaFsInventorySettlement(zxSaFsInventorySettlementList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

    @ApiOperation(value="查询附属类结算清单和清单明细列表", notes="查询附属类结算清单和清单明细列表")
    @ApiImplicitParam(name = "zxSaFsInventorySettlement", value = "附属类结算清单entity", dataType = "ZxSaFsInventorySettlement")
    @RequireToken
    @PostMapping("/getZxSaFsInventoryAndInventoryDetail")
    public ResponseEntity getZxSaFsInventoryAndInventoryDetail(@RequestBody(required = false) ZxSaFsInventorySettlement zxSaFsInventorySettlement) {
        return zxSaFsInventorySettlementService.getZxSaFsInventoryAndInventoryDetail(zxSaFsInventorySettlement);
    }

    @ApiOperation(value="查询附属类结算清单", notes="查询附属类结算清单")
    @ApiImplicitParam(name = "zxSaFsSettlement", value = "附属类结算entity", dataType = "ZxSaFsSettlement")
    @RequireToken
    @PostMapping("/getZxSaFsInventory")
    public ResponseEntity getZxSaFsInventory(@RequestBody(required = false) ZxSaFsSettlement zxSaFsSettlement) {
        return zxSaFsInventorySettlementService.getZxSaFsInventory(zxSaFsSettlement);
    }
}
