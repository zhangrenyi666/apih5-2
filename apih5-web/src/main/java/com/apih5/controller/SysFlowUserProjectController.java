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
import com.apih5.mybatis.pojo.SysFlowUserProject;
import com.apih5.service.SysFlowUserProjectService;

@RestController
public class SysFlowUserProjectController {

    @Autowired(required = true)
    private SysFlowUserProjectService sysFlowUserProjectService;

    @ApiOperation(value="查询流程用户项目关系", notes="查询流程用户项目关系")
    @ApiImplicitParam(name = "sysFlowUserProject", value = "流程用户项目关系entity", dataType = "SysFlowUserProject")
    @RequireToken
    @PostMapping("/getSysFlowUserProjectList")
    public ResponseEntity getSysFlowUserProjectList(@RequestBody(required = false) SysFlowUserProject sysFlowUserProject) {
        return sysFlowUserProjectService.getSysFlowUserProjectListByCondition(sysFlowUserProject);
    }

    @ApiOperation(value="查询详情流程用户项目关系", notes="查询详情流程用户项目关系")
    @ApiImplicitParam(name = "sysFlowUserProject", value = "流程用户项目关系entity", dataType = "SysFlowUserProject")
    @RequireToken
    @PostMapping("/getSysFlowUserProjectDetail")
    public ResponseEntity getSysFlowUserProjectDetail(@RequestBody(required = false) SysFlowUserProject sysFlowUserProject) {
        return sysFlowUserProjectService.getSysFlowUserProjectDetail(sysFlowUserProject);
    }

    @ApiOperation(value="新增流程用户项目关系", notes="新增流程用户项目关系")
    @ApiImplicitParam(name = "sysFlowUserProject", value = "流程用户项目关系entity", dataType = "SysFlowUserProject")
    @RequireToken
    @PostMapping("/addSysFlowUserProject")
    public ResponseEntity addSysFlowUserProject(@RequestBody(required = false) SysFlowUserProject sysFlowUserProject) {
        return sysFlowUserProjectService.saveSysFlowUserProject(sysFlowUserProject);
    }

    @ApiOperation(value="更新流程用户项目关系", notes="更新流程用户项目关系")
    @ApiImplicitParam(name = "sysFlowUserProject", value = "流程用户项目关系entity", dataType = "SysFlowUserProject")
    @RequireToken
    @PostMapping("/updateSysFlowUserProject")
    public ResponseEntity updateSysFlowUserProject(@RequestBody(required = false) SysFlowUserProject sysFlowUserProject) {
        return sysFlowUserProjectService.updateSysFlowUserProject(sysFlowUserProject);
    }

    @ApiOperation(value="删除流程用户项目关系", notes="删除流程用户项目关系")
    @ApiImplicitParam(name = "sysFlowUserProjectList", value = "流程用户项目关系List", dataType = "List<SysFlowUserProject>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateSysFlowUserProject")
    public ResponseEntity batchDeleteUpdateSysFlowUserProject(@RequestBody(required = false) List<SysFlowUserProject> sysFlowUserProjectList) {
        return sysFlowUserProjectService.batchDeleteUpdateSysFlowUserProject(sysFlowUserProjectList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    @ApiOperation(value="项目类型查询", notes="项目类型查询")
    @ApiImplicitParam(name = "sysFlowUserProject", value = "流程用户项目关系entity", dataType = "SysFlowUserProject")
    @RequireToken
    @PostMapping("/getSysFlowUserProjectType")
    public String getSysFlowUserProjectType(@RequestBody(required = false) SysFlowUserProject sysFlowUserProject) {
        return sysFlowUserProjectService.getSysFlowUserProjectType(sysFlowUserProject);
    }
    
    @ApiOperation(value="新增流程用户项目关系", notes="新增流程用户项目关系")
    @ApiImplicitParam(name = "sysFlowUserProject", value = "流程用户项目关系entity", dataType = "SysFlowUserProject")
    @RequireToken
    @PostMapping("/addSysFlowUserProjectByFlow")
    public ResponseEntity addSysFlowUserProjectByFlow(@RequestBody(required = false) SysFlowUserProject sysFlowUserProject) {
        return sysFlowUserProjectService.addSysFlowUserProjectByFlow(sysFlowUserProject);
    }
}
