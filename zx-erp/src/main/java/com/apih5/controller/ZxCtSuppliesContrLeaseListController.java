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
import com.apih5.mybatis.pojo.ZxCtSuppliesContrLeaseList;
import com.apih5.service.ZxCtSuppliesContrLeaseListService;

@RestController
public class ZxCtSuppliesContrLeaseListController {

    @Autowired(required = true)
    private ZxCtSuppliesContrLeaseListService zxCtSuppliesContrLeaseListService;

    @ApiOperation(value="查询物资管理类合同清单(租赁类)", notes="查询物资管理类合同清单(租赁类)")
    @ApiImplicitParam(name = "zxCtSuppliesContrLeaseList", value = "物资管理类合同清单(租赁类)entity", dataType = "ZxCtSuppliesContrLeaseList")
    @RequireToken
    @PostMapping("/getZxCtSuppliesContrLeaseListList")
    public ResponseEntity getZxCtSuppliesContrLeaseListList(@RequestBody(required = false) ZxCtSuppliesContrLeaseList zxCtSuppliesContrLeaseList) {
        return zxCtSuppliesContrLeaseListService.getZxCtSuppliesContrLeaseListListByCondition(zxCtSuppliesContrLeaseList);
    }

    @ApiOperation(value="查询详情物资管理类合同清单(租赁类)", notes="查询详情物资管理类合同清单(租赁类)")
    @ApiImplicitParam(name = "zxCtSuppliesContrLeaseList", value = "物资管理类合同清单(租赁类)entity", dataType = "ZxCtSuppliesContrLeaseList")
    @RequireToken
    @PostMapping("/getZxCtSuppliesContrLeaseListDetail")
    public ResponseEntity getZxCtSuppliesContrLeaseListDetail(@RequestBody(required = false) ZxCtSuppliesContrLeaseList zxCtSuppliesContrLeaseList) {
        return zxCtSuppliesContrLeaseListService.getZxCtSuppliesContrLeaseListDetail(zxCtSuppliesContrLeaseList);
    }

    @ApiOperation(value="新增物资管理类合同清单(租赁类)", notes="新增物资管理类合同清单(租赁类)")
    @ApiImplicitParam(name = "zxCtSuppliesContrLeaseList", value = "物资管理类合同清单(租赁类)entity", dataType = "ZxCtSuppliesContrLeaseList")
    @RequireToken
    @PostMapping("/addZxCtSuppliesContrLeaseList")
    public ResponseEntity addZxCtSuppliesContrLeaseList(@RequestBody(required = false) ZxCtSuppliesContrLeaseList zxCtSuppliesContrLeaseList) {
        return zxCtSuppliesContrLeaseListService.saveZxCtSuppliesContrLeaseList(zxCtSuppliesContrLeaseList);
    }

    @ApiOperation(value="更新物资管理类合同清单(租赁类)", notes="更新物资管理类合同清单(租赁类)")
    @ApiImplicitParam(name = "zxCtSuppliesContrLeaseList", value = "物资管理类合同清单(租赁类)entity", dataType = "ZxCtSuppliesContrLeaseList")
    @RequireToken
    @PostMapping("/updateZxCtSuppliesContrLeaseList")
    public ResponseEntity updateZxCtSuppliesContrLeaseList(@RequestBody(required = false) ZxCtSuppliesContrLeaseList zxCtSuppliesContrLeaseList) {
        return zxCtSuppliesContrLeaseListService.updateZxCtSuppliesContrLeaseList(zxCtSuppliesContrLeaseList);
    }

    @ApiOperation(value="删除物资管理类合同清单(租赁类)", notes="删除物资管理类合同清单(租赁类)")
    @ApiImplicitParam(name = "zxCtSuppliesContrLeaseListList", value = "物资管理类合同清单(租赁类)List", dataType = "List<ZxCtSuppliesContrLeaseList>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxCtSuppliesContrLeaseList")
    public ResponseEntity batchDeleteUpdateZxCtSuppliesContrLeaseList(@RequestBody(required = false) List<ZxCtSuppliesContrLeaseList> zxCtSuppliesContrLeaseListList) {
        return zxCtSuppliesContrLeaseListService.batchDeleteUpdateZxCtSuppliesContrLeaseList(zxCtSuppliesContrLeaseListList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
