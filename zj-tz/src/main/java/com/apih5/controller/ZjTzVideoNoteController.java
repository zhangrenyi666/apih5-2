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
import com.apih5.mybatis.pojo.ZjTzVideoNote;
import com.apih5.service.ZjTzVideoNoteService;

@RestController
public class ZjTzVideoNoteController {

    @Autowired(required = true)
    private ZjTzVideoNoteService zjTzVideoNoteService;

    @ApiOperation(value="查询宣贯视频留言记录", notes="查询宣贯视频留言记录")
    @ApiImplicitParam(name = "zjTzVideoNote", value = "宣贯视频留言记录entity", dataType = "ZjTzVideoNote")
    @RequireToken
    @PostMapping("/getZjTzVideoNoteList")
    public ResponseEntity getZjTzVideoNoteList(@RequestBody(required = false) ZjTzVideoNote zjTzVideoNote) {
        return zjTzVideoNoteService.getZjTzVideoNoteListByCondition(zjTzVideoNote);
    }

    @ApiOperation(value="查询详情宣贯视频留言记录", notes="查询详情宣贯视频留言记录")
    @ApiImplicitParam(name = "zjTzVideoNote", value = "宣贯视频留言记录entity", dataType = "ZjTzVideoNote")
    @RequireToken
    @PostMapping("/getZjTzVideoNoteDetails")
    public ResponseEntity getZjTzVideoNoteDetails(@RequestBody(required = false) ZjTzVideoNote zjTzVideoNote) {
        return zjTzVideoNoteService.getZjTzVideoNoteDetails(zjTzVideoNote);
    }

    @ApiOperation(value="新增宣贯视频留言记录", notes="新增宣贯视频留言记录")
    @ApiImplicitParam(name = "zjTzVideoNote", value = "宣贯视频留言记录entity", dataType = "ZjTzVideoNote")
    @RequireToken
    @PostMapping("/addZjTzVideoNote")
    public ResponseEntity addZjTzVideoNote(@RequestBody(required = false) ZjTzVideoNote zjTzVideoNote) {
        return zjTzVideoNoteService.saveZjTzVideoNote(zjTzVideoNote);
    }

    @ApiOperation(value="更新宣贯视频留言记录", notes="更新宣贯视频留言记录")
    @ApiImplicitParam(name = "zjTzVideoNote", value = "宣贯视频留言记录entity", dataType = "ZjTzVideoNote")
    @RequireToken
    @PostMapping("/updateZjTzVideoNote")
    public ResponseEntity updateZjTzVideoNote(@RequestBody(required = false) ZjTzVideoNote zjTzVideoNote) {
        return zjTzVideoNoteService.updateZjTzVideoNote(zjTzVideoNote);
    }

    @ApiOperation(value="删除宣贯视频留言记录", notes="删除宣贯视频留言记录")
    @ApiImplicitParam(name = "zjTzVideoNoteList", value = "宣贯视频留言记录List", dataType = "List<ZjTzVideoNote>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZjTzVideoNote")
    public ResponseEntity batchDeleteUpdateZjTzVideoNote(@RequestBody(required = false) List<ZjTzVideoNote> zjTzVideoNoteList) {
        return zjTzVideoNoteService.batchDeleteUpdateZjTzVideoNote(zjTzVideoNoteList);
    }

}
