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
import com.apih5.mybatis.pojo.ZxCtWorkToMU;
import com.apih5.service.ZxCtWorkToMUService;

@RestController
public class ZxCtWorkToMUController {

    @Autowired(required = true)
    private ZxCtWorkToMUService zxCtWorkToMUService;

    @ApiOperation(value="查询业主合同管理-业主合同台账-管理单元清单挂接(原表iecc_WorkToMU", notes="查询业主合同管理-业主合同台账-管理单元清单挂接(原表iecc_WorkToMU")
    @ApiImplicitParam(name = "zxCtWorkToMU", value = "业主合同管理-业主合同台账-管理单元清单挂接(原表iecc_WorkToMUentity", dataType = "ZxCtWorkToMU")
    @RequireToken
    @PostMapping("/getZxCtWorkToMUList")
    public ResponseEntity getZxCtWorkToMUList(@RequestBody(required = false) ZxCtWorkToMU zxCtWorkToMU) {
        return zxCtWorkToMUService.getZxCtWorkToMUListByCondition(zxCtWorkToMU);
    }

    @ApiOperation(value="查询详情业主合同管理-业主合同台账-管理单元清单挂接(原表iecc_WorkToMU", notes="查询详情业主合同管理-业主合同台账-管理单元清单挂接(原表iecc_WorkToMU")
    @ApiImplicitParam(name = "zxCtWorkToMU", value = "业主合同管理-业主合同台账-管理单元清单挂接(原表iecc_WorkToMUentity", dataType = "ZxCtWorkToMU")
    @RequireToken
    @PostMapping("/getZxCtWorkToMUDetail")
    public ResponseEntity getZxCtWorkToMUDetail(@RequestBody(required = false) ZxCtWorkToMU zxCtWorkToMU) {
        return zxCtWorkToMUService.getZxCtWorkToMUDetail(zxCtWorkToMU);
    }

    @ApiOperation(value="新增业主合同管理-业主合同台账-管理单元清单挂接(原表iecc_WorkToMU", notes="新增业主合同管理-业主合同台账-管理单元清单挂接(原表iecc_WorkToMU")
    @ApiImplicitParam(name = "zxCtWorkToMU", value = "业主合同管理-业主合同台账-管理单元清单挂接(原表iecc_WorkToMUentity", dataType = "ZxCtWorkToMU")
    @RequireToken
    @PostMapping("/addZxCtWorkToMU")
    public ResponseEntity addZxCtWorkToMU(@RequestBody(required = false) ZxCtWorkToMU zxCtWorkToMU) {
        return zxCtWorkToMUService.saveZxCtWorkToMU(zxCtWorkToMU);
    }

    @ApiOperation(value="更新业主合同管理-业主合同台账-管理单元清单挂接(原表iecc_WorkToMU", notes="更新业主合同管理-业主合同台账-管理单元清单挂接(原表iecc_WorkToMU")
    @ApiImplicitParam(name = "zxCtWorkToMU", value = "业主合同管理-业主合同台账-管理单元清单挂接(原表iecc_WorkToMUentity", dataType = "ZxCtWorkToMU")
    @RequireToken
    @PostMapping("/updateZxCtWorkToMU")
    public ResponseEntity updateZxCtWorkToMU(@RequestBody(required = false) ZxCtWorkToMU zxCtWorkToMU) {
        return zxCtWorkToMUService.updateZxCtWorkToMU(zxCtWorkToMU);
    }

    @ApiOperation(value="删除业主合同管理-业主合同台账-管理单元清单挂接(原表iecc_WorkToMU", notes="删除业主合同管理-业主合同台账-管理单元清单挂接(原表iecc_WorkToMU")
    @ApiImplicitParam(name = "zxCtWorkToMUList", value = "业主合同管理-业主合同台账-管理单元清单挂接(原表iecc_WorkToMUList", dataType = "List<ZxCtWorkToMU>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxCtWorkToMU")
    public ResponseEntity batchDeleteUpdateZxCtWorkToMU(@RequestBody(required = false) List<ZxCtWorkToMU> zxCtWorkToMUList) {
        return zxCtWorkToMUService.batchDeleteUpdateZxCtWorkToMU(zxCtWorkToMUList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    
    @ApiOperation(value="清单导入时自动挂接管理单元", notes="清单导入时自动挂接管理单元")
    @ApiImplicitParam(name = "zxCtWorkToMUList", value = "业主合同管理-业主合同台账-管理单元清单挂接(原表iecc_WorkToMUList", dataType = "List<ZxCtWorkToMU>")
    @RequireToken
    @PostMapping("/addAllWorkToMUByOrgID")
    public ResponseEntity addAllWorkToMUByOrgID(@RequestBody(required = false) ZxCtWorkToMU zxCtWorkToMU) {
        return zxCtWorkToMUService.addAllWorkToMUByOrgID(zxCtWorkToMU);
    }
    
}
