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
import com.apih5.mybatis.pojo.ZxSfSpecialEmp;
import com.apih5.service.ZxSfSpecialEmpService;

@RestController
public class ZxSfSpecialEmpController {

    @Autowired(required = true)
    private ZxSfSpecialEmpService zxSfSpecialEmpService;

    @ApiOperation(value="查询特种作业人员台账", notes="查询特种作业人员台账")
    @ApiImplicitParam(name = "zxSfSpecialEmp", value = "特种作业人员台账entity", dataType = "ZxSfSpecialEmp")
    @RequireToken
    @PostMapping("/getZxSfSpecialEmpList")
    public ResponseEntity getZxSfSpecialEmpList(@RequestBody(required = false) ZxSfSpecialEmp zxSfSpecialEmp) {
        return zxSfSpecialEmpService.getZxSfSpecialEmpListByCondition(zxSfSpecialEmp);
    }

    @ApiOperation(value="查询详情特种作业人员台账", notes="查询详情特种作业人员台账")
    @ApiImplicitParam(name = "zxSfSpecialEmp", value = "特种作业人员台账entity", dataType = "ZxSfSpecialEmp")
    @RequireToken
    @PostMapping("/getZxSfSpecialEmpDetail")
    public ResponseEntity getZxSfSpecialEmpDetail(@RequestBody(required = false) ZxSfSpecialEmp zxSfSpecialEmp) {
        return zxSfSpecialEmpService.getZxSfSpecialEmpDetail(zxSfSpecialEmp);
    }

    @ApiOperation(value="新增特种作业人员台账", notes="新增特种作业人员台账")
    @ApiImplicitParam(name = "zxSfSpecialEmp", value = "特种作业人员台账entity", dataType = "ZxSfSpecialEmp")
    @RequireToken
    @PostMapping("/addZxSfSpecialEmp")
    public ResponseEntity addZxSfSpecialEmp(@RequestBody(required = false) ZxSfSpecialEmp zxSfSpecialEmp) {
        return zxSfSpecialEmpService.saveZxSfSpecialEmp(zxSfSpecialEmp);
    }

    @ApiOperation(value="更新特种作业人员台账", notes="更新特种作业人员台账")
    @ApiImplicitParam(name = "zxSfSpecialEmp", value = "特种作业人员台账entity", dataType = "ZxSfSpecialEmp")
    @RequireToken
    @PostMapping("/updateZxSfSpecialEmp")
    public ResponseEntity updateZxSfSpecialEmp(@RequestBody(required = false) ZxSfSpecialEmp zxSfSpecialEmp) {
        return zxSfSpecialEmpService.updateZxSfSpecialEmp(zxSfSpecialEmp);
    }

    @ApiOperation(value="删除特种作业人员台账", notes="删除特种作业人员台账")
    @ApiImplicitParam(name = "zxSfSpecialEmpList", value = "特种作业人员台账List", dataType = "List<ZxSfSpecialEmp>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxSfSpecialEmp")
    public ResponseEntity batchDeleteUpdateZxSfSpecialEmp(@RequestBody(required = false) List<ZxSfSpecialEmp> zxSfSpecialEmpList) {
        return zxSfSpecialEmpService.batchDeleteUpdateZxSfSpecialEmp(zxSfSpecialEmpList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
