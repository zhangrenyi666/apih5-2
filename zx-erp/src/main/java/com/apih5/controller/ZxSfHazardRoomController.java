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
import com.apih5.mybatis.pojo.ZxSfHazardRoom;
import com.apih5.service.ZxSfHazardRoomService;

@RestController
public class ZxSfHazardRoomController {

    @Autowired(required = true)
    private ZxSfHazardRoomService zxSfHazardRoomService;

    @ApiOperation(value="查询危险源台账库", notes="查询危险源台账库")
    @ApiImplicitParam(name = "zxSfHazardRoom", value = "危险源台账库entity", dataType = "ZxSfHazardRoom")
    @RequireToken
    @PostMapping("/getZxSfHazardRoomList")
    public ResponseEntity getZxSfHazardRoomList(@RequestBody(required = false) ZxSfHazardRoom zxSfHazardRoom) {
        return zxSfHazardRoomService.getZxSfHazardRoomListByCondition(zxSfHazardRoom);
    }

    @ApiOperation(value="查询详情危险源台账库", notes="查询详情危险源台账库")
    @ApiImplicitParam(name = "zxSfHazardRoom", value = "危险源台账库entity", dataType = "ZxSfHazardRoom")
    @RequireToken
    @PostMapping("/getZxSfHazardRoomDetail")
    public ResponseEntity getZxSfHazardRoomDetail(@RequestBody(required = false) ZxSfHazardRoom zxSfHazardRoom) {
        return zxSfHazardRoomService.getZxSfHazardRoomDetail(zxSfHazardRoom);
    }

    @ApiOperation(value="新增危险源台账库", notes="新增危险源台账库")
    @ApiImplicitParam(name = "zxSfHazardRoom", value = "危险源台账库entity", dataType = "ZxSfHazardRoom")
    @RequireToken
    @PostMapping("/addZxSfHazardRoom")
    public ResponseEntity addZxSfHazardRoom(@RequestBody(required = false) ZxSfHazardRoom zxSfHazardRoom) {
        return zxSfHazardRoomService.saveZxSfHazardRoom(zxSfHazardRoom);
    }

    @ApiOperation(value="更新危险源台账库", notes="更新危险源台账库")
    @ApiImplicitParam(name = "zxSfHazardRoom", value = "危险源台账库entity", dataType = "ZxSfHazardRoom")
    @RequireToken
    @PostMapping("/updateZxSfHazardRoom")
    public ResponseEntity updateZxSfHazardRoom(@RequestBody(required = false) ZxSfHazardRoom zxSfHazardRoom) {
        return zxSfHazardRoomService.updateZxSfHazardRoom(zxSfHazardRoom);
    }

    @ApiOperation(value="删除危险源台账库", notes="删除危险源台账库")
    @ApiImplicitParam(name = "zxSfHazardRoomList", value = "危险源台账库List", dataType = "List<ZxSfHazardRoom>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxSfHazardRoom")
    public ResponseEntity batchDeleteUpdateZxSfHazardRoom(@RequestBody(required = false) List<ZxSfHazardRoom> zxSfHazardRoomList) {
        return zxSfHazardRoomService.batchDeleteUpdateZxSfHazardRoom(zxSfHazardRoomList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
