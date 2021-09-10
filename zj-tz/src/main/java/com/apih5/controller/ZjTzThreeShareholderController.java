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
import com.apih5.mybatis.pojo.ZjTzThreeShareholder;
import com.apih5.service.ZjTzThreeShareholderService;

@RestController
public class ZjTzThreeShareholderController {

    @Autowired(required = true)
    private ZjTzThreeShareholderService zjTzThreeShareholderService;

    @ApiOperation(value="查询三会管理-股东会", notes="查询三会管理-股东会")
    @ApiImplicitParam(name = "zjTzThreeShareholder", value = "三会管理-股东会entity", dataType = "ZjTzThreeShareholder")
    @RequireToken
    @PostMapping("/getZjTzThreeShareholderList")
    public ResponseEntity getZjTzThreeShareholderList(@RequestBody(required = false) ZjTzThreeShareholder zjTzThreeShareholder) {
        return zjTzThreeShareholderService.getZjTzThreeShareholderListByCondition(zjTzThreeShareholder);
    }

    @ApiOperation(value="查询详情三会管理-股东会", notes="查询详情三会管理-股东会")
    @ApiImplicitParam(name = "zjTzThreeShareholder", value = "三会管理-股东会entity", dataType = "ZjTzThreeShareholder")
    @RequireToken
    @PostMapping("/getZjTzThreeShareholderDetails")
    public ResponseEntity getZjTzThreeShareholderDetails(@RequestBody(required = false) ZjTzThreeShareholder zjTzThreeShareholder) {
        return zjTzThreeShareholderService.getZjTzThreeShareholderDetails(zjTzThreeShareholder);
    }

    @ApiOperation(value="新增三会管理-股东会", notes="新增三会管理-股东会")
    @ApiImplicitParam(name = "zjTzThreeShareholder", value = "三会管理-股东会entity", dataType = "ZjTzThreeShareholder")
    @RequireToken
    @PostMapping("/addZjTzThreeShareholder")
    public ResponseEntity addZjTzThreeShareholder(@RequestBody(required = false) ZjTzThreeShareholder zjTzThreeShareholder) {
        return zjTzThreeShareholderService.saveZjTzThreeShareholder(zjTzThreeShareholder);
    }

    @ApiOperation(value="更新三会管理-股东会", notes="更新三会管理-股东会")
    @ApiImplicitParam(name = "zjTzThreeShareholder", value = "三会管理-股东会entity", dataType = "ZjTzThreeShareholder")
    @RequireToken
    @PostMapping("/updateZjTzThreeShareholder")
    public ResponseEntity updateZjTzThreeShareholder(@RequestBody(required = false) ZjTzThreeShareholder zjTzThreeShareholder) {
        return zjTzThreeShareholderService.updateZjTzThreeShareholder(zjTzThreeShareholder);
    }

    @ApiOperation(value="删除三会管理-股东会", notes="删除三会管理-股东会")
    @ApiImplicitParam(name = "zjTzThreeShareholderList", value = "三会管理-股东会List", dataType = "List<ZjTzThreeShareholder>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZjTzThreeShareholder")
    public ResponseEntity batchDeleteUpdateZjTzThreeShareholder(@RequestBody(required = false) List<ZjTzThreeShareholder> zjTzThreeShareholderList) {
        return zjTzThreeShareholderService.batchDeleteUpdateZjTzThreeShareholder(zjTzThreeShareholderList);
    }
    
}