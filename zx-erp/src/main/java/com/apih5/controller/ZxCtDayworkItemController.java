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
import com.apih5.mybatis.pojo.ZxCtDayworkItem;
import com.apih5.service.ZxCtDayworkItemService;

@RestController
public class ZxCtDayworkItemController {

    @Autowired(required = true)
    private ZxCtDayworkItemService zxCtDayworkItemService;

    @ApiOperation(value="查询业主合同管理-业主合同台账-计量暂定金、计日工计量子表(原表iest_DayworkItem", notes="查询业主合同管理-业主合同台账-计量暂定金、计日工计量子表(原表iest_DayworkItem")
    @ApiImplicitParam(name = "zxCtDayworkItem", value = "业主合同管理-业主合同台账-计量暂定金、计日工计量子表(原表iest_DayworkItementity", dataType = "ZxCtDayworkItem")
    @RequireToken
    @PostMapping("/getZxCtDayworkItemList")
    public ResponseEntity getZxCtDayworkItemList(@RequestBody(required = false) ZxCtDayworkItem zxCtDayworkItem) {
        return zxCtDayworkItemService.getZxCtDayworkItemListByCondition(zxCtDayworkItem);
    }

    @ApiOperation(value="查询详情业主合同管理-业主合同台账-计量暂定金、计日工计量子表(原表iest_DayworkItem", notes="查询详情业主合同管理-业主合同台账-计量暂定金、计日工计量子表(原表iest_DayworkItem")
    @ApiImplicitParam(name = "zxCtDayworkItem", value = "业主合同管理-业主合同台账-计量暂定金、计日工计量子表(原表iest_DayworkItementity", dataType = "ZxCtDayworkItem")
    @RequireToken
    @PostMapping("/getZxCtDayworkItemDetail")
    public ResponseEntity getZxCtDayworkItemDetail(@RequestBody(required = false) ZxCtDayworkItem zxCtDayworkItem) {
        return zxCtDayworkItemService.getZxCtDayworkItemDetail(zxCtDayworkItem);
    }

    @ApiOperation(value="新增业主合同管理-业主合同台账-计量暂定金、计日工计量子表(原表iest_DayworkItem", notes="新增业主合同管理-业主合同台账-计量暂定金、计日工计量子表(原表iest_DayworkItem")
    @ApiImplicitParam(name = "zxCtDayworkItem", value = "业主合同管理-业主合同台账-计量暂定金、计日工计量子表(原表iest_DayworkItementity", dataType = "ZxCtDayworkItem")
    @RequireToken
    @PostMapping("/addZxCtDayworkItem")
    public ResponseEntity addZxCtDayworkItem(@RequestBody(required = false) ZxCtDayworkItem zxCtDayworkItem) {
        return zxCtDayworkItemService.saveZxCtDayworkItem(zxCtDayworkItem);
    }

    @ApiOperation(value="更新业主合同管理-业主合同台账-计量暂定金、计日工计量子表(原表iest_DayworkItem", notes="更新业主合同管理-业主合同台账-计量暂定金、计日工计量子表(原表iest_DayworkItem")
    @ApiImplicitParam(name = "zxCtDayworkItem", value = "业主合同管理-业主合同台账-计量暂定金、计日工计量子表(原表iest_DayworkItementity", dataType = "ZxCtDayworkItem")
    @RequireToken
    @PostMapping("/updateZxCtDayworkItem")
    public ResponseEntity updateZxCtDayworkItem(@RequestBody(required = false) ZxCtDayworkItem zxCtDayworkItem) {
        return zxCtDayworkItemService.updateZxCtDayworkItem(zxCtDayworkItem);
    }

    @ApiOperation(value="删除业主合同管理-业主合同台账-计量暂定金、计日工计量子表(原表iest_DayworkItem", notes="删除业主合同管理-业主合同台账-计量暂定金、计日工计量子表(原表iest_DayworkItem")
    @ApiImplicitParam(name = "zxCtDayworkItemList", value = "业主合同管理-业主合同台账-计量暂定金、计日工计量子表(原表iest_DayworkItemList", dataType = "List<ZxCtDayworkItem>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxCtDayworkItem")
    public ResponseEntity batchDeleteUpdateZxCtDayworkItem(@RequestBody(required = false) List<ZxCtDayworkItem> zxCtDayworkItemList) {
        return zxCtDayworkItemService.batchDeleteUpdateZxCtDayworkItem(zxCtDayworkItemList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    
    @ApiOperation(value="删除业主合同管理-业主合同台账-计量暂定金、计日工计量子表(原表iest_DayworkItem", notes="删除业主合同管理-业主合同台账-计量暂定金、计日工计量子表(原表iest_DayworkItem")
    @ApiImplicitParam(name = "zxCtDayworkItemList", value = "业主合同管理-业主合同台账-计量暂定金、计日工计量子表(原表iest_DayworkItemList", dataType = "List<ZxCtDayworkItem>")
    @RequireToken
    @PostMapping("/deleteAllZxCtDayworkItem")
    public ResponseEntity deleteAllZxCtDayworkItem(@RequestBody(required = false) ZxCtDayworkItem zxCtDayworkItem) {
        return zxCtDayworkItemService.deleteAllZxCtDayworkItem(zxCtDayworkItem);
    }
}
