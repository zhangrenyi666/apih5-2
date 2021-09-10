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
import com.apih5.mybatis.pojo.ZxEqEquipIntegratedQuery;
import com.apih5.mybatis.pojo.ZxEqExternalEquipmentUsage;
import com.apih5.service.ZxEqExternalEquipmentUsageService;

@RestController
public class ZxEqExternalEquipmentUsageController {

    @Autowired(required = true)
    private ZxEqExternalEquipmentUsageService zxEqExternalEquipmentUsageService;

    @ApiOperation(value="查询租用外部设备使用情况", notes="查询租用外部设备使用情况")
    @ApiImplicitParam(name = "zxEqExternalEquipmentUsage", value = "租用外部设备使用情况entity", dataType = "ZxEqExternalEquipmentUsage")
    @RequireToken
    @PostMapping("/getZxEqExternalEquipmentUsageList")
    public ResponseEntity getZxEqExternalEquipmentUsageList(@RequestBody(required = false) ZxEqExternalEquipmentUsage zxEqExternalEquipmentUsage) {
        return zxEqExternalEquipmentUsageService.getZxEqExternalEquipmentUsageListByCondition(zxEqExternalEquipmentUsage);
    }

    @ApiOperation(value="查询详情租用外部设备使用情况", notes="查询详情租用外部设备使用情况")
    @ApiImplicitParam(name = "zxEqExternalEquipmentUsage", value = "租用外部设备使用情况entity", dataType = "ZxEqExternalEquipmentUsage")
    @RequireToken
    @PostMapping("/getZxEqExternalEquipmentUsageDetail")
    public ResponseEntity getZxEqExternalEquipmentUsageDetail(@RequestBody(required = false) ZxEqExternalEquipmentUsage zxEqExternalEquipmentUsage) {
        return zxEqExternalEquipmentUsageService.getZxEqExternalEquipmentUsageDetail(zxEqExternalEquipmentUsage);
    }

    @ApiOperation(value="新增租用外部设备使用情况", notes="新增租用外部设备使用情况")
    @ApiImplicitParam(name = "zxEqExternalEquipmentUsage", value = "租用外部设备使用情况entity", dataType = "ZxEqExternalEquipmentUsage")
    @RequireToken
    @PostMapping("/addZxEqExternalEquipmentUsage")
    public ResponseEntity addZxEqExternalEquipmentUsage(@RequestBody(required = false) ZxEqExternalEquipmentUsage zxEqExternalEquipmentUsage) {
        return zxEqExternalEquipmentUsageService.saveZxEqExternalEquipmentUsage(zxEqExternalEquipmentUsage);
    }

    @ApiOperation(value="更新租用外部设备使用情况", notes="更新租用外部设备使用情况")
    @ApiImplicitParam(name = "zxEqExternalEquipmentUsage", value = "租用外部设备使用情况entity", dataType = "ZxEqExternalEquipmentUsage")
    @RequireToken
    @PostMapping("/updateZxEqExternalEquipmentUsage")
    public ResponseEntity updateZxEqExternalEquipmentUsage(@RequestBody(required = false) ZxEqExternalEquipmentUsage zxEqExternalEquipmentUsage) {
        return zxEqExternalEquipmentUsageService.updateZxEqExternalEquipmentUsage(zxEqExternalEquipmentUsage);
    }

    @ApiOperation(value="删除租用外部设备使用情况", notes="删除租用外部设备使用情况")
    @ApiImplicitParam(name = "zxEqExternalEquipmentUsageList", value = "租用外部设备使用情况List", dataType = "List<ZxEqExternalEquipmentUsage>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxEqExternalEquipmentUsage")
    public ResponseEntity batchDeleteUpdateZxEqExternalEquipmentUsage(@RequestBody(required = false) List<ZxEqExternalEquipmentUsage> zxEqExternalEquipmentUsageList) {
        return zxEqExternalEquipmentUsageService.batchDeleteUpdateZxEqExternalEquipmentUsage(zxEqExternalEquipmentUsageList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    
    @ApiOperation(value="报表导出租用外部设备使用情况", notes="报表导出租用外部设备使用情况")
    @ApiImplicitParam(name = "zxEqExternalEquipmentUsage", value = "设备台账entity", dataType = "ZxEqExternalEquipmentUsage")
    @PostMapping("/ureportZxEqExternalEquipmentUsage")
    public List<ZxEqExternalEquipmentUsage> ureportZxEqExternalEquipmentUsage(@RequestBody(required = false) ZxEqExternalEquipmentUsage zxEqExternalEquipmentUsage) {
        return zxEqExternalEquipmentUsageService.ureportZxEqExternalEquipmentUsage(zxEqExternalEquipmentUsage);
    }
    
    @ApiOperation(value="报表导出租用外部设备使用情况", notes="报表导出租用外部设备使用情况")
    @ApiImplicitParam(name = "zxEqExternalEquipmentUsage", value = "设备台账entity", dataType = "ZxEqExternalEquipmentUsage")
    @RequireToken
    @PostMapping("/ureportZxEqExternalEquipmentUsageIdle")
    public ResponseEntity ureportZxEqExternalEquipmentUsageIdle(@RequestBody(required = false) ZxEqExternalEquipmentUsage zxEqExternalEquipmentUsage) {
        return zxEqExternalEquipmentUsageService.ureportZxEqExternalEquipmentUsageIdle(zxEqExternalEquipmentUsage);
    }
}
