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
import com.apih5.mybatis.pojo.ZxEqIecsCBS;
import com.apih5.service.ZxEqIecsCBSService;

@RestController
public class ZxEqIecsCBSController {

    @Autowired(required = true)
    private ZxEqIecsCBSService zxEqIecsCBSService;

    @ApiOperation(value="查询分部分项和内部单位", notes="查询分部分项和内部单位")
    @ApiImplicitParam(name = "zxEqIecsCBSList", value = "分部分项和内部单位entity", dataType = "List<ZxEqIecsCBS>")
    @RequireToken
    @PostMapping("/getZxEqIecsCBSList")
    public ResponseEntity getZxEqIecsCBSList(@RequestBody(required = false) ZxEqIecsCBS zxEqIecsCBS) {
        return zxEqIecsCBSService.getZxEqIecsCBSListByCondition(zxEqIecsCBS);
    }

    @ApiOperation(value="查询详情分部分项和内部单位", notes="查询详情分部分项和内部单位")
    @ApiImplicitParam(name = "zxEqIecsCBS", value = "分部分项和内部单位entity", dataType = "ZxEqIecsCBS")
    @RequireToken
    @PostMapping("/getZxEqIecsCBSDetails")
    public ResponseEntity getZxEqIecsCBSDetails(@RequestBody(required = false) ZxEqIecsCBS zxEqIecsCBS) {
        return zxEqIecsCBSService.getZxEqIecsCBSDetails(zxEqIecsCBS);
    }

    @ApiOperation(value="新增分部分项和内部单位", notes="新增分部分项和内部单位")
    @ApiImplicitParam(name = "zxEqIecsCBS", value = "分部分项和内部单位entity", dataType = "ZxEqIecsCBS")
    @RequireToken
    @PostMapping("/addZxEqIecsCBS")
    public ResponseEntity addZxEqIecsCBS(@RequestBody(required = false) ZxEqIecsCBS zxEqIecsCBS) {
        return zxEqIecsCBSService.saveZxEqIecsCBS(zxEqIecsCBS);
    }

    @ApiOperation(value="更新分部分项和内部单位", notes="更新分部分项和内部单位")
    @ApiImplicitParam(name = "zxEqIecsCBS", value = "分部分项和内部单位entity", dataType = "ZxEqIecsCBS")
    @RequireToken
    @PostMapping("/updateZxEqIecsCBS")
    public ResponseEntity updateZxEqIecsCBS(@RequestBody(required = false) ZxEqIecsCBS zxEqIecsCBS) {
        return zxEqIecsCBSService.updateZxEqIecsCBS(zxEqIecsCBS);
    }

    @ApiOperation(value="删除分部分项和内部单位", notes="删除分部分项和内部单位")
    @ApiImplicitParam(name = "zxEqIecsCBSList", value = "分部分项和内部单位List", dataType = "List<ZxEqIecsCBS>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxEqIecsCBS")
    public ResponseEntity batchDeleteUpdateZxEqIecsCBS(@RequestBody(required = false) List<ZxEqIecsCBS> zxEqIecsCBSList) {
        return zxEqIecsCBSService.batchDeleteUpdateZxEqIecsCBS(zxEqIecsCBSList);
    }

    //orgId
    @ApiOperation(value="根据orgId查询分项", notes="根据orgId查询分项")
    @ApiImplicitParam(name = "zxEqIecsCBSList", value = "分部分项List", dataType = "List<ZxEqIecsCBS>")
    @RequireToken
    @PostMapping("/getZxEqIecsCBSOrgId")
    public ResponseEntity getZxEqIecsCBSOrgId(@RequestBody(required = false) ZxEqIecsCBS zxEqIecsCBS) {
        return zxEqIecsCBSService.getZxEqIecsCBSOrgId(zxEqIecsCBS);
    }


    @ApiOperation(value="查询分部分项树形接口", notes="查询分部分项树形接口")
    @ApiImplicitParam(name = "ZxEqIecsCBS", value = "查询分部分项树形接口List", dataType = "ZxEqIecsCBS")
    @RequireToken
    @PostMapping("/getZxEqIecsCBSTree")
    public ResponseEntity getZxEqIecsCBSTree(@RequestBody(required = false) ZxEqIecsCBS zxEqIecsCBS) {
        return zxEqIecsCBSService.getZxEqIecsCBSTree(zxEqIecsCBS);
    }


    @ApiOperation(value="查询内部单位和领料单位", notes="查询内部单位和领料单位")
    @ApiImplicitParam(name = "zxEqIecsCBSList", value = "分部分项和内部单位entity", dataType = "List<ZxEqIecsCBS>")
    @RequireToken
    @PostMapping("/getZxEqIecsCBSPickingList")
    public ResponseEntity getZxEqIecsCBSPickingList(@RequestBody(required = false) ZxEqIecsCBS zxEqIecsCBS) {
        return zxEqIecsCBSService.getZxEqIecsCBSPickingList(zxEqIecsCBS);
    }

}
