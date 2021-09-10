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
import com.apih5.mybatis.pojo.ZjTzSafetyQualityCheckBulletin;
import com.apih5.service.ZjTzSafetyQualityCheckBulletinService;

@RestController
public class ZjTzQHSESafetyQualityCheckBulletinController {

    @Autowired(required = true)
    private ZjTzSafetyQualityCheckBulletinService zjTzSafetyQualityCheckBulletinService;

    @ApiOperation(value="查询安全质量检查通报", notes="查询安全质量检查通报")
    @ApiImplicitParam(name = "zjTzSafetyQualityCheckBulletin", value = "安全质量检查通报entity", dataType = "ZjTzSafetyQualityCheckBulletin")
    @RequireToken
    @PostMapping("/getZjTzSafetyQualityCheckBulletinList")
    public ResponseEntity getZjTzSafetyQualityCheckBulletinList(@RequestBody(required = false) ZjTzSafetyQualityCheckBulletin zjTzSafetyQualityCheckBulletin) {
        return zjTzSafetyQualityCheckBulletinService.getZjTzSafetyQualityCheckBulletinListByCondition(zjTzSafetyQualityCheckBulletin);
    }

    @ApiOperation(value="查询详情安全质量检查通报", notes="查询详情安全质量检查通报")
    @ApiImplicitParam(name = "zjTzSafetyQualityCheckBulletin", value = "安全质量检查通报entity", dataType = "ZjTzSafetyQualityCheckBulletin")
    @RequireToken
    @PostMapping("/getZjTzSafetyQualityCheckBulletinDetails")
    public ResponseEntity getZjTzSafetyQualityCheckBulletinDetails(@RequestBody(required = false) ZjTzSafetyQualityCheckBulletin zjTzSafetyQualityCheckBulletin) {
        return zjTzSafetyQualityCheckBulletinService.getZjTzSafetyQualityCheckBulletinDetails(zjTzSafetyQualityCheckBulletin);
    }

    @ApiOperation(value="新增安全质量检查通报", notes="新增安全质量检查通报")
    @ApiImplicitParam(name = "zjTzSafetyQualityCheckBulletin", value = "安全质量检查通报entity", dataType = "ZjTzSafetyQualityCheckBulletin")
    @RequireToken
    @PostMapping("/addZjTzSafetyQualityCheckBulletin")
    public ResponseEntity addZjTzSafetyQualityCheckBulletin(@RequestBody(required = false) ZjTzSafetyQualityCheckBulletin zjTzSafetyQualityCheckBulletin) {
        return zjTzSafetyQualityCheckBulletinService.saveZjTzSafetyQualityCheckBulletin(zjTzSafetyQualityCheckBulletin);
    }

    @ApiOperation(value="更新安全质量检查通报", notes="更新安全质量检查通报")
    @ApiImplicitParam(name = "zjTzSafetyQualityCheckBulletin", value = "安全质量检查通报entity", dataType = "ZjTzSafetyQualityCheckBulletin")
    @RequireToken
    @PostMapping("/updateZjTzSafetyQualityCheckBulletin")
    public ResponseEntity updateZjTzSafetyQualityCheckBulletin(@RequestBody(required = false) ZjTzSafetyQualityCheckBulletin zjTzSafetyQualityCheckBulletin) {
        return zjTzSafetyQualityCheckBulletinService.updateZjTzSafetyQualityCheckBulletin(zjTzSafetyQualityCheckBulletin);
    }
    
    @ApiOperation(value="新增安全质量检查通报（增加附件）", notes="新增安全质量检查通报（增加附件）")
    @ApiImplicitParam(name = "zjTzSafetyQualityCheckBulletin", value = "安全质量检查通报entity", dataType = "ZjTzSafetyQualityCheckBulletin")
    @RequireToken
    @PostMapping("/addZjTzSafetyQualityCheckBulletinAddFile")
    public ResponseEntity addZjTzSafetyQualityCheckBulletinAddFile(@RequestBody(required = false) ZjTzSafetyQualityCheckBulletin zjTzSafetyQualityCheckBulletin) {
    	return zjTzSafetyQualityCheckBulletinService.saveZjTzSafetyQualityCheckBulletinAddFile(zjTzSafetyQualityCheckBulletin);
    }
    
    @ApiOperation(value="更新安全质量检查通报（增加附件）", notes="更新安全质量检查通报（增加附件）")
    @ApiImplicitParam(name = "zjTzSafetyQualityCheckBulletin", value = "安全质量检查通报entity", dataType = "ZjTzSafetyQualityCheckBulletin")
    @RequireToken
    @PostMapping("/updateZjTzSafetyQualityCheckBulletinAddFile")
    public ResponseEntity updateZjTzSafetyQualityCheckBulletinAddFile(@RequestBody(required = false) ZjTzSafetyQualityCheckBulletin zjTzSafetyQualityCheckBulletin) {
    	return zjTzSafetyQualityCheckBulletinService.updateZjTzSafetyQualityCheckBulletinAddFile(zjTzSafetyQualityCheckBulletin);
    }

    @ApiOperation(value="删除安全质量检查通报", notes="删除安全质量检查通报")
    @ApiImplicitParam(name = "zjTzSafetyQualityCheckBulletinList", value = "安全质量检查通报List", dataType = "List<ZjTzSafetyQualityCheckBulletin>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZjTzSafetyQualityCheckBulletin")
    public ResponseEntity batchDeleteUpdateZjTzSafetyQualityCheckBulletin(@RequestBody(required = false) List<ZjTzSafetyQualityCheckBulletin> zjTzSafetyQualityCheckBulletinList) {
        return zjTzSafetyQualityCheckBulletinService.batchDeleteUpdateZjTzSafetyQualityCheckBulletin(zjTzSafetyQualityCheckBulletinList);
    }

}
