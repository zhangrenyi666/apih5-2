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
import com.apih5.mybatis.pojo.ZxSfOtherAddFilePress;
import com.apih5.service.ZxSfOtherAddFilePressService;

@RestController
public class ZxSfOtherAddFilePressController {

    @Autowired(required = true)
    private ZxSfOtherAddFilePressService zxSfOtherAddFilePressService;

    @ApiOperation(value="查询事故快报表", notes="查询事故快报表")
    @ApiImplicitParam(name = "zxSfOtherAddFilePress", value = "事故快报表entity", dataType = "ZxSfOtherAddFilePress")
    @RequireToken
    @PostMapping("/getZxSfOtherAddFilePressList")
    public ResponseEntity getZxSfOtherAddFilePressList(@RequestBody(required = false) ZxSfOtherAddFilePress zxSfOtherAddFilePress) {
        return zxSfOtherAddFilePressService.getZxSfOtherAddFilePressListByCondition(zxSfOtherAddFilePress);
    }

    @ApiOperation(value="查询详情事故快报表", notes="查询详情事故快报表")
    @ApiImplicitParam(name = "zxSfOtherAddFilePress", value = "事故快报表entity", dataType = "ZxSfOtherAddFilePress")
    @RequireToken
    @PostMapping("/getZxSfOtherAddFilePressDetail")
    public ResponseEntity getZxSfOtherAddFilePressDetail(@RequestBody(required = false) ZxSfOtherAddFilePress zxSfOtherAddFilePress) {
        return zxSfOtherAddFilePressService.getZxSfOtherAddFilePressDetail(zxSfOtherAddFilePress);
    }

    @ApiOperation(value="新增事故快报表", notes="新增事故快报表")
    @ApiImplicitParam(name = "zxSfOtherAddFilePress", value = "事故快报表entity", dataType = "ZxSfOtherAddFilePress")
    @RequireToken
    @PostMapping("/addZxSfOtherAddFilePress")
    public ResponseEntity addZxSfOtherAddFilePress(@RequestBody(required = false) ZxSfOtherAddFilePress zxSfOtherAddFilePress) {
        return zxSfOtherAddFilePressService.saveZxSfOtherAddFilePress(zxSfOtherAddFilePress);
    }

    @ApiOperation(value="更新事故快报表", notes="更新事故快报表")
    @ApiImplicitParam(name = "zxSfOtherAddFilePress", value = "事故快报表entity", dataType = "ZxSfOtherAddFilePress")
    @RequireToken
    @PostMapping("/updateZxSfOtherAddFilePress")
    public ResponseEntity updateZxSfOtherAddFilePress(@RequestBody(required = false) ZxSfOtherAddFilePress zxSfOtherAddFilePress) {
        return zxSfOtherAddFilePressService.updateZxSfOtherAddFilePress(zxSfOtherAddFilePress);
    }

    @ApiOperation(value="删除事故快报表", notes="删除事故快报表")
    @ApiImplicitParam(name = "zxSfOtherAddFilePressList", value = "事故快报表List", dataType = "List<ZxSfOtherAddFilePress>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxSfOtherAddFilePress")
    public ResponseEntity batchDeleteUpdateZxSfOtherAddFilePress(@RequestBody(required = false) List<ZxSfOtherAddFilePress> zxSfOtherAddFilePressList) {
        return zxSfOtherAddFilePressService.batchDeleteUpdateZxSfOtherAddFilePress(zxSfOtherAddFilePressList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
