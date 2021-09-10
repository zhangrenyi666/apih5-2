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
import com.apih5.mybatis.pojo.ZjTzRunScheme;
import com.apih5.service.ZjTzRunSchemeService;

@RestController
public class ZjTzRunSchemeController {

    @Autowired(required = true)
    private ZjTzRunSchemeService zjTzRunSchemeService;

    @ApiOperation(value="查询运营方案", notes="查询运营方案")
    @ApiImplicitParam(name = "zjTzRunScheme", value = "运营方案entity", dataType = "ZjTzRunScheme")
    @RequireToken
    @PostMapping("/getZjTzRunSchemeList")
    public ResponseEntity getZjTzRunSchemeList(@RequestBody(required = false) ZjTzRunScheme zjTzRunScheme) {
        return zjTzRunSchemeService.getZjTzRunSchemeListByCondition(zjTzRunScheme);
    }

    @ApiOperation(value="查询详情运营方案", notes="查询详情运营方案")
    @ApiImplicitParam(name = "zjTzRunScheme", value = "运营方案entity", dataType = "ZjTzRunScheme")
    @RequireToken
    @PostMapping("/getZjTzRunSchemeDetails")
    public ResponseEntity getZjTzRunSchemeDetails(@RequestBody(required = false) ZjTzRunScheme zjTzRunScheme) {
        return zjTzRunSchemeService.getZjTzRunSchemeDetails(zjTzRunScheme);
    }

    @ApiOperation(value="新增运营方案", notes="新增运营方案")
    @ApiImplicitParam(name = "zjTzRunScheme", value = "运营方案entity", dataType = "ZjTzRunScheme")
    @RequireToken
    @PostMapping("/addZjTzRunScheme")
    public ResponseEntity addZjTzRunScheme(@RequestBody(required = false) ZjTzRunScheme zjTzRunScheme) {
        return zjTzRunSchemeService.saveZjTzRunScheme(zjTzRunScheme);
    }

    @ApiOperation(value="更新运营方案", notes="更新运营方案")
    @ApiImplicitParam(name = "zjTzRunScheme", value = "运营方案entity", dataType = "ZjTzRunScheme")
    @RequireToken
    @PostMapping("/updateZjTzRunScheme")
    public ResponseEntity updateZjTzRunScheme(@RequestBody(required = false) ZjTzRunScheme zjTzRunScheme) {
        return zjTzRunSchemeService.updateZjTzRunScheme(zjTzRunScheme);
    }

    @ApiOperation(value="删除运营方案", notes="删除运营方案")
    @ApiImplicitParam(name = "zjTzRunSchemeList", value = "运营方案List", dataType = "List<ZjTzRunScheme>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZjTzRunScheme")
    public ResponseEntity batchDeleteUpdateZjTzRunScheme(@RequestBody(required = false) List<ZjTzRunScheme> zjTzRunSchemeList) {
        return zjTzRunSchemeService.batchDeleteUpdateZjTzRunScheme(zjTzRunSchemeList);
    }
    
    @ApiOperation(value="上报运营方案", notes="上报运营方案")
    @ApiImplicitParam(name = "zjTzRunScheme", value = "运营方案entity", dataType = "ZjTzRunScheme")
    @RequireToken
    @PostMapping("/updateZjTzRunSchemeForFlow")
    public ResponseEntity updateZjTzRunSchemeForFlow(@RequestBody(required = false) ZjTzRunScheme zjTzRunScheme) {
        return zjTzRunSchemeService.updateZjTzRunSchemeForFlow(zjTzRunScheme);
    }

}
