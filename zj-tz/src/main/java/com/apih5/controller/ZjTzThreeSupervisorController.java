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
import com.apih5.mybatis.pojo.ZjTzThreeSupervisor;
import com.apih5.service.ZjTzThreeSupervisorService;

@RestController
public class ZjTzThreeSupervisorController {

    @Autowired(required = true)
    private ZjTzThreeSupervisorService zjTzThreeSupervisorService;

    @ApiOperation(value="查询三会管理-监事会", notes="查询三会管理-监事会")
    @ApiImplicitParam(name = "zjTzThreeSupervisor", value = "三会管理-监事会entity", dataType = "ZjTzThreeSupervisor")
    @RequireToken
    @PostMapping("/getZjTzThreeSupervisorList")
    public ResponseEntity getZjTzThreeSupervisorList(@RequestBody(required = false) ZjTzThreeSupervisor zjTzThreeSupervisor) {
        return zjTzThreeSupervisorService.getZjTzThreeSupervisorListByCondition(zjTzThreeSupervisor);
    }

    @ApiOperation(value="查询详情三会管理-监事会", notes="查询详情三会管理-监事会")
    @ApiImplicitParam(name = "zjTzThreeSupervisor", value = "三会管理-监事会entity", dataType = "ZjTzThreeSupervisor")
    @RequireToken
    @PostMapping("/getZjTzThreeSupervisorDetails")
    public ResponseEntity getZjTzThreeSupervisorDetails(@RequestBody(required = false) ZjTzThreeSupervisor zjTzThreeSupervisor) {
        return zjTzThreeSupervisorService.getZjTzThreeSupervisorDetails(zjTzThreeSupervisor);
    }

    @ApiOperation(value="新增三会管理-监事会", notes="新增三会管理-监事会")
    @ApiImplicitParam(name = "zjTzThreeSupervisor", value = "三会管理-监事会entity", dataType = "ZjTzThreeSupervisor")
    @RequireToken
    @PostMapping("/addZjTzThreeSupervisor")
    public ResponseEntity addZjTzThreeSupervisor(@RequestBody(required = false) ZjTzThreeSupervisor zjTzThreeSupervisor) {
        return zjTzThreeSupervisorService.saveZjTzThreeSupervisor(zjTzThreeSupervisor);
    }

    @ApiOperation(value="更新三会管理-监事会", notes="更新三会管理-监事会")
    @ApiImplicitParam(name = "zjTzThreeSupervisor", value = "三会管理-监事会entity", dataType = "ZjTzThreeSupervisor")
    @RequireToken
    @PostMapping("/updateZjTzThreeSupervisor")
    public ResponseEntity updateZjTzThreeSupervisor(@RequestBody(required = false) ZjTzThreeSupervisor zjTzThreeSupervisor) {
        return zjTzThreeSupervisorService.updateZjTzThreeSupervisor(zjTzThreeSupervisor);
    }

    @ApiOperation(value="删除三会管理-监事会", notes="删除三会管理-监事会")
    @ApiImplicitParam(name = "zjTzThreeSupervisorList", value = "三会管理-监事会List", dataType = "List<ZjTzThreeSupervisor>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZjTzThreeSupervisor")
    public ResponseEntity batchDeleteUpdateZjTzThreeSupervisor(@RequestBody(required = false) List<ZjTzThreeSupervisor> zjTzThreeSupervisorList) {
        return zjTzThreeSupervisorService.batchDeleteUpdateZjTzThreeSupervisor(zjTzThreeSupervisorList);
    }
    
}