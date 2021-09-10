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
import com.apih5.mybatis.pojo.ZxSkWarehouse;
import com.apih5.service.ZxSkWarehouseService;

@RestController
public class ZxSkWarehouseController {

    @Autowired(required = true)
    private ZxSkWarehouseService zxSkWarehouseService;

    @ApiOperation(value="查询仓库", notes="查询仓库")
    @ApiImplicitParam(name = "zxSkWarehouse", value = "仓库entity", dataType = "ZxSkWarehouse")
    @RequireToken
    @PostMapping("/getZxSkWarehouseList")
    public ResponseEntity getZxSkWarehouseList(@RequestBody(required = false) ZxSkWarehouse zxSkWarehouse) {
        return zxSkWarehouseService.getZxSkWarehouseListByCondition(zxSkWarehouse);
    }

    @ApiOperation(value="查询详情仓库", notes="查询详情仓库")
    @ApiImplicitParam(name = "zxSkWarehouse", value = "仓库entity", dataType = "ZxSkWarehouse")
    @RequireToken
    @PostMapping("/getZxSkWarehouseDetails")
    public ResponseEntity getZxSkWarehouseDetails(@RequestBody(required = false) ZxSkWarehouse zxSkWarehouse) {
        return zxSkWarehouseService.getZxSkWarehouseDetails(zxSkWarehouse);
    }

    @ApiOperation(value="新增仓库", notes="新增仓库")
    @ApiImplicitParam(name = "zxSkWarehouse", value = "仓库entity", dataType = "ZxSkWarehouse")
    @RequireToken
    @PostMapping("/addZxSkWarehouse")
    public ResponseEntity addZxSkWarehouse(@RequestBody(required = false) ZxSkWarehouse zxSkWarehouse) {
        return zxSkWarehouseService.saveZxSkWarehouse(zxSkWarehouse);
    }

    @ApiOperation(value="更新仓库", notes="更新仓库")
    @ApiImplicitParam(name = "zxSkWarehouse", value = "仓库entity", dataType = "ZxSkWarehouse")
    @RequireToken
    @PostMapping("/updateZxSkWarehouse")
    public ResponseEntity updateZxSkWarehouse(@RequestBody(required = false) ZxSkWarehouse zxSkWarehouse) {
        return zxSkWarehouseService.updateZxSkWarehouse(zxSkWarehouse);
    }

    @ApiOperation(value="删除仓库", notes="删除仓库")
    @ApiImplicitParam(name = "zxSkWarehouseList", value = "仓库List", dataType = "List<ZxSkWarehouse>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxSkWarehouse")
    public ResponseEntity batchDeleteUpdateZxSkWarehouse(@RequestBody(required = false) List<ZxSkWarehouse> zxSkWarehouseList) {
        return zxSkWarehouseService.batchDeleteUpdateZxSkWarehouse(zxSkWarehouseList);
    }

    @ApiOperation(value="根据项目获取仓库", notes="根据项目获取仓库")
    @ApiImplicitParam(name = "zxSkWarehouse", value = "仓库entity", dataType = "ZxSkWarehouse")
    @RequireToken
    @PostMapping("/getZxSkWarehouseByParentOrgIDList")
    public ResponseEntity getZxSkWarehouseByParentOrgIDList(@RequestBody(required = false) ZxSkWarehouse zxSkWarehouse) {
        return zxSkWarehouseService.getZxSkWarehouseByParentOrgIDList(zxSkWarehouse);
    }




}
