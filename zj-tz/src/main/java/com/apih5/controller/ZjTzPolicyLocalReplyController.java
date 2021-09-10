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
import com.apih5.mybatis.pojo.ZjTzPolicyLocalReply;
import com.apih5.service.ZjTzPolicyLocalReplyService;

@RestController
public class ZjTzPolicyLocalReplyController {

    @Autowired(required = true)
    private ZjTzPolicyLocalReplyService zjTzPolicyLocalReplyService;

    @ApiOperation(value="查询宏观政策地方回复", notes="查询宏观政策地方回复")
    @ApiImplicitParam(name = "zjTzPolicyLocalReply", value = "宏观政策地方回复entity", dataType = "ZjTzPolicyLocalReply")
    @RequireToken
    @PostMapping("/getZjTzPolicyLocalReplyList")
    public ResponseEntity getZjTzPolicyLocalReplyList(@RequestBody(required = false) ZjTzPolicyLocalReply zjTzPolicyLocalReply) {
        return zjTzPolicyLocalReplyService.getZjTzPolicyLocalReplyListByCondition(zjTzPolicyLocalReply);
    }

    @ApiOperation(value="查询详情宏观政策地方回复", notes="查询详情宏观政策地方回复")
    @ApiImplicitParam(name = "zjTzPolicyLocalReply", value = "宏观政策地方回复entity", dataType = "ZjTzPolicyLocalReply")
    @RequireToken
    @PostMapping("/getZjTzPolicyLocalReplyDetails")
    public ResponseEntity getZjTzPolicyLocalReplyDetails(@RequestBody(required = false) ZjTzPolicyLocalReply zjTzPolicyLocalReply) {
        return zjTzPolicyLocalReplyService.getZjTzPolicyLocalReplyDetails(zjTzPolicyLocalReply);
    }

    @ApiOperation(value="新增宏观政策地方回复", notes="新增宏观政策地方回复")
    @ApiImplicitParam(name = "zjTzPolicyLocalReply", value = "宏观政策地方回复entity", dataType = "ZjTzPolicyLocalReply")
    @RequireToken
    @PostMapping("/addZjTzPolicyLocalReply")
    public ResponseEntity addZjTzPolicyLocalReply(@RequestBody(required = false) ZjTzPolicyLocalReply zjTzPolicyLocalReply) {
        return zjTzPolicyLocalReplyService.saveZjTzPolicyLocalReply(zjTzPolicyLocalReply);
    }

    @ApiOperation(value="更新宏观政策地方回复", notes="更新宏观政策地方回复")
    @ApiImplicitParam(name = "zjTzPolicyLocalReply", value = "宏观政策地方回复entity", dataType = "ZjTzPolicyLocalReply")
    @RequireToken
    @PostMapping("/updateZjTzPolicyLocalReply")
    public ResponseEntity updateZjTzPolicyLocalReply(@RequestBody(required = false) ZjTzPolicyLocalReply zjTzPolicyLocalReply) {
        return zjTzPolicyLocalReplyService.updateZjTzPolicyLocalReply(zjTzPolicyLocalReply);
    }

    @ApiOperation(value="删除宏观政策地方回复", notes="删除宏观政策地方回复")
    @ApiImplicitParam(name = "zjTzPolicyLocalReplyList", value = "宏观政策地方回复List", dataType = "List<ZjTzPolicyLocalReply>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZjTzPolicyLocalReply")
    public ResponseEntity batchDeleteUpdateZjTzPolicyLocalReply(@RequestBody(required = false) List<ZjTzPolicyLocalReply> zjTzPolicyLocalReplyList) {
        return zjTzPolicyLocalReplyService.batchDeleteUpdateZjTzPolicyLocalReply(zjTzPolicyLocalReplyList);
    }

}
