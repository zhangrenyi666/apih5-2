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
import com.apih5.mybatis.pojo.ZjLzehManageTaskPlanItem;
import com.apih5.service.ZjLzehManageTaskPlanItemService;

@RestController
public class ZjLzehManageTaskPlanItemController {

    @Autowired(required = true)
    private ZjLzehManageTaskPlanItemService zjLzehManageTaskPlanItemService;

    @ApiOperation(value="查询经营目标任务计划明细", notes="查询经营目标任务计划明细")
    @ApiImplicitParam(name = "zjLzehManageTaskPlanItem", value = "经营目标任务计划明细entity", dataType = "ZjLzehManageTaskPlanItem")
    @RequireToken
    @PostMapping("/getZjLzehManageTaskPlanItemList")
    public ResponseEntity getZjLzehManageTaskPlanItemList(@RequestBody(required = false) ZjLzehManageTaskPlanItem zjLzehManageTaskPlanItem) {
        return zjLzehManageTaskPlanItemService.getZjLzehManageTaskPlanItemListByCondition(zjLzehManageTaskPlanItem);
    }

    @ApiOperation(value="查询详情经营目标任务计划明细", notes="查询详情经营目标任务计划明细")
    @ApiImplicitParam(name = "zjLzehManageTaskPlanItem", value = "经营目标任务计划明细entity", dataType = "ZjLzehManageTaskPlanItem")
    @RequireToken
    @PostMapping("/getZjLzehManageTaskPlanItemDetail")
    public ResponseEntity getZjLzehManageTaskPlanItemDetail(@RequestBody(required = false) ZjLzehManageTaskPlanItem zjLzehManageTaskPlanItem) {
        return zjLzehManageTaskPlanItemService.getZjLzehManageTaskPlanItemDetail(zjLzehManageTaskPlanItem);
    }

    @ApiOperation(value="新增经营目标任务计划明细", notes="新增经营目标任务计划明细")
    @ApiImplicitParam(name = "zjLzehManageTaskPlanItem", value = "经营目标任务计划明细entity", dataType = "ZjLzehManageTaskPlanItem")
    @RequireToken
    @PostMapping("/addZjLzehManageTaskPlanItem")
    public ResponseEntity addZjLzehManageTaskPlanItem(@RequestBody(required = false) ZjLzehManageTaskPlanItem zjLzehManageTaskPlanItem) {
        return zjLzehManageTaskPlanItemService.saveZjLzehManageTaskPlanItem(zjLzehManageTaskPlanItem);
    }

    @ApiOperation(value="更新经营目标任务计划明细", notes="更新经营目标任务计划明细")
    @ApiImplicitParam(name = "zjLzehManageTaskPlanItem", value = "经营目标任务计划明细entity", dataType = "ZjLzehManageTaskPlanItem")
    @RequireToken
    @PostMapping("/updateZjLzehManageTaskPlanItem")
    public ResponseEntity updateZjLzehManageTaskPlanItem(@RequestBody(required = false) ZjLzehManageTaskPlanItem zjLzehManageTaskPlanItem) {
        return zjLzehManageTaskPlanItemService.updateZjLzehManageTaskPlanItem(zjLzehManageTaskPlanItem);
    }

    @ApiOperation(value="删除经营目标任务计划明细", notes="删除经营目标任务计划明细")
    @ApiImplicitParam(name = "zjLzehManageTaskPlanItemList", value = "经营目标任务计划明细List", dataType = "List<ZjLzehManageTaskPlanItem>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZjLzehManageTaskPlanItem")
    public ResponseEntity batchDeleteUpdateZjLzehManageTaskPlanItem(@RequestBody(required = false) List<ZjLzehManageTaskPlanItem> zjLzehManageTaskPlanItemList) {
        return zjLzehManageTaskPlanItemService.batchDeleteUpdateZjLzehManageTaskPlanItem(zjLzehManageTaskPlanItemList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

    @ApiOperation(value="导入经营目标任务计划明细", notes="导入经营目标任务计划明细")
    @ApiImplicitParam(name = "zjLzehManageTaskPlanItem", value = "经营目标任务计划明细entity", dataType = "ZjLzehManageTaskPlanItem")
    @RequireToken
    @PostMapping("/importManageTaskPlanItem")
    public ResponseEntity importManageTaskPlanItem(@RequestBody(required = false) ZjLzehManageTaskPlanItem zjLzehManageTaskPlanItem) {
        return zjLzehManageTaskPlanItemService.importManageTaskPlanItem(zjLzehManageTaskPlanItem);
    }
    
    @ApiOperation(value="导出经营目标任务计划明细", notes="导出经营目标任务计划明细")
    @ApiImplicitParam(name = "zjLzehManageTaskPlanItem", value = "经营目标任务计划明细entity", dataType = "ZjLzehManageTaskPlanItem")
//    @RequireToken
    @PostMapping("/exportManageTaskPlanItem")
    public ResponseEntity exportManageTaskPlanItem(@RequestBody(required = false) ZjLzehManageTaskPlanItem zjLzehManageTaskPlanItem, HttpServletResponse response) {
    	return zjLzehManageTaskPlanItemService.exportManageTaskPlanItem(zjLzehManageTaskPlanItem, response);
    }

}
