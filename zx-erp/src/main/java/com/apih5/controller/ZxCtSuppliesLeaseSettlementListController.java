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
import com.apih5.mybatis.pojo.ZxCtSuppliesLeaseResSettleItem;
import com.apih5.mybatis.pojo.ZxCtSuppliesLeaseSettlementList;
import com.apih5.service.ZxCtSuppliesLeaseSettlementListService;

@RestController
public class ZxCtSuppliesLeaseSettlementListController {

    @Autowired(required = true)
    private ZxCtSuppliesLeaseSettlementListService zxCtSuppliesLeaseSettlementListService;

    @ApiOperation(value="查询物资租赁类结算单", notes="查询物资租赁类结算单")
    @ApiImplicitParam(name = "zxCtSuppliesLeaseSettlementList", value = "物资租赁类结算单entity", dataType = "ZxCtSuppliesLeaseSettlementList")
    @RequireToken
    @PostMapping("/getZxCtSuppliesLeaseSettlementListList")
    public ResponseEntity getZxCtSuppliesLeaseSettlementListList(@RequestBody(required = false) ZxCtSuppliesLeaseSettlementList zxCtSuppliesLeaseSettlementList) {
        return zxCtSuppliesLeaseSettlementListService.getZxCtSuppliesLeaseSettlementListListByCondition(zxCtSuppliesLeaseSettlementList);
    }

    @ApiOperation(value="查询详情物资租赁类结算单", notes="查询详情物资租赁类结算单")
    @ApiImplicitParam(name = "zxCtSuppliesLeaseSettlementList", value = "物资租赁类结算单entity", dataType = "ZxCtSuppliesLeaseSettlementList")
    @RequireToken
    @PostMapping("/getZxCtSuppliesLeaseSettlementListDetail")
    public ResponseEntity getZxCtSuppliesLeaseSettlementListDetail(@RequestBody(required = false) ZxCtSuppliesLeaseSettlementList zxCtSuppliesLeaseSettlementList) {
        return zxCtSuppliesLeaseSettlementListService.getZxCtSuppliesLeaseSettlementListDetail(zxCtSuppliesLeaseSettlementList);
    }

    @ApiOperation(value="新增物资租赁类结算单", notes="新增物资租赁类结算单")
    @ApiImplicitParam(name = "zxCtSuppliesLeaseSettlementList", value = "物资租赁类结算单entity", dataType = "ZxCtSuppliesLeaseSettlementList")
    @RequireToken
    @PostMapping("/addZxCtSuppliesLeaseSettlementList")
    public ResponseEntity addZxCtSuppliesLeaseSettlementList(@RequestBody(required = false) ZxCtSuppliesLeaseSettlementList zxCtSuppliesLeaseSettlementList) {
        return zxCtSuppliesLeaseSettlementListService.saveZxCtSuppliesLeaseSettlementList(zxCtSuppliesLeaseSettlementList);
    }

    @ApiOperation(value="更新物资租赁类结算单", notes="更新物资租赁类结算单")
    @ApiImplicitParam(name = "zxCtSuppliesLeaseSettlementList", value = "物资租赁类结算单entity", dataType = "ZxCtSuppliesLeaseSettlementList")
    @RequireToken
    @PostMapping("/updateZxCtSuppliesLeaseSettlementList")
    public ResponseEntity updateZxCtSuppliesLeaseSettlementList(@RequestBody(required = false) ZxCtSuppliesLeaseSettlementList zxCtSuppliesLeaseSettlementList) {
        return zxCtSuppliesLeaseSettlementListService.updateZxCtSuppliesLeaseSettlementList(zxCtSuppliesLeaseSettlementList);
    }

    @ApiOperation(value="删除物资租赁类结算单", notes="删除物资租赁类结算单")
    @ApiImplicitParam(name = "zxCtSuppliesLeaseSettlementListList", value = "物资租赁类结算单List", dataType = "List<ZxCtSuppliesLeaseSettlementList>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxCtSuppliesLeaseSettlementList")
    public ResponseEntity batchDeleteUpdateZxCtSuppliesLeaseSettlementList(@RequestBody(required = false) List<ZxCtSuppliesLeaseSettlementList> zxCtSuppliesLeaseSettlementListList) {
        return zxCtSuppliesLeaseSettlementListService.batchDeleteUpdateZxCtSuppliesLeaseSettlementList(zxCtSuppliesLeaseSettlementListList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

    @ApiOperation(value="查询物资租赁类结算单", notes="查询物资租赁类结算单")
    @ApiImplicitParam(name = "zxCtSuppliesLeaseSettlementList", value = "物资租赁类结算单entity", dataType = "ZxCtSuppliesLeaseSettlementList")
    @RequireToken
    @PostMapping("/getZxCtSuppliesLeaseSettlementListListByOrgId")
    public ResponseEntity getZxCtSuppliesLeaseSettlementListListByOrgId(@RequestBody(required = false) ZxCtSuppliesLeaseSettlementList zxCtSuppliesLeaseSettlementList) {
        return zxCtSuppliesLeaseSettlementListService.getZxCtSuppliesLeaseSettlementListListByOrgId(zxCtSuppliesLeaseSettlementList);
    }

    @ApiOperation(value="新增物资租赁类结算单", notes="新增物资租赁类结算单")
    @ApiImplicitParam(name = "zxCtSuppliesLeaseSettlementList", value = "物资租赁类结算单entity", dataType = "ZxCtSuppliesLeaseSettlementList")
    @RequireToken
    @PostMapping("/addZxCtSuppliesLeaseSettlementListByOrgId")
    public ResponseEntity addZxCtSuppliesLeaseSettlementListByOrgId(@RequestBody(required = false) ZxCtSuppliesLeaseSettlementList zxCtSuppliesLeaseSettlementList) {
        return zxCtSuppliesLeaseSettlementListService.saveZxCtSuppliesLeaseSettlementListByOrgId(zxCtSuppliesLeaseSettlementList);
    }

    @ApiOperation(value="更新物资租赁类结算单", notes="更新物资租赁类结算单")
    @ApiImplicitParam(name = "zxCtSuppliesLeaseSettlementList", value = "物资租赁类结算单entity", dataType = "ZxCtSuppliesLeaseSettlementList")
    @RequireToken
    @PostMapping("/updateZxCtSuppliesLeaseSettlementListByOrgId")
    public ResponseEntity updateZxCtSuppliesLeaseSettlementListByOrgId(@RequestBody(required = false) ZxCtSuppliesLeaseSettlementList zxCtSuppliesLeaseSettlementList) {
        return zxCtSuppliesLeaseSettlementListService.updateZxCtSuppliesLeaseSettlementListByOrgId(zxCtSuppliesLeaseSettlementList);
    }
    
    @ApiOperation(value="获取物资租赁类结算单流程详情", notes="获取物资租赁类结算单流程详情")
    @ApiImplicitParam(name = "zxCtSuppliesLeaseSettlementList", value = "物资租赁类结算单entity", dataType = "ZxCtSuppliesLeaseSettlementList")
    @RequireToken
    @PostMapping("/getZxCtSuppliesLeaseSettlementListFlowDetail")
    public ResponseEntity getZxCtSuppliesLeaseSettlementListFlowDetail(@RequestBody(required = false) ZxCtSuppliesLeaseSettlementList zxCtSuppliesLeaseSettlementList) {
    	return zxCtSuppliesLeaseSettlementListService.getZxCtSuppliesLeaseSettlementListFlowDetail(zxCtSuppliesLeaseSettlementList);
    }
    
    @ApiOperation(value="新增物资租赁类结算单流程", notes="新增物资租赁类结算单流程")
    @ApiImplicitParam(name = "zxCtSuppliesLeaseSettlementList", value = "物资租赁类结算单entity", dataType = "ZxCtSuppliesLeaseSettlementList")
    @RequireToken
    @PostMapping("/addZxCtSuppliesLeaseSettlementListFlow")
    public ResponseEntity addZxCtSuppliesLeaseSettlementListFlow(@RequestBody(required = false) ZxCtSuppliesLeaseSettlementList zxCtSuppliesLeaseSettlementList) {
    	return zxCtSuppliesLeaseSettlementListService.saveZxCtSuppliesLeaseSettlementListFlow(zxCtSuppliesLeaseSettlementList);
    }
    
    @ApiOperation(value="更新物资租赁类结算单", notes="更新物资租赁类结算单")
    @ApiImplicitParam(name = "zxCtSuppliesLeaseSettlementList", value = "物资租赁类结算单entity", dataType = "ZxCtSuppliesLeaseSettlementList")
    @RequireToken
    @PostMapping("/updateZxCtSuppliesLeaseSettlementListFlow")
    public ResponseEntity updateZxCtSuppliesLeaseSettlementListFlow(@RequestBody(required = false) ZxCtSuppliesLeaseSettlementList zxCtSuppliesLeaseSettlementList) {
    	return zxCtSuppliesLeaseSettlementListService.updateZxCtSuppliesLeaseSettlementListFlow(zxCtSuppliesLeaseSettlementList);
    }
    
    @ApiOperation(value="物资租赁类营改增一览", notes="物资租赁类营改增一览")
    @ApiImplicitParam(name = "zxCtSuppliesLeaseSettlementList", value = "物资租赁类结算单entity", dataType = "ZxCtSuppliesLeaseSettlementList")
    @RequireToken
    @PostMapping("/getZxCtSuppliesLeaseCampChangeIncrease")
    public ResponseEntity getZxCtSuppliesLeaseCampChangeIncrease(@RequestBody(required = false) ZxCtSuppliesLeaseSettlementList zxCtSuppliesLeaseSettlementList) {
        return zxCtSuppliesLeaseSettlementListService.getZxCtSuppliesLeaseCampChangeIncrease(zxCtSuppliesLeaseSettlementList);
    }    
    
    @ApiOperation(value="物资租赁类营改增一览", notes="物资租赁类营改增一览")
    @ApiImplicitParam(name = "zxCtSuppliesLeaseSettlementList", value = "物资租赁类结算单entity", dataType = "ZxCtSuppliesLeaseSettlementList")
    @PostMapping("/getZxCtSuppliesLeaseSettlementReportList")
    public List<ZxCtSuppliesLeaseCampChangeIncrease> getZxCtSuppliesLeaseSettlementReportList(@RequestBody(required = false) ZxCtSuppliesLeaseSettlementList zxCtSuppliesLeaseSettlementList) {
    	zxCtSuppliesLeaseSettlementList.setFlag("0");
    	return zxCtSuppliesLeaseSettlementListService.getZxCtSuppliesLeaseSettlementReportList(zxCtSuppliesLeaseSettlementList);
    }    
}
