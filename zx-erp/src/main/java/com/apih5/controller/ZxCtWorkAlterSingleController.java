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
import com.apih5.mybatis.pojo.ZxCtWorkAlterSingle;
import com.apih5.service.ZxCtWorkAlterSingleService;

@RestController
public class ZxCtWorkAlterSingleController {

    @Autowired(required = true)
    private ZxCtWorkAlterSingleService zxCtWorkAlterSingleService;

    @ApiOperation(value="查询业主合同管理-业主合同台账-清单数变更记录(原表iecc_WorkAlterSingle", notes="查询业主合同管理-业主合同台账-清单数变更记录(原表iecc_WorkAlterSingle")
    @ApiImplicitParam(name = "zxCtWorkAlterSingle", value = "业主合同管理-业主合同台账-清单数变更记录(原表iecc_WorkAlterSingleentity", dataType = "ZxCtWorkAlterSingle")
    @RequireToken
    @PostMapping("/getZxCtWorkAlterSingleList")
    public ResponseEntity getZxCtWorkAlterSingleList(@RequestBody(required = false) ZxCtWorkAlterSingle zxCtWorkAlterSingle) {
        return zxCtWorkAlterSingleService.getZxCtWorkAlterSingleListByCondition(zxCtWorkAlterSingle);
    }

    @ApiOperation(value="查询详情业主合同管理-业主合同台账-清单数变更记录(原表iecc_WorkAlterSingle", notes="查询详情业主合同管理-业主合同台账-清单数变更记录(原表iecc_WorkAlterSingle")
    @ApiImplicitParam(name = "zxCtWorkAlterSingle", value = "业主合同管理-业主合同台账-清单数变更记录(原表iecc_WorkAlterSingleentity", dataType = "ZxCtWorkAlterSingle")
    @RequireToken
    @PostMapping("/getZxCtWorkAlterSingleDetail")
    public ResponseEntity getZxCtWorkAlterSingleDetail(@RequestBody(required = false) ZxCtWorkAlterSingle zxCtWorkAlterSingle) {
        return zxCtWorkAlterSingleService.getZxCtWorkAlterSingleDetail(zxCtWorkAlterSingle);
    }

    @ApiOperation(value="新增业主合同管理-业主合同台账-清单数变更记录(原表iecc_WorkAlterSingle", notes="新增业主合同管理-业主合同台账-清单数变更记录(原表iecc_WorkAlterSingle")
    @ApiImplicitParam(name = "zxCtWorkAlterSingle", value = "业主合同管理-业主合同台账-清单数变更记录(原表iecc_WorkAlterSingleentity", dataType = "ZxCtWorkAlterSingle")
    @RequireToken
    @PostMapping("/addZxCtWorkAlterSingle")
    public ResponseEntity addZxCtWorkAlterSingle(@RequestBody(required = false) ZxCtWorkAlterSingle zxCtWorkAlterSingle) {
        return zxCtWorkAlterSingleService.saveZxCtWorkAlterSingle(zxCtWorkAlterSingle);
    }

    @ApiOperation(value="更新业主合同管理-业主合同台账-清单数变更记录(原表iecc_WorkAlterSingle", notes="更新业主合同管理-业主合同台账-清单数变更记录(原表iecc_WorkAlterSingle")
    @ApiImplicitParam(name = "zxCtWorkAlterSingle", value = "业主合同管理-业主合同台账-清单数变更记录(原表iecc_WorkAlterSingleentity", dataType = "ZxCtWorkAlterSingle")
    @RequireToken
    @PostMapping("/updateZxCtWorkAlterSingle")
    public ResponseEntity updateZxCtWorkAlterSingle(@RequestBody(required = false) ZxCtWorkAlterSingle zxCtWorkAlterSingle) {
        return zxCtWorkAlterSingleService.updateZxCtWorkAlterSingle(zxCtWorkAlterSingle);
    }

    @ApiOperation(value="删除业主合同管理-业主合同台账-清单数变更记录(原表iecc_WorkAlterSingle", notes="删除业主合同管理-业主合同台账-清单数变更记录(原表iecc_WorkAlterSingle")
    @ApiImplicitParam(name = "zxCtWorkAlterSingleList", value = "业主合同管理-业主合同台账-清单数变更记录(原表iecc_WorkAlterSingleList", dataType = "List<ZxCtWorkAlterSingle>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxCtWorkAlterSingle")
    public ResponseEntity batchDeleteUpdateZxCtWorkAlterSingle(@RequestBody(required = false) List<ZxCtWorkAlterSingle> zxCtWorkAlterSingleList) {
        return zxCtWorkAlterSingleService.batchDeleteUpdateZxCtWorkAlterSingle(zxCtWorkAlterSingleList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    
    @ApiOperation(value="取消变更", notes="取消变更")
    @ApiImplicitParam(name = "zxCtWorkAlterSingleList", value = "业主合同管理-业主合同台账-清单数变更记录(原表iecc_WorkAlterSingleList", dataType = "List<ZxCtWorkAlterSingle>")
    @RequireToken
    @PostMapping("/zxCtCancelWorkAlterSingle")
    public ResponseEntity zxCtCancelWorkAlterSingle(@RequestBody(required = false) ZxCtWorkAlterSingle zxCtWorkAlterSingle) {
        return zxCtWorkAlterSingleService.zxCtCancelWorkAlterSingle(zxCtWorkAlterSingle);
    }
}
