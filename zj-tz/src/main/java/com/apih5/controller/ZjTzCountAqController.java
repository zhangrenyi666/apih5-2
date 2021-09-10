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
import com.apih5.mybatis.pojo.ZjTzCountAq;
import com.apih5.service.ZjTzCountAqService;

@RestController
public class ZjTzCountAqController {

    @Autowired(required = true)
    private ZjTzCountAqService zjTzCountAqService;

    @ApiOperation(value="查询安全质量统计", notes="查询安全质量统计")
    @ApiImplicitParam(name = "zjTzCountAq", value = "安全质量统计entity", dataType = "ZjTzCountAq")
    @RequireToken
    @PostMapping("/getZjTzCountAqList")
    public ResponseEntity getZjTzCountAqList(@RequestBody(required = false) ZjTzCountAq zjTzCountAq) {
        return zjTzCountAqService.getZjTzCountAqListByCondition(zjTzCountAq);
    }

    @ApiOperation(value="查询详情安全质量统计", notes="查询详情安全质量统计")
    @ApiImplicitParam(name = "zjTzCountAq", value = "安全质量统计entity", dataType = "ZjTzCountAq")
    @RequireToken
    @PostMapping("/getZjTzCountAqDetails")
    public ResponseEntity getZjTzCountAqDetails(@RequestBody(required = false) ZjTzCountAq zjTzCountAq) {
        return zjTzCountAqService.getZjTzCountAqDetails(zjTzCountAq);
    }

    @ApiOperation(value="新增安全质量统计", notes="新增安全质量统计")
    @ApiImplicitParam(name = "zjTzCountAq", value = "安全质量统计entity", dataType = "ZjTzCountAq")
    @RequireToken
    @PostMapping("/addZjTzCountAq")
    public ResponseEntity addZjTzCountAq(@RequestBody(required = false) ZjTzCountAq zjTzCountAq) {
        return zjTzCountAqService.saveZjTzCountAq(zjTzCountAq);
    }

    @ApiOperation(value="更新安全质量统计", notes="更新安全质量统计")
    @ApiImplicitParam(name = "zjTzCountAq", value = "安全质量统计entity", dataType = "ZjTzCountAq")
    @RequireToken
    @PostMapping("/updateZjTzCountAq")
    public ResponseEntity updateZjTzCountAq(@RequestBody(required = false) ZjTzCountAq zjTzCountAq) {
        return zjTzCountAqService.updateZjTzCountAq(zjTzCountAq);
    }

    @ApiOperation(value="删除安全质量统计", notes="删除安全质量统计")
    @ApiImplicitParam(name = "zjTzCountAqList", value = "安全质量统计List", dataType = "List<ZjTzCountAq>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZjTzCountAq")
    public ResponseEntity batchDeleteUpdateZjTzCountAq(@RequestBody(required = false) List<ZjTzCountAq> zjTzCountAqList) {
        return zjTzCountAqService.batchDeleteUpdateZjTzCountAq(zjTzCountAqList);
    }

}
