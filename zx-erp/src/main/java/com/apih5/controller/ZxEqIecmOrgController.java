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
import com.apih5.mybatis.pojo.ZxEqIecmOrg;
import com.apih5.service.ZxEqIecmOrgService;

@RestController
public class ZxEqIecmOrgController {

    @Autowired(required = true)
    private ZxEqIecmOrgService zxEqIecmOrgService;

    @ApiOperation(value="查询机构", notes="查询机构")
    @ApiImplicitParam(name = "zxEqIecmOrg", value = "机构entity", dataType = "ZxEqIecmOrg")
    @RequireToken
    @PostMapping("/getZxEqIecmOrgList")
    public ResponseEntity getZxEqIecmOrgList(@RequestBody(required = false) ZxEqIecmOrg zxEqIecmOrg) {
        return zxEqIecmOrgService.getZxEqIecmOrgListByCondition(zxEqIecmOrg);
    }

    @ApiOperation(value="查询详情机构", notes="查询详情机构")
    @ApiImplicitParam(name = "zxEqIecmOrg", value = "机构entity", dataType = "ZxEqIecmOrg")
    @RequireToken
    @PostMapping("/getZxEqIecmOrgDetails")
    public ResponseEntity getZxEqIecmOrgDetails(@RequestBody(required = false) ZxEqIecmOrg zxEqIecmOrg) {
        return zxEqIecmOrgService.getZxEqIecmOrgDetails(zxEqIecmOrg);
    }

    @ApiOperation(value="新增机构", notes="新增机构")
    @ApiImplicitParam(name = "zxEqIecmOrg", value = "机构entity", dataType = "ZxEqIecmOrg")
    @RequireToken
    @PostMapping("/addZxEqIecmOrg")
    public ResponseEntity addZxEqIecmOrg(@RequestBody(required = false) ZxEqIecmOrg zxEqIecmOrg) {
        return zxEqIecmOrgService.saveZxEqIecmOrg(zxEqIecmOrg);
    }

    @ApiOperation(value="更新机构", notes="更新机构")
    @ApiImplicitParam(name = "zxEqIecmOrg", value = "机构entity", dataType = "ZxEqIecmOrg")
    @RequireToken
    @PostMapping("/updateZxEqIecmOrg")
    public ResponseEntity updateZxEqIecmOrg(@RequestBody(required = false) ZxEqIecmOrg zxEqIecmOrg) {
        return zxEqIecmOrgService.updateZxEqIecmOrg(zxEqIecmOrg);
    }

    @ApiOperation(value="删除机构", notes="删除机构")
    @ApiImplicitParam(name = "zxEqIecmOrgList", value = "机构List", dataType = "List<ZxEqIecmOrg>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxEqIecmOrg")
    public ResponseEntity batchDeleteUpdateZxEqIecmOrg(@RequestBody(required = false) List<ZxEqIecmOrg> zxEqIecmOrgList) {
        return zxEqIecmOrgService.batchDeleteUpdateZxEqIecmOrg(zxEqIecmOrgList);
    }

}
