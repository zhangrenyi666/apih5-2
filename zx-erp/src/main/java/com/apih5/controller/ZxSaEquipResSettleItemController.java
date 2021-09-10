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
import com.apih5.mybatis.pojo.ZxSaEquipResSettleItem;
import com.apih5.service.ZxSaEquipResSettleItemService;

@RestController
public class ZxSaEquipResSettleItemController {

    @Autowired(required = true)
    private ZxSaEquipResSettleItemService zxSaEquipResSettleItemService;

    @ApiOperation(value="查询清单结算明细表", notes="查询清单结算明细表")
    @ApiImplicitParam(name = "zxSaEquipResSettleItem", value = "清单结算明细表entity", dataType = "ZxSaEquipResSettleItem")
    @RequireToken
    @PostMapping("/getZxSaEquipResSettleItemList")
    public ResponseEntity getZxSaEquipResSettleItemList(@RequestBody(required = false) ZxSaEquipResSettleItem zxSaEquipResSettleItem) {
        return zxSaEquipResSettleItemService.getZxSaEquipResSettleItemListByCondition(zxSaEquipResSettleItem);
    }

    @ApiOperation(value="查询详情清单结算明细表", notes="查询详情清单结算明细表")
    @ApiImplicitParam(name = "zxSaEquipResSettleItem", value = "清单结算明细表entity", dataType = "ZxSaEquipResSettleItem")
    @RequireToken
    @PostMapping("/getZxSaEquipResSettleItemDetail")
    public ResponseEntity getZxSaEquipResSettleItemDetail(@RequestBody(required = false) ZxSaEquipResSettleItem zxSaEquipResSettleItem) {
        return zxSaEquipResSettleItemService.getZxSaEquipResSettleItemDetail(zxSaEquipResSettleItem);
    }

    @ApiOperation(value="新增清单结算明细表", notes="新增清单结算明细表")
    @ApiImplicitParam(name = "zxSaEquipResSettleItem", value = "清单结算明细表entity", dataType = "ZxSaEquipResSettleItem")
    @RequireToken
    @PostMapping("/addZxSaEquipResSettleItem")
    public ResponseEntity addZxSaEquipResSettleItem(@RequestBody(required = false) ZxSaEquipResSettleItem zxSaEquipResSettleItem) {
        return zxSaEquipResSettleItemService.saveZxSaEquipResSettleItem(zxSaEquipResSettleItem);
    }

    @ApiOperation(value="更新清单结算明细表", notes="更新清单结算明细表")
    @ApiImplicitParam(name = "zxSaEquipResSettleItem", value = "清单结算明细表entity", dataType = "ZxSaEquipResSettleItem")
    @RequireToken
    @PostMapping("/updateZxSaEquipResSettleItem")
    public ResponseEntity updateZxSaEquipResSettleItem(@RequestBody(required = false) ZxSaEquipResSettleItem zxSaEquipResSettleItem) {
        return zxSaEquipResSettleItemService.updateZxSaEquipResSettleItem(zxSaEquipResSettleItem);
    }

    @ApiOperation(value="删除清单结算明细表", notes="删除清单结算明细表")
    @ApiImplicitParam(name = "zxSaEquipResSettleItemList", value = "清单结算明细表List", dataType = "List<ZxSaEquipResSettleItem>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxSaEquipResSettleItem")
    public ResponseEntity batchDeleteUpdateZxSaEquipResSettleItem(@RequestBody(required = false) List<ZxSaEquipResSettleItem> zxSaEquipResSettleItemList) {
        return zxSaEquipResSettleItemService.batchDeleteUpdateZxSaEquipResSettleItem(zxSaEquipResSettleItemList);
    }

    @ApiOperation(value="报表导出清单结算明细表", notes="报表导出清单结算明细表")
    @ApiImplicitParam(name = "zxSaEquipResSettleItem", value = "清单结算明细表entity", dataType = "ZxSaEquipResSettleItem")
    @PostMapping("/ureportZxSaEquipResSettleItem")
    public List<ZxSaEquipResSettleItem> ureportZxSaEquipResSettleItem(@RequestBody(required = false) ZxSaEquipResSettleItem zxSaEquipResSettleItem) {
        return zxSaEquipResSettleItemService.ureportZxSaEquipResSettleItem(zxSaEquipResSettleItem);
    }
    
    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    
    
}
