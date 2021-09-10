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
import com.apih5.mybatis.pojo.ZxEqEquipIntegratedQuery;
import com.apih5.mybatis.pojo.ZxEqToEquipCategoryQuantityQueryPage;
import com.apih5.service.ZxEqToEquipCategoryQuantityQueryPageService;

@RestController
public class ZxEqToEquipCategoryQuantityQueryPageController {

    @Autowired(required = true)
    private ZxEqToEquipCategoryQuantityQueryPageService zxEqToEquipCategoryQuantityQueryPageService;

    @ApiOperation(value="查询设备分类查询", notes="查询设备分类查询")
    @ApiImplicitParam(name = "zxEqToEquipCategoryQuantityQueryPage", value = "设备分类查询entity", dataType = "ZxEqToEquipCategoryQuantityQueryPage")
    @RequireToken
    @PostMapping("/getZxEqToEquipCategoryQuantityQueryPageList")
    public ResponseEntity getZxEqToEquipCategoryQuantityQueryPageList(@RequestBody(required = false) ZxEqToEquipCategoryQuantityQueryPage zxEqToEquipCategoryQuantityQueryPage) {
        return zxEqToEquipCategoryQuantityQueryPageService.getZxEqToEquipCategoryQuantityQueryPageListByCondition(zxEqToEquipCategoryQuantityQueryPage);
    }

    @ApiOperation(value="查询详情设备分类查询", notes="查询详情设备分类查询")
    @ApiImplicitParam(name = "zxEqToEquipCategoryQuantityQueryPage", value = "设备分类查询entity", dataType = "ZxEqToEquipCategoryQuantityQueryPage")
    @RequireToken
    @PostMapping("/getZxEqToEquipCategoryQuantityQueryPageDetail")
    public ResponseEntity getZxEqToEquipCategoryQuantityQueryPageDetail(@RequestBody(required = false) ZxEqToEquipCategoryQuantityQueryPage zxEqToEquipCategoryQuantityQueryPage) {
        return zxEqToEquipCategoryQuantityQueryPageService.getZxEqToEquipCategoryQuantityQueryPageDetail(zxEqToEquipCategoryQuantityQueryPage);
    }

    @ApiOperation(value="新增设备分类查询", notes="新增设备分类查询")
    @ApiImplicitParam(name = "zxEqToEquipCategoryQuantityQueryPage", value = "设备分类查询entity", dataType = "ZxEqToEquipCategoryQuantityQueryPage")
    @RequireToken
    @PostMapping("/addZxEqToEquipCategoryQuantityQueryPage")
    public ResponseEntity addZxEqToEquipCategoryQuantityQueryPage(@RequestBody(required = false) ZxEqToEquipCategoryQuantityQueryPage zxEqToEquipCategoryQuantityQueryPage) {
        return zxEqToEquipCategoryQuantityQueryPageService.saveZxEqToEquipCategoryQuantityQueryPage(zxEqToEquipCategoryQuantityQueryPage);
    }

    @ApiOperation(value="更新设备分类查询", notes="更新设备分类查询")
    @ApiImplicitParam(name = "zxEqToEquipCategoryQuantityQueryPage", value = "设备分类查询entity", dataType = "ZxEqToEquipCategoryQuantityQueryPage")
    @RequireToken
    @PostMapping("/updateZxEqToEquipCategoryQuantityQueryPage")
    public ResponseEntity updateZxEqToEquipCategoryQuantityQueryPage(@RequestBody(required = false) ZxEqToEquipCategoryQuantityQueryPage zxEqToEquipCategoryQuantityQueryPage) {
        return zxEqToEquipCategoryQuantityQueryPageService.updateZxEqToEquipCategoryQuantityQueryPage(zxEqToEquipCategoryQuantityQueryPage);
    }

    @ApiOperation(value="删除设备分类查询", notes="删除设备分类查询")
    @ApiImplicitParam(name = "zxEqToEquipCategoryQuantityQueryPageList", value = "设备分类查询List", dataType = "List<ZxEqToEquipCategoryQuantityQueryPage>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxEqToEquipCategoryQuantityQueryPage")
    public ResponseEntity batchDeleteUpdateZxEqToEquipCategoryQuantityQueryPage(@RequestBody(required = false) List<ZxEqToEquipCategoryQuantityQueryPage> zxEqToEquipCategoryQuantityQueryPageList) {
        return zxEqToEquipCategoryQuantityQueryPageService.batchDeleteUpdateZxEqToEquipCategoryQuantityQueryPage(zxEqToEquipCategoryQuantityQueryPageList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    
    @ApiOperation(value="报表设备分类查询", notes="报表设备分类查询")
    @ApiImplicitParam(name = "zxEqToEquipCategoryQuantityQueryPage", value = "设备台账entity", dataType = "ZxEqToEquipCategoryQuantityQueryPage")
    @RequireToken
    @PostMapping("/ureportZxEqToEquipCategoryQuantityQueryPageIdle")
    public ResponseEntity ureportZxEqToEquipCategoryQuantityQueryPageIdle(@RequestBody(required = false) ZxEqToEquipCategoryQuantityQueryPage zxEqToEquipCategoryQuantityQueryPage) {
        return zxEqToEquipCategoryQuantityQueryPageService.ureportZxEqToEquipCategoryQuantityQueryPageIdle(zxEqToEquipCategoryQuantityQueryPage);
    }
    
    @ApiOperation(value="图表设备分类查询自有设备", notes="图表设备分类查询自有设备")
    @ApiImplicitParam(name = "zxEqToEquipCategoryQuantityQueryPage", value = "设备台账entity", dataType = "ZxEqToEquipCategoryQuantityQueryPage")
    @RequireToken
    @PostMapping("/ureportZxEqToEquipCategoryQuantityQueryPageChartIdle")
    public ResponseEntity ureportZxEqToEquipCategoryQuantityQueryPageChartIdle(@RequestBody(required = false) ZxEqToEquipCategoryQuantityQueryPage zxEqToEquipCategoryQuantityQueryPage) {
        return zxEqToEquipCategoryQuantityQueryPageService.ureportZxEqToEquipCategoryQuantityQueryPageChartIdle(zxEqToEquipCategoryQuantityQueryPage);
    }
    
    @ApiOperation(value="图表设备分类查询外租设备", notes="图表设备分类查询外租设备")
    @ApiImplicitParam(name = "zxEqToEquipCategoryQuantityQueryPage", value = "设备台账entity", dataType = "ZxEqToEquipCategoryQuantityQueryPage")
    @RequireToken
    @PostMapping("/ureportZxEqToEquipCategoryQuantityQueryPageRentOutIdle")
    public ResponseEntity ureportZxEqToEquipCategoryQuantityQueryPageRentOutIdle(@RequestBody(required = false) ZxEqToEquipCategoryQuantityQueryPage zxEqToEquipCategoryQuantityQueryPage) {
        return zxEqToEquipCategoryQuantityQueryPageService.ureportZxEqToEquipCategoryQuantityQueryPageRentOutIdle(zxEqToEquipCategoryQuantityQueryPage);
    }
    
    @ApiOperation(value="图表设备分类查询协作单位设备", notes="图表设备分类查询协作单位设备")
    @ApiImplicitParam(name = "zxEqToEquipCategoryQuantityQueryPage", value = "设备台账entity", dataType = "ZxEqToEquipCategoryQuantityQueryPage")
    @RequireToken
    @PostMapping("/ureportZxEqToEquipCategoryQuantityQueryPageCooperationUnitIdle")
    public ResponseEntity ureportZxEqToEquipCategoryQuantityQueryPageCooperationUnitIdle(@RequestBody(required = false) ZxEqToEquipCategoryQuantityQueryPage zxEqToEquipCategoryQuantityQueryPage) {
        return zxEqToEquipCategoryQuantityQueryPageService.ureportZxEqToEquipCategoryQuantityQueryPageCooperationUnitIdle(zxEqToEquipCategoryQuantityQueryPage);
    }
}
