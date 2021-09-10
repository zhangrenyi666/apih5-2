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
import com.apih5.mybatis.pojo.ZjTzOtherManagementSystem;
import com.apih5.service.ZjTzOtherManagementSystemService;

@RestController
public class ZjTzQHSEOtherManagementSystemController {

    @Autowired(required = true)
    private ZjTzOtherManagementSystemService zjTzOtherManagementSystemService;

    @ApiOperation(value="查询其他管理制度", notes="查询其他管理制度")
    @ApiImplicitParam(name = "zjTzOtherManagementSystem", value = "其他管理制度entity", dataType = "ZjTzOtherManagementSystem")
    @RequireToken
    @PostMapping("/getZjTzOtherManagementSystemList")
    public ResponseEntity getZjTzOtherManagementSystemList(@RequestBody(required = false) ZjTzOtherManagementSystem zjTzOtherManagementSystem) {
        return zjTzOtherManagementSystemService.getZjTzOtherManagementSystemListByCondition(zjTzOtherManagementSystem);
    }

    @ApiOperation(value="查询详情其他管理制度", notes="查询详情其他管理制度")
    @ApiImplicitParam(name = "zjTzOtherManagementSystem", value = "其他管理制度entity", dataType = "ZjTzOtherManagementSystem")
    @RequireToken
    @PostMapping("/getZjTzOtherManagementSystemDetails")
    public ResponseEntity getZjTzOtherManagementSystemDetails(@RequestBody(required = false) ZjTzOtherManagementSystem zjTzOtherManagementSystem) {
        return zjTzOtherManagementSystemService.getZjTzOtherManagementSystemDetails(zjTzOtherManagementSystem);
    }

    @ApiOperation(value="新增其他管理制度（添加附件）", notes="新增其他管理制度（添加附件）")
    @ApiImplicitParam(name = "zjTzOtherManagementSystem", value = "其他管理制度entity", dataType = "ZjTzOtherManagementSystem")
    @RequireToken
    @PostMapping("/addZjTzOtherManagementSystemAddFile")
    public ResponseEntity addZjTzOtherManagementSystemAddFile(@RequestBody(required = false) ZjTzOtherManagementSystem zjTzOtherManagementSystem) {
        return zjTzOtherManagementSystemService.saveZjTzOtherManagementSystemAddFile(zjTzOtherManagementSystem);
    }

    @ApiOperation(value="更新其他管理制度（添加附件）", notes="更新其他管理制度（添加附件）")
    @ApiImplicitParam(name = "zjTzOtherManagementSystem", value = "其他管理制度entity", dataType = "ZjTzOtherManagementSystem")
    @RequireToken
    @PostMapping("/updateZjTzOtherManagementSystemAddFile")
    public ResponseEntity updateZjTzOtherManagementSystemAddFile(@RequestBody(required = false) ZjTzOtherManagementSystem zjTzOtherManagementSystem) {
        return zjTzOtherManagementSystemService.updateZjTzOtherManagementSystemAddFile(zjTzOtherManagementSystem);
    }
    
    @ApiOperation(value="新增其他管理制度", notes="新增其他管理制度")
    @ApiImplicitParam(name = "zjTzOtherManagementSystem", value = "其他管理制度entity", dataType = "ZjTzOtherManagementSystem")
    @RequireToken
    @PostMapping("/addZjTzOtherManagementSystem")
    public ResponseEntity addZjTzOtherManagementSystem(@RequestBody(required = false) ZjTzOtherManagementSystem zjTzOtherManagementSystem) {
    	return zjTzOtherManagementSystemService.saveZjTzOtherManagementSystem(zjTzOtherManagementSystem);
    }
    
    @ApiOperation(value="更新其他管理制度", notes="更新其他管理制度")
    @ApiImplicitParam(name = "zjTzOtherManagementSystem", value = "其他管理制度entity", dataType = "ZjTzOtherManagementSystem")
    @RequireToken
    @PostMapping("/updateZjTzOtherManagementSystem")
    public ResponseEntity updateZjTzOtherManagementSystem(@RequestBody(required = false) ZjTzOtherManagementSystem zjTzOtherManagementSystem) {
    	return zjTzOtherManagementSystemService.updateZjTzOtherManagementSystem(zjTzOtherManagementSystem);
    }

    @ApiOperation(value="删除其他管理制度", notes="删除其他管理制度")
    @ApiImplicitParam(name = "zjTzOtherManagementSystemList", value = "其他管理制度List", dataType = "List<ZjTzOtherManagementSystem>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZjTzOtherManagementSystem")
    public ResponseEntity batchDeleteUpdateZjTzOtherManagementSystem(@RequestBody(required = false) List<ZjTzOtherManagementSystem> zjTzOtherManagementSystemList) {
        return zjTzOtherManagementSystemService.batchDeleteUpdateZjTzOtherManagementSystem(zjTzOtherManagementSystemList);
    }

}
