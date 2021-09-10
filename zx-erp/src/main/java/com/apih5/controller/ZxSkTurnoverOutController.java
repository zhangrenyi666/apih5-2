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
import com.apih5.mybatis.pojo.ZxSkTurnoverOut;
import com.apih5.service.ZxSkTurnoverOutService;

@RestController
public class ZxSkTurnoverOutController {

    @Autowired(required = true)
    private ZxSkTurnoverOutService zxSkTurnoverOutService;

    @ApiOperation(value="查询周转材料出库", notes="查询周转材料出库")
    @ApiImplicitParam(name = "zxSkTurnoverOut", value = "周转材料出库entity", dataType = "ZxSkTurnoverOut")
    @RequireToken
    @PostMapping("/getZxSkTurnoverOutList")
    public ResponseEntity getZxSkTurnoverOutList(@RequestBody(required = false) ZxSkTurnoverOut zxSkTurnoverOut) {
        return zxSkTurnoverOutService.getZxSkTurnoverOutListByCondition(zxSkTurnoverOut);
    }

    @ApiOperation(value="查询详情周转材料出库", notes="查询详情周转材料出库")
    @ApiImplicitParam(name = "zxSkTurnoverOut", value = "周转材料出库entity", dataType = "ZxSkTurnoverOut")
    @RequireToken
    @PostMapping("/getZxSkTurnoverOutDetail")
    public ResponseEntity getZxSkTurnoverOutDetail(@RequestBody(required = false) ZxSkTurnoverOut zxSkTurnoverOut) {
        return zxSkTurnoverOutService.getZxSkTurnoverOutDetail(zxSkTurnoverOut);
    }

    @ApiOperation(value="新增周转材料出库", notes="新增周转材料出库")
    @ApiImplicitParam(name = "zxSkTurnoverOut", value = "周转材料出库entity", dataType = "ZxSkTurnoverOut")
    @RequireToken
    @PostMapping("/addZxSkTurnoverOut")
    public ResponseEntity addZxSkTurnoverOut(@RequestBody(required = false) ZxSkTurnoverOut zxSkTurnoverOut) {
        return zxSkTurnoverOutService.saveZxSkTurnoverOut(zxSkTurnoverOut);
    }

    @ApiOperation(value="更新周转材料出库", notes="更新周转材料出库")
    @ApiImplicitParam(name = "zxSkTurnoverOut", value = "周转材料出库entity", dataType = "ZxSkTurnoverOut")
    @RequireToken
    @PostMapping("/updateZxSkTurnoverOut")
    public ResponseEntity updateZxSkTurnoverOut(@RequestBody(required = false) ZxSkTurnoverOut zxSkTurnoverOut) {
        return zxSkTurnoverOutService.updateZxSkTurnoverOut(zxSkTurnoverOut);
    }

    @ApiOperation(value="删除周转材料出库", notes="删除周转材料出库")
    @ApiImplicitParam(name = "zxSkTurnoverOutList", value = "周转材料出库List", dataType = "List<ZxSkTurnoverOut>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxSkTurnoverOut")
    public ResponseEntity batchDeleteUpdateZxSkTurnoverOut(@RequestBody(required = false) List<ZxSkTurnoverOut> zxSkTurnoverOutList) {
        return zxSkTurnoverOutService.batchDeleteUpdateZxSkTurnoverOut(zxSkTurnoverOutList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

    //编号
    @ApiOperation(value = "获取周转材料出库编号", notes = "获取周转材料出库编号")
    @ApiImplicitParam(name = "zxSkTurnoverOut", value = "周转材料入库entity", dataType = "zxSkTurnoverOut")
    @RequireToken
    @PostMapping("/getZxSkTurnoverOutNo")
    public ResponseEntity getZxSkTurnoverOutNo(@RequestBody(required = false) ZxSkTurnoverOut zxSkTurnoverOut) {
        return zxSkTurnoverOutService.getZxSkTurnoverOutNo(zxSkTurnoverOut);
    }

    /**
     * 获取周转材料出库物资
     *
     * @param zxSkTurnoverOut
     * @return
     */
    @ApiOperation(value = "获取周转材料出库物资", notes = "获取周转材料出库物资")
    @ApiImplicitParam(name = "zxSkTurnoverOut", value = "周转材料出库entity", dataType = "zxSkTurnoverOut")
    @RequireToken
    @PostMapping("/getZxSkTurnoverOutResourceList")
    public ResponseEntity getZxSkTurnoverOutResourceList(@RequestBody(required = false) ZxSkTurnoverOut zxSkTurnoverOut) {
        return zxSkTurnoverOutService.getZxSkTurnoverOutResourceList(zxSkTurnoverOut);
    }

    /**
     * 审核周转材料出库
     *
     * @param zxSkTurnoverOut
     * @return
     */
    @ApiOperation(value = "审核周转材料出库", notes = "审核周转材料出库")
    @ApiImplicitParam(name = "zxSkTurnoverOut", value = "周转材料出库entity", dataType = "zxSkTurnoverOut")
    @RequireToken
    @PostMapping("/checkZxSkTurnoverOut")
    public ResponseEntity checkZxSkTurnoverOut(@RequestBody(required = false) ZxSkTurnoverOut zxSkTurnoverOut) {
        return zxSkTurnoverOutService.checkZxSkTurnoverOut(zxSkTurnoverOut);
    }

    /**
     * 归还周转材料出库
     *
     * @param zxSkTurnoverOut
     * @return
     */
    @ApiOperation(value = "归还周转材料出库", notes = "归还周转材料出库")
    @ApiImplicitParam(name = "zxSkTurnoverOut", value = "周转材料出库entity", dataType = "zxSkTurnoverOut")
    @RequireToken
    @PostMapping("/returnZxSkTurnoverOut")
    public ResponseEntity returnZxSkTurnoverOut(@RequestBody(required = false) ZxSkTurnoverOut zxSkTurnoverOut) {
        return zxSkTurnoverOutService.returnZxSkTurnoverOut(zxSkTurnoverOut);
    }





}
