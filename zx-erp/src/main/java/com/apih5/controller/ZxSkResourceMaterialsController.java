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
import com.apih5.mybatis.pojo.ZxSkResourceMaterials;
import com.apih5.service.ZxSkResourceMaterialsService;

@RestController
public class ZxSkResourceMaterialsController {

    @Autowired(required = true)
    private ZxSkResourceMaterialsService zxSkResourceMaterialsService;

    @ApiOperation(value="查询材料基础数据", notes="查询材料基础数据")
    @ApiImplicitParam(name = "zxSkResourceMaterials", value = "材料基础数据entity", dataType = "ZxSkResourceMaterials")
    @RequireToken
    @PostMapping("/getZxSkResourceMaterialsList")
    public ResponseEntity getZxSkResourceMaterialsList(@RequestBody(required = false) ZxSkResourceMaterials zxSkResourceMaterials) {
        return zxSkResourceMaterialsService.getZxSkResourceMaterialsListByCondition(zxSkResourceMaterials);
    }

    @ApiOperation(value="查询详情材料基础数据", notes="查询详情材料基础数据")
    @ApiImplicitParam(name = "zxSkResourceMaterials", value = "材料基础数据entity", dataType = "ZxSkResourceMaterials")
    @RequireToken
    @PostMapping("/getZxSkResourceMaterialsDetails")
    public ResponseEntity getZxSkResourceMaterialsDetails(@RequestBody(required = false) ZxSkResourceMaterials zxSkResourceMaterials) {
        return zxSkResourceMaterialsService.getZxSkResourceMaterialsDetails(zxSkResourceMaterials);
    }

    @ApiOperation(value="新增材料基础数据", notes="新增材料基础数据")
    @ApiImplicitParam(name = "zxSkResourceMaterials", value = "材料基础数据entity", dataType = "ZxSkResourceMaterials")
    @RequireToken
    @PostMapping("/addZxSkResourceMaterials")
    public ResponseEntity addZxSkResourceMaterials(@RequestBody(required = false) ZxSkResourceMaterials zxSkResourceMaterials) {
        return zxSkResourceMaterialsService.saveZxSkResourceMaterials(zxSkResourceMaterials);
    }

    @ApiOperation(value="更新材料基础数据", notes="更新材料基础数据")
    @ApiImplicitParam(name = "zxSkResourceMaterials", value = "材料基础数据entity", dataType = "ZxSkResourceMaterials")
    @RequireToken
    @PostMapping("/updateZxSkResourceMaterials")
    public ResponseEntity updateZxSkResourceMaterials(@RequestBody(required = false) ZxSkResourceMaterials zxSkResourceMaterials) {
        return zxSkResourceMaterialsService.updateZxSkResourceMaterials(zxSkResourceMaterials);
    }

    @ApiOperation(value="删除材料基础数据", notes="删除材料基础数据")
    @ApiImplicitParam(name = "zxSkResourceMaterialsList", value = "材料基础数据List", dataType = "List<ZxSkResourceMaterials>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxSkResourceMaterials")
    public ResponseEntity batchDeleteUpdateZxSkResourceMaterials(@RequestBody(required = false) List<ZxSkResourceMaterials> zxSkResourceMaterialsList) {
        return zxSkResourceMaterialsService.batchDeleteUpdateZxSkResourceMaterials(zxSkResourceMaterialsList);
    }

    @ApiOperation(value="批量启动材料基础数据", notes="批量启动材料基础数据")
    @ApiImplicitParam(name = "zxSkResourceMaterialsList", value = "材料基础数据List", dataType = "List<ZxSkResourceMaterials>")
    @RequireToken
    @PostMapping("/batchStartUpdateZxSkResourceMaterials")
    public ResponseEntity batchStartUpdateZxSkResourceMaterials(@RequestBody(required = false) List<ZxSkResourceMaterials> zxSkResourceMaterialsList) {
        return zxSkResourceMaterialsService.batchStartUpdateZxSkResourceMaterials(zxSkResourceMaterialsList);
    }

    @ApiOperation(value="批量停用材料基础数据", notes="批量停用材料基础数据")
    @ApiImplicitParam(name = "zxSkResourceMaterialsList", value = "材料基础数据List", dataType = "List<ZxSkResourceMaterials>")
    @RequireToken
    @PostMapping("/batchStopUpdateZxSkResourceMaterials")
    public ResponseEntity batchStopUpdateZxSkResourceMaterials(@RequestBody(required = false) List<ZxSkResourceMaterials> zxSkResourceMaterialsList) {
        return zxSkResourceMaterialsService.batchStopUpdateZxSkResourceMaterials(zxSkResourceMaterialsList);
    }

    //查询所有材料基础数据名字拼接(无分页写上了分页)
    @ApiOperation(value="查询所有材料基础数据名字拼接", notes="查询材料基础数据名字拼接")
    @ApiImplicitParam(name = "zxSkResourceMaterials", value = "材料基础数据entity", dataType = "ZxSkResourceMaterials")
    @RequireToken
    @PostMapping("/getZxSkResourceMaterialsListNameJoin")
    public ResponseEntity getZxSkResourceMaterialsListNameJoin(@RequestBody(required = false) ZxSkResourceMaterials zxSkResourceMaterials) {
        return zxSkResourceMaterialsService.getZxSkResourceMaterialsListNameJoin(zxSkResourceMaterials);
    }


    @ApiOperation(value="查询所有材料基础数据名字拼接", notes="查询材料基础数据名字拼接")
    @ApiImplicitParam(name = "zxSkResourceMaterials", value = "材料基础数据entity", dataType = "ZxSkResourceMaterials")
    @RequireToken
    @PostMapping("/getZxSkResourceMaterialsListNameJoinNotRevolve")
    public ResponseEntity getZxSkResourceMaterialsListNameJoinNotRevolve(@RequestBody(required = false) ZxSkResourceMaterials zxSkResourceMaterials) {
        return zxSkResourceMaterialsService.getZxSkResourceMaterialsListNameJoinNotRevolve(zxSkResourceMaterials);
    }


    @ApiOperation(value="查询材料基础数据非周转", notes="查询材料基础数据非周转")
    @ApiImplicitParam(name = "zxSkResourceMaterials", value = "材料基础数据entity", dataType = "ZxSkResourceMaterials")
    @RequireToken
    @PostMapping("/getZxSkResourceMaterialsListNotRevolving")
    public ResponseEntity getZxSkResourceMaterialsListNotRevolving(@RequestBody(required = false) ZxSkResourceMaterials zxSkResourceMaterials) {
        return zxSkResourceMaterialsService.getZxSkResourceMaterialsListNotRevolving(zxSkResourceMaterials);
    }

}
