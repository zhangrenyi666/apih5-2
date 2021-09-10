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
import com.apih5.mybatis.pojo.ZxGcsgCommonAttachment;
import com.apih5.service.ZxGcsgCommonAttachmentService;

@RestController
public class ZxGcsgCommonAttachmentController {

    @Autowired(required = true)
    private ZxGcsgCommonAttachmentService zxGcsgCommonAttachmentService;

    @ApiOperation(value="查询工程施工类合同附件表(新)", notes="查询工程施工类合同附件表(新)")
    @ApiImplicitParam(name = "zxGcsgCommonAttachment", value = "工程施工类合同附件表(新)entity", dataType = "ZxGcsgCommonAttachment")
    @RequireToken
    @PostMapping("/getZxGcsgCommonAttachmentList")
    public ResponseEntity getZxGcsgCommonAttachmentList(@RequestBody(required = false) ZxGcsgCommonAttachment zxGcsgCommonAttachment) {
        return zxGcsgCommonAttachmentService.getZxGcsgCommonAttachmentListByCondition(zxGcsgCommonAttachment);
    }

    @ApiOperation(value="查询详情工程施工类合同附件表(新)", notes="查询详情工程施工类合同附件表(新)")
    @ApiImplicitParam(name = "zxGcsgCommonAttachment", value = "工程施工类合同附件表(新)entity", dataType = "ZxGcsgCommonAttachment")
    @RequireToken
    @PostMapping("/getZxGcsgCommonAttachmentDetail")
    public ResponseEntity getZxGcsgCommonAttachmentDetail(@RequestBody(required = false) ZxGcsgCommonAttachment zxGcsgCommonAttachment) {
        return zxGcsgCommonAttachmentService.getZxGcsgCommonAttachmentDetail(zxGcsgCommonAttachment);
    }

    @ApiOperation(value="新增工程施工类合同附件表(新)", notes="新增工程施工类合同附件表(新)")
    @ApiImplicitParam(name = "zxGcsgCommonAttachment", value = "工程施工类合同附件表(新)entity", dataType = "ZxGcsgCommonAttachment")
    @RequireToken
    @PostMapping("/addZxGcsgCommonAttachment")
    public ResponseEntity addZxGcsgCommonAttachment(@RequestBody(required = false) ZxGcsgCommonAttachment zxGcsgCommonAttachment) {
        return zxGcsgCommonAttachmentService.saveZxGcsgCommonAttachment(zxGcsgCommonAttachment);
    }

    @ApiOperation(value="更新工程施工类合同附件表(新)", notes="更新工程施工类合同附件表(新)")
    @ApiImplicitParam(name = "zxGcsgCommonAttachment", value = "工程施工类合同附件表(新)entity", dataType = "ZxGcsgCommonAttachment")
    @RequireToken
    @PostMapping("/updateZxGcsgCommonAttachment")
    public ResponseEntity updateZxGcsgCommonAttachment(@RequestBody(required = false) ZxGcsgCommonAttachment zxGcsgCommonAttachment) {
        return zxGcsgCommonAttachmentService.updateZxGcsgCommonAttachment(zxGcsgCommonAttachment);
    }

    @ApiOperation(value="删除工程施工类合同附件表(新)", notes="删除工程施工类合同附件表(新)")
    @ApiImplicitParam(name = "zxGcsgCommonAttachmentList", value = "工程施工类合同附件表(新)List", dataType = "List<ZxGcsgCommonAttachment>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxGcsgCommonAttachment")
    public ResponseEntity batchDeleteUpdateZxGcsgCommonAttachment(@RequestBody(required = false) List<ZxGcsgCommonAttachment> zxGcsgCommonAttachmentList) {
        return zxGcsgCommonAttachmentService.batchDeleteUpdateZxGcsgCommonAttachment(zxGcsgCommonAttachmentList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
