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
import com.apih5.mybatis.pojo.ZxCtFsContractBond;
import com.apih5.service.ZxCtFsContractBondService;

@RestController
public class ZxCtFsContractBondController {

    @Autowired(required = true)
    private ZxCtFsContractBondService zxCtFsContractBondService;

    @ApiOperation(value="查询附属合同保证金", notes="查询附属合同保证金")
    @ApiImplicitParam(name = "zxCtFsContractBond", value = "附属合同保证金entity", dataType = "ZxCtFsContractBond")
    @RequireToken
    @PostMapping("/getZxCtFsContractBondList")
    public ResponseEntity getZxCtFsContractBondList(@RequestBody(required = false) ZxCtFsContractBond zxCtFsContractBond) {
        return zxCtFsContractBondService.getZxCtFsContractBondListByCondition(zxCtFsContractBond);
    }

    @ApiOperation(value="查询详情附属合同保证金", notes="查询详情附属合同保证金")
    @ApiImplicitParam(name = "zxCtFsContractBond", value = "附属合同保证金entity", dataType = "ZxCtFsContractBond")
    @RequireToken
    @PostMapping("/getZxCtFsContractBondDetail")
    public ResponseEntity getZxCtFsContractBondDetail(@RequestBody(required = false) ZxCtFsContractBond zxCtFsContractBond) {
        return zxCtFsContractBondService.getZxCtFsContractBondDetail(zxCtFsContractBond);
    }

    @ApiOperation(value="新增附属合同保证金", notes="新增附属合同保证金")
    @ApiImplicitParam(name = "zxCtFsContractBond", value = "附属合同保证金entity", dataType = "ZxCtFsContractBond")
    @RequireToken
    @PostMapping("/addZxCtFsContractBond")
    public ResponseEntity addZxCtFsContractBond(@RequestBody(required = false) ZxCtFsContractBond zxCtFsContractBond) {
        return zxCtFsContractBondService.saveZxCtFsContractBond(zxCtFsContractBond);
    }

    @ApiOperation(value="更新附属合同保证金", notes="更新附属合同保证金")
    @ApiImplicitParam(name = "zxCtFsContractBond", value = "附属合同保证金entity", dataType = "ZxCtFsContractBond")
    @RequireToken
    @PostMapping("/updateZxCtFsContractBond")
    public ResponseEntity updateZxCtFsContractBond(@RequestBody(required = false) ZxCtFsContractBond zxCtFsContractBond) {
        return zxCtFsContractBondService.updateZxCtFsContractBond(zxCtFsContractBond);
    }

    @ApiOperation(value="删除附属合同保证金", notes="删除附属合同保证金")
    @ApiImplicitParam(name = "zxCtFsContractBondList", value = "附属合同保证金List", dataType = "List<ZxCtFsContractBond>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxCtFsContractBond")
    public ResponseEntity batchDeleteUpdateZxCtFsContractBond(@RequestBody(required = false) List<ZxCtFsContractBond> zxCtFsContractBondList) {
        return zxCtFsContractBondService.batchDeleteUpdateZxCtFsContractBond(zxCtFsContractBondList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
