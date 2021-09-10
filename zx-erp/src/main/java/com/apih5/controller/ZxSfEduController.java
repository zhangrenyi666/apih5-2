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
import com.apih5.mybatis.pojo.ZxSfEdu;
import com.apih5.service.ZxSfEduService;

@RestController
public class ZxSfEduController {

    @Autowired(required = true)
    private ZxSfEduService zxSfEduService;

    @ApiOperation(value="查询培训", notes="查询安全教育培训")
    @ApiImplicitParam(name = "zxSfEdu", value = "安全教育培训entity", dataType = "ZxSfEdu")
    @RequireToken
    @PostMapping("/getZxSfEduList")
    public ResponseEntity getZxSfEduList(@RequestBody(required = false) ZxSfEdu zxSfEdu) {
        return zxSfEduService.getZxSfEduListByCondition(zxSfEdu);
    }

    @ApiOperation(value="查询详情安全教育培训", notes="查询详情安全教育培训")
    @ApiImplicitParam(name = "zxSfEdu", value = "安全教育培训entity", dataType = "ZxSfEdu")
    @RequireToken
    @PostMapping("/getZxSfEduDetail")
    public ResponseEntity getZxSfEduDetail(@RequestBody(required = false) ZxSfEdu zxSfEdu) {
        return zxSfEduService.getZxSfEduDetail(zxSfEdu);
    }

    @ApiOperation(value="新增安全教育培训", notes="新增安全教育培训")
    @ApiImplicitParam(name = "zxSfEdu", value = "安全教育培训entity", dataType = "ZxSfEdu")
    @RequireToken
    @PostMapping("/addZxSfEdu")
    public ResponseEntity addZxSfEdu(@RequestBody(required = false) ZxSfEdu zxSfEdu) {
        return zxSfEduService.saveZxSfEdu(zxSfEdu);
    }

    @ApiOperation(value="更新安全教育培训", notes="更新安全教育培训")
    @ApiImplicitParam(name = "zxSfEdu", value = "安全教育培训entity", dataType = "ZxSfEdu")
    @RequireToken
    @PostMapping("/updateZxSfEdu")
    public ResponseEntity updateZxSfEdu(@RequestBody(required = false) ZxSfEdu zxSfEdu) {
        return zxSfEduService.updateZxSfEdu(zxSfEdu);
    }

    @ApiOperation(value="删除安全教育培训", notes="删除安全教育培训")
    @ApiImplicitParam(name = "zxSfEduList", value = "安全教育培训List", dataType = "List<ZxSfEdu>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxSfEdu")
    public ResponseEntity batchDeleteUpdateZxSfEdu(@RequestBody(required = false) List<ZxSfEdu> zxSfEduList) {
        return zxSfEduService.batchDeleteUpdateZxSfEdu(zxSfEduList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

    @ApiOperation(value="查询安全教育培训人员统计局级列表", notes="查询安全教育培训")
    @RequireToken
    @PostMapping("/getZxSfEduComList")
    public ResponseEntity getZxSfEduComList() {
        return zxSfEduService.getZxSfEduComList();
    }

    @ApiOperation(value="查询详情安全教育人员统计公司详情", notes="查询详情安全教育培训")
    @ApiImplicitParam(name = "zxSfEdu", value = "安全教育培训entity", dataType = "ZxSfEdu")
    @RequireToken
    @PostMapping("/getZxSfEduCom")
    public ResponseEntity getZxSfEduCom(@RequestBody(required = false) ZxSfEdu zxSfEdu) {
        return zxSfEduService.getZxSfEduCom(zxSfEdu);
    }

    @ApiOperation(value="查询详情安全教育人员统计项目（状态分类）", notes="查询详情安全教育人员统计项目")
    @ApiImplicitParam(name = "zxSfEdu", value = "安全教育培训entity", dataType = "ZxSfEdu")
    @RequireToken
    @PostMapping("/getZxSfEduOrgList")
    public ResponseEntity getZxSfEduOrgList(@RequestBody(required = false) ZxSfEdu zxSfEdu) {
        return zxSfEduService.getZxSfEduOrgList(zxSfEdu);
    }

    @ApiOperation(value="查询详情安全教育人员统计归档项目", notes="查询详情安全教育人员统计归档项目")
    @ApiImplicitParam(name = "zxSfEdu", value = "安全教育培训entity", dataType = "ZxSfEdu")
    @RequireToken
    @PostMapping("/getZxSfEduGuiDangList")
    public ResponseEntity getZxSfEduGuiDangList(@RequestBody(required = false) ZxSfEdu zxSfEdu) {
        return zxSfEduService.getZxSfEduGuiDangList(zxSfEdu);
    }

    @ApiOperation(value="查询详情安全教育人员统计交工项目", notes="查询详情安全教育人员统计交工项目")
    @ApiImplicitParam(name = "zxSfEdu", value = "安全教育培训entity", dataType = "ZxSfEdu")
    @RequireToken
    @PostMapping("/getZxSfEduJiaoGongList")
    public ResponseEntity getZxSfEduJiaoGongList(@RequestBody(required = false) ZxSfEdu zxSfEdu) {
        return zxSfEduService.getZxSfEduJiaoGongList(zxSfEdu);
    }

    @ApiOperation(value="查询详情安全教育人员统计完工项目", notes="查询详情安全教育人员统计完工项目")
    @ApiImplicitParam(name = "zxSfEdu", value = "安全教育培训entity", dataType = "ZxSfEdu")
    @RequireToken
    @PostMapping("/getZxSfEduWanGongList")
    public ResponseEntity getZxSfEduWanGongList(@RequestBody(required = false) ZxSfEdu zxSfEdu) {
        return zxSfEduService.getZxSfEduWanGongList(zxSfEdu);
    }

    @ApiOperation(value="查询详情安全教育人员统计开工项目", notes="查询详情安全教育人员统计开工项目")
    @ApiImplicitParam(name = "zxSfEdu", value = "安全教育培训entity", dataType = "ZxSfEdu")
    @RequireToken
    @PostMapping("/getZxSfEduKaiGongList")
    public ResponseEntity getZxSfEduKaiGongList(@RequestBody(required = false) ZxSfEdu zxSfEdu) {
        return zxSfEduService.getZxSfEduKaiGongList(zxSfEdu);
    }

    @ApiOperation(value="查询详情安全教育人员统计(局)", notes="查询详情安全教育人员统计(局)")
    @ApiImplicitParam(name = "zxSfEdu", value = "安全教育培训entity", dataType = "ZxSfEdu")
    @RequireToken
    @PostMapping("/getZxSfEduJuInfo")
    public ResponseEntity getZxSfEduJuInfo(@RequestBody(required = false) ZxSfEdu zxSfEdu) {
        return zxSfEduService.getZxSfEduJuInfo(zxSfEdu);
    }



}
