package com.apih5.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.apih5.framework.annotation.RequireToken;
import com.apih5.framework.components.Apih5Properties;
import com.apih5.framework.constant.ConfigConst;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxSaCostRatio;
import com.apih5.mybatis.pojo.ZxSaProjectSettleAudit;
import com.apih5.service.ZxSaProjectSettleAuditService;
import com.apih5.utils.ZxErpUtil;
import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfig;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

@RestController
public class ZxSaProjectSettleAuditController {
	@Autowired(required = true)
	private ResponseEntity repEntity;
	
	@ApolloConfig(ConfigConst.PUBLIC_OTHER_API)
	private Config publicConfig;

    @Autowired(required = true)
    private ZxSaProjectSettleAuditService zxSaProjectSettleAuditService;

    @ApiOperation(value="查询结算管理-工程类结算-工程类结算单(原表iesa_ProjectSettleAudit)", notes="查询结算管理-工程类结算-工程类结算单(原表iesa_ProjectSettleAudit)")
    @ApiImplicitParam(name = "zxSaProjectSettleAudit", value = "结算管理-工程类结算-工程类结算单(原表iesa_ProjectSettleAudit)entity", dataType = "ZxSaProjectSettleAudit")
    @RequireToken
    @PostMapping("/getZxSaProjectSettleAuditList")
    public ResponseEntity getZxSaProjectSettleAuditList(@RequestBody(required = false) ZxSaProjectSettleAudit zxSaProjectSettleAudit) {
        return zxSaProjectSettleAuditService.getZxSaProjectSettleAuditListByCondition(zxSaProjectSettleAudit);
    }

    @ApiOperation(value="查询详情结算管理-工程类结算-工程类结算单(原表iesa_ProjectSettleAudit)", notes="查询详情结算管理-工程类结算-工程类结算单(原表iesa_ProjectSettleAudit)")
    @ApiImplicitParam(name = "zxSaProjectSettleAudit", value = "结算管理-工程类结算-工程类结算单(原表iesa_ProjectSettleAudit)entity", dataType = "ZxSaProjectSettleAudit")
    @RequireToken
    @PostMapping("/getZxSaProjectSettleAuditDetail")
    public ResponseEntity getZxSaProjectSettleAuditDetail(@RequestBody(required = false) ZxSaProjectSettleAudit zxSaProjectSettleAudit) {
        return zxSaProjectSettleAuditService.getZxSaProjectSettleAuditDetail(zxSaProjectSettleAudit);
    }

    @ApiOperation(value="新增结算管理-工程类结算-工程类结算单(原表iesa_ProjectSettleAudit)", notes="新增结算管理-工程类结算-工程类结算单(原表iesa_ProjectSettleAudit)")
    @ApiImplicitParam(name = "zxSaProjectSettleAudit", value = "结算管理-工程类结算-工程类结算单(原表iesa_ProjectSettleAudit)entity", dataType = "ZxSaProjectSettleAudit")
    @RequireToken
    @PostMapping("/addZxSaProjectSettleAudit")
    public ResponseEntity addZxSaProjectSettleAudit(@RequestBody(required = false) ZxSaProjectSettleAudit zxSaProjectSettleAudit) {
        return zxSaProjectSettleAuditService.saveZxSaProjectSettleAudit(zxSaProjectSettleAudit);
    }

    @ApiOperation(value="更新结算管理-工程类结算-工程类结算单(原表iesa_ProjectSettleAudit)", notes="更新结算管理-工程类结算-工程类结算单(原表iesa_ProjectSettleAudit)")
    @ApiImplicitParam(name = "zxSaProjectSettleAudit", value = "结算管理-工程类结算-工程类结算单(原表iesa_ProjectSettleAudit)entity", dataType = "ZxSaProjectSettleAudit")
    @RequireToken
    @PostMapping("/updateZxSaProjectSettleAudit")
    public ResponseEntity updateZxSaProjectSettleAudit(@RequestBody(required = false) ZxSaProjectSettleAudit zxSaProjectSettleAudit) {
        return zxSaProjectSettleAuditService.updateZxSaProjectSettleAudit(zxSaProjectSettleAudit);
    }

    @ApiOperation(value="删除结算管理-工程类结算-工程类结算单(原表iesa_ProjectSettleAudit)", notes="删除结算管理-工程类结算-工程类结算单(原表iesa_ProjectSettleAudit)")
    @ApiImplicitParam(name = "zxSaProjectSettleAuditList", value = "结算管理-工程类结算-工程类结算单(原表iesa_ProjectSettleAudit)List", dataType = "List<ZxSaProjectSettleAudit>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxSaProjectSettleAudit")
    public ResponseEntity batchDeleteUpdateZxSaProjectSettleAudit(@RequestBody(required = false) List<ZxSaProjectSettleAudit> zxSaProjectSettleAuditList) {
        return zxSaProjectSettleAuditService.batchDeleteUpdateZxSaProjectSettleAudit(zxSaProjectSettleAuditList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    
    @ApiOperation(value="新增工程类结算单--项目名称选择", notes="新增工程类结算单--项目名称选择")
    @ApiImplicitParam(name = "zxSaProjectSettleAudit", value = "结算管理-工程类结算-工程类结算单entity", dataType = "ZxSaProjectSettleAudit")
    @RequireToken
    @PostMapping("/getZxSaProjectSettleAuditProjectList")
    public ResponseEntity getZxSaProjectSettleAuditProjectList(@RequestBody(required = false) ZxSaProjectSettleAudit zxSaProjectSettleAudit) {
        return zxSaProjectSettleAuditService.getZxSaProjectSettleAuditProjectList(zxSaProjectSettleAudit);
    }
    
    @ApiOperation(value="新增工程类结算单--合同编号选择", notes="新增工程类结算单--合同编号选择")
    @ApiImplicitParam(name = "zxSaProjectSettleAudit", value = "结算管理-工程类结算-工程类结算单entity", dataType = "ZxSaProjectSettleAudit")
    @RequireToken
    @PostMapping("/getZxSaProjectSettleAuditContractNoList")
    public ResponseEntity getZxSaProjectSettleAuditContractNoList(@RequestBody(required = false) ZxSaProjectSettleAudit zxSaProjectSettleAudit) {
    	return zxSaProjectSettleAuditService.getZxSaProjectSettleAuditContractNoList(zxSaProjectSettleAudit);
    }
    
    @ApiOperation(value="流程发起更新工程类结算单", notes="流程发起更新工程类结算单")
    @ApiImplicitParam(name = "zxSaProjectSettleAudit", value = "工程类结算单entity", dataType = "ZxSaProjectSettleAudit")
    @RequireToken
    @PostMapping("/addZxSaProjectSettleAuditOnCommitFlow")
    public ResponseEntity addZxSaProjectSettleAuditOnCommitFlow(@RequestBody(required = false) ZxSaProjectSettleAudit zxSaProjectSettleAudit) {
        return zxSaProjectSettleAuditService.addZxSaProjectSettleAuditOnCommitFlow(zxSaProjectSettleAudit);
    }
    
    @ApiOperation(value="流程发起更新工程类结算单", notes="流程发起更新工程类结算单")
    @ApiImplicitParam(name = "zxSaProjectSettleAudit", value = "工程类结算单entity", dataType = "ZxSaProjectSettleAudit")
    @RequireToken
    @PostMapping("/updateZxSaProjectSettleAuditOnCommitFlow")
    public ResponseEntity updateZxSaProjectSettleAuditOnCommitFlow(@RequestBody(required = false) ZxSaProjectSettleAudit zxSaProjectSettleAudit) {
    	return zxSaProjectSettleAuditService.updateZxSaProjectSettleAuditOnCommitFlow(zxSaProjectSettleAudit);
    }
    
    @ApiOperation(value="查询详情工程类结算单", notes="查询详情工程类结算单")
    @ApiImplicitParam(name = "zxSaProjectSettleAudit", value = "工程类结算单entity", dataType = "ZxSaProjectSettleAudit")
    @PostMapping("/getZxSaProjectSettleAuditDetailNoToken")
    public ZxSaProjectSettleAudit getZxSaProjectSettleAuditDetailNoToken(@RequestBody(required = false) ZxSaProjectSettleAudit zxSaProjectSettleAudit) {
        return zxSaProjectSettleAuditService.getZxSaProjectSettleAuditDetailNoToken(zxSaProjectSettleAudit);
    }
    
    @ApiOperation(value="查询集中结算管控指标输出台账Tree", notes="查询集中结算管控指标输出台账Tree")
    @ApiImplicitParam(name = "zxSaProjectSettleAudit", value = "工程类结算单entity", dataType = "ZxSaProjectSettleAudit")
    @PostMapping("/getZxSaSettleControlLedger")
    public ResponseEntity getZxSaSettleControlLedger(@RequestBody JSONObject jsonObject) {
        String userId = "";//TokenUtils.getUserId(request);
        Object object = ZxErpUtil.getApiContent(userId, "getZxSaSettleControlLedgerList", jsonObject);
        JSONObject obj = new JSONObject(object);
        JSONArray returnArray = new JSONArray(obj.get("data"));
    	List<ZxSaCostRatio> returnList = JSONUtil.toList(returnArray, ZxSaCostRatio.class);
        return repEntity.ok(getTree(returnList));
    }
    
    @ApiOperation(value="查询集中结算管控指标输出台账List", notes="查询集中结算管控指标输出台账List")
    @ApiImplicitParam(name = "zxSaProjectSettleAudit", value = "工程类结算单entity", dataType = "ZxSaProjectSettleAudit")
    @PostMapping("/getZxSaSettleControlLedgerTreeToList")
    public List<ZxSaCostRatio> getZxSaSettleControlLedgerTreeToList(@RequestBody JSONObject jsonObject) {
    	String userId = "";//TokenUtils.getUserId(request);
        Object object = ZxErpUtil.getApiContent(userId, "getZxSaSettleControlLedgerList", jsonObject);
        JSONObject obj = new JSONObject(object);
        JSONArray returnArray = new JSONArray(obj.get("data"));
    	List<ZxSaCostRatio> returnList = JSONUtil.toList(returnArray, ZxSaCostRatio.class);
    	returnList = returnList.stream().sorted((s1, s2) -> s1.getTreeNode().compareTo(s2.getTreeNode())).collect(Collectors.toList());
        
    	String[] str = new String[] {"", "  ", "    ", "      ", "        ", "          ", "            "};
    	returnList.forEach(bean -> {
    		bean.setDepartmentName(str[(bean.getTreeNode().length() / 4 - 1)] + bean.getDepartmentName());
    	});
    	
        return returnList;
    }
    
    @ApiOperation(value="查询集中结算管控指标输出台账List", notes="查询集中结算管控指标输出台账List")
    @ApiImplicitParam(name = "zxSaProjectSettleAudit", value = "工程类结算单entity", dataType = "ZxSaProjectSettleAudit")
    @PostMapping("/getZxSaCostRatioInfo")
    public ZxSaCostRatio getZxSaCostRatioInfo(@RequestBody ZxSaCostRatio zxSaCostRatio) {
    	zxSaCostRatio.setPeriod("查询月份：" + DateUtil.format(zxSaCostRatio.getPeriodTime(), "yyyy年MM月"));
    	zxSaCostRatio.setDepartmentName("查询单位名称：" + zxSaCostRatio.getDepartmentName());
    	zxSaCostRatio.setCompanyName("集中结算管控指标输出台账（" + (StrUtil.equals("1", zxSaCostRatio.getBusinessDivisionFlag()) ? "分子公司" : "事业部") + "）");
    	return zxSaCostRatio;
    }
    
    @ApiOperation(value="导出集中结算", notes="导出集中结算")
    @ApiImplicitParam(name = "zxSaProjectSettleAudit", value = "工程类结算单entity", dataType = "ZxSaProjectSettleAudit")
    @PostMapping("/exportZxSaCentralizedSettlement")
    public ResponseEntity exportZxSaCentralizedSettlement(@RequestBody ZxSaCostRatio zxSaCostRatio) {
    	if (zxSaCostRatio == null || StrUtil.isEmpty(zxSaCostRatio.getDepartmentId())) {
			return repEntity.layerMessage("no", "DepartmentId不能为空！");
		}
		String reportUrl = publicConfig.getProperty("report.web.url","");
		String webUrl = Apih5Properties.getWebUrl();
		
		String fileName = "集中结算";
		// 文件路径
		String fileUrl = reportUrl + "excel?_u=file:" + fileName + ".xml&_n=集中结算&url=" + webUrl + "&periodTime=" + zxSaCostRatio.getPeriodTime().getTime() 
		+ "&departmentId=" + zxSaCostRatio.getDepartmentId()
		+ "&departmentName=" + zxSaCostRatio.getDepartmentName()
		+ "&businessDivisionFlag=" + zxSaCostRatio.getBusinessDivisionFlag();
		return repEntity.ok(fileUrl);
    }
    
    /**
     * 获取树形结构
     * @param costRatioList --
     * @return
     */
    private JSONArray getTree(List<ZxSaCostRatio> costRatioList) {
        String child = "children";
        JSONArray r = new JSONArray();
        JSONArray newArr = new JSONArray();
        JSONObject hash = new JSONObject();
        if (costRatioList != null && costRatioList.size() > 0) {
            for (ZxSaCostRatio costRatio : costRatioList) {
                JSONObject newJSONObj = new JSONObject();
                newJSONObj.set("id", costRatio.getCostRatioId());
                newJSONObj.set("parentID", costRatio.getPid());
                newJSONObj.set("thisMonthContractNum", costRatio.getThisMonthContractNum());
                newJSONObj.set("thisMonthSettleContractNum", costRatio.getThisMonthSettleContractNum());
                newJSONObj.set("thisMonthIndexValue", costRatio.getThisMonthIndexValue());
                newJSONObj.set("thisMonthIndexValueQualifiedFlag", costRatio.getThisMonthIndexValueQualifiedFlag());
                newJSONObj.set("thisMonthQualifiedConstructionProjectNum", costRatio.getThisMonthQualifiedConstructionProjectNum());
                newJSONObj.set("thisMonthConstructionAllProjectNum", costRatio.getThisMonthConstructionAllProjectNum());
                newJSONObj.set("thisMonthQualifiedIndexValue", costRatio.getThisMonthQualifiedIndexValue());
                newJSONObj.set("thisMonthHaveFileProjectNum", costRatio.getThisMonthHaveFileProjectNum());
                newJSONObj.set("thisMonthConstructionFileAllNum", costRatio.getThisMonthConstructionFileAllNum());
                newJSONObj.set("ratioOfArchiveItemsIndexValue", costRatio.getRatioOfArchiveItemsIndexValue());
                newJSONObj.set("totalAmt", costRatio.getTotalAmt());
                newJSONObj.set("stockAmt", costRatio.getStockAmt());
                newJSONObj.set("bookAmt", costRatio.getBookAmt());
                newJSONObj.set("unrecordAmt", costRatio.getUnrecordAmt());
                newJSONObj.set("rdAmt", costRatio.getRdAmt());
                newJSONObj.set("totalSettleIndexValue", costRatio.getTotalSettleIndexValue());
                newJSONObj.set("totalSettleQualifiedFlag", costRatio.getTotalSettleQualifiedFlag());
                newJSONObj.set("thisMonthQualifiedConstructionFileAllNum", costRatio.getThisMonthQualifiedConstructionFileAllNum());
                newJSONObj.set("thisMonthConstructionFileTotalNum", costRatio.getThisMonthConstructionFileTotalNum());
                newJSONObj.set("totalSettleQualifiedValue", costRatio.getTotalSettleQualifiedValue());
                newJSONObj.set("departmentId", costRatio.getDepartmentId());
                newJSONObj.set("departmentName", costRatio.getDepartmentName());
                hash.set(costRatio.getCostRatioId(), newJSONObj);
                newArr.add(newJSONObj);
            }
            for (int j = 0; j < newArr.size(); j++) {
                JSONObject aVal = newArr.getJSONObject(Integer.valueOf(j));
                JSONObject hashVP = hash.getJSONObject(aVal.getStr("parentID"));
                if (hashVP != null) {
                    if (hashVP.get(child) != null) {
                        JSONArray ch = hashVP.getJSONArray(child);
                        ch.add(aVal);
                        hashVP.set(child, ch);
                    } else {
                        JSONArray ch = new JSONArray();
                        ch.add(aVal);
                        hashVP.set(child, ch);
                    }
                } else
                    r.add(aVal);
            }
        }
        return r;
    }
}
