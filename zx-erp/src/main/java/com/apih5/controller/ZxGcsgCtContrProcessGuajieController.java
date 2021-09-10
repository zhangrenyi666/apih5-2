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
import com.apih5.mybatis.pojo.ZxGcsgCtContrProcessGuajie;
import com.apih5.service.ZxGcsgCtContrProcessGuajieService;

@RestController
public class ZxGcsgCtContrProcessGuajieController {

    @Autowired(required = true)
    private ZxGcsgCtContrProcessGuajieService zxGcsgCtContrProcessGuajieService;

    @ApiOperation(value="查询合同清单与标准工序库关系表", notes="查询合同清单与标准工序库关系表")
    @ApiImplicitParam(name = "zxGcsgCtContrProcessGuajie", value = "合同清单与标准工序库关系表entity", dataType = "ZxGcsgCtContrProcessGuajie")
    @RequireToken
    @PostMapping("/getZxGcsgCtContrProcessGuajieList")
    public ResponseEntity getZxGcsgCtContrProcessGuajieList(@RequestBody(required = false) ZxGcsgCtContrProcessGuajie zxGcsgCtContrProcessGuajie) {
        return zxGcsgCtContrProcessGuajieService.getZxGcsgCtContrProcessGuajieListByCondition(zxGcsgCtContrProcessGuajie);
    }

    @ApiOperation(value="查询详情合同清单与标准工序库关系表", notes="查询详情合同清单与标准工序库关系表")
    @ApiImplicitParam(name = "zxGcsgCtContrProcessGuajie", value = "合同清单与标准工序库关系表entity", dataType = "ZxGcsgCtContrProcessGuajie")
    @RequireToken
    @PostMapping("/getZxGcsgCtContrProcessGuajieDetail")
    public ResponseEntity getZxGcsgCtContrProcessGuajieDetail(@RequestBody(required = false) ZxGcsgCtContrProcessGuajie zxGcsgCtContrProcessGuajie) {
        return zxGcsgCtContrProcessGuajieService.getZxGcsgCtContrProcessGuajieDetail(zxGcsgCtContrProcessGuajie);
    }

    @ApiOperation(value="新增合同清单与标准工序库关系表", notes="新增合同清单与标准工序库关系表")
    @ApiImplicitParam(name = "zxGcsgCtContrProcessGuajie", value = "合同清单与标准工序库关系表entity", dataType = "ZxGcsgCtContrProcessGuajie")
    @RequireToken
    @PostMapping("/addZxGcsgCtContrProcessGuajie")
    public ResponseEntity addZxGcsgCtContrProcessGuajie(@RequestBody(required = false) ZxGcsgCtContrProcessGuajie zxGcsgCtContrProcessGuajie) {
        return zxGcsgCtContrProcessGuajieService.saveZxGcsgCtContrProcessGuajie(zxGcsgCtContrProcessGuajie);
    }

    @ApiOperation(value="更新合同清单与标准工序库关系表", notes="更新合同清单与标准工序库关系表")
    @ApiImplicitParam(name = "zxGcsgCtContrProcessGuajie", value = "合同清单与标准工序库关系表entity", dataType = "ZxGcsgCtContrProcessGuajie")
    @RequireToken
    @PostMapping("/updateZxGcsgCtContrProcessGuajie")
    public ResponseEntity updateZxGcsgCtContrProcessGuajie(@RequestBody(required = false) ZxGcsgCtContrProcessGuajie zxGcsgCtContrProcessGuajie) {
        return zxGcsgCtContrProcessGuajieService.updateZxGcsgCtContrProcessGuajie(zxGcsgCtContrProcessGuajie);
    }

    @ApiOperation(value="删除合同清单与标准工序库关系表", notes="删除合同清单与标准工序库关系表")
    @ApiImplicitParam(name = "zxGcsgCtContrProcessGuajieList", value = "合同清单与标准工序库关系表List", dataType = "List<ZxGcsgCtContrProcessGuajie>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxGcsgCtContrProcessGuajie")
    public ResponseEntity batchDeleteUpdateZxGcsgCtContrProcessGuajie(@RequestBody(required = false) List<ZxGcsgCtContrProcessGuajie> zxGcsgCtContrProcessGuajieList) {
        return zxGcsgCtContrProcessGuajieService.batchDeleteUpdateZxGcsgCtContrProcessGuajie(zxGcsgCtContrProcessGuajieList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
