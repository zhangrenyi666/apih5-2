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
import com.apih5.mybatis.pojo.ZjTzSafetyManagementOrgan;
import com.apih5.service.ZjTzSafetyManagementOrganService;

@RestController
public class ZjTzQHSESafetyManagementOrganController {

    @Autowired(required = true)
    private ZjTzSafetyManagementOrganService zjTzSafetyManagementOrganService;

    @ApiOperation(value="查询安全管理机构", notes="查询安全管理机构")
    @ApiImplicitParam(name = "zjTzSafetyManagementOrgan", value = "安全管理机构entity", dataType = "ZjTzSafetyManagementOrgan")
    @RequireToken
    @PostMapping("/getZjTzSafetyManagementOrganList")
    public ResponseEntity getZjTzSafetyManagementOrganList(@RequestBody(required = false) ZjTzSafetyManagementOrgan zjTzSafetyManagementOrgan) {
        return zjTzSafetyManagementOrganService.getZjTzSafetyManagementOrganListByCondition(zjTzSafetyManagementOrgan);
    }

    @ApiOperation(value="查询详情安全管理机构", notes="查询详情安全管理机构")
    @ApiImplicitParam(name = "zjTzSafetyManagementOrgan", value = "安全管理机构entity", dataType = "ZjTzSafetyManagementOrgan")
    @RequireToken
    @PostMapping("/getZjTzSafetyManagementOrganDetails")
    public ResponseEntity getZjTzSafetyManagementOrganDetails(@RequestBody(required = false) ZjTzSafetyManagementOrgan zjTzSafetyManagementOrgan) {
        return zjTzSafetyManagementOrganService.getZjTzSafetyManagementOrganDetails(zjTzSafetyManagementOrgan);
    }

    @ApiOperation(value="新增安全管理机构", notes="新增安全管理机构")
    @ApiImplicitParam(name = "zjTzSafetyManagementOrgan", value = "安全管理机构entity", dataType = "ZjTzSafetyManagementOrgan")
    @RequireToken
    @PostMapping("/addZjTzSafetyManagementOrgan")
    public ResponseEntity addZjTzSafetyManagementOrgan(@RequestBody(required = false) ZjTzSafetyManagementOrgan zjTzSafetyManagementOrgan) {
        return zjTzSafetyManagementOrganService.saveZjTzSafetyManagementOrgan(zjTzSafetyManagementOrgan);
    }

    @ApiOperation(value="更新安全管理机构", notes="更新安全管理机构")
    @ApiImplicitParam(name = "zjTzSafetyManagementOrgan", value = "安全管理机构entity", dataType = "ZjTzSafetyManagementOrgan")
    @RequireToken
    @PostMapping("/updateZjTzSafetyManagementOrgan")
    public ResponseEntity updateZjTzSafetyManagementOrgan(@RequestBody(required = false) ZjTzSafetyManagementOrgan zjTzSafetyManagementOrgan) {
        return zjTzSafetyManagementOrganService.updateZjTzSafetyManagementOrgan(zjTzSafetyManagementOrgan);
    }
    
    @ApiOperation(value="新增安全管理机构（添加附件）", notes="新增安全管理机构（添加附件）")
    @ApiImplicitParam(name = "zjTzSafetyManagementOrgan", value = "安全管理机构entity", dataType = "ZjTzSafetyManagementOrgan")
    @RequireToken
    @PostMapping("/addZjTzSafetyManagementOrganAddFile")
    public ResponseEntity addZjTzSafetyManagementOrganAddFile(@RequestBody(required = false) ZjTzSafetyManagementOrgan zjTzSafetyManagementOrgan) {
    	return zjTzSafetyManagementOrganService.saveZjTzSafetyManagementOrganAddFile(zjTzSafetyManagementOrgan);
    }
    
    @ApiOperation(value="更新安全管理机构（添加附件）", notes="更新安全管理机构（添加附件）")
    @ApiImplicitParam(name = "zjTzSafetyManagementOrgan", value = "安全管理机构entity", dataType = "ZjTzSafetyManagementOrgan")
    @RequireToken
    @PostMapping("/updateZjTzSafetyManagementOrganAddFile")
    public ResponseEntity updateZjTzSafetyManagementOrganAddFile(@RequestBody(required = false) ZjTzSafetyManagementOrgan zjTzSafetyManagementOrgan) {
    	return zjTzSafetyManagementOrganService.updateZjTzSafetyManagementOrganAddFile(zjTzSafetyManagementOrgan);
    }

    @ApiOperation(value="删除安全管理机构", notes="删除安全管理机构")
    @ApiImplicitParam(name = "zjTzSafetyManagementOrganList", value = "安全管理机构List", dataType = "List<ZjTzSafetyManagementOrgan>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZjTzSafetyManagementOrgan")
    public ResponseEntity batchDeleteUpdateZjTzSafetyManagementOrgan(@RequestBody(required = false) List<ZjTzSafetyManagementOrgan> zjTzSafetyManagementOrganList) {
        return zjTzSafetyManagementOrganService.batchDeleteUpdateZjTzSafetyManagementOrgan(zjTzSafetyManagementOrganList);
    }

}
