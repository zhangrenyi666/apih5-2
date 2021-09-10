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
import com.apih5.mybatis.pojo.ZxSkCustomerOutRes;
import com.apih5.mybatis.pojo.ZxSkResourceOut;
import com.apih5.service.ZxSkResourceOutService;

@RestController
public class ZxSkResourceOutController {

    @Autowired(required = true)
    private ZxSkResourceOutService zxSkResourceOutService;

    @ApiOperation(value="查询项目物资出库台账", notes="查询项目物资出库台账")
    @ApiImplicitParam(name = "zxSkResourceOut", value = "项目物资出库台账entity", dataType = "ZxSkResourceOut")
    @RequireToken
    @PostMapping("/getZxSkResourceOutList")
    public ResponseEntity getZxSkResourceOutList(@RequestBody(required = false) ZxSkResourceOut zxSkResourceOut) {
        return zxSkResourceOutService.getZxSkResourceOutListByCondition(zxSkResourceOut);
    }

    @ApiOperation(value="查询详情项目物资出库台账", notes="查询详情项目物资出库台账")
    @ApiImplicitParam(name = "zxSkResourceOut", value = "项目物资出库台账entity", dataType = "ZxSkResourceOut")
    @RequireToken
    @PostMapping("/getZxSkResourceOutDetail")
    public ResponseEntity getZxSkResourceOutDetail(@RequestBody(required = false) ZxSkResourceOut zxSkResourceOut) {
        return zxSkResourceOutService.getZxSkResourceOutDetail(zxSkResourceOut);
    }

    @ApiOperation(value="新增项目物资出库台账", notes="新增项目物资出库台账")
    @ApiImplicitParam(name = "zxSkResourceOut", value = "项目物资出库台账entity", dataType = "ZxSkResourceOut")
    @RequireToken
    @PostMapping("/addZxSkResourceOut")
    public ResponseEntity addZxSkResourceOut(@RequestBody(required = false) ZxSkResourceOut zxSkResourceOut) {
        return zxSkResourceOutService.saveZxSkResourceOut(zxSkResourceOut);
    }

    @ApiOperation(value="更新项目物资出库台账", notes="更新项目物资出库台账")
    @ApiImplicitParam(name = "zxSkResourceOut", value = "项目物资出库台账entity", dataType = "ZxSkResourceOut")
    @RequireToken
    @PostMapping("/updateZxSkResourceOut")
    public ResponseEntity updateZxSkResourceOut(@RequestBody(required = false) ZxSkResourceOut zxSkResourceOut) {
        return zxSkResourceOutService.updateZxSkResourceOut(zxSkResourceOut);
    }

    @ApiOperation(value="删除项目物资出库台账", notes="删除项目物资出库台账")
    @ApiImplicitParam(name = "zxSkResourceOutList", value = "项目物资出库台账List", dataType = "List<ZxSkResourceOut>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxSkResourceOut")
    public ResponseEntity batchDeleteUpdateZxSkResourceOut(@RequestBody(required = false) List<ZxSkResourceOut> zxSkResourceOutList) {
        return zxSkResourceOutService.batchDeleteUpdateZxSkResourceOut(zxSkResourceOutList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    
    @ApiOperation(value="报表导出项目物资出库台账", notes="报表导出项目物资出库台账")
    @ApiImplicitParam(name = "zxSkResourceOut", value = "设备台账entity", dataType = "ZxSkResourceOut")
    @PostMapping("/ureportZxSkResourceOut")
    public List<ZxSkResourceOut> ureportZxSkResourceOut(@RequestBody(required = false) ZxSkResourceOut zxSkResourceOut) {
        return zxSkResourceOutService.ureportZxSkResourceOut(zxSkResourceOut);
    }
    
    @ApiOperation(value="报表项目物资出库台账", notes="报表项目物资出库台账")
    @ApiImplicitParam(name = "zxSkResourceOut", value = "设备台账entity", dataType = "ZxSkResourceOut")
    @RequireToken
    @PostMapping("/ureportZxSkResourceOutIdle")
    public ResponseEntity ureportZxSkResourceOutIdle(@RequestBody(required = false) ZxSkResourceOut zxSkResourceOut) {
        return zxSkResourceOutService.ureportZxSkResourceOutIdle(zxSkResourceOut);
    }
}
