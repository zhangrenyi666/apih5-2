package com.apih5.controller;

import java.util.List;

import com.apih5.mybatis.pojo.ZjTzEmployeeEducation;
import com.apih5.mybatis.pojo.ZjTzEmployeeWork;
import com.apih5.mybatis.pojo.ZjTzProjectAndEmployee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import com.apih5.framework.annotation.RequireToken;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjTzEmployeeInfo;
import com.apih5.service.ZjTzEmployeeInfoService;

@RestController
public class ZjTzEmployeeInfoController {

    @Autowired(required = true)
    private ZjTzEmployeeInfoService zjTzEmployeeInfoService;

    @ApiOperation(value="查询人员库-员工信息", notes="查询人员库-员工信息")
    @ApiImplicitParam(name = "zjTzEmployeeInfo", value = "人员库-员工信息entity", dataType = "ZjTzEmployeeInfo")
    @RequireToken
    @PostMapping("/getZjTzEmployeeInfoList")
    public ResponseEntity getZjTzEmployeeInfoList(@RequestBody(required = false) ZjTzEmployeeInfo zjTzEmployeeInfo) {
        return zjTzEmployeeInfoService.getZjTzEmployeeInfoListByCondition(zjTzEmployeeInfo);
    }

    @ApiOperation(value="查询详情人员库-员工信息", notes="查询详情人员库-员工信息")
    @ApiImplicitParam(name = "zjTzEmployeeInfo", value = "人员库-员工信息entity", dataType = "ZjTzEmployeeInfo")
    @RequireToken
    @PostMapping("/getZjTzEmployeeInfoDetails")
    public ResponseEntity getZjTzEmployeeInfoDetails(@RequestBody(required = false) ZjTzEmployeeInfo zjTzEmployeeInfo) {
        return zjTzEmployeeInfoService.getZjTzEmployeeInfoDetails(zjTzEmployeeInfo);
    }

    @ApiOperation(value="新增人员库-员工信息", notes="新增人员库-员工信息")
    @ApiImplicitParam(name = "zjTzEmployeeInfo", value = "人员库-员工信息entity", dataType = "ZjTzEmployeeInfo")
    @RequireToken
    @PostMapping("/addZjTzEmployeeInfo")
    public ResponseEntity addZjTzEmployeeInfo(@RequestBody(required = false) ZjTzEmployeeInfo zjTzEmployeeInfo) {
        return zjTzEmployeeInfoService.saveZjTzEmployeeInfo(zjTzEmployeeInfo);
    }

    @ApiOperation(value="更新人员库-员工信息", notes="更新人员库-员工信息")
    @ApiImplicitParam(name = "zjTzEmployeeInfo", value = "人员库-员工信息entity", dataType = "ZjTzEmployeeInfo")
    @RequireToken
    @PostMapping("/updateZjTzEmployeeInfo")
    public ResponseEntity updateZjTzEmployeeInfo(@RequestBody(required = false) ZjTzEmployeeInfo zjTzEmployeeInfo) {
        return zjTzEmployeeInfoService.updateZjTzEmployeeInfo(zjTzEmployeeInfo);
    }

    @ApiOperation(value="删除人员库-员工信息", notes="删除人员库-员工信息")
    @ApiImplicitParam(name = "zjTzEmployeeInfoList", value = "人员库-员工信息List", dataType = "List<ZjTzEmployeeInfo>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZjTzEmployeeInfo")
    public ResponseEntity batchDeleteUpdateZjTzEmployeeInfo(@RequestBody(required = false) List<ZjTzEmployeeInfo> zjTzEmployeeInfoList) {
        return zjTzEmployeeInfoService.batchDeleteUpdateZjTzEmployeeInfo(zjTzEmployeeInfoList);
    }

    @ApiOperation(value="批量导出人员库-员工信息数据", notes="批量导出人员库-员工信息数据")
    @ApiImplicitParam(name = "zjTzEmployeeInfo", value = "批量导出人员库-员工信息", dataType = "ZjTzEmployeeInfo")
//    @RequireToken
    @PostMapping("/exportZjTzEmployeeInfo")
    public ZjTzEmployeeInfo exportZjTzEmployeeInfo(@RequestBody(required = false) ZjTzEmployeeInfo zjTzEmployeeInfo) {
        return zjTzEmployeeInfoService.exportZjTzEmployeeInfo(zjTzEmployeeInfo);
    }

    @ApiOperation(value="批量导出人员库-员工教育经历", notes="批量导出人员库-员工信息数据")
    @ApiImplicitParam(name = "zjTzEmployeeInfo", value = "批量导出人员库-员工信息", dataType = "ZjTzEmployeeInfo")
//    @RequireToken
    @PostMapping("/exportZjTzEmployeeInfoEducation")
    public List<ZjTzEmployeeEducation> exportZjTzEmployeeInfoEducation(@RequestBody(required = false) ZjTzEmployeeInfo zjTzEmployeeInfo) {
        return zjTzEmployeeInfoService.exportZjTzEmployeeInfoEducation(zjTzEmployeeInfo);
    }

    @ApiOperation(value="批量导出人员库-员工工作经历", notes="批量导出人员库-员工信息数据")
    @ApiImplicitParam(name = "zjTzEmployeeInfo", value = "批量导出人员库-员工信息", dataType = "ZjTzEmployeeInfo")
//    @RequireToken
    @PostMapping("/exportZjTzEmployeeInfoWork")
    public List<ZjTzEmployeeWork> exportZjTzEmployeeInfoWork(@RequestBody(required = false) ZjTzEmployeeInfo zjTzEmployeeInfo) {
        return zjTzEmployeeInfoService.exportZjTzEmployeeInfoWork(zjTzEmployeeInfo);
    }

    @ApiOperation(value="批量导出人员库-员工信息", notes="批量导出人员库-员工信息")
    @ApiImplicitParam(name = "ZjTzEmployeeInfo", value = "批量导出人员库-员工信息entity", dataType = "ZjTzEmployeeInfo")
    @PostMapping("/printZjTzEmployeeInfo")
    public ResponseEntity printZjTzEmployeeInfo(@RequestBody(required = false) List<ZjTzEmployeeInfo> zjTzEmployeeInfoList) {
        return zjTzEmployeeInfoService.printZjTzEmployeeInfo(zjTzEmployeeInfoList);
    }

    @ApiOperation(value="查询人员库-员工信息", notes="查询人员库-员工信息")
    @ApiImplicitParam(name = "zjTzEmployeeInfo", value = "人员库-员工信息entity", dataType = "ZjTzEmployeeInfo")
    @RequireToken
    @PostMapping("/getZjTzEmployeeInfoListSelectPerson")
    public ResponseEntity getZjTzEmployeeInfoListSelectPerson(@RequestBody(required = false) ZjTzEmployeeInfo zjTzEmployeeInfoList) {
        return zjTzEmployeeInfoService.getZjTzEmployeeInfoListSelectPerson(zjTzEmployeeInfoList);
    }

    //泽华
    /**
     * 人员库调入调出接口
     * @param zjTzEmployeeInfo
     * @return
     */
    @ApiOperation(value="更新人员库-员工信息", notes="更新人员库-员工信息")
    @ApiImplicitParam(name = "zjTzEmployeeInfo", value = "人员库-员工信息entity", dataType = "ZjTzEmployeeInfo")
    @RequireToken
    @PostMapping("/updateZjTzEmployeeInfoListSelectPerson")
    public ResponseEntity updateZjTzEmployeeInfoPersonDepart(@RequestBody(required = false) ZjTzEmployeeInfo zjTzEmployeeInfo) {
        return zjTzEmployeeInfoService.updateZjTzEmployeeInfoPersonDepart(zjTzEmployeeInfo);
    }

    @ApiOperation(value="根据身份证,排查人员是否存在", notes="根据身份证,排查人员是否存在")
    @ApiImplicitParam(name = "zjTzEmployeeInfo", value = "人员库-员工信息entity", dataType = "ZjTzEmployeeInfo")
    @RequireToken
    @PostMapping("/getZjTzEmployeeInfoByIdCard")
    public ResponseEntity getZjTzEmployeeInfoByIdCard(@RequestBody(required = false) ZjTzEmployeeInfo zjTzEmployeeInfo) {
        return zjTzEmployeeInfoService.getZjTzEmployeeInfoByIdCard(zjTzEmployeeInfo);
    }

    @ApiOperation(value="在项目人员页中把人才库的人员调入到项目中", notes="在项目人员页中把人才库的人员调入到项目中")
    @ApiImplicitParam(name = "zjTzEmployeeInfo", value = "人员库-员工信息entity", dataType = "ZjTzEmployeeInfo")
    @RequireToken
    @PostMapping("/importProjectZjTzEmployeeInfo")
    public ResponseEntity importProjectZjTzEmployeeInfo(@RequestBody(required = false) ZjTzEmployeeInfo zjTzEmployeeInfo) {
        return zjTzEmployeeInfoService.importProjectZjTzEmployeeInfo(zjTzEmployeeInfo);
    }

    @ApiOperation(value="批量-在项目人员页中把人才库的人员调入到项目中", notes="在项目人员页中把人才库的人员调入到项目中")
    @ApiImplicitParam(name = "zjTzEmployeeInfo", value = "人员库-员工信息entity", dataType = "ZjTzEmployeeInfo")
    @RequireToken
    @PostMapping("/importProjectZjTzEmployeeInfoList")
    public ResponseEntity importProjectZjTzEmployeeInfoList(@RequestBody(required = false) List<ZjTzEmployeeInfo> zjTzEmployeeInfoList) {
        return zjTzEmployeeInfoService.importProjectZjTzEmployeeInfoList(zjTzEmployeeInfoList);
    }

    @ApiOperation(value="在项目中人员离职", notes="在项目中人员离职")
    @ApiImplicitParam(name = "zjTzEmployeeInfo", value = "人员库-员工信息entity", dataType = "ZjTzEmployeeInfo")
    @RequireToken
    @PostMapping("/leaveOfficeZjTzEmployeeInfoList")
    public ResponseEntity leaveOfficeZjTzEmployeeInfoList(@RequestBody(required = false) List<ZjTzProjectAndEmployee> zjTzProjectAndEmployeeList) {
        return zjTzEmployeeInfoService.leaveOfficeZjTzEmployeeInfoList(zjTzProjectAndEmployeeList);
    }



}


