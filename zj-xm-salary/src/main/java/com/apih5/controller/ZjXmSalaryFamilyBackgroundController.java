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
import com.apih5.mybatis.pojo.ZjXmSalaryFamilyBackground;
import com.apih5.service.ZjXmSalaryFamilyBackgroundService;

@RestController
public class ZjXmSalaryFamilyBackgroundController {

    @Autowired(required = true)
    private ZjXmSalaryFamilyBackgroundService zjXmSalaryFamilyBackgroundService;

    @ApiOperation(value="查询家庭状况", notes="查询家庭状况")
    @ApiImplicitParam(name = "zjXmSalaryFamilyBackground", value = "家庭状况entity", dataType = "ZjXmSalaryFamilyBackground")
    @RequireToken
    @PostMapping("/getZjXmSalaryFamilyBackgroundList")
    public ResponseEntity getZjXmSalaryFamilyBackgroundList(@RequestBody(required = false) ZjXmSalaryFamilyBackground zjXmSalaryFamilyBackground) {
        return zjXmSalaryFamilyBackgroundService.getZjXmSalaryFamilyBackgroundListByCondition(zjXmSalaryFamilyBackground);
    }

    @ApiOperation(value="查询详情家庭状况", notes="查询详情家庭状况")
    @ApiImplicitParam(name = "zjXmSalaryFamilyBackground", value = "家庭状况entity", dataType = "ZjXmSalaryFamilyBackground")
    @RequireToken
    @PostMapping("/getZjXmSalaryFamilyBackgroundDetails")
    public ResponseEntity getZjXmSalaryFamilyBackgroundDetails(@RequestBody(required = false) ZjXmSalaryFamilyBackground zjXmSalaryFamilyBackground) {
        return zjXmSalaryFamilyBackgroundService.getZjXmSalaryFamilyBackgroundDetails(zjXmSalaryFamilyBackground);
    }

    @ApiOperation(value="新增家庭状况", notes="新增家庭状况")
    @ApiImplicitParam(name = "zjXmSalaryFamilyBackground", value = "家庭状况entity", dataType = "ZjXmSalaryFamilyBackground")
    @RequireToken
    @PostMapping("/addZjXmSalaryFamilyBackground")
    public ResponseEntity addZjXmSalaryFamilyBackground(@RequestBody(required = false) ZjXmSalaryFamilyBackground zjXmSalaryFamilyBackground) {
        return zjXmSalaryFamilyBackgroundService.saveZjXmSalaryFamilyBackground(zjXmSalaryFamilyBackground);
    }

    @ApiOperation(value="更新家庭状况", notes="更新家庭状况")
    @ApiImplicitParam(name = "zjXmSalaryFamilyBackground", value = "家庭状况entity", dataType = "ZjXmSalaryFamilyBackground")
    @RequireToken
    @PostMapping("/updateZjXmSalaryFamilyBackground")
    public ResponseEntity updateZjXmSalaryFamilyBackground(@RequestBody(required = false) ZjXmSalaryFamilyBackground zjXmSalaryFamilyBackground) {
        return zjXmSalaryFamilyBackgroundService.updateZjXmSalaryFamilyBackground(zjXmSalaryFamilyBackground);
    }

    @ApiOperation(value="删除家庭状况", notes="删除家庭状况")
    @ApiImplicitParam(name = "zjXmSalaryFamilyBackgroundList", value = "家庭状况List", dataType = "List<ZjXmSalaryFamilyBackground>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZjXmSalaryFamilyBackground")
    public ResponseEntity batchDeleteUpdateZjXmSalaryFamilyBackground(@RequestBody(required = false) List<ZjXmSalaryFamilyBackground> zjXmSalaryFamilyBackgroundList) {
        return zjXmSalaryFamilyBackgroundService.batchDeleteUpdateZjXmSalaryFamilyBackground(zjXmSalaryFamilyBackgroundList);
    }

}
