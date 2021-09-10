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
import com.apih5.mybatis.pojo.ZjTzDesignAdvistoryUnitStandard;
import com.apih5.service.ZjTzDesignAdvistoryUnitStandardService;

@RestController
public class ZjTzDesignAdvistoryUnitStandardController {

    @Autowired(required = true)
    private ZjTzDesignAdvistoryUnitStandardService zjTzDesignAdvistoryUnitStandardService;

    @ApiOperation(value="查询设计、咨询单位标准化", notes="查询设计、咨询单位标准化")
    @ApiImplicitParam(name = "zjTzDesignAdvistoryUnitStandard", value = "设计、咨询单位标准化entity", dataType = "ZjTzDesignAdvistoryUnitStandard")
    @RequireToken
    @PostMapping("/getZjTzDesignAdvistoryUnitStandardList")
    public ResponseEntity getZjTzDesignAdvistoryUnitStandardList(@RequestBody(required = false) ZjTzDesignAdvistoryUnitStandard zjTzDesignAdvistoryUnitStandard) {
        return zjTzDesignAdvistoryUnitStandardService.getZjTzDesignAdvistoryUnitStandardListByCondition(zjTzDesignAdvistoryUnitStandard);
    }

    @ApiOperation(value="查询详情设计、咨询单位标准化", notes="查询详情设计、咨询单位标准化")
    @ApiImplicitParam(name = "zjTzDesignAdvistoryUnitStandard", value = "设计、咨询单位标准化entity", dataType = "ZjTzDesignAdvistoryUnitStandard")
    @RequireToken
    @PostMapping("/getZjTzDesignAdvistoryUnitStandardDetails")
    public ResponseEntity getZjTzDesignAdvistoryUnitStandardDetails(@RequestBody(required = false) ZjTzDesignAdvistoryUnitStandard zjTzDesignAdvistoryUnitStandard) {
        return zjTzDesignAdvistoryUnitStandardService.getZjTzDesignAdvistoryUnitStandardDetails(zjTzDesignAdvistoryUnitStandard);
    }

    @ApiOperation(value="新增设计、咨询单位标准化", notes="新增设计、咨询单位标准化")
    @ApiImplicitParam(name = "zjTzDesignAdvistoryUnitStandard", value = "设计、咨询单位标准化entity", dataType = "ZjTzDesignAdvistoryUnitStandard")
    @RequireToken
    @PostMapping("/addZjTzDesignAdvistoryUnitStandard")
    public ResponseEntity addZjTzDesignAdvistoryUnitStandard(@RequestBody(required = false) ZjTzDesignAdvistoryUnitStandard zjTzDesignAdvistoryUnitStandard) {
        return zjTzDesignAdvistoryUnitStandardService.saveZjTzDesignAdvistoryUnitStandard(zjTzDesignAdvistoryUnitStandard);
    }

    @ApiOperation(value="更新设计、咨询单位标准化", notes="更新设计、咨询单位标准化")
    @ApiImplicitParam(name = "zjTzDesignAdvistoryUnitStandard", value = "设计、咨询单位标准化entity", dataType = "ZjTzDesignAdvistoryUnitStandard")
    @RequireToken
    @PostMapping("/updateZjTzDesignAdvistoryUnitStandard")
    public ResponseEntity updateZjTzDesignAdvistoryUnitStandard(@RequestBody(required = false) ZjTzDesignAdvistoryUnitStandard zjTzDesignAdvistoryUnitStandard) {
        return zjTzDesignAdvistoryUnitStandardService.updateZjTzDesignAdvistoryUnitStandard(zjTzDesignAdvistoryUnitStandard);
    }

    @ApiOperation(value="删除设计、咨询单位标准化", notes="删除设计、咨询单位标准化")
    @ApiImplicitParam(name = "zjTzDesignAdvistoryUnitStandardList", value = "设计、咨询单位标准化List", dataType = "List<ZjTzDesignAdvistoryUnitStandard>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZjTzDesignAdvistoryUnitStandard")
    public ResponseEntity batchDeleteUpdateZjTzDesignAdvistoryUnitStandard(@RequestBody(required = false) List<ZjTzDesignAdvistoryUnitStandard> zjTzDesignAdvistoryUnitStandardList) {
        return zjTzDesignAdvistoryUnitStandardService.batchDeleteUpdateZjTzDesignAdvistoryUnitStandard(zjTzDesignAdvistoryUnitStandardList);
    }
    
    @ApiOperation(value="设置设计、咨询单位标准化推荐库", notes="设置设计、咨询单位标准化推荐库")
    @ApiImplicitParam(name = "zjTzDesignAdvistoryUnitStandard", value = "设计、咨询单位标准化entity", dataType = "ZjTzDesignAdvistoryUnitStandard")
    @RequireToken
    @PostMapping("/setZjTzDesignAdvistoryUnitStandardLibrary")
    public ResponseEntity setZjTzDesignAdvistoryUnitStandardLibrary(@RequestBody(required = false) ZjTzDesignAdvistoryUnitStandard zjTzDesignAdvistoryUnitStandard) {
        return zjTzDesignAdvistoryUnitStandardService.setZjTzDesignAdvistoryUnitStandardLibrary(zjTzDesignAdvistoryUnitStandard);
    }
    
    @ApiOperation(value="查询设计、咨询单位标准化困难化", notes="查询设计、咨询单位标准化困难化")
    @ApiImplicitParam(name = "getZjTzDesignAdvistoryUnitStandardListForHard", value = "设计、咨询单位标准化entity", dataType = "ZjTzDesignAdvistoryUnitStandard")
    @RequireToken
    @PostMapping("/getZjTzDesignAdvistoryUnitStandardListForHard")
    public ResponseEntity getZjTzDesignAdvistoryUnitStandardListForSimple(@RequestBody(required = false) ZjTzDesignAdvistoryUnitStandard zjTzDesignAdvistoryUnitStandard) {
        return zjTzDesignAdvistoryUnitStandardService.getZjTzDesignAdvistoryUnitStandardListForHard(zjTzDesignAdvistoryUnitStandard);
    }

}
