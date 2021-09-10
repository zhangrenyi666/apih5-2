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
import com.apih5.mybatis.pojo.ZxCtFsContractReview;
import com.apih5.service.ZxCtFsContractReviewService;

import javax.servlet.http.HttpServletResponse;

@RestController
public class ZxCtFsContractReviewController {

    @Autowired(required = true)
    private ZxCtFsContractReviewService zxCtFsContractReviewService;

    @ApiOperation(value="查询附属合同评审", notes="查询附属合同评审")
    @ApiImplicitParam(name = "zxCtFsContractReview", value = "附属合同评审entity", dataType = "ZxCtFsContractReview")
    @RequireToken
    @PostMapping("/getZxCtFsContractReviewList")
    public ResponseEntity getZxCtFsContractReviewList(@RequestBody(required = false) ZxCtFsContractReview zxCtFsContractReview) {
        return zxCtFsContractReviewService.getZxCtFsContractReviewListByCondition(zxCtFsContractReview);
    }

    @ApiOperation(value="查询详情附属合同评审", notes="查询详情附属合同评审")
    @ApiImplicitParam(name = "zxCtFsContractReview", value = "附属合同评审entity", dataType = "ZxCtFsContractReview")
    @RequireToken
    @PostMapping("/getZxCtFsContractReviewDetail")
    public ResponseEntity getZxCtFsContractReviewDetail(@RequestBody(required = false) ZxCtFsContractReview zxCtFsContractReview) {
        return zxCtFsContractReviewService.getZxCtFsContractReviewDetail(zxCtFsContractReview);
    }

    @ApiOperation(value="新增附属合同评审", notes="新增附属合同评审")
    @ApiImplicitParam(name = "zxCtFsContractReview", value = "附属合同评审entity", dataType = "ZxCtFsContractReview")
    @RequireToken
    @PostMapping("/addZxCtFsContractReview")
    public ResponseEntity addZxCtFsContractReview(@RequestBody(required = false) ZxCtFsContractReview zxCtFsContractReview) {
        return zxCtFsContractReviewService.saveZxCtFsContractReview(zxCtFsContractReview);
    }

    @ApiOperation(value="更新附属合同评审", notes="更新附属合同评审")
    @ApiImplicitParam(name = "zxCtFsContractReview", value = "附属合同评审entity", dataType = "ZxCtFsContractReview")
    @RequireToken
    @PostMapping("/updateZxCtFsContractReview")
    public ResponseEntity updateZxCtFsContractReview(@RequestBody(required = false) ZxCtFsContractReview zxCtFsContractReview) {
        return zxCtFsContractReviewService.updateZxCtFsContractReview(zxCtFsContractReview);
    }

    @ApiOperation(value="删除附属合同评审", notes="删除附属合同评审")
    @ApiImplicitParam(name = "zxCtFsContractReviewList", value = "附属合同评审List", dataType = "List<ZxCtFsContractReview>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxCtFsContractReview")
    public ResponseEntity batchDeleteUpdateZxCtFsContractReview(@RequestBody(required = false) List<ZxCtFsContractReview> zxCtFsContractReviewList) {
        return zxCtFsContractReviewService.batchDeleteUpdateZxCtFsContractReview(zxCtFsContractReviewList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

    @ApiOperation(value="导出附属合同评审列表", notes="导出附属合同评审列表")
    @ApiImplicitParam(name = "zxCtFsContractReview", value = "附属合同评审entity", dataType = "ZxCtFsContractReview")
    @RequireToken
    @PostMapping("/exportZxCtFsContractReview")
    public ResponseEntity exportZxCtFsContractReview(@RequestBody(required = false) ZxCtFsContractReview zxCtFsContractReview,HttpServletResponse response) {
        return zxCtFsContractReviewService.exportContractReview(zxCtFsContractReview, response );
    }

    @ApiOperation(value="查询附属合同编号", notes="查询附属合同编号")
    @ApiImplicitParam(name = "zxCtFsContractReview", value = "附属合同评审entity", dataType = "ZxCtFsContractReview")
    @RequireToken
    @PostMapping("/getZxCtFsContractNo")
    public ResponseEntity getZxCtFsContractNo(@RequestBody(required = false) ZxCtFsContractReview zxCtFsContractReview) {
        return zxCtFsContractReviewService.getZxCtFsContractNo(zxCtFsContractReview);
    }

}
