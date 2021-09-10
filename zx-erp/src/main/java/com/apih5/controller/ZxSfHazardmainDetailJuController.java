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
import com.apih5.mybatis.pojo.ZxSfHazardmainDetailJu;
import com.apih5.service.ZxSfHazardmainDetailJuService;

@RestController
public class ZxSfHazardmainDetailJuController {

    @Autowired(required = true)
    private ZxSfHazardmainDetailJuService zxSfHazardmainDetailJuService;

    @ApiOperation(value="查询危险源台账(局)明细", notes="查询危险源台账(局)明细")
    @ApiImplicitParam(name = "zxSfHazardmainDetailJu", value = "危险源台账(局)明细entity", dataType = "ZxSfHazardmainDetailJu")
    @RequireToken
    @PostMapping("/getZxSfHazardmainDetailJuList")
    public ResponseEntity getZxSfHazardmainDetailJuList(@RequestBody(required = false) ZxSfHazardmainDetailJu zxSfHazardmainDetailJu) {
        return zxSfHazardmainDetailJuService.getZxSfHazardmainDetailJuListByCondition(zxSfHazardmainDetailJu);
    }

    @ApiOperation(value="查询详情危险源台账(局)明细", notes="查询详情危险源台账(局)明细")
    @ApiImplicitParam(name = "zxSfHazardmainDetailJu", value = "危险源台账(局)明细entity", dataType = "ZxSfHazardmainDetailJu")
    @RequireToken
    @PostMapping("/getZxSfHazardmainDetailJuDetail")
    public ResponseEntity getZxSfHazardmainDetailJuDetail(@RequestBody(required = false) ZxSfHazardmainDetailJu zxSfHazardmainDetailJu) {
        return zxSfHazardmainDetailJuService.getZxSfHazardmainDetailJuDetail(zxSfHazardmainDetailJu);
    }

    @ApiOperation(value="新增危险源台账(局)明细", notes="新增危险源台账(局)明细")
    @ApiImplicitParam(name = "zxSfHazardmainDetailJu", value = "危险源台账(局)明细entity", dataType = "ZxSfHazardmainDetailJu")
    @RequireToken
    @PostMapping("/addZxSfHazardmainDetailJu")
    public ResponseEntity addZxSfHazardmainDetailJu(@RequestBody(required = false) ZxSfHazardmainDetailJu zxSfHazardmainDetailJu) {
        return zxSfHazardmainDetailJuService.saveZxSfHazardmainDetailJu(zxSfHazardmainDetailJu);
    }

    @ApiOperation(value="更新危险源台账(局)明细", notes="更新危险源台账(局)明细")
    @ApiImplicitParam(name = "zxSfHazardmainDetailJu", value = "危险源台账(局)明细entity", dataType = "ZxSfHazardmainDetailJu")
    @RequireToken
    @PostMapping("/updateZxSfHazardmainDetailJu")
    public ResponseEntity updateZxSfHazardmainDetailJu(@RequestBody(required = false) ZxSfHazardmainDetailJu zxSfHazardmainDetailJu) {
        return zxSfHazardmainDetailJuService.updateZxSfHazardmainDetailJu(zxSfHazardmainDetailJu);
    }

    @ApiOperation(value="删除危险源台账(局)明细", notes="删除危险源台账(局)明细")
    @ApiImplicitParam(name = "zxSfHazardmainDetailJuList", value = "危险源台账(局)明细List", dataType = "List<ZxSfHazardmainDetailJu>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxSfHazardmainDetailJu")
    public ResponseEntity batchDeleteUpdateZxSfHazardmainDetailJu(@RequestBody(required = false) List<ZxSfHazardmainDetailJu> zxSfHazardmainDetailJuList) {
        return zxSfHazardmainDetailJuService.batchDeleteUpdateZxSfHazardmainDetailJu(zxSfHazardmainDetailJuList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
