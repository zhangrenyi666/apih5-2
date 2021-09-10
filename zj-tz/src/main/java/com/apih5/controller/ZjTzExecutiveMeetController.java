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
import com.apih5.mybatis.pojo.ZjTzExecutiveMeet;
import com.apih5.service.ZjTzExecutiveMeetService;

@RestController
public class ZjTzExecutiveMeetController {

    @Autowired(required = true)
    private ZjTzExecutiveMeetService zjTzExecutiveMeetService;

    @ApiOperation(value="查询董监高会", notes="查询董监高会")
    @ApiImplicitParam(name = "zjTzExecutiveMeet", value = "董监高会entity", dataType = "ZjTzExecutiveMeet")
    @RequireToken
    @PostMapping("/getZjTzExecutiveMeetList")
    public ResponseEntity getZjTzExecutiveMeetList(@RequestBody(required = false) ZjTzExecutiveMeet zjTzExecutiveMeet) {
        return zjTzExecutiveMeetService.getZjTzExecutiveMeetListByCondition(zjTzExecutiveMeet);
    }

    @ApiOperation(value="查询详情董监高会", notes="查询详情董监高会")
    @ApiImplicitParam(name = "zjTzExecutiveMeet", value = "董监高会entity", dataType = "ZjTzExecutiveMeet")
    @RequireToken
    @PostMapping("/getZjTzExecutiveMeetDetails")
    public ResponseEntity getZjTzExecutiveMeetDetails(@RequestBody(required = false) ZjTzExecutiveMeet zjTzExecutiveMeet) {
        return zjTzExecutiveMeetService.getZjTzExecutiveMeetDetails(zjTzExecutiveMeet);
    }

    @ApiOperation(value="新增董监高会", notes="新增董监高会")
    @ApiImplicitParam(name = "zjTzExecutiveMeet", value = "董监高会entity", dataType = "ZjTzExecutiveMeet")
    @RequireToken
    @PostMapping("/addZjTzExecutiveMeet")
    public ResponseEntity addZjTzExecutiveMeet(@RequestBody(required = false) ZjTzExecutiveMeet zjTzExecutiveMeet) {
        return zjTzExecutiveMeetService.saveZjTzExecutiveMeet(zjTzExecutiveMeet);
    }

    @ApiOperation(value="更新董监高会", notes="更新董监高会")
    @ApiImplicitParam(name = "zjTzExecutiveMeet", value = "董监高会entity", dataType = "ZjTzExecutiveMeet")
    @RequireToken
    @PostMapping("/updateZjTzExecutiveMeet")
    public ResponseEntity updateZjTzExecutiveMeet(@RequestBody(required = false) ZjTzExecutiveMeet zjTzExecutiveMeet) {
        return zjTzExecutiveMeetService.updateZjTzExecutiveMeet(zjTzExecutiveMeet);
    }

    @ApiOperation(value="删除董监高会", notes="删除董监高会")
    @ApiImplicitParam(name = "zjTzExecutiveMeetList", value = "董监高会List", dataType = "List<ZjTzExecutiveMeet>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZjTzExecutiveMeet")
    public ResponseEntity batchDeleteUpdateZjTzExecutiveMeet(@RequestBody(required = false) List<ZjTzExecutiveMeet> zjTzExecutiveMeetList) {
        return zjTzExecutiveMeetService.batchDeleteUpdateZjTzExecutiveMeet(zjTzExecutiveMeetList);
    }
    
    @ApiOperation(value="新增修改数据时打开抽屉查询的接口", notes="新增修改数据时打开抽屉查询的接口")
    @ApiImplicitParam(name = "zjTzExecutiveMeet", value = "董监高会entity", dataType = "ZjTzExecutiveMeet")
    @RequireToken
    @PostMapping("/getZjTzExecutiveMeetListFromProjectShareholder")
    public ResponseEntity getZjTzExecutiveMeetListFromProjectShareholder(@RequestBody(required = false) ZjTzExecutiveMeet zjTzExecutiveMeet) {
        return zjTzExecutiveMeetService.getZjTzExecutiveMeetListFromProjectShareholder(zjTzExecutiveMeet);
    }
    
    @ApiOperation(value="变更董监高会", notes="变更董监高会")
    @ApiImplicitParam(name = "zjTzExecutiveMeet", value = "董监高会entity", dataType = "ZjTzExecutiveMeet")
    @RequireToken
    @PostMapping("/changeZjTzExecutiveMeet")
    public ResponseEntity changeZjTzExecutiveMeet(@RequestBody(required = false) ZjTzExecutiveMeet zjTzExecutiveMeet) {
        return zjTzExecutiveMeetService.changeZjTzExecutiveMeet(zjTzExecutiveMeet);
    }
    
    @ApiOperation(value="上报董监高会", notes="上报董监高会")
    @ApiImplicitParam(name = "zjTzExecutiveMeet", value = "董监高会entity", dataType = "ZjTzExecutiveMeet")
    @RequireToken
    @PostMapping("/correctiveZjTzExecutiveMeet")
    public ResponseEntity correctiveZjTzExecutiveMeet(@RequestBody(required = false) ZjTzExecutiveMeet zjTzExecutiveMeet) {
        return zjTzExecutiveMeetService.correctiveZjTzExecutiveMeet(zjTzExecutiveMeet);
    }

}