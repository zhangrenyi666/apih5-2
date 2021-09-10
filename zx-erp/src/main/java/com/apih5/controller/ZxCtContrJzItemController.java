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
import com.apih5.mybatis.pojo.ZxCtContrJzItem;
import com.apih5.service.ZxCtContrJzItemService;

@RestController
public class ZxCtContrJzItemController {

    @Autowired(required = true)
    private ZxCtContrJzItemService zxCtContrJzItemService;

    @ApiOperation(value="查询初始(当前)建造合同清单", notes="查询初始(当前)建造合同清单")
    @ApiImplicitParam(name = "zxCtContrJzItem", value = "初始(当前)建造合同清单entity", dataType = "ZxCtContrJzItem")
    @RequireToken
    @PostMapping("/getZxCtContrJzItemList")
    public ResponseEntity getZxCtContrJzItemList(@RequestBody(required = false) ZxCtContrJzItem zxCtContrJzItem) {
        return zxCtContrJzItemService.getZxCtContrJzItemListByCondition(zxCtContrJzItem);
    }

    @ApiOperation(value="查询详情初始(当前)建造合同清单", notes="查询详情初始(当前)建造合同清单")
    @ApiImplicitParam(name = "zxCtContrJzItem", value = "初始(当前)建造合同清单entity", dataType = "ZxCtContrJzItem")
    @RequireToken
    @PostMapping("/getZxCtContrJzItemDetail")
    public ResponseEntity getZxCtContrJzItemDetail(@RequestBody(required = false) ZxCtContrJzItem zxCtContrJzItem) {
        return zxCtContrJzItemService.getZxCtContrJzItemDetail(zxCtContrJzItem);
    }

    @ApiOperation(value="新增初始(当前)建造合同清单", notes="新增初始(当前)建造合同清单")
    @ApiImplicitParam(name = "zxCtContrJzItem", value = "初始(当前)建造合同清单entity", dataType = "ZxCtContrJzItem")
    @RequireToken
    @PostMapping("/addZxCtContrJzItem")
    public ResponseEntity addZxCtContrJzItem(@RequestBody(required = false) ZxCtContrJzItem zxCtContrJzItem) {
        return zxCtContrJzItemService.saveZxCtContrJzItem(zxCtContrJzItem);
    }

    @ApiOperation(value="更新初始(当前)建造合同清单", notes="更新初始(当前)建造合同清单")
    @ApiImplicitParam(name = "zxCtContrJzItem", value = "初始(当前)建造合同清单entity", dataType = "ZxCtContrJzItem")
    @RequireToken
    @PostMapping("/updateZxCtContrJzItem")
    public ResponseEntity updateZxCtContrJzItem(@RequestBody(required = false) ZxCtContrJzItem zxCtContrJzItem) {
        return zxCtContrJzItemService.updateZxCtContrJzItem(zxCtContrJzItem);
    }

    @ApiOperation(value="删除初始(当前)建造合同清单", notes="删除初始(当前)建造合同清单")
    @ApiImplicitParam(name = "zxCtContrJzItemList", value = "初始(当前)建造合同清单List", dataType = "List<ZxCtContrJzItem>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxCtContrJzItem")
    public ResponseEntity batchDeleteUpdateZxCtContrJzItem(@RequestBody(required = false) List<ZxCtContrJzItem> zxCtContrJzItemList) {
        return zxCtContrJzItemService.batchDeleteUpdateZxCtContrJzItem(zxCtContrJzItemList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    @ApiOperation(value="新增初始建造合同清单", notes="新增初始建造合同清单")
    @ApiImplicitParam(name = "zxCtContrJzItem", value = "初始(当前)建造合同清单entity", dataType = "ZxCtContrJzItem")
    @RequireToken
    @PostMapping("/addZxCtContrJzItemForCs")
    public ResponseEntity addZxCtContrJzItemForCs(@RequestBody(required = false) ZxCtContrJzItem zxCtContrJzItem) {
        return zxCtContrJzItemService.addZxCtContrJzItemForCs(zxCtContrJzItem);
    }
    
    @ApiOperation(value="新增当前建造合同清单", notes="新增当前建造合同清单")
    @ApiImplicitParam(name = "zxCtContrJzItem", value = "初始(当前)建造合同清单entity", dataType = "ZxCtContrJzItem")
    @RequireToken
    @PostMapping("/addZxCtContrJzItemForDq")
    public ResponseEntity addZxCtContrJzItemForDq(@RequestBody(required = false) ZxCtContrJzItem zxCtContrJzItem) {
        return zxCtContrJzItemService.addZxCtContrJzItemForDq(zxCtContrJzItem);
    }
    
}
