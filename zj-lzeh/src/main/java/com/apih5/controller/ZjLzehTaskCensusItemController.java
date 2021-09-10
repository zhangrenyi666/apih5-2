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
import com.apih5.mybatis.pojo.ZjLzehTaskCensusItem;
import com.apih5.service.ZjLzehTaskCensusItemService;

@RestController
public class ZjLzehTaskCensusItemController {

    @Autowired(required = true)
    private ZjLzehTaskCensusItemService zjLzehTaskCensusItemService;

    @ApiOperation(value="查询任务统计明细", notes="查询任务统计明细")
    @ApiImplicitParam(name = "zjLzehTaskCensusItem", value = "任务统计明细entity", dataType = "ZjLzehTaskCensusItem")
    @RequireToken
    @PostMapping("/getZjLzehTaskCensusItemList")
    public ResponseEntity getZjLzehTaskCensusItemList(@RequestBody(required = false) ZjLzehTaskCensusItem zjLzehTaskCensusItem) {
        return zjLzehTaskCensusItemService.getZjLzehTaskCensusItemListByCondition(zjLzehTaskCensusItem);
    }

    @ApiOperation(value="查询详情任务统计明细", notes="查询详情任务统计明细")
    @ApiImplicitParam(name = "zjLzehTaskCensusItem", value = "任务统计明细entity", dataType = "ZjLzehTaskCensusItem")
    @RequireToken
    @PostMapping("/getZjLzehTaskCensusItemDetail")
    public ResponseEntity getZjLzehTaskCensusItemDetail(@RequestBody(required = false) ZjLzehTaskCensusItem zjLzehTaskCensusItem) {
        return zjLzehTaskCensusItemService.getZjLzehTaskCensusItemDetail(zjLzehTaskCensusItem);
    }

    @ApiOperation(value="新增任务统计明细", notes="新增任务统计明细")
    @ApiImplicitParam(name = "zjLzehTaskCensusItem", value = "任务统计明细entity", dataType = "ZjLzehTaskCensusItem")
    @RequireToken
    @PostMapping("/addZjLzehTaskCensusItem")
    public ResponseEntity addZjLzehTaskCensusItem(@RequestBody(required = false) ZjLzehTaskCensusItem zjLzehTaskCensusItem) {
        return zjLzehTaskCensusItemService.saveZjLzehTaskCensusItem(zjLzehTaskCensusItem);
    }

    @ApiOperation(value="更新任务统计明细", notes="更新任务统计明细")
    @ApiImplicitParam(name = "zjLzehTaskCensusItem", value = "任务统计明细entity", dataType = "ZjLzehTaskCensusItem")
    @RequireToken
    @PostMapping("/updateZjLzehTaskCensusItem")
    public ResponseEntity updateZjLzehTaskCensusItem(@RequestBody(required = false) ZjLzehTaskCensusItem zjLzehTaskCensusItem) {
        return zjLzehTaskCensusItemService.updateZjLzehTaskCensusItem(zjLzehTaskCensusItem);
    }

    @ApiOperation(value="删除任务统计明细", notes="删除任务统计明细")
    @ApiImplicitParam(name = "zjLzehTaskCensusItemList", value = "任务统计明细List", dataType = "List<ZjLzehTaskCensusItem>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZjLzehTaskCensusItem")
    public ResponseEntity batchDeleteUpdateZjLzehTaskCensusItem(@RequestBody(required = false) List<ZjLzehTaskCensusItem> zjLzehTaskCensusItemList) {
        return zjLzehTaskCensusItemService.batchDeleteUpdateZjLzehTaskCensusItem(zjLzehTaskCensusItemList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

    @ApiOperation(value="查询任务统计明细图表", notes="查询任务统计明细图表")
    @ApiImplicitParam(name = "zjLzehTaskCensusItem", value = "任务统计明细entity", dataType = "ZjLzehTaskCensusItem")
    @RequireToken
    @PostMapping("/getZjLzehTaskCensusItemChartList")
    public ResponseEntity getZjLzehTaskCensusItemChartList(@RequestBody(required = false) ZjLzehTaskCensusItem zjLzehTaskCensusItem) {
        return zjLzehTaskCensusItemService.getZjLzehTaskCensusItemChartList(zjLzehTaskCensusItem);
    }

    @ApiOperation(value="查询任务人员统计表", notes="查询任务人员统计表")
    @ApiImplicitParam(name = "zjLzehTaskCensusItem", value = "任务统计明细entity", dataType = "ZjLzehTaskCensusItem")
    @RequireToken
    @PostMapping("/getTaskCensusItemChartByCenMonth")
    public ResponseEntity getTaskCensusItemChartByCenMonth(@RequestBody(required = false) ZjLzehTaskCensusItem zjLzehTaskCensusItem) {
        return zjLzehTaskCensusItemService.getTaskCensusItemChartByCenMonth(zjLzehTaskCensusItem);
    }


}
