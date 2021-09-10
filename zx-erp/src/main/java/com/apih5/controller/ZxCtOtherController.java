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
import com.apih5.mybatis.pojo.ZxCtOther;
import com.apih5.service.ZxCtOtherService;

import javax.servlet.http.HttpServletResponse;

@RestController
public class ZxCtOtherController {

    @Autowired(required = true)
    private ZxCtOtherService zxCtOtherService;

    @ApiOperation(value="查询其他合同评审", notes="查询其他合同评审")
    @ApiImplicitParam(name = "zxCtOther", value = "其他合同评审entity", dataType = "ZxCtOther")
    @RequireToken
    @PostMapping("/getZxCtOtherList")
    public ResponseEntity getZxCtOtherList(@RequestBody(required = false) ZxCtOther zxCtOther) {
        return zxCtOtherService.getZxCtOtherListByCondition(zxCtOther);
    }

    @ApiOperation(value="查询详情其他合同评审", notes="查询详情其他合同评审")
    @ApiImplicitParam(name = "zxCtOther", value = "其他合同评审entity", dataType = "ZxCtOther")
    @RequireToken
    @PostMapping("/getZxCtOtherDetail")
    public ResponseEntity getZxCtOtherDetail(@RequestBody(required = false) ZxCtOther zxCtOther) {
        return zxCtOtherService.getZxCtOtherDetail(zxCtOther);
    }

    @ApiOperation(value="新增其他合同评审", notes="新增其他合同评审")
    @ApiImplicitParam(name = "zxCtOther", value = "其他合同评审entity", dataType = "ZxCtOther")
    @RequireToken
    @PostMapping("/addZxCtOther")
    public ResponseEntity addZxCtOther(@RequestBody(required = false) ZxCtOther zxCtOther) {
        return zxCtOtherService.saveZxCtOther(zxCtOther);
    }

    @ApiOperation(value="更新其他合同评审", notes="更新其他合同评审")
    @ApiImplicitParam(name = "zxCtOther", value = "其他合同评审entity", dataType = "ZxCtOther")
    @RequireToken
    @PostMapping("/updateZxCtOther")
    public ResponseEntity updateZxCtOther(@RequestBody(required = false) ZxCtOther zxCtOther) {
        return zxCtOtherService.updateZxCtOther(zxCtOther);
    }

    @ApiOperation(value="删除其他合同评审", notes="删除其他合同评审")
    @ApiImplicitParam(name = "zxCtOtherList", value = "其他合同评审List", dataType = "List<ZxCtOther>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxCtOther")
    public ResponseEntity batchDeleteUpdateZxCtOther(@RequestBody(required = false) List<ZxCtOther> zxCtOtherList) {
        return zxCtOtherService.batchDeleteUpdateZxCtOther(zxCtOtherList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

    @ApiOperation(value="其他合同评审申请", notes="其他合同评审申请")
    @ApiImplicitParam(name = "zxCtOther", value = "其他合同评审entity", dataType = "ZxCtOther")
    @RequireToken
    @PostMapping("/zxCtOtherReviewApply")
    public ResponseEntity zxCtOtherReviewApply(@RequestBody(required = false) ZxCtOther zxCtOther) {
        return zxCtOtherService.zxCtOtherReviewApply(zxCtOther);
    }

    @ApiOperation(value="其他合同评审申请之前校验", notes="其他合同评审申请之前校验")
    @ApiImplicitParam(name = "zxCtOther", value = "其他合同评审entity", dataType = "ZxCtOther")
    @RequireToken
    @PostMapping("/zxCtOtherReviewApplyCheck")
    public ResponseEntity zxCtOtherReviewApplyCheck(@RequestBody(required = false) ZxCtOther zxCtOther) {
        return zxCtOtherService.zxCtOtherReviewApplyCheck(zxCtOther);
    }

    @ApiOperation(value="根据选择甲方名称与合同类别后自动生成其他合同评审合同编号", notes="新增其他合同评审")
    @ApiImplicitParam(name = "zxCtOther", value = "其他合同评审entity", dataType = "ZxCtOther")
    @RequireToken
    @PostMapping("/getZxCtOtherContractNo")
    public ResponseEntity getZxCtOtherContractNo(@RequestBody(required = false) ZxCtOther zxCtOther) {
        return zxCtOtherService.getZxCtOtherContractNo(zxCtOther);
    }

    @ApiOperation(value="其他合同评审导出", notes="其他合同评审导出")
    @ApiImplicitParam(name = "zxCtOther", value = "其他合同评审entity", dataType = "ZxCtOther")
    @RequireToken
    @PostMapping("/exportZxCtOther")
    public void exportZxCtOther(@RequestBody(required = false) ZxCtOther zxCtOther, HttpServletResponse response) {
        zxCtOtherService.exportZxCtOther(zxCtOther,response);
    }

    @ApiOperation(value="其他合同评审附件下载", notes="其他合同评审附件下载")
    @ApiImplicitParam(name = "zxCtOther", value = "其他合同评审entity", dataType = "ZxCtOther")
    @PostMapping("/downLoadZxCtOtherFile")
    public void downLoadZxCtOtherFile(@RequestBody(required = false) ZxCtOther zxCtOther, HttpServletResponse response){
        zxCtOtherService.downLoadZxCtOtherFile(zxCtOther,response);
    }
}
