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
import com.apih5.mybatis.pojo.ZjTzQualityManagementSystem;
import com.apih5.service.ZjTzQualityManagementSystemService;

@RestController
public class ZjTzQHSEQualityManagementSystemController {

    @Autowired(required = true)
    private ZjTzQualityManagementSystemService zjTzQualityManagementSystemService;

    @ApiOperation(value="查询质量管理制度", notes="查询质量管理制度")
    @ApiImplicitParam(name = "zjTzQualityManagementSystem", value = "质量管理制度entity", dataType = "ZjTzQualityManagementSystem")
    @RequireToken
    @PostMapping("/getZjTzQualityManagementSystemList")
    public ResponseEntity getZjTzQualityManagementSystemList(@RequestBody(required = false) ZjTzQualityManagementSystem zjTzQualityManagementSystem) {
        return zjTzQualityManagementSystemService.getZjTzQualityManagementSystemListByCondition(zjTzQualityManagementSystem);
    }

    @ApiOperation(value="查询详情质量管理制度", notes="查询详情质量管理制度")
    @ApiImplicitParam(name = "zjTzQualityManagementSystem", value = "质量管理制度entity", dataType = "ZjTzQualityManagementSystem")
    @RequireToken
    @PostMapping("/getZjTzQualityManagementSystemDetails")
    public ResponseEntity getZjTzQualityManagementSystemDetails(@RequestBody(required = false) ZjTzQualityManagementSystem zjTzQualityManagementSystem) {
        return zjTzQualityManagementSystemService.getZjTzQualityManagementSystemDetails(zjTzQualityManagementSystem);
    }

    @ApiOperation(value="新增质量管理制度（添加附件）", notes="新增质量管理制度（添加附件）")
    @ApiImplicitParam(name = "zjTzQualityManagementSystem", value = "质量管理制度entity", dataType = "ZjTzQualityManagementSystem")
    @RequireToken
    @PostMapping("/addZjTzQualityManagementSystemAddFile")
    public ResponseEntity addZjTzQualityManagementSystemAddFile(@RequestBody(required = false) ZjTzQualityManagementSystem zjTzQualityManagementSystem) {
        return zjTzQualityManagementSystemService.saveZjTzQualityManagementSystemAddFile(zjTzQualityManagementSystem);
    }
    
    @ApiOperation(value="更新质量管理制度（添加附件）", notes="更新质量管理制度（添加附件）")
    @ApiImplicitParam(name = "zjTzQualityManagementSystem", value = "质量管理制度entity", dataType = "ZjTzQualityManagementSystem")
    @RequireToken
    @PostMapping("/updateZjTzQualityManagementSystemAddFile")
    public ResponseEntity updateZjTzQualityManagementSystemAddFile(@RequestBody(required = false) ZjTzQualityManagementSystem zjTzQualityManagementSystem) {
    	return zjTzQualityManagementSystemService.updateZjTzQualityManagementSystemAddFile(zjTzQualityManagementSystem);
    }
    
    @ApiOperation(value="新增质量管理制度", notes="新增质量管理制度")
    @ApiImplicitParam(name = "zjTzQualityManagementSystem", value = "质量管理制度entity", dataType = "ZjTzQualityManagementSystem")
    @RequireToken
    @PostMapping("/addZjTzQualityManagementSystem")
    public ResponseEntity addZjTzQualityManagementSystem(@RequestBody(required = false) ZjTzQualityManagementSystem zjTzQualityManagementSystem) {
    	return zjTzQualityManagementSystemService.saveZjTzQualityManagementSystem(zjTzQualityManagementSystem);
    }

    @ApiOperation(value="更新质量管理制度", notes="更新质量管理制度")
    @ApiImplicitParam(name = "zjTzQualityManagementSystem", value = "质量管理制度entity", dataType = "ZjTzQualityManagementSystem")
    @RequireToken
    @PostMapping("/updateZjTzQualityManagementSystem")
    public ResponseEntity updateZjTzQualityManagementSystem(@RequestBody(required = false) ZjTzQualityManagementSystem zjTzQualityManagementSystem) {
        return zjTzQualityManagementSystemService.updateZjTzQualityManagementSystem(zjTzQualityManagementSystem);
    }

    @ApiOperation(value="删除质量管理制度", notes="删除质量管理制度")
    @ApiImplicitParam(name = "zjTzQualityManagementSystemList", value = "质量管理制度List", dataType = "List<ZjTzQualityManagementSystem>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZjTzQualityManagementSystem")
    public ResponseEntity batchDeleteUpdateZjTzQualityManagementSystem(@RequestBody(required = false) List<ZjTzQualityManagementSystem> zjTzQualityManagementSystemList) {
        return zjTzQualityManagementSystemService.batchDeleteUpdateZjTzQualityManagementSystem(zjTzQualityManagementSystemList);
    }

}
