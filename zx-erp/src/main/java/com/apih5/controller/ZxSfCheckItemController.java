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
import com.apih5.mybatis.pojo.ZxSfCheckItem;
import com.apih5.service.ZxSfCheckItemService;

@RestController
public class ZxSfCheckItemController {

    @Autowired(required = true)
    private ZxSfCheckItemService zxSfCheckItemService;

    @ApiOperation(value="查询安全检查明细", notes="查询安全检查明细")
    @ApiImplicitParam(name = "zxSfCheckItem", value = "安全检查明细entity", dataType = "ZxSfCheckItem")
    @RequireToken
    @PostMapping("/getZxSfCheckItemList")
    public ResponseEntity getZxSfCheckItemList(@RequestBody(required = false) ZxSfCheckItem zxSfCheckItem) {
        return zxSfCheckItemService.getZxSfCheckItemListByCondition(zxSfCheckItem);
    }

    @ApiOperation(value="查询详情安全检查明细", notes="查询详情安全检查明细")
    @ApiImplicitParam(name = "zxSfCheckItem", value = "安全检查明细entity", dataType = "ZxSfCheckItem")
    @RequireToken
    @PostMapping("/getZxSfCheckItemDetail")
    public ResponseEntity getZxSfCheckItemDetail(@RequestBody(required = false) ZxSfCheckItem zxSfCheckItem) {
        return zxSfCheckItemService.getZxSfCheckItemDetail(zxSfCheckItem);
    }

    @ApiOperation(value="新增安全检查明细", notes="新增安全检查明细")
    @ApiImplicitParam(name = "zxSfCheckItem", value = "安全检查明细entity", dataType = "ZxSfCheckItem")
    @RequireToken
    @PostMapping("/addZxSfCheckItem")
    public ResponseEntity addZxSfCheckItem(@RequestBody(required = false) ZxSfCheckItem zxSfCheckItem) {
        return zxSfCheckItemService.saveZxSfCheckItem(zxSfCheckItem);
    }

    @ApiOperation(value="更新安全检查明细", notes="更新安全检查明细")
    @ApiImplicitParam(name = "zxSfCheckItem", value = "安全检查明细entity", dataType = "ZxSfCheckItem")
    @RequireToken
    @PostMapping("/updateZxSfCheckItem")
    public ResponseEntity updateZxSfCheckItem(@RequestBody(required = false) ZxSfCheckItem zxSfCheckItem) {
        return zxSfCheckItemService.updateZxSfCheckItem(zxSfCheckItem);
    }

    @ApiOperation(value="删除安全检查明细", notes="删除安全检查明细")
    @ApiImplicitParam(name = "zxSfCheckItemList", value = "安全检查明细List", dataType = "List<ZxSfCheckItem>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxSfCheckItem")
    public ResponseEntity batchDeleteUpdateZxSfCheckItem(@RequestBody(required = false) List<ZxSfCheckItem> zxSfCheckItemList) {
        return zxSfCheckItemService.batchDeleteUpdateZxSfCheckItem(zxSfCheckItemList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
