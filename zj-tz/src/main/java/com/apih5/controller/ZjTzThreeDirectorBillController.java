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
import com.apih5.mybatis.pojo.ZjTzThreeDirectorBill;
import com.apih5.service.ZjTzThreeDirectorBillService;

@RestController
public class ZjTzThreeDirectorBillController {

    @Autowired(required = true)
    private ZjTzThreeDirectorBillService zjTzThreeDirectorBillService;

    @ApiOperation(value="查询三会管理-董事议案", notes="查询三会管理-董事议案")
    @ApiImplicitParam(name = "zjTzThreeDirectorBill", value = "三会管理-董事议案entity", dataType = "ZjTzThreeDirectorBill")
    @RequireToken
    @PostMapping("/getZjTzThreeDirectorBillList")
    public ResponseEntity getZjTzThreeDirectorBillList(@RequestBody(required = false) ZjTzThreeDirectorBill zjTzThreeDirectorBill) {
        return zjTzThreeDirectorBillService.getZjTzThreeDirectorBillListByCondition(zjTzThreeDirectorBill);
    }

    @ApiOperation(value="查询详情三会管理-董事议案", notes="查询详情三会管理-董事议案")
    @ApiImplicitParam(name = "zjTzThreeDirectorBill", value = "三会管理-董事议案entity", dataType = "ZjTzThreeDirectorBill")
    @RequireToken
    @PostMapping("/getZjTzThreeDirectorBillDetails")
    public ResponseEntity getZjTzThreeDirectorBillDetails(@RequestBody(required = false) ZjTzThreeDirectorBill zjTzThreeDirectorBill) {
        return zjTzThreeDirectorBillService.getZjTzThreeDirectorBillDetails(zjTzThreeDirectorBill);
    }

    @ApiOperation(value="新增三会管理-董事议案", notes="新增三会管理-董事议案")
    @ApiImplicitParam(name = "zjTzThreeDirectorBill", value = "三会管理-董事议案entity", dataType = "ZjTzThreeDirectorBill")
    @RequireToken
    @PostMapping("/addZjTzThreeDirectorBill")
    public ResponseEntity addZjTzThreeDirectorBill(@RequestBody(required = false) ZjTzThreeDirectorBill zjTzThreeDirectorBill) {
        return zjTzThreeDirectorBillService.saveZjTzThreeDirectorBill(zjTzThreeDirectorBill);
    }

    @ApiOperation(value="更新三会管理-董事议案", notes="更新三会管理-董事议案")
    @ApiImplicitParam(name = "zjTzThreeDirectorBill", value = "三会管理-董事议案entity", dataType = "ZjTzThreeDirectorBill")
    @RequireToken
    @PostMapping("/updateZjTzThreeDirectorBill")
    public ResponseEntity updateZjTzThreeDirectorBill(@RequestBody(required = false) ZjTzThreeDirectorBill zjTzThreeDirectorBill) {
        return zjTzThreeDirectorBillService.updateZjTzThreeDirectorBill(zjTzThreeDirectorBill);
    }

    @ApiOperation(value="删除三会管理-董事议案", notes="删除三会管理-董事议案")
    @ApiImplicitParam(name = "zjTzThreeDirectorBillList", value = "三会管理-董事议案List", dataType = "List<ZjTzThreeDirectorBill>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZjTzThreeDirectorBill")
    public ResponseEntity batchDeleteUpdateZjTzThreeDirectorBill(@RequestBody(required = false) List<ZjTzThreeDirectorBill> zjTzThreeDirectorBillList) {
        return zjTzThreeDirectorBillService.batchDeleteUpdateZjTzThreeDirectorBill(zjTzThreeDirectorBillList);
    }

}