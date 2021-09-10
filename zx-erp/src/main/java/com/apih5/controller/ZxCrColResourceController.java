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
import com.apih5.mybatis.pojo.ZxCrColResource;
import com.apih5.service.ZxCrColResourceService;

@RestController
public class ZxCrColResourceController {

    @Autowired(required = true)
    private ZxCrColResourceService zxCrColResourceService;

    @ApiOperation(value="查询专业化分类资源", notes="查询专业化分类资源")
    @ApiImplicitParam(name = "zxCrColResource", value = "专业化分类资源entity", dataType = "ZxCrColResource")
    @RequireToken
    @PostMapping("/getZxCrColResourceList")
    public ResponseEntity getZxCrColResourceList(@RequestBody(required = false) ZxCrColResource zxCrColResource) {
        return zxCrColResourceService.getZxCrColResourceListByCondition(zxCrColResource);
    }

    @ApiOperation(value="查询详情专业化分类资源", notes="查询详情专业化分类资源")
    @ApiImplicitParam(name = "zxCrColResource", value = "专业化分类资源entity", dataType = "ZxCrColResource")
    @RequireToken
    @PostMapping("/getZxCrColResourceDetail")
    public ResponseEntity getZxCrColResourceDetail(@RequestBody(required = false) ZxCrColResource zxCrColResource) {
        return zxCrColResourceService.getZxCrColResourceDetail(zxCrColResource);
    }

    @ApiOperation(value="新增专业化分类资源", notes="新增专业化分类资源")
    @ApiImplicitParam(name = "zxCrColResource", value = "专业化分类资源entity", dataType = "ZxCrColResource")
    @RequireToken
    @PostMapping("/addZxCrColResource")
    public ResponseEntity addZxCrColResource(@RequestBody(required = false) ZxCrColResource zxCrColResource) {
        return zxCrColResourceService.saveZxCrColResource(zxCrColResource);
    }

    @ApiOperation(value="更新专业化分类资源", notes="更新专业化分类资源")
    @ApiImplicitParam(name = "zxCrColResource", value = "专业化分类资源entity", dataType = "ZxCrColResource")
    @RequireToken
    @PostMapping("/updateZxCrColResource")
    public ResponseEntity updateZxCrColResource(@RequestBody(required = false) ZxCrColResource zxCrColResource) {
        return zxCrColResourceService.updateZxCrColResource(zxCrColResource);
    }

    @ApiOperation(value="删除专业化分类资源", notes="删除专业化分类资源")
    @ApiImplicitParam(name = "zxCrColResourceList", value = "专业化分类资源List", dataType = "List<ZxCrColResource>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxCrColResource")
    public ResponseEntity batchDeleteUpdateZxCrColResource(@RequestBody(required = false) List<ZxCrColResource> zxCrColResourceList) {
        return zxCrColResourceService.batchDeleteUpdateZxCrColResource(zxCrColResourceList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
