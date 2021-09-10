package com.apih5.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import com.apih5.framework.annotation.RequireToken;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxCtFsContractReviewDetail;
import com.apih5.service.ZxCtFsContractReviewDetailService;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class ZxCtFsContractReviewDetailController {

    @Autowired(required = true)
    private ZxCtFsContractReviewDetailService zxCtFsContractReviewDetailService;

    @ApiOperation(value="查询附属合同评审清单", notes="查询附属合同评审清单")
    @ApiImplicitParam(name = "zxCtFsContractReviewDetail", value = "附属合同评审清单entity", dataType = "ZxCtFsContractReviewDetail")
    @RequireToken
    @PostMapping("/getZxCtFsContractReviewDetailList")
    public ResponseEntity getZxCtFsContractReviewDetailList(@RequestBody(required = false) ZxCtFsContractReviewDetail zxCtFsContractReviewDetail) {
        return zxCtFsContractReviewDetailService.getZxCtFsContractReviewDetailListByCondition(zxCtFsContractReviewDetail);
    }

    @ApiOperation(value="查询详情附属合同评审清单", notes="查询详情附属合同评审清单")
    @ApiImplicitParam(name = "zxCtFsContractReviewDetail", value = "附属合同审核详情entity", dataType = "ZxCtFsContractReviewDetail")
    @RequireToken
    @PostMapping("/getZxCtFsContractReviewDetailDetail")
    public ResponseEntity getZxCtFsContractReviewDetailDetail(@RequestBody(required = false) ZxCtFsContractReviewDetail zxCtFsContractReviewDetail) {
        return zxCtFsContractReviewDetailService.getZxCtFsContractReviewDetailDetail(zxCtFsContractReviewDetail);
    }

    @ApiOperation(value="新增附属合同评审清单", notes="新增附属合同评审清单")
    @ApiImplicitParam(name = "zxCtFsContractReviewDetail", value = "附属合同评审清单entity", dataType = "ZxCtFsContractReviewDetail")
    @RequireToken
    @PostMapping("/addZxCtFsContractReviewDetail")
    public ResponseEntity addZxCtFsContractReviewDetail(@RequestBody(required = false) ZxCtFsContractReviewDetail zxCtFsContractReviewDetail) {
        return zxCtFsContractReviewDetailService.saveZxCtFsContractReviewDetail(zxCtFsContractReviewDetail);
    }

    @ApiOperation(value="更新附属合同评审清单", notes="更新附属合同评审清单")
    @ApiImplicitParam(name = "zxCtFsContractReviewDetail", value = "附属合同评审清单entity", dataType = "ZxCtFsContractReviewDetail")
    @RequireToken
    @PostMapping("/updateZxCtFsContractReviewDetail")
    public ResponseEntity updateZxCtFsContractReviewDetail(@RequestBody(required = false) ZxCtFsContractReviewDetail zxCtFsContractReviewDetail) {
        return zxCtFsContractReviewDetailService.updateZxCtFsContractReviewDetail(zxCtFsContractReviewDetail);
    }

    @ApiOperation(value="删除附属合同评审清单", notes="删除附属合同评审清单")
    @ApiImplicitParam(name = "zxCtFsContractReviewDetailList", value = "附属合同评审清单List", dataType = "List<ZxCtFsContractReviewDetail>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxCtFsContractReviewDetail")
    public ResponseEntity batchDeleteUpdateZxCtFsContractReviewDetail(@RequestBody(required = false) List<ZxCtFsContractReviewDetail> zxCtFsContractReviewDetailList) {
        return zxCtFsContractReviewDetailService.batchDeleteUpdateZxCtFsContractReviewDetail(zxCtFsContractReviewDetailList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    /**
     * @author suncg
     * @param zxCtFsContractReview
     * 导入execl数据
     *
     * */
    @ApiOperation(value="Excel导入附属合同评审清单", notes="Excel导入附属合同评审清单")
    @ApiImplicitParam(name = "zxCtFsContractReviewDetail", value = "附属合同评审清单数据Excel", dataType = "ZxCtFsContractReviewDetail")
    @RequireToken
    @PostMapping("/importExeclZxCtFsContractReviewDetails")
    public ResponseEntity importExeclZxCtFsContractReviewDetails( @RequestBody(required = false) ZxCtFsContractReviewDetail zxCtFsContractReview){
        return zxCtFsContractReviewDetailService.importExcel(zxCtFsContractReview);
    }

    /**
     * @author suncg
     * @param zxCtFsContractReview
     * 导入execl数据
     *
     * */
    @ApiOperation(value="查询附属合同清单", notes="查询附属合同清单")
    @ApiImplicitParam(name = "zxCtFsContractReviewDetail", value = "附属合同评审清单数据", dataType = "ZxCtFsContractReviewDetail")
    @RequireToken
    @PostMapping("/getAllZxCtFsReviewDetailList")
    public ResponseEntity getAllZxCtFsReviewDetailList(@RequestBody(required = false) ZxCtFsContractReviewDetail zxCtFsContractReview){
      return  zxCtFsContractReviewDetailService.getZxCtFsContractReviewDetailList(zxCtFsContractReview);
    }

    @ApiOperation(value="查询附属合同清单", notes="查询附属合同清单")
    @ApiImplicitParam(name = "zxCtFsContractReviewDetail", value = "附属合同评审清单数据", dataType = "ZxCtFsContractReviewDetail")
    @RequireToken
    @PostMapping("/getAllReviewDetailListByContract")
    public ResponseEntity getAllReviewDetailListByContract(@RequestBody(required = false) ZxCtFsContractReviewDetail zxCtFsContractReview){
        return  zxCtFsContractReviewDetailService.getAllReviewDetailListByContract(zxCtFsContractReview);
    }

    @ApiOperation(value="查询附属合同评审清单", notes="查询附属合同评审清单")
    @ApiImplicitParam(name = "zxCtFsContractReviewDetail", value = "附属合同评审清单entity", dataType = "ZxCtFsContractReviewDetail")
    @RequireToken
    @PostMapping("/getContractReviewDetailList")
    public ResponseEntity getContractReviewDetailList(@RequestBody(required = false) ZxCtFsContractReviewDetail zxCtFsContractReviewDetail) {
        return zxCtFsContractReviewDetailService.getReviewDetailList(zxCtFsContractReviewDetail);
    }
}
