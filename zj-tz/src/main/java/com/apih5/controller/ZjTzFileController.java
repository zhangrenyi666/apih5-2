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
import com.apih5.mybatis.pojo.ZjTzFile;
import com.apih5.service.ZjTzFileService;

@RestController
public class ZjTzFileController {

    @Autowired(required = true)
    private ZjTzFileService zjTzFileService;

    @ApiOperation(value="查询投资附件", notes="查询投资附件")
    @ApiImplicitParam(name = "zjTzFile", value = "投资附件entity", dataType = "ZjTzFile")
    @RequireToken
    @PostMapping("/getZjTzFileList")
    public ResponseEntity getZjTzFileList(@RequestBody(required = false) ZjTzFile zjTzFile) {
        return zjTzFileService.getZjTzFileListByCondition(zjTzFile);
    }

    @ApiOperation(value="查询详情投资附件", notes="查询详情投资附件")
    @ApiImplicitParam(name = "zjTzFile", value = "投资附件entity", dataType = "ZjTzFile")
    @RequireToken
    @PostMapping("/getZjTzFileDetails")
    public ResponseEntity getZjTzFileDetails(@RequestBody(required = false) ZjTzFile zjTzFile) {
        return zjTzFileService.getZjTzFileDetails(zjTzFile);
    }

    @ApiOperation(value="新增投资附件", notes="新增投资附件")
    @ApiImplicitParam(name = "zjTzFile", value = "投资附件entity", dataType = "ZjTzFile")
    @RequireToken
    @PostMapping("/addZjTzFile")
    public ResponseEntity addZjTzFile(@RequestBody(required = false) ZjTzFile zjTzFile) {
        return zjTzFileService.saveZjTzFile(zjTzFile);
    }

    @ApiOperation(value="更新投资附件", notes="更新投资附件")
    @ApiImplicitParam(name = "zjTzFile", value = "投资附件entity", dataType = "ZjTzFile")
    @RequireToken
    @PostMapping("/updateZjTzFile")
    public ResponseEntity updateZjTzFile(@RequestBody(required = false) ZjTzFile zjTzFile) {
        return zjTzFileService.updateZjTzFile(zjTzFile);
    }

    @ApiOperation(value="删除投资附件", notes="删除投资附件")
    @ApiImplicitParam(name = "zjTzFileList", value = "投资附件List", dataType = "List<ZjTzFile>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZjTzFile")
    public ResponseEntity batchDeleteUpdateZjTzFile(@RequestBody(required = false) List<ZjTzFile> zjTzFileList) {
        return zjTzFileService.batchDeleteUpdateZjTzFile(zjTzFileList);
    }

}
