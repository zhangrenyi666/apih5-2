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
import com.apih5.mybatis.pojo.ZjTzRiskDetail;
import com.apih5.service.ZjTzRiskDetailService;

@RestController
public class ZjTzRiskDetailController {

    @Autowired(required = true)
    private ZjTzRiskDetailService zjTzRiskDetailService;

    @ApiOperation(value="查询风险清单详情", notes="查询风险清单详情")
    @ApiImplicitParam(name = "zjTzRiskDetail", value = "风险清单详情entity", dataType = "ZjTzRiskDetail")
    @RequireToken
    @PostMapping("/getZjTzRiskDetailList")
    public ResponseEntity getZjTzRiskDetailList(@RequestBody(required = false) ZjTzRiskDetail zjTzRiskDetail) {
        return zjTzRiskDetailService.getZjTzRiskDetailListByCondition(zjTzRiskDetail);
    }

    @ApiOperation(value="查询详情风险清单详情", notes="查询详情风险清单详情")
    @ApiImplicitParam(name = "zjTzRiskDetail", value = "风险清单详情entity", dataType = "ZjTzRiskDetail")
    @RequireToken
    @PostMapping("/getZjTzRiskDetailDetails")
    public ResponseEntity getZjTzRiskDetailDetails(@RequestBody(required = false) ZjTzRiskDetail zjTzRiskDetail) {
        return zjTzRiskDetailService.getZjTzRiskDetailDetails(zjTzRiskDetail);
    }

    @ApiOperation(value="新增风险清单详情", notes="新增风险清单详情")
    @ApiImplicitParam(name = "zjTzRiskDetail", value = "风险清单详情entity", dataType = "ZjTzRiskDetail")
    @RequireToken
    @PostMapping("/addZjTzRiskDetail")
    public ResponseEntity addZjTzRiskDetail(@RequestBody(required = false) ZjTzRiskDetail zjTzRiskDetail) {
        return zjTzRiskDetailService.saveZjTzRiskDetail(zjTzRiskDetail);
    }

    @ApiOperation(value="更新风险清单详情", notes="更新风险清单详情")
    @ApiImplicitParam(name = "zjTzRiskDetail", value = "风险清单详情entity", dataType = "ZjTzRiskDetail")
    @RequireToken
    @PostMapping("/updateZjTzRiskDetail")
    public ResponseEntity updateZjTzRiskDetail(@RequestBody(required = false) ZjTzRiskDetail zjTzRiskDetail) {
        return zjTzRiskDetailService.updateZjTzRiskDetail(zjTzRiskDetail);
    }

    @ApiOperation(value="删除风险清单详情", notes="删除风险清单详情")
    @ApiImplicitParam(name = "zjTzRiskDetailList", value = "风险清单详情List", dataType = "List<ZjTzRiskDetail>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZjTzRiskDetail")
    public ResponseEntity batchDeleteUpdateZjTzRiskDetail(@RequestBody(required = false) List<ZjTzRiskDetail> zjTzRiskDetailList) {
        return zjTzRiskDetailService.batchDeleteUpdateZjTzRiskDetail(zjTzRiskDetailList);
    }
    
    @ApiOperation(value="批量锁定风险清单详情", notes="批量锁定风险清单详情")
    @ApiImplicitParam(name = "zjTzRiskDetailList", value = "风险清单详情List", dataType = "List<ZjTzRiskDetail>")
    @RequireToken
    @PostMapping("/batchLockUpdateZjTzRiskDetail")
    public ResponseEntity batchLockUpdateZjTzRiskDetail(@RequestBody(required = false) List<ZjTzRiskDetail> zjTzRiskDetailList) {
        return zjTzRiskDetailService.batchLockUpdateZjTzRiskDetail(zjTzRiskDetailList);
    }
    
    @ApiOperation(value="批量解锁风险清单详情", notes="批量解锁风险清单详情")
    @ApiImplicitParam(name = "zjTzRiskDetailList", value = "风险清单详情List", dataType = "List<ZjTzRiskDetail>")
    @RequireToken
    @PostMapping("/batchClearUpdateZjTzRiskDetail")
    public ResponseEntity batchClearUpdateZjTzRiskDetail(@RequestBody(required = false) List<ZjTzRiskDetail> zjTzRiskDetailList) {
        return zjTzRiskDetailService.batchClearUpdateZjTzRiskDetail(zjTzRiskDetailList);
    }
    
    @ApiOperation(value="报表导出风险清单详情", notes="报表导出风险清单详情")
    @ApiImplicitParam(name = "zjTzRiskDetail", value = "风险清单详情entity", dataType = "ZjTzRiskDetail")
    @PostMapping("/ureportZjTzRiskDetailList")
    public List<ZjTzRiskDetail> ureportZjTzRiskDetailList(@RequestBody(required = false) ZjTzRiskDetail zjTzRiskDetail) {
        return zjTzRiskDetailService.ureportZjTzRiskDetailList(zjTzRiskDetail);
    }


}
