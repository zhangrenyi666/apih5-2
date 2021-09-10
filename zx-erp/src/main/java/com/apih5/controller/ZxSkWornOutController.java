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
import com.apih5.mybatis.pojo.ZxSkWornOut;
import com.apih5.service.ZxSkWornOutService;

@RestController
public class ZxSkWornOutController {

    @Autowired(required = true)
    private ZxSkWornOutService zxSkWornOutService;

    @ApiOperation(value="查询废旧物资管理", notes="查询废旧物资管理")
    @ApiImplicitParam(name = "zxSkWornOut", value = "废旧物资管理entity", dataType = "ZxSkWornOut")
    @RequireToken
    @PostMapping("/getZxSkWornOutList")
    public ResponseEntity getZxSkWornOutList(@RequestBody(required = false) ZxSkWornOut zxSkWornOut) {
        return zxSkWornOutService.getZxSkWornOutListByCondition(zxSkWornOut);
    }

    @ApiOperation(value="查询详情废旧物资管理", notes="查询详情废旧物资管理")
    @ApiImplicitParam(name = "zxSkWornOut", value = "废旧物资管理entity", dataType = "ZxSkWornOut")
    @RequireToken
    @PostMapping("/getZxSkWornOutDetail")
    public ResponseEntity getZxSkWornOutDetail(@RequestBody(required = false) ZxSkWornOut zxSkWornOut) {
        return zxSkWornOutService.getZxSkWornOutDetail(zxSkWornOut);
    }

    @ApiOperation(value="新增废旧物资管理", notes="新增废旧物资管理")
    @ApiImplicitParam(name = "zxSkWornOut", value = "废旧物资管理entity", dataType = "ZxSkWornOut")
    @RequireToken
    @PostMapping("/addZxSkWornOut")
    public ResponseEntity addZxSkWornOut(@RequestBody(required = false) ZxSkWornOut zxSkWornOut) {
        return zxSkWornOutService.saveZxSkWornOut(zxSkWornOut);
    }

    @ApiOperation(value="更新废旧物资管理", notes="更新废旧物资管理")
    @ApiImplicitParam(name = "zxSkWornOut", value = "废旧物资管理entity", dataType = "ZxSkWornOut")
    @RequireToken
    @PostMapping("/updateZxSkWornOut")
    public ResponseEntity updateZxSkWornOut(@RequestBody(required = false) ZxSkWornOut zxSkWornOut) {
        return zxSkWornOutService.updateZxSkWornOut(zxSkWornOut);
    }

    @ApiOperation(value="删除废旧物资管理", notes="删除废旧物资管理")
    @ApiImplicitParam(name = "zxSkWornOutList", value = "废旧物资管理List", dataType = "List<ZxSkWornOut>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxSkWornOut")
    public ResponseEntity batchDeleteUpdateZxSkWornOut(@RequestBody(required = false) List<ZxSkWornOut> zxSkWornOutList) {
        return zxSkWornOutService.batchDeleteUpdateZxSkWornOut(zxSkWornOutList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

    @ApiOperation(value="获取废旧物资管理编号", notes="获取废旧物资管理编号")
    @ApiImplicitParam(name = "zxSkWornOut", value = "废旧物资管理entity", dataType = "zxSkWornOut")
    @RequireToken
    @PostMapping("/getZxSkWornOutNo")
    public ResponseEntity getZxSkWornOutNo(@RequestBody(required = false) ZxSkWornOut zxSkWornOut) {
        return zxSkWornOutService.getZxSkWornOutNo(zxSkWornOut);
    }


}
