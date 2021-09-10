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
import com.apih5.mybatis.pojo.ZjTzSpecialYearPoint;
import com.apih5.service.ZjTzSpecialYearPointService;

@RestController
public class ZjTzSpecialYearPointController {

    @Autowired(required = true)
    private ZjTzSpecialYearPointService zjTzSpecialYearPointService;

    @ApiOperation(value="查询专项活动-年底总纲要点", notes="查询专项活动-年底总纲要点")
    @ApiImplicitParam(name = "zjTzSpecialYearPoint", value = "专项活动-年底总纲要点entity", dataType = "ZjTzSpecialYearPoint")
    @RequireToken
    @PostMapping("/getZjTzSpecialYearPointList")
    public ResponseEntity getZjTzSpecialYearPointList(@RequestBody(required = false) ZjTzSpecialYearPoint zjTzSpecialYearPoint) {
        return zjTzSpecialYearPointService.getZjTzSpecialYearPointListByCondition(zjTzSpecialYearPoint);
    }

    @ApiOperation(value="查询详情专项活动-年底总纲要点", notes="查询详情专项活动-年底总纲要点")
    @ApiImplicitParam(name = "zjTzSpecialYearPoint", value = "专项活动-年底总纲要点entity", dataType = "ZjTzSpecialYearPoint")
    @RequireToken
    @PostMapping("/getZjTzSpecialYearPointDetails")
    public ResponseEntity getZjTzSpecialYearPointDetails(@RequestBody(required = false) ZjTzSpecialYearPoint zjTzSpecialYearPoint) {
        return zjTzSpecialYearPointService.getZjTzSpecialYearPointDetails(zjTzSpecialYearPoint);
    }

    @ApiOperation(value="新增专项活动-年底总纲要点", notes="新增专项活动-年底总纲要点")
    @ApiImplicitParam(name = "zjTzSpecialYearPoint", value = "专项活动-年底总纲要点entity", dataType = "ZjTzSpecialYearPoint")
    @RequireToken
    @PostMapping("/addZjTzSpecialYearPoint")
    public ResponseEntity addZjTzSpecialYearPoint(@RequestBody(required = false) ZjTzSpecialYearPoint zjTzSpecialYearPoint) {
        return zjTzSpecialYearPointService.saveZjTzSpecialYearPoint(zjTzSpecialYearPoint);
    }

    @ApiOperation(value="更新专项活动-年底总纲要点", notes="更新专项活动-年底总纲要点")
    @ApiImplicitParam(name = "zjTzSpecialYearPoint", value = "专项活动-年底总纲要点entity", dataType = "ZjTzSpecialYearPoint")
    @RequireToken
    @PostMapping("/updateZjTzSpecialYearPoint")
    public ResponseEntity updateZjTzSpecialYearPoint(@RequestBody(required = false) ZjTzSpecialYearPoint zjTzSpecialYearPoint) {
        return zjTzSpecialYearPointService.updateZjTzSpecialYearPoint(zjTzSpecialYearPoint);
    }

    @ApiOperation(value="删除专项活动-年底总纲要点", notes="删除专项活动-年底总纲要点")
    @ApiImplicitParam(name = "zjTzSpecialYearPointList", value = "专项活动-年底总纲要点List", dataType = "List<ZjTzSpecialYearPoint>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZjTzSpecialYearPoint")
    public ResponseEntity batchDeleteUpdateZjTzSpecialYearPoint(@RequestBody(required = false) List<ZjTzSpecialYearPoint> zjTzSpecialYearPointList) {
        return zjTzSpecialYearPointService.batchDeleteUpdateZjTzSpecialYearPoint(zjTzSpecialYearPointList);
    }
    
    @ApiOperation(value="广而告之专项活动-年底总纲要点", notes="广而告之专项活动-年底总纲要点")
    @ApiImplicitParam(name = "zjTzSpecialYearPoint", value = "专项活动-年底总纲要点entity", dataType = "ZjTzSpecialYearPoint")
    @RequireToken
    @PostMapping("/toHomeShowZjTzSpecialYearPoint")
    public ResponseEntity toHomeShowZjTzSpecialYearPoint(@RequestBody(required = false) ZjTzSpecialYearPoint zjTzSpecialYearPoint) {
        return zjTzSpecialYearPointService.toHomeShowZjTzSpecialYearPoint(zjTzSpecialYearPoint);
    }

    @ApiOperation(value="批量发布专项活动-年底总纲要点", notes="批量发布专项活动-年底总纲要点")
    @ApiImplicitParam(name = "zjTzSpecialYearPointList", value = "专项活动-年底总纲要点List", dataType = "List<ZjTzSpecialYearPoint>")
    @RequireToken
    @PostMapping("/batchReleaseZjTzSpecialYearPoint")
    public ResponseEntity batchReleaseZjTzSpecialYearPoint(@RequestBody(required = false) List<ZjTzSpecialYearPoint> zjTzSpecialYearPointList) {
        return zjTzSpecialYearPointService.batchReleaseZjTzSpecialYearPoint(zjTzSpecialYearPointList);
    }
    
    @ApiOperation(value="批量撤回专项活动-年底总纲要点", notes="批量撤回专项活动-年底总纲要点")
    @ApiImplicitParam(name = "zjTzSpecialYearPointList", value = "专项活动-年底总纲要点List", dataType = "List<ZjTzSpecialYearPoint>")
    @RequireToken
    @PostMapping("/batchRecallZjTzSpecialYearPoint")
    public ResponseEntity batchRecallZjTzSpecialYearPoint(@RequestBody(required = false) List<ZjTzSpecialYearPoint> zjTzSpecialYearPointList) {
        return zjTzSpecialYearPointService.batchRecallZjTzSpecialYearPoint(zjTzSpecialYearPointList);
    }
    
    @ApiOperation(value="批量导出专项活动-年底总纲要点附件", notes="批量导出专项活动-年底总纲要点附件")
    @ApiImplicitParam(name = "zjTzSpecialYearPointList", value = "专项活动-年底总纲要点List", dataType = "List<ZjTzSpecialYearPoint>")
    @RequireToken
    @PostMapping("/batchExportZjTzSpecialYearPointFile")
    public ResponseEntity batchExportZjTzSpecialYearPointFile(@RequestBody(required = false) List<ZjTzSpecialYearPoint> zjTzSpecialYearPointList) {
        return zjTzSpecialYearPointService.batchExportZjTzSpecialYearPointFile(zjTzSpecialYearPointList);
    }

}
