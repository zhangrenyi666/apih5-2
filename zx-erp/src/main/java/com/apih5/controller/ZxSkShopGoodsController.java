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
import com.apih5.mybatis.pojo.ZxSkShopGoods;
import com.apih5.service.ZxSkShopGoodsService;

@RestController
public class ZxSkShopGoodsController {

    @Autowired(required = true)
    private ZxSkShopGoodsService zxSkShopGoodsService;

    @ApiOperation(value="查询进货台账", notes="查询进货台账")
    @ApiImplicitParam(name = "zxSkShopGoods", value = "进货台账entity", dataType = "ZxSkShopGoods")
    @RequireToken
    @PostMapping("/getZxSkShopGoodsList")
    public ResponseEntity getZxSkShopGoodsList(@RequestBody(required = false) ZxSkShopGoods zxSkShopGoods) {
        return zxSkShopGoodsService.getZxSkShopGoodsListByCondition(zxSkShopGoods);
    }

    @ApiOperation(value="查询详情进货台账", notes="查询详情进货台账")
    @ApiImplicitParam(name = "zxSkShopGoods", value = "进货台账entity", dataType = "ZxSkShopGoods")
    @RequireToken
    @PostMapping("/getZxSkShopGoodsDetail")
    public ResponseEntity getZxSkShopGoodsDetail(@RequestBody(required = false) ZxSkShopGoods zxSkShopGoods) {
        return zxSkShopGoodsService.getZxSkShopGoodsDetail(zxSkShopGoods);
    }

    @ApiOperation(value="新增进货台账", notes="新增进货台账")
    @ApiImplicitParam(name = "zxSkShopGoods", value = "进货台账entity", dataType = "ZxSkShopGoods")
    @RequireToken
    @PostMapping("/addZxSkShopGoods")
    public ResponseEntity addZxSkShopGoods(@RequestBody(required = false) ZxSkShopGoods zxSkShopGoods) {
        return zxSkShopGoodsService.saveZxSkShopGoods(zxSkShopGoods);
    }

    @ApiOperation(value="更新进货台账", notes="更新进货台账")
    @ApiImplicitParam(name = "zxSkShopGoods", value = "进货台账entity", dataType = "ZxSkShopGoods")
    @RequireToken
    @PostMapping("/updateZxSkShopGoods")
    public ResponseEntity updateZxSkShopGoods(@RequestBody(required = false) ZxSkShopGoods zxSkShopGoods) {
        return zxSkShopGoodsService.updateZxSkShopGoods(zxSkShopGoods);
    }

    @ApiOperation(value="删除进货台账", notes="删除进货台账")
    @ApiImplicitParam(name = "zxSkShopGoodsList", value = "进货台账List", dataType = "List<ZxSkShopGoods>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxSkShopGoods")
    public ResponseEntity batchDeleteUpdateZxSkShopGoods(@RequestBody(required = false) List<ZxSkShopGoods> zxSkShopGoodsList) {
        return zxSkShopGoodsService.batchDeleteUpdateZxSkShopGoods(zxSkShopGoodsList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
