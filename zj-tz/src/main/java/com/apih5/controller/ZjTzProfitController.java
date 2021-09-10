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
import com.apih5.mybatis.pojo.ZjTzProfit;
import com.apih5.service.ZjTzProfitService;

@RestController
public class ZjTzProfitController {

    @Autowired(required = true)
    private ZjTzProfitService zjTzProfitService;

    @ApiOperation(value="查询利润", notes="查询利润")
    @ApiImplicitParam(name = "zjTzProfit", value = "利润entity", dataType = "ZjTzProfit")
    @RequireToken
    @PostMapping("/getZjTzProfitList")
    public ResponseEntity getZjTzProfitList(@RequestBody(required = false) ZjTzProfit zjTzProfit) {
        return zjTzProfitService.getZjTzProfitListByCondition(zjTzProfit);
    }

    @ApiOperation(value="查询详情利润", notes="查询详情利润")
    @ApiImplicitParam(name = "zjTzProfit", value = "利润entity", dataType = "ZjTzProfit")
    @RequireToken
    @PostMapping("/getZjTzProfitDetails")
    public ResponseEntity getZjTzProfitDetails(@RequestBody(required = false) ZjTzProfit zjTzProfit) {
        return zjTzProfitService.getZjTzProfitDetails(zjTzProfit);
    }

    @ApiOperation(value="新增利润", notes="新增利润")
    @ApiImplicitParam(name = "zjTzProfit", value = "利润entity", dataType = "ZjTzProfit")
    @RequireToken
    @PostMapping("/addZjTzProfit")
    public ResponseEntity addZjTzProfit(@RequestBody(required = false) ZjTzProfit zjTzProfit) {
        return zjTzProfitService.saveZjTzProfit(zjTzProfit);
    }

    @ApiOperation(value="更新利润", notes="更新利润")
    @ApiImplicitParam(name = "zjTzProfit", value = "利润entity", dataType = "ZjTzProfit")
    @RequireToken
    @PostMapping("/updateZjTzProfit")
    public ResponseEntity updateZjTzProfit(@RequestBody(required = false) ZjTzProfit zjTzProfit) {
        return zjTzProfitService.updateZjTzProfit(zjTzProfit);
    }

    @ApiOperation(value="删除利润", notes="删除利润")
    @ApiImplicitParam(name = "zjTzProfitList", value = "利润List", dataType = "List<ZjTzProfit>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZjTzProfit")
    public ResponseEntity batchDeleteUpdateZjTzProfit(@RequestBody(required = false) List<ZjTzProfit> zjTzProfitList) {
        return zjTzProfitService.batchDeleteUpdateZjTzProfit(zjTzProfitList);
    }

}
