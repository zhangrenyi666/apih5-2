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
import com.apih5.mybatis.pojo.ZxSfEAccidentItem;
import com.apih5.service.ZxSfEAccidentItemService;

@RestController
public class ZxSfEAccidentItemController {

    @Autowired(required = true)
    private ZxSfEAccidentItemService zxSfEAccidentItemService;

    @ApiOperation(value="查询企业职工伤亡事故年度统计分析表明细", notes="查询企业职工伤亡事故年度统计分析表明细")
    @ApiImplicitParam(name = "zxSfEAccidentItem", value = "企业职工伤亡事故年度统计分析表明细entity", dataType = "ZxSfEAccidentItem")
    @RequireToken
    @PostMapping("/getZxSfEAccidentItemList")
    public ResponseEntity getZxSfEAccidentItemList(@RequestBody(required = false) ZxSfEAccidentItem zxSfEAccidentItem) {
        return zxSfEAccidentItemService.getZxSfEAccidentItemListByCondition(zxSfEAccidentItem);
    }

    @ApiOperation(value="查询详情企业职工伤亡事故年度统计分析表明细", notes="查询详情企业职工伤亡事故年度统计分析表明细")
    @ApiImplicitParam(name = "zxSfEAccidentItem", value = "企业职工伤亡事故年度统计分析表明细entity", dataType = "ZxSfEAccidentItem")
    @RequireToken
    @PostMapping("/getZxSfEAccidentItemDetail")
    public ResponseEntity getZxSfEAccidentItemDetail(@RequestBody(required = false) ZxSfEAccidentItem zxSfEAccidentItem) {
        return zxSfEAccidentItemService.getZxSfEAccidentItemDetail(zxSfEAccidentItem);
    }

    @ApiOperation(value="新增企业职工伤亡事故年度统计分析表明细", notes="新增企业职工伤亡事故年度统计分析表明细")
    @ApiImplicitParam(name = "zxSfEAccidentItem", value = "企业职工伤亡事故年度统计分析表明细entity", dataType = "ZxSfEAccidentItem")
    @RequireToken
    @PostMapping("/addZxSfEAccidentItem")
    public ResponseEntity addZxSfEAccidentItem(@RequestBody(required = false) ZxSfEAccidentItem zxSfEAccidentItem) {
        return zxSfEAccidentItemService.saveZxSfEAccidentItem(zxSfEAccidentItem);
    }

    @ApiOperation(value="更新企业职工伤亡事故年度统计分析表明细", notes="更新企业职工伤亡事故年度统计分析表明细")
    @ApiImplicitParam(name = "zxSfEAccidentItem", value = "企业职工伤亡事故年度统计分析表明细entity", dataType = "ZxSfEAccidentItem")
    @RequireToken
    @PostMapping("/updateZxSfEAccidentItem")
    public ResponseEntity updateZxSfEAccidentItem(@RequestBody(required = false) ZxSfEAccidentItem zxSfEAccidentItem) {
        return zxSfEAccidentItemService.updateZxSfEAccidentItem(zxSfEAccidentItem);
    }

    @ApiOperation(value="删除企业职工伤亡事故年度统计分析表明细", notes="删除企业职工伤亡事故年度统计分析表明细")
    @ApiImplicitParam(name = "zxSfEAccidentItemList", value = "企业职工伤亡事故年度统计分析表明细List", dataType = "List<ZxSfEAccidentItem>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxSfEAccidentItem")
    public ResponseEntity batchDeleteUpdateZxSfEAccidentItem(@RequestBody(required = false) List<ZxSfEAccidentItem> zxSfEAccidentItemList) {
        return zxSfEAccidentItemService.batchDeleteUpdateZxSfEAccidentItem(zxSfEAccidentItemList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

    @ApiOperation(value="年度伤亡事故情况统计分析表报表(项目)", notes="导出伤亡事故情况统计分析表报表(项目)")
    @ApiImplicitParam(name = "zxSfAccidentItem", value = "伤亡事故情况统计分析表明细entity", dataType = "ZxSfAccidentItem")
    @RequireToken
    @PostMapping("/getZxSfAccidentItemUReportFormYearList")
    public ResponseEntity getUReportFormYearList(@RequestBody(required = false) ZxSfEAccidentItem zxSfAccidentItem) {
        return zxSfEAccidentItemService.getUReportFormYearList(zxSfAccidentItem);
    }

    @ApiOperation(value="年度伤亡事故情况统计分析表报表(项目)", notes="导出伤亡事故情况统计分析表报表(项目)")
    @ApiImplicitParam(name = "zxSfAccidentItem", value = "伤亡事故情况统计分析表明细entity", dataType = "ZxSfAccidentItem")
    @PostMapping("/uReportZxSfAccidentItemFormYear")
    public List<ZxSfEAccidentItem> uReportZxSfAccidentItemFormYear(@RequestBody(required = false) ZxSfEAccidentItem zxSfAccidentItem) {
        return zxSfEAccidentItemService.uReportFormYear(zxSfAccidentItem);
    }

    @ApiOperation(value="年度伤亡事故情况统计分析表报表(公司)", notes="导出伤亡事故情况统计分析表报表(公司)")
    @ApiImplicitParam(name = "zxSfAccidentItem", value = "伤亡事故情况统计分析表明细entity", dataType = "ZxSfAccidentItem")
    @RequireToken
    @PostMapping("/getZxSfAccidentItemUReportFormYearComList")
    public ResponseEntity getUReportFormYearListCom(@RequestBody(required = false) ZxSfEAccidentItem zxSfAccidentItem) {
        return zxSfEAccidentItemService.getUReportFormYearListCom(zxSfAccidentItem);
    }

    @ApiOperation(value="年度伤亡事故情况统计分析表报表(公司)", notes="导出伤亡事故情况统计分析表报表(公司)")
    @ApiImplicitParam(name = "zxSfAccidentItem", value = "伤亡事故情况统计分析表明细entity", dataType = "ZxSfAccidentItem")
    @PostMapping("/uReportZxSfAccidentItemFormYearCom")
    public List<ZxSfEAccidentItem> uReportZxSfAccidentItemFormYearCom(@RequestBody(required = false) ZxSfEAccidentItem zxSfAccidentItem) {
        return zxSfEAccidentItemService.uReportFormYearCom(zxSfAccidentItem);
    }
}
