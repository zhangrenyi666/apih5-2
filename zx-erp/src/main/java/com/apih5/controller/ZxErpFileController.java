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
import com.apih5.mybatis.pojo.ZxErpFile;
import com.apih5.service.ZxErpFileService;

@RestController
public class ZxErpFileController {

    @Autowired(required = true)
    private ZxErpFileService zxErpFileService;

    @ApiOperation(value="查询附件", notes="查询附件")
    @ApiImplicitParam(name = "zxErpFile", value = "附件entity", dataType = "ZxErpFile")
    @RequireToken
    @PostMapping("/getZxErpFileList")
    public ResponseEntity getZxErpFileList(@RequestBody(required = false) ZxErpFile zxErpFile) {
        return zxErpFileService.getZxErpFileListByCondition(zxErpFile);
    }

    @ApiOperation(value="查询详情附件", notes="查询详情附件")
    @ApiImplicitParam(name = "zxErpFile", value = "附件entity", dataType = "ZxErpFile")
    @RequireToken
    @PostMapping("/getZxErpFileDetails")
    public ResponseEntity getZxErpFileDetails(@RequestBody(required = false) ZxErpFile zxErpFile) {
        return zxErpFileService.getZxErpFileDetails(zxErpFile);
    }

    @ApiOperation(value="新增附件", notes="新增附件")
    @ApiImplicitParam(name = "zxErpFile", value = "附件entity", dataType = "ZxErpFile")
    @RequireToken
    @PostMapping("/addZxErpFile")
    public ResponseEntity addZxErpFile(@RequestBody(required = false) ZxErpFile zxErpFile) {
        return zxErpFileService.saveZxErpFile(zxErpFile);
    }

    @ApiOperation(value="更新附件", notes="更新附件")
    @ApiImplicitParam(name = "zxErpFile", value = "附件entity", dataType = "ZxErpFile")
    @RequireToken
    @PostMapping("/updateZxErpFile")
    public ResponseEntity updateZxErpFile(@RequestBody(required = false) ZxErpFile zxErpFile) {
        return zxErpFileService.updateZxErpFile(zxErpFile);
    }

    @ApiOperation(value="删除附件", notes="删除附件")
    @ApiImplicitParam(name = "zxErpFileList", value = "附件List", dataType = "List<ZxErpFile>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxErpFile")
    public ResponseEntity batchDeleteUpdateZxErpFile(@RequestBody(required = false) List<ZxErpFile> zxErpFileList) {
        return zxErpFileService.batchDeleteUpdateZxErpFile(zxErpFileList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    @ApiOperation(value="根据otherId删除全部附件", notes="根据otherId删除全部附件")
    @ApiImplicitParam(name = "zxErpFileList", value = "附件List", dataType = "List<ZxErpFile>")
    @RequireToken
    @PostMapping("/deleteAllZxErpFile")
    public ResponseEntity deleteAllZxErpFile(@RequestBody(required = false) ZxErpFile zxErpFile) {
        return zxErpFileService.deleteAllZxErpFile(zxErpFile);
    }
}
