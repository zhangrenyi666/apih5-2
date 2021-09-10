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
import com.apih5.mybatis.pojo.ZxEqToCompareAnalyseQueryPage;
import com.apih5.service.ZxEqToCompareAnalyseQueryPageService;

@RestController
public class ZxEqToCompareAnalyseQueryPageController {

    @Autowired(required = true)
    private ZxEqToCompareAnalyseQueryPageService zxEqToCompareAnalyseQueryPageService;

    @ApiOperation(value="查询同比分析", notes="查询同比分析")
    @ApiImplicitParam(name = "zxEqToCompareAnalyseQueryPage", value = "同比分析entity", dataType = "ZxEqToCompareAnalyseQueryPage")
    @RequireToken
    @PostMapping("/getZxEqToCompareAnalyseQueryPageList")
    public ResponseEntity getZxEqToCompareAnalyseQueryPageList(@RequestBody(required = false) ZxEqToCompareAnalyseQueryPage zxEqToCompareAnalyseQueryPage) {
        return zxEqToCompareAnalyseQueryPageService.getZxEqToCompareAnalyseQueryPageListByCondition(zxEqToCompareAnalyseQueryPage);
    }

    @ApiOperation(value="查询详情同比分析", notes="查询详情同比分析")
    @ApiImplicitParam(name = "zxEqToCompareAnalyseQueryPage", value = "同比分析entity", dataType = "ZxEqToCompareAnalyseQueryPage")
    @RequireToken
    @PostMapping("/getZxEqToCompareAnalyseQueryPageDetail")
    public ResponseEntity getZxEqToCompareAnalyseQueryPageDetail(@RequestBody(required = false) ZxEqToCompareAnalyseQueryPage zxEqToCompareAnalyseQueryPage) {
        return zxEqToCompareAnalyseQueryPageService.getZxEqToCompareAnalyseQueryPageDetail(zxEqToCompareAnalyseQueryPage);
    }

    @ApiOperation(value="新增同比分析", notes="新增同比分析")
    @ApiImplicitParam(name = "zxEqToCompareAnalyseQueryPage", value = "同比分析entity", dataType = "ZxEqToCompareAnalyseQueryPage")
    @RequireToken
    @PostMapping("/addZxEqToCompareAnalyseQueryPage")
    public ResponseEntity addZxEqToCompareAnalyseQueryPage(@RequestBody(required = false) ZxEqToCompareAnalyseQueryPage zxEqToCompareAnalyseQueryPage) {
        return zxEqToCompareAnalyseQueryPageService.saveZxEqToCompareAnalyseQueryPage(zxEqToCompareAnalyseQueryPage);
    }

    @ApiOperation(value="更新同比分析", notes="更新同比分析")
    @ApiImplicitParam(name = "zxEqToCompareAnalyseQueryPage", value = "同比分析entity", dataType = "ZxEqToCompareAnalyseQueryPage")
    @RequireToken
    @PostMapping("/updateZxEqToCompareAnalyseQueryPage")
    public ResponseEntity updateZxEqToCompareAnalyseQueryPage(@RequestBody(required = false) ZxEqToCompareAnalyseQueryPage zxEqToCompareAnalyseQueryPage) {
        return zxEqToCompareAnalyseQueryPageService.updateZxEqToCompareAnalyseQueryPage(zxEqToCompareAnalyseQueryPage);
    }

    @ApiOperation(value="删除同比分析", notes="删除同比分析")
    @ApiImplicitParam(name = "zxEqToCompareAnalyseQueryPageList", value = "同比分析List", dataType = "List<ZxEqToCompareAnalyseQueryPage>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxEqToCompareAnalyseQueryPage")
    public ResponseEntity batchDeleteUpdateZxEqToCompareAnalyseQueryPage(@RequestBody(required = false) List<ZxEqToCompareAnalyseQueryPage> zxEqToCompareAnalyseQueryPageList) {
        return zxEqToCompareAnalyseQueryPageService.batchDeleteUpdateZxEqToCompareAnalyseQueryPage(zxEqToCompareAnalyseQueryPageList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    
    @ApiOperation(value="报表同比分析", notes="报表同比分析")
    @ApiImplicitParam(name = "zxEqToCompareAnalyseQueryPage", value = "设备台账entity", dataType = "ZxEqToCompareAnalyseQueryPage")
    @RequireToken
    @PostMapping("/ureportZxEqToCompareAnalyseQueryPageIdle")
    public ResponseEntity ureportZxEqEquipIntegratedQueryIdle(@RequestBody(required = false) ZxEqToCompareAnalyseQueryPage zxEqToCompareAnalyseQueryPage) {
        return zxEqToCompareAnalyseQueryPageService.ureportZxEqEquipIntegratedQueryIdle(zxEqToCompareAnalyseQueryPage);
    }
    
    @ApiOperation(value="报表同比分析期次", notes="报表同比分析期次")
    @ApiImplicitParam(name = "zxEqToCompareAnalyseQueryPage", value = "设备台账entity", dataType = "ZxEqToCompareAnalyseQueryPage")
    @RequireToken
    @PostMapping("/ureportZxEqEquipIntegratedQueryPeriodIdle")
    public ResponseEntity ureportZxEqEquipIntegratedQueryPeriodIdle(@RequestBody(required = false) ZxEqToCompareAnalyseQueryPage zxEqToCompareAnalyseQueryPage) {
        return zxEqToCompareAnalyseQueryPageService.ureportZxEqEquipIntegratedQueryPeriodIdle(zxEqToCompareAnalyseQueryPage);
    }
}
