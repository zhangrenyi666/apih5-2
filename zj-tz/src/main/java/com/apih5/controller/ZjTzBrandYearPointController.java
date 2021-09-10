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
import com.apih5.mybatis.pojo.ZjTzBrandYearPoint;
import com.apih5.service.ZjTzBrandYearPointService;

@RestController
public class ZjTzBrandYearPointController {

    @Autowired(required = true)
    private ZjTzBrandYearPointService zjTzBrandYearPointService;

    @ApiOperation(value="查询品牌建设-年底总纲要点", notes="查询品牌建设-年底总纲要点")
    @ApiImplicitParam(name = "zjTzBrandYearPoint", value = "品牌建设-年底总纲要点entity", dataType = "ZjTzBrandYearPoint")
    @RequireToken
    @PostMapping("/getZjTzBrandYearPointList")
    public ResponseEntity getZjTzBrandYearPointList(@RequestBody(required = false) ZjTzBrandYearPoint zjTzBrandYearPoint) {
        return zjTzBrandYearPointService.getZjTzBrandYearPointListByCondition(zjTzBrandYearPoint);
    }

    @ApiOperation(value="查询详情品牌建设-年底总纲要点", notes="查询详情品牌建设-年底总纲要点")
    @ApiImplicitParam(name = "zjTzBrandYearPoint", value = "品牌建设-年底总纲要点entity", dataType = "ZjTzBrandYearPoint")
    @RequireToken
    @PostMapping("/getZjTzBrandYearPointDetails")
    public ResponseEntity getZjTzBrandYearPointDetails(@RequestBody(required = false) ZjTzBrandYearPoint zjTzBrandYearPoint) {
        return zjTzBrandYearPointService.getZjTzBrandYearPointDetails(zjTzBrandYearPoint);
    }

    @ApiOperation(value="新增品牌建设-年底总纲要点", notes="新增品牌建设-年底总纲要点")
    @ApiImplicitParam(name = "zjTzBrandYearPoint", value = "品牌建设-年底总纲要点entity", dataType = "ZjTzBrandYearPoint")
    @RequireToken
    @PostMapping("/addZjTzBrandYearPoint")
    public ResponseEntity addZjTzBrandYearPoint(@RequestBody(required = false) ZjTzBrandYearPoint zjTzBrandYearPoint) {
        return zjTzBrandYearPointService.saveZjTzBrandYearPoint(zjTzBrandYearPoint);
    }

    @ApiOperation(value="更新品牌建设-年底总纲要点", notes="更新品牌建设-年底总纲要点")
    @ApiImplicitParam(name = "zjTzBrandYearPoint", value = "品牌建设-年底总纲要点entity", dataType = "ZjTzBrandYearPoint")
    @RequireToken
    @PostMapping("/updateZjTzBrandYearPoint")
    public ResponseEntity updateZjTzBrandYearPoint(@RequestBody(required = false) ZjTzBrandYearPoint zjTzBrandYearPoint) {
        return zjTzBrandYearPointService.updateZjTzBrandYearPoint(zjTzBrandYearPoint);
    }

    @ApiOperation(value="删除品牌建设-年底总纲要点", notes="删除品牌建设-年底总纲要点")
    @ApiImplicitParam(name = "zjTzBrandYearPointList", value = "品牌建设-年底总纲要点List", dataType = "List<ZjTzBrandYearPoint>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZjTzBrandYearPoint")
    public ResponseEntity batchDeleteUpdateZjTzBrandYearPoint(@RequestBody(required = false) List<ZjTzBrandYearPoint> zjTzBrandYearPointList) {
        return zjTzBrandYearPointService.batchDeleteUpdateZjTzBrandYearPoint(zjTzBrandYearPointList);
    }
    
    @ApiOperation(value="广而告之更新品牌建设-年底总纲要点", notes="广而告之更新品牌建设-年底总纲要点")
    @ApiImplicitParam(name = "zjTzBrandYearPoint", value = "品牌建设-年底总纲要点entity", dataType = "ZjTzBrandYearPoint")
    @RequireToken
    @PostMapping("/toHomeShowZjTzBrandYearPoint")
    public ResponseEntity toHomeShowZjTzBrandYearPoint(@RequestBody(required = false) ZjTzBrandYearPoint zjTzBrandYearPoint) {
        return zjTzBrandYearPointService.toHomeShowZjTzBrandYearPoint(zjTzBrandYearPoint);
    }

    @ApiOperation(value="批量发布品牌建设-年底总纲要点", notes="批量发布品牌建设-年底总纲要点")
    @ApiImplicitParam(name = "zjTzBrandYearPointList", value = "品牌建设-年底总纲要点List", dataType = "List<ZjTzBrandYearPoint>")
    @RequireToken
    @PostMapping("/batchReleaseZjTzBrandYearPoint")
    public ResponseEntity batchReleaseZjTzBrandYearPoint(@RequestBody(required = false) List<ZjTzBrandYearPoint> zjTzBrandYearPointList) {
        return zjTzBrandYearPointService.batchReleaseZjTzBrandYearPoint(zjTzBrandYearPointList);
    }
    
    @ApiOperation(value="批量撤回品牌建设-年底总纲要点", notes="批量撤回品牌建设-年底总纲要点")
    @ApiImplicitParam(name = "zjTzBrandYearPointList", value = "品牌建设-年底总纲要点List", dataType = "List<ZjTzBrandYearPoint>")
    @RequireToken
    @PostMapping("/batchRecallZjTzBrandYearPoint")
    public ResponseEntity batchRecallZjTzBrandYearPoint(@RequestBody(required = false) List<ZjTzBrandYearPoint> zjTzBrandYearPointList) {
        return zjTzBrandYearPointService.batchRecallZjTzBrandYearPoint(zjTzBrandYearPointList);
    }
    
    @ApiOperation(value="批量导出品牌建设-年底总纲要点附件", notes="批量导出品牌建设-年底总纲要点附件")
    @ApiImplicitParam(name = "zjTzBrandYearPointList", value = "品牌建设-年底总纲要点List", dataType = "List<ZjTzBrandYearPoint>")
    @RequireToken
    @PostMapping("/batchExportZjTzBrandYearPointFile")
    public ResponseEntity batchExportZjTzBrandYearPointFile(@RequestBody(required = false) List<ZjTzBrandYearPoint> zjTzBrandYearPointList) {
        return zjTzBrandYearPointService.batchExportZjTzBrandYearPointFile(zjTzBrandYearPointList);
    }
    
    @ApiOperation(value="广而告之品牌建设-年底总纲要点", notes="广而告之品牌建设-年底总纲要点")
    @ApiImplicitParam(name = "zjTzBrandYearPoint", value = "品牌建设-年底总纲要点entity", dataType = "ZjTzBrandYearPoint")
    @RequireToken
    @PostMapping("/getZjTzBrandYearPointHome")
    public ResponseEntity getZjTzBrandYearPointHome(@RequestBody(required = false) ZjTzBrandYearPoint zjTzBrandYearPoint) {
        return zjTzBrandYearPointService.getZjTzBrandYearPointHome(zjTzBrandYearPoint);
    }
}
