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
import com.apih5.mybatis.pojo.ZjTzThreeDirector;
import com.apih5.service.ZjTzThreeDirectorService;

@RestController
public class ZjTzThreeDirectorController {

    @Autowired(required = true)
    private ZjTzThreeDirectorService zjTzThreeDirectorService;

    @ApiOperation(value="查询三会管理-董事会", notes="查询三会管理-董事会")
    @ApiImplicitParam(name = "zjTzThreeDirector", value = "三会管理-董事会entity", dataType = "ZjTzThreeDirector")
    @RequireToken
    @PostMapping("/getZjTzThreeDirectorList")
    public ResponseEntity getZjTzThreeDirectorList(@RequestBody(required = false) ZjTzThreeDirector zjTzThreeDirector) {
        return zjTzThreeDirectorService.getZjTzThreeDirectorListByCondition(zjTzThreeDirector);
    }

    @ApiOperation(value="查询详情三会管理-董事会", notes="查询详情三会管理-董事会")
    @ApiImplicitParam(name = "zjTzThreeDirector", value = "三会管理-董事会entity", dataType = "ZjTzThreeDirector")
    @RequireToken
    @PostMapping("/getZjTzThreeDirectorDetails")
    public ResponseEntity getZjTzThreeDirectorDetails(@RequestBody(required = false) ZjTzThreeDirector zjTzThreeDirector) {
        return zjTzThreeDirectorService.getZjTzThreeDirectorDetails(zjTzThreeDirector);
    }

    @ApiOperation(value="新增三会管理-董事会", notes="新增三会管理-董事会")
    @ApiImplicitParam(name = "zjTzThreeDirector", value = "三会管理-董事会entity", dataType = "ZjTzThreeDirector")
    @RequireToken
    @PostMapping("/addZjTzThreeDirector")
    public ResponseEntity addZjTzThreeDirector(@RequestBody(required = false) ZjTzThreeDirector zjTzThreeDirector) {
        return zjTzThreeDirectorService.saveZjTzThreeDirector(zjTzThreeDirector);
    }

    @ApiOperation(value="更新三会管理-董事会", notes="更新三会管理-董事会")
    @ApiImplicitParam(name = "zjTzThreeDirector", value = "三会管理-董事会entity", dataType = "ZjTzThreeDirector")
    @RequireToken
    @PostMapping("/updateZjTzThreeDirector")
    public ResponseEntity updateZjTzThreeDirector(@RequestBody(required = false) ZjTzThreeDirector zjTzThreeDirector) {
        return zjTzThreeDirectorService.updateZjTzThreeDirector(zjTzThreeDirector);
    }

    @ApiOperation(value="删除三会管理-董事会", notes="删除三会管理-董事会")
    @ApiImplicitParam(name = "zjTzThreeDirectorList", value = "三会管理-董事会List", dataType = "List<ZjTzThreeDirector>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZjTzThreeDirector")
    public ResponseEntity batchDeleteUpdateZjTzThreeDirector(@RequestBody(required = false) List<ZjTzThreeDirector> zjTzThreeDirectorList) {
        return zjTzThreeDirectorService.batchDeleteUpdateZjTzThreeDirector(zjTzThreeDirectorList);
    }
    
}