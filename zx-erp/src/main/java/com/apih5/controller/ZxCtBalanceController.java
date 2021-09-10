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
import com.apih5.mybatis.pojo.ZxCtBalance;
import com.apih5.service.ZxCtBalanceService;

@RestController
public class ZxCtBalanceController {

    @Autowired(required = true)
    private ZxCtBalanceService zxCtBalanceService;

    @ApiOperation(value="查询业主合同管理-业主合同台账-计量(原表iest_iest_Balance", notes="查询业主合同管理-业主合同台账-计量(原表iest_iest_Balance")
    @ApiImplicitParam(name = "zxCtBalance", value = "业主合同管理-业主合同台账-计量(原表iest_iest_Balanceentity", dataType = "ZxCtBalance")
    @RequireToken
    @PostMapping("/getZxCtBalanceList")
    public ResponseEntity getZxCtBalanceList(@RequestBody(required = false) ZxCtBalance zxCtBalance) {
        return zxCtBalanceService.getZxCtBalanceListByCondition(zxCtBalance);
    }

    @ApiOperation(value="查询详情业主合同管理-业主合同台账-计量(原表iest_iest_Balance", notes="查询详情业主合同管理-业主合同台账-计量(原表iest_iest_Balance")
    @ApiImplicitParam(name = "zxCtBalance", value = "业主合同管理-业主合同台账-计量(原表iest_iest_Balanceentity", dataType = "ZxCtBalance")
    @RequireToken
    @PostMapping("/getZxCtBalanceDetail")
    public ResponseEntity getZxCtBalanceDetail(@RequestBody(required = false) ZxCtBalance zxCtBalance) {
        return zxCtBalanceService.getZxCtBalanceDetail(zxCtBalance);
    }

    @ApiOperation(value="新增业主合同管理-业主合同台账-计量(原表iest_iest_Balance", notes="新增业主合同管理-业主合同台账-计量(原表iest_iest_Balance")
    @ApiImplicitParam(name = "zxCtBalance", value = "业主合同管理-业主合同台账-计量(原表iest_iest_Balanceentity", dataType = "ZxCtBalance")
    @RequireToken
    @PostMapping("/addZxCtBalance")
    public ResponseEntity addZxCtBalance(@RequestBody(required = false) ZxCtBalance zxCtBalance) {
        return zxCtBalanceService.saveZxCtBalance(zxCtBalance);
    }

    @ApiOperation(value="更新业主合同管理-业主合同台账-计量(原表iest_iest_Balance", notes="更新业主合同管理-业主合同台账-计量(原表iest_iest_Balance")
    @ApiImplicitParam(name = "zxCtBalance", value = "业主合同管理-业主合同台账-计量(原表iest_iest_Balanceentity", dataType = "ZxCtBalance")
    @RequireToken
    @PostMapping("/updateZxCtBalance")
    public ResponseEntity updateZxCtBalance(@RequestBody(required = false) ZxCtBalance zxCtBalance) {
        return zxCtBalanceService.updateZxCtBalance(zxCtBalance);
    }

    @ApiOperation(value="删除业主合同管理-业主合同台账-计量(原表iest_iest_Balance", notes="删除业主合同管理-业主合同台账-计量(原表iest_iest_Balance")
    @ApiImplicitParam(name = "zxCtBalanceList", value = "业主合同管理-业主合同台账-计量(原表iest_iest_BalanceList", dataType = "List<ZxCtBalance>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxCtBalance")
    public ResponseEntity batchDeleteUpdateZxCtBalance(@RequestBody(required = false) List<ZxCtBalance> zxCtBalanceList) {
        return zxCtBalanceService.batchDeleteUpdateZxCtBalance(zxCtBalanceList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

    @ApiOperation(value="计量审核（反审核）", notes="计量审核（反审核）")
    @ApiImplicitParam(name = "zxCtBalanceList", value = "业主合同管理-业主合同台账-计量(原表iest_iest_BalanceList", dataType = "List<ZxCtBalance>")
    @RequireToken
    @PostMapping("/zxCtBalanceAudit")
    public ResponseEntity zxCtBalanceAudit(@RequestBody(required = false) ZxCtBalance zxCtBalance) {
        return zxCtBalanceService.zxCtBalanceAudit(zxCtBalance);
    }
}
