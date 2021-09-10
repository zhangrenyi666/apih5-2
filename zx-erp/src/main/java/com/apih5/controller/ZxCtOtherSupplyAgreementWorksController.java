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
import com.apih5.mybatis.pojo.ZxCtOtherSupplyAgreementWorks;
import com.apih5.service.ZxCtOtherSupplyAgreementWorksService;

@RestController
public class ZxCtOtherSupplyAgreementWorksController {

    @Autowired(required = true)
    private ZxCtOtherSupplyAgreementWorksService zxCtOtherSupplyAgreementWorksService;

    @ApiOperation(value="查询其他合同评审补充协议清单明细", notes="查询其他合同评审补充协议清单明细")
    @ApiImplicitParam(name = "zxCtOtherSupplyAgreementWorks", value = "其他合同评审补充协议清单明细entity", dataType = "ZxCtOtherSupplyAgreementWorks")
    @RequireToken
    @PostMapping("/getZxCtOtherSupplyAgreementWorksList")
    public ResponseEntity getZxCtOtherSupplyAgreementWorksList(@RequestBody(required = false) ZxCtOtherSupplyAgreementWorks zxCtOtherSupplyAgreementWorks) {
        return zxCtOtherSupplyAgreementWorksService.getZxCtOtherSupplyAgreementWorksListByCondition(zxCtOtherSupplyAgreementWorks);
    }

    @ApiOperation(value="查询详情其他合同评审补充协议清单明细", notes="查询详情其他合同评审补充协议清单明细")
    @ApiImplicitParam(name = "zxCtOtherSupplyAgreementWorks", value = "其他合同评审补充协议清单明细entity", dataType = "ZxCtOtherSupplyAgreementWorks")
    @RequireToken
    @PostMapping("/getZxCtOtherSupplyAgreementWorksDetail")
    public ResponseEntity getZxCtOtherSupplyAgreementWorksDetail(@RequestBody(required = false) ZxCtOtherSupplyAgreementWorks zxCtOtherSupplyAgreementWorks) {
        return zxCtOtherSupplyAgreementWorksService.getZxCtOtherSupplyAgreementWorksDetail(zxCtOtherSupplyAgreementWorks);
    }

    @ApiOperation(value="新增其他合同评审补充协议清单明细", notes="新增其他合同评审补充协议清单明细")
    @ApiImplicitParam(name = "zxCtOtherSupplyAgreementWorks", value = "其他合同评审补充协议清单明细entity", dataType = "ZxCtOtherSupplyAgreementWorks")
    @RequireToken
    @PostMapping("/addZxCtOtherSupplyAgreementWorks")
    public ResponseEntity addZxCtOtherSupplyAgreementWorks(@RequestBody(required = false) ZxCtOtherSupplyAgreementWorks zxCtOtherSupplyAgreementWorks) {
        return zxCtOtherSupplyAgreementWorksService.saveZxCtOtherSupplyAgreementWorks(zxCtOtherSupplyAgreementWorks);
    }

    @ApiOperation(value="更新其他合同评审补充协议清单明细", notes="更新其他合同评审补充协议清单明细")
    @ApiImplicitParam(name = "zxCtOtherSupplyAgreementWorks", value = "其他合同评审补充协议清单明细entity", dataType = "ZxCtOtherSupplyAgreementWorks")
    @RequireToken
    @PostMapping("/updateZxCtOtherSupplyAgreementWorks")
    public ResponseEntity updateZxCtOtherSupplyAgreementWorks(@RequestBody(required = false) ZxCtOtherSupplyAgreementWorks zxCtOtherSupplyAgreementWorks) {
        return zxCtOtherSupplyAgreementWorksService.updateZxCtOtherSupplyAgreementWorks(zxCtOtherSupplyAgreementWorks);
    }

    @ApiOperation(value="删除其他合同评审补充协议清单明细", notes="删除其他合同评审补充协议清单明细")
    @ApiImplicitParam(name = "zxCtOtherSupplyAgreementWorksList", value = "其他合同评审补充协议清单明细List", dataType = "List<ZxCtOtherSupplyAgreementWorks>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxCtOtherSupplyAgreementWorks")
    public ResponseEntity batchDeleteUpdateZxCtOtherSupplyAgreementWorks(@RequestBody(required = false) List<ZxCtOtherSupplyAgreementWorks> zxCtOtherSupplyAgreementWorksList) {
        return zxCtOtherSupplyAgreementWorksService.batchDeleteUpdateZxCtOtherSupplyAgreementWorks(zxCtOtherSupplyAgreementWorksList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
