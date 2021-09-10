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
import com.apih5.mybatis.pojo.ZxScGroupScheme;
import com.apih5.service.ZxScGroupSchemeService;

@RestController
public class ZxScGroupSchemeController {

    @Autowired(required = true)
    private ZxScGroupSchemeService zxScGroupSchemeService;

    @ApiOperation(value="查询施组审批发起", notes="查询施组审批发起")
    @ApiImplicitParam(name = "zxScGroupScheme", value = "施组审批发起entity", dataType = "ZxScGroupScheme")
    @RequireToken
    @PostMapping("/getZxScGroupSchemeList")
    public ResponseEntity getZxScGroupSchemeList(@RequestBody(required = false) ZxScGroupScheme zxScGroupScheme) {
        return zxScGroupSchemeService.getZxScGroupSchemeListByCondition(zxScGroupScheme);
    }

    @ApiOperation(value="查询详情施组审批发起", notes="查询详情施组审批发起")
    @ApiImplicitParam(name = "zxScGroupScheme", value = "施组审批发起entity", dataType = "ZxScGroupScheme")
    @RequireToken
    @PostMapping("/getZxScGroupSchemeDetail")
    public ResponseEntity getZxScGroupSchemeDetail(@RequestBody(required = false) ZxScGroupScheme zxScGroupScheme) {
        return zxScGroupSchemeService.getZxScGroupSchemeDetail(zxScGroupScheme);
    }

    @ApiOperation(value="新增施组审批发起", notes="新增施组审批发起")
    @ApiImplicitParam(name = "zxScGroupScheme", value = "施组审批发起entity", dataType = "ZxScGroupScheme")
    @RequireToken
    @PostMapping("/addZxScGroupScheme")
    public ResponseEntity addZxScGroupScheme(@RequestBody(required = false) ZxScGroupScheme zxScGroupScheme) {
        return zxScGroupSchemeService.saveZxScGroupScheme(zxScGroupScheme);
    }

    @ApiOperation(value="更新施组审批发起", notes="更新施组审批发起")
    @ApiImplicitParam(name = "zxScGroupScheme", value = "施组审批发起entity", dataType = "ZxScGroupScheme")
    @RequireToken
    @PostMapping("/updateZxScGroupScheme")
    public ResponseEntity updateZxScGroupScheme(@RequestBody(required = false) ZxScGroupScheme zxScGroupScheme) {
        return zxScGroupSchemeService.updateZxScGroupScheme(zxScGroupScheme);
    }

    @ApiOperation(value="删除施组审批发起", notes="删除施组审批发起")
    @ApiImplicitParam(name = "zxScGroupSchemeList", value = "施组审批发起List", dataType = "List<ZxScGroupScheme>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxScGroupScheme")
    public ResponseEntity batchDeleteUpdateZxScGroupScheme(@RequestBody(required = false) List<ZxScGroupScheme> zxScGroupSchemeList) {
        return zxScGroupSchemeService.batchDeleteUpdateZxScGroupScheme(zxScGroupSchemeList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

    @ApiOperation(value="施组评审编号", notes="施组评审编号")
    @ApiImplicitParam(name = "getZxScGroupSchemeSequence", value = "施组审批entity", dataType = "ZxScGroupScheme")
    @RequireToken
    @PostMapping("/getZxScGroupSchemeSequence")
    public ResponseEntity getZxScGroupSchemeSequence(@RequestBody(required = false) ZxScGroupScheme zxScGroupScheme) {
        return zxScGroupSchemeService.getZxScGroupSchemeSequence(zxScGroupScheme);
    }
    @ApiOperation(value="施组评审申请", notes="施组评审申请")
    @ApiImplicitParam(name = "getZxScGroupSchemeSequence", value = "施组审批entity", dataType = "ZxScGroupScheme")
    @RequireToken
    @PostMapping("/zxScGroupSchemeReviewApply")
    public ResponseEntity zxScGroupSchemeReviewApply(@RequestBody(required = false) ZxScGroupScheme zxScGroupScheme) {
        return zxScGroupSchemeService.zxScGroupSchemeReviewApply(zxScGroupScheme);
    }

}
