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
import com.apih5.mybatis.pojo.ZjLzehTeamScore;
import com.apih5.service.ZjLzehTeamScoreService;

@RestController
public class ZjLzehTeamScoreController {

    @Autowired(required = true)
    private ZjLzehTeamScoreService zjLzehTeamScoreService;

    @ApiOperation(value="查询班组评分管理", notes="查询班组评分管理")
    @ApiImplicitParam(name = "zjLzehTeamScore", value = "班组评分管理entity", dataType = "ZjLzehTeamScore")
    @RequireToken
    @PostMapping("/getZjLzehTeamScoreList")
    public ResponseEntity getZjLzehTeamScoreList(@RequestBody(required = false) ZjLzehTeamScore zjLzehTeamScore) {
        return zjLzehTeamScoreService.getZjLzehTeamScoreListByCondition(zjLzehTeamScore);
    }

    @ApiOperation(value="查询详情班组评分管理", notes="查询详情班组评分管理")
    @ApiImplicitParam(name = "zjLzehTeamScore", value = "班组评分管理entity", dataType = "ZjLzehTeamScore")
    @RequireToken
    @PostMapping("/getZjLzehTeamScoreDetail")
    public ResponseEntity getZjLzehTeamScoreDetail(@RequestBody(required = false) ZjLzehTeamScore zjLzehTeamScore) {
        return zjLzehTeamScoreService.getZjLzehTeamScoreDetail(zjLzehTeamScore);
    }

    @ApiOperation(value="新增班组评分管理", notes="新增班组评分管理")
    @ApiImplicitParam(name = "zjLzehTeamScore", value = "班组评分管理entity", dataType = "ZjLzehTeamScore")
    @RequireToken
    @PostMapping("/addZjLzehTeamScore")
    public ResponseEntity addZjLzehTeamScore(@RequestBody(required = false) ZjLzehTeamScore zjLzehTeamScore) {
        return zjLzehTeamScoreService.saveZjLzehTeamScore(zjLzehTeamScore);
    }

    @ApiOperation(value="更新班组评分管理", notes="更新班组评分管理")
    @ApiImplicitParam(name = "zjLzehTeamScore", value = "班组评分管理entity", dataType = "ZjLzehTeamScore")
    @RequireToken
    @PostMapping("/updateZjLzehTeamScore")
    public ResponseEntity updateZjLzehTeamScore(@RequestBody(required = false) ZjLzehTeamScore zjLzehTeamScore) {
        return zjLzehTeamScoreService.updateZjLzehTeamScore(zjLzehTeamScore);
    }

    @ApiOperation(value="删除班组评分管理", notes="删除班组评分管理")
    @ApiImplicitParam(name = "zjLzehTeamScoreList", value = "班组评分管理List", dataType = "List<ZjLzehTeamScore>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZjLzehTeamScore")
    public ResponseEntity batchDeleteUpdateZjLzehTeamScore(@RequestBody(required = false) List<ZjLzehTeamScore> zjLzehTeamScoreList) {
        return zjLzehTeamScoreService.batchDeleteUpdateZjLzehTeamScore(zjLzehTeamScoreList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
