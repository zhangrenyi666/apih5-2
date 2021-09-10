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
import com.apih5.mybatis.pojo.ZjTzRules;
import com.apih5.service.ZjTzRulesService;

@RestController
public class ZjTzRulesController {

    @Autowired(required = true)
    private ZjTzRulesService zjTzRulesService;

    @ApiOperation(value="查询规章制度", notes="查询规章制度")
    @ApiImplicitParam(name = "zjTzRules", value = "规章制度entity", dataType = "ZjTzRules")
    @RequireToken
    @PostMapping("/getZjTzRulesList")
    public ResponseEntity getZjTzRulesList(@RequestBody(required = false) ZjTzRules zjTzRules) {
        return zjTzRulesService.getZjTzRulesListByCondition(zjTzRules);
    }

    @ApiOperation(value="查询详情规章制度", notes="查询详情规章制度")
    @ApiImplicitParam(name = "zjTzRules", value = "规章制度entity", dataType = "ZjTzRules")
    @RequireToken
    @PostMapping("/getZjTzRulesDetails")
    public ResponseEntity getZjTzRulesDetails(@RequestBody(required = false) ZjTzRules zjTzRules) {
        return zjTzRulesService.getZjTzRulesDetails(zjTzRules);
    }

    @ApiOperation(value="新增规章制度", notes="新增规章制度")
    @ApiImplicitParam(name = "zjTzRules", value = "规章制度entity", dataType = "ZjTzRules")
    @RequireToken
    @PostMapping("/addZjTzRules")
    public ResponseEntity addZjTzRules(@RequestBody(required = false) ZjTzRules zjTzRules) {
        return zjTzRulesService.saveZjTzRules(zjTzRules);
    }

    @ApiOperation(value="更新规章制度", notes="更新规章制度")
    @ApiImplicitParam(name = "zjTzRules", value = "规章制度entity", dataType = "ZjTzRules")
    @RequireToken
    @PostMapping("/updateZjTzRules")
    public ResponseEntity updateZjTzRules(@RequestBody(required = false) ZjTzRules zjTzRules) {
        return zjTzRulesService.updateZjTzRules(zjTzRules);
    }

    @ApiOperation(value="删除规章制度", notes="删除规章制度")
    @ApiImplicitParam(name = "zjTzRulesList", value = "规章制度List", dataType = "List<ZjTzRules>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZjTzRules")
    public ResponseEntity batchDeleteUpdateZjTzRules(@RequestBody(required = false) List<ZjTzRules> zjTzRulesList) {
        return zjTzRulesService.batchDeleteUpdateZjTzRules(zjTzRulesList);
    }
    
    @ApiOperation(value="广而告之规章制度", notes="广而告之规章制度")
    @ApiImplicitParam(name = "zjTzRulesList", value = "规章制度List", dataType = "List<ZjTzRules>")
    @RequireToken
    @PostMapping("/toHomeShowZjTzRules")
    public ResponseEntity toHomeShowZjTzRules(@RequestBody(required = false) ZjTzRules zjTzRules) {
        return zjTzRulesService.toHomeShowZjTzRules(zjTzRules);
    }

    @ApiOperation(value="批量发布规章制度", notes="批量发布规章制度")
    @ApiImplicitParam(name = "zjTzRulesList", value = "规章制度List", dataType = "List<ZjTzRules>")
    @RequireToken
    @PostMapping("/batchDeleteReleaseZjTzRules")
    public ResponseEntity batchDeleteReleaseZjTzRules(@RequestBody(required = false) List<ZjTzRules> zjTzRulesList) {
        return zjTzRulesService.batchDeleteReleaseZjTzRules(zjTzRulesList);
    }
    
    @ApiOperation(value="批量撤回规章制度", notes="批量撤回规章制度")
    @ApiImplicitParam(name = "zjTzRulesList", value = "规章制度List", dataType = "List<ZjTzRules>")
    @RequireToken
    @PostMapping("/batchDeleteRecallZjTzRules")
    public ResponseEntity batchDeleteRecallZjTzRules(@RequestBody(required = false) List<ZjTzRules> zjTzRulesList) {
        return zjTzRulesService.batchDeleteRecallZjTzRules(zjTzRulesList);
    }
    
    @ApiOperation(value="批量下载规章制度附件", notes="批量下载规章制度附件")
    @ApiImplicitParam(name = "zjTzRulesList", value = "规章制度List", dataType = "List<ZjTzRules>")
    @RequireToken
    @PostMapping("/batchExportZjTzRulesFile")
    public ResponseEntity batchExportZjTzRulesFile(@RequestBody(required = false) List<ZjTzRules> zjTzRulesList) {
        return zjTzRulesService.batchExportZjTzRulesFile(zjTzRulesList);
    }
    
    @ApiOperation(value="广而告之规章制度", notes="广而告之规章制度")
    @ApiImplicitParam(name = "zjTzRules", value = "规章制度entity", dataType = "ZjTzRules")
    @RequireToken
    @PostMapping("/getZjTzRulesHome")
    public ResponseEntity getZjTzRulesHome(@RequestBody(required = false) ZjTzRules zjTzRules) {
        return zjTzRulesService.getZjTzRulesHome(zjTzRules);
    }
    
}
