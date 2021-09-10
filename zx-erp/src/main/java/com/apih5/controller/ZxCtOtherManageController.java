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
import com.apih5.mybatis.pojo.ZxCtOtherManage;
import com.apih5.service.ZxCtOtherManageService;

import javax.servlet.http.HttpServletResponse;

@RestController
public class ZxCtOtherManageController {

    @Autowired(required = true)
    private ZxCtOtherManageService zxCtOtherManageService;

    @ApiOperation(value="查询其他合同管理", notes="查询其他合同管理")
    @ApiImplicitParam(name = "zxCtOtherManage", value = "其他合同管理entity", dataType = "ZxCtOtherManage")
    @RequireToken
    @PostMapping("/getZxCtOtherManageList")
    public ResponseEntity getZxCtOtherManageList(@RequestBody(required = false) ZxCtOtherManage zxCtOtherManage) {
        return zxCtOtherManageService.getZxCtOtherManageListByCondition(zxCtOtherManage);
    }

    @ApiOperation(value="查询详情其他合同管理", notes="查询详情其他合同管理")
    @ApiImplicitParam(name = "zxCtOtherManage", value = "其他合同管理entity", dataType = "ZxCtOtherManage")
    @RequireToken
    @PostMapping("/getZxCtOtherManageDetail")
    public ResponseEntity getZxCtOtherManageDetail(@RequestBody(required = false) ZxCtOtherManage zxCtOtherManage) {
        return zxCtOtherManageService.getZxCtOtherManageDetail(zxCtOtherManage);
    }

    @ApiOperation(value="新增其他合同管理", notes="新增其他合同管理")
    @ApiImplicitParam(name = "zxCtOtherManage", value = "其他合同管理entity", dataType = "ZxCtOtherManage")
    @RequireToken
    @PostMapping("/addZxCtOtherManage")
    public ResponseEntity addZxCtOtherManage(@RequestBody(required = false) ZxCtOtherManage zxCtOtherManage) {
        return zxCtOtherManageService.saveZxCtOtherManage(zxCtOtherManage);
    }

    @ApiOperation(value="更新其他合同管理", notes="更新其他合同管理")
    @ApiImplicitParam(name = "zxCtOtherManage", value = "其他合同管理entity", dataType = "ZxCtOtherManage")
    @RequireToken
    @PostMapping("/updateZxCtOtherManage")
    public ResponseEntity updateZxCtOtherManage(@RequestBody(required = false) ZxCtOtherManage zxCtOtherManage) {
        return zxCtOtherManageService.updateZxCtOtherManage(zxCtOtherManage);
    }

    @ApiOperation(value="删除其他合同管理", notes="删除其他合同管理")
    @ApiImplicitParam(name = "zxCtOtherManageList", value = "其他合同管理List", dataType = "List<ZxCtOtherManage>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxCtOtherManage")
    public ResponseEntity batchDeleteUpdateZxCtOtherManage(@RequestBody(required = false) List<ZxCtOtherManage> zxCtOtherManageList) {
        return zxCtOtherManageService.batchDeleteUpdateZxCtOtherManage(zxCtOtherManageList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

    @ApiOperation(value="其他合同管理恢复执行", notes="其他合同管理恢复执行")
    @ApiImplicitParam(name = "zxCtOtherManage", value = "其他合同管理entity", dataType = "ZxCtOtherManage")
    @RequireToken
    @PostMapping("/zxCtOtherManageRegainExecute")
    public ResponseEntity zxCtOtherManageRegainExecute(@RequestBody(required = false) ZxCtOtherManage zxCtOtherManage) {
        return zxCtOtherManageService.zxCtOtherManageRegainExecute(zxCtOtherManage);
    }

    @ApiOperation(value="其他合同管理导出", notes="其他合同管理导出")
    @ApiImplicitParam(name = "zxCtOtherManage", value = "其他合同管理entity", dataType = "ZxCtOtherManage")
    @RequireToken
    @PostMapping("/exportZxCtOtherManage")
    public void exportZxCtOtherManage(@RequestBody(required = false) ZxCtOtherManage zxCtOtherManage,HttpServletResponse response) {
        zxCtOtherManageService.exportZxCtOtherManage(zxCtOtherManage,response);
    }
}
