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
import com.apih5.mybatis.pojo.ZjLzehTempTaskManage;
import com.apih5.service.ZjLzehTempTaskManageService;

@RestController
public class ZjLzehTempTaskManageController {

    @Autowired(required = true)
    private ZjLzehTempTaskManageService zjLzehTempTaskManageService;

    @ApiOperation(value="查询临时任务管理", notes="查询临时任务管理")
    @ApiImplicitParam(name = "zjLzehTempTaskManage", value = "临时任务管理entity", dataType = "ZjLzehTempTaskManage")
    @RequireToken
    @PostMapping("/getZjLzehTempTaskManageList")
    public ResponseEntity getZjLzehTempTaskManageList(@RequestBody(required = false) ZjLzehTempTaskManage zjLzehTempTaskManage) {
        return zjLzehTempTaskManageService.getZjLzehTempTaskManageListByCondition(zjLzehTempTaskManage);
    }

    @ApiOperation(value="查询详情临时任务管理", notes="查询详情临时任务管理")
    @ApiImplicitParam(name = "zjLzehTempTaskManage", value = "临时任务管理entity", dataType = "ZjLzehTempTaskManage")
    @RequireToken
    @PostMapping("/getZjLzehTempTaskManageDetail")
    public ResponseEntity getZjLzehTempTaskManageDetail(@RequestBody(required = false) ZjLzehTempTaskManage zjLzehTempTaskManage) {
        return zjLzehTempTaskManageService.getZjLzehTempTaskManageDetail(zjLzehTempTaskManage);
    }

    @ApiOperation(value="新增临时任务管理", notes="新增临时任务管理")
    @ApiImplicitParam(name = "zjLzehTempTaskManage", value = "临时任务管理entity", dataType = "ZjLzehTempTaskManage")
    @RequireToken
    @PostMapping("/addZjLzehTempTaskManage")
    public ResponseEntity addZjLzehTempTaskManage(@RequestBody(required = false) ZjLzehTempTaskManage zjLzehTempTaskManage) {
        return zjLzehTempTaskManageService.saveZjLzehTempTaskManage(zjLzehTempTaskManage);
    }

    @ApiOperation(value="更新临时任务管理", notes="更新临时任务管理")
    @ApiImplicitParam(name = "zjLzehTempTaskManage", value = "临时任务管理entity", dataType = "ZjLzehTempTaskManage")
    @RequireToken
    @PostMapping("/updateZjLzehTempTaskManage")
    public ResponseEntity updateZjLzehTempTaskManage(@RequestBody(required = false) ZjLzehTempTaskManage zjLzehTempTaskManage) {
        return zjLzehTempTaskManageService.updateZjLzehTempTaskManage(zjLzehTempTaskManage);
    }

    @ApiOperation(value="删除临时任务管理", notes="删除临时任务管理")
    @ApiImplicitParam(name = "zjLzehTempTaskManageList", value = "临时任务管理List", dataType = "List<ZjLzehTempTaskManage>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZjLzehTempTaskManage")
    public ResponseEntity batchDeleteUpdateZjLzehTempTaskManage(@RequestBody(required = false) List<ZjLzehTempTaskManage> zjLzehTempTaskManageList) {
        return zjLzehTempTaskManageService.batchDeleteUpdateZjLzehTempTaskManage(zjLzehTempTaskManageList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    @ApiOperation(value="查询临时任务管理树", notes="查询临时任务管理树")
    @ApiImplicitParam(name = "zjLzehTempTaskManage", value = "临时任务管理entity", dataType = "ZjLzehTempTaskManage")
    @RequireToken
    @PostMapping("/getZjLzehTempTaskManageTree")
    public ResponseEntity getZjLzehTempTaskManageTree(@RequestBody(required = false) ZjLzehTempTaskManage zjLzehTempTaskManage) {
        return zjLzehTempTaskManageService.getZjLzehTempTaskManageTreeByCondition(zjLzehTempTaskManage);
    }

    @ApiOperation(value="查询临时任务列表(人员)", notes="查询临时任务列表(人员)")
    @ApiImplicitParam(name = "zjLzehTempTaskManage", value = "临时任务管理entity", dataType = "ZjLzehTempTaskManage")
    @RequireToken
    @PostMapping("/getZjLzehTempTaskManageListByPerson")
    public ResponseEntity getZjLzehTempTaskManageListByPerson(@RequestBody(required = false) ZjLzehTempTaskManage zjLzehTempTaskManage) {
        return zjLzehTempTaskManageService.getZjLzehTempTaskManageListByPerson(zjLzehTempTaskManage);
    }

    @ApiOperation(value="根节点分页查询树列表", notes="根节点分页查询树列表")
    @ApiImplicitParam(name = "zjLzehTempTaskManage", value = "临时任务管理entity", dataType = "ZjLzehTempTaskManage")
    @RequireToken
    @PostMapping("/getZjLzehTempTaskManageTreeList")
    public ResponseEntity getZjLzehTempTaskManageTreeList(@RequestBody(required = false) ZjLzehTempTaskManage zjLzehTempTaskManage) {
        return zjLzehTempTaskManageService.getZjLzehTempTaskManageTreeList(zjLzehTempTaskManage);
    }

    @ApiOperation(value="统计跳转查询", notes="统计跳转查询")
    @ApiImplicitParam(name = "zjLzehTempTaskManage", value = "临时任务管理entity", dataType = "ZjLzehTempTaskManage")
    @RequireToken
    @PostMapping("/selectZjLzehTempTaskManageListByPersonMonth")
    public ResponseEntity selectZjLzehTempTaskManageListByPersonMonth(@RequestBody(required = false) ZjLzehTempTaskManage zjLzehTempTaskManage) {
        return zjLzehTempTaskManageService.selectZjLzehTempTaskManageListByPersonMonth(zjLzehTempTaskManage);
    }







}
