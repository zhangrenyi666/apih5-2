package com.apih5.controller;

import java.util.List;

import com.apih5.mybatis.pojo.ZxSaOtherEquipSettle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import com.apih5.framework.annotation.RequireToken;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxSaOtherEquipResSettle;
import com.apih5.service.ZxSaOtherEquipResSettleService;

@RestController
public class ZxSaOtherEquipResSettleController {

    @Autowired(required = true)
    private ZxSaOtherEquipResSettleService zxSaOtherEquipResSettleService;

    @ApiOperation(value="查询其他类细目结算", notes="查询其他类细目结算")
    @ApiImplicitParam(name = "zxSaOtherEquipResSettle", value = "其他类细目结算entity", dataType = "ZxSaOtherEquipResSettle")
    @RequireToken
    @PostMapping("/getZxSaOtherEquipResSettleList")
    public ResponseEntity getZxSaOtherEquipResSettleList(@RequestBody(required = false) ZxSaOtherEquipResSettle zxSaOtherEquipResSettle) {
        return zxSaOtherEquipResSettleService.getZxSaOtherEquipResSettleListByCondition(zxSaOtherEquipResSettle);
    }

    @ApiOperation(value="查询详情其他类细目结算", notes="查询详情其他类细目结算")
    @ApiImplicitParam(name = "zxSaOtherEquipResSettle", value = "其他类细目结算entity", dataType = "ZxSaOtherEquipResSettle")
    @RequireToken
    @PostMapping("/getZxSaOtherEquipResSettleDetail")
    public ResponseEntity getZxSaOtherEquipResSettleDetail(@RequestBody(required = false) ZxSaOtherEquipResSettle zxSaOtherEquipResSettle) {
        return zxSaOtherEquipResSettleService.getZxSaOtherEquipResSettleDetail(zxSaOtherEquipResSettle);
    }

    @ApiOperation(value="新增其他类细目结算", notes="新增其他类细目结算")
    @ApiImplicitParam(name = "zxSaOtherEquipResSettle", value = "其他类细目结算entity", dataType = "ZxSaOtherEquipResSettle")
    @RequireToken
    @PostMapping("/addZxSaOtherEquipResSettle")
    public ResponseEntity addZxSaOtherEquipResSettle(@RequestBody(required = false) ZxSaOtherEquipResSettle zxSaOtherEquipResSettle) {
        return zxSaOtherEquipResSettleService.saveZxSaOtherEquipResSettle(zxSaOtherEquipResSettle);
    }

    @ApiOperation(value="更新其他类细目结算", notes="更新其他类细目结算")
    @ApiImplicitParam(name = "zxSaOtherEquipResSettle", value = "其他类细目结算entity", dataType = "ZxSaOtherEquipResSettle")
    @RequireToken
    @PostMapping("/updateZxSaOtherEquipResSettle")
    public ResponseEntity updateZxSaOtherEquipResSettle(@RequestBody(required = false) ZxSaOtherEquipResSettle zxSaOtherEquipResSettle) {
        return zxSaOtherEquipResSettleService.updateZxSaOtherEquipResSettle(zxSaOtherEquipResSettle);
    }

    @ApiOperation(value="删除其他类细目结算", notes="删除其他类细目结算")
    @ApiImplicitParam(name = "zxSaOtherEquipResSettleList", value = "其他类细目结算List", dataType = "List<ZxSaOtherEquipResSettle>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxSaOtherEquipResSettle")
    public ResponseEntity batchDeleteUpdateZxSaOtherEquipResSettle(@RequestBody(required = false) List<ZxSaOtherEquipResSettle> zxSaOtherEquipResSettleList) {
        return zxSaOtherEquipResSettleService.batchDeleteUpdateZxSaOtherEquipResSettle(zxSaOtherEquipResSettleList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
