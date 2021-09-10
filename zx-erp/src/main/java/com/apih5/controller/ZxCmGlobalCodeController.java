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
import com.apih5.mybatis.pojo.ZxCmGlobalCode;
import com.apih5.service.ZxCmGlobalCodeService;

@RestController
public class ZxCmGlobalCodeController {

    @Autowired(required = true)
    private ZxCmGlobalCodeService zxCmGlobalCodeService;

    @ApiOperation(value="查询基础代码", notes="查询基础代码")
    @ApiImplicitParam(name = "zxCmGlobalCode", value = "基础代码entity", dataType = "ZxCmGlobalCode")
    @RequireToken
    @PostMapping("/getZxCmGlobalCodeList")
    public ResponseEntity getZxCmGlobalCodeList(@RequestBody(required = false) ZxCmGlobalCode zxCmGlobalCode) {
        return zxCmGlobalCodeService.getZxCmGlobalCodeListByCondition(zxCmGlobalCode);
    }

    @ApiOperation(value="查询详情基础代码", notes="查询详情基础代码")
    @ApiImplicitParam(name = "zxCmGlobalCode", value = "基础代码entity", dataType = "ZxCmGlobalCode")
    @RequireToken
    @PostMapping("/getZxCmGlobalCodeDetail")
    public ResponseEntity getZxCmGlobalCodeDetail(@RequestBody(required = false) ZxCmGlobalCode zxCmGlobalCode) {
        return zxCmGlobalCodeService.getZxCmGlobalCodeDetail(zxCmGlobalCode);
    }

    @ApiOperation(value="新增基础代码", notes="新增基础代码")
    @ApiImplicitParam(name = "zxCmGlobalCode", value = "基础代码entity", dataType = "ZxCmGlobalCode")
    @RequireToken
    @PostMapping("/addZxCmGlobalCode")
    public ResponseEntity addZxCmGlobalCode(@RequestBody(required = false) ZxCmGlobalCode zxCmGlobalCode) {
        return zxCmGlobalCodeService.saveZxCmGlobalCode(zxCmGlobalCode);
    }

    @ApiOperation(value="更新基础代码", notes="更新基础代码")
    @ApiImplicitParam(name = "zxCmGlobalCode", value = "基础代码entity", dataType = "ZxCmGlobalCode")
    @RequireToken
    @PostMapping("/updateZxCmGlobalCode")
    public ResponseEntity updateZxCmGlobalCode(@RequestBody(required = false) ZxCmGlobalCode zxCmGlobalCode) {
        return zxCmGlobalCodeService.updateZxCmGlobalCode(zxCmGlobalCode);
    }

    @ApiOperation(value="删除基础代码", notes="删除基础代码")
    @ApiImplicitParam(name = "zxCmGlobalCodeList", value = "基础代码List", dataType = "List<ZxCmGlobalCode>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxCmGlobalCode")
    public ResponseEntity batchDeleteUpdateZxCmGlobalCode(@RequestBody(required = false) List<ZxCmGlobalCode> zxCmGlobalCodeList) {
        return zxCmGlobalCodeService.batchDeleteUpdateZxCmGlobalCode(zxCmGlobalCodeList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
