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
import com.apih5.mybatis.pojo.ZxSkTurnoverIn;
import com.apih5.service.ZxSkTurnoverInService;

@RestController
public class ZxSkTurnoverInController {

    @Autowired(required = true)
    private ZxSkTurnoverInService zxSkTurnoverInService;

    @ApiOperation(value="查询周转材料入库", notes="查询周转材料入库")
    @ApiImplicitParam(name = "zxSkTurnoverIn", value = "周转材料入库entity", dataType = "ZxSkTurnoverIn")
    @RequireToken
    @PostMapping("/getZxSkTurnoverInList")
    public ResponseEntity getZxSkTurnoverInList(@RequestBody(required = false) ZxSkTurnoverIn zxSkTurnoverIn) {
        return zxSkTurnoverInService.getZxSkTurnoverInListByCondition(zxSkTurnoverIn);
    }

    @ApiOperation(value="查询详情周转材料入库", notes="查询详情周转材料入库")
    @ApiImplicitParam(name = "zxSkTurnoverIn", value = "周转材料入库entity", dataType = "ZxSkTurnoverIn")
    @RequireToken
    @PostMapping("/getZxSkTurnoverInDetail")
    public ResponseEntity getZxSkTurnoverInDetail(@RequestBody(required = false) ZxSkTurnoverIn zxSkTurnoverIn) {
        return zxSkTurnoverInService.getZxSkTurnoverInDetail(zxSkTurnoverIn);
    }

    @ApiOperation(value="新增周转材料入库", notes="新增周转材料入库")
    @ApiImplicitParam(name = "zxSkTurnoverIn", value = "周转材料入库entity", dataType = "ZxSkTurnoverIn")
    @RequireToken
    @PostMapping("/addZxSkTurnoverIn")
    public ResponseEntity addZxSkTurnoverIn(@RequestBody(required = false) ZxSkTurnoverIn zxSkTurnoverIn) {
        return zxSkTurnoverInService.saveZxSkTurnoverIn(zxSkTurnoverIn);
    }

    @ApiOperation(value="更新周转材料入库", notes="更新周转材料入库")
    @ApiImplicitParam(name = "zxSkTurnoverIn", value = "周转材料入库entity", dataType = "ZxSkTurnoverIn")
    @RequireToken
    @PostMapping("/updateZxSkTurnoverIn")
    public ResponseEntity updateZxSkTurnoverIn(@RequestBody(required = false) ZxSkTurnoverIn zxSkTurnoverIn) {
        return zxSkTurnoverInService.updateZxSkTurnoverIn(zxSkTurnoverIn);
    }

    @ApiOperation(value="删除周转材料入库", notes="删除周转材料入库")
    @ApiImplicitParam(name = "zxSkTurnoverInList", value = "周转材料入库List", dataType = "List<ZxSkTurnoverIn>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxSkTurnoverIn")
    public ResponseEntity batchDeleteUpdateZxSkTurnoverIn(@RequestBody(required = false) List<ZxSkTurnoverIn> zxSkTurnoverInList) {
        return zxSkTurnoverInService.batchDeleteUpdateZxSkTurnoverIn(zxSkTurnoverInList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

    @ApiOperation(value="周转材料入库审核", notes="周转材料入库审核")
    @ApiImplicitParam(name = "zxSkTurnoverIn", value = "周转材料入库entity", dataType = "ZxSkTurnoverIn")
    @RequireToken
    @PostMapping("/checkZxSkTurnoverIn")
    public ResponseEntity checkZxSkTurnoverIn(@RequestBody(required = false) ZxSkTurnoverIn zxSkTurnoverIn) {
        return zxSkTurnoverInService.checkZxSkTurnoverIn(zxSkTurnoverIn);
    }

    //获取周转材物资
    @ApiOperation(value="获取周转材料入库物资", notes="获取周转材料入库物资")
    @ApiImplicitParam(name = "zxSkTurnoverIn", value = "周转材料入库entity", dataType = "ZxSkTurnoverIn")
    @RequireToken
    @PostMapping("/getZxSkTurnoverInResourceList")
    public ResponseEntity getZxSkTurnoverInResourceList(@RequestBody(required = false) ZxSkTurnoverIn zxSkTurnoverIn) {
        return zxSkTurnoverInService.getZxSkTurnoverInResourceList(zxSkTurnoverIn);
    }

    //获取周转材料入库编号
    @ApiOperation(value = "获取周转材料入库编号", notes = "获取周转材料入库编号")
    @ApiImplicitParam(name = "zxSkTurnoverIn", value = "周转材料入库entity", dataType = "zxSkTurnoverIn")
    @RequireToken
    @PostMapping("/getZxSkTurnoverInNo")
    public ResponseEntity getZxSkTurnoverInNo(@RequestBody(required = false) ZxSkTurnoverIn zxSkTurnoverIn) {
        return zxSkTurnoverInService.getZxSkTurnoverInNo(zxSkTurnoverIn);
    }


    @ApiOperation(value="周转材报表管理-导出周转材料入库台账报表", notes="周转材报表管理-导出周转材料入库台账报表")
    @ApiImplicitParam(name = "zxSkTurnoverIn", value = "周转材料入库entity", dataType = "ZxSkTurnoverIn")
    @PostMapping("/ureportZxSkTurnoverInList")
    public List<ZxSkTurnoverIn> ureportZxSkTurnoverInList(@RequestBody(required = false) ZxSkTurnoverIn zxSkTurnoverIn) {
        return zxSkTurnoverInService.ureportZxSkTurnoverInList(zxSkTurnoverIn);
    }

    @ApiOperation(value="周转材报表管理-周转材料入库台账报表前端查看", notes="周转材报表管理-周转材料入库台账报表前端查看")
    @ApiImplicitParam(name = "zxSkTurnoverIn", value = "周转材料入库entity", dataType = "ZxSkTurnoverIn")
    @PostMapping("/getUreportZxSkTurnoverInList")
    public  ResponseEntity getUreportZxSkTurnoverInList(@RequestBody(required = false) ZxSkTurnoverIn zxSkTurnoverIn) {
        return zxSkTurnoverInService.getUreportZxSkTurnoverInList(zxSkTurnoverIn);
    }


}
