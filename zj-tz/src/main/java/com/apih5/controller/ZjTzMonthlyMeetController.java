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
import com.apih5.mybatis.pojo.ZjTzMonthlyMeet;
import com.apih5.service.ZjTzMonthlyMeetService;

@RestController
public class ZjTzMonthlyMeetController {

    @Autowired(required = true)
    private ZjTzMonthlyMeetService zjTzMonthlyMeetService;

    @ApiOperation(value="查询月度总办会", notes="查询月度总办会")
    @ApiImplicitParam(name = "zjTzMonthlyMeet", value = "月度总办会entity", dataType = "ZjTzMonthlyMeet")
    @RequireToken
    @PostMapping("/getZjTzMonthlyMeetList")
    public ResponseEntity getZjTzMonthlyMeetList(@RequestBody(required = false) ZjTzMonthlyMeet zjTzMonthlyMeet) {
        return zjTzMonthlyMeetService.getZjTzMonthlyMeetListByCondition(zjTzMonthlyMeet);
    }

    @ApiOperation(value="查询详情月度总办会", notes="查询详情月度总办会")
    @ApiImplicitParam(name = "zjTzMonthlyMeet", value = "月度总办会entity", dataType = "ZjTzMonthlyMeet")
    @RequireToken
    @PostMapping("/getZjTzMonthlyMeetDetails")
    public ResponseEntity getZjTzMonthlyMeetDetails(@RequestBody(required = false) ZjTzMonthlyMeet zjTzMonthlyMeet) {
        return zjTzMonthlyMeetService.getZjTzMonthlyMeetDetails(zjTzMonthlyMeet);
    }

    @ApiOperation(value="新增月度总办会", notes="新增月度总办会")
    @ApiImplicitParam(name = "zjTzMonthlyMeet", value = "月度总办会entity", dataType = "ZjTzMonthlyMeet")
    @RequireToken
    @PostMapping("/addZjTzMonthlyMeet")
    public ResponseEntity addZjTzMonthlyMeet(@RequestBody(required = false) ZjTzMonthlyMeet zjTzMonthlyMeet) {
        return zjTzMonthlyMeetService.saveZjTzMonthlyMeet(zjTzMonthlyMeet);
    }

    @ApiOperation(value="更新月度总办会", notes="更新月度总办会")
    @ApiImplicitParam(name = "zjTzMonthlyMeet", value = "月度总办会entity", dataType = "ZjTzMonthlyMeet")
    @RequireToken
    @PostMapping("/updateZjTzMonthlyMeet")
    public ResponseEntity updateZjTzMonthlyMeet(@RequestBody(required = false) ZjTzMonthlyMeet zjTzMonthlyMeet) {
        return zjTzMonthlyMeetService.updateZjTzMonthlyMeet(zjTzMonthlyMeet);
    }

    @ApiOperation(value="删除月度总办会", notes="删除月度总办会")
    @ApiImplicitParam(name = "zjTzMonthlyMeetList", value = "月度总办会List", dataType = "List<ZjTzMonthlyMeet>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZjTzMonthlyMeet")
    public ResponseEntity batchDeleteUpdateZjTzMonthlyMeet(@RequestBody(required = false) List<ZjTzMonthlyMeet> zjTzMonthlyMeetList) {
        return zjTzMonthlyMeetService.batchDeleteUpdateZjTzMonthlyMeet(zjTzMonthlyMeetList);
    }

}
