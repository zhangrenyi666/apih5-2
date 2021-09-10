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
import com.apih5.mybatis.pojo.ZjConsumableSet;
import com.apih5.service.ZjConsumableSetService;

@RestController
public class ZjConsumableSetController {

    @Autowired(required = true)
    private ZjConsumableSetService zjConsumableSetService;

    @ApiOperation(value="查询耗材设置", notes="查询耗材设置")
    @ApiImplicitParam(name = "zjConsumableSet", value = "耗材设置entity", dataType = "ZjConsumableSet")
    @RequireToken
    @PostMapping("/getZjConsumableSetList")
    public ResponseEntity getZjConsumableSetList(@RequestBody(required = false) ZjConsumableSet zjConsumableSet) {
        return zjConsumableSetService.getZjConsumableSetListByCondition(zjConsumableSet);
    }

    @ApiOperation(value="查询详情耗材设置", notes="查询详情耗材设置")
    @ApiImplicitParam(name = "zjConsumableSet", value = "耗材设置entity", dataType = "ZjConsumableSet")
    @RequireToken
    @PostMapping("/getZjConsumableSetDetails")
    public ResponseEntity getZjConsumableSetDetails(@RequestBody(required = false) ZjConsumableSet zjConsumableSet) {
        return zjConsumableSetService.getZjConsumableSetDetails(zjConsumableSet);
    }

    @ApiOperation(value="新增耗材设置", notes="新增耗材设置")
    @ApiImplicitParam(name = "zjConsumableSet", value = "耗材设置entity", dataType = "ZjConsumableSet")
    @RequireToken
    @PostMapping("/addZjConsumableSet")
    public ResponseEntity addZjConsumableSet(@RequestBody(required = false) ZjConsumableSet zjConsumableSet) {
        return zjConsumableSetService.saveZjConsumableSet(zjConsumableSet);
    }

    @ApiOperation(value="更新耗材设置", notes="更新耗材设置")
    @ApiImplicitParam(name = "zjConsumableSet", value = "耗材设置entity", dataType = "ZjConsumableSet")
    @RequireToken
    @PostMapping("/updateZjConsumableSet")
    public ResponseEntity updateZjConsumableSet(@RequestBody(required = false) ZjConsumableSet zjConsumableSet) {
        return zjConsumableSetService.updateZjConsumableSet(zjConsumableSet);
    }

    @ApiOperation(value="删除耗材设置", notes="删除耗材设置")
    @ApiImplicitParam(name = "zjConsumableSetList", value = "耗材设置List", dataType = "List<ZjConsumableSet>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZjConsumableSet")
    public ResponseEntity batchDeleteUpdateZjConsumableSet(@RequestBody(required = false) List<ZjConsumableSet> zjConsumableSetList) {
        return zjConsumableSetService.batchDeleteUpdateZjConsumableSet(zjConsumableSetList);
    }

}
