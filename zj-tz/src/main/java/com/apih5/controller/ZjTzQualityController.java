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
import com.apih5.mybatis.pojo.ZjTzQuality;
import com.apih5.service.ZjTzQualityService;

@RestController
public class ZjTzQualityController {

    @Autowired(required = true)
    private ZjTzQualityService zjTzQualityService;

    @ApiOperation(value="查询资质", notes="查询资质")
    @ApiImplicitParam(name = "zjTzQuality", value = "资质entity", dataType = "ZjTzQuality")
    @RequireToken
    @PostMapping("/getZjTzQualityList")
    public ResponseEntity getZjTzQualityList(@RequestBody(required = false) ZjTzQuality zjTzQuality) {
        return zjTzQualityService.getZjTzQualityListByCondition(zjTzQuality);
    }

    @ApiOperation(value="查询详情资质", notes="查询详情资质")
    @ApiImplicitParam(name = "zjTzQuality", value = "资质entity", dataType = "ZjTzQuality")
    @RequireToken
    @PostMapping("/getZjTzQualityDetails")
    public ResponseEntity getZjTzQualityDetails(@RequestBody(required = false) ZjTzQuality zjTzQuality) {
        return zjTzQualityService.getZjTzQualityDetails(zjTzQuality);
    }

    @ApiOperation(value="新增资质", notes="新增资质")
    @ApiImplicitParam(name = "zjTzQuality", value = "资质entity", dataType = "ZjTzQuality")
    @RequireToken
    @PostMapping("/addZjTzQuality")
    public ResponseEntity addZjTzQuality(@RequestBody(required = false) ZjTzQuality zjTzQuality) {
        return zjTzQualityService.saveZjTzQuality(zjTzQuality);
    }

    @ApiOperation(value="更新资质", notes="更新资质")
    @ApiImplicitParam(name = "zjTzQuality", value = "资质entity", dataType = "ZjTzQuality")
    @RequireToken
    @PostMapping("/updateZjTzQuality")
    public ResponseEntity updateZjTzQuality(@RequestBody(required = false) ZjTzQuality zjTzQuality) {
        return zjTzQualityService.updateZjTzQuality(zjTzQuality);
    }

    @ApiOperation(value="删除资质", notes="删除资质")
    @ApiImplicitParam(name = "zjTzQualityList", value = "资质List", dataType = "List<ZjTzQuality>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZjTzQuality")
    public ResponseEntity batchDeleteUpdateZjTzQuality(@RequestBody(required = false) List<ZjTzQuality> zjTzQualityList) {
        return zjTzQualityService.batchDeleteUpdateZjTzQuality(zjTzQualityList);
    }

}
