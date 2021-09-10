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
import com.apih5.mybatis.pojo.ZjLzehVideo;
import com.apih5.service.ZjLzehVideoService;

@RestController
public class ZjLzehVideoController {

    @Autowired(required = true)
    private ZjLzehVideoService zjLzehVideoService;

    @ApiOperation(value="查询宣传片维护", notes="查询宣传片维护")
    @ApiImplicitParam(name = "zjLzehVideo", value = "宣传片维护entity", dataType = "ZjLzehVideo")
    @RequireToken
    @PostMapping("/getZjLzehVideoList")
    public ResponseEntity getZjLzehVideoList(@RequestBody(required = false) ZjLzehVideo zjLzehVideo) {
        return zjLzehVideoService.getZjLzehVideoListByCondition(zjLzehVideo);
    }

    @ApiOperation(value="查询详情宣传片维护", notes="查询详情宣传片维护")
    @ApiImplicitParam(name = "zjLzehVideo", value = "宣传片维护entity", dataType = "ZjLzehVideo")
    @RequireToken
    @PostMapping("/getZjLzehVideoDetails")
    public ResponseEntity getZjLzehVideoDetails(@RequestBody(required = false) ZjLzehVideo zjLzehVideo) {
        return zjLzehVideoService.getZjLzehVideoDetails(zjLzehVideo);
    }

    @ApiOperation(value="新增宣传片维护", notes="新增宣传片维护")
    @ApiImplicitParam(name = "zjLzehVideo", value = "宣传片维护entity", dataType = "ZjLzehVideo")
    @RequireToken
    @PostMapping("/addZjLzehVideo")
    public ResponseEntity addZjLzehVideo(@RequestBody(required = false) ZjLzehVideo zjLzehVideo) {
        return zjLzehVideoService.saveZjLzehVideo(zjLzehVideo);
    }

    @ApiOperation(value="更新宣传片维护", notes="更新宣传片维护")
    @ApiImplicitParam(name = "zjLzehVideo", value = "宣传片维护entity", dataType = "ZjLzehVideo")
    @RequireToken
    @PostMapping("/updateZjLzehVideo")
    public ResponseEntity updateZjLzehVideo(@RequestBody(required = false) ZjLzehVideo zjLzehVideo) {
        return zjLzehVideoService.updateZjLzehVideo(zjLzehVideo);
    }

    @ApiOperation(value="删除宣传片维护", notes="删除宣传片维护")
    @ApiImplicitParam(name = "zjLzehVideoList", value = "宣传片维护List", dataType = "List<ZjLzehVideo>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZjLzehVideo")
    public ResponseEntity batchDeleteUpdateZjLzehVideo(@RequestBody(required = false) List<ZjLzehVideo> zjLzehVideoList) {
        return zjLzehVideoService.batchDeleteUpdateZjLzehVideo(zjLzehVideoList);
    }

    @ApiOperation(value="查询宣传片维护大屏展示", notes="查询宣传片维护大屏展示")
    @ApiImplicitParam(name = "zjLzehVideo", value = "宣传片维护entity", dataType = "ZjLzehVideo")
    @RequireToken
    @PostMapping("/getZjLzehVideo")
    public ResponseEntity getZjLzehVideo(@RequestBody(required = false) ZjLzehVideo zjLzehVideo) {
        return zjLzehVideoService.getZjLzehVideo(zjLzehVideo);
    }

    @ApiOperation(value="修改宣传片维护大屏展示", notes="修改宣传片维护大屏展示")
    @ApiImplicitParam(name = "zjLzehVideo", value = "宣传片维护entity", dataType = "ZjLzehVideo")
    @RequireToken
    @PostMapping("/updateZjLzehVideoValue")
    public ResponseEntity updateZjLzehVideoValue(@RequestBody(required = false) ZjLzehVideo zjLzehVideo) {
        return zjLzehVideoService.updateZjLzehVideoValue(zjLzehVideo);
    }




}
