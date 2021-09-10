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
import com.apih5.mybatis.pojo.ZxSkColInvenItem;
import com.apih5.service.ZxSkColInvenItemService;

@RestController
public class ZxSkColInvenItemController {

    @Autowired(required = true)
    private ZxSkColInvenItemService zxSkColInvenItemService;

    @ApiOperation(value="查询协作队伍库存盘点明细", notes="查询协作队伍库存盘点明细")
    @ApiImplicitParam(name = "zxSkColInvenItem", value = "协作队伍库存盘点明细entity", dataType = "ZxSkColInvenItem")
    @RequireToken
    @PostMapping("/getZxSkColInvenItemList")
    public ResponseEntity getZxSkColInvenItemList(@RequestBody(required = false) ZxSkColInvenItem zxSkColInvenItem) {
        return zxSkColInvenItemService.getZxSkColInvenItemListByCondition(zxSkColInvenItem);
    }

    @ApiOperation(value="查询详情协作队伍库存盘点明细", notes="查询详情协作队伍库存盘点明细")
    @ApiImplicitParam(name = "zxSkColInvenItem", value = "协作队伍库存盘点明细entity", dataType = "ZxSkColInvenItem")
    @RequireToken
    @PostMapping("/getZxSkColInvenItemDetail")
    public ResponseEntity getZxSkColInvenItemDetail(@RequestBody(required = false) ZxSkColInvenItem zxSkColInvenItem) {
        return zxSkColInvenItemService.getZxSkColInvenItemDetail(zxSkColInvenItem);
    }

    @ApiOperation(value="新增协作队伍库存盘点明细", notes="新增协作队伍库存盘点明细")
    @ApiImplicitParam(name = "zxSkColInvenItem", value = "协作队伍库存盘点明细entity", dataType = "ZxSkColInvenItem")
    @RequireToken
    @PostMapping("/addZxSkColInvenItem")
    public ResponseEntity addZxSkColInvenItem(@RequestBody(required = false) ZxSkColInvenItem zxSkColInvenItem) {
        return zxSkColInvenItemService.saveZxSkColInvenItem(zxSkColInvenItem);
    }

    @ApiOperation(value="更新协作队伍库存盘点明细", notes="更新协作队伍库存盘点明细")
    @ApiImplicitParam(name = "zxSkColInvenItem", value = "协作队伍库存盘点明细entity", dataType = "ZxSkColInvenItem")
    @RequireToken
    @PostMapping("/updateZxSkColInvenItem")
    public ResponseEntity updateZxSkColInvenItem(@RequestBody(required = false) ZxSkColInvenItem zxSkColInvenItem) {
        return zxSkColInvenItemService.updateZxSkColInvenItem(zxSkColInvenItem);
    }

    @ApiOperation(value="删除协作队伍库存盘点明细", notes="删除协作队伍库存盘点明细")
    @ApiImplicitParam(name = "zxSkColInvenItemList", value = "协作队伍库存盘点明细List", dataType = "List<ZxSkColInvenItem>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxSkColInvenItem")
    public ResponseEntity batchDeleteUpdateZxSkColInvenItem(@RequestBody(required = false) List<ZxSkColInvenItem> zxSkColInvenItemList) {
        return zxSkColInvenItemService.batchDeleteUpdateZxSkColInvenItem(zxSkColInvenItemList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
