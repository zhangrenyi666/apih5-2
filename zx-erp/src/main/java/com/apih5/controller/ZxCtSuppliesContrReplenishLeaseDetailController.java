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
import com.apih5.mybatis.pojo.ZxCtSuppliesContrReplenishLeaseDetail;
import com.apih5.service.ZxCtSuppliesContrReplenishLeaseDetailService;

@RestController
public class ZxCtSuppliesContrReplenishLeaseDetailController {

    @Autowired(required = true)
    private ZxCtSuppliesContrReplenishLeaseDetailService zxCtSuppliesContrReplenishLeaseDetailService;

    @ApiOperation(value="查询物资管理类合同补充协议明细(租赁)", notes="查询物资管理类合同补充协议明细(租赁)")
    @ApiImplicitParam(name = "zxCtSuppliesContrReplenishLeaseDetail", value = "物资管理类合同补充协议明细(租赁)entity", dataType = "ZxCtSuppliesContrReplenishLeaseDetail")
    @RequireToken
    @PostMapping("/getZxCtSuppliesContrReplenishLeaseDetailList")
    public ResponseEntity getZxCtSuppliesContrReplenishLeaseDetailList(@RequestBody(required = false) ZxCtSuppliesContrReplenishLeaseDetail zxCtSuppliesContrReplenishLeaseDetail) {
        return zxCtSuppliesContrReplenishLeaseDetailService.getZxCtSuppliesContrReplenishLeaseDetailListByCondition(zxCtSuppliesContrReplenishLeaseDetail);
    }

    @ApiOperation(value="查询详情物资管理类合同补充协议明细(租赁)", notes="查询详情物资管理类合同补充协议明细(租赁)")
    @ApiImplicitParam(name = "zxCtSuppliesContrReplenishLeaseDetail", value = "物资管理类合同补充协议明细(租赁)entity", dataType = "ZxCtSuppliesContrReplenishLeaseDetail")
    @RequireToken
    @PostMapping("/getZxCtSuppliesContrReplenishLeaseDetailDetail")
    public ResponseEntity getZxCtSuppliesContrReplenishLeaseDetailDetail(@RequestBody(required = false) ZxCtSuppliesContrReplenishLeaseDetail zxCtSuppliesContrReplenishLeaseDetail) {
        return zxCtSuppliesContrReplenishLeaseDetailService.getZxCtSuppliesContrReplenishLeaseDetailDetail(zxCtSuppliesContrReplenishLeaseDetail);
    }

    @ApiOperation(value="新增物资管理类合同补充协议明细(租赁)", notes="新增物资管理类合同补充协议明细(租赁)")
    @ApiImplicitParam(name = "zxCtSuppliesContrReplenishLeaseDetail", value = "物资管理类合同补充协议明细(租赁)entity", dataType = "ZxCtSuppliesContrReplenishLeaseDetail")
    @RequireToken
    @PostMapping("/addZxCtSuppliesContrReplenishLeaseDetail")
    public ResponseEntity addZxCtSuppliesContrReplenishLeaseDetail(@RequestBody(required = false) ZxCtSuppliesContrReplenishLeaseDetail zxCtSuppliesContrReplenishLeaseDetail) {
        return zxCtSuppliesContrReplenishLeaseDetailService.saveZxCtSuppliesContrReplenishLeaseDetail(zxCtSuppliesContrReplenishLeaseDetail);
    }

    @ApiOperation(value="更新物资管理类合同补充协议明细(租赁)", notes="更新物资管理类合同补充协议明细(租赁)")
    @ApiImplicitParam(name = "zxCtSuppliesContrReplenishLeaseDetail", value = "物资管理类合同补充协议明细(租赁)entity", dataType = "ZxCtSuppliesContrReplenishLeaseDetail")
    @RequireToken
    @PostMapping("/updateZxCtSuppliesContrReplenishLeaseDetail")
    public ResponseEntity updateZxCtSuppliesContrReplenishLeaseDetail(@RequestBody(required = false) ZxCtSuppliesContrReplenishLeaseDetail zxCtSuppliesContrReplenishLeaseDetail) {
        return zxCtSuppliesContrReplenishLeaseDetailService.updateZxCtSuppliesContrReplenishLeaseDetail(zxCtSuppliesContrReplenishLeaseDetail);
    }

    @ApiOperation(value="删除物资管理类合同补充协议明细(租赁)", notes="删除物资管理类合同补充协议明细(租赁)")
    @ApiImplicitParam(name = "zxCtSuppliesContrReplenishLeaseDetailList", value = "物资管理类合同补充协议明细(租赁)List", dataType = "List<ZxCtSuppliesContrReplenishLeaseDetail>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxCtSuppliesContrReplenishLeaseDetail")
    public ResponseEntity batchDeleteUpdateZxCtSuppliesContrReplenishLeaseDetail(@RequestBody(required = false) List<ZxCtSuppliesContrReplenishLeaseDetail> zxCtSuppliesContrReplenishLeaseDetailList) {
        return zxCtSuppliesContrReplenishLeaseDetailService.batchDeleteUpdateZxCtSuppliesContrReplenishLeaseDetail(zxCtSuppliesContrReplenishLeaseDetailList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
