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
import com.apih5.mybatis.pojo.ZjTzThreeShareholderBill;
import com.apih5.service.ZjTzThreeShareholderBillService;

@RestController
public class ZjTzThreeShareholderBillController {

    @Autowired(required = true)
    private ZjTzThreeShareholderBillService zjTzThreeShareholderBillService;

    @ApiOperation(value="查询三会管理-股东议案", notes="查询三会管理-股东议案")
    @ApiImplicitParam(name = "zjTzThreeShareholderBill", value = "三会管理-股东议案entity", dataType = "ZjTzThreeShareholderBill")
    @RequireToken
    @PostMapping("/getZjTzThreeShareholderBillList")
    public ResponseEntity getZjTzThreeShareholderBillList(@RequestBody(required = false) ZjTzThreeShareholderBill zjTzThreeShareholderBill) {
        return zjTzThreeShareholderBillService.getZjTzThreeShareholderBillListByCondition(zjTzThreeShareholderBill);
    }

    @ApiOperation(value="查询详情三会管理-股东议案", notes="查询详情三会管理-股东议案")
    @ApiImplicitParam(name = "zjTzThreeShareholderBill", value = "三会管理-股东议案entity", dataType = "ZjTzThreeShareholderBill")
    @RequireToken
    @PostMapping("/getZjTzThreeShareholderBillDetails")
    public ResponseEntity getZjTzThreeShareholderBillDetails(@RequestBody(required = false) ZjTzThreeShareholderBill zjTzThreeShareholderBill) {
        return zjTzThreeShareholderBillService.getZjTzThreeShareholderBillDetails(zjTzThreeShareholderBill);
    }

    @ApiOperation(value="新增三会管理-股东议案", notes="新增三会管理-股东议案")
    @ApiImplicitParam(name = "zjTzThreeShareholderBill", value = "三会管理-股东议案entity", dataType = "ZjTzThreeShareholderBill")
    @RequireToken
    @PostMapping("/addZjTzThreeShareholderBill")
    public ResponseEntity addZjTzThreeShareholderBill(@RequestBody(required = false) ZjTzThreeShareholderBill zjTzThreeShareholderBill) {
        return zjTzThreeShareholderBillService.saveZjTzThreeShareholderBill(zjTzThreeShareholderBill);
    }

    @ApiOperation(value="更新三会管理-股东议案", notes="更新三会管理-股东议案")
    @ApiImplicitParam(name = "zjTzThreeShareholderBill", value = "三会管理-股东议案entity", dataType = "ZjTzThreeShareholderBill")
    @RequireToken
    @PostMapping("/updateZjTzThreeShareholderBill")
    public ResponseEntity updateZjTzThreeShareholderBill(@RequestBody(required = false) ZjTzThreeShareholderBill zjTzThreeShareholderBill) {
        return zjTzThreeShareholderBillService.updateZjTzThreeShareholderBill(zjTzThreeShareholderBill);
    }

    @ApiOperation(value="删除三会管理-股东议案", notes="删除三会管理-股东议案")
    @ApiImplicitParam(name = "zjTzThreeShareholderBillList", value = "三会管理-股东议案List", dataType = "List<ZjTzThreeShareholderBill>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZjTzThreeShareholderBill")
    public ResponseEntity batchDeleteUpdateZjTzThreeShareholderBill(@RequestBody(required = false) List<ZjTzThreeShareholderBill> zjTzThreeShareholderBillList) {
        return zjTzThreeShareholderBillService.batchDeleteUpdateZjTzThreeShareholderBill(zjTzThreeShareholderBillList);
    }
    

    @ApiOperation(value="查询报表导出三会管理-股东议案", notes="查询报表导出三会管理-股东议案")
    @ApiImplicitParam(name = "zjTzThreeShareholderBill", value = "三会管理-股东议案entity", dataType = "ZjTzThreeShareholderBill")
    @RequireToken
    @PostMapping("/getZjTzThreeShareholderBillListForReport")
    public ResponseEntity getZjTzThreeShareholderBillListForReport(@RequestBody(required = false) ZjTzThreeShareholderBill zjTzThreeShareholderBill) {
        return zjTzThreeShareholderBillService.getZjTzThreeShareholderBillListForReport(zjTzThreeShareholderBill);
    }
    
    
    @ApiOperation(value="报表导出三会管理-股东议案", notes="查询三会管理-股东议案")
    @ApiImplicitParam(name = "zjTzThreeShareholderBill", value = "三会管理-股东议案entity", dataType = "ZjTzThreeShareholderBill")
    @RequireToken
    @PostMapping("/ureportZjTzThreeBillListFinish")
    public List<ZjTzThreeShareholderBill> ureportZjTzThreeBillListFinish(@RequestBody(required = false) ZjTzThreeShareholderBill zjTzThreeShareholderBill) {
        return zjTzThreeShareholderBillService.ureportZjTzThreeBillListFinish(zjTzThreeShareholderBill);
    }

}
