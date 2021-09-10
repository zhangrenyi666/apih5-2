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
import com.apih5.mybatis.pojo.ZxSkMosResMovStatRep;
import com.apih5.service.ZxSkMosResMovStatRepService;

@RestController
public class ZxSkMosResMovStatRepController {

    @Autowired(required = true)
    private ZxSkMosResMovStatRepService zxSkMosResMovStatRepService;

    @ApiOperation(value="查询主要物资收、发、存统计报表", notes="查询主要物资收、发、存统计报表")
    @ApiImplicitParam(name = "zxSkMosResMovStatRep", value = "主要物资收、发、存统计报表entity", dataType = "ZxSkMosResMovStatRep")
    @RequireToken
    @PostMapping("/getZxSkMosResMovStatRepList")
    public ResponseEntity getZxSkMosResMovStatRepList(@RequestBody(required = false) ZxSkMosResMovStatRep zxSkMosResMovStatRep) {
        return zxSkMosResMovStatRepService.getZxSkMosResMovStatRepListByCondition(zxSkMosResMovStatRep);
    }

    @ApiOperation(value="查询详情主要物资收、发、存统计报表", notes="查询详情主要物资收、发、存统计报表")
    @ApiImplicitParam(name = "zxSkMosResMovStatRep", value = "主要物资收、发、存统计报表entity", dataType = "ZxSkMosResMovStatRep")
    @RequireToken
    @PostMapping("/getZxSkMosResMovStatRepDetail")
    public ResponseEntity getZxSkMosResMovStatRepDetail(@RequestBody(required = false) ZxSkMosResMovStatRep zxSkMosResMovStatRep) {
        return zxSkMosResMovStatRepService.getZxSkMosResMovStatRepDetail(zxSkMosResMovStatRep);
    }

    @ApiOperation(value="新增主要物资收、发、存统计报表", notes="新增主要物资收、发、存统计报表")
    @ApiImplicitParam(name = "zxSkMosResMovStatRep", value = "主要物资收、发、存统计报表entity", dataType = "ZxSkMosResMovStatRep")
    @RequireToken
    @PostMapping("/addZxSkMosResMovStatRep")
    public ResponseEntity addZxSkMosResMovStatRep(@RequestBody(required = false) ZxSkMosResMovStatRep zxSkMosResMovStatRep) {
        return zxSkMosResMovStatRepService.saveZxSkMosResMovStatRep(zxSkMosResMovStatRep);
    }

    @ApiOperation(value="更新主要物资收、发、存统计报表", notes="更新主要物资收、发、存统计报表")
    @ApiImplicitParam(name = "zxSkMosResMovStatRep", value = "主要物资收、发、存统计报表entity", dataType = "ZxSkMosResMovStatRep")
    @RequireToken
    @PostMapping("/updateZxSkMosResMovStatRep")
    public ResponseEntity updateZxSkMosResMovStatRep(@RequestBody(required = false) ZxSkMosResMovStatRep zxSkMosResMovStatRep) {
        return zxSkMosResMovStatRepService.updateZxSkMosResMovStatRep(zxSkMosResMovStatRep);
    }

    @ApiOperation(value="删除主要物资收、发、存统计报表", notes="删除主要物资收、发、存统计报表")
    @ApiImplicitParam(name = "zxSkMosResMovStatRepList", value = "主要物资收、发、存统计报表List", dataType = "List<ZxSkMosResMovStatRep>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxSkMosResMovStatRep")
    public ResponseEntity batchDeleteUpdateZxSkMosResMovStatRep(@RequestBody(required = false) List<ZxSkMosResMovStatRep> zxSkMosResMovStatRepList) {
        return zxSkMosResMovStatRepService.batchDeleteUpdateZxSkMosResMovStatRep(zxSkMosResMovStatRepList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

    @ApiOperation(value="筛选数据,获取报表数据", notes="筛选数据,获取报表数据")
    @ApiImplicitParam(name = "zxSkMosResMovStatRep", value = "主要物资收、发、存统计报表entity", dataType = "ZxSkMosResMovStatRep")
    @RequireToken
    @PostMapping("/filtrateZxSkMosResMovStatRep")
    public ResponseEntity filtrateZxSkMosResMovStatRep(@RequestBody(required = false) ZxSkMosResMovStatRep zxSkMosResMovStatRep) {
        return zxSkMosResMovStatRepService.filtrateZxSkMosResMovStatRep(zxSkMosResMovStatRep);
    }
}
