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
import com.apih5.mybatis.pojo.ZxCtSuppliesContrShopList;
import com.apih5.service.ZxCtSuppliesContrShopListService;

@RestController
public class ZxCtSuppliesContrShopListController {

    @Autowired(required = true)
    private ZxCtSuppliesContrShopListService zxCtSuppliesContrShopListService;

    @ApiOperation(value="查询物资管理类合同清单(采购类)", notes="查询物资管理类合同清单(采购类)")
    @ApiImplicitParam(name = "zxCtSuppliesContrShopList", value = "物资管理类合同清单(采购类)entity", dataType = "ZxCtSuppliesContrShopList")
    @RequireToken
    @PostMapping("/getZxCtSuppliesContrShopListList")
    public ResponseEntity getZxCtSuppliesContrShopListList(@RequestBody(required = false) ZxCtSuppliesContrShopList zxCtSuppliesContrShopList) {
        return zxCtSuppliesContrShopListService.getZxCtSuppliesContrShopListListByCondition(zxCtSuppliesContrShopList);
    }

    @ApiOperation(value="查询详情物资管理类合同清单(采购类)", notes="查询详情物资管理类合同清单(采购类)")
    @ApiImplicitParam(name = "zxCtSuppliesContrShopList", value = "物资管理类合同清单(采购类)entity", dataType = "ZxCtSuppliesContrShopList")
    @RequireToken
    @PostMapping("/getZxCtSuppliesContrShopListDetail")
    public ResponseEntity getZxCtSuppliesContrShopListDetail(@RequestBody(required = false) ZxCtSuppliesContrShopList zxCtSuppliesContrShopList) {
        return zxCtSuppliesContrShopListService.getZxCtSuppliesContrShopListDetail(zxCtSuppliesContrShopList);
    }

    @ApiOperation(value="新增物资管理类合同清单(采购类)", notes="新增物资管理类合同清单(采购类)")
    @ApiImplicitParam(name = "zxCtSuppliesContrShopList", value = "物资管理类合同清单(采购类)entity", dataType = "ZxCtSuppliesContrShopList")
    @RequireToken
    @PostMapping("/addZxCtSuppliesContrShopList")
    public ResponseEntity addZxCtSuppliesContrShopList(@RequestBody(required = false) ZxCtSuppliesContrShopList zxCtSuppliesContrShopList) {
        return zxCtSuppliesContrShopListService.saveZxCtSuppliesContrShopList(zxCtSuppliesContrShopList);
    }

    @ApiOperation(value="更新物资管理类合同清单(采购类)", notes="更新物资管理类合同清单(采购类)")
    @ApiImplicitParam(name = "zxCtSuppliesContrShopList", value = "物资管理类合同清单(采购类)entity", dataType = "ZxCtSuppliesContrShopList")
    @RequireToken
    @PostMapping("/updateZxCtSuppliesContrShopList")
    public ResponseEntity updateZxCtSuppliesContrShopList(@RequestBody(required = false) ZxCtSuppliesContrShopList zxCtSuppliesContrShopList) {
        return zxCtSuppliesContrShopListService.updateZxCtSuppliesContrShopList(zxCtSuppliesContrShopList);
    }

    @ApiOperation(value="删除物资管理类合同清单(采购类)", notes="删除物资管理类合同清单(采购类)")
    @ApiImplicitParam(name = "zxCtSuppliesContrShopListList", value = "物资管理类合同清单(采购类)List", dataType = "List<ZxCtSuppliesContrShopList>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxCtSuppliesContrShopList")
    public ResponseEntity batchDeleteUpdateZxCtSuppliesContrShopList(@RequestBody(required = false) List<ZxCtSuppliesContrShopList> zxCtSuppliesContrShopListList) {
        return zxCtSuppliesContrShopListService.batchDeleteUpdateZxCtSuppliesContrShopList(zxCtSuppliesContrShopListList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

    @ApiOperation(value="根据合同ID查询物资管理类合同清单(采购类)", notes="根据合同ID查询物资管理类合同清单(采购类)")
    @ApiImplicitParam(name = "zxCtSuppliesContrShopList", value = "物资管理类合同清单(采购类)entity", dataType = "ZxCtSuppliesContrShopList")
    @RequireToken
    @PostMapping("/getZxCtSuppliesContrShopListListByContID")
    public ResponseEntity getZxCtSuppliesContrShopListListByContID(@RequestBody(required = false) ZxCtSuppliesContrShopList zxCtSuppliesContrShopList) {
        return zxCtSuppliesContrShopListService.getZxCtSuppliesContrShopListListByContID(zxCtSuppliesContrShopList);
    }

    @ApiOperation(value="根据物资ID查询所有单价(采购类)", notes="根据物资ID查询所有单价(采购类)")
    @ApiImplicitParam(name = "zxCtSuppliesContrShopList", value = "物资管理类合同清单(采购类)entity", dataType = "ZxCtSuppliesContrShopList")
    @RequireToken
    @PostMapping("/getZxCtSuppliesContrShopListByWorkID")
    public ResponseEntity getZxCtSuppliesContrShopListByWorkID(@RequestBody(required = false) ZxCtSuppliesContrShopList zxCtSuppliesContrShopList) {
        return zxCtSuppliesContrShopListService.getZxCtSuppliesContrShopListByWorkID(zxCtSuppliesContrShopList);
    }
}
