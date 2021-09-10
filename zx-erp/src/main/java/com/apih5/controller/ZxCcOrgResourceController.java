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
import com.apih5.mybatis.pojo.ZxCcOrgResource;
import com.apih5.service.ZxCcOrgResourceService;

@RestController
public class ZxCcOrgResourceController {

    @Autowired(required = true)
    private ZxCcOrgResourceService zxCcOrgResourceService;

    @ApiOperation(value="查询资质标准明细", notes="查询资质标准明细")
    @ApiImplicitParam(name = "zxCcOrgResource", value = "资质标准明细entity", dataType = "ZxCcOrgResource")
    @RequireToken
    @PostMapping("/getZxCcOrgResourceList")
    public ResponseEntity getZxCcOrgResourceList(@RequestBody(required = false) ZxCcOrgResource zxCcOrgResource) {
        return zxCcOrgResourceService.getZxCcOrgResourceListByCondition(zxCcOrgResource);
    }

    @ApiOperation(value="查询详情资质标准明细", notes="查询详情资质标准明细")
    @ApiImplicitParam(name = "zxCcOrgResource", value = "资质标准明细entity", dataType = "ZxCcOrgResource")
    @RequireToken
    @PostMapping("/getZxCcOrgResourceDetail")
    public ResponseEntity getZxCcOrgResourceDetail(@RequestBody(required = false) ZxCcOrgResource zxCcOrgResource) {
        return zxCcOrgResourceService.getZxCcOrgResourceDetail(zxCcOrgResource);
    }

    @ApiOperation(value="新增资质标准明细", notes="新增资质标准明细")
    @ApiImplicitParam(name = "zxCcOrgResource", value = "资质标准明细entity", dataType = "ZxCcOrgResource")
    @RequireToken
    @PostMapping("/addZxCcOrgResource")
    public ResponseEntity addZxCcOrgResource(@RequestBody(required = false) ZxCcOrgResource zxCcOrgResource) {
        return zxCcOrgResourceService.saveZxCcOrgResource(zxCcOrgResource);
    }

    @ApiOperation(value="更新资质标准明细", notes="更新资质标准明细")
    @ApiImplicitParam(name = "zxCcOrgResource", value = "资质标准明细entity", dataType = "ZxCcOrgResource")
    @RequireToken
    @PostMapping("/updateZxCcOrgResource")
    public ResponseEntity updateZxCcOrgResource(@RequestBody(required = false) ZxCcOrgResource zxCcOrgResource) {
        return zxCcOrgResourceService.updateZxCcOrgResource(zxCcOrgResource);
    }

    @ApiOperation(value="删除资质标准明细", notes="删除资质标准明细")
    @ApiImplicitParam(name = "zxCcOrgResourceList", value = "资质标准明细List", dataType = "List<ZxCcOrgResource>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxCcOrgResource")
    public ResponseEntity batchDeleteUpdateZxCcOrgResource(@RequestBody(required = false) List<ZxCcOrgResource> zxCcOrgResourceList) {
        return zxCcOrgResourceService.batchDeleteUpdateZxCcOrgResource(zxCcOrgResourceList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
