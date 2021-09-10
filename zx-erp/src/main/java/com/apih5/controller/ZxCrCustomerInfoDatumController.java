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
import com.apih5.mybatis.pojo.ZxCrCustomerInfoDatum;
import com.apih5.service.ZxCrCustomerInfoDatumService;

@RestController
public class ZxCrCustomerInfoDatumController {

    @Autowired(required = true)
    private ZxCrCustomerInfoDatumService zxCrCustomerInfoDatumService;

    @ApiOperation(value="查询协作单位分类附件", notes="查询协作单位分类附件")
    @ApiImplicitParam(name = "zxCrCustomerInfoDatum", value = "协作单位分类附件entity", dataType = "ZxCrCustomerInfoDatum")
    @RequireToken
    @PostMapping("/getZxCrCustomerInfoDatumList")
    public ResponseEntity getZxCrCustomerInfoDatumList(@RequestBody(required = false) ZxCrCustomerInfoDatum zxCrCustomerInfoDatum) {
        return zxCrCustomerInfoDatumService.getZxCrCustomerInfoDatumListByCondition(zxCrCustomerInfoDatum);
    }

    @ApiOperation(value="查询详情协作单位分类附件", notes="查询详情协作单位分类附件")
    @ApiImplicitParam(name = "zxCrCustomerInfoDatum", value = "协作单位分类附件entity", dataType = "ZxCrCustomerInfoDatum")
    @RequireToken
    @PostMapping("/getZxCrCustomerInfoDatumDetail")
    public ResponseEntity getZxCrCustomerInfoDatumDetail(@RequestBody(required = false) ZxCrCustomerInfoDatum zxCrCustomerInfoDatum) {
        return zxCrCustomerInfoDatumService.getZxCrCustomerInfoDatumDetail(zxCrCustomerInfoDatum);
    }

    @ApiOperation(value="新增协作单位分类附件", notes="新增协作单位分类附件")
    @ApiImplicitParam(name = "zxCrCustomerInfoDatum", value = "协作单位分类附件entity", dataType = "ZxCrCustomerInfoDatum")
    @RequireToken
    @PostMapping("/addZxCrCustomerInfoDatum")
    public ResponseEntity addZxCrCustomerInfoDatum(@RequestBody(required = false) ZxCrCustomerInfoDatum zxCrCustomerInfoDatum) {
        return zxCrCustomerInfoDatumService.saveZxCrCustomerInfoDatum(zxCrCustomerInfoDatum);
    }

    @ApiOperation(value="更新协作单位分类附件", notes="更新协作单位分类附件")
    @ApiImplicitParam(name = "zxCrCustomerInfoDatum", value = "协作单位分类附件entity", dataType = "ZxCrCustomerInfoDatum")
    @RequireToken
    @PostMapping("/updateZxCrCustomerInfoDatum")
    public ResponseEntity updateZxCrCustomerInfoDatum(@RequestBody(required = false) ZxCrCustomerInfoDatum zxCrCustomerInfoDatum) {
        return zxCrCustomerInfoDatumService.updateZxCrCustomerInfoDatum(zxCrCustomerInfoDatum);
    }

    @ApiOperation(value="删除协作单位分类附件", notes="删除协作单位分类附件")
    @ApiImplicitParam(name = "zxCrCustomerInfoDatumList", value = "协作单位分类附件List", dataType = "List<ZxCrCustomerInfoDatum>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxCrCustomerInfoDatum")
    public ResponseEntity batchDeleteUpdateZxCrCustomerInfoDatum(@RequestBody(required = false) List<ZxCrCustomerInfoDatum> zxCrCustomerInfoDatumList) {
        return zxCrCustomerInfoDatumService.batchDeleteUpdateZxCrCustomerInfoDatum(zxCrCustomerInfoDatumList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
