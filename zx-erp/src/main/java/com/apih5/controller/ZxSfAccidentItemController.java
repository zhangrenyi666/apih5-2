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
import com.apih5.mybatis.pojo.ZxSfAccidentItem;
import com.apih5.service.ZxSfAccidentItemService;

@RestController
public class ZxSfAccidentItemController {

    @Autowired(required = true)
    private ZxSfAccidentItemService zxSfAccidentItemService;

    @ApiOperation(value="查询伤亡事故情况统计分析表明细", notes="查询伤亡事故情况统计分析表明细")
    @ApiImplicitParam(name = "zxSfAccidentItem", value = "伤亡事故情况统计分析表明细entity", dataType = "ZxSfAccidentItem")
    @RequireToken
    @PostMapping("/getZxSfAccidentItemList")
    public ResponseEntity getZxSfAccidentItemList(@RequestBody(required = false) ZxSfAccidentItem zxSfAccidentItem) {
        return zxSfAccidentItemService.getZxSfAccidentItemListByCondition(zxSfAccidentItem);
    }

    @ApiOperation(value="查询详情伤亡事故情况统计分析表明细", notes="查询详情伤亡事故情况统计分析表明细")
    @ApiImplicitParam(name = "zxSfAccidentItem", value = "伤亡事故情况统计分析表明细entity", dataType = "ZxSfAccidentItem")
    @RequireToken
    @PostMapping("/getZxSfAccidentItemDetail")
    public ResponseEntity getZxSfAccidentItemDetail(@RequestBody(required = false) ZxSfAccidentItem zxSfAccidentItem) {
        return zxSfAccidentItemService.getZxSfAccidentItemDetail(zxSfAccidentItem);
    }

    @ApiOperation(value="新增伤亡事故情况统计分析表明细", notes="新增伤亡事故情况统计分析表明细")
    @ApiImplicitParam(name = "zxSfAccidentItem", value = "伤亡事故情况统计分析表明细entity", dataType = "ZxSfAccidentItem")
    @RequireToken
    @PostMapping("/addZxSfAccidentItem")
    public ResponseEntity addZxSfAccidentItem(@RequestBody(required = false) ZxSfAccidentItem zxSfAccidentItem) {
        return zxSfAccidentItemService.saveZxSfAccidentItem(zxSfAccidentItem);
    }

    @ApiOperation(value="更新伤亡事故情况统计分析表明细", notes="更新伤亡事故情况统计分析表明细")
    @ApiImplicitParam(name = "zxSfAccidentItem", value = "伤亡事故情况统计分析表明细entity", dataType = "ZxSfAccidentItem")
    @RequireToken
    @PostMapping("/updateZxSfAccidentItem")
    public ResponseEntity updateZxSfAccidentItem(@RequestBody(required = false) ZxSfAccidentItem zxSfAccidentItem) {
        return zxSfAccidentItemService.updateZxSfAccidentItem(zxSfAccidentItem);
    }

    @ApiOperation(value="删除伤亡事故情况统计分析表明细", notes="删除伤亡事故情况统计分析表明细")
    @ApiImplicitParam(name = "zxSfAccidentItemList", value = "伤亡事故情况统计分析表明细List", dataType = "List<ZxSfAccidentItem>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxSfAccidentItem")
    public ResponseEntity batchDeleteUpdateZxSfAccidentItem(@RequestBody(required = false) List<ZxSfAccidentItem> zxSfAccidentItemList) {
        return zxSfAccidentItemService.batchDeleteUpdateZxSfAccidentItem(zxSfAccidentItemList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

    @ApiOperation(value="查询详情伤亡事故情况统计分析表报表", notes="查询详情伤亡事故情况统计分析表报表")
    @ApiImplicitParam(name = "zxSfAccidentItem", value = "伤亡事故情况统计分析表明细entity", dataType = "ZxSfAccidentItem")
    @RequireToken
    @PostMapping("/getZxSfAccidentItemFormList")
    public ResponseEntity getZxSfAccidentItemFormList(@RequestBody(required = false) ZxSfAccidentItem zxSfAccidentItem) {
        return zxSfAccidentItemService.getUReportFormList(zxSfAccidentItem);
    }

    @ApiOperation(value="导出伤亡事故情况统计分析表报表", notes="查询详情伤亡事故情况统计分析表报表")
    @ApiImplicitParam(name = "zxSfAccidentItem", value = "伤亡事故情况统计分析表明细entity", dataType = "ZxSfAccidentItem")
    @PostMapping("/uReportZxSfAccidentItemForm")
    public List<ZxSfAccidentItem> uReportZxSfAccidentItemForm(@RequestBody(required = false) ZxSfAccidentItem zxSfAccidentItem) {
        return zxSfAccidentItemService.uReportForm(zxSfAccidentItem);
    }

    @ApiOperation(value="查询详情伤亡事故情况统计分析表报表(公司)", notes="导出伤亡事故情况统计分析表报表(公司)")
    @ApiImplicitParam(name = "zxSfAccidentItem", value = "伤亡事故情况统计分析表明细entity", dataType = "ZxSfAccidentItem")
    @RequireToken
    @PostMapping("/getZxSfAccidentItemFormComList")
    public ResponseEntity getZxSfAccidentItemFormComList(@RequestBody(required = false) ZxSfAccidentItem zxSfAccidentItem) {
        return zxSfAccidentItemService.getUReportFormComList(zxSfAccidentItem);
    }

    @ApiOperation(value="导出伤亡事故情况统计分析表报表(公司)", notes="导出伤亡事故情况统计分析表报表(公司)")
    @ApiImplicitParam(name = "zxSfAccidentItem", value = "伤亡事故情况统计分析表明细entity", dataType = "ZxSfAccidentItem")
    @PostMapping("/uReportZxSfAccidentItemFormCom")
    public List<ZxSfAccidentItem> uReportZxSfAccidentItemFormCom(@RequestBody(required = false) ZxSfAccidentItem zxSfAccidentItem) {
        return zxSfAccidentItemService.uReportFormCom(zxSfAccidentItem);
    }




}
