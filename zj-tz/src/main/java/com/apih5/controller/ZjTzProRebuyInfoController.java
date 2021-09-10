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
import com.apih5.mybatis.pojo.ZjTzProRebuyInfo;
import com.apih5.service.ZjTzProRebuyInfoService;

@RestController
public class ZjTzProRebuyInfoController {

    @Autowired(required = true)
    private ZjTzProRebuyInfoService zjTzProRebuyInfoService;

    @ApiOperation(value="查询回购明细", notes="查询回购明细")
    @ApiImplicitParam(name = "zjTzProRebuyInfo", value = "回购明细entity", dataType = "ZjTzProRebuyInfo")
    @RequireToken
    @PostMapping("/getZjTzProRebuyInfoList")
    public ResponseEntity getZjTzProRebuyInfoList(@RequestBody(required = false) ZjTzProRebuyInfo zjTzProRebuyInfo) {
        return zjTzProRebuyInfoService.getZjTzProRebuyInfoListByCondition(zjTzProRebuyInfo);
    }

    @ApiOperation(value="查询详情回购明细", notes="查询详情回购明细")
    @ApiImplicitParam(name = "zjTzProRebuyInfo", value = "回购明细entity", dataType = "ZjTzProRebuyInfo")
    @RequireToken
    @PostMapping("/getZjTzProRebuyInfoDetails")
    public ResponseEntity getZjTzProRebuyInfoDetails(@RequestBody(required = false) ZjTzProRebuyInfo zjTzProRebuyInfo) {
        return zjTzProRebuyInfoService.getZjTzProRebuyInfoDetails(zjTzProRebuyInfo);
    }

    @ApiOperation(value="新增回购明细", notes="新增回购明细")
    @ApiImplicitParam(name = "zjTzProRebuyInfo", value = "回购明细entity", dataType = "ZjTzProRebuyInfo")
    @RequireToken
    @PostMapping("/addZjTzProRebuyInfo")
    public ResponseEntity addZjTzProRebuyInfo(@RequestBody(required = false) ZjTzProRebuyInfo zjTzProRebuyInfo) {
        return zjTzProRebuyInfoService.saveZjTzProRebuyInfo(zjTzProRebuyInfo);
    }

    @ApiOperation(value="更新回购明细", notes="更新回购明细")
    @ApiImplicitParam(name = "zjTzProRebuyInfo", value = "回购明细entity", dataType = "ZjTzProRebuyInfo")
    @RequireToken
    @PostMapping("/updateZjTzProRebuyInfo")
    public ResponseEntity updateZjTzProRebuyInfo(@RequestBody(required = false) ZjTzProRebuyInfo zjTzProRebuyInfo) {
        return zjTzProRebuyInfoService.updateZjTzProRebuyInfo(zjTzProRebuyInfo);
    }

    @ApiOperation(value="删除回购明细", notes="删除回购明细")
    @ApiImplicitParam(name = "zjTzProRebuyInfoList", value = "回购明细List", dataType = "List<ZjTzProRebuyInfo>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZjTzProRebuyInfo")
    public ResponseEntity batchDeleteUpdateZjTzProRebuyInfo(@RequestBody(required = false) List<ZjTzProRebuyInfo> zjTzProRebuyInfoList) {
        return zjTzProRebuyInfoService.batchDeleteUpdateZjTzProRebuyInfo(zjTzProRebuyInfoList);
    }

}
