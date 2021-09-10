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
import com.apih5.mybatis.pojo.ZxCtSuppliesShopResSettle;
import com.apih5.service.ZxCtSuppliesShopResSettleService;

@RestController
public class ZxCtSuppliesShopResSettleController {

    @Autowired(required = true)
    private ZxCtSuppliesShopResSettleService zxCtSuppliesShopResSettleService;

    @ApiOperation(value="查询物资采购类细目结算", notes="查询物资采购类细目结算")
    @ApiImplicitParam(name = "zxCtSuppliesShopResSettle", value = "物资采购类细目结算entity", dataType = "ZxCtSuppliesShopResSettle")
    @RequireToken
    @PostMapping("/getZxCtSuppliesShopResSettleList")
    public ResponseEntity getZxCtSuppliesShopResSettleList(@RequestBody(required = false) ZxCtSuppliesShopResSettle zxCtSuppliesShopResSettle) {
        return zxCtSuppliesShopResSettleService.getZxCtSuppliesShopResSettleListByCondition(zxCtSuppliesShopResSettle);
    }

    @ApiOperation(value="查询详情物资采购类细目结算", notes="查询详情物资采购类细目结算")
    @ApiImplicitParam(name = "zxCtSuppliesShopResSettle", value = "物资采购类细目结算entity", dataType = "ZxCtSuppliesShopResSettle")
    @RequireToken
    @PostMapping("/getZxCtSuppliesShopResSettleDetail")
    public ResponseEntity getZxCtSuppliesShopResSettleDetail(@RequestBody(required = false) ZxCtSuppliesShopResSettle zxCtSuppliesShopResSettle) {
        return zxCtSuppliesShopResSettleService.getZxCtSuppliesShopResSettleDetail(zxCtSuppliesShopResSettle);
    }

    @ApiOperation(value="新增物资采购类细目结算", notes="新增物资采购类细目结算")
    @ApiImplicitParam(name = "zxCtSuppliesShopResSettle", value = "物资采购类细目结算entity", dataType = "ZxCtSuppliesShopResSettle")
    @RequireToken
    @PostMapping("/addZxCtSuppliesShopResSettle")
    public ResponseEntity addZxCtSuppliesShopResSettle(@RequestBody(required = false) ZxCtSuppliesShopResSettle zxCtSuppliesShopResSettle) {
        return zxCtSuppliesShopResSettleService.saveZxCtSuppliesShopResSettle(zxCtSuppliesShopResSettle);
    }

    @ApiOperation(value="更新物资采购类细目结算", notes="更新物资采购类细目结算")
    @ApiImplicitParam(name = "zxCtSuppliesShopResSettle", value = "物资采购类细目结算entity", dataType = "ZxCtSuppliesShopResSettle")
    @RequireToken
    @PostMapping("/updateZxCtSuppliesShopResSettle")
    public ResponseEntity updateZxCtSuppliesShopResSettle(@RequestBody(required = false) ZxCtSuppliesShopResSettle zxCtSuppliesShopResSettle) {
        return zxCtSuppliesShopResSettleService.updateZxCtSuppliesShopResSettle(zxCtSuppliesShopResSettle);
    }

    @ApiOperation(value="删除物资采购类细目结算", notes="删除物资采购类细目结算")
    @ApiImplicitParam(name = "zxCtSuppliesShopResSettleList", value = "物资采购类细目结算List", dataType = "List<ZxCtSuppliesShopResSettle>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxCtSuppliesShopResSettle")
    public ResponseEntity batchDeleteUpdateZxCtSuppliesShopResSettle(@RequestBody(required = false) List<ZxCtSuppliesShopResSettle> zxCtSuppliesShopResSettleList) {
        return zxCtSuppliesShopResSettleService.batchDeleteUpdateZxCtSuppliesShopResSettle(zxCtSuppliesShopResSettleList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
