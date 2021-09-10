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
import com.apih5.mybatis.pojo.ZjLzehFile;
import com.apih5.service.ZjLzehFileService;

@RestController
public class ZjLzehFileController {

    @Autowired(required = true)
    private ZjLzehFileService zjLzehFileService;

    @ApiOperation(value="查询泸州二环项目附件", notes="查询泸州二环项目附件")
    @ApiImplicitParam(name = "zjLzehFile", value = "泸州二环项目附件entity", dataType = "ZjLzehFile")
    @RequireToken
    @PostMapping("/getZjLzehFileList")
    public ResponseEntity getZjLzehFileList(@RequestBody(required = false) ZjLzehFile zjLzehFile) {
        return zjLzehFileService.getZjLzehFileListByCondition(zjLzehFile);
    }

    @ApiOperation(value="查询详情泸州二环项目附件", notes="查询详情泸州二环项目附件")
    @ApiImplicitParam(name = "zjLzehFile", value = "泸州二环项目附件entity", dataType = "ZjLzehFile")
    @RequireToken
    @PostMapping("/getZjLzehFileDetails")
    public ResponseEntity getZjLzehFileDetails(@RequestBody(required = false) ZjLzehFile zjLzehFile) {
        return zjLzehFileService.getZjLzehFileDetails(zjLzehFile);
    }

    @ApiOperation(value="新增泸州二环项目附件", notes="新增泸州二环项目附件")
    @ApiImplicitParam(name = "zjLzehFile", value = "泸州二环项目附件entity", dataType = "ZjLzehFile")
    @RequireToken
    @PostMapping("/addZjLzehFile")
    public ResponseEntity addZjLzehFile(@RequestBody(required = false) ZjLzehFile zjLzehFile) {
        return zjLzehFileService.saveZjLzehFile(zjLzehFile);
    }

    @ApiOperation(value="更新泸州二环项目附件", notes="更新泸州二环项目附件")
    @ApiImplicitParam(name = "zjLzehFile", value = "泸州二环项目附件entity", dataType = "ZjLzehFile")
    @RequireToken
    @PostMapping("/updateZjLzehFile")
    public ResponseEntity updateZjLzehFile(@RequestBody(required = false) ZjLzehFile zjLzehFile) {
        return zjLzehFileService.updateZjLzehFile(zjLzehFile);
    }

    @ApiOperation(value="删除泸州二环项目附件", notes="删除泸州二环项目附件")
    @ApiImplicitParam(name = "zjLzehFileList", value = "泸州二环项目附件List", dataType = "List<ZjLzehFile>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZjLzehFile")
    public ResponseEntity batchDeleteUpdateZjLzehFile(@RequestBody(required = false) List<ZjLzehFile> zjLzehFileList) {
        return zjLzehFileService.batchDeleteUpdateZjLzehFile(zjLzehFileList);
    }

}
