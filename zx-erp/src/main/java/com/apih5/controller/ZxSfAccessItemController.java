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
import com.apih5.mybatis.pojo.ZxSfAccessItem;
import com.apih5.service.ZxSfAccessItemService;

@RestController
public class ZxSfAccessItemController {

    @Autowired(required = true)
    private ZxSfAccessItemService zxSfAccessItemService;

    @ApiOperation(value="查询协作队伍安全考核明细", notes="查询协作队伍安全考核明细")
    @ApiImplicitParam(name = "zxSfAccessItem", value = "协作队伍安全考核明细entity", dataType = "ZxSfAccessItem")
    @RequireToken
    @PostMapping("/getZxSfAccessItemList")
    public ResponseEntity getZxSfAccessItemList(@RequestBody(required = false) ZxSfAccessItem zxSfAccessItem) {
        return zxSfAccessItemService.getZxSfAccessItemListByCondition(zxSfAccessItem);
    }

    @ApiOperation(value="查询详情协作队伍安全考核明细", notes="查询详情协作队伍安全考核明细")
    @ApiImplicitParam(name = "zxSfAccessItem", value = "协作队伍安全考核明细entity", dataType = "ZxSfAccessItem")
    @RequireToken
    @PostMapping("/getZxSfAccessItemDetail")
    public ResponseEntity getZxSfAccessItemDetail(@RequestBody(required = false) ZxSfAccessItem zxSfAccessItem) {
        return zxSfAccessItemService.getZxSfAccessItemDetail(zxSfAccessItem);
    }

    @ApiOperation(value="新增协作队伍安全考核明细", notes="新增协作队伍安全考核明细")
    @ApiImplicitParam(name = "zxSfAccessItem", value = "协作队伍安全考核明细entity", dataType = "ZxSfAccessItem")
    @RequireToken
    @PostMapping("/addZxSfAccessItem")
    public ResponseEntity addZxSfAccessItem(@RequestBody(required = false) ZxSfAccessItem zxSfAccessItem) {
        return zxSfAccessItemService.saveZxSfAccessItem(zxSfAccessItem);
    }

    @ApiOperation(value="更新协作队伍安全考核明细", notes="更新协作队伍安全考核明细")
    @ApiImplicitParam(name = "zxSfAccessItem", value = "协作队伍安全考核明细entity", dataType = "ZxSfAccessItem")
    @RequireToken
    @PostMapping("/updateZxSfAccessItem")
    public ResponseEntity updateZxSfAccessItem(@RequestBody(required = false) ZxSfAccessItem zxSfAccessItem) {
        return zxSfAccessItemService.updateZxSfAccessItem(zxSfAccessItem);
    }

    @ApiOperation(value="删除协作队伍安全考核明细", notes="删除协作队伍安全考核明细")
    @ApiImplicitParam(name = "zxSfAccessItemList", value = "协作队伍安全考核明细List", dataType = "List<ZxSfAccessItem>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxSfAccessItem")
    public ResponseEntity batchDeleteUpdateZxSfAccessItem(@RequestBody(required = false) List<ZxSfAccessItem> zxSfAccessItemList) {
        return zxSfAccessItemService.batchDeleteUpdateZxSfAccessItem(zxSfAccessItemList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
