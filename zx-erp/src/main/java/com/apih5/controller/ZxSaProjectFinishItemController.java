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
import com.apih5.mybatis.pojo.ZxSaProjectFinishItem;
import com.apih5.service.ZxSaProjectFinishItemService;

@RestController
public class ZxSaProjectFinishItemController {

    @Autowired(required = true)
    private ZxSaProjectFinishItemService zxSaProjectFinishItemService;

    @ApiOperation(value="查询完工审核", notes="查询完工审核")
    @ApiImplicitParam(name = "zxSaProjectFinishItem", value = "完工审核entity", dataType = "ZxSaProjectFinishItem")
    @RequireToken
    @PostMapping("/getZxSaProjectFinishItemList")
    public ResponseEntity getZxSaProjectFinishItemList(@RequestBody(required = false) ZxSaProjectFinishItem zxSaProjectFinishItem) {
        return zxSaProjectFinishItemService.getZxSaProjectFinishItemListByCondition(zxSaProjectFinishItem);
    }

    @ApiOperation(value="查询详情完工审核", notes="查询详情完工审核")
    @ApiImplicitParam(name = "zxSaProjectFinishItem", value = "完工审核entity", dataType = "ZxSaProjectFinishItem")
    @RequireToken
    @PostMapping("/getZxSaProjectFinishItemDetails")
    public ResponseEntity getZxSaProjectFinishItemDetails(@RequestBody(required = false) ZxSaProjectFinishItem zxSaProjectFinishItem) {
        return zxSaProjectFinishItemService.getZxSaProjectFinishItemDetails(zxSaProjectFinishItem);
    }

    @ApiOperation(value="新增完工审核", notes="新增完工审核")
    @ApiImplicitParam(name = "zxSaProjectFinishItem", value = "完工审核entity", dataType = "ZxSaProjectFinishItem")
    @RequireToken
    @PostMapping("/addZxSaProjectFinishItem")
    public ResponseEntity addZxSaProjectFinishItem(@RequestBody(required = false) ZxSaProjectFinishItem zxSaProjectFinishItem) {
        return zxSaProjectFinishItemService.saveZxSaProjectFinishItem(zxSaProjectFinishItem);
    }

    @ApiOperation(value="更新完工审核", notes="更新完工审核")
    @ApiImplicitParam(name = "zxSaProjectFinishItem", value = "完工审核entity", dataType = "ZxSaProjectFinishItem")
    @RequireToken
    @PostMapping("/updateZxSaProjectFinishItem")
    public ResponseEntity updateZxSaProjectFinishItem(@RequestBody(required = false) ZxSaProjectFinishItem zxSaProjectFinishItem) {
        return zxSaProjectFinishItemService.updateZxSaProjectFinishItem(zxSaProjectFinishItem);
    }

    @ApiOperation(value="删除完工审核", notes="删除完工审核")
    @ApiImplicitParam(name = "zxSaProjectFinishItemList", value = "完工审核List", dataType = "List<ZxSaProjectFinishItem>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxSaProjectFinishItem")
    public ResponseEntity batchDeleteUpdateZxSaProjectFinishItem(@RequestBody(required = false) List<ZxSaProjectFinishItem> zxSaProjectFinishItemList) {
        return zxSaProjectFinishItemService.batchDeleteUpdateZxSaProjectFinishItem(zxSaProjectFinishItemList);
    }

}
