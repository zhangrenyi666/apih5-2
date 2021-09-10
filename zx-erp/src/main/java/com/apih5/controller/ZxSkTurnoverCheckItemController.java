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
import com.apih5.mybatis.pojo.ZxSkTurnoverCheckItem;
import com.apih5.service.ZxSkTurnoverCheckItemService;

@RestController
public class ZxSkTurnoverCheckItemController {

    @Autowired(required = true)
    private ZxSkTurnoverCheckItemService zxSkTurnoverCheckItemService;

    @ApiOperation(value="查询周转材料冲帐明细", notes="查询周转材料冲帐明细")
    @ApiImplicitParam(name = "zxSkTurnoverCheckItem", value = "周转材料冲帐明细entity", dataType = "ZxSkTurnoverCheckItem")
    @RequireToken
    @PostMapping("/getZxSkTurnoverCheckItemList")
    public ResponseEntity getZxSkTurnoverCheckItemList(@RequestBody(required = false) ZxSkTurnoverCheckItem zxSkTurnoverCheckItem) {
        return zxSkTurnoverCheckItemService.getZxSkTurnoverCheckItemListByCondition(zxSkTurnoverCheckItem);
    }

    @ApiOperation(value="查询详情周转材料冲帐明细", notes="查询详情周转材料冲帐明细")
    @ApiImplicitParam(name = "zxSkTurnoverCheckItem", value = "周转材料冲帐明细entity", dataType = "ZxSkTurnoverCheckItem")
    @RequireToken
    @PostMapping("/getZxSkTurnoverCheckItemDetail")
    public ResponseEntity getZxSkTurnoverCheckItemDetail(@RequestBody(required = false) ZxSkTurnoverCheckItem zxSkTurnoverCheckItem) {
        return zxSkTurnoverCheckItemService.getZxSkTurnoverCheckItemDetail(zxSkTurnoverCheckItem);
    }

    @ApiOperation(value="新增周转材料冲帐明细", notes="新增周转材料冲帐明细")
    @ApiImplicitParam(name = "zxSkTurnoverCheckItem", value = "周转材料冲帐明细entity", dataType = "ZxSkTurnoverCheckItem")
    @RequireToken
    @PostMapping("/addZxSkTurnoverCheckItem")
    public ResponseEntity addZxSkTurnoverCheckItem(@RequestBody(required = false) ZxSkTurnoverCheckItem zxSkTurnoverCheckItem) {
        return zxSkTurnoverCheckItemService.saveZxSkTurnoverCheckItem(zxSkTurnoverCheckItem);
    }

    @ApiOperation(value="更新周转材料冲帐明细", notes="更新周转材料冲帐明细")
    @ApiImplicitParam(name = "zxSkTurnoverCheckItem", value = "周转材料冲帐明细entity", dataType = "ZxSkTurnoverCheckItem")
    @RequireToken
    @PostMapping("/updateZxSkTurnoverCheckItem")
    public ResponseEntity updateZxSkTurnoverCheckItem(@RequestBody(required = false) ZxSkTurnoverCheckItem zxSkTurnoverCheckItem) {
        return zxSkTurnoverCheckItemService.updateZxSkTurnoverCheckItem(zxSkTurnoverCheckItem);
    }

    @ApiOperation(value="删除周转材料冲帐明细", notes="删除周转材料冲帐明细")
    @ApiImplicitParam(name = "zxSkTurnoverCheckItemList", value = "周转材料冲帐明细List", dataType = "List<ZxSkTurnoverCheckItem>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxSkTurnoverCheckItem")
    public ResponseEntity batchDeleteUpdateZxSkTurnoverCheckItem(@RequestBody(required = false) List<ZxSkTurnoverCheckItem> zxSkTurnoverCheckItemList) {
        return zxSkTurnoverCheckItemService.batchDeleteUpdateZxSkTurnoverCheckItem(zxSkTurnoverCheckItemList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
