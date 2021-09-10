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
import com.apih5.mybatis.pojo.ZxSfSourceOfDanger;
import com.apih5.service.ZxSfSourceOfDangerService;

@RestController
public class ZxSfSourceOfDangerController {

    @Autowired(required = true)
    private ZxSfSourceOfDangerService zxSfSourceOfDangerService;

    @ApiOperation(value="查询重大危险源", notes="查询重大危险源")
    @ApiImplicitParam(name = "zxSfSourceOfDanger", value = "重大危险源entity", dataType = "ZxSfSourceOfDanger")
    @RequireToken
    @PostMapping("/getZxSfSourceOfDangerList")
    public ResponseEntity getZxSfSourceOfDangerList(@RequestBody(required = false) ZxSfSourceOfDanger zxSfSourceOfDanger) {
        return zxSfSourceOfDangerService.getZxSfSourceOfDangerListByCondition(zxSfSourceOfDanger);
    }

    @ApiOperation(value="查询详情重大危险源", notes="查询详情重大危险源")
    @ApiImplicitParam(name = "zxSfSourceOfDanger", value = "重大危险源entity", dataType = "ZxSfSourceOfDanger")
    @RequireToken
    @PostMapping("/getZxSfSourceOfDangerDetail")
    public ResponseEntity getZxSfSourceOfDangerDetail(@RequestBody(required = false) ZxSfSourceOfDanger zxSfSourceOfDanger) {
        return zxSfSourceOfDangerService.getZxSfSourceOfDangerDetail(zxSfSourceOfDanger);
    }

    @ApiOperation(value="新增重大危险源", notes="新增重大危险源")
    @ApiImplicitParam(name = "zxSfSourceOfDanger", value = "重大危险源entity", dataType = "ZxSfSourceOfDanger")
    @RequireToken
    @PostMapping("/addZxSfSourceOfDanger")
    public ResponseEntity addZxSfSourceOfDanger(@RequestBody(required = false) ZxSfSourceOfDanger zxSfSourceOfDanger) {
        return zxSfSourceOfDangerService.saveZxSfSourceOfDanger(zxSfSourceOfDanger);
    }

    @ApiOperation(value="更新重大危险源", notes="更新重大危险源")
    @ApiImplicitParam(name = "zxSfSourceOfDanger", value = "重大危险源entity", dataType = "ZxSfSourceOfDanger")
    @RequireToken
    @PostMapping("/updateZxSfSourceOfDanger")
    public ResponseEntity updateZxSfSourceOfDanger(@RequestBody(required = false) ZxSfSourceOfDanger zxSfSourceOfDanger) {
        return zxSfSourceOfDangerService.updateZxSfSourceOfDanger(zxSfSourceOfDanger);
    }

    @ApiOperation(value="删除重大危险源", notes="删除重大危险源")
    @ApiImplicitParam(name = "zxSfSourceOfDangerList", value = "重大危险源List", dataType = "List<ZxSfSourceOfDanger>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxSfSourceOfDanger")
    public ResponseEntity batchDeleteUpdateZxSfSourceOfDanger(@RequestBody(required = false) List<ZxSfSourceOfDanger> zxSfSourceOfDangerList) {
        return zxSfSourceOfDangerService.batchDeleteUpdateZxSfSourceOfDanger(zxSfSourceOfDangerList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
