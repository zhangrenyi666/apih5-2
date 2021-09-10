package com.apih5.controller;

import java.util.List;
import com.apih5.mybatis.pojo.ZxCtContrApplyWorks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import com.apih5.framework.annotation.RequireToken;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxCtContrApply;
import com.apih5.service.ZxCtContrApplyService;

@RestController
public class ZxCtContrApplyController {

    @Autowired(required = true)
    private ZxCtContrApplyService zxCtContrApplyService;

    @ApiOperation(value="查询工程施工合同", notes="查询工程施工合同")
    @ApiImplicitParam(name = "zxCtContrApply", value = "工程施工合同entity", dataType = "ZxCtContrApply")
    @RequireToken
    @PostMapping("/getZxCtContrApplyList")
    public ResponseEntity getZxCtContrApplyList(@RequestBody(required = false) ZxCtContrApply zxCtContrApply) {
        return zxCtContrApplyService.getZxCtContrApplyListByCondition(zxCtContrApply);
    }

    @ApiOperation(value="查询详情工程施工合同", notes="查询详情工程施工合同")
    @ApiImplicitParam(name = "zxCtContrApply", value = "工程施工合同entity", dataType = "ZxCtContrApply")
    @RequireToken
    @PostMapping("/getZxCtContrApplyDetail")
    public ResponseEntity getZxCtContrApplyDetail(@RequestBody(required = false) ZxCtContrApply zxCtContrApply) {
        return zxCtContrApplyService.getZxCtContrApplyDetail(zxCtContrApply);
    }

    @ApiOperation(value="新增工程施工合同", notes="新增工程施工合同")
    @ApiImplicitParam(name = "zxCtContrApply", value = "工程施工合同entity", dataType = "ZxCtContrApply")
    @RequireToken
    @PostMapping("/addZxCtContrApply")
    public ResponseEntity addZxCtContrApply(@RequestBody(required = false) ZxCtContrApply zxCtContrApply) {
        return zxCtContrApplyService.saveZxCtContrApply(zxCtContrApply);
    }

    @ApiOperation(value="更新工程施工合同", notes="更新工程施工合同")
    @ApiImplicitParam(name = "zxCtContrApply", value = "工程施工合同entity", dataType = "ZxCtContrApply")
    @RequireToken
    @PostMapping("/updateZxCtContrApply")
    public ResponseEntity updateZxCtContrApply(@RequestBody(required = false) ZxCtContrApply zxCtContrApply) {
        return zxCtContrApplyService.updateZxCtContrApply(zxCtContrApply);
    }

    @ApiOperation(value="删除工程施工合同", notes="删除工程施工合同")
    @ApiImplicitParam(name = "zxCtContrApplyList", value = "工程施工合同List", dataType = "List<ZxCtContrApply>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxCtContrApply")
    public ResponseEntity batchDeleteUpdateZxCtContrApply(@RequestBody(required = false) List<ZxCtContrApply> zxCtContrApplyList) {
        return zxCtContrApplyService.batchDeleteUpdateZxCtContrApply(zxCtContrApplyList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

    @ApiOperation(value="评审状态为“正在评审、评审通过、评审不通过”时，查看工程施工合同清单明细", notes="查看工程施工合同清单明细")
    @ApiImplicitParam(name = "zxCtContrApply", value = "工程施工合同entity", dataType = "ZxCtContrApply")
    @RequireToken
    @PostMapping("/getZxCtContrApplyWorksDetailList")
    public ResponseEntity getZxCtContrApplyWorksDetailList(@RequestBody(required = false) ZxCtContrApply zxCtContrApply) {
        return zxCtContrApplyService.getZxCtContrApplyWorksDetailList(zxCtContrApply);
    }

    @ApiOperation(value="工程施工合同清单明细导入", notes="工程施工合同清单明细导入")
    @ApiImplicitParam(name = "zxCtContrApplyWorks", value = "工程施工合同清单明细entity", dataType = "ZxCtContrApplyWorks")
    @RequireToken
    @PostMapping("/importZxCtContrApplyWorks")
    public ResponseEntity importZxCtContrApplyWorks(@RequestBody(required = false) ZxCtContrApplyWorks zxCtContrApplyWorks) {
        return zxCtContrApplyService.importZxCtContrApplyWorks(zxCtContrApplyWorks);
    }

    @ApiOperation(value="查询施工合同清单明细树", notes="查询施工合同清单明细树")
    @ApiImplicitParam(name = "zxCtContrApplyWorks", value = "工程施工合同清单明细entity", dataType = "ZxCtContrApplyWorks")
    @RequireToken
    @PostMapping("/getZxCtContrApplyWorksTree")
    public ResponseEntity getZxCtContrApplyWorksTree(@RequestBody(required = false) ZxCtContrApplyWorks zxCtContrApplyWorks) {
        return zxCtContrApplyService.getZxCtContrApplyWorksTree(zxCtContrApplyWorks);
    }

    //前台传递点击菜单的parentId
    @ApiOperation(value="点击施工合同清单明细树菜单获取数据", notes="点击施工合同清单明细树")
    @ApiImplicitParam(name = "zxCtContrApplyWorks", value = "工程施工合同清单明细entity", dataType = "ZxCtContrApplyWorks")
    @RequireToken
    @PostMapping("/getZxCtContrApplyWorksTreeData")
    public ResponseEntity getZxCtContrApplyWorksTreeData(@RequestBody(required = false) ZxCtContrApplyWorks zxCtContrApplyWorks) {
        return zxCtContrApplyService.getZxCtContrApplyWorksTreeData(zxCtContrApplyWorks);
    }

    @ApiOperation(value = "获取施工类合同评审新增时甲方名称", notes = "获取获取施工类合同评审新增时甲方名称")
    @ApiImplicitParam(name = "zxCtContrApply", value = "工程施工合同entity", dataType = "ZxCtContrApply")
    @RequireToken
    @PostMapping("/getZxCtContrApplyFirstName")
    public ResponseEntity getZxCtContrApplyFirstName(@RequestBody(required = false) ZxCtContrApply ZxCtContrApply) {
        return zxCtContrApplyService.getZxCtContrApplyFirstName(ZxCtContrApply);
    }
}
