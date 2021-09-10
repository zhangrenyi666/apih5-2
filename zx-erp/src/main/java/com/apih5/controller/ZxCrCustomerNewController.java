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
import com.apih5.mybatis.pojo.ZxCrCustomerNew;
import com.apih5.service.ZxCrCustomerNewService;

@RestController
public class ZxCrCustomerNewController {

    @Autowired(required = true)
    private ZxCrCustomerNewService zxCrCustomerNewService;

    @ApiOperation(value="查询供应商台账", notes="查询供应商台账")
    @ApiImplicitParam(name = "zxCrCustomerNew", value = "供应商台账entity", dataType = "ZxCrCustomerNew")
    @RequireToken
    @PostMapping("/getZxCrCustomerNewList")
    public ResponseEntity getZxCrCustomerNewList(@RequestBody(required = false) ZxCrCustomerNew zxCrCustomerNew) {
        return zxCrCustomerNewService.getZxCrCustomerNewListByCondition(zxCrCustomerNew);
    }

    @ApiOperation(value="查询详情供应商台账", notes="查询详情供应商台账")
    @ApiImplicitParam(name = "zxCrCustomerNew", value = "供应商台账entity", dataType = "ZxCrCustomerNew")
    @RequireToken
    @PostMapping("/getZxCrCustomerNewDetail")
    public ResponseEntity getZxCrCustomerNewDetail(@RequestBody(required = false) ZxCrCustomerNew zxCrCustomerNew) {
        return zxCrCustomerNewService.getZxCrCustomerNewDetail(zxCrCustomerNew);
    }

    @ApiOperation(value="新增供应商台账", notes="新增供应商台账")
    @ApiImplicitParam(name = "zxCrCustomerNew", value = "供应商台账entity", dataType = "ZxCrCustomerNew")
    @RequireToken
    @PostMapping("/addZxCrCustomerNew")
    public ResponseEntity addZxCrCustomerNew(@RequestBody(required = false) ZxCrCustomerNew zxCrCustomerNew) {
        return zxCrCustomerNewService.saveZxCrCustomerNew(zxCrCustomerNew);
    }

    @ApiOperation(value="更新供应商台账", notes="更新供应商台账")
    @ApiImplicitParam(name = "zxCrCustomerNew", value = "供应商台账entity", dataType = "ZxCrCustomerNew")
    @RequireToken
    @PostMapping("/updateZxCrCustomerNew")
    public ResponseEntity updateZxCrCustomerNew(@RequestBody(required = false) ZxCrCustomerNew zxCrCustomerNew) {
        return zxCrCustomerNewService.updateZxCrCustomerNew(zxCrCustomerNew);
    }

    @ApiOperation(value="删除供应商台账", notes="删除供应商台账")
    @ApiImplicitParam(name = "zxCrCustomerNewList", value = "供应商台账List", dataType = "List<ZxCrCustomerNew>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxCrCustomerNew")
    public ResponseEntity batchDeleteUpdateZxCrCustomerNew(@RequestBody(required = false) List<ZxCrCustomerNew> zxCrCustomerNewList) {
        return zxCrCustomerNewService.batchDeleteUpdateZxCrCustomerNew(zxCrCustomerNewList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
