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
import com.apih5.mybatis.pojo.ZxCtBalanceItem;
import com.apih5.service.ZxCtBalanceItemService;

@RestController
public class ZxCtBalanceItemController {

    @Autowired(required = true)
    private ZxCtBalanceItemService zxCtBalanceItemService;

    @ApiOperation(value="查询业主合同管理-业主合同台账-计量清单(原表iest_BalanceItem", notes="查询业主合同管理-业主合同台账-计量清单(原表iest_BalanceItem")
    @ApiImplicitParam(name = "zxCtBalanceItem", value = "业主合同管理-业主合同台账-计量清单(原表iest_BalanceItementity", dataType = "ZxCtBalanceItem")
    @RequireToken
    @PostMapping("/getZxCtBalanceItemList")
    public ResponseEntity getZxCtBalanceItemList(@RequestBody(required = false) ZxCtBalanceItem zxCtBalanceItem) {
        return zxCtBalanceItemService.getZxCtBalanceItemListByCondition(zxCtBalanceItem);
    }

    @ApiOperation(value="查询详情业主合同管理-业主合同台账-计量清单(原表iest_BalanceItem", notes="查询详情业主合同管理-业主合同台账-计量清单(原表iest_BalanceItem")
    @ApiImplicitParam(name = "zxCtBalanceItem", value = "业主合同管理-业主合同台账-计量清单(原表iest_BalanceItementity", dataType = "ZxCtBalanceItem")
    @RequireToken
    @PostMapping("/getZxCtBalanceItemDetail")
    public ResponseEntity getZxCtBalanceItemDetail(@RequestBody(required = false) ZxCtBalanceItem zxCtBalanceItem) {
        return zxCtBalanceItemService.getZxCtBalanceItemDetail(zxCtBalanceItem);
    }

    @ApiOperation(value="新增业主合同管理-业主合同台账-计量清单(原表iest_BalanceItem", notes="新增业主合同管理-业主合同台账-计量清单(原表iest_BalanceItem")
    @ApiImplicitParam(name = "zxCtBalanceItem", value = "业主合同管理-业主合同台账-计量清单(原表iest_BalanceItementity", dataType = "ZxCtBalanceItem")
    @RequireToken
    @PostMapping("/addZxCtBalanceItem")
    public ResponseEntity addZxCtBalanceItem(@RequestBody(required = false) ZxCtBalanceItem zxCtBalanceItem) {
        return zxCtBalanceItemService.saveZxCtBalanceItem(zxCtBalanceItem);
    }

    @ApiOperation(value="更新业主合同管理-业主合同台账-计量清单(原表iest_BalanceItem", notes="更新业主合同管理-业主合同台账-计量清单(原表iest_BalanceItem")
    @ApiImplicitParam(name = "zxCtBalanceItem", value = "业主合同管理-业主合同台账-计量清单(原表iest_BalanceItementity", dataType = "ZxCtBalanceItem")
    @RequireToken
    @PostMapping("/updateZxCtBalanceItem")
    public ResponseEntity updateZxCtBalanceItem(@RequestBody(required = false) ZxCtBalanceItem zxCtBalanceItem) {
        return zxCtBalanceItemService.updateZxCtBalanceItem(zxCtBalanceItem);
    }

    @ApiOperation(value="删除业主合同管理-业主合同台账-计量清单(原表iest_BalanceItem", notes="删除业主合同管理-业主合同台账-计量清单(原表iest_BalanceItem")
    @ApiImplicitParam(name = "zxCtBalanceItemList", value = "业主合同管理-业主合同台账-计量清单(原表iest_BalanceItemList", dataType = "List<ZxCtBalanceItem>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxCtBalanceItem")
    public ResponseEntity batchDeleteUpdateZxCtBalanceItem(@RequestBody(required = false) List<ZxCtBalanceItem> zxCtBalanceItemList) {
        return zxCtBalanceItemService.batchDeleteUpdateZxCtBalanceItem(zxCtBalanceItemList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    

    @ApiOperation(value="更新业主合同管理-业主合同台账-计量清单(原表iest_BalanceItem", notes="更新业主合同管理-业主合同台账-计量清单(原表iest_BalanceItem")
    @ApiImplicitParam(name = "zxCtBalanceItem", value = "业主合同管理-业主合同台账-计量清单(原表iest_BalanceItementity", dataType = "ZxCtBalanceItem")
    @RequireToken
    @PostMapping("/updateZxCtBalanceItemById")
    public ResponseEntity updateZxCtBalanceItemById(@RequestBody(required = false) ZxCtBalanceItem zxCtBalanceItem) {
        return zxCtBalanceItemService.updateZxCtBalanceItemById(zxCtBalanceItem);
    }
}
