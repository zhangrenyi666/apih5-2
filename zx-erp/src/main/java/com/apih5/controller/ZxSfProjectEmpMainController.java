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
import com.apih5.mybatis.pojo.ZxSfProjectEmpMain;
import com.apih5.service.ZxSfProjectEmpMainService;

@RestController
public class ZxSfProjectEmpMainController {

    @Autowired(required = true)
    private ZxSfProjectEmpMainService zxSfProjectEmpMainService;

    @ApiOperation(value="查询安全管理人员", notes="查询安全管理人员")
    @ApiImplicitParam(name = "zxSfProjectEmpMain", value = "安全管理人员entity", dataType = "ZxSfProjectEmpMain")
    @RequireToken
    @PostMapping("/getZxSfProjectEmpMainList")
    public ResponseEntity getZxSfProjectEmpMainList(@RequestBody(required = false) ZxSfProjectEmpMain zxSfProjectEmpMain) {
        return zxSfProjectEmpMainService.getZxSfProjectEmpMainListByCondition(zxSfProjectEmpMain);
    }

    @ApiOperation(value="查询详情安全管理人员", notes="查询详情安全管理人员")
    @ApiImplicitParam(name = "zxSfProjectEmpMain", value = "安全管理人员entity", dataType = "ZxSfProjectEmpMain")
    @RequireToken
    @PostMapping("/getZxSfProjectEmpMainDetail")
    public ResponseEntity getZxSfProjectEmpMainDetail(@RequestBody(required = false) ZxSfProjectEmpMain zxSfProjectEmpMain) {
        return zxSfProjectEmpMainService.getZxSfProjectEmpMainDetail(zxSfProjectEmpMain);
    }

    @ApiOperation(value="新增安全管理人员", notes="新增安全管理人员")
    @ApiImplicitParam(name = "zxSfProjectEmpMain", value = "安全管理人员entity", dataType = "ZxSfProjectEmpMain")
    @RequireToken
    @PostMapping("/addZxSfProjectEmpMain")
    public ResponseEntity addZxSfProjectEmpMain(@RequestBody(required = false) ZxSfProjectEmpMain zxSfProjectEmpMain) {
        return zxSfProjectEmpMainService.saveZxSfProjectEmpMain(zxSfProjectEmpMain);
    }

    @ApiOperation(value="更新安全管理人员", notes="更新安全管理人员")
    @ApiImplicitParam(name = "zxSfProjectEmpMain", value = "安全管理人员entity", dataType = "ZxSfProjectEmpMain")
    @RequireToken
    @PostMapping("/updateZxSfProjectEmpMain")
    public ResponseEntity updateZxSfProjectEmpMain(@RequestBody(required = false) ZxSfProjectEmpMain zxSfProjectEmpMain) {
        return zxSfProjectEmpMainService.updateZxSfProjectEmpMain(zxSfProjectEmpMain);
    }

    @ApiOperation(value="删除安全管理人员", notes="删除安全管理人员")
    @ApiImplicitParam(name = "zxSfProjectEmpMainList", value = "安全管理人员List", dataType = "List<ZxSfProjectEmpMain>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxSfProjectEmpMain")
    public ResponseEntity batchDeleteUpdateZxSfProjectEmpMain(@RequestBody(required = false) List<ZxSfProjectEmpMain> zxSfProjectEmpMainList) {
        return zxSfProjectEmpMainService.batchDeleteUpdateZxSfProjectEmpMain(zxSfProjectEmpMainList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

    @ApiOperation(value="公司列表人员学历统计", notes="公司列表人员学历统计")
    @RequireToken
    @PostMapping("/getZxSfProEmpMainComList")
    public ResponseEntity getZxSfProEmpMainComList() {
        return zxSfProjectEmpMainService.getZxSfProEmpMainJuInfo();
    }

    @ApiOperation(value="公司人员学历统计", notes="公司人员学历统计")
    @ApiImplicitParam(name = "zxSfProjectEmpMain", value = "安全管理人员entity", dataType = "ZxSfProjectEmpMain")
    @RequireToken
    @PostMapping("/getZxSfProEmpMainComInfo")
    public ResponseEntity getZxSfProEmpMainComInfo(@RequestBody(required = false) ZxSfProjectEmpMain zxSfProjectEmpMain) {
        return zxSfProjectEmpMainService.getZxSfProEmpMainComInfo(zxSfProjectEmpMain);
    }

    @ApiOperation(value="局人员学历统计", notes="局人员学历统计")
    @ApiImplicitParam(name = "zxSfProjectEmpMain", value = "安全管理人员entity", dataType = "ZxSfProjectEmpMain")
    @RequireToken
    @PostMapping("/getZxSfProEmpMainJuInfo")
    public ResponseEntity getZxSfProEmpMainJuInfo(@RequestBody(required = false) ZxSfProjectEmpMain zxSfProjectEmpMain) {
        return zxSfProjectEmpMainService.getJuInfo(zxSfProjectEmpMain);
    }

    @ApiOperation(value="人员学历项目分类统计", notes="人员学历项目分类统计")
    @ApiImplicitParam(name = "zxSfProjectEmpMain", value = "安全管理人员entity", dataType = "ZxSfProjectEmpMain")
    @RequireToken
    @PostMapping("/getZxSfProEmpMainOrgList")
    public ResponseEntity getZxSfProEmpMainOrgList(@RequestBody(required = false) ZxSfProjectEmpMain zxSfProjectEmpMain) {
        return zxSfProjectEmpMainService.getZxSfProEmpMainOrgList(zxSfProjectEmpMain);
    }

    @ApiOperation(value="人员学历归档项目统计列表", notes="人员学历归档项目统计列表")
    @ApiImplicitParam(name = "zxSfProjectEmpMain", value = "安全管理人员entity", dataType = "ZxSfProjectEmpMain")
    @RequireToken
    @PostMapping("/getZxSfProEmpMainGuiDangList")
    public ResponseEntity getZxSfProEmpMainGuiDangList(@RequestBody(required = false) ZxSfProjectEmpMain zxSfProjectEmpMain) {
        return zxSfProjectEmpMainService.getZxSfProEmpMainGuiDangList(zxSfProjectEmpMain);
    }

    @ApiOperation(value="人员学历交工项目统计列表", notes="人员学历交工项目统计列表")
    @ApiImplicitParam(name = "zxSfProjectEmpMain", value = "安全管理人员entity", dataType = "ZxSfProjectEmpMain")
    @RequireToken
    @PostMapping("/getZxSfProEmpMainJiaoGongList")
    public ResponseEntity getZxSfProEmpMainJiaoGongList(@RequestBody(required = false) ZxSfProjectEmpMain zxSfProjectEmpMain) {
        return zxSfProjectEmpMainService.getZxSfProEmpMainJiaoGongList(zxSfProjectEmpMain);
    }

    @ApiOperation(value="人员学历完工项目统计列表", notes="人员学历完工项目统计列表")
    @ApiImplicitParam(name = "zxSfProjectEmpMain", value = "安全管理人员entity", dataType = "ZxSfProjectEmpMain")
    @RequireToken
    @PostMapping("/getZxSfProEmpMainWanGongList")
    public ResponseEntity getZxSfProEmpMainWanGongList(@RequestBody(required = false) ZxSfProjectEmpMain zxSfProjectEmpMain) {
        return zxSfProjectEmpMainService.getZxSfProEmpMainWanGongList(zxSfProjectEmpMain);
    }

    @ApiOperation(value="人员学历开工项目统计列表", notes="人员学历开工项目统计列表")
    @ApiImplicitParam(name = "zxSfProjectEmpMain", value = "安全管理人员entity", dataType = "ZxSfProjectEmpMain")
    @RequireToken
    @PostMapping("/getZxSfProEmpMainKaiGongList")
    public ResponseEntity getZxSfProEmpMainKaiGongList(@RequestBody(required = false) ZxSfProjectEmpMain zxSfProjectEmpMain) {
        return zxSfProjectEmpMainService.getZxSfProEmpMainKaiGongList(zxSfProjectEmpMain);
    }



    @ApiOperation(value="公司列表人员职称统计", notes="公司列表人员职称统计")
    @RequireToken
    @PostMapping("/getZxSfProEmpMainZhiChengComList")
    public ResponseEntity getZxSfProEmpMainZhiChengComList() {
        return zxSfProjectEmpMainService.getZhiChengComList();
    }

    @ApiOperation(value="公司人员职称统计", notes="公司人员职称统计")
    @ApiImplicitParam(name = "zxSfProjectEmpMain", value = "安全管理人员entity", dataType = "ZxSfProjectEmpMain")
    @RequireToken
    @PostMapping("/getZxSfProEmpMainZhiChengComInfo")
    public ResponseEntity getZxSfProEmpMainZhiChengComInfo(@RequestBody(required = false) ZxSfProjectEmpMain zxSfProjectEmpMain) {
        return zxSfProjectEmpMainService.getZhiChengComInfo(zxSfProjectEmpMain);
    }

    @ApiOperation(value="局人员职称统计", notes="局人员职称统计")
    @ApiImplicitParam(name = "zxSfProjectEmpMain", value = "安全管理人员entity", dataType = "ZxSfProjectEmpMain")
    @RequireToken
    @PostMapping("/getZxSfProEmpMainZhiChengJuInfo")
    public ResponseEntity getZxSfProEmpMainZhiChengJuInfo(@RequestBody(required = false) ZxSfProjectEmpMain zxSfProjectEmpMain) {
        return zxSfProjectEmpMainService.getZhiChengJuInfo(zxSfProjectEmpMain);
    }



    @ApiOperation(value="人员职称项目分类统计", notes="人员职称项目分类统计")
    @ApiImplicitParam(name = "zxSfProjectEmpMain", value = "安全管理人员entity", dataType = "ZxSfProjectEmpMain")
    @RequireToken
    @PostMapping("/getZxSfProEmpMainZhiChengOrgList")
    public ResponseEntity getZxSfProEmpMainZhiChengOrgList(@RequestBody(required = false) ZxSfProjectEmpMain zxSfProjectEmpMain) {
        return zxSfProjectEmpMainService.getZxSfProEmpZhiChengOrgList(zxSfProjectEmpMain);
    }

    @ApiOperation(value="人员职称归档项目统计列表", notes="人员职称归档项目统计列表")
    @ApiImplicitParam(name = "zxSfProjectEmpMain", value = "安全管理人员entity", dataType = "ZxSfProjectEmpMain")
    @RequireToken
    @PostMapping("/getZxSfProEmpMainZhiChengGuiDangList")
    public ResponseEntity getZxSfProEmpMainZhiChengGuiDangList(@RequestBody(required = false) ZxSfProjectEmpMain zxSfProjectEmpMain) {
        return zxSfProjectEmpMainService.getZhiChengGuiDangList(zxSfProjectEmpMain);
    }

    @ApiOperation(value="人员职称交工项目统计列表", notes="人员职称交工项目统计列表")
    @ApiImplicitParam(name = "zxSfProjectEmpMain", value = "安全管理人员entity", dataType = "ZxSfProjectEmpMain")
    @RequireToken
    @PostMapping("/getZxSfProEmpMainZhiChengJiaoGongList")
    public ResponseEntity getZxSfProEmpMainZhiChengJiaoGongList(@RequestBody(required = false) ZxSfProjectEmpMain zxSfProjectEmpMain) {
        return zxSfProjectEmpMainService.getZhiChengJiaoGongList(zxSfProjectEmpMain);
    }

    @ApiOperation(value="人员职称完工项目统计列表", notes="人员学历完工项目统计列表")
    @ApiImplicitParam(name = "zxSfProjectEmpMain", value = "安全管理人员entity", dataType = "ZxSfProjectEmpMain")
    @RequireToken
    @PostMapping("/getZxSfProEmpMainZhiChengWanGongList")
    public ResponseEntity getZxSfProEmpMainZhiChengWanGongList(@RequestBody(required = false) ZxSfProjectEmpMain zxSfProjectEmpMain) {
        return zxSfProjectEmpMainService.getZhiChengWanGongList(zxSfProjectEmpMain);
    }

    @ApiOperation(value="人员学职称开工项目统计列表", notes="人员学历开工项目统计列表")
    @ApiImplicitParam(name = "zxSfProjectEmpMain", value = "安全管理人员entity", dataType = "ZxSfProjectEmpMain")
    @RequireToken
    @PostMapping("/getZxSfProEmpMainZhiChengKaiGongList")
    public ResponseEntity getZxSfProEmpMainZhiChengKaiGongList(@RequestBody(required = false) ZxSfProjectEmpMain zxSfProjectEmpMain) {
        return zxSfProjectEmpMainService.getZhiChengKaiGongList(zxSfProjectEmpMain);
    }

    @ApiOperation(value="公司列表人员工龄统计", notes="公司列表职称统计")
    @RequireToken
    @PostMapping("/getZxSfProEmpMainWorkAgeComList")
    public ResponseEntity getZxSfProEmpMainWorkAgeComList() {
        return zxSfProjectEmpMainService.getWorkAgeComList();
    }

    @ApiOperation(value="公司人员工龄统计", notes="公司人员工龄统计")
    @ApiImplicitParam(name = "zxSfProjectEmpMain", value = "安全管理人员entity", dataType = "ZxSfProjectEmpMain")
    @RequireToken
    @PostMapping("/getZxSfProEmpMainWorkAgeComInfo")
    public ResponseEntity getZxSfProEmpMainWorkAgeComInfo(@RequestBody(required = false) ZxSfProjectEmpMain zxSfProjectEmpMain) {
        return zxSfProjectEmpMainService.getWorkAgeComInfo(zxSfProjectEmpMain);
    }

    @ApiOperation(value="局人员工龄统计", notes="局人员工龄统计")
    @ApiImplicitParam(name = "zxSfProjectEmpMain", value = "安全管理人员entity", dataType = "ZxSfProjectEmpMain")
    @RequireToken
    @PostMapping("/getZxSfProEmpMainWorkAgJuInfo")
    public ResponseEntity getZxSfProEmpMainWorkAgJuInfo(@RequestBody(required = false) ZxSfProjectEmpMain zxSfProjectEmpMain) {
        return zxSfProjectEmpMainService.getWorkAgeJuInfo(zxSfProjectEmpMain);
    }



    @ApiOperation(value="人员工龄项目分类统计", notes="人员职称项目分类统计")
    @ApiImplicitParam(name = "zxSfProjectEmpMain", value = "安全管理人员entity", dataType = "ZxSfProjectEmpMain")
    @RequireToken
    @PostMapping("/getZxSfProEmpMainpWorkAgeOrgList")
    public ResponseEntity getZxSfProEmpMainpWorkAgeOrgList(@RequestBody(required = false) ZxSfProjectEmpMain zxSfProjectEmpMain) {
        return zxSfProjectEmpMainService.getZxSfProEmpWorkAgeOrgList(zxSfProjectEmpMain);
    }

    @ApiOperation(value="人员工龄归档项目统计列表", notes="人员职称归档项目统计列表")
    @ApiImplicitParam(name = "zxSfProjectEmpMain", value = "安全管理人员entity", dataType = "ZxSfProjectEmpMain")
    @RequireToken
    @PostMapping("/getZxSfProEmpMainWorkAgeGuiDangList")
    public ResponseEntity getZxSfProEmpMainWorkAgeGuiDangList(@RequestBody(required = false) ZxSfProjectEmpMain zxSfProjectEmpMain) {
        return zxSfProjectEmpMainService.getWorkAgeGuiDangList(zxSfProjectEmpMain);
    }

    @ApiOperation(value="人员工龄交工项目统计列表", notes="人员职称交工项目统计列表")
    @ApiImplicitParam(name = "zxSfProjectEmpMain", value = "安全管理人员entity", dataType = "ZxSfProjectEmpMain")
    @RequireToken
    @PostMapping("/getZxSfProEmpMainWorkAgeJiaoGongList")
    public ResponseEntity getZxSfProEmpMainWorkAgeJiaoGongList(@RequestBody(required = false) ZxSfProjectEmpMain zxSfProjectEmpMain) {
        return zxSfProjectEmpMainService.getWorkAgeJiaoGongList(zxSfProjectEmpMain);
    }

    @ApiOperation(value="人员工龄完工项目统计列表", notes="人员学历完工项目统计列表")
    @ApiImplicitParam(name = "zxSfProjectEmpMain", value = "安全管理人员entity", dataType = "ZxSfProjectEmpMain")
    @RequireToken
    @PostMapping("/getZxSfProEmpMainWorkAgeWanGongList")
    public ResponseEntity getZxSfProEmpMainWorkAgeWanGongList(@RequestBody(required = false) ZxSfProjectEmpMain zxSfProjectEmpMain) {
        return zxSfProjectEmpMainService.getWorkAgeWanGongList(zxSfProjectEmpMain);
    }

    @ApiOperation(value="人员工龄开工项目统计列表", notes="人员学历开工项目统计列表")
    @ApiImplicitParam(name = "zxSfProjectEmpMain", value = "安全管理人员entity", dataType = "ZxSfProjectEmpMain")
    @RequireToken
    @PostMapping("/getZxSfProEmpMainWorkAgeKaiGongList")
    public ResponseEntity getZxSfProEmpMainWorkAgeKaiGongList(@RequestBody(required = false) ZxSfProjectEmpMain zxSfProjectEmpMain) {
        return zxSfProjectEmpMainService.getWorkAgeKaiGongList(zxSfProjectEmpMain);
    }




}
