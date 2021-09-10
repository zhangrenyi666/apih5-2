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
import com.apih5.mybatis.pojo.ZxCmFilterOrg;
import com.apih5.service.ZxCmFilterOrgService;

@RestController
public class ZxCmFilterOrgController {

    @Autowired(required = true)
    private ZxCmFilterOrgService zxCmFilterOrgService;

    @ApiOperation(value="查询业主合同管理-项目业务过滤（原表iecm_filterOrg）", notes="查询业主合同管理-项目业务过滤（原表iecm_filterOrg）")
    @ApiImplicitParam(name = "zxCmFilterOrg", value = "业主合同管理-项目业务过滤（原表iecm_filterOrg）entity", dataType = "ZxCmFilterOrg")
    @RequireToken
    @PostMapping("/getZxCmFilterOrgList")
    public ResponseEntity getZxCmFilterOrgList(@RequestBody(required = false) ZxCmFilterOrg zxCmFilterOrg) {
        return zxCmFilterOrgService.getZxCmFilterOrgListByCondition(zxCmFilterOrg);
    }

    @ApiOperation(value="查询详情业主合同管理-项目业务过滤（原表iecm_filterOrg）", notes="查询详情业主合同管理-项目业务过滤（原表iecm_filterOrg）")
    @ApiImplicitParam(name = "zxCmFilterOrg", value = "业主合同管理-项目业务过滤（原表iecm_filterOrg）entity", dataType = "ZxCmFilterOrg")
    @RequireToken
    @PostMapping("/getZxCmFilterOrgDetail")
    public ResponseEntity getZxCmFilterOrgDetail(@RequestBody(required = false) ZxCmFilterOrg zxCmFilterOrg) {
        return zxCmFilterOrgService.getZxCmFilterOrgDetail(zxCmFilterOrg);
    }

    @ApiOperation(value="新增业主合同管理-项目业务过滤（原表iecm_filterOrg）", notes="新增业主合同管理-项目业务过滤（原表iecm_filterOrg）")
    @ApiImplicitParam(name = "zxCmFilterOrg", value = "业主合同管理-项目业务过滤（原表iecm_filterOrg）entity", dataType = "ZxCmFilterOrg")
    @RequireToken
    @PostMapping("/addZxCmFilterOrg")
    public ResponseEntity addZxCmFilterOrg(@RequestBody(required = false) ZxCmFilterOrg zxCmFilterOrg) {
        return zxCmFilterOrgService.saveZxCmFilterOrg(zxCmFilterOrg);
    }

    @ApiOperation(value="更新业主合同管理-项目业务过滤（原表iecm_filterOrg）", notes="更新业主合同管理-项目业务过滤（原表iecm_filterOrg）")
    @ApiImplicitParam(name = "zxCmFilterOrg", value = "业主合同管理-项目业务过滤（原表iecm_filterOrg）entity", dataType = "ZxCmFilterOrg")
    @RequireToken
    @PostMapping("/updateZxCmFilterOrg")
    public ResponseEntity updateZxCmFilterOrg(@RequestBody(required = false) ZxCmFilterOrg zxCmFilterOrg) {
        return zxCmFilterOrgService.updateZxCmFilterOrg(zxCmFilterOrg);
    }

    @ApiOperation(value="删除业主合同管理-项目业务过滤（原表iecm_filterOrg）", notes="删除业主合同管理-项目业务过滤（原表iecm_filterOrg）")
    @ApiImplicitParam(name = "zxCmFilterOrgList", value = "业主合同管理-项目业务过滤（原表iecm_filterOrg）List", dataType = "List<ZxCmFilterOrg>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxCmFilterOrg")
    public ResponseEntity batchDeleteUpdateZxCmFilterOrg(@RequestBody(required = false) List<ZxCmFilterOrg> zxCmFilterOrgList) {
        return zxCmFilterOrgService.batchDeleteUpdateZxCmFilterOrg(zxCmFilterOrgList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
