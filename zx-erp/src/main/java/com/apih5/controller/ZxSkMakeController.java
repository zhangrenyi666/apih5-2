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
import com.apih5.mybatis.pojo.ZxSkMake;
import com.apih5.service.ZxSkMakeService;

@RestController
public class ZxSkMakeController {

    @Autowired(required = true)
    private ZxSkMakeService zxSkMakeService;

    @ApiOperation(value="查询仓库盘点", notes="查询仓库盘点")
    @ApiImplicitParam(name = "zxSkMake", value = "仓库盘点entity", dataType = "ZxSkMake")
    @RequireToken
    @PostMapping("/getZxSkMakeList")
    public ResponseEntity getZxSkMakeList(@RequestBody(required = false) ZxSkMake zxSkMake) {
        return zxSkMakeService.getZxSkMakeListByCondition(zxSkMake);
    }

    @ApiOperation(value="查询详情仓库盘点", notes="查询详情仓库盘点")
    @ApiImplicitParam(name = "zxSkMake", value = "仓库盘点entity", dataType = "ZxSkMake")
    @RequireToken
    @PostMapping("/getZxSkMakeDetail")
    public ResponseEntity getZxSkMakeDetail(@RequestBody(required = false) ZxSkMake zxSkMake) {
        return zxSkMakeService.getZxSkMakeDetail(zxSkMake);
    }

    @ApiOperation(value="新增仓库盘点", notes="新增仓库盘点")
    @ApiImplicitParam(name = "zxSkMake", value = "仓库盘点entity", dataType = "ZxSkMake")
    @RequireToken
    @PostMapping("/addZxSkMake")
    public ResponseEntity addZxSkMake(@RequestBody(required = false) ZxSkMake zxSkMake) {
        return zxSkMakeService.saveZxSkMake(zxSkMake);
    }

    @ApiOperation(value="更新仓库盘点", notes="更新仓库盘点")
    @ApiImplicitParam(name = "zxSkMake", value = "仓库盘点entity", dataType = "ZxSkMake")
    @RequireToken
    @PostMapping("/updateZxSkMake")
    public ResponseEntity updateZxSkMake(@RequestBody(required = false) ZxSkMake zxSkMake) {
        return zxSkMakeService.updateZxSkMake(zxSkMake);
    }

    @ApiOperation(value="删除仓库盘点", notes="删除仓库盘点")
    @ApiImplicitParam(name = "zxSkMakeList", value = "仓库盘点List", dataType = "List<ZxSkMake>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxSkMake")
    public ResponseEntity batchDeleteUpdateZxSkMake(@RequestBody(required = false) List<ZxSkMake> zxSkMakeList) {
        return zxSkMakeService.batchDeleteUpdateZxSkMake(zxSkMakeList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

    //结束盘点
    @ApiOperation(value="结束仓库盘点", notes="结束仓库盘点")
    @ApiImplicitParam(name = "zxSkMake", value = "仓库盘点entity", dataType = "ZxSkMake")
    @RequireToken
    @PostMapping("/checkZxSkMake")
    public ResponseEntity checkZxSkMake(@RequestBody(required = false) ZxSkMake zxSkMake) {
        return zxSkMakeService.checkZxSkMake(zxSkMake);
    }

    @ApiOperation(value="开始仓库盘点", notes="开始仓库盘点")
    @ApiImplicitParam(name = "zxSkMake", value = "仓库盘点entity", dataType = "ZxSkMake")
    @RequireToken
    @PostMapping("/startZxSkMake")
    public ResponseEntity startZxSkMake(@RequestBody(required = false) ZxSkMake zxSkMake) {
        return zxSkMakeService.startZxSkMake(zxSkMake);
    }



}
