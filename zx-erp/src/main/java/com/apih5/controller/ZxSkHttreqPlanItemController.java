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
import com.apih5.mybatis.pojo.ZxSkHttreqPlanItem;
import com.apih5.service.ZxSkHttreqPlanItemService;

@RestController
public class ZxSkHttreqPlanItemController {

    @Autowired(required = true)
    private ZxSkHttreqPlanItemService zxSkHttreqPlanItemService;

    @ApiOperation(value="查询总需用计划明细历史", notes="查询总需用计划明细历史")
    @ApiImplicitParam(name = "zxSkHttreqPlanItem", value = "总需用计划明细历史entity", dataType = "ZxSkHttreqPlanItem")
    @RequireToken
    @PostMapping("/getZxSkHttreqPlanItemList")
    public ResponseEntity getZxSkHttreqPlanItemList(@RequestBody(required = false) ZxSkHttreqPlanItem zxSkHttreqPlanItem) {
        return zxSkHttreqPlanItemService.getZxSkHttreqPlanItemListByCondition(zxSkHttreqPlanItem);
    }

    @ApiOperation(value="查询详情总需用计划明细历史", notes="查询详情总需用计划明细历史")
    @ApiImplicitParam(name = "zxSkHttreqPlanItem", value = "总需用计划明细历史entity", dataType = "ZxSkHttreqPlanItem")
    @RequireToken
    @PostMapping("/getZxSkHttreqPlanItemDetail")
    public ResponseEntity getZxSkHttreqPlanItemDetail(@RequestBody(required = false) ZxSkHttreqPlanItem zxSkHttreqPlanItem) {
        return zxSkHttreqPlanItemService.getZxSkHttreqPlanItemDetail(zxSkHttreqPlanItem);
    }

    @ApiOperation(value="新增总需用计划明细历史", notes="新增总需用计划明细历史")
    @ApiImplicitParam(name = "zxSkHttreqPlanItem", value = "总需用计划明细历史entity", dataType = "ZxSkHttreqPlanItem")
    @RequireToken
    @PostMapping("/addZxSkHttreqPlanItem")
    public ResponseEntity addZxSkHttreqPlanItem(@RequestBody(required = false) ZxSkHttreqPlanItem zxSkHttreqPlanItem) {
        return zxSkHttreqPlanItemService.saveZxSkHttreqPlanItem(zxSkHttreqPlanItem);
    }

    @ApiOperation(value="更新总需用计划明细历史", notes="更新总需用计划明细历史")
    @ApiImplicitParam(name = "zxSkHttreqPlanItem", value = "总需用计划明细历史entity", dataType = "ZxSkHttreqPlanItem")
    @RequireToken
    @PostMapping("/updateZxSkHttreqPlanItem")
    public ResponseEntity updateZxSkHttreqPlanItem(@RequestBody(required = false) ZxSkHttreqPlanItem zxSkHttreqPlanItem) {
        return zxSkHttreqPlanItemService.updateZxSkHttreqPlanItem(zxSkHttreqPlanItem);
    }

    @ApiOperation(value="删除总需用计划明细历史", notes="删除总需用计划明细历史")
    @ApiImplicitParam(name = "zxSkHttreqPlanItemList", value = "总需用计划明细历史List", dataType = "List<ZxSkHttreqPlanItem>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxSkHttreqPlanItem")
    public ResponseEntity batchDeleteUpdateZxSkHttreqPlanItem(@RequestBody(required = false) List<ZxSkHttreqPlanItem> zxSkHttreqPlanItemList) {
        return zxSkHttreqPlanItemService.batchDeleteUpdateZxSkHttreqPlanItem(zxSkHttreqPlanItemList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
