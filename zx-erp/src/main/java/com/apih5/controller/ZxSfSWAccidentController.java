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
import com.apih5.mybatis.pojo.ZxSfSWAccident;
import com.apih5.service.ZxSfSWAccidentService;

@RestController
public class ZxSfSWAccidentController {

    @Autowired(required = true)
    private ZxSfSWAccidentService zxSfSWAccidentService;

    @ApiOperation(value="查询船舶水上交通事故", notes="查询船舶水上交通事故")
    @ApiImplicitParam(name = "zxSfSWAccident", value = "船舶水上交通事故entity", dataType = "ZxSfSWAccident")
    @RequireToken
    @PostMapping("/getZxSfSWAccidentList")
    public ResponseEntity getZxSfSWAccidentList(@RequestBody(required = false) ZxSfSWAccident zxSfSWAccident) {
        return zxSfSWAccidentService.getZxSfSWAccidentListByCondition(zxSfSWAccident);
    }

    @ApiOperation(value="查询详情船舶水上交通事故", notes="查询详情船舶水上交通事故")
    @ApiImplicitParam(name = "zxSfSWAccident", value = "船舶水上交通事故entity", dataType = "ZxSfSWAccident")
    @RequireToken
    @PostMapping("/getZxSfSWAccidentDetail")
    public ResponseEntity getZxSfSWAccidentDetail(@RequestBody(required = false) ZxSfSWAccident zxSfSWAccident) {
        return zxSfSWAccidentService.getZxSfSWAccidentDetail(zxSfSWAccident);
    }

    @ApiOperation(value="新增船舶水上交通事故", notes="新增船舶水上交通事故")
    @ApiImplicitParam(name = "zxSfSWAccident", value = "船舶水上交通事故entity", dataType = "ZxSfSWAccident")
    @RequireToken
    @PostMapping("/addZxSfSWAccident")
    public ResponseEntity addZxSfSWAccident(@RequestBody(required = false) ZxSfSWAccident zxSfSWAccident) {
        return zxSfSWAccidentService.saveZxSfSWAccident(zxSfSWAccident);
    }

    @ApiOperation(value="更新船舶水上交通事故", notes="更新船舶水上交通事故")
    @ApiImplicitParam(name = "zxSfSWAccident", value = "船舶水上交通事故entity", dataType = "ZxSfSWAccident")
    @RequireToken
    @PostMapping("/updateZxSfSWAccident")
    public ResponseEntity updateZxSfSWAccident(@RequestBody(required = false) ZxSfSWAccident zxSfSWAccident) {
        return zxSfSWAccidentService.updateZxSfSWAccident(zxSfSWAccident);
    }

    @ApiOperation(value="删除船舶水上交通事故", notes="删除船舶水上交通事故")
    @ApiImplicitParam(name = "zxSfSWAccidentList", value = "船舶水上交通事故List", dataType = "List<ZxSfSWAccident>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxSfSWAccident")
    public ResponseEntity batchDeleteUpdateZxSfSWAccident(@RequestBody(required = false) List<ZxSfSWAccident> zxSfSWAccidentList) {
        return zxSfSWAccidentService.batchDeleteUpdateZxSfSWAccident(zxSfSWAccidentList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
