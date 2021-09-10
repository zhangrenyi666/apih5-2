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
import com.apih5.mybatis.pojo.ZjTzPartManage;
import com.apih5.service.ZjTzPartManageService;

@RestController
public class ZjTzPartManageController {

    @Autowired(required = true)
    private ZjTzPartManageService zjTzPartManageService;

    @ApiOperation(value="查询环节管理", notes="查询环节管理")
    @ApiImplicitParam(name = "zjTzPartManage", value = "环节管理entity", dataType = "ZjTzPartManage")
    @RequireToken
    @PostMapping("/getZjTzPartManageList")
    public ResponseEntity getZjTzPartManageList(@RequestBody(required = false) ZjTzPartManage zjTzPartManage) {
        return zjTzPartManageService.getZjTzPartManageListByCondition(zjTzPartManage);
    }

    @ApiOperation(value="查询详情环节管理", notes="查询详情环节管理")
    @ApiImplicitParam(name = "zjTzPartManage", value = "环节管理entity", dataType = "ZjTzPartManage")
    @RequireToken
    @PostMapping("/getZjTzPartManageDetails")
    public ResponseEntity getZjTzPartManageDetails(@RequestBody(required = false) ZjTzPartManage zjTzPartManage) {
        return zjTzPartManageService.getZjTzPartManageDetails(zjTzPartManage);
    }

    @ApiOperation(value="新增环节管理", notes="新增环节管理")
    @ApiImplicitParam(name = "zjTzPartManage", value = "环节管理entity", dataType = "ZjTzPartManage")
    @RequireToken
    @PostMapping("/addZjTzPartManage")
    public ResponseEntity addZjTzPartManage(@RequestBody(required = false) ZjTzPartManage zjTzPartManage) {
        return zjTzPartManageService.saveZjTzPartManage(zjTzPartManage);
    }

    @ApiOperation(value="更新环节管理", notes="更新环节管理")
    @ApiImplicitParam(name = "zjTzPartManage", value = "环节管理entity", dataType = "ZjTzPartManage")
    @RequireToken
    @PostMapping("/updateZjTzPartManage")
    public ResponseEntity updateZjTzPartManage(@RequestBody(required = false) ZjTzPartManage zjTzPartManage) {
        return zjTzPartManageService.updateZjTzPartManage(zjTzPartManage);
    }

    @ApiOperation(value="删除环节管理", notes="删除环节管理")
    @ApiImplicitParam(name = "zjTzPartManageList", value = "环节管理List", dataType = "List<ZjTzPartManage>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZjTzPartManage")
    public ResponseEntity batchDeleteUpdateZjTzPartManage(@RequestBody(required = false) List<ZjTzPartManage> zjTzPartManageList) {
        return zjTzPartManageService.batchDeleteUpdateZjTzPartManage(zjTzPartManageList);
    }
    
    @ApiOperation(value="插入环节管理", notes="插入环节管理")
    @ApiImplicitParam(name = "zjTzPartManage", value = "环节管理entity", dataType = "ZjTzPartManage")
    @RequireToken
    @PostMapping("/insertZjTzPartManage")
    public ResponseEntity insertZjTzPartManage(@RequestBody(required = false) ZjTzPartManage zjTzPartManage) {
        return zjTzPartManageService.insertZjTzPartManage(zjTzPartManage);
    }
    
    @ApiOperation(value="批量锁定环节管理", notes="批量锁定环节管理")
    @ApiImplicitParam(name = "zjTzPartManageList", value = "环节管理List", dataType = "List<ZjTzPartManage>")
    @RequireToken
    @PostMapping("/batchLockUpdateZjTzPartManage")
    public ResponseEntity batchLockUpdateZjTzPartManage(@RequestBody(required = false) List<ZjTzPartManage> zjTzPartManageList) {
        return zjTzPartManageService.batchLockUpdateZjTzPartManage(zjTzPartManageList);
    }
    
    @ApiOperation(value="批量解锁环节管理", notes="批量解锁环节管理")
    @ApiImplicitParam(name = "zjTzPartManageList", value = "环节管理List", dataType = "List<ZjTzPartManage>")
    @RequireToken
    @PostMapping("/batchClearUpdateZjTzPartManage")
    public ResponseEntity batchClearUpdateZjTzPartManage(@RequestBody(required = false) List<ZjTzPartManage> zjTzPartManageList) {
        return zjTzPartManageService.batchClearUpdateZjTzPartManage(zjTzPartManageList);
    }

}
