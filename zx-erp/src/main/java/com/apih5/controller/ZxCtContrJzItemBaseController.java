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
import com.apih5.mybatis.pojo.ZxCtContrJzItemBase;
import com.apih5.service.ZxCtContrJzItemBaseService;

@RestController
public class ZxCtContrJzItemBaseController {

    @Autowired(required = true)
    private ZxCtContrJzItemBaseService zxCtContrJzItemBaseService;

    @ApiOperation(value="查询初始(当前)建造合同清单基础数据", notes="查询初始(当前)建造合同清单基础数据")
    @ApiImplicitParam(name = "zxCtContrJzItemBase", value = "初始(当前)建造合同清单基础数据entity", dataType = "ZxCtContrJzItemBase")
    @RequireToken
    @PostMapping("/getZxCtContrJzItemBaseList")
    public ResponseEntity getZxCtContrJzItemBaseList(@RequestBody(required = false) ZxCtContrJzItemBase zxCtContrJzItemBase) {
        return zxCtContrJzItemBaseService.getZxCtContrJzItemBaseListByCondition(zxCtContrJzItemBase);
    }

    @ApiOperation(value="查询详情初始(当前)建造合同清单基础数据", notes="查询详情初始(当前)建造合同清单基础数据")
    @ApiImplicitParam(name = "zxCtContrJzItemBase", value = "初始(当前)建造合同清单基础数据entity", dataType = "ZxCtContrJzItemBase")
    @RequireToken
    @PostMapping("/getZxCtContrJzItemBaseDetail")
    public ResponseEntity getZxCtContrJzItemBaseDetail(@RequestBody(required = false) ZxCtContrJzItemBase zxCtContrJzItemBase) {
        return zxCtContrJzItemBaseService.getZxCtContrJzItemBaseDetail(zxCtContrJzItemBase);
    }

    @ApiOperation(value="新增初始(当前)建造合同清单基础数据", notes="新增初始(当前)建造合同清单基础数据")
    @ApiImplicitParam(name = "zxCtContrJzItemBase", value = "初始(当前)建造合同清单基础数据entity", dataType = "ZxCtContrJzItemBase")
    @RequireToken
    @PostMapping("/addZxCtContrJzItemBase")
    public ResponseEntity addZxCtContrJzItemBase(@RequestBody(required = false) ZxCtContrJzItemBase zxCtContrJzItemBase) {
        return zxCtContrJzItemBaseService.saveZxCtContrJzItemBase(zxCtContrJzItemBase);
    }

    @ApiOperation(value="更新初始(当前)建造合同清单基础数据", notes="更新初始(当前)建造合同清单基础数据")
    @ApiImplicitParam(name = "zxCtContrJzItemBase", value = "初始(当前)建造合同清单基础数据entity", dataType = "ZxCtContrJzItemBase")
    @RequireToken
    @PostMapping("/updateZxCtContrJzItemBase")
    public ResponseEntity updateZxCtContrJzItemBase(@RequestBody(required = false) ZxCtContrJzItemBase zxCtContrJzItemBase) {
        return zxCtContrJzItemBaseService.updateZxCtContrJzItemBase(zxCtContrJzItemBase);
    }

    @ApiOperation(value="删除初始(当前)建造合同清单基础数据", notes="删除初始(当前)建造合同清单基础数据")
    @ApiImplicitParam(name = "zxCtContrJzItemBaseList", value = "初始(当前)建造合同清单基础数据List", dataType = "List<ZxCtContrJzItemBase>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxCtContrJzItemBase")
    public ResponseEntity batchDeleteUpdateZxCtContrJzItemBase(@RequestBody(required = false) List<ZxCtContrJzItemBase> zxCtContrJzItemBaseList) {
        return zxCtContrJzItemBaseService.batchDeleteUpdateZxCtContrJzItemBase(zxCtContrJzItemBaseList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
