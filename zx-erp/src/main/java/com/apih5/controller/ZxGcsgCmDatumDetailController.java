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
import com.apih5.mybatis.pojo.ZxGcsgCmDatumDetail;
import com.apih5.service.ZxGcsgCmDatumDetailService;

@RestController
public class ZxGcsgCmDatumDetailController {

    @Autowired(required = true)
    private ZxGcsgCmDatumDetailService zxGcsgCmDatumDetailService;

    @ApiOperation(value="查询工程施工类合同附件表", notes="查询工程施工类合同附件表")
    @ApiImplicitParam(name = "zxGcsgCmDatumDetail", value = "工程施工类合同附件表entity", dataType = "ZxGcsgCmDatumDetail")
    @RequireToken
    @PostMapping("/getZxGcsgCmDatumDetailList")
    public ResponseEntity getZxGcsgCmDatumDetailList(@RequestBody(required = false) ZxGcsgCmDatumDetail zxGcsgCmDatumDetail) {
        return zxGcsgCmDatumDetailService.getZxGcsgCmDatumDetailListByCondition(zxGcsgCmDatumDetail);
    }

    @ApiOperation(value="查询详情工程施工类合同附件表", notes="查询详情工程施工类合同附件表")
    @ApiImplicitParam(name = "zxGcsgCmDatumDetail", value = "工程施工类合同附件表entity", dataType = "ZxGcsgCmDatumDetail")
    @RequireToken
    @PostMapping("/getZxGcsgCmDatumDetailDetail")
    public ResponseEntity getZxGcsgCmDatumDetailDetail(@RequestBody(required = false) ZxGcsgCmDatumDetail zxGcsgCmDatumDetail) {
        return zxGcsgCmDatumDetailService.getZxGcsgCmDatumDetailDetail(zxGcsgCmDatumDetail);
    }

    @ApiOperation(value="新增工程施工类合同附件表", notes="新增工程施工类合同附件表")
    @ApiImplicitParam(name = "zxGcsgCmDatumDetail", value = "工程施工类合同附件表entity", dataType = "ZxGcsgCmDatumDetail")
    @RequireToken
    @PostMapping("/addZxGcsgCmDatumDetail")
    public ResponseEntity addZxGcsgCmDatumDetail(@RequestBody(required = false) ZxGcsgCmDatumDetail zxGcsgCmDatumDetail) {
        return zxGcsgCmDatumDetailService.saveZxGcsgCmDatumDetail(zxGcsgCmDatumDetail);
    }

    @ApiOperation(value="更新工程施工类合同附件表", notes="更新工程施工类合同附件表")
    @ApiImplicitParam(name = "zxGcsgCmDatumDetail", value = "工程施工类合同附件表entity", dataType = "ZxGcsgCmDatumDetail")
    @RequireToken
    @PostMapping("/updateZxGcsgCmDatumDetail")
    public ResponseEntity updateZxGcsgCmDatumDetail(@RequestBody(required = false) ZxGcsgCmDatumDetail zxGcsgCmDatumDetail) {
        return zxGcsgCmDatumDetailService.updateZxGcsgCmDatumDetail(zxGcsgCmDatumDetail);
    }

    @ApiOperation(value="删除工程施工类合同附件表", notes="删除工程施工类合同附件表")
    @ApiImplicitParam(name = "zxGcsgCmDatumDetailList", value = "工程施工类合同附件表List", dataType = "List<ZxGcsgCmDatumDetail>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxGcsgCmDatumDetail")
    public ResponseEntity batchDeleteUpdateZxGcsgCmDatumDetail(@RequestBody(required = false) List<ZxGcsgCmDatumDetail> zxGcsgCmDatumDetailList) {
        return zxGcsgCmDatumDetailService.batchDeleteUpdateZxGcsgCmDatumDetail(zxGcsgCmDatumDetailList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
