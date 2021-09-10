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
import com.apih5.mybatis.pojo.ZjTzPermission;
import com.apih5.service.ZjTzPermissionService;

@RestController
public class ZjTzPermissionController {

    @Autowired(required = true)
    private ZjTzPermissionService zjTzPermissionService;

    @ApiOperation(value="查询项目数据权限", notes="查询项目数据权限")
    @ApiImplicitParam(name = "zjTzPermission", value = "项目数据权限entity", dataType = "ZjTzPermission")
    @RequireToken
    @PostMapping("/getZjTzPermissionList")
    public ResponseEntity getZjTzPermissionList(@RequestBody(required = false) ZjTzPermission zjTzPermission) {
        return zjTzPermissionService.getZjTzPermissionListByCondition(zjTzPermission);
    }
    
    @ApiOperation(value="查询详情项目数据权限", notes="查询详情项目数据权限")
    @ApiImplicitParam(name = "zjTzPermission", value = "项目数据权限entity", dataType = "ZjTzPermission")
    @RequireToken
    @PostMapping("/getZjTzPermissionDetails")
    public ResponseEntity getZjTzPermissionDetails(@RequestBody(required = false) ZjTzPermission zjTzPermission) {
        return zjTzPermissionService.getZjTzPermissionDetails(zjTzPermission);
    }

    @ApiOperation(value="新增项目数据权限", notes="新增项目数据权限")
    @ApiImplicitParam(name = "zjTzPermission", value = "项目数据权限entity", dataType = "ZjTzPermission")
    @RequireToken
    @PostMapping("/addZjTzPermission")
    public ResponseEntity addZjTzPermission(@RequestBody(required = false) ZjTzPermission zjTzPermission) {
        return zjTzPermissionService.saveZjTzPermission(zjTzPermission);
    }

    @ApiOperation(value="更新项目数据权限", notes="更新项目数据权限")
    @ApiImplicitParam(name = "zjTzPermission", value = "项目数据权限entity", dataType = "ZjTzPermission")
    @RequireToken
    @PostMapping("/updateZjTzPermission")
    public ResponseEntity updateZjTzPermission(@RequestBody(required = false) ZjTzPermission zjTzPermission) {
        return zjTzPermissionService.updateZjTzPermission(zjTzPermission);
    }

    @ApiOperation(value="删除项目数据权限", notes="删除项目数据权限")
    @ApiImplicitParam(name = "zjTzPermissionList", value = "项目数据权限List", dataType = "List<ZjTzPermission>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZjTzPermission")
    public ResponseEntity batchDeleteUpdateZjTzPermission(@RequestBody(required = false) List<ZjTzPermission> zjTzPermissionList) {
        return zjTzPermissionService.batchDeleteUpdateZjTzPermission(zjTzPermissionList);
    }

    // --扩展
    @ApiOperation(value="查询项目数据权限-项目获取", notes="查询项目数据权限-项目获取")
    @ApiImplicitParam(name = "zjTzPermission", value = "项目数据权限entity", dataType = "ZjTzPermission")
    @RequireToken
    @PostMapping("/getZjTzPermissionListByProject")
    public ResponseEntity getZjTzPermissionListByProject(@RequestBody(required = false) ZjTzPermission zjTzPermission) {
        return zjTzPermissionService.getZjTzPermissionListByProject(zjTzPermission);
    }

//    @ApiOperation(value="根据用户获取当前所属公司或项目ID", notes="根据用户获取当前所属公司或项目ID")
//    @ApiImplicitParam(name = "zjTzPermission", value = "项目数据权限entity", dataType = "ZjTzPermission")
//    @RequireToken
//    @PostMapping("/getZjtzSysDepartmentIdType")
//    public ResponseEntity getZjtzSysDepartmentIdType(@RequestBody(required = false) ZjTzPermission zjTzPermission) {
//        return zjTzPermissionService.getZjtzSysDepartmentIdType(zjTzPermission);
//    }
}
