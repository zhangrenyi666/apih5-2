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
import com.apih5.mybatis.pojo.BaseProjectConf;
import com.apih5.service.BaseProjectConfService;

@RestController
public class BaseProjectConfController {

    @Autowired(required = true)
    private BaseProjectConfService baseProjectConfService;

    @ApiOperation(value="查询项目人员配置字典表", notes="查询项目人员配置字典表")
    @ApiImplicitParam(name = "baseProjectConf", value = "项目人员配置字典表entity", dataType = "BaseProjectConf")
    @RequireToken
    @PostMapping("/getBaseProjectConfList")
    public ResponseEntity getBaseProjectConfList(@RequestBody(required = false) BaseProjectConf baseProjectConf) {
        return baseProjectConfService.getBaseProjectConfListByCondition(baseProjectConf);
    }

    @ApiOperation(value="查询详情项目人员配置字典表", notes="查询详情项目人员配置字典表")
    @ApiImplicitParam(name = "baseProjectConf", value = "项目人员配置字典表entity", dataType = "BaseProjectConf")
    @RequireToken
    @PostMapping("/getBaseProjectConfDetail")
    public ResponseEntity getBaseProjectConfDetail(@RequestBody(required = false) BaseProjectConf baseProjectConf) {
        return baseProjectConfService.getBaseProjectConfDetail(baseProjectConf);
    }

    @ApiOperation(value="新增项目人员配置字典表", notes="新增项目人员配置字典表")
    @ApiImplicitParam(name = "baseProjectConf", value = "项目人员配置字典表entity", dataType = "BaseProjectConf")
    @RequireToken
    @PostMapping("/addBaseProjectConf")
    public ResponseEntity addBaseProjectConf(@RequestBody(required = false) BaseProjectConf baseProjectConf) {
        return baseProjectConfService.saveBaseProjectConf(baseProjectConf);
    }

    @ApiOperation(value="更新项目人员配置字典表", notes="更新项目人员配置字典表")
    @ApiImplicitParam(name = "baseProjectConf", value = "项目人员配置字典表entity", dataType = "BaseProjectConf")
    @RequireToken
    @PostMapping("/updateBaseProjectConf")
    public ResponseEntity updateBaseProjectConf(@RequestBody(required = false) BaseProjectConf baseProjectConf) {
        return baseProjectConfService.updateBaseProjectConf(baseProjectConf);
    }

    @ApiOperation(value="删除项目人员配置字典表", notes="删除项目人员配置字典表")
    @ApiImplicitParam(name = "baseProjectConfList", value = "项目人员配置字典表List", dataType = "List<BaseProjectConf>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateBaseProjectConf")
    public ResponseEntity batchDeleteUpdateBaseProjectConf(@RequestBody(required = false) List<BaseProjectConf> baseProjectConfList) {
        return baseProjectConfService.batchDeleteUpdateBaseProjectConf(baseProjectConfList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
