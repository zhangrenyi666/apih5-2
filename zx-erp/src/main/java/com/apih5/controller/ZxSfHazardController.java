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
import com.apih5.mybatis.pojo.ZxSfHazard;
import com.apih5.service.ZxSfHazardService;

@RestController
public class ZxSfHazardController {

    @Autowired(required = true)
    private ZxSfHazardService zxSfHazardService;

    @ApiOperation(value="查询重大危险源明细", notes="查询重大危险源明细")
    @ApiImplicitParam(name = "zxSfHazard", value = "重大危险源明细entity", dataType = "ZxSfHazard")
    @RequireToken
    @PostMapping("/getZxSfHazardList")
    public ResponseEntity getZxSfHazardList(@RequestBody(required = false) ZxSfHazard zxSfHazard) {
        return zxSfHazardService.getZxSfHazardListByCondition(zxSfHazard);
    }

    @ApiOperation(value="查询详情重大危险源明细", notes="查询详情重大危险源明细")
    @ApiImplicitParam(name = "zxSfHazard", value = "重大危险源明细entity", dataType = "ZxSfHazard")
    @RequireToken
    @PostMapping("/getZxSfHazardDetail")
    public ResponseEntity getZxSfHazardDetail(@RequestBody(required = false) ZxSfHazard zxSfHazard) {
        return zxSfHazardService.getZxSfHazardDetail(zxSfHazard);
    }

    @ApiOperation(value="新增重大危险源明细", notes="新增重大危险源明细")
    @ApiImplicitParam(name = "zxSfHazard", value = "重大危险源明细entity", dataType = "ZxSfHazard")
    @RequireToken
    @PostMapping("/addZxSfHazard")
    public ResponseEntity addZxSfHazard(@RequestBody(required = false) ZxSfHazard zxSfHazard) {
        return zxSfHazardService.saveZxSfHazard(zxSfHazard);
    }

    @ApiOperation(value="更新重大危险源明细", notes="更新重大危险源明细")
    @ApiImplicitParam(name = "zxSfHazard", value = "重大危险源明细entity", dataType = "ZxSfHazard")
    @RequireToken
    @PostMapping("/updateZxSfHazard")
    public ResponseEntity updateZxSfHazard(@RequestBody(required = false) ZxSfHazard zxSfHazard) {
        return zxSfHazardService.updateZxSfHazard(zxSfHazard);
    }

    @ApiOperation(value="删除重大危险源明细", notes="删除重大危险源明细")
    @ApiImplicitParam(name = "zxSfHazardList", value = "重大危险源明细List", dataType = "List<ZxSfHazard>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxSfHazard")
    public ResponseEntity batchDeleteUpdateZxSfHazard(@RequestBody(required = false) List<ZxSfHazard> zxSfHazardList) {
        return zxSfHazardService.batchDeleteUpdateZxSfHazard(zxSfHazardList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
