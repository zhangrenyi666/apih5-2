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
import com.apih5.mybatis.pojo.BaseFlowSponsorChooser;
import com.apih5.service.BaseFlowSponsorChooserService;

@RestController
public class BaseFlowSponsorChooserController {

    @Autowired(required = true)
    private BaseFlowSponsorChooserService baseFlowSponsorChooserService;

    @ApiOperation(value="查询流程表单主从关系", notes="查询流程表单主从关系")
    @ApiImplicitParam(name = "baseFlowSponsorChooser", value = "流程表单主从关系entity", dataType = "BaseFlowSponsorChooser")
    @RequireToken
    @PostMapping("/getBaseFlowSponsorChooserList")
    public ResponseEntity getBaseFlowSponsorChooserList(@RequestBody(required = false) BaseFlowSponsorChooser baseFlowSponsorChooser) {
        return baseFlowSponsorChooserService.getBaseFlowSponsorChooserListByCondition(baseFlowSponsorChooser);
    }

    @ApiOperation(value="新增流程表单主从关系", notes="新增流程表单主从关系")
    @ApiImplicitParam(name = "baseFlowSponsorChooser", value = "流程表单主从关系entity", dataType = "BaseFlowSponsorChooser")
    @RequireToken
    @PostMapping("/addBaseFlowSponsorChooser")
    public ResponseEntity addBaseFlowSponsorChooser(@RequestBody(required = false) BaseFlowSponsorChooser baseFlowSponsorChooser) {
        return baseFlowSponsorChooserService.saveBaseFlowSponsorChooser(baseFlowSponsorChooser);
    }

    @ApiOperation(value="更新流程表单主从关系", notes="更新流程表单主从关系")
    @ApiImplicitParam(name = "baseFlowSponsorChooser", value = "流程表单主从关系entity", dataType = "BaseFlowSponsorChooser")
    @RequireToken
    @PostMapping("/updateBaseFlowSponsorChooser")
    public ResponseEntity updateBaseFlowSponsorChooser(@RequestBody(required = false) BaseFlowSponsorChooser baseFlowSponsorChooser) {
        return baseFlowSponsorChooserService.updateBaseFlowSponsorChooser(baseFlowSponsorChooser);
    }

    @ApiOperation(value="删除流程表单主从关系", notes="删除流程表单主从关系")
    @ApiImplicitParam(name = "baseFlowSponsorChooserList", value = "流程表单主从关系List", dataType = "List<BaseFlowSponsorChooser>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateBaseFlowSponsorChooser")
    public ResponseEntity batchDeleteUpdateBaseFlowSponsorChooser(@RequestBody(required = false) List<BaseFlowSponsorChooser> baseFlowSponsorChooserList) {
        return baseFlowSponsorChooserService.batchDeleteUpdateBaseFlowSponsorChooser(baseFlowSponsorChooserList);
    }

    @ApiOperation(value="新增流程表单主从关系", notes="新增流程表单主从关系")
    @ApiImplicitParam(name = "baseFlowSponsorChooser", value = "流程表单主从关系entity", dataType = "BaseFlowSponsorChooser")
    @RequireToken
    @PostMapping("/addBaseFlowSponsorChooserByList")
    public ResponseEntity addBaseFlowSponsorChooserList(@RequestBody(required = false) BaseFlowSponsorChooser baseFlowSponsorChooser) {
        return baseFlowSponsorChooserService.addBaseFlowSponsorChooserByList(baseFlowSponsorChooser);
    }
    
    @ApiOperation(value="修改流程表单主从关系", notes="修改流程表单主从关系")
    @ApiImplicitParam(name = "baseFlowSponsorChooser", value = "流程表单主从关系entity", dataType = "BaseFlowSponsorChooser")
    @RequireToken
    @PostMapping("/updateBaseFlowSponsorChooserByList")
    public ResponseEntity updateBaseFlowSponsorChooserByList(@RequestBody(required = false) BaseFlowSponsorChooser baseFlowSponsorChooser) {
        return baseFlowSponsorChooserService.updateBaseFlowSponsorChooserByList(baseFlowSponsorChooser);
    }
}
