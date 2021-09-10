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
import com.apih5.mybatis.pojo.ZxCtContrClaimItem;
import com.apih5.service.ZxCtContrClaimItemService;

@RestController
public class ZxCtContrClaimItemController {

    @Autowired(required = true)
    private ZxCtContrClaimItemService zxCtContrClaimItemService;

    @ApiOperation(value="查询业主合同管理-业主合同台账-索赔明细(原表iect_ContrClaimItem", notes="查询业主合同管理-业主合同台账-索赔明细(原表iect_ContrClaimItem")
    @ApiImplicitParam(name = "zxCtContrClaimItem", value = "业主合同管理-业主合同台账-索赔明细(原表iect_ContrClaimItementity", dataType = "ZxCtContrClaimItem")
    @RequireToken
    @PostMapping("/getZxCtContrClaimItemList")
    public ResponseEntity getZxCtContrClaimItemList(@RequestBody(required = false) ZxCtContrClaimItem zxCtContrClaimItem) {
        return zxCtContrClaimItemService.getZxCtContrClaimItemListByCondition(zxCtContrClaimItem);
    }

    @ApiOperation(value="查询详情业主合同管理-业主合同台账-索赔明细(原表iect_ContrClaimItem", notes="查询详情业主合同管理-业主合同台账-索赔明细(原表iect_ContrClaimItem")
    @ApiImplicitParam(name = "zxCtContrClaimItem", value = "业主合同管理-业主合同台账-索赔明细(原表iect_ContrClaimItementity", dataType = "ZxCtContrClaimItem")
    @RequireToken
    @PostMapping("/getZxCtContrClaimItemDetail")
    public ResponseEntity getZxCtContrClaimItemDetail(@RequestBody(required = false) ZxCtContrClaimItem zxCtContrClaimItem) {
        return zxCtContrClaimItemService.getZxCtContrClaimItemDetail(zxCtContrClaimItem);
    }

    @ApiOperation(value="新增业主合同管理-业主合同台账-索赔明细(原表iect_ContrClaimItem", notes="新增业主合同管理-业主合同台账-索赔明细(原表iect_ContrClaimItem")
    @ApiImplicitParam(name = "zxCtContrClaimItem", value = "业主合同管理-业主合同台账-索赔明细(原表iect_ContrClaimItementity", dataType = "ZxCtContrClaimItem")
    @RequireToken
    @PostMapping("/addZxCtContrClaimItem")
    public ResponseEntity addZxCtContrClaimItem(@RequestBody(required = false) ZxCtContrClaimItem zxCtContrClaimItem) {
        return zxCtContrClaimItemService.saveZxCtContrClaimItem(zxCtContrClaimItem);
    }

    @ApiOperation(value="更新业主合同管理-业主合同台账-索赔明细(原表iect_ContrClaimItem", notes="更新业主合同管理-业主合同台账-索赔明细(原表iect_ContrClaimItem")
    @ApiImplicitParam(name = "zxCtContrClaimItem", value = "业主合同管理-业主合同台账-索赔明细(原表iect_ContrClaimItementity", dataType = "ZxCtContrClaimItem")
    @RequireToken
    @PostMapping("/updateZxCtContrClaimItem")
    public ResponseEntity updateZxCtContrClaimItem(@RequestBody(required = false) ZxCtContrClaimItem zxCtContrClaimItem) {
        return zxCtContrClaimItemService.updateZxCtContrClaimItem(zxCtContrClaimItem);
    }

    @ApiOperation(value="删除业主合同管理-业主合同台账-索赔明细(原表iect_ContrClaimItem", notes="删除业主合同管理-业主合同台账-索赔明细(原表iect_ContrClaimItem")
    @ApiImplicitParam(name = "zxCtContrClaimItemList", value = "业主合同管理-业主合同台账-索赔明细(原表iect_ContrClaimItemList", dataType = "List<ZxCtContrClaimItem>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxCtContrClaimItem")
    public ResponseEntity batchDeleteUpdateZxCtContrClaimItem(@RequestBody(required = false) List<ZxCtContrClaimItem> zxCtContrClaimItemList) {
        return zxCtContrClaimItemService.batchDeleteUpdateZxCtContrClaimItem(zxCtContrClaimItemList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    
    @ApiOperation(value="删除业主合同管理-业主合同台账-索赔明细(原表iect_ContrClaimItem", notes="删除业主合同管理-业主合同台账-索赔明细(原表iect_ContrClaimItem")
    @ApiImplicitParam(name = "zxCtContrClaimItemList", value = "业主合同管理-业主合同台账-索赔明细(原表iect_ContrClaimItemList", dataType = "List<ZxCtContrClaimItem>")
    @RequireToken
    @PostMapping("/delAllZxCtContrClaimItem")
    public ResponseEntity delAllZxCtContrClaimItem(@RequestBody(required = false) ZxCtContrClaimItem zxCtContrClaimItem) {
        return zxCtContrClaimItemService.delAllZxCtContrClaimItem(zxCtContrClaimItem);
    }
}
