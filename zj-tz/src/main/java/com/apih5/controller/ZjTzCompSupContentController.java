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
import com.apih5.mybatis.pojo.ZjTzCompSupContent;
import com.apih5.service.ZjTzCompSupContentService;

@RestController
public class ZjTzCompSupContentController {

    @Autowired(required = true)
    private ZjTzCompSupContentService zjTzCompSupContentService;

    @ApiOperation(value="查询综合督察整改内容回复", notes="查询综合督察整改内容回复")
    @ApiImplicitParam(name = "zjTzCompSupContent", value = "综合督察整改内容回复entity", dataType = "ZjTzCompSupContent")
    @RequireToken
    @PostMapping("/getZjTzCompSupContentList")
    public ResponseEntity getZjTzCompSupContentList(@RequestBody(required = false) ZjTzCompSupContent zjTzCompSupContent) {
        return zjTzCompSupContentService.getZjTzCompSupContentListByCondition(zjTzCompSupContent);
    }

    @ApiOperation(value="查询详情综合督察整改内容回复", notes="查询详情综合督察整改内容回复")
    @ApiImplicitParam(name = "zjTzCompSupContent", value = "综合督察整改内容回复entity", dataType = "ZjTzCompSupContent")
    @RequireToken
    @PostMapping("/getZjTzCompSupContentDetails")
    public ResponseEntity getZjTzCompSupContentDetails(@RequestBody(required = false) ZjTzCompSupContent zjTzCompSupContent) {
        return zjTzCompSupContentService.getZjTzCompSupContentDetails(zjTzCompSupContent);
    }

    @ApiOperation(value="新增综合督察整改内容回复", notes="新增综合督察整改内容回复")
    @ApiImplicitParam(name = "zjTzCompSupContent", value = "综合督察整改内容回复entity", dataType = "ZjTzCompSupContent")
    @RequireToken
    @PostMapping("/addZjTzCompSupContent")
    public ResponseEntity addZjTzCompSupContent(@RequestBody(required = false) ZjTzCompSupContent zjTzCompSupContent) {
        return zjTzCompSupContentService.saveZjTzCompSupContent(zjTzCompSupContent);
    }

    @ApiOperation(value="回复综合督察整改内容回复", notes="回复综合督察整改内容回复")
    @ApiImplicitParam(name = "zjTzCompSupContent", value = "综合督察整改内容回复entity", dataType = "ZjTzCompSupContent")
    @RequireToken
    @PostMapping("/updateZjTzCompSupContent")
    public ResponseEntity updateZjTzCompSupContent(@RequestBody(required = false) ZjTzCompSupContent zjTzCompSupContent) {
        return zjTzCompSupContentService.updateZjTzCompSupContent(zjTzCompSupContent);
    }

    @ApiOperation(value="删除综合督察整改内容回复", notes="删除综合督察整改内容回复")
    @ApiImplicitParam(name = "zjTzCompSupContentList", value = "综合督察整改内容回复List", dataType = "List<ZjTzCompSupContent>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZjTzCompSupContent")
    public ResponseEntity batchDeleteUpdateZjTzCompSupContent(@RequestBody(required = false) List<ZjTzCompSupContent> zjTzCompSupContentList) {
        return zjTzCompSupContentService.batchDeleteUpdateZjTzCompSupContent(zjTzCompSupContentList);
    }

}
