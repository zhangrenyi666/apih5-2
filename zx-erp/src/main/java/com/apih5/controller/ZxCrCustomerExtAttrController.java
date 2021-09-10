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
import com.apih5.mybatis.pojo.ZxCrCustomerExtAttr;
import com.apih5.service.ZxCrCustomerExtAttrService;

@RestController
public class ZxCrCustomerExtAttrController {

    @Autowired(required = true)
    private ZxCrCustomerExtAttrService zxCrCustomerExtAttrService;

    @ApiOperation(value="查询协作单位管理", notes="查询协作单位管理")
    @ApiImplicitParam(name = "zxCrCustomerExtAttr", value = "协作单位管理entity", dataType = "ZxCrCustomerExtAttr")
    @RequireToken
    @PostMapping("/getZxCrCustomerExtAttrList")
    public ResponseEntity getZxCrCustomerExtAttrList(@RequestBody(required = false) ZxCrCustomerExtAttr zxCrCustomerExtAttr) {
        return zxCrCustomerExtAttrService.getZxCrCustomerExtAttrListByCondition(zxCrCustomerExtAttr);
    }

    @ApiOperation(value="查询详情协作单位管理", notes="查询详情协作单位管理")
    @ApiImplicitParam(name = "zxCrCustomerExtAttr", value = "协作单位管理entity", dataType = "ZxCrCustomerExtAttr")
    @RequireToken
    @PostMapping("/getZxCrCustomerExtAttrDetail")
    public ResponseEntity getZxCrCustomerExtAttrDetail(@RequestBody(required = false) ZxCrCustomerExtAttr zxCrCustomerExtAttr) {
        return zxCrCustomerExtAttrService.getZxCrCustomerExtAttrDetail(zxCrCustomerExtAttr);
    }

    @ApiOperation(value="新增协作单位管理", notes="新增协作单位管理")
    @ApiImplicitParam(name = "zxCrCustomerExtAttr", value = "协作单位管理entity", dataType = "ZxCrCustomerExtAttr")
    @RequireToken
    @PostMapping("/addZxCrCustomerExtAttr")
    public ResponseEntity addZxCrCustomerExtAttr(@RequestBody(required = false) ZxCrCustomerExtAttr zxCrCustomerExtAttr) {
        return zxCrCustomerExtAttrService.saveZxCrCustomerExtAttr(zxCrCustomerExtAttr);
    }

    @ApiOperation(value="更新协作单位管理", notes="更新协作单位管理")
    @ApiImplicitParam(name = "zxCrCustomerExtAttr", value = "协作单位管理entity", dataType = "ZxCrCustomerExtAttr")
    @RequireToken
    @PostMapping("/updateZxCrCustomerExtAttr")
    public ResponseEntity updateZxCrCustomerExtAttr(@RequestBody(required = false) ZxCrCustomerExtAttr zxCrCustomerExtAttr) {
        return zxCrCustomerExtAttrService.updateZxCrCustomerExtAttr(zxCrCustomerExtAttr);
    }

    @ApiOperation(value="删除协作单位管理", notes="删除协作单位管理")
    @ApiImplicitParam(name = "zxCrCustomerExtAttrList", value = "协作单位管理List", dataType = "List<ZxCrCustomerExtAttr>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxCrCustomerExtAttr")
    public ResponseEntity batchDeleteUpdateZxCrCustomerExtAttr(@RequestBody(required = false) List<ZxCrCustomerExtAttr> zxCrCustomerExtAttrList) {
        return zxCrCustomerExtAttrService.batchDeleteUpdateZxCrCustomerExtAttr(zxCrCustomerExtAttrList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    
    @ApiOperation(value="新增检索客商名称", notes="查询详情协作单位管理")
    @ApiImplicitParam(name = "zxCrCustomerExtAttr", value = "协作单位管理entity", dataType = "ZxCrCustomerExtAttr")
    @RequireToken
    @PostMapping("/getZxCrCustomerExtAttrDetailRetrieval")
    public ResponseEntity getZxCrCustomerExtAttrDetailRetrieval(@RequestBody(required = false) ZxCrCustomerExtAttr zxCrCustomerExtAttr) {
        return zxCrCustomerExtAttrService.getZxCrCustomerExtAttrDetailRetrieval(zxCrCustomerExtAttr);
    }
    
    @ApiOperation(value="新增检索代码证", notes="查询详情协作单位管理")
    @ApiImplicitParam(name = "zxCrCustomerExtAttr", value = "协作单位管理entity", dataType = "ZxCrCustomerExtAttr")
    @RequireToken
    @PostMapping("/getZxCrCustomerExtAttrAll")
    public ResponseEntity getZxCrCustomerExtAttrAll(@RequestBody(required = false) ZxCrCustomerExtAttr zxCrCustomerExtAttr) {
        return zxCrCustomerExtAttrService.getZxCrCustomerExtAttrAll(zxCrCustomerExtAttr);
    }
    
    @ApiOperation(value="新增检索代码证", notes="查询详情协作单位管理")
    @ApiImplicitParam(name = "zxCrCustomerExtAttr", value = "协作单位管理entity", dataType = "ZxCrCustomerExtAttr")
    @RequireToken
    @PostMapping("/getZxCrCustomerExtAttrListAll")
    public ResponseEntity getZxCrCustomerExtAttrListAll(@RequestBody(required = false) ZxCrCustomerExtAttr zxCrCustomerExtAttr) {
        return zxCrCustomerExtAttrService.getZxCrCustomerExtAttrListAll(zxCrCustomerExtAttr);
    }

    @ApiOperation(value="领料单位新增", notes="领料单位新增")
    @ApiImplicitParam(name = "zxCrCustomerExtAttr", value = "协作单位管理entity", dataType = "ZxCrCustomerExtAttr")
    @RequireToken
    @PostMapping("/addZxCrCustomerExtAttrPicking")
    public ResponseEntity addZxCrCustomerExtAttrPicking(@RequestBody(required = false) ZxCrCustomerExtAttr zxCrCustomerExtAttr) {
        return zxCrCustomerExtAttrService.addZxCrCustomerExtAttrPicking(zxCrCustomerExtAttr);
    }

    @ApiOperation(value="领料单位更新", notes="领料单位更新")
    @ApiImplicitParam(name = "zxCrCustomerExtAttr", value = "协作单位管理entity", dataType = "ZxCrCustomerExtAttr")
    @RequireToken
    @PostMapping("/updateZxCrCustomerExtAttrPicking")
    public ResponseEntity updateZxCrCustomerExtAttrPicking(@RequestBody(required = false) ZxCrCustomerExtAttr zxCrCustomerExtAttr) {
        return zxCrCustomerExtAttrService.updateZxCrCustomerExtAttrPicking(zxCrCustomerExtAttr);
    }

    @ApiOperation(value="查询领料单位", notes="查询领料单位")
    @ApiImplicitParam(name = "zxCrCustomerExtAttr", value = "协作单位管理entity", dataType = "ZxCrCustomerExtAttr")
    @RequireToken
    @PostMapping("/getZxCrCustomerExtAttrPickingList")
    public ResponseEntity getZxCrCustomerExtAttrPickingList(@RequestBody(required = false) ZxCrCustomerExtAttr zxCrCustomerExtAttr) {
        return zxCrCustomerExtAttrService.getZxCrCustomerExtAttrPickingList(zxCrCustomerExtAttr);
    }
    
    @ApiOperation(value="查询工程类合同乙方", notes="查询工程类合同乙方")
    @ApiImplicitParam(name = "zxCrCustomerExtAttr", value = "协作单位管理entity", dataType = "ZxCrCustomerExtAttr")
    @RequireToken
    @PostMapping("/getZxCrCustomerExtAttrEngineeringList")
    public ResponseEntity getZxCrCustomerExtAttrEngineeringList(@RequestBody(required = false) ZxCrCustomerExtAttr zxCrCustomerExtAttr) {
        return zxCrCustomerExtAttrService.getZxCrCustomerExtAttrEngineeringList(zxCrCustomerExtAttr);
    }


}
