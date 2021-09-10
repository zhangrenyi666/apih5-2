package com.apih5.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.apih5.framework.annotation.RequireToken;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.SysProject;
import com.apih5.service.SysProjectService;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

@RestController
public class SysProjectController {

    @Autowired(required = true)
    private SysProjectService sysProjectService;

    @ApiOperation(value="查询项目表", notes="查询项目表")
    @ApiImplicitParam(name = "sysProject", value = "项目表entity", dataType = "SysProject")
    @RequireToken
    @PostMapping("/getSysProjectList")
    public ResponseEntity getSysProjectList(@RequestBody(required = false) SysProject sysProject) {
        return sysProjectService.getSysProjectListByCondition(sysProject);
    }

    @ApiOperation(value="查询详情项目表", notes="查询详情项目表")
    @ApiImplicitParam(name = "sysProject", value = "项目表entity", dataType = "SysProject")
    @RequireToken
    @PostMapping("/getSysProjectDetail")
    public ResponseEntity getSysProjectDetail(@RequestBody(required = false) SysProject sysProject) {
        return sysProjectService.getSysProjectDetail(sysProject);
    }

    @ApiOperation(value="新增项目表", notes="新增项目表")
    @ApiImplicitParam(name = "sysProject", value = "项目表entity", dataType = "SysProject")
    @RequireToken
    @PostMapping("/addSysProject")
    public ResponseEntity addSysProject(@RequestBody(required = false) SysProject sysProject) {
        return sysProjectService.saveSysProject(sysProject);
    }

    @ApiOperation(value="更新项目表", notes="更新项目表")
    @ApiImplicitParam(name = "sysProject", value = "项目表entity", dataType = "SysProject")
    @RequireToken
    @PostMapping("/updateSysProject")
    public ResponseEntity updateSysProject(@RequestBody(required = false) SysProject sysProject) {
        return sysProjectService.updateSysProject(sysProject);
    }

    @ApiOperation(value="删除项目表", notes="删除项目表")
    @ApiImplicitParam(name = "sysProjectList", value = "项目表List", dataType = "List<SysProject>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateSysProject")
    public ResponseEntity batchDeleteUpdateSysProject(@RequestBody(required = false) List<SysProject> sysProjectList) {
        return sysProjectService.batchDeleteUpdateSysProject(sysProjectList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    
    @ApiOperation(value="新增项目关联部门表数据", notes="新增项目关联部门表数据")
    @ApiImplicitParam(name = "sysProject", value = "项目表entity", dataType = "SysProject")
    @RequireToken
    @PostMapping("/addSysProjectByRelation")
    public ResponseEntity addSysProjectByRelation(@RequestBody(required = false) SysProject sysProject) {
        return sysProjectService.saveSysProjectByRelation(sysProject);
    }
    
    @ApiOperation(value="修改项目关联部门表数据", notes="修改项目关联部门表数据")
    @ApiImplicitParam(name = "sysProject", value = "项目表entity", dataType = "SysProject")
    @RequireToken
    @PostMapping("/updateSysProjectByRelation")
    public ResponseEntity updateSysProjectByRelation(@RequestBody(required = false) SysProject sysProject) {
        return sysProjectService.updateSysProjectByRelation(sysProject);
    }
    
    @ApiOperation(value="主体完工的相关更新", notes="主体完工的相关更新")
    @ApiImplicitParam(name = "sysProject", value = "项目表entity", dataType = "SysProject")
    @RequireToken
    @PostMapping("/updateSysProjectByMainFinish")
    public ResponseEntity updateSysProjectByMainFinish(@RequestBody(required = false) SysProject sysProject) {
        return sysProjectService.updateSysProjectByMainFinish(sysProject);
    }
    
    @ApiOperation(value="业主台账的相关更新", notes="业主台账的相关更新")
    @ApiImplicitParam(name = "sysProject", value = "项目表entity", dataType = "SysProject")
    @RequireToken
    @PostMapping("/updateSysProjectByContract")
    public ResponseEntity updateSysProjectByContract(@RequestBody(required = false) SysProject sysProject) {
        return sysProjectService.updateSysProjectByContract(sysProject);
    }
    
    @ApiOperation(value="修改项目关联部门表数据", notes="修改项目关联部门表数据")
    @ApiImplicitParam(name = "sysProject", value = "项目表entity", dataType = "SysProject")
    @RequireToken
    @PostMapping("/batchDeleteUpdateSysProjectByRelation")
    public ResponseEntity batchDeleteUpdateSysProjectByRelation(@RequestBody(required = false)
            List<SysProject> sysProjectList) {
        return sysProjectService.batchDeleteUpdateSysProjectByRelation(sysProjectList);
    }
    
    @ApiOperation(value="查询项目数据权限-项目获取", notes="查询项目数据权限-项目获取")
    @ApiImplicitParam(name = "zjTzPermission", value = "项目数据权限entity", dataType = "ZjTzPermission")
    @RequireToken
    @PostMapping("/getSysPermissionListByProject")
    public ResponseEntity getSysPermissionListByProject(@RequestBody(required = false) SysProject sysProject) {
        return sysProjectService.getSysPermissionListByProject(sysProject);
    }
    
    @ApiOperation(value="查询项目数据权限-项目获取", notes="查询项目数据权限-项目获取")
    @ApiImplicitParam(name = "zjTzPermission", value = "项目数据权限entity", dataType = "ZjTzPermission")
    @RequireToken
    @PostMapping("/getSysProjectBySelect")
    public ResponseEntity getSysProjectBySelect(@RequestBody(required = false) SysProject sysProject) {
        return sysProjectService.getSysProjectBySelect(sysProject);
    }
    
    @ApiOperation(value="查询项目数据权限-公司获取", notes="查询项目数据权限-公司获取")
    @ApiImplicitParam(name = "zjTzPermission", value = "项目数据权限entity", dataType = "ZjTzPermission")
    @RequireToken
    @PostMapping("/getSysCompanyBySelect")
    public ResponseEntity getSysCompanyBySelect(@RequestBody(required = false) SysProject sysProject) {
        return sysProjectService.getSysCompanyBySelect(sysProject);
    }
    
    @ApiOperation(value="同时获取公司及项目下拉数据", notes="同时获取公司及项目下拉数据")
    @ApiImplicitParam(name = "zjTzPermission", value = "项目数据权限entity", dataType = "ZjTzPermission")
    @RequireToken
    @PostMapping("/getSysCompanyProject")
    public ResponseEntity getSysCompanyProject(@RequestBody(required = false) SysProject sysProject) {
        return sysProjectService.getSysCompanyProject(sysProject);
    }
    
    @ApiOperation(value="查询项目数据权限-项目获取", notes="查询项目数据权限-项目获取")
    @ApiImplicitParam(name = "zjTzPermission", value = "项目数据权限entity", dataType = "ZjTzPermission")
    @RequireToken
    @PostMapping("/changeSysProject")
    public ResponseEntity changeSysProject(@RequestBody(required = false) SysProject sysProject) {
        return sysProjectService.changeSysProject(sysProject);
    }
    
//    @ApiOperation(value="项目类型查询", notes="项目类型查询")
//    @ApiImplicitParam(name = "zjTzPermission", value = "项目数据权限entity", dataType = "ZjTzPermission")
//    @RequireToken
//    @PostMapping("/getSysProjectByFlowType")
//    public String getSysProjectByFlowType(@RequestBody(required = false) SysProject sysProject) {
//        return sysProjectService.getSysProjectByFlowType(sysProject);
//    }
}
