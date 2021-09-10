package com.apih5.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import com.apih5.framework.annotation.RequireToken;
import com.apih5.framework.api.sysdb.entity.SysFiles;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.service.SysFilesService;

@RestController
public class SysFilesController {

    @Autowired(required = true)
    private SysFilesService sysFilesService;

    @ApiOperation(value="查询文件", notes="查询文件")
    @ApiImplicitParam(name = "sysFiles", value = "文件entity", dataType = "SysFiles")
    @RequireToken
    @PostMapping("/getSysFilesList")
    public ResponseEntity getSysFilesList(@RequestBody(required = false) SysFiles sysFiles) {
        return sysFilesService.getSysFilesListByCondition(sysFiles);
    }

    @ApiOperation(value="新增文件", notes="新增文件")
    @ApiImplicitParam(name = "sysFiles", value = "文件entity", dataType = "SysFiles")
    @RequireToken
    @PostMapping("/addSysFiles")
    public ResponseEntity addSysFiles(@RequestBody(required = false) SysFiles sysFiles) {
        return sysFilesService.saveSysFiles(sysFiles);
    }

    @ApiOperation(value="更新文件", notes="更新文件")
    @ApiImplicitParam(name = "sysFiles", value = "文件entity", dataType = "SysFiles")
    @RequireToken
    @PostMapping("/updateSysFiles")
    public ResponseEntity updateSysFiles(@RequestBody(required = false) SysFiles sysFiles) {
        return sysFilesService.updateSysFiles(sysFiles);
    }

    @ApiOperation(value="删除文件", notes="删除文件")
    @ApiImplicitParam(name = "sysFilesList", value = "文件List", dataType = "List<SysFiles>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateSysFiles")
    public ResponseEntity batchDeleteUpdateSysFiles(@RequestBody(required = false) List<SysFiles> sysFilesList) {
        return sysFilesService.batchDeleteUpdateSysFiles(sysFilesList);
    }

}
