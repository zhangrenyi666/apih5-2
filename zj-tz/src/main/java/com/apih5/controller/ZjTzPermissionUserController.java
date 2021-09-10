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
import com.apih5.mybatis.pojo.ZjTzPermissionUser;
import com.apih5.service.ZjTzPermissionUserService;

@RestController
public class ZjTzPermissionUserController {

    @Autowired(required = true)
    private ZjTzPermissionUserService zjTzPermissionUserService;

    @ApiOperation(value="查询项目数据权限", notes="查询项目数据权限")
    @ApiImplicitParam(name = "zjTzPermissionUser", value = "项目数据权限entity", dataType = "ZjTzPermissionUser")
    @RequireToken
    @PostMapping("/getZjTzPermissionUserList")
    public ResponseEntity getZjTzPermissionUserList(@RequestBody(required = false) ZjTzPermissionUser zjTzPermissionUser) {
        return zjTzPermissionUserService.getZjTzPermissionUserListByCondition(zjTzPermissionUser);
    }

    @ApiOperation(value="查询详情项目数据权限", notes="查询详情项目数据权限")
    @ApiImplicitParam(name = "zjTzPermissionUser", value = "项目数据权限entity", dataType = "ZjTzPermissionUser")
    @RequireToken
    @PostMapping("/getZjTzPermissionUserDetails")
    public ResponseEntity getZjTzPermissionUserDetails(@RequestBody(required = false) ZjTzPermissionUser zjTzPermissionUser) {
        return zjTzPermissionUserService.getZjTzPermissionUserDetails(zjTzPermissionUser);
    }

    @ApiOperation(value="新增项目数据权限", notes="新增项目数据权限")
    @ApiImplicitParam(name = "zjTzPermissionUser", value = "项目数据权限entity", dataType = "ZjTzPermissionUser")
    @RequireToken
    @PostMapping("/addZjTzPermissionUser")
    public ResponseEntity addZjTzPermissionUser(@RequestBody(required = false) ZjTzPermissionUser zjTzPermissionUser) {
        return zjTzPermissionUserService.saveZjTzPermissionUser(zjTzPermissionUser);
    }

    @ApiOperation(value="更新项目数据权限", notes="更新项目数据权限")
    @ApiImplicitParam(name = "zjTzPermissionUser", value = "项目数据权限entity", dataType = "ZjTzPermissionUser")
    @RequireToken
    @PostMapping("/updateZjTzPermissionUser")
    public ResponseEntity updateZjTzPermissionUser(@RequestBody(required = false) ZjTzPermissionUser zjTzPermissionUser) {
        return zjTzPermissionUserService.updateZjTzPermissionUser(zjTzPermissionUser);
    }

    @ApiOperation(value="删除项目数据权限", notes="删除项目数据权限")
    @ApiImplicitParam(name = "zjTzPermissionUserList", value = "项目数据权限List", dataType = "List<ZjTzPermissionUser>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZjTzPermissionUser")
    public ResponseEntity batchDeleteUpdateZjTzPermissionUser(@RequestBody(required = false) List<ZjTzPermissionUser> zjTzPermissionUserList) {
        return zjTzPermissionUserService.batchDeleteUpdateZjTzPermissionUser(zjTzPermissionUserList);
    }

}
