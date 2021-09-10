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
import com.apih5.mybatis.pojo.ZjTzSupContent;
import com.apih5.service.ZjTzSupContentService;

@RestController
public class ZjTzSupContentController {

    @Autowired(required = true)
    private ZjTzSupContentService zjTzSupContentService;

    @ApiOperation(value="查询督察要点通报内容", notes="查询督察要点通报内容")
    @ApiImplicitParam(name = "zjTzSupContent", value = "督察要点通报内容entity", dataType = "ZjTzSupContent")
    @RequireToken
    @PostMapping("/getZjTzSupContentList")
    public ResponseEntity getZjTzSupContentList(@RequestBody(required = false) ZjTzSupContent zjTzSupContent) {
        return zjTzSupContentService.getZjTzSupContentListByCondition(zjTzSupContent);
    }

    @ApiOperation(value="查询详情督察要点通报内容", notes="查询详情督察要点通报内容")
    @ApiImplicitParam(name = "zjTzSupContent", value = "督察要点通报内容entity", dataType = "ZjTzSupContent")
    @RequireToken
    @PostMapping("/getZjTzSupContentDetails")
    public ResponseEntity getZjTzSupContentDetails(@RequestBody(required = false) ZjTzSupContent zjTzSupContent) {
        return zjTzSupContentService.getZjTzSupContentDetails(zjTzSupContent);
    }

    @ApiOperation(value="新增督察要点通报内容", notes="新增督察要点通报内容")
    @ApiImplicitParam(name = "zjTzSupContent", value = "督察要点通报内容entity", dataType = "ZjTzSupContent")
    @RequireToken
    @PostMapping("/addZjTzSupContent")
    public ResponseEntity addZjTzSupContent(@RequestBody(required = false) ZjTzSupContent zjTzSupContent) {
        return zjTzSupContentService.saveZjTzSupContent(zjTzSupContent);
    }

    @ApiOperation(value="回复督察要点通报内容", notes="回复督察要点通报内容")
    @ApiImplicitParam(name = "zjTzSupContent", value = "督察要点通报内容entity", dataType = "ZjTzSupContent")
    @RequireToken
    @PostMapping("/updateZjTzSupContent")
    public ResponseEntity updateZjTzSupContent(@RequestBody(required = false) ZjTzSupContent zjTzSupContent) {
        return zjTzSupContentService.updateZjTzSupContent(zjTzSupContent);
    }

    @ApiOperation(value="删除督察要点通报内容", notes="删除督察要点通报内容")
    @ApiImplicitParam(name = "zjTzSupContentList", value = "督察要点通报内容List", dataType = "List<ZjTzSupContent>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZjTzSupContent")
    public ResponseEntity batchDeleteUpdateZjTzSupContent(@RequestBody(required = false) List<ZjTzSupContent> zjTzSupContentList) {
        return zjTzSupContentService.batchDeleteUpdateZjTzSupContent(zjTzSupContentList);
    }
    
}
