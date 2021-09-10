package com.apih5.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import com.apih5.framework.annotation.RequireToken;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjLzehProduceTaskPlanItem;
import com.apih5.service.ZjLzehProduceTaskPlanItemService;

@RestController
public class ZjLzehProduceTaskPlanItemController {

    @Autowired(required = true)
    private ZjLzehProduceTaskPlanItemService zjLzehProduceTaskPlanItemService;

    @ApiOperation(value="查询生产目标任务计划明细", notes="查询生产目标任务计划明细")
    @ApiImplicitParam(name = "zjLzehProduceTaskPlanItem", value = "生产目标任务计划明细entity", dataType = "ZjLzehProduceTaskPlanItem")
    @RequireToken
    @PostMapping("/getZjLzehProduceTaskPlanItemList")
    public ResponseEntity getZjLzehProduceTaskPlanItemList(@RequestBody(required = false) ZjLzehProduceTaskPlanItem zjLzehProduceTaskPlanItem) {
        return zjLzehProduceTaskPlanItemService.getZjLzehProduceTaskPlanItemListByCondition(zjLzehProduceTaskPlanItem);
    }

    @ApiOperation(value="查询详情生产目标任务计划明细", notes="查询详情生产目标任务计划明细")
    @ApiImplicitParam(name = "zjLzehProduceTaskPlanItem", value = "生产目标任务计划明细entity", dataType = "ZjLzehProduceTaskPlanItem")
    @RequireToken
    @PostMapping("/getZjLzehProduceTaskPlanItemDetail")
    public ResponseEntity getZjLzehProduceTaskPlanItemDetail(@RequestBody(required = false) ZjLzehProduceTaskPlanItem zjLzehProduceTaskPlanItem) {
        return zjLzehProduceTaskPlanItemService.getZjLzehProduceTaskPlanItemDetail(zjLzehProduceTaskPlanItem);
    }

    @ApiOperation(value="新增生产目标任务计划明细", notes="新增生产目标任务计划明细")
    @ApiImplicitParam(name = "zjLzehProduceTaskPlanItem", value = "生产目标任务计划明细entity", dataType = "ZjLzehProduceTaskPlanItem")
    @RequireToken
    @PostMapping("/addZjLzehProduceTaskPlanItem")
    public ResponseEntity addZjLzehProduceTaskPlanItem(@RequestBody(required = false) ZjLzehProduceTaskPlanItem zjLzehProduceTaskPlanItem) {
        return zjLzehProduceTaskPlanItemService.saveZjLzehProduceTaskPlanItem(zjLzehProduceTaskPlanItem);
    }

    @ApiOperation(value="更新生产目标任务计划明细", notes="更新生产目标任务计划明细")
    @ApiImplicitParam(name = "zjLzehProduceTaskPlanItem", value = "生产目标任务计划明细entity", dataType = "ZjLzehProduceTaskPlanItem")
    @RequireToken
    @PostMapping("/updateZjLzehProduceTaskPlanItem")
    public ResponseEntity updateZjLzehProduceTaskPlanItem(@RequestBody(required = false) ZjLzehProduceTaskPlanItem zjLzehProduceTaskPlanItem) {
        return zjLzehProduceTaskPlanItemService.updateZjLzehProduceTaskPlanItem(zjLzehProduceTaskPlanItem);
    }

    @ApiOperation(value="删除生产目标任务计划明细", notes="删除生产目标任务计划明细")
    @ApiImplicitParam(name = "zjLzehProduceTaskPlanItemList", value = "生产目标任务计划明细List", dataType = "List<ZjLzehProduceTaskPlanItem>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZjLzehProduceTaskPlanItem")
    public ResponseEntity batchDeleteUpdateZjLzehProduceTaskPlanItem(@RequestBody(required = false) List<ZjLzehProduceTaskPlanItem> zjLzehProduceTaskPlanItemList) {
        return zjLzehProduceTaskPlanItemService.batchDeleteUpdateZjLzehProduceTaskPlanItem(zjLzehProduceTaskPlanItemList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    @ApiOperation(value="导入生产目标任务计划明细", notes="导入生产目标任务计划明细")
    @ApiImplicitParam(name = "zjLzehProduceTaskPlanItemList", value = "生产目标任务计划明细entity", dataType = "ZjLzehProduceTaskPlanItem")
    @RequireToken
    @PostMapping("/importProduceTaskPlanItem")
    public ResponseEntity importProduceTaskPlanItem(@RequestBody(required = false) ZjLzehProduceTaskPlanItem zjLzehProduceTaskPlanItem) {
        return zjLzehProduceTaskPlanItemService.importProduceTaskPlanItem(zjLzehProduceTaskPlanItem);
    }
    
    @ApiOperation(value="导入生产目标任务计划明细", notes="导入生产目标任务计划明细")
    @ApiImplicitParam(name = "zjLzehProduceTaskPlanItemList", value = "生产目标任务计划明细entity", dataType = "ZjLzehProduceTaskPlanItem")
//    @RequireToken
    @PostMapping("/exportProduceTaskPlanItem")
    public ResponseEntity exportProduceTaskPlanItem(@RequestBody(required = false) ZjLzehProduceTaskPlanItem zjLzehProduceTaskPlanItem, HttpServletResponse response) {
    	return zjLzehProduceTaskPlanItemService.exportProduceTaskPlanItem(zjLzehProduceTaskPlanItem, response);
    }
}
