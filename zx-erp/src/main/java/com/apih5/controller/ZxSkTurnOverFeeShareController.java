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
import com.apih5.mybatis.pojo.ZxSkTurnOverFeeShare;
import com.apih5.service.ZxSkTurnOverFeeShareService;

@RestController
public class ZxSkTurnOverFeeShareController {

    @Autowired(required = true)
    private ZxSkTurnOverFeeShareService zxSkTurnOverFeeShareService;

    @ApiOperation(value="查询周转材料摊销", notes="查询周转材料摊销")
    @ApiImplicitParam(name = "zxSkTurnOverFeeShare", value = "周转材料摊销entity", dataType = "ZxSkTurnOverFeeShare")
    @RequireToken
    @PostMapping("/getZxSkTurnOverFeeShareList")
    public ResponseEntity getZxSkTurnOverFeeShareList(@RequestBody(required = false) ZxSkTurnOverFeeShare zxSkTurnOverFeeShare) {
        return zxSkTurnOverFeeShareService.getZxSkTurnOverFeeShareListByCondition(zxSkTurnOverFeeShare);
    }

    @ApiOperation(value="查询详情周转材料摊销", notes="查询详情周转材料摊销")
    @ApiImplicitParam(name = "zxSkTurnOverFeeShare", value = "周转材料摊销entity", dataType = "ZxSkTurnOverFeeShare")
    @RequireToken
    @PostMapping("/getZxSkTurnOverFeeShareDetail")
    public ResponseEntity getZxSkTurnOverFeeShareDetail(@RequestBody(required = false) ZxSkTurnOverFeeShare zxSkTurnOverFeeShare) {
        return zxSkTurnOverFeeShareService.getZxSkTurnOverFeeShareDetail(zxSkTurnOverFeeShare);
    }

    @ApiOperation(value="新增周转材料摊销", notes="新增周转材料摊销")
    @ApiImplicitParam(name = "zxSkTurnOverFeeShare", value = "周转材料摊销entity", dataType = "ZxSkTurnOverFeeShare")
    @RequireToken
    @PostMapping("/addZxSkTurnOverFeeShare")
    public ResponseEntity addZxSkTurnOverFeeShare(@RequestBody(required = false) ZxSkTurnOverFeeShare zxSkTurnOverFeeShare) {
        return zxSkTurnOverFeeShareService.saveZxSkTurnOverFeeShare(zxSkTurnOverFeeShare);
    }

    @ApiOperation(value="更新周转材料摊销", notes="更新周转材料摊销")
    @ApiImplicitParam(name = "zxSkTurnOverFeeShare", value = "周转材料摊销entity", dataType = "ZxSkTurnOverFeeShare")
    @RequireToken
    @PostMapping("/updateZxSkTurnOverFeeShare")
    public ResponseEntity updateZxSkTurnOverFeeShare(@RequestBody(required = false) ZxSkTurnOverFeeShare zxSkTurnOverFeeShare) {
        return zxSkTurnOverFeeShareService.updateZxSkTurnOverFeeShare(zxSkTurnOverFeeShare);
    }

    @ApiOperation(value="删除周转材料摊销", notes="删除周转材料摊销")
    @ApiImplicitParam(name = "zxSkTurnOverFeeShareList", value = "周转材料摊销List", dataType = "List<ZxSkTurnOverFeeShare>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxSkTurnOverFeeShare")
    public ResponseEntity batchDeleteUpdateZxSkTurnOverFeeShare(@RequestBody(required = false) List<ZxSkTurnOverFeeShare> zxSkTurnOverFeeShareList) {
        return zxSkTurnOverFeeShareService.batchDeleteUpdateZxSkTurnOverFeeShare(zxSkTurnOverFeeShareList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

    //编号
    //获取领料单据编号
    //todo:将来要改的
    @ApiOperation(value = "获取周转材料摊销编号", notes = "获取周转材料摊销编号")
    @ApiImplicitParam(name = "zxSkTurnOverFeeShare", value = "周转材料摊entity", dataType = "zxSkTurnOverFeeShare")
    @RequireToken
    @PostMapping("/getZxSkTurnOverFeeShareNo")
    public ResponseEntity getZxSkTurnOverFeeShareNo(@RequestBody(required = false) ZxSkTurnOverFeeShare zxSkTurnOverFeeShare) {
        return zxSkTurnOverFeeShareService.getZxSkTurnOverFeeShareNo(zxSkTurnOverFeeShare);
    }


    @ApiOperation(value = "获取周转材料摊销物资", notes = "获取周转材料摊销物资")
    @ApiImplicitParam(name = "zxSkTurnOverFeeShare", value = "周转材料摊销entity", dataType = "zxSkTurnOverFeeShare")
    @RequireToken
    @PostMapping("/getZxSkTurnOverFeeShareResourceList")
    public ResponseEntity getZxSkTurnOverFeeShareResourceList(@RequestBody(required = false) ZxSkTurnOverFeeShare zxSkTurnOverFeeShare) {
        return zxSkTurnOverFeeShareService.getZxSkTurnOverFeeShareResourceList(zxSkTurnOverFeeShare);
    }


    @ApiOperation(value = "审核周转材料摊销", notes = "审核周转材料摊销")
    @ApiImplicitParam(name = "zxSkTurnOverFeeShare", value = "周转材料摊销entity", dataType = "zxSkTurnOverFeeShare")
    @RequireToken
    @PostMapping("/checkZxSkTurnOverFeeShare")
    public ResponseEntity checkZxSkTurnOverFeeShare(@RequestBody(required = false) ZxSkTurnOverFeeShare zxSkTurnOverFeeShare) {
        return zxSkTurnOverFeeShareService.checkZxSkTurnOverFeeShare(zxSkTurnOverFeeShare);
    }

    @ApiOperation(value = "反审核周转材料摊销", notes = "反审核周转材料摊销")
    @ApiImplicitParam(name = "zxSkTurnOverFeeShare", value = "周转材料摊销entity", dataType = "zxSkTurnOverFeeShare")
    @RequireToken
    @PostMapping("/counterCheckZxSkTurnOverFeeShare")
    public ResponseEntity counterCheckZxSkTurnOverFeeShare(@RequestBody(required = false) ZxSkTurnOverFeeShare zxSkTurnOverFeeShare) {
        return zxSkTurnOverFeeShareService.counterCheckZxSkTurnOverFeeShare(zxSkTurnOverFeeShare);
    }


}
