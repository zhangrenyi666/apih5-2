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
import com.apih5.mybatis.pojo.ZxBuProjectTypeItem;
import com.apih5.service.ZxBuProjectTypeItemService;

@RestController
public class ZxBuProjectTypeItemController {

    @Autowired(required = true)
    private ZxBuProjectTypeItemService zxBuProjectTypeItemService;

    @ApiOperation(value="查询项目工程类别明细", notes="查询项目工程类别明细")
    @ApiImplicitParam(name = "zxBuProjectTypeItem", value = "项目工程类别明细entity", dataType = "ZxBuProjectTypeItem")
    @RequireToken
    @PostMapping("/getZxBuProjectTypeItemList")
    public ResponseEntity getZxBuProjectTypeItemList(@RequestBody(required = false) ZxBuProjectTypeItem zxBuProjectTypeItem) {
        return zxBuProjectTypeItemService.getZxBuProjectTypeItemListByCondition(zxBuProjectTypeItem);
    }

    @ApiOperation(value="查询详情项目工程类别明细", notes="查询详情项目工程类别明细")
    @ApiImplicitParam(name = "zxBuProjectTypeItem", value = "项目工程类别明细entity", dataType = "ZxBuProjectTypeItem")
    @RequireToken
    @PostMapping("/getZxBuProjectTypeItemDetail")
    public ResponseEntity getZxBuProjectTypeItemDetail(@RequestBody(required = false) ZxBuProjectTypeItem zxBuProjectTypeItem) {
        return zxBuProjectTypeItemService.getZxBuProjectTypeItemDetail(zxBuProjectTypeItem);
    }

    @ApiOperation(value="新增项目工程类别明细", notes="新增项目工程类别明细")
    @ApiImplicitParam(name = "zxBuProjectTypeItem", value = "项目工程类别明细entity", dataType = "ZxBuProjectTypeItem")
    @RequireToken
    @PostMapping("/addZxBuProjectTypeItem")
    public ResponseEntity addZxBuProjectTypeItem(@RequestBody(required = false) ZxBuProjectTypeItem zxBuProjectTypeItem) {
        return zxBuProjectTypeItemService.saveZxBuProjectTypeItem(zxBuProjectTypeItem);
    }

    @ApiOperation(value="更新项目工程类别明细", notes="更新项目工程类别明细")
    @ApiImplicitParam(name = "zxBuProjectTypeItem", value = "项目工程类别明细entity", dataType = "ZxBuProjectTypeItem")
    @RequireToken
    @PostMapping("/updateZxBuProjectTypeItem")
    public ResponseEntity updateZxBuProjectTypeItem(@RequestBody(required = false) ZxBuProjectTypeItem zxBuProjectTypeItem) {
        return zxBuProjectTypeItemService.updateZxBuProjectTypeItem(zxBuProjectTypeItem);
    }

    @ApiOperation(value="删除项目工程类别明细", notes="删除项目工程类别明细")
    @ApiImplicitParam(name = "zxBuProjectTypeItemList", value = "项目工程类别明细List", dataType = "List<ZxBuProjectTypeItem>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxBuProjectTypeItem")
    public ResponseEntity batchDeleteUpdateZxBuProjectTypeItem(@RequestBody(required = false) List<ZxBuProjectTypeItem> zxBuProjectTypeItemList) {
        return zxBuProjectTypeItemService.batchDeleteUpdateZxBuProjectTypeItem(zxBuProjectTypeItemList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
