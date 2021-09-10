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
import com.apih5.mybatis.pojo.ZxSfAccident;
import com.apih5.service.ZxSfAccidentService;

@RestController
public class ZxSfAccidentController {

    @Autowired(required = true)
    private ZxSfAccidentService zxSfAccidentService;

    @ApiOperation(value="查询伤亡事故情况统计分析表", notes="查询伤亡事故情况统计分析表")
    @ApiImplicitParam(name = "zxSfAccident", value = "伤亡事故情况统计分析表entity", dataType = "ZxSfAccident")
    @RequireToken
    @PostMapping("/getZxSfAccidentList")
    public ResponseEntity getZxSfAccidentList(@RequestBody(required = false) ZxSfAccident zxSfAccident) {
        return zxSfAccidentService.getZxSfAccidentListByCondition(zxSfAccident);
    }

    @ApiOperation(value="查询详情伤亡事故情况统计分析表", notes="查询详情伤亡事故情况统计分析表")
    @ApiImplicitParam(name = "zxSfAccident", value = "伤亡事故情况统计分析表entity", dataType = "ZxSfAccident")
    @RequireToken
    @PostMapping("/getZxSfAccidentDetail")
    public ResponseEntity getZxSfAccidentDetail(@RequestBody(required = false) ZxSfAccident zxSfAccident) {
        return zxSfAccidentService.getZxSfAccidentDetail(zxSfAccident);
    }

    @ApiOperation(value="新增伤亡事故情况统计分析表", notes="新增伤亡事故情况统计分析表")
    @ApiImplicitParam(name = "zxSfAccident", value = "伤亡事故情况统计分析表entity", dataType = "ZxSfAccident")
    @RequireToken
    @PostMapping("/addZxSfAccident")
    public ResponseEntity addZxSfAccident(@RequestBody(required = false) ZxSfAccident zxSfAccident) {
        return zxSfAccidentService.saveZxSfAccident(zxSfAccident);
    }

    @ApiOperation(value="更新伤亡事故情况统计分析表", notes="更新伤亡事故情况统计分析表")
    @ApiImplicitParam(name = "zxSfAccident", value = "伤亡事故情况统计分析表entity", dataType = "ZxSfAccident")
    @RequireToken
    @PostMapping("/updateZxSfAccident")
    public ResponseEntity updateZxSfAccident(@RequestBody(required = false) ZxSfAccident zxSfAccident) {
        return zxSfAccidentService.updateZxSfAccident(zxSfAccident);
    }

    @ApiOperation(value="删除伤亡事故情况统计分析表", notes="删除伤亡事故情况统计分析表")
    @ApiImplicitParam(name = "zxSfAccidentList", value = "伤亡事故情况统计分析表List", dataType = "List<ZxSfAccident>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxSfAccident")
    public ResponseEntity batchDeleteUpdateZxSfAccident(@RequestBody(required = false) List<ZxSfAccident> zxSfAccidentList) {
        return zxSfAccidentService.batchDeleteUpdateZxSfAccident(zxSfAccidentList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
