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
import com.apih5.mybatis.pojo.ZxEqUseEquipIn;
import com.apih5.service.ZxEqUseEquipInService;

@RestController
public class ZxEqUseEquipInController {

    @Autowired(required = true)
    private ZxEqUseEquipInService zxEqUseEquipInService;

    @ApiOperation(value="查询项目设备管理-外部设备台账入场信息登记", notes="查询项目设备管理-外部设备台账入场信息登记")
    @ApiImplicitParam(name = "zxEqUseEquipIn", value = "项目设备管理-外部设备台账入场信息登记entity", dataType = "ZxEqUseEquipIn")
    @RequireToken
    @PostMapping("/getZxEqUseEquipInList")
    public ResponseEntity getZxEqUseEquipInList(@RequestBody(required = false) ZxEqUseEquipIn zxEqUseEquipIn) {
        return zxEqUseEquipInService.getZxEqUseEquipInListByCondition(zxEqUseEquipIn);
    }

    @ApiOperation(value="查询详情项目设备管理-外部设备台账入场信息登记", notes="查询详情项目设备管理-外部设备台账入场信息登记")
    @ApiImplicitParam(name = "zxEqUseEquipIn", value = "项目设备管理-外部设备台账入场信息登记entity", dataType = "ZxEqUseEquipIn")
    @RequireToken
    @PostMapping("/getZxEqUseEquipInDetails")
    public ResponseEntity getZxEqUseEquipInDetails(@RequestBody(required = false) ZxEqUseEquipIn zxEqUseEquipIn) {
        return zxEqUseEquipInService.getZxEqUseEquipInDetails(zxEqUseEquipIn);
    }

    @ApiOperation(value="新增项目设备管理-外部设备台账入场信息登记", notes="新增项目设备管理-外部设备台账入场信息登记")
    @ApiImplicitParam(name = "zxEqUseEquipIn", value = "项目设备管理-外部设备台账入场信息登记entity", dataType = "ZxEqUseEquipIn")
    @RequireToken
    @PostMapping("/addZxEqUseEquipIn")
    public ResponseEntity addZxEqUseEquipIn(@RequestBody(required = false) ZxEqUseEquipIn zxEqUseEquipIn) {
        return zxEqUseEquipInService.saveZxEqUseEquipIn(zxEqUseEquipIn);
    }

    @ApiOperation(value="更新项目设备管理-外部设备台账入场信息登记", notes="更新项目设备管理-外部设备台账入场信息登记")
    @ApiImplicitParam(name = "zxEqUseEquipIn", value = "项目设备管理-外部设备台账入场信息登记entity", dataType = "ZxEqUseEquipIn")
    @RequireToken
    @PostMapping("/updateZxEqUseEquipIn")
    public ResponseEntity updateZxEqUseEquipIn(@RequestBody(required = false) ZxEqUseEquipIn zxEqUseEquipIn) {
        return zxEqUseEquipInService.updateZxEqUseEquipIn(zxEqUseEquipIn);
    }

    @ApiOperation(value="删除项目设备管理-外部设备台账入场信息登记", notes="删除项目设备管理-外部设备台账入场信息登记")
    @ApiImplicitParam(name = "zxEqUseEquipInList", value = "项目设备管理-外部设备台账入场信息登记List", dataType = "List<ZxEqUseEquipIn>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxEqUseEquipIn")
    public ResponseEntity batchDeleteUpdateZxEqUseEquipIn(@RequestBody(required = false) List<ZxEqUseEquipIn> zxEqUseEquipInList) {
        return zxEqUseEquipInService.batchDeleteUpdateZxEqUseEquipIn(zxEqUseEquipInList);
    }

}
