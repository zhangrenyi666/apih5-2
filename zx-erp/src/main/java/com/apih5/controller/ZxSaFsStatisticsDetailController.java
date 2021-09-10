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
import com.apih5.mybatis.pojo.ZxSaFsStatisticsDetail;
import com.apih5.service.ZxSaFsStatisticsDetailService;

@RestController
public class ZxSaFsStatisticsDetailController {

    @Autowired(required = true)
    private ZxSaFsStatisticsDetailService zxSaFsStatisticsDetailService;

    @ApiOperation(value="查询附属类结算统计项明细", notes="查询附属类结算统计项明细")
    @ApiImplicitParam(name = "zxSaFsStatisticsDetail", value = "附属类结算统计项明细entity", dataType = "ZxSaFsStatisticsDetail")
    @RequireToken
    @PostMapping("/getZxSaFsStatisticsDetailList")
    public ResponseEntity getZxSaFsStatisticsDetailList(@RequestBody(required = false) ZxSaFsStatisticsDetail zxSaFsStatisticsDetail) {
        return zxSaFsStatisticsDetailService.getZxSaFsStatisticsDetailListByCondition(zxSaFsStatisticsDetail);
    }

    @ApiOperation(value="查询详情附属类结算统计项明细", notes="查询详情附属类结算统计项明细")
    @ApiImplicitParam(name = "zxSaFsStatisticsDetail", value = "附属类结算统计项明细entity", dataType = "ZxSaFsStatisticsDetail")
    @RequireToken
    @PostMapping("/getZxSaFsStatisticsDetailDetail")
    public ResponseEntity getZxSaFsStatisticsDetailDetail(@RequestBody(required = false) ZxSaFsStatisticsDetail zxSaFsStatisticsDetail) {
        return zxSaFsStatisticsDetailService.getZxSaFsStatisticsDetailDetail(zxSaFsStatisticsDetail);
    }

    @ApiOperation(value="新增附属类结算统计项明细", notes="新增附属类结算统计项明细")
    @ApiImplicitParam(name = "zxSaFsStatisticsDetail", value = "附属类结算统计项明细entity", dataType = "ZxSaFsStatisticsDetail")
    @RequireToken
    @PostMapping("/addZxSaFsStatisticsDetail")
    public ResponseEntity addZxSaFsStatisticsDetail(@RequestBody(required = false) ZxSaFsStatisticsDetail zxSaFsStatisticsDetail) {
        return zxSaFsStatisticsDetailService.saveZxSaFsStatisticsDetail(zxSaFsStatisticsDetail);
    }

    @ApiOperation(value="更新附属类结算统计项明细", notes="更新附属类结算统计项明细")
    @ApiImplicitParam(name = "zxSaFsStatisticsDetail", value = "附属类结算统计项明细entity", dataType = "ZxSaFsStatisticsDetail")
    @RequireToken
    @PostMapping("/updateZxSaFsStatisticsDetail")
    public ResponseEntity updateZxSaFsStatisticsDetail(@RequestBody(required = false) ZxSaFsStatisticsDetail zxSaFsStatisticsDetail) {
        return zxSaFsStatisticsDetailService.updateZxSaFsStatisticsDetail(zxSaFsStatisticsDetail);
    }

    @ApiOperation(value="删除附属类结算统计项明细", notes="删除附属类结算统计项明细")
    @ApiImplicitParam(name = "zxSaFsStatisticsDetailList", value = "附属类结算统计项明细List", dataType = "List<ZxSaFsStatisticsDetail>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxSaFsStatisticsDetail")
    public ResponseEntity batchDeleteUpdateZxSaFsStatisticsDetail(@RequestBody(required = false) List<ZxSaFsStatisticsDetail> zxSaFsStatisticsDetailList) {
        return zxSaFsStatisticsDetailService.batchDeleteUpdateZxSaFsStatisticsDetail(zxSaFsStatisticsDetailList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
