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
import com.apih5.mybatis.pojo.ZxSkColInven;
import com.apih5.service.ZxSkColInvenService;

@RestController
public class ZxSkColInvenController {

    @Autowired(required = true)
    private ZxSkColInvenService zxSkColInvenService;

    @ApiOperation(value="查询协作队伍库存盘点", notes="查询协作队伍库存盘点")
    @ApiImplicitParam(name = "zxSkColInven", value = "协作队伍库存盘点entity", dataType = "ZxSkColInven")
    @RequireToken
    @PostMapping("/getZxSkColInvenList")
    public ResponseEntity getZxSkColInvenList(@RequestBody(required = false) ZxSkColInven zxSkColInven) {
        return zxSkColInvenService.getZxSkColInvenListByCondition(zxSkColInven);
    }

    @ApiOperation(value="查询详情协作队伍库存盘点", notes="查询详情协作队伍库存盘点")
    @ApiImplicitParam(name = "zxSkColInven", value = "协作队伍库存盘点entity", dataType = "ZxSkColInven")
    @RequireToken
    @PostMapping("/getZxSkColInvenDetail")
    public ResponseEntity getZxSkColInvenDetail(@RequestBody(required = false) ZxSkColInven zxSkColInven) {
        return zxSkColInvenService.getZxSkColInvenDetail(zxSkColInven);
    }

    @ApiOperation(value="新增协作队伍库存盘点", notes="新增协作队伍库存盘点")
    @ApiImplicitParam(name = "zxSkColInven", value = "协作队伍库存盘点entity", dataType = "ZxSkColInven")
    @RequireToken
    @PostMapping("/addZxSkColInven")
    public ResponseEntity addZxSkColInven(@RequestBody(required = false) ZxSkColInven zxSkColInven) {
        return zxSkColInvenService.saveZxSkColInven(zxSkColInven);
    }

    @ApiOperation(value="更新协作队伍库存盘点", notes="更新协作队伍库存盘点")
    @ApiImplicitParam(name = "zxSkColInven", value = "协作队伍库存盘点entity", dataType = "ZxSkColInven")
    @RequireToken
    @PostMapping("/updateZxSkColInven")
    public ResponseEntity updateZxSkColInven(@RequestBody(required = false) ZxSkColInven zxSkColInven) {
        return zxSkColInvenService.updateZxSkColInven(zxSkColInven);
    }

    @ApiOperation(value="删除协作队伍库存盘点", notes="删除协作队伍库存盘点")
    @ApiImplicitParam(name = "zxSkColInvenList", value = "协作队伍库存盘点List", dataType = "List<ZxSkColInven>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxSkColInven")
    public ResponseEntity batchDeleteUpdateZxSkColInven(@RequestBody(required = false) List<ZxSkColInven> zxSkColInvenList) {
        return zxSkColInvenService.batchDeleteUpdateZxSkColInven(zxSkColInvenList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

    //获取领过料的物资
    //领料单位orgID  内部单位customerOrgID(筛选条件)
    @ApiOperation(value="查询协作队伍库存盘点物资", notes="查询协作队伍库存盘点物资")
    @ApiImplicitParam(name = "zxSkColInven", value = "协作队伍库存盘点entity", dataType = "ZxSkColInven")
    @RequireToken
    @PostMapping("/getZxSkColInvenResourceList")
    public ResponseEntity getZxSkColInvenResourceList(@RequestBody(required = false) ZxSkColInven zxSkColInven) {
        return zxSkColInvenService.getZxSkColInvenResourceList(zxSkColInven);
    }

    //companyID: "1"
    @ApiOperation(value = "查询协作队伍", notes = "查询协作队伍")
    @ApiImplicitParam(name = "zxSkColInven", value = "协作队伍库存盘点entity", dataType = "ZxSkColInven")
    @RequireToken
    @PostMapping("/getZxSkColInvenInOrgList")
    public ResponseEntity getZxSkColInvenInOrgList(@RequestBody(required = false)ZxSkColInven zxSkColInven) {
        return zxSkColInvenService.getZxSkColInvenInOrgList(zxSkColInven);
    }




}
