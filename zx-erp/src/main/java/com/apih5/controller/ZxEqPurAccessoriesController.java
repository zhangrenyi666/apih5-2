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
import com.apih5.mybatis.pojo.ZxEqPurAccessories;
import com.apih5.service.ZxEqPurAccessoriesService;

@RestController
public class ZxEqPurAccessoriesController {

    @Autowired(required = true)
    private ZxEqPurAccessoriesService zxEqPurAccessoriesService;

    @ApiOperation(value="查询设备购置记录-随机工具及配件", notes="查询设备购置记录-随机工具及配件")
    @ApiImplicitParam(name = "zxEqPurAccessories", value = "设备购置记录-随机工具及配件entity", dataType = "ZxEqPurAccessories")
    @RequireToken
    @PostMapping("/getZxEqPurAccessoriesList")
    public ResponseEntity getZxEqPurAccessoriesList(@RequestBody(required = false) ZxEqPurAccessories zxEqPurAccessories) {
        return zxEqPurAccessoriesService.getZxEqPurAccessoriesListByCondition(zxEqPurAccessories);
    }

    @ApiOperation(value="查询详情设备购置记录-随机工具及配件", notes="查询详情设备购置记录-随机工具及配件")
    @ApiImplicitParam(name = "zxEqPurAccessories", value = "设备购置记录-随机工具及配件entity", dataType = "ZxEqPurAccessories")
    @RequireToken
    @PostMapping("/getZxEqPurAccessoriesDetails")
    public ResponseEntity getZxEqPurAccessoriesDetails(@RequestBody(required = false) ZxEqPurAccessories zxEqPurAccessories) {
        return zxEqPurAccessoriesService.getZxEqPurAccessoriesDetails(zxEqPurAccessories);
    }

    @ApiOperation(value="新增设备购置记录-随机工具及配件", notes="新增设备购置记录-随机工具及配件")
    @ApiImplicitParam(name = "zxEqPurAccessories", value = "设备购置记录-随机工具及配件entity", dataType = "ZxEqPurAccessories")
    @RequireToken
    @PostMapping("/addZxEqPurAccessories")
    public ResponseEntity addZxEqPurAccessories(@RequestBody(required = false) ZxEqPurAccessories zxEqPurAccessories) {
        return zxEqPurAccessoriesService.saveZxEqPurAccessories(zxEqPurAccessories);
    }

    @ApiOperation(value="更新设备购置记录-随机工具及配件", notes="更新设备购置记录-随机工具及配件")
    @ApiImplicitParam(name = "zxEqPurAccessories", value = "设备购置记录-随机工具及配件entity", dataType = "ZxEqPurAccessories")
    @RequireToken
    @PostMapping("/updateZxEqPurAccessories")
    public ResponseEntity updateZxEqPurAccessories(@RequestBody(required = false) ZxEqPurAccessories zxEqPurAccessories) {
        return zxEqPurAccessoriesService.updateZxEqPurAccessories(zxEqPurAccessories);
    }

    @ApiOperation(value="删除设备购置记录-随机工具及配件", notes="删除设备购置记录-随机工具及配件")
    @ApiImplicitParam(name = "zxEqPurAccessoriesList", value = "设备购置记录-随机工具及配件List", dataType = "List<ZxEqPurAccessories>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxEqPurAccessories")
    public ResponseEntity batchDeleteUpdateZxEqPurAccessories(@RequestBody(required = false) List<ZxEqPurAccessories> zxEqPurAccessoriesList) {
        return zxEqPurAccessoriesService.batchDeleteUpdateZxEqPurAccessories(zxEqPurAccessoriesList);
    }
    
    @ApiOperation(value="报表导出设备购置记录-随机工具及配件", notes="报表导出设备购置记录-随机工具及配件")
    @ApiImplicitParam(name = "zxEqPurAccessories", value = "设备购置记录-随机工具及配件entity", dataType = "ZxEqPurAccessories")
    @PostMapping("/ureportZxEqPurAccessoriesList")
    public List<ZxEqPurAccessories> ureportZxEqPurAccessoriesList(@RequestBody(required = false) ZxEqPurAccessories zxEqPurAccessories) {
        return zxEqPurAccessoriesService.ureportZxEqPurAccessoriesList(zxEqPurAccessories);
    }

}
