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
import com.apih5.mybatis.pojo.ZxEqResCategory;
import com.apih5.service.ZxEqResCategoryService;

@RestController
public class ZxEqResCategoryController {

    @Autowired(required = true)
    private ZxEqResCategoryService zxEqResCategoryService;

    @ApiOperation(value="查询设备分类", notes="查询设备分类")
    @ApiImplicitParam(name = "zxEqResCategory", value = "设备分类entity", dataType = "ZxEqResCategory")
    @RequireToken
    @PostMapping("/getZxEqResCategoryList")
    public ResponseEntity getZxEqResCategoryList(@RequestBody(required = false) ZxEqResCategory zxEqResCategory) {
        return zxEqResCategoryService.getZxEqResCategoryListByCondition(zxEqResCategory);
    }

    @ApiOperation(value="查询详情设备分类", notes="查询详情设备分类")
    @ApiImplicitParam(name = "zxEqResCategory", value = "设备分类entity", dataType = "ZxEqResCategory")
    @RequireToken
    @PostMapping("/getZxEqResCategoryDetails")
    public ResponseEntity getZxEqResCategoryDetails(@RequestBody(required = false) ZxEqResCategory zxEqResCategory) {
        return zxEqResCategoryService.getZxEqResCategoryDetails(zxEqResCategory);
    }

    @ApiOperation(value="新增设备分类", notes="新增设备分类")
    @ApiImplicitParam(name = "zxEqResCategory", value = "设备分类entity", dataType = "ZxEqResCategory")
    @RequireToken
    @PostMapping("/addZxEqResCategory")
    public ResponseEntity addZxEqResCategory(@RequestBody(required = false) ZxEqResCategory zxEqResCategory) {
        return zxEqResCategoryService.saveZxEqResCategory(zxEqResCategory);
    }

    @ApiOperation(value="更新设备分类", notes="更新设备分类")
    @ApiImplicitParam(name = "zxEqResCategory", value = "设备分类entity", dataType = "ZxEqResCategory")
    @RequireToken
    @PostMapping("/updateZxEqResCategory")
    public ResponseEntity updateZxEqResCategory(@RequestBody(required = false) ZxEqResCategory zxEqResCategory) {
        return zxEqResCategoryService.updateZxEqResCategory(zxEqResCategory);
    }

    @ApiOperation(value="删除设备分类", notes="删除设备分类")
    @ApiImplicitParam(name = "zxEqResCategoryList", value = "设备分类List", dataType = "List<ZxEqResCategory>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxEqResCategory")
    public ResponseEntity batchDeleteUpdateZxEqResCategory(@RequestBody(required = false) List<ZxEqResCategory> zxEqResCategoryList) {
        return zxEqResCategoryService.batchDeleteUpdateZxEqResCategory(zxEqResCategoryList);
    }

    @ApiOperation(value="批量启动设备分类", notes="批量启动设备分类")
    @ApiImplicitParam(name = "zxEqResCategoryList", value = "设备分类List", dataType = "List<ZxEqResCategory>")
    @RequireToken
    @PostMapping("/batchStartUpdateZxEqResCategory")
    public ResponseEntity batchStartUpdateZxEqResCategory(@RequestBody(required = false) List<ZxEqResCategory> zxEqResCategoryList) {
        return zxEqResCategoryService.batchStartUpdateZxEqResCategory(zxEqResCategoryList);
    }
    
    @ApiOperation(value="批量停用设备分类", notes="批量停用设备分类")
    @ApiImplicitParam(name = "zxEqResCategoryList", value = "设备分类List", dataType = "List<ZxEqResCategory>")
    @RequireToken
    @PostMapping("/batchStopUpdateZxEqResCategory")
    public ResponseEntity batchStopUpdateZxEqResCategory(@RequestBody(required = false) List<ZxEqResCategory> zxEqResCategoryList) {
        return zxEqResCategoryService.batchStopUpdateZxEqResCategory(zxEqResCategoryList);
    }
    
    @ApiOperation(value="查询设备分类", notes="查询设备分类")
    @ApiImplicitParam(name = "zxEqResCategory", value = "设备分类entity", dataType = "ZxEqResCategory")
    @RequireToken
    @PostMapping("/getZxEqResCategoryTree")
    public ResponseEntity getZxEqResCategoryTree(@RequestBody(required = false) ZxEqResCategory zxEqResCategory) {
        return zxEqResCategoryService.getZxEqResCategoryTree(zxEqResCategory);
    }
    
    @ApiOperation(value="查询设备分类", notes="查询设备分类")
    @ApiImplicitParam(name = "zxEqResCategory", value = "设备分类entity", dataType = "ZxEqResCategory")
    @RequireToken
    @PostMapping("/getZxEqResCategoryItemList")
    public ResponseEntity getZxEqResCategoryItemList(@RequestBody(required = false) ZxEqResCategory zxEqResCategory) {
        return zxEqResCategoryService.getZxEqResCategoryItemList(zxEqResCategory);
    }
    
}
