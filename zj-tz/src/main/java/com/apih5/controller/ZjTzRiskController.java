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
import com.apih5.mybatis.pojo.ZjTzRisk;
import com.apih5.service.ZjTzRiskService;

@RestController
public class ZjTzRiskController {

    @Autowired(required = true)
    private ZjTzRiskService zjTzRiskService;

    @ApiOperation(value="查询风险管理", notes="查询风险管理")
    @ApiImplicitParam(name = "zjTzRisk", value = "风险管理entity", dataType = "ZjTzRisk")
    @RequireToken
    @PostMapping("/getZjTzRiskList")
    public ResponseEntity getZjTzRiskList(@RequestBody(required = false) ZjTzRisk zjTzRisk) {
        return zjTzRiskService.getZjTzRiskListByCondition(zjTzRisk);
    }

    @ApiOperation(value="查询详情风险管理", notes="查询详情风险管理")
    @ApiImplicitParam(name = "zjTzRisk", value = "风险管理entity", dataType = "ZjTzRisk")
    @RequireToken
    @PostMapping("/getZjTzRiskDetails")
    public ResponseEntity getZjTzRiskDetails(@RequestBody(required = false) ZjTzRisk zjTzRisk) {
        return zjTzRiskService.getZjTzRiskDetails(zjTzRisk);
    }

    @ApiOperation(value="新增风险管理", notes="新增风险管理")
    @ApiImplicitParam(name = "zjTzRisk", value = "风险管理entity", dataType = "ZjTzRisk")
    @RequireToken
    @PostMapping("/addZjTzRisk")
    public ResponseEntity addZjTzRisk(@RequestBody(required = false) ZjTzRisk zjTzRisk) {
        return zjTzRiskService.saveZjTzRisk(zjTzRisk);
    }

    @ApiOperation(value="更新风险管理", notes="更新风险管理")
    @ApiImplicitParam(name = "zjTzRisk", value = "风险管理entity", dataType = "ZjTzRisk")
    @RequireToken
    @PostMapping("/updateZjTzRisk")
    public ResponseEntity updateZjTzRisk(@RequestBody(required = false) ZjTzRisk zjTzRisk) {
        return zjTzRiskService.updateZjTzRisk(zjTzRisk);
    }

    @ApiOperation(value="删除风险管理", notes="删除风险管理")
    @ApiImplicitParam(name = "zjTzRiskList", value = "风险管理List", dataType = "List<ZjTzRisk>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZjTzRisk")
    public ResponseEntity batchDeleteUpdateZjTzRisk(@RequestBody(required = false) List<ZjTzRisk> zjTzRiskList) {
        return zjTzRiskService.batchDeleteUpdateZjTzRisk(zjTzRiskList);
    }
    
    @ApiOperation(value="批量上报风险管理", notes="批量上报风险管理")
    @ApiImplicitParam(name = "zjTzRiskList", value = "风险管理List", dataType = "List<ZjTzRisk>")
    @RequireToken
    @PostMapping("/batchReleaseZjTzRisk")
    public ResponseEntity batchReleaseZjTzRisk(@RequestBody(required = false) List<ZjTzRisk> zjTzRiskList) {
        return zjTzRiskService.batchReleaseZjTzRisk(zjTzRiskList);
    }
    
    @ApiOperation(value="批量撤回风险管理", notes="批量撤回风险管理")
    @ApiImplicitParam(name = "zjTzRiskList", value = "风险管理List", dataType = "List<ZjTzRisk>")
    @RequireToken
    @PostMapping("/batchRecallZjTzRisk")
    public ResponseEntity batchRecallZjTzRisk(@RequestBody(required = false) List<ZjTzRisk> zjTzRiskList) {
        return zjTzRiskService.batchRecallZjTzRisk(zjTzRiskList);
    }
    
    @ApiOperation(value="保存风险管理所有清单的接口", notes="保存风险管理所有清单的接口")
    @ApiImplicitParam(name = "zjTzRisk", value = "风险管理entity", dataType = "ZjTzRisk")
    @RequireToken
    @PostMapping("/saveZjTzRiskAllDetail")
    public ResponseEntity saveZjTzRiskAllDetail(@RequestBody(required = false) ZjTzRisk zjTzRisk) {
        return zjTzRiskService.saveZjTzRiskAllDetail(zjTzRisk);
    }

    
}
