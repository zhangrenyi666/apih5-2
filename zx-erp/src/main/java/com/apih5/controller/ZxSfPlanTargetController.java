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
import com.apih5.mybatis.pojo.ZxSfPlanTarget;
import com.apih5.service.ZxSfPlanTargetService;

@RestController
public class ZxSfPlanTargetController {

    @Autowired(required = true)
    private ZxSfPlanTargetService zxSfPlanTargetService;

    @ApiOperation(value="查询", notes="查询安全目标计划")
    @ApiImplicitParam(name = "zxSfPlanTarget", value = "安全目标计划entity", dataType = "ZxSfPlanTarget")
    @RequireToken
    @PostMapping("/getZxSfPlanTargetList")
    public ResponseEntity getZxSfPlanTargetList(@RequestBody(required = false) ZxSfPlanTarget zxSfPlanTarget) {
        return zxSfPlanTargetService.getZxSfPlanTargetListByCondition(zxSfPlanTarget);
    }

    @ApiOperation(value="查询详情安全目标计划", notes="查询详情安全目标计划")
    @ApiImplicitParam(name = "zxSfPlanTarget", value = "安全目标计划entity", dataType = "ZxSfPlanTarget")
    @RequireToken
    @PostMapping("/getZxSfPlanTargetDetail")
    public ResponseEntity getZxSfPlanTargetDetail(@RequestBody(required = false) ZxSfPlanTarget zxSfPlanTarget) {
        return zxSfPlanTargetService.getZxSfPlanTargetDetail(zxSfPlanTarget);
    }

    @ApiOperation(value="新增安全目标计划", notes="新增安全目标计划")
    @ApiImplicitParam(name = "zxSfPlanTarget", value = "安全目标计划entity", dataType = "ZxSfPlanTarget")
    @RequireToken
    @PostMapping("/addZxSfPlanTarget")
    public ResponseEntity addZxSfPlanTarget(@RequestBody(required = false) ZxSfPlanTarget zxSfPlanTarget) {
        return zxSfPlanTargetService.saveZxSfPlanTarget(zxSfPlanTarget);
    }

    @ApiOperation(value="更新安全目标计划", notes="更新安全目标计划")
    @ApiImplicitParam(name = "zxSfPlanTarget", value = "安全目标计划entity", dataType = "ZxSfPlanTarget")
    @RequireToken
    @PostMapping("/updateZxSfPlanTarget")
    public ResponseEntity updateZxSfPlanTarget(@RequestBody(required = false) ZxSfPlanTarget zxSfPlanTarget) {
        return zxSfPlanTargetService.updateZxSfPlanTarget(zxSfPlanTarget);
    }

    @ApiOperation(value="删除安全目标计划", notes="删除安全目标计划")
    @ApiImplicitParam(name = "zxSfPlanTargetList", value = "安全目标计划List", dataType = "List<ZxSfPlanTarget>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxSfPlanTarget")
    public ResponseEntity batchDeleteUpdateZxSfPlanTarget(@RequestBody(required = false) List<ZxSfPlanTarget> zxSfPlanTargetList) {
        return zxSfPlanTargetService.batchDeleteUpdateZxSfPlanTarget(zxSfPlanTargetList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

    @ApiOperation(value="局级安全目标计划统计", notes="局级安全目标计划统计")
    @RequireToken
    @PostMapping("/getZxSfPlanTargetJuInfo")
    public ResponseEntity getZxSfPlanTargetJuInfo() {
        return zxSfPlanTargetService.getJuInfo();
    }

    @ApiOperation(value="局级安全目标计划公司列表", notes="局级安全目标计划公司列表")
    @RequireToken
    @PostMapping("/getZxSfPlanTargetComList")
    public ResponseEntity getZxSfPlanTargetComList() {
        return zxSfPlanTargetService.getComList();
    }

    @ApiOperation(value="查询公司安全目标计划统计", notes="查询公司安全目标计划统计")
    @ApiImplicitParam(name = "zxSfPlanTarget", value = "安全目标计划entity", dataType = "ZxSfPlanTarget")
    @RequireToken
    @PostMapping("/getZxSfPlanTargetComInfo")
    public ResponseEntity getZxSfPlanTargetComInfo(@RequestBody(required = false) ZxSfPlanTarget zxSfPlanTarget) {
        return zxSfPlanTargetService.getComInfo(zxSfPlanTarget);
    }

    @ApiOperation(value="查询项目安全目标计划统计（状态分类）", notes="查询项目安全目标计划统计（状态分类）")
    @ApiImplicitParam(name = "zxSfPlanTarget", value = "安全目标计划entity", dataType = "ZxSfPlanTarget")
    @RequireToken
    @PostMapping("/getZxSfPlanTargetOrgList")
    public ResponseEntity getZxSfPlanTargetOrgList(@RequestBody(required = false) ZxSfPlanTarget zxSfPlanTarget) {
        return zxSfPlanTargetService.getOrgList(zxSfPlanTarget);
    }

    @ApiOperation(value="查询归档项目安全目标计划统计", notes="查询归档项目安全目标计划统计")
    @ApiImplicitParam(name = "zxSfPlanTarget", value = "安全目标计划entity", dataType = "ZxSfPlanTarget")
    @RequireToken
    @PostMapping("/getZxSfPlanTargetGuiDangList")
    public ResponseEntity getZxSfPlanTargetGuiDangList(@RequestBody(required = false) ZxSfPlanTarget zxSfPlanTarget) {
        return zxSfPlanTargetService.getGuiDangList(zxSfPlanTarget);
    }

    @ApiOperation(value="查询交工项目安全目标计划统计", notes="查询交工项目安全目标计划统计")
    @ApiImplicitParam(name = "zxSfPlanTarget", value = "安全目标计划entity", dataType = "ZxSfPlanTarget")
    @RequireToken
    @PostMapping("/getZxSfPlanTargetJiaoGongList")
    public ResponseEntity getZxSfPlanTargetJiaoGongList(@RequestBody(required = false) ZxSfPlanTarget zxSfPlanTarget) {
        return zxSfPlanTargetService.getJiaoGongList(zxSfPlanTarget);
    }

    @ApiOperation(value="查询完工项目安全目标计划统计", notes="查询完工项目安全目标计划统计")
    @ApiImplicitParam(name = "zxSfPlanTarget", value = "安全目标计划entity", dataType = "ZxSfPlanTarget")
    @RequireToken
    @PostMapping("/getZxSfPlanTargetWanGongList")
    public ResponseEntity getZxSfPlanTargetWanGongList(@RequestBody(required = false) ZxSfPlanTarget zxSfPlanTarget) {
        return zxSfPlanTargetService.getWanGongList(zxSfPlanTarget);
    }

    @ApiOperation(value="查询开工项目安全目标计划统计", notes="查询开工项目安全目标计划统计")
    @ApiImplicitParam(name = "zxSfPlanTarget", value = "安全目标计划entity", dataType = "ZxSfPlanTarget")
    @RequireToken
    @PostMapping("/getZxSfPlanTargetKaiGongList")
    public ResponseEntity getZxSfPlanTargetKaiGongList(@RequestBody(required = false) ZxSfPlanTarget zxSfPlanTarget) {
        return zxSfPlanTargetService.getKaiGongList(zxSfPlanTarget);
    }
}
