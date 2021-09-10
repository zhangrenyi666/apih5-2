package com.apih5.controller;

import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.apih5.framework.annotation.RequireToken;
import com.apih5.mybatis.pojo.ZxSaCostRatio;
import com.apih5.utils.ZxErpUtil;
import com.google.common.collect.Lists;

import cn.hutool.json.JSONObject;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 * 结算管理-数据填报-累计结算成本比指标填报（原表iesa_CostRatio） 前端控制器
 * </p>
 *
 * @author jobob
 * @since 2021-03-17
 */
@RestController
public class ZxSaCostRatioController {
    
    @ApiOperation(value = "查询集中结算管控指标输出台账", notes = "查询集中结算管控指标输出台账")
    @ApiImplicitParam(name = "zxSaCostRatio", value = "累计结算成本比指标填报entity", dataType = "ZxSaCostRatio")
    @RequireToken
    @PostMapping("/getZxSaSettleControlLedgerList")
    public Object getZxSaSettleControlLedgerList(@RequestBody(required = false) ZxSaCostRatio zxSaCostRatio) {
        String userId = "";//TokenUtils.getUserId(request);
        JSONObject jsonObject = new JSONObject(zxSaCostRatio);
        Object object = ZxErpUtil.getApiContent(userId, "getZxSaSettleControlLedgerList", jsonObject);
        return object;
    }
}
