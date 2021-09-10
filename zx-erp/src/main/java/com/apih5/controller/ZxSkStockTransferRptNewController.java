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
import com.apih5.mybatis.pojo.ZxSkCustomerOutRes;
import com.apih5.mybatis.pojo.ZxSkStockTransferRptNew;
import com.apih5.service.ZxSkStockTransferRptNewService;

@RestController
public class ZxSkStockTransferRptNewController {

    @Autowired(required = true)
    private ZxSkStockTransferRptNewService zxSkStockTransferRptNewService;

    @ApiOperation(value="查询中交物资收发存季度统计报表", notes="查询中交物资收发存季度统计报表")
    @ApiImplicitParam(name = "zxSkStockTransferRptNew", value = "中交物资收发存季度统计报表entity", dataType = "ZxSkStockTransferRptNew")
    @RequireToken
    @PostMapping("/getZxSkStockTransferRptNewList")
    public ResponseEntity getZxSkStockTransferRptNewList(@RequestBody(required = false) ZxSkStockTransferRptNew zxSkStockTransferRptNew) {
        return zxSkStockTransferRptNewService.getZxSkStockTransferRptNewListByCondition(zxSkStockTransferRptNew);
    }

    @ApiOperation(value="查询详情中交物资收发存季度统计报表", notes="查询详情中交物资收发存季度统计报表")
    @ApiImplicitParam(name = "zxSkStockTransferRptNew", value = "中交物资收发存季度统计报表entity", dataType = "ZxSkStockTransferRptNew")
    @RequireToken
    @PostMapping("/getZxSkStockTransferRptNewDetail")
    public ResponseEntity getZxSkStockTransferRptNewDetail(@RequestBody(required = false) ZxSkStockTransferRptNew zxSkStockTransferRptNew) {
        return zxSkStockTransferRptNewService.getZxSkStockTransferRptNewDetail(zxSkStockTransferRptNew);
    }

    @ApiOperation(value="新增中交物资收发存季度统计报表", notes="新增中交物资收发存季度统计报表")
    @ApiImplicitParam(name = "zxSkStockTransferRptNew", value = "中交物资收发存季度统计报表entity", dataType = "ZxSkStockTransferRptNew")
    @RequireToken
    @PostMapping("/addZxSkStockTransferRptNew")
    public ResponseEntity addZxSkStockTransferRptNew(@RequestBody(required = false) ZxSkStockTransferRptNew zxSkStockTransferRptNew) {
        return zxSkStockTransferRptNewService.saveZxSkStockTransferRptNew(zxSkStockTransferRptNew);
    }

    @ApiOperation(value="更新中交物资收发存季度统计报表", notes="更新中交物资收发存季度统计报表")
    @ApiImplicitParam(name = "zxSkStockTransferRptNew", value = "中交物资收发存季度统计报表entity", dataType = "ZxSkStockTransferRptNew")
    @RequireToken
    @PostMapping("/updateZxSkStockTransferRptNew")
    public ResponseEntity updateZxSkStockTransferRptNew(@RequestBody(required = false) ZxSkStockTransferRptNew zxSkStockTransferRptNew) {
        return zxSkStockTransferRptNewService.updateZxSkStockTransferRptNew(zxSkStockTransferRptNew);
    }

    @ApiOperation(value="删除中交物资收发存季度统计报表", notes="删除中交物资收发存季度统计报表")
    @ApiImplicitParam(name = "zxSkStockTransferRptNewList", value = "中交物资收发存季度统计报表List", dataType = "List<ZxSkStockTransferRptNew>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxSkStockTransferRptNew")
    public ResponseEntity batchDeleteUpdateZxSkStockTransferRptNew(@RequestBody(required = false) List<ZxSkStockTransferRptNew> zxSkStockTransferRptNewList) {
        return zxSkStockTransferRptNewService.batchDeleteUpdateZxSkStockTransferRptNew(zxSkStockTransferRptNewList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    
    @ApiOperation(value="报表导出中交物资收发存季度统计报表", notes="报表导出中交物资收发存季度统计报表")
    @ApiImplicitParam(name = "zxSkStockTransferRptNew", value = "设备台账entity", dataType = "ZxSkStockTransferRptNew")
    @PostMapping("/ureportZxSkStockTransferRptNew")
    public List<ZxSkStockTransferRptNew> ureportZxSkStockTransferRptNew(@RequestBody(required = false) ZxSkStockTransferRptNew zxSkStockTransferRptNew) {
        return zxSkStockTransferRptNewService.ureportZxSkStockTransferRptNew(zxSkStockTransferRptNew);
    }
    
    @ApiOperation(value="报表导出中交物资收发存季度统计报表", notes="报表导出中交物资收发存季度统计报表")
    @ApiImplicitParam(name = "zxSkStockTransferRptNew", value = "设备台账entity", dataType = "ZxSkStockTransferRptNew")
    @RequireToken
    @PostMapping("/ureportZxSkStockTransferRptNewIdle")
    public ResponseEntity ureportZxSkStockTransferRptNewIdle(@RequestBody(required = false) ZxSkStockTransferRptNew zxSkStockTransferRptNew) {
        return zxSkStockTransferRptNewService.ureportZxSkStockTransferRptNewIdle(zxSkStockTransferRptNew);
    }
}
