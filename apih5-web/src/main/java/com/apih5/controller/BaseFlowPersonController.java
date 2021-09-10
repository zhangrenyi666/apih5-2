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
import com.apih5.mybatis.pojo.BaseFlowPerson;
import com.apih5.service.BaseFlowPersonService;

@RestController
public class BaseFlowPersonController {

    @Autowired(required = true)
    private BaseFlowPersonService baseFlowPersonService;

    @ApiOperation(value="查询流程人员", notes="查询流程人员")
    @ApiImplicitParam(name = "baseFlowPerson", value = "流程人员entity", dataType = "BaseFlowPerson")
    @RequireToken
    @PostMapping("/getBaseFlowPersonList")
    public ResponseEntity getBaseFlowPersonList(@RequestBody(required = false) BaseFlowPerson baseFlowPerson) {
        return baseFlowPersonService.getBaseFlowPersonListByCondition(baseFlowPerson);
    }

    @ApiOperation(value="新增流程人员", notes="新增流程人员")
    @ApiImplicitParam(name = "baseFlowPerson", value = "流程人员entity", dataType = "BaseFlowPerson")
    @RequireToken
    @PostMapping("/addBaseFlowPerson")
    public ResponseEntity addBaseFlowPerson(@RequestBody(required = false) BaseFlowPerson baseFlowPerson) {
        return baseFlowPersonService.saveBaseFlowPerson(baseFlowPerson);
    }

    @ApiOperation(value="更新流程人员", notes="更新流程人员")
    @ApiImplicitParam(name = "baseFlowPerson", value = "流程人员entity", dataType = "BaseFlowPerson")
    @RequireToken
    @PostMapping("/updateBaseFlowPerson")
    public ResponseEntity updateBaseFlowPerson(@RequestBody(required = false) BaseFlowPerson baseFlowPerson) {
        return baseFlowPersonService.updateBaseFlowPerson(baseFlowPerson);
    }

    @ApiOperation(value="删除流程人员", notes="删除流程人员")
    @ApiImplicitParam(name = "baseFlowPersonList", value = "流程人员List", dataType = "List<BaseFlowPerson>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateBaseFlowPerson")
    public ResponseEntity batchDeleteUpdateBaseFlowPerson(@RequestBody(required = false) List<BaseFlowPerson> baseFlowPersonList) {
        return baseFlowPersonService.batchDeleteUpdateBaseFlowPerson(baseFlowPersonList);
    }

}
