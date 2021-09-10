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
import com.apih5.mybatis.pojo.ZxSfEquManage;
import com.apih5.service.ZxSfEquManageService;

@RestController
public class ZxSfEquManageController {

    @Autowired(required = true)
    private ZxSfEquManageService zxSfEquManageService;

    @ApiOperation(value="查询项目特种设备台账", notes="查询项目特种设备台账")
    @ApiImplicitParam(name = "zxSfEquManage", value = "项目特种设备台账entity", dataType = "ZxSfEquManage")
    @RequireToken
    @PostMapping("/getZxSfEquManageList")
    public ResponseEntity getZxSfEquManageList(@RequestBody(required = false) ZxSfEquManage zxSfEquManage) {
        return zxSfEquManageService.getZxSfEquManageListByCondition(zxSfEquManage);
    }

    @ApiOperation(value="查询详情项目特种设备台账", notes="查询详情项目特种设备台账")
    @ApiImplicitParam(name = "zxSfEquManage", value = "项目特种设备台账entity", dataType = "ZxSfEquManage")
    @RequireToken
    @PostMapping("/getZxSfEquManageDetail")
    public ResponseEntity getZxSfEquManageDetail(@RequestBody(required = false) ZxSfEquManage zxSfEquManage) {
        return zxSfEquManageService.getZxSfEquManageDetail(zxSfEquManage);
    }

    @ApiOperation(value="新增项目特种设备台账", notes="新增项目特种设备台账")
    @ApiImplicitParam(name = "zxSfEquManage", value = "项目特种设备台账entity", dataType = "ZxSfEquManage")
    @RequireToken
    @PostMapping("/addZxSfEquManage")
    public ResponseEntity addZxSfEquManage(@RequestBody(required = false) ZxSfEquManage zxSfEquManage) {
        return zxSfEquManageService.saveZxSfEquManage(zxSfEquManage);
    }

    @ApiOperation(value="更新项目特种设备台账", notes="更新项目特种设备台账")
    @ApiImplicitParam(name = "zxSfEquManage", value = "项目特种设备台账entity", dataType = "ZxSfEquManage")
    @RequireToken
    @PostMapping("/updateZxSfEquManage")
    public ResponseEntity updateZxSfEquManage(@RequestBody(required = false) ZxSfEquManage zxSfEquManage) {
        return zxSfEquManageService.updateZxSfEquManage(zxSfEquManage);
    }

    @ApiOperation(value="删除项目特种设备台账", notes="删除项目特种设备台账")
    @ApiImplicitParam(name = "zxSfEquManageList", value = "项目特种设备台账List", dataType = "List<ZxSfEquManage>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxSfEquManage")
    public ResponseEntity batchDeleteUpdateZxSfEquManage(@RequestBody(required = false) List<ZxSfEquManage> zxSfEquManageList) {
        return zxSfEquManageService.batchDeleteUpdateZxSfEquManage(zxSfEquManageList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
