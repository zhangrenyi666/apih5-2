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
import com.apih5.mybatis.pojo.ZjTzFinanceMonth;
import com.apih5.service.ZjTzFinanceMonthService;

@RestController
public class ZjTzFinanceMonthController {

    @Autowired(required = true)
    private ZjTzFinanceMonthService zjTzFinanceMonthService;

    @ApiOperation(value="查询融资落实情况月报", notes="查询融资落实情况月报")
    @ApiImplicitParam(name = "zjTzFinanceMonth", value = "融资落实情况月报entity", dataType = "ZjTzFinanceMonth")
    @RequireToken
    @PostMapping("/getZjTzFinanceMonthList")
    public ResponseEntity getZjTzFinanceMonthList(@RequestBody(required = false) ZjTzFinanceMonth zjTzFinanceMonth) {
        return zjTzFinanceMonthService.getZjTzFinanceMonthListByCondition(zjTzFinanceMonth);
    }

    @ApiOperation(value="查询详情融资落实情况月报", notes="查询详情融资落实情况月报")
    @ApiImplicitParam(name = "zjTzFinanceMonth", value = "融资落实情况月报entity", dataType = "ZjTzFinanceMonth")
    @RequireToken
    @PostMapping("/getZjTzFinanceMonthDetails")
    public ResponseEntity getZjTzFinanceMonthDetails(@RequestBody(required = false) ZjTzFinanceMonth zjTzFinanceMonth) {
        return zjTzFinanceMonthService.getZjTzFinanceMonthDetails(zjTzFinanceMonth);
    }

    @ApiOperation(value="新增融资落实情况月报", notes="新增融资落实情况月报")
    @ApiImplicitParam(name = "zjTzFinanceMonth", value = "融资落实情况月报entity", dataType = "ZjTzFinanceMonth")
    @RequireToken
    @PostMapping("/addZjTzFinanceMonth")
    public ResponseEntity addZjTzFinanceMonth(@RequestBody(required = false) ZjTzFinanceMonth zjTzFinanceMonth) {
        return zjTzFinanceMonthService.saveZjTzFinanceMonth(zjTzFinanceMonth);
    }

    @ApiOperation(value="更新融资落实情况月报", notes="更新融资落实情况月报")
    @ApiImplicitParam(name = "zjTzFinanceMonth", value = "融资落实情况月报entity", dataType = "ZjTzFinanceMonth")
    @RequireToken
    @PostMapping("/updateZjTzFinanceMonth")
    public ResponseEntity updateZjTzFinanceMonth(@RequestBody(required = false) ZjTzFinanceMonth zjTzFinanceMonth) {
        return zjTzFinanceMonthService.updateZjTzFinanceMonth(zjTzFinanceMonth);
    }

    @ApiOperation(value="删除融资落实情况月报", notes="删除融资落实情况月报")
    @ApiImplicitParam(name = "zjTzFinanceMonthList", value = "融资落实情况月报List", dataType = "List<ZjTzFinanceMonth>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZjTzFinanceMonth")
    public ResponseEntity batchDeleteUpdateZjTzFinanceMonth(@RequestBody(required = false) List<ZjTzFinanceMonth> zjTzFinanceMonthList) {
        return zjTzFinanceMonthService.batchDeleteUpdateZjTzFinanceMonth(zjTzFinanceMonthList);
    }
    
    @ApiOperation(value="批量上报落实情况月报", notes="批量上报落实情况月报")
    @ApiImplicitParam(name = "zjTzFinanceMonthList", value = "融资落实情况月报List", dataType = "List<ZjTzFinanceMonth>")
    @RequireToken
    @PostMapping("/batchReleaseZjTzFinanceMonth")
    public ResponseEntity batchReleaseZjTzFinanceMonth(@RequestBody(required = false) List<ZjTzFinanceMonth> zjTzFinanceMonthList) {
        return zjTzFinanceMonthService.batchReleaseZjTzFinanceMonth(zjTzFinanceMonthList);
    }
    
    @ApiOperation(value="批量退回融资落实情况月报", notes="批量退回融资落实情况月报")
    @ApiImplicitParam(name = "zjTzFinanceMonthList", value = "融资落实情况月报List", dataType = "List<ZjTzFinanceMonth>")
    @RequireToken
    @PostMapping("/batchRecallZjTzFinanceMonth")
    public ResponseEntity batchRecallZjTzFinanceMonth(@RequestBody(required = false) List<ZjTzFinanceMonth> zjTzFinanceMonthList) {
        return zjTzFinanceMonthService.batchRecallZjTzFinanceMonth(zjTzFinanceMonthList);
    }

}
