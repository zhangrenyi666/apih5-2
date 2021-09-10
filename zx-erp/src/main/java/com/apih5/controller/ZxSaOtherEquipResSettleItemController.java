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
import com.apih5.mybatis.pojo.ZxSaOtherEquipResSettleItem;
import com.apih5.service.ZxSaOtherEquipResSettleItemService;

@RestController
public class ZxSaOtherEquipResSettleItemController {

    @Autowired(required = true)
    private ZxSaOtherEquipResSettleItemService zxSaOtherEquipResSettleItemService;

    @ApiOperation(value="查询其他类细目结算清单明细", notes="查询其他类细目结算清单明细")
    @ApiImplicitParam(name = "zxSaOtherEquipResSettleItem", value = "其他类细目结算清单明细entity", dataType = "ZxSaOtherEquipResSettleItem")
    @RequireToken
    @PostMapping("/getZxSaOtherEquipResSettleItemList")
    public ResponseEntity getZxSaOtherEquipResSettleItemList(@RequestBody(required = false) ZxSaOtherEquipResSettleItem zxSaOtherEquipResSettleItem) {
        return zxSaOtherEquipResSettleItemService.getZxSaOtherEquipResSettleItemListByCondition(zxSaOtherEquipResSettleItem);
    }

    @ApiOperation(value="查询详情其他类细目结算清单明细", notes="查询详情其他类细目结算清单明细")
    @ApiImplicitParam(name = "zxSaOtherEquipResSettleItem", value = "其他类细目结算清单明细entity", dataType = "ZxSaOtherEquipResSettleItem")
    @RequireToken
    @PostMapping("/getZxSaOtherEquipResSettleItemDetail")
    public ResponseEntity getZxSaOtherEquipResSettleItemDetail(@RequestBody(required = false) ZxSaOtherEquipResSettleItem zxSaOtherEquipResSettleItem) {
        return zxSaOtherEquipResSettleItemService.getZxSaOtherEquipResSettleItemDetail(zxSaOtherEquipResSettleItem);
    }

    @ApiOperation(value="新增其他类细目结算清单明细", notes="新增其他类细目结算清单明细")
    @ApiImplicitParam(name = "zxSaOtherEquipResSettleItem", value = "其他类细目结算清单明细entity", dataType = "ZxSaOtherEquipResSettleItem")
    @RequireToken
    @PostMapping("/addZxSaOtherEquipResSettleItem")
    public ResponseEntity addZxSaOtherEquipResSettleItem(@RequestBody(required = false) ZxSaOtherEquipResSettleItem zxSaOtherEquipResSettleItem) {
        return zxSaOtherEquipResSettleItemService.saveZxSaOtherEquipResSettleItem(zxSaOtherEquipResSettleItem);
    }

    @ApiOperation(value="更新其他类细目结算清单明细", notes="更新其他类细目结算清单明细")
    @ApiImplicitParam(name = "zxSaOtherEquipResSettleItem", value = "其他类细目结算清单明细entity", dataType = "ZxSaOtherEquipResSettleItem")
    @RequireToken
    @PostMapping("/updateZxSaOtherEquipResSettleItem")
    public ResponseEntity updateZxSaOtherEquipResSettleItem(@RequestBody(required = false) ZxSaOtherEquipResSettleItem zxSaOtherEquipResSettleItem) {
        return zxSaOtherEquipResSettleItemService.updateZxSaOtherEquipResSettleItem(zxSaOtherEquipResSettleItem);
    }

    @ApiOperation(value="删除其他类细目结算清单明细", notes="删除其他类细目结算清单明细")
    @ApiImplicitParam(name = "zxSaOtherEquipResSettleItemList", value = "其他类细目结算清单明细List", dataType = "List<ZxSaOtherEquipResSettleItem>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxSaOtherEquipResSettleItem")
    public ResponseEntity batchDeleteUpdateZxSaOtherEquipResSettleItem(@RequestBody(required = false) List<ZxSaOtherEquipResSettleItem> zxSaOtherEquipResSettleItemList) {
        return zxSaOtherEquipResSettleItemService.batchDeleteUpdateZxSaOtherEquipResSettleItem(zxSaOtherEquipResSettleItemList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
