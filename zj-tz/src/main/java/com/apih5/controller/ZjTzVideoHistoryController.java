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
import com.apih5.mybatis.pojo.ZjTzVideoHistory;
import com.apih5.service.ZjTzVideoHistoryService;

@RestController
public class ZjTzVideoHistoryController {

    @Autowired(required = true)
    private ZjTzVideoHistoryService zjTzVideoHistoryService;

    @ApiOperation(value="查询宣贯视频浏览记录", notes="查询宣贯视频浏览记录")
    @ApiImplicitParam(name = "zjTzVideoHistory", value = "宣贯视频浏览记录entity", dataType = "ZjTzVideoHistory")
    @RequireToken
    @PostMapping("/getZjTzVideoHistoryList")
    public ResponseEntity getZjTzVideoHistoryList(@RequestBody(required = false) ZjTzVideoHistory zjTzVideoHistory) {
        return zjTzVideoHistoryService.getZjTzVideoHistoryListByCondition(zjTzVideoHistory);
    }

    @ApiOperation(value="查询详情宣贯视频浏览记录", notes="查询详情宣贯视频浏览记录")
    @ApiImplicitParam(name = "zjTzVideoHistory", value = "宣贯视频浏览记录entity", dataType = "ZjTzVideoHistory")
    @RequireToken
    @PostMapping("/getZjTzVideoHistoryDetails")
    public ResponseEntity getZjTzVideoHistoryDetails(@RequestBody(required = false) ZjTzVideoHistory zjTzVideoHistory) {
        return zjTzVideoHistoryService.getZjTzVideoHistoryDetails(zjTzVideoHistory);
    }

    @ApiOperation(value="新增宣贯视频浏览记录", notes="新增宣贯视频浏览记录")
    @ApiImplicitParam(name = "zjTzVideoHistory", value = "宣贯视频浏览记录entity", dataType = "ZjTzVideoHistory")
    @RequireToken
    @PostMapping("/addZjTzVideoHistory")
    public ResponseEntity addZjTzVideoHistory(@RequestBody(required = false) ZjTzVideoHistory zjTzVideoHistory) {
        return zjTzVideoHistoryService.saveZjTzVideoHistory(zjTzVideoHistory);
    }

    @ApiOperation(value="更新宣贯视频浏览记录", notes="更新宣贯视频浏览记录")
    @ApiImplicitParam(name = "zjTzVideoHistory", value = "宣贯视频浏览记录entity", dataType = "ZjTzVideoHistory")
    @RequireToken
    @PostMapping("/updateZjTzVideoHistory")
    public ResponseEntity updateZjTzVideoHistory(@RequestBody(required = false) ZjTzVideoHistory zjTzVideoHistory) {
        return zjTzVideoHistoryService.updateZjTzVideoHistory(zjTzVideoHistory);
    }

    @ApiOperation(value="删除宣贯视频浏览记录", notes="删除宣贯视频浏览记录")
    @ApiImplicitParam(name = "zjTzVideoHistoryList", value = "宣贯视频浏览记录List", dataType = "List<ZjTzVideoHistory>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZjTzVideoHistory")
    public ResponseEntity batchDeleteUpdateZjTzVideoHistory(@RequestBody(required = false) List<ZjTzVideoHistory> zjTzVideoHistoryList) {
        return zjTzVideoHistoryService.batchDeleteUpdateZjTzVideoHistory(zjTzVideoHistoryList);
    }

}
