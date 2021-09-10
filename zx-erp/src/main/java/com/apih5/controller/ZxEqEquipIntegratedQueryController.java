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
import com.apih5.mybatis.pojo.ZxSkCustomerOutRes;
import com.apih5.service.ZxEqEquipIntegratedQueryService;

@RestController
public class ZxEqEquipIntegratedQueryController {

    @Autowired(required = true)
    private ZxEqEquipIntegratedQueryService zxEqEquipIntegratedQueryService;

    @ApiOperation(value="查询ABCD设备资产分布", notes="查询ABCD设备资产分布")
    @ApiImplicitParam(name = "zxEqEquipIntegratedQuery", value = "ABCD设备资产分布entity", dataType = "ZxEqEquipIntegratedQuery")
    @RequireToken
    @PostMapping("/getZxEqEquipIntegratedQueryList")
    public ResponseEntity getZxEqEquipIntegratedQueryList(@RequestBody(required = false) ZxEqEquipIntegratedQuery zxEqEquipIntegratedQuery) {
        return zxEqEquipIntegratedQueryService.getZxEqEquipIntegratedQueryListByCondition(zxEqEquipIntegratedQuery);
    }

    @ApiOperation(value="查询详情ABCD设备资产分布", notes="查询详情ABCD设备资产分布")
    @ApiImplicitParam(name = "zxEqEquipIntegratedQuery", value = "ABCD设备资产分布entity", dataType = "ZxEqEquipIntegratedQuery")
    @RequireToken
    @PostMapping("/getZxEqEquipIntegratedQueryDetail")
    public ResponseEntity getZxEqEquipIntegratedQueryDetail(@RequestBody(required = false) ZxEqEquipIntegratedQuery zxEqEquipIntegratedQuery) {
        return zxEqEquipIntegratedQueryService.getZxEqEquipIntegratedQueryDetail(zxEqEquipIntegratedQuery);
    }

    @ApiOperation(value="新增ABCD设备资产分布", notes="新增ABCD设备资产分布")
    @ApiImplicitParam(name = "zxEqEquipIntegratedQuery", value = "ABCD设备资产分布entity", dataType = "ZxEqEquipIntegratedQuery")
    @RequireToken
    @PostMapping("/addZxEqEquipIntegratedQuery")
    public ResponseEntity addZxEqEquipIntegratedQuery(@RequestBody(required = false) ZxEqEquipIntegratedQuery zxEqEquipIntegratedQuery) {
        return zxEqEquipIntegratedQueryService.saveZxEqEquipIntegratedQuery(zxEqEquipIntegratedQuery);
    }

    @ApiOperation(value="更新ABCD设备资产分布", notes="更新ABCD设备资产分布")
    @ApiImplicitParam(name = "zxEqEquipIntegratedQuery", value = "ABCD设备资产分布entity", dataType = "ZxEqEquipIntegratedQuery")
    @RequireToken
    @PostMapping("/updateZxEqEquipIntegratedQuery")
    public ResponseEntity updateZxEqEquipIntegratedQuery(@RequestBody(required = false) ZxEqEquipIntegratedQuery zxEqEquipIntegratedQuery) {
        return zxEqEquipIntegratedQueryService.updateZxEqEquipIntegratedQuery(zxEqEquipIntegratedQuery);
    }

    @ApiOperation(value="删除ABCD设备资产分布", notes="删除ABCD设备资产分布")
    @ApiImplicitParam(name = "zxEqEquipIntegratedQueryList", value = "ABCD设备资产分布List", dataType = "List<ZxEqEquipIntegratedQuery>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxEqEquipIntegratedQuery")
    public ResponseEntity batchDeleteUpdateZxEqEquipIntegratedQuery(@RequestBody(required = false) List<ZxEqEquipIntegratedQuery> zxEqEquipIntegratedQueryList) {
        return zxEqEquipIntegratedQueryService.batchDeleteUpdateZxEqEquipIntegratedQuery(zxEqEquipIntegratedQueryList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    
    @ApiOperation(value="报表ABCD设备资产分布", notes="报表ABCD设备资产分布")
    @ApiImplicitParam(name = "zxEqEquipIntegratedQuery", value = "设备台账entity", dataType = "ZxEqEquipIntegratedQuery")
    @RequireToken
    @PostMapping("/ureportZxEqEquipIntegratedQueryIdle")
    public ResponseEntity ureportZxEqEquipIntegratedQueryIdle(@RequestBody(required = false) ZxEqEquipIntegratedQuery zxEqEquipIntegratedQuery) {
        return zxEqEquipIntegratedQueryService.ureportZxEqEquipIntegratedQueryIdle(zxEqEquipIntegratedQuery);
    }
}
