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
import com.apih5.mybatis.pojo.ZjTzLifeCycleOpinion;
import com.apih5.service.ZjTzLifeCycleOpinionService;

@RestController
public class ZjTzLifeCycleOpinionController {

    @Autowired(required = true)
    private ZjTzLifeCycleOpinionService zjTzLifeCycleOpinionService;

    @ApiOperation(value="查询全生命周期策划评审意见", notes="查询全生命周期策划评审意见")
    @ApiImplicitParam(name = "zjTzLifeCycleOpinion", value = "全生命周期策划评审意见entity", dataType = "ZjTzLifeCycleOpinion")
    @RequireToken
    @PostMapping("/getZjTzLifeCycleOpinionList")
    public ResponseEntity getZjTzLifeCycleOpinionList(@RequestBody(required = false) ZjTzLifeCycleOpinion zjTzLifeCycleOpinion) {
        return zjTzLifeCycleOpinionService.getZjTzLifeCycleOpinionListByCondition(zjTzLifeCycleOpinion);
    }

    @ApiOperation(value="查询详情全生命周期策划评审意见", notes="查询详情全生命周期策划评审意见")
    @ApiImplicitParam(name = "zjTzLifeCycleOpinion", value = "全生命周期策划评审意见entity", dataType = "ZjTzLifeCycleOpinion")
    @RequireToken
    @PostMapping("/getZjTzLifeCycleOpinionDetails")
    public ResponseEntity getZjTzLifeCycleOpinionDetails(@RequestBody(required = false) ZjTzLifeCycleOpinion zjTzLifeCycleOpinion) {
        return zjTzLifeCycleOpinionService.getZjTzLifeCycleOpinionDetails(zjTzLifeCycleOpinion);
    }

    @ApiOperation(value="新增全生命周期策划评审意见", notes="新增全生命周期策划评审意见")
    @ApiImplicitParam(name = "zjTzLifeCycleOpinion", value = "全生命周期策划评审意见entity", dataType = "ZjTzLifeCycleOpinion")
    @RequireToken
    @PostMapping("/addZjTzLifeCycleOpinion")
    public ResponseEntity addZjTzLifeCycleOpinion(@RequestBody(required = false) ZjTzLifeCycleOpinion zjTzLifeCycleOpinion) {
        return zjTzLifeCycleOpinionService.saveZjTzLifeCycleOpinion(zjTzLifeCycleOpinion);
    }

    @ApiOperation(value="更新全生命周期策划评审意见", notes="更新全生命周期策划评审意见")
    @ApiImplicitParam(name = "zjTzLifeCycleOpinion", value = "全生命周期策划评审意见entity", dataType = "ZjTzLifeCycleOpinion")
    @RequireToken
    @PostMapping("/updateZjTzLifeCycleOpinion")
    public ResponseEntity updateZjTzLifeCycleOpinion(@RequestBody(required = false) ZjTzLifeCycleOpinion zjTzLifeCycleOpinion) {
        return zjTzLifeCycleOpinionService.updateZjTzLifeCycleOpinion(zjTzLifeCycleOpinion);
    }

    @ApiOperation(value="删除全生命周期策划评审意见", notes="删除全生命周期策划评审意见")
    @ApiImplicitParam(name = "zjTzLifeCycleOpinionList", value = "全生命周期策划评审意见List", dataType = "List<ZjTzLifeCycleOpinion>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZjTzLifeCycleOpinion")
    public ResponseEntity batchDeleteUpdateZjTzLifeCycleOpinion(@RequestBody(required = false) List<ZjTzLifeCycleOpinion> zjTzLifeCycleOpinionList) {
        return zjTzLifeCycleOpinionService.batchDeleteUpdateZjTzLifeCycleOpinion(zjTzLifeCycleOpinionList);
    }

}
