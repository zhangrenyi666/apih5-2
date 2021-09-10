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
import com.apih5.mybatis.pojo.ZxSfEduItem;
import com.apih5.service.ZxSfEduItemService;

@RestController
public class ZxSfEduItemController {

    @Autowired(required = true)
    private ZxSfEduItemService zxSfEduItemService;

    @ApiOperation(value="查询安全教育培训明细", notes="查询安全教育培训明细")
    @ApiImplicitParam(name = "zxSfEduItem", value = "安全教育培训明细entity", dataType = "ZxSfEduItem")
    @RequireToken
    @PostMapping("/getZxSfEduItemList")
    public ResponseEntity getZxSfEduItemList(@RequestBody(required = false) ZxSfEduItem zxSfEduItem) {
        return zxSfEduItemService.getZxSfEduItemListByCondition(zxSfEduItem);
    }

    @ApiOperation(value="查询详情安全教育培训明细", notes="查询详情安全教育培训明细")
    @ApiImplicitParam(name = "zxSfEduItem", value = "安全教育培训明细entity", dataType = "ZxSfEduItem")
    @RequireToken
    @PostMapping("/getZxSfEduItemDetail")
    public ResponseEntity getZxSfEduItemDetail(@RequestBody(required = false) ZxSfEduItem zxSfEduItem) {
        return zxSfEduItemService.getZxSfEduItemDetail(zxSfEduItem);
    }

    @ApiOperation(value="新增安全教育培训明细", notes="新增安全教育培训明细")
    @ApiImplicitParam(name = "zxSfEduItem", value = "安全教育培训明细entity", dataType = "ZxSfEduItem")
    @RequireToken
    @PostMapping("/addZxSfEduItem")
    public ResponseEntity addZxSfEduItem(@RequestBody(required = false) ZxSfEduItem zxSfEduItem) {
        return zxSfEduItemService.saveZxSfEduItem(zxSfEduItem);
    }

    @ApiOperation(value="更新安全教育培训明细", notes="更新安全教育培训明细")
    @ApiImplicitParam(name = "zxSfEduItem", value = "安全教育培训明细entity", dataType = "ZxSfEduItem")
    @RequireToken
    @PostMapping("/updateZxSfEduItem")
    public ResponseEntity updateZxSfEduItem(@RequestBody(required = false) ZxSfEduItem zxSfEduItem) {
        return zxSfEduItemService.updateZxSfEduItem(zxSfEduItem);
    }

    @ApiOperation(value="删除安全教育培训明细", notes="删除安全教育培训明细")
    @ApiImplicitParam(name = "zxSfEduItemList", value = "安全教育培训明细List", dataType = "List<ZxSfEduItem>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxSfEduItem")
    public ResponseEntity batchDeleteUpdateZxSfEduItem(@RequestBody(required = false) List<ZxSfEduItem> zxSfEduItemList) {
        return zxSfEduItemService.batchDeleteUpdateZxSfEduItem(zxSfEduItemList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

    @ApiOperation(value="查询报表数据", notes="查询报表数据")
    @ApiImplicitParam(name = "zxSfEduItem", value = "安全教育培训明细entity", dataType = "ZxSfEduItem")
    @RequireToken
    @PostMapping("/getZxSfEduUReportFormList")
    public ResponseEntity getZxSfEduUReportFormList(@RequestBody(required = false) ZxSfEduItem zxSfEduItem) {
        return zxSfEduItemService.getUReportFormList(zxSfEduItem);
    }

    @ApiOperation(value="导出报表数据查询", notes="导出报表数据查询")
    @ApiImplicitParam(name = "zxSfEduItem", value = "安全教育培训明细entity", dataType = "ZxSfEduItem")
    @PostMapping("/uReportZxSfEduItemForm")
    public List<ZxSfEduItem> uReportZxSfEduItemForm(@RequestBody(required = false) ZxSfEduItem zxSfEduItem) {
        return zxSfEduItemService.uReportForm(zxSfEduItem);
    }
}
