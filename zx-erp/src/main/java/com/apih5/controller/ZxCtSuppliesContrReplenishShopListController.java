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
import com.apih5.mybatis.pojo.ZxCtSuppliesContrReplenishShopList;
import com.apih5.service.ZxCtSuppliesContrReplenishShopListService;

@RestController
public class ZxCtSuppliesContrReplenishShopListController {

    @Autowired(required = true)
    private ZxCtSuppliesContrReplenishShopListService zxCtSuppliesContrReplenishShopListService;

    @ApiOperation(value="查询物资管理类合同补充协议清单（采购）", notes="查询物资管理类合同补充协议清单（采购）")
    @ApiImplicitParam(name = "zxCtSuppliesContrReplenishShopList", value = "物资管理类合同补充协议清单（采购）entity", dataType = "ZxCtSuppliesContrReplenishShopList")
    @RequireToken
    @PostMapping("/getZxCtSuppliesContrReplenishShopListList")
    public ResponseEntity getZxCtSuppliesContrReplenishShopListList(@RequestBody(required = false) ZxCtSuppliesContrReplenishShopList zxCtSuppliesContrReplenishShopList) {
        return zxCtSuppliesContrReplenishShopListService.getZxCtSuppliesContrReplenishShopListListByCondition(zxCtSuppliesContrReplenishShopList);
    }

    @ApiOperation(value="查询详情物资管理类合同补充协议清单（采购）", notes="查询详情物资管理类合同补充协议清单（采购）")
    @ApiImplicitParam(name = "zxCtSuppliesContrReplenishShopList", value = "物资管理类合同补充协议清单（采购）entity", dataType = "ZxCtSuppliesContrReplenishShopList")
    @RequireToken
    @PostMapping("/getZxCtSuppliesContrReplenishShopListDetail")
    public ResponseEntity getZxCtSuppliesContrReplenishShopListDetail(@RequestBody(required = false) ZxCtSuppliesContrReplenishShopList zxCtSuppliesContrReplenishShopList) {
        return zxCtSuppliesContrReplenishShopListService.getZxCtSuppliesContrReplenishShopListDetail(zxCtSuppliesContrReplenishShopList);
    }

    @ApiOperation(value="新增物资管理类合同补充协议清单（采购）", notes="新增物资管理类合同补充协议清单（采购）")
    @ApiImplicitParam(name = "zxCtSuppliesContrReplenishShopList", value = "物资管理类合同补充协议清单（采购）entity", dataType = "ZxCtSuppliesContrReplenishShopList")
    @RequireToken
    @PostMapping("/addZxCtSuppliesContrReplenishShopList")
    public ResponseEntity addZxCtSuppliesContrReplenishShopList(@RequestBody(required = false) ZxCtSuppliesContrReplenishShopList zxCtSuppliesContrReplenishShopList) {
        return zxCtSuppliesContrReplenishShopListService.saveZxCtSuppliesContrReplenishShopList(zxCtSuppliesContrReplenishShopList);
    }

    @ApiOperation(value="更新物资管理类合同补充协议清单（采购）", notes="更新物资管理类合同补充协议清单（采购）")
    @ApiImplicitParam(name = "zxCtSuppliesContrReplenishShopList", value = "物资管理类合同补充协议清单（采购）entity", dataType = "ZxCtSuppliesContrReplenishShopList")
    @RequireToken
    @PostMapping("/updateZxCtSuppliesContrReplenishShopList")
    public ResponseEntity updateZxCtSuppliesContrReplenishShopList(@RequestBody(required = false) ZxCtSuppliesContrReplenishShopList zxCtSuppliesContrReplenishShopList) {
        return zxCtSuppliesContrReplenishShopListService.updateZxCtSuppliesContrReplenishShopList(zxCtSuppliesContrReplenishShopList);
    }

    @ApiOperation(value="删除物资管理类合同补充协议清单（采购）", notes="删除物资管理类合同补充协议清单（采购）")
    @ApiImplicitParam(name = "zxCtSuppliesContrReplenishShopListList", value = "物资管理类合同补充协议清单（采购）List", dataType = "List<ZxCtSuppliesContrReplenishShopList>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxCtSuppliesContrReplenishShopList")
    public ResponseEntity batchDeleteUpdateZxCtSuppliesContrReplenishShopList(@RequestBody(required = false) List<ZxCtSuppliesContrReplenishShopList> zxCtSuppliesContrReplenishShopListList) {
        return zxCtSuppliesContrReplenishShopListService.batchDeleteUpdateZxCtSuppliesContrReplenishShopList(zxCtSuppliesContrReplenishShopListList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
