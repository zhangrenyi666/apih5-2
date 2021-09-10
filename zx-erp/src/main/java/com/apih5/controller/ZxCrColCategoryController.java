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
import com.apih5.mybatis.pojo.ZxCrColCategory;
import com.apih5.service.ZxCrColCategoryService;

@RestController
public class ZxCrColCategoryController {

    @Autowired(required = true)
    private ZxCrColCategoryService zxCrColCategoryService;

    @ApiOperation(value="查询专业化分类目录", notes="查询专业化分类目录")
    @ApiImplicitParam(name = "zxCrColCategory", value = "专业化分类目录entity", dataType = "ZxCrColCategory")
    @RequireToken
    @PostMapping("/getZxCrColCategoryList")
    public ResponseEntity getZxCrColCategoryList(@RequestBody(required = false) ZxCrColCategory zxCrColCategory) {
        return zxCrColCategoryService.getZxCrColCategoryListByCondition(zxCrColCategory);
    }

    @ApiOperation(value="查询详情专业化分类目录", notes="查询详情专业化分类目录")
    @ApiImplicitParam(name = "zxCrColCategory", value = "专业化分类目录entity", dataType = "ZxCrColCategory")
    @RequireToken
    @PostMapping("/getZxCrColCategoryDetail")
    public ResponseEntity getZxCrColCategoryDetail(@RequestBody(required = false) ZxCrColCategory zxCrColCategory) {
        return zxCrColCategoryService.getZxCrColCategoryDetail(zxCrColCategory);
    }

    @ApiOperation(value="新增专业化分类目录", notes="新增专业化分类目录")
    @ApiImplicitParam(name = "zxCrColCategory", value = "专业化分类目录entity", dataType = "ZxCrColCategory")
    @RequireToken
    @PostMapping("/addZxCrColCategory")
    public ResponseEntity addZxCrColCategory(@RequestBody(required = false) ZxCrColCategory zxCrColCategory) {
        return zxCrColCategoryService.saveZxCrColCategory(zxCrColCategory);
    }

    @ApiOperation(value="更新专业化分类目录", notes="更新专业化分类目录")
    @ApiImplicitParam(name = "zxCrColCategory", value = "专业化分类目录entity", dataType = "ZxCrColCategory")
    @RequireToken
    @PostMapping("/updateZxCrColCategory")
    public ResponseEntity updateZxCrColCategory(@RequestBody(required = false) ZxCrColCategory zxCrColCategory) {
        return zxCrColCategoryService.updateZxCrColCategory(zxCrColCategory);
    }

    @ApiOperation(value="删除专业化分类目录", notes="删除专业化分类目录")
    @ApiImplicitParam(name = "zxCrColCategoryList", value = "专业化分类目录List", dataType = "List<ZxCrColCategory>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxCrColCategory")
    public ResponseEntity batchDeleteUpdateZxCrColCategory(@RequestBody(required = false) List<ZxCrColCategory> zxCrColCategoryList) {
        return zxCrColCategoryService.batchDeleteUpdateZxCrColCategory(zxCrColCategoryList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

    @ApiOperation(value="左边大树形=专业化分类目录", notes="左边大树形=专业化分类目录")
    @ApiImplicitParam(name = "zxCrColCategory", value = "专业化分类目录entity", dataType = "ZxCrColCategory")
    @RequireToken
    @PostMapping("/getZxCrColCategoryTree")
    public ResponseEntity getZxCrColCategoryTree(@RequestBody(required = false) ZxCrColCategory zxCrColCategory) {
        return zxCrColCategoryService.getZxCrColCategoryTree(zxCrColCategory);
    }
}
