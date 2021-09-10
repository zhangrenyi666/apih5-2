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
import com.apih5.mybatis.pojo.ZjTzPermissionSelect;
import com.apih5.mybatis.pojo.ZjTzPermissionSelect;
import com.apih5.service.ZjTzPermissionSelectService;

@RestController
public class ZjTzPermissionSelectController {

    @Autowired(required = true)
    private ZjTzPermissionSelectService zjTzPermissionSelectService;

    @ApiOperation(value="查询选中项目权限", notes="查询选中项目权限")
    @ApiImplicitParam(name = "zjTzPermissionSelect", value = "选中项目权限entity", dataType = "ZjTzPermissionSelect")
    @RequireToken
    @PostMapping("/getZjTzPermissionSelectList")
    public ResponseEntity getZjTzPermissionSelectList(@RequestBody(required = false) ZjTzPermissionSelect zjTzPermissionSelect) {
        return zjTzPermissionSelectService.getZjTzPermissionSelectListByCondition(zjTzPermissionSelect);
    }

    @ApiOperation(value="查询详情选中项目权限", notes="查询详情选中项目权限")
    @ApiImplicitParam(name = "zjTzPermissionSelect", value = "选中项目权限entity", dataType = "ZjTzPermissionSelect")
    @RequireToken
    @PostMapping("/getZjTzPermissionSelectDetails")
    public ResponseEntity getZjTzPermissionSelectDetails(@RequestBody(required = false) ZjTzPermissionSelect zjTzPermissionSelect) {
        return zjTzPermissionSelectService.getZjTzPermissionSelectDetails(zjTzPermissionSelect);
    }

    @ApiOperation(value="新增选中项目权限", notes="新增选中项目权限")
    @ApiImplicitParam(name = "zjTzPermissionSelect", value = "选中项目权限entity", dataType = "ZjTzPermissionSelect")
    @RequireToken
    @PostMapping("/addZjTzPermissionSelect")
    public ResponseEntity addZjTzPermissionSelect(@RequestBody(required = false) ZjTzPermissionSelect zjTzPermissionSelect) {
        return zjTzPermissionSelectService.saveZjTzPermissionSelect(zjTzPermissionSelect);
    }

    @ApiOperation(value="更新选中项目权限", notes="更新选中项目权限")
    @ApiImplicitParam(name = "zjTzPermissionSelect", value = "选中项目权限entity", dataType = "ZjTzPermissionSelect")
    @RequireToken
    @PostMapping("/updateZjTzPermissionSelect")
    public ResponseEntity updateZjTzPermissionSelect(@RequestBody(required = false) ZjTzPermissionSelect zjTzPermissionSelect) {
        return zjTzPermissionSelectService.updateZjTzPermissionSelect(zjTzPermissionSelect);
    }

    @ApiOperation(value="删除选中项目权限", notes="删除选中项目权限")
    @ApiImplicitParam(name = "zjTzPermissionSelectList", value = "选中项目权限List", dataType = "List<ZjTzPermissionSelect>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZjTzPermissionSelect")
    public ResponseEntity batchDeleteUpdateZjTzPermissionSelect(@RequestBody(required = false) List<ZjTzPermissionSelect> zjTzPermissionSelectList) {
        return zjTzPermissionSelectService.batchDeleteUpdateZjTzPermissionSelect(zjTzPermissionSelectList);
    }

    // ---扩展
    @ApiOperation(value="中项目权限-切换", notes="中项目权限-切换")
    @ApiImplicitParam(name = "zjTzPermissionSelect", value = "选中项目权限entity", dataType = "ZjTzPermissionSelect")
    @RequireToken
    @PostMapping("/changeZjTzProjectManagement")
    public ResponseEntity changeZjTzProjectManagement(@RequestBody(required = false) ZjTzPermissionSelect zjTzPermissionSelect) {
        return zjTzPermissionSelectService.changeZjTzProjectManagement(zjTzPermissionSelect);
    }
}
