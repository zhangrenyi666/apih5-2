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
import com.apih5.mybatis.pojo.ZxSfOtherAddFile;
import com.apih5.service.ZxSfOtherAddFileService;

@RestController
public class ZxSfOtherAddFileController {

    @Autowired(required = true)
    private ZxSfOtherAddFileService zxSfOtherAddFileService;

    @ApiOperation(value="查询应急预案", notes="查询应急预案")
    @ApiImplicitParam(name = "zxSfOtherAddFile", value = "应急预案entity", dataType = "ZxSfOtherAddFile")
    @RequireToken
    @PostMapping("/getZxSfOtherAddFileList")
    public ResponseEntity getZxSfOtherAddFileList(@RequestBody(required = false) ZxSfOtherAddFile zxSfOtherAddFile) {
        return zxSfOtherAddFileService.getZxSfOtherAddFileListByCondition(zxSfOtherAddFile);
    }

    @ApiOperation(value="查询详情应急预案", notes="查询详情应急预案")
    @ApiImplicitParam(name = "zxSfOtherAddFile", value = "应急预案entity", dataType = "ZxSfOtherAddFile")
    @RequireToken
    @PostMapping("/getZxSfOtherAddFileDetail")
    public ResponseEntity getZxSfOtherAddFileDetail(@RequestBody(required = false) ZxSfOtherAddFile zxSfOtherAddFile) {
        return zxSfOtherAddFileService.getZxSfOtherAddFileDetail(zxSfOtherAddFile);
    }

    @ApiOperation(value="新增应急预案", notes="新增应急预案")
    @ApiImplicitParam(name = "zxSfOtherAddFile", value = "应急预案entity", dataType = "ZxSfOtherAddFile")
    @RequireToken
    @PostMapping("/addZxSfOtherAddFile")
    public ResponseEntity addZxSfOtherAddFile(@RequestBody(required = false) ZxSfOtherAddFile zxSfOtherAddFile) {
        return zxSfOtherAddFileService.saveZxSfOtherAddFile(zxSfOtherAddFile);
    }

    @ApiOperation(value="更新应急预案", notes="更新应急预案")
    @ApiImplicitParam(name = "zxSfOtherAddFile", value = "应急预案entity", dataType = "ZxSfOtherAddFile")
    @RequireToken
    @PostMapping("/updateZxSfOtherAddFile")
    public ResponseEntity updateZxSfOtherAddFile(@RequestBody(required = false) ZxSfOtherAddFile zxSfOtherAddFile) {
        return zxSfOtherAddFileService.updateZxSfOtherAddFile(zxSfOtherAddFile);
    }

    @ApiOperation(value="删除应急预案", notes="删除应急预案")
    @ApiImplicitParam(name = "zxSfOtherAddFileList", value = "应急预案List", dataType = "List<ZxSfOtherAddFile>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxSfOtherAddFile")
    public ResponseEntity batchDeleteUpdateZxSfOtherAddFile(@RequestBody(required = false) List<ZxSfOtherAddFile> zxSfOtherAddFileList) {
        return zxSfOtherAddFileService.batchDeleteUpdateZxSfOtherAddFile(zxSfOtherAddFileList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
