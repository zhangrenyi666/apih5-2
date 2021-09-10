package com.apih5.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.apih5.framework.annotation.RequireToken;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxSkLimitPriceRep;
import com.apih5.service.ZxSkLimitPriceRepService;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

@RestController
public class ZxSkLimitPriceRepController {

    @Autowired(required = true)
    private ZxSkLimitPriceRepService zxSkLimitPriceRepService;

    @ApiOperation(value="查询项目限价明细报表", notes="查询项目限价明细报表")
    @ApiImplicitParam(name = "zxSkLimitPriceRep", value = "项目限价明细报表entity", dataType = "ZxSkLimitPriceRep")
    @RequireToken
    @PostMapping("/getZxSkLimitPriceRepList")
    public ResponseEntity getZxSkLimitPriceRepList(@RequestBody(required = false) ZxSkLimitPriceRep zxSkLimitPriceRep) {
        return zxSkLimitPriceRepService.getZxSkLimitPriceRepListByCondition(zxSkLimitPriceRep);
    }

    @ApiOperation(value="查询详情项目限价明细报表", notes="查询详情项目限价明细报表")
    @ApiImplicitParam(name = "zxSkLimitPriceRep", value = "项目限价明细报表entity", dataType = "ZxSkLimitPriceRep")
    @RequireToken
    @PostMapping("/getZxSkLimitPriceRepDetail")
    public ResponseEntity getZxSkLimitPriceRepDetail(@RequestBody(required = false) ZxSkLimitPriceRep zxSkLimitPriceRep) {
        return zxSkLimitPriceRepService.getZxSkLimitPriceRepDetail(zxSkLimitPriceRep);
    }

    @ApiOperation(value="新增项目限价明细报表", notes="新增项目限价明细报表")
    @ApiImplicitParam(name = "zxSkLimitPriceRep", value = "项目限价明细报表entity", dataType = "ZxSkLimitPriceRep")
    @RequireToken
    @PostMapping("/addZxSkLimitPriceRep")
    public ResponseEntity addZxSkLimitPriceRep(@RequestBody(required = false) ZxSkLimitPriceRep zxSkLimitPriceRep) {
        return zxSkLimitPriceRepService.saveZxSkLimitPriceRep(zxSkLimitPriceRep);
    }

    @ApiOperation(value="更新项目限价明细报表", notes="更新项目限价明细报表")
    @ApiImplicitParam(name = "zxSkLimitPriceRep", value = "项目限价明细报表entity", dataType = "ZxSkLimitPriceRep")
    @RequireToken
    @PostMapping("/updateZxSkLimitPriceRep")
    public ResponseEntity updateZxSkLimitPriceRep(@RequestBody(required = false) ZxSkLimitPriceRep zxSkLimitPriceRep) {
        return zxSkLimitPriceRepService.updateZxSkLimitPriceRep(zxSkLimitPriceRep);
    }

    @ApiOperation(value="删除项目限价明细报表", notes="删除项目限价明细报表")
    @ApiImplicitParam(name = "zxSkLimitPriceRepList", value = "项目限价明细报表List", dataType = "List<ZxSkLimitPriceRep>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxSkLimitPriceRep")
    public ResponseEntity batchDeleteUpdateZxSkLimitPriceRep(@RequestBody(required = false) List<ZxSkLimitPriceRep> zxSkLimitPriceRepList) {
        return zxSkLimitPriceRepService.batchDeleteUpdateZxSkLimitPriceRep(zxSkLimitPriceRepList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    
    @ApiOperation(value="报表导出项目限价明细报表", notes="报表导出项目限价明细报表")
    @ApiImplicitParam(name = "zxSkLimitPriceRep", value = "设备台账entity", dataType = "ZxSkLimitPriceRep")
    @PostMapping("/ureportZxSkLimitPriceRep")
    public List<ZxSkLimitPriceRep> ureportZxSkLimitPriceRep(@RequestBody(required = false) ZxSkLimitPriceRep zxSkLimitPriceRep) {
        return zxSkLimitPriceRepService.ureportZxSkLimitPriceRep(zxSkLimitPriceRep);
    }
    
    @ApiOperation(value="报表导出项目限价明细报表", notes="报表导出项目限价明细报表")
    @ApiImplicitParam(name = "zxSkLimitPriceRep", value = "设备台账entity", dataType = "ZxSkLimitPriceRep")
    @RequireToken
    @PostMapping("/ureportZxSkLimitPriceRepIdle")
    public ResponseEntity ureportZxSkLimitPriceRepIdle(@RequestBody(required = false) ZxSkLimitPriceRep zxSkLimitPriceRep) {
        return zxSkLimitPriceRepService.ureportZxSkLimitPriceRepIdle(zxSkLimitPriceRep);
    }
}
