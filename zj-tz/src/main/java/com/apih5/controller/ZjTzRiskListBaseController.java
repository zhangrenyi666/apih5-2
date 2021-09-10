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
import com.apih5.mybatis.pojo.ZjTzRiskListBase;
import com.apih5.service.ZjTzRiskListBaseService;

@RestController
public class ZjTzRiskListBaseController {

    @Autowired(required = true)
    private ZjTzRiskListBaseService zjTzRiskListBaseService;

    @ApiOperation(value="查询风险清单设置", notes="查询风险清单设置")
    @ApiImplicitParam(name = "zjTzRiskListBase", value = "风险清单设置entity", dataType = "ZjTzRiskListBase")
    @RequireToken
    @PostMapping("/getZjTzRiskListBaseList")
    public ResponseEntity getZjTzRiskListBaseList(@RequestBody(required = false) ZjTzRiskListBase zjTzRiskListBase) {
        return zjTzRiskListBaseService.getZjTzRiskListBaseListByCondition(zjTzRiskListBase);
    }

    @ApiOperation(value="查询详情风险清单设置", notes="查询详情风险清单设置")
    @ApiImplicitParam(name = "zjTzRiskListBase", value = "风险清单设置entity", dataType = "ZjTzRiskListBase")
    @RequireToken
    @PostMapping("/getZjTzRiskListBaseDetails")
    public ResponseEntity getZjTzRiskListBaseDetails(@RequestBody(required = false) ZjTzRiskListBase zjTzRiskListBase) {
        return zjTzRiskListBaseService.getZjTzRiskListBaseDetails(zjTzRiskListBase);
    }

    @ApiOperation(value="新增风险清单设置", notes="新增风险清单设置")
    @ApiImplicitParam(name = "zjTzRiskListBase", value = "风险清单设置entity", dataType = "ZjTzRiskListBase")
    @RequireToken
    @PostMapping("/addZjTzRiskListBase")
    public ResponseEntity addZjTzRiskListBase(@RequestBody(required = false) ZjTzRiskListBase zjTzRiskListBase) {
        return zjTzRiskListBaseService.saveZjTzRiskListBase(zjTzRiskListBase);
    }

    @ApiOperation(value="更新风险清单设置", notes="更新风险清单设置")
    @ApiImplicitParam(name = "zjTzRiskListBase", value = "风险清单设置entity", dataType = "ZjTzRiskListBase")
    @RequireToken
    @PostMapping("/updateZjTzRiskListBase")
    public ResponseEntity updateZjTzRiskListBase(@RequestBody(required = false) ZjTzRiskListBase zjTzRiskListBase) {
        return zjTzRiskListBaseService.updateZjTzRiskListBase(zjTzRiskListBase);
    }

    @ApiOperation(value="删除风险清单设置", notes="删除风险清单设置")
    @ApiImplicitParam(name = "zjTzRiskListBaseList", value = "风险清单设置List", dataType = "List<ZjTzRiskListBase>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZjTzRiskListBase")
    public ResponseEntity batchDeleteUpdateZjTzRiskListBase(@RequestBody(required = false) List<ZjTzRiskListBase> zjTzRiskListBaseList) {
        return zjTzRiskListBaseService.batchDeleteUpdateZjTzRiskListBase(zjTzRiskListBaseList);
    }

    @ApiOperation(value="批量锁定风险清单设置", notes="批量锁定风险清单设置")
    @ApiImplicitParam(name = "zjTzRiskListBaseList", value = "风险清单设置List", dataType = "List<ZjTzRiskListBase>")
    @RequireToken
    @PostMapping("/batchLockUpdateZjTzRiskListBase")
    public ResponseEntity batchLockUpdateZjTzRiskListBase(@RequestBody(required = false) List<ZjTzRiskListBase> zjTzRiskListBaseList) {
        return zjTzRiskListBaseService.batchLockUpdateZjTzRiskListBase(zjTzRiskListBaseList);
    }
    
    @ApiOperation(value="批量解锁风险清单设置", notes="批量解锁风险清单设置")
    @ApiImplicitParam(name = "zjTzRiskListBaseList", value = "风险清单设置List", dataType = "List<ZjTzRiskListBase>")
    @RequireToken
    @PostMapping("/batchClearUpdateZjTzRiskListBase")
    public ResponseEntity batchClearUpdateZjTzRiskListBase(@RequestBody(required = false) List<ZjTzRiskListBase> zjTzRiskListBaseList) {
        return zjTzRiskListBaseService.batchClearUpdateZjTzRiskListBase(zjTzRiskListBaseList);
    }


}
