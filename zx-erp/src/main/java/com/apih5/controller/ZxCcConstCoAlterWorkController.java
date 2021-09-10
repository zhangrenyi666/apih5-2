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
import com.apih5.mybatis.pojo.ZxCcConstCoAlterWork;
import com.apih5.service.ZxCcConstCoAlterWorkService;

@RestController
public class ZxCcConstCoAlterWorkController {

    @Autowired(required = true)
    private ZxCcConstCoAlterWorkService zxCcConstCoAlterWorkService;

    @ApiOperation(value="查询工程施工类合同补充协议清单明", notes="查询工程施工类合同补充协议清单明")
    @ApiImplicitParam(name = "zxCcConstCoAlterWork", value = "工程施工类合同补充协议清单明entity", dataType = "ZxCcConstCoAlterWork")
    @RequireToken
    @PostMapping("/getZxCcConstCoAlterWorkList")
    public ResponseEntity getZxCcConstCoAlterWorkList(@RequestBody(required = false) ZxCcConstCoAlterWork zxCcConstCoAlterWork) {
        return zxCcConstCoAlterWorkService.getZxCcConstCoAlterWorkListByCondition(zxCcConstCoAlterWork);
    }

    @ApiOperation(value="查询详情工程施工类合同补充协议清单明", notes="查询详情工程施工类合同补充协议清单明")
    @ApiImplicitParam(name = "zxCcConstCoAlterWork", value = "工程施工类合同补充协议清单明entity", dataType = "ZxCcConstCoAlterWork")
    @RequireToken
    @PostMapping("/getZxCcConstCoAlterWorkDetail")
    public ResponseEntity getZxCcConstCoAlterWorkDetail(@RequestBody(required = false) ZxCcConstCoAlterWork zxCcConstCoAlterWork) {
        return zxCcConstCoAlterWorkService.getZxCcConstCoAlterWorkDetail(zxCcConstCoAlterWork);
    }

    @ApiOperation(value="新增工程施工类合同补充协议清单明", notes="新增工程施工类合同补充协议清单明")
    @ApiImplicitParam(name = "zxCcConstCoAlterWork", value = "工程施工类合同补充协议清单明entity", dataType = "ZxCcConstCoAlterWork")
    @RequireToken
    @PostMapping("/addZxCcConstCoAlterWork")
    public ResponseEntity addZxCcConstCoAlterWork(@RequestBody(required = false) ZxCcConstCoAlterWork zxCcConstCoAlterWork) {
        return zxCcConstCoAlterWorkService.saveZxCcConstCoAlterWork(zxCcConstCoAlterWork);
    }

    @ApiOperation(value="更新工程施工类合同补充协议清单明", notes="更新工程施工类合同补充协议清单明")
    @ApiImplicitParam(name = "zxCcConstCoAlterWork", value = "工程施工类合同补充协议清单明entity", dataType = "ZxCcConstCoAlterWork")
    @RequireToken
    @PostMapping("/updateZxCcConstCoAlterWork")
    public ResponseEntity updateZxCcConstCoAlterWork(@RequestBody(required = false) ZxCcConstCoAlterWork zxCcConstCoAlterWork) {
        return zxCcConstCoAlterWorkService.updateZxCcConstCoAlterWork(zxCcConstCoAlterWork);
    }

    @ApiOperation(value="删除工程施工类合同补充协议清单明", notes="删除工程施工类合同补充协议清单明")
    @ApiImplicitParam(name = "zxCcConstCoAlterWorkList", value = "工程施工类合同补充协议清单明List", dataType = "List<ZxCcConstCoAlterWork>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxCcConstCoAlterWork")
    public ResponseEntity batchDeleteUpdateZxCcConstCoAlterWork(@RequestBody(required = false) List<ZxCcConstCoAlterWork> zxCcConstCoAlterWorkList) {
        return zxCcConstCoAlterWorkService.batchDeleteUpdateZxCcConstCoAlterWork(zxCcConstCoAlterWorkList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
