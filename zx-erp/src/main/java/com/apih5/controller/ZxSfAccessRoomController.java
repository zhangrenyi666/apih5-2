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
import com.apih5.mybatis.pojo.ZxSfAccessRoom;
import com.apih5.service.ZxSfAccessRoomService;

@RestController
public class ZxSfAccessRoomController {

    @Autowired(required = true)
    private ZxSfAccessRoomService zxSfAccessRoomService;

    @ApiOperation(value="查询考核库", notes="查询考核库")
    @ApiImplicitParam(name = "zxSfAccessRoom", value = "考核库entity", dataType = "ZxSfAccessRoom")
    @RequireToken
    @PostMapping("/getZxSfAccessRoomList")
    public ResponseEntity getZxSfAccessRoomList(@RequestBody(required = false) ZxSfAccessRoom zxSfAccessRoom) {
        return zxSfAccessRoomService.getZxSfAccessRoomListByCondition(zxSfAccessRoom);
    }

    @ApiOperation(value="查询详情考核库", notes="查询详情考核库")
    @ApiImplicitParam(name = "zxSfAccessRoom", value = "考核库entity", dataType = "ZxSfAccessRoom")
    @RequireToken
    @PostMapping("/getZxSfAccessRoomDetail")
    public ResponseEntity getZxSfAccessRoomDetail(@RequestBody(required = false) ZxSfAccessRoom zxSfAccessRoom) {
        return zxSfAccessRoomService.getZxSfAccessRoomDetail(zxSfAccessRoom);
    }

    @ApiOperation(value="新增考核库", notes="新增考核库")
    @ApiImplicitParam(name = "zxSfAccessRoom", value = "考核库entity", dataType = "ZxSfAccessRoom")
    @RequireToken
    @PostMapping("/addZxSfAccessRoom")
    public ResponseEntity addZxSfAccessRoom(@RequestBody(required = false) ZxSfAccessRoom zxSfAccessRoom) {
        return zxSfAccessRoomService.saveZxSfAccessRoom(zxSfAccessRoom);
    }

    @ApiOperation(value="更新考核库", notes="更新考核库")
    @ApiImplicitParam(name = "zxSfAccessRoom", value = "考核库entity", dataType = "ZxSfAccessRoom")
    @RequireToken
    @PostMapping("/updateZxSfAccessRoom")
    public ResponseEntity updateZxSfAccessRoom(@RequestBody(required = false) ZxSfAccessRoom zxSfAccessRoom) {
        return zxSfAccessRoomService.updateZxSfAccessRoom(zxSfAccessRoom);
    }

    @ApiOperation(value="删除考核库", notes="删除考核库")
    @ApiImplicitParam(name = "zxSfAccessRoomList", value = "考核库List", dataType = "List<ZxSfAccessRoom>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxSfAccessRoom")
    public ResponseEntity batchDeleteUpdateZxSfAccessRoom(@RequestBody(required = false) List<ZxSfAccessRoom> zxSfAccessRoomList) {
        return zxSfAccessRoomService.batchDeleteUpdateZxSfAccessRoom(zxSfAccessRoomList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
