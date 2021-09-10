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
import com.apih5.mybatis.pojo.ZxSfFee;
import com.apih5.service.ZxSfFeeService;

@RestController
public class ZxSfFeeController {

    @Autowired(required = true)
    private ZxSfFeeService zxSfFeeService;

    @ApiOperation(value="查询安全费用", notes="查询安全费用")
    @ApiImplicitParam(name = "zxSfFee", value = "安全费用entity", dataType = "ZxSfFee")
    @RequireToken
    @PostMapping("/getZxSfFeeList")
    public ResponseEntity getZxSfFeeList(@RequestBody(required = false) ZxSfFee zxSfFee) {
        return zxSfFeeService.getZxSfFeeListByCondition(zxSfFee);
    }

    @ApiOperation(value="查询详情安全费用", notes="查询详情安全费用")
    @ApiImplicitParam(name = "zxSfFee", value = "安全费用entity", dataType = "ZxSfFee")
    @RequireToken
    @PostMapping("/getZxSfFeeDetail")
    public ResponseEntity getZxSfFeeDetail(@RequestBody(required = false) ZxSfFee zxSfFee) {
        return zxSfFeeService.getZxSfFeeDetail(zxSfFee);
    }

    @ApiOperation(value="新增安全费用", notes="新增安全费用")
    @ApiImplicitParam(name = "zxSfFee", value = "安全费用entity", dataType = "ZxSfFee")
    @RequireToken
    @PostMapping("/addZxSfFee")
    public ResponseEntity addZxSfFee(@RequestBody(required = false) ZxSfFee zxSfFee) {
        return zxSfFeeService.saveZxSfFee(zxSfFee);
    }

    @ApiOperation(value="更新安全费用", notes="更新安全费用")
    @ApiImplicitParam(name = "zxSfFee", value = "安全费用entity", dataType = "ZxSfFee")
    @RequireToken
    @PostMapping("/updateZxSfFee")
    public ResponseEntity updateZxSfFee(@RequestBody(required = false) ZxSfFee zxSfFee) {
        return zxSfFeeService.updateZxSfFee(zxSfFee);
    }

    @ApiOperation(value="删除安全费用", notes="删除安全费用")
    @ApiImplicitParam(name = "zxSfFeeList", value = "安全费用List", dataType = "List<ZxSfFee>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxSfFee")
    public ResponseEntity batchDeleteUpdateZxSfFee(@RequestBody(required = false) List<ZxSfFee> zxSfFeeList) {
        return zxSfFeeService.batchDeleteUpdateZxSfFee(zxSfFeeList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

    @ApiOperation(value="查询公司安全费用查询", notes="查询公司安全费用查询")
    @ApiImplicitParam(name = "zxSfFee", value = "安全费用entity", dataType = "zxSfFee")
    @RequireToken
    @PostMapping("/getZxSfFeeCompany")
    public ResponseEntity getZxSfFeeCompany(@RequestBody(required = false) ZxSfFee zxSfCost) {
        return zxSfFeeService.getCompany(zxSfCost);
    }

    @ApiOperation(value="查询公司列表安全费用查询", notes="查询公司列表安全费用查询")
    @ApiImplicitParam(name = "zxSfFee", value = "安全费用entity", dataType = "zxSfFee")
    @RequireToken
    @PostMapping("/getZxSfFeeCompanyList")
    public ResponseEntity getZxSfFeeCompanyList(@RequestBody(required = false) ZxSfFee zxSfCost) {
        return zxSfFeeService.getCompanyList(zxSfCost);
    }


    @ApiOperation(value="查询项目列表安全费用查询", notes="查询项目列表安全费用查询")
    @ApiImplicitParam(name = "zxSfFee", value = "安全费用entity", dataType = "zxSfFee")
    @RequireToken
    @PostMapping("/getZxSfFeeOrgList")
    public ResponseEntity getZxSfFeeOrgList(@RequestBody(required = false) ZxSfFee zxSfCost) {
        return zxSfFeeService.getOrgCostList(zxSfCost);
    }

    @ApiOperation(value="查询归档项目列表安全费用查询", notes="查询归档项目列表安全费用查询")
    @ApiImplicitParam(name = "zxSfFee", value = "安全费用entity", dataType = "zxSfFee")
    @RequireToken
    @PostMapping("/getZxSfFeeGuiDangList")
    public ResponseEntity getZxSfFeeGuiDangList(@RequestBody(required = false) ZxSfFee zxSfCost) {
        return zxSfFeeService.getGuiDangList(zxSfCost);
    }

    @ApiOperation(value="查询交工项目列表安全费用查询", notes="查询交工项目列表安全费用查询")
    @ApiImplicitParam(name = "zxSfFee", value = "安全费用entity", dataType = "zxSfFee")
    @RequireToken
    @PostMapping("/getZxSfFeeJiaoGongList")
    public ResponseEntity getZxSfFeeJiaoGongList(@RequestBody(required = false) ZxSfFee zxSfCost) {
        return zxSfFeeService.getJiaoGongList(zxSfCost);
    }

    @ApiOperation(value="查询完工项目列表安全费用查询", notes="查询完工项目列表安全费用查询")
    @ApiImplicitParam(name = "zxSfFee", value = "安全费用entity", dataType = "zxSfFee")
    @RequireToken
    @PostMapping("/getZxSfFeeWanGangList")
    public ResponseEntity getZxSfFeeWanGangList(@RequestBody(required = false) ZxSfFee zxSfCost) {
        return zxSfFeeService.getWanGongList(zxSfCost);
    }

    @ApiOperation(value="查询开工项目列表安全费用查询", notes="查询开工项目列表安全费用查询")
    @ApiImplicitParam(name = "zxSfFee", value = "安全费用entity", dataType = "zxSfFee")
    @RequireToken
    @PostMapping("/getZxSfFeeKaiGongList")
    public ResponseEntity getZxSfFeeKaiGongList(@RequestBody(required = false) ZxSfFee zxSfCost) {
        return zxSfFeeService.getKaiGongList(zxSfCost);
    }

    @ApiOperation(value="查询开工项目列表安全费用查询", notes="查询开工项目列表安全费用查询")
    @ApiImplicitParam(name = "zxSfFee", value = "安全费用entity", dataType = "zxSfFee")
    @RequireToken
    @PostMapping("/getZxSfFeeJuInfo")
    public ResponseEntity getZxSfFeeJuInfo(@RequestBody(required = false) ZxSfFee zxSfCost) {
        return zxSfFeeService.getJuInfo(zxSfCost);
    }

}
