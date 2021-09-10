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
import com.apih5.mybatis.pojo.ZxSfJuExam;
import com.apih5.service.ZxSfJuExamService;

@RestController
public class ZxSfJuExamController {

    @Autowired(required = true)
    private ZxSfJuExamService zxSfJuExamService;

    @ApiOperation(value="查询安全管理综合考核（局）", notes="查询安全管理综合考核（局）")
    @ApiImplicitParam(name = "zxSfJuExam", value = "安全管理综合考核（局）entity", dataType = "ZxSfJuExam")
    @RequireToken
    @PostMapping("/getZxSfJuExamList")
    public ResponseEntity getZxSfJuExamList(@RequestBody(required = false) ZxSfJuExam zxSfJuExam) {
        return zxSfJuExamService.getZxSfJuExamListByCondition(zxSfJuExam);
    }

    @ApiOperation(value="查询详情安全管理综合考核（局）", notes="查询详情安全管理综合考核（局）")
    @ApiImplicitParam(name = "zxSfJuExam", value = "安全管理综合考核（局）entity", dataType = "ZxSfJuExam")
    @RequireToken
    @PostMapping("/getZxSfJuExamDetail")
    public ResponseEntity getZxSfJuExamDetail(@RequestBody(required = false) ZxSfJuExam zxSfJuExam) {
        return zxSfJuExamService.getZxSfJuExamDetail(zxSfJuExam);
    }

    @ApiOperation(value="新增安全管理综合考核（局）", notes="新增安全管理综合考核（局）")
    @ApiImplicitParam(name = "zxSfJuExam", value = "安全管理综合考核（局）entity", dataType = "ZxSfJuExam")
    @RequireToken
    @PostMapping("/addZxSfJuExam")
    public ResponseEntity addZxSfJuExam(@RequestBody(required = false) ZxSfJuExam zxSfJuExam) {
        return zxSfJuExamService.saveZxSfJuExam(zxSfJuExam);
    }

    @ApiOperation(value="更新安全管理综合考核（局）", notes="更新安全管理综合考核（局）")
    @ApiImplicitParam(name = "zxSfJuExam", value = "安全管理综合考核（局）entity", dataType = "ZxSfJuExam")
    @RequireToken
    @PostMapping("/updateZxSfJuExam")
    public ResponseEntity updateZxSfJuExam(@RequestBody(required = false) ZxSfJuExam zxSfJuExam) {
        return zxSfJuExamService.updateZxSfJuExam(zxSfJuExam);
    }

    @ApiOperation(value="删除安全管理综合考核（局）", notes="删除安全管理综合考核（局）")
    @ApiImplicitParam(name = "zxSfJuExamList", value = "安全管理综合考核（局）List", dataType = "List<ZxSfJuExam>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxSfJuExam")
    public ResponseEntity batchDeleteUpdateZxSfJuExam(@RequestBody(required = false) List<ZxSfJuExam> zxSfJuExamList) {
        return zxSfJuExamService.batchDeleteUpdateZxSfJuExam(zxSfJuExamList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
