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
import com.apih5.mybatis.pojo.ZxCrProjCreditBadBeha;
import com.apih5.service.ZxCrProjCreditBadBehaService;

@RestController
public class ZxCrProjCreditBadBehaController {

    @Autowired(required = true)
    private ZxCrProjCreditBadBehaService zxCrProjCreditBadBehaService;

    @ApiOperation(value="查询项目信用评价严重不良行为", notes="查询项目信用评价严重不良行为")
    @ApiImplicitParam(name = "zxCrProjCreditBadBeha", value = "项目信用评价严重不良行为entity", dataType = "ZxCrProjCreditBadBeha")
    @RequireToken
    @PostMapping("/getZxCrProjCreditBadBehaList")
    public ResponseEntity getZxCrProjCreditBadBehaList(@RequestBody(required = false) ZxCrProjCreditBadBeha zxCrProjCreditBadBeha) {
        return zxCrProjCreditBadBehaService.getZxCrProjCreditBadBehaListByCondition(zxCrProjCreditBadBeha);
    }

    @ApiOperation(value="查询详情项目信用评价严重不良行为", notes="查询详情项目信用评价严重不良行为")
    @ApiImplicitParam(name = "zxCrProjCreditBadBeha", value = "项目信用评价严重不良行为entity", dataType = "ZxCrProjCreditBadBeha")
    @RequireToken
    @PostMapping("/getZxCrProjCreditBadBehaDetail")
    public ResponseEntity getZxCrProjCreditBadBehaDetail(@RequestBody(required = false) ZxCrProjCreditBadBeha zxCrProjCreditBadBeha) {
        return zxCrProjCreditBadBehaService.getZxCrProjCreditBadBehaDetail(zxCrProjCreditBadBeha);
    }

    @ApiOperation(value="新增项目信用评价严重不良行为", notes="新增项目信用评价严重不良行为")
    @ApiImplicitParam(name = "zxCrProjCreditBadBeha", value = "项目信用评价严重不良行为entity", dataType = "ZxCrProjCreditBadBeha")
    @RequireToken
    @PostMapping("/addZxCrProjCreditBadBeha")
    public ResponseEntity addZxCrProjCreditBadBeha(@RequestBody(required = false) ZxCrProjCreditBadBeha zxCrProjCreditBadBeha) {
        return zxCrProjCreditBadBehaService.saveZxCrProjCreditBadBeha(zxCrProjCreditBadBeha);
    }

    @ApiOperation(value="更新项目信用评价严重不良行为", notes="更新项目信用评价严重不良行为")
    @ApiImplicitParam(name = "zxCrProjCreditBadBeha", value = "项目信用评价严重不良行为entity", dataType = "ZxCrProjCreditBadBeha")
    @RequireToken
    @PostMapping("/updateZxCrProjCreditBadBeha")
    public ResponseEntity updateZxCrProjCreditBadBeha(@RequestBody(required = false) ZxCrProjCreditBadBeha zxCrProjCreditBadBeha) {
        return zxCrProjCreditBadBehaService.updateZxCrProjCreditBadBeha(zxCrProjCreditBadBeha);
    }

    @ApiOperation(value="删除项目信用评价严重不良行为", notes="删除项目信用评价严重不良行为")
    @ApiImplicitParam(name = "zxCrProjCreditBadBehaList", value = "项目信用评价严重不良行为List", dataType = "List<ZxCrProjCreditBadBeha>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxCrProjCreditBadBeha")
    public ResponseEntity batchDeleteUpdateZxCrProjCreditBadBeha(@RequestBody(required = false) List<ZxCrProjCreditBadBeha> zxCrProjCreditBadBehaList) {
        return zxCrProjCreditBadBehaService.batchDeleteUpdateZxCrProjCreditBadBeha(zxCrProjCreditBadBehaList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
