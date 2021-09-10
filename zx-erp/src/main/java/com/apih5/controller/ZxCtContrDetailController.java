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
import com.apih5.mybatis.pojo.ZxCtContrDetail;
import com.apih5.service.ZxCtContrDetailService;

@RestController
public class ZxCtContrDetailController {

    @Autowired(required = true)
    private ZxCtContrDetailService zxCtContrDetailService;

    @ApiOperation(value="查询业主合同管理-业主合同台账-保证金主表（原表iect_YgjContrDetail）", notes="查询业主合同管理-业主合同台账-保证金主表（原表iect_YgjContrDetail）")
    @ApiImplicitParam(name = "zxCtContrDetail", value = "业主合同管理-业主合同台账-保证金主表（原表iect_YgjContrDetail）entity", dataType = "ZxCtContrDetail")
    @RequireToken
    @PostMapping("/getZxCtContrDetailList")
    public ResponseEntity getZxCtContrDetailList(@RequestBody(required = false) ZxCtContrDetail zxCtContrDetail) {
        return zxCtContrDetailService.getZxCtContrDetailListByCondition(zxCtContrDetail);
    }

    @ApiOperation(value="查询详情业主合同管理-业主合同台账-保证金主表（原表iect_YgjContrDetail）", notes="查询详情业主合同管理-业主合同台账-保证金主表（原表iect_YgjContrDetail）")
    @ApiImplicitParam(name = "zxCtContrDetail", value = "业主合同管理-业主合同台账-保证金主表（原表iect_YgjContrDetail）entity", dataType = "ZxCtContrDetail")
    @RequireToken
    @PostMapping("/getZxCtContrDetailDetail")
    public ResponseEntity getZxCtContrDetailDetail(@RequestBody(required = false) ZxCtContrDetail zxCtContrDetail) {
        return zxCtContrDetailService.getZxCtContrDetailDetail(zxCtContrDetail);
    }

    @ApiOperation(value="新增业主合同管理-业主合同台账-保证金主表（原表iect_YgjContrDetail）", notes="新增业主合同管理-业主合同台账-保证金主表（原表iect_YgjContrDetail）")
    @ApiImplicitParam(name = "zxCtContrDetail", value = "业主合同管理-业主合同台账-保证金主表（原表iect_YgjContrDetail）entity", dataType = "ZxCtContrDetail")
    @RequireToken
    @PostMapping("/addZxCtContrDetail")
    public ResponseEntity addZxCtContrDetail(@RequestBody(required = false) ZxCtContrDetail zxCtContrDetail) {
        return zxCtContrDetailService.saveZxCtContrDetail(zxCtContrDetail);
    }

    @ApiOperation(value="更新业主合同管理-业主合同台账-保证金主表（原表iect_YgjContrDetail）", notes="更新业主合同管理-业主合同台账-保证金主表（原表iect_YgjContrDetail）")
    @ApiImplicitParam(name = "zxCtContrDetail", value = "业主合同管理-业主合同台账-保证金主表（原表iect_YgjContrDetail）entity", dataType = "ZxCtContrDetail")
    @RequireToken
    @PostMapping("/updateZxCtContrDetail")
    public ResponseEntity updateZxCtContrDetail(@RequestBody(required = false) ZxCtContrDetail zxCtContrDetail) {
        return zxCtContrDetailService.updateZxCtContrDetail(zxCtContrDetail);
    }

    @ApiOperation(value="删除业主合同管理-业主合同台账-保证金主表（原表iect_YgjContrDetail）", notes="删除业主合同管理-业主合同台账-保证金主表（原表iect_YgjContrDetail）")
    @ApiImplicitParam(name = "zxCtContrDetailList", value = "业主合同管理-业主合同台账-保证金主表（原表iect_YgjContrDetail）List", dataType = "List<ZxCtContrDetail>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxCtContrDetail")
    public ResponseEntity batchDeleteUpdateZxCtContrDetail(@RequestBody(required = false) List<ZxCtContrDetail> zxCtContrDetailList) {
        return zxCtContrDetailService.batchDeleteUpdateZxCtContrDetail(zxCtContrDetailList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
