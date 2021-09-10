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
import com.apih5.mybatis.pojo.ZxEqCooEquip;
import com.apih5.service.ZxEqCooEquipService;

@RestController
public class ZxEqCooEquipController {

    @Autowired(required = true)
    private ZxEqCooEquipService zxEqCooEquipService;

    @ApiOperation(value="查询项目设备管理-协作单位自带设备入场登记", notes="查询项目设备管理-协作单位自带设备入场登记")
    @ApiImplicitParam(name = "zxEqCooEquip", value = "项目设备管理-协作单位自带设备入场登记entity", dataType = "ZxEqCooEquip")
    @RequireToken
    @PostMapping("/getZxEqCooEquipList")
    public ResponseEntity getZxEqCooEquipList(@RequestBody(required = false) ZxEqCooEquip zxEqCooEquip) {
        return zxEqCooEquipService.getZxEqCooEquipListByCondition(zxEqCooEquip);
    }

    @ApiOperation(value="查询详情项目设备管理-协作单位自带设备入场登记", notes="查询详情项目设备管理-协作单位自带设备入场登记")
    @ApiImplicitParam(name = "zxEqCooEquip", value = "项目设备管理-协作单位自带设备入场登记entity", dataType = "ZxEqCooEquip")
    @RequireToken
    @PostMapping("/getZxEqCooEquipDetails")
    public ResponseEntity getZxEqCooEquipDetails(@RequestBody(required = false) ZxEqCooEquip zxEqCooEquip) {
        return zxEqCooEquipService.getZxEqCooEquipDetails(zxEqCooEquip);
    }

    @ApiOperation(value="新增项目设备管理-协作单位自带设备入场登记", notes="新增项目设备管理-协作单位自带设备入场登记")
    @ApiImplicitParam(name = "zxEqCooEquip", value = "项目设备管理-协作单位自带设备入场登记entity", dataType = "ZxEqCooEquip")
    @RequireToken
    @PostMapping("/addZxEqCooEquip")
    public ResponseEntity addZxEqCooEquip(@RequestBody(required = false) ZxEqCooEquip zxEqCooEquip) {
        return zxEqCooEquipService.saveZxEqCooEquip(zxEqCooEquip);
    }

    @ApiOperation(value="更新项目设备管理-协作单位自带设备入场登记", notes="更新项目设备管理-协作单位自带设备入场登记")
    @ApiImplicitParam(name = "zxEqCooEquip", value = "项目设备管理-协作单位自带设备入场登记entity", dataType = "ZxEqCooEquip")
    @RequireToken
    @PostMapping("/updateZxEqCooEquip")
    public ResponseEntity updateZxEqCooEquip(@RequestBody(required = false) ZxEqCooEquip zxEqCooEquip) {
        return zxEqCooEquipService.updateZxEqCooEquip(zxEqCooEquip);
    }

    @ApiOperation(value="删除项目设备管理-协作单位自带设备入场登记", notes="删除项目设备管理-协作单位自带设备入场登记")
    @ApiImplicitParam(name = "zxEqCooEquipList", value = "项目设备管理-协作单位自带设备入场登记List", dataType = "List<ZxEqCooEquip>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxEqCooEquip")
    public ResponseEntity batchDeleteUpdateZxEqCooEquip(@RequestBody(required = false) List<ZxEqCooEquip> zxEqCooEquipList) {
        return zxEqCooEquipService.batchDeleteUpdateZxEqCooEquip(zxEqCooEquipList);
    }
    
    @ApiOperation(value="导入项目设备管理-协作单位自带设备入场登记", notes="导入项目设备管理-协作单位自带设备入场登记")
    @ApiImplicitParam(name = "zxEqCooEquip", value = "项目设备管理-协作单位自带设备入场登记entity", dataType = "ZxEqCooEquip")
    @RequireToken
    @PostMapping("/importZxEqCooEquipList")
    public ResponseEntity importZxEqCooEquipList(@RequestBody(required = false) ZxEqCooEquip zxEqCooEquip) {
        return zxEqCooEquipService.importZxEqCooEquipList(zxEqCooEquip);
    }

}
