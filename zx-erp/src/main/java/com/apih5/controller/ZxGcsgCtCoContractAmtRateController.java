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
import com.apih5.mybatis.pojo.ZxGcsgCtCoContractAmtRate;
import com.apih5.service.ZxGcsgCtCoContractAmtRateService;

@RestController
public class ZxGcsgCtCoContractAmtRateController {

    @Autowired(required = true)
    private ZxGcsgCtCoContractAmtRateService zxGcsgCtCoContractAmtRateService;

    @ApiOperation(value="查询分包合同保证金比例表", notes="查询分包合同保证金比例表")
    @ApiImplicitParam(name = "zxGcsgCtCoContractAmtRate", value = "分包合同保证金比例表entity", dataType = "ZxGcsgCtCoContractAmtRate")
    @RequireToken
    @PostMapping("/getZxGcsgCtCoContractAmtRateList")
    public ResponseEntity getZxGcsgCtCoContractAmtRateList(@RequestBody(required = false) ZxGcsgCtCoContractAmtRate zxGcsgCtCoContractAmtRate) {
        return zxGcsgCtCoContractAmtRateService.getZxGcsgCtCoContractAmtRateListByCondition(zxGcsgCtCoContractAmtRate);
    }

    @ApiOperation(value="查询详情分包合同保证金比例表", notes="查询详情分包合同保证金比例表")
    @ApiImplicitParam(name = "zxGcsgCtCoContractAmtRate", value = "分包合同保证金比例表entity", dataType = "ZxGcsgCtCoContractAmtRate")
    @RequireToken
    @PostMapping("/getZxGcsgCtCoContractAmtRateDetail")
    public ResponseEntity getZxGcsgCtCoContractAmtRateDetail(@RequestBody(required = false) ZxGcsgCtCoContractAmtRate zxGcsgCtCoContractAmtRate) {
        return zxGcsgCtCoContractAmtRateService.getZxGcsgCtCoContractAmtRateDetail(zxGcsgCtCoContractAmtRate);
    }

    @ApiOperation(value="新增分包合同保证金比例表", notes="新增分包合同保证金比例表")
    @ApiImplicitParam(name = "zxGcsgCtCoContractAmtRate", value = "分包合同保证金比例表entity", dataType = "ZxGcsgCtCoContractAmtRate")
    @RequireToken
    @PostMapping("/addZxGcsgCtCoContractAmtRate")
    public ResponseEntity addZxGcsgCtCoContractAmtRate(@RequestBody(required = false) ZxGcsgCtCoContractAmtRate zxGcsgCtCoContractAmtRate) {
        return zxGcsgCtCoContractAmtRateService.saveZxGcsgCtCoContractAmtRate(zxGcsgCtCoContractAmtRate);
    }

    @ApiOperation(value="更新分包合同保证金比例表", notes="更新分包合同保证金比例表")
    @ApiImplicitParam(name = "zxGcsgCtCoContractAmtRate", value = "分包合同保证金比例表entity", dataType = "ZxGcsgCtCoContractAmtRate")
    @RequireToken
    @PostMapping("/updateZxGcsgCtCoContractAmtRate")
    public ResponseEntity updateZxGcsgCtCoContractAmtRate(@RequestBody(required = false) ZxGcsgCtCoContractAmtRate zxGcsgCtCoContractAmtRate) {
        return zxGcsgCtCoContractAmtRateService.updateZxGcsgCtCoContractAmtRate(zxGcsgCtCoContractAmtRate);
    }

    @ApiOperation(value="删除分包合同保证金比例表", notes="删除分包合同保证金比例表")
    @ApiImplicitParam(name = "zxGcsgCtCoContractAmtRateList", value = "分包合同保证金比例表List", dataType = "List<ZxGcsgCtCoContractAmtRate>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxGcsgCtCoContractAmtRate")
    public ResponseEntity batchDeleteUpdateZxGcsgCtCoContractAmtRate(@RequestBody(required = false) List<ZxGcsgCtCoContractAmtRate> zxGcsgCtCoContractAmtRateList) {
        return zxGcsgCtCoContractAmtRateService.batchDeleteUpdateZxGcsgCtCoContractAmtRate(zxGcsgCtCoContractAmtRateList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
