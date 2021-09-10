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
import com.apih5.mybatis.pojo.ZxSfExam;
import com.apih5.service.ZxSfExamService;

@RestController
public class ZxSfExamController {

    @Autowired(required = true)
    private ZxSfExamService zxSfExamService;

    @ApiOperation(value="查询安全管理综合考核", notes="查询安全管理综合考核")
    @ApiImplicitParam(name = "zxSfExam", value = "安全管理综合考核entity", dataType = "ZxSfExam")
    @RequireToken
    @PostMapping("/getZxSfExamList")
    public ResponseEntity getZxSfExamList(@RequestBody(required = false) ZxSfExam zxSfExam) {
        return zxSfExamService.getZxSfExamListByCondition(zxSfExam);
    }

    @ApiOperation(value="查询详情安全管理综合考核", notes="查询详情安全管理综合考核")
    @ApiImplicitParam(name = "zxSfExam", value = "安全管理综合考核entity", dataType = "ZxSfExam")
    @RequireToken
    @PostMapping("/getZxSfExamDetail")
    public ResponseEntity getZxSfExamDetail(@RequestBody(required = false) ZxSfExam zxSfExam) {
        return zxSfExamService.getZxSfExamDetail(zxSfExam);
    }

    @ApiOperation(value="新增安全管理综合考核", notes="新增安全管理综合考核")
    @ApiImplicitParam(name = "zxSfExam", value = "安全管理综合考核entity", dataType = "ZxSfExam")
    @RequireToken
    @PostMapping("/addZxSfExam")
    public ResponseEntity addZxSfExam(@RequestBody(required = false) ZxSfExam zxSfExam) {
        return zxSfExamService.saveZxSfExam(zxSfExam);
    }

    @ApiOperation(value="更新安全管理综合考核", notes="更新安全管理综合考核")
    @ApiImplicitParam(name = "zxSfExam", value = "安全管理综合考核entity", dataType = "ZxSfExam")
    @RequireToken
    @PostMapping("/updateZxSfExam")
    public ResponseEntity updateZxSfExam(@RequestBody(required = false) ZxSfExam zxSfExam) {
        return zxSfExamService.updateZxSfExam(zxSfExam);
    }

    @ApiOperation(value="删除安全管理综合考核", notes="删除安全管理综合考核")
    @ApiImplicitParam(name = "zxSfExamList", value = "安全管理综合考核List", dataType = "List<ZxSfExam>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxSfExam")
    public ResponseEntity batchDeleteUpdateZxSfExam(@RequestBody(required = false) List<ZxSfExam> zxSfExamList) {
        return zxSfExamService.batchDeleteUpdateZxSfExam(zxSfExamList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    
    @ApiOperation(value="上报安全管理综合考核", notes="上报安全管理综合考核")
    @ApiImplicitParam(name = "zxSfExam", value = "安全管理综合考核entity", dataType = "ZxSfExam")
    @RequireToken
    @PostMapping("/updateZxSfExamStatus")
    public ResponseEntity updateZxSfExamStatus(@RequestBody(required = false) ZxSfExam zxSfExam) {
        return zxSfExamService.updateZxSfExamStatus(zxSfExam);
    }
    
    @ApiOperation(value="上报局安全管理综合考核", notes="上报安全管理综合考核")
    @ApiImplicitParam(name = "zxSfExam", value = "安全管理综合考核entity", dataType = "ZxSfExam")
    @RequireToken
    @PostMapping("/updateZxSfExamStatusju")
    public ResponseEntity updateZxSfExamStatusju(@RequestBody(required = false) ZxSfExam zxSfExam) {
        return zxSfExamService.updateZxSfExamStatusju(zxSfExam);
    }
}
