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
import com.apih5.mybatis.pojo.ZjTzSupervisorMonthlyMeeting;
import com.apih5.service.ZjTzSupervisorMonthlyMeetingService;

@RestController
public class ZjTzQHSESupervisorMonthlyMeetingController {

    @Autowired(required = true)
    private ZjTzSupervisorMonthlyMeetingService zjTzSupervisorMonthlyMeetingService;

    @ApiOperation(value="查询监理月度例会", notes="查询监理月度例会")
    @ApiImplicitParam(name = "zjTzSupervisorMonthlyMeeting", value = "监理月度例会entity", dataType = "ZjTzSupervisorMonthlyMeeting")
    @RequireToken
    @PostMapping("/getZjTzSupervisorMonthlyMeetingList")
    public ResponseEntity getZjTzSupervisorMonthlyMeetingList(@RequestBody(required = false) ZjTzSupervisorMonthlyMeeting zjTzSupervisorMonthlyMeeting) {
        return zjTzSupervisorMonthlyMeetingService.getZjTzSupervisorMonthlyMeetingListByCondition(zjTzSupervisorMonthlyMeeting);
    }

    @ApiOperation(value="查询详情监理月度例会", notes="查询详情监理月度例会")
    @ApiImplicitParam(name = "zjTzSupervisorMonthlyMeeting", value = "监理月度例会entity", dataType = "ZjTzSupervisorMonthlyMeeting")
    @RequireToken
    @PostMapping("/getZjTzSupervisorMonthlyMeetingDetails")
    public ResponseEntity getZjTzSupervisorMonthlyMeetingDetails(@RequestBody(required = false) ZjTzSupervisorMonthlyMeeting zjTzSupervisorMonthlyMeeting) {
        return zjTzSupervisorMonthlyMeetingService.getZjTzSupervisorMonthlyMeetingDetails(zjTzSupervisorMonthlyMeeting);
    }

    @ApiOperation(value="新增监理月度例会", notes="新增监理月度例会")
    @ApiImplicitParam(name = "zjTzSupervisorMonthlyMeeting", value = "监理月度例会entity", dataType = "ZjTzSupervisorMonthlyMeeting")
    @RequireToken
    @PostMapping("/addZjTzSupervisorMonthlyMeeting")
    public ResponseEntity addZjTzSupervisorMonthlyMeeting(@RequestBody(required = false) ZjTzSupervisorMonthlyMeeting zjTzSupervisorMonthlyMeeting) {
        return zjTzSupervisorMonthlyMeetingService.saveZjTzSupervisorMonthlyMeeting(zjTzSupervisorMonthlyMeeting);
    }

    @ApiOperation(value="更新监理月度例会", notes="更新监理月度例会")
    @ApiImplicitParam(name = "zjTzSupervisorMonthlyMeeting", value = "监理月度例会entity", dataType = "ZjTzSupervisorMonthlyMeeting")
    @RequireToken
    @PostMapping("/updateZjTzSupervisorMonthlyMeeting")
    public ResponseEntity updateZjTzSupervisorMonthlyMeeting(@RequestBody(required = false) ZjTzSupervisorMonthlyMeeting zjTzSupervisorMonthlyMeeting) {
        return zjTzSupervisorMonthlyMeetingService.updateZjTzSupervisorMonthlyMeeting(zjTzSupervisorMonthlyMeeting);
    }
    
    @ApiOperation(value="新增监理月度例会（增加附件）", notes="新增监理月度例会（增加附件）")
    @ApiImplicitParam(name = "zjTzSupervisorMonthlyMeeting", value = "监理月度例会entity", dataType = "ZjTzSupervisorMonthlyMeeting")
    @RequireToken
    @PostMapping("/addZjTzSupervisorMonthlyMeetingAddFile")
    public ResponseEntity addZjTzSupervisorMonthlyMeetingAddFile(@RequestBody(required = false) ZjTzSupervisorMonthlyMeeting zjTzSupervisorMonthlyMeeting) {
    	return zjTzSupervisorMonthlyMeetingService.saveZjTzSupervisorMonthlyMeetingAddFile(zjTzSupervisorMonthlyMeeting);
    }
    
    @ApiOperation(value="更新监理月度例会（增加附件）", notes="更新监理月度例会（增加附件）")
    @ApiImplicitParam(name = "zjTzSupervisorMonthlyMeeting", value = "监理月度例会entity", dataType = "ZjTzSupervisorMonthlyMeeting")
    @RequireToken
    @PostMapping("/updateZjTzSupervisorMonthlyMeetingAddFile")
    public ResponseEntity updateZjTzSupervisorMonthlyMeetingAddFile(@RequestBody(required = false) ZjTzSupervisorMonthlyMeeting zjTzSupervisorMonthlyMeeting) {
    	return zjTzSupervisorMonthlyMeetingService.updateZjTzSupervisorMonthlyMeetingAddFile(zjTzSupervisorMonthlyMeeting);
    }

    @ApiOperation(value="删除监理月度例会", notes="删除监理月度例会")
    @ApiImplicitParam(name = "zjTzSupervisorMonthlyMeetingList", value = "监理月度例会List", dataType = "List<ZjTzSupervisorMonthlyMeeting>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZjTzSupervisorMonthlyMeeting")
    public ResponseEntity batchDeleteUpdateZjTzSupervisorMonthlyMeeting(@RequestBody(required = false) List<ZjTzSupervisorMonthlyMeeting> zjTzSupervisorMonthlyMeetingList) {
        return zjTzSupervisorMonthlyMeetingService.batchDeleteUpdateZjTzSupervisorMonthlyMeeting(zjTzSupervisorMonthlyMeetingList);
    }

}
