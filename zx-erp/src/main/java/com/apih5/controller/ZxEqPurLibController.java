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
import com.apih5.mybatis.pojo.ZxEqPurLib;
import com.apih5.service.ZxEqPurLibService;

@RestController
public class ZxEqPurLibController {

    @Autowired(required = true)
    private ZxEqPurLibService zxEqPurLibService;

    @ApiOperation(value="查询设备购置记录-随机技术资料名称", notes="查询设备购置记录-随机技术资料名称")
    @ApiImplicitParam(name = "zxEqPurLib", value = "设备购置记录-随机技术资料名称entity", dataType = "ZxEqPurLib")
    @RequireToken
    @PostMapping("/getZxEqPurLibList")
    public ResponseEntity getZxEqPurLibList(@RequestBody(required = false) ZxEqPurLib zxEqPurLib) {
        return zxEqPurLibService.getZxEqPurLibListByCondition(zxEqPurLib);
    }

    @ApiOperation(value="查询详情设备购置记录-随机技术资料名称", notes="查询详情设备购置记录-随机技术资料名称")
    @ApiImplicitParam(name = "zxEqPurLib", value = "设备购置记录-随机技术资料名称entity", dataType = "ZxEqPurLib")
    @RequireToken
    @PostMapping("/getZxEqPurLibDetails")
    public ResponseEntity getZxEqPurLibDetails(@RequestBody(required = false) ZxEqPurLib zxEqPurLib) {
        return zxEqPurLibService.getZxEqPurLibDetails(zxEqPurLib);
    }

    @ApiOperation(value="新增设备购置记录-随机技术资料名称", notes="新增设备购置记录-随机技术资料名称")
    @ApiImplicitParam(name = "zxEqPurLib", value = "设备购置记录-随机技术资料名称entity", dataType = "ZxEqPurLib")
    @RequireToken
    @PostMapping("/addZxEqPurLib")
    public ResponseEntity addZxEqPurLib(@RequestBody(required = false) ZxEqPurLib zxEqPurLib) {
        return zxEqPurLibService.saveZxEqPurLib(zxEqPurLib);
    }

    @ApiOperation(value="更新设备购置记录-随机技术资料名称", notes="更新设备购置记录-随机技术资料名称")
    @ApiImplicitParam(name = "zxEqPurLib", value = "设备购置记录-随机技术资料名称entity", dataType = "ZxEqPurLib")
    @RequireToken
    @PostMapping("/updateZxEqPurLib")
    public ResponseEntity updateZxEqPurLib(@RequestBody(required = false) ZxEqPurLib zxEqPurLib) {
        return zxEqPurLibService.updateZxEqPurLib(zxEqPurLib);
    }

    @ApiOperation(value="删除设备购置记录-随机技术资料名称", notes="删除设备购置记录-随机技术资料名称")
    @ApiImplicitParam(name = "zxEqPurLibList", value = "设备购置记录-随机技术资料名称List", dataType = "List<ZxEqPurLib>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxEqPurLib")
    public ResponseEntity batchDeleteUpdateZxEqPurLib(@RequestBody(required = false) List<ZxEqPurLib> zxEqPurLibList) {
        return zxEqPurLibService.batchDeleteUpdateZxEqPurLib(zxEqPurLibList);
    }

}
