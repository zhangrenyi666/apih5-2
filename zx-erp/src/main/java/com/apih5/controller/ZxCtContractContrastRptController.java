package com.apih5.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.apih5.framework.annotation.RequireToken;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxCtContractContrastRpt;
import com.apih5.service.ZxCtContractContrastRptService;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

@RestController
public class ZxCtContractContrastRptController {

    @Autowired(required = true)
    private ZxCtContractContrastRptService zxCtContractContrastRptService;

    @ApiOperation(value="查询物资合同与采购对比台账", notes="查询物资合同与采购对比台账")
    @ApiImplicitParam(name = "zxCtContractContrastRpt", value = "物资合同与采购对比台账entity", dataType = "ZxCtContractContrastRpt")
    @RequireToken
    @PostMapping("/getZxCtContractContrastRptList")
    public ResponseEntity getZxCtContractContrastRptList(@RequestBody(required = false) ZxCtContractContrastRpt zxCtContractContrastRpt) {
        return zxCtContractContrastRptService.getZxCtContractContrastRptListByCondition(zxCtContractContrastRpt);
    }

    @ApiOperation(value="查询详情物资合同与采购对比台账", notes="查询详情物资合同与采购对比台账")
    @ApiImplicitParam(name = "zxCtContractContrastRpt", value = "物资合同与采购对比台账entity", dataType = "ZxCtContractContrastRpt")
    @RequireToken
    @PostMapping("/getZxCtContractContrastRptDetail")
    public ResponseEntity getZxCtContractContrastRptDetail(@RequestBody(required = false) ZxCtContractContrastRpt zxCtContractContrastRpt) {
        return zxCtContractContrastRptService.getZxCtContractContrastRptDetail(zxCtContractContrastRpt);
    }

    @ApiOperation(value="新增物资合同与采购对比台账", notes="新增物资合同与采购对比台账")
    @ApiImplicitParam(name = "zxCtContractContrastRpt", value = "物资合同与采购对比台账entity", dataType = "ZxCtContractContrastRpt")
    @RequireToken
    @PostMapping("/addZxCtContractContrastRpt")
    public ResponseEntity addZxCtContractContrastRpt(@RequestBody(required = false) ZxCtContractContrastRpt zxCtContractContrastRpt) {
        return zxCtContractContrastRptService.saveZxCtContractContrastRpt(zxCtContractContrastRpt);
    }

    @ApiOperation(value="更新物资合同与采购对比台账", notes="更新物资合同与采购对比台账")
    @ApiImplicitParam(name = "zxCtContractContrastRpt", value = "物资合同与采购对比台账entity", dataType = "ZxCtContractContrastRpt")
    @RequireToken
    @PostMapping("/updateZxCtContractContrastRpt")
    public ResponseEntity updateZxCtContractContrastRpt(@RequestBody(required = false) ZxCtContractContrastRpt zxCtContractContrastRpt) {
        return zxCtContractContrastRptService.updateZxCtContractContrastRpt(zxCtContractContrastRpt);
    }

    @ApiOperation(value="删除物资合同与采购对比台账", notes="删除物资合同与采购对比台账")
    @ApiImplicitParam(name = "zxCtContractContrastRptList", value = "物资合同与采购对比台账List", dataType = "List<ZxCtContractContrastRpt>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxCtContractContrastRpt")
    public ResponseEntity batchDeleteUpdateZxCtContractContrastRpt(@RequestBody(required = false) List<ZxCtContractContrastRpt> zxCtContractContrastRptList) {
        return zxCtContractContrastRptService.batchDeleteUpdateZxCtContractContrastRpt(zxCtContractContrastRptList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    
    @ApiOperation(value="报表导出物资合同与采购对比台账", notes="报表导出物资合同与采购对比台账")
    @ApiImplicitParam(name = "zxCtContractContrastRpt", value = "设备台账entity", dataType = "ZxCtContractContrastRpt")
    @PostMapping("/ureportZxCtContractContrastRpt")
    public List<ZxCtContractContrastRpt> ureportZxCtContractContrastRpt(@RequestBody(required = false) ZxCtContractContrastRpt zxCtContractContrastRpt) {
        return zxCtContractContrastRptService.ureportZxCtContractContrastRpt(zxCtContractContrastRpt);
    }
    
    @ApiOperation(value="报表导出物资合同与采购对比台账", notes="报表导出物资合同与采购对比台账")
    @ApiImplicitParam(name = "zxCtContractContrastRpt", value = "设备台账entity", dataType = "ZxCtContractContrastRpt")
    @RequireToken
    @PostMapping("/ureportZxCtContractContrastRptIdle")
    public ResponseEntity ureportZxCtContractContrastRptIdle(@RequestBody(required = false) ZxCtContractContrastRpt zxCtContractContrastRpt) {
        return zxCtContractContrastRptService.ureportZxCtContractContrastRptIdle(zxCtContractContrastRpt);
    }
}
