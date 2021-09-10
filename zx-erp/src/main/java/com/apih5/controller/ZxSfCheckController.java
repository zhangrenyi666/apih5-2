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
import com.apih5.mybatis.pojo.ZxSfCheck;
import com.apih5.service.ZxSfCheckService;

@RestController
public class ZxSfCheckController {

    @Autowired(required = true)
    private ZxSfCheckService zxSfCheckService;

    @ApiOperation(value="查询项目安全检查", notes="查询项目安全检查")
    @ApiImplicitParam(name = "zxSfCheck", value = "项目安全检查entity", dataType = "ZxSfCheck")
    @RequireToken
    @PostMapping("/getZxSfCheckList")
    public ResponseEntity getZxSfCheckList(@RequestBody(required = false) ZxSfCheck zxSfCheck) {
        return zxSfCheckService.getZxSfCheckListByCondition(zxSfCheck);
    }

    @ApiOperation(value="查询详情项目安全检查", notes="查询详情项目安全检查")
    @ApiImplicitParam(name = "zxSfCheck", value = "项目安全检查entity", dataType = "ZxSfCheck")
    @RequireToken
    @PostMapping("/getZxSfCheckDetail")
    public ResponseEntity getZxSfCheckDetail(@RequestBody(required = false) ZxSfCheck zxSfCheck) {
        return zxSfCheckService.getZxSfCheckDetail(zxSfCheck);
    }

    @ApiOperation(value="新增项目安全检查", notes="新增项目安全检查")
    @ApiImplicitParam(name = "zxSfCheck", value = "项目安全检查entity", dataType = "ZxSfCheck")
    @RequireToken
    @PostMapping("/addZxSfCheck")
    public ResponseEntity addZxSfCheck(@RequestBody(required = false) ZxSfCheck zxSfCheck) {
        return zxSfCheckService.saveZxSfCheck(zxSfCheck);
    }

    @ApiOperation(value="更新项目安全检查", notes="更新项目安全检查")
    @ApiImplicitParam(name = "zxSfCheck", value = "项目安全检查entity", dataType = "ZxSfCheck")
    @RequireToken
    @PostMapping("/updateZxSfCheck")
    public ResponseEntity updateZxSfCheck(@RequestBody(required = false) ZxSfCheck zxSfCheck) {
        return zxSfCheckService.updateZxSfCheck(zxSfCheck);
    }

    @ApiOperation(value="删除项目安全检查", notes="删除项目安全检查")
    @ApiImplicitParam(name = "zxSfCheckList", value = "项目安全检查List", dataType = "List<ZxSfCheck>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxSfCheck")
    public ResponseEntity batchDeleteUpdateZxSfCheck(@RequestBody(required = false) List<ZxSfCheck> zxSfCheckList) {
        return zxSfCheckService.batchDeleteUpdateZxSfCheck(zxSfCheckList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    
    // 项目上报
    @ApiOperation(value="上报项目安全检查", notes="上报项目安全检查")
    @ApiImplicitParam(name = "zxSfCheck", value = "项目安全检查entity", dataType = "ZxSfCheck")
    @RequireToken
    @PostMapping("/updateZxSfCheckIsReported")
    public ResponseEntity updateZxSfCheckIsReported(@RequestBody(required = false) ZxSfCheck zxSfCheck) {
        return zxSfCheckService.updateZxSfCheckIsReported(zxSfCheck);
    }
    
    // 项目复查
    @ApiOperation(value="复查项目安全检查", notes="复查项目安全检查")
    @ApiImplicitParam(name = "zxSfCheck", value = "项目安全检查entity", dataType = "ZxSfCheck")
    @RequireToken
    @PostMapping("/updateZxSfCheckCheckAgainStatus")
    public ResponseEntity updateZxSfCheckCheckAgainStatus(@RequestBody(required = false) ZxSfCheck zxSfCheck) {
        return zxSfCheckService.updateZxSfCheckCheckAgainStatus(zxSfCheck);
    }
    
    // 项目新查询
    @ApiOperation(value="新查询项目安全检查", notes="新查询项目安全检查")
    @ApiImplicitParam(name = "zxSfCheck", value = "项目安全检查entity", dataType = "ZxSfCheck")
    @RequireToken
    @PostMapping("/getZxSfCheckListAll")
    public ResponseEntity getZxSfCheckListAll(@RequestBody(required = false) ZxSfCheck zxSfCheck) {
        return zxSfCheckService.getZxSfCheckListAll(zxSfCheck);
    }
    
    // 公司上报
    @ApiOperation(value="上报公司安全检查", notes="上报公司安全检查")
    @ApiImplicitParam(name = "zxSfCheck", value = "项目安全检查entity", dataType = "ZxSfCheck")
    @RequireToken
    @PostMapping("/updateZxSfCheckIsReportedCompany")
    public ResponseEntity updateZxSfCheckIsReportedCompany(@RequestBody(required = false) ZxSfCheck zxSfCheck) {
        return zxSfCheckService.updateZxSfCheckIsReportedCompany(zxSfCheck);
    }
     
    // 公司复查
    @ApiOperation(value="复查公司安全检查", notes="复查公司安全检查")
    @ApiImplicitParam(name = "zxSfCheck", value = "项目安全检查entity", dataType = "ZxSfCheck")
    @RequireToken
    @PostMapping("/updateZxSfCheckCheckAgainStatusCompany")
    public ResponseEntity updateZxSfCheckCheckAgainStatusCompany(@RequestBody(required = false) ZxSfCheck zxSfCheck) {
        return zxSfCheckService.updateZxSfCheckCheckAgainStatusCompany(zxSfCheck);
    }
    
    // 公司新查询
    @ApiOperation(value="新查询公司安全检查", notes="新查询公司安全检查")
    @ApiImplicitParam(name = "zxSfCheck", value = "项目安全检查entity", dataType = "ZxSfCheck")
    @RequireToken
    @PostMapping("/getZxSfCheckListAllCompany")
    public ResponseEntity getZxSfCheckListAllCompany(@RequestBody(required = false) ZxSfCheck zxSfCheck) {
        return zxSfCheckService.getZxSfCheckListAllCompany(zxSfCheck);
    }
    
    // 公司下达
    @ApiOperation(value="下达公司安全检查", notes="下达公司安全检查")
    @ApiImplicitParam(name = "zxSfCheck", value = "项目安全检查entity", dataType = "ZxSfCheck")
    @RequireToken
    @PostMapping("/updateZxSfCheckIsSendCompany")
    public ResponseEntity updateZxSfCheckIsSendCompany(@RequestBody(required = false) ZxSfCheck zxSfCheck) {
        return zxSfCheckService.updateZxSfCheckIsSendCompany(zxSfCheck);
    }
    
    // 局复查
    @ApiOperation(value="复查局安全检查", notes="复查局安全检查")
    @ApiImplicitParam(name = "zxSfCheck", value = "项目安全检查entity", dataType = "ZxSfCheck")
    @RequireToken
    @PostMapping("/updateZxSfCheckAgainStatusBureau")
    public ResponseEntity updateZxSfCheckAgainStatusBureau(@RequestBody(required = false) ZxSfCheck zxSfCheck) {
        return zxSfCheckService.updateZxSfCheckAgainStatusBureau(zxSfCheck);
    }
    
    // 局新查询
    @ApiOperation(value="新查询局安全检查", notes="新查询局安全检查")
    @ApiImplicitParam(name = "zxSfCheck", value = "项目安全检查entity", dataType = "ZxSfCheck")
    @RequireToken
    @PostMapping("/getZxSfCheckListAllBureau")
    public ResponseEntity getZxSfCheckListAllBureau(@RequestBody(required = false) ZxSfCheck zxSfCheck) {
        return zxSfCheckService.getZxSfCheckListAllBureau(zxSfCheck);
    }
    
    // 局下达
    @ApiOperation(value="下达局安全检查", notes="下达局安全检查")
    @ApiImplicitParam(name = "zxSfCheck", value = "项目安全检查entity", dataType = "ZxSfCheck")
    @RequireToken
    @PostMapping("/updateZxSfCheckIsSendBureau")
    public ResponseEntity updateZxSfCheckIsSendBureau(@RequestBody(required = false) ZxSfCheck zxSfCheck) {
        return zxSfCheckService.updateZxSfCheckIsSendBureau(zxSfCheck);
    }

    @ApiOperation(value="公司信息安全检查查询", notes="公司信息安全检查查询")
    @ApiImplicitParam(name = "zxSfCheck", value = "项目安全检查entity", dataType = "zxSfCheck")
    @RequireToken
    @PostMapping("/getZxSfCheckCompany")
    public ResponseEntity getZxSfCheckCompany(@RequestBody(required = false) ZxSfCheck zxSfCheck){
        return zxSfCheckService.getCompany(zxSfCheck);
    }

    @ApiOperation(value="项目（状态区分）安全检查查询", notes="项目（状态区分）安全检查查询")
    @ApiImplicitParam(name = "zxSfCheck", value = "项目安全检查entity", dataType = "zxSfCheck")
    @RequireToken
    @PostMapping("/getZxSfCheckOrgList")
    public ResponseEntity getZxSfCheckOrgList(@RequestBody(required = false) ZxSfCheck zxSfCheck){
        return zxSfCheckService.getCheckList(zxSfCheck);
    }

    @ApiOperation(value="归档项目安全检查查询", notes="归档项目安全检查查询")
    @ApiImplicitParam(name = "zxSfCheck", value = "项目安全检查entity", dataType = "zxSfCheck")
    @RequireToken
    @PostMapping("/getZxSfCheckGuiDangList")
    public ResponseEntity getZxSfCheckGuiDangList(@RequestBody(required = false) ZxSfCheck zxSfCheck){
        return zxSfCheckService.getGuiDangList(zxSfCheck);
    }

    @ApiOperation(value="交工项目安全检查查询", notes="交工项目安全检查查询")
    @ApiImplicitParam(name = "zxSfCheck", value = "项目安全检查entity", dataType = "zxSfCheck")
    @RequireToken
    @PostMapping("/getZxSfCheckJiaoGongList")
    public ResponseEntity getZxSfCheckJiaoGongList(@RequestBody(required = false) ZxSfCheck zxSfCheck){
        return zxSfCheckService.getJiaoGongList(zxSfCheck);
    }

    @ApiOperation(value="完工项目安全检查查询", notes="完工项目安全检查查询")
    @ApiImplicitParam(name = "zxSfCheck", value = "项目安全检查entity", dataType = "zxSfCheck")
    @RequireToken
    @PostMapping("/getZxSfCheckWanGongList")
    public ResponseEntity getZxSfCheckWanGongList(@RequestBody(required = false) ZxSfCheck zxSfCheck){
        return zxSfCheckService.getWanGongList(zxSfCheck);
    }

    @ApiOperation(value="开工项目安全检查查询", notes="完工项目安全检查查询")
    @ApiImplicitParam(name = "zxSfCheck", value = "项目安全检查entity", dataType = "zxSfCheck")
    @RequireToken
    @PostMapping("/getZxSfCheckKaiGongList")
    public ResponseEntity getZxSfCheckKaiGongList(@RequestBody(required = false) ZxSfCheck zxSfCheck){
        return zxSfCheckService.getKaiGongList(zxSfCheck);
    }

    @ApiOperation(value="查询公司列表", notes="查询公司列表")
    @RequireToken
    @ApiImplicitParam(name = "zxSfCheck", value = "项目安全检查entity", dataType = "zxSfCheck")
    @PostMapping("/getZxSfCheckComList")
    public ResponseEntity getZxSfCheckComList(@RequestBody(required = false) ZxSfCheck zxSfCheck){
        return zxSfCheckService.getComList(zxSfCheck);
    }

    @ApiOperation(value="查询项目安全检查报表", notes="查询项目安全检查报表")
    @ApiImplicitParam(name = "zxSfCheck", value = "项目安全检查entity", dataType = "ZxSfCheck")
    @PostMapping("/getZxSfCheckListForm")
    public List<ZxSfCheck> getZxSfCheckListForm(@RequestBody(required = false) ZxSfCheck zxSfCheck) {
        return zxSfCheckService.getZxSfCheckListForm(zxSfCheck);
    }

    @ApiOperation(value="查询项目安全检查报表", notes="查询项目安全检查报表")
    @ApiImplicitParam(name = "zxSfCheck", value = "项目安全检查entity", dataType = "ZxSfCheck")
    @RequireToken
    @PostMapping("/getFormZxSfCheckList")
    public ResponseEntity getFormZxSfCheckList(@RequestBody(required = false) ZxSfCheck zxSfCheck) {
        return zxSfCheckService.getFormZxSfCheckList(zxSfCheck);
    }

    @ApiOperation(value="查询项目安全检查局信息)", notes="查询项目安全检查局信息")
    @ApiImplicitParam(name = "zxSfCheck", value = "项目安全检查entity", dataType = "ZxSfCheck")
    @RequireToken
    @PostMapping("/getFormZxSfCheckJuInfo")
    public ResponseEntity getFormZxSfCheckJuInfo(@RequestBody(required = false) ZxSfCheck zxSfCheck) {
        return zxSfCheckService.getFormJuInfo(zxSfCheck);
    }

}
