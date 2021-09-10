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
import com.apih5.mybatis.pojo.ZjLzehLaborRealName;
import com.apih5.service.ZjLzehLaborRealNameService;

@RestController
public class ZjLzehLaborRealNameController {

    @Autowired(required = true)
    private ZjLzehLaborRealNameService zjLzehLaborRealNameService;

    @ApiOperation(value="查询劳务实名制", notes="查询劳务实名制")
    @ApiImplicitParam(name = "zjLzehLaborRealName", value = "劳务实名制entity", dataType = "ZjLzehLaborRealName")
    @RequireToken
    @PostMapping("/getZjLzehLaborRealNameList")
    public ResponseEntity getZjLzehLaborRealNameList(@RequestBody(required = false) ZjLzehLaborRealName zjLzehLaborRealName) {
        return zjLzehLaborRealNameService.getZjLzehLaborRealNameListByCondition(zjLzehLaborRealName);
    }

    @ApiOperation(value="查询详情劳务实名制", notes="查询详情劳务实名制")
    @ApiImplicitParam(name = "zjLzehLaborRealName", value = "劳务实名制entity", dataType = "ZjLzehLaborRealName")
    @RequireToken
    @PostMapping("/getZjLzehLaborRealNameDetails")
    public ResponseEntity getZjLzehLaborRealNameDetails(@RequestBody(required = false) ZjLzehLaborRealName zjLzehLaborRealName) {
        return zjLzehLaborRealNameService.getZjLzehLaborRealNameDetails(zjLzehLaborRealName);
    }

    @ApiOperation(value="新增劳务实名制", notes="新增劳务实名制")
    @ApiImplicitParam(name = "zjLzehLaborRealName", value = "劳务实名制entity", dataType = "ZjLzehLaborRealName")
    @RequireToken
    @PostMapping("/addZjLzehLaborRealName")
    public ResponseEntity addZjLzehLaborRealName(@RequestBody(required = false) ZjLzehLaborRealName zjLzehLaborRealName) {
        return zjLzehLaborRealNameService.saveZjLzehLaborRealName(zjLzehLaborRealName);
    }

    @ApiOperation(value="更新劳务实名制", notes="更新劳务实名制")
    @ApiImplicitParam(name = "zjLzehLaborRealName", value = "劳务实名制entity", dataType = "ZjLzehLaborRealName")
    @RequireToken
    @PostMapping("/updateZjLzehLaborRealName")
    public ResponseEntity updateZjLzehLaborRealName(@RequestBody(required = false) ZjLzehLaborRealName zjLzehLaborRealName) {
        return zjLzehLaborRealNameService.updateZjLzehLaborRealName(zjLzehLaborRealName);
    }

    @ApiOperation(value="删除劳务实名制", notes="删除劳务实名制")
    @ApiImplicitParam(name = "zjLzehLaborRealNameList", value = "劳务实名制List", dataType = "List<ZjLzehLaborRealName>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZjLzehLaborRealName")
    public ResponseEntity batchDeleteUpdateZjLzehLaborRealName(@RequestBody(required = false) List<ZjLzehLaborRealName> zjLzehLaborRealNameList) {
        return zjLzehLaborRealNameService.batchDeleteUpdateZjLzehLaborRealName(zjLzehLaborRealNameList);
    }

}
