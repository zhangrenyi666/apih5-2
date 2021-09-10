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
import com.apih5.mybatis.pojo.ZxCtSuppliesContrReplenishShopDetail;
import com.apih5.service.ZxCtSuppliesContrReplenishShopDetailService;

@RestController
public class ZxCtSuppliesContrReplenishShopDetailController {

    @Autowired(required = true)
    private ZxCtSuppliesContrReplenishShopDetailService zxCtSuppliesContrReplenishShopDetailService;

    @ApiOperation(value="查询物资管理类合同补充协议明细(采购)", notes="查询物资管理类合同补充协议明细(采购)")
    @ApiImplicitParam(name = "zxCtSuppliesContrReplenishShopDetail", value = "物资管理类合同补充协议明细(采购)entity", dataType = "ZxCtSuppliesContrReplenishShopDetail")
    @RequireToken
    @PostMapping("/getZxCtSuppliesContrReplenishShopDetailList")
    public ResponseEntity getZxCtSuppliesContrReplenishShopDetailList(@RequestBody(required = false) ZxCtSuppliesContrReplenishShopDetail zxCtSuppliesContrReplenishShopDetail) {
        return zxCtSuppliesContrReplenishShopDetailService.getZxCtSuppliesContrReplenishShopDetailListByCondition(zxCtSuppliesContrReplenishShopDetail);
    }

    @ApiOperation(value="查询详情物资管理类合同补充协议明细(采购)", notes="查询详情物资管理类合同补充协议明细(采购)")
    @ApiImplicitParam(name = "zxCtSuppliesContrReplenishShopDetail", value = "物资管理类合同补充协议明细(采购)entity", dataType = "ZxCtSuppliesContrReplenishShopDetail")
    @RequireToken
    @PostMapping("/getZxCtSuppliesContrReplenishShopDetailDetail")
    public ResponseEntity getZxCtSuppliesContrReplenishShopDetailDetail(@RequestBody(required = false) ZxCtSuppliesContrReplenishShopDetail zxCtSuppliesContrReplenishShopDetail) {
        return zxCtSuppliesContrReplenishShopDetailService.getZxCtSuppliesContrReplenishShopDetailDetail(zxCtSuppliesContrReplenishShopDetail);
    }

    @ApiOperation(value="新增物资管理类合同补充协议明细(采购)", notes="新增物资管理类合同补充协议明细(采购)")
    @ApiImplicitParam(name = "zxCtSuppliesContrReplenishShopDetail", value = "物资管理类合同补充协议明细(采购)entity", dataType = "ZxCtSuppliesContrReplenishShopDetail")
    @RequireToken
    @PostMapping("/addZxCtSuppliesContrReplenishShopDetail")
    public ResponseEntity addZxCtSuppliesContrReplenishShopDetail(@RequestBody(required = false) ZxCtSuppliesContrReplenishShopDetail zxCtSuppliesContrReplenishShopDetail) {
        return zxCtSuppliesContrReplenishShopDetailService.saveZxCtSuppliesContrReplenishShopDetail(zxCtSuppliesContrReplenishShopDetail);
    }

    @ApiOperation(value="更新物资管理类合同补充协议明细(采购)", notes="更新物资管理类合同补充协议明细(采购)")
    @ApiImplicitParam(name = "zxCtSuppliesContrReplenishShopDetail", value = "物资管理类合同补充协议明细(采购)entity", dataType = "ZxCtSuppliesContrReplenishShopDetail")
    @RequireToken
    @PostMapping("/updateZxCtSuppliesContrReplenishShopDetail")
    public ResponseEntity updateZxCtSuppliesContrReplenishShopDetail(@RequestBody(required = false) ZxCtSuppliesContrReplenishShopDetail zxCtSuppliesContrReplenishShopDetail) {
        return zxCtSuppliesContrReplenishShopDetailService.updateZxCtSuppliesContrReplenishShopDetail(zxCtSuppliesContrReplenishShopDetail);
    }

    @ApiOperation(value="删除物资管理类合同补充协议明细(采购)", notes="删除物资管理类合同补充协议明细(采购)")
    @ApiImplicitParam(name = "zxCtSuppliesContrReplenishShopDetailList", value = "物资管理类合同补充协议明细(采购)List", dataType = "List<ZxCtSuppliesContrReplenishShopDetail>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxCtSuppliesContrReplenishShopDetail")
    public ResponseEntity batchDeleteUpdateZxCtSuppliesContrReplenishShopDetail(@RequestBody(required = false) List<ZxCtSuppliesContrReplenishShopDetail> zxCtSuppliesContrReplenishShopDetailList) {
        return zxCtSuppliesContrReplenishShopDetailService.batchDeleteUpdateZxCtSuppliesContrReplenishShopDetail(zxCtSuppliesContrReplenishShopDetailList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
