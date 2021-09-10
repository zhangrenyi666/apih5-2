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
import com.apih5.mybatis.pojo.ZxSfProjectEmployee;
import com.apih5.service.ZxSfProjectEmployeeService;

@RestController
public class ZxSfProjectEmployeeController {

    @Autowired(required = true)
    private ZxSfProjectEmployeeService zxSfProjectEmployeeService;

    @ApiOperation(value="查询安全管理人员明细", notes="查询安全管理人员明细")
    @ApiImplicitParam(name = "zxSfProjectEmployee", value = "安全管理人员明细entity", dataType = "ZxSfProjectEmployee")
    @RequireToken
    @PostMapping("/getZxSfProjectEmployeeList")
    public ResponseEntity getZxSfProjectEmployeeList(@RequestBody(required = false) ZxSfProjectEmployee zxSfProjectEmployee) {
        return zxSfProjectEmployeeService.getZxSfProjectEmployeeListByCondition(zxSfProjectEmployee);
    }

    @ApiOperation(value="查询详情安全管理人员明细", notes="查询详情安全管理人员明细")
    @ApiImplicitParam(name = "zxSfProjectEmployee", value = "安全管理人员明细entity", dataType = "ZxSfProjectEmployee")
    @RequireToken
    @PostMapping("/getZxSfProjectEmployeeDetail")
    public ResponseEntity getZxSfProjectEmployeeDetail(@RequestBody(required = false) ZxSfProjectEmployee zxSfProjectEmployee) {
        return zxSfProjectEmployeeService.getZxSfProjectEmployeeDetail(zxSfProjectEmployee);
    }

    @ApiOperation(value="新增安全管理人员明细", notes="新增安全管理人员明细")
    @ApiImplicitParam(name = "zxSfProjectEmployee", value = "安全管理人员明细entity", dataType = "ZxSfProjectEmployee")
    @RequireToken
    @PostMapping("/addZxSfProjectEmployee")
    public ResponseEntity addZxSfProjectEmployee(@RequestBody(required = false) ZxSfProjectEmployee zxSfProjectEmployee) {
        return zxSfProjectEmployeeService.saveZxSfProjectEmployee(zxSfProjectEmployee);
    }

    @ApiOperation(value="更新安全管理人员明细", notes="更新安全管理人员明细")
    @ApiImplicitParam(name = "zxSfProjectEmployee", value = "安全管理人员明细entity", dataType = "ZxSfProjectEmployee")
    @RequireToken
    @PostMapping("/updateZxSfProjectEmployee")
    public ResponseEntity updateZxSfProjectEmployee(@RequestBody(required = false) ZxSfProjectEmployee zxSfProjectEmployee) {
        return zxSfProjectEmployeeService.updateZxSfProjectEmployee(zxSfProjectEmployee);
    }

    @ApiOperation(value="删除安全管理人员明细", notes="删除安全管理人员明细")
    @ApiImplicitParam(name = "zxSfProjectEmployeeList", value = "安全管理人员明细List", dataType = "List<ZxSfProjectEmployee>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxSfProjectEmployee")
    public ResponseEntity batchDeleteUpdateZxSfProjectEmployee(@RequestBody(required = false) List<ZxSfProjectEmployee> zxSfProjectEmployeeList) {
        return zxSfProjectEmployeeService.batchDeleteUpdateZxSfProjectEmployee(zxSfProjectEmployeeList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

    @ApiOperation(value="查询安全管理人员报表", notes="查询安全管理人员报表")
    @ApiImplicitParam(name = "zxSfProjectEmployee", value = "安全管理人员明细entity", dataType = "ZxSfProjectEmployee")
    @RequireToken
    @PostMapping("/getZxSfProjectEmployeeFormList")
    public ResponseEntity getZxSfProjectEmployeeFormList(@RequestBody(required = false) ZxSfProjectEmployee zxSfProjectEmployee) {
        return zxSfProjectEmployeeService.getuReportFormList(zxSfProjectEmployee);
    }

    @ApiOperation(value="导出安全管理人员报表", notes="导出安全管理人员报表")
    @ApiImplicitParam(name = "zxSfProjectEmployee", value = "安全管理人员明细entity", dataType = "ZxSfProjectEmployee")
    @PostMapping("/uReportZxSfProjectEmployeetForm")
    public List<ZxSfProjectEmployee> uReportZxSfProjectEmployeetForm(@RequestBody(required = false) ZxSfProjectEmployee zxSfProjectEmployee) {
        return zxSfProjectEmployeeService.uReportForm(zxSfProjectEmployee);
    }

    @ApiOperation(value="查询安全管理人员报表(公司)", notes="查询安全管理人员报表")
    @ApiImplicitParam(name = "zxSfProjectEmployee", value = "安全管理人员明细entity", dataType = "ZxSfProjectEmployee")
    @RequireToken
    @PostMapping("/getZxSfProjectEmployeeFormComList")
    public ResponseEntity getZxSfProjectEmployeeFormComList(@RequestBody(required = false) ZxSfProjectEmployee zxSfProjectEmployee) {
        return zxSfProjectEmployeeService.getuReportFormComList(zxSfProjectEmployee);
    }

    @ApiOperation(value="导出安全管理人员报表(公司)", notes="导出安全管理人员报表")
    @ApiImplicitParam(name = "zxSfProjectEmployee", value = "安全管理人员明细entity", dataType = "ZxSfProjectEmployee")
    @PostMapping("/uReportZxSfProjectEmployeetFormCom")
    public List<ZxSfProjectEmployee> uReportZxSfProjectEmployeetFormCom(@RequestBody(required = false) ZxSfProjectEmployee zxSfProjectEmployee) {
        return zxSfProjectEmployeeService.uReportFormCom(zxSfProjectEmployee);
    }
}
