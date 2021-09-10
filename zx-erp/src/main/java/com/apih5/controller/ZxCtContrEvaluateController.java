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
import com.apih5.mybatis.pojo.ZxCtContrEvaluate;
import com.apih5.service.ZxCtContrEvaluateService;

@RestController
public class ZxCtContrEvaluateController {

    @Autowired(required = true)
    private ZxCtContrEvaluateService zxCtContrEvaluateService;

    @ApiOperation(value="查询业主合同管理-业主合同台账-信用评价(原表iect_ContrEvaluate", notes="查询业主合同管理-业主合同台账-信用评价(原表iect_ContrEvaluate")
    @ApiImplicitParam(name = "zxCtContrEvaluate", value = "业主合同管理-业主合同台账-信用评价(原表iect_ContrEvaluateentity", dataType = "ZxCtContrEvaluate")
    @RequireToken
    @PostMapping("/getZxCtContrEvaluateList")
    public ResponseEntity getZxCtContrEvaluateList(@RequestBody(required = false) ZxCtContrEvaluate zxCtContrEvaluate) {
        return zxCtContrEvaluateService.getZxCtContrEvaluateListByCondition(zxCtContrEvaluate);
    }

    @ApiOperation(value="查询详情业主合同管理-业主合同台账-信用评价(原表iect_ContrEvaluate", notes="查询详情业主合同管理-业主合同台账-信用评价(原表iect_ContrEvaluate")
    @ApiImplicitParam(name = "zxCtContrEvaluate", value = "业主合同管理-业主合同台账-信用评价(原表iect_ContrEvaluateentity", dataType = "ZxCtContrEvaluate")
    @RequireToken
    @PostMapping("/getZxCtContrEvaluateDetail")
    public ResponseEntity getZxCtContrEvaluateDetail(@RequestBody(required = false) ZxCtContrEvaluate zxCtContrEvaluate) {
        return zxCtContrEvaluateService.getZxCtContrEvaluateDetail(zxCtContrEvaluate);
    }

    @ApiOperation(value="新增业主合同管理-业主合同台账-信用评价(原表iect_ContrEvaluate", notes="新增业主合同管理-业主合同台账-信用评价(原表iect_ContrEvaluate")
    @ApiImplicitParam(name = "zxCtContrEvaluate", value = "业主合同管理-业主合同台账-信用评价(原表iect_ContrEvaluateentity", dataType = "ZxCtContrEvaluate")
    @RequireToken
    @PostMapping("/addZxCtContrEvaluate")
    public ResponseEntity addZxCtContrEvaluate(@RequestBody(required = false) ZxCtContrEvaluate zxCtContrEvaluate) {
        return zxCtContrEvaluateService.saveZxCtContrEvaluate(zxCtContrEvaluate);
    }

    @ApiOperation(value="更新业主合同管理-业主合同台账-信用评价(原表iect_ContrEvaluate", notes="更新业主合同管理-业主合同台账-信用评价(原表iect_ContrEvaluate")
    @ApiImplicitParam(name = "zxCtContrEvaluate", value = "业主合同管理-业主合同台账-信用评价(原表iect_ContrEvaluateentity", dataType = "ZxCtContrEvaluate")
    @RequireToken
    @PostMapping("/updateZxCtContrEvaluate")
    public ResponseEntity updateZxCtContrEvaluate(@RequestBody(required = false) ZxCtContrEvaluate zxCtContrEvaluate) {
        return zxCtContrEvaluateService.updateZxCtContrEvaluate(zxCtContrEvaluate);
    }

    @ApiOperation(value="删除业主合同管理-业主合同台账-信用评价(原表iect_ContrEvaluate", notes="删除业主合同管理-业主合同台账-信用评价(原表iect_ContrEvaluate")
    @ApiImplicitParam(name = "zxCtContrEvaluateList", value = "业主合同管理-业主合同台账-信用评价(原表iect_ContrEvaluateList", dataType = "List<ZxCtContrEvaluate>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxCtContrEvaluate")
    public ResponseEntity batchDeleteUpdateZxCtContrEvaluate(@RequestBody(required = false) List<ZxCtContrEvaluate> zxCtContrEvaluateList) {
        return zxCtContrEvaluateService.batchDeleteUpdateZxCtContrEvaluate(zxCtContrEvaluateList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
