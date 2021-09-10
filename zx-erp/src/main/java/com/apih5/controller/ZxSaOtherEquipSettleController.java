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
import com.apih5.mybatis.pojo.ZxSaOtherEquipSettle;
import com.apih5.service.ZxSaOtherEquipSettleService;

import javax.servlet.http.HttpServletResponse;

@RestController
public class ZxSaOtherEquipSettleController {

    @Autowired(required = true)
    private ZxSaOtherEquipSettleService zxSaOtherEquipSettleService;

    @ApiOperation(value="查询其他类结算", notes="查询其他类结算")
    @ApiImplicitParam(name = "zxSaOtherEquipSettle", value = "其他类结算entity", dataType = "ZxSaOtherEquipSettle")
    @RequireToken
    @PostMapping("/getZxSaOtherEquipSettleList")
    public ResponseEntity getZxSaOtherEquipSettleList(@RequestBody(required = false) ZxSaOtherEquipSettle zxSaOtherEquipSettle) {
        return zxSaOtherEquipSettleService.getZxSaOtherEquipSettleListByCondition(zxSaOtherEquipSettle);
    }

    @ApiOperation(value="查询详情其他类结算", notes="查询详情其他类结算")
    @ApiImplicitParam(name = "zxSaOtherEquipSettle", value = "其他类结算entity", dataType = "ZxSaOtherEquipSettle")
    @RequireToken
    @PostMapping("/getZxSaOtherEquipSettleDetail")
    public ResponseEntity getZxSaOtherEquipSettleDetail(@RequestBody(required = false) ZxSaOtherEquipSettle zxSaOtherEquipSettle) {
        return zxSaOtherEquipSettleService.getZxSaOtherEquipSettleDetail(zxSaOtherEquipSettle);
    }

    @ApiOperation(value="新增其他类结算", notes="新增其他类结算")
    @ApiImplicitParam(name = "zxSaOtherEquipSettle", value = "其他类结算entity", dataType = "ZxSaOtherEquipSettle")
    @RequireToken
    @PostMapping("/addZxSaOtherEquipSettle")
    public ResponseEntity addZxSaOtherEquipSettle(@RequestBody(required = false) ZxSaOtherEquipSettle zxSaOtherEquipSettle) {
        return zxSaOtherEquipSettleService.saveZxSaOtherEquipSettle(zxSaOtherEquipSettle);
    }

    @ApiOperation(value="更新其他类结算", notes="更新其他类结算")
    @ApiImplicitParam(name = "zxSaOtherEquipSettle", value = "其他类结算entity", dataType = "ZxSaOtherEquipSettle")
    @RequireToken
    @PostMapping("/updateZxSaOtherEquipSettle")
    public ResponseEntity updateZxSaOtherEquipSettle(@RequestBody(required = false) ZxSaOtherEquipSettle zxSaOtherEquipSettle) {
        return zxSaOtherEquipSettleService.updateZxSaOtherEquipSettle(zxSaOtherEquipSettle);
    }

    @ApiOperation(value="删除其他类结算", notes="删除其他类结算")
    @ApiImplicitParam(name = "zxSaOtherEquipSettleList", value = "其他类结算List", dataType = "List<ZxSaOtherEquipSettle>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxSaOtherEquipSettle")
    public ResponseEntity batchDeleteUpdateZxSaOtherEquipSettle(@RequestBody(required = false) List<ZxSaOtherEquipSettle> zxSaOtherEquipSettleList) {
        return zxSaOtherEquipSettleService.batchDeleteUpdateZxSaOtherEquipSettle(zxSaOtherEquipSettleList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

    @ApiOperation(value="其他类结算根据项目名称--查询合同编号", notes="其他类结算根据项目名称--查询合同编号")
    @ApiImplicitParam(name = "zxSaOtherEquipSettle", value = "其他类结算entity", dataType = "ZxSaOtherEquipSettle")
    @RequireToken
    @PostMapping("/getZxSaOtherEquipSettleContractNo")
    public ResponseEntity getZxSaOtherEquipSettleContractNo(@RequestBody(required = false) ZxSaOtherEquipSettle zxSaOtherEquipSettle) {
        return zxSaOtherEquipSettleService.getZxSaOtherEquipSettleContractNo(zxSaOtherEquipSettle);
    }

    @ApiOperation(value="其他类结算根据合同管理Id--查询是否首次结算", notes="其他类结算根据合同管理Id--查询是否首次结算")
    @ApiImplicitParam(name = "zxSaOtherEquipSettle", value = "其他类结算entity", dataType = "ZxSaOtherEquipSettle")
    @RequireToken
    @PostMapping("/getIsFirstByContractId")
    public ResponseEntity getIsFirstByContractId(@RequestBody(required = false) ZxSaOtherEquipSettle zxSaOtherEquipSettle) {
        return zxSaOtherEquipSettleService.getIsFirstByContractId(zxSaOtherEquipSettle);
    }

    @ApiOperation(value="其他类结算根据合同管理Id--查询当前合同对应的结算情况是否为已最终结算", notes="其他类结算根据合同管理Id--查询当前合同对应的结算情况是否为已最终结算")
    @ApiImplicitParam(name = "zxSaOtherEquipSettle", value = "其他类结算entity", dataType = "ZxSaOtherEquipSettle")
    @RequireToken
    @PostMapping("/getSettleTypeByContractId")
    public ResponseEntity getSettleTypeByContractId(@RequestBody(required = false) ZxSaOtherEquipSettle zxSaOtherEquipSettle) {
        return zxSaOtherEquipSettleService.getSettleTypeByContractId(zxSaOtherEquipSettle);
    }

    @ApiOperation(value="其他类结算评审申请", notes="新增其他类结算评审申请")
    @ApiImplicitParam(name = "zxSaOtherEquipSettle", value = "其他类结算entity", dataType = "ZxSaOtherEquipSettle")
    @RequireToken
    @PostMapping("/zxSaOtherEquipSettleReviewApply")
    public ResponseEntity zxSaOtherEquipSettleReviewApply(@RequestBody(required = false) ZxSaOtherEquipSettle zxSaOtherEquipSettle) {
        return zxSaOtherEquipSettleService.zxSaOtherEquipSettleReviewApply(zxSaOtherEquipSettle);
    }

    @ApiOperation(value="根据合同编号和结算期次自动生成其他类结算单编号,结算单初始化顺序号,结算签认单编号", notes="根据合同编号和结算期次自动生成其他类结算单编号,结算单初始化顺序号,结算签认单编号")
    @ApiImplicitParam(name = "zxSaOtherEquipSettle", value = "其他类结算entity", dataType = "ZxSaOtherEquipSettle")
    @RequireToken
    @PostMapping("/getZxSaOtherEquipSettleBillNo")
    public ResponseEntity getZxSaOtherEquipSettleBillNo(@RequestBody(required = false) ZxSaOtherEquipSettle zxSaOtherEquipSettle) {
        return zxSaOtherEquipSettleService.getZxSaOtherEquipSettleBillNo(zxSaOtherEquipSettle);
    }

    @ApiOperation(value="其他类结算导出", notes="其他类结算导出")
    @ApiImplicitParam(name = "zxSaOtherEquipSettle", value = "其他类结算entity", dataType = "ZxSaOtherEquipSettle")
    @RequireToken
    @PostMapping("/exportZxSaOtherEquipSettle")
    public void exportZxSaOtherEquipSettle(@RequestBody(required = false) ZxSaOtherEquipSettle zxSaOtherEquipSettle, HttpServletResponse response) {
        zxSaOtherEquipSettleService.exportZxSaOtherEquipSettle(zxSaOtherEquipSettle,response);
    }

    @ApiOperation(value="其他类结算-详情一览报表", notes="其他类结算-详情一览报表")
    @ApiImplicitParam(name = "zxSaOtherEquipSettle", value = "其他类结算entity", dataType = "ZxSaOtherEquipSettle")
    @PostMapping("/ureportZxSaOtherEquipSettleList")
    public List<ZxSaOtherEquipSettle> ureportZxSaOtherEquipSettleList(@RequestBody(required = false) ZxSaOtherEquipSettle zxSaOtherEquipSettle) {
        return zxSaOtherEquipSettleService.ureportZxSaOtherEquipSettleList(zxSaOtherEquipSettle);
    }

    @ApiOperation(value="其他类结算-营改增,审批内容的结算单明细报表前端查看", notes="其他类结算-,审批内容的结算单明细报表前端查看")
    @ApiImplicitParam(name = "zxSaOtherEquipSettle", value = "其他类结算entity", dataType = "ZxSaOtherEquipSettle")
    @PostMapping("/getUreportZxSaOtherEquipSettleList")
    public  ResponseEntity getUreportZxSaOtherEquipSettleList(@RequestBody(required = false) ZxSaOtherEquipSettle zxSaOtherEquipSettle) {
        return zxSaOtherEquipSettleService.getUreportZxSaOtherEquipSettleList(zxSaOtherEquipSettle);
    }

    @ApiOperation(value="其他类结算-营改增导出报表", notes="其他类结算-营改增导出报表")
    @ApiImplicitParam(name = "zxSaOtherEquipSettle", value = "其他类结算entity", dataType = "ZxSaOtherEquipSettle")
    @PostMapping("/ureportZxSaOtherEquipSettleListYGZ")
    public List<ZxSaOtherEquipSettle> ureportZxSaOtherEquipSettleList_YGZ(@RequestBody(required = false) ZxSaOtherEquipSettle zxSaOtherEquipSettle) {
        return zxSaOtherEquipSettleService.ureportZxSaOtherEquipSettleList_YGZ(zxSaOtherEquipSettle);
    }

}
