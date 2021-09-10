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
import com.apih5.mybatis.pojo.ZjTzRunSchemeOpinion;
import com.apih5.service.ZjTzRunSchemeOpinionService;

@RestController
public class ZjTzRunSchemeOpinionController {

    @Autowired(required = true)
    private ZjTzRunSchemeOpinionService zjTzRunSchemeOpinionService;

    @ApiOperation(value="查询运营方案评审意见", notes="查询运营方案评审意见")
    @ApiImplicitParam(name = "zjTzRunSchemeOpinion", value = "运营方案评审意见entity", dataType = "ZjTzRunSchemeOpinion")
    @RequireToken
    @PostMapping("/getZjTzRunSchemeOpinionList")
    public ResponseEntity getZjTzRunSchemeOpinionList(@RequestBody(required = false) ZjTzRunSchemeOpinion zjTzRunSchemeOpinion) {
        return zjTzRunSchemeOpinionService.getZjTzRunSchemeOpinionListByCondition(zjTzRunSchemeOpinion);
    }

    @ApiOperation(value="查询详情运营方案评审意见", notes="查询详情运营方案评审意见")
    @ApiImplicitParam(name = "zjTzRunSchemeOpinion", value = "运营方案评审意见entity", dataType = "ZjTzRunSchemeOpinion")
    @RequireToken
    @PostMapping("/getZjTzRunSchemeOpinionDetails")
    public ResponseEntity getZjTzRunSchemeOpinionDetails(@RequestBody(required = false) ZjTzRunSchemeOpinion zjTzRunSchemeOpinion) {
        return zjTzRunSchemeOpinionService.getZjTzRunSchemeOpinionDetails(zjTzRunSchemeOpinion);
    }

    @ApiOperation(value="新增运营方案评审意见", notes="新增运营方案评审意见")
    @ApiImplicitParam(name = "zjTzRunSchemeOpinion", value = "运营方案评审意见entity", dataType = "ZjTzRunSchemeOpinion")
    @RequireToken
    @PostMapping("/addZjTzRunSchemeOpinion")
    public ResponseEntity addZjTzRunSchemeOpinion(@RequestBody(required = false) ZjTzRunSchemeOpinion zjTzRunSchemeOpinion) {
        return zjTzRunSchemeOpinionService.saveZjTzRunSchemeOpinion(zjTzRunSchemeOpinion);
    }

    @ApiOperation(value="更新运营方案评审意见", notes="更新运营方案评审意见")
    @ApiImplicitParam(name = "zjTzRunSchemeOpinion", value = "运营方案评审意见entity", dataType = "ZjTzRunSchemeOpinion")
    @RequireToken
    @PostMapping("/updateZjTzRunSchemeOpinion")
    public ResponseEntity updateZjTzRunSchemeOpinion(@RequestBody(required = false) ZjTzRunSchemeOpinion zjTzRunSchemeOpinion) {
        return zjTzRunSchemeOpinionService.updateZjTzRunSchemeOpinion(zjTzRunSchemeOpinion);
    }

    @ApiOperation(value="删除运营方案评审意见", notes="删除运营方案评审意见")
    @ApiImplicitParam(name = "zjTzRunSchemeOpinionList", value = "运营方案评审意见List", dataType = "List<ZjTzRunSchemeOpinion>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZjTzRunSchemeOpinion")
    public ResponseEntity batchDeleteUpdateZjTzRunSchemeOpinion(@RequestBody(required = false) List<ZjTzRunSchemeOpinion> zjTzRunSchemeOpinionList) {
        return zjTzRunSchemeOpinionService.batchDeleteUpdateZjTzRunSchemeOpinion(zjTzRunSchemeOpinionList);
    }
    
}
