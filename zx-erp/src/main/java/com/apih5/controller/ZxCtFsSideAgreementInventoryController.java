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
import com.apih5.mybatis.pojo.ZxCtFsSideAgreementInventory;
import com.apih5.service.ZxCtFsSideAgreementInventoryService;

@RestController
public class ZxCtFsSideAgreementInventoryController {

    @Autowired(required = true)
    private ZxCtFsSideAgreementInventoryService zxCtFsSideAgreementInventoryService;

    @ApiOperation(value="查询附属合同补充协议清单", notes="查询附属合同补充协议清单")
    @ApiImplicitParam(name = "zxCtFsSideAgreementInventory", value = "附属合同补充协议清单entity", dataType = "ZxCtFsSideAgreementInventory")
    @RequireToken
    @PostMapping("/getZxCtFsSideAgreementInventoryList")
    public ResponseEntity getZxCtFsSideAgreementInventoryList(@RequestBody(required = false) ZxCtFsSideAgreementInventory zxCtFsSideAgreementInventory) {
        return zxCtFsSideAgreementInventoryService.getZxCtFsSideAgreementInventoryListByCondition(zxCtFsSideAgreementInventory);
    }

    @ApiOperation(value="查询详情附属合同补充协议清单", notes="查询详情附属合同补充协议清单")
    @ApiImplicitParam(name = "zxCtFsSideAgreementInventory", value = "附属合同补充协议清单entity", dataType = "ZxCtFsSideAgreementInventory")
    @RequireToken
    @PostMapping("/getZxCtFsSideAgreementInventoryDetail")
    public ResponseEntity getZxCtFsSideAgreementInventoryDetail(@RequestBody(required = false) ZxCtFsSideAgreementInventory zxCtFsSideAgreementInventory) {
        return zxCtFsSideAgreementInventoryService.getZxCtFsSideAgreementInventoryDetail(zxCtFsSideAgreementInventory);
    }

    @ApiOperation(value="新增附属合同补充协议清单", notes="新增附属合同补充协议清单")
    @ApiImplicitParam(name = "zxCtFsSideAgreementInventory", value = "附属合同补充协议清单entity", dataType = "ZxCtFsSideAgreementInventory")
    @RequireToken
    @PostMapping("/addZxCtFsSideAgreementInventory")
    public ResponseEntity addZxCtFsSideAgreementInventory(@RequestBody(required = false) ZxCtFsSideAgreementInventory zxCtFsSideAgreementInventory) {
        return zxCtFsSideAgreementInventoryService.saveZxCtFsSideAgreementInventory(zxCtFsSideAgreementInventory);
    }

    @ApiOperation(value="更新附属合同补充协议清单", notes="更新附属合同补充协议清单")
    @ApiImplicitParam(name = "zxCtFsSideAgreementInventory", value = "附属合同补充协议清单entity", dataType = "ZxCtFsSideAgreementInventory")
    @RequireToken
    @PostMapping("/updateZxCtFsSideAgreementInventory")
    public ResponseEntity updateZxCtFsSideAgreementInventory(@RequestBody(required = false) ZxCtFsSideAgreementInventory zxCtFsSideAgreementInventory) {
        return zxCtFsSideAgreementInventoryService.updateZxCtFsSideAgreementInventory(zxCtFsSideAgreementInventory);
    }

    @ApiOperation(value="删除附属合同补充协议清单", notes="删除附属合同补充协议清单")
    @ApiImplicitParam(name = "zxCtFsSideAgreementInventoryList", value = "附属合同补充协议清单List", dataType = "List<ZxCtFsSideAgreementInventory>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxCtFsSideAgreementInventory")
    public ResponseEntity batchDeleteUpdateZxCtFsSideAgreementInventory(@RequestBody(required = false) List<ZxCtFsSideAgreementInventory> zxCtFsSideAgreementInventoryList) {
        return zxCtFsSideAgreementInventoryService.batchDeleteUpdateZxCtFsSideAgreementInventory(zxCtFsSideAgreementInventoryList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    /**
     * 批量更新附属合同补充协议清单
     * @author suncg
     * @Param zxCtFsSideAgreementInventoryList
     * */
    @ApiOperation(value="批量更新附属合同补充协议清单", notes="批量更新附属合同补充协议清单")
    @ApiImplicitParam(name = "zxCtFsSideAgreementInventoryList", value = "附属合同补充协议清单List", dataType = "List<ZxCtFsSideAgreementInventory>")
    @RequireToken
    @PostMapping("/batchUpdateZxCtFsSideAgreementInventory")
    public ResponseEntity batchUpdate(@RequestBody(required = false) List<ZxCtFsSideAgreementInventory> zxCtFsSideAgreementInventoryList) {
        return zxCtFsSideAgreementInventoryService.batchUpdateOrAdd(zxCtFsSideAgreementInventoryList);
    }
}
