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
import com.apih5.mybatis.pojo.ZxEqExternalEquipmentUsage;
import com.apih5.mybatis.pojo.ZxEqVehicleUsageCompany;
import com.apih5.service.ZxEqVehicleUsageCompanyService;

@RestController
public class ZxEqVehicleUsageCompanyController {

    @Autowired(required = true)
    private ZxEqVehicleUsageCompanyService zxEqVehicleUsageCompanyService;

    @ApiOperation(value="查询机械车辆使用情况报表（公司）", notes="查询机械车辆使用情况报表（公司）")
    @ApiImplicitParam(name = "zxEqVehicleUsageCompany", value = "机械车辆使用情况报表（公司）entity", dataType = "ZxEqVehicleUsageCompany")
    @RequireToken
    @PostMapping("/getZxEqVehicleUsageCompanyList")
    public ResponseEntity getZxEqVehicleUsageCompanyList(@RequestBody(required = false) ZxEqVehicleUsageCompany zxEqVehicleUsageCompany) {
        return zxEqVehicleUsageCompanyService.getZxEqVehicleUsageCompanyListByCondition(zxEqVehicleUsageCompany);
    }

    @ApiOperation(value="查询详情机械车辆使用情况报表（公司）", notes="查询详情机械车辆使用情况报表（公司）")
    @ApiImplicitParam(name = "zxEqVehicleUsageCompany", value = "机械车辆使用情况报表（公司）entity", dataType = "ZxEqVehicleUsageCompany")
    @RequireToken
    @PostMapping("/getZxEqVehicleUsageCompanyDetail")
    public ResponseEntity getZxEqVehicleUsageCompanyDetail(@RequestBody(required = false) ZxEqVehicleUsageCompany zxEqVehicleUsageCompany) {
        return zxEqVehicleUsageCompanyService.getZxEqVehicleUsageCompanyDetail(zxEqVehicleUsageCompany);
    }

    @ApiOperation(value="新增机械车辆使用情况报表（公司）", notes="新增机械车辆使用情况报表（公司）")
    @ApiImplicitParam(name = "zxEqVehicleUsageCompany", value = "机械车辆使用情况报表（公司）entity", dataType = "ZxEqVehicleUsageCompany")
    @RequireToken
    @PostMapping("/addZxEqVehicleUsageCompany")
    public ResponseEntity addZxEqVehicleUsageCompany(@RequestBody(required = false) ZxEqVehicleUsageCompany zxEqVehicleUsageCompany) {
        return zxEqVehicleUsageCompanyService.saveZxEqVehicleUsageCompany(zxEqVehicleUsageCompany);
    }

    @ApiOperation(value="更新机械车辆使用情况报表（公司）", notes="更新机械车辆使用情况报表（公司）")
    @ApiImplicitParam(name = "zxEqVehicleUsageCompany", value = "机械车辆使用情况报表（公司）entity", dataType = "ZxEqVehicleUsageCompany")
    @RequireToken
    @PostMapping("/updateZxEqVehicleUsageCompany")
    public ResponseEntity updateZxEqVehicleUsageCompany(@RequestBody(required = false) ZxEqVehicleUsageCompany zxEqVehicleUsageCompany) {
        return zxEqVehicleUsageCompanyService.updateZxEqVehicleUsageCompany(zxEqVehicleUsageCompany);
    }

    @ApiOperation(value="删除机械车辆使用情况报表（公司）", notes="删除机械车辆使用情况报表（公司）")
    @ApiImplicitParam(name = "zxEqVehicleUsageCompanyList", value = "机械车辆使用情况报表（公司）List", dataType = "List<ZxEqVehicleUsageCompany>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxEqVehicleUsageCompany")
    public ResponseEntity batchDeleteUpdateZxEqVehicleUsageCompany(@RequestBody(required = false) List<ZxEqVehicleUsageCompany> zxEqVehicleUsageCompanyList) {
        return zxEqVehicleUsageCompanyService.batchDeleteUpdateZxEqVehicleUsageCompany(zxEqVehicleUsageCompanyList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    
    @ApiOperation(value="报表导出租用外部设备使用情况", notes="报表导出租用外部设备使用情况")
    @ApiImplicitParam(name = "zxEqVehicleUsageCompany", value = "设备台账entity", dataType = "ZxEqVehicleUsageCompany")
    @PostMapping("/ureportZxEqVehicleUsageCompany")
    public List<ZxEqVehicleUsageCompany> ureportZxEqVehicleUsageCompany(@RequestBody(required = false) ZxEqVehicleUsageCompany zxEqVehicleUsageCompany) {
        return zxEqVehicleUsageCompanyService.ureportZxEqVehicleUsageCompany(zxEqVehicleUsageCompany);
    }
    
    @ApiOperation(value="报表导出租用外部设备使用情况", notes="报表导出租用外部设备使用情况")
    @ApiImplicitParam(name = "zxEqVehicleUsageCompany", value = "设备台账entity", dataType = "ZxEqVehicleUsageCompany")
    @RequireToken
    @PostMapping("/ureportZxEqVehicleUsageCompanyIdle")
    public ResponseEntity ureportZxEqVehicleUsageCompanyIdle(@RequestBody(required = false) ZxEqVehicleUsageCompany zxEqVehicleUsageCompany) {
        return zxEqVehicleUsageCompanyService.ureportZxEqVehicleUsageCompanyIdle(zxEqVehicleUsageCompany);
    }
}
