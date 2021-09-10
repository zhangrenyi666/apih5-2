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
import com.apih5.mybatis.pojo.ZxEqEquipIntegratedQuery;
import com.apih5.mybatis.pojo.ZxEqToEquipSourceQueryPage;
import com.apih5.service.ZxEqToEquipSourceQueryPageService;

@RestController
public class ZxEqToEquipSourceQueryPageController {

    @Autowired(required = true)
    private ZxEqToEquipSourceQueryPageService zxEqToEquipSourceQueryPageService;

    @ApiOperation(value="查询设备来源查询", notes="查询设备来源查询")
    @ApiImplicitParam(name = "zxEqToEquipSourceQueryPage", value = "设备来源查询entity", dataType = "ZxEqToEquipSourceQueryPage")
    @RequireToken
    @PostMapping("/getZxEqToEquipSourceQueryPageList")
    public ResponseEntity getZxEqToEquipSourceQueryPageList(@RequestBody(required = false) ZxEqToEquipSourceQueryPage zxEqToEquipSourceQueryPage) {
        return zxEqToEquipSourceQueryPageService.getZxEqToEquipSourceQueryPageListByCondition(zxEqToEquipSourceQueryPage);
    }

    @ApiOperation(value="查询详情设备来源查询", notes="查询详情设备来源查询")
    @ApiImplicitParam(name = "zxEqToEquipSourceQueryPage", value = "设备来源查询entity", dataType = "ZxEqToEquipSourceQueryPage")
    @RequireToken
    @PostMapping("/getZxEqToEquipSourceQueryPageDetail")
    public ResponseEntity getZxEqToEquipSourceQueryPageDetail(@RequestBody(required = false) ZxEqToEquipSourceQueryPage zxEqToEquipSourceQueryPage) {
        return zxEqToEquipSourceQueryPageService.getZxEqToEquipSourceQueryPageDetail(zxEqToEquipSourceQueryPage);
    }

    @ApiOperation(value="新增设备来源查询", notes="新增设备来源查询")
    @ApiImplicitParam(name = "zxEqToEquipSourceQueryPage", value = "设备来源查询entity", dataType = "ZxEqToEquipSourceQueryPage")
    @RequireToken
    @PostMapping("/addZxEqToEquipSourceQueryPage")
    public ResponseEntity addZxEqToEquipSourceQueryPage(@RequestBody(required = false) ZxEqToEquipSourceQueryPage zxEqToEquipSourceQueryPage) {
        return zxEqToEquipSourceQueryPageService.saveZxEqToEquipSourceQueryPage(zxEqToEquipSourceQueryPage);
    }

    @ApiOperation(value="更新设备来源查询", notes="更新设备来源查询")
    @ApiImplicitParam(name = "zxEqToEquipSourceQueryPage", value = "设备来源查询entity", dataType = "ZxEqToEquipSourceQueryPage")
    @RequireToken
    @PostMapping("/updateZxEqToEquipSourceQueryPage")
    public ResponseEntity updateZxEqToEquipSourceQueryPage(@RequestBody(required = false) ZxEqToEquipSourceQueryPage zxEqToEquipSourceQueryPage) {
        return zxEqToEquipSourceQueryPageService.updateZxEqToEquipSourceQueryPage(zxEqToEquipSourceQueryPage);
    }

    @ApiOperation(value="删除设备来源查询", notes="删除设备来源查询")
    @ApiImplicitParam(name = "zxEqToEquipSourceQueryPageList", value = "设备来源查询List", dataType = "List<ZxEqToEquipSourceQueryPage>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxEqToEquipSourceQueryPage")
    public ResponseEntity batchDeleteUpdateZxEqToEquipSourceQueryPage(@RequestBody(required = false) List<ZxEqToEquipSourceQueryPage> zxEqToEquipSourceQueryPageList) {
        return zxEqToEquipSourceQueryPageService.batchDeleteUpdateZxEqToEquipSourceQueryPage(zxEqToEquipSourceQueryPageList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    
    @ApiOperation(value="报表设备来源查询", notes="报表设备来源查询")
    @ApiImplicitParam(name = "zxEqToEquipSourceQueryPage", value = "设备台账entity", dataType = "ZxEqToEquipSourceQueryPage")
    @RequireToken
    @PostMapping("/ureportZxEqToEquipSourceQueryPageIdle")
    public ResponseEntity ureportZxEqToEquipSourceQueryPageIdle(@RequestBody(required = false) ZxEqToEquipSourceQueryPage zxEqToEquipSourceQueryPage) {
        return zxEqToEquipSourceQueryPageService.ureportZxEqToEquipSourceQueryPageIdle(zxEqToEquipSourceQueryPage);
    }
}
