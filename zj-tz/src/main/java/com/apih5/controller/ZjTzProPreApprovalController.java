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
import com.apih5.mybatis.pojo.ZjTzProPreApproval;
import com.apih5.service.ZjTzProPreApprovalService;

@RestController
public class ZjTzProPreApprovalController {

    @Autowired(required = true)
    private ZjTzProPreApprovalService zjTzProPreApprovalService;

    @ApiOperation(value="查询项目标前核准", notes="查询项目标前核准")
    @ApiImplicitParam(name = "zjTzProPreApproval", value = "项目标前核准entity", dataType = "ZjTzProPreApproval")
    @RequireToken
    @PostMapping("/getZjTzProPreApprovalList")
    public ResponseEntity getZjTzProPreApprovalList(@RequestBody(required = false) ZjTzProPreApproval zjTzProPreApproval) {
        return zjTzProPreApprovalService.getZjTzProPreApprovalListByCondition(zjTzProPreApproval);
    }

    @ApiOperation(value="查询详情项目标前核准", notes="查询详情项目标前核准")
    @ApiImplicitParam(name = "zjTzProPreApproval", value = "项目标前核准entity", dataType = "ZjTzProPreApproval")
    @RequireToken
    @PostMapping("/getZjTzProPreApprovalDetails")
    public ResponseEntity getZjTzProPreApprovalDetails(@RequestBody(required = false) ZjTzProPreApproval zjTzProPreApproval) {
        return zjTzProPreApprovalService.getZjTzProPreApprovalDetails(zjTzProPreApproval);
    }

    @ApiOperation(value="新增项目标前核准", notes="新增项目标前核准")
    @ApiImplicitParam(name = "zjTzProPreApproval", value = "项目标前核准entity", dataType = "ZjTzProPreApproval")
    @RequireToken
    @PostMapping("/addZjTzProPreApproval")
    public ResponseEntity addZjTzProPreApproval(@RequestBody(required = false) ZjTzProPreApproval zjTzProPreApproval) {
        return zjTzProPreApprovalService.saveZjTzProPreApproval(zjTzProPreApproval);
    }

    @ApiOperation(value="更新项目标前核准", notes="更新项目标前核准")
    @ApiImplicitParam(name = "zjTzProPreApproval", value = "项目标前核准entity", dataType = "ZjTzProPreApproval")
    @RequireToken
    @PostMapping("/updateZjTzProPreApproval")
    public ResponseEntity updateZjTzProPreApproval(@RequestBody(required = false) ZjTzProPreApproval zjTzProPreApproval) {
        return zjTzProPreApprovalService.updateZjTzProPreApproval(zjTzProPreApproval);
    }

    @ApiOperation(value="删除项目标前核准", notes="删除项目标前核准")
    @ApiImplicitParam(name = "zjTzProPreApprovalList", value = "项目标前核准List", dataType = "List<ZjTzProPreApproval>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZjTzProPreApproval")
    public ResponseEntity batchDeleteUpdateZjTzProPreApproval(@RequestBody(required = false) List<ZjTzProPreApproval> zjTzProPreApprovalList) {
        return zjTzProPreApprovalService.batchDeleteUpdateZjTzProPreApproval(zjTzProPreApprovalList);
    }

    @ApiOperation(value="批量下达项目标前核准", notes="批量下达项目标前核准")
    @ApiImplicitParam(name = "zjTzProPreApprovalList", value = "项目标前核准List", dataType = "List<ZjTzProPreApproval>")
    @RequireToken
    @PostMapping("/batchReleaseZjTzProPreApproval")
    public ResponseEntity batchReleaseZjTzProPreApproval(@RequestBody(required = false) List<ZjTzProPreApproval> zjTzProPreApprovalList) {
        return zjTzProPreApprovalService.batchReleaseZjTzProPreApproval(zjTzProPreApprovalList);
    }
    
    @ApiOperation(value="批量撤回项目标前核准", notes="批量撤回项目标前核准")
    @ApiImplicitParam(name = "zjTzProPreApprovalList", value = "项目标前核准List", dataType = "List<ZjTzProPreApproval>")
    @RequireToken
    @PostMapping("/batchRecallZjTzProPreApproval")
    public ResponseEntity batchRecallZjTzProPreApproval(@RequestBody(required = false) List<ZjTzProPreApproval> zjTzProPreApprovalList) {
        return zjTzProPreApprovalService.batchRecallZjTzProPreApproval(zjTzProPreApprovalList);
    }
    
    @ApiOperation(value="批量导出项目标前核准附件", notes="批量导出项目标前核准附件")
    @ApiImplicitParam(name = "zjTzProPreApprovalList", value = "项目标前核准List", dataType = "List<ZjTzProPreApproval>")
    @RequireToken
    @PostMapping("/batchExportZjTzProPreApprovalFile")
    public ResponseEntity batchExportZjTzProPreApprovalFile(@RequestBody(required = false) List<ZjTzProPreApproval> zjTzProPreApprovalList) {
        return zjTzProPreApprovalService.batchExportZjTzProPreApprovalFile(zjTzProPreApprovalList);
    }


}
