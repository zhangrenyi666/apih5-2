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
import com.apih5.mybatis.pojo.ZxCrProjCreditScore;
import com.apih5.service.ZxCrProjCreditScoreService;

@RestController
public class ZxCrProjCreditScoreController {

    @Autowired(required = true)
    private ZxCrProjCreditScoreService zxCrProjCreditScoreService;

    @ApiOperation(value="查询项目信用评价考核标准库", notes="查询项目信用评价考核标准库")
    @ApiImplicitParam(name = "zxCrProjCreditScore", value = "项目信用评价考核标准库entity", dataType = "ZxCrProjCreditScore")
    @RequireToken
    @PostMapping("/getZxCrProjCreditScoreList")
    public ResponseEntity getZxCrProjCreditScoreList(@RequestBody(required = false) ZxCrProjCreditScore zxCrProjCreditScore) {
        return zxCrProjCreditScoreService.getZxCrProjCreditScoreListByCondition(zxCrProjCreditScore);
    }

    @ApiOperation(value="查询详情项目信用评价考核标准库", notes="查询详情项目信用评价考核标准库")
    @ApiImplicitParam(name = "zxCrProjCreditScore", value = "项目信用评价考核标准库entity", dataType = "ZxCrProjCreditScore")
    @RequireToken
    @PostMapping("/getZxCrProjCreditScoreDetail")
    public ResponseEntity getZxCrProjCreditScoreDetail(@RequestBody(required = false) ZxCrProjCreditScore zxCrProjCreditScore) {
        return zxCrProjCreditScoreService.getZxCrProjCreditScoreDetail(zxCrProjCreditScore);
    }

    @ApiOperation(value="新增项目信用评价考核标准库", notes="新增项目信用评价考核标准库")
    @ApiImplicitParam(name = "zxCrProjCreditScore", value = "项目信用评价考核标准库entity", dataType = "ZxCrProjCreditScore")
    @RequireToken
    @PostMapping("/addZxCrProjCreditScore")
    public ResponseEntity addZxCrProjCreditScore(@RequestBody(required = false) ZxCrProjCreditScore zxCrProjCreditScore) {
        return zxCrProjCreditScoreService.saveZxCrProjCreditScore(zxCrProjCreditScore);
    }

    @ApiOperation(value="更新项目信用评价考核标准库", notes="更新项目信用评价考核标准库")
    @ApiImplicitParam(name = "zxCrProjCreditScore", value = "项目信用评价考核标准库entity", dataType = "ZxCrProjCreditScore")
    @RequireToken
    @PostMapping("/updateZxCrProjCreditScore")
    public ResponseEntity updateZxCrProjCreditScore(@RequestBody(required = false) ZxCrProjCreditScore zxCrProjCreditScore) {
        return zxCrProjCreditScoreService.updateZxCrProjCreditScore(zxCrProjCreditScore);
    }

    @ApiOperation(value="删除项目信用评价考核标准库", notes="删除项目信用评价考核标准库")
    @ApiImplicitParam(name = "zxCrProjCreditScoreList", value = "项目信用评价考核标准库List", dataType = "List<ZxCrProjCreditScore>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxCrProjCreditScore")
    public ResponseEntity batchDeleteUpdateZxCrProjCreditScore(@RequestBody(required = false) List<ZxCrProjCreditScore> zxCrProjCreditScoreList) {
        return zxCrProjCreditScoreService.batchDeleteUpdateZxCrProjCreditScore(zxCrProjCreditScoreList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
