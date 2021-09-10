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
import com.apih5.mybatis.pojo.ZxSkTtReqPlanItem;
import com.apih5.service.ZxSkTtReqPlanItemService;

@RestController
public class ZxSkTtReqPlanItemController {

    @Autowired(required = true)
    private ZxSkTtReqPlanItemService zxSkTtReqPlanItemService;

    @ApiOperation(value="查询物资总需用计划明细", notes="查询物资总需用计划明细")
    @ApiImplicitParam(name = "zxSkTtReqPlanItem", value = "物资总需用计划明细entity", dataType = "ZxSkTtReqPlanItem")
    @RequireToken
    @PostMapping("/getZxSkTtReqPlanItemList")
    public ResponseEntity getZxSkTtReqPlanItemList(@RequestBody(required = false) ZxSkTtReqPlanItem zxSkTtReqPlanItem) {
        return zxSkTtReqPlanItemService.getZxSkTtReqPlanItemListByCondition(zxSkTtReqPlanItem);
    }

    @ApiOperation(value="查询详情物资总需用计划明细", notes="查询详情物资总需用计划明细")
    @ApiImplicitParam(name = "zxSkTtReqPlanItem", value = "物资总需用计划明细entity", dataType = "ZxSkTtReqPlanItem")
    @RequireToken
    @PostMapping("/getZxSkTtReqPlanItemDetails")
    public ResponseEntity getZxSkTtReqPlanItemDetails(@RequestBody(required = false) ZxSkTtReqPlanItem zxSkTtReqPlanItem) {
        return zxSkTtReqPlanItemService.getZxSkTtReqPlanItemDetails(zxSkTtReqPlanItem);
    }

    @ApiOperation(value="新增物资总需用计划明细", notes="新增物资总需用计划明细")
    @ApiImplicitParam(name = "zxSkTtReqPlanItem", value = "物资总需用计划明细entity", dataType = "ZxSkTtReqPlanItem")
    @RequireToken
    @PostMapping("/addZxSkTtReqPlanItem")
    public ResponseEntity addZxSkTtReqPlanItem(@RequestBody(required = false) ZxSkTtReqPlanItem zxSkTtReqPlanItem) {
        return zxSkTtReqPlanItemService.saveZxSkTtReqPlanItem(zxSkTtReqPlanItem);
    }

    @ApiOperation(value="更新物资总需用计划明细", notes="更新物资总需用计划明细")
    @ApiImplicitParam(name = "zxSkTtReqPlanItem", value = "物资总需用计划明细entity", dataType = "ZxSkTtReqPlanItem")
    @RequireToken
    @PostMapping("/updateZxSkTtReqPlanItem")
    public ResponseEntity updateZxSkTtReqPlanItem(@RequestBody(required = false) ZxSkTtReqPlanItem zxSkTtReqPlanItem) {
        return zxSkTtReqPlanItemService.updateZxSkTtReqPlanItem(zxSkTtReqPlanItem);
    }

    @ApiOperation(value="删除物资总需用计划明细", notes="删除物资总需用计划明细")
    @ApiImplicitParam(name = "zxSkTtReqPlanItemList", value = "物资总需用计划明细List", dataType = "List<ZxSkTtReqPlanItem>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxSkTtReqPlanItem")
    public ResponseEntity batchDeleteUpdateZxSkTtReqPlanItem(@RequestBody(required = false) List<ZxSkTtReqPlanItem> zxSkTtReqPlanItemList) {
        return zxSkTtReqPlanItemService.batchDeleteUpdateZxSkTtReqPlanItem(zxSkTtReqPlanItemList);
    }

}
