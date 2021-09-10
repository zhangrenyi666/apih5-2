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
import com.apih5.mybatis.pojo.ZxSfJuAccessRoom;
import com.apih5.service.ZxSfJuAccessRoomService;

@RestController
public class ZxSfJuAccessRoomController {

    @Autowired(required = true)
    private ZxSfJuAccessRoomService zxSfJuAccessRoomService;

    @ApiOperation(value="查询局考核库", notes="查询局考核库")
    @ApiImplicitParam(name = "zxSfJuAccessRoom", value = "局考核库entity", dataType = "ZxSfJuAccessRoom")
    @RequireToken
    @PostMapping("/getZxSfJuAccessRoomList")
    public ResponseEntity getZxSfJuAccessRoomList(@RequestBody(required = false) ZxSfJuAccessRoom zxSfJuAccessRoom) {
        return zxSfJuAccessRoomService.getZxSfJuAccessRoomListByCondition(zxSfJuAccessRoom);
    }

    @ApiOperation(value="查询详情局考核库", notes="查询详情局考核库")
    @ApiImplicitParam(name = "zxSfJuAccessRoom", value = "局考核库entity", dataType = "ZxSfJuAccessRoom")
    @RequireToken
    @PostMapping("/getZxSfJuAccessRoomDetail")
    public ResponseEntity getZxSfJuAccessRoomDetail(@RequestBody(required = false) ZxSfJuAccessRoom zxSfJuAccessRoom) {
        return zxSfJuAccessRoomService.getZxSfJuAccessRoomDetail(zxSfJuAccessRoom);
    }

    @ApiOperation(value="新增局考核库", notes="新增局考核库")
    @ApiImplicitParam(name = "zxSfJuAccessRoom", value = "局考核库entity", dataType = "ZxSfJuAccessRoom")
    @RequireToken
    @PostMapping("/addZxSfJuAccessRoom")
    public ResponseEntity addZxSfJuAccessRoom(@RequestBody(required = false) ZxSfJuAccessRoom zxSfJuAccessRoom) {
        return zxSfJuAccessRoomService.saveZxSfJuAccessRoom(zxSfJuAccessRoom);
    }

    @ApiOperation(value="更新局考核库", notes="更新局考核库")
    @ApiImplicitParam(name = "zxSfJuAccessRoom", value = "局考核库entity", dataType = "ZxSfJuAccessRoom")
    @RequireToken
    @PostMapping("/updateZxSfJuAccessRoom")
    public ResponseEntity updateZxSfJuAccessRoom(@RequestBody(required = false) ZxSfJuAccessRoom zxSfJuAccessRoom) {
        return zxSfJuAccessRoomService.updateZxSfJuAccessRoom(zxSfJuAccessRoom);
    }

    @ApiOperation(value="删除局考核库", notes="删除局考核库")
    @ApiImplicitParam(name = "zxSfJuAccessRoomList", value = "局考核库List", dataType = "List<ZxSfJuAccessRoom>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxSfJuAccessRoom")
    public ResponseEntity batchDeleteUpdateZxSfJuAccessRoom(@RequestBody(required = false) List<ZxSfJuAccessRoom> zxSfJuAccessRoomList) {
        return zxSfJuAccessRoomService.batchDeleteUpdateZxSfJuAccessRoom(zxSfJuAccessRoomList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
