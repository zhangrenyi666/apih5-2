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
import com.apih5.mybatis.pojo.ZjLzehTeamScoreItem;
import com.apih5.service.ZjLzehTeamScoreItemService;

@RestController
public class ZjLzehTeamScoreItemController {

    @Autowired(required = true)
    private ZjLzehTeamScoreItemService zjLzehTeamScoreItemService;

    @ApiOperation(value="查询班组评分明细", notes="查询班组评分明细")
    @ApiImplicitParam(name = "zjLzehTeamScoreItem", value = "班组评分明细entity", dataType = "ZjLzehTeamScoreItem")
    @RequireToken
    @PostMapping("/getZjLzehTeamScoreItemList")
    public ResponseEntity getZjLzehTeamScoreItemList(@RequestBody(required = false) ZjLzehTeamScoreItem zjLzehTeamScoreItem) {
        return zjLzehTeamScoreItemService.getZjLzehTeamScoreItemListByCondition(zjLzehTeamScoreItem);
    }

    @ApiOperation(value="查询详情班组评分明细", notes="查询详情班组评分明细")
    @ApiImplicitParam(name = "zjLzehTeamScoreItem", value = "班组评分明细entity", dataType = "ZjLzehTeamScoreItem")
    @RequireToken
    @PostMapping("/getZjLzehTeamScoreItemDetail")
    public ResponseEntity getZjLzehTeamScoreItemDetail(@RequestBody(required = false) ZjLzehTeamScoreItem zjLzehTeamScoreItem) {
        return zjLzehTeamScoreItemService.getZjLzehTeamScoreItemDetail(zjLzehTeamScoreItem);
    }

    @ApiOperation(value="新增班组评分明细", notes="新增班组评分明细")
    @ApiImplicitParam(name = "zjLzehTeamScoreItem", value = "班组评分明细entity", dataType = "ZjLzehTeamScoreItem")
    @RequireToken
    @PostMapping("/addZjLzehTeamScoreItem")
    public ResponseEntity addZjLzehTeamScoreItem(@RequestBody(required = false) ZjLzehTeamScoreItem zjLzehTeamScoreItem) {
        return zjLzehTeamScoreItemService.saveZjLzehTeamScoreItem(zjLzehTeamScoreItem);
    }

    @ApiOperation(value="更新班组评分明细", notes="更新班组评分明细")
    @ApiImplicitParam(name = "zjLzehTeamScoreItem", value = "班组评分明细entity", dataType = "ZjLzehTeamScoreItem")
    @RequireToken
    @PostMapping("/updateZjLzehTeamScoreItem")
    public ResponseEntity updateZjLzehTeamScoreItem(@RequestBody(required = false) ZjLzehTeamScoreItem zjLzehTeamScoreItem) {
        return zjLzehTeamScoreItemService.updateZjLzehTeamScoreItem(zjLzehTeamScoreItem);
    }

    @ApiOperation(value="删除班组评分明细", notes="删除班组评分明细")
    @ApiImplicitParam(name = "zjLzehTeamScoreItemList", value = "班组评分明细List", dataType = "List<ZjLzehTeamScoreItem>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZjLzehTeamScoreItem")
    public ResponseEntity batchDeleteUpdateZjLzehTeamScoreItem(@RequestBody(required = false) List<ZjLzehTeamScoreItem> zjLzehTeamScoreItemList) {
        return zjLzehTeamScoreItemService.batchDeleteUpdateZjLzehTeamScoreItem(zjLzehTeamScoreItemList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    @ApiOperation(value="查询班组评分明细折线图", notes="查询班组评分明细折线图")
    @ApiImplicitParam(name = "zjLzehTeamScoreItem", value = "班组评分明细entity", dataType = "ZjLzehTeamScoreItem")
    @RequireToken
    @PostMapping("/getZjLzehTeamScoreItemChartList")
    public ResponseEntity getZjLzehTeamScoreItemChartList(@RequestBody(required = false) ZjLzehTeamScoreItem zjLzehTeamScoreItem) {
        return zjLzehTeamScoreItemService.getChartList(zjLzehTeamScoreItem);
    }

    @ApiOperation(value="根据主表ID查询班组评分明细", notes="根据主表ID查询班组评分明细")
    @ApiImplicitParam(name = "zjLzehTeamScoreItem", value = "班组评分明细entity", dataType = "ZjLzehTeamScoreItem")
    @RequireToken
    @PostMapping("/getZjLzehTeamScoreItemByTeamScoreId")
    public ResponseEntity getZjLzehTeamScoreItemByTeamScoreId(@RequestBody(required = false) ZjLzehTeamScoreItem zjLzehTeamScoreItem) {
        return zjLzehTeamScoreItemService.getZjLzehTeamScoreItemByTeamScoreId(zjLzehTeamScoreItem);
    }

    @ApiOperation(value="查询月班组评分明细柱状图", notes="查询月班组评分明细柱状图")
    @ApiImplicitParam(name = "zjLzehTeamScoreItem", value = "班组评分明细entity", dataType = "ZjLzehTeamScoreItem")
    @RequireToken
    @PostMapping("/getZjLzehTeamScoreItemgetChartByMonth")
    public ResponseEntity getZjLzehTeamScoreItemgetChartByMonth(@RequestBody(required = false) ZjLzehTeamScoreItem zjLzehTeamScoreItem) {
        return zjLzehTeamScoreItemService.getChartByMonth(zjLzehTeamScoreItem);
    }



}
