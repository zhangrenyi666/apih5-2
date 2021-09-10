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
import com.apih5.mybatis.pojo.ZxCtValuationRules;
import com.apih5.service.ZxCtValuationRulesService;

@RestController
public class ZxCtValuationRulesController {

    @Autowired(required = true)
    private ZxCtValuationRulesService zxCtValuationRulesService;

    @ApiOperation(value="查询分包计价规则库", notes="查询分包计价规则库")
    @ApiImplicitParam(name = "zxCtValuationRules", value = "分包计价规则库entity", dataType = "ZxCtValuationRules")
    @RequireToken
    @PostMapping("/getZxCtValuationRulesList")
    public ResponseEntity getZxCtValuationRulesList(@RequestBody(required = false) ZxCtValuationRules zxCtValuationRules) {
        return zxCtValuationRulesService.getZxCtValuationRulesListByCondition(zxCtValuationRules);
    }

    @ApiOperation(value="查询详情分包计价规则库", notes="查询详情分包计价规则库")
    @ApiImplicitParam(name = "zxCtValuationRules", value = "分包计价规则库entity", dataType = "ZxCtValuationRules")
    @RequireToken
    @PostMapping("/getZxCtValuationRulesDetails")
    public ResponseEntity getZxCtValuationRulesDetails(@RequestBody(required = false) ZxCtValuationRules zxCtValuationRules) {
        return zxCtValuationRulesService.getZxCtValuationRulesDetails(zxCtValuationRules);
    }

    @ApiOperation(value="新增分包计价规则库", notes="新增分包计价规则库")
    @ApiImplicitParam(name = "zxCtValuationRules", value = "分包计价规则库entity", dataType = "ZxCtValuationRules")
    @RequireToken
    @PostMapping("/addZxCtValuationRules")
    public ResponseEntity addZxCtValuationRules(@RequestBody(required = false) ZxCtValuationRules zxCtValuationRules) {
        return zxCtValuationRulesService.saveZxCtValuationRules(zxCtValuationRules);
    }

    @ApiOperation(value="更新分包计价规则库", notes="更新分包计价规则库")
    @ApiImplicitParam(name = "zxCtValuationRules", value = "分包计价规则库entity", dataType = "ZxCtValuationRules")
    @RequireToken
    @PostMapping("/updateZxCtValuationRules")
    public ResponseEntity updateZxCtValuationRules(@RequestBody(required = false) ZxCtValuationRules zxCtValuationRules) {
        return zxCtValuationRulesService.updateZxCtValuationRules(zxCtValuationRules);
    }

    @ApiOperation(value="删除分包计价规则库", notes="删除分包计价规则库")
    @ApiImplicitParam(name = "zxCtValuationRulesList", value = "分包计价规则库List", dataType = "List<ZxCtValuationRules>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxCtValuationRules")
    public ResponseEntity batchDeleteUpdateZxCtValuationRules(@RequestBody(required = false) List<ZxCtValuationRules> zxCtValuationRulesList) {
        return zxCtValuationRulesService.batchDeleteUpdateZxCtValuationRules(zxCtValuationRulesList);
    }

}
