package com.apih5.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import com.apih5.mybatis.pojo.ZxSkStockDifMonthItem;
import com.apih5.mybatis.pojo.ZxSkStockTransferInitialReceipt;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import com.apih5.framework.annotation.RequireToken;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.mybatis.pojo.ZxSkReceivingDynamic;
import com.apih5.service.ZxSkReceivingDynamicService;
import com.apih5.utils.ZxErpUtil;

@RestController
public class ZxSkReceivingDynamicController {

    @Autowired(required = true)
    private ZxSkReceivingDynamicService zxSkReceivingDynamicService;

    @ApiOperation(value="查询物资动态帐", notes="查询物资动态帐")
    @ApiImplicitParam(name = "zxSkReceivingDynamic", value = "物资动态帐entity", dataType = "ZxSkReceivingDynamic")
    @RequireToken
    @PostMapping("/getZxSkReceivingDynamicList")
    public ResponseEntity getZxSkReceivingDynamicList(@RequestBody(required = false) ZxSkReceivingDynamic zxSkReceivingDynamic) {
        return zxSkReceivingDynamicService.getZxSkReceivingDynamicListByCondition(zxSkReceivingDynamic);
    }

    @ApiOperation(value="查询详情物资动态帐", notes="查询详情物资动态帐")
    @ApiImplicitParam(name = "zxSkReceivingDynamic", value = "物资动态帐entity", dataType = "ZxSkReceivingDynamic")
    @RequireToken
    @PostMapping("/getZxSkReceivingDynamicDetail")
    public ResponseEntity getZxSkReceivingDynamicDetail(@RequestBody(required = false) ZxSkReceivingDynamic zxSkReceivingDynamic) {
        return zxSkReceivingDynamicService.getZxSkReceivingDynamicDetail(zxSkReceivingDynamic);
    }

    @ApiOperation(value="新增物资动态帐", notes="新增物资动态帐")
    @ApiImplicitParam(name = "zxSkReceivingDynamic", value = "物资动态帐entity", dataType = "ZxSkReceivingDynamic")
    @RequireToken
    @PostMapping("/addZxSkReceivingDynamic")
    public ResponseEntity addZxSkReceivingDynamic(@RequestBody(required = false) ZxSkReceivingDynamic zxSkReceivingDynamic) {
        return zxSkReceivingDynamicService.saveZxSkReceivingDynamic(zxSkReceivingDynamic);
    }

    @ApiOperation(value="更新物资动态帐", notes="更新物资动态帐")
    @ApiImplicitParam(name = "zxSkReceivingDynamic", value = "物资动态帐entity", dataType = "ZxSkReceivingDynamic")
    @RequireToken
    @PostMapping("/updateZxSkReceivingDynamic")
    public ResponseEntity updateZxSkReceivingDynamic(@RequestBody(required = false) ZxSkReceivingDynamic zxSkReceivingDynamic) {
        return zxSkReceivingDynamicService.updateZxSkReceivingDynamic(zxSkReceivingDynamic);
    }

    @ApiOperation(value="删除物资动态帐", notes="删除物资动态帐")
    @ApiImplicitParam(name = "zxSkReceivingDynamicList", value = "物资动态帐List", dataType = "List<ZxSkReceivingDynamic>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxSkReceivingDynamic")
    public ResponseEntity batchDeleteUpdateZxSkReceivingDynamic(@RequestBody(required = false) List<ZxSkReceivingDynamic> zxSkReceivingDynamicList) {
        return zxSkReceivingDynamicService.batchDeleteUpdateZxSkReceivingDynamic(zxSkReceivingDynamicList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

    @ApiOperation(value="导出物资动态帐报表", notes="物资动态帐")
    @ApiImplicitParam(name = "zxSkStockDifMonthItem", value = "物资动态帐", dataType = "ZxSkStockDifMonthItem")
//    @RequireToken
    @PostMapping("/exportZxSkReceivingDynamicListByPage")
    public Object exportZxSkReceivingDynamicListByPage(HttpServletRequest request,
            @RequestBody(required = false) ZxSkReceivingDynamic zxSkReceivingDynamic) {
        String userId = "";//TokenUtils.getUserId(request);
        JSONObject jsonObject = new JSONObject(zxSkReceivingDynamic);
        Object object = ZxErpUtil.getApiContent(userId, "exportZxSkReceivingDynamicList", jsonObject);
        return object;
    }

    
    @ApiOperation(value="导出物资动态帐报表", notes="物资动态帐")
    @ApiImplicitParam(name = "zxSkStockDifMonthItem", value = "物资动态帐", dataType = "ZxSkStockDifMonthItem")
//    @RequireToken
    @PostMapping("/exportZxSkReceivingDynamicList")
    public Object exportZxSkReceivingDynamicList(HttpServletRequest request,
            @RequestBody(required = false) ZxSkReceivingDynamic zxSkReceivingDynamic) {
        String userId = "";//TokenUtils.getUserId(request);
        JSONObject jsonObject = new JSONObject(zxSkReceivingDynamic);
        Object resultObject = ZxErpUtil.getApiContent(userId, "exportZxSkReceivingDynamicList", jsonObject);
        JSONObject resultJSONObject = new JSONObject(resultObject);
        return resultJSONObject.getObj("data");
    }



}
