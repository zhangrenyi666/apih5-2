package com.apih5.controller;

import java.util.Date;
import java.util.List;

import io.swagger.annotations.ApiImplicitParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import com.apih5.framework.annotation.RequireToken;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxSkStockDifMonthItem;
import com.apih5.service.ZxSkStockDifMonthItemService;

import javax.xml.crypto.Data;

@RestController
public class ZxSkStockDifMonthItemController {

    @Autowired(required = true)
    private ZxSkStockDifMonthItemService zxSkStockDifMonthItemService;

    @ApiOperation(value="查询物资价差量差核算(月)明细", notes="查询物资价差量差核算(月)明细")
    @ApiImplicitParam(name = "zxSkStockDifMonthItem", value = "物资价差量差核算(月)明细entity", dataType = "ZxSkStockDifMonthItem")
    @RequireToken
    @PostMapping("/getZxSkStockDifMonthItemList")
    public ResponseEntity getZxSkStockDifMonthItemList(@RequestBody(required = false) ZxSkStockDifMonthItem zxSkStockDifMonthItem) {
        return zxSkStockDifMonthItemService.getZxSkStockDifMonthItemListByCondition(zxSkStockDifMonthItem);
    }

    @ApiOperation(value="查询详情物资价差量差核算(月)明细", notes="查询详情物资价差量差核算(月)明细")
    @ApiImplicitParam(name = "zxSkStockDifMonthItem", value = "物资价差量差核算(月)明细entity", dataType = "ZxSkStockDifMonthItem")
    @RequireToken
    @PostMapping("/getZxSkStockDifMonthItemDetail")
    public ResponseEntity getZxSkStockDifMonthItemDetail(@RequestBody(required = false) ZxSkStockDifMonthItem zxSkStockDifMonthItem) {
        return zxSkStockDifMonthItemService.getZxSkStockDifMonthItemDetail(zxSkStockDifMonthItem);
    }

    @ApiOperation(value="新增物资价差量差核算(月)明细", notes="新增物资价差量差核算(月)明细")
    @ApiImplicitParam(name = "zxSkStockDifMonthItem", value = "物资价差量差核算(月)明细entity", dataType = "ZxSkStockDifMonthItem")
    @RequireToken
    @PostMapping("/addZxSkStockDifMonthItem")
    public ResponseEntity addZxSkStockDifMonthItem(@RequestBody(required = false) ZxSkStockDifMonthItem zxSkStockDifMonthItem) {
        return zxSkStockDifMonthItemService.saveZxSkStockDifMonthItem(zxSkStockDifMonthItem);
    }

    @ApiOperation(value="更新物资价差量差核算(月)明细", notes="更新物资价差量差核算(月)明细")
    @ApiImplicitParam(name = "zxSkStockDifMonthItem", value = "物资价差量差核算(月)明细entity", dataType = "ZxSkStockDifMonthItem")
    @RequireToken
    @PostMapping("/updateZxSkStockDifMonthItem")
    public ResponseEntity updateZxSkStockDifMonthItem(@RequestBody(required = false) ZxSkStockDifMonthItem zxSkStockDifMonthItem) {
        return zxSkStockDifMonthItemService.updateZxSkStockDifMonthItem(zxSkStockDifMonthItem);
    }

    @ApiOperation(value="删除物资价差量差核算(月)明细", notes="删除物资价差量差核算(月)明细")
    @ApiImplicitParam(name = "zxSkStockDifMonthItemList", value = "物资价差量差核算(月)明细List", dataType = "List<ZxSkStockDifMonthItem>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxSkStockDifMonthItem")
    public ResponseEntity batchDeleteUpdateZxSkStockDifMonthItem(@RequestBody(required = false) List<ZxSkStockDifMonthItem> zxSkStockDifMonthItemList) {
        return zxSkStockDifMonthItemService.batchDeleteUpdateZxSkStockDifMonthItem(zxSkStockDifMonthItemList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

    @ApiOperation(value="物资价差量差核算报表(月)", notes="物资价差量差核算报表(月)")
    @ApiImplicitParam(name = "zxSkStockDifMonthItem", value = "物资价差量差核算(月)明细", dataType = "ZxSkStockDifMonthItem")
    @PostMapping("/getStockDifMonthForm")
    public List<ZxSkStockDifMonthItem> getStockDifMonthForm(){
      return   zxSkStockDifMonthItemService.getStockDifMonthForm();
    }

    @ApiOperation(value="物资价差量差核算报表(月)", notes="物资价差量差核算报表(月)")
    @ApiImplicitParam(name = "zxSkStockDifMonthItem", value = "物资价差量差核算(月)明细", dataType = "ZxSkStockDifMonthItem")
    @RequireToken
    @PostMapping("/getStockDifMonthFormList")
    public ResponseEntity getStockDifMonthFormList(){
        Date period =new Date();
        return   zxSkStockDifMonthItemService.getStockDifMonthFormList(period);
    }

    @ApiOperation(value="物资价差量差核算报表(季度)", notes="物资价差量差核算报表(季度)")
    @ApiImplicitParam(name = "zxSkStockDifMonthItem", value = "物资价差量差核算(月)明细", dataType = "ZxSkStockDifMonthItem")
    @RequireToken
    @PostMapping("/getStockDifMonthFormJDList")
    public ResponseEntity getStockDifMonthFormJDList(){
        Date period =new Date();
        return   zxSkStockDifMonthItemService.getStockDifJiDuFormList(period);
    }

    @ApiOperation(value="物资价差量差核算报表(季度)", notes="物资价差量差核算报表(季度)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orgID", value = "机构ID", dataType = "String"),
            @ApiImplicitParam(name = "projectID", value = "项目ID", dataType = "String"),
            @ApiImplicitParam(name = "jiDu", value = "季度", dataType = "Date"),
            @ApiImplicitParam(name = "resCode", value = "物资编号", dataType = "String")
    })
    @PostMapping("/getStockDifJiDuForm")
    public List<ZxSkStockDifMonthItem> getStockDifJiDuForm(){
        //Date period =new Date();
        return zxSkStockDifMonthItemService.getStockDifJiDuForm();
    }
    
    @ApiOperation(value="公司量差汇总表1(按物资类别)", notes="公司量差汇总表1(按物资类别)")
    @ApiImplicitParam(name = "zxSkStockDifMonthItem", value = "公司量差汇总表1(按物资类别)", dataType = "ZxSkStockDifMonthItem")
    @PostMapping("/getStockDifMonthMaterialCategory")
    public List<ZxSkStockDifMonthItem> getStockDifMonthMaterialCategory(){
      return   zxSkStockDifMonthItemService.getStockDifMonthMaterialCategory();
    }

    @ApiOperation(value="物资动态帐", notes="物资动态帐")
    @ApiImplicitParam(name = "zxSkStockDifMonthItem", value = "物资动态帐", dataType = "ZxSkStockDifMonthItem")
    @PostMapping("/getReceivingDynamicItem")
    public ResponseEntity getReceivingDynamicItem(@RequestBody(required = false) ZxSkStockDifMonthItem zxSkStockDifMonthItem){
      return zxSkStockDifMonthItemService.getReceivingDynamicItem(zxSkStockDifMonthItem);
    }
    
    @ApiOperation(value="物资动态帐报表", notes="物资动态帐")
    @ApiImplicitParam(name = "zxSkStockDifMonthItem", value = "物资动态帐", dataType = "ZxSkStockDifMonthItem")
    @PostMapping("/getReceivingDynamic")
    public List<ZxSkStockDifMonthItem> getReceivingDynamic(@RequestBody(required = false) ZxSkStockDifMonthItem zxSkStockDifMonthItem){
      return zxSkStockDifMonthItemService.getReceivingDynamic(zxSkStockDifMonthItem);
    }
    
    @ApiOperation(value="物资收发存月报表导出", notes="物资收发存月报表导出")
    @ApiImplicitParam(name = "zxSkStockDifMonthItem", value = "物资收发存月报表导出", dataType = "ZxSkStockDifMonthItem")
    @PostMapping("/getResMoveMonthMP")
    public List<ZxSkStockDifMonthItem> getResMoveMonthMP(){
      return zxSkStockDifMonthItemService.getResMoveMonthMP();
    }
    
    @ApiOperation(value="物资收发存月报表", notes="物资收发存月报表")
    @ApiImplicitParam(name = "zxSkStockDifMonthItem", value = "物资收发存月报表", dataType = "ZxSkStockDifMonthItem")
    @PostMapping("/getResMoveMonthMPItem")
    public ResponseEntity getResMoveMonthMPItem(){
      return zxSkStockDifMonthItemService.getResMoveMonthMPItem();
    }
}
