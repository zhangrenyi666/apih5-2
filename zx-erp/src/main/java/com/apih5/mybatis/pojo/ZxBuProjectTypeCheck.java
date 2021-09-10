package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONArray;

import com.apih5.framework.entity.BasePojo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class ZxBuProjectTypeCheck extends BasePojo {
    // 主键
    private String zxBuProjectTypeCheckId;

    // 父节点
    private String parentID;

    // 树节点
    private String treeNode;

    // 机构ID
    private String orgID;

    // 类型
    private int checkType=0;

    // 编码
    private String checkNo;

    // 名称
    private String checkName;

    // 关系一
    private String operate1;

    // 参数一
    private BigDecimal rate1;

    // 关系二
    private String operate2;

    // 参数二
    private BigDecimal rate2;

    // 参数显示
    private String dispRate;

    // 是否叶子节点
    private int isLeaf=0;

    // 最后编辑时间
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

    // 备注
    private String remarks;

    // 排序
    private int sort=0;

    public String getZxBuProjectTypeCheckId() {
        return zxBuProjectTypeCheckId == null ? "" : zxBuProjectTypeCheckId.trim();
    }

    public void setZxBuProjectTypeCheckId(String zxBuProjectTypeCheckId) {
        this.zxBuProjectTypeCheckId = zxBuProjectTypeCheckId == null ? null : zxBuProjectTypeCheckId.trim();
    }

    public String getParentID() {
        return parentID == null ? "" : parentID.trim();
    }

    public void setParentID(String parentID) {
        this.parentID = parentID == null ? null : parentID.trim();
    }

    public String getTreeNode() {
        return treeNode == null ? "" : treeNode.trim();
    }

    public void setTreeNode(String treeNode) {
        this.treeNode = treeNode == null ? null : treeNode.trim();
    }

    public String getOrgID() {
        return orgID == null ? "" : orgID.trim();
    }

    public void setOrgID(String orgID) {
        this.orgID = orgID == null ? null : orgID.trim();
    }

    public int getCheckType() {
        return checkType;
    }

    public void setCheckType(int checkType) {
        this.checkType = checkType;
    }

    public String getCheckNo() {
        return checkNo == null ? "" : checkNo.trim();
    }

    public void setCheckNo(String checkNo) {
        this.checkNo = checkNo == null ? null : checkNo.trim();
    }

    public String getCheckName() {
        return checkName == null ? "" : checkName.trim();
    }

    public void setCheckName(String checkName) {
        this.checkName = checkName == null ? null : checkName.trim();
    }

    public String getOperate1() {
        return operate1 == null ? "" : operate1.trim();
    }

    public void setOperate1(String operate1) {
        this.operate1 = operate1 == null ? null : operate1.trim();
    }

    public BigDecimal getRate1() {
        return rate1;
    }

    public void setRate1(BigDecimal rate1) {
        this.rate1 = rate1;
    }

    public String getOperate2() {
        return operate2 == null ? "" : operate2.trim();
    }

    public void setOperate2(String operate2) {
        this.operate2 = operate2 == null ? null : operate2.trim();
    }

    public BigDecimal getRate2() {
        return rate2;
    }

    public void setRate2(BigDecimal rate2) {
        this.rate2 = rate2;
    }

    public String getDispRate() {
        return dispRate == null ? "" : dispRate.trim();
    }

    public void setDispRate(String dispRate) {
        this.dispRate = dispRate == null ? null : dispRate.trim();
    }

    public int getIsLeaf() {
        return isLeaf;
    }

    public void setIsLeaf(int isLeaf) {
        this.isLeaf = isLeaf;
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
