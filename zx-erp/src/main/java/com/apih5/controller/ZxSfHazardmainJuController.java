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
import com.apih5.mybatis.pojo.ZxSfHazardmainJu;
import com.apih5.service.ZxSfHazardmainJuService;

@RestController
public class ZxSfHazardmainJuController {

    @Autowired(required = true)
    private ZxSfHazardmainJuService zxSfHazardmainJuService;

    @ApiOperation(value="查询危险源台账(局)", notes="查询危险源台账(局)")
    @ApiImplicitParam(name = "zxSfHazardmainJu", value = "危险源台账(局)entity", dataType = "ZxSfHazardmainJu")
    @RequireToken
    @PostMapping("/getZxSfHazardmainJuList")
    public ResponseEntity getZxSfHazardmainJuList(@RequestBody(required = false) ZxSfHazardmainJu zxSfHazardmainJu) {
        return zxSfHazardmainJuService.getZxSfHazardmainJuListByCondition(zxSfHazardmainJu);
    }

    @ApiOperation(value="查询详情危险源台账(局)", notes="查询详情危险源台账(局)")
    @ApiImplicitParam(name = "zxSfHazardmainJu", value = "危险源台账(局)entity", dataType = "ZxSfHazardmainJu")
    @RequireToken
    @PostMapping("/getZxSfHazardmainJuDetail")
    public ResponseEntity getZxSfHazardmainJuDetail(@RequestBody(required = false) ZxSfHazardmainJu zxSfHazardmainJu) {
        return zxSfHazardmainJuService.getZxSfHazardmainJuDetail(zxSfHazardmainJu);
    }

    @ApiOperation(value="新增危险源台账(局)", notes="新增危险源台账(局)")
    @ApiImplicitParam(name = "zxSfHazardmainJu", value = "危险源台账(局)entity", dataType = "ZxSfHazardmainJu")
    @RequireToken
    @PostMapping("/addZxSfHazardmainJu")
    public ResponseEntity addZxSfHazardmainJu(@RequestBody(required = false) ZxSfHazardmainJu zxSfHazardmainJu) {
        return zxSfHazardmainJuService.saveZxSfHazardmainJu(zxSfHazardmainJu);
    }

    @ApiOperation(value="更新危险源台账(局)", notes="更新危险源台账(局)")
    @ApiImplicitParam(name = "zxSfHazardmainJu", value = "危险源台账(局)entity", dataType = "ZxSfHazardmainJu")
    @RequireToken
    @PostMapping("/updateZxSfHazardmainJu")
    public ResponseEntity updateZxSfHazardmainJu(@RequestBody(required = false) ZxSfHazardmainJu zxSfHazardmainJu) {
        return zxSfHazardmainJuService.updateZxSfHazardmainJu(zxSfHazardmainJu);
    }

    @ApiOperation(value="删除危险源台账(局)", notes="删除危险源台账(局)")
    @ApiImplicitParam(name = "zxSfHazardmainJuList", value = "危险源台账(局)List", dataType = "List<ZxSfHazardmainJu>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxSfHazardmainJu")
    public ResponseEntity batchDeleteUpdateZxSfHazardmainJu(@RequestBody(required = false) List<ZxSfHazardmainJu> zxSfHazardmainJuList) {
        return zxSfHazardmainJuService.batchDeleteUpdateZxSfHazardmainJu(zxSfHazardmainJuList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
