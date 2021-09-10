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
import com.apih5.mybatis.pojo.ZjTzRunFile;
import com.apih5.service.ZjTzRunFileService;

@RestController
public class ZjTzRunFileController {

    @Autowired(required = true)
    private ZjTzRunFileService zjTzRunFileService;

    @ApiOperation(value="查询运营文件管理", notes="查询运营文件管理")
    @ApiImplicitParam(name = "zjTzRunFile", value = "运营文件管理entity", dataType = "ZjTzRunFile")
    @RequireToken
    @PostMapping("/getZjTzRunFileList")
    public ResponseEntity getZjTzRunFileList(@RequestBody(required = false) ZjTzRunFile zjTzRunFile) {
        return zjTzRunFileService.getZjTzRunFileListByCondition(zjTzRunFile);
    }

    @ApiOperation(value="查询详情运营文件管理", notes="查询详情运营文件管理")
    @ApiImplicitParam(name = "zjTzRunFile", value = "运营文件管理entity", dataType = "ZjTzRunFile")
    @RequireToken
    @PostMapping("/getZjTzRunFileDetails")
    public ResponseEntity getZjTzRunFileDetails(@RequestBody(required = false) ZjTzRunFile zjTzRunFile) {
        return zjTzRunFileService.getZjTzRunFileDetails(zjTzRunFile);
    }

    @ApiOperation(value="新增运营文件管理", notes="新增运营文件管理")
    @ApiImplicitParam(name = "zjTzRunFile", value = "运营文件管理entity", dataType = "ZjTzRunFile")
    @RequireToken
    @PostMapping("/addZjTzRunFile")
    public ResponseEntity addZjTzRunFile(@RequestBody(required = false) ZjTzRunFile zjTzRunFile) {
        return zjTzRunFileService.saveZjTzRunFile(zjTzRunFile);
    }

    @ApiOperation(value="更新运营文件管理", notes="更新运营文件管理")
    @ApiImplicitParam(name = "zjTzRunFile", value = "运营文件管理entity", dataType = "ZjTzRunFile")
    @RequireToken
    @PostMapping("/updateZjTzRunFile")
    public ResponseEntity updateZjTzRunFile(@RequestBody(required = false) ZjTzRunFile zjTzRunFile) {
        return zjTzRunFileService.updateZjTzRunFile(zjTzRunFile);
    }

    @ApiOperation(value="删除运营文件管理", notes="删除运营文件管理")
    @ApiImplicitParam(name = "zjTzRunFileList", value = "运营文件管理List", dataType = "List<ZjTzRunFile>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZjTzRunFile")
    public ResponseEntity batchDeleteUpdateZjTzRunFile(@RequestBody(required = false) List<ZjTzRunFile> zjTzRunFileList) {
        return zjTzRunFileService.batchDeleteUpdateZjTzRunFile(zjTzRunFileList);
    }
    
    @ApiOperation(value="批量下载运营文件管理附件", notes="批量下载运营文件管理附件")
    @ApiImplicitParam(name = "zjTzRulesList", value = "运营文件管理List", dataType = "List<ZjTzRunFile>")
    @RequireToken
    @PostMapping("/batchExportZjTzRunFile")
    public ResponseEntity batchExportZjTzRunFile(@RequestBody(required = false) List<ZjTzRunFile> zjTzRunFileList) {
        return zjTzRunFileService.batchExportZjTzRunFile(zjTzRunFileList);
    }

}
