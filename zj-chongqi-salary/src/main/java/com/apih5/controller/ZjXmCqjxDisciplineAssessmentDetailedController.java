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
import com.apih5.mybatis.pojo.ZjXmCqjxDisciplineAssessmentDetailed;
import com.apih5.service.ZjXmCqjxDisciplineAssessmentDetailedService;

@RestController
public class ZjXmCqjxDisciplineAssessmentDetailedController {

    @Autowired(required = true)
    private ZjXmCqjxDisciplineAssessmentDetailedService zjXmCqjxDisciplineAssessmentDetailedService;

    @ApiOperation(value="查询员工纪律性考核明细", notes="查询员工纪律性考核明细")
    @ApiImplicitParam(name = "zjXmCqjxDisciplineAssessmentDetailed", value = "员工纪律性考核明细entity", dataType = "ZjXmCqjxDisciplineAssessmentDetailed")
    @RequireToken
    @PostMapping("/getZjXmCqjxDisciplineAssessmentDetailedList")
    public ResponseEntity getZjXmCqjxDisciplineAssessmentDetailedList(@RequestBody(required = false) ZjXmCqjxDisciplineAssessmentDetailed zjXmCqjxDisciplineAssessmentDetailed) {
        return zjXmCqjxDisciplineAssessmentDetailedService.getZjXmCqjxDisciplineAssessmentDetailedListByCondition(zjXmCqjxDisciplineAssessmentDetailed);
    }

    @ApiOperation(value="查询详情员工纪律性考核明细", notes="查询详情员工纪律性考核明细")
    @ApiImplicitParam(name = "zjXmCqjxDisciplineAssessmentDetailed", value = "员工纪律性考核明细entity", dataType = "ZjXmCqjxDisciplineAssessmentDetailed")
    @RequireToken
    @PostMapping("/getZjXmCqjxDisciplineAssessmentDetailedDetails")
    public ResponseEntity getZjXmCqjxDisciplineAssessmentDetailedDetails(@RequestBody(required = false) ZjXmCqjxDisciplineAssessmentDetailed zjXmCqjxDisciplineAssessmentDetailed) {
        return zjXmCqjxDisciplineAssessmentDetailedService.getZjXmCqjxDisciplineAssessmentDetailedDetails(zjXmCqjxDisciplineAssessmentDetailed);
    }

    @ApiOperation(value="新增员工纪律性考核明细", notes="新增员工纪律性考核明细")
    @ApiImplicitParam(name = "zjXmCqjxDisciplineAssessmentDetailed", value = "员工纪律性考核明细entity", dataType = "ZjXmCqjxDisciplineAssessmentDetailed")
    @RequireToken
    @PostMapping("/addZjXmCqjxDisciplineAssessmentDetailed")
    public ResponseEntity addZjXmCqjxDisciplineAssessmentDetailed(@RequestBody(required = false) ZjXmCqjxDisciplineAssessmentDetailed zjXmCqjxDisciplineAssessmentDetailed) {
        return zjXmCqjxDisciplineAssessmentDetailedService.saveZjXmCqjxDisciplineAssessmentDetailed(zjXmCqjxDisciplineAssessmentDetailed);
    }

    @ApiOperation(value="更新员工纪律性考核明细", notes="更新员工纪律性考核明细")
    @ApiImplicitParam(name = "zjXmCqjxDisciplineAssessmentDetailed", value = "员工纪律性考核明细entity", dataType = "ZjXmCqjxDisciplineAssessmentDetailed")
    @RequireToken
    @PostMapping("/updateZjXmCqjxDisciplineAssessmentDetailed")
    public ResponseEntity updateZjXmCqjxDisciplineAssessmentDetailed(@RequestBody(required = false) ZjXmCqjxDisciplineAssessmentDetailed zjXmCqjxDisciplineAssessmentDetailed) {
        return zjXmCqjxDisciplineAssessmentDetailedService.updateZjXmCqjxDisciplineAssessmentDetailed(zjXmCqjxDisciplineAssessmentDetailed);
    }

    @ApiOperation(value="删除员工纪律性考核明细", notes="删除员工纪律性考核明细")
    @ApiImplicitParam(name = "zjXmCqjxDisciplineAssessmentDetailedList", value = "员工纪律性考核明细List", dataType = "List<ZjXmCqjxDisciplineAssessmentDetailed>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZjXmCqjxDisciplineAssessmentDetailed")
    public ResponseEntity batchDeleteUpdateZjXmCqjxDisciplineAssessmentDetailed(@RequestBody(required = false) List<ZjXmCqjxDisciplineAssessmentDetailed> zjXmCqjxDisciplineAssessmentDetailedList) {
        return zjXmCqjxDisciplineAssessmentDetailedService.batchDeleteUpdateZjXmCqjxDisciplineAssessmentDetailed(zjXmCqjxDisciplineAssessmentDetailedList);
    }
    
    @ApiOperation(value="员工纪律性考核批量评分", notes="员工纪律性考核批量评分")
    @ApiImplicitParam(name = "zjXmCqjxDisciplineAssessmentDetailedList", value = "员工纪律性考核明细List", dataType = "List<ZjXmCqjxDisciplineAssessmentDetailed>")
    @RequireToken
    @PostMapping("/batchUpdateZjXmCqjxDisciplineAssessmentDetailed")
    public ResponseEntity batchUpdateZjXmCqjxDisciplineAssessmentDetailed(@RequestBody(required = false) List<ZjXmCqjxDisciplineAssessmentDetailed> zjXmCqjxDisciplineAssessmentDetailedList) {
    	return zjXmCqjxDisciplineAssessmentDetailedService.batchUpdateZjXmCqjxDisciplineAssessmentDetailed(zjXmCqjxDisciplineAssessmentDetailedList);
    }

}
