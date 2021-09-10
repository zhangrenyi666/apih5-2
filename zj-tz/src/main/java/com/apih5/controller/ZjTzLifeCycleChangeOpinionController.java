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
import com.apih5.mybatis.pojo.ZjTzLifeCycleChangeOpinion;
import com.apih5.service.ZjTzLifeCycleChangeOpinionService;

@RestController
public class ZjTzLifeCycleChangeOpinionController {

    @Autowired(required = true)
    private ZjTzLifeCycleChangeOpinionService zjTzLifeCycleChangeOpinionService;

    @ApiOperation(value="查询全生命周期策划变更评审意见", notes="查询全生命周期策划变更评审意见")
    @ApiImplicitParam(name = "zjTzLifeCycleChangeOpinion", value = "全生命周期策划变更评审意见entity", dataType = "ZjTzLifeCycleChangeOpinion")
    @RequireToken
    @PostMapping("/getZjTzLifeCycleChangeOpinionList")
    public ResponseEntity getZjTzLifeCycleChangeOpinionList(@RequestBody(required = false) ZjTzLifeCycleChangeOpinion zjTzLifeCycleChangeOpinion) {
        return zjTzLifeCycleChangeOpinionService.getZjTzLifeCycleChangeOpinionListByCondition(zjTzLifeCycleChangeOpinion);
    }

    @ApiOperation(value="查询详情全生命周期策划变更评审意见", notes="查询详情全生命周期策划变更评审意见")
    @ApiImplicitParam(name = "zjTzLifeCycleChangeOpinion", value = "全生命周期策划变更评审意见entity", dataType = "ZjTzLifeCycleChangeOpinion")
    @RequireToken
    @PostMapping("/getZjTzLifeCycleChangeOpinionDetails")
    public ResponseEntity getZjTzLifeCycleChangeOpinionDetails(@RequestBody(required = false) ZjTzLifeCycleChangeOpinion zjTzLifeCycleChangeOpinion) {
        return zjTzLifeCycleChangeOpinionService.getZjTzLifeCycleChangeOpinionDetails(zjTzLifeCycleChangeOpinion);
    }

    @ApiOperation(value="新增全生命周期策划变更评审意见", notes="新增全生命周期策划变更评审意见")
    @ApiImplicitParam(name = "zjTzLifeCycleChangeOpinion", value = "全生命周期策划变更评审意见entity", dataType = "ZjTzLifeCycleChangeOpinion")
    @RequireToken
    @PostMapping("/addZjTzLifeCycleChangeOpinion")
    public ResponseEntity addZjTzLifeCycleChangeOpinion(@RequestBody(required = false) ZjTzLifeCycleChangeOpinion zjTzLifeCycleChangeOpinion) {
        return zjTzLifeCycleChangeOpinionService.saveZjTzLifeCycleChangeOpinion(zjTzLifeCycleChangeOpinion);
    }

    @ApiOperation(value="更新全生命周期策划变更评审意见", notes="更新全生命周期策划变更评审意见")
    @ApiImplicitParam(name = "zjTzLifeCycleChangeOpinion", value = "全生命周期策划变更评审意见entity", dataType = "ZjTzLifeCycleChangeOpinion")
    @RequireToken
    @PostMapping("/updateZjTzLifeCycleChangeOpinion")
    public ResponseEntity updateZjTzLifeCycleChangeOpinion(@RequestBody(required = false) ZjTzLifeCycleChangeOpinion zjTzLifeCycleChangeOpinion) {
        return zjTzLifeCycleChangeOpinionService.updateZjTzLifeCycleChangeOpinion(zjTzLifeCycleChangeOpinion);
    }

    @ApiOperation(value="删除全生命周期策划变更评审意见", notes="删除全生命周期策划变更评审意见")
    @ApiImplicitParam(name = "zjTzLifeCycleChangeOpinionList", value = "全生命周期策划变更评审意见List", dataType = "List<ZjTzLifeCycleChangeOpinion>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZjTzLifeCycleChangeOpinion")
    public ResponseEntity batchDeleteUpdateZjTzLifeCycleChangeOpinion(@RequestBody(required = false) List<ZjTzLifeCycleChangeOpinion> zjTzLifeCycleChangeOpinionList) {
        return zjTzLifeCycleChangeOpinionService.batchDeleteUpdateZjTzLifeCycleChangeOpinion(zjTzLifeCycleChangeOpinionList);
    }
    
    @ApiOperation(value="全生命周期策划查询全生命周期策划变更详情list", notes="全生命周期策划查询全生命周期策划变更详情list")
    @ApiImplicitParam(name = "zjTzLifeCycleChangeOpinion", value = "全生命周期策划变更评审意见entity", dataType = "ZjTzLifeCycleChangeOpinion")
    @RequireToken
    @PostMapping("/getZjTzLifeCycleChangeOpinionAndProIdList")
    public ResponseEntity getZjTzLifeCycleChangeOpinionAndProIdList(@RequestBody(required = false) ZjTzLifeCycleChangeOpinion zjTzLifeCycleChangeOpinion) {
        return zjTzLifeCycleChangeOpinionService.getZjTzLifeCycleChangeOpinionAndProIdList(zjTzLifeCycleChangeOpinion);
    }

}
