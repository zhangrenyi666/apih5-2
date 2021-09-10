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
import com.apih5.mybatis.pojo.ZxSfEquManageItem;
import com.apih5.service.ZxSfEquManageItemService;

@RestController
public class ZxSfEquManageItemController {

    @Autowired(required = true)
    private ZxSfEquManageItemService zxSfEquManageItemService;

    @ApiOperation(value="查询项目特种设备台账明细", notes="查询项目特种设备台账明细")
    @ApiImplicitParam(name = "zxSfEquManageItem", value = "项目特种设备台账明细entity", dataType = "ZxSfEquManageItem")
    @RequireToken
    @PostMapping("/getZxSfEquManageItemList")
    public ResponseEntity getZxSfEquManageItemList(@RequestBody(required = false) ZxSfEquManageItem zxSfEquManageItem) {
        return zxSfEquManageItemService.getZxSfEquManageItemListByCondition(zxSfEquManageItem);
    }

    @ApiOperation(value="查询详情项目特种设备台账明细", notes="查询详情项目特种设备台账明细")
    @ApiImplicitParam(name = "zxSfEquManageItem", value = "项目特种设备台账明细entity", dataType = "ZxSfEquManageItem")
    @RequireToken
    @PostMapping("/getZxSfEquManageItemDetail")
    public ResponseEntity getZxSfEquManageItemDetail(@RequestBody(required = false) ZxSfEquManageItem zxSfEquManageItem) {
        return zxSfEquManageItemService.getZxSfEquManageItemDetail(zxSfEquManageItem);
    }

    @ApiOperation(value="新增项目特种设备台账明细", notes="新增项目特种设备台账明细")
    @ApiImplicitParam(name = "zxSfEquManageItem", value = "项目特种设备台账明细entity", dataType = "ZxSfEquManageItem")
    @RequireToken
    @PostMapping("/addZxSfEquManageItem")
    public ResponseEntity addZxSfEquManageItem(@RequestBody(required = false) ZxSfEquManageItem zxSfEquManageItem) {
        return zxSfEquManageItemService.saveZxSfEquManageItem(zxSfEquManageItem);
    }

    @ApiOperation(value="更新项目特种设备台账明细", notes="更新项目特种设备台账明细")
    @ApiImplicitParam(name = "zxSfEquManageItem", value = "项目特种设备台账明细entity", dataType = "ZxSfEquManageItem")
    @RequireToken
    @PostMapping("/updateZxSfEquManageItem")
    public ResponseEntity updateZxSfEquManageItem(@RequestBody(required = false) ZxSfEquManageItem zxSfEquManageItem) {
        return zxSfEquManageItemService.updateZxSfEquManageItem(zxSfEquManageItem);
    }

    @ApiOperation(value="删除项目特种设备台账明细", notes="删除项目特种设备台账明细")
    @ApiImplicitParam(name = "zxSfEquManageItemList", value = "项目特种设备台账明细List", dataType = "List<ZxSfEquManageItem>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxSfEquManageItem")
    public ResponseEntity batchDeleteUpdateZxSfEquManageItem(@RequestBody(required = false) List<ZxSfEquManageItem> zxSfEquManageItemList) {
        return zxSfEquManageItemService.batchDeleteUpdateZxSfEquManageItem(zxSfEquManageItemList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

    @ApiOperation(value="查询项目特种设备台账报表", notes="查询项目特种设备台账报表")
    @ApiImplicitParam(name = "zxSfEquManageItem", value = "项目特种设备台账明细entity", dataType = "ZxSfEquManageItem")
    @RequireToken
    @PostMapping("/getZxSfEquManageItemUreportFormList")
    public ResponseEntity getZxSfEquManageItemUreportFormList(@RequestBody(required = false) ZxSfEquManageItem zxSfEquManageItem) {
        return zxSfEquManageItemService.getUreportFormList(zxSfEquManageItem);
    }

    @ApiOperation(value="导出项目特种设备台账报表", notes="导出项目特种设备台账报表")
    @ApiImplicitParam(name = "zxSfEquManageItem", value = "项目特种设备台账明细entity", dataType = "ZxSfEquManageItem")
    @PostMapping("/UreportFormZxSfEquManageItem")
    public List<ZxSfEquManageItem> UreportFormZxSfEquManageItem(@RequestBody(required = false) ZxSfEquManageItem zxSfEquManageItem) {
        return zxSfEquManageItemService.UreportForm(zxSfEquManageItem);
    }
}
