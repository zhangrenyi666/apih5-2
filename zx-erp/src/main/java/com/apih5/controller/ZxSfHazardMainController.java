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
import com.apih5.mybatis.pojo.ZxSfHazardMain;
import com.apih5.service.ZxSfHazardMainService;

@RestController
public class ZxSfHazardMainController {

    @Autowired(required = true)
    private ZxSfHazardMainService zxSfHazardMainService;

    @ApiOperation(value="查询重大危险源", notes="查询重大危险源")
    @ApiImplicitParam(name = "zxSfHazardMain", value = "重大危险源entity", dataType = "ZxSfHazardMain")
    @RequireToken
    @PostMapping("/getZxSfHazardMainList")
    public ResponseEntity getZxSfHazardMainList(@RequestBody(required = false) ZxSfHazardMain zxSfHazardMain) {
        return zxSfHazardMainService.getZxSfHazardMainListByCondition(zxSfHazardMain);
    }

    @ApiOperation(value="查询详情重大危险源", notes="查询详情重大危险源")
    @ApiImplicitParam(name = "zxSfHazardMain", value = "重大危险源entity", dataType = "ZxSfHazardMain")
    @RequireToken
    @PostMapping("/getZxSfHazardMainDetail")
    public ResponseEntity getZxSfHazardMainDetail(@RequestBody(required = false) ZxSfHazardMain zxSfHazardMain) {
        return zxSfHazardMainService.getZxSfHazardMainDetail(zxSfHazardMain);
    }

    @ApiOperation(value="新增重大危险源", notes="新增重大危险源")
    @ApiImplicitParam(name = "zxSfHazardMain", value = "重大危险源entity", dataType = "ZxSfHazardMain")
    @RequireToken
    @PostMapping("/addZxSfHazardMain")
    public ResponseEntity addZxSfHazardMain(@RequestBody(required = false) ZxSfHazardMain zxSfHazardMain) {
        return zxSfHazardMainService.saveZxSfHazardMain(zxSfHazardMain);
    }

    @ApiOperation(value="更新重大危险源", notes="更新重大危险源")
    @ApiImplicitParam(name = "zxSfHazardMain", value = "重大危险源entity", dataType = "ZxSfHazardMain")
    @RequireToken
    @PostMapping("/updateZxSfHazardMain")
    public ResponseEntity updateZxSfHazardMain(@RequestBody(required = false) ZxSfHazardMain zxSfHazardMain) {
        return zxSfHazardMainService.updateZxSfHazardMain(zxSfHazardMain);
    }

    @ApiOperation(value="删除重大危险源", notes="删除重大危险源")
    @ApiImplicitParam(name = "zxSfHazardMainList", value = "重大危险源List", dataType = "List<ZxSfHazardMain>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxSfHazardMain")
    public ResponseEntity batchDeleteUpdateZxSfHazardMain(@RequestBody(required = false) List<ZxSfHazardMain> zxSfHazardMainList) {
        return zxSfHazardMainService.batchDeleteUpdateZxSfHazardMain(zxSfHazardMainList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
