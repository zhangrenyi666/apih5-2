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
import com.apih5.mybatis.pojo.ZjLzehCountTangerTrouble;
import com.apih5.service.ZjLzehCountTangerTroubleService;

@RestController
public class ZjLzehCountTangerTroubleController {

    @Autowired(required = true)
    private ZjLzehCountTangerTroubleService zjLzehCountTangerTroubleService;

    @ApiOperation(value="查询质量安全统计", notes="查询质量安全统计")
    @ApiImplicitParam(name = "zjLzehCountTangerTrouble", value = "质量安全统计entity", dataType = "ZjLzehCountTangerTrouble")
    @RequireToken
    @PostMapping("/getZjLzehCountTangerTroubleList")
    public ResponseEntity getZjLzehCountTangerTroubleList(@RequestBody(required = false) ZjLzehCountTangerTrouble zjLzehCountTangerTrouble) {
        return zjLzehCountTangerTroubleService.getZjLzehCountTangerTroubleListByCondition(zjLzehCountTangerTrouble);
    }

    @ApiOperation(value="查询详情质量安全统计", notes="查询详情质量安全统计")
    @ApiImplicitParam(name = "zjLzehCountTangerTrouble", value = "质量安全统计entity", dataType = "ZjLzehCountTangerTrouble")
    @RequireToken
    @PostMapping("/getZjLzehCountTangerTroubleDetail")
    public ResponseEntity getZjLzehCountTangerTroubleDetail(@RequestBody(required = false) ZjLzehCountTangerTrouble zjLzehCountTangerTrouble) {
        return zjLzehCountTangerTroubleService.getZjLzehCountTangerTroubleDetail(zjLzehCountTangerTrouble);
    }

    @ApiOperation(value="新增质量安全统计", notes="新增质量安全统计")
    @ApiImplicitParam(name = "zjLzehCountTangerTrouble", value = "质量安全统计entity", dataType = "ZjLzehCountTangerTrouble")
    @RequireToken
    @PostMapping("/addZjLzehCountTangerTrouble")
    public ResponseEntity addZjLzehCountTangerTrouble(@RequestBody(required = false) ZjLzehCountTangerTrouble zjLzehCountTangerTrouble) {
        return zjLzehCountTangerTroubleService.saveZjLzehCountTangerTrouble(zjLzehCountTangerTrouble);
    }

    @ApiOperation(value="更新质量安全统计", notes="更新质量安全统计")
    @ApiImplicitParam(name = "zjLzehCountTangerTrouble", value = "质量安全统计entity", dataType = "ZjLzehCountTangerTrouble")
    @RequireToken
    @PostMapping("/updateZjLzehCountTangerTrouble")
    public ResponseEntity updateZjLzehCountTangerTrouble(@RequestBody(required = false) ZjLzehCountTangerTrouble zjLzehCountTangerTrouble) {
        return zjLzehCountTangerTroubleService.updateZjLzehCountTangerTrouble(zjLzehCountTangerTrouble);
    }

    @ApiOperation(value="删除质量安全统计", notes="删除质量安全统计")
    @ApiImplicitParam(name = "zjLzehCountTangerTroubleList", value = "质量安全统计List", dataType = "List<ZjLzehCountTangerTrouble>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZjLzehCountTangerTrouble")
    public ResponseEntity batchDeleteUpdateZjLzehCountTangerTrouble(@RequestBody(required = false) List<ZjLzehCountTangerTrouble> zjLzehCountTangerTroubleList) {
        return zjLzehCountTangerTroubleService.batchDeleteUpdateZjLzehCountTangerTrouble(zjLzehCountTangerTroubleList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    @ApiOperation(value="查询质量统计", notes="查询质量统计")
    @ApiImplicitParam(name = "zjLzehCountTangerTrouble", value = "质量安全统计entity", dataType = "ZjLzehCountTangerTrouble")
    @RequireToken
    @PostMapping("/getTroubleCountInfoList")
    public ResponseEntity getTroubleCountInfoList(@RequestBody(required = false) ZjLzehCountTangerTrouble zjLzehCountTangerTrouble) {
        return zjLzehCountTangerTroubleService.selectTroubleCountInfo(zjLzehCountTangerTrouble);
    }

    @ApiOperation(value="查询安全统计", notes="查询安全统计")
    @ApiImplicitParam(name = "zjLzehCountTangerTrouble", value = "质量安全统计entity", dataType = "ZjLzehCountTangerTrouble")
    @RequireToken
    @PostMapping("/getDangerCountInfoList")
    public ResponseEntity getDangerCountInfoList(@RequestBody(required = false) ZjLzehCountTangerTrouble zjLzehCountTangerTrouble) {
        return zjLzehCountTangerTroubleService.selectDangerCountInfo(zjLzehCountTangerTrouble);
    }

}
