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
import com.apih5.mybatis.pojo.ZxCtProduceAmtCal;
import com.apih5.service.ZxCtProduceAmtCalService;

@RestController
public class ZxCtProduceAmtCalController {

    @Autowired(required = true)
    private ZxCtProduceAmtCalService zxCtProduceAmtCalService;

    @ApiOperation(value="查询业主合同管理-业主合同台账-产值(原表iest_ProduceAmtCal", notes="查询业主合同管理-业主合同台账-产值(原表iest_ProduceAmtCal")
    @ApiImplicitParam(name = "zxCtProduceAmtCal", value = "业主合同管理-业主合同台账-产值(原表iest_ProduceAmtCalentity", dataType = "ZxCtProduceAmtCal")
    @RequireToken
    @PostMapping("/getZxCtProduceAmtCalList")
    public ResponseEntity getZxCtProduceAmtCalList(@RequestBody(required = false) ZxCtProduceAmtCal zxCtProduceAmtCal) {
        return zxCtProduceAmtCalService.getZxCtProduceAmtCalListByCondition(zxCtProduceAmtCal);
    }

    @ApiOperation(value="查询详情业主合同管理-业主合同台账-产值(原表iest_ProduceAmtCal", notes="查询详情业主合同管理-业主合同台账-产值(原表iest_ProduceAmtCal")
    @ApiImplicitParam(name = "zxCtProduceAmtCal", value = "业主合同管理-业主合同台账-产值(原表iest_ProduceAmtCalentity", dataType = "ZxCtProduceAmtCal")
    @RequireToken
    @PostMapping("/getZxCtProduceAmtCalDetail")
    public ResponseEntity getZxCtProduceAmtCalDetail(@RequestBody(required = false) ZxCtProduceAmtCal zxCtProduceAmtCal) {
        return zxCtProduceAmtCalService.getZxCtProduceAmtCalDetail(zxCtProduceAmtCal);
    }

    @ApiOperation(value="新增业主合同管理-业主合同台账-产值(原表iest_ProduceAmtCal", notes="新增业主合同管理-业主合同台账-产值(原表iest_ProduceAmtCal")
    @ApiImplicitParam(name = "zxCtProduceAmtCal", value = "业主合同管理-业主合同台账-产值(原表iest_ProduceAmtCalentity", dataType = "ZxCtProduceAmtCal")
    @RequireToken
    @PostMapping("/addZxCtProduceAmtCal")
    public ResponseEntity addZxCtProduceAmtCal(@RequestBody(required = false) ZxCtProduceAmtCal zxCtProduceAmtCal) {
        return zxCtProduceAmtCalService.saveZxCtProduceAmtCal(zxCtProduceAmtCal);
    }

    @ApiOperation(value="更新业主合同管理-业主合同台账-产值(原表iest_ProduceAmtCal", notes="更新业主合同管理-业主合同台账-产值(原表iest_ProduceAmtCal")
    @ApiImplicitParam(name = "zxCtProduceAmtCal", value = "业主合同管理-业主合同台账-产值(原表iest_ProduceAmtCalentity", dataType = "ZxCtProduceAmtCal")
    @RequireToken
    @PostMapping("/updateZxCtProduceAmtCal")
    public ResponseEntity updateZxCtProduceAmtCal(@RequestBody(required = false) ZxCtProduceAmtCal zxCtProduceAmtCal) {
        return zxCtProduceAmtCalService.updateZxCtProduceAmtCal(zxCtProduceAmtCal);
    }

    @ApiOperation(value="删除业主合同管理-业主合同台账-产值(原表iest_ProduceAmtCal", notes="删除业主合同管理-业主合同台账-产值(原表iest_ProduceAmtCal")
    @ApiImplicitParam(name = "zxCtProduceAmtCalList", value = "业主合同管理-业主合同台账-产值(原表iest_ProduceAmtCalList", dataType = "List<ZxCtProduceAmtCal>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxCtProduceAmtCal")
    public ResponseEntity batchDeleteUpdateZxCtProduceAmtCal(@RequestBody(required = false) List<ZxCtProduceAmtCal> zxCtProduceAmtCalList) {
        return zxCtProduceAmtCalService.batchDeleteUpdateZxCtProduceAmtCal(zxCtProduceAmtCalList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
