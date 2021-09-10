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
import com.apih5.mybatis.pojo.ZxCtContrCsjz;
import com.apih5.service.ZxCtContrCsjzService;

@RestController
public class ZxCtContrCsjzController {

    @Autowired(required = true)
    private ZxCtContrCsjzService zxCtContrCsjzService;

    @ApiOperation(value="查询初始建造合同", notes="查询初始建造合同")
    @ApiImplicitParam(name = "zxCtContrCsjz", value = "初始建造合同entity", dataType = "ZxCtContrCsjz")
    @RequireToken
    @PostMapping("/getZxCtContrCsjzList")
    public ResponseEntity getZxCtContrCsjzList(@RequestBody(required = false) ZxCtContrCsjz zxCtContrCsjz) {
        return zxCtContrCsjzService.getZxCtContrCsjzListByCondition(zxCtContrCsjz);
    }

    @ApiOperation(value="查询详情初始建造合同", notes="查询详情初始建造合同")
    @ApiImplicitParam(name = "zxCtContrCsjz", value = "初始建造合同entity", dataType = "ZxCtContrCsjz")
    @RequireToken
    @PostMapping("/getZxCtContrCsjzDetail")
    public ResponseEntity getZxCtContrCsjzDetail(@RequestBody(required = false) ZxCtContrCsjz zxCtContrCsjz) {
        return zxCtContrCsjzService.getZxCtContrCsjzDetail(zxCtContrCsjz);
    }

    @ApiOperation(value="新增初始建造合同", notes="新增初始建造合同")
    @ApiImplicitParam(name = "zxCtContrCsjz", value = "初始建造合同entity", dataType = "ZxCtContrCsjz")
    @RequireToken
    @PostMapping("/addZxCtContrCsjz")
    public ResponseEntity addZxCtContrCsjz(@RequestBody(required = false) ZxCtContrCsjz zxCtContrCsjz) {
        return zxCtContrCsjzService.saveZxCtContrCsjz(zxCtContrCsjz);
    }

    @ApiOperation(value="更新初始建造合同", notes="更新初始建造合同")
    @ApiImplicitParam(name = "zxCtContrCsjz", value = "初始建造合同entity", dataType = "ZxCtContrCsjz")
    @RequireToken
    @PostMapping("/updateZxCtContrCsjz")
    public ResponseEntity updateZxCtContrCsjz(@RequestBody(required = false) ZxCtContrCsjz zxCtContrCsjz) {
        return zxCtContrCsjzService.updateZxCtContrCsjz(zxCtContrCsjz);
    }

    @ApiOperation(value="删除初始建造合同", notes="删除初始建造合同")
    @ApiImplicitParam(name = "zxCtContrCsjzList", value = "初始建造合同List", dataType = "List<ZxCtContrCsjz>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxCtContrCsjz")
    public ResponseEntity batchDeleteUpdateZxCtContrCsjz(@RequestBody(required = false) List<ZxCtContrCsjz> zxCtContrCsjzList) {
        return zxCtContrCsjzService.batchDeleteUpdateZxCtContrCsjz(zxCtContrCsjzList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    
    @ApiOperation(value="查询初始建造合同明细清单列表", notes="查询初始建造合同明细清单列表")
    @ApiImplicitParam(name = "zxCtContrCsjz", value = "初始建造合同entity", dataType = "ZxCtContrCsjz")
    @RequireToken
    @PostMapping("/getZxCtContrCsjzItemList")
    public ResponseEntity getZxCtContrCsjzItemList(@RequestBody(required = false) ZxCtContrCsjz zxCtContrCsjz) {
        return zxCtContrCsjzService.getZxCtContrCsjzItemList(zxCtContrCsjz);
    }
    
    @ApiOperation(value="给当前建造合同获取审批完成的项目列表数据接口", notes="给当前建造合同获取审批完成的项目列表数据接口")
    @ApiImplicitParam(name = "zxCtContrCsjz", value = "初始建造合同entity", dataType = "ZxCtContrCsjz")
    @RequireToken
    @PostMapping("/getZxCtContrCsjzListApih5FlowStatus2ForDq")
    public ResponseEntity getZxCtContrCsjzListApih5FlowStatus2ForDq(@RequestBody(required = false) ZxCtContrCsjz zxCtContrCsjz) {
        return zxCtContrCsjzService.getZxCtContrCsjzListApih5FlowStatus2ForDq(zxCtContrCsjz);
    }
    
}
