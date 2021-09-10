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
import com.apih5.service.ZxSfExamItemService;

@RestController
public class ZxSfExamItemController {

    @Autowired(required = true)
    private ZxSfExamItemService zxSfExamItemService;

    @ApiOperation(value="查询安全管理综合考核明细", notes="查询安全管理综合考核明细")
    @ApiImplicitParam(name = "zxSfExamItem", value = "安全管理综合考核明细entity", dataType = "ZxSfExamItem")
    @RequireToken
    @PostMapping("/getZxSfExamItemList")
    public ResponseEntity getZxSfExamItemList(@RequestBody(required = false) ZxSfExamItem zxSfExamItem) {
        return zxSfExamItemService.getZxSfExamItemListByCondition(zxSfExamItem);
    }

    @ApiOperation(value="查询详情安全管理综合考核明细", notes="查询详情安全管理综合考核明细")
    @ApiImplicitParam(name = "zxSfExamItem", value = "安全管理综合考核明细entity", dataType = "ZxSfExamItem")
    @RequireToken
    @PostMapping("/getZxSfExamItemDetail")
    public ResponseEntity getZxSfExamItemDetail(@RequestBody(required = false) ZxSfExamItem zxSfExamItem) {
        return zxSfExamItemService.getZxSfExamItemDetail(zxSfExamItem);
    }

    @ApiOperation(value="新增安全管理综合考核明细", notes="新增安全管理综合考核明细")
    @ApiImplicitParam(name = "zxSfExamItem", value = "安全管理综合考核明细entity", dataType = "ZxSfExamItem")
    @RequireToken
    @PostMapping("/addZxSfExamItem")
    public ResponseEntity addZxSfExamItem(@RequestBody(required = false) ZxSfExamItem zxSfExamItem) {
        return zxSfExamItemService.saveZxSfExamItem(zxSfExamItem);
    }

    @ApiOperation(value="更新安全管理综合考核明细", notes="更新安全管理综合考核明细")
    @ApiImplicitParam(name = "zxSfExamItem", value = "安全管理综合考核明细entity", dataType = "ZxSfExamItem")
    @RequireToken
    @PostMapping("/updateZxSfExamItem")
    public ResponseEntity updateZxSfExamItem(@RequestBody(required = false) ZxSfExamItem zxSfExamItem) {
        return zxSfExamItemService.updateZxSfExamItem(zxSfExamItem);
    }

    @ApiOperation(value="删除安全管理综合考核明细", notes="删除安全管理综合考核明细")
    @ApiImplicitParam(name = "zxSfExamItemList", value = "安全管理综合考核明细List", dataType = "List<ZxSfExamItem>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxSfExamItem")
    public ResponseEntity batchDeleteUpdateZxSfExamItem(@RequestBody(required = false) List<ZxSfExamItem> zxSfExamItemList) {
        return zxSfExamItemService.batchDeleteUpdateZxSfExamItem(zxSfExamItemList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    
    @ApiOperation(value="查询安全管理综合考核明细", notes="查询安全管理综合考核明细")
    @ApiImplicitParam(name = "zxSfExamItem", value = "安全管理综合考核明细entity", dataType = "ZxSfExamItem")
    @RequireToken
    @PostMapping("/getZxSfExamItemListInit")
    public ResponseEntity getZxSfExamItemListInit(@RequestBody(required = false) ZxSfExamItem zxSfExamItem) {
        return zxSfExamItemService.getZxSfExamItemInit(zxSfExamItem);
    }
}
