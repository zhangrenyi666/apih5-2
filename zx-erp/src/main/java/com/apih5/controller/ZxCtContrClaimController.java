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
import com.apih5.mybatis.pojo.ZxCtContrClaim;
import com.apih5.service.ZxCtContrClaimService;

@RestController
public class ZxCtContrClaimController {

    @Autowired(required = true)
    private ZxCtContrClaimService zxCtContrClaimService;

    @ApiOperation(value="查询业主合同管理-业主合同台账-索赔(原表iect_ContrClaim", notes="查询业主合同管理-业主合同台账-索赔(原表iect_ContrClaim")
    @ApiImplicitParam(name = "zxCtContrClaim", value = "业主合同管理-业主合同台账-索赔(原表iect_ContrClaimentity", dataType = "ZxCtContrClaim")
    @RequireToken
    @PostMapping("/getZxCtContrClaimList")
    public ResponseEntity getZxCtContrClaimList(@RequestBody(required = false) ZxCtContrClaim zxCtContrClaim) {
        return zxCtContrClaimService.getZxCtContrClaimListByCondition(zxCtContrClaim);
    }

    @ApiOperation(value="查询详情业主合同管理-业主合同台账-索赔(原表iect_ContrClaim", notes="查询详情业主合同管理-业主合同台账-索赔(原表iect_ContrClaim")
    @ApiImplicitParam(name = "zxCtContrClaim", value = "业主合同管理-业主合同台账-索赔(原表iect_ContrClaimentity", dataType = "ZxCtContrClaim")
    @RequireToken
    @PostMapping("/getZxCtContrClaimDetail")
    public ResponseEntity getZxCtContrClaimDetail(@RequestBody(required = false) ZxCtContrClaim zxCtContrClaim) {
        return zxCtContrClaimService.getZxCtContrClaimDetail(zxCtContrClaim);
    }

    @ApiOperation(value="新增业主合同管理-业主合同台账-索赔(原表iect_ContrClaim", notes="新增业主合同管理-业主合同台账-索赔(原表iect_ContrClaim")
    @ApiImplicitParam(name = "zxCtContrClaim", value = "业主合同管理-业主合同台账-索赔(原表iect_ContrClaimentity", dataType = "ZxCtContrClaim")
    @RequireToken
    @PostMapping("/addZxCtContrClaim")
    public ResponseEntity addZxCtContrClaim(@RequestBody(required = false) ZxCtContrClaim zxCtContrClaim) {
        return zxCtContrClaimService.saveZxCtContrClaim(zxCtContrClaim);
    }

    @ApiOperation(value="更新业主合同管理-业主合同台账-索赔(原表iect_ContrClaim", notes="更新业主合同管理-业主合同台账-索赔(原表iect_ContrClaim")
    @ApiImplicitParam(name = "zxCtContrClaim", value = "业主合同管理-业主合同台账-索赔(原表iect_ContrClaimentity", dataType = "ZxCtContrClaim")
    @RequireToken
    @PostMapping("/updateZxCtContrClaim")
    public ResponseEntity updateZxCtContrClaim(@RequestBody(required = false) ZxCtContrClaim zxCtContrClaim) {
        return zxCtContrClaimService.updateZxCtContrClaim(zxCtContrClaim);
    }

    @ApiOperation(value="删除业主合同管理-业主合同台账-索赔(原表iect_ContrClaim", notes="删除业主合同管理-业主合同台账-索赔(原表iect_ContrClaim")
    @ApiImplicitParam(name = "zxCtContrClaimList", value = "业主合同管理-业主合同台账-索赔(原表iect_ContrClaimList", dataType = "List<ZxCtContrClaim>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxCtContrClaim")
    public ResponseEntity batchDeleteUpdateZxCtContrClaim(@RequestBody(required = false) List<ZxCtContrClaim> zxCtContrClaimList) {
        return zxCtContrClaimService.batchDeleteUpdateZxCtContrClaim(zxCtContrClaimList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
