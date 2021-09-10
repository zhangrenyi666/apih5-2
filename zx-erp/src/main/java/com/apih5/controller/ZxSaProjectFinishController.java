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
import com.apih5.mybatis.pojo.ZxSaProjectFinish;
import com.apih5.service.ZxSaProjectFinishService;

@RestController
public class ZxSaProjectFinishController {

    @Autowired(required = true)
    private ZxSaProjectFinishService zxSaProjectFinishService;

    @ApiOperation(value="查询完工审核", notes="查询完工审核")
    @ApiImplicitParam(name = "zxSaProjectFinish", value = "完工审核entity", dataType = "ZxSaProjectFinish")
    @RequireToken
    @PostMapping("/getZxSaProjectFinishList")
    public ResponseEntity getZxSaProjectFinishList(@RequestBody(required = false) ZxSaProjectFinish zxSaProjectFinish) {
        return zxSaProjectFinishService.getZxSaProjectFinishListByCondition(zxSaProjectFinish);
    }

    @ApiOperation(value="查询详情完工审核", notes="查询详情完工审核")
    @ApiImplicitParam(name = "zxSaProjectFinish", value = "完工审核entity", dataType = "ZxSaProjectFinish")
    @RequireToken
    @PostMapping("/getZxSaProjectFinishDetails")
    public ResponseEntity getZxSaProjectFinishDetails(@RequestBody(required = false) ZxSaProjectFinish zxSaProjectFinish) {
        return zxSaProjectFinishService.getZxSaProjectFinishDetails(zxSaProjectFinish);
    }

    @ApiOperation(value="新增完工审核", notes="新增完工审核")
    @ApiImplicitParam(name = "zxSaProjectFinish", value = "完工审核entity", dataType = "ZxSaProjectFinish")
    @RequireToken
    @PostMapping("/addZxSaProjectFinish")
    public ResponseEntity addZxSaProjectFinish(@RequestBody(required = false) ZxSaProjectFinish zxSaProjectFinish) {
        return zxSaProjectFinishService.saveZxSaProjectFinish(zxSaProjectFinish);
    }

    @ApiOperation(value="更新完工审核", notes="更新完工审核")
    @ApiImplicitParam(name = "zxSaProjectFinish", value = "完工审核entity", dataType = "ZxSaProjectFinish")
    @RequireToken
    @PostMapping("/updateZxSaProjectFinish")
    public ResponseEntity updateZxSaProjectFinish(@RequestBody(required = false) ZxSaProjectFinish zxSaProjectFinish) {
        return zxSaProjectFinishService.updateZxSaProjectFinish(zxSaProjectFinish);
    }

    @ApiOperation(value="删除完工审核", notes="删除完工审核")
    @ApiImplicitParam(name = "zxSaProjectFinishList", value = "完工审核List", dataType = "List<ZxSaProjectFinish>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxSaProjectFinish")
    public ResponseEntity batchDeleteUpdateZxSaProjectFinish(@RequestBody(required = false) List<ZxSaProjectFinish> zxSaProjectFinishList) {
        return zxSaProjectFinishService.batchDeleteUpdateZxSaProjectFinish(zxSaProjectFinishList);
    }
    
    @ApiOperation(value="同步完工审核", notes="同步完工审核")
    @ApiImplicitParam(name = "zxSaProjectFinish", value = "完工审核", dataType = "ZxSaProjectFinish")
    @RequireToken
    @PostMapping("/syncZxSaProjectFinish")
    public ResponseEntity syncZxSaProjectFinish(@RequestBody(required = false) ZxSaProjectFinish zxSaProjectFinish) {
    	return zxSaProjectFinishService.syncZxSaProjectFinish(zxSaProjectFinish);
    }
    
    @ApiOperation(value="获取未完工列表", notes="获取未完工列表")
    @ApiImplicitParam(name = "zxSaProjectFinish", value = "完工审核", dataType = "ZxSaProjectFinish")
    @RequireToken
    @PostMapping("/getZxSaProjectUnFinishList")
    public ResponseEntity getZxSaProjectUnFinishList(@RequestBody(required = false) ZxSaProjectFinish zxSaProjectFinish) {
    	return zxSaProjectFinishService.getZxSaProjectUnFinishList(zxSaProjectFinish);
    }

}
