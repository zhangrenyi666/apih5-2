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
import com.apih5.mybatis.pojo.ZxCrCustomerInfoQa;
import com.apih5.service.ZxCrCustomerInfoQaService;

@RestController
public class ZxCrCustomerInfoQaController {

    @Autowired(required = true)
    private ZxCrCustomerInfoQaService zxCrCustomerInfoQaService;

    @ApiOperation(value="查询协作单位资质", notes="查询协作单位资质")
    @ApiImplicitParam(name = "zxCrCustomerInfoQa", value = "协作单位资质entity", dataType = "ZxCrCustomerInfoQa")
    @RequireToken
    @PostMapping("/getZxCrCustomerInfoQaList")
    public ResponseEntity getZxCrCustomerInfoQaList(@RequestBody(required = false) ZxCrCustomerInfoQa zxCrCustomerInfoQa) {
        return zxCrCustomerInfoQaService.getZxCrCustomerInfoQaListByCondition(zxCrCustomerInfoQa);
    }

    @ApiOperation(value="查询详情协作单位资质", notes="查询详情协作单位资质")
    @ApiImplicitParam(name = "zxCrCustomerInfoQa", value = "协作单位资质entity", dataType = "ZxCrCustomerInfoQa")
    @RequireToken
    @PostMapping("/getZxCrCustomerInfoQaDetail")
    public ResponseEntity getZxCrCustomerInfoQaDetail(@RequestBody(required = false) ZxCrCustomerInfoQa zxCrCustomerInfoQa) {
        return zxCrCustomerInfoQaService.getZxCrCustomerInfoQaDetail(zxCrCustomerInfoQa);
    }

    @ApiOperation(value="新增协作单位资质", notes="新增协作单位资质")
    @ApiImplicitParam(name = "zxCrCustomerInfoQa", value = "协作单位资质entity", dataType = "ZxCrCustomerInfoQa")
    @RequireToken
    @PostMapping("/addZxCrCustomerInfoQa")
    public ResponseEntity addZxCrCustomerInfoQa(@RequestBody(required = false) ZxCrCustomerInfoQa zxCrCustomerInfoQa) {
        return zxCrCustomerInfoQaService.saveZxCrCustomerInfoQa(zxCrCustomerInfoQa);
    }

    @ApiOperation(value="更新协作单位资质", notes="更新协作单位资质")
    @ApiImplicitParam(name = "zxCrCustomerInfoQa", value = "协作单位资质entity", dataType = "ZxCrCustomerInfoQa")
    @RequireToken
    @PostMapping("/updateZxCrCustomerInfoQa")
    public ResponseEntity updateZxCrCustomerInfoQa(@RequestBody(required = false) ZxCrCustomerInfoQa zxCrCustomerInfoQa) {
        return zxCrCustomerInfoQaService.updateZxCrCustomerInfoQa(zxCrCustomerInfoQa);
    }

    @ApiOperation(value="删除协作单位资质", notes="删除协作单位资质")
    @ApiImplicitParam(name = "zxCrCustomerInfoQaList", value = "协作单位资质List", dataType = "List<ZxCrCustomerInfoQa>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxCrCustomerInfoQa")
    public ResponseEntity batchDeleteUpdateZxCrCustomerInfoQa(@RequestBody(required = false) List<ZxCrCustomerInfoQa> zxCrCustomerInfoQaList) {
        return zxCrCustomerInfoQaService.batchDeleteUpdateZxCrCustomerInfoQa(zxCrCustomerInfoQaList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
