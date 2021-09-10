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
import com.apih5.mybatis.pojo.ZxCtSuppliesContrApplyLeaseList;
import com.apih5.service.ZxCtSuppliesContrApplyLeaseListService;

@RestController
public class ZxCtSuppliesContrApplyLeaseListController {

    @Autowired(required = true)
    private ZxCtSuppliesContrApplyLeaseListService zxCtSuppliesContrApplyLeaseListService;

    @ApiOperation(value="查询物资管理类合同评审清单(租赁类)", notes="查询物资管理类合同评审清单(租赁类)")
    @ApiImplicitParam(name = "zxCtSuppliesContrApplyLeaseList", value = "物资管理类合同评审清单(租赁类)entity", dataType = "ZxCtSuppliesContrApplyLeaseList")
    @RequireToken
    @PostMapping("/getZxCtSuppliesContrApplyLeaseListList")
    public ResponseEntity getZxCtSuppliesContrApplyLeaseListList(@RequestBody(required = false) ZxCtSuppliesContrApplyLeaseList zxCtSuppliesContrApplyLeaseList) {
        return zxCtSuppliesContrApplyLeaseListService.getZxCtSuppliesContrApplyLeaseListListByCondition(zxCtSuppliesContrApplyLeaseList);
    }

    @ApiOperation(value="查询详情物资管理类合同评审清单(租赁类)", notes="查询详情物资管理类合同评审清单(租赁类)")
    @ApiImplicitParam(name = "zxCtSuppliesContrApplyLeaseList", value = "物资管理类合同评审清单(租赁类)entity", dataType = "ZxCtSuppliesContrApplyLeaseList")
    @RequireToken
    @PostMapping("/getZxCtSuppliesContrApplyLeaseListDetail")
    public ResponseEntity getZxCtSuppliesContrApplyLeaseListDetail(@RequestBody(required = false) ZxCtSuppliesContrApplyLeaseList zxCtSuppliesContrApplyLeaseList) {
        return zxCtSuppliesContrApplyLeaseListService.getZxCtSuppliesContrApplyLeaseListDetail(zxCtSuppliesContrApplyLeaseList);
    }

    @ApiOperation(value="新增物资管理类合同评审清单(租赁类)", notes="新增物资管理类合同评审清单(租赁类)")
    @ApiImplicitParam(name = "zxCtSuppliesContrApplyLeaseList", value = "物资管理类合同评审清单(租赁类)entity", dataType = "ZxCtSuppliesContrApplyLeaseList")
    @RequireToken
    @PostMapping("/addZxCtSuppliesContrApplyLeaseList")
    public ResponseEntity addZxCtSuppliesContrApplyLeaseList(@RequestBody(required = false) ZxCtSuppliesContrApplyLeaseList zxCtSuppliesContrApplyLeaseList) {
        return zxCtSuppliesContrApplyLeaseListService.saveZxCtSuppliesContrApplyLeaseList(zxCtSuppliesContrApplyLeaseList);
    }

    @ApiOperation(value="更新物资管理类合同评审清单(租赁类)", notes="更新物资管理类合同评审清单(租赁类)")
    @ApiImplicitParam(name = "zxCtSuppliesContrApplyLeaseList", value = "物资管理类合同评审清单(租赁类)entity", dataType = "ZxCtSuppliesContrApplyLeaseList")
    @RequireToken
    @PostMapping("/updateZxCtSuppliesContrApplyLeaseList")
    public ResponseEntity updateZxCtSuppliesContrApplyLeaseList(@RequestBody(required = false) ZxCtSuppliesContrApplyLeaseList zxCtSuppliesContrApplyLeaseList) {
        return zxCtSuppliesContrApplyLeaseListService.updateZxCtSuppliesContrApplyLeaseList(zxCtSuppliesContrApplyLeaseList);
    }

    @ApiOperation(value="删除物资管理类合同评审清单(租赁类)", notes="删除物资管理类合同评审清单(租赁类)")
    @ApiImplicitParam(name = "zxCtSuppliesContrApplyLeaseListList", value = "物资管理类合同评审清单(租赁类)List", dataType = "List<ZxCtSuppliesContrApplyLeaseList>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxCtSuppliesContrApplyLeaseList")
    public ResponseEntity batchDeleteUpdateZxCtSuppliesContrApplyLeaseList(@RequestBody(required = false) List<ZxCtSuppliesContrApplyLeaseList> zxCtSuppliesContrApplyLeaseListList) {
        return zxCtSuppliesContrApplyLeaseListService.batchDeleteUpdateZxCtSuppliesContrApplyLeaseList(zxCtSuppliesContrApplyLeaseListList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

    @ApiOperation(value="新增物资管理类合同评审清单(租赁类)", notes="新增物资管理类合同评审清单(租赁类)")
    @ApiImplicitParam(name = "zxCtSuppliesContrApplyLeaseList", value = "物资管理类合同评审清单(租赁类)entity", dataType = "ZxCtSuppliesContrApplyLeaseList")
    @RequireToken
    @PostMapping("/addZxCtSuppliesContrApplyLeaseListByApplyId")
    public ResponseEntity addZxCtSuppliesContrApplyLeaseListByApplyId(@RequestBody(required = false) ZxCtSuppliesContrApplyLeaseList zxCtSuppliesContrApplyLeaseList) {
        return zxCtSuppliesContrApplyLeaseListService.saveZxCtSuppliesContrApplyLeaseListByApplyId(zxCtSuppliesContrApplyLeaseList);
    }
    
    @ApiOperation(value="更新物资管理类合同评审清单(租赁类)", notes="更新物资管理类合同评审清单(租赁类)")
    @ApiImplicitParam(name = "zxCtSuppliesContrApplyLeaseList", value = "物资管理类合同评审清单(租赁类)entity", dataType = "ZxCtSuppliesContrApplyLeaseList")
    @RequireToken
    @PostMapping("/updateZxCtSuppliesContrApplyLeaseListByApplyId")
    public ResponseEntity updateZxCtSuppliesContrApplyLeaseListByApplyId(@RequestBody(required = false) ZxCtSuppliesContrApplyLeaseList zxCtSuppliesContrApplyLeaseList) {
    	return zxCtSuppliesContrApplyLeaseListService.updateZxCtSuppliesContrApplyLeaseListByApplyId(zxCtSuppliesContrApplyLeaseList);
    }
}
