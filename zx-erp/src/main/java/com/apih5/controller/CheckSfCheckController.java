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
import com.apih5.mybatis.pojo.CheckSfCheck;
import com.apih5.service.CheckSfCheckService;

@RestController
public class CheckSfCheckController {

    @Autowired(required = true)
    private CheckSfCheckService checkSfCheckService;

    @ApiOperation(value="查询安全检查查询", notes="查询安全检查查询")
    @ApiImplicitParam(name = "checkSfCheck", value = "安全检查查询entity", dataType = "CheckSfCheck")
    @RequireToken
    @PostMapping("/getCheckSfCheckList")
    public ResponseEntity getCheckSfCheckList(@RequestBody(required = false) CheckSfCheck checkSfCheck) {
        return checkSfCheckService.getCheckSfCheckListByCondition(checkSfCheck);
    }

    @ApiOperation(value="查询详情安全检查查询", notes="查询详情安全检查查询")
    @ApiImplicitParam(name = "checkSfCheck", value = "安全检查查询entity", dataType = "CheckSfCheck")
    @RequireToken
    @PostMapping("/getCheckSfCheckDetail")
    public ResponseEntity getCheckSfCheckDetail(@RequestBody(required = false) CheckSfCheck checkSfCheck) {
        return checkSfCheckService.getCheckSfCheckDetail(checkSfCheck);
    }

    @ApiOperation(value="新增安全检查查询", notes="新增安全检查查询")
    @ApiImplicitParam(name = "checkSfCheck", value = "安全检查查询entity", dataType = "CheckSfCheck")
    @RequireToken
    @PostMapping("/addCheckSfCheck")
    public ResponseEntity addCheckSfCheck(@RequestBody(required = false) CheckSfCheck checkSfCheck) {
        return checkSfCheckService.saveCheckSfCheck(checkSfCheck);
    }

    @ApiOperation(value="更新安全检查查询", notes="更新安全检查查询")
    @ApiImplicitParam(name = "checkSfCheck", value = "安全检查查询entity", dataType = "CheckSfCheck")
    @RequireToken
    @PostMapping("/updateCheckSfCheck")
    public ResponseEntity updateCheckSfCheck(@RequestBody(required = false) CheckSfCheck checkSfCheck) {
        return checkSfCheckService.updateCheckSfCheck(checkSfCheck);
    }

    @ApiOperation(value="删除安全检查查询", notes="删除安全检查查询")
    @ApiImplicitParam(name = "checkSfCheckList", value = "安全检查查询List", dataType = "List<CheckSfCheck>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateCheckSfCheck")
    public ResponseEntity batchDeleteUpdateCheckSfCheck(@RequestBody(required = false) List<CheckSfCheck> checkSfCheckList) {
        return checkSfCheckService.batchDeleteUpdateCheckSfCheck(checkSfCheckList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

    @ApiOperation(value="公司信息安全检查查询", notes="公司信息安全检查查询")
    @ApiImplicitParam(name = "checkSfCheck", value = "安全检查查询entity", dataType = "CheckSfCheck")
    @RequireToken
    @PostMapping("/getCheckSfCheckCompany")
    public ResponseEntity getCheckSfCheckCompany(@RequestBody(required = false) CheckSfCheck checkSfCheck){
       return checkSfCheckService.getCompany(checkSfCheck);
    }

    @ApiOperation(value="项目（状态区分）安全检查查询", notes="项目（状态区分）安全检查查询")
    @ApiImplicitParam(name = "checkSfCheck", value = "安全检查查询entity", dataType = "CheckSfCheck")
    @RequireToken
    @PostMapping("/getCheckSfCheckCheckList")
    public ResponseEntity getCheckSfCheckCheckList(@RequestBody(required = false) CheckSfCheck checkSfCheck){
        return checkSfCheckService.getCheckList(checkSfCheck);
    }

    @ApiOperation(value="归档查询", notes="归档项目安全检查查询")
    @ApiImplicitParam(name = "checkSfCheck", value = "安全检查查询entity", dataType = "CheckSfCheck")
    @RequireToken
    @PostMapping("/getCheckSfCheckGuiDangList")
    public ResponseEntity getCheckSfCheckGuiDangList(@RequestBody(required = false) CheckSfCheck checkSfCheck){
        return checkSfCheckService.getGuiDangList(checkSfCheck);
    }

    @ApiOperation(value="交工项目安全检查查询", notes="交工项目安全检查查询")
    @ApiImplicitParam(name = "checkSfCheck", value = "安全检查查询entity", dataType = "CheckSfCheck")
    @RequireToken
    @PostMapping("/getCheckSfCheckJiaoGongList")
    public ResponseEntity getCheckSfCheckJiaoGongList(@RequestBody(required = false) CheckSfCheck checkSfCheck){
        return checkSfCheckService.getJiaoGongList(checkSfCheck);
    }

    @ApiOperation(value="完工项目安全检查查询", notes="完工项目安全检查查询")
    @ApiImplicitParam(name = "checkSfCheck", value = "安全检查查询entity", dataType = "CheckSfCheck")
    @RequireToken
    @PostMapping("/getCheckSfCheckWanGongList")
    public ResponseEntity getCheckSfCheckWanGongList(@RequestBody(required = false) CheckSfCheck checkSfCheck){
        return checkSfCheckService.getWanGongList(checkSfCheck);
    }

    @ApiOperation(value="完工项目安全检查查询", notes="完工项目安全检查查询")
    @ApiImplicitParam(name = "checkSfCheck", value = "安全检查查询entity", dataType = "CheckSfCheck")
    @RequireToken
    @PostMapping("/getCheckSfCheckKaiGongList")
    public ResponseEntity getCheckSfCheckKaiGongList(@RequestBody(required = false) CheckSfCheck checkSfCheck){
        return checkSfCheckService.getKaiGongList(checkSfCheck);
    }

    @ApiOperation(value="查询公司列表", notes="查询公司列表")
    @ApiImplicitParam(name = "checkSfCheck", value = "安全检查查询entity", dataType = "CheckSfCheck")
    @RequireToken
    @PostMapping("/getCheckSfCheckComList")
    public ResponseEntity getCheckSfCheckComList(@RequestBody(required = false) CheckSfCheck checkSfCheck){
        return checkSfCheckService.getComList(checkSfCheck);
    }


}
