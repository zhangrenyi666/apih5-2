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
import com.apih5.mybatis.pojo.ZxCrJYearCreditEvaItem;
import com.apih5.service.ZxCrJYearCreditEvaItemService;

@RestController
public class ZxCrJYearCreditEvaItemController {

    @Autowired(required = true)
    private ZxCrJYearCreditEvaItemService zxCrJYearCreditEvaItemService;

    @ApiOperation(value="查询局年度信用评价明细", notes="查询局年度信用评价明细")
    @ApiImplicitParam(name = "zxCrJYearCreditEvaItem", value = "局年度信用评价明细entity", dataType = "ZxCrJYearCreditEvaItem")
    @RequireToken
    @PostMapping("/getZxCrJYearCreditEvaItemList")
    public ResponseEntity getZxCrJYearCreditEvaItemList(@RequestBody(required = false) ZxCrJYearCreditEvaItem zxCrJYearCreditEvaItem) {
        return zxCrJYearCreditEvaItemService.getZxCrJYearCreditEvaItemListByCondition(zxCrJYearCreditEvaItem);
    }

    @ApiOperation(value="查询详情局年度信用评价明细", notes="查询详情局年度信用评价明细")
    @ApiImplicitParam(name = "zxCrJYearCreditEvaItem", value = "局年度信用评价明细entity", dataType = "ZxCrJYearCreditEvaItem")
    @RequireToken
    @PostMapping("/getZxCrJYearCreditEvaItemDetail")
    public ResponseEntity getZxCrJYearCreditEvaItemDetail(@RequestBody(required = false) ZxCrJYearCreditEvaItem zxCrJYearCreditEvaItem) {
        return zxCrJYearCreditEvaItemService.getZxCrJYearCreditEvaItemDetail(zxCrJYearCreditEvaItem);
    }

    @ApiOperation(value="新增局年度信用评价明细", notes="新增局年度信用评价明细")
    @ApiImplicitParam(name = "zxCrJYearCreditEvaItem", value = "局年度信用评价明细entity", dataType = "ZxCrJYearCreditEvaItem")
    @RequireToken
    @PostMapping("/addZxCrJYearCreditEvaItem")
    public ResponseEntity addZxCrJYearCreditEvaItem(@RequestBody(required = false) ZxCrJYearCreditEvaItem zxCrJYearCreditEvaItem) {
        return zxCrJYearCreditEvaItemService.saveZxCrJYearCreditEvaItem(zxCrJYearCreditEvaItem);
    }

    @ApiOperation(value="更新局年度信用评价明细", notes="更新局年度信用评价明细")
    @ApiImplicitParam(name = "zxCrJYearCreditEvaItem", value = "局年度信用评价明细entity", dataType = "ZxCrJYearCreditEvaItem")
    @RequireToken
    @PostMapping("/updateZxCrJYearCreditEvaItem")
    public ResponseEntity updateZxCrJYearCreditEvaItem(@RequestBody(required = false) ZxCrJYearCreditEvaItem zxCrJYearCreditEvaItem) {
        return zxCrJYearCreditEvaItemService.updateZxCrJYearCreditEvaItem(zxCrJYearCreditEvaItem);
    }

    @ApiOperation(value="删除局年度信用评价明细", notes="删除局年度信用评价明细")
    @ApiImplicitParam(name = "zxCrJYearCreditEvaItemList", value = "局年度信用评价明细List", dataType = "List<ZxCrJYearCreditEvaItem>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxCrJYearCreditEvaItem")
    public ResponseEntity batchDeleteUpdateZxCrJYearCreditEvaItem(@RequestBody(required = false) List<ZxCrJYearCreditEvaItem> zxCrJYearCreditEvaItemList) {
        return zxCrJYearCreditEvaItemService.batchDeleteUpdateZxCrJYearCreditEvaItem(zxCrJYearCreditEvaItemList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    
    @ApiOperation(value="查询局年度信用评价明细", notes="查询局年度信用评价明细")
    @ApiImplicitParam(name = "zxCrJYearCreditEvaItem", value = "局年度信用评价明细entity", dataType = "ZxCrJYearCreditEvaItem")
    @RequireToken
    @PostMapping("/getZxCrJYearCreditEvaItemInit")
    public ResponseEntity getZxCrJYearCreditEvaItemInit(@RequestBody(required = false) ZxCrJYearCreditEvaItem zxCrJYearCreditEvaItem) {
        return zxCrJYearCreditEvaItemService.getZxCrJYearCreditEvaItemInit(zxCrJYearCreditEvaItem);
    }
    
    @ApiOperation(value="更新全部局年度信用评价明细", notes="更新全部局年度信用评价明细")
    @ApiImplicitParam(name = "zxCrJYearCreditEvaItem", value = "局年度信用评价明细entity", dataType = "ZxCrJYearCreditEvaItem")
    @RequireToken
    @PostMapping("/updateZxCrJYearCreditEvaItemAll")
    public ResponseEntity updateZxCrJYearCreditEvaItemAll(@RequestBody(required = false) List<ZxCrJYearCreditEvaItem> zxCrJYearCreditEvaItem) {
        return zxCrJYearCreditEvaItemService.updateZxCrJYearCreditEvaItemAll(zxCrJYearCreditEvaItem);
    }
}
