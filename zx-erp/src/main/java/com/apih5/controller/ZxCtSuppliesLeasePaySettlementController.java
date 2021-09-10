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
import com.apih5.mybatis.pojo.ZxCtSuppliesLeasePaySettlement;
import com.apih5.service.ZxCtSuppliesLeasePaySettlementService;

@RestController
public class ZxCtSuppliesLeasePaySettlementController {

    @Autowired(required = true)
    private ZxCtSuppliesLeasePaySettlementService zxCtSuppliesLeasePaySettlementService;

    @ApiOperation(value="查询物资租赁支付项结算", notes="查询物资租赁支付项结算")
    @ApiImplicitParam(name = "zxCtSuppliesLeasePaySettlement", value = "物资租赁支付项结算entity", dataType = "ZxCtSuppliesLeasePaySettlement")
    @RequireToken
    @PostMapping("/getZxCtSuppliesLeasePaySettlementList")
    public ResponseEntity getZxCtSuppliesLeasePaySettlementList(@RequestBody(required = false) ZxCtSuppliesLeasePaySettlement zxCtSuppliesLeasePaySettlement) {
        return zxCtSuppliesLeasePaySettlementService.getZxCtSuppliesLeasePaySettlementListByCondition(zxCtSuppliesLeasePaySettlement);
    }

    @ApiOperation(value="查询详情物资租赁支付项结算", notes="查询详情物资租赁支付项结算")
    @ApiImplicitParam(name = "zxCtSuppliesLeasePaySettlement", value = "物资租赁支付项结算entity", dataType = "ZxCtSuppliesLeasePaySettlement")
    @RequireToken
    @PostMapping("/getZxCtSuppliesLeasePaySettlementDetail")
    public ResponseEntity getZxCtSuppliesLeasePaySettlementDetail(@RequestBody(required = false) ZxCtSuppliesLeasePaySettlement zxCtSuppliesLeasePaySettlement) {
        return zxCtSuppliesLeasePaySettlementService.getZxCtSuppliesLeasePaySettlementDetail(zxCtSuppliesLeasePaySettlement);
    }

    @ApiOperation(value="新增物资租赁支付项结算", notes="新增物资租赁支付项结算")
    @ApiImplicitParam(name = "zxCtSuppliesLeasePaySettlement", value = "物资租赁支付项结算entity", dataType = "ZxCtSuppliesLeasePaySettlement")
    @RequireToken
    @PostMapping("/addZxCtSuppliesLeasePaySettlement")
    public ResponseEntity addZxCtSuppliesLeasePaySettlement(@RequestBody(required = false) ZxCtSuppliesLeasePaySettlement zxCtSuppliesLeasePaySettlement) {
        return zxCtSuppliesLeasePaySettlementService.saveZxCtSuppliesLeasePaySettlement(zxCtSuppliesLeasePaySettlement);
    }

    @ApiOperation(value="更新物资租赁支付项结算", notes="更新物资租赁支付项结算")
    @ApiImplicitParam(name = "zxCtSuppliesLeasePaySettlement", value = "物资租赁支付项结算entity", dataType = "ZxCtSuppliesLeasePaySettlement")
    @RequireToken
    @PostMapping("/updateZxCtSuppliesLeasePaySettlement")
    public ResponseEntity updateZxCtSuppliesLeasePaySettlement(@RequestBody(required = false) ZxCtSuppliesLeasePaySettlement zxCtSuppliesLeasePaySettlement) {
        return zxCtSuppliesLeasePaySettlementService.updateZxCtSuppliesLeasePaySettlement(zxCtSuppliesLeasePaySettlement);
    }

    @ApiOperation(value="删除物资租赁支付项结算", notes="删除物资租赁支付项结算")
    @ApiImplicitParam(name = "zxCtSuppliesLeasePaySettlementList", value = "物资租赁支付项结算List", dataType = "List<ZxCtSuppliesLeasePaySettlement>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxCtSuppliesLeasePaySettlement")
    public ResponseEntity batchDeleteUpdateZxCtSuppliesLeasePaySettlement(@RequestBody(required = false) List<ZxCtSuppliesLeasePaySettlement> zxCtSuppliesLeasePaySettlementList) {
        return zxCtSuppliesLeasePaySettlementService.batchDeleteUpdateZxCtSuppliesLeasePaySettlement(zxCtSuppliesLeasePaySettlementList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
