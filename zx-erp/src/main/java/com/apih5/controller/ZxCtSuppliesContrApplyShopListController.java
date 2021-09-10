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
import com.apih5.mybatis.pojo.ZxCtSuppliesContrApplyShopList;
import com.apih5.service.ZxCtSuppliesContrApplyShopListService;

@RestController
public class ZxCtSuppliesContrApplyShopListController {

    @Autowired(required = true)
    private ZxCtSuppliesContrApplyShopListService zxCtSuppliesContrApplyShopListService;

    @ApiOperation(value="查询物资管理类合同评审清单(采购类)", notes="查询物资管理类合同评审清单(采购类)")
    @ApiImplicitParam(name = "zxCtSuppliesContrApplyShopList", value = "物资管理类合同评审清单(采购类)entity", dataType = "ZxCtSuppliesContrApplyShopList")
    @RequireToken
    @PostMapping("/getZxCtSuppliesContrApplyShopListList")
    public ResponseEntity getZxCtSuppliesContrApplyShopListList(@RequestBody(required = false) ZxCtSuppliesContrApplyShopList zxCtSuppliesContrApplyShopList) {
        return zxCtSuppliesContrApplyShopListService.getZxCtSuppliesContrApplyShopListListByCondition(zxCtSuppliesContrApplyShopList);
    }

    @ApiOperation(value="查询详情物资管理类合同评审清单(采购类)", notes="查询详情物资管理类合同评审清单(采购类)")
    @ApiImplicitParam(name = "zxCtSuppliesContrApplyShopList", value = "物资管理类合同评审清单(采购类)entity", dataType = "ZxCtSuppliesContrApplyShopList")
    @RequireToken
    @PostMapping("/getZxCtSuppliesContrApplyShopListDetail")
    public ResponseEntity getZxCtSuppliesContrApplyShopListDetail(@RequestBody(required = false) ZxCtSuppliesContrApplyShopList zxCtSuppliesContrApplyShopList) {
        return zxCtSuppliesContrApplyShopListService.getZxCtSuppliesContrApplyShopListDetail(zxCtSuppliesContrApplyShopList);
    }

    @ApiOperation(value="新增物资管理类合同评审清单(采购类)", notes="新增物资管理类合同评审清单(采购类)")
    @ApiImplicitParam(name = "zxCtSuppliesContrApplyShopList", value = "物资管理类合同评审清单(采购类)entity", dataType = "ZxCtSuppliesContrApplyShopList")
    @RequireToken
    @PostMapping("/addZxCtSuppliesContrApplyShopList")
    public ResponseEntity addZxCtSuppliesContrApplyShopList(@RequestBody(required = false) ZxCtSuppliesContrApplyShopList zxCtSuppliesContrApplyShopList) {
        return zxCtSuppliesContrApplyShopListService.saveZxCtSuppliesContrApplyShopList(zxCtSuppliesContrApplyShopList);
    }

    @ApiOperation(value="更新物资管理类合同评审清单(采购类)", notes="更新物资管理类合同评审清单(采购类)")
    @ApiImplicitParam(name = "zxCtSuppliesContrApplyShopList", value = "物资管理类合同评审清单(采购类)entity", dataType = "ZxCtSuppliesContrApplyShopList")
    @RequireToken
    @PostMapping("/updateZxCtSuppliesContrApplyShopList")
    public ResponseEntity updateZxCtSuppliesContrApplyShopList(@RequestBody(required = false) ZxCtSuppliesContrApplyShopList zxCtSuppliesContrApplyShopList) {
        return zxCtSuppliesContrApplyShopListService.updateZxCtSuppliesContrApplyShopList(zxCtSuppliesContrApplyShopList);
    }

    @ApiOperation(value="删除物资管理类合同评审清单(采购类)", notes="删除物资管理类合同评审清单(采购类)")
    @ApiImplicitParam(name = "zxCtSuppliesContrApplyShopListList", value = "物资管理类合同评审清单(采购类)List", dataType = "List<ZxCtSuppliesContrApplyShopList>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxCtSuppliesContrApplyShopList")
    public ResponseEntity batchDeleteUpdateZxCtSuppliesContrApplyShopList(@RequestBody(required = false) List<ZxCtSuppliesContrApplyShopList> zxCtSuppliesContrApplyShopListList) {
        return zxCtSuppliesContrApplyShopListService.batchDeleteUpdateZxCtSuppliesContrApplyShopList(zxCtSuppliesContrApplyShopListList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

    @ApiOperation(value="新增物资管理类合同评审清单(采购类)", notes="新增物资管理类合同评审清单(采购类)")
    @ApiImplicitParam(name = "zxCtSuppliesContrApplyShopList", value = "物资管理类合同评审清单(采购类)entity", dataType = "ZxCtSuppliesContrApplyShopList")
    @RequireToken
    @PostMapping("/addZxCtSuppliesContrApplyShopListByApplyId")
    public ResponseEntity addZxCtSuppliesContrApplyShopListByApplyId(@RequestBody(required = false) ZxCtSuppliesContrApplyShopList zxCtSuppliesContrApplyShopList) {
        return zxCtSuppliesContrApplyShopListService.saveZxCtSuppliesContrApplyShopListByApplyId(zxCtSuppliesContrApplyShopList);
    }
    
    @ApiOperation(value="更新物资管理类合同评审清单(采购类)", notes="更新物资管理类合同评审清单(采购类)")
    @ApiImplicitParam(name = "zxCtSuppliesContrApplyShopList", value = "物资管理类合同评审清单(采购类)entity", dataType = "ZxCtSuppliesContrApplyShopList")
    @RequireToken
    @PostMapping("/updateZxCtSuppliesContrApplyShopListByApplyId")
    public ResponseEntity updateZxCtSuppliesContrApplyShopListByApplyId(@RequestBody(required = false) ZxCtSuppliesContrApplyShopList zxCtSuppliesContrApplyShopList) {
        return zxCtSuppliesContrApplyShopListService.updateZxCtSuppliesContrApplyShopListByApplyId(zxCtSuppliesContrApplyShopList);
    }

    @ApiOperation(value="删除物资管理类合同评审清单(采购类)", notes="删除物资管理类合同评审清单(采购类)")
    @ApiImplicitParam(name = "zxCtSuppliesContrApplyShopListList", value = "物资管理类合同评审清单(采购类)List", dataType = "List<ZxCtSuppliesContrApplyShopList>")
    @RequireToken
    @PostMapping("/batchDeleteZxCtSuppliesContrApplyShopListByApplyId")
    public ResponseEntity batchDeleteZxCtSuppliesContrApplyShopListByApplyId(@RequestBody(required = false) List<ZxCtSuppliesContrApplyShopList> zxCtSuppliesContrApplyShopListList) {
        return zxCtSuppliesContrApplyShopListService.batchDeleteZxCtSuppliesContrApplyShopListByApplyId(zxCtSuppliesContrApplyShopListList);
    }    
}
