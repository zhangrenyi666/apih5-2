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
import com.apih5.mybatis.pojo.ZxSfSpecialEmpItem;
import com.apih5.service.ZxSfSpecialEmpItemService;

@RestController
public class ZxSfSpecialEmpItemController {

    @Autowired(required = true)
    private ZxSfSpecialEmpItemService zxSfSpecialEmpItemService;

    @ApiOperation(value="查询特种作业人员台账明细", notes="查询特种作业人员台账明细")
    @ApiImplicitParam(name = "zxSfSpecialEmpItem", value = "特种作业人员台账明细entity", dataType = "ZxSfSpecialEmpItem")
    @RequireToken
    @PostMapping("/getZxSfSpecialEmpItemList")
    public ResponseEntity getZxSfSpecialEmpItemList(@RequestBody(required = false) ZxSfSpecialEmpItem zxSfSpecialEmpItem) {
        return zxSfSpecialEmpItemService.getZxSfSpecialEmpItemListByCondition(zxSfSpecialEmpItem);
    }

    @ApiOperation(value="查询详情特种作业人员台账明细", notes="查询详情特种作业人员台账明细")
    @ApiImplicitParam(name = "zxSfSpecialEmpItem", value = "特种作业人员台账明细entity", dataType = "ZxSfSpecialEmpItem")
    @RequireToken
    @PostMapping("/getZxSfSpecialEmpItemDetail")
    public ResponseEntity getZxSfSpecialEmpItemDetail(@RequestBody(required = false) ZxSfSpecialEmpItem zxSfSpecialEmpItem) {
        return zxSfSpecialEmpItemService.getZxSfSpecialEmpItemDetail(zxSfSpecialEmpItem);
    }

    @ApiOperation(value="新增特种作业人员台账明细", notes="新增特种作业人员台账明细")
    @ApiImplicitParam(name = "zxSfSpecialEmpItem", value = "特种作业人员台账明细entity", dataType = "ZxSfSpecialEmpItem")
    @RequireToken
    @PostMapping("/addZxSfSpecialEmpItem")
    public ResponseEntity addZxSfSpecialEmpItem(@RequestBody(required = false) ZxSfSpecialEmpItem zxSfSpecialEmpItem) {
        return zxSfSpecialEmpItemService.saveZxSfSpecialEmpItem(zxSfSpecialEmpItem);
    }

    @ApiOperation(value="更新特种作业人员台账明细", notes="更新特种作业人员台账明细")
    @ApiImplicitParam(name = "zxSfSpecialEmpItem", value = "特种作业人员台账明细entity", dataType = "ZxSfSpecialEmpItem")
    @RequireToken
    @PostMapping("/updateZxSfSpecialEmpItem")
    public ResponseEntity updateZxSfSpecialEmpItem(@RequestBody(required = false) ZxSfSpecialEmpItem zxSfSpecialEmpItem) {
        return zxSfSpecialEmpItemService.updateZxSfSpecialEmpItem(zxSfSpecialEmpItem);
    }

    @ApiOperation(value="删除特种作业人员台账明细", notes="删除特种作业人员台账明细")
    @ApiImplicitParam(name = "zxSfSpecialEmpItemList", value = "特种作业人员台账明细List", dataType = "List<ZxSfSpecialEmpItem>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxSfSpecialEmpItem")
    public ResponseEntity batchDeleteUpdateZxSfSpecialEmpItem(@RequestBody(required = false) List<ZxSfSpecialEmpItem> zxSfSpecialEmpItemList) {
        return zxSfSpecialEmpItemService.batchDeleteUpdateZxSfSpecialEmpItem(zxSfSpecialEmpItemList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

    @ApiOperation(value="项目特种作业人员台账报表查询", notes="项目特种作业人员台账报表查询")
    @ApiImplicitParam(name = "zxSfSpecialEmpItem", value = "特种作业人员台账明细entity", dataType = "ZxSfSpecialEmpItem")
    @RequireToken
    @PostMapping("/getZxSfSpecialEmpItemDetailFormList")
    public ResponseEntity getZxSfSpecialEmpItemDetailFormList(@RequestBody(required = false) ZxSfSpecialEmpItem zxSfSpecialEmpItem) {
        return zxSfSpecialEmpItemService.getFormList(zxSfSpecialEmpItem);
    }

    @ApiOperation(value="项目特种作业人员台账报表查询", notes="项目特种作业人员台账报表查询")
    @ApiImplicitParam(name = "zxSfSpecialEmpItem", value = "特种作业人员台账明细entity", dataType = "ZxSfSpecialEmpItem")
    @PostMapping("/UreportZxSfSpecialEmpItemDetailForm")
    public List<ZxSfSpecialEmpItem> UreportZxSfSpecialEmpItemDetailForm(@RequestBody(required = false) ZxSfSpecialEmpItem zxSfSpecialEmpItem) {
        return zxSfSpecialEmpItemService.UreportForm(zxSfSpecialEmpItem);
    }

    @ApiOperation(value="公司特种作业人员台账报表查询", notes="公司特种作业人员台账报表查询")
    @ApiImplicitParam(name = "zxSfSpecialEmpItem", value = "特种作业人员台账明细entity", dataType = "ZxSfSpecialEmpItem")
    @RequireToken
    @PostMapping("/getZxSfSpecialEmpItemDetailFormComList")
    public ResponseEntity getZxSfSpecialEmpItemDetailFormComList(@RequestBody(required = false) ZxSfSpecialEmpItem zxSfSpecialEmpItem) {
        return zxSfSpecialEmpItemService.getFormListCom(zxSfSpecialEmpItem);
    }

    @ApiOperation(value="公司特种作业人员台账报表查询", notes="公司特种作业人员台账报表查询")
    @ApiImplicitParam(name = "zxSfSpecialEmpItem", value = "特种作业人员台账明细entity", dataType = "ZxSfSpecialEmpItem")
    @PostMapping("/UreportZxSfSpecialEmpItemDetailFormCom")
    public List<ZxSfSpecialEmpItem> UreportZxSfSpecialEmpItemDetailFormCom(@RequestBody(required = false) ZxSfSpecialEmpItem zxSfSpecialEmpItem) {
        return zxSfSpecialEmpItemService.UreportFormCom(zxSfSpecialEmpItem);
    }
}
