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
import com.apih5.mybatis.pojo.ZxSfCost;
import com.apih5.service.ZxSfCostService;

@RestController
public class ZxSfCostController {

    @Autowired(required = true)
    private ZxSfCostService zxSfCostService;

    @ApiOperation(value="查询安全费用查询", notes="查询安全费用查询")
    @ApiImplicitParam(name = "zxSfCost", value = "安全费用查询entity", dataType = "ZxSfCost")
    @RequireToken
    @PostMapping("/getZxSfCostList")
    public ResponseEntity getZxSfCostList(@RequestBody(required = false) ZxSfCost zxSfCost) {
        return zxSfCostService.getZxSfCostListByCondition(zxSfCost);
    }

    @ApiOperation(value="查询详情安全费用查询", notes="查询详情安全费用查询")
    @ApiImplicitParam(name = "zxSfCost", value = "安全费用查询entity", dataType = "ZxSfCost")
    @RequireToken
    @PostMapping("/getZxSfCostDetail")
    public ResponseEntity getZxSfCostDetail(@RequestBody(required = false) ZxSfCost zxSfCost) {
        return zxSfCostService.getZxSfCostDetail(zxSfCost);
    }

    @ApiOperation(value="新增安全费用查询", notes="新增安全费用查询")
    @ApiImplicitParam(name = "zxSfCost", value = "安全费用查询entity", dataType = "ZxSfCost")
    @RequireToken
    @PostMapping("/addZxSfCost")
    public ResponseEntity addZxSfCost(@RequestBody(required = false) ZxSfCost zxSfCost) {
        return zxSfCostService.saveZxSfCost(zxSfCost);
    }

    @ApiOperation(value="更新安全费用查询", notes="更新安全费用查询")
    @ApiImplicitParam(name = "zxSfCost", value = "安全费用查询entity", dataType = "ZxSfCost")
    @RequireToken
    @PostMapping("/updateZxSfCost")
    public ResponseEntity updateZxSfCost(@RequestBody(required = false) ZxSfCost zxSfCost) {
        return zxSfCostService.updateZxSfCost(zxSfCost);
    }

    @ApiOperation(value="删除安全费用查询", notes="删除安全费用查询")
    @ApiImplicitParam(name = "zxSfCostList", value = "安全费用查询List", dataType = "List<ZxSfCost>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxSfCost")
    public ResponseEntity batchDeleteUpdateZxSfCost(@RequestBody(required = false) List<ZxSfCost> zxSfCostList) {
        return zxSfCostService.batchDeleteUpdateZxSfCost(zxSfCostList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

    @ApiOperation(value="查询公司安全费用查询", notes="查询公司安全费用查询")
    @ApiImplicitParam(name = "zxSfCost", value = "安全费用查询entity", dataType = "ZxSfCost")
    @RequireToken
    @PostMapping("/getCostCompany")
    public ResponseEntity getCostCompany(@RequestBody(required = false) ZxSfCost zxSfCost) {
        return zxSfCostService.getCompany(zxSfCost);
    }

    @ApiOperation(value="查询公司列表安全费用查询", notes="查询公司列表安全费用查询")
    @ApiImplicitParam(name = "zxSfCost", value = "安全费用查询entity", dataType = "ZxSfCost")
    @RequireToken
    @PostMapping("/getCostCompanyList")
    public ResponseEntity getCostCompanyList(@RequestBody(required = false) ZxSfCost zxSfCost) {
        return zxSfCostService.getCompanyList(zxSfCost);
    }


    @ApiOperation(value="查询项目列表安全费用查询", notes="查询项目列表安全费用查询")
    @ApiImplicitParam(name = "zxSfCost", value = "安全费用查询entity", dataType = "ZxSfCost")
    @RequireToken
    @PostMapping("/getCostOrgCostList")
    public ResponseEntity getCostOrgCostList(@RequestBody(required = false) ZxSfCost zxSfCost) {
        return zxSfCostService.getOrgCostList(zxSfCost);
    }

    @ApiOperation(value="查询归档项目列表安全费用查询", notes="查询归档项目列表安全费用查询")
    @ApiImplicitParam(name = "zxSfCost", value = "安全费用查询entity", dataType = "ZxSfCost")
    @RequireToken
    @PostMapping("/getCostGuiDangList")
    public ResponseEntity getCostGuiDangList(@RequestBody(required = false) ZxSfCost zxSfCost) {
        return zxSfCostService.getGuiDangList(zxSfCost);
    }

    @ApiOperation(value="查询交工项目列表安全费用查询", notes="查询交工项目列表安全费用查询")
    @ApiImplicitParam(name = "zxSfCost", value = "安全费用查询entity", dataType = "ZxSfCost")
    @RequireToken
    @PostMapping("/getCostJiaoGongList")
    public ResponseEntity getCostJiaoGongList(@RequestBody(required = false) ZxSfCost zxSfCost) {
        return zxSfCostService.getJiaoGongList(zxSfCost);
    }

    @ApiOperation(value="查询完工项目列表安全费用查询", notes="查询完工项目列表安全费用查询")
    @ApiImplicitParam(name = "zxSfCost", value = "安全费用查询entity", dataType = "ZxSfCost")
    @RequireToken
    @PostMapping("/getCostWanGangList")
    public ResponseEntity getCostWanGangList(@RequestBody(required = false) ZxSfCost zxSfCost) {
        return zxSfCostService.getWanGongList(zxSfCost);
    }

    @ApiOperation(value="查询开工项目列表安全费用查询", notes="查询开工项目列表安全费用查询")
    @ApiImplicitParam(name = "zxSfCost", value = "安全费用查询entity", dataType = "ZxSfCost")
    @RequireToken
    @PostMapping("/getCostKaiGongList")
    public ResponseEntity getCostKaiGongList(@RequestBody(required = false) ZxSfCost zxSfCost) {
        return zxSfCostService.getKaiGongList(zxSfCost);
    }


}
