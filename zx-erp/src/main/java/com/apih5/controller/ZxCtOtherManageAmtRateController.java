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
import com.apih5.mybatis.pojo.ZxCtOtherManageAmtRate;
import com.apih5.service.ZxCtOtherManageAmtRateService;

@RestController
public class ZxCtOtherManageAmtRateController {

    @Autowired(required = true)
    private ZxCtOtherManageAmtRateService zxCtOtherManageAmtRateService;

    @ApiOperation(value="查询其他合同管理保证金比例设置", notes="查询其他合同管理保证金比例设置")
    @ApiImplicitParam(name = "zxCtOtherManageAmtRate", value = "其他合同管理保证金比例设置entity", dataType = "ZxCtOtherManageAmtRate")
    @RequireToken
    @PostMapping("/getZxCtOtherManageAmtRateList")
    public ResponseEntity getZxCtOtherManageAmtRateList(@RequestBody(required = false) ZxCtOtherManageAmtRate zxCtOtherManageAmtRate) {
        return zxCtOtherManageAmtRateService.getZxCtOtherManageAmtRateListByCondition(zxCtOtherManageAmtRate);
    }

    @ApiOperation(value="查询详情其他合同管理保证金比例设置", notes="查询详情其他合同管理保证金比例设置")
    @ApiImplicitParam(name = "zxCtOtherManageAmtRate", value = "其他合同管理保证金比例设置entity", dataType = "ZxCtOtherManageAmtRate")
    @RequireToken
    @PostMapping("/getZxCtOtherManageAmtRateDetail")
    public ResponseEntity getZxCtOtherManageAmtRateDetail(@RequestBody(required = false) ZxCtOtherManageAmtRate zxCtOtherManageAmtRate) {
        return zxCtOtherManageAmtRateService.getZxCtOtherManageAmtRateDetail(zxCtOtherManageAmtRate);
    }

    @ApiOperation(value="新增其他合同管理保证金比例设置", notes="新增其他合同管理保证金比例设置")
    @ApiImplicitParam(name = "zxCtOtherManageAmtRate", value = "其他合同管理保证金比例设置entity", dataType = "ZxCtOtherManageAmtRate")
    @RequireToken
    @PostMapping("/addZxCtOtherManageAmtRate")
    public ResponseEntity addZxCtOtherManageAmtRate(@RequestBody(required = false) ZxCtOtherManageAmtRate zxCtOtherManageAmtRate) {
        return zxCtOtherManageAmtRateService.saveZxCtOtherManageAmtRate(zxCtOtherManageAmtRate);
    }

    @ApiOperation(value="更新其他合同管理保证金比例设置", notes="更新其他合同管理保证金比例设置")
    @ApiImplicitParam(name = "zxCtOtherManageAmtRate", value = "其他合同管理保证金比例设置entity", dataType = "ZxCtOtherManageAmtRate")
    @RequireToken
    @PostMapping("/updateZxCtOtherManageAmtRate")
    public ResponseEntity updateZxCtOtherManageAmtRate(@RequestBody(required = false) ZxCtOtherManageAmtRate zxCtOtherManageAmtRate) {
        return zxCtOtherManageAmtRateService.updateZxCtOtherManageAmtRate(zxCtOtherManageAmtRate);
    }

    @ApiOperation(value="删除其他合同管理保证金比例设置", notes="删除其他合同管理保证金比例设置")
    @ApiImplicitParam(name = "zxCtOtherManageAmtRateList", value = "其他合同管理保证金比例设置List", dataType = "List<ZxCtOtherManageAmtRate>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxCtOtherManageAmtRate")
    public ResponseEntity batchDeleteUpdateZxCtOtherManageAmtRate(@RequestBody(required = false) List<ZxCtOtherManageAmtRate> zxCtOtherManageAmtRateList) {
        return zxCtOtherManageAmtRateService.batchDeleteUpdateZxCtOtherManageAmtRate(zxCtOtherManageAmtRateList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

    @ApiOperation(value="其他合同评审通过后初始化其他合同管理保证金比例设置", notes="其他合同评审通过后初始化其他合同管理保证金比例设置")
    @ApiImplicitParam(name = "zxCtOtherManageAmtRate", value = "其他合同管理保证金比例设置entity", dataType = "ZxCtOtherManageAmtRate")
    @RequireToken
    @PostMapping("/getZxCtOtherManageAmtRateByContractId")
    public ResponseEntity getZxCtOtherManageAmtRateByContractId(@RequestBody(required = false) ZxCtOtherManageAmtRate zxCtOtherManageAmtRate) {
        return zxCtOtherManageAmtRateService.getZxCtOtherManageAmtRateByContractId(zxCtOtherManageAmtRate);
    }
}
