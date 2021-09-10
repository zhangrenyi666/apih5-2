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
import com.apih5.mybatis.pojo.ZjTzPolicyCountryReply;
import com.apih5.service.ZjTzPolicyCountryReplyService;

@RestController
public class ZjTzPolicyCountryReplyController {

    @Autowired(required = true)
    private ZjTzPolicyCountryReplyService zjTzPolicyCountryReplyService;

    @ApiOperation(value="查询宏观政策国家回复", notes="查询宏观政策国家回复")
    @ApiImplicitParam(name = "zjTzPolicyCountryReply", value = "宏观政策国家回复entity", dataType = "ZjTzPolicyCountryReply")
    @RequireToken
    @PostMapping("/getZjTzPolicyCountryReplyList")
    public ResponseEntity getZjTzPolicyCountryReplyList(@RequestBody(required = false) ZjTzPolicyCountryReply zjTzPolicyCountryReply) {
        return zjTzPolicyCountryReplyService.getZjTzPolicyCountryReplyListByCondition(zjTzPolicyCountryReply);
    }

    @ApiOperation(value="查询详情宏观政策国家回复", notes="查询详情宏观政策国家回复")
    @ApiImplicitParam(name = "zjTzPolicyCountryReply", value = "宏观政策国家回复entity", dataType = "ZjTzPolicyCountryReply")
    @RequireToken
    @PostMapping("/getZjTzPolicyCountryReplyDetails")
    public ResponseEntity getZjTzPolicyCountryReplyDetails(@RequestBody(required = false) ZjTzPolicyCountryReply zjTzPolicyCountryReply) {
        return zjTzPolicyCountryReplyService.getZjTzPolicyCountryReplyDetails(zjTzPolicyCountryReply);
    }

    @ApiOperation(value="新增宏观政策国家回复", notes="新增宏观政策国家回复")
    @ApiImplicitParam(name = "zjTzPolicyCountryReply", value = "宏观政策国家回复entity", dataType = "ZjTzPolicyCountryReply")
    @RequireToken
    @PostMapping("/addZjTzPolicyCountryReply")
    public ResponseEntity addZjTzPolicyCountryReply(@RequestBody(required = false) ZjTzPolicyCountryReply zjTzPolicyCountryReply) {
        return zjTzPolicyCountryReplyService.saveZjTzPolicyCountryReply(zjTzPolicyCountryReply);
    }

    @ApiOperation(value="更新宏观政策国家回复", notes="更新宏观政策国家回复")
    @ApiImplicitParam(name = "zjTzPolicyCountryReply", value = "宏观政策国家回复entity", dataType = "ZjTzPolicyCountryReply")
    @RequireToken
    @PostMapping("/updateZjTzPolicyCountryReply")
    public ResponseEntity updateZjTzPolicyCountryReply(@RequestBody(required = false) ZjTzPolicyCountryReply zjTzPolicyCountryReply) {
        return zjTzPolicyCountryReplyService.updateZjTzPolicyCountryReply(zjTzPolicyCountryReply);
    }

    @ApiOperation(value="删除宏观政策国家回复", notes="删除宏观政策国家回复")
    @ApiImplicitParam(name = "zjTzPolicyCountryReplyList", value = "宏观政策国家回复List", dataType = "List<ZjTzPolicyCountryReply>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZjTzPolicyCountryReply")
    public ResponseEntity batchDeleteUpdateZjTzPolicyCountryReply(@RequestBody(required = false) List<ZjTzPolicyCountryReply> zjTzPolicyCountryReplyList) {
        return zjTzPolicyCountryReplyService.batchDeleteUpdateZjTzPolicyCountryReply(zjTzPolicyCountryReplyList);
    }

}
