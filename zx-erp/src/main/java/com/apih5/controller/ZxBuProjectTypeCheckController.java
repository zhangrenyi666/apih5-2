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
import com.apih5.mybatis.pojo.ZxBuProjectTypeCheck;
import com.apih5.service.ZxBuProjectTypeCheckService;

@RestController
public class ZxBuProjectTypeCheckController {

    @Autowired(required = true)
    private ZxBuProjectTypeCheckService zxBuProjectTypeCheckService;

    @ApiOperation(value="查询折算系数", notes="查询折算系数")
    @ApiImplicitParam(name = "zxBuProjectTypeCheck", value = "折算系数entity", dataType = "ZxBuProjectTypeCheck")
    @RequireToken
    @PostMapping("/getZxBuProjectTypeCheckList")
    public ResponseEntity getZxBuProjectTypeCheckList(@RequestBody(required = false) ZxBuProjectTypeCheck zxBuProjectTypeCheck) {
        return zxBuProjectTypeCheckService.getZxBuProjectTypeCheckListByCondition(zxBuProjectTypeCheck);
    }

    @ApiOperation(value="查询详情折算系数", notes="查询详情折算系数")
    @ApiImplicitParam(name = "zxBuProjectTypeCheck", value = "折算系数entity", dataType = "ZxBuProjectTypeCheck")
    @RequireToken
    @PostMapping("/getZxBuProjectTypeCheckDetail")
    public ResponseEntity getZxBuProjectTypeCheckDetail(@RequestBody(required = false) ZxBuProjectTypeCheck zxBuProjectTypeCheck) {
        return zxBuProjectTypeCheckService.getZxBuProjectTypeCheckDetail(zxBuProjectTypeCheck);
    }

    @ApiOperation(value="新增折算系数", notes="新增折算系数")
    @ApiImplicitParam(name = "zxBuProjectTypeCheck", value = "折算系数entity", dataType = "ZxBuProjectTypeCheck")
    @RequireToken
    @PostMapping("/addZxBuProjectTypeCheck")
    public ResponseEntity addZxBuProjectTypeCheck(@RequestBody(required = false) ZxBuProjectTypeCheck zxBuProjectTypeCheck) {
        return zxBuProjectTypeCheckService.saveZxBuProjectTypeCheck(zxBuProjectTypeCheck);
    }

    @ApiOperation(value="更新折算系数", notes="更新折算系数")
    @ApiImplicitParam(name = "zxBuProjectTypeCheck", value = "折算系数entity", dataType = "ZxBuProjectTypeCheck")
    @RequireToken
    @PostMapping("/updateZxBuProjectTypeCheck")
    public ResponseEntity updateZxBuProjectTypeCheck(@RequestBody(required = false) ZxBuProjectTypeCheck zxBuProjectTypeCheck) {
        return zxBuProjectTypeCheckService.updateZxBuProjectTypeCheck(zxBuProjectTypeCheck);
    }

    @ApiOperation(value="删除折算系数", notes="删除折算系数")
    @ApiImplicitParam(name = "zxBuProjectTypeCheckList", value = "折算系数List", dataType = "List<ZxBuProjectTypeCheck>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxBuProjectTypeCheck")
    public ResponseEntity batchDeleteUpdateZxBuProjectTypeCheck(@RequestBody(required = false) List<ZxBuProjectTypeCheck> zxBuProjectTypeCheckList) {
        return zxBuProjectTypeCheckService.batchDeleteUpdateZxBuProjectTypeCheck(zxBuProjectTypeCheckList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

    @ApiOperation(value="获取折算系数树", notes="获取折算系数树")
    @ApiImplicitParam(name = "zxBuProjectTypeCheckList", value = "折算系数List", dataType = "List<ZxBuProjectTypeCheck>")
    @RequireToken
    @PostMapping("/getZxBuProjectTypeCheckTree")
    public ResponseEntity getZxBuProjectTypeCheckTree(@RequestBody(required = false) ZxBuProjectTypeCheck zxBuProjectTypeCheck) {
        return zxBuProjectTypeCheckService.getZxBuProjectTypeCheckTree(zxBuProjectTypeCheck);
    }

    @ApiOperation(value="获取折算系数列表", notes="获取折算系数列表")
    @ApiImplicitParam(name = "zxBuProjectTypeCheckList", value = "折算系数List", dataType = "List<ZxBuProjectTypeCheck>")
    @RequireToken
    @PostMapping("/getZxBuProjectTypeCheckTreeList")
    public ResponseEntity getZxBuProjectTypeCheckTreeList(@RequestBody(required = false) ZxBuProjectTypeCheck zxBuProjectTypeCheck) {
        return zxBuProjectTypeCheckService.getZxBuProjectTypeCheckTreeList(zxBuProjectTypeCheck);
    }

    @ApiOperation(value="获取项目工程类别", notes="获取项目工程类别")
    @ApiImplicitParam(name = "zxBuProjectTypeCheckList", value = "折算系数List", dataType = "List<ZxBuProjectTypeCheck>")
    @RequireToken
    @PostMapping("/getZxBuProjectTypeCheckProjectType")
    public ResponseEntity getZxBuProjectTypeCheckProjectType(@RequestBody(required = false) ZxBuProjectTypeCheck zxBuProjectTypeCheck) {
        return zxBuProjectTypeCheckService.getZxBuProjectTypeCheckProjectType(zxBuProjectTypeCheck);
    }


}
