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
import com.apih5.mybatis.pojo.ZjTzBaseCode;
import com.apih5.service.ZjTzBaseCodeService;

@RestController
public class ZjTzBaseCodeController {

    @Autowired(required = true)
    private ZjTzBaseCodeService zjTzBaseCodeService;

    @ApiOperation(value="外层查询数据字典", notes="外层查询数据字典")
    @ApiImplicitParam(name = "zjTzBaseCode", value = "数据字典entity", dataType = "ZjTzBaseCode")
    @RequireToken
    @PostMapping("/getZjTzBaseCodeList")
    public ResponseEntity getZjTzBaseCodeList(@RequestBody(required = false) ZjTzBaseCode zjTzBaseCode) {
        return zjTzBaseCodeService.getZjTzBaseCodeListByCondition(zjTzBaseCode);
    }

    @ApiOperation(value="查询详情数据字典", notes="查询详情数据字典")
    @ApiImplicitParam(name = "zjTzBaseCode", value = "数据字典entity", dataType = "ZjTzBaseCode")
    @RequireToken
    @PostMapping("/getZjTzBaseCodeDetails")
    public ResponseEntity getZjTzBaseCodeDetails(@RequestBody(required = false) ZjTzBaseCode zjTzBaseCode) {
        return zjTzBaseCodeService.getZjTzBaseCodeDetails(zjTzBaseCode);
    }

    @ApiOperation(value="新增数据字典", notes="新增数据字典")
    @ApiImplicitParam(name = "zjTzBaseCode", value = "数据字典entity", dataType = "ZjTzBaseCode")
    @RequireToken
    @PostMapping("/addZjTzBaseCode")
    public ResponseEntity addZjTzBaseCode(@RequestBody(required = false) ZjTzBaseCode zjTzBaseCode) {
        return zjTzBaseCodeService.saveZjTzBaseCode(zjTzBaseCode);
    }

    @ApiOperation(value="更新数据字典", notes="更新数据字典")
    @ApiImplicitParam(name = "zjTzBaseCode", value = "数据字典entity", dataType = "ZjTzBaseCode")
    @RequireToken
    @PostMapping("/updateZjTzBaseCode")
    public ResponseEntity updateZjTzBaseCode(@RequestBody(required = false) ZjTzBaseCode zjTzBaseCode) {
        return zjTzBaseCodeService.updateZjTzBaseCode(zjTzBaseCode);
    }

    @ApiOperation(value="删除数据字典", notes="删除数据字典")
    @ApiImplicitParam(name = "zjTzBaseCodeList", value = "数据字典List", dataType = "List<ZjTzBaseCode>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZjTzBaseCode")
    public ResponseEntity batchDeleteUpdateZjTzBaseCode(@RequestBody(required = false) List<ZjTzBaseCode> zjTzBaseCodeList) throws Exception {
        return zjTzBaseCodeService.batchDeleteUpdateZjTzBaseCode(zjTzBaseCodeList);
    }

}
