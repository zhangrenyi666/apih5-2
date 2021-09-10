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
import com.apih5.mybatis.pojo.ZxCrCustomerInfoDire;
import com.apih5.service.ZxCrCustomerInfoDireService;

@RestController
public class ZxCrCustomerInfoDireController {

    @Autowired(required = true)
    private ZxCrCustomerInfoDireService zxCrCustomerInfoDireService;

    @ApiOperation(value="查询协作单位专业类别", notes="查询协作单位专业类别")
    @ApiImplicitParam(name = "zxCrCustomerInfoDire", value = "协作单位专业类别entity", dataType = "ZxCrCustomerInfoDire")
    @RequireToken
    @PostMapping("/getZxCrCustomerInfoDireList")
    public ResponseEntity getZxCrCustomerInfoDireList(@RequestBody(required = false) ZxCrCustomerInfoDire zxCrCustomerInfoDire) {
        return zxCrCustomerInfoDireService.getZxCrCustomerInfoDireListByCondition(zxCrCustomerInfoDire);
    }

    @ApiOperation(value="查询详情协作单位专业类别", notes="查询详情协作单位专业类别")
    @ApiImplicitParam(name = "zxCrCustomerInfoDire", value = "协作单位专业类别entity", dataType = "ZxCrCustomerInfoDire")
    @RequireToken
    @PostMapping("/getZxCrCustomerInfoDireDetail")
    public ResponseEntity getZxCrCustomerInfoDireDetail(@RequestBody(required = false) ZxCrCustomerInfoDire zxCrCustomerInfoDire) {
        return zxCrCustomerInfoDireService.getZxCrCustomerInfoDireDetail(zxCrCustomerInfoDire);
    }

    @ApiOperation(value="新增协作单位专业类别", notes="新增协作单位专业类别")
    @ApiImplicitParam(name = "zxCrCustomerInfoDire", value = "协作单位专业类别entity", dataType = "ZxCrCustomerInfoDire")
    @RequireToken
    @PostMapping("/addZxCrCustomerInfoDire")
    public ResponseEntity addZxCrCustomerInfoDire(@RequestBody(required = false) ZxCrCustomerInfoDire zxCrCustomerInfoDire) {
        return zxCrCustomerInfoDireService.saveZxCrCustomerInfoDire(zxCrCustomerInfoDire);
    }

    @ApiOperation(value="更新协作单位专业类别", notes="更新协作单位专业类别")
    @ApiImplicitParam(name = "zxCrCustomerInfoDire", value = "协作单位专业类别entity", dataType = "ZxCrCustomerInfoDire")
    @RequireToken
    @PostMapping("/updateZxCrCustomerInfoDire")
    public ResponseEntity updateZxCrCustomerInfoDire(@RequestBody(required = false) ZxCrCustomerInfoDire zxCrCustomerInfoDire) {
        return zxCrCustomerInfoDireService.updateZxCrCustomerInfoDire(zxCrCustomerInfoDire);
    }

    @ApiOperation(value="删除协作单位专业类别", notes="删除协作单位专业类别")
    @ApiImplicitParam(name = "zxCrCustomerInfoDireList", value = "协作单位专业类别List", dataType = "List<ZxCrCustomerInfoDire>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxCrCustomerInfoDire")
    public ResponseEntity batchDeleteUpdateZxCrCustomerInfoDire(@RequestBody(required = false) List<ZxCrCustomerInfoDire> zxCrCustomerInfoDireList) {
        return zxCrCustomerInfoDireService.batchDeleteUpdateZxCrCustomerInfoDire(zxCrCustomerInfoDireList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
