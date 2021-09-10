package com.apih5.controller;

import java.util.List;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import com.apih5.framework.annotation.RequireToken;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxSkInvoice;
import com.apih5.service.ZxSkInvoiceService;

@RestController
public class ZxSkInvoiceController {

    @Autowired(required = true)
    private ZxSkInvoiceService zxSkInvoiceService;

    @ApiOperation(value="查询冲账单", notes="查询冲账单")
    @ApiImplicitParam(name = "zxSkInvoice", value = "冲账单entity", dataType = "ZxSkInvoice")
    @RequireToken
    @PostMapping("/getZxSkInvoiceList")
    public ResponseEntity getZxSkInvoiceList(@RequestBody(required = false) ZxSkInvoice zxSkInvoice) {
        return zxSkInvoiceService.getZxSkInvoiceListByCondition(zxSkInvoice);
    }

    @ApiOperation(value="查询详情冲账单", notes="查询详情冲账单")
    @ApiImplicitParam(name = "zxSkInvoice", value = "冲账单entity", dataType = "ZxSkInvoice")
    @RequireToken
    @PostMapping("/getZxSkInvoiceDetail")
    public ResponseEntity getZxSkInvoiceDetail(@RequestBody(required = false) ZxSkInvoice zxSkInvoice) {
        return zxSkInvoiceService.getZxSkInvoiceDetail(zxSkInvoice);
    }

    @ApiOperation(value="新增冲账单", notes="新增冲账单")
    @ApiImplicitParam(name = "zxSkInvoice", value = "冲账单entity", dataType = "ZxSkInvoice")
    @RequireToken
    @PostMapping("/addZxSkInvoice")
    public ResponseEntity addZxSkInvoice(@RequestBody(required = false) ZxSkInvoice zxSkInvoice) {
        return zxSkInvoiceService.saveZxSkInvoice(zxSkInvoice);
    }

    @ApiOperation(value="更新冲账单", notes="更新冲账单")
    @ApiImplicitParam(name = "zxSkInvoice", value = "冲账单entity", dataType = "ZxSkInvoice")
    @RequireToken
    @PostMapping("/updateZxSkInvoice")
    public ResponseEntity updateZxSkInvoice(@RequestBody(required = false) ZxSkInvoice zxSkInvoice) {
        return zxSkInvoiceService.updateZxSkInvoice(zxSkInvoice);
    }

    @ApiOperation(value="删除冲账单", notes="删除冲账单")
    @ApiImplicitParam(name = "zxSkInvoiceList", value = "冲账单List", dataType = "List<ZxSkInvoice>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxSkInvoice")
    public ResponseEntity batchDeleteUpdateZxSkInvoice(@RequestBody(required = false) List<ZxSkInvoice> zxSkInvoiceList) {
        return zxSkInvoiceService.batchDeleteUpdateZxSkInvoice(zxSkInvoiceList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

    @ApiOperation(value="获取冲账编号", notes="获取冲账编号")
    @ApiImplicitParam(name = "zxSkInvoice", value = "冲账entity", dataType = "ZxSkInvoice")
    @RequireToken
    @PostMapping("/getZxSkInvoiceNo")
    public ResponseEntity getZxSkInvoiceNo(@RequestBody(required = false) ZxSkInvoice zxSkInvoice) {
        return zxSkInvoiceService.getZxSkInvoiceNo(zxSkInvoice);
    }

    @ApiOperation(value="获取预收的收料单", notes="获取预收的收料单")
    @ApiImplicitParam(name = "zxSkInvoice", value = "冲账entity", dataType = "ZxSkInvoice")
    @RequireToken
    @PostMapping("/getZxSkInvoiceReceivableOrder")
    public ResponseEntity getZxSkInvoiceReceivableOrder(@RequestBody(required = false) ZxSkInvoice zxSkInvoice) {
        return zxSkInvoiceService.getZxSkInvoiceReceivableOrder(zxSkInvoice);
    }

    @ApiOperation(value="审核冲账单", notes="审核冲账单")
    @ApiImplicitParam(name = "zxSkInvoice", value = "冲账entity", dataType = "ZxSkInvoice")
    @RequireToken
    @PostMapping("/checkZxSkInvoice")
    public ResponseEntity checkZxSkInvoice(@RequestBody(required = false) ZxSkInvoice zxSkInvoice) {
        return zxSkInvoiceService.checkZxSkInvoice(zxSkInvoice);
    }

    @ApiOperation(value="获取收料单供方", notes="获取收料单供方")
    @ApiImplicitParam(name = "zxSkInvoice", value = "冲账entity", dataType = "ZxSkInvoice")
    @RequireToken
    @PostMapping("/getZxSkInvoiceOutOrg")
    public ResponseEntity getZxSkInvoiceOutOrg(@RequestBody(required = false) ZxSkInvoice zxSkInvoice) {
        return zxSkInvoiceService.getZxSkInvoiceOutOrg(zxSkInvoice);
    }

    @ApiOperation(value="获取收料单物资", notes="获取收料单物资")
    @ApiImplicitParam(name = "zxSkInvoice", value = "冲账entity", dataType = "ZxSkInvoice")
    @RequireToken
    @PostMapping("/getZxSkInvoiceResource")
    public ResponseEntity getZxSkInvoiceResource(@RequestBody(required = false) ZxSkInvoice zxSkInvoice) {
        return zxSkInvoiceService.getZxSkInvoiceResource(zxSkInvoice);
    }





}
