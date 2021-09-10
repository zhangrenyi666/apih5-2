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
import com.apih5.mybatis.pojo.ZjLzehProduction;
import com.apih5.service.ZjLzehProductionService;

@RestController
public class ZjLzehProductionController {

    @Autowired(required = true)
    private ZjLzehProductionService zjLzehProductionService;

    @ApiOperation(value="查询产值完成情况", notes="查询产值完成情况")
    @ApiImplicitParam(name = "zjLzehProduction", value = "产值完成情况entity", dataType = "ZjLzehProduction")
    @RequireToken
    @PostMapping("/getZjLzehProductionList")
    public ResponseEntity getZjLzehProductionList(@RequestBody(required = false) ZjLzehProduction zjLzehProduction) {
        return zjLzehProductionService.getZjLzehProductionListByCondition(zjLzehProduction);
    }

    @ApiOperation(value="查询详情产值完成情况", notes="查询详情产值完成情况")
    @ApiImplicitParam(name = "zjLzehProduction", value = "产值完成情况entity", dataType = "ZjLzehProduction")
    @RequireToken
    @PostMapping("/getZjLzehProductionDetails")
    public ResponseEntity getZjLzehProductionDetails(@RequestBody(required = false) ZjLzehProduction zjLzehProduction) {
        return zjLzehProductionService.getZjLzehProductionDetails(zjLzehProduction);
    }

    @ApiOperation(value="新增产值完成情况", notes="新增产值完成情况")
    @ApiImplicitParam(name = "zjLzehProduction", value = "产值完成情况entity", dataType = "ZjLzehProduction")
    @RequireToken
    @PostMapping("/addZjLzehProduction")
    public ResponseEntity addZjLzehProduction(@RequestBody(required = false) ZjLzehProduction zjLzehProduction) {
        return zjLzehProductionService.saveZjLzehProduction(zjLzehProduction);
    }

    @ApiOperation(value="更新产值完成情况", notes="更新产值完成情况")
    @ApiImplicitParam(name = "zjLzehProduction", value = "产值完成情况entity", dataType = "ZjLzehProduction")
    @RequireToken
    @PostMapping("/updateZjLzehProduction")
    public ResponseEntity updateZjLzehProduction(@RequestBody(required = false) ZjLzehProduction zjLzehProduction) {
        return zjLzehProductionService.updateZjLzehProduction(zjLzehProduction);
    }

    @ApiOperation(value="删除产值完成情况", notes="删除产值完成情况")
    @ApiImplicitParam(name = "zjLzehProductionList", value = "产值完成情况List", dataType = "List<ZjLzehProduction>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZjLzehProduction")
    public ResponseEntity batchDeleteUpdateZjLzehProduction(@RequestBody(required = false) List<ZjLzehProduction> zjLzehProductionList) {
        return zjLzehProductionService.batchDeleteUpdateZjLzehProduction(zjLzehProductionList);
    }

    @ApiOperation(value="通过项目id更新产值完成情况", notes="通过项目id更新产值完成情况")
    @ApiImplicitParam(name = "zjLzehProduction", value = "产值完成情况entity", dataType = "ZjLzehProduction")
//    @RequireToken
    @PostMapping("/updateZjLzehProductionByProjectId")
    public ResponseEntity updateZjLzehProductionByProjectId(@RequestBody(required = false) ZjLzehProduction zjLzehProduction) {
    	return zjLzehProductionService.updateZjLzehProductionByProjectId(zjLzehProduction);
    }
}
