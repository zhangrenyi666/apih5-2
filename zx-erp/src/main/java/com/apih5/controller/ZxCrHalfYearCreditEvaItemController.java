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
import com.apih5.mybatis.pojo.ZxCrColCategory;
import com.apih5.mybatis.pojo.ZxCrHalfYearCreditEvaItem;
import com.apih5.service.ZxCrHalfYearCreditEvaItemService;

@RestController
public class ZxCrHalfYearCreditEvaItemController {

    @Autowired(required = true)
    private ZxCrHalfYearCreditEvaItemService zxCrHalfYearCreditEvaItemService;

    @ApiOperation(value="查询公司半年信用评价明细", notes="查询公司半年信用评价明细")
    @ApiImplicitParam(name = "zxCrHalfYearCreditEvaItem", value = "公司半年信用评价明细entity", dataType = "ZxCrHalfYearCreditEvaItem")
    @RequireToken
    @PostMapping("/getZxCrHalfYearCreditEvaItemList")
    public ResponseEntity getZxCrHalfYearCreditEvaItemList(@RequestBody(required = false) ZxCrHalfYearCreditEvaItem zxCrHalfYearCreditEvaItem) {
        return zxCrHalfYearCreditEvaItemService.getZxCrHalfYearCreditEvaItemListByCondition(zxCrHalfYearCreditEvaItem);
    }

    @ApiOperation(value="查询详情公司半年信用评价明细", notes="查询详情公司半年信用评价明细")
    @ApiImplicitParam(name = "zxCrHalfYearCreditEvaItem", value = "公司半年信用评价明细entity", dataType = "ZxCrHalfYearCreditEvaItem")
    @RequireToken
    @PostMapping("/getZxCrHalfYearCreditEvaItemDetail")
    public ResponseEntity getZxCrHalfYearCreditEvaItemDetail(@RequestBody(required = false) ZxCrHalfYearCreditEvaItem zxCrHalfYearCreditEvaItem) {
        return zxCrHalfYearCreditEvaItemService.getZxCrHalfYearCreditEvaItemDetail(zxCrHalfYearCreditEvaItem);
    }

    @ApiOperation(value="新增公司半年信用评价明细", notes="新增公司半年信用评价明细")
    @ApiImplicitParam(name = "zxCrHalfYearCreditEvaItem", value = "公司半年信用评价明细entity", dataType = "ZxCrHalfYearCreditEvaItem")
    @RequireToken
    @PostMapping("/addZxCrHalfYearCreditEvaItem")
    public ResponseEntity addZxCrHalfYearCreditEvaItem(@RequestBody(required = false) ZxCrHalfYearCreditEvaItem zxCrHalfYearCreditEvaItem) {
        return zxCrHalfYearCreditEvaItemService.saveZxCrHalfYearCreditEvaItem(zxCrHalfYearCreditEvaItem);
    }

    @ApiOperation(value="更新公司半年信用评价明细", notes="更新公司半年信用评价明细")
    @ApiImplicitParam(name = "zxCrHalfYearCreditEvaItem", value = "公司半年信用评价明细entity", dataType = "ZxCrHalfYearCreditEvaItem")
    @RequireToken
    @PostMapping("/updateZxCrHalfYearCreditEvaItem")
    public ResponseEntity updateZxCrHalfYearCreditEvaItem(@RequestBody(required = false) ZxCrHalfYearCreditEvaItem zxCrHalfYearCreditEvaItem) {
        return zxCrHalfYearCreditEvaItemService.updateZxCrHalfYearCreditEvaItem(zxCrHalfYearCreditEvaItem);
    }

    @ApiOperation(value="删除公司半年信用评价明细", notes="删除公司半年信用评价明细")
    @ApiImplicitParam(name = "zxCrHalfYearCreditEvaItemList", value = "公司半年信用评价明细List", dataType = "List<ZxCrHalfYearCreditEvaItem>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxCrHalfYearCreditEvaItem")
    public ResponseEntity batchDeleteUpdateZxCrHalfYearCreditEvaItem(@RequestBody(required = false) List<ZxCrHalfYearCreditEvaItem> zxCrHalfYearCreditEvaItemList) {
        return zxCrHalfYearCreditEvaItemService.batchDeleteUpdateZxCrHalfYearCreditEvaItem(zxCrHalfYearCreditEvaItemList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    
    @ApiOperation(value="左边大树形=专业化分类目录", notes="左边大树形=专业化分类目录")
    @ApiImplicitParam(name = "zxCrColCategory", value = "专业化分类目录entity", dataType = "ZxCrColCategory")
    @RequireToken
    @PostMapping("/getZxCrColCategoryTreeShu")
    public ResponseEntity getZxCrColCategoryTreeShu(@RequestBody(required = false) ZxCrColCategory zxCrColCategory) {
        return zxCrHalfYearCreditEvaItemService.getZxCrColCategoryTree(zxCrColCategory);
    }
    
    @ApiOperation(value="初期显示公司半年信用评价明细", notes="初期显示公司半年信用评价明细")
    @ApiImplicitParam(name = "zxCrHalfYearCreditEvaItem", value = "公司半年信用评价明细entity", dataType = "ZxCrHalfYearCreditEvaItem")
    @RequireToken
    @PostMapping("/getZxCrHalfYearCreditEvaItemListInit")
    public ResponseEntity getZxCrHalfYearCreditEvaItemListInit(@RequestBody(required = false) ZxCrHalfYearCreditEvaItem zxCrHalfYearCreditEvaItem) {
        return zxCrHalfYearCreditEvaItemService.getZxCrHalfYearCreditEvaItemInit(zxCrHalfYearCreditEvaItem);
    }
}
