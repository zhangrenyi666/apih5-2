package com.apih5.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.apih5.framework.annotation.RequireToken;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxSkCustomerOutRes;
import com.apih5.service.ZxSkCustomerOutResService;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

@RestController
public class ZxSkCustomerOutResController {

    @Autowired(required = true)
    private ZxSkCustomerOutResService zxSkCustomerOutResService;

    @ApiOperation(value="查询收料单台账", notes="查询收料单台账")
    @ApiImplicitParam(name = "zxSkCustomerOutRes", value = "收料单台账entity", dataType = "ZxSkCustomerOutRes")
    @RequireToken
    @PostMapping("/getZxSkCustomerOutResList")
    public ResponseEntity getZxSkCustomerOutResList(@RequestBody(required = false) ZxSkCustomerOutRes zxSkCustomerOutRes) {
        return zxSkCustomerOutResService.getZxSkCustomerOutResListByCondition(zxSkCustomerOutRes);
    }

    @ApiOperation(value="查询详情收料单台账", notes="查询详情收料单台账")
    @ApiImplicitParam(name = "zxSkCustomerOutRes", value = "收料单台账entity", dataType = "ZxSkCustomerOutRes")
    @RequireToken
    @PostMapping("/getZxSkCustomerOutResDetail")
    public ResponseEntity getZxSkCustomerOutResDetail(@RequestBody(required = false) ZxSkCustomerOutRes zxSkCustomerOutRes) {
        return zxSkCustomerOutResService.getZxSkCustomerOutResDetail(zxSkCustomerOutRes);
    }

    @ApiOperation(value="新增收料单台账", notes="新增收料单台账")
    @ApiImplicitParam(name = "zxSkCustomerOutRes", value = "收料单台账entity", dataType = "ZxSkCustomerOutRes")
    @RequireToken
    @PostMapping("/addZxSkCustomerOutRes")
    public ResponseEntity addZxSkCustomerOutRes(@RequestBody(required = false) ZxSkCustomerOutRes zxSkCustomerOutRes) {
        return zxSkCustomerOutResService.saveZxSkCustomerOutRes(zxSkCustomerOutRes);
    }

    @ApiOperation(value="更新收料单台账", notes="更新收料单台账")
    @ApiImplicitParam(name = "zxSkCustomerOutRes", value = "收料单台账entity", dataType = "ZxSkCustomerOutRes")
    @RequireToken
    @PostMapping("/updateZxSkCustomerOutRes")
    public ResponseEntity updateZxSkCustomerOutRes(@RequestBody(required = false) ZxSkCustomerOutRes zxSkCustomerOutRes) {
        return zxSkCustomerOutResService.updateZxSkCustomerOutRes(zxSkCustomerOutRes);
    }

    @ApiOperation(value="删除收料单台账", notes="删除收料单台账")
    @ApiImplicitParam(name = "zxSkCustomerOutResList", value = "收料单台账List", dataType = "List<ZxSkCustomerOutRes>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxSkCustomerOutRes")
    public ResponseEntity batchDeleteUpdateZxSkCustomerOutRes(@RequestBody(required = false) List<ZxSkCustomerOutRes> zxSkCustomerOutResList) {
        return zxSkCustomerOutResService.batchDeleteUpdateZxSkCustomerOutRes(zxSkCustomerOutResList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    
    @ApiOperation(value="报表导出收料单台账", notes="报表导出收料单台账")
    @ApiImplicitParam(name = "zxSkCustomerOutRes", value = "设备台账entity", dataType = "ZxSkCustomerOutRes")
    @PostMapping("/ureportZxSkReceivingDynamic")
    public List<ZxSkCustomerOutRes> ureportZxSkReceivingDynamic(@RequestBody(required = false) ZxSkCustomerOutRes zxSkCustomerOutRes) {
        return zxSkCustomerOutResService.ureportZxSkReceivingDynamic(zxSkCustomerOutRes);
    }
    
    @ApiOperation(value="报表收料单台账", notes="报表收料单台账")
    @ApiImplicitParam(name = "zxSkCustomerOutRes", value = "设备台账entity", dataType = "ZxSkCustomerOutRes")
    @RequireToken
    @PostMapping("/ureportZxSkReceivingDynamicIdle")
    public ResponseEntity ureportZxSkReceivingDynamicIdle(@RequestBody(required = false) ZxSkCustomerOutRes zxSkCustomerOutRes) {
        return zxSkCustomerOutResService.ureportZxSkReceivingDynamicIdle(zxSkCustomerOutRes);
    }
}
