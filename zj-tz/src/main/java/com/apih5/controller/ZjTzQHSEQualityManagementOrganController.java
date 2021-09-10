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
import com.apih5.mybatis.pojo.ZjTzQualityManagementOrgan;
import com.apih5.service.ZjTzQualityManagementOrganService;

@RestController
public class ZjTzQHSEQualityManagementOrganController {

    @Autowired(required = true)
    private ZjTzQualityManagementOrganService zjTzQualityManagementOrganService;

    @ApiOperation(value="查询质量管理机构", notes="查询质量管理机构")
    @ApiImplicitParam(name = "zjTzQualityManagementOrgan", value = "质量管理机构entity", dataType = "ZjTzQualityManagementOrgan")
    @RequireToken
    @PostMapping("/getZjTzQualityManagementOrganList")
    public ResponseEntity getZjTzQualityManagementOrganList(@RequestBody(required = false) ZjTzQualityManagementOrgan zjTzQualityManagementOrgan) {
        return zjTzQualityManagementOrganService.getZjTzQualityManagementOrganListByCondition(zjTzQualityManagementOrgan);
    }

    @ApiOperation(value="查询详情质量管理机构", notes="查询详情质量管理机构")
    @ApiImplicitParam(name = "zjTzQualityManagementOrgan", value = "质量管理机构entity", dataType = "ZjTzQualityManagementOrgan")
    @RequireToken
    @PostMapping("/getZjTzQualityManagementOrganDetails")
    public ResponseEntity getZjTzQualityManagementOrganDetails(@RequestBody(required = false) ZjTzQualityManagementOrgan zjTzQualityManagementOrgan) {
        return zjTzQualityManagementOrganService.getZjTzQualityManagementOrganDetails(zjTzQualityManagementOrgan);
    }

    @ApiOperation(value="新增质量管理机构", notes="新增质量管理机构")
    @ApiImplicitParam(name = "zjTzQualityManagementOrgan", value = "质量管理机构entity", dataType = "ZjTzQualityManagementOrgan")
    @RequireToken
    @PostMapping("/addZjTzQualityManagementOrgan")
    public ResponseEntity addZjTzQualityManagementOrgan(@RequestBody(required = false) ZjTzQualityManagementOrgan zjTzQualityManagementOrgan) {
        return zjTzQualityManagementOrganService.saveZjTzQualityManagementOrgan(zjTzQualityManagementOrgan);
    }

    @ApiOperation(value="更新质量管理机构", notes="更新质量管理机构")
    @ApiImplicitParam(name = "zjTzQualityManagementOrgan", value = "质量管理机构entity", dataType = "ZjTzQualityManagementOrgan")
    @RequireToken
    @PostMapping("/updateZjTzQualityManagementOrgan")
    public ResponseEntity updateZjTzQualityManagementOrgan(@RequestBody(required = false) ZjTzQualityManagementOrgan zjTzQualityManagementOrgan) {
        return zjTzQualityManagementOrganService.updateZjTzQualityManagementOrgan(zjTzQualityManagementOrgan);
    }
    
    @ApiOperation(value="新增质量管理机构（增加附件）", notes="新增质量管理机构（增加附件）")
    @ApiImplicitParam(name = "zjTzQualityManagementOrgan", value = "质量管理机构entity", dataType = "ZjTzQualityManagementOrgan")
    @RequireToken
    @PostMapping("/addZjTzQualityManagementOrganAddFile")
    public ResponseEntity addZjTzQualityManagementOrganAddFile(@RequestBody(required = false) ZjTzQualityManagementOrgan zjTzQualityManagementOrgan) {
    	return zjTzQualityManagementOrganService.saveZjTzQualityManagementOrganAddFile(zjTzQualityManagementOrgan);
    }
    
    @ApiOperation(value="更新质量管理机构（增加附件）", notes="更新质量管理机构（增加附件）")
    @ApiImplicitParam(name = "zjTzQualityManagementOrgan", value = "质量管理机构entity", dataType = "ZjTzQualityManagementOrgan")
    @RequireToken
    @PostMapping("/updateZjTzQualityManagementOrganAddFile")
    public ResponseEntity updateZjTzQualityManagementOrganAddFile(@RequestBody(required = false) ZjTzQualityManagementOrgan zjTzQualityManagementOrgan) {
    	return zjTzQualityManagementOrganService.updateZjTzQualityManagementOrganAddFile(zjTzQualityManagementOrgan);
    }

    @ApiOperation(value="删除质量管理机构", notes="删除质量管理机构")
    @ApiImplicitParam(name = "zjTzQualityManagementOrganList", value = "质量管理机构List", dataType = "List<ZjTzQualityManagementOrgan>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZjTzQualityManagementOrgan")
    public ResponseEntity batchDeleteUpdateZjTzQualityManagementOrgan(@RequestBody(required = false) List<ZjTzQualityManagementOrgan> zjTzQualityManagementOrganList) {
        return zjTzQualityManagementOrganService.batchDeleteUpdateZjTzQualityManagementOrgan(zjTzQualityManagementOrganList);
    }

}
