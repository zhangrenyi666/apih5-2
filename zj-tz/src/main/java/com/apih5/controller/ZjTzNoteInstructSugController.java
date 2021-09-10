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
import com.apih5.mybatis.pojo.ZjTzNoteInstructSug;
import com.apih5.service.ZjTzNoteInstructSugService;

@RestController
public class ZjTzNoteInstructSugController {

    @Autowired(required = true)
    private ZjTzNoteInstructSugService zjTzNoteInstructSugService;

    @ApiOperation(value="查询公告、指令、建议", notes="查询公告、指令、建议")
    @ApiImplicitParam(name = "zjTzNoteInstructSug", value = "公告、指令、建议entity", dataType = "ZjTzNoteInstructSug")
    @RequireToken
    @PostMapping("/getZjTzNoteInstructSugList")
    public ResponseEntity getZjTzNoteInstructSugList(@RequestBody(required = false) ZjTzNoteInstructSug zjTzNoteInstructSug) {
        return zjTzNoteInstructSugService.getZjTzNoteInstructSugListByCondition(zjTzNoteInstructSug);
    }

    @ApiOperation(value="查询详情公告、指令、建议", notes="查询详情公告、指令、建议")
    @ApiImplicitParam(name = "zjTzNoteInstructSug", value = "公告、指令、建议entity", dataType = "ZjTzNoteInstructSug")
    @RequireToken
    @PostMapping("/getZjTzNoteInstructSugDetails")
    public ResponseEntity getZjTzNoteInstructSugDetails(@RequestBody(required = false) ZjTzNoteInstructSug zjTzNoteInstructSug) {
        return zjTzNoteInstructSugService.getZjTzNoteInstructSugDetails(zjTzNoteInstructSug);
    }

    @ApiOperation(value="新增公告、指令、建议", notes="新增公告、指令、建议")
    @ApiImplicitParam(name = "zjTzNoteInstructSug", value = "公告、指令、建议entity", dataType = "ZjTzNoteInstructSug")
    @RequireToken
    @PostMapping("/addZjTzNoteInstructSug")
    public ResponseEntity addZjTzNoteInstructSug(@RequestBody(required = false) ZjTzNoteInstructSug zjTzNoteInstructSug) {
        return zjTzNoteInstructSugService.saveZjTzNoteInstructSug(zjTzNoteInstructSug);
    }

    @ApiOperation(value="更新公告、指令、建议", notes="更新公告、指令、建议")
    @ApiImplicitParam(name = "zjTzNoteInstructSug", value = "公告、指令、建议entity", dataType = "ZjTzNoteInstructSug")
    @RequireToken
    @PostMapping("/updateZjTzNoteInstructSug")
    public ResponseEntity updateZjTzNoteInstructSug(@RequestBody(required = false) ZjTzNoteInstructSug zjTzNoteInstructSug) {
        return zjTzNoteInstructSugService.updateZjTzNoteInstructSug(zjTzNoteInstructSug);
    }

    @ApiOperation(value="删除公告、指令、建议", notes="删除公告、指令、建议")
    @ApiImplicitParam(name = "zjTzNoteInstructSugList", value = "公告、指令、建议List", dataType = "List<ZjTzNoteInstructSug>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZjTzNoteInstructSug")
    public ResponseEntity batchDeleteUpdateZjTzNoteInstructSug(@RequestBody(required = false) List<ZjTzNoteInstructSug> zjTzNoteInstructSugList) {
        return zjTzNoteInstructSugService.batchDeleteUpdateZjTzNoteInstructSug(zjTzNoteInstructSugList);
    }
    
    @ApiOperation(value="广而告之公告、指令、建议", notes="广而告之公告、指令、建议")
    @ApiImplicitParam(name = "zjTzNoteInstructSug", value = "公告、指令、建议entity", dataType = "ZjTzNoteInstructSug")
    @RequireToken
    @PostMapping("/toHomeShowZjTzNoteInstructSug")
    public ResponseEntity toHomeShowZjTzNoteInstructSug(@RequestBody(required = false) ZjTzNoteInstructSug zjTzNoteInstructSug) {
        return zjTzNoteInstructSugService.toHomeShowZjTzNoteInstructSug(zjTzNoteInstructSug);
    }

    @ApiOperation(value="批量发布公告、指令、建议", notes="批量发布公告、指令、建议")
    @ApiImplicitParam(name = "zjTzNoteInstructSugList", value = "公告、指令、建议List", dataType = "List<ZjTzNoteInstructSug>")
    @RequireToken
    @PostMapping("/batchReleaseZjTzNoteInstructSug")
    public ResponseEntity batchReleaseZjTzNoteInstructSug(@RequestBody(required = false) List<ZjTzNoteInstructSug> zjTzNoteInstructSugList) {
        return zjTzNoteInstructSugService.batchReleaseZjTzNoteInstructSug(zjTzNoteInstructSugList);
    }
    
    @ApiOperation(value="批量撤回公告、指令、建议", notes="批量撤回公告、指令、建议")
    @ApiImplicitParam(name = "zjTzNoteInstructSugList", value = "公告、指令、建议List", dataType = "List<ZjTzNoteInstructSug>")
    @RequireToken
    @PostMapping("/batchRecallZjTzNoteInstructSug")
    public ResponseEntity batchRecallZjTzNoteInstructSug(@RequestBody(required = false) List<ZjTzNoteInstructSug> zjTzNoteInstructSugList) {
        return zjTzNoteInstructSugService.batchRecallZjTzNoteInstructSug(zjTzNoteInstructSugList);
    }
    
    @ApiOperation(value="批量导出公告、指令、建议附件", notes="批量导出公告、指令、建议附件")
    @ApiImplicitParam(name = "zjTzNoteInstructSugList", value = "公告、指令、建议List", dataType = "List<ZjTzNoteInstructSug>")
    @RequireToken
    @PostMapping("/batchExportZjTzNoteInstructSugFile")
    public ResponseEntity batchExportZjTzNoteInstructSugFile(@RequestBody(required = false) List<ZjTzNoteInstructSug> zjTzNoteInstructSugList) {
        return zjTzNoteInstructSugService.batchExportZjTzNoteInstructSugFile(zjTzNoteInstructSugList);
    }
    
    @ApiOperation(value="首页公告栏", notes="首页公告栏")
    @ApiImplicitParam(name = "zjTzNoteInstructSug", value = "公告、指令、建议entity", dataType = "ZjTzNoteInstructSug")
    @RequireToken
    @PostMapping("/getHomeZjTzNoteInstructSug")
    public ResponseEntity getHomeZjTzNoteInstructSug(@RequestBody(required = false) ZjTzNoteInstructSug zjTzNoteInstructSug) {
        return zjTzNoteInstructSugService.getHomeZjTzNoteInstructSug(zjTzNoteInstructSug);
    }

}
