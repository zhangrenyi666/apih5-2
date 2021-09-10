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
import com.apih5.mybatis.pojo.ZxSkMmReqPlanItem;
import com.apih5.service.ZxSkMmReqPlanItemService;

@RestController
public class ZxSkMmReqPlanItemController {

    @Autowired(required = true)
    private ZxSkMmReqPlanItemService zxSkMmReqPlanItemService;

    @ApiOperation(value="查询月物资需用计划明细", notes="查询月物资需用计划明细")
    @ApiImplicitParam(name = "zxSkMmReqPlanItem", value = "月物资需用计划明细entity", dataType = "ZxSkMmReqPlanItem")
    @RequireToken
    @PostMapping("/getZxSkMmReqPlanItemList")
    public ResponseEntity getZxSkMmReqPlanItemList(@RequestBody(required = false) ZxSkMmReqPlanItem zxSkMmReqPlanItem) {
        return zxSkMmReqPlanItemService.getZxSkMmReqPlanItemListByCondition(zxSkMmReqPlanItem);
    }

    @ApiOperation(value="查询详情月物资需用计划明细", notes="查询详情月物资需用计划明细")
    @ApiImplicitParam(name = "zxSkMmReqPlanItem", value = "月物资需用计划明细entity", dataType = "ZxSkMmReqPlanItem")
    @RequireToken
    @PostMapping("/getZxSkMmReqPlanItemDetails")
    public ResponseEntity getZxSkMmReqPlanItemDetails(@RequestBody(required = false) ZxSkMmReqPlanItem zxSkMmReqPlanItem) {
        return zxSkMmReqPlanItemService.getZxSkMmReqPlanItemDetails(zxSkMmReqPlanItem);
    }

    @ApiOperation(value="新增月物资需用计划明细", notes="新增月物资需用计划明细")
    @ApiImplicitParam(name = "zxSkMmReqPlanItem", value = "月物资需用计划明细entity", dataType = "ZxSkMmReqPlanItem")
    @RequireToken
    @PostMapping("/addZxSkMmReqPlanItem")
    public ResponseEntity addZxSkMmReqPlanItem(@RequestBody(required = false) ZxSkMmReqPlanItem zxSkMmReqPlanItem) {
        return zxSkMmReqPlanItemService.saveZxSkMmReqPlanItem(zxSkMmReqPlanItem);
    }

    @ApiOperation(value="更新月物资需用计划明细", notes="更新月物资需用计划明细")
    @ApiImplicitParam(name = "zxSkMmReqPlanItem", value = "月物资需用计划明细entity", dataType = "ZxSkMmReqPlanItem")
    @RequireToken
    @PostMapping("/updateZxSkMmReqPlanItem")
    public ResponseEntity updateZxSkMmReqPlanItem(@RequestBody(required = false) ZxSkMmReqPlanItem zxSkMmReqPlanItem) {
        return zxSkMmReqPlanItemService.updateZxSkMmReqPlanItem(zxSkMmReqPlanItem);
    }

    @ApiOperation(value="删除月物资需用计划明细", notes="删除月物资需用计划明细")
    @ApiImplicitParam(name = "zxSkMmReqPlanItemList", value = "月物资需用计划明细List", dataType = "List<ZxSkMmReqPlanItem>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxSkMmReqPlanItem")
    public ResponseEntity batchDeleteUpdateZxSkMmReqPlanItem(@RequestBody(required = false) List<ZxSkMmReqPlanItem> zxSkMmReqPlanItemList) {
        return zxSkMmReqPlanItemService.batchDeleteUpdateZxSkMmReqPlanItem(zxSkMmReqPlanItemList);
    }

}
