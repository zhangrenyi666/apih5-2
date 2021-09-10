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
import com.apih5.mybatis.pojo.ZjTzImportDoc;
import com.apih5.service.ZjTzImportDocService;

@RestController
public class ZjTzImportDocController {

    @Autowired(required = true)
    private ZjTzImportDocService zjTzImportDocService;

    @ApiOperation(value="查询重要文件", notes="查询重要文件")
    @ApiImplicitParam(name = "zjTzImportDoc", value = "重要文件entity", dataType = "ZjTzImportDoc")
    @RequireToken
    @PostMapping("/getZjTzImportDocList")
    public ResponseEntity getZjTzImportDocList(@RequestBody(required = false) ZjTzImportDoc zjTzImportDoc) {
        return zjTzImportDocService.getZjTzImportDocListByCondition(zjTzImportDoc);
    }

    @ApiOperation(value="查询详情重要文件", notes="查询详情重要文件")
    @ApiImplicitParam(name = "zjTzImportDoc", value = "重要文件entity", dataType = "ZjTzImportDoc")
    @RequireToken
    @PostMapping("/getZjTzImportDocDetails")
    public ResponseEntity getZjTzImportDocDetails(@RequestBody(required = false) ZjTzImportDoc zjTzImportDoc) {
        return zjTzImportDocService.getZjTzImportDocDetails(zjTzImportDoc);
    }

    @ApiOperation(value="新增重要文件", notes="新增重要文件")
    @ApiImplicitParam(name = "zjTzImportDoc", value = "重要文件entity", dataType = "ZjTzImportDoc")
    @RequireToken
    @PostMapping("/addZjTzImportDoc")
    public ResponseEntity addZjTzImportDoc(@RequestBody(required = false) ZjTzImportDoc zjTzImportDoc) {
        return zjTzImportDocService.saveZjTzImportDoc(zjTzImportDoc);
    }

    @ApiOperation(value="更新重要文件", notes="更新重要文件")
    @ApiImplicitParam(name = "zjTzImportDoc", value = "重要文件entity", dataType = "ZjTzImportDoc")
    @RequireToken
    @PostMapping("/updateZjTzImportDoc")
    public ResponseEntity updateZjTzImportDoc(@RequestBody(required = false) ZjTzImportDoc zjTzImportDoc) {
        return zjTzImportDocService.updateZjTzImportDoc(zjTzImportDoc);
    }

    @ApiOperation(value="删除重要文件", notes="删除重要文件")
    @ApiImplicitParam(name = "zjTzImportDocList", value = "重要文件List", dataType = "List<ZjTzImportDoc>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZjTzImportDoc")
    public ResponseEntity batchDeleteUpdateZjTzImportDoc(@RequestBody(required = false) List<ZjTzImportDoc> zjTzImportDocList) {
        return zjTzImportDocService.batchDeleteUpdateZjTzImportDoc(zjTzImportDocList);
    }
    
    @ApiOperation(value="广而告之重要文件", notes="广而告之重要文件")
    @ApiImplicitParam(name = "zjTzImportDocList", value = "重要文件List", dataType = "List<ZjTzImportDoc>")
    @RequireToken
    @PostMapping("/toHomeShowZjTzImportDoc")
    public ResponseEntity toHomeShowZjTzImportDoc(@RequestBody(required = false) ZjTzImportDoc zjTzImportDoc) {
        return zjTzImportDocService.toHomeShowZjTzImportDoc(zjTzImportDoc);
    }
    
    @ApiOperation(value="批量发布重要文件", notes="批量发布重要文件")
    @ApiImplicitParam(name = "zjTzImportDocList", value = "重要文件List", dataType = "List<ZjTzImportDoc>")
    @RequireToken
    @PostMapping("/batchDeleteReleaseZjTzImportDoc")
    public ResponseEntity batchDeleteReleaseZjTzImportDoc(@RequestBody(required = false) List<ZjTzImportDoc> zjTzImportDocList) {
        return zjTzImportDocService.batchDeleteReleaseZjTzImportDoc(zjTzImportDocList);
    }
    
    @ApiOperation(value="批量撤回重要文件", notes="批量撤回重要文件")
    @ApiImplicitParam(name = "zjTzImportDocList", value = "重要文件List", dataType = "List<ZjTzImportDoc>")
    @RequireToken
    @PostMapping("/batchDeleteRecallZjTzImportDoc")
    public ResponseEntity batchDeleteRecallZjTzImportDoc(@RequestBody(required = false) List<ZjTzImportDoc> zjTzImportDocList) {
        return zjTzImportDocService.batchDeleteRecallZjTzImportDoc(zjTzImportDocList);
    }
    
    @ApiOperation(value="批量下载重要文件附件", notes="批量下载重要文件附件")
    @ApiImplicitParam(name = "zjTzImportDocList", value = "重要文件List", dataType = "List<ZjTzImportDoc>")
    @RequireToken
    @PostMapping("/batchExportZjTzImportDocFile")
    public ResponseEntity batchExportZjTzImportDocFile(@RequestBody(required = false) List<ZjTzImportDoc> zjTzImportDocList) {
        return zjTzImportDocService.batchExportZjTzImportDocFile(zjTzImportDocList);
    }


}
