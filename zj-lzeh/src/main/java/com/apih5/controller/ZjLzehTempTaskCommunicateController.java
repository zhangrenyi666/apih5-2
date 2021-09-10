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
import com.apih5.mybatis.pojo.ZjLzehTempTaskCommunicate;
import com.apih5.service.ZjLzehTempTaskCommunicateService;

@RestController
public class ZjLzehTempTaskCommunicateController {

    @Autowired(required = true)
    private ZjLzehTempTaskCommunicateService zjLzehTempTaskCommunicateService;

    @ApiOperation(value="查询临时任务沟通", notes="查询临时任务沟通")
    @ApiImplicitParam(name = "zjLzehTempTaskCommunicate", value = "临时任务沟通entity", dataType = "ZjLzehTempTaskCommunicate")
    @RequireToken
    @PostMapping("/getZjLzehTempTaskCommunicateList")
    public ResponseEntity getZjLzehTempTaskCommunicateList(@RequestBody(required = false) ZjLzehTempTaskCommunicate zjLzehTempTaskCommunicate) {
        return zjLzehTempTaskCommunicateService.getZjLzehTempTaskCommunicateListByCondition(zjLzehTempTaskCommunicate);
    }

    @ApiOperation(value="查询详情临时任务沟通", notes="查询详情临时任务沟通")
    @ApiImplicitParam(name = "zjLzehTempTaskCommunicate", value = "临时任务沟通entity", dataType = "ZjLzehTempTaskCommunicate")
    @RequireToken
    @PostMapping("/getZjLzehTempTaskCommunicateDetail")
    public ResponseEntity getZjLzehTempTaskCommunicateDetail(@RequestBody(required = false) ZjLzehTempTaskCommunicate zjLzehTempTaskCommunicate) {
        return zjLzehTempTaskCommunicateService.getZjLzehTempTaskCommunicateDetail(zjLzehTempTaskCommunicate);
    }

    @ApiOperation(value="新增临时任务沟通", notes="新增临时任务沟通")
    @ApiImplicitParam(name = "zjLzehTempTaskCommunicate", value = "临时任务沟通entity", dataType = "ZjLzehTempTaskCommunicate")
    @RequireToken
    @PostMapping("/addZjLzehTempTaskCommunicate")
    public ResponseEntity addZjLzehTempTaskCommunicate(@RequestBody(required = false) ZjLzehTempTaskCommunicate zjLzehTempTaskCommunicate) {
        return zjLzehTempTaskCommunicateService.saveZjLzehTempTaskCommunicate(zjLzehTempTaskCommunicate);
    }

    @ApiOperation(value="更新临时任务沟通", notes="更新临时任务沟通")
    @ApiImplicitParam(name = "zjLzehTempTaskCommunicate", value = "临时任务沟通entity", dataType = "ZjLzehTempTaskCommunicate")
    @RequireToken
    @PostMapping("/updateZjLzehTempTaskCommunicate")
    public ResponseEntity updateZjLzehTempTaskCommunicate(@RequestBody(required = false) ZjLzehTempTaskCommunicate zjLzehTempTaskCommunicate) {
        return zjLzehTempTaskCommunicateService.updateZjLzehTempTaskCommunicate(zjLzehTempTaskCommunicate);
    }

    @ApiOperation(value="删除临时任务沟通", notes="删除临时任务沟通")
    @ApiImplicitParam(name = "zjLzehTempTaskCommunicateList", value = "临时任务沟通List", dataType = "List<ZjLzehTempTaskCommunicate>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZjLzehTempTaskCommunicate")
    public ResponseEntity batchDeleteUpdateZjLzehTempTaskCommunicate(@RequestBody(required = false) List<ZjLzehTempTaskCommunicate> zjLzehTempTaskCommunicateList) {
        return zjLzehTempTaskCommunicateService.batchDeleteUpdateZjLzehTempTaskCommunicate(zjLzehTempTaskCommunicateList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

    @ApiOperation(value="查询临时任务沟通树", notes="查询临时任务沟通树")
    @ApiImplicitParam(name = "zjLzehTempTaskCommunicate", value = "临时任务沟通entity", dataType = "ZjLzehTempTaskCommunicate")
    @RequireToken
    @PostMapping("/getZjLzehTempTaskCommunicateTree")
    public ResponseEntity getZjLzehTempTaskCommunicateTree(@RequestBody(required = false) ZjLzehTempTaskCommunicate zjLzehTempTaskCommunicate) {
        return zjLzehTempTaskCommunicateService.getZjLzehTempTaskCommunicateTreeByCondition(zjLzehTempTaskCommunicate);
    }

}
