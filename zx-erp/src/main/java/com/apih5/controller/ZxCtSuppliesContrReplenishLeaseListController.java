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
import com.apih5.mybatis.pojo.ZxCtSuppliesContrReplenishLeaseList;
import com.apih5.service.ZxCtSuppliesContrReplenishLeaseListService;

@RestController
public class ZxCtSuppliesContrReplenishLeaseListController {

    @Autowired(required = true)
    private ZxCtSuppliesContrReplenishLeaseListService zxCtSuppliesContrReplenishLeaseListService;

    @ApiOperation(value="查询物资管理类合同补充协议清单（租赁）", notes="查询物资管理类合同补充协议清单（租赁）")
    @ApiImplicitParam(name = "zxCtSuppliesContrReplenishLeaseList", value = "物资管理类合同补充协议清单（租赁）entity", dataType = "ZxCtSuppliesContrReplenishLeaseList")
    @RequireToken
    @PostMapping("/getZxCtSuppliesContrReplenishLeaseListList")
    public ResponseEntity getZxCtSuppliesContrReplenishLeaseListList(@RequestBody(required = false) ZxCtSuppliesContrReplenishLeaseList zxCtSuppliesContrReplenishLeaseList) {
        return zxCtSuppliesContrReplenishLeaseListService.getZxCtSuppliesContrReplenishLeaseListListByCondition(zxCtSuppliesContrReplenishLeaseList);
    }

    @ApiOperation(value="查询详情物资管理类合同补充协议清单（租赁）", notes="查询详情物资管理类合同补充协议清单（租赁）")
    @ApiImplicitParam(name = "zxCtSuppliesContrReplenishLeaseList", value = "物资管理类合同补充协议清单（租赁）entity", dataType = "ZxCtSuppliesContrReplenishLeaseList")
    @RequireToken
    @PostMapping("/getZxCtSuppliesContrReplenishLeaseListDetail")
    public ResponseEntity getZxCtSuppliesContrReplenishLeaseListDetail(@RequestBody(required = false) ZxCtSuppliesContrReplenishLeaseList zxCtSuppliesContrReplenishLeaseList) {
        return zxCtSuppliesContrReplenishLeaseListService.getZxCtSuppliesContrReplenishLeaseListDetail(zxCtSuppliesContrReplenishLeaseList);
    }

    @ApiOperation(value="新增物资管理类合同补充协议清单（租赁）", notes="新增物资管理类合同补充协议清单（租赁）")
    @ApiImplicitParam(name = "zxCtSuppliesContrReplenishLeaseList", value = "物资管理类合同补充协议清单（租赁）entity", dataType = "ZxCtSuppliesContrReplenishLeaseList")
    @RequireToken
    @PostMapping("/addZxCtSuppliesContrReplenishLeaseList")
    public ResponseEntity addZxCtSuppliesContrReplenishLeaseList(@RequestBody(required = false) ZxCtSuppliesContrReplenishLeaseList zxCtSuppliesContrReplenishLeaseList) {
        return zxCtSuppliesContrReplenishLeaseListService.saveZxCtSuppliesContrReplenishLeaseList(zxCtSuppliesContrReplenishLeaseList);
    }

    @ApiOperation(value="更新物资管理类合同补充协议清单（租赁）", notes="更新物资管理类合同补充协议清单（租赁）")
    @ApiImplicitParam(name = "zxCtSuppliesContrReplenishLeaseList", value = "物资管理类合同补充协议清单（租赁）entity", dataType = "ZxCtSuppliesContrReplenishLeaseList")
    @RequireToken
    @PostMapping("/updateZxCtSuppliesContrReplenishLeaseList")
    public ResponseEntity updateZxCtSuppliesContrReplenishLeaseList(@RequestBody(required = false) ZxCtSuppliesContrReplenishLeaseList zxCtSuppliesContrReplenishLeaseList) {
        return zxCtSuppliesContrReplenishLeaseListService.updateZxCtSuppliesContrReplenishLeaseList(zxCtSuppliesContrReplenishLeaseList);
    }

    @ApiOperation(value="删除物资管理类合同补充协议清单（租赁）", notes="删除物资管理类合同补充协议清单（租赁）")
    @ApiImplicitParam(name = "zxCtSuppliesContrReplenishLeaseListList", value = "物资管理类合同补充协议清单（租赁）List", dataType = "List<ZxCtSuppliesContrReplenishLeaseList>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxCtSuppliesContrReplenishLeaseList")
    public ResponseEntity batchDeleteUpdateZxCtSuppliesContrReplenishLeaseList(@RequestBody(required = false) List<ZxCtSuppliesContrReplenishLeaseList> zxCtSuppliesContrReplenishLeaseListList) {
        return zxCtSuppliesContrReplenishLeaseListService.batchDeleteUpdateZxCtSuppliesContrReplenishLeaseList(zxCtSuppliesContrReplenishLeaseListList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
