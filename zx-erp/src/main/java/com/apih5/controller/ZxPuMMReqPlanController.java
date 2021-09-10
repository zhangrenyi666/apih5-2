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
import com.apih5.mybatis.pojo.ZxPuMMReqPlan;
import com.apih5.mybatis.pojo.ZxSkCustomerOutRes;
import com.apih5.service.ZxPuMMReqPlanService;

@RestController
public class ZxPuMMReqPlanController {

    @Autowired(required = true)
    private ZxPuMMReqPlanService zxPuMMReqPlanService;

    @ApiOperation(value="查询物资月度需用计划汇总表", notes="查询物资月度需用计划汇总表")
    @ApiImplicitParam(name = "zxPuMMReqPlan", value = "物资月度需用计划汇总表entity", dataType = "ZxPuMMReqPlan")
    @RequireToken
    @PostMapping("/getZxPuMMReqPlanList")
    public ResponseEntity getZxPuMMReqPlanList(@RequestBody(required = false) ZxPuMMReqPlan zxPuMMReqPlan) {
        return zxPuMMReqPlanService.getZxPuMMReqPlanListByCondition(zxPuMMReqPlan);
    }

    @ApiOperation(value="查询详情物资月度需用计划汇总表", notes="查询详情物资月度需用计划汇总表")
    @ApiImplicitParam(name = "zxPuMMReqPlan", value = "物资月度需用计划汇总表entity", dataType = "ZxPuMMReqPlan")
    @RequireToken
    @PostMapping("/getZxPuMMReqPlanDetail")
    public ResponseEntity getZxPuMMReqPlanDetail(@RequestBody(required = false) ZxPuMMReqPlan zxPuMMReqPlan) {
        return zxPuMMReqPlanService.getZxPuMMReqPlanDetail(zxPuMMReqPlan);
    }

    @ApiOperation(value="新增物资月度需用计划汇总表", notes="新增物资月度需用计划汇总表")
    @ApiImplicitParam(name = "zxPuMMReqPlan", value = "物资月度需用计划汇总表entity", dataType = "ZxPuMMReqPlan")
    @RequireToken
    @PostMapping("/addZxPuMMReqPlan")
    public ResponseEntity addZxPuMMReqPlan(@RequestBody(required = false) ZxPuMMReqPlan zxPuMMReqPlan) {
        return zxPuMMReqPlanService.saveZxPuMMReqPlan(zxPuMMReqPlan);
    }

    @ApiOperation(value="更新物资月度需用计划汇总表", notes="更新物资月度需用计划汇总表")
    @ApiImplicitParam(name = "zxPuMMReqPlan", value = "物资月度需用计划汇总表entity", dataType = "ZxPuMMReqPlan")
    @RequireToken
    @PostMapping("/updateZxPuMMReqPlan")
    public ResponseEntity updateZxPuMMReqPlan(@RequestBody(required = false) ZxPuMMReqPlan zxPuMMReqPlan) {
        return zxPuMMReqPlanService.updateZxPuMMReqPlan(zxPuMMReqPlan);
    }

    @ApiOperation(value="删除物资月度需用计划汇总表", notes="删除物资月度需用计划汇总表")
    @ApiImplicitParam(name = "zxPuMMReqPlanList", value = "物资月度需用计划汇总表List", dataType = "List<ZxPuMMReqPlan>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxPuMMReqPlan")
    public ResponseEntity batchDeleteUpdateZxPuMMReqPlan(@RequestBody(required = false) List<ZxPuMMReqPlan> zxPuMMReqPlanList) {
        return zxPuMMReqPlanService.batchDeleteUpdateZxPuMMReqPlan(zxPuMMReqPlanList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    
    @ApiOperation(value="报表导出物资月度需用计划汇总表", notes="报表导出物资月度需用计划汇总表")
    @ApiImplicitParam(name = "zxPuMMReqPlan", value = "设备台账entity", dataType = "ZxPuMMReqPlan")
    @PostMapping("/ureportZxPuMMReqPlan")
    public List<ZxPuMMReqPlan> ureportZxPuMMReqPlan(@RequestBody(required = false) ZxPuMMReqPlan zxPuMMReqPlan) {
        return zxPuMMReqPlanService.ureportZxPuMMReqPlan(zxPuMMReqPlan);
    }
    
    @ApiOperation(value="报表导出物资月度需用计划汇总表", notes="报表导出物资月度需用计划汇总表")
    @ApiImplicitParam(name = "zxPuMMReqPlan", value = "设备台账entity", dataType = "ZxPuMMReqPlan")
    @RequireToken
    @PostMapping("/ureportZxPuMMReqPlanIdle")
    public ResponseEntity ureportZxPuMMReqPlanIdle(@RequestBody(required = false) ZxPuMMReqPlan zxPuMMReqPlan) {
        return zxPuMMReqPlanService.ureportZxPuMMReqPlanIdle(zxPuMMReqPlan);
    }
}
