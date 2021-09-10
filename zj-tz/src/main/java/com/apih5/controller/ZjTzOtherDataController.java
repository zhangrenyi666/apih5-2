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
import com.apih5.mybatis.pojo.ZjTzOtherData;
import com.apih5.service.ZjTzOtherDataService;

@RestController
public class ZjTzOtherDataController {

    @Autowired(required = true)
    private ZjTzOtherDataService zjTzOtherDataService;

    @ApiOperation(value="查询其他资料", notes="查询其他资料")
    @ApiImplicitParam(name = "zjTzOtherData", value = "其他资料entity", dataType = "ZjTzOtherData")
    @RequireToken
    @PostMapping("/getZjTzOtherDataList")
    public ResponseEntity getZjTzOtherDataList(@RequestBody(required = false) ZjTzOtherData zjTzOtherData) {
        return zjTzOtherDataService.getZjTzOtherDataListByCondition(zjTzOtherData);
    }

    @ApiOperation(value="查询详情其他资料", notes="查询详情其他资料")
    @ApiImplicitParam(name = "zjTzOtherData", value = "其他资料entity", dataType = "ZjTzOtherData")
    @RequireToken
    @PostMapping("/getZjTzOtherDataDetails")
    public ResponseEntity getZjTzOtherDataDetails(@RequestBody(required = false) ZjTzOtherData zjTzOtherData) {
        return zjTzOtherDataService.getZjTzOtherDataDetails(zjTzOtherData);
    }

    @ApiOperation(value="新增其他资料", notes="新增其他资料")
    @ApiImplicitParam(name = "zjTzOtherData", value = "其他资料entity", dataType = "ZjTzOtherData")
    @RequireToken
    @PostMapping("/addZjTzOtherData")
    public ResponseEntity addZjTzOtherData(@RequestBody(required = false) ZjTzOtherData zjTzOtherData) {
        return zjTzOtherDataService.saveZjTzOtherData(zjTzOtherData);
    }

    @ApiOperation(value="更新其他资料", notes="更新其他资料")
    @ApiImplicitParam(name = "zjTzOtherData", value = "其他资料entity", dataType = "ZjTzOtherData")
    @RequireToken
    @PostMapping("/updateZjTzOtherData")
    public ResponseEntity updateZjTzOtherData(@RequestBody(required = false) ZjTzOtherData zjTzOtherData) {
        return zjTzOtherDataService.updateZjTzOtherData(zjTzOtherData);
    }

    @ApiOperation(value="删除其他资料", notes="删除其他资料")
    @ApiImplicitParam(name = "zjTzOtherDataList", value = "其他资料List", dataType = "List<ZjTzOtherData>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZjTzOtherData")
    public ResponseEntity batchDeleteUpdateZjTzOtherData(@RequestBody(required = false) List<ZjTzOtherData> zjTzOtherDataList) {
        return zjTzOtherDataService.batchDeleteUpdateZjTzOtherData(zjTzOtherDataList);
    }
    
    @ApiOperation(value="广而告之其他资料", notes="广而告之其他资料")
    @ApiImplicitParam(name = "zjTzOtherDataList", value = "其他资料List", dataType = "List<ZjTzOtherData>")
    @RequireToken
    @PostMapping("/toHomeShowZjTzOtherData")
    public ResponseEntity toHomeShowZjTzOtherData(@RequestBody(required = false) ZjTzOtherData zjTzOtherData) {
        return zjTzOtherDataService.toHomeShowZjTzOtherData(zjTzOtherData);
    }

    @ApiOperation(value="批量发布其他资料", notes="批量发布其他资料")
    @ApiImplicitParam(name = "zjTzOtherDataList", value = "其他资料List", dataType = "List<ZjTzOtherData>")
    @RequireToken
    @PostMapping("/batchDeleteReleaseZjTzOtherData")
    public ResponseEntity batchDeleteReleaseZjTzOtherData(@RequestBody(required = false) List<ZjTzOtherData> zjTzOtherDataList) {
        return zjTzOtherDataService.batchDeleteReleaseZjTzOtherData(zjTzOtherDataList);
    }
    
    @ApiOperation(value="批量撤回其他资料", notes="批量撤回其他资料")
    @ApiImplicitParam(name = "zjTzOtherDataList", value = "其他资料List", dataType = "List<ZjTzOtherData>")
    @RequireToken
    @PostMapping("/batchDeleteRecallZjTzOtherData")
    public ResponseEntity batchDeleteRecallZjTzOtherData(@RequestBody(required = false) List<ZjTzOtherData> zjTzOtherDataList) {
        return zjTzOtherDataService.batchDeleteRecallZjTzOtherData(zjTzOtherDataList);
    }
    
    @ApiOperation(value="批量下载其他资料", notes="批量撤回其他资料")
    @ApiImplicitParam(name = "zjTzOtherDataList", value = "其他资料List", dataType = "List<ZjTzOtherData>")
    @RequireToken
    @PostMapping("/batchExportZjTzOtherDataFile")
    public ResponseEntity batchExportZjTzOtherDataFile(@RequestBody(required = false) List<ZjTzOtherData> zjTzOtherDataList) {
        return zjTzOtherDataService.batchExportZjTzOtherDataFile(zjTzOtherDataList);
    }
    
    @ApiOperation(value="广而告之其他资料", notes="广而告之其他资料")
    @ApiImplicitParam(name = "zjTzOtherData", value = "其他资料entity", dataType = "ZjTzOtherData")
    @RequireToken
    @PostMapping("/getZjTzOtherDataHome")
    public ResponseEntity getZjTzOtherDataHome(@RequestBody(required = false) ZjTzOtherData zjTzOtherData) {
        return zjTzOtherDataService.getZjTzOtherDataHome(zjTzOtherData);
    }
}
