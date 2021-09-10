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
import com.apih5.mybatis.pojo.ZxBuProjectType;
import com.apih5.service.ZxBuProjectTypeService;

@RestController
public class ZxBuProjectTypeController {

    @Autowired(required = true)
    private ZxBuProjectTypeService zxBuProjectTypeService;

    @ApiOperation(value="查询项目工程类别", notes="查询项目工程类别")
    @ApiImplicitParam(name = "zxBuProjectType", value = "项目工程类别entity", dataType = "ZxBuProjectType")
    @RequireToken
    @PostMapping("/getZxBuProjectTypeList")
    public ResponseEntity getZxBuProjectTypeList(@RequestBody(required = false) ZxBuProjectType zxBuProjectType) {
        return zxBuProjectTypeService.getZxBuProjectTypeListByCondition(zxBuProjectType);
    }

    @ApiOperation(value="查询详情项目工程类别", notes="查询详情项目工程类别")
    @ApiImplicitParam(name = "zxBuProjectType", value = "项目工程类别entity", dataType = "ZxBuProjectType")
    @RequireToken
    @PostMapping("/getZxBuProjectTypeDetail")
    public ResponseEntity getZxBuProjectTypeDetail(@RequestBody(required = false) ZxBuProjectType zxBuProjectType) {
        return zxBuProjectTypeService.getZxBuProjectTypeDetail(zxBuProjectType);
    }

    @ApiOperation(value="新增项目工程类别", notes="新增项目工程类别")
    @ApiImplicitParam(name = "zxBuProjectType", value = "项目工程类别entity", dataType = "ZxBuProjectType")
    @RequireToken
    @PostMapping("/addZxBuProjectType")
    public ResponseEntity addZxBuProjectType(@RequestBody(required = false) ZxBuProjectType zxBuProjectType) {
        return zxBuProjectTypeService.saveZxBuProjectType(zxBuProjectType);
    }

    @ApiOperation(value="更新项目工程类别", notes="更新项目工程类别")
    @ApiImplicitParam(name = "zxBuProjectType", value = "项目工程类别entity", dataType = "ZxBuProjectType")
    @RequireToken
    @PostMapping("/updateZxBuProjectType")
    public ResponseEntity updateZxBuProjectType(@RequestBody(required = false) ZxBuProjectType zxBuProjectType) {
        return zxBuProjectTypeService.updateZxBuProjectType(zxBuProjectType);
    }

    @ApiOperation(value="删除项目工程类别", notes="删除项目工程类别")
    @ApiImplicitParam(name = "zxBuProjectTypeList", value = "项目工程类别List", dataType = "List<ZxBuProjectType>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxBuProjectType")
    public ResponseEntity batchDeleteUpdateZxBuProjectType(@RequestBody(required = false) List<ZxBuProjectType> zxBuProjectTypeList) {
        return zxBuProjectTypeService.batchDeleteUpdateZxBuProjectType(zxBuProjectTypeList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

    //审核
    @ApiOperation(value="审核项目工程类别", notes="审核项目工程类别")
    @ApiImplicitParam(name = "zxBuProjectType", value = "项目工程类别", dataType = "ZxBuProjectType")
    @RequireToken
    @PostMapping("/checkZxBuProjectType")
    public ResponseEntity checkZxBuProjectType(@RequestBody(required = false) ZxBuProjectType zxBuProjectType) {
        return zxBuProjectTypeService.checkZxBuProjectType(zxBuProjectType);
    }

    //获取审核后的项目列表
    @ApiOperation(value="审核后项目", notes="审核后项目")
    @ApiImplicitParam(name = "zxBuProjectType", value = "项目工程类别", dataType = "ZxBuProjectType")
    @RequireToken
    @PostMapping("/getZxBuProjectTypeCheckOver")
    public ResponseEntity getZxBuProjectTypeCheckOver(@RequestBody(required = false) ZxBuProjectType zxBuProjectType) {
        return zxBuProjectTypeService.getZxBuProjectTypeCheckOver(zxBuProjectType);
    }

    //获取项目列表
    @ApiOperation(value="项目工程类别项目列表", notes="项目工程类别项目列表")
    @ApiImplicitParam(name = "zxBuProjectType", value = "项目工程类别", dataType = "ZxBuProjectType")
    @RequireToken
    @PostMapping("/getZxBuProjectTypeProjectList")
    public ResponseEntity getZxBuProjectTypeProjectList(@RequestBody(required = false) ZxBuProjectType zxBuProjectType) {
        return zxBuProjectTypeService.getZxBuProjectTypeProjectList(zxBuProjectType);
    }





}
