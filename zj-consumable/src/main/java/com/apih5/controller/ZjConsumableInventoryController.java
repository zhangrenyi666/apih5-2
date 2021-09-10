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
import com.apih5.mybatis.pojo.ZjConsumableInventory;
import com.apih5.service.ZjConsumableInventoryService;

@RestController
public class ZjConsumableInventoryController {

    @Autowired(required = true)
    private ZjConsumableInventoryService zjConsumableInventoryService;

    @ApiOperation(value="查询库存", notes="查询库存")
    @ApiImplicitParam(name = "zjConsumableInventory", value = "库存entity", dataType = "ZjConsumableInventory")
    @RequireToken
    @PostMapping("/getZjConsumableInventoryList")
    public ResponseEntity getZjConsumableInventoryList(@RequestBody(required = false) ZjConsumableInventory zjConsumableInventory) {
        return zjConsumableInventoryService.getZjConsumableInventoryListByCondition(zjConsumableInventory);
    }

    @ApiOperation(value="查询详情库存", notes="查询详情库存")
    @ApiImplicitParam(name = "zjConsumableInventory", value = "库存entity", dataType = "ZjConsumableInventory")
    @RequireToken
    @PostMapping("/getZjConsumableInventoryDetails")
    public ResponseEntity getZjConsumableInventoryDetails(@RequestBody(required = false) ZjConsumableInventory zjConsumableInventory) {
        return zjConsumableInventoryService.getZjConsumableInventoryDetails(zjConsumableInventory);
    }

    @ApiOperation(value="新增库存", notes="新增库存")
    @ApiImplicitParam(name = "zjConsumableInventory", value = "库存entity", dataType = "ZjConsumableInventory")
    @RequireToken
    @PostMapping("/addZjConsumableInventory")
    public ResponseEntity addZjConsumableInventory(@RequestBody(required = false) ZjConsumableInventory zjConsumableInventory) {
        return zjConsumableInventoryService.saveZjConsumableInventory(zjConsumableInventory);
    }

    @ApiOperation(value="更新库存", notes="更新库存")
    @ApiImplicitParam(name = "zjConsumableInventory", value = "库存entity", dataType = "ZjConsumableInventory")
    @RequireToken
    @PostMapping("/updateZjConsumableInventory")
    public ResponseEntity updateZjConsumableInventory(@RequestBody(required = false) ZjConsumableInventory zjConsumableInventory) {
        return zjConsumableInventoryService.updateZjConsumableInventory(zjConsumableInventory);
    }

    @ApiOperation(value="删除库存", notes="删除库存")
    @ApiImplicitParam(name = "zjConsumableInventoryList", value = "库存List", dataType = "List<ZjConsumableInventory>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZjConsumableInventory")
    public ResponseEntity batchDeleteUpdateZjConsumableInventory(@RequestBody(required = false) List<ZjConsumableInventory> zjConsumableInventoryList) {
        return zjConsumableInventoryService.batchDeleteUpdateZjConsumableInventory(zjConsumableInventoryList);
    }

}
