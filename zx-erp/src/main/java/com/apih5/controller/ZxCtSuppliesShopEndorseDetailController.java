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
import com.apih5.mybatis.pojo.ZxCtSuppliesShopEndorseDetail;
import com.apih5.service.ZxCtSuppliesShopEndorseDetailService;

@RestController
public class ZxCtSuppliesShopEndorseDetailController {

    @Autowired(required = true)
    private ZxCtSuppliesShopEndorseDetailService zxCtSuppliesShopEndorseDetailService;

    @ApiOperation(value="查询物资签认单明细", notes="查询物资签认单明细")
    @ApiImplicitParam(name = "zxCtSuppliesShopEndorseDetail", value = "物资签认单明细entity", dataType = "ZxCtSuppliesShopEndorseDetail")
    @RequireToken
    @PostMapping("/getZxCtSuppliesShopEndorseDetailList")
    public ResponseEntity getZxCtSuppliesShopEndorseDetailList(@RequestBody(required = false) ZxCtSuppliesShopEndorseDetail zxCtSuppliesShopEndorseDetail) {
        return zxCtSuppliesShopEndorseDetailService.getZxCtSuppliesShopEndorseDetailListByCondition(zxCtSuppliesShopEndorseDetail);
    }

    @ApiOperation(value="查询详情物资签认单明细", notes="查询详情物资签认单明细")
    @ApiImplicitParam(name = "zxCtSuppliesShopEndorseDetail", value = "物资签认单明细entity", dataType = "ZxCtSuppliesShopEndorseDetail")
    @RequireToken
    @PostMapping("/getZxCtSuppliesShopEndorseDetailDetail")
    public ResponseEntity getZxCtSuppliesShopEndorseDetailDetail(@RequestBody(required = false) ZxCtSuppliesShopEndorseDetail zxCtSuppliesShopEndorseDetail) {
        return zxCtSuppliesShopEndorseDetailService.getZxCtSuppliesShopEndorseDetailDetail(zxCtSuppliesShopEndorseDetail);
    }

    @ApiOperation(value="新增物资签认单明细", notes="新增物资签认单明细")
    @ApiImplicitParam(name = "zxCtSuppliesShopEndorseDetail", value = "物资签认单明细entity", dataType = "ZxCtSuppliesShopEndorseDetail")
    @RequireToken
    @PostMapping("/addZxCtSuppliesShopEndorseDetail")
    public ResponseEntity addZxCtSuppliesShopEndorseDetail(@RequestBody(required = false) ZxCtSuppliesShopEndorseDetail zxCtSuppliesShopEndorseDetail) {
        return zxCtSuppliesShopEndorseDetailService.saveZxCtSuppliesShopEndorseDetail(zxCtSuppliesShopEndorseDetail);
    }

    @ApiOperation(value="更新物资签认单明细", notes="更新物资签认单明细")
    @ApiImplicitParam(name = "zxCtSuppliesShopEndorseDetail", value = "物资签认单明细entity", dataType = "ZxCtSuppliesShopEndorseDetail")
    @RequireToken
    @PostMapping("/updateZxCtSuppliesShopEndorseDetail")
    public ResponseEntity updateZxCtSuppliesShopEndorseDetail(@RequestBody(required = false) ZxCtSuppliesShopEndorseDetail zxCtSuppliesShopEndorseDetail) {
        return zxCtSuppliesShopEndorseDetailService.updateZxCtSuppliesShopEndorseDetail(zxCtSuppliesShopEndorseDetail);
    }

    @ApiOperation(value="删除物资签认单明细", notes="删除物资签认单明细")
    @ApiImplicitParam(name = "zxCtSuppliesShopEndorseDetailList", value = "物资签认单明细List", dataType = "List<ZxCtSuppliesShopEndorseDetail>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxCtSuppliesShopEndorseDetail")
    public ResponseEntity batchDeleteUpdateZxCtSuppliesShopEndorseDetail(@RequestBody(required = false) List<ZxCtSuppliesShopEndorseDetail> zxCtSuppliesShopEndorseDetailList) {
        return zxCtSuppliesShopEndorseDetailService.batchDeleteUpdateZxCtSuppliesShopEndorseDetail(zxCtSuppliesShopEndorseDetailList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
