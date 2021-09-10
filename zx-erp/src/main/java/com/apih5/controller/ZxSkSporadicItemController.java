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
import com.apih5.mybatis.pojo.ZxSkSporadicItem;
import com.apih5.service.ZxSkSporadicItemService;

@RestController
public class ZxSkSporadicItemController {

    @Autowired(required = true)
    private ZxSkSporadicItemService zxSkSporadicItemService;

    @ApiOperation(value="查询零星采购明细", notes="查询零星采购明细")
    @ApiImplicitParam(name = "zxSkSporadicItem", value = "零星采购明细entity", dataType = "ZxSkSporadicItem")
    @RequireToken
    @PostMapping("/getZxSkSporadicItemList")
    public ResponseEntity getZxSkSporadicItemList(@RequestBody(required = false) ZxSkSporadicItem zxSkSporadicItem) {
        return zxSkSporadicItemService.getZxSkSporadicItemListByCondition(zxSkSporadicItem);
    }

    @ApiOperation(value="查询详情零星采购明细", notes="查询详情零星采购明细")
    @ApiImplicitParam(name = "zxSkSporadicItem", value = "零星采购明细entity", dataType = "ZxSkSporadicItem")
    @RequireToken
    @PostMapping("/getZxSkSporadicItemDetails")
    public ResponseEntity getZxSkSporadicItemDetails(@RequestBody(required = false) ZxSkSporadicItem zxSkSporadicItem) {
        return zxSkSporadicItemService.getZxSkSporadicItemDetails(zxSkSporadicItem);
    }

    @ApiOperation(value="新增零星采购明细", notes="新增零星采购明细")
    @ApiImplicitParam(name = "zxSkSporadicItem", value = "零星采购明细entity", dataType = "ZxSkSporadicItem")
    @RequireToken
    @PostMapping("/addZxSkSporadicItem")
    public ResponseEntity addZxSkSporadicItem(@RequestBody(required = false) ZxSkSporadicItem zxSkSporadicItem) {
        return zxSkSporadicItemService.saveZxSkSporadicItem(zxSkSporadicItem);
    }

    @ApiOperation(value="更新零星采购明细", notes="更新零星采购明细")
    @ApiImplicitParam(name = "zxSkSporadicItem", value = "零星采购明细entity", dataType = "ZxSkSporadicItem")
    @RequireToken
    @PostMapping("/updateZxSkSporadicItem")
    public ResponseEntity updateZxSkSporadicItem(@RequestBody(required = false) ZxSkSporadicItem zxSkSporadicItem) {
        return zxSkSporadicItemService.updateZxSkSporadicItem(zxSkSporadicItem);
    }

    @ApiOperation(value="删除零星采购明细", notes="删除零星采购明细")
    @ApiImplicitParam(name = "zxSkSporadicItemList", value = "零星采购明细List", dataType = "List<ZxSkSporadicItem>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxSkSporadicItem")
    public ResponseEntity batchDeleteUpdateZxSkSporadicItem(@RequestBody(required = false) List<ZxSkSporadicItem> zxSkSporadicItemList) {
        return zxSkSporadicItemService.batchDeleteUpdateZxSkSporadicItem(zxSkSporadicItemList);
    }

}
