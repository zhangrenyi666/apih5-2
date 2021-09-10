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
import com.apih5.mybatis.pojo.ZxSfSWAccidentItem;
import com.apih5.service.ZxSfSWAccidentItemService;

@RestController
public class ZxSfSWAccidentItemController {

    @Autowired(required = true)
    private ZxSfSWAccidentItemService zxSfSWAccidentItemService;

    @ApiOperation(value="查询船舶水上交通事故明细", notes="查询船舶水上交通事故明细")
    @ApiImplicitParam(name = "zxSfSWAccidentItem", value = "船舶水上交通事故明细entity", dataType = "ZxSfSWAccidentItem")
    @RequireToken
    @PostMapping("/getZxSfSWAccidentItemList")
    public ResponseEntity getZxSfSWAccidentItemList(@RequestBody(required = false) ZxSfSWAccidentItem zxSfSWAccidentItem) {
        return zxSfSWAccidentItemService.getZxSfSWAccidentItemListByCondition(zxSfSWAccidentItem);
    }

    @ApiOperation(value="查询详情船舶水上交通事故明细", notes="查询详情船舶水上交通事故明细")
    @ApiImplicitParam(name = "zxSfSWAccidentItem", value = "船舶水上交通事故明细entity", dataType = "ZxSfSWAccidentItem")
    @RequireToken
    @PostMapping("/getZxSfSWAccidentItemDetail")
    public ResponseEntity getZxSfSWAccidentItemDetail(@RequestBody(required = false) ZxSfSWAccidentItem zxSfSWAccidentItem) {
        return zxSfSWAccidentItemService.getZxSfSWAccidentItemDetail(zxSfSWAccidentItem);
    }

    @ApiOperation(value="新增船舶水上交通事故明细", notes="新增船舶水上交通事故明细")
    @ApiImplicitParam(name = "zxSfSWAccidentItem", value = "船舶水上交通事故明细entity", dataType = "ZxSfSWAccidentItem")
    @RequireToken
    @PostMapping("/addZxSfSWAccidentItem")
    public ResponseEntity addZxSfSWAccidentItem(@RequestBody(required = false) ZxSfSWAccidentItem zxSfSWAccidentItem) {
        return zxSfSWAccidentItemService.saveZxSfSWAccidentItem(zxSfSWAccidentItem);
    }

    @ApiOperation(value="更新船舶水上交通事故明细", notes="更新船舶水上交通事故明细")
    @ApiImplicitParam(name = "zxSfSWAccidentItem", value = "船舶水上交通事故明细entity", dataType = "ZxSfSWAccidentItem")
    @RequireToken
    @PostMapping("/updateZxSfSWAccidentItem")
    public ResponseEntity updateZxSfSWAccidentItem(@RequestBody(required = false) ZxSfSWAccidentItem zxSfSWAccidentItem) {
        return zxSfSWAccidentItemService.updateZxSfSWAccidentItem(zxSfSWAccidentItem);
    }

    @ApiOperation(value="删除船舶水上交通事故明细", notes="删除船舶水上交通事故明细")
    @ApiImplicitParam(name = "zxSfSWAccidentItemList", value = "船舶水上交通事故明细List", dataType = "List<ZxSfSWAccidentItem>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxSfSWAccidentItem")
    public ResponseEntity batchDeleteUpdateZxSfSWAccidentItem(@RequestBody(required = false) List<ZxSfSWAccidentItem> zxSfSWAccidentItemList) {
        return zxSfSWAccidentItemService.batchDeleteUpdateZxSfSWAccidentItem(zxSfSWAccidentItemList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    @ApiOperation(value="查询船舶水上交通事故报表", notes="查询详情船舶水上交通事故报表")
    @ApiImplicitParam(name = "zxSfSWAccidentItem", value = "船舶水上交通事故明细entity", dataType = "ZxSfSWAccidentItem")
    @RequireToken
    @PostMapping("/getZxSfSWAccidentUReportFormList")
    public ResponseEntity getZxSfSWAccidentUReportFormList(@RequestBody(required = false) ZxSfSWAccidentItem zxSfSWAccidentItem) {
        return zxSfSWAccidentItemService.getUReportFormList(zxSfSWAccidentItem);
    }

    @ApiOperation(value="查询船舶水上交通事故报表（导出）", notes="查询船舶水上交通事故报表（导出）")
    @ApiImplicitParam(name = "zxSfSWAccidentItem", value = "船舶水上交通事故明细entity", dataType = "ZxSfSWAccidentItem")
    @PostMapping("/uReportZxSfSWAccidentForm")
    public List<ZxSfSWAccidentItem> uReportZxSfSWAccidentForm(@RequestBody(required = false) ZxSfSWAccidentItem zxSfSWAccidentItem) {
        return zxSfSWAccidentItemService.UReportForm(zxSfSWAccidentItem);
    }


    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    @ApiOperation(value="查询详情船舶水上交通事故报表", notes="查询详情船舶水上交通事故报表")
    @ApiImplicitParam(name = "zxSfSWAccidentItem", value = "船舶水上交通事故明细entity", dataType = "ZxSfSWAccidentItem")
    @RequireToken
    @PostMapping("/getZxSfSWAccidentUReportFormComList")
    public ResponseEntity getZxSfSWAccidentUReportFormComList(@RequestBody(required = false) ZxSfSWAccidentItem zxSfSWAccidentItem) {
        return zxSfSWAccidentItemService.getUReportFormComList(zxSfSWAccidentItem);
    }

    @ApiOperation(value="查询详情船舶水上交通事故报表", notes="查询详情船舶水上交通事故报表")
    @ApiImplicitParam(name = "zxSfSWAccidentItem", value = "船舶水上交通事故明细entity", dataType = "ZxSfSWAccidentItem")
    @PostMapping("/uReportZxSfSWAccidentFormCom")
    public List<ZxSfSWAccidentItem> uReportZxSfSWAccidentFormCom(@RequestBody(required = false) ZxSfSWAccidentItem zxSfSWAccidentItem) {
        return zxSfSWAccidentItemService.UReportFormCom(zxSfSWAccidentItem);
    }

}
