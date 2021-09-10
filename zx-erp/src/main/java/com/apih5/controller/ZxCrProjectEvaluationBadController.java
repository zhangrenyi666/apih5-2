package com.apih5.controller;

import java.util.List;

import com.apih5.mybatis.pojo.ZxCrProjCreditBadBeha;
import com.apih5.mybatis.pojo.ZxCrProjCreditScore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import com.apih5.framework.annotation.RequireToken;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxCrProjectEvaluationBad;
import com.apih5.service.ZxCrProjectEvaluationBadService;

@RestController
public class ZxCrProjectEvaluationBadController {

    @Autowired(required = true)
    private ZxCrProjectEvaluationBadService zxCrProjectEvaluationBadService;

    @ApiOperation(value="查询严重不良行为考核表", notes="查询严重不良行为考核表")
    @ApiImplicitParam(name = "zxCrProjectEvaluationBad", value = "严重不良行为考核表entity", dataType = "ZxCrProjectEvaluationBad")
    @RequireToken
    @PostMapping("/getZxCrProjectEvaluationBadList")
    public ResponseEntity getZxCrProjectEvaluationBadList(@RequestBody(required = false) ZxCrProjectEvaluationBad zxCrProjectEvaluationBad) {
        return zxCrProjectEvaluationBadService.getZxCrProjectEvaluationBadListByCondition(zxCrProjectEvaluationBad);
    }

    @ApiOperation(value="查询详情严重不良行为考核表", notes="查询详情严重不良行为考核表")
    @ApiImplicitParam(name = "zxCrProjectEvaluationBad", value = "严重不良行为考核表entity", dataType = "ZxCrProjectEvaluationBad")
    @RequireToken
    @PostMapping("/getZxCrProjectEvaluationBadDetail")
    public ResponseEntity getZxCrProjectEvaluationBadDetail(@RequestBody(required = false) ZxCrProjectEvaluationBad zxCrProjectEvaluationBad) {
        return zxCrProjectEvaluationBadService.getZxCrProjectEvaluationBadDetail(zxCrProjectEvaluationBad);
    }

    @ApiOperation(value="新增严重不良行为考核表", notes="新增严重不良行为考核表")
    @ApiImplicitParam(name = "zxCrProjectEvaluationBad", value = "严重不良行为考核表entity", dataType = "ZxCrProjectEvaluationBad")
    @RequireToken
    @PostMapping("/addZxCrProjectEvaluationBad")
    public ResponseEntity addZxCrProjectEvaluationBad(@RequestBody(required = false) ZxCrProjectEvaluationBad zxCrProjectEvaluationBad) {
        return zxCrProjectEvaluationBadService.saveZxCrProjectEvaluationBad(zxCrProjectEvaluationBad);
    }

    @ApiOperation(value="更新严重不良行为考核表", notes="更新严重不良行为考核表")
    @ApiImplicitParam(name = "zxCrProjectEvaluationBad", value = "严重不良行为考核表entity", dataType = "ZxCrProjectEvaluationBad")
    @RequireToken
    @PostMapping("/updateZxCrProjectEvaluationBad")
    public ResponseEntity updateZxCrProjectEvaluationBad(@RequestBody(required = false) ZxCrProjectEvaluationBad zxCrProjectEvaluationBad) {
        return zxCrProjectEvaluationBadService.updateZxCrProjectEvaluationBad(zxCrProjectEvaluationBad);
    }

    @ApiOperation(value="删除严重不良行为考核表", notes="删除严重不良行为考核表")
    @ApiImplicitParam(name = "zxCrProjectEvaluationBadList", value = "严重不良行为考核表List", dataType = "List<ZxCrProjectEvaluationBad>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxCrProjectEvaluationBad")
    public ResponseEntity batchDeleteUpdateZxCrProjectEvaluationBad(@RequestBody(required = false) List<ZxCrProjectEvaluationBad> zxCrProjectEvaluationBadList) {
        return zxCrProjectEvaluationBadService.batchDeleteUpdateZxCrProjectEvaluationBad(zxCrProjectEvaluationBadList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    
    @ApiOperation(value="信用评价用到的查询", notes="信用评价用到的查询")
    @ApiImplicitParam(name = "zxCrProjectEvaluationBadOne", value = "严重不良行为考核表entity", dataType = "ZxCrProjectEvaluationBad")
    @RequireToken
    @PostMapping("/getZxCrProjectEvaluationBadDetailOne")
    public ResponseEntity getZxCrProjectEvaluationBadDetailOne(@RequestBody(required = false) ZxCrProjCreditBadBeha zxCrProjCreditBadBeha) {
        return zxCrProjectEvaluationBadService.getZxCrProjectEvaluationBadDetailOne(zxCrProjCreditBadBeha);
    }
}
