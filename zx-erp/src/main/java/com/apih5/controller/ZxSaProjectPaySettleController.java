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
import com.apih5.mybatis.pojo.ZxSaProjectPaySettle;
import com.apih5.service.ZxSaProjectPaySettleService;

@RestController
public class ZxSaProjectPaySettleController {

    @Autowired(required = true)
    private ZxSaProjectPaySettleService zxSaProjectPaySettleService;

    @ApiOperation(value="查询结算管理-工程类结算-工程类结算单支付项(原表iesa_ProjectPaySettle)", notes="查询结算管理-工程类结算-工程类结算单支付项(原表iesa_ProjectPaySettle)")
    @ApiImplicitParam(name = "zxSaProjectPaySettle", value = "结算管理-工程类结算-工程类结算单支付项(原表iesa_ProjectPaySettle)entity", dataType = "ZxSaProjectPaySettle")
    @RequireToken
    @PostMapping("/getZxSaProjectPaySettleList")
    public ResponseEntity getZxSaProjectPaySettleList(@RequestBody(required = false) ZxSaProjectPaySettle zxSaProjectPaySettle) {
        return zxSaProjectPaySettleService.getZxSaProjectPaySettleListByCondition(zxSaProjectPaySettle);
    }

    @ApiOperation(value="查询详情结算管理-工程类结算-工程类结算单支付项(原表iesa_ProjectPaySettle)", notes="查询详情结算管理-工程类结算-工程类结算单支付项(原表iesa_ProjectPaySettle)")
    @ApiImplicitParam(name = "zxSaProjectPaySettle", value = "结算管理-工程类结算-工程类结算单支付项(原表iesa_ProjectPaySettle)entity", dataType = "ZxSaProjectPaySettle")
    @RequireToken
    @PostMapping("/getZxSaProjectPaySettleDetail")
    public ResponseEntity getZxSaProjectPaySettleDetail(@RequestBody(required = false) ZxSaProjectPaySettle zxSaProjectPaySettle) {
        return zxSaProjectPaySettleService.getZxSaProjectPaySettleDetail(zxSaProjectPaySettle);
    }

    @ApiOperation(value="新增结算管理-工程类结算-工程类结算单支付项(原表iesa_ProjectPaySettle)", notes="新增结算管理-工程类结算-工程类结算单支付项(原表iesa_ProjectPaySettle)")
    @ApiImplicitParam(name = "zxSaProjectPaySettle", value = "结算管理-工程类结算-工程类结算单支付项(原表iesa_ProjectPaySettle)entity", dataType = "ZxSaProjectPaySettle")
    @RequireToken
    @PostMapping("/addZxSaProjectPaySettle")
    public ResponseEntity addZxSaProjectPaySettle(@RequestBody(required = false) ZxSaProjectPaySettle zxSaProjectPaySettle) {
        return zxSaProjectPaySettleService.saveZxSaProjectPaySettle(zxSaProjectPaySettle);
    }

    @ApiOperation(value="更新结算管理-工程类结算-工程类结算单支付项(原表iesa_ProjectPaySettle)", notes="更新结算管理-工程类结算-工程类结算单支付项(原表iesa_ProjectPaySettle)")
    @ApiImplicitParam(name = "zxSaProjectPaySettle", value = "结算管理-工程类结算-工程类结算单支付项(原表iesa_ProjectPaySettle)entity", dataType = "ZxSaProjectPaySettle")
    @RequireToken
    @PostMapping("/updateZxSaProjectPaySettle")
    public ResponseEntity updateZxSaProjectPaySettle(@RequestBody(required = false) ZxSaProjectPaySettle zxSaProjectPaySettle) {
        return zxSaProjectPaySettleService.updateZxSaProjectPaySettle(zxSaProjectPaySettle);
    }

    @ApiOperation(value="删除结算管理-工程类结算-工程类结算单支付项(原表iesa_ProjectPaySettle)", notes="删除结算管理-工程类结算-工程类结算单支付项(原表iesa_ProjectPaySettle)")
    @ApiImplicitParam(name = "zxSaProjectPaySettleList", value = "结算管理-工程类结算-工程类结算单支付项(原表iesa_ProjectPaySettle)List", dataType = "List<ZxSaProjectPaySettle>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxSaProjectPaySettle")
    public ResponseEntity batchDeleteUpdateZxSaProjectPaySettle(@RequestBody(required = false) List<ZxSaProjectPaySettle> zxSaProjectPaySettleList) {
        return zxSaProjectPaySettleService.batchDeleteUpdateZxSaProjectPaySettle(zxSaProjectPaySettleList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
