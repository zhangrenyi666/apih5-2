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
import com.apih5.mybatis.pojo.ZxSaProjectWorkSettle;
import com.apih5.service.ZxSaProjectWorkSettleService;

@RestController
public class ZxSaProjectWorkSettleController {

    @Autowired(required = true)
    private ZxSaProjectWorkSettleService zxSaProjectWorkSettleService;

    @ApiOperation(value="查询结算管理-工程类结算-工程类结算单结算清单(原表iesa_ProjectWorkSettle)", notes="查询结算管理-工程类结算-工程类结算单结算清单(原表iesa_ProjectWorkSettle)")
    @ApiImplicitParam(name = "zxSaProjectWorkSettle", value = "结算管理-工程类结算-工程类结算单结算清单(原表iesa_ProjectWorkSettle)entity", dataType = "ZxSaProjectWorkSettle")
    @RequireToken
    @PostMapping("/getZxSaProjectWorkSettleList")
    public ResponseEntity getZxSaProjectWorkSettleList(@RequestBody(required = false) ZxSaProjectWorkSettle zxSaProjectWorkSettle) {
        return zxSaProjectWorkSettleService.getZxSaProjectWorkSettleListByCondition(zxSaProjectWorkSettle);
    }

    @ApiOperation(value="查询详情结算管理-工程类结算-工程类结算单结算清单(原表iesa_ProjectWorkSettle)", notes="查询详情结算管理-工程类结算-工程类结算单结算清单(原表iesa_ProjectWorkSettle)")
    @ApiImplicitParam(name = "zxSaProjectWorkSettle", value = "结算管理-工程类结算-工程类结算单结算清单(原表iesa_ProjectWorkSettle)entity", dataType = "ZxSaProjectWorkSettle")
    @RequireToken
    @PostMapping("/getZxSaProjectWorkSettleDetail")
    public ResponseEntity getZxSaProjectWorkSettleDetail(@RequestBody(required = false) ZxSaProjectWorkSettle zxSaProjectWorkSettle) {
        return zxSaProjectWorkSettleService.getZxSaProjectWorkSettleDetail(zxSaProjectWorkSettle);
    }

    @ApiOperation(value="新增结算管理-工程类结算-工程类结算单结算清单(原表iesa_ProjectWorkSettle)", notes="新增结算管理-工程类结算-工程类结算单结算清单(原表iesa_ProjectWorkSettle)")
    @ApiImplicitParam(name = "zxSaProjectWorkSettle", value = "结算管理-工程类结算-工程类结算单结算清单(原表iesa_ProjectWorkSettle)entity", dataType = "ZxSaProjectWorkSettle")
    @RequireToken
    @PostMapping("/addZxSaProjectWorkSettle")
    public ResponseEntity addZxSaProjectWorkSettle(@RequestBody(required = false) ZxSaProjectWorkSettle zxSaProjectWorkSettle) {
        return zxSaProjectWorkSettleService.saveZxSaProjectWorkSettle(zxSaProjectWorkSettle);
    }

    @ApiOperation(value="更新结算管理-工程类结算-工程类结算单结算清单(原表iesa_ProjectWorkSettle)", notes="更新结算管理-工程类结算-工程类结算单结算清单(原表iesa_ProjectWorkSettle)")
    @ApiImplicitParam(name = "zxSaProjectWorkSettle", value = "结算管理-工程类结算-工程类结算单结算清单(原表iesa_ProjectWorkSettle)entity", dataType = "ZxSaProjectWorkSettle")
    @RequireToken
    @PostMapping("/updateZxSaProjectWorkSettle")
    public ResponseEntity updateZxSaProjectWorkSettle(@RequestBody(required = false) ZxSaProjectWorkSettle zxSaProjectWorkSettle) {
        return zxSaProjectWorkSettleService.updateZxSaProjectWorkSettle(zxSaProjectWorkSettle);
    }

    @ApiOperation(value="删除结算管理-工程类结算-工程类结算单结算清单(原表iesa_ProjectWorkSettle)", notes="删除结算管理-工程类结算-工程类结算单结算清单(原表iesa_ProjectWorkSettle)")
    @ApiImplicitParam(name = "zxSaProjectWorkSettleList", value = "结算管理-工程类结算-工程类结算单结算清单(原表iesa_ProjectWorkSettle)List", dataType = "List<ZxSaProjectWorkSettle>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxSaProjectWorkSettle")
    public ResponseEntity batchDeleteUpdateZxSaProjectWorkSettle(@RequestBody(required = false) List<ZxSaProjectWorkSettle> zxSaProjectWorkSettleList) {
        return zxSaProjectWorkSettleService.batchDeleteUpdateZxSaProjectWorkSettle(zxSaProjectWorkSettleList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
