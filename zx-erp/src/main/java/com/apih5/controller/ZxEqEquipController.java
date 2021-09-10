package com.apih5.controller;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.apih5.framework.annotation.RequireToken;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxEqEquip;
import com.apih5.service.ZxEqEquipService;
import com.apih5.utils.ZxErpUtil;
import cn.hutool.json.JSONObject;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

@RestController
public class ZxEqEquipController {

    @Autowired(required = true)
    private ZxEqEquipService zxEqEquipService;

    @ApiOperation(value="查询设备台账", notes="查询设备台账")
    @ApiImplicitParam(name = "zxEqEquip", value = "设备台账entity", dataType = "ZxEqEquip")
    @RequireToken
    @PostMapping("/getZxEqEquipList")
    public ResponseEntity getZxEqEquipList(@RequestBody(required = false) ZxEqEquip zxEqEquip) {
        return zxEqEquipService.getZxEqEquipListByCondition(zxEqEquip);
    }

    @ApiOperation(value="查询详情设备台账", notes="查询详情设备台账")
    @ApiImplicitParam(name = "zxEqEquip", value = "设备台账entity", dataType = "ZxEqEquip")
    @RequireToken
    @PostMapping("/getZxEqEquipDetails")
    public ResponseEntity getZxEqEquipDetails(@RequestBody(required = false) ZxEqEquip zxEqEquip) {
        return zxEqEquipService.getZxEqEquipDetails(zxEqEquip);
    }

    @ApiOperation(value="新增设备台账", notes="新增设备台账")
    @ApiImplicitParam(name = "zxEqEquip", value = "设备台账entity", dataType = "ZxEqEquip")
    @RequireToken
    @PostMapping("/addZxEqEquip")
    public ResponseEntity addZxEqEquip(@RequestBody(required = false) ZxEqEquip zxEqEquip) {
        return zxEqEquipService.saveZxEqEquip(zxEqEquip);
    }

    @ApiOperation(value="更新设备台账", notes="更新设备台账")
    @ApiImplicitParam(name = "zxEqEquip", value = "设备台账entity", dataType = "ZxEqEquip")
    @RequireToken
    @PostMapping("/updateZxEqEquip")
    public ResponseEntity updateZxEqEquip(@RequestBody(required = false) ZxEqEquip zxEqEquip) {
        return zxEqEquipService.updateZxEqEquip(zxEqEquip);
    }

    @ApiOperation(value="删除设备台账", notes="删除设备台账")
    @ApiImplicitParam(name = "zxEqEquipList", value = "设备台账List", dataType = "List<ZxEqEquip>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxEqEquip")
    public ResponseEntity batchDeleteUpdateZxEqEquip(@RequestBody(required = false) List<ZxEqEquip> zxEqEquipList) {
        return zxEqEquipService.batchDeleteUpdateZxEqEquip(zxEqEquipList);
    }
    
    @ApiOperation(value="闲置设备台账", notes="闲置设备台账")
    @ApiImplicitParam(name = "zxEqEquip", value = "设备台账entity", dataType = "ZxEqEquip")
    @RequireToken
    @PostMapping("/unusedZxEqEquip")
    public ResponseEntity unusedZxEqEquip(@RequestBody(required = false) ZxEqEquip zxEqEquip) {
        return zxEqEquipService.unusedZxEqEquip(zxEqEquip);
    }
    
    @ApiOperation(value="恢复设备台账", notes="恢复设备台账")
    @ApiImplicitParam(name = "zxEqEquip", value = "设备台账entity", dataType = "ZxEqEquip")
    @RequireToken
    @PostMapping("/recoverZxEqEquip")
    public ResponseEntity recoverZxEqEquip(@RequestBody(required = false) ZxEqEquip zxEqEquip) {
        return zxEqEquipService.recoverZxEqEquip(zxEqEquip);
    }
    
    //报表
    @ApiOperation(value="查询导出设备台账报表列表", notes="查询导出设备台账报表列表")
    @ApiImplicitParam(name = "zxEqEquip", value = "设备台账entity", dataType = "ZxEqEquip")
    @RequireToken
    @PostMapping("/getZxEqEquipListForReport")
    public ResponseEntity getZxEqEquipListForReport(@RequestBody(required = false) ZxEqEquip zxEqEquip) {
        return zxEqEquipService.getZxEqEquipListForReport(zxEqEquip);
    }
    
    @ApiOperation(value="报表导出设备台账", notes="报表导出设备台账")
    @ApiImplicitParam(name = "zxEqEquip", value = "设备台账entity", dataType = "ZxEqEquip")
    @PostMapping("/ureportZxEqEquipList")
    public List<ZxEqEquip> ureportZxEqEquipList(@RequestBody(required = false) ZxEqEquip zxEqEquip) {
        return zxEqEquipService.ureportZxEqEquipList(zxEqEquip);
    }
    
    @ApiOperation(value="报表导出闲置设备台账", notes="报表导出闲置设备台账")
    @ApiImplicitParam(name = "zxEqEquip", value = "设备台账entity", dataType = "ZxEqEquip")
    @PostMapping("/ureportZxEqEquipListIdle")
    public List<ZxEqEquip> ureportZxEqEquipListIdle(@RequestBody(required = false) ZxEqEquip zxEqEquip) {
        return zxEqEquipService.ureportZxEqEquipListIdle(zxEqEquip);
    }

    @ApiOperation(value="报表导出公司车辆报表", notes="报表导出公司车辆报表")
    @ApiImplicitParam(name = "zxEqEquip", value = "设备台账entity", dataType = "ZxEqEquip")
    @PostMapping("/ureportZxEqEquipListVehicle")
    public List<ZxEqEquip> ureportZxEqEquipListVehicle(@RequestBody(required = false) ZxEqEquip zxEqEquip) {
        return zxEqEquipService.ureportZxEqEquipListVehicle(zxEqEquip);
    }
    
    @ApiOperation(value="报表公司车辆报表", notes="报表公司车辆报表")
    @ApiImplicitParam(name = "zxEqEquip", value = "设备台账entity", dataType = "ZxEqEquip")
    @RequireToken
    @PostMapping("/ureportZxEqEquipListVehicleIdle")
    public ResponseEntity ureportZxEqEquipListVehicleIdle(@RequestBody(required = false) ZxEqEquip zxEqEquip) {
        return zxEqEquipService.ureportZxEqEquipListVehicleIdle(zxEqEquip);
    }

    @ApiOperation(value="报表导出公司机械报表", notes="报表导出公司机械报表")
    @ApiImplicitParam(name = "zxEqEquip", value = "设备台账entity", dataType = "ZxEqEquip")
    @PostMapping("/ureportZxEqEquipListMechanics")
    public List<ZxEqEquip> ureportZxEqEquipListMechanics(@RequestBody(required = false) ZxEqEquip zxEqEquip) {
        return zxEqEquipService.ureportZxEqEquipListMechanics(zxEqEquip);
    }
    
    @ApiOperation(value="报表公司机械报表", notes="报表公司机械报表")
    @ApiImplicitParam(name = "zxEqEquip", value = "设备台账entity", dataType = "ZxEqEquip")
    @RequireToken
    @PostMapping("/ureportZxEqEquipListMechanicsIdle")
    public ResponseEntity ureportZxEqEquipListMechanicsIdle(@RequestBody(required = false) ZxEqEquip zxEqEquip) {
        return zxEqEquipService.ureportZxEqEquipListMechanicsIdle(zxEqEquip);
    }

    @ApiOperation(value="给实际验收月台账写的下拉接口", notes="给实际验收月台账写的下拉接口")
    @ApiImplicitParam(name = "zxEqEquip", value = "设备台账entity", dataType = "ZxEqEquip")
    @RequireToken
    @PostMapping("/getZxEqEquipListForRealFact")
    public ResponseEntity getZxEqEquipListForRealFact(@RequestBody(required = false) ZxEqEquip zxEqEquip) {
        return zxEqEquipService.getZxEqEquipListForRealFact(zxEqEquip);
    }

    @ApiOperation(value="查询设备台账", notes="查询设备台账")
    @ApiImplicitParam(name = "zxEqEquip", value = "设备台账entity", dataType = "ZxEqEquip")
    @RequireToken
    @PostMapping("/getZxEqEquipListForDepreciation")
    public ResponseEntity getZxEqEquipListForDepreciation(@RequestBody(required = false) ZxEqEquip zxEqEquip) {
        return zxEqEquipService.getZxEqEquipListForDepreciation(zxEqEquip);
    }
    
    @ApiOperation(value="查询设备台账", notes="查询设备台账")
    @ApiImplicitParam(name = "zxEqEquip", value = "设备台账entity", dataType = "ZxEqEquip")
    @RequireToken
    @PostMapping("/getZxEqEquipListForDepreciationRemove")
    public ResponseEntity getZxEqEquipListForDepreciationRemove(@RequestBody(required = false) ZxEqEquip zxEqEquip) {
        return zxEqEquipService.getZxEqEquipListForDepreciationRemove(zxEqEquip);
    }
    
    @ApiOperation(value="公司内调拨=查询设备台账", notes="查询设备台账")
    @ApiImplicitParam(name = "zxEqEquip", value = "设备台账entity", dataType = "ZxEqEquip")
    @RequireToken
    @PostMapping("/getZxEqEquipListForMoveUseOrg")
    public ResponseEntity getZxEqEquipListForMoveUseOrg(@RequestBody(required = false) ZxEqEquip zxEqEquip) {
        return zxEqEquipService.getZxEqEquipListForMoveUseOrg(zxEqEquip);
    }

    
    @ApiOperation(value="新增设备汇总表", notes="新增设备汇总表")
    @ApiImplicitParam(name = "zxEqEquip", value = "设备台账entity", dataType = "ZxEqEquip")
    @RequireToken
    @PostMapping("/getAddZxEqEquipTotalList")
    public ResponseEntity getAddZxEqEquipTotalList(@RequestBody(required = false) ZxEqEquip zxEqEquip) {
        return zxEqEquipService.getAddZxEqEquipTotalList(zxEqEquip);
    }
    @ApiOperation(value="导出新增设备汇总表", notes="导出新增设备汇总表")
    @ApiImplicitParam(name = "zxEqEquip", value = "设备台账entity", dataType = "ZxEqEquip")
    @PostMapping("/ureportAddZxEqEquipTotalList")
    public List<ZxEqEquip> ureportAddZxEqEquipTotalList(@RequestBody(required = false) ZxEqEquip zxEqEquip) {
        return zxEqEquipService.ureportAddZxEqEquipTotalList(zxEqEquip);
    }
    
    @ApiOperation(value="自有车辆配备情况统计表(月报)", notes="自有车辆配备情况统计表(月报)")
    @ApiImplicitParam(name = "zxEqEquip", value = "设备台账entity", dataType = "ZxEqEquip")
    @RequireToken
    @PostMapping("/getZxEqEquipTotalListForCar")
    public ResponseEntity getZxEqEquipTotalListForCar(@RequestBody(required = false) ZxEqEquip zxEqEquip) {
    	zxEqEquip.setSelectTypeFlag("只查设备分类=运输机械类的数据");
    	return zxEqEquipService.getAddZxEqEquipTotalList(zxEqEquip);
    }
    //
    @ApiOperation(value="五年趋势分析", notes="五年趋势分析")
    @ApiImplicitParam(name = "zxEqEquip", value = "设备台账entity", dataType = "ZxEqEquip")
    @RequireToken
    @PostMapping("/getZxEqEquipListForFiveYearTrend")
    public Object getZxEqEquipListForFiveYearTrend(@RequestBody(required = false) ZxEqEquip zxEqEquip) {
    	String userId = "";//TokenUtils.getUserId(request);
        JSONObject jsonObject = new JSONObject(zxEqEquip);
        Object object = ZxErpUtil.getApiContent(userId, "getZxEqEquipListForFiveYearTrend", jsonObject);
        return object;
    }
    @ApiOperation(value="同比分析", notes="同比分析")
    @ApiImplicitParam(name = "zxEqEquip", value = "设备台账entity", dataType = "ZxEqEquip")
    @RequireToken
    @PostMapping("/getZxEqEquipListForComparedWith")
    public Object getZxEqEquipListForComparedWith(@RequestBody(required = false) ZxEqEquip zxEqEquip) {
    	String userId = "";//TokenUtils.getUserId(request);
        JSONObject jsonObject = new JSONObject(zxEqEquip);
        Object object = ZxErpUtil.getApiContent(userId, "getZxEqEquipListForComparedWith", jsonObject);
        return object;
    }
	//
    @ApiOperation(value="ABCD设备资产分布", notes="ABCD设备资产分布")
    @ApiImplicitParam(name = "zxEqEquip", value = "设备台账entity", dataType = "ZxEqEquip")
    @RequireToken
    @PostMapping("/getZxEqEquipListForABCDDistribute")
    public Object getZxEqEquipListForABCDDistribute(@RequestBody(required = false) ZxEqEquip zxEqEquip) {
    	String userId = "";//TokenUtils.getUserId(request);
        JSONObject jsonObject = new JSONObject(zxEqEquip);
        Object object = ZxErpUtil.getApiContent(userId, "getZxEqEquipListForABCDDistribute", jsonObject);
        return object;
    }
    //
    @ApiOperation(value="设备分类查询", notes="设备分类查询")
    @ApiImplicitParam(name = "zxEqEquip", value = "设备台账entity", dataType = "ZxEqEquip")
    @RequireToken
    @PostMapping("/getZxEqEquipListForEquipClassify")
    public Object getZxEqEquipListForEquipClassify(@RequestBody(required = false) ZxEqEquip zxEqEquip) {
    	String userId = "";//TokenUtils.getUserId(request);
        JSONObject jsonObject = new JSONObject(zxEqEquip);
        Object object = ZxErpUtil.getApiContent(userId, "getZxEqEquipListForEquipClassify", jsonObject);
        return object;
    }
    //
    @ApiOperation(value="设备种类资产分布", notes="设备种类资产分布")
    @ApiImplicitParam(name = "zxEqEquip", value = "设备台账entity", dataType = "ZxEqEquip")
    @RequireToken 
    @PostMapping("/getZxEqEquipListForEquipClassifyDistribute")
    public Object getZxEqEquipListForEquipClassifyDistribute(HttpServletRequest request,@RequestBody(required = false) ZxEqEquip zxEqEquip) {
    	String userId = "";//TokenUtils.getUserId(request);
        JSONObject jsonObject = new JSONObject(zxEqEquip);
        Object object = ZxErpUtil.getApiContent(userId, "getZxEqEquipListForEquipClassifyDistribute", jsonObject);
        return object;
    }
    //
    @ApiOperation(value="设备来源查询", notes="设备来源查询")
    @ApiImplicitParam(name = "zxEqEquip", value = "设备台账entity", dataType = "ZxEqEquip")
    @RequireToken 
    @PostMapping("/getZxEqEquipListForEquipSource")
    public Object getZxEqEquipListForEquipSource(HttpServletRequest request,@RequestBody(required = false) ZxEqEquip zxEqEquip) {
    	String userId = "";//TokenUtils.getUserId(request);
        JSONObject jsonObject = new JSONObject(zxEqEquip);
        Object object = ZxErpUtil.getApiContent(userId, "getZxEqEquipListForEquipSource", jsonObject);
        return object;
    }
    //
    @ApiOperation(value="主要机械设备ABCD情况表", notes="主要机械设备ABCD情况表")
    @ApiImplicitParam(name = "zxEqEquip", value = "设备台账entity", dataType = "ZxEqEquip")
    @RequireToken
    @PostMapping("/getZxEqEquipListForMainABCDCase")
    public Object getZxEqEquipListForABCDCase(@RequestBody(required = false) ZxEqEquip zxEqEquip) {
    	String userId = "";//TokenUtils.getUserId(request);
        JSONObject jsonObject = new JSONObject(zxEqEquip);
        Object object = ZxErpUtil.getApiContent(userId, "getZxEqEquipListForMainABCDCase", jsonObject);
        return object;
    }
    @ApiOperation(value="导出主要机械设备ABCD情况表", notes="导出主要机械设备ABCD情况表")
    @ApiImplicitParam(name = "zxEqEquip", value = "设备台账entity", dataType = "ZxEqEquip")
    @PostMapping("/ureportZxEqEquipListForMainABCDCase")
    public Object ureportZxEqEquipListForMainABCDCase(HttpServletRequest request,@RequestBody(required = false) ZxEqEquip zxEqEquip) {
        String userId = "";//TokenUtils.getUserId(request);
        JSONObject jsonObject = new JSONObject(zxEqEquip);
        Object resultObject = ZxErpUtil.getApiContent(userId, "ureportZxEqEquipListForMainABCDCase", jsonObject);
        JSONObject resultJSONObject = new JSONObject(resultObject);
        return resultJSONObject.getObj("data");
    }
    
    @ApiOperation(value="运营管理业务采集季报", notes="运营管理业务采集季报")
    @ApiImplicitParam(name = "zxEqEquip", value = "设备台账entity", dataType = "ZxEqEquip")
    @RequireToken
    @PostMapping("/getZxEqEquipListForRunQuarterly")
    public Object getZxEqEquipListForRunQuarterly(@RequestBody(required = false) ZxEqEquip zxEqEquip) {
    	String userId = "";//TokenUtils.getUserId(request);
        JSONObject jsonObject = new JSONObject(zxEqEquip);
        Object object = ZxErpUtil.getApiContent(userId, "getZxEqEquipListForRunQuarterly", jsonObject);
        return object;
    }
    @ApiOperation(value="导出运营管理业务采集季报", notes="导出运营管理业务采集季报")
    @ApiImplicitParam(name = "zxEqEquip", value = "设备台账entity", dataType = "ZxEqEquip")
    @PostMapping("/ureportZxEqEquipListForRunQuarterly")
    public Object ureportZxEqEquipListForRunQuarterly(HttpServletRequest request,@RequestBody(required = false) ZxEqEquip zxEqEquip) {
        String userId = "";//TokenUtils.getUserId(request);
        JSONObject jsonObject = new JSONObject(zxEqEquip);
        Object resultObject = ZxErpUtil.getApiContent(userId, "ureportZxEqEquipListForRunQuarterly", jsonObject);
        JSONObject resultJSONObject = new JSONObject(resultObject);
        return resultJSONObject.getObj("data");
    }
    
}