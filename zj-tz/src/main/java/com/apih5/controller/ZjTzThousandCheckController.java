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
import com.apih5.mybatis.pojo.ZjTzThousandCheck;
import com.apih5.service.ZjTzThousandCheckService;

@RestController
public class ZjTzThousandCheckController {

    @Autowired(required = true)
    private ZjTzThousandCheckService zjTzThousandCheckService;

    @ApiOperation(value="查询千分制检查", notes="查询千分制检查")
    @ApiImplicitParam(name = "zjTzThousandCheck", value = "千分制检查entity", dataType = "ZjTzThousandCheck")
    @RequireToken
    @PostMapping("/getZjTzThousandCheckList")
    public ResponseEntity getZjTzThousandCheckList(@RequestBody(required = false) ZjTzThousandCheck zjTzThousandCheck) {
        return zjTzThousandCheckService.getZjTzThousandCheckListByCondition(zjTzThousandCheck);
    }

    @ApiOperation(value="查询详情千分制检查", notes="查询详情千分制检查")
    @ApiImplicitParam(name = "zjTzThousandCheck", value = "千分制检查entity", dataType = "ZjTzThousandCheck")
    @RequireToken
    @PostMapping("/getZjTzThousandCheckDetails")
    public ResponseEntity getZjTzThousandCheckDetails(@RequestBody(required = false) ZjTzThousandCheck zjTzThousandCheck) {
        return zjTzThousandCheckService.getZjTzThousandCheckDetails(zjTzThousandCheck);
    }

    @ApiOperation(value="新增千分制检查", notes="新增千分制检查")
    @ApiImplicitParam(name = "zjTzThousandCheck", value = "千分制检查entity", dataType = "ZjTzThousandCheck")
    @RequireToken
    @PostMapping("/addZjTzThousandCheck")
    public ResponseEntity addZjTzThousandCheck(@RequestBody(required = false) ZjTzThousandCheck zjTzThousandCheck) {
        return zjTzThousandCheckService.saveZjTzThousandCheck(zjTzThousandCheck);
    }

    @ApiOperation(value="更新千分制检查", notes="更新千分制检查")
    @ApiImplicitParam(name = "zjTzThousandCheck", value = "千分制检查entity", dataType = "ZjTzThousandCheck")
    @RequireToken
    @PostMapping("/updateZjTzThousandCheck")
    public ResponseEntity updateZjTzThousandCheck(@RequestBody(required = false) ZjTzThousandCheck zjTzThousandCheck) {
        return zjTzThousandCheckService.updateZjTzThousandCheck(zjTzThousandCheck);
    }

    @ApiOperation(value="删除千分制检查", notes="删除千分制检查")
    @ApiImplicitParam(name = "zjTzThousandCheckList", value = "千分制检查List", dataType = "List<ZjTzThousandCheck>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZjTzThousandCheck")
    public ResponseEntity batchDeleteUpdateZjTzThousandCheck(@RequestBody(required = false) List<ZjTzThousandCheck> zjTzThousandCheckList) {
        return zjTzThousandCheckService.batchDeleteUpdateZjTzThousandCheck(zjTzThousandCheckList);
    }
    
    @ApiOperation(value="批量下达千分制检查", notes="批量下达千分制检查")
    @ApiImplicitParam(name = "zjTzThousandCheckList", value = "千分制检查List", dataType = "List<ZjTzThousandCheck>")
    @RequireToken
    @PostMapping("/batchReleaseZjTzThousandCheck")
    public ResponseEntity batchReleaseZjTzThousandCheck(@RequestBody(required = false) List<ZjTzThousandCheck> zjTzThousandCheckList) {
        return zjTzThousandCheckService.batchReleaseZjTzThousandCheck(zjTzThousandCheckList);
    }
    
    @ApiOperation(value="批量撤回千分制检查", notes="批量撤回千分制检查")
    @ApiImplicitParam(name = "zjTzThousandCheckList", value = "千分制检查List", dataType = "List<ZjTzThousandCheck>")
    @RequireToken
    @PostMapping("/batchRecallZjTzThousandCheck")
    public ResponseEntity batchRecallZjTzThousandCheck(@RequestBody(required = false) List<ZjTzThousandCheck> zjTzThousandCheckList) {
        return zjTzThousandCheckService.batchRecallZjTzThousandCheck(zjTzThousandCheckList);
    }
    
    @ApiOperation(value="批量导出千分制检查附件", notes="批量导出千分制检查附件")
    @ApiImplicitParam(name = "zjTzThousandCheckList", value = "千分制检查List", dataType = "List<ZjTzThousandCheck>")
    @RequireToken
    @PostMapping("/batchExportZjTzThousandCheckFile")
    public ResponseEntity batchExportZjTzThousandCheckFile(@RequestBody(required = false) List<ZjTzThousandCheck> zjTzThousandCheckList) {
        return zjTzThousandCheckService.batchExportZjTzThousandCheckFile(zjTzThousandCheckList);
    }
    
    @ApiOperation(value="保存千分制检查所有扣分的接口", notes="保存千分制检查所有扣分的接口")
    @ApiImplicitParam(name = "zjTzThousandCheck", value = "千分制检查entity", dataType = "ZjTzThousandCheck")
    @RequireToken
    @PostMapping("/saveZjTzThousandCheckAllDeduct")
    public ResponseEntity saveZjTzThousandCheckAllDeduct(@RequestBody(required = false) ZjTzThousandCheck zjTzThousandCheck) {
        return zjTzThousandCheckService.saveZjTzThousandCheckAllDeduct(zjTzThousandCheck);
    }

}
