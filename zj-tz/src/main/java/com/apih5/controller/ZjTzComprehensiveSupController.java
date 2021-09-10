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
import com.apih5.mybatis.pojo.ZjTzComprehensiveSup;
import com.apih5.service.ZjTzComprehensiveSupService;

@RestController
public class ZjTzComprehensiveSupController {

    @Autowired(required = true)
    private ZjTzComprehensiveSupService zjTzComprehensiveSupService;

    @ApiOperation(value="查询项目综合督、查/综合督导", notes="查询项目综合督、查/综合督导")
    @ApiImplicitParam(name = "zjTzComprehensiveSup", value = "项目综合督、查/综合督导entity", dataType = "ZjTzComprehensiveSup")
    @RequireToken
    @PostMapping("/getZjTzComprehensiveSupList")
    public ResponseEntity getZjTzComprehensiveSupList(@RequestBody(required = false) ZjTzComprehensiveSup zjTzComprehensiveSup) {
        return zjTzComprehensiveSupService.getZjTzComprehensiveSupListByCondition(zjTzComprehensiveSup);
    }

    @ApiOperation(value="查询详情项目综合督、查/综合督导", notes="查询详情项目综合督、查/综合督导")
    @ApiImplicitParam(name = "zjTzComprehensiveSup", value = "项目综合督、查/综合督导entity", dataType = "ZjTzComprehensiveSup")
    @RequireToken
    @PostMapping("/getZjTzComprehensiveSupDetails")
    public ResponseEntity getZjTzComprehensiveSupDetails(@RequestBody(required = false) ZjTzComprehensiveSup zjTzComprehensiveSup) {
        return zjTzComprehensiveSupService.getZjTzComprehensiveSupDetails(zjTzComprehensiveSup);
    }

    @ApiOperation(value="新增项目综合督、查/综合督导", notes="新增项目综合督、查/综合督导")
    @ApiImplicitParam(name = "zjTzComprehensiveSup", value = "项目综合督、查/综合督导entity", dataType = "ZjTzComprehensiveSup")
    @RequireToken
    @PostMapping("/addZjTzComprehensiveSup")
    public ResponseEntity addZjTzComprehensiveSup(@RequestBody(required = false) ZjTzComprehensiveSup zjTzComprehensiveSup) {
        return zjTzComprehensiveSupService.saveZjTzComprehensiveSup(zjTzComprehensiveSup);
    }

    @ApiOperation(value="更新项目综合督、查/综合督导", notes="更新项目综合督、查/综合督导")
    @ApiImplicitParam(name = "zjTzComprehensiveSup", value = "项目综合督、查/综合督导entity", dataType = "ZjTzComprehensiveSup")
    @RequireToken
    @PostMapping("/updateZjTzComprehensiveSup")
    public ResponseEntity updateZjTzComprehensiveSup(@RequestBody(required = false) ZjTzComprehensiveSup zjTzComprehensiveSup) {
        return zjTzComprehensiveSupService.updateZjTzComprehensiveSup(zjTzComprehensiveSup);
    }

    @ApiOperation(value="删除项目综合督、查/综合督导", notes="删除项目综合督、查/综合督导")
    @ApiImplicitParam(name = "zjTzComprehensiveSupList", value = "项目综合督、查/综合督导List", dataType = "List<ZjTzComprehensiveSup>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZjTzComprehensiveSup")
    public ResponseEntity batchDeleteUpdateZjTzComprehensiveSup(@RequestBody(required = false) List<ZjTzComprehensiveSup> zjTzComprehensiveSupList) {
        return zjTzComprehensiveSupService.batchDeleteUpdateZjTzComprehensiveSup(zjTzComprehensiveSupList);
    }
    
    @ApiOperation(value="批量撤回项目综合督、查/综合督导==只能对已下达的数据测回", notes="批量撤回项目综合督、查/综合督导")
    @ApiImplicitParam(name = "zjTzComprehensiveSupList", value = "项目综合督、查/综合督导List", dataType = "List<ZjTzComprehensiveSup>")
    @RequireToken
    @PostMapping("/batchRecallZjTzComprehensiveSup")
    public ResponseEntity batchRecallZjTzComprehensiveSup(@RequestBody(required = false) List<ZjTzComprehensiveSup> zjTzComprehensiveSupList) {
        return zjTzComprehensiveSupService.batchRecallZjTzComprehensiveSup(zjTzComprehensiveSupList);
    }
    
    @ApiOperation(value="整改下达", notes="整改下达")
    @ApiImplicitParam(name = "zjTzComprehensiveSup", value = "项目综合督、查/综合督导entity", dataType = "ZjTzComprehensiveSup")
    @RequireToken
    @PostMapping("/correctiveZjTzComprehensiveSup")
    public ResponseEntity correctiveZjTzComprehensiveSup(@RequestBody(required = false) ZjTzComprehensiveSup zjTzComprehensiveSup) {
        return zjTzComprehensiveSupService.correctiveZjTzComprehensiveSup(zjTzComprehensiveSup);
    }
    
    @ApiOperation(value="查询项目综合督、查/综合督导回复情况列表", notes="查询项目综合督、查/综合督导回复情况列表")
    @ApiImplicitParam(name = "zjTzComprehensiveSup", value = "项目综合督、查/综合督导entity", dataType = "ZjTzComprehensiveSup")
    @RequireToken
    @PostMapping("/getZjTzComprehensiveSupReplyList")
    public ResponseEntity getZjTzComprehensiveSupReplyList(@RequestBody(required = false) ZjTzComprehensiveSup zjTzComprehensiveSup) {
        return zjTzComprehensiveSupService.getZjTzComprehensiveSupReplyList(zjTzComprehensiveSup);
    }
    
    @ApiOperation(value="批量导出项目综合督、查/综合督导附件", notes="批量导出项目综合督、查/综合督导附件")
    @ApiImplicitParam(name = "zjTzComprehensiveSupList", value = "项目综合督、查/综合督导List", dataType = "List<ZjTzComprehensiveSup>")
    @RequireToken
    @PostMapping("/batchExportZjTzComprehensiveSupFile")
    public ResponseEntity batchExportZjTzComprehensiveSupFile(@RequestBody(required = false) List<ZjTzComprehensiveSup> zjTzComprehensiveSupList) {
        return zjTzComprehensiveSupService.batchExportZjTzComprehensiveSupFile(zjTzComprehensiveSupList);
    }
    
    @ApiOperation(value="回复项目综合督、查/综合督导", notes="回复项目综合督、查/综合督导")
    @ApiImplicitParam(name = "zjTzComprehensiveSup", value = "项目综合督、查/综合督导entity", dataType = "ZjTzComprehensiveSup")
    @RequireToken
    @PostMapping("/replyZjTzComprehensiveSup")
    public ResponseEntity replyZjTzComprehensiveSup(@RequestBody(required = false) ZjTzComprehensiveSup zjTzComprehensiveSup) {
        return zjTzComprehensiveSupService.replyZjTzComprehensiveSup(zjTzComprehensiveSup);
    }

}
