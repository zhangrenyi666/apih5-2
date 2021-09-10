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
import com.apih5.mybatis.pojo.ZjTzBrandImplementPoint;
import com.apih5.service.ZjTzBrandImplementPointService;

@RestController
public class ZjTzBrandImplementPointController {

    @Autowired(required = true)
    private ZjTzBrandImplementPointService zjTzBrandImplementPointService;

    @ApiOperation(value="查询品牌建设-项目、专项实施方案要点", notes="查询品牌建设-项目、专项实施方案要点")
    @ApiImplicitParam(name = "zjTzBrandImplementPoint", value = "品牌建设-项目、专项实施方案要点entity", dataType = "ZjTzBrandImplementPoint")
    @RequireToken
    @PostMapping("/getZjTzBrandImplementPointList")
    public ResponseEntity getZjTzBrandImplementPointList(@RequestBody(required = false) ZjTzBrandImplementPoint zjTzBrandImplementPoint) {
        return zjTzBrandImplementPointService.getZjTzBrandImplementPointListByCondition(zjTzBrandImplementPoint);
    }

    @ApiOperation(value="查询详情品牌建设-项目、专项实施方案要点", notes="查询详情品牌建设-项目、专项实施方案要点")
    @ApiImplicitParam(name = "zjTzBrandImplementPoint", value = "品牌建设-项目、专项实施方案要点entity", dataType = "ZjTzBrandImplementPoint")
    @RequireToken
    @PostMapping("/getZjTzBrandImplementPointDetails")
    public ResponseEntity getZjTzBrandImplementPointDetails(@RequestBody(required = false) ZjTzBrandImplementPoint zjTzBrandImplementPoint) {
        return zjTzBrandImplementPointService.getZjTzBrandImplementPointDetails(zjTzBrandImplementPoint);
    }

    @ApiOperation(value="新增品牌建设-项目、专项实施方案要点", notes="新增品牌建设-项目、专项实施方案要点")
    @ApiImplicitParam(name = "zjTzBrandImplementPoint", value = "品牌建设-项目、专项实施方案要点entity", dataType = "ZjTzBrandImplementPoint")
    @RequireToken
    @PostMapping("/addZjTzBrandImplementPoint")
    public ResponseEntity addZjTzBrandImplementPoint(@RequestBody(required = false) ZjTzBrandImplementPoint zjTzBrandImplementPoint) {
        return zjTzBrandImplementPointService.saveZjTzBrandImplementPoint(zjTzBrandImplementPoint);
    }

    @ApiOperation(value="更新品牌建设-项目、专项实施方案要点", notes="更新品牌建设-项目、专项实施方案要点")
    @ApiImplicitParam(name = "zjTzBrandImplementPoint", value = "品牌建设-项目、专项实施方案要点entity", dataType = "ZjTzBrandImplementPoint")
    @RequireToken
    @PostMapping("/updateZjTzBrandImplementPoint")
    public ResponseEntity updateZjTzBrandImplementPoint(@RequestBody(required = false) ZjTzBrandImplementPoint zjTzBrandImplementPoint) {
        return zjTzBrandImplementPointService.updateZjTzBrandImplementPoint(zjTzBrandImplementPoint);
    }

    @ApiOperation(value="删除品牌建设-项目、专项实施方案要点", notes="删除品牌建设-项目、专项实施方案要点")
    @ApiImplicitParam(name = "zjTzBrandImplementPointList", value = "品牌建设-项目、专项实施方案要点List", dataType = "List<ZjTzBrandImplementPoint>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZjTzBrandImplementPoint")
    public ResponseEntity batchDeleteUpdateZjTzBrandImplementPoint(@RequestBody(required = false) List<ZjTzBrandImplementPoint> zjTzBrandImplementPointList) {
        return zjTzBrandImplementPointService.batchDeleteUpdateZjTzBrandImplementPoint(zjTzBrandImplementPointList);
    }
    
    @ApiOperation(value="广而告之品牌建设-项目、专项实施方案要点", notes="广而告之品牌建设-项目、专项实施方案要点")
    @ApiImplicitParam(name = "zjTzBrandImplementPoint", value = "品牌建设-项目、专项实施方案要点entity", dataType = "ZjTzBrandImplementPoint")
    @RequireToken
    @PostMapping("/toHomeShowZjTzBrandImplementPoint")
    public ResponseEntity toHomeShowZjTzBrandImplementPoint(@RequestBody(required = false) ZjTzBrandImplementPoint zjTzBrandImplementPoint) {
        return zjTzBrandImplementPointService.toHomeShowZjTzBrandImplementPoint(zjTzBrandImplementPoint);
    }

    @ApiOperation(value="批量上报品牌建设-项目、专项实施方案要点", notes="批量上报品牌建设-项目、专项实施方案要点")
    @ApiImplicitParam(name = "zjTzBrandImplementPointList", value = "品牌建设-项目、专项实施方案要点List", dataType = "List<ZjTzBrandImplementPoint>")
    @RequireToken
    @PostMapping("/batchReleaseZjTzBrandImplementPoint")
    public ResponseEntity batchReleaseZjTzBrandImplementPoint(@RequestBody(required = false) List<ZjTzBrandImplementPoint> zjTzBrandImplementPointList) {
        return zjTzBrandImplementPointService.batchReleaseZjTzBrandImplementPoint(zjTzBrandImplementPointList);
    }
    
    @ApiOperation(value="批量退回品牌建设-项目、专项实施方案要点", notes="批量退回品牌建设-项目、专项实施方案要点")
    @ApiImplicitParam(name = "zjTzBrandImplementPointList", value = "品牌建设-项目、专项实施方案要点List", dataType = "List<ZjTzBrandImplementPoint>")
    @RequireToken
    @PostMapping("/batchRecallZjTzBrandImplementPoint")
    public ResponseEntity batchRecallZjTzBrandImplementPoint(@RequestBody(required = false) List<ZjTzBrandImplementPoint> zjTzBrandImplementPointList) {
        return zjTzBrandImplementPointService.batchRecallZjTzBrandImplementPoint(zjTzBrandImplementPointList);
    }

    @ApiOperation(value="批量导出品牌建设-项目、专项实施方案要点附件", notes="批量导出品牌建设-项目、专项实施方案要点附件")
    @ApiImplicitParam(name = "zjTzBrandImplementPointList", value = "品牌建设-项目、专项实施方案要点List", dataType = "List<ZjTzBrandImplementPoint>")
    @RequireToken
    @PostMapping("/batchExportZjTzBrandImplementPointFile")
    public ResponseEntity batchExportZjTzBrandImplementPointFile(@RequestBody(required = false) List<ZjTzBrandImplementPoint> zjTzBrandImplementPointList) {
        return zjTzBrandImplementPointService.batchExportZjTzBrandImplementPointFile(zjTzBrandImplementPointList);
    }

}
