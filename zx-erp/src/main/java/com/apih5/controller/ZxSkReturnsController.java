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
import com.apih5.mybatis.pojo.ZxSkReturns;
import com.apih5.service.ZxSkReturnsService;

@RestController
public class ZxSkReturnsController {

    @Autowired(required = true)
    private ZxSkReturnsService zxSkReturnsService;

    @ApiOperation(value="查询周转材料退货", notes="查询周转材料退货")
    @ApiImplicitParam(name = "zxSkReturns", value = "周转材料退货entity", dataType = "ZxSkReturns")
    @RequireToken
    @PostMapping("/getZxSkReturnsList")
    public ResponseEntity getZxSkReturnsList(@RequestBody(required = false) ZxSkReturns zxSkReturns) {
        return zxSkReturnsService.getZxSkReturnsListByCondition(zxSkReturns);
    }

    @ApiOperation(value="查询详情周转材料退货", notes="查询详情周转材料退货")
    @ApiImplicitParam(name = "zxSkReturns", value = "周转材料退货entity", dataType = "ZxSkReturns")
    @RequireToken
    @PostMapping("/getZxSkReturnsDetail")
    public ResponseEntity getZxSkReturnsDetail(@RequestBody(required = false) ZxSkReturns zxSkReturns) {
        return zxSkReturnsService.getZxSkReturnsDetail(zxSkReturns);
    }

    @ApiOperation(value="新增周转材料退货", notes="新增周转材料退货")
    @ApiImplicitParam(name = "zxSkReturns", value = "周转材料退货entity", dataType = "ZxSkReturns")
    @RequireToken
    @PostMapping("/addZxSkReturns")
    public ResponseEntity addZxSkReturns(@RequestBody(required = false) ZxSkReturns zxSkReturns) {
        return zxSkReturnsService.saveZxSkReturns(zxSkReturns);
    }

    @ApiOperation(value="更新周转材料退货", notes="更新周转材料退货")
    @ApiImplicitParam(name = "zxSkReturns", value = "周转材料退货entity", dataType = "ZxSkReturns")
    @RequireToken
    @PostMapping("/updateZxSkReturns")
    public ResponseEntity updateZxSkReturns(@RequestBody(required = false) ZxSkReturns zxSkReturns) {
        return zxSkReturnsService.updateZxSkReturns(zxSkReturns);
    }

    @ApiOperation(value="删除周转材料退货", notes="删除周转材料退货")
    @ApiImplicitParam(name = "zxSkReturnsList", value = "周转材料退货List", dataType = "List<ZxSkReturns>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxSkReturns")
    public ResponseEntity batchDeleteUpdateZxSkReturns(@RequestBody(required = false) List<ZxSkReturns> zxSkReturnsList) {
        return zxSkReturnsService.batchDeleteUpdateZxSkReturns(zxSkReturnsList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

    @ApiOperation(value="获取周转材料退货编号", notes="获取周转材料退货编号")
    @ApiImplicitParam(name = "zxSkReturns", value = "周转材料退货entity", dataType = "zxSkReturns")
    @RequireToken
    @PostMapping("/getZxSkReturnsNo")
    public ResponseEntity getZxSkReturnsNo(@RequestBody(required = false) ZxSkReturns zxSkReturns) {
        return zxSkReturnsService.getZxSkReturnsNo(zxSkReturns);
    }

    @ApiOperation(value="获取周转材料退货物资", notes="获取周转材料退货物资")
    @ApiImplicitParam(name = "zxSkReturns", value = "周转材料退货entity", dataType = "ZxSkReturns")
    @RequireToken
    @PostMapping("/getZxSkReturnsResourceList")
    public ResponseEntity getZxSkReturnsResourceList(@RequestBody(required = false) ZxSkReturns zxSkReturns) {
        return zxSkReturnsService.getZxSkReturnsResourceList(zxSkReturns);
    }

    @ApiOperation(value="审核周转材料退货", notes="审核周转材料退货")
    @ApiImplicitParam(name = "zxSkReturns", value = "周转材料退货entity", dataType = "ZxSkReturns")
    @RequireToken
    @PostMapping("/checkZxSkReturns")
    public ResponseEntity checkZxSkReturns(@RequestBody(required = false) ZxSkReturns zxSkReturns) {
        return zxSkReturnsService.checkZxSkReturns(zxSkReturns);
    }








}
