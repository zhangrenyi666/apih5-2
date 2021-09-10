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
import com.apih5.mybatis.pojo.ZxSkTurnOverScrap;
import com.apih5.service.ZxSkTurnOverScrapService;

@RestController
public class ZxSkTurnOverScrapController {

    @Autowired(required = true)
    private ZxSkTurnOverScrapService zxSkTurnOverScrapService;

    @ApiOperation(value="查询周转材料报废", notes="查询周转材料报废")
    @ApiImplicitParam(name = "zxSkTurnOverScrap", value = "周转材料报废entity", dataType = "ZxSkTurnOverScrap")
    @RequireToken
    @PostMapping("/getZxSkTurnOverScrapList")
    public ResponseEntity getZxSkTurnOverScrapList(@RequestBody(required = false) ZxSkTurnOverScrap zxSkTurnOverScrap) {
        return zxSkTurnOverScrapService.getZxSkTurnOverScrapListByCondition(zxSkTurnOverScrap);
    }

    @ApiOperation(value="查询详情周转材料报废", notes="查询详情周转材料报废")
    @ApiImplicitParam(name = "zxSkTurnOverScrap", value = "周转材料报废entity", dataType = "ZxSkTurnOverScrap")
    @RequireToken
    @PostMapping("/getZxSkTurnOverScrapDetail")
    public ResponseEntity getZxSkTurnOverScrapDetail(@RequestBody(required = false) ZxSkTurnOverScrap zxSkTurnOverScrap) {
        return zxSkTurnOverScrapService.getZxSkTurnOverScrapDetail(zxSkTurnOverScrap);
    }

    @ApiOperation(value="新增周转材料报废", notes="新增周转材料报废")
    @ApiImplicitParam(name = "zxSkTurnOverScrap", value = "周转材料报废entity", dataType = "ZxSkTurnOverScrap")
    @RequireToken
    @PostMapping("/addZxSkTurnOverScrap")
    public ResponseEntity addZxSkTurnOverScrap(@RequestBody(required = false) ZxSkTurnOverScrap zxSkTurnOverScrap) {
        return zxSkTurnOverScrapService.saveZxSkTurnOverScrap(zxSkTurnOverScrap);
    }

    @ApiOperation(value="更新周转材料报废", notes="更新周转材料报废")
    @ApiImplicitParam(name = "zxSkTurnOverScrap", value = "周转材料报废entity", dataType = "ZxSkTurnOverScrap")
    @RequireToken
    @PostMapping("/updateZxSkTurnOverScrap")
    public ResponseEntity updateZxSkTurnOverScrap(@RequestBody(required = false) ZxSkTurnOverScrap zxSkTurnOverScrap) {
        return zxSkTurnOverScrapService.updateZxSkTurnOverScrap(zxSkTurnOverScrap);
    }

    @ApiOperation(value="删除周转材料报废", notes="删除周转材料报废")
    @ApiImplicitParam(name = "zxSkTurnOverScrapList", value = "周转材料报废List", dataType = "List<ZxSkTurnOverScrap>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxSkTurnOverScrap")
    public ResponseEntity batchDeleteUpdateZxSkTurnOverScrap(@RequestBody(required = false) List<ZxSkTurnOverScrap> zxSkTurnOverScrapList) {
        return zxSkTurnOverScrapService.batchDeleteUpdateZxSkTurnOverScrap(zxSkTurnOverScrapList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

    //编号
    @ApiOperation(value = "获取周转材料报废编号", notes = "获取周转材料报废编号")
    @ApiImplicitParam(name = "zxSkTurnOverScrap", value = "周转材料报废entity", dataType = "zxSkTurnOverScrap")
    @RequireToken
    @PostMapping("/getZxSkTurnOverScrapNo")
    public ResponseEntity getZxSkTurnOverScrapNo(@RequestBody(required = false) ZxSkTurnOverScrap zxSkTurnOverScrap) {
        return zxSkTurnOverScrapService.getZxSkTurnOverScrapNo(zxSkTurnOverScrap);
    }

    /**
     * 获取周转材料报废物资
     *
     * @param zxSkTurnOverScrap
     * @return
     */
    @ApiOperation(value = "获取周转材料报废物资", notes = "获取周转材料报废物资")
    @ApiImplicitParam(name = "zxSkTurnOverScrap", value = "周转材料报废entity", dataType = "zxSkTurnOverScrap")
    @RequireToken
    @PostMapping("/getZxSkTurnOverScrapResourceList")
    public ResponseEntity getZxSkTurnOverScrapResourceList(@RequestBody(required = false) ZxSkTurnOverScrap zxSkTurnOverScrap) {
        return zxSkTurnOverScrapService.getZxSkTurnOverScrapResourceList(zxSkTurnOverScrap);
    }

    //审核是流程.     库存表减少数量  原值  净值        原值净值减少的都是按照调拨一样的业务
    //根据数量 * 入库单价 = 原值


}
