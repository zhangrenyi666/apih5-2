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
import com.apih5.mybatis.pojo.ZxCtSuppliesContractChangeDetail;
import com.apih5.service.ZxCtSuppliesContractChangeDetailService;

@RestController
public class ZxCtSuppliesContractChangeDetailController {

    @Autowired(required = true)
    private ZxCtSuppliesContractChangeDetailService zxCtSuppliesContractChangeDetailService;

    @ApiOperation(value="查询物资管理类合同变更明细", notes="查询物资管理类合同变更明细")
    @ApiImplicitParam(name = "zxCtSuppliesContractChangeDetail", value = "物资管理类合同变更明细entity", dataType = "ZxCtSuppliesContractChangeDetail")
    @RequireToken
    @PostMapping("/getZxCtSuppliesContractChangeDetailList")
    public ResponseEntity getZxCtSuppliesContractChangeDetailList(@RequestBody(required = false) ZxCtSuppliesContractChangeDetail zxCtSuppliesContractChangeDetail) {
        return zxCtSuppliesContractChangeDetailService.getZxCtSuppliesContractChangeDetailListByCondition(zxCtSuppliesContractChangeDetail);
    }

    @ApiOperation(value="查询详情物资管理类合同变更明细", notes="查询详情物资管理类合同变更明细")
    @ApiImplicitParam(name = "zxCtSuppliesContractChangeDetail", value = "物资管理类合同变更明细entity", dataType = "ZxCtSuppliesContractChangeDetail")
    @RequireToken
    @PostMapping("/getZxCtSuppliesContractChangeDetailDetail")
    public ResponseEntity getZxCtSuppliesContractChangeDetailDetail(@RequestBody(required = false) ZxCtSuppliesContractChangeDetail zxCtSuppliesContractChangeDetail) {
        return zxCtSuppliesContractChangeDetailService.getZxCtSuppliesContractChangeDetailDetail(zxCtSuppliesContractChangeDetail);
    }

    @ApiOperation(value="新增物资管理类合同变更明细", notes="新增物资管理类合同变更明细")
    @ApiImplicitParam(name = "zxCtSuppliesContractChangeDetail", value = "物资管理类合同变更明细entity", dataType = "ZxCtSuppliesContractChangeDetail")
    @RequireToken
    @PostMapping("/addZxCtSuppliesContractChangeDetail")
    public ResponseEntity addZxCtSuppliesContractChangeDetail(@RequestBody(required = false) ZxCtSuppliesContractChangeDetail zxCtSuppliesContractChangeDetail) {
        return zxCtSuppliesContractChangeDetailService.saveZxCtSuppliesContractChangeDetail(zxCtSuppliesContractChangeDetail);
    }

    @ApiOperation(value="更新物资管理类合同变更明细", notes="更新物资管理类合同变更明细")
    @ApiImplicitParam(name = "zxCtSuppliesContractChangeDetail", value = "物资管理类合同变更明细entity", dataType = "ZxCtSuppliesContractChangeDetail")
    @RequireToken
    @PostMapping("/updateZxCtSuppliesContractChangeDetail")
    public ResponseEntity updateZxCtSuppliesContractChangeDetail(@RequestBody(required = false) ZxCtSuppliesContractChangeDetail zxCtSuppliesContractChangeDetail) {
        return zxCtSuppliesContractChangeDetailService.updateZxCtSuppliesContractChangeDetail(zxCtSuppliesContractChangeDetail);
    }

    @ApiOperation(value="删除物资管理类合同变更明细", notes="删除物资管理类合同变更明细")
    @ApiImplicitParam(name = "zxCtSuppliesContractChangeDetailList", value = "物资管理类合同变更明细List", dataType = "List<ZxCtSuppliesContractChangeDetail>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxCtSuppliesContractChangeDetail")
    public ResponseEntity batchDeleteUpdateZxCtSuppliesContractChangeDetail(@RequestBody(required = false) List<ZxCtSuppliesContractChangeDetail> zxCtSuppliesContractChangeDetailList) {
        return zxCtSuppliesContractChangeDetailService.batchDeleteUpdateZxCtSuppliesContractChangeDetail(zxCtSuppliesContractChangeDetailList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
