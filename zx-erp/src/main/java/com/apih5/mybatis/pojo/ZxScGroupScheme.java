package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONArray;

import com.apih5.framework.entity.BasePojo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class ZxScGroupScheme extends BasePojo {
    // 主键
    private String zxScGroupSchemeId;

    // 施组编号
    private String schemeNo;

    // 施组名称
    private String schemeName;

    // 项目名称ID
    private String projectId;

    // 项目名称
    private String projectName;

    // 所属公司ID
    private String comId;

    // 单位名称
    private String comName;

    // 工程类别
    private String projectType;

    // 所在省份
    private String province;

    // 合同工期
    private String contrDuration;

    // 计划开工日期
    private Date planDate;

    // 合同竣工日期
    private Date contrEndDate;

    // 合同金额(万元)
    private BigDecimal contractAmt;

    // 项目经理
    private String projManager;

    // 项目总工
    private String projEngineer;

    // 联系方式
    private String engineerTel;

    // 项目联系人
    private String schemeAppro;

    // 联系方式
    private String approTel;

    // 发起人
    private String beginer;

    // 评审状态
    private String apih5FlowStatus;

    // 流程实例ID、用于查看流程图
    private String instProcessId;

    // 用于查看审批进度
    private String workitemId;

    // 所属公司排序
    private String comOrders;

    // 上报日期
    private Date bizDate;

    // 评审结束时间
    private Date passTime;

    // 序列号
    private String serialNumber;

    // 所属公司ID
    private String companyId;

    // 所属公司名称
    private String companyName;

    // 意见1
    private String opinionFieId1;

    // 意见2
    private String opinionFieId2;

    // 意见3
    private String opinionFieId3;

    // 意见4
    private String opinionFieId4;

    // 意见5
    private String opinionFieId5;

    // 意见6
    private String opinionFieId6;

    // 意见7
    private String opinionFieId7;

    // 意见8
    private String opinionFieId8;

    // 意见9
    private String opinionFieId9;

    // 意见10
    private String opinionFieId10;

    // 流程Id
    private String flowId;

    // work_id
    private String workId;

    // 审核节点状态
    private String apih5FlowNodeStatus;

    // 备注
    private String remark;

    // 排序
    private int sort=0;

    private String orgID;

    private List<ZxErpFile> zxZhengWenFileList;

    public String getZxScGroupSchemeId() {
        return zxScGroupSchemeId == null ? "" : zxScGroupSchemeId.trim();
    }

    public void setZxScGroupSchemeId(String zxScGroupSchemeId) {
        this.zxScGroupSchemeId = zxScGroupSchemeId == null ? null : zxScGroupSchemeId.trim();
    }

    public String getSchemeNo() {
        return schemeNo == null ? "" : schemeNo.trim();
    }

    public void setSchemeNo(String schemeNo) {
        this.schemeNo = schemeNo == null ? null : schemeNo.trim();
    }

    public String getSchemeName() {
        return schemeName == null ? "" : schemeName.trim();
    }

    public void setSchemeName(String schemeName) {
        this.schemeName = schemeName == null ? null : schemeName.trim();
    }

    public String getProjectId() {
        return projectId == null ? "" : projectId.trim();
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId == null ? null : projectId.trim();
    }

    public String getProjectName() {
        return projectName == null ? "" : projectName.trim();
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName == null ? null : projectName.trim();
    }

    public String getComId() {
        return comId == null ? "" : comId.trim();
    }

    public void setComId(String comId) {
        this.comId = comId == null ? null : comId.trim();
    }

    public String getComName() {
        return comName == null ? "" : comName.trim();
    }

    public void setComName(String comName) {
        this.comName = comName == null ? null : comName.trim();
    }

    public String getProjectType() {
        return projectType == null ? "" : projectType.trim();
    }

    public void setProjectType(String projectType) {
        this.projectType = projectType == null ? null : projectType.trim();
    }

    public String getProvince() {
        return province == null ? "" : province.trim();
    }

    public void setProvince(String province) {
        this.province = province == null ? null : province.trim();
    }

    public String getContrDuration() {
        return contrDuration == null ? "" : contrDuration.trim();
    }

    public void setContrDuration(String contrDuration) {
        this.contrDuration = contrDuration == null ? null : contrDuration.trim();
    }

    public Date getPlanDate() {
        return planDate;
    }

    public void setPlanDate(Date planDate) {
        this.planDate = planDate;
    }

    public Date getContrEndDate() {
        return contrEndDate;
    }

    public void setContrEndDate(Date contrEndDate) {
        this.contrEndDate = contrEndDate;
    }

    public BigDecimal getContractAmt() {
        return contractAmt;
    }

    public void setContractAmt(BigDecimal contractAmt) {
        this.contractAmt = contractAmt;
    }

    public String getProjManager() {
        return projManager == null ? "" : projManager.trim();
    }

    public void setProjManager(String projManager) {
        this.projManager = projManager == null ? null : projManager.trim();
    }

    public String getProjEngineer() {
        return projEngineer == null ? "" : projEngineer.trim();
    }

    public void setProjEngineer(String projEngineer) {
        this.projEngineer = projEngineer == null ? null : projEngineer.trim();
    }

    public String getEngineerTel() {
        return engineerTel == null ? "" : engineerTel.trim();
    }

    public void setEngineerTel(String engineerTel) {
        this.engineerTel = engineerTel == null ? null : engineerTel.trim();
    }

    public String getSchemeAppro() {
        return schemeAppro == null ? "" : schemeAppro.trim();
    }

    public void setSchemeAppro(String schemeAppro) {
        this.schemeAppro = schemeAppro == null ? null : schemeAppro.trim();
    }

    public String getApproTel() {
        return approTel == null ? "" : approTel.trim();
    }

    public void setApproTel(String approTel) {
        this.approTel = approTel == null ? null : approTel.trim();
    }

    public String getBeginer() {
        return beginer == null ? "" : beginer.trim();
    }

    public void setBeginer(String beginer) {
        this.beginer = beginer == null ? null : beginer.trim();
    }

    public String getApih5FlowStatus() {
        return apih5FlowStatus == null ? "" : apih5FlowStatus.trim();
    }

    public void setApih5FlowStatus(String apih5FlowStatus) {
        this.apih5FlowStatus = apih5FlowStatus == null ? null : apih5FlowStatus.trim();
    }

    public String getInstProcessId() {
        return instProcessId == null ? "" : instProcessId.trim();
    }

    public void setInstProcessId(String instProcessId) {
        this.instProcessId = instProcessId == null ? null : instProcessId.trim();
    }

    public String getWorkitemId() {
        return workitemId == null ? "" : workitemId.trim();
    }

    public void setWorkitemId(String workitemId) {
        this.workitemId = workitemId == null ? null : workitemId.trim();
    }

    public String getComOrders() {
        return comOrders == null ? "" : comOrders.trim();
    }

    public void setComOrders(String comOrders) {
        this.comOrders = comOrders == null ? null : comOrders.trim();
    }

    public Date getBizDate() {
        return bizDate;
    }

    public void setBizDate(Date bizDate) {
        this.bizDate = bizDate;
    }

    public Date getPassTime() {
        return passTime;
    }

    public void setPassTime(Date passTime) {
        this.passTime = passTime;
    }

    public String getSerialNumber() {
        return serialNumber == null ? "" : serialNumber.trim();
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber == null ? null : serialNumber.trim();
    }

    public String getCompanyId() {
        return companyId == null ? "" : companyId.trim();
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId == null ? null : companyId.trim();
    }

    public String getCompanyName() {
        return companyName == null ? "" : companyName.trim();
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName == null ? null : companyName.trim();
    }

    public String getOpinionFieId1() {
        return opinionFieId1 == null ? "" : opinionFieId1.trim();
    }

    public void setOpinionFieId1(String opinionFieId1) {
        this.opinionFieId1 = opinionFieId1 == null ? null : opinionFieId1.trim();
    }

    public String getOpinionFieId2() {
        return opinionFieId2 == null ? "" : opinionFieId2.trim();
    }

    public void setOpinionFieId2(String opinionFieId2) {
        this.opinionFieId2 = opinionFieId2 == null ? null : opinionFieId2.trim();
    }

    public String getOpinionFieId3() {
        return opinionFieId3 == null ? "" : opinionFieId3.trim();
    }

    public void setOpinionFieId3(String opinionFieId3) {
        this.opinionFieId3 = opinionFieId3 == null ? null : opinionFieId3.trim();
    }

    public String getOpinionFieId4() {
        return opinionFieId4 == null ? "" : opinionFieId4.trim();
    }

    public void setOpinionFieId4(String opinionFieId4) {
        this.opinionFieId4 = opinionFieId4 == null ? null : opinionFieId4.trim();
    }

    public String getOpinionFieId5() {
        return opinionFieId5 == null ? "" : opinionFieId5.trim();
    }

    public void setOpinionFieId5(String opinionFieId5) {
        this.opinionFieId5 = opinionFieId5 == null ? null : opinionFieId5.trim();
    }

    public String getOpinionFieId6() {
        return opinionFieId6 == null ? "" : opinionFieId6.trim();
    }

    public void setOpinionFieId6(String opinionFieId6) {
        this.opinionFieId6 = opinionFieId6 == null ? null : opinionFieId6.trim();
    }

    public String getOpinionFieId7() {
        return opinionFieId7 == null ? "" : opinionFieId7.trim();
    }

    public void setOpinionFieId7(String opinionFieId7) {
        this.opinionFieId7 = opinionFieId7 == null ? null : opinionFieId7.trim();
    }

    public String getOpinionFieId8() {
        return opinionFieId8 == null ? "" : opinionFieId8.trim();
    }

    public void setOpinionFieId8(String opinionFieId8) {
        this.opinionFieId8 = opinionFieId8 == null ? null : opinionFieId8.trim();
    }

    public String getOpinionFieId9() {
        return opinionFieId9 == null ? "" : opinionFieId9.trim();
    }

    public void setOpinionFieId9(String opinionFieId9) {
        this.opinionFieId9 = opinionFieId9 == null ? null : opinionFieId9.trim();
    }

    public String getOpinionFieId10() {
        return opinionFieId10 == null ? "" : opinionFieId10.trim();
    }

    public void setOpinionFieId10(String opinionFieId10) {
        this.opinionFieId10 = opinionFieId10 == null ? null : opinionFieId10.trim();
    }

    public String getFlowId() {
        return flowId == null ? "" : flowId.trim();
    }

    public void setFlowId(String flowId) {
        this.flowId = flowId == null ? null : flowId.trim();
    }

    public String getWorkId() {
        return workId == null ? "" : workId.trim();
    }

    public void setWorkId(String workId) {
        this.workId = workId == null ? null : workId.trim();
    }

    public String getApih5FlowNodeStatus() {
        return apih5FlowNodeStatus == null ? "" : apih5FlowNodeStatus.trim();
    }

    public void setApih5FlowNodeStatus(String apih5FlowNodeStatus) {
        this.apih5FlowNodeStatus = apih5FlowNodeStatus == null ? null : apih5FlowNodeStatus.trim();
    }

    public String getRemark() {
        return remark == null ? "" : remark.trim();
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public String getOrgID() {
        return orgID;
    }

    public void setOrgID(String orgID) {
        this.orgID = orgID;
    }

    public List<ZxErpFile> getZxZhengWenFileList() {
        return zxZhengWenFileList;
    }

    public void setZxZhengWenFileList(List<ZxErpFile> zxZhengWenFileList) {
        this.zxZhengWenFileList = zxZhengWenFileList;
    }
}
