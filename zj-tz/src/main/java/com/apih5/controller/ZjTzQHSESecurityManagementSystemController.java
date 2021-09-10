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
import com.apih5.mybatis.pojo.ZjTzSecurityManagementSystem;
import com.apih5.service.ZjTzSecurityManagementSystemService;

@RestController
public class ZjTzQHSESecurityManagementSystemController {

    @Autowired(required = true)
    private ZjTzSecurityManagementSystemService zjTzSecurityManagementSystemService;

    @ApiOperation(value="查询安全管理制度", notes="查询安全管理制度")
    @ApiImplicitParam(name = "zjTzSecurityManagementSystem", value = "安全管理制度entity", dataType = "ZjTzSecurityManagementSystem")
    @RequireToken
    @PostMapping("/getZjTzSecurityManagementSystemList")
    public ResponseEntity getZjTzSecurityManagementSystemList(@RequestBody(required = false) ZjTzSecurityManagementSystem zjTzSecurityManagementSystem) {
        return zjTzSecurityManagementSystemService.getZjTzSecurityManagementSystemListByCondition(zjTzSecurityManagementSystem);
    }

    @ApiOperation(value="查询详情安全管理制度", notes="查询详情安全管理制度")
    @ApiImplicitParam(name = "zjTzSecurityManagementSystem", value = "安全管理制度entity", dataType = "ZjTzSecurityManagementSystem")
    @RequireToken
    @PostMapping("/getZjTzSecurityManagementSystemDetails")
    public ResponseEntity getZjTzSecurityManagementSystemDetails(@RequestBody(required = false) ZjTzSecurityManagementSystem zjTzSecurityManagementSystem) {
        return zjTzSecurityManagementSystemService.getZjTzSecurityManagementSystemDetails(zjTzSecurityManagementSystem);
    }
    
    @ApiOperation(value="新增安全管理制度（添加附件）", notes="新增安全管理制度（添加附件）")
    @ApiImplicitParam(name = "zjTzSecurityManagementSystem", value = "安全管理制度entity", dataType = "ZjTzSecurityManagementSystem")
    @RequireToken
    @PostMapping("/addZjTzSecurityManagementSystemAddFile")
    public ResponseEntity addZjTzSecurityManagementSystemAddFile(@RequestBody(required = false) ZjTzSecurityManagementSystem zjTzSecurityManagementSystem) {
    	return zjTzSecurityManagementSystemService.saveZjTzSecurityManagementSystemAddFile(zjTzSecurityManagementSystem);
    }

    @ApiOperation(value="更新安全管理制度（添加附件）", notes="更新安全管理制度（添加附件）")
    @ApiImplicitParam(name = "zjTzSecurityManagementSystem", value = "安全管理制度entity", dataType = "ZjTzSecurityManagementSystem")
    @RequireToken
    @PostMapping("/updateZjTzSecurityManagementSystemAddFile")
    public ResponseEntity updateZjTzSecurityManagementSystemAddFile(@RequestBody(required = false) ZjTzSecurityManagementSystem zjTzSecurityManagementSystem) {
        return zjTzSecurityManagementSystemService.updateZjTzSecurityManagementSystemAddFile(zjTzSecurityManagementSystem);
    }
    
    @ApiOperation(value="新增安全管理制度", notes="新增安全管理制度")
    @ApiImplicitParam(name = "zjTzSecurityManagementSystem", value = "安全管理制度entity", dataType = "ZjTzSecurityManagementSystem")
    @RequireToken
    @PostMapping("/addZjTzSecurityManagementSystem")
    public ResponseEntity addZjTzSecurityManagementSystem(@RequestBody(required = false) ZjTzSecurityManagementSystem zjTzSecurityManagementSystem) {
        return zjTzSecurityManagementSystemService.saveZjTzSecurityManagementSystem(zjTzSecurityManagementSystem);
    }
    
    @ApiOperation(value="更新安全管理制度", notes="更新安全管理制度")
    @ApiImplicitParam(name = "zjTzSecurityManagementSystem", value = "安全管理制度entity", dataType = "ZjTzSecurityManagementSystem")
    @RequireToken
    @PostMapping("/updateZjTzSecurityManagementSystem")
    public ResponseEntity updateZjTzSecurityManagementSystem(@RequestBody(required = false) ZjTzSecurityManagementSystem zjTzSecurityManagementSystem) {
    	return zjTzSecurityManagementSystemService.updateZjTzSecurityManagementSystem(zjTzSecurityManagementSystem);
    }

    @ApiOperation(value="删除安全管理制度", notes="删除安全管理制度")
    @ApiImplicitParam(name = "zjTzSecurityManagementSystemList", value = "安全管理制度List", dataType = "List<ZjTzSecurityManagementSystem>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZjTzSecurityManagementSystem")
    public ResponseEntity batchDeleteUpdateZjTzSecurityManagementSystem(@RequestBody(required = false) List<ZjTzSecurityManagementSystem> zjTzSecurityManagementSystemList) {
        return zjTzSecurityManagementSystemService.batchDeleteUpdateZjTzSecurityManagementSystem(zjTzSecurityManagementSystemList);
    }

}
