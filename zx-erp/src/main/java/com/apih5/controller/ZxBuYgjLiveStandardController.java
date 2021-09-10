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
import com.apih5.mybatis.pojo.ZxBuYgjLiveStandard;
import com.apih5.service.ZxBuYgjLiveStandardService;

@RestController
public class ZxBuYgjLiveStandardController {

    @Autowired(required = true)
    private ZxBuYgjLiveStandardService zxBuYgjLiveStandardService;

    @ApiOperation(value="查询四项费用标准", notes="查询四项费用标准")
    @ApiImplicitParam(name = "zxBuYgjLiveStandard", value = "四项费用标准表entity", dataType = "ZxBuYgjLiveStandard")
    @RequireToken
    @PostMapping("/getZxBuYgjLiveStandardList")
    public ResponseEntity getZxBuYgjLiveStandardList(@RequestBody(required = false) ZxBuYgjLiveStandard zxBuYgjLiveStandard) {
        return zxBuYgjLiveStandardService.getZxBuYgjLiveStandardListByCondition(zxBuYgjLiveStandard);
    }

    @ApiOperation(value="查询详情四项费用标准", notes="查询详情四项费用标准")
    @ApiImplicitParam(name = "zxBuYgjLiveStandard", value = "四项费用标准表entity", dataType = "ZxBuYgjLiveStandard")
    @RequireToken
    @PostMapping("/getZxBuYgjLiveStandardDetail")
    public ResponseEntity getZxBuYgjLiveStandardDetail(@RequestBody(required = false) ZxBuYgjLiveStandard zxBuYgjLiveStandard) {
        return zxBuYgjLiveStandardService.getZxBuYgjLiveStandardDetail(zxBuYgjLiveStandard);
    }

    @ApiOperation(value="新增四项费用标准", notes="新增四项费用标准")
    @ApiImplicitParam(name = "zxBuYgjLiveStandard", value = "四项费用标准表entity", dataType = "ZxBuYgjLiveStandard")
    @RequireToken
    @PostMapping("/addZxBuYgjLiveStandard")
    public ResponseEntity addZxBuYgjLiveStandard(@RequestBody(required = false) ZxBuYgjLiveStandard zxBuYgjLiveStandard) {
        return zxBuYgjLiveStandardService.saveZxBuYgjLiveStandard(zxBuYgjLiveStandard);
    }

    @ApiOperation(value="更新四项费用标准", notes="更新四项费用标准")
    @ApiImplicitParam(name = "zxBuYgjLiveStandard", value = "四项费用标准表entity", dataType = "ZxBuYgjLiveStandard")
    @RequireToken
    @PostMapping("/updateZxBuYgjLiveStandard")
    public ResponseEntity updateZxBuYgjLiveStandard(@RequestBody(required = false) ZxBuYgjLiveStandard zxBuYgjLiveStandard) {
        return zxBuYgjLiveStandardService.updateZxBuYgjLiveStandard(zxBuYgjLiveStandard);
    }

    @ApiOperation(value="删除四项费用标准", notes="删除四项费用标准")
    @ApiImplicitParam(name = "zxBuYgjLiveStandardList", value = "四项费用标准表List", dataType = "List<ZxBuYgjLiveStandard>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxBuYgjLiveStandard")
    public ResponseEntity batchDeleteUpdateZxBuYgjLiveStandard(@RequestBody(required = false) List<ZxBuYgjLiveStandard> zxBuYgjLiveStandardList) {
        return zxBuYgjLiveStandardService.batchDeleteUpdateZxBuYgjLiveStandard(zxBuYgjLiveStandardList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓


    @ApiOperation(value="获取四项费用标准树", notes="获取四项费用标准树")
    @ApiImplicitParam(name = "zxBuYgjLiveStandard", value = "四项费用标准List", dataType = "ZxBuYgjLiveStandard")
    @RequireToken
    @PostMapping("/getZxBuYgjLiveStandardTree")
    public ResponseEntity getZxBuYgjLiveStandardTree(@RequestBody(required = false) ZxBuYgjLiveStandard zxBuYgjLiveStandard) {
        return zxBuYgjLiveStandardService.getZxBuYgjLiveStandardTree(zxBuYgjLiveStandard);
    }

    @ApiOperation(value="获取四项费用标准列表", notes="获取四项费用标准列表")
    @ApiImplicitParam(name = "zxBuYgjLiveStandard", value = "四项费用标准List", dataType = "ZxBuYgjLiveStandard")
    @RequireToken
    @PostMapping("/getZxBuYgjLiveStandardTreeList")
    public ResponseEntity getZxBuYgjLiveStandardTreeList(@RequestBody(required = false) ZxBuYgjLiveStandard zxBuYgjLiveStandard) {
        return zxBuYgjLiveStandardService.getZxBuYgjLiveStandardTreeList(zxBuYgjLiveStandard);
    }

}
