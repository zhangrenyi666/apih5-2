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
import com.apih5.mybatis.pojo.ZxCcResCategory;
import com.apih5.service.ZxCcResCategoryService;

@RestController
public class ZxCcResCategoryController {

    @Autowired(required = true)
    private ZxCcResCategoryService zxCcResCategoryService;

    @ApiOperation(value="查询基础数据(资质标准、企业性质、工程类别)", notes="查询基础数据(资质标准、企业性质、工程类别)")
    @ApiImplicitParam(name = "zxCcResCategory", value = "基础数据(资质标准、企业性质、工程类别)entity", dataType = "ZxCcResCategory")
    @RequireToken
    @PostMapping("/getZxCcResCategoryList")
    public ResponseEntity getZxCcResCategoryList(@RequestBody(required = false) ZxCcResCategory zxCcResCategory) {
        return zxCcResCategoryService.getZxCcResCategoryListByCondition(zxCcResCategory);
    }

    @ApiOperation(value="查询详情基础数据(资质标准、企业性质、工程类别)", notes="查询详情基础数据(资质标准、企业性质、工程类别)")
    @ApiImplicitParam(name = "zxCcResCategory", value = "基础数据(资质标准、企业性质、工程类别)entity", dataType = "ZxCcResCategory")
    @RequireToken
    @PostMapping("/getZxCcResCategoryDetail")
    public ResponseEntity getZxCcResCategoryDetail(@RequestBody(required = false) ZxCcResCategory zxCcResCategory) {
        return zxCcResCategoryService.getZxCcResCategoryDetail(zxCcResCategory);
    }

    @ApiOperation(value="新增基础数据(资质标准、企业性质、工程类别)", notes="新增基础数据(资质标准、企业性质、工程类别)")
    @ApiImplicitParam(name = "zxCcResCategory", value = "基础数据(资质标准、企业性质、工程类别)entity", dataType = "ZxCcResCategory")
    @RequireToken
    @PostMapping("/addZxCcResCategory")
    public ResponseEntity addZxCcResCategory(@RequestBody(required = false) ZxCcResCategory zxCcResCategory) {
        return zxCcResCategoryService.saveZxCcResCategory(zxCcResCategory);
    }

    @ApiOperation(value="更新基础数据(资质标准、企业性质、工程类别)", notes="更新基础数据(资质标准、企业性质、工程类别)")
    @ApiImplicitParam(name = "zxCcResCategory", value = "基础数据(资质标准、企业性质、工程类别)entity", dataType = "ZxCcResCategory")
    @RequireToken
    @PostMapping("/updateZxCcResCategory")
    public ResponseEntity updateZxCcResCategory(@RequestBody(required = false) ZxCcResCategory zxCcResCategory) {
        return zxCcResCategoryService.updateZxCcResCategory(zxCcResCategory);
    }

    @ApiOperation(value="删除基础数据(资质标准、企业性质、工程类别)", notes="删除基础数据(资质标准、企业性质、工程类别)")
    @ApiImplicitParam(name = "zxCcResCategoryList", value = "基础数据(资质标准、企业性质、工程类别)List", dataType = "List<ZxCcResCategory>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxCcResCategory")
    public ResponseEntity batchDeleteUpdateZxCcResCategory(@RequestBody(required = false) List<ZxCcResCategory> zxCcResCategoryList) {
        return zxCcResCategoryService.batchDeleteUpdateZxCcResCategory(zxCcResCategoryList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    @ApiOperation(value="查询基础数据(资质标准、企业性质、工程类别)左边的树形", notes="查询基础数据(资质标准、企业性质、工程类别)左边的树形")
    @ApiImplicitParam(name = "zxCcResCategory", value = "基础数据(资质标准、企业性质、工程类别)entity", dataType = "ZxCcResCategory")
    @RequireToken
    @PostMapping("/getZxCcResCategoryTree")
    public ResponseEntity getZxCcResCategoryTree(@RequestBody(required = false) ZxCcResCategory zxCcResCategory) {
        return zxCcResCategoryService.getZxCcResCategoryTree(zxCcResCategory);
    }
    
    
    @ApiOperation(value="查询基础数据(资质标准、企业性质、工程类别)右边的明细树形", notes="查询基础数据(资质标准、企业性质、工程类别)右边的明细树形")
    @ApiImplicitParam(name = "zxCcResCategory", value = "基础数据(资质标准、企业性质、工程类别)entity", dataType = "ZxCcResCategory")
    @RequireToken
    @PostMapping("/getZxCcResCategoryItemList")
    public ResponseEntity getZxCcResCategoryItemList(@RequestBody(required = false) ZxCcResCategory zxCcResCategory) {
        return zxCcResCategoryService.getZxCcResCategoryItemList(zxCcResCategory);
    }
    
    
    
    
    
    
}
