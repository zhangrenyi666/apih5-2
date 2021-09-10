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
import com.apih5.mybatis.pojo.ZjXmCqjxAssistantRange;
import com.apih5.service.ZjXmCqjxAssistantRangeService;

@RestController
public class ZjXmCqjxAssistantRangeController {

    @Autowired(required = true)
    private ZjXmCqjxAssistantRangeService zjXmCqjxAssistantRangeService;

    @ApiOperation(value="查询重庆绩效oa协作性考核范围", notes="查询重庆绩效oa协作性考核范围")
    @ApiImplicitParam(name = "zjXmCqjxAssistantRange", value = "重庆绩效oa协作性考核范围entity", dataType = "ZjXmCqjxAssistantRange")
    @RequireToken
    @PostMapping("/getZjXmCqjxAssistantRangeList")
    public ResponseEntity getZjXmCqjxAssistantRangeList(@RequestBody(required = false) ZjXmCqjxAssistantRange zjXmCqjxAssistantRange) {
        return zjXmCqjxAssistantRangeService.getZjXmCqjxAssistantRangeListByCondition(zjXmCqjxAssistantRange);
    }

    @ApiOperation(value="查询详情重庆绩效oa协作性考核范围", notes="查询详情重庆绩效oa协作性考核范围")
    @ApiImplicitParam(name = "zjXmCqjxAssistantRange", value = "重庆绩效oa协作性考核范围entity", dataType = "ZjXmCqjxAssistantRange")
    @RequireToken
    @PostMapping("/getZjXmCqjxAssistantRangeDetails")
    public ResponseEntity getZjXmCqjxAssistantRangeDetails(@RequestBody(required = false) ZjXmCqjxAssistantRange zjXmCqjxAssistantRange) {
        return zjXmCqjxAssistantRangeService.getZjXmCqjxAssistantRangeDetails(zjXmCqjxAssistantRange);
    }

    @ApiOperation(value="新增重庆绩效oa协作性考核范围", notes="新增重庆绩效oa协作性考核范围")
    @ApiImplicitParam(name = "zjXmCqjxAssistantRange", value = "重庆绩效oa协作性考核范围entity", dataType = "ZjXmCqjxAssistantRange")
    @RequireToken
    @PostMapping("/addZjXmCqjxAssistantRange")
    public ResponseEntity addZjXmCqjxAssistantRange(@RequestBody(required = false) ZjXmCqjxAssistantRange zjXmCqjxAssistantRange) {
        return zjXmCqjxAssistantRangeService.saveZjXmCqjxAssistantRange(zjXmCqjxAssistantRange);
    }

    @ApiOperation(value="更新重庆绩效oa协作性考核范围", notes="更新重庆绩效oa协作性考核范围")
    @ApiImplicitParam(name = "zjXmCqjxAssistantRange", value = "重庆绩效oa协作性考核范围entity", dataType = "ZjXmCqjxAssistantRange")
    @RequireToken
    @PostMapping("/updateZjXmCqjxAssistantRange")
    public ResponseEntity updateZjXmCqjxAssistantRange(@RequestBody(required = false) ZjXmCqjxAssistantRange zjXmCqjxAssistantRange) {
        return zjXmCqjxAssistantRangeService.updateZjXmCqjxAssistantRange(zjXmCqjxAssistantRange);
    }

    @ApiOperation(value="删除重庆绩效oa协作性考核范围", notes="删除重庆绩效oa协作性考核范围")
    @ApiImplicitParam(name = "zjXmCqjxAssistantRangeList", value = "重庆绩效oa协作性考核范围List", dataType = "List<ZjXmCqjxAssistantRange>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZjXmCqjxAssistantRange")
    public ResponseEntity batchDeleteUpdateZjXmCqjxAssistantRange(@RequestBody(required = false) List<ZjXmCqjxAssistantRange> zjXmCqjxAssistantRangeList) {
        return zjXmCqjxAssistantRangeService.batchDeleteUpdateZjXmCqjxAssistantRange(zjXmCqjxAssistantRangeList);
    }

}
