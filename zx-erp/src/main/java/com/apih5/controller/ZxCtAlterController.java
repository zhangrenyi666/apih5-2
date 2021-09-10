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
import com.apih5.mybatis.pojo.ZxCtAlter;
import com.apih5.service.ZxCtAlterService;

@RestController
public class ZxCtAlterController {

    @Autowired(required = true)
    private ZxCtAlterService zxCtAlterService;

    @ApiOperation(value="查询业主合同管理-业主合同台账-合同变更(原表iecc_Alter", notes="查询业主合同管理-业主合同台账-合同变更(原表iecc_Alter")
    @ApiImplicitParam(name = "zxCtAlter", value = "业主合同管理-业主合同台账-合同变更(原表iecc_Alterentity", dataType = "ZxCtAlter")
    @RequireToken
    @PostMapping("/getZxCtAlterList")
    public ResponseEntity getZxCtAlterList(@RequestBody(required = false) ZxCtAlter zxCtAlter) {
        return zxCtAlterService.getZxCtAlterListByCondition(zxCtAlter);
    }

    @ApiOperation(value="查询详情业主合同管理-业主合同台账-合同变更(原表iecc_Alter", notes="查询详情业主合同管理-业主合同台账-合同变更(原表iecc_Alter")
    @ApiImplicitParam(name = "zxCtAlter", value = "业主合同管理-业主合同台账-合同变更(原表iecc_Alterentity", dataType = "ZxCtAlter")
    @RequireToken
    @PostMapping("/getZxCtAlterDetail")
    public ResponseEntity getZxCtAlterDetail(@RequestBody(required = false) ZxCtAlter zxCtAlter) {
        return zxCtAlterService.getZxCtAlterDetail(zxCtAlter);
    }

    @ApiOperation(value="新增业主合同管理-业主合同台账-合同变更(原表iecc_Alter", notes="新增业主合同管理-业主合同台账-合同变更(原表iecc_Alter")
    @ApiImplicitParam(name = "zxCtAlter", value = "业主合同管理-业主合同台账-合同变更(原表iecc_Alterentity", dataType = "ZxCtAlter")
    @RequireToken
    @PostMapping("/addZxCtAlter")
    public ResponseEntity addZxCtAlter(@RequestBody(required = false) ZxCtAlter zxCtAlter) {
        return zxCtAlterService.saveZxCtAlter(zxCtAlter);
    }

    @ApiOperation(value="更新业主合同管理-业主合同台账-合同变更(原表iecc_Alter", notes="更新业主合同管理-业主合同台账-合同变更(原表iecc_Alter")
    @ApiImplicitParam(name = "zxCtAlter", value = "业主合同管理-业主合同台账-合同变更(原表iecc_Alterentity", dataType = "ZxCtAlter")
    @RequireToken
    @PostMapping("/updateZxCtAlter")
    public ResponseEntity updateZxCtAlter(@RequestBody(required = false) ZxCtAlter zxCtAlter) {
        return zxCtAlterService.updateZxCtAlter(zxCtAlter);
    }

    @ApiOperation(value="删除业主合同管理-业主合同台账-合同变更(原表iecc_Alter", notes="删除业主合同管理-业主合同台账-合同变更(原表iecc_Alter")
    @ApiImplicitParam(name = "zxCtAlterList", value = "业主合同管理-业主合同台账-合同变更(原表iecc_AlterList", dataType = "List<ZxCtAlter>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxCtAlter")
    public ResponseEntity batchDeleteUpdateZxCtAlter(@RequestBody(required = false) List<ZxCtAlter> zxCtAlterList) {
        return zxCtAlterService.batchDeleteUpdateZxCtAlter(zxCtAlterList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    
    @ApiOperation(value="审核-业主合同管理-业主合同台账-合同变更(原表iecc_Alter", notes="删除业主合同管理-业主合同台账-合同变更(原表iecc_Alter")
    @ApiImplicitParam(name = "zxCtAlterList", value = "业主合同管理-业主合同台账-合同变更(原表iecc_AlterList", dataType = "List<ZxCtAlter>")
    @RequireToken
    @PostMapping("/zxCtAlterAudit")
    public ResponseEntity zxCtAlterAudit(@RequestBody(required = false) ZxCtAlter zxCtAlter) {
        return zxCtAlterService.zxCtAlterAudit(zxCtAlter);
    }
    
    @ApiOperation(value="上报-业主合同管理-业主合同台账-合同变更(原表iecc_Alter", notes="删除业主合同管理-业主合同台账-合同变更(原表iecc_Alter")
    @ApiImplicitParam(name = "zxCtAlterList", value = "业主合同管理-业主合同台账-合同变更(原表iecc_AlterList", dataType = "List<ZxCtAlter>")
    @RequireToken
    @PostMapping("/zxCtAlterReporting")
    public ResponseEntity zxCtAlterReporting(@RequestBody(required = false) ZxCtAlter zxCtAlter) {
    	return zxCtAlterService.zxCtAlterReporting(zxCtAlter);
    }
}
