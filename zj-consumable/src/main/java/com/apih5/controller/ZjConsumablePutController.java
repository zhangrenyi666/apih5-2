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
import com.apih5.mybatis.pojo.ZjConsumablePut;
import com.apih5.service.ZjConsumablePutService;

@RestController
public class ZjConsumablePutController {

    @Autowired(required = true)
    private ZjConsumablePutService zjConsumablePutService;

    @ApiOperation(value="查询耗材入库台账", notes="查询耗材入库台账")
    @ApiImplicitParam(name = "zjConsumablePut", value = "耗材入库台账entity", dataType = "ZjConsumablePut")
    @RequireToken
    @PostMapping("/getZjConsumablePutList")
    public ResponseEntity getZjConsumablePutList(@RequestBody(required = false) ZjConsumablePut zjConsumablePut) {
        return zjConsumablePutService.getZjConsumablePutListByCondition(zjConsumablePut);
    }

    @ApiOperation(value="查询详情耗材入库台账", notes="查询详情耗材入库台账")
    @ApiImplicitParam(name = "zjConsumablePut", value = "耗材入库台账entity", dataType = "ZjConsumablePut")
    @RequireToken
    @PostMapping("/getZjConsumablePutDetails")
    public ResponseEntity getZjConsumablePutDetails(@RequestBody(required = false) ZjConsumablePut zjConsumablePut) {
        return zjConsumablePutService.getZjConsumablePutDetails(zjConsumablePut);
    }

    @ApiOperation(value="新增耗材入库台账", notes="新增耗材入库台账")
    @ApiImplicitParam(name = "zjConsumablePut", value = "耗材入库台账entity", dataType = "ZjConsumablePut")
    @RequireToken
    @PostMapping("/addZjConsumablePut")
    public ResponseEntity addZjConsumablePut(@RequestBody(required = false) ZjConsumablePut zjConsumablePut) {
        return zjConsumablePutService.saveZjConsumablePut(zjConsumablePut);
    }

    @ApiOperation(value="更新耗材入库台账", notes="更新耗材入库台账")
    @ApiImplicitParam(name = "zjConsumablePut", value = "耗材入库台账entity", dataType = "ZjConsumablePut")
    @RequireToken
    @PostMapping("/updateZjConsumablePut")
    public ResponseEntity updateZjConsumablePut(@RequestBody(required = false) ZjConsumablePut zjConsumablePut) {
        return zjConsumablePutService.updateZjConsumablePut(zjConsumablePut);
    }

    @ApiOperation(value="删除耗材入库台账", notes="删除耗材入库台账")
    @ApiImplicitParam(name = "zjConsumablePutList", value = "耗材入库台账List", dataType = "List<ZjConsumablePut>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZjConsumablePut")
    public ResponseEntity batchDeleteUpdateZjConsumablePut(@RequestBody(required = false) List<ZjConsumablePut> zjConsumablePutList) {
        return zjConsumablePutService.batchDeleteUpdateZjConsumablePut(zjConsumablePutList);
    }

}
