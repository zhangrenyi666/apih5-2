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
import com.apih5.mybatis.pojo.ZxBuYgjResTechnics;
import com.apih5.service.ZxBuYgjResTechnicsService;

@RestController
public class ZxBuYgjResTechnicsController {

    @Autowired(required = true)
    private ZxBuYgjResTechnicsService zxBuYgjResTechnicsService;

    @ApiOperation(value="查询标准清单工序", notes="查询标准清单工序")
    @ApiImplicitParam(name = "zxBuYgjResTechnics", value = "标准清单工序entity", dataType = "ZxBuYgjResTechnics")
    @RequireToken
    @PostMapping("/getZxBuYgjResTechnicsList")
    public ResponseEntity getZxBuYgjResTechnicsList(@RequestBody(required = false) ZxBuYgjResTechnics zxBuYgjResTechnics) {
        return zxBuYgjResTechnicsService.getZxBuYgjResTechnicsListByCondition(zxBuYgjResTechnics);
    }

    @ApiOperation(value="查询详情标准清单工序", notes="查询详情标准清单工序")
    @ApiImplicitParam(name = "zxBuYgjResTechnics", value = "标准清单工序entity", dataType = "ZxBuYgjResTechnics")
    @RequireToken
    @PostMapping("/getZxBuYgjResTechnicsDetail")
    public ResponseEntity getZxBuYgjResTechnicsDetail(@RequestBody(required = false) ZxBuYgjResTechnics zxBuYgjResTechnics) {
        return zxBuYgjResTechnicsService.getZxBuYgjResTechnicsDetail(zxBuYgjResTechnics);
    }

    @ApiOperation(value="新增标准清单工序", notes="新增标准清单工序")
    @ApiImplicitParam(name = "zxBuYgjResTechnics", value = "标准清单工序entity", dataType = "ZxBuYgjResTechnics")
    @RequireToken
    @PostMapping("/addZxBuYgjResTechnics")
    public ResponseEntity addZxBuYgjResTechnics(@RequestBody(required = false) ZxBuYgjResTechnics zxBuYgjResTechnics) {
        return zxBuYgjResTechnicsService.saveZxBuYgjResTechnics(zxBuYgjResTechnics);
    }

    @ApiOperation(value="更新标准清单工序", notes="更新标准清单工序")
    @ApiImplicitParam(name = "zxBuYgjResTechnics", value = "标准清单工序entity", dataType = "ZxBuYgjResTechnics")
    @RequireToken
    @PostMapping("/updateZxBuYgjResTechnics")
    public ResponseEntity updateZxBuYgjResTechnics(@RequestBody(required = false) ZxBuYgjResTechnics zxBuYgjResTechnics) {
        return zxBuYgjResTechnicsService.updateZxBuYgjResTechnics(zxBuYgjResTechnics);
    }

    @ApiOperation(value="删除标准清单工序", notes="删除标准清单工序")
    @ApiImplicitParam(name = "zxBuYgjResTechnicsList", value = "标准清单工序List", dataType = "List<ZxBuYgjResTechnics>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxBuYgjResTechnics")
    public ResponseEntity batchDeleteUpdateZxBuYgjResTechnics(@RequestBody(required = false) List<ZxBuYgjResTechnics> zxBuYgjResTechnicsList) {
        return zxBuYgjResTechnicsService.batchDeleteUpdateZxBuYgjResTechnics(zxBuYgjResTechnicsList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

    //关联  项目清单,挂接清单标准库
    @ApiOperation(value="清单关联", notes="清单关联")
    @ApiImplicitParam(name = "zxBuYgjResTechnicsList", value = "标准清单工序List", dataType = "List<ZxBuYgjResTechnics>")
    @RequireToken
    @PostMapping("/relevanceZxBuYgjResTechnics")
    public ResponseEntity relevanceZxBuYgjResTechnics(@RequestBody(required = false) ZxBuYgjResTechnics zxBuYgjResTechnics) {
        return zxBuYgjResTechnicsService.relevanceZxBuYgjResTechnics(zxBuYgjResTechnics);
    }

    //清除关联
    @ApiOperation(value="清除清单关联", notes="清除清单关联")
    @ApiImplicitParam(name = "zxBuYgjResTechnicsList", value = "标准清单工序List", dataType = "List<ZxBuYgjResTechnics>")
    @RequireToken
    @PostMapping("/removeRelevanceZxBuYgjResTechnics")
    public ResponseEntity removeRelevanceZxBuYgjResTechnics(@RequestBody(required = false) ZxBuYgjResTechnics zxBuYgjResTechnics) {
        return zxBuYgjResTechnicsService.removeRelevanceZxBuYgjResTechnics(zxBuYgjResTechnics);
    }

    @ApiOperation(value="批量更新标准清单工序", notes="批量更新标准清单工序")
    @ApiImplicitParam(name = "zxBuYgjResTechnics", value = "标准清单工序entity", dataType = "ZxBuYgjResTechnics")
    @RequireToken
    @PostMapping("/updateZxBuYgjResTechnicsList")
    public ResponseEntity updateZxBuYgjResTechnicsList(@RequestBody(required = false) List<ZxBuYgjResTechnics> zxBuYgjResTechnicsList) {
        return zxBuYgjResTechnicsService.updateZxBuYgjResTechnicsList(zxBuYgjResTechnicsList);
    }

    @ApiOperation(value="查询标准清单工序和数据库清单", notes="查询标准清单工序和数据库清单")
    @ApiImplicitParam(name = "zxBuYgjResTechnics", value = "标准清单工序entity", dataType = "ZxBuYgjResTechnics")
    @RequireToken
    @PostMapping("/getZxBuYgjResTechnicsAndQDList")
    public ResponseEntity getZxBuYgjResTechnicsAndQDList(@RequestBody(required = false) ZxBuYgjResTechnics zxBuYgjResTechnics) {
        return zxBuYgjResTechnicsService.getZxBuYgjResTechnicsAndQDList(zxBuYgjResTechnics);
    }





}
