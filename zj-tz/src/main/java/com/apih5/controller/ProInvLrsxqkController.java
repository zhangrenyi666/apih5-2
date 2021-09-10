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
import com.apih5.mybatis.pojo.ProInvLrsxqk;
import com.apih5.service.ProInvLrsxqkService;

@RestController
public class ProInvLrsxqkController {

    @Autowired(required = true)
    private ProInvLrsxqkService proInvLrsxqkService;

    @ApiOperation(value="查询项目管理", notes="查询项目管理")
    @ApiImplicitParam(name = "proInvLrsxqk", value = "项目管理entity", dataType = "ProInvLrsxqk")
    @RequireToken
    @PostMapping("/getProInvLrsxqkList")
    public ResponseEntity getProInvLrsxqkList(@RequestBody(required = false) ProInvLrsxqk proInvLrsxqk) {
        return proInvLrsxqkService.getProInvLrsxqkListByCondition(proInvLrsxqk);
    }

    @ApiOperation(value="查询详情项目管理", notes="查询详情项目管理")
    @ApiImplicitParam(name = "proInvLrsxqk", value = "项目管理entity", dataType = "ProInvLrsxqk")
    @RequireToken
    @PostMapping("/getProInvLrsxqkDetails")
    public ResponseEntity getProInvLrsxqkDetails(@RequestBody(required = false) ProInvLrsxqk proInvLrsxqk) {
        return proInvLrsxqkService.getProInvLrsxqkDetails(proInvLrsxqk);
    }

    @ApiOperation(value="新增项目管理", notes="新增项目管理")
    @ApiImplicitParam(name = "proInvLrsxqk", value = "项目管理entity", dataType = "ProInvLrsxqk")
    @RequireToken
    @PostMapping("/addProInvLrsxqk")
    public ResponseEntity addProInvLrsxqk(@RequestBody(required = false) ProInvLrsxqk proInvLrsxqk) {
        return proInvLrsxqkService.saveProInvLrsxqk(proInvLrsxqk);
    }

    @ApiOperation(value="更新项目管理", notes="更新项目管理")
    @ApiImplicitParam(name = "proInvLrsxqk", value = "项目管理entity", dataType = "ProInvLrsxqk")
    @RequireToken
    @PostMapping("/updateProInvLrsxqk")
    public ResponseEntity updateProInvLrsxqk(@RequestBody(required = false) ProInvLrsxqk proInvLrsxqk) {
        return proInvLrsxqkService.updateProInvLrsxqk(proInvLrsxqk);
    }

    @ApiOperation(value="删除项目管理", notes="删除项目管理")
    @ApiImplicitParam(name = "proInvLrsxqkList", value = "项目管理List", dataType = "List<ProInvLrsxqk>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateProInvLrsxqk")
    public ResponseEntity batchDeleteUpdateProInvLrsxqk(@RequestBody(required = false) List<ProInvLrsxqk> proInvLrsxqkList) {
        return proInvLrsxqkService.batchDeleteUpdateProInvLrsxqk(proInvLrsxqkList);
    }

}
