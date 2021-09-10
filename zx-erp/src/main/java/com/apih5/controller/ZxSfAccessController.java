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
import com.apih5.mybatis.pojo.ZxSfAccess;
import com.apih5.service.ZxSfAccessService;

@RestController
public class ZxSfAccessController {

    @Autowired(required = true)
    private ZxSfAccessService zxSfAccessService;

    @ApiOperation(value="查询协作队伍安全考核", notes="查询协作队伍安全考核")
    @ApiImplicitParam(name = "zxSfAccess", value = "协作队伍安全考核entity", dataType = "ZxSfAccess")
    @RequireToken
    @PostMapping("/getZxSfAccessList")
    public ResponseEntity getZxSfAccessList(@RequestBody(required = false) ZxSfAccess zxSfAccess) {
        return zxSfAccessService.getZxSfAccessListByCondition(zxSfAccess);
    }

    @ApiOperation(value="查询详情协作队伍安全考核", notes="查询详情协作队伍安全考核")
    @ApiImplicitParam(name = "zxSfAccess", value = "协作队伍安全考核entity", dataType = "ZxSfAccess")
    @RequireToken
    @PostMapping("/getZxSfAccessDetail")
    public ResponseEntity getZxSfAccessDetail(@RequestBody(required = false) ZxSfAccess zxSfAccess) {
        return zxSfAccessService.getZxSfAccessDetail(zxSfAccess);
    }

    @ApiOperation(value="新增协作队伍安全考核", notes="新增协作队伍安全考核")
    @ApiImplicitParam(name = "zxSfAccess", value = "协作队伍安全考核entity", dataType = "ZxSfAccess")
    @RequireToken
    @PostMapping("/addZxSfAccess")
    public ResponseEntity addZxSfAccess(@RequestBody(required = false) ZxSfAccess zxSfAccess) {
        return zxSfAccessService.saveZxSfAccess(zxSfAccess);
    }

    @ApiOperation(value="更新协作队伍安全考核", notes="更新协作队伍安全考核")
    @ApiImplicitParam(name = "zxSfAccess", value = "协作队伍安全考核entity", dataType = "ZxSfAccess")
    @RequireToken
    @PostMapping("/updateZxSfAccess")
    public ResponseEntity updateZxSfAccess(@RequestBody(required = false) ZxSfAccess zxSfAccess) {
        return zxSfAccessService.updateZxSfAccess(zxSfAccess);
    }

    @ApiOperation(value="删除协作队伍安全考核", notes="删除协作队伍安全考核")
    @ApiImplicitParam(name = "zxSfAccessList", value = "协作队伍安全考核List", dataType = "List<ZxSfAccess>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxSfAccess")
    public ResponseEntity batchDeleteUpdateZxSfAccess(@RequestBody(required = false) List<ZxSfAccess> zxSfAccessList) {
        return zxSfAccessService.batchDeleteUpdateZxSfAccess(zxSfAccessList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    
    @ApiOperation(value="更新协作队伍安全考核退场时间", notes="更新协作队伍安全考核退场时间")
    @ApiImplicitParam(name = "zxSfAccess", value = "协作队伍安全考核entity", dataType = "ZxSfAccess")
    @RequireToken
    @PostMapping("/updateZxSfAccessOutDate")
    public ResponseEntity updateZxSfAccessOutDate(@RequestBody(required = false) ZxSfAccess zxSfAccess) {
        return zxSfAccessService.updateZxSfAccessOutDate(zxSfAccess);
    }
    
    @ApiOperation(value="更新协作队伍安全考核退场时间", notes="更新协作队伍安全考核退场时间")
    @ApiImplicitParam(name = "zxSfAccess", value = "协作队伍安全考核entity", dataType = "ZxSfAccess")
    @RequireToken
    @PostMapping("/updateZxSfAccessOutDateReturn")
    public ResponseEntity updateZxSfAccessOutDateReturn(@RequestBody(required = false) ZxSfAccess zxSfAccess) {
        return zxSfAccessService.updateZxSfAccessOutDateReturn(zxSfAccess);
    }
}
