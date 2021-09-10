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
import com.apih5.mybatis.pojo.ZxCtSuppliesContractChange;
import com.apih5.service.ZxCtSuppliesContractChangeService;

@RestController
public class ZxCtSuppliesContractChangeController {

    @Autowired(required = true)
    private ZxCtSuppliesContractChangeService zxCtSuppliesContractChangeService;

    @ApiOperation(value="查询物资类合同变更", notes="查询物资类合同变更")
    @ApiImplicitParam(name = "zxCtSuppliesContractChange", value = "物资类合同变更entity", dataType = "ZxCtSuppliesContractChange")
    @RequireToken
    @PostMapping("/getZxCtSuppliesContractChangeList")
    public ResponseEntity getZxCtSuppliesContractChangeList(@RequestBody(required = false) ZxCtSuppliesContractChange zxCtSuppliesContractChange) {
        return zxCtSuppliesContractChangeService.getZxCtSuppliesContractChangeListByCondition(zxCtSuppliesContractChange);
    }

    @ApiOperation(value="查询详情物资类合同变更", notes="查询详情物资类合同变更")
    @ApiImplicitParam(name = "zxCtSuppliesContractChange", value = "物资类合同变更entity", dataType = "ZxCtSuppliesContractChange")
    @RequireToken
    @PostMapping("/getZxCtSuppliesContractChangeDetail")
    public ResponseEntity getZxCtSuppliesContractChangeDetail(@RequestBody(required = false) ZxCtSuppliesContractChange zxCtSuppliesContractChange) {
        return zxCtSuppliesContractChangeService.getZxCtSuppliesContractChangeDetail(zxCtSuppliesContractChange);
    }

    @ApiOperation(value="新增物资类合同变更", notes="新增物资类合同变更")
    @ApiImplicitParam(name = "zxCtSuppliesContractChange", value = "物资类合同变更entity", dataType = "ZxCtSuppliesContractChange")
    @RequireToken
    @PostMapping("/addZxCtSuppliesContractChange")
    public ResponseEntity addZxCtSuppliesContractChange(@RequestBody(required = false) ZxCtSuppliesContractChange zxCtSuppliesContractChange) {
        return zxCtSuppliesContractChangeService.saveZxCtSuppliesContractChange(zxCtSuppliesContractChange);
    }

    @ApiOperation(value="更新物资类合同变更", notes="更新物资类合同变更")
    @ApiImplicitParam(name = "zxCtSuppliesContractChange", value = "物资类合同变更entity", dataType = "ZxCtSuppliesContractChange")
    @RequireToken
    @PostMapping("/updateZxCtSuppliesContractChange")
    public ResponseEntity updateZxCtSuppliesContractChange(@RequestBody(required = false) ZxCtSuppliesContractChange zxCtSuppliesContractChange) {
        return zxCtSuppliesContractChangeService.updateZxCtSuppliesContractChange(zxCtSuppliesContractChange);
    }

    @ApiOperation(value="删除物资类合同变更", notes="删除物资类合同变更")
    @ApiImplicitParam(name = "zxCtSuppliesContractChangeList", value = "物资类合同变更List", dataType = "List<ZxCtSuppliesContractChange>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxCtSuppliesContractChange")
    public ResponseEntity batchDeleteUpdateZxCtSuppliesContractChange(@RequestBody(required = false) List<ZxCtSuppliesContractChange> zxCtSuppliesContractChangeList) {
        return zxCtSuppliesContractChangeService.batchDeleteUpdateZxCtSuppliesContractChange(zxCtSuppliesContractChangeList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
