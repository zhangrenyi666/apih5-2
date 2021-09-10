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
import com.apih5.mybatis.pojo.ZxSfEAccident;
import com.apih5.service.ZxSfEAccidentService;

@RestController
public class ZxSfEAccidentController {

    @Autowired(required = true)
    private ZxSfEAccidentService zxSfEAccidentService;

    @ApiOperation(value="查询企业职工年度统计分析表", notes="查询企业职工伤亡事故年度统计分析表")
    @ApiImplicitParam(name = "zxSfEAccident", value = "企业职工伤亡事故年度统计分析表entity", dataType = "ZxSfEAccident")
    @RequireToken
    @PostMapping("/getZxSfEAccidentList")
    public ResponseEntity getZxSfEAccidentList(@RequestBody(required = false) ZxSfEAccident zxSfEAccident) {
        return zxSfEAccidentService.getZxSfEAccidentListByCondition(zxSfEAccident);
    }

    @ApiOperation(value="查询详情企业职工伤亡事故年度统计分析表", notes="查询详情企业职工伤亡事故年度统计分析表")
    @ApiImplicitParam(name = "zxSfEAccident", value = "企业职工伤亡事故年度统计分析表entity", dataType = "ZxSfEAccident")
    @RequireToken
    @PostMapping("/getZxSfEAccidentDetail")
    public ResponseEntity getZxSfEAccidentDetail(@RequestBody(required = false) ZxSfEAccident zxSfEAccident) {
        return zxSfEAccidentService.getZxSfEAccidentDetail(zxSfEAccident);
    }

    @ApiOperation(value="新增企业职工伤亡事故年度统计分析表", notes="新增企业职工伤亡事故年度统计分析表")
    @ApiImplicitParam(name = "zxSfEAccident", value = "企业职工伤亡事故年度统计分析表entity", dataType = "ZxSfEAccident")
    @RequireToken
    @PostMapping("/addZxSfEAccident")
    public ResponseEntity addZxSfEAccident(@RequestBody(required = false) ZxSfEAccident zxSfEAccident) {
        return zxSfEAccidentService.saveZxSfEAccident(zxSfEAccident);
    }

    @ApiOperation(value="更新企业职工伤亡事故年度统计分析表", notes="更新企业职工伤亡事故年度统计分析表")
    @ApiImplicitParam(name = "zxSfEAccident", value = "企业职工伤亡事故年度统计分析表entity", dataType = "ZxSfEAccident")
    @RequireToken
    @PostMapping("/updateZxSfEAccident")
    public ResponseEntity updateZxSfEAccident(@RequestBody(required = false) ZxSfEAccident zxSfEAccident) {
        return zxSfEAccidentService.updateZxSfEAccident(zxSfEAccident);
    }

    @ApiOperation(value="删除企业职工伤亡事故年度统计分析表", notes="删除企业职工伤亡事故年度统计分析表")
    @ApiImplicitParam(name = "zxSfEAccidentList", value = "企业职工伤亡事故年度统计分析表List", dataType = "List<ZxSfEAccident>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxSfEAccident")
    public ResponseEntity batchDeleteUpdateZxSfEAccident(@RequestBody(required = false) List<ZxSfEAccident> zxSfEAccidentList) {
        return zxSfEAccidentService.batchDeleteUpdateZxSfEAccident(zxSfEAccidentList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
