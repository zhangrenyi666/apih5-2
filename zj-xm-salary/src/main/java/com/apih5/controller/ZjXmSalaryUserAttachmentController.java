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
import com.apih5.mybatis.pojo.ZjXmSalaryUserAttachment;
import com.apih5.service.ZjXmSalaryUserAttachmentService;

@RestController
public class ZjXmSalaryUserAttachmentController {

    @Autowired(required = true)
    private ZjXmSalaryUserAttachmentService zjXmSalaryUserAttachmentService;

    @ApiOperation(value="查询人员附件", notes="查询人员附件")
    @ApiImplicitParam(name = "zjXmSalaryUserAttachment", value = "人员附件entity", dataType = "ZjXmSalaryUserAttachment")
    @RequireToken
    @PostMapping("/getZjXmSalaryUserAttachmentList")
    public ResponseEntity getZjXmSalaryUserAttachmentList(@RequestBody(required = false) ZjXmSalaryUserAttachment zjXmSalaryUserAttachment) {
        return zjXmSalaryUserAttachmentService.getZjXmSalaryUserAttachmentListByCondition(zjXmSalaryUserAttachment);
    }

    @ApiOperation(value="查询详情人员附件", notes="查询详情人员附件")
    @ApiImplicitParam(name = "zjXmSalaryUserAttachment", value = "人员附件entity", dataType = "ZjXmSalaryUserAttachment")
    @RequireToken
    @PostMapping("/getZjXmSalaryUserAttachmentDetails")
    public ResponseEntity getZjXmSalaryUserAttachmentDetails(@RequestBody(required = false) ZjXmSalaryUserAttachment zjXmSalaryUserAttachment) {
        return zjXmSalaryUserAttachmentService.getZjXmSalaryUserAttachmentDetails(zjXmSalaryUserAttachment);
    }

    @ApiOperation(value="新增人员附件", notes="新增人员附件")
    @ApiImplicitParam(name = "zjXmSalaryUserAttachment", value = "人员附件entity", dataType = "ZjXmSalaryUserAttachment")
    @RequireToken
    @PostMapping("/addZjXmSalaryUserAttachment")
    public ResponseEntity addZjXmSalaryUserAttachment(@RequestBody(required = false) ZjXmSalaryUserAttachment zjXmSalaryUserAttachment) {
        return zjXmSalaryUserAttachmentService.saveZjXmSalaryUserAttachment(zjXmSalaryUserAttachment);
    }

    @ApiOperation(value="更新人员附件", notes="更新人员附件")
    @ApiImplicitParam(name = "zjXmSalaryUserAttachment", value = "人员附件entity", dataType = "ZjXmSalaryUserAttachment")
    @RequireToken
    @PostMapping("/updateZjXmSalaryUserAttachment")
    public ResponseEntity updateZjXmSalaryUserAttachment(@RequestBody(required = false) ZjXmSalaryUserAttachment zjXmSalaryUserAttachment) {
        return zjXmSalaryUserAttachmentService.updateZjXmSalaryUserAttachment(zjXmSalaryUserAttachment);
    }

    @ApiOperation(value="删除人员附件", notes="删除人员附件")
    @ApiImplicitParam(name = "zjXmSalaryUserAttachmentList", value = "人员附件List", dataType = "List<ZjXmSalaryUserAttachment>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZjXmSalaryUserAttachment")
    public ResponseEntity batchDeleteUpdateZjXmSalaryUserAttachment(@RequestBody(required = false) List<ZjXmSalaryUserAttachment> zjXmSalaryUserAttachmentList) {
        return zjXmSalaryUserAttachmentService.batchDeleteUpdateZjXmSalaryUserAttachment(zjXmSalaryUserAttachmentList);
    }

}
