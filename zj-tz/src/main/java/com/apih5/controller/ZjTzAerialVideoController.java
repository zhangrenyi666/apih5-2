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
import com.apih5.mybatis.pojo.ZjTzAerialVideo;
import com.apih5.service.ZjTzAerialVideoService;

@RestController
public class ZjTzAerialVideoController {

    @Autowired(required = true)
    private ZjTzAerialVideoService zjTzAerialVideoService;

    @ApiOperation(value="查询航拍视频", notes="查询航拍视频")
    @ApiImplicitParam(name = "zjTzAerialVideo", value = "航拍视频entity", dataType = "ZjTzAerialVideo")
    @RequireToken
    @PostMapping("/getZjTzAerialVideoList")
    public ResponseEntity getZjTzAerialVideoList(@RequestBody(required = false) ZjTzAerialVideo zjTzAerialVideo) {
        return zjTzAerialVideoService.getZjTzAerialVideoListByCondition(zjTzAerialVideo);
    }

    @ApiOperation(value="查询详情航拍视频", notes="查询详情航拍视频")
    @ApiImplicitParam(name = "zjTzAerialVideo", value = "航拍视频entity", dataType = "ZjTzAerialVideo")
    @RequireToken
    @PostMapping("/getZjTzAerialVideoDetails")
    public ResponseEntity getZjTzAerialVideoDetails(@RequestBody(required = false) ZjTzAerialVideo zjTzAerialVideo) {
        return zjTzAerialVideoService.getZjTzAerialVideoDetails(zjTzAerialVideo);
    }

    @ApiOperation(value="新增航拍视频", notes="新增航拍视频")
    @ApiImplicitParam(name = "zjTzAerialVideo", value = "航拍视频entity", dataType = "ZjTzAerialVideo")
    @RequireToken
    @PostMapping("/addZjTzAerialVideo")
    public ResponseEntity addZjTzAerialVideo(@RequestBody(required = false) ZjTzAerialVideo zjTzAerialVideo) {
        return zjTzAerialVideoService.saveZjTzAerialVideo(zjTzAerialVideo);
    }

    @ApiOperation(value="更新航拍视频", notes="更新航拍视频")
    @ApiImplicitParam(name = "zjTzAerialVideo", value = "航拍视频entity", dataType = "ZjTzAerialVideo")
    @RequireToken
    @PostMapping("/updateZjTzAerialVideo")
    public ResponseEntity updateZjTzAerialVideo(@RequestBody(required = false) ZjTzAerialVideo zjTzAerialVideo) {
        return zjTzAerialVideoService.updateZjTzAerialVideo(zjTzAerialVideo);
    }

    @ApiOperation(value="删除航拍视频", notes="删除航拍视频")
    @ApiImplicitParam(name = "zjTzAerialVideoList", value = "航拍视频List", dataType = "List<ZjTzAerialVideo>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZjTzAerialVideo")
    public ResponseEntity batchDeleteUpdateZjTzAerialVideo(@RequestBody(required = false) List<ZjTzAerialVideo> zjTzAerialVideoList) {
        return zjTzAerialVideoService.batchDeleteUpdateZjTzAerialVideo(zjTzAerialVideoList);
    }

}
