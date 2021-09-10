package com.apih5.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.apih5.framework.annotation.RequireToken;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxSkWornOutList;
import com.apih5.service.ZxSkWornOutListService;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

@RestController
public class ZxSkWornOutListController {

    @Autowired(required = true)
    private ZxSkWornOutListService zxSkWornOutListService;

    @ApiOperation(value="查询废旧物资处理台账", notes="查询废旧物资处理台账")
    @ApiImplicitParam(name = "zxSkWornOutList", value = "废旧物资处理台账entity", dataType = "ZxSkWornOutList")
    @RequireToken
    @PostMapping("/getZxSkWornOutListList")
    public ResponseEntity getZxSkWornOutListList(@RequestBody(required = false) ZxSkWornOutList zxSkWornOutList) {
        return zxSkWornOutListService.getZxSkWornOutListListByCondition(zxSkWornOutList);
    }

    @ApiOperation(value="查询详情废旧物资处理台账", notes="查询详情废旧物资处理台账")
    @ApiImplicitParam(name = "zxSkWornOutList", value = "废旧物资处理台账entity", dataType = "ZxSkWornOutList")
    @RequireToken
    @PostMapping("/getZxSkWornOutListDetail")
    public ResponseEntity getZxSkWornOutListDetail(@RequestBody(required = false) ZxSkWornOutList zxSkWornOutList) {
        return zxSkWornOutListService.getZxSkWornOutListDetail(zxSkWornOutList);
    }

    @ApiOperation(value="新增废旧物资处理台账", notes="新增废旧物资处理台账")
    @ApiImplicitParam(name = "zxSkWornOutList", value = "废旧物资处理台账entity", dataType = "ZxSkWornOutList")
    @RequireToken
    @PostMapping("/addZxSkWornOutList")
    public ResponseEntity addZxSkWornOutList(@RequestBody(required = false) ZxSkWornOutList zxSkWornOutList) {
        return zxSkWornOutListService.saveZxSkWornOutList(zxSkWornOutList);
    }

    @ApiOperation(value="更新废旧物资处理台账", notes="更新废旧物资处理台账")
    @ApiImplicitParam(name = "zxSkWornOutList", value = "废旧物资处理台账entity", dataType = "ZxSkWornOutList")
    @RequireToken
    @PostMapping("/updateZxSkWornOutList")
    public ResponseEntity updateZxSkWornOutList(@RequestBody(required = false) ZxSkWornOutList zxSkWornOutList) {
        return zxSkWornOutListService.updateZxSkWornOutList(zxSkWornOutList);
    }

    @ApiOperation(value="删除废旧物资处理台账", notes="删除废旧物资处理台账")
    @ApiImplicitParam(name = "zxSkWornOutListList", value = "废旧物资处理台账List", dataType = "List<ZxSkWornOutList>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxSkWornOutList")
    public ResponseEntity batchDeleteUpdateZxSkWornOutList(@RequestBody(required = false) List<ZxSkWornOutList> zxSkWornOutListList) {
        return zxSkWornOutListService.batchDeleteUpdateZxSkWornOutList(zxSkWornOutListList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    
    @ApiOperation(value="报表导出废旧物资处理台账", notes="报表导出废旧物资处理台账")
    @ApiImplicitParam(name = "zxSkWornOutList", value = "设备台账entity", dataType = "ZxSkWornOutList")
    @PostMapping("/ureportZxSkWornOutList")
    public List<ZxSkWornOutList> ureportZxSkWornOutList(@RequestBody(required = false) ZxSkWornOutList zxSkWornOutList) {
        return zxSkWornOutListService.ureportZxSkWornOutList(zxSkWornOutList);
    }
    
    @ApiOperation(value="报表导出废旧物资处理台账", notes="报表导出废旧物资处理台账")
    @ApiImplicitParam(name = "zxSkWornOutList", value = "设备台账entity", dataType = "ZxSkWornOutList")
    @RequireToken
    @PostMapping("/ureportZxSkWornOutListIdle")
    public ResponseEntity ureportZxSkWornOutListIdle(@RequestBody(required = false) ZxSkWornOutList zxSkWornOutList) {
        return zxSkWornOutListService.ureportZxSkWornOutListIdle(zxSkWornOutList);
    }
}
