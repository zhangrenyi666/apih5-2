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
import com.apih5.mybatis.pojo.ZxSkResCategoryMaterials;
import com.apih5.service.ZxSkResCategoryMaterialsService;

@RestController
public class ZxSkResCategoryMaterialsController {

    @Autowired(required = true)
    private ZxSkResCategoryMaterialsService zxSkResCategoryMaterialsService;

    @ApiOperation(value="查询材料分类", notes="查询材料分类")
    @ApiImplicitParam(name = "zxSkResCategoryMaterials", value = "材料分类entity", dataType = "ZxSkResCategoryMaterials")
    @RequireToken
    @PostMapping("/getZxSkResCategoryMaterialsList")
    public ResponseEntity getZxSkResCategoryMaterialsList(@RequestBody(required = false) ZxSkResCategoryMaterials zxSkResCategoryMaterials) {
        return zxSkResCategoryMaterialsService.getZxSkResCategoryMaterialsListByCondition(zxSkResCategoryMaterials);
    }

    @ApiOperation(value="查询详情材料分类", notes="查询详情材料分类")
    @ApiImplicitParam(name = "zxSkResCategoryMaterials", value = "材料分类entity", dataType = "ZxSkResCategoryMaterials")
    @RequireToken
    @PostMapping("/getZxSkResCategoryMaterialsDetails")
    public ResponseEntity getZxSkResCategoryMaterialsDetails(@RequestBody(required = false) ZxSkResCategoryMaterials zxSkResCategoryMaterials) {
        return zxSkResCategoryMaterialsService.getZxSkResCategoryMaterialsDetails(zxSkResCategoryMaterials);
    }

    @ApiOperation(value="新增材料分类", notes="新增材料分类")
    @ApiImplicitParam(name = "zxSkResCategoryMaterials", value = "材料分类entity", dataType = "ZxSkResCategoryMaterials")
    @RequireToken
    @PostMapping("/addZxSkResCategoryMaterials")
    public ResponseEntity addZxSkResCategoryMaterials(@RequestBody(required = false) ZxSkResCategoryMaterials zxSkResCategoryMaterials) {
        return zxSkResCategoryMaterialsService.saveZxSkResCategoryMaterials(zxSkResCategoryMaterials);
    }

    @ApiOperation(value="更新材料分类", notes="更新材料分类")
    @ApiImplicitParam(name = "zxSkResCategoryMaterials", value = "材料分类entity", dataType = "ZxSkResCategoryMaterials")
    @RequireToken
    @PostMapping("/updateZxSkResCategoryMaterials")
    public ResponseEntity updateZxSkResCategoryMaterials(@RequestBody(required = false) ZxSkResCategoryMaterials zxSkResCategoryMaterials) {
        return zxSkResCategoryMaterialsService.updateZxSkResCategoryMaterials(zxSkResCategoryMaterials);
    }

    @ApiOperation(value="删除材料分类", notes="删除材料分类")
    @ApiImplicitParam(name = "zxSkResCategoryMaterialsList", value = "材料分类List", dataType = "List<ZxSkResCategoryMaterials>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxSkResCategoryMaterials")
    public ResponseEntity batchDeleteUpdateZxSkResCategoryMaterials(@RequestBody(required = false) List<ZxSkResCategoryMaterials> zxSkResCategoryMaterialsList) {
        return zxSkResCategoryMaterialsService.batchDeleteUpdateZxSkResCategoryMaterials(zxSkResCategoryMaterialsList);
    }

    @ApiOperation(value="查询材料树", notes="查询材料树")
    @ApiImplicitParam(name = "zxSkResCategoryMaterials", value = "查询材料分类entity", dataType = "zxSkResCategoryMaterials")
    @RequireToken
    @PostMapping("/getZxSkResCategoryMaterialsTree")
    public ResponseEntity getZxSkResCategoryMaterialsTree(@RequestBody(required = false) ZxSkResCategoryMaterials zxSkResCategoryMaterials) {
        return zxSkResCategoryMaterialsService.getZxSkResCategoryMaterialsTree(zxSkResCategoryMaterials);
    }

    @ApiOperation(value="批量启动材料分类", notes="批量启动材料分类")
    @ApiImplicitParam(name = "zxSkResCategoryMaterialsList", value = "材料分类List", dataType = "List<ZxSkResCategoryMaterials>")
    @RequireToken
    @PostMapping("/batchStartUpdateZxSkResCategoryMaterials")
    public ResponseEntity batchStartUpdateZxSkResCategoryMaterials(@RequestBody(required = false) List<ZxSkResCategoryMaterials> zxSkResCategoryMaterialsList) {
        return zxSkResCategoryMaterialsService.batchStartUpdateZxSkResCategoryMaterials(zxSkResCategoryMaterialsList);
    }

    @ApiOperation(value="批量停用材料分类", notes="批量停用材料分类")
    @ApiImplicitParam(name = "zxSkResCategoryMaterialsList", value = "材料分类List", dataType = "List<ZxSkResCategoryMaterials>")
    @RequireToken
    @PostMapping("/batchStopUpdateZxSkResCategoryMaterials")
    public ResponseEntity batchStopUpdateZxSkResCategoryMaterials(@RequestBody(required = false) List<ZxSkResCategoryMaterials> zxSkResCategoryMaterialsList) {
        return zxSkResCategoryMaterialsService.batchStopUpdateZxSkResCategoryMaterials(zxSkResCategoryMaterialsList);
    }

    @ApiOperation(value="查询材料大类", notes="查询材料大类")
    @ApiImplicitParam(name = "zxSkResCategoryMaterials", value = "材料分类List", dataType = "ZxSkResCategoryMaterials")
    @RequireToken
    @PostMapping("/getZxSkResCategoryMaterialsListResource")
    public ResponseEntity getZxSkResCategoryMaterialsListResource(@RequestBody(required = false) ZxSkResCategoryMaterials zxSkResCategoryMaterials) {
        return zxSkResCategoryMaterialsService.getZxSkResCategoryMaterialsListResource(zxSkResCategoryMaterials);
    }

    @ApiOperation(value="根据大类查询材料基础数据名字拼接", notes="根据大类查询材料基础数据名字拼接")
    @ApiImplicitParam(name = "zxSkResCategoryMaterials", value = "材料基础数据entity", dataType = "ZxSkResCategoryMaterials")
    @RequireToken
    @PostMapping("/getZxSkResourceMaterialsListNameJoinResource")
    public ResponseEntity getZxSkResourceMaterialsListNameJoinResource(@RequestBody(required = false) ZxSkResCategoryMaterials zxSkResCategoryMaterials) {
        return zxSkResCategoryMaterialsService.getZxSkResourceMaterialsListNameJoinResource(zxSkResCategoryMaterials);
    }

    //写死大类和明细数据前20条
    @ApiOperation(value="大类和明细的数据", notes="大类和明细的数据")
    @ApiImplicitParam(name = "zxSkResCategoryMaterials", value = "大类和明细的数据List", dataType = "ZxSkResCategoryMaterials")
    @RequireToken
    @PostMapping("/getZxSkResCategoryMaterialsAll")
    public ResponseEntity getZxSkResCategoryMaterialsAll(@RequestBody(required = false) ZxSkResCategoryMaterials zxSkResCategoryMaterials) {
        return zxSkResCategoryMaterialsService.getZxSkResCategoryMaterialsAll(zxSkResCategoryMaterials);
    }

    @ApiOperation(value="根据大类查询该分类下所有物资编码", notes="根据大类查询该分类下所有物资编码")
    @ApiImplicitParam(name = "zxSkResCategoryMaterials", value = "物资编码entity", dataType = "ZxSkResCategoryMaterials")
    @RequireToken
    @PostMapping("/getZxSkResCategoryMaterialsAllResource")
    public ResponseEntity getZxSkResCategoryMaterialsAllResource(@RequestBody(required = false) ZxSkResCategoryMaterials zxSkResCategoryMaterials) {
        return zxSkResCategoryMaterialsService.getZxSkResCategoryMaterialsAllResource(zxSkResCategoryMaterials);
    }

    @ApiOperation(value="查询材料大类无周转", notes="查询材料大类无周转")
    @ApiImplicitParam(name = "zxSkResCategoryMaterials", value = "材料分类List", dataType = "ZxSkResCategoryMaterials")
    @RequireToken
    @PostMapping("/getZxSkResCategoryMaterialsListResourceNotRevolve")
    public ResponseEntity getZxSkResCategoryMaterialsListResourceNotRevolve(@RequestBody(required = false) ZxSkResCategoryMaterials zxSkResCategoryMaterials) {
        return zxSkResCategoryMaterialsService.getZxSkResCategoryMaterialsListResourceNotRevolve(zxSkResCategoryMaterials);
    }

}
