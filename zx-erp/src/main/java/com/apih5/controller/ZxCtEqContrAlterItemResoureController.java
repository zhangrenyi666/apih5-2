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
import com.apih5.mybatis.pojo.ZxCtEqContrAlterItemResoure;
import com.apih5.service.ZxCtEqContrAlterItemResoureService;

@RestController
public class ZxCtEqContrAlterItemResoureController {

    @Autowired(required = true)
    private ZxCtEqContrAlterItemResoureService zxCtEqContrAlterItemResoureService;

    @ApiOperation(value="查询设备补充协议清单明细表", notes="查询设备补充协议清单明细表")
    @ApiImplicitParam(name = "zxCtEqContrAlterItemResoure", value = "设备补充协议清单明细表entity", dataType = "ZxCtEqContrAlterItemResoure")
    @RequireToken
    @PostMapping("/getZxCtEqContrAlterItemResoureList")
    public ResponseEntity getZxCtEqContrAlterItemResoureList(@RequestBody(required = false) ZxCtEqContrAlterItemResoure zxCtEqContrAlterItemResoure) {
        return zxCtEqContrAlterItemResoureService.getZxCtEqContrAlterItemResoureListByCondition(zxCtEqContrAlterItemResoure);
    }

    @ApiOperation(value="查询详情设备补充协议清单明细表", notes="查询详情设备补充协议清单明细表")
    @ApiImplicitParam(name = "zxCtEqContrAlterItemResoure", value = "设备补充协议清单明细表entity", dataType = "ZxCtEqContrAlterItemResoure")
    @RequireToken
    @PostMapping("/getZxCtEqContrAlterItemResoureDetail")
    public ResponseEntity getZxCtEqContrAlterItemResoureDetail(@RequestBody(required = false) ZxCtEqContrAlterItemResoure zxCtEqContrAlterItemResoure) {
        return zxCtEqContrAlterItemResoureService.getZxCtEqContrAlterItemResoureDetail(zxCtEqContrAlterItemResoure);
    }

    @ApiOperation(value="新增设备补充协议清单明细表", notes="新增设备补充协议清单明细表")
    @ApiImplicitParam(name = "zxCtEqContrAlterItemResoure", value = "设备补充协议清单明细表entity", dataType = "ZxCtEqContrAlterItemResoure")
    @RequireToken
    @PostMapping("/addZxCtEqContrAlterItemResoure")
    public ResponseEntity addZxCtEqContrAlterItemResoure(@RequestBody(required = false) ZxCtEqContrAlterItemResoure zxCtEqContrAlterItemResoure) {
        return zxCtEqContrAlterItemResoureService.saveZxCtEqContrAlterItemResoure(zxCtEqContrAlterItemResoure);
    }

    @ApiOperation(value="更新设备补充协议清单明细表", notes="更新设备补充协议清单明细表")
    @ApiImplicitParam(name = "zxCtEqContrAlterItemResoure", value = "设备补充协议清单明细表entity", dataType = "ZxCtEqContrAlterItemResoure")
    @RequireToken
    @PostMapping("/updateZxCtEqContrAlterItemResoure")
    public ResponseEntity updateZxCtEqContrAlterItemResoure(@RequestBody(required = false) ZxCtEqContrAlterItemResoure zxCtEqContrAlterItemResoure) {
        return zxCtEqContrAlterItemResoureService.updateZxCtEqContrAlterItemResoure(zxCtEqContrAlterItemResoure);
    }

    @ApiOperation(value="删除设备补充协议清单明细表", notes="删除设备补充协议清单明细表")
    @ApiImplicitParam(name = "zxCtEqContrAlterItemResoureList", value = "设备补充协议清单明细表List", dataType = "List<ZxCtEqContrAlterItemResoure>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxCtEqContrAlterItemResoure")
    public ResponseEntity batchDeleteUpdateZxCtEqContrAlterItemResoure(@RequestBody(required = false) List<ZxCtEqContrAlterItemResoure> zxCtEqContrAlterItemResoureList) {
        return zxCtEqContrAlterItemResoureService.batchDeleteUpdateZxCtEqContrAlterItemResoure(zxCtEqContrAlterItemResoureList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
