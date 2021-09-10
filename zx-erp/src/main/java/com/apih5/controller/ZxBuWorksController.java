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
import com.apih5.mybatis.pojo.ZxBuWorks;
import com.apih5.service.ZxBuWorksService;

@RestController
public class ZxBuWorksController {

    @Autowired(required = true)
    private ZxBuWorksService zxBuWorksService;

    @ApiOperation(value="查询标准清单库", notes="查询标准清单库")
    @ApiImplicitParam(name = "zxBuWorks", value = "标准清单库entity", dataType = "ZxBuWorks")
    @RequireToken
    @PostMapping("/getZxBuWorksList")
    public ResponseEntity getZxBuWorksList(@RequestBody(required = false) ZxBuWorks zxBuWorks) {
        return zxBuWorksService.getZxBuWorksListByCondition(zxBuWorks);
    }

    @ApiOperation(value="查询详情标准清单库", notes="查询详情标准清单库")
    @ApiImplicitParam(name = "zxBuWorks", value = "标准清单库entity", dataType = "ZxBuWorks")
    @RequireToken
    @PostMapping("/getZxBuWorksDetail")
    public ResponseEntity getZxBuWorksDetail(@RequestBody(required = false) ZxBuWorks zxBuWorks) {
        return zxBuWorksService.getZxBuWorksDetail(zxBuWorks);
    }

    @ApiOperation(value="新增标准清单库", notes="新增标准清单库")
    @ApiImplicitParam(name = "zxBuWorks", value = "标准清单库entity", dataType = "ZxBuWorks")
    @RequireToken
    @PostMapping("/addZxBuWorks")
    public ResponseEntity addZxBuWorks(@RequestBody(required = false) ZxBuWorks zxBuWorks) {
        return zxBuWorksService.saveZxBuWorks(zxBuWorks);
    }

    @ApiOperation(value="更新标准清单库", notes="更新标准清单库")
    @ApiImplicitParam(name = "zxBuWorks", value = "标准清单库entity", dataType = "ZxBuWorks")
    @RequireToken
    @PostMapping("/updateZxBuWorks")
    public ResponseEntity updateZxBuWorks(@RequestBody(required = false) ZxBuWorks zxBuWorks) {
        return zxBuWorksService.updateZxBuWorks(zxBuWorks);
    }

    @ApiOperation(value="删除标准清单库", notes="删除标准清单库")
    @ApiImplicitParam(name = "zxBuWorksList", value = "标准清单库List", dataType = "List<ZxBuWorks>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxBuWorks")
    public ResponseEntity batchDeleteUpdateZxBuWorks(@RequestBody(required = false) List<ZxBuWorks> zxBuWorksList) {
        return zxBuWorksService.batchDeleteUpdateZxBuWorks(zxBuWorksList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

    @ApiOperation(value="获取标准清单库树", notes="获取标准清单库树")
    @ApiImplicitParam(name = "zxBuWorksList", value = "标准清单库List", dataType = "List<ZxBuWorks>")
    @RequireToken
    @PostMapping("/getZxBuWorksTree")
    public ResponseEntity getZxBuWorksTree(@RequestBody(required = false) ZxBuWorks zxBuWorks) {
        return zxBuWorksService.getZxBuWorksTree(zxBuWorks);
    }

    @ApiOperation(value="获取标准清单库列表", notes="获取标准清单库列表")
    @ApiImplicitParam(name = "zxBuWorksList", value = "标准清单库List", dataType = "List<ZxBuWorks>")
    @RequireToken
    @PostMapping("/getZxBuWorksTreeList")
    public ResponseEntity getZxBuWorksTreeList(@RequestBody(required = false) ZxBuWorks zxBuWorks) {
        return zxBuWorksService.getZxBuWorksTreeList(zxBuWorks);
    }

    //获取所有标准清单库列表
    @ApiOperation(value="获取所有标准清单库列表", notes="获取所有标准清单库列表")
    @ApiImplicitParam(name = "zxBuWorksList", value = "标准清单库List", dataType = "List<ZxBuWorks>")
    @RequireToken
    @PostMapping("/getZxBuWorksTreeListAll")
    public ResponseEntity getZxBuWorksTreeListAll(@RequestBody(required = false) ZxBuWorks zxBuWorks) {
        return zxBuWorksService.getZxBuWorksTreeListAll(zxBuWorks);
    }

    //通过项目获取清单  数据清单编号[],数据库清单名称[]
    @ApiOperation(value="通过项目获取清单编号清单名称", notes="通过项目获取清单编号清单名称")
    @ApiImplicitParam(name = "zxBuWorks", value = "标准清单库entity", dataType = "ZxBuWorks")
    @RequireToken
    @PostMapping("/getZxBuWorksNoName")
    public ResponseEntity getZxBuWorksNoName(@RequestBody(required = false) ZxBuWorks zxBuWorks) {
        return zxBuWorksService.getZxBuWorksNoName(zxBuWorks);
    }

    //通过项目获取清单  材料编号[] 材料名称[]
    @ApiOperation(value="通过项目获取清单材料编号材料名称", notes="通过项目获取清单材料编号材料名称")
    @ApiImplicitParam(name = "zxBuWorks", value = "标准清单库entity", dataType = "ZxBuWorks")
    @RequireToken
    @PostMapping("/getZxBuWorksResNoName")
    public ResponseEntity getZxBuWorksResNoName(@RequestBody(required = false) ZxBuWorks zxBuWorks) {
        return zxBuWorksService.getZxBuWorksResNoName(zxBuWorks);
    }







}
