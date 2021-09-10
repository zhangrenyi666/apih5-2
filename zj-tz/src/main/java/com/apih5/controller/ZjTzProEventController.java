package com.apih5.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import com.apih5.framework.annotation.RequireToken;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjTzProEvent;
import com.apih5.service.ZjTzProEventService;

@RestController
public class ZjTzProEventController {

    @Autowired(required = true)
    private ZjTzProEventService zjTzProEventService;

    @ApiOperation(value="查询项目大事记", notes="查询项目大事记")
    @ApiImplicitParam(name = "zjTzProEvent", value = "项目大事记entity", dataType = "ZjTzProEvent")
    @RequireToken
    @PostMapping("/getZjTzProEventList")
    public ResponseEntity getZjTzProEventList(@RequestBody(required = false) ZjTzProEvent zjTzProEvent) {
        return zjTzProEventService.getZjTzProEventListByCondition(zjTzProEvent);
    }

    @ApiOperation(value="查询详情项目大事记", notes="查询详情项目大事记")
    @ApiImplicitParam(name = "zjTzProEvent", value = "项目大事记entity", dataType = "ZjTzProEvent")
    @RequireToken
    @PostMapping("/getZjTzProEventDetails")
    public ResponseEntity getZjTzProEventDetails(@RequestBody(required = false) ZjTzProEvent zjTzProEvent) {
        return zjTzProEventService.getZjTzProEventDetails(zjTzProEvent);
    }

    @ApiOperation(value="新增项目大事记", notes="新增项目大事记")
    @ApiImplicitParam(name = "zjTzProEvent", value = "项目大事记entity", dataType = "ZjTzProEvent")
    @RequireToken
    @PostMapping("/addZjTzProEvent")
    public ResponseEntity addZjTzProEvent(@RequestBody(required = false) ZjTzProEvent zjTzProEvent) {
        return zjTzProEventService.saveZjTzProEvent(zjTzProEvent);
    }

    @ApiOperation(value="更新项目大事记", notes="更新项目大事记")
    @ApiImplicitParam(name = "zjTzProEvent", value = "项目大事记entity", dataType = "ZjTzProEvent")
    @RequireToken
    @PostMapping("/updateZjTzProEvent")
    public ResponseEntity updateZjTzProEvent(@RequestBody(required = false) ZjTzProEvent zjTzProEvent) {
        return zjTzProEventService.updateZjTzProEvent(zjTzProEvent);
    }

    @ApiOperation(value="删除项目大事记", notes="删除项目大事记")
    @ApiImplicitParam(name = "zjTzProEventList", value = "项目大事记List", dataType = "List<ZjTzProEvent>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZjTzProEvent")
    public ResponseEntity batchDeleteUpdateZjTzProEvent(@RequestBody(required = false) List<ZjTzProEvent> zjTzProEventList) {
        return zjTzProEventService.batchDeleteUpdateZjTzProEvent(zjTzProEventList);
    }
    
    @ApiOperation(value="广而告之项目大事记", notes="广而告之项目大事记")
    @ApiImplicitParam(name = "zjTzProEvent", value = "项目大事记entity", dataType = "ZjTzProEvent")
    @RequireToken
    @PostMapping("/toHomeShowZjTzProEvent")
    public ResponseEntity toHomeShowZjTzProEvent(@RequestBody(required = false) ZjTzProEvent zjTzProEvent) {
        return zjTzProEventService.toHomeShowZjTzProEvent(zjTzProEvent);
    }
    
    @ApiOperation(value="批量撤回项目大事记", notes="批量撤回项目大事记")
    @ApiImplicitParam(name = "zjTzProEvent", value = "项目大事记entity", dataType = "ZjTzProEvent")
    @RequireToken
    @PostMapping("/batchRecallZjTzProEvent")
    public ResponseEntity batchRecallZjTzProEvent(@RequestBody(required = false) List<ZjTzProEvent> zjTzProEventList) {
        return zjTzProEventService.batchRecallZjTzProEvent(zjTzProEventList);
    }
    
    @ApiOperation(value="导出项目大事记", notes="导出项目大事记")
    @ApiImplicitParam(name = "zjTzProEvent", value = "项目大事记entity", dataType = "ZjTzProEvent")
//    @RequireToken
    @PostMapping("/exportZjTzProEventList")
    public ResponseEntity exportZjTzProEventList(@RequestBody(required = false) ZjTzProEvent zjTzProEvent, HttpServletResponse response) {
        return zjTzProEventService.exportZjTzProEventList(zjTzProEvent, response);
    }

}
