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
import com.apih5.mybatis.pojo.ZjTzSizeControl;
import com.apih5.service.ZjTzSizeControlService;

@RestController
public class ZjTzSizeControlController {

    @Autowired(required = true)
    private ZjTzSizeControlService zjTzSizeControlService;

    @ApiOperation(value="查询投资规模", notes="查询投资规模")
    @ApiImplicitParam(name = "zjTzSizeControl", value = "投资规模entity", dataType = "ZjTzSizeControl")
    @RequireToken
    @PostMapping("/getZjTzSizeControlList")
    public ResponseEntity getZjTzSizeControlList(@RequestBody(required = false) ZjTzSizeControl zjTzSizeControl) {
        return zjTzSizeControlService.getZjTzSizeControlListByCondition(zjTzSizeControl);
    }

    @ApiOperation(value="查询详情投资规模", notes="查询详情投资规模")
    @ApiImplicitParam(name = "zjTzSizeControl", value = "投资规模entity", dataType = "ZjTzSizeControl")
    @RequireToken
    @PostMapping("/getZjTzSizeControlDetails")
    public ResponseEntity getZjTzSizeControlDetails(@RequestBody(required = false) ZjTzSizeControl zjTzSizeControl) {
        return zjTzSizeControlService.getZjTzSizeControlDetails(zjTzSizeControl);
    }

    @ApiOperation(value="新增投资规模", notes="新增投资规模")
    @ApiImplicitParam(name = "zjTzSizeControl", value = "投资规模entity", dataType = "ZjTzSizeControl")
    @RequireToken
    @PostMapping("/addZjTzSizeControl")
    public ResponseEntity addZjTzSizeControl(@RequestBody(required = false) ZjTzSizeControl zjTzSizeControl) {
        return zjTzSizeControlService.saveZjTzSizeControl(zjTzSizeControl);
    }

    @ApiOperation(value="更新投资规模", notes="更新投资规模")
    @ApiImplicitParam(name = "zjTzSizeControl", value = "投资规模entity", dataType = "ZjTzSizeControl")
    @RequireToken
    @PostMapping("/updateZjTzSizeControl")
    public ResponseEntity updateZjTzSizeControl(@RequestBody(required = false) ZjTzSizeControl zjTzSizeControl) {
        return zjTzSizeControlService.updateZjTzSizeControl(zjTzSizeControl);
    }

    @ApiOperation(value="删除投资规模", notes="删除投资规模")
    @ApiImplicitParam(name = "zjTzSizeControlList", value = "投资规模List", dataType = "List<ZjTzSizeControl>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZjTzSizeControl")
    public ResponseEntity batchDeleteUpdateZjTzSizeControl(@RequestBody(required = false) List<ZjTzSizeControl> zjTzSizeControlList) {
        return zjTzSizeControlService.batchDeleteUpdateZjTzSizeControl(zjTzSizeControlList);
    }

}
