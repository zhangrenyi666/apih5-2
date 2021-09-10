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
import com.apih5.mybatis.pojo.ZxSkStockTransferSalesReturn;
import com.apih5.service.ZxSkStockTransferSalesReturnService;

@RestController
public class ZxSkStockTransferSalesReturnController {

    @Autowired(required = true)
    private ZxSkStockTransferSalesReturnService zxSkStockTransferSalesReturnService;

    @ApiOperation(value="查询退货单", notes="查询退货单")
    @ApiImplicitParam(name = "zxSkStockTransferSalesReturn", value = "退货单entity", dataType = "ZxSkStockTransferSalesReturn")
    @RequireToken
    @PostMapping("/getZxSkStockTransferSalesReturnList")
    public ResponseEntity getZxSkStockTransferSalesReturnList(@RequestBody(required = false) ZxSkStockTransferSalesReturn zxSkStockTransferSalesReturn) {
        return zxSkStockTransferSalesReturnService.getZxSkStockTransferSalesReturnListByCondition(zxSkStockTransferSalesReturn);
    }

    @ApiOperation(value="查询详情退货单", notes="查询详情退货单")
    @ApiImplicitParam(name = "zxSkStockTransferSalesReturn", value = "退货单entity", dataType = "ZxSkStockTransferSalesReturn")
    @RequireToken
    @PostMapping("/getZxSkStockTransferSalesReturnDetails")
    public ResponseEntity getZxSkStockTransferSalesReturnDetails(@RequestBody(required = false) ZxSkStockTransferSalesReturn zxSkStockTransferSalesReturn) {
        return zxSkStockTransferSalesReturnService.getZxSkStockTransferSalesReturnDetails(zxSkStockTransferSalesReturn);
    }

    @ApiOperation(value="新增退货单", notes="新增退货单")
    @ApiImplicitParam(name = "zxSkStockTransferSalesReturn", value = "退货单entity", dataType = "ZxSkStockTransferSalesReturn")
    @RequireToken
    @PostMapping("/addZxSkStockTransferSalesReturn")
    public ResponseEntity addZxSkStockTransferSalesReturn(@RequestBody(required = false) ZxSkStockTransferSalesReturn zxSkStockTransferSalesReturn) {
        return zxSkStockTransferSalesReturnService.saveZxSkStockTransferSalesReturn(zxSkStockTransferSalesReturn);
    }

    @ApiOperation(value="更新退货单", notes="更新退货单")
    @ApiImplicitParam(name = "zxSkStockTransferSalesReturn", value = "退货单entity", dataType = "ZxSkStockTransferSalesReturn")
    @RequireToken
    @PostMapping("/updateZxSkStockTransferSalesReturn")
    public ResponseEntity updateZxSkStockTransferSalesReturn(@RequestBody(required = false) ZxSkStockTransferSalesReturn zxSkStockTransferSalesReturn) {
        return zxSkStockTransferSalesReturnService.updateZxSkStockTransferSalesReturn(zxSkStockTransferSalesReturn);
    }

    @ApiOperation(value="删除退货单", notes="删除退货单")
    @ApiImplicitParam(name = "zxSkStockTransferSalesReturnList", value = "退货单List", dataType = "List<ZxSkStockTransferSalesReturn>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxSkStockTransferSalesReturn")
    public ResponseEntity batchDeleteUpdateZxSkStockTransferSalesReturn(@RequestBody(required = false) List<ZxSkStockTransferSalesReturn> zxSkStockTransferSalesReturnList) {
        return zxSkStockTransferSalesReturnService.batchDeleteUpdateZxSkStockTransferSalesReturn(zxSkStockTransferSalesReturnList);
    }

    //审核
    @ApiOperation(value="审核退货单", notes="审核退货单")
    @ApiImplicitParam(name = "zxSkStockTransferSalesReturn", value = "审核退货单List", dataType = "List<ZxSkStockTransferSalesReturn>")
    @RequireToken
    @PostMapping("/checkZxSkStockTransferSalesReturn")
    public ResponseEntity checkZxSkStockTransferSalesReturn(@RequestBody(required = false) ZxSkStockTransferSalesReturn zxSkStockTransferSalesReturn) {
        return zxSkStockTransferSalesReturnService.checkZxSkStockTransferSalesReturn(zxSkStockTransferSalesReturn);
    }

    //获取退货单据编号
    @ApiOperation(value = "获取退货单据编号", notes = "获取退货单据编号")
    @ApiImplicitParam(name = "zxSkStockTransferSalesReturn", value = "退货单entity", dataType = "ZxSkStockTransferSalesReturn")
    @RequireToken
    @PostMapping("/getZxSkStockTransferSalesReturnNo")
    public ResponseEntity getZxSkStockTransferSalesReturnNo(@RequestBody(required = false) ZxSkStockTransferSalesReturn zxSkStockTransferSalesReturn) {
        return zxSkStockTransferSalesReturnService.getZxSkStockTransferSalesReturnNo(zxSkStockTransferSalesReturn);
    }

    //根据仓库id,项目id
    //查询的 预收初始单/收料单 的供货单位
    //获取退货单位(供应商/供货单位)
    @ApiOperation(value = "获取退货单位", notes = "获取退货单位")
    @ApiImplicitParam(name = "zxSkStockTransferSalesReturn", value = "退货单entity", dataType = "ZxSkStockTransferSalesReturn")
    @RequireToken
    @PostMapping("/getZxSkStockTransferSalesReturnOutOrgName")
    public ResponseEntity getZxSkStockTransferSalesReturnOutOrgName(@RequestBody(required = false)ZxSkStockTransferSalesReturn zxSkStockTransferSalesReturn) {
        return zxSkStockTransferSalesReturnService.getZxSkStockTransferSalesReturnOutOrgName(zxSkStockTransferSalesReturn);
    }


    //根据仓库id,项目id,退货单位id        outOrgID,outOrgName
    //查询的 预收初始单/收料单 的   物资大类
    //获取物资大类
    @ApiOperation(value = "获取退货中物资大类", notes = "获取退货中物资大类")
    @ApiImplicitParam(name = "zxSkStockTransferSalesReturn", value = "退货单entity", dataType = "ZxSkStockTransferSalesReturn")
    @RequireToken
    @PostMapping("/getZxSkStockTransferSalesReturnResourceName")
    public ResponseEntity getZxSkStockTransferSalesReturnResourceName(@RequestBody(required = false)ZxSkStockTransferSalesReturn zxSkStockTransferSalesReturn) {
        return zxSkStockTransferSalesReturnService.getZxSkStockTransferSalesReturnResourceName(zxSkStockTransferSalesReturn);
    }


    //项目id,仓库id,供货单位id, 物资大类id
    //              outOrgID,resourceID
    //根据预收初始单.收料单 查询物资基础数据
    @ApiOperation(value = "获取物资编码", notes = "获取物资编码")
    @ApiImplicitParam(name = "zxSkStockTransferMaterialRequisition", value = "领料单entity", dataType = "ZxSkStockTransferMaterialRequisition")
    @RequireToken
    @PostMapping("/getZxSkStockTransferSalesReturnResName")
    public ResponseEntity getZxSkStockTransferSalesReturnResName(@RequestBody(required = false)ZxSkStockTransferSalesReturn zxSkStockTransferSalesReturn) {
        return zxSkStockTransferSalesReturnService.getZxSkStockTransferSalesReturnResName(zxSkStockTransferSalesReturn);
    }

}
