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
import com.apih5.mybatis.pojo.ZxCtSuppliesLeaseCampChangeIncrease;
import com.apih5.mybatis.pojo.ZxCtSuppliesShopSettlementList;
import com.apih5.service.ZxCtSuppliesShopSettlementListService;

@RestController
public class ZxCtSuppliesShopSettlementListController {

    @Autowired(required = true)
    private ZxCtSuppliesShopSettlementListService zxCtSuppliesShopSettlementListService;

    @ApiOperation(value="查询物资结算单", notes="查询物资结算单")
    @ApiImplicitParam(name = "zxCtSuppliesShopSettlementList", value = "物资结算单entity", dataType = "ZxCtSuppliesShopSettlementList")
    @RequireToken
    @PostMapping("/getZxCtSuppliesShopSettlementListList")
    public ResponseEntity getZxCtSuppliesShopSettlementListList(@RequestBody(required = false) ZxCtSuppliesShopSettlementList zxCtSuppliesShopSettlementList) {
        return zxCtSuppliesShopSettlementListService.getZxCtSuppliesShopSettlementListListByCondition(zxCtSuppliesShopSettlementList);
    }

    @ApiOperation(value="查询详情物资结算单", notes="查询详情物资结算单")
    @ApiImplicitParam(name = "zxCtSuppliesShopSettlementList", value = "物资结算单entity", dataType = "ZxCtSuppliesShopSettlementList")
    @RequireToken
    @PostMapping("/getZxCtSuppliesShopSettlementListDetail")
    public ResponseEntity getZxCtSuppliesShopSettlementListDetail(@RequestBody(required = false) ZxCtSuppliesShopSettlementList zxCtSuppliesShopSettlementList) {
        return zxCtSuppliesShopSettlementListService.getZxCtSuppliesShopSettlementListDetail(zxCtSuppliesShopSettlementList);
    }

    @ApiOperation(value="新增物资结算单", notes="新增物资结算单")
    @ApiImplicitParam(name = "zxCtSuppliesShopSettlementList", value = "物资结算单entity", dataType = "ZxCtSuppliesShopSettlementList")
    @RequireToken
    @PostMapping("/addZxCtSuppliesShopSettlementList")
    public ResponseEntity addZxCtSuppliesShopSettlementList(@RequestBody(required = false) ZxCtSuppliesShopSettlementList zxCtSuppliesShopSettlementList) {
        return zxCtSuppliesShopSettlementListService.saveZxCtSuppliesShopSettlementList(zxCtSuppliesShopSettlementList);
    }

    @ApiOperation(value="更新物资结算单", notes="更新物资结算单")
    @ApiImplicitParam(name = "zxCtSuppliesShopSettlementList", value = "物资结算单entity", dataType = "ZxCtSuppliesShopSettlementList")
    @RequireToken
    @PostMapping("/updateZxCtSuppliesShopSettlementList")
    public ResponseEntity updateZxCtSuppliesShopSettlementList(@RequestBody(required = false) ZxCtSuppliesShopSettlementList zxCtSuppliesShopSettlementList) {
        return zxCtSuppliesShopSettlementListService.updateZxCtSuppliesShopSettlementList(zxCtSuppliesShopSettlementList);
    }

    @ApiOperation(value="删除物资结算单", notes="删除物资结算单")
    @ApiImplicitParam(name = "zxCtSuppliesShopSettlementListList", value = "物资结算单List", dataType = "List<ZxCtSuppliesShopSettlementList>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxCtSuppliesShopSettlementList")
    public ResponseEntity batchDeleteUpdateZxCtSuppliesShopSettlementList(@RequestBody(required = false) List<ZxCtSuppliesShopSettlementList> zxCtSuppliesShopSettlementListList) {
        return zxCtSuppliesShopSettlementListService.batchDeleteUpdateZxCtSuppliesShopSettlementList(zxCtSuppliesShopSettlementListList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

    @ApiOperation(value="查询物资结算单", notes="查询物资结算单")
    @ApiImplicitParam(name = "zxCtSuppliesShopSettlementList", value = "物资结算单entity", dataType = "ZxCtSuppliesShopSettlementList")
    @RequireToken
    @PostMapping("/getZxCtSuppliesShopSettlementListListByOrgId")
    public ResponseEntity getZxCtSuppliesShopSettlementListListByOrgId(@RequestBody(required = false) ZxCtSuppliesShopSettlementList zxCtSuppliesShopSettlementList) {
        return zxCtSuppliesShopSettlementListService.getZxCtSuppliesShopSettlementListListByOrgId(zxCtSuppliesShopSettlementList);
    }

    @ApiOperation(value="新增物资结算单", notes="新增物资结算单")
    @ApiImplicitParam(name = "zxCtSuppliesShopSettlementList", value = "物资结算单entity", dataType = "ZxCtSuppliesShopSettlementList")
    @RequireToken
    @PostMapping("/addZxCtSuppliesShopSettlementListByOrgId")
    public ResponseEntity addZxCtSuppliesShopSettlementListByProId(@RequestBody(required = false) ZxCtSuppliesShopSettlementList zxCtSuppliesShopSettlementList) {
        return zxCtSuppliesShopSettlementListService.addZxCtSuppliesShopSettlementListByOrgId(zxCtSuppliesShopSettlementList);
    }

    @ApiOperation(value="更新物资结算单", notes="更新物资结算单")
    @ApiImplicitParam(name = "zxCtSuppliesShopSettlementList", value = "物资结算单entity", dataType = "ZxCtSuppliesShopSettlementList")
    @RequireToken
    @PostMapping("/updateZxCtSuppliesShopSettlementListByOrgId")
    public ResponseEntity updateZxCtSuppliesShopSettlementListByOrgId(@RequestBody(required = false) ZxCtSuppliesShopSettlementList zxCtSuppliesShopSettlementList) {
        return zxCtSuppliesShopSettlementListService.updateZxCtSuppliesShopSettlementListByOrgId(zxCtSuppliesShopSettlementList);
    }    

    @ApiOperation(value="获取物资结算单流程详情", notes="获取物资结算单流程详情")
    @ApiImplicitParam(name = "zxCtSuppliesShopSettlementList", value = "物资结算单entity", dataType = "ZxCtSuppliesShopSettlementList")
    @RequireToken
    @PostMapping("/getZxCtSuppliesShopSettlementListFlowDetail")
    public ResponseEntity getZxCtSuppliesShopSettlementListFlowDetail(@RequestBody(required = false) ZxCtSuppliesShopSettlementList zxCtSuppliesShopSettlementList) {
        return zxCtSuppliesShopSettlementListService.getZxCtSuppliesShopSettlementListFlowDetail(zxCtSuppliesShopSettlementList);
    }

    @ApiOperation(value="新增物资结算单流程", notes="新增物资结算单流程")
    @ApiImplicitParam(name = "zxCtSuppliesShopSettlementList", value = "物资结算单entity", dataType = "ZxCtSuppliesShopSettlementList")
    @RequireToken
    @PostMapping("/addZxCtSuppliesShopSettlementListFlow")
    public ResponseEntity addZxCtSuppliesShopSettlementListFlow(@RequestBody(required = false) ZxCtSuppliesShopSettlementList zxCtSuppliesShopSettlementList) {
        return zxCtSuppliesShopSettlementListService.saveZxCtSuppliesShopSettlementListFlow(zxCtSuppliesShopSettlementList);
    }

    @ApiOperation(value="更新物资结算单", notes="更新物资结算单")
    @ApiImplicitParam(name = "zxCtSuppliesShopSettlementList", value = "物资结算单entity", dataType = "ZxCtSuppliesShopSettlementList")
    @RequireToken
    @PostMapping("/updateZxCtSuppliesShopSettlementListFlow")
    public ResponseEntity updateZxCtSuppliesShopSettlementListFlow(@RequestBody(required = false) ZxCtSuppliesShopSettlementList zxCtSuppliesShopSettlementList) {
        return zxCtSuppliesShopSettlementListService.updateZxCtSuppliesShopSettlementListFlow(zxCtSuppliesShopSettlementList);
    }       
    
    @ApiOperation(value="物资采购类营改增一览", notes="物资采购类营改增一览")
    @ApiImplicitParam(name = "zxCtSuppliesShopSettlementList", value = "物资结算单entity", dataType = "ZxCtSuppliesShopSettlementList")
    @RequireToken
    @PostMapping("/getZxCtSuppliesShopCampChangeIncrease")
    public ResponseEntity getZxCtSuppliesShopCampChangeIncrease(@RequestBody(required = false) ZxCtSuppliesShopSettlementList zxCtSuppliesShopSettlementList) {
    	return zxCtSuppliesShopSettlementListService.getZxCtSuppliesShopCampChangeIncreaseList(zxCtSuppliesShopSettlementList);
    }       
    
    @ApiOperation(value="物资采购类本期结算金额计算表（报表）", notes="物资采购类本期结算金额计算表（报表）")
    @ApiImplicitParam(name = "zxCtSuppliesShopSettlementList", value = "物资结算单entity", dataType = "ZxCtSuppliesShopSettlementList")
    @PostMapping("/getZxCtSuppliesShopSettlementReportList")
    public List<ZxCtSuppliesLeaseCampChangeIncrease> getZxCtSuppliesShopSettlementReportList(@RequestBody(required = false) ZxCtSuppliesShopSettlementList zxCtSuppliesShopSettlementList) {
    	return zxCtSuppliesShopSettlementListService.getZxCtSuppliesShopSettlementReportList(zxCtSuppliesShopSettlementList);
    }       
    
    @ApiOperation(value="物资采购类本期结算汇总", notes="物资采购类本期结算汇总")
    @ApiImplicitParam(name = "zxCtSuppliesShopSettlementList", value = "物资结算单entity", dataType = "ZxCtSuppliesShopSettlementList")
    @PostMapping("/getZxCtSuppliesShopSettlementSummaryReportList")
    public List<ZxCtSuppliesLeaseCampChangeIncrease> getZxCtSuppliesShopSettlementSummaryReportList(@RequestBody(required = false) ZxCtSuppliesShopSettlementList zxCtSuppliesShopSettlementList) {
    	zxCtSuppliesShopSettlementList.setFlag("0");
    	return zxCtSuppliesShopSettlementListService.getZxCtSuppliesShopSettlementSummaryReportList(zxCtSuppliesShopSettlementList);
    }       
}
