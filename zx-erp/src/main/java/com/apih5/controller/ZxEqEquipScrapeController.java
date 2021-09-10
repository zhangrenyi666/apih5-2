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
import com.apih5.mybatis.pojo.ZxEqEquipScrape;
import com.apih5.service.ZxEqEquipScrapeService;

@RestController
public class ZxEqEquipScrapeController {

    @Autowired(required = true)
    private ZxEqEquipScrapeService zxEqEquipScrapeService;

    @ApiOperation(value="查询设备异动-设备报废", notes="查询设备异动-设备报废")
    @ApiImplicitParam(name = "zxEqEquipScrape", value = "设备异动-设备报废entity", dataType = "ZxEqEquipScrape")
    @RequireToken
    @PostMapping("/getZxEqEquipScrapeList")
    public ResponseEntity getZxEqEquipScrapeList(@RequestBody(required = false) ZxEqEquipScrape zxEqEquipScrape) {
        return zxEqEquipScrapeService.getZxEqEquipScrapeListByCondition(zxEqEquipScrape);
    }

    @ApiOperation(value="查询详情设备异动-设备报废", notes="查询详情设备异动-设备报废")
    @ApiImplicitParam(name = "zxEqEquipScrape", value = "设备异动-设备报废entity", dataType = "ZxEqEquipScrape")
    @RequireToken
    @PostMapping("/getZxEqEquipScrapeDetails")
    public ResponseEntity getZxEqEquipScrapeDetails(@RequestBody(required = false) ZxEqEquipScrape zxEqEquipScrape) {
        return zxEqEquipScrapeService.getZxEqEquipScrapeDetails(zxEqEquipScrape);
    }

    @ApiOperation(value="新增设备异动-设备报废", notes="新增设备异动-设备报废")
    @ApiImplicitParam(name = "zxEqEquipScrape", value = "设备异动-设备报废entity", dataType = "ZxEqEquipScrape")
    @RequireToken
    @PostMapping("/addZxEqEquipScrape")
    public ResponseEntity addZxEqEquipScrape(@RequestBody(required = false) ZxEqEquipScrape zxEqEquipScrape) {
        return zxEqEquipScrapeService.saveZxEqEquipScrape(zxEqEquipScrape);
    }

    @ApiOperation(value="更新设备异动-设备报废", notes="更新设备异动-设备报废")
    @ApiImplicitParam(name = "zxEqEquipScrape", value = "设备异动-设备报废entity", dataType = "ZxEqEquipScrape")
    @RequireToken
    @PostMapping("/updateZxEqEquipScrape")
    public ResponseEntity updateZxEqEquipScrape(@RequestBody(required = false) ZxEqEquipScrape zxEqEquipScrape) {
        return zxEqEquipScrapeService.updateZxEqEquipScrape(zxEqEquipScrape);
    }

    @ApiOperation(value="删除设备异动-设备报废", notes="删除设备异动-设备报废")
    @ApiImplicitParam(name = "zxEqEquipScrapeList", value = "设备异动-设备报废List", dataType = "List<ZxEqEquipScrape>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxEqEquipScrape")
    public ResponseEntity batchDeleteUpdateZxEqEquipScrape(@RequestBody(required = false) List<ZxEqEquipScrape> zxEqEquipScrapeList) {
        return zxEqEquipScrapeService.batchDeleteUpdateZxEqEquipScrape(zxEqEquipScrapeList);
    }

    @ApiOperation(value="上报设备异动-设备报废", notes="上报设备异动-设备报废")
    @ApiImplicitParam(name = "zxEqEquipScrape", value = "设备异动-设备报废entity", dataType = "ZxEqEquipScrape")
    @RequireToken
    @PostMapping("/reportZxEqEquipScrape")
    public ResponseEntity reportZxEqEquipScrape(@RequestBody(required = false) ZxEqEquipScrape zxEqEquipScrape) {
        return zxEqEquipScrapeService.reportZxEqEquipScrape(zxEqEquipScrape);
    }
    
    @ApiOperation(value="查询详情设备异动-设备报废", notes="查询详情设备异动-设备报废")
    @ApiImplicitParam(name = "zxEqEquipScrape", value = "设备异动-设备报废entity", dataType = "ZxEqEquipScrape")
    @PostMapping("/ureportZxEqEquipScrape")
    public ZxEqEquipScrape ureportZxEqEquipScrape(@RequestBody(required = false) ZxEqEquipScrape zxEqEquipScrape) {
        return zxEqEquipScrapeService.ureportZxEqEquipScrape(zxEqEquipScrape);
    }
    @ApiOperation(value="审批通过设备异动-设备报废", notes="审批通过设备异动-设备报废")
    @ApiImplicitParam(name = "zxEqEquipScrape", value = "设备异动-设备报废entity", dataType = "ZxEqEquipScrape")
    @RequireToken
    @PostMapping("/agreeZxEqEquipScrape")
    public ResponseEntity agreeZxEqEquipScrape(@RequestBody(required = false) ZxEqEquipScrape zxEqEquipScrape) {
        return zxEqEquipScrapeService.agreeZxEqEquipScrape(zxEqEquipScrape);
    }
    
    
    @ApiOperation(value="审批未通过设备异动-设备报废", notes="审批未通过设备异动-设备报废")
    @ApiImplicitParam(name = "zxEqEquipScrape", value = "设备异动-设备报废entity", dataType = "ZxEqEquipScrape")
    @RequireToken
    @PostMapping("/refuseZxEqEquipScrape")
    public ResponseEntity refuseZxEqEquipScrape(@RequestBody(required = false) ZxEqEquipScrape zxEqEquipScrape) {
        return zxEqEquipScrapeService.refuseZxEqEquipScrape(zxEqEquipScrape);
    }
    
}
