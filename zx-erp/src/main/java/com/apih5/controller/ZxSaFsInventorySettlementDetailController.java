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
import com.apih5.mybatis.pojo.ZxSaFsInventorySettlementDetail;
import com.apih5.service.ZxSaFsInventorySettlementDetailService;

@RestController
public class ZxSaFsInventorySettlementDetailController {

    @Autowired(required = true)
    private ZxSaFsInventorySettlementDetailService zxSaFsInventorySettlementDetailService;

    @ApiOperation(value="查询附属类清单结算明细", notes="查询附属类清单结算明细")
    @ApiImplicitParam(name = "zxSaFsInventorySettlementDetail", value = "附属类清单结算明细entity", dataType = "ZxSaFsInventorySettlementDetail")
    @RequireToken
    @PostMapping("/getZxSaFsInventorySettlementDetailList")
    public ResponseEntity getZxSaFsInventorySettlementDetailList(@RequestBody(required = false) ZxSaFsInventorySettlementDetail zxSaFsInventorySettlementDetail) {
        return zxSaFsInventorySettlementDetailService.getZxSaFsInventorySettlementDetailListByCondition(zxSaFsInventorySettlementDetail);
    }

    @ApiOperation(value="查询详情附属类清单结算明细", notes="查询详情附属类清单结算明细")
    @ApiImplicitParam(name = "zxSaFsInventorySettlementDetail", value = "附属类清单结算明细entity", dataType = "ZxSaFsInventorySettlementDetail")
    @RequireToken
    @PostMapping("/getZxSaFsInventorySettlementDetailDetail")
    public ResponseEntity getZxSaFsInventorySettlementDetailDetail(@RequestBody(required = false) ZxSaFsInventorySettlementDetail zxSaFsInventorySettlementDetail) {
        return zxSaFsInventorySettlementDetailService.getZxSaFsInventorySettlementDetailDetail(zxSaFsInventorySettlementDetail);
    }

    @ApiOperation(value="新增附属类清单结算明细", notes="新增附属类清单结算明细")
    @ApiImplicitParam(name = "zxSaFsInventorySettlementDetail", value = "附属类清单结算明细entity", dataType = "ZxSaFsInventorySettlementDetail")
    @RequireToken
    @PostMapping("/addZxSaFsInventorySettlementDetail")
    public ResponseEntity addZxSaFsInventorySettlementDetail(@RequestBody(required = false) ZxSaFsInventorySettlementDetail zxSaFsInventorySettlementDetail) {
        return zxSaFsInventorySettlementDetailService.saveZxSaFsInventorySettlementDetail(zxSaFsInventorySettlementDetail);
    }

    @ApiOperation(value="更新附属类清单结算明细", notes="更新附属类清单结算明细")
    @ApiImplicitParam(name = "zxSaFsInventorySettlementDetail", value = "附属类清单结算明细entity", dataType = "ZxSaFsInventorySettlementDetail")
    @RequireToken
    @PostMapping("/updateZxSaFsInventorySettlementDetail")
    public ResponseEntity updateZxSaFsInventorySettlementDetail(@RequestBody(required = false) ZxSaFsInventorySettlementDetail zxSaFsInventorySettlementDetail) throws Exception {
        return zxSaFsInventorySettlementDetailService.updateZxSaFsInventorySettlementDetail(zxSaFsInventorySettlementDetail);
    }

    @ApiOperation(value="删除附属类清单结算明细", notes="删除附属类清单结算明细")
    @ApiImplicitParam(name = "zxSaFsInventorySettlementDetailList", value = "附属类清单结算明细List", dataType = "List<ZxSaFsInventorySettlementDetail>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxSaFsInventorySettlementDetail")
    public ResponseEntity batchDeleteUpdateZxSaFsInventorySettlementDetail(@RequestBody(required = false) List<ZxSaFsInventorySettlementDetail> zxSaFsInventorySettlementDetailList) {
        return zxSaFsInventorySettlementDetailService.batchDeleteUpdateZxSaFsInventorySettlementDetail(zxSaFsInventorySettlementDetailList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    @ApiOperation(value="查询营改增一览数据", notes="查询营改增一览数据")
    @ApiImplicitParam(name = "zxSaFsInventorySettlementDetail", value = "附属类清单结算明细entity", dataType = "ZxSaFsInventorySettlementDetail")
    @RequireToken
    @PostMapping("/getYgzYiLan")
    public ResponseEntity getYgzYiLan(@RequestBody(required = false) ZxSaFsInventorySettlementDetail zxSaFsInventorySettlementDetail){
        return zxSaFsInventorySettlementDetailService.getYgzYiLan(zxSaFsInventorySettlementDetail);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    @ApiOperation(value="导出营改增一览数据", notes="导出营改增一览数据")
    @ApiImplicitParam(name = "zxSaFsInventorySettlementDetail", value = "附属类清单结算明细entity", dataType = "ZxSaFsInventorySettlementDetail")
    @PostMapping("/exportYgzYiLan")
    public List<ZxSaFsInventorySettlementDetail> exportYgzYiLan(@RequestBody(required = false) ZxSaFsInventorySettlementDetail zxSaFsInventorySettlementDetail){
        return zxSaFsInventorySettlementDetailService.exportYgzYiLan(zxSaFsInventorySettlementDetail);
    }
}
