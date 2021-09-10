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
import com.apih5.mybatis.pojo.SysDepartmentPermission;
import com.apih5.service.SysDepartmentPermissionService;

@RestController
public class SysDepartmentPermissionController {

    @Autowired(required = true)
    private SysDepartmentPermissionService sysDepartmentPermissionService;

    @ApiOperation(value="查询劳务人员权限", notes="查询劳务人员权限")
    @ApiImplicitParam(name = "sysDepartmentPermission", value = "劳务人员权限entity", dataType = "SysDepartmentPermission")
    @RequireToken
    @PostMapping("/getSysDepartmentPermissionList")
    public ResponseEntity getSysDepartmentPermissionList(@RequestBody(required = false) SysDepartmentPermission sysDepartmentPermission) {
        return sysDepartmentPermissionService.getSysDepartmentPermissionListByCondition(sysDepartmentPermission);
    }

    @ApiOperation(value="新增劳务人员权限", notes="新增劳务人员权限")
    @ApiImplicitParam(name = "sysDepartmentPermission", value = "劳务人员权限entity", dataType = "SysDepartmentPermission")
    @RequireToken
    @PostMapping("/addSysDepartmentPermission")
    public ResponseEntity addSysDepartmentPermission(@RequestBody(required = false) SysDepartmentPermission sysDepartmentPermission) {
        return sysDepartmentPermissionService.saveSysDepartmentPermission(sysDepartmentPermission);
    }

    @ApiOperation(value="更新劳务人员权限", notes="更新劳务人员权限")
    @ApiImplicitParam(name = "sysDepartmentPermission", value = "劳务人员权限entity", dataType = "SysDepartmentPermission")
    @RequireToken
    @PostMapping("/updateSysDepartmentPermission")
    public ResponseEntity updateSysDepartmentPermission(@RequestBody(required = false) SysDepartmentPermission sysDepartmentPermission) {
        return sysDepartmentPermissionService.updateSysDepartmentPermission(sysDepartmentPermission);
    }

    @ApiOperation(value="删除劳务人员权限", notes="删除劳务人员权限")
    @ApiImplicitParam(name = "sysDepartmentPermissionList", value = "劳务人员权限List", dataType = "List<SysDepartmentPermission>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateSysDepartmentPermission")
    public ResponseEntity batchDeleteUpdateSysDepartmentPermission(@RequestBody(required = false) List<SysDepartmentPermission> sysDepartmentPermissionList) {
        return sysDepartmentPermissionService.batchDeleteUpdateSysDepartmentPermission(sysDepartmentPermissionList);
    }

}
