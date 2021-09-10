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
import com.apih5.mybatis.pojo.ZjTzVideo;
import com.apih5.service.ZjTzVideoService;

@RestController
public class ZjTzVideoController {

    @Autowired(required = true)
    private ZjTzVideoService zjTzVideoService;

    @ApiOperation(value="查询宣贯视频", notes="查询宣贯视频")
    @ApiImplicitParam(name = "zjTzVideo", value = "宣贯视频entity", dataType = "ZjTzVideo")
    @RequireToken
    @PostMapping("/getZjTzVideoList")
    public ResponseEntity getZjTzVideoList(@RequestBody(required = false) ZjTzVideo zjTzVideo) {
        return zjTzVideoService.getZjTzVideoListByCondition(zjTzVideo);
    }

    @ApiOperation(value="查询详情宣贯视频", notes="查询详情宣贯视频")
    @ApiImplicitParam(name = "zjTzVideo", value = "宣贯视频entity", dataType = "ZjTzVideo")
    @RequireToken
    @PostMapping("/getZjTzVideoDetails")
    public ResponseEntity getZjTzVideoDetails(@RequestBody(required = false) ZjTzVideo zjTzVideo) {
        return zjTzVideoService.getZjTzVideoDetails(zjTzVideo);
    }

    @ApiOperation(value="新增宣贯视频", notes="新增宣贯视频")
    @ApiImplicitParam(name = "zjTzVideo", value = "宣贯视频entity", dataType = "ZjTzVideo")
    @RequireToken
    @PostMapping("/addZjTzVideo")
    public ResponseEntity addZjTzVideo(@RequestBody(required = false) ZjTzVideo zjTzVideo) {
        return zjTzVideoService.saveZjTzVideo(zjTzVideo);
    }

    @ApiOperation(value="更新宣贯视频", notes="更新宣贯视频")
    @ApiImplicitParam(name = "zjTzVideo", value = "宣贯视频entity", dataType = "ZjTzVideo")
    @RequireToken
    @PostMapping("/updateZjTzVideo")
    public ResponseEntity updateZjTzVideo(@RequestBody(required = false) ZjTzVideo zjTzVideo) {
        return zjTzVideoService.updateZjTzVideo(zjTzVideo);
    }

    @ApiOperation(value="删除宣贯视频", notes="删除宣贯视频")
    @ApiImplicitParam(name = "zjTzVideoList", value = "宣贯视频List", dataType = "List<ZjTzVideo>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZjTzVideo")
    public ResponseEntity batchDeleteUpdateZjTzVideo(@RequestBody(required = false) List<ZjTzVideo> zjTzVideoList) {
        return zjTzVideoService.batchDeleteUpdateZjTzVideo(zjTzVideoList);
    }
    
    @ApiOperation(value="广而告之宣贯视频", notes="广而告之宣贯视频")
    @ApiImplicitParam(name = "zjTzVideoList", value = "宣贯视频List", dataType = "List<ZjTzVideo>")
    @RequireToken
    @PostMapping("/toHomeShowZjTzVideo")
    public ResponseEntity toHomeShowZjTzVideo(@RequestBody(required = false) ZjTzVideo zjTzVideo) {
        return zjTzVideoService.toHomeShowZjTzVideo(zjTzVideo);
    }
    
    @ApiOperation(value="批量审核同意宣贯视频", notes="批量审核同意宣贯视频")
    @ApiImplicitParam(name = "zjTzVideoList", value = "宣贯视频List", dataType = "List<ZjTzVideo>")
    @RequireToken
    @PostMapping("/batchApproveAgreeZjTzVideo")
    public ResponseEntity batchApproveAgreeZjTzVideo(@RequestBody(required = false) List<ZjTzVideo> zjTzVideoList) {
        return zjTzVideoService.batchApproveAgreeZjTzVideo(zjTzVideoList);
    }
    
    @ApiOperation(value="批量审核驳回宣贯视频", notes="批量审核驳回宣贯视频")
    @ApiImplicitParam(name = "zjTzVideoList", value = "宣贯视频List", dataType = "List<ZjTzVideo>")
    @RequireToken
    @PostMapping("/batchApproveRejectZjTzVideo")
    public ResponseEntity batchApproveRejectZjTzVideo(@RequestBody(required = false) List<ZjTzVideo> zjTzVideoList) {
        return zjTzVideoService.batchApproveRejectZjTzVideo(zjTzVideoList);
    }
    
    @ApiOperation(value="批量发布宣贯视频", notes="批量发布宣贯视频")
    @ApiImplicitParam(name = "zjTzVideoList", value = "宣贯视频List", dataType = "List<ZjTzVideo>")
    @RequireToken
    @PostMapping("/batchDeleteReleaseZjTzVideo")
    public ResponseEntity batchDeleteReleaseZjTzVideo(@RequestBody(required = false) List<ZjTzVideo> zjTzVideoList) {
        return zjTzVideoService.batchDeleteReleaseZjTzVideo(zjTzVideoList);
    }
    
    @ApiOperation(value="批量撤回宣贯视频", notes="批量撤回宣贯视频")
    @ApiImplicitParam(name = "zjTzVideoList", value = "宣贯视频List", dataType = "List<ZjTzVideo>")
    @RequireToken
    @PostMapping("/batchDeleteRecallZjTzVideo")
    public ResponseEntity batchDeleteRecallZjTzVideo(@RequestBody(required = false) List<ZjTzVideo> zjTzVideoList) {
        return zjTzVideoService.batchDeleteRecallZjTzVideo(zjTzVideoList);
    }
    
    @ApiOperation(value="首页广而告之宣贯视频", notes="首页广而告之宣贯视频")
    @ApiImplicitParam(name = "zjTzVideo", value = "宣贯视频entity", dataType = "ZjTzVideo")
    @RequireToken
    @PostMapping("/getZjTzVideoHome")
    public ResponseEntity getZjTzVideoHome(@RequestBody(required = false) ZjTzVideo zjTzVideo) {
        return zjTzVideoService.getZjTzVideoHome(zjTzVideo);
    }
}
