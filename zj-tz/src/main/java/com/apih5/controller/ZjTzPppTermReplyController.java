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
import com.apih5.mybatis.pojo.ZjTzPppTermReply;
import com.apih5.service.ZjTzPppTermReplyService;

@RestController
public class ZjTzPppTermReplyController {

    @Autowired(required = true)
    private ZjTzPppTermReplyService zjTzPppTermReplyService;

    @ApiOperation(value="查询ppp合同分析回复", notes="查询ppp合同分析回复")
    @ApiImplicitParam(name = "zjTzPppTermReply", value = "ppp合同分析回复entity", dataType = "ZjTzPppTermReply")
    @RequireToken
    @PostMapping("/getZjTzPppTermReplyList")
    public ResponseEntity getZjTzPppTermReplyList(@RequestBody(required = false) ZjTzPppTermReply zjTzPppTermReply) {
        return zjTzPppTermReplyService.getZjTzPppTermReplyListByCondition(zjTzPppTermReply);
    }

    @ApiOperation(value="查询详情ppp合同分析回复", notes="查询详情ppp合同分析回复")
    @ApiImplicitParam(name = "zjTzPppTermReply", value = "ppp合同分析回复entity", dataType = "ZjTzPppTermReply")
    @RequireToken
    @PostMapping("/getZjTzPppTermReplyDetails")
    public ResponseEntity getZjTzPppTermReplyDetails(@RequestBody(required = false) ZjTzPppTermReply zjTzPppTermReply) {
        return zjTzPppTermReplyService.getZjTzPppTermReplyDetails(zjTzPppTermReply);
    }

    @ApiOperation(value="新增ppp合同分析回复", notes="新增ppp合同分析回复")
    @ApiImplicitParam(name = "zjTzPppTermReply", value = "ppp合同分析回复entity", dataType = "ZjTzPppTermReply")
    @RequireToken
    @PostMapping("/addZjTzPppTermReply")
    public ResponseEntity addZjTzPppTermReply(@RequestBody(required = false) ZjTzPppTermReply zjTzPppTermReply) {
        return zjTzPppTermReplyService.saveZjTzPppTermReply(zjTzPppTermReply);
    }

    @ApiOperation(value="更新ppp合同分析回复", notes="更新ppp合同分析回复")
    @ApiImplicitParam(name = "zjTzPppTermReply", value = "ppp合同分析回复entity", dataType = "ZjTzPppTermReply")
    @RequireToken
    @PostMapping("/updateZjTzPppTermReply")
    public ResponseEntity updateZjTzPppTermReply(@RequestBody(required = false) ZjTzPppTermReply zjTzPppTermReply) {
        return zjTzPppTermReplyService.updateZjTzPppTermReply(zjTzPppTermReply);
    }

    @ApiOperation(value="删除ppp合同分析回复", notes="删除ppp合同分析回复")
    @ApiImplicitParam(name = "zjTzPppTermReplyList", value = "ppp合同分析回复List", dataType = "List<ZjTzPppTermReply>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZjTzPppTermReply")
    public ResponseEntity batchDeleteUpdateZjTzPppTermReply(@RequestBody(required = false) List<ZjTzPppTermReply> zjTzPppTermReplyList) {
        return zjTzPppTermReplyService.batchDeleteUpdateZjTzPppTermReply(zjTzPppTermReplyList);
    }
    
    @ApiOperation(value="报表导出ppp合同分析回复", notes="报表导出ppp合同分析回复")
    @ApiImplicitParam(name = "zjTzPppTermReply", value = "ppp合同分析回复entity", dataType = "ZjTzPppTermReply")
    @PostMapping("/ureportZjTzPppTermReplyList")
    public List<ZjTzPppTermReply> ureportZjTzPppTermReplyList(@RequestBody(required = false) ZjTzPppTermReply zjTzPppTermReply) {
        return zjTzPppTermReplyService.ureportZjTzPppTermReplyList(zjTzPppTermReply);
    }

}
