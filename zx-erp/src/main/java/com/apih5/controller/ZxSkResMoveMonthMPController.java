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
import com.apih5.mybatis.pojo.ZxSkResMoveMonthMP;
import com.apih5.service.ZxSkResMoveMonthMPService;

import javax.servlet.http.HttpServletRequest;

@RestController
public class ZxSkResMoveMonthMPController {

    @Autowired(required = true)
    private ZxSkResMoveMonthMPService zxSkResMoveMonthMPService;

    @ApiOperation(value="查询物资收发存月报表", notes="查询物资收发存月报表")
    @ApiImplicitParam(name = "zxSkResMoveMonthMP", value = "物资收发存月报表entity", dataType = "ZxSkResMoveMonthMP")
    @RequireToken
    @PostMapping("/getZxSkResMoveMonthMPList")
    public ResponseEntity getZxSkResMoveMonthMPList(@RequestBody(required = false) ZxSkResMoveMonthMP zxSkResMoveMonthMP) {
        return zxSkResMoveMonthMPService.getZxSkResMoveMonthMPListByCondition(zxSkResMoveMonthMP);
    }

    @ApiOperation(value="查询详情物资收发存月报表", notes="查询详情物资收发存月报表")
    @ApiImplicitParam(name = "zxSkResMoveMonthMP", value = "物资收发存月报表entity", dataType = "ZxSkResMoveMonthMP")
    @RequireToken
    @PostMapping("/getZxSkResMoveMonthMPDetail")
    public ResponseEntity getZxSkResMoveMonthMPDetail(@RequestBody(required = false) ZxSkResMoveMonthMP zxSkResMoveMonthMP) {
        return zxSkResMoveMonthMPService.getZxSkResMoveMonthMPDetail(zxSkResMoveMonthMP);
    }

    @ApiOperation(value="新增物资收发存月报表", notes="新增物资收发存月报表")
    @ApiImplicitParam(name = "zxSkResMoveMonthMP", value = "物资收发存月报表entity", dataType = "ZxSkResMoveMonthMP")
    @RequireToken
    @PostMapping("/addZxSkResMoveMonthMP")
    public ResponseEntity addZxSkResMoveMonthMP(@RequestBody(required = false) ZxSkResMoveMonthMP zxSkResMoveMonthMP) {
        return zxSkResMoveMonthMPService.saveZxSkResMoveMonthMP(zxSkResMoveMonthMP);
    }

    @ApiOperation(value="更新物资收发存月报表", notes="更新物资收发存月报表")
    @ApiImplicitParam(name = "zxSkResMoveMonthMP", value = "物资收发存月报表entity", dataType = "ZxSkResMoveMonthMP")
    @RequireToken
    @PostMapping("/updateZxSkResMoveMonthMP")
    public ResponseEntity updateZxSkResMoveMonthMP(@RequestBody(required = false) ZxSkResMoveMonthMP zxSkResMoveMonthMP) {
        return zxSkResMoveMonthMPService.updateZxSkResMoveMonthMP(zxSkResMoveMonthMP);
    }

    @ApiOperation(value="删除物资收发存月报表", notes="删除物资收发存月报表")
    @ApiImplicitParam(name = "zxSkResMoveMonthMPList", value = "物资收发存月报表List", dataType = "List<ZxSkResMoveMonthMP>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxSkResMoveMonthMP")
    public ResponseEntity batchDeleteUpdateZxSkResMoveMonthMP(@RequestBody(required = false) List<ZxSkResMoveMonthMP> zxSkResMoveMonthMPList) {
        return zxSkResMoveMonthMPService.batchDeleteUpdateZxSkResMoveMonthMP(zxSkResMoveMonthMPList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

    @ApiOperation(value="导出物资收发存月报表", notes="物资收发存月报表")
    @ApiImplicitParam(name = "zxSkResMoveMonthMP", value = "物资收发存月报表entity", dataType = "ZxSkResMoveMonthMP")
//    @RequireToken
    @PostMapping("/exportZxSkResMoveMonthMPListByPage")
    public Object exportZxSkResMoveMonthMPListByPage(HttpServletRequest requZxSkResMoveMonthMPest,
                                                   @RequestBody(required = false) ZxSkResMoveMonthMP zxSkResMoveMonthMPList) {
        String userId = "";//TokenUtils.getUserId(request);
        JSONObject jsonObject = new JSONObject(zxSkResMoveMonthMPList);
        return ZxErpUtil.getApiContent(userId, "exportZxSkResMoveMonthMPList", jsonObject);
    }


    @ApiOperation(value="导出物资收发存月报表", notes="物资收发存月报表")
    @ApiImplicitParam(name = "ZxSkResMoveMonthMP", value = "物资收发存月报表entity", dataType = "ZxSkResMoveMonthMP")
//    @RequireToken
    @PostMapping("/exportZxSkResMoveMonthMPList")
    public Object exportZxSkResMoveMonthMPList(HttpServletRequest request,
                                                 @RequestBody(required = false)  ZxSkResMoveMonthMP zxSkResMoveMonthMPList) {
        String userId = "";//TokenUtils.getUserId(request);
        JSONObject jsonObject = new JSONObject(zxSkResMoveMonthMPList);
        Object resultObject = ZxErpUtil.getApiContent(userId, "exportZxSkResMoveMonthMPList", jsonObject);
        JSONObject resultJSONObject = new JSONObject(resultObject);
        return resultJSONObject.getObj("data");
    }
}
