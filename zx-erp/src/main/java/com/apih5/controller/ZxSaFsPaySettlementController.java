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
import com.apih5.mybatis.pojo.ZxSaFsPaySettlement;
import com.apih5.service.ZxSaFsPaySettlementService;

@RestController
public class ZxSaFsPaySettlementController {

    @Autowired(required = true)
    private ZxSaFsPaySettlementService zxSaFsPaySettlementService;

    @ApiOperation(value="查询附属类支付项结算", notes="查询附属类支付项结算")
    @ApiImplicitParam(name = "zxSaFsPaySettlement", value = "附属类支付项结算entity", dataType = "ZxSaFsPaySettlement")
    @RequireToken
    @PostMapping("/getZxSaFsPaySettlementList")
    public ResponseEntity getZxSaFsPaySettlementList(@RequestBody(required = false) ZxSaFsPaySettlement zxSaFsPaySettlement) {
        return zxSaFsPaySettlementService.getZxSaFsPaySettlementListByCondition(zxSaFsPaySettlement);
    }

    @ApiOperation(value="查询详情附属类支付项结算", notes="查询详情附属类支付项结算")
    @ApiImplicitParam(name = "zxSaFsPaySettlement", value = "附属类支付项结算entity", dataType = "ZxSaFsPaySettlement")
    @RequireToken
    @PostMapping("/getZxSaFsPaySettlementDetail")
    public ResponseEntity getZxSaFsPaySettlementDetail(@RequestBody(required = false) ZxSaFsPaySettlement zxSaFsPaySettlement) {
        return zxSaFsPaySettlementService.getZxSaFsPaySettlementDetail(zxSaFsPaySettlement);
    }

    @ApiOperation(value="新增附属类支付项结算", notes="新增附属类支付项结算")
    @ApiImplicitParam(name = "zxSaFsPaySettlement", value = "附属类支付项结算entity", dataType = "ZxSaFsPaySettlement")
    @RequireToken
    @PostMapping("/addZxSaFsPaySettlement")
    public ResponseEntity addZxSaFsPaySettlement(@RequestBody(required = false) ZxSaFsPaySettlement zxSaFsPaySettlement) {
        return zxSaFsPaySettlementService.saveZxSaFsPaySettlement(zxSaFsPaySettlement);
    }

    @ApiOperation(value="更新附属类支付项结算", notes="更新附属类支付项结算")
    @ApiImplicitParam(name = "zxSaFsPaySettlement", value = "附属类支付项结算entity", dataType = "ZxSaFsPaySettlement")
    @RequireToken
    @PostMapping("/updateZxSaFsPaySettlement")
    public ResponseEntity updateZxSaFsPaySettlement(@RequestBody(required = false) ZxSaFsPaySettlement zxSaFsPaySettlement) {
        return zxSaFsPaySettlementService.updateZxSaFsPaySettlement(zxSaFsPaySettlement);
    }

    @ApiOperation(value="删除附属类支付项结算", notes="删除附属类支付项结算")
    @ApiImplicitParam(name = "zxSaFsPaySettlementList", value = "附属类支付项结算List", dataType = "List<ZxSaFsPaySettlement>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxSaFsPaySettlement")
    public ResponseEntity batchDeleteUpdateZxSaFsPaySettlement(@RequestBody(required = false) List<ZxSaFsPaySettlement> zxSaFsPaySettlementList) {
        return zxSaFsPaySettlementService.batchDeleteUpdateZxSaFsPaySettlement(zxSaFsPaySettlementList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

    /**
     * 查询附属类支付项的主从表数据
     * @author suncg
     * @param zxSaFsSettlement
     *
     * */
    @ApiOperation(value="查询附属类支付项结算和详情", notes="查询附属类支付项结算和详情")
    @ApiImplicitParam(name = "zxSaFsSettlement", value = "附属类结算entity", dataType = "zxSaFsSettlement")
    @RequireToken
    @PostMapping("/getZxSaFsPaySettlementAndDetail")
    public ResponseEntity getZxSaFsPaySettlementAndDetail(@RequestBody(required = false) ZxSaFsSettlement zxSaFsSettlement) {
        return zxSaFsPaySettlementService.getZxSaFsPaySettlementAndPaySettlementDetail(zxSaFsSettlement);
    }

    /**
     * 查询附属类支付项
     * @author suncg
     * @param zxSaFsSettlement
     *
     * */
    @ApiOperation(value="查询附属类支付项", notes="查询附属类支付项")
    @ApiImplicitParam(name = "zxSaFsSettlement", value = "附属类结算entity", dataType = "zxSaFsSettlement")
    @RequireToken
    @PostMapping("/getZxSaFsPaySettlement")
    public ResponseEntity getZxSaFsPaySettlement(@RequestBody(required = false) ZxSaFsSettlement zxSaFsSettlement) {
        return zxSaFsPaySettlementService.getZxSaFsPaySettlement(zxSaFsSettlement);
    }
}
