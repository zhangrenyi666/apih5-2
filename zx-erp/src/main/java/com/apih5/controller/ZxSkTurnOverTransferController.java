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
import com.apih5.mybatis.pojo.ZxSkTurnOverTransfer;
import com.apih5.service.ZxSkTurnOverTransferService;

@RestController
public class ZxSkTurnOverTransferController {

    @Autowired(required = true)
    private ZxSkTurnOverTransferService zxSkTurnOverTransferService;

    @ApiOperation(value="查询周转材料调拨", notes="查询周转材料调拨")
    @ApiImplicitParam(name = "zxSkTurnOverTransfer", value = "周转材料调拨entity", dataType = "ZxSkTurnOverTransfer")
    @RequireToken
    @PostMapping("/getZxSkTurnOverTransferList")
    public ResponseEntity getZxSkTurnOverTransferList(@RequestBody(required = false) ZxSkTurnOverTransfer zxSkTurnOverTransfer) {
        return zxSkTurnOverTransferService.getZxSkTurnOverTransferListByCondition(zxSkTurnOverTransfer);
    }

    @ApiOperation(value="查询详情周转材料调拨", notes="查询详情周转材料调拨")
    @ApiImplicitParam(name = "zxSkTurnOverTransfer", value = "周转材料调拨entity", dataType = "ZxSkTurnOverTransfer")
    @RequireToken
    @PostMapping("/getZxSkTurnOverTransferDetail")
    public ResponseEntity getZxSkTurnOverTransferDetail(@RequestBody(required = false) ZxSkTurnOverTransfer zxSkTurnOverTransfer) {
        return zxSkTurnOverTransferService.getZxSkTurnOverTransferDetail(zxSkTurnOverTransfer);
    }

    @ApiOperation(value="新增周转材料调拨", notes="新增周转材料调拨")
    @ApiImplicitParam(name = "zxSkTurnOverTransfer", value = "周转材料调拨entity", dataType = "ZxSkTurnOverTransfer")
    @RequireToken
    @PostMapping("/addZxSkTurnOverTransfer")
    public ResponseEntity addZxSkTurnOverTransfer(@RequestBody(required = false) ZxSkTurnOverTransfer zxSkTurnOverTransfer) {
        return zxSkTurnOverTransferService.saveZxSkTurnOverTransfer(zxSkTurnOverTransfer);
    }

    @ApiOperation(value="更新周转材料调拨", notes="更新周转材料调拨")
    @ApiImplicitParam(name = "zxSkTurnOverTransfer", value = "周转材料调拨entity", dataType = "ZxSkTurnOverTransfer")
    @RequireToken
    @PostMapping("/updateZxSkTurnOverTransfer")
    public ResponseEntity updateZxSkTurnOverTransfer(@RequestBody(required = false) ZxSkTurnOverTransfer zxSkTurnOverTransfer) {
        return zxSkTurnOverTransferService.updateZxSkTurnOverTransfer(zxSkTurnOverTransfer);
    }

    @ApiOperation(value="删除周转材料调拨", notes="删除周转材料调拨")
    @ApiImplicitParam(name = "zxSkTurnOverTransferList", value = "周转材料调拨List", dataType = "List<ZxSkTurnOverTransfer>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxSkTurnOverTransfer")
    public ResponseEntity batchDeleteUpdateZxSkTurnOverTransfer(@RequestBody(required = false) List<ZxSkTurnOverTransfer> zxSkTurnOverTransferList) {
        return zxSkTurnOverTransferService.batchDeleteUpdateZxSkTurnOverTransfer(zxSkTurnOverTransferList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

    //编号
    @ApiOperation(value="获取周转材料调拨编号", notes="获取周转材料调拨编号")
    @ApiImplicitParam(name = "zxSkTurnOverTransfer", value = "周转材料调拨entity", dataType = "ZxSkTurnOverTransfer")
    @RequireToken
    @PostMapping("/getZxSkTurnOverTransferNo")
    public ResponseEntity getZxSkTurnOverTransferNo(@RequestBody(required = false) ZxSkTurnOverTransfer zxSkTurnOverTransfer) {
        return zxSkTurnOverTransferService.getZxSkTurnOverTransferNo(zxSkTurnOverTransfer);
    }


    //获取周转材物资
    @ApiOperation(value="获取周转材料调拨物资", notes="获取周转材料调拨物资")
    @ApiImplicitParam(name = "zxSkTurnOverTransfer", value = "周转材料调拨entity", dataType = "ZxSkTurnOverTransfer")
    @RequireToken
    @PostMapping("/getZxSkTurnOverTransferResourceList")
    public ResponseEntity getZxSkTurnOverTransferResourceList(@RequestBody(required = false) ZxSkTurnOverTransfer zxSkTurnOverTransfer) {
        return zxSkTurnOverTransferService.getZxSkTurnOverTransferResourceList(zxSkTurnOverTransfer);
    }

    //审核周转材
    @ApiOperation(value="审核周转材料调拨物资", notes="审核周转材料调拨物资")
    @ApiImplicitParam(name = "zxSkTurnOverTransfer", value = "周转材料调拨entity", dataType = "ZxSkTurnOverTransfer")
    @RequireToken
    @PostMapping("/checkZxSkTurnOverTransfer")
    public ResponseEntity checkZxSkTurnOverTransfer(@RequestBody(required = false) ZxSkTurnOverTransfer zxSkTurnOverTransfer) {
        return zxSkTurnOverTransferService.checkZxSkTurnOverTransfer(zxSkTurnOverTransfer);
    }


    //反审核周转材
    @ApiOperation(value="反审核周转材料调拨物资", notes="反审核周转材料调拨物资")
    @ApiImplicitParam(name = "zxSkTurnOverTransfer", value = "周转材料调拨entity", dataType = "ZxSkTurnOverTransfer")
    @RequireToken
    @PostMapping("/counterCheckZxSkTurnOverTransfer")
    public ResponseEntity counterCheckZxSkTurnOverTransfer(@RequestBody(required = false) ZxSkTurnOverTransfer zxSkTurnOverTransfer) {
        return zxSkTurnOverTransferService.counterCheckZxSkTurnOverTransfer(zxSkTurnOverTransfer);
    }




}
