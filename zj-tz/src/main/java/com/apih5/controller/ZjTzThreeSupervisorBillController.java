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
import com.apih5.mybatis.pojo.ZjTzThreeSupervisorBill;
import com.apih5.service.ZjTzThreeSupervisorBillService;

@RestController
public class ZjTzThreeSupervisorBillController {

    @Autowired(required = true)
    private ZjTzThreeSupervisorBillService zjTzThreeSupervisorBillService;

    @ApiOperation(value="查询三会管理-监事议案", notes="查询三会管理-监事议案")
    @ApiImplicitParam(name = "zjTzThreeSupervisorBill", value = "三会管理-监事议案entity", dataType = "ZjTzThreeSupervisorBill")
    @RequireToken
    @PostMapping("/getZjTzThreeSupervisorBillList")
    public ResponseEntity getZjTzThreeSupervisorBillList(@RequestBody(required = false) ZjTzThreeSupervisorBill zjTzThreeSupervisorBill) {
        return zjTzThreeSupervisorBillService.getZjTzThreeSupervisorBillListByCondition(zjTzThreeSupervisorBill);
    }

    @ApiOperation(value="查询详情三会管理-监事议案", notes="查询详情三会管理-监事议案")
    @ApiImplicitParam(name = "zjTzThreeSupervisorBill", value = "三会管理-监事议案entity", dataType = "ZjTzThreeSupervisorBill")
    @RequireToken
    @PostMapping("/getZjTzThreeSupervisorBillDetails")
    public ResponseEntity getZjTzThreeSupervisorBillDetails(@RequestBody(required = false) ZjTzThreeSupervisorBill zjTzThreeSupervisorBill) {
        return zjTzThreeSupervisorBillService.getZjTzThreeSupervisorBillDetails(zjTzThreeSupervisorBill);
    }

    @ApiOperation(value="新增三会管理-监事议案", notes="新增三会管理-监事议案")
    @ApiImplicitParam(name = "zjTzThreeSupervisorBill", value = "三会管理-监事议案entity", dataType = "ZjTzThreeSupervisorBill")
    @RequireToken
    @PostMapping("/addZjTzThreeSupervisorBill")
    public ResponseEntity addZjTzThreeSupervisorBill(@RequestBody(required = false) ZjTzThreeSupervisorBill zjTzThreeSupervisorBill) {
        return zjTzThreeSupervisorBillService.saveZjTzThreeSupervisorBill(zjTzThreeSupervisorBill);
    }

    @ApiOperation(value="更新三会管理-监事议案", notes="更新三会管理-监事议案")
    @ApiImplicitParam(name = "zjTzThreeSupervisorBill", value = "三会管理-监事议案entity", dataType = "ZjTzThreeSupervisorBill")
    @RequireToken
    @PostMapping("/updateZjTzThreeSupervisorBill")
    public ResponseEntity updateZjTzThreeSupervisorBill(@RequestBody(required = false) ZjTzThreeSupervisorBill zjTzThreeSupervisorBill) {
        return zjTzThreeSupervisorBillService.updateZjTzThreeSupervisorBill(zjTzThreeSupervisorBill);
    }

    @ApiOperation(value="删除三会管理-监事议案", notes="删除三会管理-监事议案")
    @ApiImplicitParam(name = "zjTzThreeSupervisorBillList", value = "三会管理-监事议案List", dataType = "List<ZjTzThreeSupervisorBill>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZjTzThreeSupervisorBill")
    public ResponseEntity batchDeleteUpdateZjTzThreeSupervisorBill(@RequestBody(required = false) List<ZjTzThreeSupervisorBill> zjTzThreeSupervisorBillList) {
        return zjTzThreeSupervisorBillService.batchDeleteUpdateZjTzThreeSupervisorBill(zjTzThreeSupervisorBillList);
    }

}
