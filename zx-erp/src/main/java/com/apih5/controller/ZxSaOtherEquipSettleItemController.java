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
import com.apih5.mybatis.pojo.ZxSaOtherEquipSettleItem;
import com.apih5.service.ZxSaOtherEquipSettleItemService;

@RestController
public class ZxSaOtherEquipSettleItemController {

    @Autowired(required = true)
    private ZxSaOtherEquipSettleItemService zxSaOtherEquipSettleItemService;

    @ApiOperation(value="查询其他类结算子表清单明细（统计项）", notes="查询其他类结算子表清单明细（统计项）")
    @ApiImplicitParam(name = "zxSaOtherEquipSettleItem", value = "其他类结算子表清单明细（统计项）entity", dataType = "ZxSaOtherEquipSettleItem")
    @RequireToken
    @PostMapping("/getZxSaOtherEquipSettleItemList")
    public ResponseEntity getZxSaOtherEquipSettleItemList(@RequestBody(required = false) ZxSaOtherEquipSettleItem zxSaOtherEquipSettleItem) {
        return zxSaOtherEquipSettleItemService.getZxSaOtherEquipSettleItemListByCondition(zxSaOtherEquipSettleItem);
    }

    @ApiOperation(value="查询详情其他类结算子表清单明细（统计项）", notes="查询详情其他类结算子表清单明细（统计项）")
    @ApiImplicitParam(name = "zxSaOtherEquipSettleItem", value = "其他类结算子表清单明细（统计项）entity", dataType = "ZxSaOtherEquipSettleItem")
    @RequireToken
    @PostMapping("/getZxSaOtherEquipSettleItemDetail")
    public ResponseEntity getZxSaOtherEquipSettleItemDetail(@RequestBody(required = false) ZxSaOtherEquipSettleItem zxSaOtherEquipSettleItem) {
        return zxSaOtherEquipSettleItemService.getZxSaOtherEquipSettleItemDetail(zxSaOtherEquipSettleItem);
    }

    @ApiOperation(value="新增其他类结算子表清单明细（统计项）", notes="新增其他类结算子表清单明细（统计项）")
    @ApiImplicitParam(name = "zxSaOtherEquipSettleItem", value = "其他类结算子表清单明细（统计项）entity", dataType = "ZxSaOtherEquipSettleItem")
    @RequireToken
    @PostMapping("/addZxSaOtherEquipSettleItem")
    public ResponseEntity addZxSaOtherEquipSettleItem(@RequestBody(required = false) ZxSaOtherEquipSettleItem zxSaOtherEquipSettleItem) {
        return zxSaOtherEquipSettleItemService.saveZxSaOtherEquipSettleItem(zxSaOtherEquipSettleItem);
    }

    @ApiOperation(value="更新其他类结算子表清单明细（统计项）", notes="更新其他类结算子表清单明细（统计项）")
    @ApiImplicitParam(name = "zxSaOtherEquipSettleItem", value = "其他类结算子表清单明细（统计项）entity", dataType = "ZxSaOtherEquipSettleItem")
    @RequireToken
    @PostMapping("/updateZxSaOtherEquipSettleItem")
    public ResponseEntity updateZxSaOtherEquipSettleItem(@RequestBody(required = false) ZxSaOtherEquipSettleItem zxSaOtherEquipSettleItem) {
        return zxSaOtherEquipSettleItemService.updateZxSaOtherEquipSettleItem(zxSaOtherEquipSettleItem);
    }

    @ApiOperation(value="删除其他类结算子表清单明细（统计项）", notes="删除其他类结算子表清单明细（统计项）")
    @ApiImplicitParam(name = "zxSaOtherEquipSettleItemList", value = "其他类结算子表清单明细（统计项）List", dataType = "List<ZxSaOtherEquipSettleItem>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxSaOtherEquipSettleItem")
    public ResponseEntity batchDeleteUpdateZxSaOtherEquipSettleItem(@RequestBody(required = false) List<ZxSaOtherEquipSettleItem> zxSaOtherEquipSettleItemList) {
        return zxSaOtherEquipSettleItemService.batchDeleteUpdateZxSaOtherEquipSettleItem(zxSaOtherEquipSettleItemList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

    @ApiOperation(value="初始化结算单统计项明细数据", notes="初始化结算单统计项明细数据")
    @ApiImplicitParam(name = "zxSaOtherEquipSettleItemList", value = "其他类结算明细统计项List", dataType = "List<ZxSaOtherEquipSettleItem>")
    @RequireToken
    @PostMapping("/getZxSaOtherEquipSettleItemByContractId")
    public ResponseEntity getZxSaOtherEquipSettleItemByContractId(@RequestBody(required = false) ZxSaOtherEquipSettleItem zxSaOtherEquipSettleItem) {
        return zxSaOtherEquipSettleItemService.getZxSaOtherEquipSettleItemByContractId(zxSaOtherEquipSettleItem);
    }
}
