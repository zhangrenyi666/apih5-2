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
import com.apih5.mybatis.pojo.ZxCcConstCoAlter;
import com.apih5.service.ZxCcConstCoAlterService;

@RestController
public class ZxCcConstCoAlterController {

    @Autowired(required = true)
    private ZxCcConstCoAlterService zxCcConstCoAlterService;

    @ApiOperation(value="查询工程施工类补充协议", notes="查询工程施工类补充协议")
    @ApiImplicitParam(name = "zxCcConstCoAlter", value = "工程施工类补充协议entity", dataType = "ZxCcConstCoAlter")
    @RequireToken
    @PostMapping("/getZxCcConstCoAlterList")
    public ResponseEntity getZxCcConstCoAlterList(@RequestBody(required = false) ZxCcConstCoAlter zxCcConstCoAlter) {
        return zxCcConstCoAlterService.getZxCcConstCoAlterListByCondition(zxCcConstCoAlter);
    }

    @ApiOperation(value="查询详情工程施工类补充协议", notes="查询详情工程施工类补充协议")
    @ApiImplicitParam(name = "zxCcConstCoAlter", value = "工程施工类补充协议entity", dataType = "ZxCcConstCoAlter")
    @RequireToken
    @PostMapping("/getZxCcConstCoAlterDetail")
    public ResponseEntity getZxCcConstCoAlterDetail(@RequestBody(required = false) ZxCcConstCoAlter zxCcConstCoAlter) {
        return zxCcConstCoAlterService.getZxCcConstCoAlterDetail(zxCcConstCoAlter);
    }

    @ApiOperation(value="新增工程施工类补充协议", notes="新增工程施工类补充协议")
    @ApiImplicitParam(name = "zxCcConstCoAlter", value = "工程施工类补充协议entity", dataType = "ZxCcConstCoAlter")
    @RequireToken
    @PostMapping("/addZxCcConstCoAlter")
    public ResponseEntity addZxCcConstCoAlter(@RequestBody(required = false) ZxCcConstCoAlter zxCcConstCoAlter) {
        return zxCcConstCoAlterService.saveZxCcConstCoAlter(zxCcConstCoAlter);
    }

    @ApiOperation(value="更新工程施工类补充协议", notes="更新工程施工类补充协议")
    @ApiImplicitParam(name = "zxCcConstCoAlter", value = "工程施工类补充协议entity", dataType = "ZxCcConstCoAlter")
    @RequireToken
    @PostMapping("/updateZxCcConstCoAlter")
    public ResponseEntity updateZxCcConstCoAlter(@RequestBody(required = false) ZxCcConstCoAlter zxCcConstCoAlter) {
        return zxCcConstCoAlterService.updateZxCcConstCoAlter(zxCcConstCoAlter);
    }

    @ApiOperation(value="删除工程施工类补充协议", notes="删除工程施工类补充协议")
    @ApiImplicitParam(name = "zxCcConstCoAlterList", value = "工程施工类补充协议List", dataType = "List<ZxCcConstCoAlter>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxCcConstCoAlter")
    public ResponseEntity batchDeleteUpdateZxCcConstCoAlter(@RequestBody(required = false) List<ZxCcConstCoAlter> zxCcConstCoAlterList) {
        return zxCcConstCoAlterService.batchDeleteUpdateZxCcConstCoAlter(zxCcConstCoAlterList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

    @ApiOperation(value="评审状态为“正在评审、评审通过、评审不通过”时，查看工程施工类补充协议清单明细", notes="查看工程施工类补充协议清单明细")
    @ApiImplicitParam(name = "zxCcConstCoAlter", value = "工程施工类补充协议entity", dataType = "ZxCcConstCoAlter")
    @RequireToken
    @PostMapping("/getZxCcConstCoAlterWorkDetailList")
    public ResponseEntity getZxCcConstCoAlterWorkDetailList(@RequestBody(required = false) ZxCcConstCoAlter zxCcConstCoAlter) {
        return zxCcConstCoAlterService.getZxCcConstCoAlterWorkDetail(zxCcConstCoAlter);
    }

    @ApiOperation(value="评审状态为“未评审”时，编辑工程施工类补充协议清单明细", notes="编辑工程施工类补充协议清单明细")
    @ApiImplicitParam(name = "zxCcConstCoAlter", value = "工程施工类补充协议entity", dataType = "ZxCcConstCoAlter")
    @RequireToken
    @PostMapping("/updateZxCcConstCoAlterAndWorkDetail")
    public ResponseEntity updateZxCcConstCoAlterAndWorkDetail(@RequestBody(required = false) ZxCcConstCoAlter zxCcConstCoAlter) {
        return zxCcConstCoAlterService.updateZxCcConstCoAlterAndWorkDetail(zxCcConstCoAlter);
    }
}
