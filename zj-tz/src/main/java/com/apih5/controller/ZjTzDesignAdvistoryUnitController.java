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
import com.apih5.mybatis.pojo.ZjTzDesignAdvistoryUnit;
import com.apih5.service.ZjTzDesignAdvistoryUnitService;

@RestController
public class ZjTzDesignAdvistoryUnitController {

    @Autowired(required = true)
    private ZjTzDesignAdvistoryUnitService zjTzDesignAdvistoryUnitService;

    @ApiOperation(value="查询设计、咨询单位管理", notes="查询设计、咨询单位管理")
    @ApiImplicitParam(name = "zjTzDesignAdvistoryUnit", value = "设计、咨询单位管理entity", dataType = "ZjTzDesignAdvistoryUnit")
    @RequireToken
    @PostMapping("/getZjTzDesignAdvistoryUnitList")
    public ResponseEntity getZjTzDesignAdvistoryUnitList(@RequestBody(required = false) ZjTzDesignAdvistoryUnit zjTzDesignAdvistoryUnit) {
        return zjTzDesignAdvistoryUnitService.getZjTzDesignAdvistoryUnitListByCondition(zjTzDesignAdvistoryUnit);
    }

    @ApiOperation(value="查询详情设计、咨询单位管理", notes="查询详情设计、咨询单位管理")
    @ApiImplicitParam(name = "zjTzDesignAdvistoryUnit", value = "设计、咨询单位管理entity", dataType = "ZjTzDesignAdvistoryUnit")
    @RequireToken
    @PostMapping("/getZjTzDesignAdvistoryUnitDetails")
    public ResponseEntity getZjTzDesignAdvistoryUnitDetails(@RequestBody(required = false) ZjTzDesignAdvistoryUnit zjTzDesignAdvistoryUnit) {
        return zjTzDesignAdvistoryUnitService.getZjTzDesignAdvistoryUnitDetails(zjTzDesignAdvistoryUnit);
    }

    @ApiOperation(value="新增设计、咨询单位管理", notes="新增设计、咨询单位管理")
    @ApiImplicitParam(name = "zjTzDesignAdvistoryUnit", value = "设计、咨询单位管理entity", dataType = "ZjTzDesignAdvistoryUnit")
    @RequireToken
    @PostMapping("/addZjTzDesignAdvistoryUnit")
    public ResponseEntity addZjTzDesignAdvistoryUnit(@RequestBody(required = false) ZjTzDesignAdvistoryUnit zjTzDesignAdvistoryUnit) {
        return zjTzDesignAdvistoryUnitService.saveZjTzDesignAdvistoryUnit(zjTzDesignAdvistoryUnit);
    }

    @ApiOperation(value="更新设计、咨询单位管理", notes="更新设计、咨询单位管理")
    @ApiImplicitParam(name = "zjTzDesignAdvistoryUnit", value = "设计、咨询单位管理entity", dataType = "ZjTzDesignAdvistoryUnit")
    @RequireToken
    @PostMapping("/updateZjTzDesignAdvistoryUnit")
    public ResponseEntity updateZjTzDesignAdvistoryUnit(@RequestBody(required = false) ZjTzDesignAdvistoryUnit zjTzDesignAdvistoryUnit) {
        return zjTzDesignAdvistoryUnitService.updateZjTzDesignAdvistoryUnit(zjTzDesignAdvistoryUnit);
    }

    @ApiOperation(value="删除设计、咨询单位管理", notes="删除设计、咨询单位管理")
    @ApiImplicitParam(name = "zjTzDesignAdvistoryUnitList", value = "设计、咨询单位管理List", dataType = "List<ZjTzDesignAdvistoryUnit>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZjTzDesignAdvistoryUnit")
    public ResponseEntity batchDeleteUpdateZjTzDesignAdvistoryUnit(@RequestBody(required = false) List<ZjTzDesignAdvistoryUnit> zjTzDesignAdvistoryUnitList) {
        return zjTzDesignAdvistoryUnitService.batchDeleteUpdateZjTzDesignAdvistoryUnit(zjTzDesignAdvistoryUnitList);
    }

}
