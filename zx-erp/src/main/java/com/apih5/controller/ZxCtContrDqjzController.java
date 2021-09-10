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
import com.apih5.mybatis.pojo.ZxCtContrDqjz;
import com.apih5.service.ZxCtContrDqjzService;

@RestController
public class ZxCtContrDqjzController {

    @Autowired(required = true)
    private ZxCtContrDqjzService zxCtContrDqjzService;

    @ApiOperation(value="查询当前建造合同表", notes="查询当前建造合同表")
    @ApiImplicitParam(name = "zxCtContrDqjz", value = "当前建造合同表entity", dataType = "ZxCtContrDqjz")
    @RequireToken
    @PostMapping("/getZxCtContrDqjzList")
    public ResponseEntity getZxCtContrDqjzList(@RequestBody(required = false) ZxCtContrDqjz zxCtContrDqjz) {
        return zxCtContrDqjzService.getZxCtContrDqjzListByCondition(zxCtContrDqjz);
    }

    @ApiOperation(value="查询详情当前建造合同表", notes="查询详情当前建造合同表")
    @ApiImplicitParam(name = "zxCtContrDqjz", value = "当前建造合同表entity", dataType = "ZxCtContrDqjz")
    @RequireToken
    @PostMapping("/getZxCtContrDqjzDetail")
    public ResponseEntity getZxCtContrDqjzDetail(@RequestBody(required = false) ZxCtContrDqjz zxCtContrDqjz) {
        return zxCtContrDqjzService.getZxCtContrDqjzDetail(zxCtContrDqjz);
    }

    @ApiOperation(value="新增当前建造合同表", notes="新增当前建造合同表")
    @ApiImplicitParam(name = "zxCtContrDqjz", value = "当前建造合同表entity", dataType = "ZxCtContrDqjz")
    @RequireToken
    @PostMapping("/addZxCtContrDqjz")
    public ResponseEntity addZxCtContrDqjz(@RequestBody(required = false) ZxCtContrDqjz zxCtContrDqjz) {
        return zxCtContrDqjzService.saveZxCtContrDqjz(zxCtContrDqjz);
    }

    @ApiOperation(value="更新当前建造合同表", notes="更新当前建造合同表")
    @ApiImplicitParam(name = "zxCtContrDqjz", value = "当前建造合同表entity", dataType = "ZxCtContrDqjz")
    @RequireToken
    @PostMapping("/updateZxCtContrDqjz")
    public ResponseEntity updateZxCtContrDqjz(@RequestBody(required = false) ZxCtContrDqjz zxCtContrDqjz) {
        return zxCtContrDqjzService.updateZxCtContrDqjz(zxCtContrDqjz);
    }

    @ApiOperation(value="删除当前建造合同表", notes="删除当前建造合同表")
    @ApiImplicitParam(name = "zxCtContrDqjzList", value = "当前建造合同表List", dataType = "List<ZxCtContrDqjz>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxCtContrDqjz")
    public ResponseEntity batchDeleteUpdateZxCtContrDqjz(@RequestBody(required = false) List<ZxCtContrDqjz> zxCtContrDqjzList) {
        return zxCtContrDqjzService.batchDeleteUpdateZxCtContrDqjz(zxCtContrDqjzList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
   
    @ApiOperation(value="查询当前建造合同明细清单列表", notes="查询当前建造合同明细清单列表")
    @ApiImplicitParam(name = "zxCtContrDqjz", value = "当前建造合同entity", dataType = "ZxCtContrDqjz")
    @RequireToken
    @PostMapping("/getZxCtContrDqjzItemList")
    public ResponseEntity getZxCtContrDqjzItemList(@RequestBody(required = false) ZxCtContrDqjz zxCtContrDqjz) {
        return zxCtContrDqjzService.getZxCtContrDqjzItemList(zxCtContrDqjz);
    }
}
