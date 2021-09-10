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
import com.apih5.mybatis.pojo.ZxCtSuppliesLeaseResSettlement;
import com.apih5.service.ZxCtSuppliesLeaseResSettlementService;

@RestController
public class ZxCtSuppliesLeaseResSettlementController {

    @Autowired(required = true)
    private ZxCtSuppliesLeaseResSettlementService zxCtSuppliesLeaseResSettlementService;

    @ApiOperation(value="查询物资租赁细目结算", notes="查询物资租赁细目结算")
    @ApiImplicitParam(name = "zxCtSuppliesLeaseResSettlement", value = "物资租赁细目结算entity", dataType = "ZxCtSuppliesLeaseResSettlement")
    @RequireToken
    @PostMapping("/getZxCtSuppliesLeaseResSettlementList")
    public ResponseEntity getZxCtSuppliesLeaseResSettlementList(@RequestBody(required = false) ZxCtSuppliesLeaseResSettlement zxCtSuppliesLeaseResSettlement) {
        return zxCtSuppliesLeaseResSettlementService.getZxCtSuppliesLeaseResSettlementListByCondition(zxCtSuppliesLeaseResSettlement);
    }

    @ApiOperation(value="查询详情物资租赁细目结算", notes="查询详情物资租赁细目结算")
    @ApiImplicitParam(name = "zxCtSuppliesLeaseResSettlement", value = "物资租赁细目结算entity", dataType = "ZxCtSuppliesLeaseResSettlement")
    @RequireToken
    @PostMapping("/getZxCtSuppliesLeaseResSettlementDetail")
    public ResponseEntity getZxCtSuppliesLeaseResSettlementDetail(@RequestBody(required = false) ZxCtSuppliesLeaseResSettlement zxCtSuppliesLeaseResSettlement) {
        return zxCtSuppliesLeaseResSettlementService.getZxCtSuppliesLeaseResSettlementDetail(zxCtSuppliesLeaseResSettlement);
    }

    @ApiOperation(value="新增物资租赁细目结算", notes="新增物资租赁细目结算")
    @ApiImplicitParam(name = "zxCtSuppliesLeaseResSettlement", value = "物资租赁细目结算entity", dataType = "ZxCtSuppliesLeaseResSettlement")
    @RequireToken
    @PostMapping("/addZxCtSuppliesLeaseResSettlement")
    public ResponseEntity addZxCtSuppliesLeaseResSettlement(@RequestBody(required = false) ZxCtSuppliesLeaseResSettlement zxCtSuppliesLeaseResSettlement) {
        return zxCtSuppliesLeaseResSettlementService.saveZxCtSuppliesLeaseResSettlement(zxCtSuppliesLeaseResSettlement);
    }

    @ApiOperation(value="更新物资租赁细目结算", notes="更新物资租赁细目结算")
    @ApiImplicitParam(name = "zxCtSuppliesLeaseResSettlement", value = "物资租赁细目结算entity", dataType = "ZxCtSuppliesLeaseResSettlement")
    @RequireToken
    @PostMapping("/updateZxCtSuppliesLeaseResSettlement")
    public ResponseEntity updateZxCtSuppliesLeaseResSettlement(@RequestBody(required = false) ZxCtSuppliesLeaseResSettlement zxCtSuppliesLeaseResSettlement) {
        return zxCtSuppliesLeaseResSettlementService.updateZxCtSuppliesLeaseResSettlement(zxCtSuppliesLeaseResSettlement);
    }

    @ApiOperation(value="删除物资租赁细目结算", notes="删除物资租赁细目结算")
    @ApiImplicitParam(name = "zxCtSuppliesLeaseResSettlementList", value = "物资租赁细目结算List", dataType = "List<ZxCtSuppliesLeaseResSettlement>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxCtSuppliesLeaseResSettlement")
    public ResponseEntity batchDeleteUpdateZxCtSuppliesLeaseResSettlement(@RequestBody(required = false) List<ZxCtSuppliesLeaseResSettlement> zxCtSuppliesLeaseResSettlementList) {
        return zxCtSuppliesLeaseResSettlementService.batchDeleteUpdateZxCtSuppliesLeaseResSettlement(zxCtSuppliesLeaseResSettlementList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
