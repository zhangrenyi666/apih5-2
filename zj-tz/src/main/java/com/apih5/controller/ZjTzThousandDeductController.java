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
import com.apih5.mybatis.pojo.ZjTzThousandDeduct;
import com.apih5.service.ZjTzThousandDeductService;

@RestController
public class ZjTzThousandDeductController {

    @Autowired(required = true)
    private ZjTzThousandDeductService zjTzThousandDeductService;

    @ApiOperation(value="查询千分制扣分", notes="查询千分制扣分")
    @ApiImplicitParam(name = "zjTzThousandDeduct", value = "千分制扣分entity", dataType = "ZjTzThousandDeduct")
    @RequireToken
    @PostMapping("/getZjTzThousandDeductList")
    public ResponseEntity getZjTzThousandDeductList(@RequestBody(required = false) ZjTzThousandDeduct zjTzThousandDeduct) {
        return zjTzThousandDeductService.getZjTzThousandDeductListByCondition(zjTzThousandDeduct);
    }

    @ApiOperation(value="查询详情千分制扣分", notes="查询详情千分制扣分")
    @ApiImplicitParam(name = "zjTzThousandDeduct", value = "千分制扣分entity", dataType = "ZjTzThousandDeduct")
    @RequireToken
    @PostMapping("/getZjTzThousandDeductDetails")
    public ResponseEntity getZjTzThousandDeductDetails(@RequestBody(required = false) ZjTzThousandDeduct zjTzThousandDeduct) {
        return zjTzThousandDeductService.getZjTzThousandDeductDetails(zjTzThousandDeduct);
    }

    @ApiOperation(value="新增千分制扣分", notes="新增千分制扣分")
    @ApiImplicitParam(name = "zjTzThousandDeduct", value = "千分制扣分entity", dataType = "ZjTzThousandDeduct")
    @RequireToken
    @PostMapping("/addZjTzThousandDeduct")
    public ResponseEntity addZjTzThousandDeduct(@RequestBody(required = false) ZjTzThousandDeduct zjTzThousandDeduct) {
        return zjTzThousandDeductService.saveZjTzThousandDeduct(zjTzThousandDeduct);
    }

    @ApiOperation(value="更新千分制扣分", notes="更新千分制扣分")
    @ApiImplicitParam(name = "zjTzThousandDeduct", value = "千分制扣分entity", dataType = "ZjTzThousandDeduct")
    @RequireToken
    @PostMapping("/updateZjTzThousandDeduct")
    public ResponseEntity updateZjTzThousandDeduct(@RequestBody(required = false) ZjTzThousandDeduct zjTzThousandDeduct) {
        return zjTzThousandDeductService.updateZjTzThousandDeduct(zjTzThousandDeduct);
    }

    @ApiOperation(value="删除千分制扣分", notes="删除千分制扣分")
    @ApiImplicitParam(name = "zjTzThousandDeductList", value = "千分制扣分List", dataType = "List<ZjTzThousandDeduct>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZjTzThousandDeduct")
    public ResponseEntity batchDeleteUpdateZjTzThousandDeduct(@RequestBody(required = false) List<ZjTzThousandDeduct> zjTzThousandDeductList) {
        return zjTzThousandDeductService.batchDeleteUpdateZjTzThousandDeduct(zjTzThousandDeductList);
    }

}
