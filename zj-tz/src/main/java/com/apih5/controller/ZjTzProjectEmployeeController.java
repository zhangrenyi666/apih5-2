package com.apih5.controller;

import java.util.List;

import com.apih5.mybatis.pojo.ZjTzProManage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import com.apih5.framework.annotation.RequireToken;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjTzProjectEmployee;
import com.apih5.service.ZjTzProjectEmployeeService;

@RestController
public class ZjTzProjectEmployeeController {

    @Autowired(required = true)
    private ZjTzProjectEmployeeService zjTzProjectEmployeeService;

    @ApiOperation(value="查询项目人员", notes="查询项目人员")
    @ApiImplicitParam(name = "zjTzProjectEmployee", value = "项目人员entity", dataType = "ZjTzProjectEmployee")
    @RequireToken
    @PostMapping("/getZjTzProjectEmployeeList")
    public ResponseEntity getZjTzProjectEmployeeList(@RequestBody(required = false) ZjTzProjectEmployee zjTzProjectEmployee) {
        return zjTzProjectEmployeeService.getZjTzProjectEmployeeListByCondition(zjTzProjectEmployee);
    }

    @ApiOperation(value="查询详情项目人员", notes="查询详情项目人员")
    @ApiImplicitParam(name = "zjTzProjectEmployee", value = "项目人员entity", dataType = "ZjTzProjectEmployee")
    @RequireToken
    @PostMapping("/getZjTzProjectEmployeeDetails")
    public ResponseEntity getZjTzProjectEmployeeDetails(@RequestBody(required = false) ZjTzProjectEmployee zjTzProjectEmployee) {
        return zjTzProjectEmployeeService.getZjTzProjectEmployeeDetails(zjTzProjectEmployee);
    }

    @ApiOperation(value="新增项目人员", notes="新增项目人员")
    @ApiImplicitParam(name = "zjTzProjectEmployee", value = "项目人员entity", dataType = "ZjTzProjectEmployee")
    @RequireToken
    @PostMapping("/addZjTzProjectEmployee")
    public ResponseEntity addZjTzProjectEmployee(@RequestBody(required = false) ZjTzProjectEmployee zjTzProjectEmployee) {
        return zjTzProjectEmployeeService.saveZjTzProjectEmployee(zjTzProjectEmployee);
    }

    @ApiOperation(value="更新项目人员", notes="更新项目人员")
    @ApiImplicitParam(name = "zjTzProjectEmployee", value = "项目人员entity", dataType = "ZjTzProjectEmployee")
    @RequireToken
    @PostMapping("/updateZjTzProjectEmployee")
    public ResponseEntity updateZjTzProjectEmployee(@RequestBody(required = false) ZjTzProjectEmployee zjTzProjectEmployee) {
        return zjTzProjectEmployeeService.updateZjTzProjectEmployee(zjTzProjectEmployee);
    }

    @ApiOperation(value="删除项目人员", notes="删除项目人员")
    @ApiImplicitParam(name = "zjTzProjectEmployeeList", value = "项目人员List", dataType = "List<ZjTzProjectEmployee>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZjTzProjectEmployee")
    public ResponseEntity batchDeleteUpdateZjTzProjectEmployee(@RequestBody(required = false) List<ZjTzProjectEmployee> zjTzProjectEmployeeList) {
        return zjTzProjectEmployeeService.batchDeleteUpdateZjTzProjectEmployee(zjTzProjectEmployeeList);
    }

    @ApiOperation(value="批量审核项目人员", notes="批量审核项目人员")
    @ApiImplicitParam(name = "zjTzProjectEmployeeList", value = "批量审核项目人员List", dataType = "List<ZjTzProjectEmployee>")
    @RequireToken
    @PostMapping("/checkZjTzProjectEmployee")
    public ResponseEntity checkZjTzProjectEmployee(@RequestBody(required = false) List<ZjTzProjectEmployee> zjTzProjectEmployeeList) {
        return zjTzProjectEmployeeService.checkZjTzProjectEmployee(zjTzProjectEmployeeList);
    }

    @ApiOperation(value="批量撤回项目人员", notes="批量撤回项目人员")
    @ApiImplicitParam(name = "zjTzProjectEmployeeList", value = "批量撤回项目人员List", dataType = "List<ZjTzProjectEmployee>")
    @RequireToken
    @PostMapping("/recallZjTzProjectEmployee")
    public ResponseEntity recallZjTzProjectEmployee(@RequestBody(required = false) List<ZjTzProjectEmployee> zjTzProjectEmployeeList) {
        return zjTzProjectEmployeeService.recallZjTzProjectEmployee(zjTzProjectEmployeeList);
    }


    @ApiOperation(value="批量添加项目中的人员", notes="批量添加项目中的人员")
    @ApiImplicitParam(name = "ZjTzProjectEmployee", value = "批量添加项目中的人员", dataType = "ZjTzProjectEmployee")
    @RequireToken
    @PostMapping("/recallAddZjTzProjectEmployee")
    public ResponseEntity recallAddZjTzProjectEmployee(@RequestBody(required = false) ZjTzProjectEmployee ZjTzProjectEmployee) {
        return zjTzProjectEmployeeService.recallAddZjTzProjectEmployee(ZjTzProjectEmployee);
    }


    //    @ApiOperation(value="查询项目", notes="查询项目")
//    @ApiImplicitParam(name = "zjTzProjectEmployeeList", value = "项目人员List", dataType = "List<ZjTzProjectEmployee>")
//    @RequireToken
//    @PostMapping("/getZjTzProjectAllList")
//    public ResponseEntity getZjTzProjectAllList(@RequestBody(required = false) ZjTzProManage zjTzProManage) {
//        return zjTzProjectEmployeeService.getZjTzProjectAllList(zjTzProManage);
//    }


}
