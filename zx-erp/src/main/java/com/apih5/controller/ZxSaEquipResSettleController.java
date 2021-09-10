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
import com.apih5.mybatis.pojo.ZxSaEquipResSettle;
import com.apih5.service.ZxSaEquipResSettleService;

@RestController
public class ZxSaEquipResSettleController {

    @Autowired(required = true)
    private ZxSaEquipResSettleService zxSaEquipResSettleService;

    @ApiOperation(value="查询清单结算表", notes="查询清单结算表")
    @ApiImplicitParam(name = "zxSaEquipResSettle", value = "清单结算表entity", dataType = "ZxSaEquipResSettle")
    @RequireToken
    @PostMapping("/getZxSaEquipResSettleList")
    public ResponseEntity getZxSaEquipResSettleList(@RequestBody(required = false) ZxSaEquipResSettle zxSaEquipResSettle) {
        return zxSaEquipResSettleService.getZxSaEquipResSettleListByCondition(zxSaEquipResSettle);
    }

    @ApiOperation(value="查询详情清单结算表", notes="查询详情清单结算表")
    @ApiImplicitParam(name = "zxSaEquipResSettle", value = "清单结算表entity", dataType = "ZxSaEquipResSettle")
    @RequireToken
    @PostMapping("/getZxSaEquipResSettleDetail")
    public ResponseEntity getZxSaEquipResSettleDetail(@RequestBody(required = false) ZxSaEquipResSettle zxSaEquipResSettle) {
        return zxSaEquipResSettleService.getZxSaEquipResSettleDetail(zxSaEquipResSettle);
    }

    @ApiOperation(value="新增清单结算表", notes="新增清单结算表")
    @ApiImplicitParam(name = "zxSaEquipResSettle", value = "清单结算表entity", dataType = "ZxSaEquipResSettle")
    @RequireToken
    @PostMapping("/addZxSaEquipResSettle")
    public ResponseEntity addZxSaEquipResSettle(@RequestBody(required = false) ZxSaEquipResSettle zxSaEquipResSettle) {
        return zxSaEquipResSettleService.saveZxSaEquipResSettle(zxSaEquipResSettle);
    }

    @ApiOperation(value="更新清单结算表", notes="更新清单结算表")
    @ApiImplicitParam(name = "zxSaEquipResSettle", value = "清单结算表entity", dataType = "ZxSaEquipResSettle")
    @RequireToken
    @PostMapping("/updateZxSaEquipResSettle")
    public ResponseEntity updateZxSaEquipResSettle(@RequestBody(required = false) ZxSaEquipResSettle zxSaEquipResSettle) {
        return zxSaEquipResSettleService.updateZxSaEquipResSettle(zxSaEquipResSettle);
    }

    @ApiOperation(value="删除清单结算表", notes="删除清单结算表")
    @ApiImplicitParam(name = "zxSaEquipResSettleList", value = "清单结算表List", dataType = "List<ZxSaEquipResSettle>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxSaEquipResSettle")
    public ResponseEntity batchDeleteUpdateZxSaEquipResSettle(@RequestBody(required = false) List<ZxSaEquipResSettle> zxSaEquipResSettleList) {
        return zxSaEquipResSettleService.batchDeleteUpdateZxSaEquipResSettle(zxSaEquipResSettleList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
