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
import com.apih5.mybatis.pojo.ZxCtContrJzBase;
import com.apih5.service.ZxCtContrJzBaseService;

@RestController
public class ZxCtContrJzBaseController {

    @Autowired(required = true)
    private ZxCtContrJzBaseService zxCtContrJzBaseService;

    @ApiOperation(value="查询项目初始(当前)建造合同清单", notes="查询项目初始(当前)建造合同清单")
    @ApiImplicitParam(name = "zxCtContrJzBase", value = "项目初始(当前)建造合同清单entity", dataType = "ZxCtContrJzBase")
    @RequireToken
    @PostMapping("/getZxCtContrJzBaseList")
    public ResponseEntity getZxCtContrJzBaseList(@RequestBody(required = false) ZxCtContrJzBase zxCtContrJzBase) {
        return zxCtContrJzBaseService.getZxCtContrJzBaseListByCondition(zxCtContrJzBase);
    }

    @ApiOperation(value="查询详情项目初始(当前)建造合同清单", notes="查询详情项目初始(当前)建造合同清单")
    @ApiImplicitParam(name = "zxCtContrJzBase", value = "项目初始(当前)建造合同清单entity", dataType = "ZxCtContrJzBase")
    @RequireToken
    @PostMapping("/getZxCtContrJzBaseDetail")
    public ResponseEntity getZxCtContrJzBaseDetail(@RequestBody(required = false) ZxCtContrJzBase zxCtContrJzBase) {
        return zxCtContrJzBaseService.getZxCtContrJzBaseDetail(zxCtContrJzBase);
    }

    @ApiOperation(value="新增项目初始(当前)建造合同清单", notes="新增项目初始(当前)建造合同清单")
    @ApiImplicitParam(name = "zxCtContrJzBase", value = "项目初始(当前)建造合同清单entity", dataType = "ZxCtContrJzBase")
    @RequireToken
    @PostMapping("/addZxCtContrJzBase")
    public ResponseEntity addZxCtContrJzBase(@RequestBody(required = false) ZxCtContrJzBase zxCtContrJzBase) {
        return zxCtContrJzBaseService.saveZxCtContrJzBase(zxCtContrJzBase);
    }

    @ApiOperation(value="更新项目初始(当前)建造合同清单", notes="更新项目初始(当前)建造合同清单")
    @ApiImplicitParam(name = "zxCtContrJzBase", value = "项目初始(当前)建造合同清单entity", dataType = "ZxCtContrJzBase")
    @RequireToken
    @PostMapping("/updateZxCtContrJzBase")
    public ResponseEntity updateZxCtContrJzBase(@RequestBody(required = false) ZxCtContrJzBase zxCtContrJzBase) {
        return zxCtContrJzBaseService.updateZxCtContrJzBase(zxCtContrJzBase);
    }

    @ApiOperation(value="删除项目初始(当前)建造合同清单", notes="删除项目初始(当前)建造合同清单")
    @ApiImplicitParam(name = "zxCtContrJzBaseList", value = "项目初始(当前)建造合同清单List", dataType = "List<ZxCtContrJzBase>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxCtContrJzBase")
    public ResponseEntity batchDeleteUpdateZxCtContrJzBase(@RequestBody(required = false) List<ZxCtContrJzBase> zxCtContrJzBaseList) {
        return zxCtContrJzBaseService.batchDeleteUpdateZxCtContrJzBase(zxCtContrJzBaseList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    @ApiOperation(value="先不要了-del--编辑项目初始(当前)建造合同清单", notes="编辑项目初始(当前)建造合同清单")
    @ApiImplicitParam(name = "zxCtContrJzBase", value = "项目初始(当前)建造合同清entity", dataType = "ZxCtContrJzBase")
    @RequireToken
    @PostMapping("/editZxCtContrJzBaseItem")
    public ResponseEntity editZxCtContrJzBaseItem(@RequestBody(required = false) ZxCtContrJzBase zxCtContrJzBase) {
        return zxCtContrJzBaseService.editZxCtContrJzBaseItem(zxCtContrJzBase);
    }
}
