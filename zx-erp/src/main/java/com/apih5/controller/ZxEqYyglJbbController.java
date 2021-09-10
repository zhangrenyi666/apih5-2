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
import com.apih5.mybatis.pojo.ZxEqYyglJbb;
import com.apih5.service.ZxEqYyglJbbService;

@RestController
public class ZxEqYyglJbbController {

    @Autowired(required = true)
    private ZxEqYyglJbbService zxEqYyglJbbService;

    @ApiOperation(value="查询运营管理业务采集季报表", notes="查询运营管理业务采集季报表")
    @ApiImplicitParam(name = "zxEqYyglJbb", value = "运营管理业务采集季报表entity", dataType = "ZxEqYyglJbb")
    @RequireToken
    @PostMapping("/getZxEqYyglJbbList")
    public ResponseEntity getZxEqYyglJbbList(@RequestBody(required = false) ZxEqYyglJbb zxEqYyglJbb) {
        return zxEqYyglJbbService.getZxEqYyglJbbListByCondition(zxEqYyglJbb);
    }

    @ApiOperation(value="查询详情运营管理业务采集季报表", notes="查询详情运营管理业务采集季报表")
    @ApiImplicitParam(name = "zxEqYyglJbb", value = "运营管理业务采集季报表entity", dataType = "ZxEqYyglJbb")
    @RequireToken
    @PostMapping("/getZxEqYyglJbbDetail")
    public ResponseEntity getZxEqYyglJbbDetail(@RequestBody(required = false) ZxEqYyglJbb zxEqYyglJbb) {
        return zxEqYyglJbbService.getZxEqYyglJbbDetail(zxEqYyglJbb);
    }

    @ApiOperation(value="新增运营管理业务采集季报表", notes="新增运营管理业务采集季报表")
    @ApiImplicitParam(name = "zxEqYyglJbb", value = "运营管理业务采集季报表entity", dataType = "ZxEqYyglJbb")
    @RequireToken
    @PostMapping("/addZxEqYyglJbb")
    public ResponseEntity addZxEqYyglJbb(@RequestBody(required = false) ZxEqYyglJbb zxEqYyglJbb) {
        return zxEqYyglJbbService.saveZxEqYyglJbb(zxEqYyglJbb);
    }

    @ApiOperation(value="更新运营管理业务采集季报表", notes="更新运营管理业务采集季报表")
    @ApiImplicitParam(name = "zxEqYyglJbb", value = "运营管理业务采集季报表entity", dataType = "ZxEqYyglJbb")
    @RequireToken
    @PostMapping("/updateZxEqYyglJbb")
    public ResponseEntity updateZxEqYyglJbb(@RequestBody(required = false) ZxEqYyglJbb zxEqYyglJbb) {
        return zxEqYyglJbbService.updateZxEqYyglJbb(zxEqYyglJbb);
    }

    @ApiOperation(value="删除运营管理业务采集季报表", notes="删除运营管理业务采集季报表")
    @ApiImplicitParam(name = "zxEqYyglJbbList", value = "运营管理业务采集季报表List", dataType = "List<ZxEqYyglJbb>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxEqYyglJbb")
    public ResponseEntity batchDeleteUpdateZxEqYyglJbb(@RequestBody(required = false) List<ZxEqYyglJbb> zxEqYyglJbbList) {
        return zxEqYyglJbbService.batchDeleteUpdateZxEqYyglJbb(zxEqYyglJbbList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    
    @ApiOperation(value="报表导出运营管理业务采集季报表", notes="报表导出运营管理业务采集季报表")
    @ApiImplicitParam(name = "zxEqYyglJbb", value = "设备台账entity", dataType = "ZxEqYyglJbb")
    @PostMapping("/ureportZxEqYyglJbb")
    public ZxEqYyglJbb ureportZxEqYyglJbb(@RequestBody(required = false) ZxEqYyglJbb zxEqYyglJbb) {
        return zxEqYyglJbbService.ureportZxEqYyglJbb(zxEqYyglJbb);
    }
    
    @ApiOperation(value="报表运营管理业务采集季报表", notes="报表运营管理业务采集季报表")
    @ApiImplicitParam(name = "zxEqYyglJbb", value = "设备台账entity", dataType = "ZxEqYyglJbb")
    @RequireToken
    @PostMapping("/ureportZxEqYyglJbbIdle")
    public ResponseEntity ureportZxEqYyglJbbIdle(@RequestBody(required = false) ZxEqYyglJbb zxEqYyglJbb) {
        return zxEqYyglJbbService.ureportZxEqYyglJbbIdle(zxEqYyglJbb);
    }
}
