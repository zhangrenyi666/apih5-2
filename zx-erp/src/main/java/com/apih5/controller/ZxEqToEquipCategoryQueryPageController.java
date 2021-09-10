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
import com.apih5.mybatis.pojo.ZxEqToEquipCategoryQueryPage;
import com.apih5.service.ZxEqToEquipCategoryQueryPageService;

@RestController
public class ZxEqToEquipCategoryQueryPageController {

    @Autowired(required = true)
    private ZxEqToEquipCategoryQueryPageService zxEqToEquipCategoryQueryPageService;

    @ApiOperation(value="查询设备种类资产分布", notes="查询设备种类资产分布")
    @ApiImplicitParam(name = "zxEqToEquipCategoryQueryPage", value = "设备种类资产分布entity", dataType = "ZxEqToEquipCategoryQueryPage")
    @RequireToken
    @PostMapping("/getZxEqToEquipCategoryQueryPageList")
    public ResponseEntity getZxEqToEquipCategoryQueryPageList(@RequestBody(required = false) ZxEqToEquipCategoryQueryPage zxEqToEquipCategoryQueryPage) {
        return zxEqToEquipCategoryQueryPageService.getZxEqToEquipCategoryQueryPageListByCondition(zxEqToEquipCategoryQueryPage);
    }

    @ApiOperation(value="查询详情设备种类资产分布", notes="查询详情设备种类资产分布")
    @ApiImplicitParam(name = "zxEqToEquipCategoryQueryPage", value = "设备种类资产分布entity", dataType = "ZxEqToEquipCategoryQueryPage")
    @RequireToken
    @PostMapping("/getZxEqToEquipCategoryQueryPageDetail")
    public ResponseEntity getZxEqToEquipCategoryQueryPageDetail(@RequestBody(required = false) ZxEqToEquipCategoryQueryPage zxEqToEquipCategoryQueryPage) {
        return zxEqToEquipCategoryQueryPageService.getZxEqToEquipCategoryQueryPageDetail(zxEqToEquipCategoryQueryPage);
    }

    @ApiOperation(value="新增设备种类资产分布", notes="新增设备种类资产分布")
    @ApiImplicitParam(name = "zxEqToEquipCategoryQueryPage", value = "设备种类资产分布entity", dataType = "ZxEqToEquipCategoryQueryPage")
    @RequireToken
    @PostMapping("/addZxEqToEquipCategoryQueryPage")
    public ResponseEntity addZxEqToEquipCategoryQueryPage(@RequestBody(required = false) ZxEqToEquipCategoryQueryPage zxEqToEquipCategoryQueryPage) {
        return zxEqToEquipCategoryQueryPageService.saveZxEqToEquipCategoryQueryPage(zxEqToEquipCategoryQueryPage);
    }

    @ApiOperation(value="更新设备种类资产分布", notes="更新设备种类资产分布")
    @ApiImplicitParam(name = "zxEqToEquipCategoryQueryPage", value = "设备种类资产分布entity", dataType = "ZxEqToEquipCategoryQueryPage")
    @RequireToken
    @PostMapping("/updateZxEqToEquipCategoryQueryPage")
    public ResponseEntity updateZxEqToEquipCategoryQueryPage(@RequestBody(required = false) ZxEqToEquipCategoryQueryPage zxEqToEquipCategoryQueryPage) {
        return zxEqToEquipCategoryQueryPageService.updateZxEqToEquipCategoryQueryPage(zxEqToEquipCategoryQueryPage);
    }

    @ApiOperation(value="删除设备种类资产分布", notes="删除设备种类资产分布")
    @ApiImplicitParam(name = "zxEqToEquipCategoryQueryPageList", value = "设备种类资产分布List", dataType = "List<ZxEqToEquipCategoryQueryPage>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxEqToEquipCategoryQueryPage")
    public ResponseEntity batchDeleteUpdateZxEqToEquipCategoryQueryPage(@RequestBody(required = false) List<ZxEqToEquipCategoryQueryPage> zxEqToEquipCategoryQueryPageList) {
        return zxEqToEquipCategoryQueryPageService.batchDeleteUpdateZxEqToEquipCategoryQueryPage(zxEqToEquipCategoryQueryPageList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    
    @ApiOperation(value="报表设备种类资产分布", notes="报表设备种类资产分布")
    @ApiImplicitParam(name = "zxEqEquipIntegratedQuery", value = "设备台账entity", dataType = "ZxEqEquipIntegratedQuery")
    @RequireToken
    @PostMapping("/ureportZxEqToEquipCategoryQueryPageIdle")
    public ResponseEntity ureportZxEqToEquipCategoryQueryPageIdle(@RequestBody(required = false) ZxEqToEquipCategoryQueryPage zxEqToEquipCategoryQueryPage) {
        return zxEqToEquipCategoryQueryPageService.ureportZxEqToEquipCategoryQueryPageIdle(zxEqToEquipCategoryQueryPage);
    }
    
    @ApiOperation(value="报表设备种类资产分布数量比", notes="报表设备种类资产分布数量比")
    @ApiImplicitParam(name = "zxEqEquipIntegratedQuery", value = "设备台账entity", dataType = "ZxEqEquipIntegratedQuery")
    @RequireToken
    @PostMapping("/ureportZxEqToEquipCategoryQueryPageCountIdle")
    public ResponseEntity ureportZxEqToEquipCategoryQueryPageCountIdle(@RequestBody(required = false) ZxEqToEquipCategoryQueryPage zxEqToEquipCategoryQueryPage) {
        return zxEqToEquipCategoryQueryPageService.ureportZxEqToEquipCategoryQueryPageCountIdle(zxEqToEquipCategoryQueryPage);
    }
    
    @ApiOperation(value="报表设备种类资产分布原值比", notes="报表设备种类资产分布原值比")
    @ApiImplicitParam(name = "zxEqEquipIntegratedQuery", value = "设备台账entity", dataType = "ZxEqEquipIntegratedQuery")
    @RequireToken
    @PostMapping("/ureportZxEqToEquipCategoryQueryPageOrginalValueIdle")
    public ResponseEntity ureportZxEqToEquipCategoryQueryPageOrginalValueIdle(@RequestBody(required = false) ZxEqToEquipCategoryQueryPage zxEqToEquipCategoryQueryPage) {
        return zxEqToEquipCategoryQueryPageService.ureportZxEqToEquipCategoryQueryPageOrginalValueIdle(zxEqToEquipCategoryQueryPage);
    }
    
    @ApiOperation(value="报表设备种类资产分布净值比", notes="报表设备种类资产分布净值比")
    @ApiImplicitParam(name = "zxEqEquipIntegratedQuery", value = "设备台账entity", dataType = "ZxEqEquipIntegratedQuery")
    @RequireToken
    @PostMapping("/ureportZxEqToEquipCategoryQueryPageleftValueIdle")
    public ResponseEntity ureportZxEqToEquipCategoryQueryPageleftValueIdle(@RequestBody(required = false) ZxEqToEquipCategoryQueryPage zxEqToEquipCategoryQueryPage) {
        return zxEqToEquipCategoryQueryPageService.ureportZxEqToEquipCategoryQueryPageleftValueIdle(zxEqToEquipCategoryQueryPage);
    }
    
   
}
