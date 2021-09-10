package com.apih5.controller;

import java.util.List;

import com.apih5.mybatis.pojo.ZxCrProjCreditScore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import com.apih5.framework.annotation.RequireToken;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxCrProjectEvaluationScore;
import com.apih5.service.ZxCrProjectEvaluationScoreService;

@RestController
public class ZxCrProjectEvaluationScoreController {

    @Autowired(required = true)
    private ZxCrProjectEvaluationScoreService zxCrProjectEvaluationScoreService;

    @ApiOperation(value="查询打分考核表", notes="查询打分考核表")
    @ApiImplicitParam(name = "zxCrProjectEvaluationScore", value = "打分考核表entity", dataType = "ZxCrProjectEvaluationScore")
    @RequireToken
    @PostMapping("/getZxCrProjectEvaluationScoreList")
    public ResponseEntity getZxCrProjectEvaluationScoreList(@RequestBody(required = false) ZxCrProjectEvaluationScore zxCrProjectEvaluationScore) {
        return zxCrProjectEvaluationScoreService.getZxCrProjectEvaluationScoreListByCondition(zxCrProjectEvaluationScore);
    }

    @ApiOperation(value="查询详情打分考核表", notes="查询详情打分考核表")
    @ApiImplicitParam(name = "zxCrProjectEvaluationScore", value = "打分考核表entity", dataType = "ZxCrProjectEvaluationScore")
    @RequireToken
    @PostMapping("/getZxCrProjectEvaluationScoreDetail")
    public ResponseEntity getZxCrProjectEvaluationScoreDetail(@RequestBody(required = false) ZxCrProjectEvaluationScore zxCrProjectEvaluationScore) {
        return zxCrProjectEvaluationScoreService.getZxCrProjectEvaluationScoreDetail(zxCrProjectEvaluationScore);
    }

    @ApiOperation(value="新增打分考核表", notes="新增打分考核表")
    @ApiImplicitParam(name = "zxCrProjectEvaluationScore", value = "打分考核表entity", dataType = "ZxCrProjectEvaluationScore")
    @RequireToken
    @PostMapping("/addZxCrProjectEvaluationScore")
    public ResponseEntity addZxCrProjectEvaluationScore(@RequestBody(required = false) ZxCrProjectEvaluationScore zxCrProjectEvaluationScore) {
        return zxCrProjectEvaluationScoreService.saveZxCrProjectEvaluationScore(zxCrProjectEvaluationScore);
    }

    @ApiOperation(value="更新打分考核表", notes="更新打分考核表")
    @ApiImplicitParam(name = "zxCrProjectEvaluationScore", value = "打分考核表entity", dataType = "ZxCrProjectEvaluationScore")
    @RequireToken
    @PostMapping("/updateZxCrProjectEvaluationScore")
    public ResponseEntity updateZxCrProjectEvaluationScore(@RequestBody(required = false) ZxCrProjectEvaluationScore zxCrProjectEvaluationScore) {
        return zxCrProjectEvaluationScoreService.updateZxCrProjectEvaluationScore(zxCrProjectEvaluationScore);
    }

    @ApiOperation(value="删除打分考核表", notes="删除打分考核表")
    @ApiImplicitParam(name = "zxCrProjectEvaluationScoreList", value = "打分考核表List", dataType = "List<ZxCrProjectEvaluationScore>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxCrProjectEvaluationScore")
    public ResponseEntity batchDeleteUpdateZxCrProjectEvaluationScore(@RequestBody(required = false) List<ZxCrProjectEvaluationScore> zxCrProjectEvaluationScoreList) {
        return zxCrProjectEvaluationScoreService.batchDeleteUpdateZxCrProjectEvaluationScore(zxCrProjectEvaluationScoreList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    
    //信用评价下拉列表用到查询
    @ApiOperation(value="信用评价下拉列表用到查询", notes="信用评价下拉列表用到查询")
    @ApiImplicitParam(name = "zxCrProjectEvaluationScoreOne", value = "打分考核表entity", dataType = "ZxCrProjectEvaluationScore")
    @RequireToken
    @PostMapping("/getZxCrProjectEvaluationScoreDetailOne")
    public ResponseEntity getZxCrProjectEvaluationScoreDetailOne(@RequestBody(required = false) ZxCrProjCreditScore zxCrProjCreditScore) {
        return zxCrProjectEvaluationScoreService.getZxCrProjectEvaluationScoreDetailOne(zxCrProjCreditScore);
    }
}
