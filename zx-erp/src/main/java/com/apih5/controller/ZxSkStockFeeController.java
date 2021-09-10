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
import com.apih5.mybatis.pojo.ZxSkStockFee;
import com.apih5.service.ZxSkStockFeeService;

@RestController
public class ZxSkStockFeeController {

    @Autowired(required = true)
    private ZxSkStockFeeService zxSkStockFeeService;

    @ApiOperation(value="查询物资其它费运输费", notes="查询物资其它费运输费")
    @ApiImplicitParam(name = "zxSkStockFee", value = "物资其它费运输费entity", dataType = "ZxSkStockFee")
    @RequireToken
    @PostMapping("/getZxSkStockFeeList")
    public ResponseEntity getZxSkStockFeeList(@RequestBody(required = false) ZxSkStockFee zxSkStockFee) {
        return zxSkStockFeeService.getZxSkStockFeeListByCondition(zxSkStockFee);
    }

    @ApiOperation(value="查询详情物资其它费运输费", notes="查询详情物资其它费运输费")
    @ApiImplicitParam(name = "zxSkStockFee", value = "物资其它费运输费entity", dataType = "ZxSkStockFee")
    @RequireToken
    @PostMapping("/getZxSkStockFeeDetail")
    public ResponseEntity getZxSkStockFeeDetail(@RequestBody(required = false) ZxSkStockFee zxSkStockFee) {
        return zxSkStockFeeService.getZxSkStockFeeDetail(zxSkStockFee);
    }

    @ApiOperation(value="新增物资其它费运输费", notes="新增物资其它费运输费")
    @ApiImplicitParam(name = "zxSkStockFee", value = "物资其它费运输费entity", dataType = "ZxSkStockFee")
    @RequireToken
    @PostMapping("/addZxSkStockFee")
    public ResponseEntity addZxSkStockFee(@RequestBody(required = false) ZxSkStockFee zxSkStockFee) {
        return zxSkStockFeeService.saveZxSkStockFee(zxSkStockFee);
    }

    @ApiOperation(value="更新物资其它费运输费", notes="更新物资其它费运输费")
    @ApiImplicitParam(name = "zxSkStockFee", value = "物资其它费运输费entity", dataType = "ZxSkStockFee")
    @RequireToken
    @PostMapping("/updateZxSkStockFee")
    public ResponseEntity updateZxSkStockFee(@RequestBody(required = false) ZxSkStockFee zxSkStockFee) {
        return zxSkStockFeeService.updateZxSkStockFee(zxSkStockFee);
    }

    @ApiOperation(value="删除物资其它费运输费", notes="删除物资其它费运输费")
    @ApiImplicitParam(name = "zxSkStockFeeList", value = "物资其它费运输费List", dataType = "List<ZxSkStockFee>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxSkStockFee")
    public ResponseEntity batchDeleteUpdateZxSkStockFee(@RequestBody(required = false) List<ZxSkStockFee> zxSkStockFeeList) {
        return zxSkStockFeeService.batchDeleteUpdateZxSkStockFee(zxSkStockFeeList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓


}
