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
import com.apih5.mybatis.pojo.ZxCtDaywork;
import com.apih5.service.ZxCtDayworkService;

@RestController
public class ZxCtDayworkController {

    @Autowired(required = true)
    private ZxCtDayworkService zxCtDayworkService;

    @ApiOperation(value="查询业主合同管理-业主合同台账-计量暂定金、计日工计量(原表iest_Daywork", notes="查询业主合同管理-业主合同台账-计量暂定金、计日工计量(原表iest_Daywork")
    @ApiImplicitParam(name = "zxCtDaywork", value = "业主合同管理-业主合同台账-计量暂定金、计日工计量(原表iest_Dayworkentity", dataType = "ZxCtDaywork")
    @RequireToken
    @PostMapping("/getZxCtDayworkList")
    public ResponseEntity getZxCtDayworkList(@RequestBody(required = false) ZxCtDaywork zxCtDaywork) {
        return zxCtDayworkService.getZxCtDayworkListByCondition(zxCtDaywork);
    }

    @ApiOperation(value="查询详情业主合同管理-业主合同台账-计量暂定金、计日工计量(原表iest_Daywork", notes="查询详情业主合同管理-业主合同台账-计量暂定金、计日工计量(原表iest_Daywork")
    @ApiImplicitParam(name = "zxCtDaywork", value = "业主合同管理-业主合同台账-计量暂定金、计日工计量(原表iest_Dayworkentity", dataType = "ZxCtDaywork")
    @RequireToken
    @PostMapping("/getZxCtDayworkDetail")
    public ResponseEntity getZxCtDayworkDetail(@RequestBody(required = false) ZxCtDaywork zxCtDaywork) {
        return zxCtDayworkService.getZxCtDayworkDetail(zxCtDaywork);
    }

    @ApiOperation(value="新增业主合同管理-业主合同台账-计量暂定金、计日工计量(原表iest_Daywork", notes="新增业主合同管理-业主合同台账-计量暂定金、计日工计量(原表iest_Daywork")
    @ApiImplicitParam(name = "zxCtDaywork", value = "业主合同管理-业主合同台账-计量暂定金、计日工计量(原表iest_Dayworkentity", dataType = "ZxCtDaywork")
    @RequireToken
    @PostMapping("/addZxCtDaywork")
    public ResponseEntity addZxCtDaywork(@RequestBody(required = false) ZxCtDaywork zxCtDaywork) {
        return zxCtDayworkService.saveZxCtDaywork(zxCtDaywork);
    }

    @ApiOperation(value="更新业主合同管理-业主合同台账-计量暂定金、计日工计量(原表iest_Daywork", notes="更新业主合同管理-业主合同台账-计量暂定金、计日工计量(原表iest_Daywork")
    @ApiImplicitParam(name = "zxCtDaywork", value = "业主合同管理-业主合同台账-计量暂定金、计日工计量(原表iest_Dayworkentity", dataType = "ZxCtDaywork")
    @RequireToken
    @PostMapping("/updateZxCtDaywork")
    public ResponseEntity updateZxCtDaywork(@RequestBody(required = false) ZxCtDaywork zxCtDaywork) {
        return zxCtDayworkService.updateZxCtDaywork(zxCtDaywork);
    }

    @ApiOperation(value="删除业主合同管理-业主合同台账-计量暂定金、计日工计量(原表iest_Daywork", notes="删除业主合同管理-业主合同台账-计量暂定金、计日工计量(原表iest_Daywork")
    @ApiImplicitParam(name = "zxCtDayworkList", value = "业主合同管理-业主合同台账-计量暂定金、计日工计量(原表iest_DayworkList", dataType = "List<ZxCtDaywork>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxCtDaywork")
    public ResponseEntity batchDeleteUpdateZxCtDaywork(@RequestBody(required = false) List<ZxCtDaywork> zxCtDayworkList) {
        return zxCtDayworkService.batchDeleteUpdateZxCtDaywork(zxCtDayworkList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
