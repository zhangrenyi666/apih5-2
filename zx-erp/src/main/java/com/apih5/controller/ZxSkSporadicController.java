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
import com.apih5.mybatis.pojo.ZxSkSporadic;
import com.apih5.service.ZxSkSporadicService;

@RestController
public class ZxSkSporadicController {

    @Autowired(required = true)
    private ZxSkSporadicService zxSkSporadicService;

    @ApiOperation(value="查询零星采购", notes="查询零星采购")
    @ApiImplicitParam(name = "zxSkSporadic", value = "零星采购entity", dataType = "ZxSkSporadic")
    @RequireToken
    @PostMapping("/getZxSkSporadicList")
    public ResponseEntity getZxSkSporadicList(@RequestBody(required = false) ZxSkSporadic zxSkSporadic) {
        return zxSkSporadicService.getZxSkSporadicListByCondition(zxSkSporadic);
    }

    @ApiOperation(value="查询详情零星采购", notes="查询详情零星采购")
    @ApiImplicitParam(name = "zxSkSporadic", value = "零星采购entity", dataType = "ZxSkSporadic")
    @RequireToken
    @PostMapping("/getZxSkSporadicDetails")
    public ResponseEntity getZxSkSporadicDetails(@RequestBody(required = false) ZxSkSporadic zxSkSporadic) {
        return zxSkSporadicService.getZxSkSporadicDetails(zxSkSporadic);
    }

    @ApiOperation(value="新增零星采购", notes="新增零星采购")
    @ApiImplicitParam(name = "zxSkSporadic", value = "零星采购entity", dataType = "ZxSkSporadic")
    @RequireToken
    @PostMapping("/addZxSkSporadic")
    public ResponseEntity addZxSkSporadic(@RequestBody(required = false) ZxSkSporadic zxSkSporadic) {
        return zxSkSporadicService.saveZxSkSporadic(zxSkSporadic);
    }

    @ApiOperation(value="更新零星采购", notes="更新零星采购")
    @ApiImplicitParam(name = "zxSkSporadic", value = "零星采购entity", dataType = "ZxSkSporadic")
    @RequireToken
    @PostMapping("/updateZxSkSporadic")
    public ResponseEntity updateZxSkSporadic(@RequestBody(required = false) ZxSkSporadic zxSkSporadic) {
        return zxSkSporadicService.updateZxSkSporadic(zxSkSporadic);
    }

    @ApiOperation(value="删除零星采购", notes="删除零星采购")
    @ApiImplicitParam(name = "zxSkSporadicList", value = "零星采购List", dataType = "List<ZxSkSporadic>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxSkSporadic")
    public ResponseEntity batchDeleteUpdateZxSkSporadic(@RequestBody(required = false) List<ZxSkSporadic> zxSkSporadicList) {
        return zxSkSporadicService.batchDeleteUpdateZxSkSporadic(zxSkSporadicList);
    }

}
