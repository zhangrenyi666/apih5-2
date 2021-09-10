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
import com.apih5.mybatis.pojo.ZjTzCash;
import com.apih5.service.ZjTzCashService;

@RestController
public class ZjTzCashController {

    @Autowired(required = true)
    private ZjTzCashService zjTzCashService;

    @ApiOperation(value="查询现金流量", notes="查询现金流量")
    @ApiImplicitParam(name = "zjTzCash", value = "现金流量entity", dataType = "ZjTzCash")
    @RequireToken
    @PostMapping("/getZjTzCashList")
    public ResponseEntity getZjTzCashList(@RequestBody(required = false) ZjTzCash zjTzCash) {
        return zjTzCashService.getZjTzCashListByCondition(zjTzCash);
    }

    @ApiOperation(value="查询详情现金流量", notes="查询详情现金流量")
    @ApiImplicitParam(name = "zjTzCash", value = "现金流量entity", dataType = "ZjTzCash")
    @RequireToken
    @PostMapping("/getZjTzCashDetails")
    public ResponseEntity getZjTzCashDetails(@RequestBody(required = false) ZjTzCash zjTzCash) {
        return zjTzCashService.getZjTzCashDetails(zjTzCash);
    }

    @ApiOperation(value="新增现金流量", notes="新增现金流量")
    @ApiImplicitParam(name = "zjTzCash", value = "现金流量entity", dataType = "ZjTzCash")
    @RequireToken
    @PostMapping("/addZjTzCash")
    public ResponseEntity addZjTzCash(@RequestBody(required = false) ZjTzCash zjTzCash) {
        return zjTzCashService.saveZjTzCash(zjTzCash);
    }

    @ApiOperation(value="更新现金流量", notes="更新现金流量")
    @ApiImplicitParam(name = "zjTzCash", value = "现金流量entity", dataType = "ZjTzCash")
    @RequireToken
    @PostMapping("/updateZjTzCash")
    public ResponseEntity updateZjTzCash(@RequestBody(required = false) ZjTzCash zjTzCash) {
        return zjTzCashService.updateZjTzCash(zjTzCash);
    }

    @ApiOperation(value="删除现金流量", notes="删除现金流量")
    @ApiImplicitParam(name = "zjTzCashList", value = "现金流量List", dataType = "List<ZjTzCash>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZjTzCash")
    public ResponseEntity batchDeleteUpdateZjTzCash(@RequestBody(required = false) List<ZjTzCash> zjTzCashList) {
        return zjTzCashService.batchDeleteUpdateZjTzCash(zjTzCashList);
    }

}
