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
import com.apih5.mybatis.pojo.ZxCtContrDetailAttr;
import com.apih5.service.ZxCtContrDetailAttrService;

@RestController
public class ZxCtContrDetailAttrController {

    @Autowired(required = true)
    private ZxCtContrDetailAttrService zxCtContrDetailAttrService;

    @ApiOperation(value="查询业主合同管理-业主合同台账-子表（保证金", notes="查询业主合同管理-业主合同台账-子表（保证金")
    @ApiImplicitParam(name = "zxCtContrDetailAttr", value = "业主合同管理-业主合同台账-子表（保证金entity", dataType = "ZxCtContrDetailAttr")
    @RequireToken
    @PostMapping("/getZxCtContrDetailAttrList")
    public ResponseEntity getZxCtContrDetailAttrList(@RequestBody(required = false) ZxCtContrDetailAttr zxCtContrDetailAttr) {
        return zxCtContrDetailAttrService.getZxCtContrDetailAttrListByCondition(zxCtContrDetailAttr);
    }

    @ApiOperation(value="查询详情业主合同管理-业主合同台账-子表（保证金", notes="查询详情业主合同管理-业主合同台账-子表（保证金")
    @ApiImplicitParam(name = "zxCtContrDetailAttr", value = "业主合同管理-业主合同台账-子表（保证金entity", dataType = "ZxCtContrDetailAttr")
    @RequireToken
    @PostMapping("/getZxCtContrDetailAttrDetail")
    public ResponseEntity getZxCtContrDetailAttrDetail(@RequestBody(required = false) ZxCtContrDetailAttr zxCtContrDetailAttr) {
        return zxCtContrDetailAttrService.getZxCtContrDetailAttrDetail(zxCtContrDetailAttr);
    }

    @ApiOperation(value="新增业主合同管理-业主合同台账-子表（保证金", notes="新增业主合同管理-业主合同台账-子表（保证金")
    @ApiImplicitParam(name = "zxCtContrDetailAttr", value = "业主合同管理-业主合同台账-子表（保证金entity", dataType = "ZxCtContrDetailAttr")
    @RequireToken
    @PostMapping("/addZxCtContrDetailAttr")
    public ResponseEntity addZxCtContrDetailAttr(@RequestBody(required = false) ZxCtContrDetailAttr zxCtContrDetailAttr) {
        return zxCtContrDetailAttrService.saveZxCtContrDetailAttr(zxCtContrDetailAttr);
    }

    @ApiOperation(value="更新业主合同管理-业主合同台账-子表（保证金", notes="更新业主合同管理-业主合同台账-子表（保证金")
    @ApiImplicitParam(name = "zxCtContrDetailAttr", value = "业主合同管理-业主合同台账-子表（保证金entity", dataType = "ZxCtContrDetailAttr")
    @RequireToken
    @PostMapping("/updateZxCtContrDetailAttr")
    public ResponseEntity updateZxCtContrDetailAttr(@RequestBody(required = false) ZxCtContrDetailAttr zxCtContrDetailAttr) {
        return zxCtContrDetailAttrService.updateZxCtContrDetailAttr(zxCtContrDetailAttr);
    }

    @ApiOperation(value="删除业主合同管理-业主合同台账-子表（保证金", notes="删除业主合同管理-业主合同台账-子表（保证金")
    @ApiImplicitParam(name = "zxCtContrDetailAttrList", value = "业主合同管理-业主合同台账-子表（保证金List", dataType = "List<ZxCtContrDetailAttr>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxCtContrDetailAttr")
    public ResponseEntity batchDeleteUpdateZxCtContrDetailAttr(@RequestBody(required = false) List<ZxCtContrDetailAttr> zxCtContrDetailAttrList) {
        return zxCtContrDetailAttrService.batchDeleteUpdateZxCtContrDetailAttr(zxCtContrDetailAttrList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

    @ApiOperation(value="删除业主合同管理-业主合同台账-子表（保证金", notes="删除业主合同管理-业主合同台账-子表（保证金")
    @ApiImplicitParam(name = "zxCtContrDetailAttrList", value = "业主合同管理-业主合同台账-子表（保证金List", dataType = "List<ZxCtContrDetailAttr>")
    @RequireToken
    @PostMapping("/deleteAllZxCtContrDetailAttr")
    public ResponseEntity deleteAllZxCtContrDetailAttr(@RequestBody(required = false) ZxCtContrDetailAttr zxCtContrDetailAttr) {
        return zxCtContrDetailAttrService.deleteAllZxCtContrDetailAttr(zxCtContrDetailAttr);
    }
    
}
