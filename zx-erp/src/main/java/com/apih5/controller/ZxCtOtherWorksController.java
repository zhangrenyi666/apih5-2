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
import com.apih5.mybatis.pojo.ZxCtOtherWorks;
import com.apih5.service.ZxCtOtherWorksService;

@RestController
public class ZxCtOtherWorksController {

    @Autowired(required = true)
    private ZxCtOtherWorksService zxCtOtherWorksService;

    @ApiOperation(value="查询其他合同评审清单明细", notes="查询其他合同评审清单明细")
    @ApiImplicitParam(name = "zxCtOtherWorks", value = "其他合同评审清单明细entity", dataType = "ZxCtOtherWorks")
    @RequireToken
    @PostMapping("/getZxCtOtherWorksList")
    public ResponseEntity getZxCtOtherWorksList(@RequestBody(required = false) ZxCtOtherWorks zxCtOtherWorks) {
        return zxCtOtherWorksService.getZxCtOtherWorksListByCondition(zxCtOtherWorks);
    }

    @ApiOperation(value="查询详情其他合同评审清单明细", notes="查询详情其他合同评审清单明细")
    @ApiImplicitParam(name = "zxCtOtherWorks", value = "其他合同评审清单明细entity", dataType = "ZxCtOtherWorks")
    @RequireToken
    @PostMapping("/getZxCtOtherWorksDetail")
    public ResponseEntity getZxCtOtherWorksDetail(@RequestBody(required = false) ZxCtOtherWorks zxCtOtherWorks) {
        return zxCtOtherWorksService.getZxCtOtherWorksDetail(zxCtOtherWorks);
    }

    @ApiOperation(value="新增其他合同评审清单明细", notes="新增其他合同评审清单明细")
    @ApiImplicitParam(name = "zxCtOtherWorks", value = "其他合同评审清单明细entity", dataType = "ZxCtOtherWorks")
    @RequireToken
    @PostMapping("/addZxCtOtherWorks")
    public ResponseEntity addZxCtOtherWorks(@RequestBody(required = false) ZxCtOtherWorks zxCtOtherWorks) {
        return zxCtOtherWorksService.saveZxCtOtherWorks(zxCtOtherWorks);
    }

    @ApiOperation(value="更新其他合同评审清单明细", notes="更新其他合同评审清单明细")
    @ApiImplicitParam(name = "zxCtOtherWorks", value = "其他合同评审清单明细entity", dataType = "ZxCtOtherWorks")
    @RequireToken
    @PostMapping("/updateZxCtOtherWorks")
    public ResponseEntity updateZxCtOtherWorks(@RequestBody(required = false) ZxCtOtherWorks zxCtOtherWorks) {
        return zxCtOtherWorksService.updateZxCtOtherWorks(zxCtOtherWorks);
    }

    @ApiOperation(value="删除其他合同评审清单明细", notes="删除其他合同评审清单明细")
    @ApiImplicitParam(name = "zxCtOtherWorksList", value = "其他合同评审清单明细List", dataType = "List<ZxCtOtherWorks>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxCtOtherWorks")
    public ResponseEntity batchDeleteUpdateZxCtOtherWorks(@RequestBody(required = false) List<ZxCtOtherWorks> zxCtOtherWorksList) {
        return zxCtOtherWorksService.batchDeleteUpdateZxCtOtherWorks(zxCtOtherWorksList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

    @ApiOperation(value="其他类合同评审清单明细导入", notes="其他类合同评审清单明细导入")
    @ApiImplicitParam(name = "zxCtOtherWorks", value = "其他类合同评审清单明细entity", dataType = "ZxCtOtherWorks")
    @RequireToken
    @PostMapping("/importZxCtOtherWorks")
    public ResponseEntity importZxCtOtherWorks(@RequestBody(required = false) ZxCtOtherWorks zxCtOtherWorks) {
        return zxCtOtherWorksService.importZxCtOtherWorks(zxCtOtherWorks);
    }
}
