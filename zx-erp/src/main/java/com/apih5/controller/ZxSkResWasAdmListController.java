package com.apih5.controller;

import java.util.List;

import cn.hutool.json.JSONObject;
import com.apih5.mybatis.pojo.ZxSkReceivingDynamic;
import com.apih5.utils.ZxErpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import com.apih5.framework.annotation.RequireToken;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxSkResWasAdmList;
import com.apih5.service.ZxSkResWasAdmListService;

import javax.servlet.http.HttpServletRequest;

@RestController
public class ZxSkResWasAdmListController {

    @Autowired(required = true)
    private ZxSkResWasAdmListService zxSkResWasAdmListService;

    @ApiOperation(value="查询物资耗用分配表", notes="查询物资耗用分配表")
    @ApiImplicitParam(name = "zxSkResWasAdmList", value = "物资耗用分配表entity", dataType = "ZxSkResWasAdmList")
    @RequireToken
    @PostMapping("/getZxSkResWasAdmListList")
    public ResponseEntity getZxSkResWasAdmListList(@RequestBody(required = false) ZxSkResWasAdmList zxSkResWasAdmList) {
        return zxSkResWasAdmListService.getZxSkResWasAdmListListByCondition(zxSkResWasAdmList);
    }

    @ApiOperation(value="查询详情物资耗用分配表", notes="查询详情物资耗用分配表")
    @ApiImplicitParam(name = "zxSkResWasAdmList", value = "物资耗用分配表entity", dataType = "ZxSkResWasAdmList")
    @RequireToken
    @PostMapping("/getZxSkResWasAdmListDetail")
    public ResponseEntity getZxSkResWasAdmListDetail(@RequestBody(required = false) ZxSkResWasAdmList zxSkResWasAdmList) {
        return zxSkResWasAdmListService.getZxSkResWasAdmListDetail(zxSkResWasAdmList);
    }

    @ApiOperation(value="新增物资耗用分配表", notes="新增物资耗用分配表")
    @ApiImplicitParam(name = "zxSkResWasAdmList", value = "物资耗用分配表entity", dataType = "ZxSkResWasAdmList")
    @RequireToken
    @PostMapping("/addZxSkResWasAdmList")
    public ResponseEntity addZxSkResWasAdmList(@RequestBody(required = false) ZxSkResWasAdmList zxSkResWasAdmList) {
        return zxSkResWasAdmListService.saveZxSkResWasAdmList(zxSkResWasAdmList);
    }

    @ApiOperation(value="更新物资耗用分配表", notes="更新物资耗用分配表")
    @ApiImplicitParam(name = "zxSkResWasAdmList", value = "物资耗用分配表entity", dataType = "ZxSkResWasAdmList")
    @RequireToken
    @PostMapping("/updateZxSkResWasAdmList")
    public ResponseEntity updateZxSkResWasAdmList(@RequestBody(required = false) ZxSkResWasAdmList zxSkResWasAdmList) {
        return zxSkResWasAdmListService.updateZxSkResWasAdmList(zxSkResWasAdmList);
    }

    @ApiOperation(value="删除物资耗用分配表", notes="删除物资耗用分配表")
    @ApiImplicitParam(name = "zxSkResWasAdmListList", value = "物资耗用分配表List", dataType = "List<ZxSkResWasAdmList>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxSkResWasAdmList")
    public ResponseEntity batchDeleteUpdateZxSkResWasAdmList(@RequestBody(required = false) List<ZxSkResWasAdmList> zxSkResWasAdmListList) {
        return zxSkResWasAdmListService.batchDeleteUpdateZxSkResWasAdmList(zxSkResWasAdmListList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

    @ApiOperation(value="导出物资耗用分配表", notes="物资耗用分配表")
    @ApiImplicitParam(name = "zxSkResWasAdmListList", value = "物资耗用分配表", dataType = "ZxSkResWasAdmList")
//    @RequireToken
    @PostMapping("/exportZxSkResWasAdmListByPage")
    public Object exportZxSkResWasAdmListByPage(HttpServletRequest request,
                                                       @RequestBody(required = false) ZxSkResWasAdmList zxSkResWasAdmList) {
        String userId = "";//TokenUtils.getUserId(request);
        JSONObject jsonObject = new JSONObject(zxSkResWasAdmList);
        return ZxErpUtil.getApiContent(userId, "exportZxSkResWasAdmList", jsonObject);
    }


    @ApiOperation(value="导出物资耗用分配表", notes="物资耗用分配表")
    @ApiImplicitParam(name = "zxSkResWasAdmListList", value = "物资耗用分配表", dataType = "ZxSkResWasAdmList")
//    @RequireToken
    @PostMapping("/exportZxSkResWasAdmList")
    public Object exportZxSkReceivingDynamicList(HttpServletRequest request,
                                                 @RequestBody(required = false) ZxSkResWasAdmList zxSkResWasAdmList) {
        String userId = "";//TokenUtils.getUserId(request);
        JSONObject jsonObject = new JSONObject(zxSkResWasAdmList);
        Object resultObject = ZxErpUtil.getApiContent(userId, "exportZxSkResWasAdmList", jsonObject);
        JSONObject resultJSONObject = new JSONObject(resultObject);
        return resultJSONObject.getObj("data");
    }

    @ApiOperation(value="导出物资耗用分配表", notes="物资耗用分配表")
    @ApiImplicitParam(name = "zxSkResWasAdmListList", value = "物资耗用分配表", dataType = "ZxSkResWasAdmList")
//    @RequireToken
    @PostMapping("/exportZxSkResWasAdmListGetCbs")
    public Object exportZxSkResWasAdmListGetCbs(HttpServletRequest request,
                                                 @RequestBody(required = false) ZxSkResWasAdmList zxSkResWasAdmList) {
        String userId = "";//TokenUtils.getUserId(request);
        JSONObject jsonObject = new JSONObject(zxSkResWasAdmList);
        Object resultObject = ZxErpUtil.getApiContent(userId, "exportZxSkResWasAdmListGetCbs", jsonObject);
        JSONObject resultJSONObject = new JSONObject(resultObject);
        return resultJSONObject.getObj("data");
    }


    @ApiOperation(value="导出物资耗用分配表", notes="物资耗用分配表")
    @ApiImplicitParam(name = "zxSkResWasAdmListList", value = "物资耗用分配表", dataType = "ZxSkResWasAdmList")
//    @RequireToken
    @PostMapping("/exportZxSkResWasAdmListGetCbsByPage")
    public Object exportZxSkResWasAdmListGetCbsByPage(HttpServletRequest request,
                                                @RequestBody(required = false) ZxSkResWasAdmList zxSkResWasAdmList) {
        String userId = "";//TokenUtils.getUserId(request);
        JSONObject jsonObject = new JSONObject(zxSkResWasAdmList);
        return ZxErpUtil.getApiContent(userId, "exportZxSkResWasAdmListGetTitle", jsonObject);
    }


}
