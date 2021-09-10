package com.apih5.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.apih5.framework.annotation.RequireToken;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxCtSuppliesShopPaySettlement;
import com.apih5.mybatis.pojo.ZxSaProjectSet;
import com.apih5.service.ZxCtSuppliesShopPaySettlementService;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

@RestController
public class ZxCtSuppliesShopPaySettlementController {

    @Autowired(required = true)
    private ZxCtSuppliesShopPaySettlementService zxCtSuppliesShopPaySettlementService;
    
    @ApiOperation(value="查询物资支付项结算", notes="查询物资支付项结算")
    @ApiImplicitParam(name = "zxCtSuppliesShopPaySettlement", value = "物资支付项结算entity", dataType = "ZxCtSuppliesShopPaySettlement")
    @RequireToken
    @PostMapping("/getZxCtSuppliesShopPaySettlementList")
    public ResponseEntity getZxCtSuppliesShopPaySettlementList(@RequestBody(required = false) ZxCtSuppliesShopPaySettlement zxCtSuppliesShopPaySettlement) {
        return zxCtSuppliesShopPaySettlementService.getZxCtSuppliesShopPaySettlementListByCondition(zxCtSuppliesShopPaySettlement);
    }

    @ApiOperation(value="查询详情物资支付项结算", notes="查询详情物资支付项结算")
    @ApiImplicitParam(name = "zxCtSuppliesShopPaySettlement", value = "物资支付项结算entity", dataType = "ZxCtSuppliesShopPaySettlement")
    @RequireToken
    @PostMapping("/getZxCtSuppliesShopPaySettlementDetail")
    public ResponseEntity getZxCtSuppliesShopPaySettlementDetail(@RequestBody(required = false) ZxCtSuppliesShopPaySettlement zxCtSuppliesShopPaySettlement) {
        return zxCtSuppliesShopPaySettlementService.getZxCtSuppliesShopPaySettlementDetail(zxCtSuppliesShopPaySettlement);
    }

    @ApiOperation(value="新增物资支付项结算", notes="新增物资支付项结算")
    @ApiImplicitParam(name = "zxCtSuppliesShopPaySettlement", value = "物资支付项结算entity", dataType = "ZxCtSuppliesShopPaySettlement")
    @RequireToken
    @PostMapping("/addZxCtSuppliesShopPaySettlement")
    public ResponseEntity addZxCtSuppliesShopPaySettlement(@RequestBody(required = false) ZxCtSuppliesShopPaySettlement zxCtSuppliesShopPaySettlement) {
        return zxCtSuppliesShopPaySettlementService.saveZxCtSuppliesShopPaySettlement(zxCtSuppliesShopPaySettlement);
    }

    @ApiOperation(value="更新物资支付项结算", notes="更新物资支付项结算")
    @ApiImplicitParam(name = "zxCtSuppliesShopPaySettlement", value = "物资支付项结算entity", dataType = "ZxCtSuppliesShopPaySettlement")
    @RequireToken
    @PostMapping("/updateZxCtSuppliesShopPaySettlement")
    public ResponseEntity updateZxCtSuppliesShopPaySettlement(@RequestBody(required = false) ZxCtSuppliesShopPaySettlement zxCtSuppliesShopPaySettlement) {
        return zxCtSuppliesShopPaySettlementService.updateZxCtSuppliesShopPaySettlement(zxCtSuppliesShopPaySettlement);
    }

    @ApiOperation(value="删除物资支付项结算", notes="删除物资支付项结算")
    @ApiImplicitParam(name = "zxCtSuppliesShopPaySettlementList", value = "物资支付项结算List", dataType = "List<ZxCtSuppliesShopPaySettlement>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxCtSuppliesShopPaySettlement")
    public ResponseEntity batchDeleteUpdateZxCtSuppliesShopPaySettlement(@RequestBody(required = false) List<ZxCtSuppliesShopPaySettlement> zxCtSuppliesShopPaySettlementList) {
        return zxCtSuppliesShopPaySettlementService.batchDeleteUpdateZxCtSuppliesShopPaySettlement(zxCtSuppliesShopPaySettlementList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

    @ApiOperation(value="根据项目ID查询结算支付信息明细集合", notes="根据项目ID查询结算支付信息明细集合")
    @ApiImplicitParam(name = "zxSaProjectSet", value = "结算支付信息entity", dataType = "ZxSaProjectSet")
    @RequireToken
    @PostMapping("/getZxSaProjectSetListAllListByOrgId")
    public ResponseEntity getZxSaProjectSetListAllListByOrgId(@RequestBody(required = false) ZxSaProjectSet zxSaProjectSet) {
        return zxCtSuppliesShopPaySettlementService.getZxSaProjectSetListAllListByOrgId(zxSaProjectSet);
    }    
}
