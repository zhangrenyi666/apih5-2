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
import com.apih5.mybatis.pojo.ZxSkStockTransItemSalesReturn;
import com.apih5.service.ZxSkStockTransItemSalesReturnService;

@RestController
public class ZxSkStockTransItemSalesReturnController {

    @Autowired(required = true)
    private ZxSkStockTransItemSalesReturnService zxSkStockTransItemSalesReturnService;

    @ApiOperation(value="查询退货单明细", notes="查询退货单明细")
    @ApiImplicitParam(name = "zxSkStockTransItemSalesReturn", value = "退货单明细entity", dataType = "ZxSkStockTransItemSalesReturn")
    @RequireToken
    @PostMapping("/getZxSkStockTransItemSalesReturnList")
    public ResponseEntity getZxSkStockTransItemSalesReturnList(@RequestBody(required = false) ZxSkStockTransItemSalesReturn zxSkStockTransItemSalesReturn) {
        return zxSkStockTransItemSalesReturnService.getZxSkStockTransItemSalesReturnListByCondition(zxSkStockTransItemSalesReturn);
    }

    @ApiOperation(value="查询详情退货单明细", notes="查询详情退货单明细")
    @ApiImplicitParam(name = "zxSkStockTransItemSalesReturn", value = "退货单明细entity", dataType = "ZxSkStockTransItemSalesReturn")
    @RequireToken
    @PostMapping("/getZxSkStockTransItemSalesReturnDetails")
    public ResponseEntity getZxSkStockTransItemSalesReturnDetails(@RequestBody(required = false) ZxSkStockTransItemSalesReturn zxSkStockTransItemSalesReturn) {
        return zxSkStockTransItemSalesReturnService.getZxSkStockTransItemSalesReturnDetails(zxSkStockTransItemSalesReturn);
    }

    @ApiOperation(value="新增退货单明细", notes="新增退货单明细")
    @ApiImplicitParam(name = "zxSkStockTransItemSalesReturn", value = "退货单明细entity", dataType = "ZxSkStockTransItemSalesReturn")
    @RequireToken
    @PostMapping("/addZxSkStockTransItemSalesReturn")
    public ResponseEntity addZxSkStockTransItemSalesReturn(@RequestBody(required = false) ZxSkStockTransItemSalesReturn zxSkStockTransItemSalesReturn) {
        return zxSkStockTransItemSalesReturnService.saveZxSkStockTransItemSalesReturn(zxSkStockTransItemSalesReturn);
    }

    @ApiOperation(value="更新退货单明细", notes="更新退货单明细")
    @ApiImplicitParam(name = "zxSkStockTransItemSalesReturn", value = "退货单明细entity", dataType = "ZxSkStockTransItemSalesReturn")
    @RequireToken
    @PostMapping("/updateZxSkStockTransItemSalesReturn")
    public ResponseEntity updateZxSkStockTransItemSalesReturn(@RequestBody(required = false) ZxSkStockTransItemSalesReturn zxSkStockTransItemSalesReturn) {
        return zxSkStockTransItemSalesReturnService.updateZxSkStockTransItemSalesReturn(zxSkStockTransItemSalesReturn);
    }

    @ApiOperation(value="删除退货单明细", notes="删除退货单明细")
    @ApiImplicitParam(name = "zxSkStockTransItemSalesReturnList", value = "退货单明细List", dataType = "List<ZxSkStockTransItemSalesReturn>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxSkStockTransItemSalesReturn")
    public ResponseEntity batchDeleteUpdateZxSkStockTransItemSalesReturn(@RequestBody(required = false) List<ZxSkStockTransItemSalesReturn> zxSkStockTransItemSalesReturnList) {
        return zxSkStockTransItemSalesReturnService.batchDeleteUpdateZxSkStockTransItemSalesReturn(zxSkStockTransItemSalesReturnList);
    }

}
