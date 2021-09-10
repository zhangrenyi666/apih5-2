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
import com.apih5.mybatis.pojo.ZxEqEmployeeNum;
import com.apih5.service.ZxEqEmployeeNumService;

@RestController
public class ZxEqEmployeeNumController {

    @Autowired(required = true)
    private ZxEqEmployeeNumService zxEqEmployeeNumService;

    @ApiOperation(value="查询职工人数设置", notes="查询职工人数设置")
    @ApiImplicitParam(name = "zxEqEmployeeNum", value = "职工人数设置entity", dataType = "ZxEqEmployeeNum")
    @RequireToken
    @PostMapping("/getZxEqEmployeeNumList")
    public ResponseEntity getZxEqEmployeeNumList(@RequestBody(required = false) ZxEqEmployeeNum zxEqEmployeeNum) {
        return zxEqEmployeeNumService.getZxEqEmployeeNumListByCondition(zxEqEmployeeNum);
    }

    @ApiOperation(value="查询详情职工人数设置", notes="查询详情职工人数设置")
    @ApiImplicitParam(name = "zxEqEmployeeNum", value = "职工人数设置entity", dataType = "ZxEqEmployeeNum")
    @RequireToken
    @PostMapping("/getZxEqEmployeeNumDetails")
    public ResponseEntity getZxEqEmployeeNumDetails(@RequestBody(required = false) ZxEqEmployeeNum zxEqEmployeeNum) {
        return zxEqEmployeeNumService.getZxEqEmployeeNumDetails(zxEqEmployeeNum);
    }

    @ApiOperation(value="新增职工人数设置", notes="新增职工人数设置")
    @ApiImplicitParam(name = "zxEqEmployeeNum", value = "职工人数设置entity", dataType = "ZxEqEmployeeNum")
    @RequireToken
    @PostMapping("/addZxEqEmployeeNum")
    public ResponseEntity addZxEqEmployeeNum(@RequestBody(required = false) ZxEqEmployeeNum zxEqEmployeeNum) {
        return zxEqEmployeeNumService.saveZxEqEmployeeNum(zxEqEmployeeNum);
    }

    @ApiOperation(value="更新职工人数设置", notes="更新职工人数设置")
    @ApiImplicitParam(name = "zxEqEmployeeNum", value = "职工人数设置entity", dataType = "ZxEqEmployeeNum")
    @RequireToken
    @PostMapping("/updateZxEqEmployeeNum")
    public ResponseEntity updateZxEqEmployeeNum(@RequestBody(required = false) ZxEqEmployeeNum zxEqEmployeeNum) {
        return zxEqEmployeeNumService.updateZxEqEmployeeNum(zxEqEmployeeNum);
    }

    @ApiOperation(value="删除职工人数设置", notes="删除职工人数设置")
    @ApiImplicitParam(name = "zxEqEmployeeNumList", value = "职工人数设置List", dataType = "List<ZxEqEmployeeNum>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxEqEmployeeNum")
    public ResponseEntity batchDeleteUpdateZxEqEmployeeNum(@RequestBody(required = false) List<ZxEqEmployeeNum> zxEqEmployeeNumList) {
        return zxEqEmployeeNumService.batchDeleteUpdateZxEqEmployeeNum(zxEqEmployeeNumList);
    }

}
