package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONArray;

import com.apih5.framework.entity.BasePojo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class ZxBuProjectType extends BasePojo {
    // 主键
    private String zxBuProjectTypeId;

    // 项目ID
    private String orgID;

    // 项目名称
    private String orgName;

    // 合同中标价(亿元)
    private BigDecimal contractCost;

    // 总工期(月)
    private BigDecimal duration;

    // 折算系数
    private BigDecimal rate;

    // 折算总合同金额(亿元)ID
    private String codeID1;

    // 折算总合同金额(亿元)
    private BigDecimal codeNum1;

    // 折算平均年产值(亿元)ID
    private String codeID2;

    // 折算平均年产值(亿元)
    private BigDecimal codeNum2;

    // 工程类别划分标准扩展ID3
    private String codeID3;

    // 工程类别划分标准扩展3
    private BigDecimal codeNum3;

    // 工程类别划分标准扩展ID4
    private String codeID4;

    // 工程类别划分标准扩展4
    private BigDecimal codeNum4;

    // 工程类别划分标准扩展ID5
    private String codeID5;

    // 工程类别划分标准扩展5
    private BigDecimal codeNum5;

    // 编制日期
    private Date regTime;

    // 项目工程类别
    private String projectTypeName;

    // 工程类型ID
    private String checkLevel1ID;

    // 工程类型
    private String checkLevel1Name;

    // 状态
    private String status;

    // 最后修改时间
    private Date editTime;

    // combProp
    private String combProp;

    // pp1
    private String pp1;

    // pp2
    private String pp2;

    // pp3
    private String pp3;

    // pp4
    private String pp4;

    // pp5
    private String pp5;

    // pp6
    private String pp6;

    // pp7
    private String pp7;

    // pp8
    private String pp8;

    // pp9
    private String pp9;

    // pp10
    private String pp10;

    // 所属工程类别
    private String engiType;

    // 公司id
    private String companyId;

    // 公司名称
    private String companyName;

    // 备注
    private String remarks;

    // 排序
    private int sort=0;

    //明细
    List<ZxBuProjectTypeItem> zxBuProjectTypeItemList;

    public List<ZxBuProjectTypeItem> getZxBuProjectTypeItemList() {
        return zxBuProjectTypeItemList;
    }

    public void setZxBuProjectTypeItemList(List<ZxBuProjectTypeItem> zxBuProjectTypeItemList) {
        this.zxBuProjectTypeItemList = zxBuProjectTypeItemList;
    }

    public String getZxBuProjectTypeId() {
        return zxBuProjectTypeId == null ? "" : zxBuProjectTypeId.trim();
    }

    public void setZxBuProjectTypeId(String zxBuProjectTypeId) {
        this.zxBuProjectTypeId = zxBuProjectTypeId == null ? null : zxBuProjectTypeId.trim();
    }

    public String getOrgID() {
        return orgID == null ? "" : orgID.trim();
    }

    public void setOrgID(String orgID) {
        this.orgID = orgID == null ? null : orgID.trim();
    }

    public String getOrgName() {
        return orgName == null ? "" : orgName.trim();
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName == null ? null : orgName.trim();
    }

    public BigDecimal getContractCost() {
        return contractCost;
    }

    public void setContractCost(BigDecimal contractCost) {
        this.contractCost = contractCost;
    }

    public BigDecimal getDuration() {
        return duration;
    }

    public void setDuration(BigDecimal duration) {
        this.duration = duration;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public String getCodeID1() {
        return codeID1 == null ? "" : codeID1.trim();
    }

    public void setCodeID1(String codeID1) {
        this.codeID1 = codeID1 == null ? null : codeID1.trim();
    }

    public BigDecimal getCodeNum1() {
        return codeNum1;
    }

    public void setCodeNum1(BigDecimal codeNum1) {
        this.codeNum1 = codeNum1;
    }

    public String getCodeID2() {
        return codeID2 == null ? "" : codeID2.trim();
    }

    public void setCodeID2(String codeID2) {
        this.codeID2 = codeID2 == null ? null : codeID2.trim();
    }

    public BigDecimal getCodeNum2() {
        return codeNum2;
    }

    public void setCodeNum2(BigDecimal codeNum2) {
        this.codeNum2 = codeNum2;
    }

    public String getCodeID3() {
        return codeID3 == null ? "" : codeID3.trim();
    }

    public void setCodeID3(String codeID3) {
        this.codeID3 = codeID3 == null ? null : codeID3.trim();
    }

    public BigDecimal getCodeNum3() {
        return codeNum3;
    }

    public void setCodeNum3(BigDecimal codeNum3) {
        this.codeNum3 = codeNum3;
    }

    public String getCodeID4() {
        return codeID4 == null ? "" : codeID4.trim();
    }

    public void setCodeID4(String codeID4) {
        this.codeID4 = codeID4 == null ? null : codeID4.trim();
    }

    public BigDecimal getCodeNum4() {
        return codeNum4;
    }

    public void setCodeNum4(BigDecimal codeNum4) {
        this.codeNum4 = codeNum4;
    }

    public String getCodeID5() {
        return codeID5 == null ? "" : codeID5.trim();
    }

    public void setCodeID5(String codeID5) {
        this.codeID5 = codeID5 == null ? null : codeID5.trim();
    }

    public BigDecimal getCodeNum5() {
        return codeNum5;
    }

    public void setCodeNum5(BigDecimal codeNum5) {
        this.codeNum5 = codeNum5;
    }

    public Date getRegTime() {
        return regTime;
    }

    public void setRegTime(Date regTime) {
        this.regTime = regTime;
    }

    public String getProjectTypeName() {
        return projectTypeName == null ? "" : projectTypeName.trim();
    }

    public void setProjectTypeName(String projectTypeName) {
        this.projectTypeName = projectTypeName == null ? null : projectTypeName.trim();
    }

    public String getCheckLevel1ID() {
        return checkLevel1ID == null ? "" : checkLevel1ID.trim();
    }

    public void setCheckLevel1ID(String checkLevel1ID) {
        this.checkLevel1ID = checkLevel1ID == null ? null : checkLevel1ID.trim();
    }

    public String getCheckLevel1Name() {
        return checkLevel1Name == null ? "" : checkLevel1Name.trim();
    }

    public void setCheckLevel1Name(String checkLevel1Name) {
        this.checkLevel1Name = checkLevel1Name == null ? null : checkLevel1Name.trim();
    }

    public String getStatus() {
        return status == null ? "" : status.trim();
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public Date getEditTime() {
        return editTime;
    }

    public void setEditTime(Date editTime) {
        this.editTime = editTime;
    }

    public String getCombProp() {
        return combProp == null ? "" : combProp.trim();
    }

    public void setCombProp(String combProp) {
        this.combProp = combProp == null ? null : combProp.trim();
    }

    public String getPp1() {
        return pp1 == null ? "" : pp1.trim();
    }

    public void setPp1(String pp1) {
        this.pp1 = pp1 == null ? null : pp1.trim();
    }

    public String getPp2() {
        return pp2 == null ? "" : pp2.trim();
    }

    public void setPp2(String pp2) {
        this.pp2 = pp2 == null ? null : pp2.trim();
    }

    public String getPp3() {
        return pp3 == null ? "" : pp3.trim();
    }

    public void setPp3(String pp3) {
        this.pp3 = pp3 == null ? null : pp3.trim();
    }

    public String getPp4() {
        return pp4 == null ? "" : pp4.trim();
    }

    public void setPp4(String pp4) {
        this.pp4 = pp4 == null ? null : pp4.trim();
    }

    public String getPp5() {
        return pp5 == null ? "" : pp5.trim();
    }

    public void setPp5(String pp5) {
        this.pp5 = pp5 == null ? null : pp5.trim();
    }

    public String getPp6() {
        return pp6 == null ? "" : pp6.trim();
    }

    public void setPp6(String pp6) {
        this.pp6 = pp6 == null ? null : pp6.trim();
    }

    public String getPp7() {
        return pp7 == null ? "" : pp7.trim();
    }

    public void setPp7(String pp7) {
        this.pp7 = pp7 == null ? null : pp7.trim();
    }

    public String getPp8() {
        return pp8 == null ? "" : pp8.trim();
    }

    public void setPp8(String pp8) {
        this.pp8 = pp8 == null ? null : pp8.trim();
    }

    public String getPp9() {
        return pp9 == null ? "" : pp9.trim();
    }

    public void setPp9(String pp9) {
        this.pp9 = pp9 == null ? null : pp9.trim();
    }

    public String getPp10() {
        return pp10 == null ? "" : pp10.trim();
    }

    public void setPp10(String pp10) {
        this.pp10 = pp10 == null ? null : pp10.trim();
    }

    public String getEngiType() {
        return engiType == null ? "" : engiType.trim();
    }

    public void setEngiType(String engiType) {
        this.engiType = engiType == null ? null : engiType.trim();
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

    public String getRemarks() {
        return remarks == null ? "" : remarks.trim();
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks == null ? null : remarks.trim();
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

}
