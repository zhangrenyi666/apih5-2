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
import com.apih5.mybatis.pojo.ZxCrCustomerHonor;
import com.apih5.service.ZxCrCustomerHonorService;

@RestController
public class ZxCrCustomerHonorController {

    @Autowired(required = true)
    private ZxCrCustomerHonorService zxCrCustomerHonorService;

    @ApiOperation(value="查询近三年业绩和荣誉", notes="查询近三年业绩和荣誉")
    @ApiImplicitParam(name = "zxCrCustomerHonor", value = "近三年业绩和荣誉entity", dataType = "ZxCrCustomerHonor")
    @RequireToken
    @PostMapping("/getZxCrCustomerHonorList")
    public ResponseEntity getZxCrCustomerHonorList(@RequestBody(required = false) ZxCrCustomerHonor zxCrCustomerHonor) {
        return zxCrCustomerHonorService.getZxCrCustomerHonorListByCondition(zxCrCustomerHonor);
    }

    @ApiOperation(value="查询详情近三年业绩和荣誉", notes="查询详情近三年业绩和荣誉")
    @ApiImplicitParam(name = "zxCrCustomerHonor", value = "近三年业绩和荣誉entity", dataType = "ZxCrCustomerHonor")
    @RequireToken
    @PostMapping("/getZxCrCustomerHonorDetail")
    public ResponseEntity getZxCrCustomerHonorDetail(@RequestBody(required = false) ZxCrCustomerHonor zxCrCustomerHonor) {
        return zxCrCustomerHonorService.getZxCrCustomerHonorDetail(zxCrCustomerHonor);
    }

    @ApiOperation(value="新增近三年业绩和荣誉", notes="新增近三年业绩和荣誉")
    @ApiImplicitParam(name = "zxCrCustomerHonor", value = "近三年业绩和荣誉entity", dataType = "ZxCrCustomerHonor")
    @RequireToken
    @PostMapping("/addZxCrCustomerHonor")
    public ResponseEntity addZxCrCustomerHonor(@RequestBody(required = false) ZxCrCustomerHonor zxCrCustomerHonor) {
        return zxCrCustomerHonorService.saveZxCrCustomerHonor(zxCrCustomerHonor);
    }

    @ApiOperation(value="更新近三年业绩和荣誉", notes="更新近三年业绩和荣誉")
    @ApiImplicitParam(name = "zxCrCustomerHonor", value = "近三年业绩和荣誉entity", dataType = "ZxCrCustomerHonor")
    @RequireToken
    @PostMapping("/updateZxCrCustomerHonor")
    public ResponseEntity updateZxCrCustomerHonor(@RequestBody(required = false) ZxCrCustomerHonor zxCrCustomerHonor) {
        return zxCrCustomerHonorService.updateZxCrCustomerHonor(zxCrCustomerHonor);
    }

    @ApiOperation(value="删除近三年业绩和荣誉", notes="删除近三年业绩和荣誉")
    @ApiImplicitParam(name = "zxCrCustomerHonorList", value = "近三年业绩和荣誉List", dataType = "List<ZxCrCustomerHonor>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxCrCustomerHonor")
    public ResponseEntity batchDeleteUpdateZxCrCustomerHonor(@RequestBody(required = false) List<ZxCrCustomerHonor> zxCrCustomerHonorList) {
        return zxCrCustomerHonorService.batchDeleteUpdateZxCrCustomerHonor(zxCrCustomerHonorList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
