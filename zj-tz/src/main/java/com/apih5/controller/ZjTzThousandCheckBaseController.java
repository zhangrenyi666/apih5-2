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
import com.apih5.mybatis.pojo.ZjTzThousandCheckBase;
import com.apih5.service.ZjTzThousandCheckBaseService;

@RestController
public class ZjTzThousandCheckBaseController {

    @Autowired(required = true)
    private ZjTzThousandCheckBaseService zjTzThousandCheckBaseService;

    @ApiOperation(value="查询千分制检查项设置", notes="查询千分制检查项设置")
    @ApiImplicitParam(name = "zjTzThousandCheckBase", value = "千分制检查项设置entity", dataType = "ZjTzThousandCheckBase")
    @RequireToken
    @PostMapping("/getZjTzThousandCheckBaseList")
    public ResponseEntity getZjTzThousandCheckBaseList(@RequestBody(required = false) ZjTzThousandCheckBase zjTzThousandCheckBase) {
        return zjTzThousandCheckBaseService.getZjTzThousandCheckBaseListByCondition(zjTzThousandCheckBase);
    }

    @ApiOperation(value="查询详情千分制检查项设置", notes="查询详情千分制检查项设置")
    @ApiImplicitParam(name = "zjTzThousandCheckBase", value = "千分制检查项设置entity", dataType = "ZjTzThousandCheckBase")
    @RequireToken
    @PostMapping("/getZjTzThousandCheckBaseDetails")
    public ResponseEntity getZjTzThousandCheckBaseDetails(@RequestBody(required = false) ZjTzThousandCheckBase zjTzThousandCheckBase) {
        return zjTzThousandCheckBaseService.getZjTzThousandCheckBaseDetails(zjTzThousandCheckBase);
    }

    @ApiOperation(value="新增千分制检查项设置", notes="新增千分制检查项设置")
    @ApiImplicitParam(name = "zjTzThousandCheckBase", value = "千分制检查项设置entity", dataType = "ZjTzThousandCheckBase")
    @RequireToken
    @PostMapping("/addZjTzThousandCheckBase")
    public ResponseEntity addZjTzThousandCheckBase(@RequestBody(required = false) ZjTzThousandCheckBase zjTzThousandCheckBase) {
        return zjTzThousandCheckBaseService.saveZjTzThousandCheckBase(zjTzThousandCheckBase);
    }

    @ApiOperation(value="更新千分制检查项设置", notes="更新千分制检查项设置")
    @ApiImplicitParam(name = "zjTzThousandCheckBase", value = "千分制检查项设置entity", dataType = "ZjTzThousandCheckBase")
    @RequireToken
    @PostMapping("/updateZjTzThousandCheckBase")
    public ResponseEntity updateZjTzThousandCheckBase(@RequestBody(required = false) ZjTzThousandCheckBase zjTzThousandCheckBase) {
        return zjTzThousandCheckBaseService.updateZjTzThousandCheckBase(zjTzThousandCheckBase);
    }

    @ApiOperation(value="删除千分制检查项设置", notes="删除千分制检查项设置")
    @ApiImplicitParam(name = "zjTzThousandCheckBaseList", value = "千分制检查项设置List", dataType = "List<ZjTzThousandCheckBase>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZjTzThousandCheckBase")
    public ResponseEntity batchDeleteUpdateZjTzThousandCheckBase(@RequestBody(required = false) List<ZjTzThousandCheckBase> zjTzThousandCheckBaseList) {
        return zjTzThousandCheckBaseService.batchDeleteUpdateZjTzThousandCheckBase(zjTzThousandCheckBaseList);
    }
    
    @ApiOperation(value="批量导入千分制检查项设置", notes="批量导入千分制检查项设置")
    @ApiImplicitParam(name = "zjTzThousandCheckBase", value = "千分制检查项设置entity", dataType = "ZjTzThousandCheckBase")
    @RequireToken
    @PostMapping("/batchImportZjTzThousandCheckBase")
    public ResponseEntity batchImportZjTzThousandCheckBase(@RequestBody(required = false) ZjTzThousandCheckBase zjTzThousandCheckBase) {
        return zjTzThousandCheckBaseService.batchImportZjTzThousandCheckBase(zjTzThousandCheckBase);
    }
    
}
