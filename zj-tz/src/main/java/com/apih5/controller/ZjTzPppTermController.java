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
import com.apih5.mybatis.pojo.ZjTzPppTerm;
import com.apih5.service.ZjTzPppTermService;

@RestController
public class ZjTzPppTermController {

    @Autowired(required = true)
    private ZjTzPppTermService zjTzPppTermService;

    @ApiOperation(value="查询ppp合同分析", notes="查询ppp合同分析")
    @ApiImplicitParam(name = "zjTzPppTerm", value = "ppp合同分析entity", dataType = "ZjTzPppTerm")
    @RequireToken
    @PostMapping("/getZjTzPppTermList")
    public ResponseEntity getZjTzPppTermList(@RequestBody(required = false) ZjTzPppTerm zjTzPppTerm) {
        return zjTzPppTermService.getZjTzPppTermListByCondition(zjTzPppTerm);
    }

    @ApiOperation(value="查询详情ppp合同分析", notes="查询详情ppp合同分析")
    @ApiImplicitParam(name = "zjTzPppTerm", value = "ppp合同分析entity", dataType = "ZjTzPppTerm")
    @RequireToken
    @PostMapping("/getZjTzPppTermDetails")
    public ResponseEntity getZjTzPppTermDetails(@RequestBody(required = false) ZjTzPppTerm zjTzPppTerm) {
        return zjTzPppTermService.getZjTzPppTermDetails(zjTzPppTerm);
    }

    @ApiOperation(value="新增ppp合同分析", notes="新增ppp合同分析")
    @ApiImplicitParam(name = "zjTzPppTerm", value = "ppp合同分析entity", dataType = "ZjTzPppTerm")
    @RequireToken
    @PostMapping("/addZjTzPppTerm")
    public ResponseEntity addZjTzPppTerm(@RequestBody(required = false) ZjTzPppTerm zjTzPppTerm) {
        return zjTzPppTermService.saveZjTzPppTerm(zjTzPppTerm);
    }

    @ApiOperation(value="更新ppp合同分析", notes="更新ppp合同分析")
    @ApiImplicitParam(name = "zjTzPppTerm", value = "ppp合同分析entity", dataType = "ZjTzPppTerm")
    @RequireToken
    @PostMapping("/updateZjTzPppTerm")
    public ResponseEntity updateZjTzPppTerm(@RequestBody(required = false) ZjTzPppTerm zjTzPppTerm) {
        return zjTzPppTermService.updateZjTzPppTerm(zjTzPppTerm);
    }

    @ApiOperation(value="删除ppp合同分析", notes="删除ppp合同分析")
    @ApiImplicitParam(name = "zjTzPppTermList", value = "ppp合同分析List", dataType = "List<ZjTzPppTerm>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZjTzPppTerm")
    public ResponseEntity batchDeleteUpdateZjTzPppTerm(@RequestBody(required = false) List<ZjTzPppTerm> zjTzPppTermList) {
        return zjTzPppTermService.batchDeleteUpdateZjTzPppTerm(zjTzPppTermList);
    }
    
    @ApiOperation(value="批量上报ppp合同分析", notes="批量上报ppp合同分析")
    @ApiImplicitParam(name = "zjTzPppTermList", value = "ppp合同分析List", dataType = "List<ZjTzPppTerm>")
    @RequireToken
    @PostMapping("/batchReleaseZjTzPppTerm")
    public ResponseEntity batchReleaseZjTzPppTerm(@RequestBody(required = false) List<ZjTzPppTerm> zjTzPppTermList) {
        return zjTzPppTermService.batchReleaseZjTzPppTerm(zjTzPppTermList);
    }
    
    @ApiOperation(value="批量退回ppp合同分析", notes="批量退回ppp合同分析")
    @ApiImplicitParam(name = "zjTzPppTermList", value = "ppp合同分析List", dataType = "List<ZjTzPppTerm>")
    @RequireToken
    @PostMapping("/batchRecallZjTzPppTerm")
    public ResponseEntity batchRecallZjTzPppTerm(@RequestBody(required = false) List<ZjTzPppTerm> zjTzPppTermList) {
        return zjTzPppTermService.batchRecallZjTzPppTerm(zjTzPppTermList);
    }
    
    @ApiOperation(value="保存ppp合同分析所有回复的接口", notes="保存ppp合同分析所有回复的接口")
    @ApiImplicitParam(name = "zjTzPppTerm", value = "ppp合同分析entity", dataType = "ZjTzPppTerm")
    @RequireToken
    @PostMapping("/saveZjTzPppTermAllReply")
    public ResponseEntity saveZjTzPppTermAllReply(@RequestBody(required = false) ZjTzPppTerm zjTzPppTerm) {
        return zjTzPppTermService.saveZjTzPppTermAllReply(zjTzPppTerm);
    }

}
