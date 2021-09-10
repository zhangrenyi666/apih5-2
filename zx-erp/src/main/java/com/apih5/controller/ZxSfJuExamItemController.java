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
import com.apih5.mybatis.pojo.ZxSfExamItem;
import com.apih5.mybatis.pojo.ZxSfJuExamItem;
import com.apih5.service.ZxSfJuExamItemService;

@RestController
public class ZxSfJuExamItemController {

    @Autowired(required = true)
    private ZxSfJuExamItemService zxSfJuExamItemService;

    @ApiOperation(value="查询安全管理综合考核（局）明细", notes="查询安全管理综合考核（局）明细")
    @ApiImplicitParam(name = "zxSfJuExamItem", value = "安全管理综合考核（局）明细entity", dataType = "ZxSfJuExamItem")
    @RequireToken
    @PostMapping("/getZxSfJuExamItemList")
    public ResponseEntity getZxSfJuExamItemList(@RequestBody(required = false) ZxSfJuExamItem zxSfJuExamItem) {
        return zxSfJuExamItemService.getZxSfJuExamItemListByCondition(zxSfJuExamItem);
    }

    @ApiOperation(value="查询详情安全管理综合考核（局）明细", notes="查询详情安全管理综合考核（局）明细")
    @ApiImplicitParam(name = "zxSfJuExamItem", value = "安全管理综合考核（局）明细entity", dataType = "ZxSfJuExamItem")
    @RequireToken
    @PostMapping("/getZxSfJuExamItemDetail")
    public ResponseEntity getZxSfJuExamItemDetail(@RequestBody(required = false) ZxSfJuExamItem zxSfJuExamItem) {
        return zxSfJuExamItemService.getZxSfJuExamItemDetail(zxSfJuExamItem);
    }

    @ApiOperation(value="新增安全管理综合考核（局）明细", notes="新增安全管理综合考核（局）明细")
    @ApiImplicitParam(name = "zxSfJuExamItem", value = "安全管理综合考核（局）明细entity", dataType = "ZxSfJuExamItem")
    @RequireToken
    @PostMapping("/addZxSfJuExamItem")
    public ResponseEntity addZxSfJuExamItem(@RequestBody(required = false) ZxSfJuExamItem zxSfJuExamItem) {
        return zxSfJuExamItemService.saveZxSfJuExamItem(zxSfJuExamItem);
    }

    @ApiOperation(value="更新安全管理综合考核（局）明细", notes="更新安全管理综合考核（局）明细")
    @ApiImplicitParam(name = "zxSfJuExamItem", value = "安全管理综合考核（局）明细entity", dataType = "ZxSfJuExamItem")
    @RequireToken
    @PostMapping("/updateZxSfJuExamItem")
    public ResponseEntity updateZxSfJuExamItem(@RequestBody(required = false) ZxSfJuExamItem zxSfJuExamItem) {
        return zxSfJuExamItemService.updateZxSfJuExamItem(zxSfJuExamItem);
    }

    @ApiOperation(value="删除安全管理综合考核（局）明细", notes="删除安全管理综合考核（局）明细")
    @ApiImplicitParam(name = "zxSfJuExamItemList", value = "安全管理综合考核（局）明细List", dataType = "List<ZxSfJuExamItem>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxSfJuExamItem")
    public ResponseEntity batchDeleteUpdateZxSfJuExamItem(@RequestBody(required = false) List<ZxSfJuExamItem> zxSfJuExamItemList) {
        return zxSfJuExamItemService.batchDeleteUpdateZxSfJuExamItem(zxSfJuExamItemList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    
    @ApiOperation(value="查询安全管理综合考核(局)明细", notes="查询安全管理综合考核明细")
    @ApiImplicitParam(name = "zxSfJuExamItem", value = "安全管理综合考核明细entity", dataType = "ZxSfJuExamItem")
    @RequireToken
    @PostMapping("/getZxSfJuExamItemListInit")
    public ResponseEntity getZxSfJuExamItemListInit(@RequestBody(required = false) ZxSfJuExamItem zxSfJuExamItem) {
        return zxSfJuExamItemService.getZxSfJuExamItemInit(zxSfJuExamItem);
    }
}
